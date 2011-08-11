BudgetView = Ext.extend(Ext.Panel, {
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		BudgetView.superclass.constructor.call(this, {
			id : "BudgetView",
			title : "预算管理",
			iconCls : "menu-arch-rec-type",
			region : "center",
			layout : "border",
			items : [ this.searchPanel, this.gridPanel ]
		});
	},
	initUIComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			height : 40,
			region : "north",
			frame : false,
			border : false,
			layout : "hbox",
			layoutConfig : {
				padding : "5",
				align : "middle"
			},
			defaults : {
				xtype : "label",
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [ {
				text : "预算名称"
			}, {
				name : "Q_name_S_LK",
				xtype : "textfield",
				width : 100
			}, {
				text : "部门名称"
			}, {
				name : "Q_belongDept.depName_S_LK",
				xtype : "textfield",
				width : 100
			}, {
				xtype : "button",
				text : "查询",
				iconCls : "search",
				handler : this.search.createCallback(this)
			} ]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/budget/listBudget.do",
			root : "result",
			totalProperty : "totalCounts",
			remoteSort : true,
			fields : [ {
				name : "budgetId",
				type : "int"
			}, "name", {
				name : "depName",
				mapping : "belongDept.depName"
			}, "beginDate" 
			, "endDate" 
			, "createDate"
			, "createPerson", {
				name : "createPerson",
				mapping : "createPerson.fullname"
			}]
		});
		this.store.setDefaultSort("budgetId", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		if (isGranted("_BudgetDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		if (isGranted("_BudgetEdit")) {
			b.push({
				iconCls : "btn-edit",
				qtip : "编辑",
				style : "margin:0 3px 0 3px"
			});
		}
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : b
		});
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [ c, new Ext.grid.RowNumberer(), {
				header : "budgetId",
				dataIndex : "budgetId",
				hidden : true
			}, {
				header : "名称",
				dataIndex : "name"
			}, {
				header : "所属部门",
				dataIndex : "depName"
			}, {
				header : "开始时间",
				dataIndex : "beginDate"
			}, {
				header : "结束时间",
				dataIndex : "endDate"
			}, {
				header : "创建人",
				dataIndex : "createPerson"
			}, {
				header : "创建时间",
				dataIndex : "createDate"
			}, this.rowActions ], 
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		if (isGranted("_BudgetAdd")) {
			this.topbar.add({
				iconCls : "btn-add",
				text : "添加预算",
				xtype : "button",
				handler : this.createRecord
			});
		}
		if (isGranted("_BudgetDel")) {
			this.topbar.add({
				iconCls : "btn-del",
				text : "删除预算",
				xtype : "button",
				handler : this.delRecords,
				scope : this
			});
		}
		this.gridPanel = new Ext.grid.GridPanel({
			id : "BudgetGrid",
			region : "center",
			stripeRows : true,
			tbar : this.topbar,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			autoHeight : true,
			cm : a,
			sm : c,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				autoFill : true,
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				new BudgetForm({
					budgetId : e.data.budgetId
				}).show();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if (a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询",
				url : __ctxPath + "/budget/listBudget.do",
				success : function(c, d) {
					var b = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(b);
				}
			});
		}
	},
	createRecord : function() {
		var a = Ext.getCmp("centerTabPanel");
		var b = Ext.getCmp("BudgetFormWin");
		if (b != null) {
			a.remove("BudgetFormWin");
		}
		b = new BudgetForm();
		a.add(b);
		a.activate(b);
	},
	delByIds : function(a) {
		Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(b) {
			if (b == "yes") {
				Ext.Ajax.request({
					url : __ctxPath + "/budget/multiDelBudget.do",
					params : {
						ids : a
					},
					method : "POST",
					success : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "成功删除该！");
						Ext.getCmp("BudgetGrid").getStore().reload();
					},
					failure : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
					}
				});
			}
		});
	},
	delRecords : function() {
		var c = Ext.getCmp("BudgetGrid");
		var a = c.getSelectionModel().getSelections();
		if (a.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var d = Array();
		for ( var b = 0; b < a.length; b++) {
			d.push(a[b].data.budgetId);
		}
		this.delByIds(d);
	},
	editRecord : function(l) {		
		var a = Ext.getCmp("centerTabPanel");
		var b = Ext.getCmp("BudgetFormWin");
		if (b != null) {
			a.remove("BudgetFormWin");
		}
		b = new BudgetForm({
			budgetId : l.data.budgetId
		});
		a.add(b);
		a.activate(b);
	},
	onRowAction : function(c, a, d, e, b) {
		switch (d) {
		case "btn-del":
			this.delByIds(a.data.budgetId);
			break;
		case "btn-edit":
			this.editRecord(a);
			break;
		default:
			break;
		}
	}
});