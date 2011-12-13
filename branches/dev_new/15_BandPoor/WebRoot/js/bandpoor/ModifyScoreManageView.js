ModifyScoreManageView = Ext.extend(Ext.Panel, {
		searchPanel : null,
		gridPanel : null,
		store : null,
		topbar : null,
		constructor : function (a) {
			Ext.applyIf(this, a);
			this.initUIComponents();
			ModifyScoreManageView.superclass.constructor.call(this, {
				id : "ModifyScoreManageView",
				iconCls : "menu-dictionary",
				title : "可评分信息修正",
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
					id : "ModifyScoreManageSearchForm",
					defaults : {
						xtype : "label",
						style : "padding:0px 5px 0px 5px;"
					},
					items : [{
							text : "请输入查询条件:"
						}, {
							text : "品牌"
						}, {
							id : "ModifyScoreManageSearchFormBandName",
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
									var d = Ext.getCmp("ModifyScoreManageSearchFormBandName").getStore();
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
						url : __ctxPath + "/bandpoor/listScoreManage.do"
					}),
					reader : new Ext.data.JsonReader({
						root : "result",
						totalProperty : "totalCounts",
						id : "id",
						fields : [{
								name : "id",
								type : "int"
							}, "bandName", "floorNumName", "proClassName","bandStyleName","mainPriceName","saleStoreName","bandBusinessAreaName","bandDesc","infoStatus"]
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
			var b = [];
			if (isGranted("_ModifyScoreManageDel")) {
				b.push({
					iconCls : "btn-del",
					qtip : "删除",
					style : "margin:0 3px 0 3px"
				});
			}
			if (isGranted("_ModifyScoreManageEdit")) {
				b.push({
					iconCls : "btn-edit",
					qtip : "编辑",
					style : "margin:0 3px 0 3px"
				});
			}
			this.rowActions = new Ext.ux.grid.RowActions({
					header : "管理",
					width : 80,
					actions : b
				});
			var c = new Ext.grid.CheckboxSelectionModel();
			var a = new Ext.grid.ColumnModel({
					columns : [c, new Ext.grid.RowNumberer(), {
							header : "id",
							dataIndex : "id",
							hidden : true
						}, {
							header : "品牌名称",
							dataIndex : "bandName"
						}, {
							header : "楼层",
							dataIndex : "floorNumName"
						}, {
							header : "品类",
							dataIndex : "proClassName"
						}, {
							header : "品牌风格",
							dataIndex : "bandStyleName"
						}, {
							header : "主力价格",
							dataIndex : "mainPriceName"
						},{
							header : "销售场所",
							dataIndex : "saleStoreName"
						},{
							header : "商圈",
							dataIndex : "bandBusinessAreaName"
						},{
							header : "备注",
							dataIndex : "bandDesc",
							renderer : function (d) {
								if (d == null || d == "" || d == "undefined") {
									return "无";
								} else {
									return d;
								}
							}
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
						}, this.rowActions],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});
			this.topbar = new Ext.Toolbar({
					id : "ModifyScoreManageFootBar",
					height : 30,
					bodyStyle : "text-align:left",
					items : []
				});
			if (isGranted("_ModifyScoreManageAdd")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-add",
						text : "添加",
						handler : this.createRecord,
						scope : this
					}));
			}
			if (isGranted("_ModifyScoreManageDel")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-del",
						text : "删除信息并同步品牌池",
						handler : this.delRecords,
						scope : this
					}));
			}
			if (isGranted("_ModifyScoreManagePass")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-add",
						text : "审核并进入品牌池",
						handler : this.passRecords,
						scope : this
					}));
			}
			this.gridPanel = new Ext.grid.GridPanel({
					id : "ModifyScoreManageGrid",
					region : "center",
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					autoHeight : true,
					cm : a,
					sm : c,
					plugins : this.rowActions,
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
			this.gridPanel.addListener("rowdblclick", function (f, d, g) {
				f.getSelectionModel().each(function (e) {
					if (isGranted("_ModifyScoreManageEdit")) {
						ModifyScoreManageView.edit(e.data.id);
					}
				});
			});
			this.rowActions.on("action", this.onRowAction, this);
		},
		search : function (a) {
			if (a.searchPanel.getForm().isValid()) {
				a.searchPanel.getForm().submit({
					waitMsg : "正在提交查询",
					url : __ctxPath + "/bandpoor/listScoreManage.do",
					success : function (c, d) {
						var b = Ext.util.JSON.decode(d.response.responseText);
						a.gridPanel.getStore().loadData(b);
					}
				});
			}
		},
		createRecord : function () {
			new ModifyScoreManageForm().show();
		},
		delByIds : function (a) {
			Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？此处删除将同时删除品牌池中最近品牌信息！", function (b) {
				if (b == "yes") {
					Ext.Ajax.request({
						url : __ctxPath + "/bandpoor/multiDelModifyScore.do",
						params : {
							ids : a
						},
						method : "POST",
						success : function (c, d) {
							Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
							Ext.getCmp("ModifyScoreManageGrid").getStore().reload();
						},
						failure : function (c, d) {
							Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
						}
					});
				}
			});
		},
		delRecords : function () {
			var c = Ext.getCmp("ModifyScoreManageGrid");
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
		passRecords:function(){
		var c = Ext.getCmp("ModifyScoreManageGrid");
		var ids = c.getSelectionModel().getSelections();
		if (ids.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要审批通过的记录！");
			return;
		}
		var d = Array();
		for (var i = 0; i < ids.length; i++) {
			d.push(ids[i].data.id);
		}
		var modifyCountFormPanel = new Ext.FormPanel({
					layout : "form",
					url : __ctxPath + "/bandpoor/applyRecordForModifyModifyScore.do",
					bodyStyle : "padding:10px 10px 10px 10px",
					border : false,
					id : "modifyCountFormPanelForm",
					defaults : {
						anchor : "98%,98%"
					},
					defaultType : "textfield",
					items : [{
						fieldLabel : "采集年份",
						name : "modfiyInfoPoor.year",
						id : "modfiyInfoPoor.year",
						xtype : "combo",
						triggerAction : "all",
						allowBlank : false,
						store : ["2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021"]
						
					},{
						name : "modfiyInfoPoor.poorVersion",
						id : "modfiyInfoPoor.poorVersion",
						xtype : "hidden"
					},{
						fieldLabel : "采集频率",
						name : "modfiyInfoPoor.poorVersionName",
						id : "modfiyInfoPoor.poorVersionName",
						xtype : "combo",
						triggerAction : "all",
						allowBlank : false,
						store : [["1","一次采集(6月-10)"],["2","二次采集(11月-次年5月)"]],
						listeners : {
								select:function(e,c,d){
									var poorVersion=Ext.getCmp("modfiyInfoPoor.poorVersionName").getValue();
									Ext.getCmp("modfiyInfoPoor.poorVersion").setValue(poorVersion);
								}
						}
					}]
				});
				var win = new Ext.Window({
					id : 'modfiyInfoPoorWin',
					title : '审核并进入品牌池',
					width : 300,
					height : 150,
					layout : "fit",
					iconCls : "menu-JobSalaryRelation",
					modal : true,
					maximizable : true,
					items : modifyCountFormPanel,
					buttonAlign : "center",
					buttons : [
						{
							text : "设定",
							iconCls : "btn-save",
							handler : function(){
								var a = Ext.getCmp("modifyCountFormPanelForm");
								if (a.getForm().isValid()) {
									a.getForm().submit({
										method : "post",
										params : {
											ids : d 
										},
										waitMsg : "正在提交数据...",
										success : function (b, c) {
											Ext.ux.Toast.msg("操作信息", "成功保存信息！");
											Ext.getCmp("ModifyScoreManageGrid").getStore().reload();
											Ext.getCmp("modfiyInfoPoorWin").close();
										},
										failure : function (b, c) {
											Ext.MessageBox.show({
												title : "操作信息",
												msg : c.result.msg,
												buttons : Ext.MessageBox.OK,
												icon : "ext-mb-error"
											});
											Ext.getCmp("modfiyInfoPoorWin").close();
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
								Ext.getCmp("modfiyInfoPoorWin").close();
							}
						}]
				});
				win.show();
		},
		editRecord : function (a) {
			new ModifyScoreManageForm({
				id : a.data.id
			}).show();
		},
		onRowAction : function (c, a, d, e, b) {
			switch (d) {
			case "btn-del":
				this.delByIds(a.data.id);
				break;
			case "btn-edit":
				this.editRecord(a);
				break;
			default:
				break;
			}
		}
	});
