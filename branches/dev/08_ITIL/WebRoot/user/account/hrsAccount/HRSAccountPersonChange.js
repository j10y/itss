PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : true,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 800,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab,
			tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>1.页面中带红色<font color=red>*</font>号的必填项，请在填写完整后再提交申请！</font>')]

		});
		return this.tabPanel;

	},

	getFormpanel_HRSAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		this.formpanel_HRSAccountApply_Input = new Ext.form.FormPanel({
			id : 'panel_HRSAccountApply_Input',
			layout : 'column',
			height : 'auto',
			width : 800,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{
				name : 'HRSAccountApply$id',
				mapping : 'HRSAccountApply$id'
			}, {
				name : 'HRSAccountApply$name',
				mapping : 'HRSAccountApply$name'
			}, {
				name : 'HRSAccountApply$oldApply',
				mapping : 'HRSAccountApply$oldApply'
			}, {
				name : 'HRSAccountApply$processType',
				mapping : 'HRSAccountApply$processType'
			}, {
				name : 'HRSAccountApply$status',
				mapping : 'HRSAccountApply$status'
			}, {
				name : 'HRSAccountApply$deleteFlag',
				mapping : 'HRSAccountApply$deleteFlag'
			}, {
				name : 'HRSAccountApply$serviceItem',
				mapping : 'HRSAccountApply$serviceItem'
			}, {
				name : 'HRSAccountApply$accountName',
				mapping : 'HRSAccountApply$accountName'
			}, {
				name : 'HRSAccountApply$accountState',
				mapping : 'HRSAccountApply$accountState'
			}, {
				name : 'HRSAccountApply$applyReason',
				mapping : 'HRSAccountApply$applyReason'
			}, {
				name : 'HRSAccountApply$isReferMoney',
				mapping : 'HRSAccountApply$isReferMoney'
			}, {
				name : 'HRSAccountApply$userRight',
				mapping : 'HRSAccountApply$userRight'
			}, {
				name : 'HRSAccountApply$operationScope',
				mapping : 'HRSAccountApply$operationScope'
			}, {
				name : 'HRSAccountApply$userOwner',
				mapping : 'HRSAccountApply$userOwner'
			}, {
				name : 'HRSAccountApply$isChangeRight',
				mapping : 'HRSAccountApply$isChangeRight'
			}, {
				name : 'HRSAccountApply$workDuty',
				mapping : 'HRSAccountApply$workDuty'
			}, {
				name : 'HRSAccountApply$applyUser',
				mapping : 'HRSAccountApply$applyUser'
			}, {
				name : 'HRSAccountApply$isChangeRight',
				mapping : 'HRSAccountApply$isChangeRight'
			}, {
				name : 'HRSAccountApply$applyTel',
				mapping : 'HRSAccountApply$applyTel'
			}, {
				name : 'HRSAccountApply$accountManger',
				mapping : 'HRSAccountApply$accountManger'
			}, {
				name : 'HRSAccountApply$createUser',
				mapping : 'HRSAccountApply$createUser'
			}, {
				name : 'sUserInfos$password',
				mapping : 'sUserInfos$password'
			}, {
				name : 'HRSAccountApply$createDate',
				mapping : 'HRSAccountApply$createDate'
			}, {
				name : 'HRSAccountApply$modifyUser',
				mapping : 'HRSAccountApply$modifyUser'
			}, {
				name : 'HRSAccountApply$modifyDate',
				mapping : 'HRSAccountApply$modifyDate'
			}, {
				name : 'sUserInfos$realName',
				mapping : 'sUserInfos$realName'
			}, {
				name : 'sUserInfos$userName',
				mapping : 'sUserInfos$userName'
			}, {
				name : 'sUserInfos$department',
				mapping : 'sUserInfos$department'
			}, {
				name : 'sUserInfos$email',
				mapping : 'sUserInfos$email'
			}, {
				name : 'sUserInfos$telephone',
				mapping : 'sUserInfos$telephone'
			}, {
				name : 'sUserInfos$mobilePhone',
				mapping : 'sUserInfos$mobilePhone'
			}, {
				name : 'sUserInfos$employeeCode',
				mapping : 'sUserInfos$employeeCode'
			}, {
				name : 'sUserInfos$costCenterCode',
				mapping : 'sUserInfos$costCenterCode'
			}, {
				name : 'sUserInfos$sameMailDept',
				mapping : 'sUserInfos$sameMailDept'
			}, {
				name : 'sUserInfos$workSpace',
				mapping : 'sUserInfos$workSpace'
			}, {
				name : 'sUserInfos$mailServer',
				mapping : 'sUserInfos$mailServer'
			}, {
				name : 'sUserInfos$userType',
				mapping : 'sUserInfos$userType'
			}, {
				name : 'sUserInfos$personnelScope',
				mapping : 'sUserInfos$personnelScope'
			}, {
				name : 'sUserInfos$isTemp',
				mapping : 'sUserInfos$isTemp'
			}, {
				name : 'sUserInfos$isAccredited',
				mapping : 'sUserInfos$isAccredited'
			}, {
				name : 'HRSAccountApply$confirmUser',
				mapping : 'HRSAccountApply$confirmUser'
			}, {
				name : 'HRSAccountApply$delegateApplyTel',
				mapping : 'HRSAccountApply$delegateApplyTel'
			}, {
				name : 'HRSAccountApply$delegateApplyUser',
				mapping : 'HRSAccountApply$delegateApplyUser'
			}, {
				name : 'HRSAccountApply$applyDate',
				mapping : 'HRSAccountApply$applyDate'
			}]),
			title : "HRS帐号人员调整申请",
			items : [{
				xtype : 'fieldset',
				title : '申请人信息',
				layout : 'table',
				anchor : '100%',
				autoHeight : true,
				animCollapse : true,
				collapsible : true,
				style : 'border:1px dotted #b0acac',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				items : [{
					html : '申请编号:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '申请编号',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'HRSAccountApply$name',
					name : 'HRSAccountApply$name',
					style : '',
					width : 200,
					value : '',
					readOnly : true,
					emptyText : '自动生成',
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '申请日期:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.DateField({
					xtype : 'datefield',
					id : 'HRSAccountApply$applyDate',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$applyDate',
					width : 200,
					style : '',
					hideTrigger : true,
					readOnly : true,
					value : '',
					allowBlank : true,
					validator : '',
					format : 'Y-m-d',
					fieldLabel : '申请日期'
				}), {
					html : '代申请人:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'HRSAccountApply$delegateApplyUser',
					id : 'HRSAccountApply$delegateApplyUserCombo',
					width : 200,
					style : '',
					fieldLabel : '带申请人',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					hideTrigger : true,
					readOnly : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : true,
					
					name : 'HRSAccountApply$delegateApplyUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['HRSAccountApply$delegateApplyUser'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('HRSAccountApply$delegateApplyUserCombo').defaultParam;
								}
							}
						},
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
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
						if(this.getValue()!=''){
							var combo=this;
							this.store.load({
								params : {
									id : this.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
								}
							});
						}
					}
				}), {
					html : '<font color=red>*</font>代申请人联系电话:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '代申请人联系电话',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'HRSAccountApply$delegateApplyTel',
					name : 'HRSAccountApply$delegateApplyTel',
					style : '',
					width : 200,
					value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}), 
				{
					html : '<font color=red>*</font>申请人:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'HRSAccountApply$applyUser',
					id : 'HRSAccountApply$applyUserCombo',
					width : 200,
					style : '',
					fieldLabel : '申请人',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请输入ITCODE进行选择...',
					allowBlank : false,
					
					name : 'HRSAccountApply$applyUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['HRSAccountApply$applyUser'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('HRSAccountApply$applyUserCombo').defaultParam;
								}
							}
						},
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
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
						},
						'select' : function(combo, record, index) {
							var userInfo = Ext
									.getCmp('HRSAccountApply$applyUserCombo')
									.getValue();
						    	if (userInfo == '无') {
										Ext.MessageBox.alert("提示",
												"请您选择具体的申请人,谢谢您的合作！",
												function(btn) {
													Ext
															.getCmp('HRSAccountApply$applyUserCombo')
															.setValue("");
												});
									}
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
								},

								success : function(response, options) {
									var r = Ext.decode(response.responseText);

									if (!r.success) {

									} else {

										Ext.getCmp('sUserInfos$costCenterCode')
												.setValue(r.costCenter);
										Ext.getCmp('sUserInfos$employeeCode')
												.setValue(r.employeeCode);
										Ext.getCmp('sUserInfos$userTypeCombo')
												.setValue(r.userType);
										Ext.getCmp('HRSAccountApply$applyTel')
												.setValue(r.telephone);

									}
								},

								failure : function(response, options) {

								}
							}, this);

						}
					},
					initComponent : function() {
						if(this.getValue()!=''){
							var combo=this;
							this.store.load({
								params : {
									id : this.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
								}
							});
						}
					}
				}), {
					html : '<font color=red>*</font>申请人联系电话:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '申请人联系电话',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'HRSAccountApply$applyTel',
					name : 'HRSAccountApply$applyTel',
					style : '',
					width : 200,
					value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}), 
					 {
					html : '员工编号:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '员工编号',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'sUserInfos$employeeCode',
					name : 'sUserInfos$employeeCode',
					style : '',
					width : 200,
					readOnly : true,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '成本中心号:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '成本中心号',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'sUserInfos$costCenterCode',
					name : 'sUserInfos$costCenterCode',
					style : '',
					width : 200,
					readOnly : true,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '用户类别/员工组:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'sUserInfos$userType',
					id : 'sUserInfos$userTypeCombo',
					width : 200,
					style : '',
					fieldLabel : '用户类型',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					readOnly : true,
					hideTrigger : true,
					displayField : 'name',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : true,
					
					name : 'sUserInfos$userType',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserType',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['sUserInfos$userType'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('sUserInfos$userTypeCombo').defaultParam;
								}
							}
						},
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
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
								id : Ext.getCmp('sUserInfos$userTypeCombo')
										.getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext
										.getCmp('sUserInfos$userTypeCombo')
										.setValue(Ext
												.getCmp('sUserInfos$userTypeCombo')
												.getValue());
							}
						});
					}
				}), 
				{
					html : '<font color=red>*</font>帐号名称:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'HRSAccountApply$accountName',
					id : 'HRSAccountApply$accountNameCombo',
					width : 200,
					style : '',
					fieldLabel : '帐号名称',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'accountName',
					valueField : 'accountName',
					emptyText : '请选择...',
					allowBlank : false,
					editable : false,
                    name : 'HRSAccountApply$accountName',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ "/accountAction_listHRSAccountName.action",
						fields : ['id', 'accountName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['HRSAccountApply$accountName'] == undefined) {
									opt.params['accountName'] = Ext
											.getCmp('HRSAccountApply$accountNameCombo').defaultParam;
								}
							}
						},

						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
							var param = queryEvent.combo.getRawValue();
							this.defaultParam = param;
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.load({
								params : {
									accountName : param,
									start : 0
								}
							});
							return true;
						},
						'select' : function(combo, record, index) {
							var accountName = Ext
									.getCmp('HRSAccountApply$accountNameCombo')
									.getRawValue();
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initHRSRightData.action',
								params : {
									accountName : accountName
								},

								success : function(response, options) {
									var r = Ext.decode(response.responseText);

									if (!r.success) {

									} else {

										Ext.getCmp('operationScopeCombo')
												.setValue(r.os);
										Ext.getCmp('isReferMoneyCombo')
												.setValue(r.referMoney);
										Ext.getCmp('userRightCombo')
												.setValue(r.hr);

									}
								},

								failure : function(response, options) {

								}
							}, this);

						}
					},
					initComponent : function() {
						if(this.getValue()!=''){
							var combo=this;
							this.store.load({
								params : {
									id : this.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
								}
							});
						}
					}
				}),
					{
					html : '<font color=red>*</font>帐号审批人:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'HRSAccountApply$accountManger',
					id : 'HRSAccountApply$accountMangerCombo',
					width : 200,
					style : '',
					fieldLabel : '帐号审批人',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : false,
					
					name : 'HRSAccountApply$accountManger',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.HRSAccountManger',
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['HRSAccountApply$accountManger'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('HRSAccountApply$accountMangerCombo').defaultParam;
								}
							}
						},
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
							var param = queryEvent.combo.getRawValue();
							this.defaultParam = param;
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.load({
								params : {
									itcode : param,
									start : 0
								}
							});
							return true;
						}
					},
					initComponent : function() {
						if(this.getValue()!=''){
							var combo=this;
							this.store.load({
								params : {
									id : this.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
								}
							});
						}
					}
				})]
			},

			{
				xtype : 'fieldset',
				title : '申请帐号信息',
				layout : 'table',
				anchor : '100%',
				animCollapse : true,
				collapsible : true,
				style : 'border:1px dotted #b0acac;margin:15px 0px 0px px',
				autoHeight : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				items : [

				{
					html : '<font color=red>*</font>申请原因:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'HRSAccountApply$applyReason',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$applyReason',
					width : 200,

					style : '',
					value : '',
					allowBlank : false,
					validator : '',
					fieldLabel : '申请原因'
				}), {
					html : '<font color=red>*</font>工作职责:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'HRSAccountApply$workDuty',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$workDuty',
					width : 200,

					style : '',
					value : '',
					allowBlank : false,
					validator : '',
					fieldLabel : '工作职责'
				}), {
					html : '是否调整权限:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					id : 'HRSAccountApply$isChangeRightCombo',
					style : '',
					mode : 'local',
					hiddenName : 'HRSAccountApply$isChangeRight',
					colspan : 0,
					rowspan : 0,
					triggerAction : 'all',
					editable:false,
					forceSelection : true,
					allowBlank : true,
					store : new Ext.data.SimpleStore({
						fields : ['id', 'name'],
						data : [['1', '是'], ['0', '否']]
					}),
					emptyText : '请选择...',
					valueField : 'id',
					value : '',
					displayField : 'name',
					name : 'HRSAccountApply$isChangeRight',
					width : 200,
					fieldLabel : '是否涉及薪酬',
					listeners : {
						'expand' : function(combo) {
							combo.reset();
						},
						'select' : function(combo) {
							var result = Ext
									.getCmp('HRSAccountApply$isChangeRightCombo')
									.getValue();

							if (result == 1) {
								Ext.getCmp('newRight').expand(true);
								Ext.getCmp('HRSAccountApply$userRightCombo').allowBlank = false;
								Ext
										.getCmp('HRSAccountApply$operationScopeCombo').allowBlank = false;
							} else {
								Ext.getCmp('newRight').collapse(true);
								Ext.getCmp('HRSAccountApply$userRightCombo').allowBlank = true;
								Ext
										.getCmp('HRSAccountApply$operationScopeCombo').allowBlank = true;
							}

						}
					}
				}), {}, {},

				{
					xtype : 'fieldset',
					id : 'newRight',
					layout : 'table',
					anchor : '100%',
					colspan : 4,
					rowspan : 0,
					collapsed : true,
					animCollapse : false,
					collapsible : false,
					autoHeight : true,
					style : 'border:1px dotted #b0acac;margin:15px 0px 0px 25px',
					autoHeight : true,
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layoutConfig : {
						columns : 4
					},
					items : [{
						html : '原用户权限:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'userRight',
						id : 'userRightCombo',
						width : 200,
						style : '',
						fieldLabel : '用户权限',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						hideTrigger : true,
						readOnly : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '请选择...',
						allowBlank : false,
						editable:false,
						name : 'userRight',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.HRSUserRight',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['userRight'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('userRightCombo').defaultParam;
									}
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
						}),
						pageSize : 10,
						listeners : {
							'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
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
									id : Ext.getCmp('userRightCombo')
											.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									Ext.getCmp('userRightCombo').setValue(Ext
											.getCmp('userRightCombo')
											.getValue());
								}
							});
						}
					}), {
						html : '操作范围:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'operationScope',
						id : 'operationScopeCombo',
						width : 200,
						style : '',
						fieldLabel : '操作范围',
						colspan : 0,
						rowspan : 0,
						hideTrigger : true,
						readOnly : true,
						lazyRender : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '请选择...',
						allowBlank : false,
						editable:false,
						name : 'operationScope',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.HRSOperationScope',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['operationScope'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('operationScopeCombo').defaultParam;
									}
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
						}),
						pageSize : 10,
						listeners : {
							'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
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
									id : Ext.getCmp('operationScopeCombo')
											.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									Ext
											.getCmp('operationScopeCombo')
											.setValue(Ext
													.getCmp('operationScopeCombo')
													.getValue());
								}
							});
						}
					}), {
						html : '是否涉及薪酬:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						id : 'isReferMoneyCombo',
						style : '',
						mode : 'local',
						hideTrigger : true,
						readOnly : true,
						hiddenName : 'isReferMoney',
						colspan : 0,
						rowspan : 0,
						triggerAction : 'all',
						editable:false,
						forceSelection : true,
						allowBlank : true,
						store : new Ext.data.SimpleStore({
							fields : ['id', 'name'],
							data : [['1', '是'], ['0', '否']]
						}),
						emptyText : '请选择...',
						valueField : 'id',
						value : '',
						displayField : 'name',
						name : 'isReferMoney',
						width : 200,
						fieldLabel : '是否涉及薪酬',
						listeners : {
							'expand' : function(combo) {
								combo.reset();
							}
						}
					}), {
						html : '<font color=red>*</font>新用户权限:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'HRSAccountApply$userRight',
						id : 'HRSAccountApply$userRightCombo',
						width : 200,
						style : '',
						fieldLabel : '用户权限',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '请选择...',
						allowBlank : true,
						editable:false,
						name : 'HRSAccountApply$userRight',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.HRSUserRight',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['HRSAccountApply$userRight'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('HRSAccountApply$userRightCombo').defaultParam;
									}
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
						}),
						pageSize : 10,
						listeners : {
							'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
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
									id : Ext
											.getCmp('HRSAccountApply$userRightCombo')
											.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									Ext
											.getCmp('HRSAccountApply$userRightCombo')
											.setValue(Ext
													.getCmp('HRSAccountApply$userRightCombo')
													.getValue());
								}
							});
						}
					}), {
						html : '<font color=red>*</font>新操作范围:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'HRSAccountApply$operationScope',
						id : 'HRSAccountApply$operationScopeCombo',
						width : 200,
						style : '',
						fieldLabel : '操作范围',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '请选择...',
						allowBlank : true,
						editable:false,
						name : 'HRSAccountApply$operationScope',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.HRSOperationScope',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['HRSAccountApply$operationScope'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('HRSAccountApply$operationScopeCombo').defaultParam;
									}
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
						}),
						pageSize : 10,
						listeners : {
							'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
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
									id : Ext
											.getCmp('HRSAccountApply$operationScopeCombo')
											.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									Ext
											.getCmp('HRSAccountApply$operationScopeCombo')
											.setValue(Ext
													.getCmp('HRSAccountApply$operationScopeCombo')
													.getValue());
								}
							});
						}
					}), {
						html : '是否涉及薪酬:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						id : 'HRSAccountApply$isReferMoneyCombo',
						style : '',
						mode : 'local',
						hiddenName : 'HRSAccountApply$isReferMoney',
						colspan : 0,
						rowspan : 0,
						triggerAction : 'all',
						editable:false,
						forceSelection : true,
						allowBlank : true,
						store : new Ext.data.SimpleStore({
							fields : ['id', 'name'],
							data : [['1', '是'], ['0', '否']]
						}),
						emptyText : '请选择...',
						valueField : 'id',
						value : '',
						displayField : 'name',
						name : 'HRSAccountApply$isReferMoney',
						width : 200,
						fieldLabel : '是否涉及薪酬',
						listeners : {
							'expand' : function(combo) {
								combo.reset();
							}
						}
					})]
				}, new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$id',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '自动编号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$oldApply',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$oldApply',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '变更前申请'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$processType',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$processType',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '流程类型'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$status',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$status',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '状态'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$deleteFlag',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$deleteFlag',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '删除状态'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$serviceItem',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$serviceItem',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '所属服务'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$accountState',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$accountState',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '帐号状态'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$createUser',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$createUser',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '创建人'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$modifyUser',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$modifyUser',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '最后修改人'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'HRSAccountApply$modifyDate',
					colspan : 0,
					rowspan : 0,
					name : 'HRSAccountApply$modifyDate',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '最后修改日期'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'sUserInfos$isTemp',
					colspan : 0,
					rowspan : 0,
					name : 'sUserInfos$isTemp',
					width : 200,
					style : '',
					value : '',
					fieldLabel : 'isTemp'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'sUserInfos$isAccredited',
					colspan : 0,
					rowspan : 0,
					name : 'sUserInfos$isAccredited',
					width : 200,
					style : '',
					value : '',
					fieldLabel : 'isAccredited'
				})]
			}],

			buttons : [{
				text : '保存为草稿',
				iconCls : 'save',
				id : 'save',
				handler : function() {
					if (!Ext.getCmp('panel_HRSAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var accountName = Ext
							.getCmp("HRSAccountApply$accountNameCombo")
							.getValue();
					var info = Ext.encode(getFormParam('panel_HRSAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveHRSDeleteDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							accountName : accountName,
							panelName : 'panel_HRSAccountApply_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_HRSAccountApply_Input').load({
								url : webContext
										+ '/accountAction_getHRSApplyDraftData.action?panelName=panel_HRSAccountApply_Input&dataId='
										+ curId,
								timeout : 30,
								success : function(action, form) {
								}
							});
							Ext.MessageBox.alert("提示", "保存成功", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
							});
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示","保存失败,请检查申请人是否选择正确！");
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();
						}
					}, this);
				}
			}, {
				text : '提交申请',
				iconCls : 'submit',
				id : 'submit',
				handler : function() {
					if (!Ext.getCmp('panel_HRSAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					var applyUser = Ext
							.getCmp('HRSAccountApply$applyUserCombo')
							.getRawValue();
					var start = applyUser.indexOf("/");
					var end = applyUser.lastIndexOf("/");
					var tmp = applyUser.substring(start + 1, end);
					var confirmUser = Ext
							.getCmp('HRSAccountApply$accountMangerCombo')
							.getRawValue();
					var itcodeNum = confirmUser.indexOf("/");
					var itcode = confirmUser.substring(0, itcodeNum);

					if (tmp == itcode) {
						Ext.MessageBox.alert("提示", "申请人不能和审批人相同,请确认后再提交申请！");
						return false;
					}

					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var accountName = Ext
							.getCmp("HRSAccountApply$accountNameCombo")
							.getValue();
					var info = Ext.encode(getFormParam('panel_HRSAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveHRSDeleteDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							accountName : accountName,
							panelName : 'panel_HRSAccountApply_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
							var userInfo = Ext
									.getCmp('HRSAccountApply$applyUserCombo')
									.getValue();

							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_applyHrs.action',
								params : {
									dataId : curId,
									userInfo : userInfo,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject',"
											+ "applyTypeName: 'HRS帐号人员调整申请',"
											+ "customer:'',serviceItemId:'"
											+ curscid + "'}",
									defname : pName
								},
								success : function(response, options) {
									Ext.Msg.alert("提示", "提交申请成功", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
									});
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("提示", "提交申请失败,请检查审批人是否选择正确!");
									Ext.getCmp("save").enable();
									Ext.getCmp("submit").enable();
									Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "提交申请失败,请检查申请人是否选择正确!");
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();
						}
					}, this);
				}
			}, {
				text : '返回',
				iconCls : 'back',
				id : 'back',
				handler : function() {
					window.history.back(-1);
				}
			}],
			buttonAlign:'center'

		});
		if (this.dataId == "0" || this.dataId == "null") {

			this.formpanel_HRSAccountApply_Input.load({
				url : webContext
						+ '/accountAction_initPersonAccountDeleteData.action',
				params : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_HRSAccountApply_Input'
				},
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("HRSAccountApply$applyUserCombo")
							.initComponent();
					Ext.getCmp("HRSAccountApply$delegateApplyUserCombo")
							.initComponent();

				},
				failure : function(response, options) {

				}
			});
		} else {
			this.formpanel_HRSAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getHRSApplyDraftData.action?panelName=panel_HRSAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					var accountName = Ext
							.getCmp('HRSAccountApply$accountNameCombo')
							.getRawValue();
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_initHRSRightData.action',
						params : {
							accountName : accountName
						},

						success : function(response, options) {
							var r = Ext.decode(response.responseText);

							if (!r.success) {

							} else {

								Ext.getCmp('operationScopeCombo')
										.setValue(r.os);
								Ext.getCmp('isReferMoneyCombo')
										.setValue(r.referMoney);
								Ext.getCmp('userRightCombo').setValue(r.hr);

								Ext.getCmp("HRSAccountApply$applyUserCombo")
										.initComponent();
								Ext
										.getCmp("HRSAccountApply$delegateApplyUserCombo")
										.initComponent();

							}
						},

						failure : function(response, options) {

						}
					}, this);

					Ext.getCmp("HRSAccountApply$applyUserCombo")
							.initComponent();
					Ext.getCmp("HRSAccountApply$delegateApplyUserCombo")
							.initComponent();

				}
			});

		}
		return this.formpanel_HRSAccountApply_Input;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.HRSAccountApply"
		});
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
		this.model = "ar_HRSAccountApply";

		this.getFormpanel_HRSAccountApply_Input();
		this.pa.push(this.formpanel_HRSAccountApply_Input);
		this.formname.push("panel_HRSAccountApply_Input");
		temp.push(this.formpanel_HRSAccountApply_Input);
		if(this.dataId != "0" &&this.dataId != "null"){
			temp.push(histroyForm);
		}
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
		if(this.status!=0){
			Ext.getCmp("submit").hide();
			Ext.getCmp("save").hide();

		}
		if (this.readOnly == 1) {
			Ext.getCmp("back").hide();
		}

	}
})