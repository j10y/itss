SelectRelItemPanel=Ext.extend(Ext.Panel, {
	id:'selectRelItemPanel',
	title:'��ѡ���滻���ĸ�������',
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
				    {header: "����",  sortable: true, dataIndex: 'name'}, 
				    {header: "����", sortable: true, dataIndex: 'Type'},
				    {header: "���", sortable: true, dataIndex: 'itemCode'},
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
					displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
					emptyMsg : '����ʾ����',
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
					{html: "&nbsp;&nbsp;����:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
					itemName,
					{html: "&nbsp;&nbsp;���:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;'},
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
						text : ' ��ѯ',
						handler : searchConfigItem,
						scope : this,
						iconCls : 'search',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' ����',
						handler : function(){
							searchForm.form.reset();
						},
						scope : this,
						iconCls : 'reset',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' �鿴',
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
						Ext.Msg.alert("��ʾ","��ѡ��������!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","ֻ��ѡ��һ��������!");
						return;
					}
					var itemsId=records[0].get('id');
					var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoForLook.jsp';
					var win=new Ext.Window({
						title:'��������Ϣ',
						width:730,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:350,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?dataId="+ itemsId,
							text : "ҳ�����ڼ�����......",
							method : 'post',
							scope : this
						},
						buttonAlign:"center",
						buttons:[{
							text:'�ر�',
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
											{header:'������',dataIndex:'parentType',sortable:true},
											{header:'������',dataIndex:'parentName',sortable:true},
											{header:'�����',dataIndex:'parentCode',sortable:true},
											{header:'������',dataIndex:'childType',sortable:true},
											{header:'������',dataIndex:'childName',sortable:true},											
											{header:'�ӱ��',dataIndex:'childCode',sortable:true},
											{header:'��ϵ����',dataIndex:'relationShipType',sortable:true},
											{header:'��ϵ���̶ܳ�',dataIndex:'relationShipGrade',sortable:true},
											{header:'�鼯ϵ��',dataIndex:'attachQuotiety',sortable:true},
											{header:'A�˼�����Ϣ',dataIndex:'atechnoInfo',sortable:true},
											{header:'B�˼�����Ϣ',dataIndex:'btechnoInfo',sortable:true},
											{header:'������Ϣ',dataIndex:'otherInfo',sortable:true}
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
			pageSize :10,// ʹ�õ���ϵͳĬ��ֵ
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
			plugins :new Ext.ux.ProgressBarPager()
		});
		var title="���滻�Ĺ�ϵ---"+itemName;
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
			{text : 'ѡ���滻',
				handler : function(){
					var records = Ext.getCmp('replaceItemGrid').getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("��ʾ","��ѡ��Ҫ�滻���ĸ�������!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","һ��ֻ���滻��һ��������!");
						return;
					}
					var itemCodeSelected=records[0].get("itemCode");
					if(itemCodeSelected==itemCode){
						Ext.Msg.alert("��ʾ","Ҫ�滻��ϵ��������ܺ�Ҫ�滻������������ͬ!");
						return;						
					}
					var relRecords = Ext.getCmp('relListGrid').getSelectionModel().getSelections();
					if(relRecords.length==0){
						Ext.Msg.alert("��ʾ","��ѡ��Ҫ�滻�Ĺ�ϵ!");
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
								Ext.Msg.alert("��ʾ", responseText,function(){
								});
							}else{
								Ext.Msg.alert("��ʾ", "�滻�����ѽ��������õ���",function(){
									Ext.getCmp('relListGrid').getStore().reload();
									window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
								});
							}
						},
						failure : function(response, options) {
							Ext.Msg.alert("��ʾ", "ϵͳ�쳣��",function(){
							});
						}
					});
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},"-",{text : 'ȫ���滻',
				handler : function(){
					var records = Ext.getCmp('replaceItemGrid').getSelectionModel().getSelections();
					if(Ext.getCmp('relListGrid').getStore().getRange().length<=0){
						Ext.Msg.alert("��ʾ","û�п����滻�Ĺ�ϵ!");
						return;
					}
					if(records.length==0){
						Ext.Msg.alert("��ʾ","��ѡ��Ҫ�滻���ĸ�������!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","һ��ֻ���滻��һ��������!");
						return;
					}
					var itemCodeSelected=records[0].get("itemCode");
					if(itemCodeSelected==itemCode){
						Ext.Msg.alert("��ʾ","Ҫ�滻��ϵ��������ܺ�Ҫ�滻������������ͬ!");
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
								Ext.Msg.alert("��ʾ", responseText,function(){
								});
							}else{
								Ext.Msg.alert("��ʾ", "�滻�����ѽ��������õ���",function(){
									Ext.getCmp('relListGrid').getStore().reload();
									window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
								});
							}
						},
						failure : function(response, options) {
							Ext.Msg.alert("��ʾ", "ϵͳ�쳣��",function(){
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
