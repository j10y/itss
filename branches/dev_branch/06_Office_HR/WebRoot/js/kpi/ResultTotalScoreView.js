ResultTotalScoreView = Ext.extend(Ext.Window, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		ResultTotalScoreView.superclass.constructor.call(this, {
			id : "ResultTotalScoreView",
			title : "个人考核模板得分",
			width : 800,
			height : 500,
			modal : true,
			autoScroll : true,
			layout : "border",
			items : [
				this.gridPanel
			],
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	gridPanel : null,
	store : null,
	initComponents : function() {
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listResultHrPaKpiPBC2User.do",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"fullname",
				"pbcName",
				"totalScore",
				"content"
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 100
			},
			callback : function(r, o, s) {
				if(this.getCount() > 0) {
					if(parseFloat(this.getAt(0).data.totalScore) > 0) {
						Ext.getCmp("moveToHist").show();
					}
				}
			}
		});
		var d = new Ext.ux.grid.RowExpander({
			tpl : new Ext.Template('<div style="padding:5px 5px 5px 62px;">{content}</div>')
		});
		var a = new Ext.grid.ColumnModel({
			columns : [
				new Ext.grid.RowNumberer(),
				d,
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "姓名",
					dataIndex : "fullname"
				}, {
					header : "个人考核模板名称",
					dataIndex : "pbcName"
				}, {
					header : "总分",
					dataIndex : "totalScore"
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "ResultTotalScoreGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			closeable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			plugins : d,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
		this.buttons = [
			{
				text : "批准",
				id : "moveToHist",
				hidden : true,
				handler : this.saveToHist.createCallback(this)
			}
		]
	},
	saveToHist : function(a) {
		Ext.Ajax.request({
			url : __ctxPath + "/kpi/saveToHistHrPaKpiPBC2User.do",
			success : function(d) {
				Ext.ux.Toast.msg("提示信息", "成功移动到历史记录里！");
				a.close();
				return ;
			}
		});
	}
});