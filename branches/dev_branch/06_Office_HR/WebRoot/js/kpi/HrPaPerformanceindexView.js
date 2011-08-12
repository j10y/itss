HrPaPerformanceindexView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaPerformanceindexView.superclass.constructor.call(this, {
			id : "HrPaPerformanceindexView",
			title : "考核项目管理",
			items : [
				this.searchPanel,
				this.gridPanel
			]
		});
	},
	typeId : null,
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	initComponents : function() {
		this.searchPanel = new Ext.FormPanel({
			region : "north",
			height : 40,
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
			items : [
				{
					text : "查询条件：考核项目名称"
				}, {
					fieldLabel : "考核项目名称：",
					name : "Q_paName",
					xtype : "textfield"
				}, {
					text : "考核项目类型："
				}, {
					name : "Q_paType",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaPerformanceindex.do",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"paName",
				"type",
				"frequency",
				"mode"
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var b = new Array();
		if(isGranted("_PaDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		if(isGranted("_PaEdit")) {
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
			columns : [
				c,
				new Ext.grid.RowNumberer(),
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "考核项目名称",
					dataIndex : "paName"
				}, {
					header : "考核项目类型",
					dataIndex : "type",
					renderer : function(d) {
						return d.name;
					}
				}, {
					header : "考核频度",
					dataIndex : "frequency",
					renderer : function(d) {
						return d.name;
					}
				}, {
					header : "考核方式",
					dataIndex : "mode",
					renderer : function(d) {
						return d.name;
					}
					
				},
				this.rowActions
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		this.topbar.add(new Ext.Button({
			iconCls : "btn-add",
			text : "增加考核项目",
			handler : this.addHrPaPerformanceindex
		}));
		this.topbar.add(new Ext.Button({
			iconCls : "btn-del",
			text : "删除考核项目",
			handler : this.delHrPaPerformanceindex
		}));
		this.gridPanel = new Ext.grid.GridPanel({
			id : "HrPaPerformanceindexGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			tbar : this.topbar,
			closable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			sm : c,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				if(isGranted("_PaEdit")) {
					new HrPaPerformanceindexForm({
						piId : e.data.id
					}).show();
				}
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		
	},
	addHrPaPerformanceindex : function() {
		new HrPaPerformanceindexForm().show();
	},
	delHrPaPerformanceindex : function() {
		var e = Ext.getCmp("HrPaPerformanceindexGrid");
		var c = e.getSelectionModel().getSelections();
		if(c.length == 0) {
			Ext.ux.Toast.msg("提示信息","请选择要删除的记录！");
			return ;
		}
		var f = Array();
		for(var d = 0; d < c.length; d++) {
			f.push(c[d].data.id);
		}
		HrPaPerformanceindexView.remove(f);
	},
	editHrPaPerformanceindex : function(a) {
		new HrPaPerformanceindexForm({
			piId : a.data.id
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-del":
				this.delHrPaPerformanceindex();
				break ;
			case "btn-edit":
				this.editHrPaPerformanceindex(a);
				break ;
			default:
				break ;
		}
	}
});
HrPaPerformanceindexView.remove = function(b) {
	var a = Ext.getCmp("HrPaPerformanceindexGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(c) {
		if(c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/kpi/multiDelHrPaPerformanceindex.do",
				params : {
					ids : b
				},
				method : "post",
				success : function() {
					Ext.ux.Toast.msg("提示信息", "成功删除所选记录！");
					a.getStore().reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			});
		}
	});
}