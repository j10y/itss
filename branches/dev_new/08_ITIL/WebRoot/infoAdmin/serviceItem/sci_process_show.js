ProcessPanel = Ext.extend(Ext.Panel, {
	id : "processPanel",
	title : '服务项流程信息',
	closable : true,
	width : 1020,
	height : 400,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	frame : true,
	items : this.items,
	getServiceItemProcessPanel : function(tempId) {
		this.serviceItemProcessPanel = new Ext.form.FormPanel({
			id : 'serviceItemProcessPanel',
			height : '400',
			width : 800,
			frame : true,
			collapsible : true,
			buttonAlign : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{
				name : 'ServiceItemProcess$id',
				mapping : 'ServiceItemProcess$id'
			}, {
				name : 'ServiceItemProcess$processInfo',
				mapping : 'ServiceItemProcess$processInfo'
			}, {
				name : 'ServiceItemProcess$reqTable',
				mapping : 'ServiceItemProcess$reqTable'
			}, {
				name : 'ServiceItemProcess$endPageModel',
				mapping : 'ServiceItemProcess$endPageModel'
			}, {
				name : 'ServiceItemProcess$buttonName',
				mapping : 'ServiceItemProcess$buttonName'
			}, {
				name : 'ServiceItemProcess$agreement',
				mapping : 'ServiceItemProcess$agreement'
			}, {
				name : 'ServiceItemProcess$sidProcessType',
				mapping : 'ServiceItemProcess$sidProcessType'
			}, {
				name : 'ServiceItemProcess$definitionName',
				mapping : 'ServiceItemProcess$definitionName'
			}, {
				name : 'ServiceItemProcess$pagePanel',
				mapping : 'ServiceItemProcess$pagePanel'
			}, {
				name : 'ServiceItemProcess$pageModel',
				mapping : 'ServiceItemProcess$pageModel'
			}, {
				name : 'ServiceItemProcess$pageListPanel',
				mapping : 'ServiceItemProcess$pageListPanel'
			}]),
			// title : "服务项流程设置",
			items : [{
				xtype : 'fieldset',
				title : '<font color=red>流程信息</font>',
				layout : 'column',
				anchor : '100%',
				labelwidth : 50,
				autoHeight : true,
				items : [
						// new Ext.form.TextField({
						// fieldLabel : '流程类型',
						// xtype : 'textfield',
						// id : 'ServiceItemProcess$sidProcessType',
						// name : 'ServiceItemProcess$sidProcessType',
						// width : 200,
						// hidden : true,
						// allowBlank : true
						// }),
						{
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								name : "ServiceItemProcess$sidProcessType",
								allowBlank : false,
								fieldLabel : "流程类型",
								id : 'ServiceItemProcess$sidProcessTypeCombo',
								width : 180,
								mode : 'local',
								defaultParam : '',
								allowBlank : false,
								hiddenName : 'ServiceItemProcess$sidProcessType',
								xtype : 'combo',
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								typeAhead : true,
								forceSelection : true,
								emptyText : '请选择',
								store : new Ext.data.SimpleStore({
									fields : ['id', 'name'],
									data : [['0', '申请流程'], ['1', '变更流程'],['2', '撤销流程'],['3', '重置流程'],['4', '查询流程']]
								}),
								listeners : {
									'expand' : function(combo) {
										combo.reset();
									}
								},
								initComponent : function() {
									this.store.load({
										callback : function(r, options, success) {
											Ext.getCmp('ServiceItemProcess$sidProcessTypeCombo')
													.setValue(Ext.getCmp('ServiceItemProcess$sidProcessTypeCombo').getValue());
										}
									});
								}
							})]
						}, {
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.TextField({
								fieldLabel : '流程名称',
								xtype : 'textfield',
								id : 'ServiceItemProcess$definitionName',
								name : 'ServiceItemProcess$definitionName',
								width : 200,
								allowBlank : true
							})]
						}, {
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ServiceItemProcess$processInfo',
								id : 'ServiceItemProcess$processInfoCombo',
								width : 200,
								style : '',
								fieldLabel : '流程',
								colspan : 0,
								rowspan : 0,
								lazyRender : true,
								displayField : 'processName',
								valueField : 'id',
								emptyText : '请选择...',
								allowBlank : true,
								typeAhead : true,
								name : 'ServiceItemProcess$processInfo',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext
											+ '/sciProcess_getProcessComboData.action',
									fields : ['id', 'processName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ServiceItemProcess$processInfo'] == undefined) {
												opt.params['processName'] = Ext.getCmp('ServiceItemProcess$processInfoCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
										var param = queryEvent.combo
												.getRawValue();
										this.defaultParam = param;
										if (queryEvent.query == '') {
											param = '';
										}
										this.store.load({
											params : {
												processName : param,
												start : 0
											}
										});
										return true;
									}
								},
								initComponent : function() {
									this.store.load({
										params : {
											id : Ext.getCmp('ServiceItemProcess$processInfoCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ServiceItemProcess$processInfoCombo')
												.setValue(Ext.getCmp('ServiceItemProcess$processInfoCombo').getValue());
										}
									});
								}
							})]
						}, {
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ServiceItemProcess$pagePanel',
								id : 'ServiceItemProcess$pagePanelCombo',
								width : 200,
								style : '',
								fieldLabel : '入口面板',
								colspan : 0,
								rowspan : 0,
								lazyRender : true,
								displayField : 'pageName',
								valueField : 'id',
								emptyText : '请选择...',
								allowBlank : true,
								typeAhead : true,
								name : 'ServiceItemProcess$pagePanel',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext
											+ '/sciProcess_getPanelComboData.action',
									// +
									// '/pageModel/pagePanelManage.do?methodCall=getPagePanelComboData',
									fields : ['id', 'pageName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ServiceItemProcess$pagePanel'] == undefined) {
												opt.params['pageName'] = Ext.getCmp('ServiceItemProcess$pagePanelCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
										var param = queryEvent.combo.getRawValue();
										this.defaultParam = param;
										if (queryEvent.query == '') {
											param = '';
										}
										this.store.load({
											params : {
												pageName : param,
												start : 0
											}
										});
										return true;
									}
								},
								initComponent : function() {
									this.store.load({
										params : {
											id : Ext.getCmp('ServiceItemProcess$pagePanelCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ServiceItemProcess$pagePanelCombo')
												.setValue(Ext.getCmp('ServiceItemProcess$pagePanelCombo').getValue());
										}
									});
								}
							})]
						}, {
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ServiceItemProcess$pageListPanel',
								id : 'ServiceItemProcess$pageListPanelCombo',
								width : 200,
								style : '',
								fieldLabel : '列表面板',
								colspan : 0,
								rowspan : 0,
								lazyRender : true,
								displayField : 'pageName',
								valueField : 'id',
								emptyText : '请选择...',
								allowBlank : true,
								typeAhead : true,
								name : 'ServiceItemProcess$pageListPanel',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext
											+ '/sciProcess_getPanelComboData.action',
									// +
									// '/pageModel/pagePanelManage.do?methodCall=getPagePanelComboData',
									fields : ['id', 'pageName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ServiceItemProcess$pageListPanel'] == undefined) {
												opt.params['pageName'] = Ext.getCmp('ServiceItemProcess$pageListPanelCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
										var param = queryEvent.combo.getRawValue();
										this.defaultParam = param;
										if (queryEvent.query == '') {
											param = '';
										}
										this.store.load({
											params : {
												pageName : param,
												start : 0
											}
										});
										return true;
									}
								},
								initComponent : function() {
									this.store.load({
										params : {
											id : Ext.getCmp('ServiceItemProcess$pageListPanelCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ServiceItemProcess$pageListPanelCombo')
												.setValue(Ext.getCmp('ServiceItemProcess$pageListPanelCombo').getValue());
										}
									});
								}
							})]
						}, {
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ServiceItemProcess$pageModel',
								id : 'ServiceItemProcess$pageModelCombo',
								width : 200,
								style : '',
								fieldLabel : '入口页面',
								colspan : 0,
								rowspan : 0,
								lazyRender : true,
								displayField : 'pageName',
								valueField : 'id',
								emptyText : '请选择...',
								allowBlank : true,
								typeAhead : true,
								name : 'ServiceItemProcess$pageModel',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext
											+ '/sciProcess_getModelComboData.action',
									// +
									// '/pageModel/pageModelManage.do?methodCall=getPageModelComboData',
									fields : ['id', 'pageName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ServiceItemProcess$pageModel'] == undefined) {
												opt.params['pageName'] = Ext.getCmp('ServiceItemProcess$pageModelCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
										var param = queryEvent.combo.getRawValue();
										this.defaultParam = param;
										if (queryEvent.query == '') {
											param = '';
										}
										this.store.load({
											params : {
												pageName : param,
												start : 0
											}
										});
										return true;
									}
								},
								initComponent : function() {
									this.store.load({
										params : {
											id : Ext.getCmp('ServiceItemProcess$pageModelCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ServiceItemProcess$pageModelCombo')
												.setValue(Ext.getCmp('ServiceItemProcess$pageModelCombo').getValue());
										}
									});
								}
							})]
						}, {
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ServiceItemProcess$endPageModel',
								id : 'ServiceItemProcess$endPageModelCombo',
								width : 200,
								style : '',
								fieldLabel : '结束页面',
								colspan : 0,
								rowspan : 0,
								lazyRender : true,
								displayField : 'pageName',
								valueField : 'id',
								emptyText : '请选择...',
								allowBlank : true,
								typeAhead : true,
								name : 'ServiceItemProcess$endPageModel',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext
											+ '/sciProcess_getModelComboData.action',
									// +
									// '/pageModel/pageModelManage.do?methodCall=getPageModelComboData',
									fields : ['id', 'pageName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ServiceItemProcess$endPageModel'] == undefined) {
												opt.params['pageName'] = Ext.getCmp('ServiceItemProcess$endPageModelCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
										var param = queryEvent.combo.getRawValue();
										this.defaultParam = param;
										if (queryEvent.query == '') {
											param = '';
										}
										this.store.load({
											params : {
												pageName : param,
												start : 0
											}
										});
										return true;
									}
								},
								initComponent : function() {
									this.store.load({
										params : {
											id : Ext.getCmp('ServiceItemProcess$endPageModelCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ServiceItemProcess$endPageModelCombo')
												.setValue(Ext.getCmp('ServiceItemProcess$endPageModelCombo').getValue());
										}
									});
								}
							})]
						},

						{
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ServiceItemProcess$reqTable',
								id : 'ServiceItemProcess$reqTableCombo',
								width : 200,
								style : '',
								fieldLabel : '需求主表',
								lazyRender : true,
								displayField : 'tableName',
								valueField : 'id',
								emptyText : '请选择...',
								allowBlank : true,
								typeAhead : true,
								name : 'ServiceItemProcess$reqTable',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext+ '/sciProcess_getSmtComboData.action',
									// +
									// '/infoAdmin/sysMainTable.do?methodCall=getSmtComboData',
									fields : ['id', 'tableName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ServiceItemProcess$reqTable'] == undefined) {
												opt.params['tableName'] = Ext.getCmp('ServiceItemProcess$reqTableCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
										var param = queryEvent.combo.getRawValue();
										this.defaultParam = param;
										if (queryEvent.query == '') {
											param = '';
										}
										this.store.load({
											params : {
												tableName : param,
												start : 0
											}
										});
										return true;
									}
								},
								initComponent : function() {
									this.store.load({
										params : {
											id : Ext.getCmp('ServiceItemProcess$reqTableCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ServiceItemProcess$reqTableCombo')
												.setValue(Ext.getCmp('ServiceItemProcess$reqTableCombo').getValue());
										}
									});
								}
							})]
						}, new Ext.form.Hidden({
							xtype : 'hidden',
							id : 'ServiceItemProcess$id',
							colspan : 0,
							rowspan : 0,
							name : 'ServiceItemProcess$id',
							width : 200,
							style : '',
							value : '',
							fieldLabel : '自动编号'
						}), {
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.TextField({
								fieldLabel : '流程启动按钮名称',
								xtype : 'textfield',
								id : 'ServiceItemProcess$buttonName',
								name : 'ServiceItemProcess$buttonName',
								width : 200,
								allowBlank : true
							})]
						}]
			},
					// /////////////////////////////////////////////////////////////////////////////////////
					{
						xtype : 'fieldset',
						title : '<font color=red>用户协议信息</font>',
						layout : 'column',
						anchor : '100%',
						labelwidth : 50,
						autoHeight : true,
						// buttonAlign : 'center',
						items : [{
							columnWidth : 1,
							layout : 'form',
							border : false,
							items : [new Ext.form.HtmlEditor({
								fieldLabel : '用户协议',
								// xtype : 'textfield',
								id : 'ServiceItemProcess$agreement',
								name : 'ServiceItemProcess$agreement',
								width : 500,
								allowBlank : true
							})]
						}]
					}]
		});
		if (tempId != 0) {
			this.serviceItemProcessPanel.load({
				url : webContext
						+ '/sciProcess_getSciProcessInfo.action?sciProcessId='
						+ tempId,
				timeout : 30,
				success : function(action, form) {

				}
			});
		}
		return this.serviceItemProcessPanel;
	},
	showtt : function() {
		var record = this.processgrid.getSelectionModel().getSelected();
		var id = record.get("id");
		var curSciId = this.serviceItemId;
		this.getServiceItemProcessPanel(id);
		var win1 = new Ext.Window({
			title : '服务项流程信息',
			proxy : '0',
			height : 500,
			width : 800,
			buttonAlign : 'center',
			resizable : false,
			draggable : true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'fit',
			items : [this.serviceItemProcessPanel],
			buttons : [{
				text : '保存',
				handler : function() {

					alert(curSciId);
					var info = Ext.encode(Ext.getCmp('serviceItemProcessPanel').form.getValues(false));
					Ext.Ajax.request({
						url : webContext + '/sciProcess_saveProcess.action',
						params : {
							serviceItemId : curSciId,
							info : info
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('serviceItemProcessPanel').load({
								url : webContext
										+ '/infoAdmin/serviceItemProcessAction.do?methodCall=getSciProcessInfo&sciProcessId='
										+ curId,
								timeout : 30,
								success : function(action, form) {

								}
							});
							Ext.MessageBox.alert("保存成功");
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("保存失败");
						}
					}, this);
				}
			}, {
				text : '关闭',
				handler : function() {
					win1.close();
				},
				listeners : {
					'beforeclose' : function(p) {
						return true;
					}
				},
				scope : this
			}]
		});
		win1.show();
	},
	create : function() {
		this.getServiceItemProcessPanel(0);
		var curSciId = this.serviceItemId;
		var win1 = new Ext.Window({
			title : '服务项流程信息',
			height : 500,
			width : 800,
			buttonAlign : 'center',
			layout : 'fit',
			items : [this.serviceItemProcessPanel],
			buttons : [{
				text : '保存',
				handler : function() {
					alert(curSciId);

					var info = Ext.encode(Ext.getCmp('serviceItemProcessPanel').form.getValues(false));
					Ext.Ajax.request({
						url : webContext + '/sciProcess_saveProcess.action',
						params : {
							serviceItemId : curSciId,
							info : info
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('serviceItemProcessPanel').load({
								url : webContext
										+ '/infoAdmin/serviceItemProcessAction.do?methodCall=getSciProcessInfo&sciProcessId='
										+ curId,
								timeout : 30,
								success : function(action, form) {

								}
							});
							Ext.MessageBox.alert("保存成功");
							Ext.getCmp('processgridpanel').getStore()
									.removeAll();
							Ext.getCmp('processgridpanel').getStore().reload();
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("保存失败");
						}
					}, this);
				}
			}, {
				text : '关闭',
				handler : function() {
					win1.close();
				},
				listeners : {
					'beforeclose' : function(p) {
						return true;
					}
				},
				scope : this
			}]
		});
		win1.show();
	},
	remove : function() {
		var record = this.processgrid.getSelectionModel().getSelected();
		var id = record.get("id");
		Ext.Ajax.request({
			url : webContext + '/sciProcess_removeProcess.action',
			params : {
				processId : id
			},

			success : function(response, options) {
				Ext.getCmp('processgridpanel').getStore().removeAll();
				Ext.getCmp('processgridpanel').getStore().reload();
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	initComponent : function() {
		// 创建表格数据
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/infoAdmin/serviceItemProcessAction.do?methodCall=listSciProcess&serviceItemId='
					+ this.serviceItemId,
			fields : ['id', 'processInfo', 'reqTable', 'pageModel',
					'endPageModel', 'buttonName', 'agreement'],
			totalProperty : "rowCount",
			root : "data",
			id : 'id'
		});

		var sm = new Ext.grid.CheckboxSelectionModel();

		// 创建Grid表格组件
		this.processgrid = new Ext.grid.GridPanel({
			width : 'auto',// 800,
			height : 550,
			loadMask : true,
			id : 'processgridpanel',
			frame : true,
			store : this.store,
			trackMouseOve : true,
			tbar : [{
				pressed : true,
				text : "生成新流程关联",
				iconCls : 'add',
				scope : this,
				handler : this.create
			}, {
				pressed : true,
				text : "删除流程关联",
				iconCls : 'delete',
				scope : this,
				handler : this.remove
			}],
			sm : sm,
			columns : [sm, {
				id : 'id',
				header : "id",
				width : 50,
				sortable : true,
				dataIndex : 'id',
				hidden : true
			}, {
				header : "流程",
				width : 100,
				sortable : true,
				dataIndex : 'processInfo'
			}, {
				header : "需求主表",
				width : 200,
				sortable : true,
				dataIndex : 'reqTable'
			}, {
				header : "入口页面",
				width : 200,
				sortable : true,
				dataIndex : 'pageModel'
			}, {
				header : "结束页面",
				width : 200,
				sortable : true,
				dataIndex : 'endPageModel'
			}, {
				header : "流程启动按钮名",
				width : 200,
				sortable : true,
				dataIndex : 'buttonName'
			}, {
				header : "用户协议",
				width : 150,
				sortable : true,
				hidden : true,
				dataIndex : 'agreement'
			}]
		})
		this.store.removeAll();
		this.store.load();
		this.processgrid.on("rowdblclick", this.showtt, this);
		var item = new Array();
		item.push(this.processgrid);
		this.items = item;
		ProcessPanel.superclass.initComponent.call(this);
	}
});
