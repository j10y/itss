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
	getAccountType : function(grid, rowIndex, event) {
		var record = grid.getStore().getAt(rowIndex);
		this.accountType = record.get('accountType');

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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>1.ҳ���д���ɫ<font color=red>*</font>�ŵı����������д���������ύ���룡</font>')]

		});
		return this.tabPanel;

	},

	getFormpanel_DeptChange_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		this.formpanel_DeptChange_Input = new Ext.form.FormPanel({
			id : 'panel_DeptChange_Input',
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
				name : 'sUserInfos$id',
				mapping : 'sUserInfos$id'
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
			}, {
				name : 'AccountApplyMainTable$signAuditUser',
				mapping : 'AccountApplyMainTable$signAuditUser'
			}, {
				name : 'AccountApplyMainTable$mail',
				mapping : 'AccountApplyMainTable$mail'
			}, {
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
				name : 'itil_ac_PersonFormalAccount$stationNumber',
				mapping : 'itil_ac_PersonFormalAccount$stationNumber'
			}, {
				name : 'itil_ac_PersonFormalAccount$yearMoney',
				mapping : 'itil_ac_PersonFormalAccount$yearMoney'
			}, {
				name : 'itil_ac_PersonFormalAccount$telephoneType',
				mapping : 'itil_ac_PersonFormalAccount$telephoneType'
			}, {
				name : 'itil_ac_PersonFormalAccount$telephoneNumber',
				mapping : 'itil_ac_PersonFormalAccount$telephoneNumber'
			}, {
				name : 'itil_ac_PersonFormalAccount$ifSysn',
				mapping : 'itil_ac_PersonFormalAccount$ifSysn'
			}, {
				name : 'itil_ac_PersonFormalAccount$cardState',
				mapping : 'itil_ac_PersonFormalAccount$cardState'
			}, {
				name : 'itil_ac_PersonFormalAccount$olodApplyAccount',
				mapping : 'itil_ac_PersonFormalAccount$olodApplyAccount'
			}, {
				name : 'itil_ac_DCContacts$department',
				mapping : 'itil_ac_DCContacts$department'
			}, {
				name : 'itil_ac_DCContacts$costCenterCode',
				mapping : 'itil_ac_DCContacts$costCenterCode'
			}, {
				name : 'itil_ac_PersonFormalAccount$applyId',
				mapping : 'itil_ac_PersonFormalAccount$applyId'
			}, {
				name : 'itil_ac_PersonFormalAccount$beyondMoney',
				mapping : 'itil_ac_PersonFormalAccount$beyondMoney'
			}, {
				name : 'itil_ac_DCContacts$sameMailDept',
				mapping : 'itil_ac_DCContacts$sameMailDept'
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
				name : 'itil_ac_DCContacts$workSpace',
				mapping : 'itil_ac_DCContacts$workSpace'
			}, {
				name : 'itil_ac_DCContacts$mailServer',
				mapping : 'itil_ac_DCContacts$mailServer'
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
			}]),
			title : "���ű������",
			items : [{
				xtype : 'fieldset',
				title : '��������Ϣ',
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
					readOnly : true,
					emptyText : '�Զ�����',
					id : 'AccountApplyMainTable$name',
					name : 'AccountApplyMainTable$name',
					style : '',
					width : 200,
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
					hideTrigger : true,
					readOnly : true,
					allowBlank : true,
					validator : '',
					format : 'Y-m-d',
					fieldLabel : '��������'
				}), {
					html : '<font color=red>*</font>������:',
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
					lazyRender : true,
					hideTrigger : true,
					readOnly : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '������ITCODE����ѡ��...',
					allowBlank : false,
					typeAhead : true,
					name : 'AccountApplyMainTable$applyUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
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
					value : '',

					readOnly : true,
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
					typeAhead : true,
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
				}), /*{
					html : 'ԭ�ɱ����ĺ�:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '�ɱ����ĺ�',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_DCContacts$costCenterCode',
					name : 'itil_ac_DCContacts$costCenterCode',
					style : '',
					width : 200,
					hideTrigger : true,
					readOnly : true,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '�³ɱ����ĺ�:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '�³ɱ����ĺ�',
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
				}), */{
					html : 'ԭ�ʼ��ȼ�������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_DCContacts$sameMailDept',
					id : 'itil_ac_DCContacts$sameMailDeptCombo',
					width : 200,
					style : '',
					fieldLabel : '�ʼ��ȼ�������',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					hideTrigger : true,
					readOnly : true,
					displayField : 'name',
					valueField : 'id',
					emptyText : '��ѡ��...',
					allowBlank : true,
					//typeAhead : true,
					name : 'itil_ac_DCContacts$sameMailDept',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.SameMailDept',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['itil_ac_DCContacts$sameMailDept'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('itil_ac_DCContacts$sameMailDeptCombo').defaultParam;
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
					html : '���ʼ��ȼ�������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'sUserInfos$sameMailDept',
					id : 'sUserInfos$sameMailDeptCombo',
					width : 200,
					style : '',
					fieldLabel : '�ʼ��ȼ�������',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					readOnly : false,
					hideTrigger : false,
					displayField : 'name',
					valueField : 'id',
					emptyText : '��ѡ��...',
					allowBlank : false,
					//typeAhead : true,
					name : 'sUserInfos$sameMailDept',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.SameMailDept',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['sUserInfos$sameMailDept'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('sUserInfos$sameMailDeptCombo').defaultParam;
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
					xtype : 'fieldset',
					title : '<font color=red>�Ƿ���ƽ̨</font>',
					layout : 'table',
					anchor : '100%',
					id : "platFormChange",
					checkboxToggle : 'true',
					checkboxName : 'platForm',
					collapsed : true,
					animCollapse : false,
					collapsible : false,
					colspan : 4,
					rowspan : 0,
					autoHeight : true,
					style : 'border:1px dotted #b0acac;margin:5px 0px 0px 0px',
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layoutConfig : {
						columns : 4
					},
					items : [{
						html : 'ԭ�����ص�:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({forceSelection:true,
						xtype : 'combo',
						hiddenName : 'itil_ac_DCContacts$workSpace',
						id : 'itil_ac_DCContacts$workSpaceCombo',
						width : 200,
						style : '',
						fieldLabel : '�����ص�',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						hideTrigger : true,
						readOnly : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '��ѡ��...',
						allowBlank : true,
						//typeAhead : true,
						name : 'itil_ac_DCContacts$workSpace',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.WorkSpace',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['itil_ac_DCContacts$workSpace'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('itil_ac_DCContacts$workSpaceCombo').defaultParam;
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
										space : param,
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
						html : '�¹����ص�:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({forceSelection:true,
						xtype : 'combo',
						hiddenName : 'sUserInfos$workSpace',
						id : 'sUserInfos$workSpaceCombo',
						width : 200,
						style : '',
						fieldLabel : '�����ص�',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '��ѡ��...',

						allowBlank : true,
						//typeAhead : true,
						name : 'sUserInfos$workSpace',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.WorkSpace',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['sUserInfos$workSpace'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('sUserInfos$workSpaceCombo').defaultParam;
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
							},
							'select' : function(combo, record, index) {

								var workSpace = Ext
										.getCmp('sUserInfos$workSpaceCombo')
										.getValue();
								Ext.Ajax.request({
									url : webContext
											+ '/accountAction_getMailServer.action',
									params : {
										workSpace : workSpace
									},
									success : function(response, options) {
										var r = Ext
												.decode(response.responseText);
										var mail = r.mailServer;
										Ext
												.getCmp("sUserInfos$mailServerCombo")
												.setValue(mail);
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
				html : 'ԭ�ʼ�������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, 
//modify by liuying for �޸�ԭ�ʼ�������Ϊ�ı��� at 20100514 start				
			new Ext.form.TextField({
					fieldLabel : '�ʼ�������',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_DCContacts$mailServer',
					name : 'itil_ac_DCContacts$mailServer',
					style : '',
					width : 200,
                    readOnly : true,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
			
//				xtype : 'combo',
//				hiddenName : 'itil_ac_DCContacts$mailServer',
//				id : 'itil_ac_DCContacts$mailServerCombo',
//				width : 200,
//				style : '',
//				fieldLabel : '�ʼ�������',
//				colspan : 0,
//				rowspan : 0,
//				lazyRender : true,
//				displayField : 'name',
//				valueField : 'id',
//				emptyText : '��ѡ��...',
//				allowBlank : true,
//				typeAhead : true,
//				name : 'itil_ac_DCContacts$mailServer',
//				triggerAction : 'all',
//				minChars : 50,
//				hideTrigger:true,
//				readOnly : true,
//				queryDelay : 700,
//				store : new Ext.data.JsonStore({
//					url : webContext
//							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.MailServer',
//					fields : ['id', 'name'],
//					listeners : {
//						beforeload : function(store, opt) {
//							if (opt.params['itil_ac_DCContacts$mailServer'] == undefined) {
//								opt.params['name'] = Ext
//										.getCmp('itil_ac_DCContacts$mailServerCombo').defaultParam;
//							}
//						}
//					},
//					totalProperty : 'rowCount',
//					root : 'data',
//					id : 'id'
//				}),
//				pageSize : 10,
//				listeners : {
//					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
//						var param = queryEvent.combo.getRawValue();
//						this.defaultParam = param;
//						if (queryEvent.query == '') {
//							param = '';
//						}
//						this.store.load({
//							params : {
//								name : param,
//								start : 0
//							}
//						});
//						return true;
//					}
//				},
//				initComponent : function() {
//					this.store.load({
//						params : {
//							id : Ext.getCmp('itil_ac_DCContacts$mailServerCombo')
//									.getValue(),
//							start : 0
//						},
//						callback : function(r, options, success) {
//							Ext
//									.getCmp('itil_ac_DCContacts$mailServerCombo')
//									.setValue(Ext
//											.getCmp('itil_ac_DCContacts$mailServerCombo')
//											.getValue());
//						}
//					});
//				}
//modify by liuying for �޸�ԭ�ʼ�������Ϊ�ı��� at 20100514 end
			}), {
						html : '���ʼ�������:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({forceSelection:true,
						xtype : 'combo',
						hiddenName : 'sUserInfos$mailServer',
						id : 'sUserInfos$mailServerCombo',
						width : 200,
						style : '',
						fieldLabel : '�ʼ�������',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '��ѡ��...',
						allowBlank : true,
						//typeAhead : true,
						name : 'sUserInfos$mailServer',
						triggerAction : 'all',
						minChars : 50,
						hideTrigger : true,
						readOnly : true,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.MailServer',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['sUserInfos$mailServer'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('sUserInfos$mailServerCombo').defaultParam;
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
					html : '<font color=red>*</font>ԭ����������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$confirmUser',
					id : 'AccountApplyMainTable$confirmUserCombo',
					width : 200,
					style : '',
					fieldLabel : '������',
					//hideTrigger : true,
					listWidth:500,
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '�����벿�ž����ITCODE��ѡ��...',
					allowBlank : false,
					//typeAhead : true,
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
					var mailvalue=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getRawValue();
					if(mailvalue=='��'||mailvalue==''){
					Ext.MessageBox.alert("��ʾ","����ѡ������ԭ����������,лл���ĺ�����",function(btn){
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
				}), {
					html : '<font color=red>*</font>�²���������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$signAuditUser',
					id : 'AccountApplyMainTable$signAuditUserCombo',
					width : 200,
					style : '',
					fieldLabel : '������',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '��ѡ��...',
					allowBlank : false,
					listWidth:500,
					//typeAhead : true,
					name : 'AccountApplyMainTable$signAuditUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['AccountApplyMainTable$signAuditUser'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('AccountApplyMainTable$signAuditUserCombo').defaultParam;
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
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'sUserInfos$id',
					colspan : 0,
					rowspan : 0,
					name : 'sUserInfos$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�Զ����'
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
				}), new Ext.form.Hidden({
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
					id : 'itil_ac_PersonFormalAccount$cardState',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$cardState',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '��״̬'
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
					id : 'sUserInfos$costCenterCode',
					colspan : 0,
					rowspan : 0,
					name : 'sUserInfos$costCenterCode',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�ʺ�״̬'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_DCContacts$costCenterCode',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_DCContacts$costCenterCode',
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
				})]
			}, {
				xtype : 'fieldset',
				title : '<font color=red><b>�ʺ���Ϣ</b></font></b>',
				anchor : '100%',
				animCollapse : true,
				collapsible : true,
				style : 'border:1px dotted #b0acac;margin:25px 0px 0px 0px',
				items : [this.grid2]
			}, {
				xtype : 'fieldset',
				title : '<font color=red>�Ƿ�����WWW�ʺŶ��</font>',
				layout : 'table',
				anchor : '100%',
				id : "wwwAccount",
				checkboxToggle : 'true',
				checkboxName : 'www',
				collapsed : true,
				animCollapse : false,
				collapsible : false,
				autoHeight : true,
				style : 'border:1px dotted #b0acac;margin:25px 0px 0px 0px',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				items : [{
					html : '<font color=red>*</font>www�ʺŶ��:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_PersonFormalAccount$wwwAccountValue',
					id : 'itil_ac_PersonFormalAccount$wwwAccountValueCombo',
					width : 200,
					style : '',
					fieldLabel : 'www�ʺŶ��',
					colspan : 0,
					editable : false,
					rowspan : 0,
					lazyRender : true,
					displayField : 'type',
					valueField : 'id',
					emptyText : '��ѡ��...',
					allowBlank : true,
					typeAhead : true,
					name : 'itil_ac_PersonFormalAccount$wwwAccountValue',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.WWWScanType',
						fields : ['id', 'type'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['itil_ac_PersonFormalAccount$wwwAccountValue'] == undefined) {
									opt.params['type'] = Ext
											.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').defaultParam;
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
									type : param,
									start : 0
								}
							});
							return true;
						},
						'select' : function(combo, record, index) {
							var mailvalue = Ext
									.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
									.getRawValue();
							if (mailvalue == '��') {
								Ext.MessageBox.alert("��ʾ",
										"����ѡ������www�ʺŶ��,лл���ĺ�����",
										function(btn) {
											Ext
													.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
													.setValue("");
											return false;
										});
							}
						}
					},
					initComponent : function() {
						this.store.load({
							params : {
								id : Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
										.setValue(Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getValue());
							}
						});
					}
				})]
			}],

			buttons : [{
				text : '����Ϊ�ݸ�',
				iconCls : 'save',
				id : 'save',
				handler : function() {
					if (Ext.getDom('www').checked) {
						Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').allowBlank = false;
					}
					if (!Ext.getCmp('panel_DeptChange_Input').form.isValid()) {
						Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();

					var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var info = Ext.encode(getFormParam('panel_DeptChange_Input'));
					var workSpace = Ext.getCmp('sUserInfos$workSpaceCombo').getValue();
					var mailServer = Ext.getCmp('sUserInfos$mailServerCombo').getValue();
					Ext.Ajax.request({
						url : webContext + '/accountAction_saveAccountApplyInfo.action',
						params : {
							workSpace : workSpace,
							mailServer : mailServer,
							info : info,
							serviceItemId : curscid,
							processType : processType,
							processInfoId : processInfoId,
							panelName : 'panel_DeptChange_Input'
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
							var wwwValue = Ext
									.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
									.getValue();
							var records = Ext.getCmp('account').getStore()
									.getRange(
											0,
											Ext.getCmp('account').getStore()
													.getCount());
							var id = new Array();
							var ifHold = new Array();
							var remark = new Array();
							var accountType = new Array();
							if (records.length == 0) {
								Ext.MessageBox.alert('����',
										'�㻹û�и����ʺ�,���Բ����������ű������!');
								return;
							}
							for (i = 0; i < records.length; i++) {
								id[i] = records[i].get('id');
								accountType[i] = records[i].get('accountType');
								ifHold[i] = records[i].get('ifHold');
								remark[i] = records[i].get('remarkDesc');
								Ext.Ajax.request({
									url : webContext
											+ '/accountAction_saveDeptChangeDraff.action',
									params : {
										id : id[i],
										ifHold : ifHold[i],
										remark : remark[i],
										curId : curId,
										accountType : accountType[i],
										wwwValue : wwwValue
									},
									timeout : 100000,
									success : function(response) {
										var r = Ext
												.decode(response.responseText);
										if (!r.success) {
											Ext.getCmp("save").enable();
											Ext.getCmp("submit").enable();
											Ext.getCmp("back").enable();
										} else {

										}

									},
									scope : this
								});
							}
							var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo')
									.getValue();

							Ext.getCmp('panel_DeptChange_Input').load({
								url : webContext
										+ '/accountAction_getDeptChangeDraftData.action?panelName=panel_DeptChange_Input&dataId='
										+ curId,

								timeout : 30,
								success : function(action, form) {
								}
							});
							Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
									});
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();
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
				id : 'submit',
				iconCls : 'submit',
				handler : function() {
					
					var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getUserApply.action',
						params : {
							serviceItemProcess:processInfoId,
							processType:processType,
							userInfo:userInfo
							
						} ,

						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							 if(responseArray.success){	
							 	Ext.MessageBox.alert("��ʾ","�������Ѵ��������еĲ��ű������,���ܹ���������룡",function(btn){
		                      	window.history.back(-1);
		                      	});
							 }else{
					if (Ext.getDom('www').checked) {
						var wwwValue = Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getValue();
						if (wwwValue == "" || wwwValue == null) {
							Ext.MessageBox.alert("��ʾ","Ҫ���www�ʺŶ��,��ѡ���µ��ʺŶ�ȡ�лл��������");
							return false;
						}

					}
					if (!Ext.getCmp('panel_DeptChange_Input').form.isValid()) {
						Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					var signAuditUser=Ext.getCmp('AccountApplyMainTable$signAuditUserCombo').getValue();
					var sameMailDept = Ext.getCmp('sUserInfos$sameMailDeptCombo').getValue();
					if(confirmUser==''||confirmUser==null){
						Ext.MessageBox.alert("��ʾ","ԭ���������˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if(sameMailDept==''||sameMailDept==null){
						Ext.MessageBox.alert("��ʾ","���ʼ��ȼ۲��ű���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if(signAuditUser==''||signAuditUser==null){
						Ext.MessageBox.alert("��ʾ","�²��������˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","�����˲��ܺ�ԭ������������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					if(signAuditUser==applyUser){
					 Ext.MessageBox.alert("��ʾ","�����˲��ܺ��²�����������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();

					var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var info = Ext.encode(getFormParam('panel_DeptChange_Input'));
					var workSpace = Ext.getCmp('sUserInfos$workSpaceCombo').getValue();
					var mailServer = Ext.getCmp('sUserInfos$mailServerCombo').getValue();
//					var sameMailDept = Ext.getCmp('sUserInfos$sameMailDeptCombo')
//							.getValue();
				    
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveAccountApplyInfo.action',
						params : {
							workSpace : workSpace,
							mailServer : mailServer,
							sameMailDept:sameMailDept,
							info : info,
							serviceItemId : curscid,
							processType : processType,
							processInfoId : processInfoId,
							panelName : 'panel_DeptChange_Input'
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
							var wwwValue = Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getValue();

							var records = Ext.getCmp('account').getStore().getRange(	0,Ext.getCmp('account').getStore().getCount());
							var id = new Array();
							var ifHold = new Array();
							var remark = new Array();
							var accountType = new Array();
							if (records.length == 0) {
								Ext.MessageBox.alert('����','�㻹û�и����ʺ�,���Բ����������ű������!');
								return;
							}
							for (i = 0; i < records.length; i++) {
								id[i] = records[i].get('id');
								accountType[i] = records[i].get('accountType');
								ifHold[i] = records[i].get('ifHold');
								remark[i] = records[i].get('remarkDesc');
								var url = webContext
											+ '/accountAction_saveDeptChangeDraff.action?id='+id[i]+'&ifHold='+ifHold[i]+'&remark='+remark[i]+'&curId='+curId+'&wwwValue='+wwwValue;
								var conn = Ext.lib.Ajax.getConnectionObject().conn;
								conn.open("post", url, false);
								conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
								conn.send("accountType="+accountType[i]); 
								if (conn.status == "200") {
									var responseText = conn.responseText;
									var r=Ext.decode(responseText);
									if(!r.success){
										Ext.getCmp("save").enable();
										Ext.getCmp("submit").enable();
										Ext.getCmp("back").enable();
									}
								} 
//								Ext.Ajax.request({
//									url : webContext
//											+ '/accountAction_saveDeptChangeDraff.action',
//									params : {
//										id : id[i],
//										ifHold : ifHold[i],
//										remark : remark[i],
//										curId : curId,
//										accountType : accountType[i],
//										wwwValue : wwwValue
//									},
//									timeout : 100000,
//									success : function(response) {
//										var r = Ext.decode(response.responseText);
//										if (!r.success) {
//											Ext.getCmp("save").enable();
//											Ext.getCmp("submit").enable();
//											Ext.getCmp("back").enable();
//										} else {
//										}
//
//									},
//									scope : this
//								});
							}
							var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
							var wwwValue = Ext.getCmp("itil_ac_PersonFormalAccount$wwwAccountValueCombo").getValue();
							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_applyDeptChange.action',
								params : {
									dataId : curId,
									userInfo : userInfo,
									wwwValue : wwwValue,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject',"
											+ "applyTypeName: 'Ա�����ű������',"
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
								Ext.MessageBox.alert("��ʾ", "�ύ����ʧ��,���鲿���������Ƿ�ѡ����ȷ!");
									Ext.getCmp("save").enable();
									Ext.getCmp("submit").enable();
									Ext.getCmp("back").enable();
								}
							}, this);
							

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "�ύ����ʧ��,�����������Ƿ�ѡ����ȷ!");
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();
						}
					}, this);
				}
				
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "�����������Ƿ�ѡ����ȷ!");
						}
					}, this);
					
				
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
		 Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getUserPersonAccount.action',
						params : {
							accountType:'�ʼ��ʺ�'
						},

						success : function(response, options) {
						var responseArray = Ext.util.JSON
									.decode(response.responseText);
						 if(responseArray.success){	
						Ext.getCmp('panel_DeptChange_Input').form.load({
							url : webContext
									+ '/accountAction_initDeptChangeUser.action',
							params : {
								serviceItemId : this.serviceItemId,
								processType : this.processType,
								panelName : 'panel_DeptChange_Input'
							},
							timeout : 30,
							success : function(action, form) {
								Ext
										.getCmp("AccountApplyMainTable$applyUserCombo")
										.initComponent();
											Ext
										.getCmp("itil_ac_DCContacts$sameMailDeptCombo")
										.initComponent();
							Ext.getCmp("itil_ac_DCContacts$workSpaceCombo")
							.initComponent();
										
								Ext
										.getCmp("AccountApplyMainTable$confirmUserCombo")
										.initComponent();
										Ext.getCmp("AccountApplyMainTable$signAuditUserCombo").initComponent();
								Ext
										.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
										.setValue("");
								Ext.getCmp("sUserInfos$sameMailDeptCombo").setValue("");
										
								Ext.getCmp("sUserInfos$workSpaceCombo")
										.initComponent();
//								Ext.getCmp("itil_ac_DCContacts$mailServerCombo")
//										.initComponent();
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ",
										"�û��������ڿ��õ��ʺ�,�����Բ����ύ���ű������", function(
												btn) {
											window.history.back(-1);
										});
							}
						});
						Ext.Ajax.request({
							url : webContext
									+ '/accountAction_getUserPersonAccount.action',
							params : {
								accountType : 'WWW�ʺ�'
							},
							success : function(response, options) {
								var responseArray = Ext.util.JSON
										.decode(response.responseText);
								if (responseArray.success) {
									Ext
											.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
											.setValue("");

								} else {
									Ext.getCmp('wwwAccount').hide();

								}
							},
							failure : function(response, options) {

							}
						}, this);
						 }else{
						 Ext.MessageBox.alert("��ʾ",
										"�û��������ʼ��ʺŲ��ܹ��ύ���ű������", function(
												btn) {
											window.history.back(-1);
										});
						 }
						 },
							failure : function(response, options) {

							}
						}, this);
		
		} else {
			this.formpanel_DeptChange_Input.load({
				url : webContext
						+ '/accountAction_getDeptChangeDraftData.action?panelName=panel_DeptChange_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					
					Ext.getCmp("AccountApplyMainTable$signAuditUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
					Ext.getCmp("itil_ac_DCContacts$workSpaceCombo")
							.initComponent();
				    Ext.getCmp("sUserInfos$sameMailDeptCombo")
										.initComponent();
					Ext.getCmp("itil_ac_DCContacts$sameMailDeptCombo")
										.initComponent();
					Ext.getCmp("sUserInfos$workSpaceCombo").initComponent();
					var newWorkSpace = Ext
							.getCmp('itil_ac_DCContacts$workSpaceCombo')
							.getValue();
					var oldWorkSpace = Ext.getCmp('sUserInfos$workSpaceCombo')
							.getValue();
					
					if (newWorkSpace != oldWorkSpace) {
						Ext.getCmp("platFormChange").expand(true);
						Ext.getDom('platForm').checked = true;
					}
					var wwwValue = Ext
							.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
							.getValue();
					if (wwwValue != null && wwwValue != '') {
						Ext.getDom('www').checked = true;
						Ext.getCmp("wwwAccount").expand(true);

					} else {
						Ext.getCmp('wwwAccount').hide();
					}

				}
			})

		}
		return this.formpanel_DeptChange_Input;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		this.accountType = "";
	
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
		this.model = "ar_DeptChangeApply";
		if (this.dataId == "0" || this.dataId == "null") {
			this.store1 = new Ext.data.JsonStore({
				url : webContext + "/accountAction_listPersonAccountDeptChange.action",
				fields : ['id', 'accountName', 'accountType', 'ifHold', 'remarkDesc'],
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
				url : webContext + "/accountAction_listDeptChangeAccountList.action",
				fields : ['id', 'accountName', 'accountType', 'ifHold', 'remarkDesc'],
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
		}
		this.store1.paramNames.sort = "orderBy";
		this.store1.paramNames.dir = "orderType";

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

//		this.pageBar = new Ext.PagingToolbar({
//			pageSize : 10,
//			store : this.store1,
//			displayInfo : true,
//			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
//			emptyMsg : "����ʾ����"
//		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;

		this.grid2 = new Ext.grid.EditorGridPanel({
			title : 'Ա���ʺ���Ϣ(ע<font color=red>*</font>������������ʺŻ����ʼ��ʺ��佫Ĭ�ϱ���)',
			id : 'account',
			name : 'account',
			border : true,
			store : this.store1,
			style : 'margin:0px 0px 0px 45px;border:1px dotted #b0acac',
			viewConfig : {
				forceFit : true
			},
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
				hidden : false
			}, {
				header : '<font color=red>�Ƿ���(˫���ɱ༭)</font>',
				dataIndex : 'ifHold',
				align : 'center',
				sortable : true,
				hidden : false,
				editor : new Ext.grid.GridEditor(new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					style : 'float:left;align:left',
					mode : 'local',
					colspan : 0,
					rowspan : 0,
					triggerAction : 'all',
					typeAhead : true,
					forceSelection : true,
					allowBlank : true,
					store : new Ext.data.SimpleStore({
						fields : ['id', 'name'],
						data : [['1', '��'], ['0', '��']]
					}),

					emptyText : '��ѡ��...',
					valueField : 'id',
					value : '',
					scope : this,
					displayField : 'name',
					id : 'itil_ac_PersonFormalAccount$referSalary',
					name : 'itil_ac_PersonFormalAccount$referSalary',
					width : 200,
					fieldLabel : '�Ƿ���',
					listeners : {
						'expand' : function(combo) {
							combo.reset();
						}
					// 'select' : function(combo, record, index) {
					// var
					// result=Ext.getCmp('itil_ac_PersonFormalAccount$referSalary').getValue();
					//						
					//						
					// }

					}
				})),
				renderer : function(value, record) {

					if (value == 1) {
						return '��'
					} else {
						return '��'
					}
				}
			}, {
				header : '<font color=red>��ע˵��(˫���ɱ༭)</font>',
				dataIndex : 'remarkDesc',
				align : 'center',
				sortable : true,
				hidden : false,
				editor : new Ext.grid.GridEditor(new Ext.form.TextField({
					allowBlank : true
				}))
			}],

			trackMouseOver : false,
			loadMask : true,
			stripeRows : true,
			width : 665,
			autoHeight : true
			//bbar : this.pageBar

		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 1,
			'rootFlag' : 1,
			'status' : 0
		};
		Ext.getCmp('itil_ac_PersonFormalAccount$referSalary').on('select',
				function(combo, record, index) {
					var result = Ext
							.getCmp('itil_ac_PersonFormalAccount$referSalary')
							.getValue();
					if (result == '0' && this.accountType == 'Զ�̽����ʺ�') {
						Ext.MessageBox
								.alert("��ʾ", "��������Զ�̽����ʺ�,������ʱ�黹���ƿ�,лл����!");
					}
					if (result == '0' && this.accountType == 'WWW�ʺ�') {
						Ext.getCmp('wwwAccount').hide();

					}
					if (result == '1' && this.accountType == 'WWW�ʺ�') {
						Ext.getCmp('wwwAccount').show();

					}
				}, this);

		//this.pageBar.formValue = param;
		this.grid2.on("rowclick", this.getAccountType, this);

		this.store1.removeAll();
		this.store1.load({
			params : param
		});

		this.getFormpanel_DeptChange_Input();
		this.pa.push(this.formpanel_DeptChange_Input);
		this.formname.push("panel_DeptChange_Input");
		temp.push(this.formpanel_DeptChange_Input);
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