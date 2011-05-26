ModelProcessPanel = Ext.extend(Ext.Panel, {
	id : "processPanel",
	title : '模块流程关联信息',
	//closable : true,
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
			height : '300',
			width : 700,
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
				name : 'ModleToProcess$id',
				mapping : 'ModleToProcess$id'
			},{
				name : 'ModleToProcess$processInfo',
				mapping : 'ModleToProcess$processInfo'
			}, {
				name : 'ModleToProcess$processStatusType',
				mapping : 'ModleToProcess$processStatusType'
			}, {
				name : 'ModleToProcess$modleType',
				mapping : 'ModleToProcess$modleType'
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
							{
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ModleToProcess$processInfo',
								id : 'ModleToProcess$processInfoCombo',
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
								name : 'ModleToProcess$processInfo',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext+ '/sciProcess_getProcessComboData.action',
									fields : ['id', 'processName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ModleToProcess$processInfo'] == undefined) {
												opt.params['ModleToProcess$processInfo'] = Ext.getCmp('ModleToProcess$processInfoCombo').defaultParam;
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
											id : Ext.getCmp('ModleToProcess$processInfoCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ModleToProcess$processInfoCombo').setValue(Ext.getCmp('ModleToProcess$processInfoCombo').getValue());
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
								name : "ModleToProcess$processStatusType",
								allowBlank : false,
								fieldLabel : "流程类型",
								id : 'ModleToProcess$processStatusTypeCombo',
								width : 200,
								mode : 'local',
								defaultParam : '',
								allowBlank : false,
								hiddenName : 'ModleToProcess$processStatusType',
								xtype : 'combo',
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								typeAhead : true,
								forceSelection : true,
								emptyText : '请选择',
								store : new Ext.data.SimpleStore({
									fields : ['id', 'name'],
									data : [['0', '申请流程'], ['1', '变更流程'],['2', '删除流程']]
								})//,
							})]
						},	
							{
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								name : "ModleToProcess$modleType",
								allowBlank : false,
								fieldLabel : "流程隶属模块",
								id : 'ModleToProcess$modleTypeCombo',
								width : 200,
								mode : 'local',
								defaultParam : '',
								allowBlank : false,
								hiddenName : 'ModleToProcess$modleType',
								xtype : 'combo',
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								typeAhead : true,
								forceSelection : true,
								emptyText : '请选择',
								store : new Ext.data.SimpleStore({
									fields : ['id', 'name'],
									data : [['CI', '配置项'], ['SCI', '服务项'],['SCIC', '服务目录'],['Event', '事件'],['Notice', '公告'],
									['Kno_Solution', '解决方案'],['Kno_Contract', '合同'],['Kno_File', '文件']]
								})//,
							})]
						},
						new Ext.form.Hidden({
							xtype : 'hidden',
							id : 'ModleToProcess$id',
							colspan : 0,
							rowspan : 0,
							name : 'ModleToProcess$id',
							width : 200,
							style : '',
							value : '',
							fieldLabel : '自动编号'
						})]
			}]
		});
		if (tempId != 0) {
			this.serviceItemProcessPanel.load({
				//url : webContext+ '/sciProcess_getSciProcessInfo.action?sciProcessId='+ tempId,
				url : webContext+ '/configWorkflow_findModleToProcess.action?dataId='+ tempId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp('ModleToProcess$processStatusTypeCombo').initComponent();
					Ext.getCmp('ModleToProcess$processInfoCombo').initComponent();
				}
			});
		}
		return this.serviceItemProcessPanel;
	},
	showtt : function() {
		var record = this.processgrid.getSelectionModel().getSelected();
		var id = record.get("id");
		this.getServiceItemProcessPanel(id);
		var win1 = new Ext.Window({
			title : '服务项流程信息',
			proxy : '0',
			height : 300,
			width : 700,
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
					var info = Ext.encode(getFormParam('serviceItemProcessPanel'));
					Ext.Ajax.request({
						url : webContext + '/configWorkflow_saveModleToProcess.action',
						params : {
							info : info
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('serviceItemProcessPanel').load({
								url : webContext+ '/configWorkflow_getModleProcess.action',
								timeout : 30,
								success : function(action, form) {

								}
							});
							
							Ext.MessageBox.alert("保存成功");
							Ext.getCmp('processgridpanel').getStore().removeAll();
							Ext.getCmp('processgridpanel').getStore().reload();
							win1.close();
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
			height : 300,
			width : 700,
			buttonAlign : 'center',
			layout : 'fit',
			items : [this.serviceItemProcessPanel],
			buttons : [{
				text : '保存',
				handler : function() {
					var info = Ext.encode(getFormParam('serviceItemProcessPanel'));
					Ext.Ajax.request({
						url : webContext + '/configWorkflow_saveModleToProcess.action',
						params : {
							serviceItemId : curSciId,
							info : info
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							Ext.getCmp('serviceItemProcessPanel').load({
								url : webContext+ '/configWorkflow_getModleProcess.action',
								timeout : 30,
								success : function(action, form) {

								}
							});
							Ext.MessageBox.alert("保存成功");
							win1.close();
							Ext.getCmp('processgridpanel').getStore().removeAll();
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
			url : webContext + '/configWorkflow_removeModleToProcess.action',
			params : {
				dataId : id
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
			url : webContext+ '/configWorkflow_getModleProcess.action',
			fields : ['id', 'modleType', 'processStatusType', 'definitionName'],
			totalProperty : "rowCount",
			root : "data",
			id : 'id'
		});
		this.pageBar = new Ext.PagingToolbar({
			id : 'pagebar',
			pageSize : 15,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "没有符合条件的数据"
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
			bbar : this.pageBar,
			autoScroll : true,
			trackMouseOve : true,
			tbar : [{
				pressed : true,
				text : "生成新模块流程关联",
				iconCls : 'add',
				scope : this,
				handler : this.create
			}, {
				pressed : true,
				text : "删除模块流程关联",
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
				header : "隶属功能模块",
				width : 100,
				sortable : true,
				dataIndex : 'modleType'
			}, {
				header : "流程类型",
				width : 200,
				sortable : true,
				dataIndex : 'processStatusType'
			}, {
				header : "对应流程",
				width : 200,
				sortable : true,
				dataIndex : 'definitionName'
			}]
		})
		var param = {
			pageSize : 15,
			start : 0
		};
		this.store.baseParams=param;
		this.store.removeAll();
		this.store.load();
		this.processgrid.on("rowdblclick", this.showtt, this);
		var item = new Array();
		item.push(this.processgrid);
		this.items = item;
		ModelProcessPanel.superclass.initComponent.call(this);
	}
});
