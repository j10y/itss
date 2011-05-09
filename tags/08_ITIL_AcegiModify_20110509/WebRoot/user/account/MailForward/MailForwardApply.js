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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>1.ҳ���д���ɫ<font color=red>*</font>�ŵı����������д���������ύ���룡&nbsp<font color=red>2.�����˱���������б���ѡ��</font></font>')]			

		});
		return this.tabPanel;

	},

	getFormpanel_MailForwardApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		this.formpanel_MailForwardApply_Input = new Ext.form.FormPanel({
			id : 'panel_MailForwardApply_Input',
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
				name : 'MailForwardApply$accountOwner',
				mapping : 'MailForwardApply$accountOwner'
			}, {
				name : 'MailForwardApply$id',
				mapping : 'MailForwardApply$id'
			}, {
				name : 'MailForwardApply$accountName',
				mapping : 'MailForwardApply$accountName'
			}, {
				name : 'MailForwardApply$applyReason',
				mapping : 'MailForwardApply$applyReason'
			}, {
				name : 'MailForwardApply$targetMail',
				mapping : 'MailForwardApply$targetMail'
			}, 
			{
				name : 'MailForwardApply$companionDCMail',
				mapping : 'MailForwardApply$companionDCMail'
			},
				{
				name : 'MailForwardApply$accountState',
				mapping : 'MailForwardApply$accountState'
			}, {
				name : 'MailForwardApply$mailType',
				mapping : 'MailForwardApply$mailType'
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
				name : 'sUserInfos$employeeCode',
				mapping : 'sUserInfos$employeeCode'
			}, {
				name : 'sUserInfos$userType',
				mapping : 'sUserInfos$userType'
			}, {
				name : 'sUserInfos$costCenterCode',
				mapping : 'sUserInfos$costCenterCode'
			}, {
				name : 'sUserInfos$personnelScope',
				mapping : 'sUserInfos$personnelScope'
			}]),
			title : "�ʼ�ת������",
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
					id : 'AccountApplyMainTable$name',
					name : 'AccountApplyMainTable$name',
					style : '',
					width : 200,
					value : '',
					readOnly : true,

					emptyText : '�Զ�����',
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
					hideTrigger : true,
					readOnly : true,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '������ITCODE����ѡ��...',
					allowBlank : true,
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
					html : '�ɱ����ĺ�:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '�ɱ����ĺ�',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					readOnly : true,
					id : 'sUserInfos$costCenterCode',
					name : 'sUserInfos$costCenterCode',
					style : '',
					width : 200,
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
				}), {
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
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '�����벿�ž����ITCODE��ѡ��...',
					allowBlank : false,
					listWidth:500,
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
								param="";
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
				})]
			}, {
				xtype : 'fieldset',
				title : '�����ʺ���Ϣ',
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
				items : [{
					html : '<font color=red>*</font>ת���ʺ�����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'MailForwardApply$accountName',
					id : 'MailForwardApply$accountNameCombo',
					width : 200,
					style : '',
					fieldLabel : 'ID�ļ���',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'userNames',
					valueField : 'userNames',
					emptyText : '��ѡ��...',
					allowBlank : false,
					typeAhead : true,
					name : 'MailForwardApply$accountName',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.itil.account.entity.DCContacts',
						fields : ['id', 'userNames'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['MailForwardApply$accountName'] == undefined) {
									opt.params['userNames'] = Ext
											.getCmp('MailForwardApply$accountNameCombo').defaultParam;
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
							var mailvalue = Ext
									.getCmp('MailForwardApply$accountNameCombo')
									.getRawValue();
							if (mailvalue == '��') {
								Ext.MessageBox.alert("��ʾ",
										"����ѡ������ת���ʺ�����,лл���ĺ�����", function(btn) {
											Ext
													.getCmp('MailForwardApply$accountNameCombo')
													.setValue("");
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
					html : '<font color=red>*</font>����ԭ��:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '����ԭ��',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'MailForwardApply$applyReason',
					name : 'MailForwardApply$applyReason',
					style : '',
					width : 200,
					value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}), {
					html : '<font color=red>*</font>����ת������������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					id : 'MailForwardApply$mailType',
					style : 'float:left;align:left',
					mode : 'local',
					colspan : 0,
					rowspan : 0,
					editable : false,
					triggerAction : 'all',
					typeAhead : true,
					forceSelection : true,
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						fields : ['id', 'name'],
						data : [['1', 'ͬ�¹�˾����'], ['2', '�Լ�˽������']]
					}),
					emptyText : '��ѡ��...',
					valueField : 'id',
					value : '',
					displayField : 'name',
					name : '',
					width : 200,
					fieldLabel : '��������',
					listeners : {
						'expand' : function(combo) {
							combo.reset();
						},
						'select' : function(combo, record, index) {
							var value = Ext.getCmp('MailForwardApply$mailType')
									.getValue();
							if (value == 1) {
								Ext.getCmp("MailForwardApply$companionDCMailCombo")
										.enable();
								Ext.getCmp("MailForwardApply$companionDCMailCombo").allowBlank = false;
								Ext.getCmp('MailForwardApply$targetMail')
										.disable();
								Ext.getCmp('MailForwardApply$targetMail')
										.setValue("");
							} else {
								Ext.getCmp('MailForwardApply$targetMail')
										.enable();
								Ext.getCmp('MailForwardApply$companionDCMailCombo')
										.disable();
								Ext.getCmp("MailForwardApply$targetMail").allowBlank = false;
								Ext.getCmp('MailForwardApply$companionDCMailCombo')
										.setValue("");

							}
						}
					}
				}), {
					html : '�Լ������������ַ:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : 'Ŀ������',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'MailForwardApply$targetMail',
					name : 'MailForwardApply$targetMail',
					style : '',
					width : 200,
					value : '',
					allowBlank : true,
					validator : validate_email,
					vtype : 'email'
				}), {
					html : 'ͬ��DC��������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'MailForwardApply$companionDCMail',
					id : 'MailForwardApply$companionDCMailCombo',
					width : 200,
					style : '',
					fieldLabel : '��������',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'email',
					valueField : 'email',
					emptyText : '',
					allowBlank : true,
					typeAhead : true,
					name : 'MailForwardApply$companionDCMail',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.itil.account.entity.DCContacts',
						fields : ['id', 'email'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['MailForwardApply$companionDCMail'] == undefined) {
									opt.params['email'] = Ext
											.getCmp('MailForwardApply$companionDCMailCombo').defaultParam;
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
							var pid = Ext.getCmp('MailForwardApply$mailType')
									.getValue();
							if (pid == "" || pid == null) {
								Ext.Msg.alert("��ʾ", "����ѡ����������!");
								return false;
							}
						   var param = queryEvent.combo.getRawValue();
							this.defaultParam = param;
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.load({
								params : {
									email : param,
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
				}), 

				new Ext.form.Hidden({
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
					id : 'MailForwardApply$accountOwner',
					colspan : 0,
					rowspan : 0,
					name : 'MailForwardApply$accountOwner',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�ʺ�������'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'MailForwardApply$id',
					colspan : 0,
					rowspan : 0,
					name : 'MailForwardApply$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�Զ����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'MailForwardApply$accountState',
					colspan : 0,
					rowspan : 0,
					name : 'MailForwardApply$accountState',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�ʺ�״̬'
				})]
			}],
			buttons : [{
				text : '����Ϊ�ݸ�',
				iconCls : 'save',
				id : 'save',
				handler : function() {
					
					if (!Ext.getCmp('panel_MailForwardApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					var mailtype=Ext.getCmp("MailForwardApply$mailType").getValue();
			
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
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var info = Ext.encode(getFormParam('panel_MailForwardApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveMailForwardDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							panelName : 'panel_MailForwardApply_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_MailForwardApply_Input').load({
								url : webContext
										+ '/accountAction_getMailForwardDraft.action?panelName=panel_MailForwardApply_Input&dataId='
										+ curId,
								timeout : 30,
								success : function(action, form) {
									Ext
											.getCmp("AccountApplyMainTable$applyUserCombo")
											.initComponent();

									Ext
											.getCmp("AccountApplyMainTable$confirmUserCombo")
											.initComponent();
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
				iconCls : 'submit',
				id : 'submit',
				handler : function() {
					if (!Ext.getCmp('panel_MailForwardApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					
					var name = Ext.getCmp('MailForwardApply$accountNameCombo')
							.getValue();
					if (name == null || name == '') {
						Ext.MessageBox.alert("��ʾ", "����ȷѡ��ת���ʺ�����");
						return false;
					}
					
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
				     
					if(confirmUser==''||confirmUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("��ʾ", "�����˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();

					var info = Ext.encode(getFormParam('panel_MailForwardApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveMailForwardDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							panelName : 'panel_MailForwardApply_Input'
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
										+ '/accountWorkflow_apply.action',
								params : {
									dataId : curId,
									userInfo : userInfo,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject',"
											+ "applyTypeName: '�ʼ�ת������',"
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
									Ext.MessageBox.alert("��ʾ",
											"�ύ����ʧ��,����ת���ʺ������Ƿ�ѡ����ȷ!");
									Ext.getCmp("save").enable();
									Ext.getCmp("submit").enable();
									Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "�ύ����ʧ��,�����������Ƿ�ѡ����ȷ!");
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();
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
			this.formpanel_MailForwardApply_Input.load({
				url : webContext
						+ '/accountAction_initMailForwardApplyData.action',
				params : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_MailForwardApply_Input'
				},
				timeout : 30,
				success : function(action, form) {
					 var userType= Ext.getCmp("sUserInfos$userTypeCombo").getValue();
					if(userType==4){
								Ext.MessageBox.alert("ϵͳ��ʾ","��������ʱԱ������û��Ȩ���������룡",function(btn){
								window.history.back(-1);
							})}
					Ext.getCmp('MailForwardApply$targetMail').disable();
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();

					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();

				}
			});
		} else {
			this.formpanel_MailForwardApply_Input.load({
				url : webContext
						+ '/accountAction_getMailForwardDraft.action?panelName=panel_MailForwardApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
				Ext.getCmp('MailForwardApply$targetMail').disable();
				}
			});
		}
		return this.formpanel_MailForwardApply_Input;
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
		this.model = "ar_MailForwardApplyAM";
		this.getFormpanel_MailForwardApply_Input();
		this.pa.push(this.formpanel_MailForwardApply_Input);
		this.formname.push("panel_MailForwardApply_Input");
		temp.push(this.formpanel_MailForwardApply_Input);


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