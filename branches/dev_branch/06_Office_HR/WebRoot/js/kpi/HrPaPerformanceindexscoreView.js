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
			title : "考核指标评分标准维护",
			id : "HrPaPerformanceindexscoreView",
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
					text : "保存草稿",
					scope : "true",
					handler : this.saveAsDraft.createCallback(this.getForm, this)
				}, {
					text : "确认发布",
					scope : "true",
					handler : this.saveToPublish.createCallback(this.getForm, this)
				}
			],
			listeners : {
				close : function() {
					if(Ext.getCmp("HrPaPerformanceindexFormWin") != null) {
						Ext.getCmp("HrPaPerformanceindexFormWin").close();
					}
				}
			}
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
				}, {
					name : "pi.id",
					mapping : "pi.id"
				}, {
					name : "pisType.name",
					mapping : "pisType.name"
				},
				"pisScore",
				"pisDesc"
			]
		});
		this.store.setDefaultSort("pisScore", "asc");
		this.store.load({
			params : {
				start : 0,
				limit : 25,
				"Q_pi.id_L_EQ" : this.piId,
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
					dataIndex : "pi.id",
					hidden : true
				}, {
					header : "pisType",
					dataIndex : "pisType.name",
					hidden : true
				}, {
					header : "得分",
					width : 50,
					dataIndex : "pisScore"
				}, {
					header : "评分说明",
					dataIndex : "pisDesc"
				}, {
					header : "计算公式",
					dataIndex : "formula",
					hidden : true
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
		var pisType = this.paMode;
		this.gridPanel.addListener("rowdblclick", function(f, d, g) {
			f.getSelectionModel().each(function(e) {
				if(pisType == QUALITATIVE_ASSESSMENT) {
					new HrPaPerformanceindexscoreForm1({
						pisId : e.data.id,
						piId : e.data.piId,
						pisType : pisType,
						rowNumber : Ext.getCmp("HrPaPerformanceindexscoreGrid").getStore().indexOf(e)
					}).show();
				} else {
					new HrPaPerformanceindexscoreForm2({
						pisId : e.data.id,
						piId : e.data.piId,
						pisType : pisType,
						rowNumber : Ext.getCmp("HrPaPerformanceindexscoreGrid").getStore().indexOf(e)
					}).show();
				}
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	},
	cancel : function(b) {
		Ext.getCmp("HrPaPerformanceindexFormWin").close();
		b.close();
	},
	saveAsDraft : function(a, b) {
		var pisList = "";
		var pisStore = b.gridPanel.getStore();
		if(b.paMode == QUALITATIVE_ASSESSMENT) {//定性考核指标
			for(var i = 0; i < pisStore.getCount(); i++) {
				var pisItem = pisStore.getAt(i).data;
				pisList += pisItem.id + "," + pisItem.pisScore + "," + pisItem.pisDesc + " ";
			}
		} else {//定量考核指标
			for(var i = 0; i < pisStore.getCount(); i++) {
				var pisItem = pisStore.getAt(i).data;
				pisList += pisItem.id + "," + pisItem.pisScore + "," + pisItem.pisDesc + "," + pisItem.formula + " ";
			}
		}
		Ext.getCmp("indexScores").setValue(pisList);
		var submitForm = Ext.getCmp("HrPaPerformanceindexFormWin").formPanel.getForm();
		submitForm.submit({
			url : __ctxPath + "/kpi/saveAsDraftHrPaPerformanceindex.do",
			method : "post",
			waitMsg : "正在提交数据…",
			success : function(c, d) {
				Ext.getCmp("HrPaPerformanceindexFormWin").close();
				Ext.getCmp("HrPaPerformanceindexscoreView").close();
				if(b.from == "draft") {
					Ext.getCmp("DraftHrPaPerformanceindexView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				} else if(a.from == "publish") {
					Ext.getCmp("PublishHrPaPerformanceindexView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			},
			failure : function(c, d) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "信息录入有误，请核实！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
		});
	},
	saveToPublish : function(a, b) {
		var pisList = "";
		var pisStore = b.gridPanel.getStore();
		if(b.paMode == QUALITATIVE_ASSESSMENT) {//定性考核指标
			for(var i = 0; i < pisStore.getCount(); i++) {
				var pisItem = pisStore.getAt(i).data;
				pisList += pisItem.id + "," + pisItem.pisScore + "," + pisItem.pisDesc + " ";
			}
		} else {//定量考核指标
			for(var i = 0; i < pisStore.getCount(); i++) {
				var pisItem = pisStore.getAt(i).data;
				pisList += pisItem.id + "," + pisItem.pisScore + "," + pisItem.pisDesc + "," + pisItem.formula + " ";
			}
		}
		Ext.getCmp("indexScores").setValue(pisList);
		var submitForm = Ext.getCmp("HrPaPerformanceindexFormWin").formPanel.getForm();
		submitForm.submit({
			url : __ctxPath + "/kpi/saveToPublishHrPaPerformanceindex.do",
			method : "post",
			waitMsg : "正在提交数据…",
			success : function(c, d) {
				Ext.getCmp("HrPaPerformanceindexFormWin").close();
				Ext.getCmp("HrPaPerformanceindexscoreView").close();
				if(b.from == "draft") {
					Ext.getCmp("DraftHrPaPerformanceindexView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				} else if(a.from == "publish") {
					Ext.getCmp("PublishHrPaPerformanceindexView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			},
			failure : function(c, d) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "信息录入有误，请核实！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
		});
	},
	addHrPaPerformanceindexscore : function() {
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
		if(a.paMode == QUALITATIVE_ASSESSMENT) {
			new HrPaPerformanceindexscoreForm1({
				pisId : a.data.id,
				piId : this.piId,
				pisType : this.paMode,
				rowNumber : Ext.getCmp("HrPaPerformanceindexscoreGrid").getStore().indexOf(a)
			}).show();
		} else {
			new HrPaPerformanceindexscoreForm2({
				pisId : a.data.id,
				piId : this.piId,
				pisType : this.paMode,
				rowNumber : Ext.getCmp("HrPaPerformanceindexscoreGrid").getStore().indexOf(e)
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
		//只是前台删除，数据库并不真正删除
		var allStore = e.getStore();
		Ext.each(c, function(item) {
			allStore.remove(item);
		});
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