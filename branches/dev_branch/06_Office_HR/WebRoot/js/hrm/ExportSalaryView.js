Ext.ns("ExportSalaryView");
ExportSalaryView = Ext.extend(Ext.Panel, {
			searchPanel : null,
			gridPanel : null,
			store : null,
			topbar : null,
			constructor : function (a) {
				Ext.applyIf(this, a);
				this.initUIComponents();
				ExportSalaryView.superclass.constructor.call(this, {
						id : "ExportSalaryView",
						title : "导出模板设置",
						iconCls : "menu-news_type",
						region : "center",
						layout : "border",
						items : [this.formPanel, this.gridPanel]
					});
			},
			initUIComponents : function () {
				this.formPanel = new Ext.FormPanel({
							id : "ExportSalarySearchForm",
							title : "模板设置",
							region : "north",
							height : 65,
							frame : false,
							border : false,
							layout : "hbox",
							layoutConfig : {
								padding : "5",
								align : "middle"
							},
							defaults : {
								style : "padding:0px 5px 0px 5px;",
								xtype : "label"
							},
							items : [{
									text : "模板名称"
								}, {
									xtype : "textfield",
									id:"ExportSalary.name",
									name : "exportSalary.name"
								}, {
									text : "保存",
									xtype : "button",
									iconCls : "btn-save",
									handler : function () {
										Ext.getCmp("ExportSalarySearchForm").getForm().submit({
											waitMsg : "正在保存信息",
											url : __ctxPath + "/hrm/saveExportSalary.do",
											success : function (f, g) {
												var e = Ext.util.JSON.decode(g.response.responseText);
											}
										});
									}
								},{
									id:"ExportSalary.id",
									xtype : "hidden",
									name : "exportSalary.id"
								}
							]
						});
						this.formPanel
									.getForm()
									.load(
											{
												deferredRender : false,
												url : __ctxPath
														+ "/hrm/getExportSalary.do",
												waitMsg : "正在载入数据...",
												success : function(g, h) {
													var e = Ext.util.JSON
															.decode(h.response.responseText).data[0];
													var d = e.id;
													Ext.getCmp("ExportSalary.id").setValue(d);
													Ext.getCmp("ExportSalary.name").setValue(e.name);
													Ext.getCmp("ExportSalaryItemGrid").getStore().reload();
												},
												failure : function(c, d) {
												}
											});
				this.store = new Ext.data.Store({
							proxy : new Ext.data.HttpProxy({
									url : __ctxPath + "/hrm/listExSaItemExportSalary.do"
								}),
							reader : new Ext.data.JsonReader({
									root : "result",
									totalProperty : "totalCounts",
									id : "id",
									fields : [{
											name : "id",
											type : "int"
										}, "exportName","fromTableName","fromTableTypeName",
										"orderCol","exportSalId","isDefaut"
									]
								}),
							remoteSort : true
						});
				this.store.setDefaultSort("orderCol", "asc");
				this.store.load({
						params : {
							start : 0,
							limit : 999
						}
					});
				var c = new Ext.grid.CheckboxSelectionModel();
				var a = new Ext.grid.ColumnModel({
							columns : [c, new Ext.grid.RowNumberer(), {
									header : "导出字段id",
									hidden : true,
									dataIndex : "id",
									width : 40
								}, {
									header : "导出字段名",
									dataIndex : "exportName",
									width : 100
								}, {
									header : "字段来源",
									dataIndex : "fromTableName",
									width : 50
								}, {
									header : "字段来源类型",
									dataIndex : "fromTableTypeName",
									width : 50
								}, {
									header : "字段顺序",
									dataIndex : "orderCol",
									width : 50
								}, {
									header : "所属模板",
									dataIndex : "exportSalId",
									hidden : true,
									width : 50
								}, {
									header : "默认字段",
									dataIndex : "isDefaut",
									hidden : true,
									width : 50
								},{
									header : "管理",
									dataIndex : "id",
									width : 150,
									renderer : function (g, f, d, j, e) {
										var i = d.data.id;
										var h = "";
										if (d.data.isDefaut!=0) {
											h = '<button title="删除" value=" " class="btn-del" onclick="ExportSalaryView.remove(' + i + ')">&nbsp</button>';
											h += '&nbsp;&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ExportSalaryView.edit(' + i + ')">&nbsp</button>';
										}
										h += '&nbsp;&nbsp;<button title="置顶" value=" " class="btn-up" onclick="ExportSalaryView.sort(' + i + "," + 1 + ')">&nbsp</button>';
										h += '&nbsp;&nbsp;<button title="上移" value=" " class="btn-top" onclick="ExportSalaryView.sort(' + i + "," + 2 + ')">&nbsp</button>';
										h += '&nbsp;&nbsp;<button title="下移" value=" " class="btn-down" onclick="ExportSalaryView.sort(' + i + "," + 3 + ')">&nbsp</button>';
										h += '&nbsp;&nbsp;<button title="置末" value=" " class="btn-last" onclick="ExportSalaryView.sort(' + i + "," + 4 + ')">&nbsp</button>';
										return h;
									}
								}
							],
							defaults : {
								menuDisabled : true,
								width : 100
							},
							listeners : {
								hiddenchange : function (d, e, f) {
									saveConfig(e, f);
								}
							}
						});
				this.topbar = new Ext.Toolbar({
							height : 30,
							items : []
						});
				this.topbar.add(new Ext.Button({
							text : "添加",
							iconCls : "btn-add",
							handler : function () {
								var a=Ext.getCmp("ExportSalary.id").getValue();
								new ExportSalaryItemForm({exportSaId:a}).show();
							}
						}));
				this.gridPanel = new Ext.grid.GridPanel({
							id : "ExportSalaryItemGrid",
							region : "center",
							autoWidth : true,
							autoHeight : true,
							tbar : this.topbar,
							closable : true,
							store : this.store,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							cm : a,
							sm : c,
							viewConfig : {
								forceFit : true,
								enableRowBody : false,
								showPreview : false
							},
							bbar : new Ext.PagingToolbar({
									pageSize : 999,
									store : this.store,
									displayInfo : true,
									displayMsg : "当前显示从{0}至{1}， 共{2}条记录",
									emptyMsg : "当前没有记录"
								})
						});
				this.gridPanel.addListener("rowdblclick", b);
				function b(f, d, g) {
					f.getSelectionModel().each(function (e) {
							if (isGranted("_ExportSalaryEdit")) {
								ExportSalaryView.edit(e.data.id);
							}
						});
				}
			}
		});
