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
			title : "��ѯ����",
			items : [{
				html : "������:",
				cls : 'common-text',
				style:'width:60;height:20;text-align:left;'
			},
			new Ext.form.TextField({
				id:"modifyNo",
				width:150,
				emptyText : '��������'
			}),
			{
				html : "�������:",
				cls : 'common-text',
				style:'width:60;height:20;text-align:left;'
			},
			new Ext.form.TextField({
				id:"name",
				width:150,
				emptyText : '������������'
			}),
			{
				html : "״̬:",
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
				emptyText :'��ѡ��״̬',
				value:0,
				store :new Ext.data.SimpleStore({
					fields : [ "status", "name" ],
					data : [ [ 0, "�ݸ�" ], [ 2, "������" ],[1,"�������"],[3,"����"],["4","����"]]
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
				html : "�ύʱ�俪ʼ:",
				cls : 'common-text',
				style:'width:90;height:20;text-align:left;'
			},
			new Ext.form.DateField({
				id:"applyDateStart",
				width:150,
				format:"Y-m-d",
				altFormats:"Y-m-d",
				emptyText :'��ѡ������'
			}),			{
				html : "�ύʱ�����:",
				cls : 'common-text',
				style:'width:90;height:20;text-align:left;'
			},
			new Ext.form.DateField({
				id:"applyDateEnd",
				width:150,
				format:"Y-m-d",
				altFormats:"Y-m-d",
				emptyText :'��ѡ������'
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
			{header : "������",dataIndex : "modifyNo",width : 100,sortable: true}, 
			{header : "�������",dataIndex : "name",width : 150,sortable: true},
			{header : "�Ƿ����",dataIndex : "emergent",width : 80,sortable: true},
			{header : "����ύ��",dataIndex : "applyUser",width : 100,sortable: true},
			{header : "����ύʱ��",dataIndex : "applyDate",width : 100,sortable: true},
			{header : "״̬",dataIndex : "status",width : 80,sortable: true}
		]);
		
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
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
						text : "��ѯ",
						scope : this,
						iconCls : "search",
						handler : this.searchModify
					},"-",{
					    text : "����",
						scope : this,
						iconCls :"reset",
						handler : function() {
							Ext.getCmp("searchCriteria").form.reset();
						}
					},"-",{
						text : "���������",
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
						text : "ɾ������ݸ�",
						scope : this,
						iconCls : "remove",
						handler : function(){
							var records = this.batchModifyGrid.getSelectionModel().getSelections();
							if(records.length==0){
								Ext.Msg.alert("��ʾ","��ѡ��Ҫɾ���ļ�¼!");
								return;
							}
							var modifyId=new Array();
							var message=""
							for(var i=0;i<records.length;i++){
								if(records[i].get("status")!='�ݸ�'){
									message=message+records[i].get("modifyNo")+",";
								}
								modifyId.push(records[i].get("id"));
							}
							if(message!=''){
								message=message.substring(0,message.length-1);
								Ext.Msg.alert("��ʾ",message+"��Ϊ�ݸ�,������ɾ��!");
								return;
							}
							modifyId=Ext.encode(modifyId);
							Ext.MessageBox.confirm("��ʾ","ȷ��ɾ����",function(buttontext){
								if(buttontext=='yes'){
									Ext.Ajax.request({
										url : webContext +'/configItemAction_deleteBatchModifyDraft.action',
										params:{modifyId:modifyId},
										success : function(response, options) {
											Ext.MessageBox.alert("��ʾ��Ϣ��", "ɾ���ɹ���",function(){
												Ext.getCmp("batchModifyGrid").store.load();
											});
										},
										failure : function() {
											Ext.MessageBox.alert("��ʾ��Ϣ��", "ɾ��ʧ�ܣ�");
										}
									});
										}
									});
						}
					},{xtype: 'tbtext',
					   text:"��<font style='font-weight:lighter' color=red >˫����¼�鿴��ϸ��Ϣ</font>��"}
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
			if(status=='�ݸ�')
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
