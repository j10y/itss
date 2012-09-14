YYCScoreManage = Ext.extend(Ext.Panel, {
		constructor : function (a) {
			if (a == null) {
				a = {};
			}
			Ext.apply(this, a);
			this.initComponents();
			YYCScoreManage.superclass.constructor.call(this, {
				id : "YYCScoreManage",
				title : "可评分应用池管理",
				region : "center",
				iconCls : "menu-personal-salary",
				layout : "border",
				items : [this.YYCSearchPanel, this.gridPanel]
			});
		},
		typeId : null,
		searchPanel : null,
		gridPanel : null,
		store : null,
		topbar : null,
		initComponents : function () {
			this.YYCSearchPanel = new Ext.FormPanel({
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
							id : "YYCSearchFormBandName",
							width : 120,
							name : "Q_bandName_S_LK",
							maxHeight : 200,
							xtype : "combo",
							mode : "local",
							editable : true,
							triggerAction : "all",
							valueField : "YYCBandId",
							displayField : "YYCBandName",
							store : new Ext.data.SimpleStore(
							{
								url : __ctxPath
										+ "/bandpoor/getBandsScoreManage.do",
								fields : [
										"YYCBandId",
										"YYCBandName"]
							}),
							listeners : {
								focus : function (e) {
									var d = Ext.getCmp("YYCSearchFormBandName").getStore();
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
							id : "bandPoor.year",
							xtype : "combo",
							triggerAction : "all",
							store : ["2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021"]
							
						}, {
							text : "采集频率"
						}, {
							name : "bandPoor.poorVersionName",
							id : "bandPoor.poorVersionName",
							xtype : "combo",
							triggerAction : "all",
							store : [["1","一次采集(6月-10)"],["2","二次采集(11月-次年5月)"]],
							listeners : {
									select:function(e,c,d){
										var poorVersion=Ext.getCmp("bandPoor.poorVersionName").getValue();
										Ext.getCmp("bandPoor.poorVersion").setValue(poorVersion);
									}
							}
						},{
							xtype : "button",
							text : "查询",
							iconCls : "search",
							handler : this.search.createCallback(this)
						},{
							name : "Q_poorVersion_N_EQ",
							id : "bandPoor.poorVersion",
							xtype : "hidden"
						}
					]
				});
			this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/bandpoor/scoreListPpcScore.do",
					root : "result",
					baseParams : {
					},
					totalProperty : "totalCounts",
					remoteSort : false,
					fields : [{
							name : "id",
							type : "int"
						}, "bandName","saleStoreNum", "bandScore", "status", "infoType", "creatDate", "createUser", "content","bandrealScore","year","poorVersion","bandlevel","mainprice"]
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
							header : "品牌等级（分类）",
							width : 60,
							dataIndex : "bandlevel"
						},{
							header : "主力价格段",
							width : 60,
							dataIndex : "mainprice"
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
					id : "YYCScoreManagFootBar",
					height : 30,
					bodyStyle : "text-align:left",
					items : []
				});
			if (isGranted("_YYCScoreManageSet")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-setting",
						text : "设定品牌等级",
						handler : this.setLevel,
						scope : this
					}));
			}
			this.gridPanel = new Ext.grid.GridPanel({
					id : "YYCScoreManageGrid",
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
				a.YYCSearchPanel.getForm().submit({
					waitMsg : "正在提交查询",
					url : __ctxPath + "/bandpoor/scoreListPpcScore.do",
					params : {
						"Q_infoType_N_EQ" : 1
					},
					success : function (c, d) {
						var b = Ext.util.JSON.decode(d.response.responseText);
						a.gridPanel.getStore().loadData(b);
					}
				});
		},setLevel : function () {
				var c = Ext.getCmp("YYCScoreManageGrid");
				var a = c.getSelectionModel().getSelections();
				if (a.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要设定品牌等级的记录！");
					return;
				}
				var d = Array();
				for (var b = 0; b < a.length; b++) {
					d.push(a[b].data.id);
				}
				this.saveLevelValue(d);
		},
		saveLevelValue:function(b){
		// b 选择设定的ids
		var formPanel = new Ext.FormPanel({
					layout : "form",
					bodyStyle : "padding:10px 10px 10px 10px",
					border : false,
					id : "bandLevelValueForm",
					defaults : {
						anchor : "98%,98%"
					},
					defaultType : "textfield",
					items : [{
							fieldLabel : "等级",
							name : "bandPoor.bandLevelValue",
							id : "bandPoor.bandLevelValue",
							maxHeight : 200,
							xtype : "combo",
							mode : "local",
							editable : true,
							allowBlank : false,
							triggerAction : "all",
							valueField : "frombandLevelId",
							displayField : "frombandLevelName",
							store : new Ext.data.SimpleStore({
								url : __ctxPath
								 + "/bandpoor/getLevelPpcScore.do",
								fields : [
									"frombandLevelId",
									"frombandLevelName"]
							}),
							listeners : {
								focus : function (b) {
									var a = Ext.getCmp("bandPoor.bandLevelValue").getStore();
									if (a.getCount() <= 0) {
										Ext.Ajax.request({
											url : __ctxPath + "/bandpoor/getLevelPpcScore.do",
											method : "post",
											success : function (d) {
												var c = Ext.util.JSON.decode(d.responseText);
												a.loadData(c);
											}
										});
									}
								},
								select:function(e,c,d){
									var levelId=Ext.getCmp("bandPoor.bandLevelValue").getValue();
									Ext.getCmp("bandPoor.bandLevelId").setValue(levelId);
								}
							}
						}, {
							name : "bandPoor.bandLevelId",
							id : "bandPoor.bandLevelId",
							xtype : "hidden"
						}]
				});
				var win = new Ext.Window({
					id : 'setLevelWin',
					title : '设定品牌等级',
					width : 300,
					height : 150,
					layout : "fit",
					iconCls : "menu-JobSalaryRelation",
					modal : true,
					maximizable : true,
					items : formPanel,
					buttonAlign : "center",
					buttons : [
						{
							text : "设定",
							iconCls : "btn-save",
							handler : function(){
								var bandLevelValue=Ext.getCmp("bandPoor.bandLevelValue").getValue();
								if(bandLevelValue.length==0){
									Ext.MessageBox
												.show({
													title : "操作信息",
													msg : "品牌等级不能为空！",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
												return;
								}
								Ext.getCmp("bandLevelValueForm").getForm().submit({
									waitMsg : "正在设定数据...",
									url : __ctxPath + "/bandpoor/saveLevelPpcScore.do?ids="+b,
									success : function (c, d) {
											Ext.getCmp("setLevelWin").close();
											Ext.getCmp("YYCScoreManageGrid").getStore().reload();
											Ext.ux.Toast.msg("信息", "设定成功！");
									},
									failure : function(c, d) {
										Ext.MessageBox
												.show({
													title : "操作信息",
													msg : "信息设定出错，请联系管理员！",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
									}
								});
							}
						}, {
							text : "取消",
							iconCls : "btn-cancel",
							handler : function(a){
								Ext.getCmp("setLevelWin").close();
							}
						}]
				});
				win.show();
		}
	});
