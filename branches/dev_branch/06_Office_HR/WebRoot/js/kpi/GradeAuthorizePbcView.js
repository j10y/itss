GradeAuthorizePbcView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		GradeAuthorizePbcView.superclass.constructor.call(this, {
			id : "GradeAuthorizePbcView",
			title : "授权考核模板打分",
			region : "center",
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
			url : __ctxPath + "/kpi/currentListHrPaAuthorizepbc.do",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				}, {
					name : "userPbc.belongUser.fullname",
					mapping : "userPbc.belongUser.fullname"
				}, {
					name : "userPbc.pbcName",
					mapping : "userPbc.pbcName"
				}
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
			iconCls : "btn-edit",
			qtip : "授权",
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
					header : "姓名",
					dataIndex : "userPbc.belongUser.fullname"
				}, {
					header : "考核模板名称",
					dataIndex : "userPbc.pbcName"
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
			id : "GradeAuthorizePbcGrid",
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
				new GradeAuthorizePbcForm({
					pbcId : e.data.id
				}).show();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
	}
});