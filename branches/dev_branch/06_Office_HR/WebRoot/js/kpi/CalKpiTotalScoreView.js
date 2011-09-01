CalKpiTotalScoreView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		CalKpiTotalScoreView.superclass.constructor.call(this, {
			id : "CalKpiTotalScoreView",
			title : "计算总分",
			region : "center",
			layout : "border",
			items : [
				this.calPanel,
				this.gridPanel
			],
			listeners : {
				afterrender : function() {
					//判断是否所有授权打分都已完成
					if(this.store.getCount() > 0) {
						Ext.getCmp("calButton").hide();
						Ext.getCmp("reminder").show();
					} else {
						Ext.getCmp("reminder").hide();
						Ext.getCmp("calButton").show();
					}
				}
			}
		});
	},
	calPanel : null,
	gridPanel : null,
	resultPanel : null,
	store : null,
	resultStore : null,
	initComponents : function() {
		this.calPanel = new Ext.FormPanel({
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
					xtype : "button",
					id : "calButton",
					text : "计算总分",
					handler : this.calculateTotal.createCallback(this)
				}, {
					id : "reminder",
					text : "有下表列出的授权打分未完成，请完成后再计算总分！"
				}, {
					xtype : "button",
					id : "resultButton",
					text : "查看结果",
					handler : this.previewResult.createCallback(this)
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listUnFinishedHrPaKpiPBC2User.do",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "itemId",
					type : "int"
				},
				"pbcName",
				"paName",
				"desc"
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 100
			}
		});
		var a = new Ext.grid.ColumnModel({
			columns : [
				new Ext.grid.RowNumberer(),
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "考核模板名称",
					dataIndex : "pbcName"
				}, {
					header : "考核指标名称",
					dataIndex : "paName"
				}, {
					header : "说明",
					dataIndex : "desc"
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "CalKpiTotalScoreGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRow : true,
			closeable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
	},
	calculateTotal : function(a) {
		Ext.Ajax.request({
			url : __ctxPath + "/kpi/multiCalHrPaKpiPBC2User.do",
			success : function(d) {
				Ext.ux.Toast.msg("提示信息","计算完成！");
				new ResultTotalScoreView().show();
			}
		});
	},
	previewResult : function(a) {
		new ResultTotalScoreView().show();
	}
});