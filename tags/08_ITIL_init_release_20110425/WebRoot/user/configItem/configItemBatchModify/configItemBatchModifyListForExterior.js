PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	layout:'fit',
	getModifyGrid:function(type,typeId,backUrl){
   		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											{header:'变更编号',dataIndex:'modifyNo',sortable:true},
											{header:'变更名称',dataIndex:'name',sortable:true},
											{header:'变更描述',dataIndex:'descn',sortable:true},
											{header:'变更原因',dataIndex:'reason',sortable:true},
											{header:'变更提交人',dataIndex:'applyUser',sortable:true},
											{header:'变更提交日期',dataIndex:'applyDate',sortable:true},
											{header:'状态',dataIndex:'status',sortable:true}
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
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
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
					if(status=='草稿'){
						window.parent.location = webContext
							+ '/user/configItem/configItemBatchModify/configItemBatchModify.jsp?dataId='+id+'&typeId='+typeId+"&type="+type+"&backUrl="+backUrl;
					}else{
						window.parent.location = webContext
							+ '/user/configItem/configItemBatchModify/configItemBatchModifyReadOnly.jsp?dataId='+id+'&typeId='+typeId+"&type="+type+"&backUrl="+backUrl;
					}
        
				}
			},
			tbar:[{
				text : '创建变更单',
				handler : function(){
					 /* var backUrl=webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyListForReq.jsp?reqId='+reqId;*/
						window.parent.location = webContext
							+ '/user/configItem/configItemBatchModify/configItemBatchModify.jsp?typeId='+typeId+"&type="+type+"&backUrl="+backUrl;
				},
				scope : this,
				iconCls : 'add'
			},'-',{
				text : '删除变更草稿',
				iconCls : 'remove',
				scope : this,
				handler:function(){
					var records =  Ext.getCmp("modifyGrid").getSelectionModel().getSelections();
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
						Ext.Msg.alert("提示","编号为:"+message+"的申请不为草稿,不允许删除!");
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
										Ext.getCmp("modifyGrid").store.load();
									});
								},
								failure : function() {
									Ext.MessageBox.alert("提示信息：", "删除失败！");
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
