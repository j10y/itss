ScoreManageView = Ext.extend(Ext.Panel, {
		searchPanel : null,
		gridPanel : null,
		store : null,
		topbar : null,
		constructor : function (a) {
			Ext.applyIf(this, a);
			this.initUIComponents();
			ScoreManageView.superclass.constructor.call(this, {
				id : "ScoreManageView",
				iconCls : "menu-dictionary",
				title : "可评分信息管理",
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
					id : "ScoreManageSearchForm",
					defaults : {
						xtype : "label",
						style : "padding:0px 5px 0px 5px;"
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
							valueField : "fromBandName",
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
						},{
							xtype : "button",
							text : "查询",
							iconCls : "search",
							handler : this.search.createCallback(this)
						}
					]
				});
			this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/bandpoor/listScoreManage.do",
					root : "result",
					totalProperty : "totalCounts",
					remoteSort : true,
					fields : [{
								name : "id",
								type : "int"
							}, "bandName", 
							"floorNumName",
							 "proClassName",
							 "bandStyleName",
							"mainPriceName",
							"saleStoreName",
							"bandBusinessAreaName",
							"bandDesc",
							"infoStatus"],
					listeners : {
						beforeload : function() {
							this.baseParams = {
								Q_bandName_S_LK : Ext.getCmp("ScoreManageSearchFormBandName").getValue(),
								start : 0,
								limit : 25
							};
						}
					}

				});
			this.store.load();
			var b = [];
			if (isGranted("_ScoreManageDel")) {
				b.push({
					iconCls : "btn-del",
					qtip : "删除",
					style : "margin:0 3px 0 3px"
				});
			}
			if (isGranted("_ScoreManageEdit")) {
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
					id : "ScoreManageFootBar",
					height : 30,
					bodyStyle : "text-align:left",
					items : []
				});
			if (isGranted("_ScoreManageAdd")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-add",
						text : "添加",
						handler : this.createRecord,
						scope : this
					}));
			}
			if (isGranted("_ScoreManageDel")) {
				this.topbar.add(new Ext.Button({
						iconCls : "btn-del",
						text : "删除",
						handler : this.delRecords,
						scope : this
					}));
			}
			if (isGranted("_ScoreManageImport")) {
				this.topbar.add(new Ext.Button({
					text : "批量导入品牌信息",
					handler : this.uploadBandInfo,
				}));
			}
			this.topbar.add(new Ext.Button({
				text : "下载品牌信息数据模板excel文件",
				handler : function() {
					window.open(__ctxPath + "/userfiles/dataTemplate/infopool.xls");
				}
			}));
			this.gridPanel = new Ext.grid.GridPanel({
					id : "ScoreManageGrid",
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
					if (isGranted("_ScoreManageEdit")) {
						ScoreManageView.edit(e.data.id);
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
			new ScoreManageForm().show();
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
							Ext.getCmp("ScoreManageGrid").getStore().reload();
						},
						failure : function (c, d) {
							Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
						}
					});
				}
			});
		},
		delRecords : function () {
			var c = Ext.getCmp("ScoreManageGrid");
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
		editRecord : function (a) {
			new ScoreManageForm({
				id : a.data.id
			}).show();
		},
		uploadBandInfo : function() {
			var a = App.createUploadDialog({
				file_cat : "uploadData",
				callback : function (c) {
					for (var b = 0; b < c.length; b++) {
						Ext.Ajax.request({
							url : __ctxPath + "/bandpoor/uploadScoreManage.do",
							params : {
								filePath : c[b].filepath
							},
							success : function(d) {
								var e = Ext.util.JSON.decode(d.responseText);
								if(e.flag == "0") {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : e.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
									return ;
								}
								Ext.getCmp("ScoreManageGrid").getStore().reload();
								Ext.MessageBox.show({
									title : "操作信息",
									msg : "数据导入成功！",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						});
					}
				}
			});
			a.show();
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
