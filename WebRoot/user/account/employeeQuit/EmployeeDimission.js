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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 800,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab,
			tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>1.ҳ���д���ɫ<font color=red>*</font>�ŵı����������д���������ύ���룡 <br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2.����ְ��Ա��<font color=red>��ҳ</font>��<font color=red>"������Դ��������"</font>��<font color=red>�ύ"����"�����</font >,�ٽ���IT�ʺ�ע������</font>')]

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
			title : "Ա����ְ�ʺ�ע������",
			items : [{
				xtype : 'fieldset',
				title : '�����˻�����Ϣ',
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
					html : '������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '������',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'AccountApplyMainTable$name',
					name : 'AccountApplyMainTable$name',
					style : '',
					width : 200,
					readOnly : true,
					emptyText : '�Զ�����',
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '��������:',
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
					fieldLabel : '��������'
				}), {
					html : '��������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$delegateApplyUser',
					id : 'AccountApplyMainTable$delegateApplyUserCombo',
					width : 200,
					style : '',
					fieldLabel : '��������',
					colspan : 0,
					rowspan : 0,
					readOnly : true,

					hideTrigger : true,
					readOnly : true,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '��ѡ��...',
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
					html : '<font color=red>*</font>����������ϵ�绰:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '<font color=red>*</font>��������ϵ�绰',
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
					html : '������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$applyUser',
					id : 'AccountApplyMainTable$applyUserCombo',
					width : 200,
					style : '',
					fieldLabel : '������',
					colspan : 0,
					rowspan : 0,
					listWidth:500,//add by liuying at 20100507
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '������ITCODE����ѡ��...',
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
										Ext.MessageBox.alert("IT��ܰ��ʾ","������Ϊϵͳ��Ӧ�ø�����,����ϵ��ع���Ա��������ύ�����룡");
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
														"IT��ܰ��ʾ",
														"�����˴�����ʱ��Ա�ʺŻ������ʺ�,�������������ʺŽ��������ύ������,<br>�Ƿ�鿴������ӵ�е������ʺţ�",
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
//									accountType : 'ERP�ʺ�',
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
					html : '<font color=red>*</font>��������ϵ�绰:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '��������ϵ�绰',
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
					html : 'Ա�����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : 'Ա�����',
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
					html : '�ɱ����ĺ�:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '�ɱ����ĺ�',
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
					html : '�û����/Ա����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'sUserInfos$userType',
					id : 'sUserInfos$userTypeCombo',
					width : 200,
					style : '',
					fieldLabel : '�û�����',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					readOnly : true,
					hideTrigger : true,
					displayField : 'name',
					valueField : 'id',
					emptyText : '��ѡ��...',
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
					html : '<font color=red>*</font>������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$confirmUser',
					id : 'AccountApplyMainTable$confirmUserCombo',
					width : 200,
					style : '',
					fieldLabel : '������',
					colspan : 0,
					rowspan : 0,
					listWidth:500,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '�����벿�ž����ITCODE��ѡ��...',
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
					if(mailvalue=='��'||mailvalue==''){
					Ext.MessageBox.alert("��ʾ","����ѡ������������,лл���ĺ�����",function(btn){
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
				title : '<h3><font color=red>Ա����ְע��˵��:</font></h3>',
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
					html : ' 1������ְ��Ա����ҳ��"������Դ��������"���ύ"����"�����,�ٽ���IT�ʺ�ע�����롣<br>2��Ա����ְ�ʺ�ע������"������2Сʱ������IT�ʺţ�����EB��ERP��ESS��MSN��WWW�����䡢��<br>&nbsp&nbsp&nbsp&nbsp&nbspԶ�̽����ʺŵȣ���ɾ������á�<br>3����ְ��Ա��"Ա����ְ�ʺ�ע������"δ�ύ������δͨ��,IT����ǩ��ֽ����ְת����<br>4����ְ��Ա����Զ�̽����ʺ�,��Я�����ƿ���IT���Ž���ע��. ',
					cls : 'common-text',
				    style : 'margin:0px 0px 0px 55px;line-height:18px'
				}]
			}, {
				xtype : 'fieldset',
				title : '<font color=red><b>�ʺ���Ϣ</b></font></b>',
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
							title : '������ʽ�ʺ���Ϣ',
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
//									html : '��ERP�ʺ��Ƿ���:',
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
//										data : [['1', '��'], ['0', '��']]
//									}),
//									emptyText : '��ѡ��...',
//									valueField : 'id',
//									value : '',
//									displayField : 'name',
//									name : 'itil_ac_PersonFormalAccount$ifHold',
//									width : 100,
//									fieldLabel : '�Ƿ��漰н��',
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
//									html : '�ʺ���������:',
//									cls : 'common-text',
//									style : 'width:135;text-align:right'
//								}, new Ext.form.ComboBox({forceSelection:true,
//									xtype : 'combo',
//									hiddenName : 'itil_ac_PersonFormalAccount$accountowner',
//									id : 'itil_ac_PersonFormalAccount$accountownerCombo',
//									width : 200,
//									style : '',
//									fieldLabel : '�ʺ���������',
//									colspan : 0,
//									rowspan : 0,
//									lazyRender : true,
//									displayField : 'userName',
//									valueField : 'id',
//									emptyText : '��ѡ��...',
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
					fieldLabel : '������'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$createDate',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$createDate',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '��������'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$modifyUser',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$modifyUser',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '����޸���'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$modifyDate',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$modifyDate',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '����޸�����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$id',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�Զ����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$oldApply',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$oldApply',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '���ǰ����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$processType',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$processType',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '��������'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$status',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$status',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '״̬'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$deleteFlag',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$deleteFlag',
					width : 200,
					style : '',
					value : '',
					fieldLabel : 'ɾ��״̬'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$serviceItem',
					colspan : 0,
					rowspan : 0,
					name : 'AccountApplyMainTable$serviceItem',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '��������'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$olodApplyAccount',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$olodApplyAccount',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�仯ǰ�ʺ�'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$applyId',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$applyId',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '������'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$id',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�Զ����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$accountName',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$accountName',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�ʺ���'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$password',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$password',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$accountType',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$accountType',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�ʺ�����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$accountState',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$accountState',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�ʺ�״̬'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_PersonFormalAccount$remarkDesc',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$remarkDesc',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '��ע˵��'
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
				text : '����Ϊ�ݸ�',
				iconCls : 'save',
				id : 'save',
				handler : function() {
				if (!Ext.getCmp('panel_PersonAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
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
						Ext.MessageBox.alert("��ʾ", "�����˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
						return false;
					}
					if (delegateApplyUser == confirmUser) {
						Ext.MessageBox.alert("��ʾ", "�������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
						return false;
					}
					var records = Ext.getCmp('account').getStore().getRange(0,
							Ext.getCmp('account').getStore().getCount());
					if (records.length < 1) {
						Ext.MessageBox.alert('��ʾ', '�������ڸ�����ʽ�ʺ�,���Բ�����Ա����ְע������!');
						return false;
					}
					var accountType = new Array();
					var vpnTypes = new Array();
					for (var i = 0; i < records.length; i++) {
						accountType[i] = records[i].get('accountType');
						vpnTypes[i] = records[i].get('vpnType');
					   					    
						if (accountType[i] == 'Զ�̽����ʺ�'&&vpnTypes[i] == '0') {
							Ext.MessageBox.alert("��ʾ",
									"�뽫�������ƿ��黹��IT����ʦ��,лл���ĺ�����");
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
							Ext.Msg.alert("��ʾ", "����ɹ�", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
									});

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("����ʧ��");
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();

						}
					}, this);
				}
			}, {
				text : '�ύ����',
				iconCls : 'submit',
				id : 'submit',
				handler : function() {
					if (!Ext.getCmp('panel_PersonAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
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
//							 	Ext.MessageBox.alert("��ʾ","�������Ѵ��������е�Ա����ְע������,���ܹ���������룡",function(btn){
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
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if(confirmUser==''||confirmUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("��ʾ", "�����˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
						return false;
					}
					if (delegateApplyUser == confirmUser) {
						Ext.MessageBox.alert("��ʾ", "�������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
						return false;
					}
					var records = Ext.getCmp('account').getStore().getRange(0,
							Ext.getCmp('account').getStore().getCount());
					if (records.length < 1) {
						Ext.MessageBox.alert('��ʾ', '�������ڸ�����ʽ�ʺ�,���Բ�����Ա����ְע������!');
						return false;
					}
					
					var accountType = new Array();
					var vpnTypes = new Array();
                    var j=0; 
					for (var i = 0; i < records.length; i++) {
						accountType[i] = records[i].get('accountType');
						vpnTypes[i] = records[i].get('vpnType');
					    if ((accountType[i] == 'Զ�̽����ʺ�'&&vpnTypes[i] == '0')||accountType[i] =='����') {
					    	j=1;
					    }
					}
		    if(j==1){
			   var envForm = new Ext.form.FormPanel({//���������� �����Form
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
				html : '<font color=red>*</font>�黹�ص�:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$drawSpace',
				id : 'itil_ac_PersonFormalAccount$drawSpaceCombo',
				width : 200,
				style : '',
				fieldLabel : '�쿨�ص�',
				colspan : 0,
				rowspan : 0,
				editable:false,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
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
			  var win = new Ext.Window({//���������˵� ����
				title : '�黹���ƿ�������Ӳ�绰�ص�',
				width : 400,
				height : 150,
				modal : true,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//�����ťʱ�����ȴ�����еõ��������ݣ����ύ����ӦAction�еķ���
					text : 'ȷ��',
					//id:'postButton',
					handler : function() {//Ϊ���水ť���Ӽ�����
					if (!Ext.getCmp('envForm').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"�黹�ص������,����д������лл��������");
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
					//add by liuying at 20100224 for ������ʾȷ����Ϣ start
					var uInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getRawValue();
					Ext.Msg.show({
							   title:'��ʾ',
							   msg: '����ɾ�� <font color="#FF0000">'+uInfo+'</font> ������IT�ʺţ���˶�ȷ�ϣ�',
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
								
										// Ext.MessageBox.alert("����ɹ�");
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
														+ "applyTypeName: 'Ա����ְIT����ע������',"
														+ "customer:'',serviceItemId:'"
														+ curscid + "'}",
												defname : pName
											},
											success : function(response, options) {
												Ext.Msg.alert("��ʾ", "�ύ����ɹ�", function() {
													window.location = webContext
															+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
															+ curscid;
												});
											},
											failure : function(response, options) {
												Ext.MessageBox.alert("��ʾ", "����������ʧ��");
												Ext.getCmp("save").enable();
												Ext.getCmp("submit").enable();
												Ext.getCmp("back").enable();
											}
			
										}, this);
			
										// ///////////////////////////////////////////////////////////////////
			
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("����ʧ��");
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
					//add by liuying at 20100224 for ������ʾȷ����Ϣ end		
					
					},
					scope : this //ָ��Ŀ��������

				}, {
					text : '�ر�',
					handler : function() {
						win.hide();
					},
					scope : this
				}]
				});
		win.show();
		
//							Ext.MessageBox.alert("��ʾ",
//									"�뽫�������ƿ��黹��IT����ʦ��,лл���ĺ�����");
						
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
					//add by liuying at 20100224 for ������ʾȷ����Ϣ start
					var uInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getRawValue();
					Ext.Msg.show({
												   title:'��ʾ',
												   msg: '����ɾ�� <font color="#FF0000">'+uInfo+'</font> ������IT�ʺţ���˶�ȷ�ϣ�',
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
															
																	// Ext.MessageBox.alert("����ɹ�");
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
																					+ "applyTypeName: 'Ա����ְIT����ע������',"
																					+ "customer:'',serviceItemId:'"
																					+ curscid + "'}",
																			defname : pName
																		},
																		success : function(response, options) {
																			Ext.Msg.alert("��ʾ", "�ύ����ɹ�", function() {
																				window.location = webContext
																						+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
																						+ curscid;
																			});
																		},
																		failure : function(response, options) {
																			Ext.MessageBox.alert("��ʾ", "����������ʧ��");
																			Ext.getCmp("save").enable();
																			Ext.getCmp("submit").enable();
																			Ext.getCmp("back").enable();
																		}
										
																	}, this);
										
																	// ///////////////////////////////////////////////////////////////////
										
																},
																failure : function(response, options) {
																	Ext.MessageBox.alert("����ʧ��");
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
					//add by liuying at 20100224 for ������ʾȷ����Ϣ end	
					
					
				}
//				}
//						},
//						failure : function(response, options) {
//							Ext.MessageBox.alert("��ʾ", "�����������Ƿ�ѡ����ȷ!");
//						}
//					}, this);
					
				
				}
			}, {
				text : '����',
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
	 * clientvalidation ��ʼ��
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
			title : 'Ա���ʺ���Ϣ(<font color=red>*</font>ע ��<font color=red>�������˴���Զ�̽����ʺ�,�������ύ�����ʱ�����ƿ��黹��ָ����ƽ̨����ʦ��.лл���ĺ�����)',
			id : 'account',
			name : 'account',
			store : this.store1,
			viewConfig : {
				forceFit : true
			},
			autoScroll : true,
			columns : [{
				header : '�Զ����',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				hidden : true
			}, {
				header : '<font color=red>�ʺ�����</font>',
				dataIndex : 'accountName',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>�ʺ�����</font>',
				dataIndex : 'accountType',
				align : 'center',
				sortable : true,
				hidden : false,
				editor : new Ext.grid.GridEditor(new Ext.form.TextField({
					allowBlank : true
				}))
			}, {
				header : '<font color=red>����Ҫ��</font>',
				dataIndex : 'remarkDesc',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>�������</font>',
				dataIndex : 'userRight',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>����Ա˵��</font>',
				dataIndex : 'rightsDesc',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '<font color=red>vpn����</font>',
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