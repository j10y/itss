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

	getFormpanel_MailGroup : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		this.formpanel_MailGroup = new Ext.form.FormPanel({
			id : 'panel_MailGroup',
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
				name : 'MailGroup$groupManger',
				mapping : 'MailGroup$groupManger'
			}, {
				name : 'MailGroup$changeGroupManger',
				mapping : 'MailGroup$changeGroupManger'
			}, {
				name : 'MailGroup$groupNewName',
				mapping : 'MailGroup$groupNewName'
			}, {
				name : 'MailGroup$applyReason',
				mapping : 'MailGroup$applyReason'
			}, {
				name : 'MailGroup$oldApply',
				mapping : 'MailGroup$oldApply'
			}, {
				name : 'MailGroup$id',
				mapping : 'MailGroup$id'
			}, {
				name : 'MailGroup$groupName',
				mapping : 'MailGroup$groupName'
			}, {
				name : 'sUserInfos$email',
				mapping : 'sUserInfos$email'
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
			}]),
			title : "�ʼ�Ⱥ��ɾ������",
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
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '������ITCODE����ѡ��...',
					allowBlank : true,
					
					hideTrigger : true,
					readOnly : true,
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
							param='';
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
				title : 'Ⱥ����Ϣ',
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
					html : '<font color=red>*</font> Ⱥ������:(���Ⱥ��ɾ����ʹ�÷ֺŷָ������ʮ��Ⱥ�飬Ⱥ�����Ա������ͬ )',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0,
					style : 'border:1px dotted #b0acac;width:600;text-align:left;margin:0px 0px 0px 75px'
				}, new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'MailGroup$groupName',
					colspan : 4,
					rowspan : 0,
					name : 'MailGroup$groupName',
					width : 603,
					readOnly : true,
					style : 'margin:0px 0px 0px 75p',
					value : '',
					allowBlank : false,
					validator : '',
					fieldLabel : 'Ⱥ������'
				}), {
					html : ' <font color=red>��ѡ���Ⱥ����:</font>',
					cls : 'common-text',
					style : 'margin:0px 0px 0px 75p'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'applyUser',
					id : 'applyUserCombo',
					width : 400,//modify by liuying at 20100507
					style : '',
					fieldLabel : '������',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'accountName',
					valueField : 'id',
					emptyText : '������ؼ��ֺ�ѡ��...',
					allowBlank : true,
					

					name : 'applyUser',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ "/accountAction_listMialGroupName.action",
						fields : ['id', 'accountName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['applyUser'] == undefined) {
									opt.params['accountName'] = Ext
											.getCmp('applyUserCombo').defaultParam;
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
				}),

				new Ext.Button({
					text : '���',
					pressed : true,
					iconCls : 'add',
					scope : this,
					handler : function() {

						var manger = Ext.getCmp("applyUserCombo").getValue();
						if (manger == "") {
							Ext.Msg.alert("��ʾ", "��ѡ��Ⱥ�����������");
							return;
						}
						var temp = Ext.getCmp("applyUserCombo").getRawValue();
						var mangerGroup = Ext.getCmp('MailGroup$groupName')
								.getValue();
						var number = Ext.getCmp('MailGroup$groupName')
								.getValue().split(';');
						if (number.length >= 10) {
							Ext.Msg.alert("��ʾ", "��Ⱥ�������Ϊʮ�����������ټ�����ӣ�");
							return;
						}
						if (mangerGroup.indexOf(temp) >= 0) {
							Ext.Msg.alert("��ʾ", "��Ⱥ�����Ѿ���ӣ������ظ����");
							return;
						}
						if (mangerGroup == "") {
							Ext.getCmp('MailGroup$groupName').setValue(temp);

						} else {

							Ext.getCmp('MailGroup$groupName')
									.setValue(mangerGroup + ";" + temp);
						}
						Ext.getCmp("applyUserCombo").setValue("");
					}
				}), new Ext.Button({
					text : '����',
					pressed : true,
					iconCls : 'refresh',
					scope : this,
					handler : function() {
						Ext.getCmp('MailGroup$groupName').setValue("");
					}
				}),

				{
					html : '<font color=red>*</font>ɾ��ԭ��',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0,
					style : 'border:1px dotted #b0acac;width:600;text-align:left;margin:0px 0px 0px 75px'
				}, new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'MailGroup$applyReason',
					colspan : 4,
					rowspan : 0,
					name : 'MailGroup$applyReason',
					width : 603,

					style : 'margin:0px 0px 0px 75p',
					value : '',
					allowBlank : false,
					validator : '',
					fieldLabel : '����ԭ��'
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
					id : 'MailGroup$oldApply',
					colspan : 0,
					rowspan : 0,
					name : 'MailGroup$oldApply',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '���Ǯ����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'MailGroup$id',
					colspan : 0,
					rowspan : 0,
					name : 'MailGroup$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�Զ����'
				})]
			}],
			buttons : [{
				text : '����Ϊ�ݸ�',
				id : 'save',
				iconCls : 'save',
				handler : function() {
					if (!Ext.getCmp('panel_MailGroup').form.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
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
					var info = Ext.encode(getFormParam('panel_MailGroup'));
					var groupName = Ext.getCmp('MailGroup$groupName')
							.getValue();
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveMailGroupDeleteDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							groupName : groupName,
							panelName : 'panel_MailGroup'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_MailGroup').load({
								url : webContext
										+ '/accountAction_getMailGroupDraft.action?panelName=panel_MailGroup&dataId='
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
				iconCls : 'submit',
				id : 'submit',
				handler : function() {
					if (!Ext.getCmp('panel_MailGroup').form.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
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

					var info = Ext.encode(getFormParam('panel_MailGroup'));
					var groupName = Ext.getCmp('MailGroup$groupName')
							.getValue();
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveMailGroupDeleteDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							groupName : groupName,
							panelName : 'panel_MailGroup'
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
											+ "applyTypeName: '�ʼ�Ⱥ��ɾ������',"
											+ "customer:'',serviceItemId:'"
											+ curscid + "'}",
									defname : pName
								},
								success : function(response, options) {
									Ext.Msg.alert("��ʾ", "�����������ɹ�", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
									});
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("��ʾ",
											"�ύ����ʧ��,�����������Ƿ�ѡ����ȷ!");
									Ext.getCmp("save").enable();
									Ext.getCmp("submit").enable();
									Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ", "�ύ����ʧ��,����Ⱥ������ѡ����ȷ!");
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
			this.formpanel_MailGroup.load({
				url : webContext
						+ '/accountAction_initPersonAccountApplyData.action',
				params : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_MailGroup'
				},
				timeout : 30,
				success : function(action, form) {
					  var userType= Ext.getCmp("sUserInfos$userTypeCombo").getValue();
								if(userType==7){
								Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲԱ������û��Ȩ���������룡",function(btn){
								window.history.back(-1);
							})}
								else if(userType==9){
								Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲ(����)Ա������û��Ȩ���������룡",function(btn){
								window.history.back(-1);
							})}
							 //modify by liuying for �޸�������Ա�����͵���֤ at 20100428 start
//							else	if(userType==3){
//								Ext.MessageBox.alert("ϵͳ��ʾ","����������Ա������û��Ȩ���������룡",function(btn){
//								window.history.back(-1);
//							})}
//								else if(userType==4){
//								Ext.MessageBox.alert("ϵͳ��ʾ","��������ʱԱ������û��Ȩ���������룡",function(btn){
//								window.history.back(-1);
//							})}
							 //modify by liuying for �޸�������Ա�����͵���֤ at 20100428 end
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();

				}
			});
		} else {
			this.formpanel_MailGroup.load({
				url : webContext
						+ '/accountAction_getMailGroupDraft.action?panelName=panel_MailGroup&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {

					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
				}
			});
		}
		return this.formpanel_MailGroup;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		 if(this.status==1){
		  var histroyForm = new HistroyForm({
		   reqId : this.dataId,
		   reqClass : "com.zsgj.itil.require.entity.AccountApplyMainTable"
		  });
		  }else{
		         var histroyForm = new HistroyGrid({
		   reqId : this.dataId,
		   reqClass : "com.zsgj.itil.require.entity.AccountApplyMainTable"
		  });
		  }

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
		this.model = "ar_MailGroupApplys";

		this.getFormpanel_MailGroup();
		this.pa.push(this.formpanel_MailGroup);
		this.formname.push("panel_MailGroup");
		temp.push(this.formpanel_MailGroup);
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