ExportSalaryView.remove = function (a) {
	Ext.Msg.confirm("删除操作", "你确定要删除该类别吗?", function (b) {
			if (b == "yes") {
				Ext.Ajax.request({
						url : __ctxPath + "/hrm/removeExSaItemExportSalary.do",
						method : "post",
						params : {
							id : a
						},
						success : function () {
							Ext.ux.Toast.msg("操作信息", "类别删除成功");
							Ext.getCmp("ExportSalaryItemGrid").getStore().reload();
						},
						failure : function () {
							Ext.ux.Toast.msg("操作信息", "类别删除失败");
						}
					});
			}
		});
};
ExportSalaryView.edit = function (b) {
	var a=Ext.getCmp("ExportSalary.id").getValue();
	new ExportSalaryItemForm({exportSaId:a,id:b}).show();
};
ExportSalaryView.sort = function (b, a) {
	Ext.Ajax.request({
			url : __ctxPath + "/hrm/sortExportSalary.do",
			method : "post",
			params : {
				id : b,
				opt : a
			},
			success : function () {
				Ext.getCmp("ExportSalaryItemGrid").getStore().reload();
			},
			failure : function () {
				Ext.ux.Toast.msg("操作信息", "操作失败");
				Ext.getCmp("ExportSalaryItemGrid").getStore().reload();
			}
		});
};
 