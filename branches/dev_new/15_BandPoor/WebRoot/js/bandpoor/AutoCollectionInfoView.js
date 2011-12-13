AutoCollectionInfoView = Ext.extend(Ext.Panel, {
		searchPanel : null,
		gridPanel : null,
		store : null,
		topbar : null,
		constructor : function (a) {
			Ext.applyIf(this, a);
			this.initUIComponents();
			AutoCollectionInfoView.superclass.constructor.call(this, {
				id : "AutoCollectionInfoView",
				iconCls : "menu-dictionary",
				title : "自动采集信息管理",
				region : "center",
				layout : "border",
				items : [this.searchPanel, this.gridPanel]
			});
		},
		initUIComponents : function () {
			this.searchPanel = new Ext.FormPanel({
					height : 35,
					region : "north",
					frame : false,
					border : false,
					layout : "hbox",
					layoutConfig : {
						padding : "5",
						align : "middle"
					},
					id : "AutoCollectionSearchForm",
					defaults : {
						xtype : "label",
						style : "padding:0px 5px 0px 5px;"
					},
					items : [{
							text : "请输入查询条件:"
						}, {
							text : "品牌"
						}, {
							id : "AutoCollectionSearchFormBandName",
							width : 120,
							name : "Q_bandName_S_LK",
							maxHeight : 200,
							xtype : "combo",
							mode : "local",
							editable : true,
							triggerAction : "all",
							valueField : "fromBandId",
							displayField : "fromBandName",
							store : new Ext.data.SimpleStore(
							{
								url : __ctxPath
										+ "/bandpoor/getBandsScoreManage.do",
								fields : [
										"fromBandId",
										"fromBandName"]
							}),
							listeners : {
								focus : function (e) {
									var d = Ext.getCmp("AutoCollectionSearchFormBandName").getStore();
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
						},{
							xtype : "button",
							text : "查询",
							iconCls : "search",
							handler : this.search.createCallback(this)
						}
					]
				});
			this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
						url : __ctxPath + "/bandpoor/autoColListScoreManage.do"
					}),
					reader : new Ext.data.JsonReader({
						root : "result",
						totalProperty : "totalCounts",
						id : "id",
						fields : [{
								name : "id",
								type : "int"
							},"proClassName","mainPriceName","saleStoreName","saleSroteDesc","contactUser","contactPhone","bandDesc","companyNature","companyAddress","productLine","infoStatus"]
					}),
					remoteSort : true
				});
			this.store.setDefaultSort("id", "desc");
			this.store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
			var d = new Ext.ux.grid.RowExpander({
					tpl : new Ext.Template('<div style="padding:5px 5px 5px 62px;">{bandDesc}</div>')
				});
			var c = new Ext.grid.CheckboxSelectionModel();
			var a = new Ext.grid.ColumnModel({
					columns : [c, new Ext.grid.RowNumberer(),d, {
							header : "id",
							dataIndex : "id",
							hidden : true
						}, {
							header : "品类",
							dataIndex : "proClassName"
						}, {
							header : "主力价格",
							dataIndex : "mainPriceName"
						}, {
							header : "销售场所",
							dataIndex : "saleStoreName"
						}, {
							header : "销售场所描述",
							dataIndex : "saleSroteDesc"
						}, {
							header : "联系人",
							dataIndex : "contactUser"
						}, {
							header : "联系电话",
							dataIndex : "contactPhone"
						},{
							header : "公司性质",
							dataIndex : "companyNature"
						},{
							header : "产地",
							dataIndex : "companyAddress"
						},{
							header : "生产线",
							dataIndex : "productLine"
						},{
							header : "状态",
							dataIndex : "infoStatus",
							renderer : function (d) {
								if (d == null || d == "" || d == "undefined") {
									return "无";
								} else if(d==0){
									return "删除";
								} else if(d==1){
									return "新建";
								} else if(d==2){
									return "审核通过";
								} else if(d==3){
									return "打回";
								} else if(d==4){
									return "修改";
								}
							}
						}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});
			this.topbar = new Ext.Toolbar({
					id : "AutoCollectionFootBar",
					height : 30,
					bodyStyle : "text-align:left",
					items : []
				});
			if (isGranted("_AutoCollectionExport")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-add",
						text : "导出",
						handler : this.createRecord,
						scope : this
					}));
			}
			if (isGranted("_AutoCollectionDel")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-del",
						text : "删除",
						handler : this.delRecords,
						scope : this
					}));
			}
			this.gridPanel = new Ext.grid.GridPanel({
					id : "AutoCollectionGrid",
					region : "center",
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					autoHeight : true,
					cm : a,
					sm : c,
					plugins : d,
					viewConfig : {
						forceFit : true,
						autoFill : true,
						forceFit : true
					},
					bbar : new Ext.PagingToolbar({
						pageSize : 25,
						store : this.store,
						displayInfo : true,
						displayMsg : "当前显示{0}至{1}， 共{2}条记录",
						emptyMsg : "当前没有记录"
					})
				});
		},
		search : function (a) {
			if (a.searchPanel.getForm().isValid()) {
				a.searchPanel.getForm().submit({
					waitMsg : "正在提交查询",
					url : __ctxPath + "/bandpoor/autoColListScoreManage.do",
					success : function (c, d) {
						var b = Ext.util.JSON.decode(d.response.responseText);
						a.gridPanel.getStore().loadData(b);
					}
				});
			}
		},
		delByIds : function (a) {
			Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function (b) {
				if (b == "yes") {
					Ext.Ajax.request({
						url : __ctxPath + "/bandpoor/multiDelScoreManage.do",
						params : {
							ids : a
						},
						method : "POST",
						success : function (c, d) {
							Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
							Ext.getCmp("AutoCollectionGrid").getStore().reload();
						},
						failure : function (c, d) {
							Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
						}
					});
				}
			});
		},
		delRecords : function () {
			var c = Ext.getCmp("AutoCollectionGrid");
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
		}
	});
