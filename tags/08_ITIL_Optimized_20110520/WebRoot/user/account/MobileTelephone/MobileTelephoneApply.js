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

	getFormpanel_MobileTelephoneApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		var description=this.description;
		
		this.formpanel_MobileTelephoneApply_Input = new Ext.form.FormPanel({
			id : 'panel_MobileTelephoneApply_Input',
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
				name : 'sUserInfos$titleCode',
				mapping : 'sUserInfos$titleCode'
			}, {
				name : 'sUserInfos$postName',
				mapping : 'sUserInfos$postName'
			}, {
				name : 'itil_ac_MobileTelephoneApply$allowance',
				mapping : 'itil_ac_MobileTelephoneApply$allowance'
			}, {
				name : 'itil_ac_MobileTelephoneApply$deptAllowance',
				mapping : 'itil_ac_MobileTelephoneApply$deptAllowance'
			}, {
				name : 'itil_ac_MobileTelephoneApply$oldApply',
				mapping : 'itil_ac_MobileTelephoneApply$oldApply'
			}, {
				name : 'itil_ac_MobileTelephoneApply$applyId',
				mapping : 'itil_ac_MobileTelephoneApply$applyId'
			}, {
				name : 'itil_ac_MobileTelephoneApply$startMonth',
				mapping : 'itil_ac_MobileTelephoneApply$startMonth'
			}, {
				name : 'itil_ac_MobileTelephoneApply$id',
				mapping : 'itil_ac_MobileTelephoneApply$id'
			}, {
				name : 'itil_ac_MobileTelephoneApply$accountType',
				mapping : 'itil_ac_MobileTelephoneApply$accountType'
			}, {
				name : 'itil_ac_MobileTelephoneApply$accountowner',
				mapping : 'itil_ac_MobileTelephoneApply$accountowner'
			}, {
				name : 'itil_ac_MobileTelephoneApply$accountState',
				mapping : 'itil_ac_MobileTelephoneApply$accountState'
			}, {
				name : 'itil_ac_MobileTelephoneApply$createDate',
				mapping : 'itil_ac_MobileTelephoneApply$createDate'
			}, {
				name : 'itil_ac_MobileTelephoneApply$rightsDesc',
				mapping : 'itil_ac_MobileTelephoneApply$rightsDesc'
			}, {
				name : 'itil_ac_MobileTelephoneApply$remarkDesc',
				mapping : 'itil_ac_MobileTelephoneApply$remarkDesc'
			}, {
				name : 'itil_ac_MobileTelephoneApply$applyReason',
				mapping : 'itil_ac_MobileTelephoneApply$applyReason'
			}, {
				name : 'itil_ac_MobileTelephoneApply$payType',
				mapping : 'itil_ac_MobileTelephoneApply$payType'
			}, {
				name : 'itil_ac_MobileTelephoneApply$telephone',
				mapping : 'itil_ac_MobileTelephoneApply$telephone'
			}, {
				name : 'itil_ac_MobileTelephoneApply$oldTelephone',
				mapping : 'itil_ac_MobileTelephoneApply$oldTelephone'
			}, {
				name : 'itil_ac_MobileTelephoneApply$platForm',
				mapping : 'itil_ac_MobileTelephoneApply$platForm'
			}, {
				name : 'itil_ac_MobileTelephoneApply$startDate',
				mapping : 'itil_ac_MobileTelephoneApply$startDate'
			},
			{
				name : 'itil_ac_MobileTelephoneApply$countSign',
				mapping : 'itil_ac_MobileTelephoneApply$countSign'
			}
			]),
			title : "�ֻ���ȼ���������",
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
					lazyRender : true,
					hideTrigger : true,
					readOnly : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '��ѡ��...',
					allowBlank : true,
					typeAhead : true,
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
					fieldLabel : '����������ϵ�绰',
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
				}),

				{
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
					displayField : 'userName',
					valueField : 'id',
					emptyText : '������ITCODE����ѡ��...',
					allowBlank : false,
					listWidth:500,
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
						},
						'select' : function(combo, record, index) {
							var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
							if(userInfo==''||userInfo==null){
							
							}else{
							Ext.Ajax.request({
								url : webContext+ '/accountAction_getUserMobileTelephone.action',
								params : {
									userInfo : userInfo,
									accountType : '�ֻ�'
								},
								success : function(response, options) {
								var r = Ext.decode(response.responseText);
								if (!r.success) {
										var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
										Ext.Ajax.request({
											url : webContext+ '/accountAction_initUserInfoData.action',
											params : {
												userInfo : userInfo
											},
											success : function(response,options) {
												var r = Ext.decode(response.responseText);
												/*if(r.userType==7){
													Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲԱ������û��Ȩ���������룡",function(btn){
													 Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue('');
													Ext.getCmp('sUserInfos$costCenterCode').setValue('');
													Ext.getCmp('sUserInfos$employeeCode').setValue('');
													Ext.getCmp('sUserInfos$userTypeCombo').setValue('');
													Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue('');
													Ext.getCmp('sUserInfos$postName').setValue('');
													Ext.getCmp('sUserInfos$titleCode').setValue('');
													Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').setValue('');
												})}else if(r.userType==9){
													Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲ(����)Ա������û��Ȩ���������룡",function(btn){
													Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue('');
													Ext.getCmp('sUserInfos$costCenterCode').setValue('');
													Ext.getCmp('sUserInfos$employeeCode').setValue('');
													Ext.getCmp('sUserInfos$userTypeCombo').setValue('');
													Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue('');
													Ext.getCmp('sUserInfos$postName').setValue('');
													Ext.getCmp('sUserInfos$titleCode').setValue('');
													Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').setValue('');
												})}else{*/
													Ext.getCmp('sUserInfos$costCenterCode').setValue(r.costCenter);
													Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
													Ext.getCmp('sUserInfos$userTypeCombo').setValue(r.userType);
													Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);
													Ext.getCmp('sUserInfos$postName').setValue(r.postName);
													Ext.getCmp('sUserInfos$titleCode').setValue(r.titleCode);
													Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').setValue(r.allowance);
												//}
												
											},
											failure : function(response,
													options) {
												Ext.MessageBox.alert("��ʾ","���������ݼ���ʧ�ܣ�����ϵ����Ա����");
												var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
												return false;
											}
										}, this);

									} else {
										Ext.MessageBox.alert("��ʾ","�������Ѿ�������ֻ�����,���ܹ������룡");
										var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
										return false;

									}
								},

								failure : function(response, options) {

								}
							}, this);
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
					// hideTrigger:true,
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
					html : '��λ���:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '��λ���',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'sUserInfos$titleCode',
					name : 'sUserInfos$titleCode',
					style : '',
					width : 200,
					value : '',
					readOnly : true,
					emptyText : '�Զ�����',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '��λ����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '������',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'sUserInfos$postName',
					name : 'sUserInfos$postName',
					style : '',
					width : 200,
					value : '',
					readOnly : true,
					emptyText : '�Զ�����',
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
					hideTrigger : true,
					readOnly : true,
					lazyRender : true,
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
				}), 
			{
				html : '<font color=red>*</font>��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_MobileTelephoneApply$countSign',
				id : 'itil_ac_MobileTelephoneApply$countSignCombo',
				width : 200,
				style : '',
				editable : false,
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'department',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				name : 'itil_ac_MobileTelephoneApply$countSign',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.TelephoneCountSign',
					fields : ['id', 'department'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_MobileTelephoneApply$countSign'] == undefined) {
								opt.params['department'] = Ext
										.getCmp('itil_ac_MobileTelephoneApply$countSignCombo').defaultParam;
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
								department : param,
								start : 0
							}
						});
						return true;
					},
						'select' : function(combo, record, index) {
							var mailvalue = Ext
									.getCmp('itil_ac_MobileTelephoneApply$countSignCombo')
									.getRawValue();
							if (mailvalue == '��') {
								Ext.MessageBox.alert("��ʾ",
										"����ѡ�����Ĳ���,лл���ĺ�����", function(btn) {
											Ext
													.getCmp('AccountApplyMainTable$confirmUserCombo')
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
					html : '<font color=red>*</font>����ƽ̨:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_MobileTelephoneApply$platForm',
					id : 'itil_ac_MobileTelephoneApply$platFormCombo',
					width : 200,
					style : '',
					fieldLabel : '�����ص�',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					editable:false,
					
					displayField : 'name',
					valueField : 'id',
					emptyText : '��ѡ��...',
					allowBlank : false,
					typeAhead : true,
					name : 'itil_ac_MobileTelephoneApply$platForm',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.Platform',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['itil_ac_MobileTelephoneApply$platForm'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('itil_ac_MobileTelephoneApply$platFormCombo').defaultParam;
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
							var mailvalue = Ext
									.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
									.getRawValue();
							if (mailvalue == '��') {
								Ext.MessageBox.alert("��ʾ",
										"����ѡ������ƽ̨,лл���ĺ�����", function(btn) {
											Ext
													.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
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
						},
						'select' : function(combo, record, index) {
							var mailvalue = Ext
									.getCmp('AccountApplyMainTable$confirmUserCombo')
									.getRawValue();
							if (mailvalue == '��') {
								Ext.MessageBox.alert("��ʾ",
										"����ѡ������������,лл���ĺ�����", function(btn) {
											Ext
													.getCmp('AccountApplyMainTable$confirmUserCombo')
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
				})]
			}, 
			{
			xtype : 'fieldset',
		   layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :false,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:5px 0px 0px 0px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns :1
			},
			
			items:[ 
			{
				html : '<font size=3px><a href="" target="_blank"  style="text-decoration:none"><b>��ͨѶ����涨��</b></a></font>',
				cls : 'common-text',
				style : 'margin:0px 0px 0px 170px'
				
				
			}
			]
			},
				{
				xtype : 'fieldset',
				title : '�ֻ��Ѳ�������',
				layout : 'table',
				anchor : '100%',
				animCollapse : true,
				collapsible : true,
//				width:700,
				style : 'border:1px dotted #b0acac;margin:5px 0px 0px px',
				autoHeight : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				items : [{
					html : '<font color=red>*</font>�Ƿ����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					id : 'itil_ac_MobileTelephoneApply$payTypeCombo',
					style : '',
					mode : 'local',
					hiddenName : 'itil_ac_MobileTelephoneApply$payType',
					colspan : 0,
					rowspan : 0,
					triggerAction : 'all',
					typeAhead : true,
					forceSelection : true,
					allowBlank : false,
					editable : false,
					store : new Ext.data.SimpleStore({
						fields : ['id', 'name'],
						data : [['1', '��'], ['0', '��']]
					}),
					emptyText : '��ѡ��...',
					valueField : 'id',
					value : '',
					displayField : 'name',
					name : 'itil_ac_MobileTelephoneApply$payType',
					width : 200,
					fieldLabel : '�Ƿ����',
					listeners : {
						'expand' : function(combo) {
							combo.reset();
						}
					}
				}), {
					html : '������׼:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '������׼',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_MobileTelephoneApply$allowance',
					name : 'itil_ac_MobileTelephoneApply$allowance',
					style : '',
					readOnly:true,
					width : 200,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '<font color=red>*</font>�ֻ�����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '�ֻ�����',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_MobileTelephoneApply$telephone',
					name : 'itil_ac_MobileTelephoneApply$telephone',
					style : '',
					width : 200,
					value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}),

				{
					html : '����ȷ�ϲ�����׼:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '���Ų�����׼',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_MobileTelephoneApply$deptAllowance',
					name : 'itil_ac_MobileTelephoneApply$deptAllowance',
					style : '',
					width : 200,
					value : '',
					emptyText : '�������ⲹ����׼,�ɲ���',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '<font color=red>*</font>����/������ʼ����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '���ɱ����ʼ����',
					xtype : 'numberfield',
					colspan : 0,
					rowspan : 0,
					width : '195',
					id : 'itil_ac_MobileTelephoneApply$startDate',
					name : 'itil_ac_MobileTelephoneApply$startDate',
					style : '',
					minValue : 1,
					maxValue : 12,
				    value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}), {
					html : '��1��',
					cls : 'common-text',
					style : 'text-align:left;float:left'
				}, {},
					{
				html : '<font color=red>*</font>����ԭ��:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'itil_ac_MobileTelephoneApply$applyReason',
				colspan : 3,
				rowspan : 0,
				name : 'itil_ac_MobileTelephoneApply$applyReason',
				width : 530,
				style : '',
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
					id : 'itil_ac_MobileTelephoneApply$oldApply',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$oldApply',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '���ǰ����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_MobileTelephoneApply$applyId',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$applyId',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '������'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_MobileTelephoneApply$id',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�Զ����'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_MobileTelephoneApply$accountowner',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$accountowner',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�ֻ�������'
				})]
			}],
			buttons : [{
				text : '����Ϊ�ݸ�',
				id : 'save',
				iconCls : 'save',
				handler : function() {
					if (!Ext.getCmp('panel_MobileTelephoneApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					//add by liuying for ���Ӳ�����׼û�д�������֤ at 20100512 start
					var wance=Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').getValue();
					if(wance==0){
						Ext.MessageBox.alert("��ʾ",
								"����ְ��Ա�����������׼û���Զ�����������ϵ�ֻ�����Աȷ�϶�ȣ�");
						return false;
					}
					//add by liuying for ���Ӳ�����׼û�д�������֤ at 20100512 end
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","�����˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					if(delegateApplyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","�������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var info = Ext.encode(getFormParam('panel_MobileTelephoneApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveMobileTelephoneApplyDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							accountType : '�ֻ�',
							panelName : 'panel_MobileTelephoneApply_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_MobileTelephoneApply_Input')
									.load({
										url : webContext
												+ '/accountAction_getMobileTelephoneDraftData.action?panelName=panel_MobileTelephoneApply_Input&dataId='
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

					if (!Ext.getCmp('panel_MobileTelephoneApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					//add by liuying for ���Ӳ�����׼û�д�������֤ at 20100512 start
					var wance=Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').getValue();
					if(wance==0){
						Ext.MessageBox.alert("��ʾ",
								"����ְ��Ա�����������׼û���Զ���������ڶ������ύ�����룡");
						return false;
					}
					//add by liuying for ���Ӳ�����׼û�д�������֤ at 20100512 end
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
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							 if(responseArray.success){	
							 	Ext.MessageBox.alert("��ʾ","�������Ѵ��������е��ֻ�����,���ܹ���������룡",function(btn){
		                      	window.history.back(-1);
		                      	});
							 }else{
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
						if(applyUser==''||applyUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if(confirmUser==''||confirmUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","�����˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					if(delegateApplyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","�������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var info = Ext.encode(getFormParam('panel_MobileTelephoneApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveMobileTelephoneApplyDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							accountType : '�ֻ�',
							panelName : 'panel_MobileTelephoneApply_Input'
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
							var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo')
									.getValue();
									var department = Ext
									.getCmp('itil_ac_MobileTelephoneApply$countSignCombo')
									.getValue();
							// Ext.MessageBox.alert("����ɹ�");
							// //////////////////////////////////////////////////////////////////

							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_applyMobileTelephoneApply.action',
								params : {
									dataId : curId,
									userInfo : userInfo,
									description:description,
									department:department,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject',"
											+ "applyTypeName: '�ֻ���ȼ���������',"
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
											"�ύ����ʧ��,�����������Ƿ�ѡ����ȷ!");
									Ext.getCmp("save").enable();
									Ext.getCmp("submit").enable();
									Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ",
									"�ύ����ʧ��,���������˻��������Ƿ�ѡ����ȷ!");
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
						+ '/accountAction_getUserMobileTelephoneInfo.action',
				params : {
					accountType : '�ֻ�'
				},

				success : function(response, options) {
					var responseArray = Ext.util.JSON
							.decode(response.responseText);
					if (responseArray.success) {

						Ext.getCmp('panel_MobileTelephoneApply_Input').form
								.load({
									url : webContext
											+ '/accountAction_initPersonAccountDeleteData.action',
									params : {
										serviceItemId : this.serviceItemId,
										processType : this.processType,
										panelName : 'panel_MobileTelephoneApply_Input'
									},
									timeout : 30,
									success : function(action, form) {
				

										Ext
												.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
												.initComponent();

									},
									failure : function(response, options) {

									}
								})

					} else {

						var curId = responseArray.id;
						Ext.getCmp('panel_MobileTelephoneApply_Input').form
								.load({
									url : webContext
											+ '/accountAction_initPersonAccountApplyData.action',
									params : {
										serviceItemId : this.serviceItemId,
										processType : this.processType,
										panelName : 'panel_MobileTelephoneApply_Input'
									},
									timeout : 30,
									success : function(action, form) {
							var userType= Ext.getCmp("sUserInfos$userTypeCombo").getValue();
//							 if(userType==7){
//								Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲԱ������û��Ȩ���������룡",function(btn){
//								window.history.back(-1);
//							})}
//							 if(userType==9){
//								Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲ(����)Ա������û��Ȩ���������룡",function(btn){
//								window.history.back(-1);
//							})}
										Ext
												.getCmp("AccountApplyMainTable$applyUserCombo")
												.initComponent();
										Ext
												.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
												.initComponent();
										Ext
												.getCmp("AccountApplyMainTable$confirmUserCombo")
												.initComponent();
												
									},
									failure : function(response, options) {

									}
								})
					}

				},
				failure : function(response, options) {

				}
			}, this);

		} else {
			this.formpanel_MobileTelephoneApply_Input.load({
				url : webContext
						+ '/accountAction_getMobileTelephoneDraftData.action?panelName=panel_MobileTelephoneApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
							 Ext.getCmp("itil_ac_MobileTelephoneApply$platFormCombo")
							.initComponent();
							 Ext.getCmp("itil_ac_MobileTelephoneApply$countSignCombo")
							.initComponent();
				}
			});
		}
		return this.formpanel_MobileTelephoneApply_Input;
	},
	items : this.items,

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
		this.model = "ar_MobileTelephoneApply";

		this.getFormpanel_MobileTelephoneApply_Input();
		this.pa.push(this.formpanel_MobileTelephoneApply_Input);
		this.formname.push("panel_MobileTelephoneApply_Input");
		temp.push(this.formpanel_MobileTelephoneApply_Input);
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