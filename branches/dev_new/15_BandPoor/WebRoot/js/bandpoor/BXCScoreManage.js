BXCScoreManage = Ext.extend(Ext.Panel, {
		constructor : function (a) {
			if (a == null) {
				a = {};
			}
			Ext.apply(this, a);
			this.initComponents();
			BXCScoreManage.superclass.constructor.call(this, {
				id : "BXCScoreManage",
				title : "待选池评分管理",
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
							id : "BXCSearchFormBandName",
							width : 120,
							name : "Q_bandName_S_LK",
							maxHeight : 200,
							xtype : "combo",
							mode : "local",
							editable : true,
							triggerAction : "all",
							valueField : "BXCBandId",
							displayField : "BXCBandName",
							store : new Ext.data.SimpleStore(
							{
								url : __ctxPath
										+ "/bandpoor/getBandsScoreManage.do",
								fields : [
										"BXCBandId",
										"BXCBandName"]
							}),
							listeners : {
								focus : function (e) {
									var d = Ext.getCmp("BXCSearchFormBandName").getStore();
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
							text : "采集年份"
						}, {
							name : "Q_year_N_EQ",
							id : "beElectedBandPoor.year",
							xtype : "combo",
							triggerAction : "all",
							store : ["2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021"]
							
						}, {
							text : "采集频率"
						}, {
							name : "beElectedBandPoor.poorVersionName",
							id : "beElectedBandPoor.poorVersionName",
							xtype : "combo",
							triggerAction : "all",
							store : [["1","一次采集(6月-10)"],["2","二次采集(11月-次年5月)"]],
							listeners : {
									select:function(e,c,d){
										var poorVersion=Ext.getCmp("beElectedBandPoor.poorVersionName").getValue();
										Ext.getCmp("beElectedBandPoor.poorVersion").setValue(poorVersion);
									}
							}
						}, {
							xtype : "button",
							text : "查询",
							iconCls : "search",
							handler : this.search.createCallback(this)
						},{
							name : "Q_poorVersion_N_EQ",
							id : "beElectedBandPoor.poorVersion",
							xtype : "hidden"
						}
					]
				});
			this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/bandpoor/unScoreListPpcScore.do",
					root : "result",
					baseParams : {
					},
					totalProperty : "totalCounts",
					remoteSort : false,
					fields : [{
							name : "id",
							type : "int"
						}, "bandName","saleStoreNum", "bandScore", "status", "infoType", "creatDate", "createUser", "content","bandrealScore","year","poorVersion"]
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
							header : "销售场所数量",
							width : 60,
							dataIndex : "saleStoreNum"
						}, {
							header : "品牌考核分",
							width : 60,
							dataIndex : "bandScore"
						}, {
							header : "品牌实际得分",
							width : 60,
							dataIndex : "bandrealScore"
						}, {
							header : "年度",
							width : 60,
							dataIndex : "year"
						}, {
							header : "频率",
							width : 60,
							dataIndex : "poorVersion",
							renderer : function (e) {
								if(e==1){
									return  "一次采集(6月-10)";
								}else{
									return  "二次采集(11月-次年5月)";
								}
							}
						}, {
							header : "状态",
							width : 60,
							dataIndex : "status",
							hidden : true,
							renderer : function (e) {
								if(e==1){
									return  "新建";
								}else{
									return  "删除";
								}
							}
						}, {
							header : "品牌类型",
							width : 60,
							dataIndex : "infoType",
							renderer : function (e) {
								if(e==1){
									return  "可评分";
								}else if(e==2){
									return  "不可评分";
								}
							}
						}, {
							header : "创建日期",
							width : 60,
							dataIndex : "creatDate"
						}, {
							header : "创建人",
							width : 60,
							dataIndex : "createUser",
							renderer : function (e) {
								return e;
							}
						}
					],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});
			this.topbar = new Ext.Toolbar({
					id : "BXCScoreManageFootBar",
					height : 30,
					bodyStyle : "text-align:left",
					items : []
				});
			this.gridPanel = new Ext.grid.GridPanel({
					id : "BXCScoreManageGrid",
					region : "center",
					stripeRows : true,
					plugins : d,
					tbar : this.topbar,
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
					url : __ctxPath + "/bandpoor/unScoreListPpcScore.do",
					params : {
						"Q_infoType_N_EQ" : 1
					},
					success : function (c, d) {
						var b = Ext.util.JSON.decode(d.response.responseText);
						a.gridPanel.getStore().loadData(b);
					}
				});
			}
		}
	});
