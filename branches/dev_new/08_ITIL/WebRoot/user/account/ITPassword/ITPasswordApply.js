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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>1.ҳ���д���ɫ<font color=red>*</font>�ŵı����������д���������ύ���룡<font color=red>*2.���»�ȡIT������Ҫ�����Чʱ��</font></font>')]

		});
		return this.tabPanel;

	},

	getFormpanel_ITPasswordApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		this.formpanel_ITPasswordApply_Input = new Ext.form.FormPanel({
			id : 'panel_ITPasswordApply_Input',
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
				name : 'sUserInfos$employeeCode',
				mapping : 'sUserInfos$employeeCode'
			}, {
				name : 'sUserInfos$itcode',
				mapping : 'sUserInfos$itcode'
			}, {
				name : 'sUserInfos$costCenterCode',
				mapping : 'sUserInfos$costCenterCode'
			}, {
				name : 'sUserInfos$userType',
				mapping : 'sUserInfos$userType'
			},
				{
				name : 'itil_ac_ITPassword$mailType',
				mapping : 'itil_ac_ITPassword$mailType'
			},
				{
				name : 'itil_ac_ITPassword$dcMail',
				mapping : 'itil_ac_ITPassword$dcMail'
			},
				{
				name : 'itil_ac_ITPassword$webMail',
				mapping : 'itil_ac_ITPassword$webMail'
			},
				{
				name : 'itil_ac_ITPassword$id',
				mapping : 'itil_ac_ITPassword$id'
			},
			

			{
				name : 'AccountApplyMainTable$confirmUser',
				mapping : 'AccountApplyMainTable$confirmUser'
			}]),
			title : "Itpassword������������",
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
					hideTrigger : true,
					readOnly : true,
					allowBlank : true,
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
					listWidth:500,
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
							var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo')
									.getValue();
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
								},
								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									Ext.getCmp('sUserInfos$costCenterCode')
											.setValue(r.costCenter);
									Ext.getCmp('sUserInfos$employeeCode')
											.setValue(r.employeeCode);
									Ext.getCmp('sUserInfos$userTypeCombo')
											.setValue(r.userType);
									Ext
											.getCmp('AccountApplyMainTable$applyUserTel')
											.setValue(r.telephone);

								},

								failure : function(response, options) {

								}
							}, this);

						}
					}
					,
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
					listWidth:500,
					colspan : 0,
					rowspan : 0,
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
					if(mailvalue=='��'){
					Ext.MessageBox.alert("��ʾ","����ѡ������������,лл���ĺ�����",function(btn){
					Ext.getCmp('AccountApplyMainTable$confirmUserCombo').setValue("");
					return false;
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
					html : '<font color="red">*</font>������IT���������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					id : 'itil_ac_ITPassword$mailType',
					style : 'float:left;align:left',
					mode : 'local',
					colspan : 0,
					rowspan : 0,
					triggerAction : 'all',
					
					editable : false,
					forceSelection : true,
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						fields : ['id', 'name'],
						data : [['1', '�Լ���˾����'], ['2', 'ͬ�¹�˾����'],
								['3', '�Լ�˽������']]
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
							var value = Ext
									.getCmp('itil_ac_ITPassword$mailType')
									.getValue();
							if (value == 1) {
								var userName = Ext.getCmp("sUserInfos$itcode")
										.getValue();
								Ext.getCmp("itil_ac_ITPassword$dcMailCombo")
										.setValue(userName.toLowerCase());
								Ext.getCmp("itil_ac_ITPassword$dcMailCombo")
										.disable();
								Ext.getCmp("itil_ac_ITPassword$webMail")
										.disable();
								Ext.getCmp("itil_ac_ITPassword$webMail")
										.setValue("");
							} else if (value == 2) {
								Ext.getCmp('domain').show();
								Ext.getCmp("itil_ac_ITPassword$dcMailCombo")
										.enable();
								Ext.getCmp("itil_ac_ITPassword$webMail")
										.disable();
								Ext.getCmp("itil_ac_ITPassword$webMail")
										.setValue("");
								Ext.getCmp("itil_ac_ITPassword$dcMailCombo").allowBlank = false;
							} else {
								Ext.getCmp('itil_ac_ITPassword$webMail').enable();
								Ext.getCmp('itil_ac_ITPassword$dcMailCombo')
										.disable();
								Ext.getCmp("itil_ac_ITPassword$dcMailCombo")
										.setValue("");	
								

							}
						}
					}
				}), {
					html : '����IT�����<br>�ⲿ����:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '�ɱ����ĺ�',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_ITPassword$webMail',
					name : 'itil_ac_ITPassword$webMail',
					style : '',
					width : 200,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : 'email'
				}),

				{
					html : 'DC��������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_ITPassword$dcMail',
					id : 'itil_ac_ITPassword$dcMailCombo',
					width : 200,
					style : '',
					fieldLabel : '��������',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'userNames',
					valueField : 'userNames',
					emptyText : '',
					allowBlank : true,
					name : 'NotesIDFile$dcMail',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.itil.account.entity.DCContacts',
						fields : ['id', 'userNames'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['itil_ac_ITPassword$dcMail'] == undefined) {
									opt.params['userNames'] = Ext
											.getCmp('itil_ac_ITPassword$dcMailCombo').defaultParam;
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
							var pid = Ext
									.getCmp('itil_ac_ITPassword$mailType')
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
									userName : param,
									start : 0
								}
							});
							return true;
						},
					'select' : function(combo, record, index) {
					var mailvalue=Ext.getCmp('itil_ac_ITPassword$dcMailCombo').getRawValue();
					if(mailvalue=='��'){
					Ext.MessageBox.alert("��ʾ","����ѡ������DC����,лл���ĺ�����",function(btn){
					Ext.getCmp('itil_ac_ITPassword$dcMailCombo').setValue("");
					return false;
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
					html : '@zsgj.com',
					id : 'domain',
					cls : 'common-text',
					style : 'width:135;text-align:left'
				}

				]
			}, {

				items : [
				new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_ITPassword$id',
					name : 'itil_ac_ITPassword$id',
					style : '',
					value : '',
					fieldLabel : ''
				}),

				new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'AccountApplyMainTable$attachment',
					name : 'AccountApplyMainTable$attachment',
					style : '',
					value : 'null',
					fieldLabel : 'nowtime'
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
					id : 'sUserInfos$itcode',
					colspan : 0,
					rowspan : 0,
					name : 'sUserInfos$itcode',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '�������û���'
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
				})]
			},{
			xtype : 'fieldset',
		    title : '<font color=red>ITPASSWORD�������:</font>',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
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
				html : '<font color="blue"><B>DC������</B></font>����½zsgj������'+
						'<br><font color="blue"><B>ESSϵͳ����</B></font>��HR�����������롪5������Ч'+
						'<br><font color="blue"><B>OAЭͬ����</B></font>��OAӦ�����롪2Сʱ��Ч'+
						'<br><font color="blue"><B>��ʱ��Ϣ����</B></font>��NOTES�еļ�ʱ��Ϣ���루�������������룩��2Сʱ��Ч'+
						'<br>����ITPASSWORD2Сʱ���벻Ҫ�������ԡ�'+
						'<br>ϵͳ��ʼIT�����ָ���ʼ������յ�����IT@zsgj.com����Ϊ������IT�ʻ�����ITpassword����4-5���ʼ�����ֻ��ʹ������һ���ʼ����ʼ������ں���ϵͳ������ɵ�IT���롣�������˿ɵ�½http://am.zsgj.com���¼��ҳ���������ITPASSWORD�� ά��IT���롣',
						
				cls : 'common-text',
				style : ';margin:0px 0px 0px 70px'
				
			}
			]
			}],
			buttons : [{
				text : '����Ϊ�ݸ�',
				iconCls : 'save',
				id : 'save',
				handler : function() {
					if (!Ext.getCmp('panel_ITPasswordApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$delegateApplyUserCombo')
							.getValue();
					//add by liuying for �����˱���������б���ѡ�����֤ start
					var appUser=
							Ext.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					//add by liuying for �����˱���������б���ѡ�����֤ end
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
					if(appUser==''||appUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					
					if(confirmUser==''||confirmUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("��ʾ", "�������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
						return false;
					}
					var value = Ext
									.getCmp('itil_ac_ITPassword$mailType')
									.getValue();
					var dc= Ext
							.getCmp('itil_ac_ITPassword$dcMailCombo')
							.getValue();
				   if((value==1||value==2)&&(dc==''||dc==null)){
						Ext.MessageBox.alert("��ʾ","DC�������Ʊ���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					var webMail=Ext.getCmp('itil_ac_ITPassword$webMail').getValue();
					 if((value==3)&&(webMail==''||webMail==null)){
						Ext.MessageBox.alert("��ʾ","����IT������ⲿ���䲻��Ϊ��,лл���ĺ���!");
						return false;
					}
					
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					Ext.getCmp("itil_ac_ITPassword$dcMailCombo").enable();
					var info = Ext.encode(getFormParam('panel_ITPasswordApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveITPassword.action',
						params : {
							info : info,
							value:value,
							webMail:webMail,
							dc:dc,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							panelName : 'panel_ITPasswordApply_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
						
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
					if (!Ext.getCmp('panel_ITPasswordApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$delegateApplyUserCombo')
							.getValue();
					//add by liuying for �����˱���������б���ѡ�����֤ start
					var appUser=
							Ext.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					//add by liuying for �����˱���������б���ѡ�����֤ end
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
					if(appUser==''||appUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					
					if(confirmUser==''||confirmUser==null){
						Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("��ʾ", "�������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
						return false;
					}
					var value = Ext
									.getCmp('itil_ac_ITPassword$mailType')
									.getValue();
					var dc= Ext
							.getCmp('itil_ac_ITPassword$dcMailCombo')
							.getValue();
				   if((value==1||value==2)&&(dc==''||dc==null)){
						Ext.MessageBox.alert("��ʾ","DC�������Ʊ���������б���ѡ��,лл���ĺ���!");
						return false;
					}
					var webMail=Ext.getCmp('itil_ac_ITPassword$webMail').getValue();
					 if((value==3)&&(webMail==''||webMail==null)){
						Ext.MessageBox.alert("��ʾ","����IT������ⲿ���䲻��Ϊ��,лл���ĺ���!");
						return false;
					}
					
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					Ext.getCmp("itil_ac_ITPassword$dcMailCombo").enable();
					var info = Ext.encode(getFormParam('panel_ITPasswordApply_Input'));
					var uInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getRawValue();
					Ext.Msg.show({
												   title:'ϵͳ��ʾ',
												   msg: '�������� <font color="#FF0000">'+uInfo+'</font> ��ITPASSWORD���룬��˶�ȷ�ϣ�',
												   buttons: Ext.Msg.OKCANCEL,
												   fn:function(btn){
													   if(btn=='ok'){
													   Ext.Ajax.request({
															url : webContext
																	+ '/accountAction_saveITPassword.action',
															params : {
																info : info,
																value:value,
																webMail:webMail,
																dc:dc,
																serviceItemId : curscid,
																processInfoId : processInfoId,
																processType : processType,
																panelName : 'panel_ITPasswordApply_Input'
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
																				+ "applyTypeName: 'Itpassword������������',"
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
																				"�ύ����ʧ��,����ҳ�������Ƿ�ѡ����ȷ!");
																		Ext.getCmp("save").enable();
																		Ext.getCmp("submit").enable();
																		Ext.getCmp("back").enable();
																	}
																}, this);
									
																// ///////////////////////////////////////////////////////////////////
									
															},
															failure : function(response, options) {
																Ext.MessageBox.alert("��ʾ",
																		"�ύ����ʧ��,����ҳ�������Ƿ�ѡ����ȷ!");
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
			Ext.getCmp('panel_ITPasswordApply_Input').form.load({
				url : webContext
						+ '/accountAction_initPersonAccountApplyData.action',
				params : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_ITPasswordApply_Input'
				},
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.setValue("");
					Ext.getCmp("AccountApplyMainTable$applyUserTel")
							.setValue("");
					Ext.getCmp("sUserInfos$employeeCode")
							.setValue("");
					Ext.getCmp("sUserInfos$costCenterCode")
							.setValue("");
					Ext.getCmp("sUserInfos$userTypeCombo")
							.setValue("");
							
					Ext.getCmp("itil_ac_ITPassword$webMail").disable();

				},
				failure : function(response, options) {

				}
			});
		} else {
			this.formpanel_ITPasswordApply_Input.load({
				url : webContext
						+ '/accountAction_getITPasswordDraftData.action?panelName=panel_ITPasswordApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
					var value = Ext
									.getCmp('itil_ac_ITPassword$mailType')
									.getValue();
			         if(value==3){
			         	Ext.getCmp("itil_ac_ITPassword$dcMailCombo").disable();
			         }else{
			         	Ext.getCmp("itil_ac_ITPassword$webMail").disable();
			         }

					
				}
			});

		}
		return this.formpanel_ITPasswordApply_Input;
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
		this.model = "ar_ITPasswordApply";

		this.getFormpanel_ITPasswordApply_Input();
		this.pa.push(this.formpanel_ITPasswordApply_Input);
		this.formname.push("panel_ITPasswordApply_Input");
		temp.push(this.formpanel_ITPasswordApply_Input);

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