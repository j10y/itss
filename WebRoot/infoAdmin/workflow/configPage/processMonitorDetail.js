ProcessMonitorDetailGridPanel = Ext.extend(Ext.Panel, {
	id : "processMonitorDetailGridPanel",
	// title : "���̼����ϸ��Ϣ",
	layout : 'fit',
	items : this.items,
	scope : this,
	
	//�鿴����ͼ�ķ���(˫���¼�����)
	getProcessIMG : function() {
		var record = Ext.getCmp("processMonitorDetailGrid")
				.getSelectionModel().getSelected();
		var records = Ext.getCmp("processMonitorDetailGrid")
				.getSelectionModel().getSelections();
		alert('˫���¼�');
	},
	//����ҳ�������gridPanel
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
					header : '�Զ����',
					dataIndex : 'id',
					hidden : true,
					sortable : true
				}, {
					header : '������',
					dataIndex : 'createUser',
					sortable : true
				}, {
					header : '��������',
					dataIndex : 'processName',
					sortable : true
				}, {
					header : '��������',
					dataIndex : 'processDesc',
					sortable : true
				},{
					header : '�ڵ�����',
					dataIndex : 'nodeName',
					sortable : true
				}, {
					header : '�ڵ�����',
					dataIndex : 'nodeDesc',
					sortable : true
				},{
					header : '�������',
					dataIndex : 'comment',
					sortable : true
				}]);
		var bbar = new Ext.PagingToolbar({
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
					emptyMsg : '����ʾ����'
				});
				
		var tbar = new Ext.Toolbar([
				'<font color=red>��˫���ɲ鿴����ͼ��</font>'])
				
		var gridPanel = new Ext.grid.GridPanel({
					id : 'processMonitorDetailGrid',
					title : '���̼����ϸ��Ϣ�б�',
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
