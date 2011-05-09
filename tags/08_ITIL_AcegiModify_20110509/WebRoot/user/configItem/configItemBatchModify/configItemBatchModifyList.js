PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getSearchForm : function() {
		var searchModify=this.searchModify;
		this.panel = new Ext.form.FormPanel({
			id:"searchCriteria",
			region : "north",
			layout : 'table',
			height : 'auto',
			keys:{
			    key:Ext.EventObject.ENTER,
			    fn: function(){
			    	searchModify();
			    },
			    scope: this
			},
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "查询条件",
			items : [{
				html : "变更编号:",
				cls : 'common-text',
				style:'width:60;height:20;text-align:left;'
			},
			new Ext.form.TextField({
				id:"modifyNo",
				width:150,
				emptyText : '请输入编号'
			}),
			{
				html : "变更名称:",
				cls : 'common-text',
				style:'width:60;height:20;text-align:left;'
			},
			new Ext.form.TextField({
				id:"name",
				width:150,
				emptyText : '请输入变更名称'
			}),
			{
				html : "状态:",
				cls : 'common-text',
				style:'width:40;height:20;text-align:left;'
			},
			new Ext.form.ComboBox({
				id:'sta',
				mode :'local',
				width:150,
				editable :false,
				triggerAction :'all',
				hiddenName:'status',
				displayField :'name',
				valueField :'status',
				emptyText :'请选择状态',
				value:0,
				store :new Ext.data.SimpleStore({
					fields : [ "status", "name" ],
					data : [ [ 0, "草稿" ], [ 2, "处理中" ],[1,"处理结束"],[3,"放弃"],["4","不限"]]
				}),
				listeners:{
					'select':function(combo,record,index){
						if(record.get('status')==2||record.get('status')==1){
							Ext.getCmp('removeButton').hide();
						}else{
							Ext.getCmp('removeButton').setVisible(true);
						}				
					}
				}
			}),
			{
				html : "提交时间开始:",
				cls : 'common-text',
				style:'width:90;height:20;text-align:left;'
			},
			new Ext.form.DateField({
				id:"applyDateStart",
				width:150,
				format:"Y-m-d",
				altFormats:"Y-m-d",
				emptyText :'请选择日期'
			}),			{
				html : "提交时间结束:",
				cls : 'common-text',
				style:'width:90;height:20;text-align:left;'
			},
			new Ext.form.DateField({
				id:"applyDateEnd",
				width:150,
				format:"Y-m-d",
				altFormats:"Y-m-d",
				emptyText :'请选择日期'
			})
			]
		});
		return this.panel;
	},
	
	getBatchModifyGrid : function(){
		this.store=new Ext.data.JsonStore({
			url : webContext + '/configItemAction_findBatchModify.action',
				fields : ['id', 'modifyNo','name','emergent','applyUser','applyDate','status'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,
			{header : "变更编号",dataIndex : "modifyNo",width : 100,sortable: true}, 
			{header : "变更名称",dataIndex : "name",width : 150,sortable: true},
			{header : "是否紧急",dataIndex : "emergent",width : 80,sortable: true},
			{header : "变更提交人",dataIndex : "applyUser",width : 100,sortable: true},
			{header : "变更提交时间",dataIndex : "applyDate",width : 100,sortable: true},
			{header : "状态",dataIndex : "status",width : 80,sortable: true}
		]);
		
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.batchModifyGrid = new Ext.grid.GridPanel({
			id:"batchModifyGrid",
			region : "center",
			store : this.store,
			cm : this.cm,
			sm:this.sm,
			trackMouseOver : false,
			loadMask : true,
			bbar : this.pageBar,
			tbar : [{
						text : "查询",
						scope : this,
						iconCls : "search",
						handler : this.searchModify
					},"-",{
					    text : "重置",
						scope : this,
						iconCls :"reset",
						handler : function() {
							Ext.getCmp("searchCriteria").form.reset();
						}
					},"-",{
						text : "创建变更单",
						scope : this,
						iconCls : "add",
						handler : function(){
							var backUrl=webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyList.jsp';
							backUrl=escape(backUrl);
							window.location=webContext+'/user/configItem/configItemBatchModify/configItemBatchModify.jsp?backUrl='+backUrl;
						}
					}, "-",
					{
						id:'removeButton',
						text : "删除变更草稿",
						scope : this,
						iconCls : "remove",
						handler : function(){
							var records = this.batchModifyGrid.getSelectionModel().getSelections();
							if(records.length==0){
								Ext.Msg.alert("提示","请选择要删除的记录!");
								return;
							}
							var modifyId=new Array();
							var message=""
							for(var i=0;i<records.length;i++){
								if(records[i].get("status")!='草稿'){
									message=message+records[i].get("modifyNo")+",";
								}
								modifyId.push(records[i].get("id"));
							}
							if(message!=''){
								message=message.substring(0,message.length-1);
								Ext.Msg.alert("提示",message+"不为草稿,不允许删除!");
								return;
							}
							modifyId=Ext.encode(modifyId);
							Ext.MessageBox.confirm("提示","确认删除？",function(buttontext){
								if(buttontext=='yes'){
									Ext.Ajax.request({
										url : webContext +'/configItemAction_deleteBatchModifyDraft.action',
										params:{modifyId:modifyId},
										success : function(response, options) {
											Ext.MessageBox.alert("提示信息：", "删除成功！",function(){
												Ext.getCmp("batchModifyGrid").store.load();
											});
										},
										failure : function() {
											Ext.MessageBox.alert("提示信息：", "删除失败！");
										}
									});
										}
									});
						}
					},{xtype: 'tbtext',
					   text:"【<font style='font-weight:lighter' color=red >双击记录查看详细信息</font>】"}
					]
		});
		this.batchModifyGrid.on("rowdblclick",function(grid, rowIndex, eventObject){
			var records = grid.getSelectionModel().getSelected();
			var id=records.get("id");
			var status=records.get("status");
			var backUrl=webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyList.jsp';
	   		backUrl=escape(backUrl);
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			var url=webContext+"/configItemAction_getModifyTypeAndTypeId.action?modifyId="+id;
			conn.open("post", url, false);
			conn.send(null);
			if (conn.status == "200") {
				var responseText = conn.responseText;
				var result=Ext.decode(responseText);
				var type=result.type;
				var typeId=result.typeId;
			}
			if(status=='草稿')
				window.location =webContext+'/user/configItem/configItemBatchModify/configItemBatchModify.jsp?dataId='+id+"&typeId="+typeId+"&type="+type+"&backUrl="+backUrl;
			else
				window.location =webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyReadOnly.jsp?dataId='+id+"&typeId="+typeId+"&type="+type+"&backUrl="+backUrl;
		},this);
		return this.batchModifyGrid;
	},
	searchModify:function(){
		var name=Ext.encode(Ext.getCmp("name").getValue());
		name=name.substring(1,name.length-1);
		Ext.getCmp("batchModifyGrid").store.baseParams={
											modifyNo:Ext.getCmp("modifyNo").getValue(),
											name:name,
											applyDateStart:Ext.getCmp("applyDateStart").getRawValue(),
											applyDateEnd:Ext.getCmp("applyDateEnd").getRawValue(),
											status:Ext.getCmp("sta").getValue(),
											start:0};
		Ext.getCmp("batchModifyGrid").store.load();
	},
	items : this.items,
	
	initComponent : function(){
		var batchModifyGrid=this.getBatchModifyGrid();
		var items = new Array();
		items.push(this.getSearchForm());
		items.push(batchModifyGrid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		Ext.getCmp("batchModifyGrid").store.baseParams={
			status:0,
			start:0
		}
		Ext.getCmp("batchModifyGrid").store.load();
	}
});
