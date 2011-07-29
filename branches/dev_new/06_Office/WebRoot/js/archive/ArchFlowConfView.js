ArchFlowConfView = Ext
		.extend(
				Ext.Panel,
				{
					formPanel : null,
					gridPanel : null,
					store : null,
					topbar : null,
					constructor : function(a) {
						Ext.applyIf(this, a);
						this.initUIComponents();
						ArchFlowConfView.superclass.constructor.call(this, {
							id : "ArchFlowConfView",
							title : "公文流程配置",
							iconCls : "menu-archive-setting",
							region : "center",
							layout : "form",
							tbar : this.toolbar,
							items : [ this.formPanel, this.sendArchFlowGridPanel, this.recArchFlowGridPanel, this.gridPanel]
						});
					},
					initUIComponents : function() {
						this.formPanel = new Ext.FormPanel(
								{
									layout : "form",
									bodyStyle : "padding:10px 10px 10px 10px",
									border : false,
									url : __ctxPath
											+ "/archive/saveArchFlowConf.do",
									id : "ArchFlowConfForm",
									defaults : {
										anchor : "98%,98%"
									},
									defaultType : "textfield",
									items : [ {
										xtype : "fieldset",
										title : "公文流程配置",
										items : [
												{
													xtype : "container",
													layout : "column",
													items : [
															{
																xtype : "label",
																text : "发文流程:",
																width : 101
															},
															{
																xtype : "textfield",
																name : "sendProcessName",
																id : "sendProcessName",
																width : 200
															},
															{
																xtype : "hidden",
																name : "sendProcessId",
																id : "sendProcessId"
															},
															{
																xtype : "button",
																iconCls : "menu-flow",
																text : "选择流程",
																handler : function() {
																	FlowSelector
																			.getView(
																					function(
																							j,
																							h) {
																						Ext
																								.getCmp(
																										"sendProcessId")
																								.setValue(
																										j);
																						Ext
																								.getCmp(
																										"sendProcessName")
																								.setValue(
																										h);
																					},
																					true)
																			.show();
																}
															} ]
												},
												{
													xtype : "container",
													style : "padding-top:3px;",
													layout : "column",
													items : [
															{
																xtype : "label",
																text : "收文流程:",
																width : 101
															},
															{
																xtype : "textfield",
																name : "recProcessName",
																id : "recProcessName",
																width : 200
															},
															{
																xtype : "hidden",
																name : "recProcessId",
																id : "recProcessId"
															},
															{
																xtype : "button",
																text : "选择流程",
																iconCls : "menu-flow",
																handler : function() {
																	FlowSelector
																			.getView(
																					function(
																							j,
																							h) {
																						Ext
																								.getCmp(
																										"recProcessId")
																								.setValue(
																										j);
																						Ext
																								.getCmp(
																										"recProcessName")
																								.setValue(
																										h);
																					},
																					true)
																			.show();
																}
															} ]
												} ]
									} ]
								});
						Ext.Ajax
								.request({
									url : __ctxPath
											+ "/archive/getArchFlowConf.do",
									success : function(h, j) {
										var k = Ext.util.JSON
												.decode(h.responseText).data;
										Ext.getCmp("sendProcessId").setValue(
												k.sendProcessId);
										Ext.getCmp("sendProcessName").setValue(
												k.sendProcessName);
										Ext.getCmp("recProcessId").setValue(
												k.recProcessId);
										Ext.getCmp("recProcessName").setValue(
												k.recProcessName);
									}
								});
						this.toolbar = new Ext.Toolbar({
							id : "ArchToolbar",
							items : [
									{
										text : "保存",
										iconCls : "btn-save",
										handler : this.save.createCallback(
												this.formPanel, this)
									},
									{
										text : "重置",
										iconCls : "btn-reset",
										handler : this.reset
												.createCallback(this.formPanel)
									},
									{
										text : "取消",
										iconCls : "btn-cancel",
										handler : this.cancel
												.createCallback(this)
									} ]
						});
						this.store = new Ext.data.JsonStore({
							url : __ctxPath + "/archive/depListArchRecUser.do",
							root : "result",
							totalProperty : "totalCounts",
							remoteSort : true,
							fields : [ "archRecId", "userId", "fullname",
									"depId", "depLevel", "depName" ]
						});
						this.store.load();
						var g = new Ext.grid.CheckboxSelectionModel();
						var f = 0;
						var c = 0;
						var e = 0;
						var b = new Ext.form.ComboBox({
							mode : "local",
							anchor : "74%",
							allowBlank : false,
							editable : false,
							valueField : "name",
							displayField : "name",
							triggerAction : "all",
							store : new Ext.data.SimpleStore({
								url : __ctxPath
										+ "/archive/selectArchRecUser.do",
								fields : [ "id", "name" ]
							}),
							listeners : {
								select : function(l, h, k) {
									var j = Ext.getCmp("ArchFlowConfGrid")
											.getStore();
									var m = j.getAt(f);
									m.set("userId", h.data.id);
									m.set("fullname", h.data.name);
								}
							}
						});
						var a = new Ext.grid.ColumnModel(
								{
									columns : [
											new Ext.grid.RowNumberer(),
											{
												header : "archRecId",
												dataIndex : "archRecId",
												hidden : true
											},
											{
												header : "部门ID ",
												dataIndex : "depId",
												hidden : true
											},
											{
												header : "部门名称",
												dataIndex : "depName",
												renderer : function(l, k, h) {
													var m = "";
													var n = h.data.depLevel;
													if (n != null && !isNaN(n)) {
														for ( var j = 2; j <= n; j++) {
															m += '<img src="'
																	+ __ctxPath
																	+ '/images/system/down.gif"/>';
														}
													}
													m += l;
													return m;
												}
											}, {
												header : "用户ID",
												dataIndex : "userId",
												hidden : true
											}, {
												header : "部门收文负责人",
												dataIndex : "fullname",
												editor : b
											} ],
									defaults : {
										sortable : false,
										menuDisabled : false,
										width : 100
									}
								});
						this.topbar = new Ext.Toolbar(
								{
									height : 30,
									bodyStyle : "text-align:left",
									items : [ {
										iconCls : "btn-save",
										text : "保存",
										xtype : "button",
										handler : function() {
											var l = [];
											var k = Ext
													.getCmp("ArchFlowConfGrid");
											var j = k.getStore();
											for (i = 0, cnt = j.getCount(); i < cnt; i += 1) {
												var h = j.getAt(i);
												if (h.data.archRecId == ""
														|| h.data.archRecId == null) {
													if (h.data.userId != null
															&& h.data.userId != "") {
														h.set("archRecId", -1);
													}
												}
												if (h.dirty) {
													l.push(h.data);
												}
											}
											if (l.length == 0) {
												Ext.ux.Toast.msg("信息",
														"没有对数据进行任何更改");
												return;
											}
											Ext.Ajax
													.request({
														method : "post",
														url : __ctxPath
																+ "/archive/saveArchRecUser.do",
														success : function(m) {
															Ext.ux.Toast
																	.msg(
																			"操作信息",
																			"成功设置部门收文负责人");
															j.reload();
															k.getView()
																	.refresh();
														},
														failure : function(m) {
															Ext.MessageBox
																	.show({
																		title : "操作信息",
																		msg : "信息保存出错，请联系管理员！",
																		buttons : Ext.MessageBox.OK,
																		icon : "ext-mb-error"
																	});
														},
														params : {
															data : Ext
																	.encode(l)
														}
													});
										}
									} ]
								});
						this.gridPanel = new Ext.grid.EditorGridPanel({
							id : "ArchFlowConfGrid",
							title : "部门负责人配置",
							region : "center",
							stripeRows : true,
							tbar : this.topbar,
							store : this.store,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							autoHeight : true,
							cm : a,
							viewConfig : {
								autoFill : true
							}
						});
						var d = this.gridPanel;
						d.on("cellclick", function(j, m, k, l) {
							f = m;
							var h = j.getStore().getAt(f).get("depId");
							b.getStore().load({
								params : {
									depId : h
								}
							});
						});
						
						//add by awen for select process by archive's type on 2011-07-26 beigin
						this.sendArchstore = new Ext.data.JsonStore({
							url : __ctxPath + "/archive/listArchivesType.do",
							root : "result",
							totalProperty : "totalCounts",
							remoteSort : true,
							fields : [ "typeId", "typeName", "typeDesc","processDefId","processDefName"]
						});
						this.sendArchstore.load();
						var g = new Ext.grid.CheckboxSelectionModel();
						var sendCheckLine = 0;
						var c = 0;
						var e = 0;
						var sendArchFlowCombo = new Ext.form.ComboBox({
							mode : "local",
							anchor : "74%",
							allowBlank : false,
							editable : false,
							valueField : "name",
							displayField : "name",
							triggerAction : "all",
							store : new Ext.data.SimpleStore({
								url : __ctxPath 
										+ "/flow/selectProDefinition.do",
								params : {
									typeId : this.typeId == null ? 0 : this.typeId
								},
								fields : [ "defId", "name"]
							}),
							listeners : {
								select : function(l, h, k) {
									var j = Ext.getCmp("SendArchFlowConfGrid")
											.getStore();
									var m = j.getAt(sendCheckLine);
									m.set("processDefId", h.data.defId);
									m.set("processDefName", h.data.name);
								}
							}
						});
						var sendColumnModel = new Ext.grid.ColumnModel(
								{
									columns : [
											new Ext.grid.RowNumberer(),
											{
												header : "typeId",
												dataIndex : "typeId",
												hidden : true
											},
											{
												header : "发文类型",
												dataIndex : "typeName",
												renderer : function(l, k, h) {
													var m = "";
													var n = h.data.depLevel;
													if (n != null && !isNaN(n)) {
														for ( var j = 2; j <= n; j++) {
															m += '<img src="'
																	+ __ctxPath
																	+ '/images/system/down.gif"/>';
														}
													}
													m += l;
													return m;
												}
											}, {
												header : "流程定义ID",
												dataIndex : "processDefId",
												hidden : true
											}, {
												header : "流程定义",
												dataIndex : "processDefName",
												editor : sendArchFlowCombo
											} ],
									defaults : {
										sortable : false,
										menuDisabled : false,
										width : 100
									}
								});
						this.sendTopbar = new Ext.Toolbar(
								{
									height : 30,
									bodyStyle : "text-align:left",
									items : [ {
										iconCls : "btn-save",
										text : "保存",
										xtype : "button",
										handler : function() {
											var l = [];
											var k = Ext.getCmp("SendArchFlowConfGrid");
											var j = k.getStore();
											for (i = 0, cnt = j.getCount(); i < cnt; i += 1) {
												var h = j.getAt(i);
												if (h.data.typeId == ""
														|| h.data.typeId == null) {
													if (h.data.processDefId != null
															&& h.data.processDefId != "") {
														h.set("typeId", -1);
													}
												}
												if (h.dirty) {
													l.push(h.data);
												}
											}
											if (l.length == 0) {
												Ext.ux.Toast.msg("信息",
														"没有对数据进行任何更改");
												return;
											}
											Ext.Ajax
													.request({
														method : "post",
														url : __ctxPath
																+ "/archive/saveListArchivesType.do",
														success : function(m) {
															Ext.ux.Toast
																	.msg(
																			"操作信息",
																			"成功设置发文流程");
															j.reload();
															k.getView()
																	.refresh();
														},
														failure : function(m) {
															Ext.MessageBox
																	.show({
																		title : "操作信息",
																		msg : "信息保存出错，请联系管理员！",
																		buttons : Ext.MessageBox.OK,
																		icon : "ext-mb-error"
																	});
														},
														params : {
															data : Ext
																	.encode(l)
														}
													});
										}
									} ]
								});
						this.sendArchFlowGridPanel = new Ext.grid.EditorGridPanel({
							id : "SendArchFlowConfGrid",
							title : "发文流程配置",
							stripeRows : true,
							tbar : this.sendTopbar,
							store : this.sendArchstore,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							autoHeight : true,
							cm : sendColumnModel,
							viewConfig : {
								autoFill : true
							}
						});
						var sendTmp = this.sendArchFlowGridPanel;
						sendTmp.on("cellclick", function(j, m, k, l) {
							sendCheckLine = m;
							sendArchFlowCombo.getStore().load({
								params : {
									typeId : 1
								}
							});
						});
						
						this.recArchstore = new Ext.data.JsonStore({
							url : __ctxPath + "/archive/listArchRecType.do",
							root : "result",
							totalProperty : "totalCounts",
							remoteSort : true,
							fields : [ "recTypeId", "typeName","processDefId","processDefName"]
						});
						this.recArchstore.load();
						var g = new Ext.grid.CheckboxSelectionModel();
						var recCheckLine = 0;
						var c = 0;
						var e = 0;
						var recArchFlowCombo = new Ext.form.ComboBox({
							mode : "local",
							anchor : "74%",
							allowBlank : false,
							editable : false,
							valueField : "name",
							displayField : "name",
							triggerAction : "all",
							store : new Ext.data.SimpleStore({
								url : __ctxPath 
										+ "/flow/selectProDefinition.do",
								params : {
									typeId : this.typeId == null ? 0 : this.typeId
								},
								fields : [ "defId", "name"]
							}),
							listeners : {
								select : function(l, h, k) {
									var j = Ext.getCmp("RecArchFlowConfGrid")
											.getStore();
									var m = j.getAt(recCheckLine);
									m.set("processDefId", h.data.defId);
									m.set("processDefName", h.data.name);
								}
							}
						});
						var recColumnModel = new Ext.grid.ColumnModel(
								{
									columns : [
											new Ext.grid.RowNumberer(),
											{
												header : "recTypeId",
												dataIndex : "recTypeId",
												hidden : true
											},
											{
												header : "发文类型",
												dataIndex : "typeName",
												renderer : function(l, k, h) {
													var m = "";
													var n = h.data.depLevel;
													if (n != null && !isNaN(n)) {
														for ( var j = 2; j <= n; j++) {
															m += '<img src="'
																	+ __ctxPath
																	+ '/images/system/down.gif"/>';
														}
													}
													m += l;
													return m;
												}
											}, {
												header : "流程定义ID",
												dataIndex : "processDefId",
												hidden : true
											}, {
												header : "流程定义",
												dataIndex : "processDefName",
												editor : recArchFlowCombo
											} ],
									defaults : {
										sortable : false,
										menuDisabled : false,
										width : 100
									}
								});
						this.recTopbar = new Ext.Toolbar(
								{
									height : 30,
									bodyStyle : "text-align:left",
									items : [ {
										iconCls : "btn-save",
										text : "保存",
										xtype : "button",
										handler : function() {
											var l = [];
											var k = Ext.getCmp("RecArchFlowConfGrid");
											var j = k.getStore();
											for (i = 0, cnt = j.getCount(); i < cnt; i += 1) {
												var h = j.getAt(i);
												if (h.data.recTypeId == ""
														|| h.data.recTypeId == null) {
													if (h.data.processDefId != null
															&& h.data.processDefId != "") {
														h.set("recTypeId", -1);
													}
												}
												if (h.dirty) {
													l.push(h.data);
												}
											}
											if (l.length == 0) {
												Ext.ux.Toast.msg("信息",
														"没有对数据进行任何更改");
												return;
											}
											Ext.Ajax
													.request({
														method : "post",
														url : __ctxPath
																+ "/archive/saveListArchRecType.do",
														success : function(m) {
															Ext.ux.Toast
																	.msg(
																			"操作信息",
																			"成功设置收文流程");
															j.reload();
															k.getView()
																	.refresh();
														},
														failure : function(m) {
															Ext.MessageBox
																	.show({
																		title : "操作信息",
																		msg : "信息保存出错，请联系管理员！",
																		buttons : Ext.MessageBox.OK,
																		icon : "ext-mb-error"
																	});
														},
														params : {
															data : Ext
																	.encode(l)
														}
													});
										}
									} ]
								});
						this.recArchFlowGridPanel = new Ext.grid.EditorGridPanel({
							id : "RecArchFlowConfGrid",
							title : "收文流程配置",
							stripeRows : true,
							tbar : this.recTopbar,
							store : this.recArchstore,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							autoHeight : true,
							cm : recColumnModel,
							viewConfig : {
								autoFill : true
							}
						});
						var recTmp = this.recArchFlowGridPanel;
						recTmp.on("cellclick", function(j, m, k, l) {
							recCheckLine = m;
							recArchFlowCombo.getStore().load({
								params : {
									typeId : 1
								}
							});
						});				
						//add by awen for select process by archive's type on 2011-07-26 end

					},
					reset : function(a) {
						a.getForm().reset();
					},
					cancel : function(a) {
						var b = Ext.getCmp("centerTabPanel");
						if (a != null) {
							b.remove("ArchFlowConfView");
						}
					},
					save : function(a, b) {
						if (a.getForm().isValid()) {
							a.getForm().submit({
								method : "POST",
								waitMsg : "正在提交数据...",
								success : function(c, d) {
									Ext.ux.Toast.msg("操作信息", "成功保存信息！");
								},
								failure : function(c, d) {
									Ext.MessageBox.show({
										title : "操作信息",
										msg : "信息保存出错，请联系管理员！",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
								}
							});
						}
					}
				});