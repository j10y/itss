SpResultTotalScoreView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SpResultTotalScoreView.superclass.constructor.call(this, {
			id : "SpResultTotalScoreView",
			title : "历史记录列表",
			region : "center",
			autoScroll : true,
			layout : "border",
			items : [
				this.gridPanel
			]
		});
	},
	gridPanel : null,
	store : null,
	initComponents : function() {
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/shop/listResultSpPaKpiPBC2User.do",
			totalProperty : "totalCounts",
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
				limit : 25
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
			id : "SpResultTotalScoreGrid",
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
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
	}
});