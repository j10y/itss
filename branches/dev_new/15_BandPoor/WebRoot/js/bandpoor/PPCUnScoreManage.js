PPCUnScoreManage = Ext.extend(Ext.Panel, {
	constructor : function (a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		PPCUnScoreManage.superclass.constructor.call(this, {
			id : "PPCUnScoreManage",
			title : "不可评分品牌池管理",
			region : "center",
			iconCls : "menu-personal-salary",
			layout : "border",
			items : [this.searchPanel, this.gridPanel]
		});
	},
	typeId : null,
	searchPanel : null,
	gridPanel : null,
	store : null,
	topbar : null,
	initComponents : function () {
		this.searchPanel = new Ext.FormPanel({
				region : "north",
				frame : false,
				border : false,
				layout : "hbox",
				height : 40,
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
				items : [{
						text : "请输入查询条件:"
					}, {
						text : "品牌"
					}, {
						id : "UnScorePBCManageSearchFormBandId",
						width : 120,
						name : "bandName",
						maxHeight : 200,
						xtype : "combo",
						mode : "local",
						editable : true,
						triggerAction : "all",
						valueField : "DXCBandId",
						displayField : "DXCBandName",
						store : new Ext.data.SimpleStore(
						{
							url : __ctxPath
									+ "/bandpoor/getBandsScoreManage.do",
							fields : [
									"DXCBandId",
									"DXCBandName"]
						}),
						listeners : {
							focus : function (e) {
								var d = Ext.getCmp("UnScorePBCManageSearchFormBandId").getStore();
								if (d.getCount() <= 0) {
									Ext.Ajax.request({
										url : __ctxPath + "/bandpoor/getBandsScoreManage.do",
										method : "post",
										success : function (g) {
											var f = Ext.util.JSON.decode(g.responseText);
											d.loadData(f);
										}
									});
								}
							}
						}
					}, {
						text : "品牌池状态"
					}, {
						width : 120,
						hiddenName : "bandPoorStatus",
						xtype : "combo",
						mode : "local",
						editable : "false",
						triggerAction : "all",
						store : [
							[
								"1",
								"可应用池"
							], [
								"2",
								"备选池"
							]
						]
					}, {
						xtype : "button",
						text : "查询",
						iconCls : "search",
						handler : this.search.createCallback(this)
					}
				]
			});
		this.store = new Ext.data.JsonStore({
				url : __ctxPath + "/bandpoor/unScorelistPpcScore.do",
				root : "result",
				baseParams : {
				},
				totalProperty : "totalCounts",
				remoteSort : false,
				fields : [
					{
						name : "id",
						type : "int"
					}, 
					"bandName",
					"bandPoorStatus",
					"targetValue",
					"requireValue",
					"bandRankValue",
					"selBandRankValue",
					"status",
					"createDate",
					"createUser",
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
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
				columns : [c, new Ext.grid.RowNumberer(), d, {
						header : "id",
						dataIndex : "id",
						hidden : true
					}, {
						header : "品牌名称",
						width : 60,
						dataIndex : "bandName"
					}, {
						header : "品类评效",
						width : 60,
						dataIndex : "targetValue"
					}, {
						header : "本品牌评效",
						width : 60,
						dataIndex : "requireValue"
					}, {
						header : "品类排名",
						width : 60,
						dataIndex : "bandRankValue"
					}, {
						header : "本品牌排名",
						width : 60,
						dataIndex : "selBandRankValue"
					}, {
						header : "状态",
						width : 60,
						dataIndex : "status",
						renderer : function (e) {
							if(e == "1") {
								return "新建";
							}
							if(e == "2") {
								return "达成通过";
							}
							if(e == "3") {
								return "未达成";
							}
						}
					}, {
						header : "品牌池状态",
						width : 60,
						dataIndex : "bandPoorStatus",
						renderer : function(d) {
							if(d == "1") {
								return "可应用池";
							}
							if(d == "2") {
								return "备选池";
							}
						}
					}, {
						header : "品牌类型",
						width : 60,
						dataIndex : "infoType",
						renderer : function (e) {
							return "不可评分";
						}
					}, {
						header : "创建日期",
						width : 60,
						dataIndex : "createDate"
					}, {
						header : "创建人",
						width : 60,
						dataIndex : "createUser"
					}
				],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});		
		this.gridPanel = new Ext.grid.GridPanel({
				id : "DXCUnScoreManageGrid",
				region : "center",
				stripeRows : true,
				plugins : d,
				store : this.store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				autoHeight : true,
				cm : a,
				sm : c,
				viewConfig : {
					forceFit : true,
					autoFill : true,
					forceFit : true
				},
				bbar : new Ext.PagingToolbar({
					pageSize : 25,
					store : this.store,
					displayInfo : true,
					displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
					emptyMsg : "当前没有记录"
				})
			});
	},
	search : function (a) {
		if (a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询",
				url : __ctxPath + "/bandpoor/unScorelistPpcScore.do",
				success : function (c, d) {
					var b = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(b);
				}
			});
		}
	}
});
