PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// baseCls : 'background: #6495ED;',
	closable : true,
	width : 1020,
	height : 400,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	frame : true,
	items : this.items,
	show : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var id = record.get("id");
		window.location = webContext + "/infoAdmin/serviceItem/req_table_info.jsp?id=" + id;
	},
	search : function() {
		var temptn = Ext.getCmp('tempTableName').getValue();
		var param = {
			tableName : temptn,
			start : 1
		};
		this.pageBar.formValue = param;
		this.store.removeAll();
		this.store.load({
			params : param
		})
	},
	create : function() {
		window.location = webContext + "/infoAdmin/serviceItem/req_table_info.jsp";
	},
	initComponent : function() {
		var searchTableName = new Ext.form.TextField({
			fieldLabel : '��ѯ����',
			emptyText : '��������ұ���',
			id : 'tempTableName',
			name : 'tempTableName',
			width : 200
		});
		// �����������
		this.store = new Ext.data.JsonStore({
			url : webContext + '/serviceItem_getReqTables.action',
			fields : ['id', 'tableName', 'tableCnName', 'classNameInfo'],
			totalProperty : "rowCount",
			root : "data",
			id : 'id'
		});

		this.pageBar = new Ext.PagingToolbarExt({
			id : 'pagebar',
			pageSize : 20,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "û�з�������������"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		var sm = new Ext.grid.CheckboxSelectionModel();

		// ����Grid������
		this.grid = new Ext.grid.GridPanel({
			width : 800,
			height : 550,
			loadMask : true,
			id : 'gridpanel',
			frame : true,
			store : this.store,
			bbar : this.pageBar,
			// autoExpandMin : 50,
			trackMouseOve : true,
			tbar : [new Ext.Toolbar.TextItem("��ѯ����"), '-', searchTableName, 
				{
				pressed : true,
				text : "����",
				iconCls : 'search',
				scope : this,
				handler : this.search
				},{
				pressed : true,
				text : "��������������",
				iconCls : 'add',
				scope : this,
				handler : this.create
				}
			],
			sm : sm,
			columns : [sm, {
				id : 'id',
				header : "id",
				width : 50,
				sortable : true,
				dataIndex : 'id',
				hidden : true
			}, {
				header : "����Ӣ����",
				width : 220,
				sortable : true,
				dataIndex : 'tableName'
			}, {
				header : "����������",
				width : 220,
				sortable : true,
				dataIndex : 'tableCnName'
			}, {
				header : "��������",
				width : 300,
				sortable : true,
				dataIndex : 'classNameInfo'
			}]
		})
		var param = {
			tableName : "",
			start : 1
		};

		this.pageBar.formValue = param;
		this.store.removeAll();
		this.store.load({
			params : param
		});
		this.grid.on("rowdblclick", this.show, this);
		var item = new Array();
		item.push(this.grid);
		this.items = item;
		PagePanel.superclass.initComponent.call(this);
	}
});
