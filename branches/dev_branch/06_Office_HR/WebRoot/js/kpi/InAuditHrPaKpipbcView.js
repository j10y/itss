InAuditHrPaKpipbcView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		InAuditHrPaKpipbcView.superclass.constructor.call(this, {
			id : "InAuditHrPaKpipbcView",
			title : "考核模板管理",
			region : "center",
			layout : "border",
			items : [
				this.searchPanel,
				this.gridPanel
			]
		});
	},
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
					text : "查询条件：考核模板名称"
				}, {
					fieldLabel : "考核模板名称",
					name : "Q_pbcName_S_LK",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaKpipbc.do?Q_publishStatus_N_EQ=1",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"pbcName",
				{
					name : "frequency.name",
					mapping : "frequency.name"
				}, {
					name : "belongDept.depName",
					mapping : "belongDept.depName"
				}, {
					name : "belongPost.jobName",
					mapping : "belongPost.jobName"
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
		if(isGranted("_PbcEdit")) {
			b.push({
				iconCls : "btn-edit",
				qtip : "查看",
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
					header : "考核模板名称",
					dataIndex : "pbcName"
				}, {
					header : "考核频度",
					dataIndex : "frequency.name"
				}, {
					header : "所属部门",
					dataIndex : "belongDept.depName"
				}, {
					header : "所属岗位",
					dataIndex : "belongPost.jobName"
				}, {
					header : "状态",
					dataIndex : "publishStatus",
					renderer : function(d) {
						if(d == 0) {
							return "<font color='red'>草稿</font>";
						}
						if(d == 1) {
							return "<font color='red'>审核中</font>";
						}
						if(d == 2) {
							return "<font color='red'>已退回</font>";
						}
						if(d == 3) {
							return "<font color='green'>已发布</font>";
						}
						if(d == 4) {
							return "<font color='red'>已删除</font>";
						}
					}
				},
				this.rowActions
			],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "DraftHrPaKpipbcGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			tbar : this.topbar,
			closeable : true,
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
		this.gridPanel.addListener("rowdblclick", function (f, d, g) {
			f.getSelectionModel().each(function(e) {
				if(isGranted("_PbcEdit")) {
					new HrPaKpipbcFormView({
						pbcId : e.data.id
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
				url : __ctxPath + "/kpi/listHrPaKpipbc.do?Q_publishStatus_N_EQ=1",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	editHrPaKpipbc : function(a) {
		new HrPaKpipbcFormView({
			pbcId : a.data.id
		});
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-edit":
				this.editHrPaKpipbc(a);
				break ;
			default:
				break ;
		}
	}
});