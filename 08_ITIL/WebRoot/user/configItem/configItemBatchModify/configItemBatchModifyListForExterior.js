PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	layout:'fit',
	getModifyGrid:function(type,typeId,backUrl){
   		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											{header:'������',dataIndex:'modifyNo',sortable:true},
											{header:'�������',dataIndex:'name',sortable:true},
											{header:'�������',dataIndex:'descn',sortable:true},
											{header:'���ԭ��',dataIndex:'reason',sortable:true},
											{header:'����ύ��',dataIndex:'applyUser',sortable:true},
											{header:'����ύ����',dataIndex:'applyDate',sortable:true},
											{header:'״̬',dataIndex:'status',sortable:true}
											]);
		this.storeChild=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getBatchModifyInfoList.action?id='+typeId+"&type="+type,
				fields : ['id', 'modifyNo','name','descn','reason','applyUser','applyDate','status'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
				
		});
		this.pageBar = new Ext.PagingToolbar({
			pageSize :5,
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"modifyGrid",
			store : this.storeChild,
			cm : cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			listeners:{
				rowdblclick:function(grid,rowIndex,eventObject){
					var record = grid.getSelectionModel().getSelected();
					var status=record.get('status');
					var id=record.get('id');
					if(status=='�ݸ�'){
						window.parent.location = webContext
							+ '/user/configItem/configItemBatchModify/configItemBatchModify.jsp?dataId='+id+'&typeId='+typeId+"&type="+type+"&backUrl="+backUrl;
					}else{
						window.parent.location = webContext
							+ '/user/configItem/configItemBatchModify/configItemBatchModifyReadOnly.jsp?dataId='+id+'&typeId='+typeId+"&type="+type+"&backUrl="+backUrl;
					}
        
				}
			},
			tbar:[{
				text : '���������',
				handler : function(){
					 /* var backUrl=webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyListForReq.jsp?reqId='+reqId;*/
						window.parent.location = webContext
							+ '/user/configItem/configItemBatchModify/configItemBatchModify.jsp?typeId='+typeId+"&type="+type+"&backUrl="+backUrl;
				},
				scope : this,
				iconCls : 'add'
			},'-',{
				text : 'ɾ������ݸ�',
				iconCls : 'remove',
				scope : this,
				handler:function(){
					var records =  Ext.getCmp("modifyGrid").getSelectionModel().getSelections();
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
						Ext.Msg.alert("��ʾ","���Ϊ:"+message+"�����벻Ϊ�ݸ�,������ɾ��!");
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
										Ext.getCmp("modifyGrid").store.load();
									});
								},
								failure : function() {
									Ext.MessageBox.alert("��ʾ��Ϣ��", "ɾ��ʧ�ܣ�");
								}
							});
								}
							});
						
				}
			}],
			bbar : this.pageBar			
			});
		var param = {
			'start' : 0
		};
		this.storeChild.baseParams=param;
		this.storeChild.load();
		return this.grid; 
   },
	initComponent : function(){
		var backUrl=this.backUrl;
		var typeId=this.typeId;
		var type=this.type;
		this.items=this.getModifyGrid(type,typeId,backUrl);
		PagePanel.superclass.initComponent.call(this);
	}
});
