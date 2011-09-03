DraftHrPaAssessmentcriteriaView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		DraftHrPaAssessmentcriteriaView.superclass.constructor.call(this, {
			id : "DraftHrPaAssessmentcriteriaView",
			title : "考核标准草稿",
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
					text : "查询条件：考核标准名称"
				}, {
					fieldLabel : "考核标准关名称",
					name : "Q_acName_S_LK",
					xtype : "textfield"
				}, {
					text : "考核标准关键字"
				}, {
					fieldLabel : "考核标准关键字",
					name : "Q_acKey_S_LK",
					xtype : "textfield"
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaAssessmentcriteria.do?Q_publishStatus_N_EQ=0",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"acName",
				"acKey",
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
		if(isGranted("_AcDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		if(isGranted("_AcEdit")) {
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
					header : "考核标准名称",
					dataIndex : "acName"
				}, {
					header : "考核标准关键字",
					dataIndex : "acKey"
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
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		this.topbar.add(new Ext.Button({
			iconCls : "btn-add",
			text : "增加考核标准",
			handler : this.addHrPaAssessmentcriteria
		}));
		this.topbar.add(new Ext.Button({
			iconCls : "btn-del",
			text : "删除考核标准",
			handler : this.delHrPaAssessmentcriteria
		}));
		this.gridPanel = new Ext.grid.GridPanel({
			id : "DraftHrPaAssessmentcriteriaGrid",
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
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				if(isGranted("_AcEdit")) {
					new HrPaAssessmentcriteriaForm({
						acId : e.data.id,
						from : "draft"
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
				url : __ctxPath + "/kpi/listHrPaAssessmentcriteria.do?Q_publishStatus_N_EQ=0",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	addHrPaAssessmentcriteria : function() {
		new HrPaAssessmentcriteriaForm({
			from : "draft"
		}).show();
	},
	delHrPaAssessmentcriteria : function() {
		var e = Ext.getCmp("DraftHrPaAssessmentcriteriaGrid");
		var c = e.getSelectionModel().getSelections();
		if(c.length == 0) {
			Ext.ux.Toast.msg("提示信息", "请选择要删除的记录！");
			return ;
		}
		var f = Array();
		for(var d = 0; d < c.length; d++) {
			f.push(c[d].data.id);
		}
		DraftHrPaAssessmentcriteriaView.remove(f);
	},
	editHrPaAssessmentcriteria : function(a) {
		new HrPaAssessmentcriteriaForm({
			acId : a.data.id,
			from : "draft"
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-del":
				this.delHrPaAssessmentcriteria();
				break ;
			case "btn-edit":
				this.editHrPaAssessmentcriteria(a);
				break ;
			default:
				break ;
		}
	}
});
DraftHrPaAssessmentcriteriaView.remove = function(b) {
	var a = Ext.getCmp("DraftHrPaAssessmentcriteriaGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(c) {
		if(c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/kpi/multiDelHrPaAssessmentcriteria.do",
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