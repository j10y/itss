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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>1.页面中带红色<font color=red>*</font>号的必填项，请在填写完整后再提交申请！ <br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2.请离职人员在<font color=red>主页</font>→<font color=red>"人力资源管理流程"</font>→<font color=red>提交"调离"申请后</font >,再进行IT帐号注销申请</font>')]

		});
		return this.tabPanel;

	},

	getFormpanel_PersonAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		this.formpanel_PersonAccountApply_Input = new Ext.form.FormPanel({
			id : 'panel_PersonAccountApply_Input',
			layout : 'column',
			height : 'auto',
			width : 800,
			frame : true,
			//collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{
				name : 'AccountApplyMainTable$createUser',
				mapping : 'AccountApplyMainTable$createUser'
			}, {
				name : 'AccountApplyMainTable$createDate',
				mapping : 'AccountApplyMainTable$createDate'
			}, {
				name : 'AccountApplyMainTable$modifyUser',
				mapping : 'AccountApplyMainTable$modifyUser'
			}, {
				name : 'AccountApplyMainTable$modifyDate',
				mapping : 'AccountApplyMainTable$modifyDate'
			}, {
				name : 'AccountApplyMainTable$id',
				mapping : 'AccountApplyMainTable$id'
			}, {
				name : 'AccountApplyMainTable$name',
				mapping : 'AccountApplyMainTable$name'
			}, {
				name : 'AccountApplyMainTable$oldApply',
				mapping : 'AccountApplyMainTable$oldApply'
			}, {
				name : 'AccountApplyMainTable$processType',
				mapping : 'AccountApplyMainTable$processType'
			}, {
				name : 'AccountApplyMainTable$status',
				mapping : 'AccountApplyMainTable$status'
			}, {
				name : 'AccountApplyMainTable$deleteFlag',
				mapping : 'AccountApplyMainTable$deleteFlag'
			}, {
				name : 'AccountApplyMainTable$serviceItem',
				mapping : 'AccountApplyMainTable$serviceItem'
			}, {
				name : 'AccountApplyMainTable$applyDate',
				mapping : 'AccountApplyMainTable$applyDate'
			}, {
				name : 'AccountApplyMainTable$applyUser',
				mapping : 'AccountApplyMainTable$applyUser'
			}, {
				name : 'AccountApplyMainTable$delegateApplyUser',
				mapping : 'AccountApplyMainTable$delegateApplyUser'
			}, {
				name : 'AccountApplyMainTable$applyUserTel',
				mapping : 'AccountApplyMainTable$applyUserTel'
			}, {
				name : 'AccountApplyMainTable$delegateApplyTel',
				mapping : 'AccountApplyMainTable$delegateApplyTel'
			}, {
				name : 'AccountApplyMainTable$attachment',
				mapping : 'AccountApplyMainTable$attachment'
			}, {
				name : 'AccountApplyMainTable$confirmUser',
				mapping : 'AccountApplyMainTable$confirmUser'
			}, {
				name : 'itil_ac_PersonFormalAccount$olodApplyAccount',
				mapping : 'itil_ac_PersonFormalAccount$olodApplyAccount'
			}, {
				name : 'itil_ac_PersonFormalAccount$applyId',
				mapping : 'itil_ac_PersonFormalAccount$applyId'
			}, {
				name : 'itil_ac_PersonFormalAccount$id',
				mapping : 'itil_ac_PersonFormalAccount$id'
			}, {
				name : 'itil_ac_PersonFormalAccount$accountName',
				mapping : 'itil_ac_PersonFormalAccount$accountName'
			}, {
				name : 'itil_ac_PersonFormalAccount$password',
				mapping : 'itil_ac_PersonFormalAccount$password'
			}, {
				name : 'itil_ac_PersonFormalAccount$accountType',
				mapping : 'itil_ac_PersonFormalAccount$accountType'
			}, {
				name : 'itil_ac_PersonFormalAccount$accountowner',
				mapping : 'itil_ac_PersonFormalAccount$accountowner'
			}, {
				name : 'itil_ac_PersonFormalAccount$accountState',
				mapping : 'itil_ac_PersonFormalAccount$accountState'
			}, {
				name : 'itil_ac_PersonFormalAccount$createDate',
				mapping : 'itil_ac_PersonFormalAccount$createDate'
			}, {
				name : 'itil_ac_PersonFormalAccount$registerServiceRightDesc',
				mapping : 'itil_ac_PersonFormalAccount$registerServiceRightDesc'
			}, {
				name : 'itil_ac_PersonFormalAccount$sameRightAccount',
				mapping : 'itil_ac_PersonFormalAccount$sameRightAccount'
			}, {
				name : 'itil_ac_PersonFormalAccount$rightsDesc',
				mapping : 'itil_ac_PersonFormalAccount$rightsDesc'
			}, {
				name : 'itil_ac_PersonFormalAccount$remarkDesc',
				mapping : 'itil_ac_PersonFormalAccount$remarkDesc'
			}, {
				name : 'itil_ac_PersonFormalAccount$attachment',
				mapping : 'itil_ac_PersonFormalAccount$attachment'
			}, {
				name : 'itil_ac_PersonFormalAccount$applyReason',
				mapping : 'itil_ac_PersonFormalAccount$applyReason'
			}, {
				name : 'itil_ac_PersonFormalAccount$confirmUser',
				mapping : 'itil_ac_PersonFormalAccount$confirmUser'
			}, {
				name : 'itil_ac_PersonFormalAccount$mailValue',
				mapping : 'itil_ac_PersonFormalAccount$mailValue'
			}, {
				name : 'itil_ac_PersonFormalAccount$wwwAccountValue',
				mapping : 'itil_ac_PersonFormalAccount$wwwAccountValue'
			}, {
				name : 'itil_ac_PersonFormalAccount$referSalary',
				mapping : 'itil_ac_PersonFormalAccount$referSalary'
			}, {
				name : 'itil_ac_PersonFormalAccount$telephone',
				mapping : 'itil_ac_PersonFormalAccount$telephone'
			}, {
				name : 'itil_ac_PersonFormalAccount$registerServiceType',
				mapping : 'itil_ac_PersonFormalAccount$registerServiceType'
			}, {
				name : 'itil_ac_PersonFormalAccount$dutyName',
				mapping : 'itil_ac_PersonFormalAccount$dutyName'
			}, {
				name : 'itil_ac_PersonFormalAccount$thingCode',
				mapping : 'itil_ac_PersonFormalAccount$thingCode'
			}, {
				name : 'itil_ac_PersonFormalAccount$controlScope',
				mapping : 'itil_ac_PersonFormalAccount$controlScope'
			}, {
				name : 'itil_ac_PersonFormalAccount$userRight',
				mapping : 'itil_ac_PersonFormalAccount$userRight'
			}, {
				name : 'itil_ac_PersonFormalAccount$operatorScope',
				mapping : 'itil_ac_PersonFormalAccount$operatorScope'
			}, {
				name : 'itil_ac_PersonFormalAccount$erpUserName',
				mapping : 'itil_ac_PersonFormalAccount$erpUserName'
			}, {
				name : 'itil_ac_PersonFormalAccount$workSpace',
				mapping : 'itil_ac_PersonFormalAccount$workSpace'
			}, {
				name : 'itil_ac_PersonFormalAccount$mailServer',
				mapping : 'itil_ac_PersonFormalAccount$mailServer'
			}, {
				name : 'itil_ac_PersonFormalAccount$endDate',
				mapping : 'itil_ac_PersonFormalAccount$endDate'
			}, {
				name : 'itil_ac_PersonFormalAccount$otherLinkCompany',
				mapping : 'itil_ac_PersonFormalAccount$otherLinkCompany'
			}, {
				name : 'itil_ac_PersonFormalAccount$drawSpace',
				mapping : 'itil_ac_PersonFormalAccount$drawSpace'
			}, {
				name : 'itil_ac_PersonFormalAccount$ifHold',
				mapping : 'itil_ac_PersonFormalAccount$ifHold'
			}, {
				name : 'sUserInfos$password',
				mapping : 'sUserInfos$password'
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
			}]),
			title : "员工离职帐号注销申请",
			items : [{
				xtype : 'fieldset',
				title : '申请人基本信息',
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
					id : 'AccountApplyMainTable$name',
					name : 'AccountApplyMainTable$name',
					style : '',
					width : 200,
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
					id : 'AccountApplyMainTable$applyDate',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$applyDate',
					width : 200,
					style : '',
					value : '',
					allowBlank : true,
					hideTrigger : true,
					readOnly : true,
					validator : '',
					format : 'Y-m-d',
					fieldLabel : '申请日期'
				}), {
					html : '代申请人:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$delegateApplyUser',
					id : 'AccountApplyMainTable$delegateApplyUserCombo',
					width : 200,
					style : '',
					fieldLabel : '代申请人',
					colspan : 0,
					rowspan : 0,
					readOnly : true,

					hideTrigger : true,
					readOnly : true,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : true,
					
					name : 'AccountApplyMainTable$delegateApplyUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['AccountApplyMainTable$delegateApplyUser'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('AccountApplyMainTable$delegateApplyUserCombo').defaultParam;
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
					fieldLabel : '<font color=red>*</font>代办人联系电话',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'AccountApplyMainTable$delegateApplyTel',
					name : 'AccountApplyMainTable$delegateApplyTel',
					style : '',
					width : 200,
					value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}), {
					html : '申请人:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$applyUser',
					id : 'AccountApplyMainTable$applyUserCombo',
					width : 200,
					style : '',
					fieldLabel : '申请人',
					colspan : 0,
					rowspan : 0,
					listWidth:500,//add by liuying at 20100507
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请输入ITCODE进行选择...',
					allowBlank : false,
					name : 'AccountApplyMainTable$applyUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ "/actorUtilAction_getAllUserForCombo.action",
						
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['AccountApplyMainTable$applyUser'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('AccountApplyMainTable$applyUserCombo').defaultParam;
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
									.getCmp('AccountApplyMainTable$applyUserCombo')
									.getValue();
								Ext.Ajax.request({
								url : webContext
										+ '/accountAction_isAppAdministrator.action',
								params : {
									userInfo : userInfo
								},
								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									if (r.success) {
										Ext.MessageBox.alert("IT温馨提示","申请人为系统及应用负责人,请联系相关管理员处理后再提交该申请！");
										Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue('');
										return false;
									}
						     else{
						     	
						     	Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getUserAllSpecailAccount.action',
								params : {
									userInfo : userInfo
								},
								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									if (r.success) {
											Ext.MessageBox.confirm(
														"IT温馨提示",
														"申请人存在临时人员帐号或特殊帐号,请变更或撤销此类帐号结束后再提交该申请,<br>是否查看申请人拥有的特殊帐号？",
														function(button, text) {
															if (button == 'yes') {
																window.location = webContext
																		+ "/account/myAccount.do?methodCall=query&&userInfo="+userInfo;
															} else {
																Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue('');
															}

														});
//										
									} else {
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
										Ext
												.getCmp('AccountApplyMainTable$applyUserTel')
												.setValue(r.telephone);
										
									}
								},

								failure : function(response, options) {

								}
							}, this);

							var param = {
								serviceItemId : this.serviceItemId,
								processType : this.processType,
								userInfo : userInfo,
								panelName : 'panel_PersonAccountApply_Input'
							}
							Ext.getCmp('account').store.removeAll();
							Ext.getCmp('account').store.load({
								params : param
							})
							Ext.getCmp('account').view.refresh();
//                          Ext.Ajax.request({
//								url : webContext
//										+ '/accountAction_getUserPersonAccount.action',
//								params : {
//									accountType : 'ERP帐号',
//									userInfo : userInfo
//
//								},
//								success : function(response, options) {
//									var responseArray = Ext.util.JSON
//											.decode(response.responseText);
//									if (responseArray.success) {
//										Ext.getCmp('ifHoldErp').show();
//										Ext.getCmp('itil_ac_PersonFormalAccount$ifHoldCombo')
//												.setValue(0);
//									} else {
//
//									}
//								},
//								failure : function(response, options) {
//
//								}
//							}, this);
										}
								},

								failure : function(response, options) {

								}
							}, this);
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
					id : 'AccountApplyMainTable$applyUserTel',
					name : 'AccountApplyMainTable$applyUserTel',
					style : '',
					width : 200,
					value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}), {
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
				}, new Ext.form.ComboBox({forceSelection:true,
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
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserType',
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
					html : '<font color=red>*</font>审批人:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$confirmUser',
					id : 'AccountApplyMainTable$confirmUserCombo',
					width : 200,
					style : '',
					fieldLabel : '审批人',
					colspan : 0,
					rowspan : 0,
					listWidth:500,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请输入部门经理的ITCODE后选择...',
					allowBlank : false,
					
					name : 'AccountApplyMainTable$confirmUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['AccountApplyMainTable$confirmUser'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('AccountApplyMainTable$confirmUserCombo').defaultParam;
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
							//queryEvent.combo.cleanValue();
							param='';
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
					var mailvalue=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getRawValue();
					if(mailvalue=='无'||mailvalue==''){
					Ext.MessageBox.alert("提示","请您选择具体的审批人,谢谢您的合作！",function(btn){
					Ext.getCmp('AccountApplyMainTable$confirmUserCombo').setValue("");
					});
					}
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
				title : '<h3><font color=red>员工离职注销说明:</font></h3>',
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
					columns : 2
				},
				items : [{
					html : ' 1、请离职人员在主页→"人力资源管理流程"→提交"调离"申请后,再进行IT帐号注销申请。<br>2、员工离职帐号注销申请"审批后2小时内所有IT帐号（包括EB、ERP、ESS、MSN、WWW、邮箱、域、<br>&nbsp&nbsp&nbsp&nbsp&nbsp远程接入帐号等）将删除或禁用。<br>3、离职人员的"员工离职帐号注销申请"未提交或审批未通过,IT不予签署纸质离职转单。<br>4、离职人员如有远程接入帐号,请携带令牌卡到IT部门进行注销. ',
					cls : 'common-text',
				    style : 'margin:0px 0px 0px 55px;line-height:18px'
				}]
			}, {
				xtype : 'fieldset',
				title : '<font color=red><b>帐号信息</b></font></b>',
				anchor : '100%',
				animCollapse : true,
				collapsible : true,
				style : 'border:1px dotted #b0acac;margin:25px 0px 0px 0px',
				items : [{
					items : [new Ext.TabPanel({
						width : 'auto',
						activeTab : 0,
						id : 'tab',
						height : 'auto',
						enableTabScroll : true,
						deferredRender : false,
						defaults : {
							autoHeight : true
						},
						frame : true,
						items : [{
							title : '个人正式帐号信息',
							items : [{
								items : [this.grid2]
							}
//							, {
//								layout : 'table',
//								style : 'margin:10px 0px 0px 25px',
//								anchor : '100%',
//								defaults : {
//									bodyStyle : 'padding:4px'
//								},
//								id : 'ifHoldErp',
//								layoutConfig : {
//									columns : 2
//								},
//
//								items : [{
//									html : '该ERP帐号是否保留:',
//									cls : 'common-text',
//									id : '',
//									style : 'width:135;text-align:right'
//								}, new Ext.form.ComboBox({forceSelection:true,
//									xtype : 'combo',
//									id : 'itil_ac_PersonFormalAccount$ifHoldCombo',
//									style : '',
//									mode : 'local',
//									hiddenName : 'itil_ac_PersonFormalAccount$ifHold',
//									colspan : 0,
//									rowspan : 0,
//									triggerAction : 'all',
//									
//									forceSelection : true,
//									allowBlank : true,
//									store : new Ext.data.SimpleStore({
//										fields : ['id', 'name'],
//										data : [['1', '是'], ['0', '否']]
//									}),
//									emptyText : '请选择...',
//									valueField : 'id',
//									value : '',
//									displayField : 'name',
//									name : 'itil_ac_PersonFormalAccount$ifHold',
//									width : 100,
//									fieldLabel : '是否涉及薪酬',
//									listeners : {
//										'expand' : function(combo) {
//											combo.reset();
//										},
//										'select' : function(combo) {
//											var result = Ext
//													.getCmp('itil_ac_PersonFormalAccount$ifHoldCombo')
//													.getValue();
//
//											if (result == 1) {
//												Ext.getCmp('newRight')
//														.expand(true);
//												Ext
//														.getCmp('itil_ac_PersonFormalAccount$accountownerCombo').allowBlank = false;
//											} else {
//												Ext.getCmp('newRight')
//														.collapse(true);
//											}
//
//										}
//									}
//								})]
//
//							}
//							, {
//								xtype : 'fieldset',
//								id : 'newRight',
//								layout : 'table',
//								anchor : '100%',
//								colspan : 4,
//								rowspan : 0,
//								collapsed : true,
//								animCollapse : false,
//								collapsible : false,
//								autoHeight : true,
//								style : 'border:1px dotted #b0acac;margin:0px 0px 0px 0px',
//								autoHeight : true,
//								defaults : {
//									bodyStyle : 'padding:4px'
//								},
//								layoutConfig : {
//									columns : 4
//								},
//								items : [{
//									html : '帐号新所有者:',
//									cls : 'common-text',
//									style : 'width:135;text-align:right'
//								}, new Ext.form.ComboBox({forceSelection:true,
//									xtype : 'combo',
//									hiddenName : 'itil_ac_PersonFormalAccount$accountowner',
//									id : 'itil_ac_PersonFormalAccount$accountownerCombo',
//									width : 200,
//									style : '',
//									fieldLabel : '帐号新所有者',
//									colspan : 0,
//									rowspan : 0,
//									lazyRender : true,
//									displayField : 'userName',
//									valueField : 'id',
//									emptyText : '请选择...',
//									allowBlank : true,
//									
//									name : 'itil_ac_PersonFormalAccount$accountowner',
//									triggerAction : 'all',
//									minChars : 50,
//									queryDelay : 700,
//									store : new Ext.data.JsonStore({
//										url : webContext
//												+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
//										fields : ['id', 'userName'],
//										listeners : {
//											beforeload : function(store, opt) {
//												if (opt.params['itil_ac_PersonFormalAccount$accountowner'] == undefined) {
//													opt.params['userName'] = Ext
//															.getCmp('itil_ac_PersonFormalAccount$accountownerCombo').defaultParam;
//												}
//											}
//										},
//										totalProperty : 'rowCount',
//										root : 'data',
//										id : 'id'
//									}),
//									pageSize : 10,
//									listeners : {
//										'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
//											var param = queryEvent.combo
//													.getRawValue();
//											this.defaultParam = param;
//											if (queryEvent.query == '') {
//												param = '';
//											}
//											this.store.load({
//												params : {
//													userName : param,
//													start : 0
//												}
//											});
//											return true;
//										}
//									},
//									initComponent : function() {
//										this.store.load({
//											params : {
//												id : Ext
//														.getCmp('itil_ac_PersonFormalAccount$accountownerCombo')
//														.getValue(),
//												start : 0
//											},
//											callback : function(r, options,
//													success) {
//												Ext
//														.getCmp('itil_ac_PersonFormalAccount$accountownerCombo')
//														.setValue(Ext
//																.getCmp('itil_ac_PersonFormalAccount$accountownerCombo')
//																.getValue());
//											}
//										});
//									}
//								})]
//
//							}
							]
						}]
					})]

				}

				]
			}, {
				items : [new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$createUser',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$createUser',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '创建人'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$createDate',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$createDate',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '创建日期'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$modifyUser',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$modifyUser',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '最后修改人'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$modifyDate',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$modifyDate',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '最后修改日期'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$id',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '自动编号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$oldApply',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$oldApply',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '变更前申请'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$processType',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$processType',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '流程类型'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$status',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$status',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '状态'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$deleteFlag',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$deleteFlag',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '删除状态'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$serviceItem',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$serviceItem',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '所属服务'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$olodApplyAccount',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$olodApplyAccount',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '变化前帐号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$applyId',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$applyId',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '申请编号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$id',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '自动编号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$accountName',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$accountName',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '帐号名'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$password',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$password',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '密码'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$accountType',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$accountType',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '帐号类型'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$accountState',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$accountState',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '帐号状态'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$remarkDesc',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$remarkDesc',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '备注说明'
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
				if (!Ext.getCmp('panel_PersonAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					var delegateApplyUser = Ext
							.getCmp('AccountApplyMainTable$delegateApplyUserCombo')
							.getValue();
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "申请人不能和审批人相同,请确认后再保存！");
						return false;
					}
					if (delegateApplyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "代申请人不能和审批人相同,请确认后再保存！");
						return false;
					}
					var records = Ext.getCmp('account').getStore().getRange(0,
							Ext.getCmp('account').getStore().getCount());
					if (records.length < 1) {
						Ext.MessageBox.alert('提示', '您不存在个人正式帐号,可以不用提员工离职注销申请!');
						return false;
					}
					var accountType = new Array();
					var vpnTypes = new Array();
					for (var i = 0; i < records.length; i++) {
						accountType[i] = records[i].get('accountType');
						vpnTypes[i] = records[i].get('vpnType');
					   					    
						if (accountType[i] == '远程接入帐号'&&vpnTypes[i] == '0') {
							Ext.MessageBox.alert("提示",
									"请将您的令牌卡归还到IT工程师处,谢谢您的合作！");
						}
					}
		

					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var userInfo = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
							
//					var erpIfHold = Ext
//							.getCmp('itil_ac_PersonFormalAccount$ifHoldCombo')
//							.getValue();
//					var accountownUser = Ext
//							.getCmp('itil_ac_PersonFormalAccount$accountownerCombo')
//							.getValue();
							// var workSpace = Ext
							//.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo')
							//.getValue();
							
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveEmployeeDimissionDraft.action',
						
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							erpIfHold : '0',
							//workSpace:workSpace,
							//accountownUser : accountownUser,
							userInfo : userInfo,
							processType : processType,
							panelName : 'panel_AccountApplyMainTable_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.Msg.alert("提示", "保存成功", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
									});

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("保存失败");
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
					if (!Ext.getCmp('panel_PersonAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
//					Ext.Ajax.request({
//						url : webContext
//								+ '/accountAction_getUserApply.action',
//						params : {
//							serviceItemProcess:processInfoId,
//							processType:processType,
//							userInfo:userInfo
//						} ,

//						success : function(response, options) {
//							var responseArray = Ext.util.JSON
//									.decode(response.responseText);
//							 if(responseArray.success){	
//							 	Ext.MessageBox.alert("提示","申请人已存在审批中的员工离职注销申请,不能够再提该申请！",function(btn){
//		                      	window.history.back(-1);
//		                      	});
//							 }else{
					var delegateApplyUser = Ext
							.getCmp('AccountApplyMainTable$delegateApplyUserCombo')
							.getValue();
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
				  	if(applyUser==''||applyUser==null){
						Ext.MessageBox.alert("提示","申请人必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(confirmUser==''||confirmUser==null){
						Ext.MessageBox.alert("提示","审批人必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "申请人不能和审批人相同,请确认后再保存！");
						return false;
					}
					if (delegateApplyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "代申请人不能和审批人相同,请确认后再保存！");
						return false;
					}
					var records = Ext.getCmp('account').getStore().getRange(0,
							Ext.getCmp('account').getStore().getCount());
					if (records.length < 1) {
						Ext.MessageBox.alert('提示', '您不存在个人正式帐号,可以不用提员工离职注销申请!');
						return false;
					}
					
					var accountType = new Array();
					var vpnTypes = new Array();
                    var j=0; 
					for (var i = 0; i < records.length; i++) {
						accountType[i] = records[i].get('accountType');
						vpnTypes[i] = records[i].get('vpnType');
					    if ((accountType[i] == '远程接入帐号'&&vpnTypes[i] == '0')||accountType[i] =='座机') {
					    	j=1;
					    }
					}
		    if(j==1){
			   var envForm = new Ext.form.FormPanel({//增加审批人 界面的Form
		    	layout : 'table',
		    	id:'envForm',
			    width : 390,
			   height : 150,
			   layoutConfig : {
				columns : 2
			 },
			defaults : {
				bodyStyle : 'padding:15px '
			},
			frame : true,
			items :[
			{
				html : '<font color=red>*</font>归还地点:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$drawSpace',
				id : 'itil_ac_PersonFormalAccount$drawSpaceCombo',
				width : 200,
				style : '',
				fieldLabel : '领卡地点',
				colspan : 0,
				rowspan : 0,
				editable:false,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				name : 'itil_ac_PersonFormalAccount$drawSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.AR_DrawSpace',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$drawSpace'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').defaultParam;
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
									.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo')
									.setValue(Ext
											.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo')
											.getValue());
						}
					});
				}
			})

			]

		});
			  var win = new Ext.Window({//增加审批人的 窗口
				title : '归还令牌卡或座机硬电话地点',
				width : 400,
				height : 150,
				modal : true,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//点击按钮时，首先从面板中得到所有数据，在提交给相应Action中的方法
					text : '确定',
					//id:'postButton',
					handler : function() {//为保存按钮增加监听器
					if (!Ext.getCmp('envForm').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"归还地点必填项,请填写完整。谢谢您合作！");
						return false;
					}
				    Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var userInfo = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
//					var erpIfHold = Ext
//							.getCmp('itil_ac_PersonFormalAccount$ifHoldCombo')
//							.getValue();
//					var accountownUser = Ext
//							.getCmp('itil_ac_PersonFormalAccount$accountownerCombo')
//							.getValue();
				     var workSpace = Ext
							.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo')
							.getValue();
					//add by liuying at 20100224 for 增加提示确认信息 start
					var uInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getRawValue();
					Ext.Msg.show({
							   title:'提示',
							   msg: '即将删除 <font color="#FF0000">'+uInfo+'</font> 的所有IT帐号，请核对确认！',
							   buttons: Ext.Msg.OKCANCEL,
							   fn:function(btn){
							   if(btn=='ok'){
							   Ext.Ajax.request({
									url : webContext
											+ '/accountAction_saveEmployeeDimissionDraft.action',
									params : {
										info : info,
										serviceItemId : curscid,
										processInfoId : processInfoId,
										erpIfHold : '0',
						//				accountownUser : accountownUser,
										userInfo : userInfo,
										processType : processType,
										panelName : 'panel_AccountApplyMainTable_Input'
									},
			
									success : function(response, options) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										var curId = responseArray.id;
										var curName = responseArray.applyId;
										var userInfo = Ext
												.getCmp('AccountApplyMainTable$applyUserCombo')
												.getValue();
								
										// Ext.MessageBox.alert("保存成功");
										// //////////////////////////////////////////////////////////////////
										Ext.Ajax.request({
											url : webContext
													+ '/accountWorkflow_applyEmployQuit.action',
											params : {
												dataId : curId,
												userInfo : userInfo,
												workSpace:workSpace,
												bzparam : "{dataId :'" + curId
														+ "',applyId : '" + curId
														+ "',accountName:'" + curName
														+ "',applyType: 'acproject',"
														+ "applyTypeName: '员工离职IT服务注销申请',"
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
												Ext.MessageBox.alert("提示", "启动工作流失败");
												Ext.getCmp("save").enable();
												Ext.getCmp("submit").enable();
												Ext.getCmp("back").enable();
											}
			
										}, this);
			
										// ///////////////////////////////////////////////////////////////////
			
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("保存失败");
										Ext.getCmp("save").enable();
										Ext.getCmp("submit").enable();
										Ext.getCmp("back").enable();
			
									}
								}, this);


							   }else{
								Ext.getCmp("save").enable();
								Ext.getCmp("submit").enable();
								Ext.getCmp("back").enable();
								win.hide();
							   }
  	
							   },
							   icon: Ext.MessageBox.INFO
							});	
					//add by liuying at 20100224 for 增加提示确认信息 end		
					
					},
					scope : this //指定目标作用域

				}, {
					text : '关闭',
					handler : function() {
						win.hide();
					},
					scope : this
				}]
				});
		win.show();
		
