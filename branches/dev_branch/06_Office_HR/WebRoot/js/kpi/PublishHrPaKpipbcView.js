PublishHrPaKpipbcView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		PublishHrPaKpipbcView.superclass.constructor.call(this, {
			id : "PublishHrPaKpipbcView",
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
			url : __ctxPath + "/kpi/listHrPaKpipbc.do?Q_publishStatus_N_EQ=3",
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
		if(isGranted("_PbcDel")) {
			b.push({
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			});
		}
		if(isGranted("_PbcEdit")) {
			b.push({
				iconCls : "btn-edit",
				qtip : "编辑",
				style : "margin:0 3px 0 3px"
			});
		}
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
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		if(isGranted("_PbcAdd")) {
			this.topbar.add({
				iconCls : "btn-add",
				text : "添加考核模板",
				xtype : "button",
				handler : this.addHrPaKpipbc
			});
		}
		if(isGranted("_PbcDel")) {
			this.topbar.add({
				iconCls : "btn-del",
				text : "删除考核模板",
				xtype : "button",
				handler : this.delHrPaKpipbc
			});
		}
		this.gridPanel = new Ext.grid.GridPanel({
			id : "PublishHrPaKpipbcGrid",
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
					new HrPaKpipbcForm({
						pbcId : e.data.id,
						from : "publish"
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
				url : __ctxPath + "/kpi/listHrPaKpipbc.do?Q_publishStatus_N_EQ=3",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	addHrPaKpipbc : function() {
		new HrPaKpipbcForm({
			from : "publish"
		}).show();
	},
	delHrPaKpipbc : function() {
		var e = Ext.getCmp("PublishHrPaKpipbcGrid");
		var c = e.getSelectionModel().getSelections();
		if(c.length == 0) {
			Ext.ux.Toast.msg("提示信息", "请选择要删除的记录！");
			return ;
		}
		var f = Array();
		for(var d = 0; d < c.length; d++) {
			f.push(c[d].data.id);
		}
		PublishHrPaKpipbcView.remove(f);
	},
	editHrPaKpipbc : function(a) {
		new HrPaKpipbcForm({
			pbcId : a.data.id,
			from : "publish"
		}).show();
	},
	previewHrPaKpipbc : function(a) {
		new HrPaKpipbcFormView({
			pbcId : a.data.id
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-del":
				this.delHrPaKpipbc();
				break ;
			case "btn-edit":
				this.editHrPaKpipbc(a);
				break ;
			case "btn-preview":
				this.previewHrPaKpipbc(a);
			default:
				break ;
		}
	}
});
PublishHrPaKpipbcView.remove = function(b) {
	var a = Ext.getCmp("PublishHrPaKpipbcGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(c) {
		if(c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/kpi/multiDelHrPaKpipbc.do",
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