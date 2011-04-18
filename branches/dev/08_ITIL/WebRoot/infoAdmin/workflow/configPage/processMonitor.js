ProcessMonitorGridPanel = Ext.extend(Ext.Panel, {
	id : "processMonitorGridPanel",
	// title : "流程监控",
	layout : 'fit',
	items : this.items,
	scope : this,
	
	//输入查询条件的查询方法
	searchProcess : function() {
		alert(1);
	},
	
	//结束流程的方法
	endProcess : function() {
		
		var record = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelected();
		var records = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择需要结束的流程!");
			return;
		}
		
	},
	
	//查看流程的方法(双击事件执行的方法)
	getProcessInfo : function() {
		var record = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelected();
		var records = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择需要查看的流程!");
			return;
		}
		if (records.length>1) {
			Ext.Msg.alert("提示", "一次只能查看一个流程!");
			return;
		}
		window.location = webContext + '/infoAdmin/workflow/configPage/processMonitorDetail.jsp';
	},
	//代审页面主面板gridPanel
	getGridPanel : function() {
		var store = new Ext.data.JsonStore({
			id:'gridStore',
//			url: webContext+ '/workflow/preassign.do?methodCall=showAllUserInfoWorkmates',
//			autoLoad : true,
//			root:'data',
			data : [{id:1,processDefineName:2,processDefineDesc:3,nodeName:4,nodeDesc:5,start:6,createUser:7}],
			fields : ['id', 'processDefineName', 'processDefineDesc', 'nodeName','nodeDesc' ,'start' ,'createUser'],
			sortInfo: {field: "id", direction: "ASC"}
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm, {
					header : '自动编号',
					dataIndex : 'id',
					hidden : true,
					sortable : true
				}, {
					header : '流程定义名称',
					dataIndex : 'processDefineName',
					sortable : true
				}, {
					header : '流程定义描述',
					dataIndex : 'processDefineDesc',
					sortable : true
				}, {
					header : '节点名称',
					dataIndex : 'nodeName',
					sortable : true
				},{
					header : '节点描述',
					dataIndex : 'nodeDesc',
					sortable : true
				}, {
					header : '开始时间',
					dataIndex : 'start',
					sortable : true
				},{
					header : '创建人',
					dataIndex : 'createUser',
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
				'请输入流程名:&nbsp;', {
					name : 'processName',
					xtype : 'textfield',
					width : 200
				},{},{
					text : '查询',
					pressed : true,
					iconCls : 'search',
					handler : this.searchProcess
				}, {
					text : '结束流程',
					pressed : true,
					iconCls : 'delete',
					handler : this.endProcess
				}, {
					text : '查看流程',
					pressed : true,
					iconCls : 'edit',
					handler : this.getProcessInfo
				}])
				
		var gridPanel = new Ext.grid.GridPanel({
					id : 'processMonitorGrid',
					title : '流程监控列表',
					width : 'auto',
					height : 'auto',
					store : store,
					sm : sm,
					cm : cm,
					tbar : tbar,
					bbar : bbar,
					listeners : {
						'rowdblclick' : this.getProcessInfo
					}
				});
		return gridPanel;
	},

	initComponent : function() {
		this.items = this.getGridPanel();
		ProcessMonitorGridPanel.superclass.initComponent.call(this);
	}
});