//							Ext.MessageBox.alert("提示",
//									"请将您的令牌卡归还到IT工程师处,谢谢您的合作！");
						
					}else{
					
					
			
				    Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var userInfo = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
//					var erpIfHold = Ext
//							.getCmp('itil_ac_PersonFormalAccount$ifHoldCombo')
//							.getValue();
//					var accountownUser = Ext
//							.getCmp('itil_ac_PersonFormalAccount$accountownerCombo')
//							.getValue();
					//add by liuying at 20100224 for 增加提示确认信息 start
					var uInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getRawValue();
					Ext.Msg.show({
												   title:'提示',
												   msg: '即将删除 <font color="#FF0000">'+uInfo+'</font> 的所有IT帐号，请核对确认！',
												   buttons: Ext.Msg.OKCANCEL,
												   fn:function(btn){
												   if(btn=='ok'){
														 Ext.Ajax.request({
																url : webContext
																		+ '/accountAction_saveEmployeeDimissionDraft.action',
																params : {
																	info : info,
																	serviceItemId : curscid,
																	processInfoId : processInfoId,
																	erpIfHold : '0',
//																	accountownUser : accountownUser,
																	userInfo : userInfo,
																	processType : processType,
																	panelName : 'panel_AccountApplyMainTable_Input'
																},
										
																success : function(response, options) {
																	var responseArray = Ext.util.JSON
																			.decode(response.responseText);
																	var curId = responseArray.id;
																	var curName = responseArray.applyId;
																	var userInfo = Ext
																			.getCmp('AccountApplyMainTable$applyUserCombo')
																			.getValue();
															
																	// Ext.MessageBox.alert("保存成功");
																	// //////////////////////////////////////////////////////////////////
																	Ext.Ajax.request({
																		url : webContext
																				+ '/accountWorkflow_applyEmployQuit.action',
																		params : {
																			dataId : curId,
																			userInfo : userInfo,
																			bzparam : "{dataId :'" + curId
																					+ "',applyId : '" + curId
																					+ "',accountName:'" + curName
																					+ "',applyType: 'acproject',"
																					+ "applyTypeName: '员工离职IT服务注销申请',"
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
																			Ext.MessageBox.alert("提示", "启动工作流失败");
																			Ext.getCmp("save").enable();
																			Ext.getCmp("submit").enable();
																			Ext.getCmp("back").enable();
																		}
										
																	}, this);
										
																	// ///////////////////////////////////////////////////////////////////
										
																},
																failure : function(response, options) {
																	Ext.MessageBox.alert("保存失败");
																	Ext.getCmp("save").enable();
																	Ext.getCmp("submit").enable();
																	Ext.getCmp("back").enable();
										
																}
															}, this);
				
												   }else{
													Ext.getCmp("save").enable();
													Ext.getCmp("submit").enable();
													Ext.getCmp("back").enable();
												   }
					  	
												   },
												   icon: Ext.MessageBox.INFO
												});	
					//add by liuying at 20100224 for 增加提示确认信息 end	
					
					
				}
