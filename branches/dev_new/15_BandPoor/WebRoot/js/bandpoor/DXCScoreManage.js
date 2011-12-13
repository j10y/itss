DXCScoreManage = Ext.extend(Ext.Panel, {
		constructor : function (a) {
			if (a == null) {
				a = {};
			}
			Ext.apply(this, a);
			this.initComponents();
			DXCScoreManage.superclass.constructor.call(this, {
				id : "DXCScoreManage",
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
							id : "ScoreManageSearchFormBandName",
							width : 120,
							name : "Q_bandName_S_LK",
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
									var d = Ext.getCmp("ScoreManageSearchFormBandName").getStore();
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
							xtype : "button",
							text : "查询",
							iconCls : "search",
							handler : this.search.createCallback(this)
						}
					]
				});
			this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/bandpoor/listDxcScore.do",
					root : "result",
					baseParams : {
					},
					totalProperty : "totalCounts",
					remoteSort : false,
					fields : [{
							name : "id",
							type : "int"
						}, "bandName","saleStoreNum", "bandScore", "status", "infoType", "creatDate", "createUser", "content","bandrealScore"]
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
			this.dxctopbar = new Ext.Toolbar({
					id : "DXCScoreManagFootBar",
					height : 30,
					bodyStyle : "text-align:left",
					items : []
				});
			if (isGranted("_DXCScoreManageSet")) {
				this.dxctopbar.add(new Ext.Button({
						iconCls : "btn-setting",
						text : "指定分值设定",
						handler : this.setScore,
						scope : this
					}));
			}
			if (isGranted("_DXCScoreManageAllSet")) {
				this.dxctopbar.add(new Ext.Button({
						iconCls : "btn-setting",
						text : "全部设定",
						handler : this.setAllScore,
						scope : this
					}));
			}
			if (isGranted("_DXCScoreManageCountScore")) {
				this.dxctopbar.add(new Ext.Button({
						iconCls : "btn-pred",
						text : "分值计算",
						handler : this.countScoreValue,
						scope : this
					}));
			}
			if (isGranted("_DXCScoreManageDel")) {
				this.dxctopbar.add(new Ext.Button({
						iconCls : "btn-del",
						text : "删除",
						handler : this.delRecords,
						scope : this
					}));
			}		
			this.gridPanel = new Ext.grid.GridPanel({
					id : "DXCScoreManageGrid",
					region : "center",
					stripeRows : true,
					plugins : d,
					tbar : this.dxctopbar,
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
					url : __ctxPath + "/bandpoor/listDxcScore.do",
					params : {
						"Q_infoType_N_EQ" : 1
					},
					success : function (c, d) {
						var b = Ext.util.JSON.decode(d.response.responseText);
						a.gridPanel.getStore().loadData(b);
					}
				});
			}
		},setScore : function () {
				var c = Ext.getCmp("DXCScoreManageGrid");
				var a = c.getSelectionModel().getSelections();
				if (a.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要设定考核分数的记录！");
					return;
				}
				var d = Array();
				for (var b = 0; b < a.length; b++) {
					d.push(a[b].data.id);
				}
				this.saveScoreValue("2",d);
		},
		setAllScore : function () {
			//1 为全部设定 2为部分设定
				this.saveScoreValue("1","");
		},
		delRecords : function () {
			var c = Ext.getCmp("DXCScoreManageGrid");
			var a = c.getSelectionModel().getSelections();
			if (a.length == 0) {
				Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
				return;
			}
			var d = Array();
			for (var b = 0; b < a.length; b++) {
				d.push(a[b].data.id);
			}
			this.delByIds(d);
		},
		delByIds : function (a) {
			Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function (b) {
				if (b == "yes") {
					Ext.Ajax.request({
						url : __ctxPath + "/bandpoor/multiDelDxcScore.do",
						params : {
							ids : a
						},
						method : "POST",
						success : function (c, d) {
							Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
							Ext.getCmp("DXCScoreManageGrid").getStore().reload();
						},
						failure : function (c, d) {
							Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
						}
					});
				}
			});
		},
		saveScoreValue:function(a,b){
		//a 全部审定或选择设定 b 选择设定的ids
		var formPanel = new Ext.FormPanel({
					layout : "form",
					bodyStyle : "padding:10px 10px 10px 10px",
					border : false,
					id : "bandScoreValueForm",
					defaults : {
						anchor : "98%,98%"
					},
					defaultType : "textfield",
					items : [{
					fieldLabel : "品牌考核分",
					name : "bandScoreValue",
					id : "bandScoreValue",
					allowBlank : false,
					blankText : "品牌考核分不能为空！"
					}]
				});
				var win = new Ext.Window({
					id : 'setScoreWin',
					title : '设定品牌考核分',
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
								var bandScoreValue=Ext.getCmp("bandScoreValue").getValue();
								if(bandScoreValue.length==0){
									Ext.MessageBox
												.show({
													title : "操作信息",
													msg : "考核分数不能为空！",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
												return;
								}
								Ext.getCmp("bandScoreValueForm").getForm().submit({
									waitMsg : "正在设定数据...",
									url : __ctxPath + "/bandpoor/saveScoreValueDxcScore.do?bandScoreValue="+bandScoreValue+"&settype="+a+"&ids="+b,
									success : function (c, d) {
											Ext.getCmp("setScoreWin").close();
											Ext.getCmp("DXCScoreManageGrid").getStore().reload();
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
								Ext.getCmp("setScoreWin").close();
							}
						}]
				});
				win.show();
		},
		countScoreValue:function(){
		//a 全部审定或选择设定 b 选择设定的ids
		var acountFormPanel = new Ext.FormPanel({
					layout : "form",
					url : __ctxPath + "/bandpoor/countScoreValueDxcScore.do",
					bodyStyle : "padding:10px 10px 10px 10px",
					border : false,
					id : "acountBandScoreValueForm",
					defaults : {
						anchor : "98%,98%"
					},
					defaultType : "textfield",
					items : [{
						fieldLabel : "采集年份",
						name : "beElectedBandPoor.year",
						id : "beElectedBandPoor.year",
						xtype : "combo",
						triggerAction : "all",
						allowBlank : false,
						store : ["2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021"]
						
					},{
						name : "beElectedBandPoor.poorVersion",
						id : "beElectedBandPoor.poorVersion",
						xtype : "hidden"
					},{
						fieldLabel : "采集频率",
						name : "beElectedBandPoor.poorVersionName",
						id : "beElectedBandPoor.poorVersionName",
						xtype : "combo",
						triggerAction : "all",
						allowBlank : false,
						store : [["1","一次采集(6月-10)"],["2","二次采集(11月-次年5月)"]],
						listeners : {
								select:function(e,c,d){
									var poorVersion=Ext.getCmp("beElectedBandPoor.poorVersionName").getValue();
									Ext.getCmp("beElectedBandPoor.poorVersion").setValue(poorVersion);
								}
						}
					}]
				});
				var win = new Ext.Window({
					id : 'countScoreWin',
					title : '计算品牌考核分',
					width : 300,
					height : 150,
					layout : "fit",
					iconCls : "menu-JobSalaryRelation",
					modal : true,
					maximizable : true,
					items : acountFormPanel,
					buttonAlign : "center",
					buttons : [
						{
							text : "设定",
							iconCls : "btn-save",
							handler : function(){
								var a = Ext.getCmp("acountBandScoreValueForm");
								if (a.getForm().isValid()) {
									a.getForm().submit({
										method : "post",
										waitMsg : "正在提交数据...",
										success : function (b, c) {
											Ext.ux.Toast.msg("操作信息", "成功保存信息！");
											Ext.getCmp("DXCScoreManageGrid").getStore().reload();
											Ext.getCmp("countScoreWin").close();
										},
										failure : function (b, c) {
											Ext.MessageBox.show({
												title : "操作信息",
												msg : c.result.msg,
												buttons : Ext.MessageBox.OK,
												icon : "ext-mb-error"
											});
											Ext.getCmp("countScoreWin").close();
										}
									});
								} else {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : "红线框的为必填字段！",
										buttons : Ext.MessageBox.OK,
										icon : "ext-mb-error"
									});
								}
							}
						}, {
							text : "取消",
							iconCls : "btn-cancel",
							handler : function(a){
								Ext.getCmp("countScoreWin").close();
							}
						}]
				});
				win.show();
		}
	});
