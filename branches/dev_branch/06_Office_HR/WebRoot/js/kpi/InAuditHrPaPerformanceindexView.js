InAuditHrPaPerformanceindexView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		InAuditHrPaPerformanceindexView.superclass.constructor.call(this, {
			id : "InAuditHrPaPerformanceindexView",
			title : "考核指标审核",
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
					text : "查询条件：考核指标名称"
				}, {
					fieldLabel : "考核指标名称：",
					name : "Q_paName_S_LK",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaPerformanceindex.do?Q_publishStatus_N_EQ=1",
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
				{
					name : "type.name",
					mapping : "type.name"
				}, {
					name : "frequency.name",
					mapping : "frequency.name"
				}, {
					name : "mode.name",
					mapping : "mode.name"
				},
				"publishStatus"
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
		b.push({
			iconCls : "btn-preview",
			qtip : "查看",
			style : "margin:0 3px 0 3px"
		});
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
					header : "考核指标名称",
					dataIndex : "paName"
				}, {
					header : "考核指标类型",
					dataIndex : "type.name"
				}, {
					header : "考核频度",
					dataIndex : "frequency.name"
				}, {
					header : "考核方式",
					dataIndex : "mode.name"
				}, {
					header : "状态",
					dataIndex : "publishStatus",
					renderer : function(d) {
						if(d == 0) {        //草稿
							return "<font color='red'>草稿</font>";
						}
						if(d == 1) {        //审核中
							return "<font color='red'>审核中</font>";
						}
						if(d == 2) {        //退回
							return "<font color='red'>退回</font>";
						}
						if(d == 3) {        //审核完毕，发布
							return "<font color='green'>已发布</font>";
						}
						if(d == 4) {        //删除标记
							return "<font color='red'>已删除</font>";
						}
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
		this.gridPanel = new Ext.grid.GridPanel({
			id : "InAuditHrPaPerformanceindexGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
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
					new HrPaPerformanceindexFormView({
						piId : e.data.id
					}).show();
				}
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : __ctxPath + "/kpi/listHrPaPerformanceindex.do?Q_publishStatus_N_EQ=1",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	editHrPaPerformanceindex : function(a) {
		new HrPaPerformanceindexFormView({
			piId : a.data.id
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-preview":
				this.editHrPaPerformanceindex(a);
				break ;
			default:
				break ;
		}
	}
});