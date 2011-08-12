//定性考核和定量考核关键字，对应数据库里边的ID
var QUALITATIVE_ASSESSMENT = 12;
var QUANTITATIVE_ASSESSMENT = 13;
HrPaPerformanceindexscoreView = Ext.extend(Ext.Window, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaPerformanceindexscoreView.superclass.constructor.call(this, {
			title : "考核项目评分标准维护",
			id : "hrPaPerformanceindexscoreView",
			width : 440,
			height : 420,
			layout : "fit",
			items : this.gridPanel,
			modal : true,
			buttonAlign : "center",
			buttons : [
				{
					text : "取消",
					scope : "true",
					handler : this.cancel.createCallback(this)
				}, {
					text : "确认发布",
					scope : "true",
					handler : this.saveToPublish.createCallback(this.getForm, this)
				}
			]
		});
	},
	topbar : null,
	initComponents : function() {
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		this.topbar.add(new Ext.Button({
			text : "增加评分标准",
			handler : this.addHrPaPerformanceindexscore,
			scope : this
		}));
		this.topbar.add(new Ext.Button({
			text : "删除评分标准",
			handler : this.delHrPaPerformanceindexscore,
			scope : this
		}));
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listHrPaPerformanceindexscore.do",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"piId",
				"pisType",
				"pisScore",
				"pisDesc"
			]
		});
		this.store.setDefaultSort("pisScore", "asc");
		this.store.load({
			params : {
				start : 0,
				limit : 25,
				Q_piId_L_EQ : this.piId,
				paMode : this.paMode
			}
		});
		var b = new Array();
		b.push({
			iconCls : "btn-del",
			qtip : "删除",
			style : "margin:0 3px 0 3px"
		});
		b.push({
			iconCls : "btn-edit",
			qtip : "编辑",
			style : "margin:0 3px 0 3px"
		});
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 60,
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
					header : "piId",
					dataIndex : "id",
					hidden : true
				}, {
					header : "pisType",
					dataIndex : "pisType",
					hidden : true
				}, {
					header : "得分",
					width : 50,
					dataIndex : "pisScore"
				}, {
					header : "评分说明",
					dataIndex : "pisDesc"
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
			id : "HrPaPerformanceindexscoreGrid",
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
			}
		});
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				if(e.data.pisType == QUALITATIVE_ASSESSMENT) {
					new HrPaPerformanceindexscoreForm1({
						pisId : e.data.id,
						piId : e.data.piId,
						pisType : e.data.pisType
					}).show();
				} else {
					new HrPaPerformanceindexscoreForm2({
						pisId : e.data.id,
						piId : e.data.piId,
						pisType : e.data.pisType
					}).show();
				}
				Ext.getCmp("hrPaPerformanceindexscoreView").close();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	cancel : function(a) {
		Ext.getCmp("HrPaPerformanceindexView").gridPanel.store.reload({
			params : {
				start : 0,
				limit : 25
			}
		});
		a.close();
	},
	saveToPublish : function(a, b) {
		Ext.Ajax.request({
			url : __ctxPath + "/kpi/publishHrPaPerformanceindex.do",
			params : {
				piId : b.piId
			},
			method : "post",
			success : function(d) {
				var e = Ext.util.JSON.decode(d.responseText);
				if(e.flag) {
					Ext.ux.Toast.msg("提示信息","发布成功！");
					Ext.getCmp("HrPaPerformanceindexView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
					b.close();
				} else {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "发布失败，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			},
			failure : function() {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "发布失败，请联系管理员！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	},
	addHrPaPerformanceindexscore : function() {
		this.close();
		if(this.paMode == QUALITATIVE_ASSESSMENT) {
			new HrPaPerformanceindexscoreForm1({
				piId : this.piId,
				pisType : this.paMode
			}).show();
		} else {
			new HrPaPerformanceindexscoreForm2({
				piId : this.piId,
				pisType : this.paMode
			}).show();
		}
	},
	editHrPaPerformanceindexscore : function(a) {
		this.close();
		if(a.data.pisType == QUALITATIVE_ASSESSMENT) {
			new HrPaPerformanceindexscoreForm1({
				pisId : a.data.id,
				piId : this.piId,
				pisType : this.paMode
			}).show();
		} else {
			new HrPaPerformanceindexscoreForm2({
				pisId : a.data.id,
				piId : this.piId,
				pisType : this.paMode
			}).show();
		}
	},
	delHrPaPerformanceindexscore : function() {
		var e = Ext.getCmp("HrPaPerformanceindexscoreGrid");
		var c = e.getSelectionModel().getSelections();
		if(c.length == 0) {
			Ext.ux.Toast.msg("提示信息","请选择要删除的记录！");
			return ;
		}
		var f = Array();
		for(var d = 0; d < c.length; d++) {
			f.push(c[d].data.id);
		}
		HrPaPerformanceindexscoreView.remove(f, this);
	},
	onRowAction : function(c, a, d, e, b) {
		switch(d) {
			case "btn-del":
				this.delHrPaPerformanceindexscore();
				break ;
			case "btn-edit":
				this.editHrPaPerformanceindexscore(a);
				break ;
			default:
				break ;
		}
	}
});
HrPaPerformanceindexscoreView.remove = function(b, t) {
	var a = Ext.getCmp("HrPaPerformanceindexscoreGrid");
	Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(c) {
		if(c == "yes") {
			Ext.Ajax.request({
				url : __ctxPath + "/kpi/multiDelHrPaPerformanceindexscore.do",
				params : {
					ids : b
				},
				method : "post",
				success : function() {
					Ext.ux.Toast.msg("提示信息", "成功删除所选记录！");
					a.getStore().reload({
						params : {
							start : 0,
							limit : 25,
							Q_piId_L_EQ : t.piId,
							paMode : t.paMode
						}
					});
				}
			});
		}
	});
}