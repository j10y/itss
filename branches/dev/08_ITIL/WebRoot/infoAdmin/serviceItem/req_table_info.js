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
			fieldLabel : '查询表名',
			emptyText : '请输入查找表名',
			id : 'tempTableName',
			name : 'tempTableName',
			width : 200
		});
		// 创建表格数据
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
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "没有符合条件的数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		var sm = new Ext.grid.CheckboxSelectionModel();

		// 创建Grid表格组件
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
			tbar : [new Ext.Toolbar.TextItem("查询表名"), '-', searchTableName, 
				{
				pressed : true,
				text : "检索",
				iconCls : 'search',
				scope : this,
				handler : this.search
				},{
				pressed : true,
				text : "生成新需求主表",
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
				header : "主表英文名",
				width : 220,
				sortable : true,
				dataIndex : 'tableName'
			}, {
				header : "主表中文名",
				width : 220,
				sortable : true,
				dataIndex : 'tableCnName'
			}, {
				header : "关联类名",
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
