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
			layout : "border",
			items : [
				this.gridPanel
			],
			buttonAlign : "center"
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
				"totalScore"
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
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
	}
});