//				}
//						},
//						failure : function(response, options) {
//							Ext.MessageBox.alert("提示", "请检查申请人是否选择正确!");
//						}
//					}, this);
					
				
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

			Ext.getCmp('panel_PersonAccountApply_Input').form.load({
				url : webContext + '/accountAction_initNewApplyData.action',
				params : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_PersonAccountApply_Input'
				},
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							.initComponent();

					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
//					Ext.getCmp('ifHoldErp').hide();

				},
				failure : function(response, options) {

				}
			})

		} else {
			this.formpanel_PersonAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getEmployeeQuitDraftData.action?panelName=panel_PersonAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					

					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
//					Ext.getCmp("itil_ac_PersonFormalAccount$accountownerCombo")
//							.initComponent();
//					var ifHold = Ext
//							.getCmp('itil_ac_PersonFormalAccount$ifHoldCombo')
//							.getValue();
//					if (ifHold == null || ifHold == '') {
//						Ext.getCmp('ifHoldErp').hide();
//					} else if (ifHold == '1') {
//						Ext.getCmp('newRight').expand(true);
//					}
				

				}
			});
		}
		return this.formpanel_PersonAccountApply_Input;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		  var histroyForm = new HistroyForm({
		   reqId : this.dataId,
		   reqClass : "com.zsgj.itil.require.entity.AccountApplyMainTable"
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
		this.model = "ac_EmployeeDimission";
		if (this.dataId == "0" || this.dataId == "null") {
			this.store1 = new Ext.data.JsonStore({
				url : webContext + "/accountAction_listPersonAccount.action",
				fields : ['id', 'accountName', 'accountType', 'remarkDesc',
						'userRight', 'rightsDesc','vpnType'],
				root : 'data',
				totalProperty : 'rowCount',
				sortInfo : {
					field : "id",
					direction : "ASC"
				},
				baseParams : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_PersonAccountApply_Input'
				}
			});

		} else {

			this.store1 = new Ext.data.JsonStore({
				url : webContext + "/accountAction_listUserAccount.action",
				fields : ['id', 'accountName', 'accountType', 'remarkDesc',
						'userRight', 'rightsDesc','vpnType'],
				root : 'data',
				totalProperty : 'rowCount',
				sortInfo : {
					field : "id",
					direction : "ASC"
				},
				baseParams : {
					dataId : this.dataId
				}

			});
			this.store1.load();
		}

	

		this.store1.paramNames.sort = "orderBy";
		this.store1.paramNames.dir = "orderType";
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.grid2 = new Ext.grid.EditorGridPanel({
			title : '员工帐号信息(<font color=red>*</font>注 ：<font color=red>若申请人存在远程接入帐号,请您在提交申请后及时将令牌卡归还到指定的平台工程师处.谢谢您的合作！)',
			id : 'account',
			name : 'account',
			store : this.store1,
			viewConfig : {
				forceFit : true
			},
			autoScroll : true,
			columns : [{
				header : '自动编号',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				hidden : true
			}, {
				header : '<font color=red>帐号名称</font>',
				dataIndex : 'accountName',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>帐号类型</font>',
				dataIndex : 'accountType',
				align : 'center',
				sortable : true,
				hidden : false,
				editor : new Ext.grid.GridEditor(new Ext.form.TextField({
					allowBlank : true
				}))
			}, {
				header : '<font color=red>特殊要求</font>',
				dataIndex : 'remarkDesc',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>办理情况</font>',
				dataIndex : 'userRight',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>管理员说明</font>',
				dataIndex : 'rightsDesc',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>vpn类型</font>',
				dataIndex : 'vpnType',
				align : 'center',
				sortable : true,
				hidden : true
			}],

			trackMouseOver : false,
			loadMask : true,
			stripeRows : true,
			width : 765,
			autoHeight : true,
			bbar : [{}]
		});

		


		this.getFormpanel_PersonAccountApply_Input();
		this.pa.push(this.formpanel_PersonAccountApply_Input);
		this.formname.push("panel_PersonAccountApply_Input");
		temp.push(this.formpanel_PersonAccountApply_Input);
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