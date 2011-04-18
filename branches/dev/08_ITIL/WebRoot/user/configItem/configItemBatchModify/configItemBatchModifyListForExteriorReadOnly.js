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
					window.parent.location = webContext+ '/user/configItem/configItemBatchModify/configItemBatchModifyReadOnly.jsp?dataId='+id+'&typeId='+typeId+"&type="+type+"&backUrl="+backUrl;
					
				}
			},
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
