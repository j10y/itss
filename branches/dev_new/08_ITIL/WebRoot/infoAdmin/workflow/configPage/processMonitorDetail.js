ProcessMonitorDetailGridPanel = Ext.extend(Ext.Panel, {
	id : "processMonitorDetailGridPanel",
	// title : "流程监控详细信息",
	layout : 'fit',
	items : this.items,
	scope : this,
	
	//查看流程图的方法(双击事件方法)
	getProcessIMG : function() {
		var record = Ext.getCmp("processMonitorDetailGrid")
				.getSelectionModel().getSelected();
		var records = Ext.getCmp("processMonitorDetailGrid")
				.getSelectionModel().getSelections();
		alert('双击事件');
	},
	//代审页面主面板gridPanel
	getGridPanel : function() {
		var store = new Ext.data.JsonStore({
			id:'gridStore',
//			url: webContext+ '/workflow/preassign.do?methodCall=showAllUserInfoWorkmates',
//			autoLoad : true,
//			root:'data',
			data : [{id:1,createUser:2,processName:3,processDesc:4,nodeName:5,nodeDesc:6,comment:7}],
			fields : ['id', 'createUser', 'processName', 'processDesc','nodeName' ,'nodeDesc' ,'comment'],
			sortInfo: {field: "id", direction: "ASC"}
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm, {
					header : '自动编号',
					dataIndex : 'id',
					hidden : true,
					sortable : true
				}, {
					header : '创建人',
					dataIndex : 'createUser',
					sortable : true
				}, {
					header : '流程名称',
					dataIndex : 'processName',
					sortable : true
				}, {
					header : '流程描述',
					dataIndex : 'processDesc',
					sortable : true
				},{
					header : '节点名称',
					dataIndex : 'nodeName',
					sortable : true
				}, {
					header : '节点描述',
					dataIndex : 'nodeDesc',
					sortable : true
				},{
					header : '审批意见',
					dataIndex : 'comment',
					sortable : true
				}]);
		var bbar = new Ext.PagingToolbar({
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
					emptyMsg : '无显示数据'
				});
				
		var tbar = new Ext.Toolbar([
				'<font color=red>【双击可查看流程图】</font>'])
				
		var gridPanel = new Ext.grid.GridPanel({
					id : 'processMonitorDetailGrid',
					title : '流程监控详细信息列表',
					width : 'auto',
					height : 'auto',
					store : store,
					sm : sm,
					cm : cm,
					tbar : tbar,
					bbar : bbar,
					listeners : {
						'rowdblclick' : this.getProcessIMG
					}
				});
		return gridPanel;
	},

	initComponent : function() {
		this.items = this.getGridPanel();
		ProcessMonitorDetailGridPanel.superclass.initComponent.call(this);
	}
});
