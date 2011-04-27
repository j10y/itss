PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : false,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	save : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('panel_ServerManageOut_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		if(Ext.getCmp('ServerManage$serverTypeCombo').getValue()==2&&Ext.getCmp('ServerManage$serverPort').getValue()==""){
			Ext.MessageBox.alert("提示","服务器类型为Internet应用服务器时,请填写服务器端口。谢谢您合作！");
		 	return false;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var formParam = Ext.getCmp('panel_ServerManageOut_Input').form
				.getValues(false);

		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action?pagePanel=panel_ServerManageOut_Input'
				+ '&serviceItemId=' + curscid;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext
						+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
						+ curscid;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// 保存并提交
	saveAndSubmit : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('panel_ServerManageOut_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		if(Ext.getCmp('ServerManage$serverTypeCombo').getValue()==2&&Ext.getCmp('ServerManage$serverPort').getValue()==""){
			Ext.MessageBox.alert("提示","服务器类型为Internet应用服务器时,请填写服务器端口。谢谢您合作！");
		 	return false;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		formParam = Ext.getCmp('panel_ServerManageOut_Input').form
				.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action?pagePanel=panel_ServerManageOut_Input'
				+ '&serviceItemId=' + curscid;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curDataId = responseArray.id;
				var reqName = responseArray.reqName;
				var reqCode = responseArray.applyNum;
				var reqDate = responseArray.applyDate;
				Ext.Ajax.request({
					url : webContext
							+ '/requireWorkflow_applyForServerManage.action',
					params : {
						dataId : curDataId,
						model : curModel,
						bzparam : "{dataId :'"+ curDataId
								+ "',applyId : '"+ curDataId
								+ "',serviceItemId : '"+ curscid
								+ "',reqName : '"+ reqName
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.zsgj.itil.service.entity.ServiceItemApplyAuditHis'"
								+ ",applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {

						var meg = Ext.decode(response.responseText);
						if (meg.id != undefined) {
							Ext.Msg.alert("提示", "申请提交成功", function() {//modify by zhangzy for 用户要求添加提示
								window.location = webContext
										+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
										+ curscid;
							});
						} else {
							Ext.Msg.alert("提示", "申请提交失败", function() {
								alert(meg.Exception);
							});
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示", "申请提交失败");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("申请提交失败");
			}
		}, this);
	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},

	selectServer : function() {
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			// minTabWidth:100,
			// resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa) {
		this.Panel = new Ext.Panel({
			id : "1979sf09a8s7f9879sa87f9as87",
			height : 'auto',
			align : 'center',
			//title : panelTitle,
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	},

	getFormpanel_ServerManageOut_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ServerManageOut_Input", this);
		if (this.dataId != '0') {
			data = da.getRequireFormPanelElementsForEdit(
					"panel_ServerManageOut_Input", this.dataId);// 这是要随时变得
			for (i = 0; i < data.length; i++) {
				var idStr = data[i].id + "";
				if (idStr.indexOf('$applyNum') > 0
						|| idStr.indexOf('$applyUser') > 0
						|| idStr.indexOf('$applyDate') > 0) {
					data[i].readOnly = true;
					data[i].cls = "x-form-field-wrap";
					if (data[i].value == '') {
						data[i].emptyText = '自动生成';
						data[i].disabled = true;
					}
					if (data[i].xtype == 'combo'
							|| data[i].xtype == 'datefield') {
						data[i].hideTrigger = true;
					}
				}
				
				if (idStr=='ServerManage$configItemCombo'){
					data[i]= new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'ServerManage$configItem',
						id : 'ServerManage$configItemCombo',
						width : 200,
						style : '',
						fieldLabel : '服务器配置项',
						lazyRender : true,
						displayField : 'cisn',
						valueField : 'id',
						emptyText : '请选择...',
						allowBlank : false,
						typeAhead : true,
						name : 'ServerManage$configItem',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.entity.ConfigItem&configItemType=77',
							fields : ['id', 'cisn'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['ServerManage$configItem'] == undefined) {
										opt.params['cisn'] = Ext.getCmp('ServerManage$configItemCombo').defaultParam;
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
										cisn : param,
										start : 0
									}
								});
								return true;
							}
						},
						initComponent : function() {
							this.store.load({
								params : {
									id : Ext.getCmp('ServerManage$configItemCombo').getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									Ext.getCmp('ServerManage$configItemCombo').setValue(Ext.getCmp('ServerManage$configItemCombo')
											.getValue());
								}
							});
						}
					});
				}
				
			}
		} else {
			data = da.getPanelElementsForAdd("panel_ServerManageOut_Input");
			for (i = 0; i < data.length; i++) {
				var idStr = data[i].id + "";
				if (idStr.indexOf('$applyNum') > 0
						|| idStr.indexOf('$applyUser') > 0
						|| idStr.indexOf('$applyDate') > 0) {
					data[i].readOnly = true;
					data[i].cls = "x-form-field-wrap";
					if (data[i].value == '') {
						data[i].emptyText = '自动生成';
						data[i].disabled = true;
					}
					if (data[i].xtype == 'combo'|| data[i].xtype == 'datefield') {
						data[i].hideTrigger = true;
					}
				}
			}
		}
		if (this.status != 0) {
			biddata = da.splitForReadOnly(data);
		}else{
			biddata = da.split(data);
		}
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '保存为草稿',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.save
		});
		var submitButton = new Ext.Button({
			text : '保存并提交',
			id : 'workFlowButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : this.saveAndSubmit
		});
		var backButton = new Ext.Button({
			text : '返回',
			id : 'refresh',
			pressed : true,
			iconCls : 'back',
			scope : this,
			handler : this.back
		});
		if (this.status == 0) {
			curbuttons.push(saveButton);
			curbuttons.push(submitButton);
		}
		curbuttons.push(backButton);
		if (this.getFormButtons.length != 0) {
			this.formpanel_ServerManageOut_Input = new Ext.form.FormPanel({
				id : 'panel_ServerManageOut_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
//				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "服务器迁出数据中心申请",
				items : biddata,
				tbar : curbuttons
			});
		} else {
			this.formpanel_ServerManageOut_Input = new Ext.form.FormPanel({
				id : 'panel_ServerManageOut_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
//				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "服务器迁出数据中心申请",
				items : biddata,
				tbar : curbuttons
			});
		}
		return this.formpanel_ServerManageOut_Input;
	},
	// 获取配置项基础信息面板
	getFormconfigItemBasicPanel : function(id) {
		this.formconfigItemBasicPanel = new Ext.form.FormPanel({
			id : 'configItemBasicPanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
//			collapsible : true,
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
				name : 'ConfigItem$id',
				mapping : 'ConfigItem$id'
			}, {
				name : 'ConfigItem$configItemType',
				mapping : 'ConfigItem$configItemType'
			}, {
				name : 'ConfigItem$name',
				mapping : 'ConfigItem$name'
			}, {
				name : 'ConfigItem$cisn',
				mapping : 'ConfigItem$cisn'
			}, {
				name : 'ConfigItem$customerType',
				mapping : 'ConfigItem$customerType'
			}, {
				name : 'ConfigItem$customer',
				mapping : 'ConfigItem$customer'
			}, {
				name : 'ConfigItem$owner',
				mapping : 'ConfigItem$owner'
			}, {
				name : 'ConfigItem$principal',
				mapping : 'ConfigItem$principal'
			}, {
				name : 'ConfigItem$buyDate',
				mapping : 'ConfigItem$buyDate'
			}, {
				name : 'ConfigItem$useDate',
				mapping : 'ConfigItem$useDate'
			}, {
				name : 'ConfigItem$preStopDate',
				mapping : 'ConfigItem$preStopDate'
			}, {
				name : 'ConfigItem$acutalStopDate',
				mapping : 'ConfigItem$acutalStopDate'
			}, {
				name : 'ConfigItem$configItemStatus',
				mapping : 'ConfigItem$configItemStatus'
			}, {
				name : 'ConfigItem$environment',
				mapping : 'ConfigItem$environment'
			}, {
				name : 'ConfigItem$tenancyFlag',
				mapping : 'ConfigItem$tenancyFlag'
			}, {
				name : 'ConfigItem$descn',
				mapping : 'ConfigItem$descn'
			}, {
				name : 'ConfigItem$status',
				mapping : 'ConfigItem$status'
			}]),
			title : "配置项基础信息",
			items : [{
				html : '配置项类型:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'ConfigItem$configItemType',
				id : 'ConfigItem$configItemTypeCombo',
				width : 200,
				style : '',
				fieldLabel : '配置项类型',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				readOnly:true,
				hideTrigger : true,
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'ConfigItem$configItemType',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.entity.ConfigItemType',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['ConfigItem$configItemType'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('ConfigItem$configItemTypeCombo').defaultParam;
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
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('ConfigItem$configItemTypeCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('ConfigItem$configItemTypeCombo')
									.setValue(Ext
											.getCmp('ConfigItem$configItemTypeCombo')
											.getValue());
						}
					});
				}
			}), {
				html : '配置项名称:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '配置项名称',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				hideTrigger :true,
				rowspan : 0,
				id : 'ConfigItem$name',
				name : 'ConfigItem$name',
				style : '',
				width : 200,
				value : '',
				allowBlank : false,
				validator : '',
				vtype : ''
			}), {
				html : '配置项编号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '配置项编号',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				id : 'ConfigItem$cisn',
				name : 'ConfigItem$cisn',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '客户类型:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				readOnly:true,
				hideTrigger:true,
				hiddenName : 'ConfigItem$customerType',
				id : 'ConfigItem$customerTypeCombo',
				width : 200,
				style : '',
				fieldLabel : '客户类型',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'ConfigItem$customerType',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.actor.entity.CustomerType',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['ConfigItem$customerType'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('ConfigItem$customerTypeCombo').defaultParam;
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
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('ConfigItem$customerTypeCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('ConfigItem$customerTypeCombo')
									.setValue(Ext
											.getCmp('ConfigItem$customerTypeCombo')
											.getValue());
						}
					});
				}
			}), {
				html : '隶属客户:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'ConfigItem$customer',
				id : 'ConfigItem$customerCombo',
				width : 200,
				style : '',
				readOnly:true,
				hideTrigger : true,
				fieldLabel : '隶属客户',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'ConfigItem$customer',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext + '/extjs/comboDataAction?clazz=',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['ConfigItem$customer'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('ConfigItem$customerCombo').defaultParam;
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
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('ConfigItem$customerCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('ConfigItem$customerCombo').setValue(Ext
									.getCmp('ConfigItem$customerCombo')
									.getValue());
						}
					});
				}
			}), {
				html : '归属部门:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'ConfigItem$owner',
				id : 'ConfigItem$ownerCombo',
				width : 200,
				readOnly:true,
				hideTrigger : true,
				style : '',
				fieldLabel : '归属部门',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'ConfigItem$owner',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext + '/extjs/comboDataAction?clazz=',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['ConfigItem$owner'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('ConfigItem$ownerCombo').defaultParam;
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
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('ConfigItem$ownerCombo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('ConfigItem$ownerCombo')
									.setValue(Ext
											.getCmp('ConfigItem$ownerCombo')
											.getValue());
						}
					});
				}
			}), {
				html : '负责人:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'ConfigItem$principal',
				id : 'ConfigItem$principalCombo',
				width : 200,
				style : '',
				readOnly:true,
				hideTrigger : true,
				fieldLabel : '负责人',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				typeAhead : true,
				name : 'ConfigItem$principal',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['ConfigItem$principal'] == undefined) {
								opt.params['userName'] = Ext
										.getCmp('ConfigItem$principalCombo').defaultParam;
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
								userName : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('ConfigItem$principalCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('ConfigItem$principalCombo')
									.setValue(Ext
											.getCmp('ConfigItem$principalCombo')
											.getValue());
						}
					});
				}
			}), {
				html : '计划使用日期:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.DateField({
				xtype : 'datefield',
				id : 'ConfigItem$buyDate',
				colspan : 0,
				rowspan : 0,
				readOnly:true,
				name : 'ConfigItem$buyDate',
				width : 200,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '计划使用日期'
			}), {
				html : '使用日期:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.DateField({
				xtype : 'datefield',
				id : 'ConfigItem$useDate',
				colspan : 0,
				rowspan : 0,
				name : 'ConfigItem$useDate',
				width : 200,
				readOnly:true,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '使用日期'
			}), {
				html : '预计停止日期:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.DateField({
				xtype : 'datefield',
				id : 'ConfigItem$preStopDate',
				colspan : 0,
				rowspan : 0,
				name : 'ConfigItem$preStopDate',
				width : 200,
				readOnly:true,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '预计停止日期'
			}), {
				html : '实际停止日期:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.DateField({
				xtype : 'datefield',
				id : 'ConfigItem$acutalStopDate',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				name : 'ConfigItem$acutalStopDate',
				width : 200,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '实际停止日期'
			}), {
				html : '配置项状态:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'ConfigItem$configItemStatus',
				id : 'ConfigItem$configItemStatusCombo',
				width : 200,
				style : '',
				fieldLabel : '配置项状态',
				readOnly:true,
				hideTrigger : true,
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'ConfigItem$configItemStatus',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.entity.ConfigItemStatus',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['ConfigItem$configItemStatus'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('ConfigItem$configItemStatusCombo').defaultParam;
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
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('ConfigItem$configItemStatusCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('ConfigItem$configItemStatusCombo')
									.setValue(Ext
											.getCmp('ConfigItem$configItemStatusCombo')
											.getValue());
						}
					});
				}
			}), {
				html : '所属环境:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'ConfigItem$environment',
				id : 'ConfigItem$environmentCombo',
				width : 200,
				style : '',
				fieldLabel : '所属环境',
				readOnly:true,
				hideTrigger : true,
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'ConfigItem$environment',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.entity.Environment',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['ConfigItem$environment'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('ConfigItem$environmentCombo').defaultParam;
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
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('ConfigItem$environmentCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('ConfigItem$environmentCombo')
									.setValue(Ext
											.getCmp('ConfigItem$environmentCombo')
											.getValue());
						}
					});
				}
			}), {
				html : '租赁设备标识:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				id : 'ConfigItem$tenancyFlagCombo',
				style : '',
				readOnly:true,
				hideTrigger : true,
				mode : 'local',
				hiddenName : 'ConfigItem$tenancyFlag',
				colspan : 0,
				rowspan : 0,
				triggerAction : 'all',
				typeAhead : true,
				forceSelection : true,
				allowBlank : false,
				store : new Ext.data.SimpleStore({
					fields : ['id', 'name'],
					data : [['1', '是'], ['0', '否']]
				}),
				emptyText : '请选择...',
				valueField : 'id',
				value : '',
				displayField : 'name',
				name : 'ConfigItem$tenancyFlag',
				width : 200,
				fieldLabel : '租赁设备标识',
				listeners : {
					'expand' : function(combo) {
						combo.reset();
					}
				}
			}), {
				html : '备注:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'ConfigItem$descn',
				colspan : 0,
				rowspan : 0,
				name : 'ConfigItem$descn',
				width : 200,
				readOnly:true,
				height : null,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				fieldLabel : '备注'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'ConfigItem$id',
				colspan : 0,
				rowspan : 0,
				name : 'ConfigItem$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '自动编号'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'ConfigItem$status',
				colspan : 0,
				rowspan : 0,
				name : 'ConfigItem$status',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '状态'
			})]
		});
		if (id != "0") {
			this.formconfigItemBasicPanel.load({
				url : webContext
						+ '/extjs/panelData?method=findPanelDataById&panelName=configItemBasicPanel&dataId='
						+ id,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("ConfigItem$configItemTypeCombo").initComponent();
					Ext.getCmp("ConfigItem$customerTypeCombo").initComponent();
					Ext.getCmp("ConfigItem$customerCombo").initComponent();
					Ext.getCmp("ConfigItem$ownerCombo").initComponent();
					Ext.getCmp("ConfigItem$principalCombo").initComponent();
					Ext.getCmp("ConfigItem$configItemStatusCombo").initComponent();
					Ext.getCmp("ConfigItem$environmentCombo").initComponent();
				}
			});
		}
		return this.formconfigItemBasicPanel;
	},
	getFormpanel_ServerPanel : function(id) {
		this.formpanel_ServerPanel = new Ext.form.FormPanel({
			id : 'panel_ServerPanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
//			collapsible : true,
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
				name : 'Server$id',
				mapping : 'Server$id'
			}, {
				name : 'Server$serverType',
				mapping : 'Server$serverType'
			}, {
				name : 'Server$brand',
				mapping : 'Server$brand'
			}, {
				name : 'Server$model',
				mapping : 'Server$model'
			}, {
				name : 'Server$powerConsumption',
				mapping : 'Server$powerConsumption'
			}, {
				name : 'Server$serverName',
				mapping : 'Server$serverName'
			}, {
				name : 'Server$sn',
				mapping : 'Server$sn'
			}, {
				name : 'Server$cpuType',
				mapping : 'Server$cpuType'
			}, {
				name : 'Server$cpuNumber',
				mapping : 'Server$cpuNumber'
			}, {
				name : 'Server$ram',
				mapping : 'Server$ram'
			}, {
				name : 'Server$internalDriver',
				mapping : 'Server$internalDriver'
			}, {
				name : 'Server$nicNumber',
				mapping : 'Server$nicNumber'
			}, {
				name : 'Server$fcPortNumber',
				mapping : 'Server$fcPortNumber'
			}, {
				name : 'Server$ipAddress1',
				mapping : 'Server$ipAddress1'
			}, {
				name : 'Server$ipAddress2',
				mapping : 'Server$ipAddress2'
			}, {
				name : 'Server$ipAddress3',
				mapping : 'Server$ipAddress3'
			}, {
				name : 'Server$mac1',
				mapping : 'Server$mac1'
			}, {
				name : 'Server$mac2',
				mapping : 'Server$mac2'
			}, {
				name : 'Server$mac3',
				mapping : 'Server$mac3'
			}, {
				name : 'Server$diskRaidType',
				mapping : 'Server$diskRaidType'
			}, {
				name : 'Server$createUser',
				mapping : 'Server$createUser'
			}, {
				name : 'Server$createDate',
				mapping : 'Server$createDate'
			}, {
				name : 'Server$modifyUser',
				mapping : 'Server$modifyUser'
			}, {
				name : 'Server$modifyDate',
				mapping : 'Server$modifyDate'
			}]),
			title : "物理服务器",
			items : [{
				html : '服务器类型:',
				cls : 'common-text',
				style : 'width:135;text-align:right'				
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Server$serverType',
				id : 'Server$serverTypeCombo',
				width : 200,
				readOnly:true,
				hideTrigger : true,
				style : '',
				fieldLabel : '服务器类型',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				typeAhead : true,
				name : 'Server$serverType',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.ServerType',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Server$serverType'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('Server$serverTypeCombo').defaultParam;
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
								name : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Server$serverTypeCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Server$serverTypeCombo').setValue(Ext
									.getCmp('Server$serverTypeCombo')
									.getValue());
						}
					});
				}
			}), {
				html : '品牌:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '品牌',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				readOnly:true,
				id : 'Server$brand',
				name : 'Server$brand',
				style : '',
				width : 200,
				value : '',
				allowBlank : false,
				validator : '',
				vtype : ''
			}), {
				html : '型号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '型号',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'Server$model',
				name : 'Server$model',
				style : '',
				width : 200,
				value : '',
				readOnly:true,
				allowBlank : false,
				validator : '',
				vtype : ''
			}), {
				html : '功耗:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '功耗',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'Server$powerConsumption',
				name : 'Server$powerConsumption',
				style : '',
				width : 200,
				readOnly:true,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '服务器名称:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '服务器名称',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				id : 'Server$serverName',
				name : 'Server$serverName',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '序列号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '序列号',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'Server$sn',
				name : 'Server$sn',
				style : '',
				readOnly:true,
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : 'CPU型号:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'CPU型号',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				id : 'Server$cpuType',
				name : 'Server$cpuType',
				style : '',
				width : 200,
				value : '',
				allowBlank : false,
				validator : '',
				vtype : ''
			}), {
				html : 'CPU个数:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'CPU个数',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				id : 'Server$cpuNumber',
				name : 'Server$cpuNumber',
				style : '',
				width : 200,
				value : '',
				allowBlank : false,
				validator : validate_integer,
				vtype : ''
			}), {
				html : 'RAM大小:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'RAM大小',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'Server$ram',
				name : 'Server$ram',
				style : '',
				readOnly:true,
				width : 200,
				value : '',
				allowBlank : false,
				validator : validate_integer,
				vtype : ''
			}), {
				html : '内置硬盘空间:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '内置硬盘空间',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				id : 'Server$internalDriver',
				name : 'Server$internalDriver',
				style : '',
				width : 200,
				value : '',
				allowBlank : false,
				validator : validate_integer,
				vtype : ''
			}), {
				html : '网口数:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '网口数',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				readOnly:true,
				id : 'Server$nicNumber',
				name : 'Server$nicNumber',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : validate_integer,
				vtype : ''
			}), {
				html : '光纤端口数量:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '光纤端口数量',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				id : 'Server$fcPortNumber',
				name : 'Server$fcPortNumber',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : 'IP地址1:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'IP地址1',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'Server$ipAddress1',
				name : 'Server$ipAddress1',
				readOnly:true,
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : 'IP地址2:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'IP地址2',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				readOnly:true,
				id : 'Server$ipAddress2',
				name : 'Server$ipAddress2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : 'IP地址3:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'IP地址3',
				xtype : 'textfield',
				colspan : 0,
				readOnly:true,
				rowspan : 0,
				id : 'Server$ipAddress3',
				name : 'Server$ipAddress3',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : 'MAC地址1:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'MAC地址1',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'Server$mac1',
				readOnly:true,
				name : 'Server$mac1',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : 'MAC地址2:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'MAC地址2',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'Server$mac2',
				readOnly:true,
				name : 'Server$mac2',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : 'MAC地址3:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'MAC地址3',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				readOnly:true,
				id : 'Server$mac3',
				name : 'Server$mac3',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '主要的RAID类型:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'Server$diskRaidType',
				id : 'Server$diskRaidTypeCombo',
				width : 200,
				style : '',
				readOnly:true,
				hideTrigger : true,
				fieldLabel : '主要的RAID类型',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'type',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				typeAhead : true,
				name : 'Server$diskRaidType',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.DiskRaidtype',
					fields : ['id', 'type'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['Server$diskRaidType'] == undefined) {
								opt.params['type'] = Ext.getCmp('Server$diskRaidTypeCombo').defaultParam;
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
								type : param,
								start : 0
							}
						});
						return true;
					}
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('Server$diskRaidTypeCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('Server$diskRaidTypeCombo').setValue(Ext
									.getCmp('Server$diskRaidTypeCombo')
									.getValue());
						}
					});
				}
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'Server$id',
				colspan : 0,
				rowspan : 0,
				name : 'Server$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '自动编号'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'Server$createUser',
				colspan : 0,
				rowspan : 0,
				name : 'Server$createUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '创建人'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'Server$createDate',
				colspan : 0,
				rowspan : 0,
				name : 'Server$createDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '创建日期'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'Server$modifyUser',
				colspan : 0,
				rowspan : 0,
				name : 'Server$modifyUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '最后修改人'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'Server$modifyDate',
				colspan : 0,
				rowspan : 0,
				name : 'Server$modifyDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '最后修改日期'
			})]
		});
		if (id != "0") {
			this.formpanel_ServerPanel.load({
				url : webContext
						+ '/extjs/panelData?method=findPanelDataById&panelName=panel_ServerPanel&dataId='
						+ id,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("Server$serverTypeCombo").initComponent();
					Ext.getCmp("Server$diskRaidTypeCombo").initComponent();
				}
			});
		}
		return this.formpanel_ServerPanel;
	},
	getServerPanel : function(ciId, serverId) {
		this.getFormconfigItemBasicPanel(ciId);
		this.getFormpanel_ServerPanel(serverId);

		this.serverPanel = new Ext.Panel({
			id : 'serverPanel',
			height : 'auto',
			align : 'center',
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			title : "服务器配置项信息",
			items : [this.formconfigItemBasicPanel, this.formpanel_ServerPanel]
		});
		return this.serverPanel;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var items = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var temp = new Array();
		this.temp = temp;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
		this.model = "serverManageOut1";
//		var buttonUtil = new ButtonUtil();
//		this.mybuttons = buttonUtil.getButtonForModel("serverManageOut1", this);
//		if (this.mybuttons != "") {
//			this.buttons = {
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				style : 'margin:4px 6px 4px 300px',
//				align : 'center',
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				items : this.mybuttons
//			};
//		} else {
//			this.buttons = {
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				style : 'margin:4px 6px 4px 300px',
//				align : 'center',
//				defaults : {
//					bodyStyle : 'padding:4px'
//				}
//			};
//		}
		this.getFormpanel_ServerManageOut_Input();
		this.pa.push(this.formpanel_ServerManageOut_Input);
		this.formname.push("panel_ServerManageOut_Input");
		temp.push(this.formpanel_ServerManageOut_Input);
		var sra = new SRAction();
		var data = sra.getServerManageCiData(this.dataId);
		var ciId = data.ciId;
		var serverId = data.serverId;	
		this.getFormpanel_ServerPanel(serverId);
		temp.push(this.formpanel_ServerPanel);
		this.getFormconfigItemBasicPanel(ciId);		
		temp.push(this.formconfigItemBasicPanel);
//		this.getServerPanel(ciId, serverId);
//		temp.push(this.serverPanel);
		items.push(this.getTabpanel(temp));//modify by zhangzy for 改变panel展现方式 in 2009 11 27
//		items.push(this.buttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
		Ext.getCmp('ServerManage$configItemCombo').on('beforequery',
				function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.load({
						params : {
							cisn : param,
							//configItemType : 77,
							start : 0
						}
					});
					return true;
				});
			
		Ext.getCmp('ServerManage$configItemCombo').addListener('collapse',
				function(box) {
					var newv = box.getValue();
					if (newv != "") {
						var sra = new SRAction();
						var data = sra.getCiDataByCiId(newv);
						var curServerId = data.serverId;
						//alert(newv+"------"+curServerId);
						Ext.getCmp('configItemBasicPanel').load({
							url : webContext
									+ '/extjs/panelData?method=findPanelDataById&panelName=configItemBasicPanel&dataId='
									+ newv,
							timeout : 30,
							success : function(action, form) {
								Ext.getCmp("ConfigItem$configItemTypeCombo").initComponent();
								Ext.getCmp("ConfigItem$customerTypeCombo").initComponent();
								Ext.getCmp("ConfigItem$customerCombo").initComponent();
								Ext.getCmp("ConfigItem$ownerCombo").initComponent();
								Ext.getCmp("ConfigItem$principalCombo").initComponent();
								Ext.getCmp("ConfigItem$configItemStatusCombo").initComponent();
								Ext.getCmp("ConfigItem$environmentCombo").initComponent();
							}
						});
						Ext.getCmp('panel_ServerPanel').load({
							url : webContext
									+ '/extjs/panelData?method=findPanelDataById&panelName=panel_ServerPanel&dataId='
									+ curServerId,
							timeout : 30,
							success : function(action, form) {
								Ext.getCmp("Server$serverTypeCombo").initComponent();
								Ext.getCmp("Server$diskRaidTypeCombo").initComponent();
							}
						});
					}
				});
	}
})