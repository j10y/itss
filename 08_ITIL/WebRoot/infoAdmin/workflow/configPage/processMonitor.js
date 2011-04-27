ProcessMonitorGridPanel = Ext.extend(Ext.Panel, {
	id : "processMonitorGridPanel",
	// title : "���̼��",
	layout : 'fit',
	items : this.items,
	scope : this,
	
	//�����ѯ�����Ĳ�ѯ����
	searchProcess : function() {
		alert(1);
	},
	
	//�������̵ķ���
	endProcess : function() {
		
		var record = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelected();
		var records = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ����Ҫ����������!");
			return;
		}
		
	},
	
	//�鿴���̵ķ���(˫���¼�ִ�еķ���)
	getProcessInfo : function() {
		var record = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelected();
		var records = Ext.getCmp("processMonitorGrid")
				.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ����Ҫ�鿴������!");
			return;
		}
		if (records.length>1) {
			Ext.Msg.alert("��ʾ", "һ��ֻ�ܲ鿴һ������!");
			return;
		}
		window.location = webContext + '/infoAdmin/workflow/configPage/processMonitorDetail.jsp';
	},
	//����ҳ�������gridPanel
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
					header : '�Զ����',
					dataIndex : 'id',
					hidden : true,
					sortable : true
				}, {
					header : '���̶�������',
					dataIndex : 'processDefineName',
					sortable : true
				}, {
					header : '���̶�������',
					dataIndex : 'processDefineDesc',
					sortable : true
				}, {
					header : '�ڵ�����',
					dataIndex : 'nodeName',
					sortable : true
				},{
					header : '�ڵ�����',
					dataIndex : 'nodeDesc',
					sortable : true
				}, {
					header : '��ʼʱ��',
					dataIndex : 'start',
					sortable : true
				},{
					header : '������',
					dataIndex : 'createUser',
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
				'������������:&nbsp;', {
					name : 'processName',
					xtype : 'textfield',
					width : 200
				},{},{
					text : '��ѯ',
					pressed : true,
					iconCls : 'search',
					handler : this.searchProcess
				}, {
					text : '��������',
					pressed : true,
					iconCls : 'delete',
					handler : this.endProcess
				}, {
					text : '�鿴����',
					pressed : true,
					iconCls : 'edit',
					handler : this.getProcessInfo
				}])
				
		var gridPanel = new Ext.grid.GridPanel({
					id : 'processMonitorGrid',
					title : '���̼���б�',
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
