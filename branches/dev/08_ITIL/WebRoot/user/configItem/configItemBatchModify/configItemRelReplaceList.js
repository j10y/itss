SelectRelItemPanel=Ext.extend(Ext.Panel, {
	id:'selectRelItemPanel',
	title:'请选择替换到哪个配置项',
	layout:'border',
	width:740,
	height:300,
	initComponent: function(){
		var modifyId=this.modifyId;
		var TypeId=this.TypeId;
		var	searchConfigItem= function() {
			var name=Ext.encode(Ext.getCmp('itemName').getValue());
			name=name.substring(1,name.length-1);
			var code = Ext.getCmp('itemCode').getValue();
			var param = {
						item : "ci",
						name : name,
						type:TypeId,
						code:code,
						start: 0
				};
			store.baseParams=param;
			store.load();
		}
		var store = new Ext.data.JsonStore({ 				
						url: webContext+'/configItemAction_findItem.action?modifyId='+modifyId,
						fields: ['id','name','Type','TypeId','itemCode'],//configType
					    root:'data',
					    totalProperty : 'rowCount',
						sortInfo: {field: "id", direction: "ASC"}
		}); 
		var sm = new Ext.grid.CheckboxSelectionModel();		
		var cm = new Ext.grid.ColumnModel([sm,
				    {header: "名称",  sortable: true, dataIndex: 'name'}, 
				    {header: "类型", sortable: true, dataIndex: 'Type'},
				    {header: "编号", sortable: true, dataIndex: 'itemCode'},
				    {header: "id",  sortable: true, dataIndex: 'id',hidden:true}	
			]); 
				
			var itemName = new Ext.form.TextField({
				id:"itemName",
				width:150,
				name : 'name'
			});
				
			var itemCode = new Ext.form.TextField({
				id:"itemCode",
				width:150,
				name : 'code'
			});
				
			var pageBar = new Ext.PagingToolbar({
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
					emptyMsg : '无显示数据',
					plugins :new Ext.ux.ProgressBarPager()
			});
				
			var searchForm = new Ext.form.FormPanel({
				layout : 'table',
				region:'north',
				height:43,
				frame : true,
				keys:{
				    key:Ext.EventObject.ENTER,
				    fn: function(){
				    	searchConfigItem();
				    },
				    scope: this
				},
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {columns: 4},
				items : [
					{html: "&nbsp;&nbsp;名称:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
					itemName,
					{html: "&nbsp;&nbsp;编号:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
					itemCode
					]
			});
				var grid = new Ext.grid.GridPanel({
						id :'replaceItemGrid',
				        store: store,
				        sm:sm,
				        cm: cm,
				        region:'center',
				        frame:true,
				        loadMask: true,
				        tbar : ['   ', {
						text : ' 查询',
						handler : searchConfigItem,
						scope : this,
						iconCls : 'search',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' 重置',
						handler : function(){
							searchForm.form.reset();
						},
						scope : this,
						iconCls : 'reset',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' 查看',
						handler : function(){
							grid.fireEvent('rowdblclick',grid);
						},
						scope : this,
						iconCls : 'look',
						cls : "x-btn-text-icon"
						}
						],
						bbar : pageBar
				}); 
				grid.on('rowdblclick',function(grid,rowIndex,eventObject){
					var records = grid.getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("提示","请选择配置项!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("提示","只能选择一个配置项!");
						return;
					}
					var itemsId=records[0].get('id');
					var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoForLook.jsp';
					var win=new Ext.Window({
						title:'配置项信息',
						width:730,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:350,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?dataId="+ itemsId,
							text : "页面正在加载中......",
							method : 'post',
							scope : this
						},
						buttonAlign:"center",
						buttons:[{
							text:'关闭',
							handler:function(){
								win.close();
							}
						}
						]
					});
					win.show();
				})
			var param = {
				item : "ci",
				type:TypeId,
				start:0
			};
			store.baseParams=param;
			store.load();
			this.items = [searchForm,grid];	
		SelectRelItemPanel.superclass.initComponent.call(this);	
	}
})
ConfigItemRelReplace=Ext.extend(Ext.Panel, {
	id:'configItemRelReplace',
	frame:true,
	width:740,
	autoScroll:true,
	closable:true,
	getRelListGrid:function(itemCode,modifyId,itemName){
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											{header:'父类型',dataIndex:'parentType',sortable:true},
											{header:'父名称',dataIndex:'parentName',sortable:true},
											{header:'父编号',dataIndex:'parentCode',sortable:true},
											{header:'子类型',dataIndex:'childType',sortable:true},
											{header:'子名称',dataIndex:'childName',sortable:true},											
											{header:'子编号',dataIndex:'childCode',sortable:true},
											{header:'关系类型',dataIndex:'relationShipType',sortable:true},
											{header:'关系紧密程度',dataIndex:'relationShipGrade',sortable:true},
											{header:'归集系数',dataIndex:'attachQuotiety',sortable:true},
											{header:'A端技术信息',dataIndex:'atechnoInfo',sortable:true},
											{header:'B端技术信息',dataIndex:'btechnoInfo',sortable:true},
											{header:'其他信息',dataIndex:'otherInfo',sortable:true}
											]);
		this.store=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getReplaceRelList.action?itemCode='+itemCode+"&modifyId="+modifyId,
				fields : ['id','parentType','parentName','parentCode','childType','childName','childCode','relationShipType','relationShipGrade','attachQuotiety','atechnoInfo','btechnoInfo','otherInfo'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.pageBar = new Ext.PagingToolbar({
			id:'pageBar',
			pageSize :10,// 使用的是系统默认值
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		var title="可替换的关系---"+itemName;
		if(title.length>40){
			title=title.substring(0,41)+"......";
		}
		title=title+"("+itemCode+")";
		this.grid = new Ext.grid.GridPanel({
			title:title,
			id:"relListGrid",
			store : this.store,
			height:300,
			width:740,
			cm : cm,
			sm : sm,
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar,
			tbar:[
			{text : '选择替换',
				handler : function(){
					var records = Ext.getCmp('replaceItemGrid').getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("提示","请选择要替换到哪个配置项!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("提示","一次只能替换到一个配置项!");
						return;
					}
					var itemCodeSelected=records[0].get("itemCode");
					if(itemCodeSelected==itemCode){
						Ext.Msg.alert("提示","要替换关系的配置项不能和要替换到的配置项相同!");
						return;						
					}
					var relRecords = Ext.getCmp('relListGrid').getSelectionModel().getSelections();
					if(relRecords.length==0){
						Ext.Msg.alert("提示","请选择要替换的关系!");
						return;
					}
					var rids=new Array();
					for(var i=0;i<relRecords.length;i++){
						rids.push(relRecords[i].get("id"));
					}
					rids=Ext.encode(rids);
					Ext.Ajax.request({
						url : webContext
								+ '/configItemAction_replaceRel.action',
						params : {
							rids:rids,
							replaceItemCode:itemCodeSelected,
							itemCode:itemCode,
							modifyId:modifyId
						},
						success : function(response, options) {
							var responseText=response.responseText;
							if(responseText.trim().length!=0){
								Ext.Msg.alert("提示", responseText,function(){
								});
							}else{
								Ext.Msg.alert("提示", "替换内容已进入变更配置单！",function(){
									Ext.getCmp('relListGrid').getStore().reload();
									window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
								});
							}
						},
						failure : function(response, options) {
							Ext.Msg.alert("提示", "系统异常！",function(){
							});
						}
					});
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},"-",{text : '全部替换',
				handler : function(){
					var records = Ext.getCmp('replaceItemGrid').getSelectionModel().getSelections();
					if(Ext.getCmp('relListGrid').getStore().getRange().length<=0){
						Ext.Msg.alert("提示","没有可以替换的关系!");
						return;
					}
					if(records.length==0){
						Ext.Msg.alert("提示","请选择要替换到哪个配置项!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("提示","一次只能替换到一个配置项!");
						return;
					}
					var itemCodeSelected=records[0].get("itemCode");
					if(itemCodeSelected==itemCode){
						Ext.Msg.alert("提示","要替换关系的配置项不能和要替换到的配置项相同!");
						return;						
					}
					Ext.Ajax.request({
						url : webContext
								+ '/configItemAction_replaceRel.action',
						params : {
							replaceItemCode:itemCodeSelected,
							itemCode:itemCode,
							modifyId:modifyId
						},
						success : function(response, options) {
							var responseText=response.responseText;
							if(responseText.trim().length!=0){
								Ext.Msg.alert("提示", responseText,function(){
								});
							}else{
								Ext.Msg.alert("提示", "替换内容已进入变更配置单！",function(){
									Ext.getCmp('relListGrid').getStore().reload();
									window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
								});
							}
						},
						failure : function(response, options) {
							Ext.Msg.alert("提示", "系统异常！",function(){
							});
						}
					});
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			
			}]
			});
		var params={
				start:0
			}
		this.store.baseParams=params;
		this.store.load();
		return this.grid;
	},
	initComponent: function(){
		var TypeId=this.TypeId;
		var itemCode=this.itemCode;
		var modifyId=this.modifyId;
		var itemName=this.itemName;
        this.items = [new SelectRelItemPanel({modifyId:modifyId,TypeId:TypeId}),this.getRelListGrid(itemCode,modifyId,itemName)];	
		ConfigItemRelReplace.superclass.initComponent.call(this);	
	}
})
