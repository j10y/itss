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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue><font color=red>����ʱҪ�ϸ���Ȩ</font>�����������˲�ͨ��������ҳ���·���<font color=red>�����ء�</font>��ť</font>')]

		});
		return this.tabPanel;

	},

	getFormpanel_HRSAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId=this.serviceItemProcess;
		var tkId = this.taskId;
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
				name : 'HRSAccountApply$workDuty',
				mapping : 'HRSAccountApply$workDuty'
			}, {
				name : 'HRSAccountApply$applyUser',
				mapping : 'HRSAccountApply$applyUser'
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
			},
			{
				name : 'HRSAccountApply$isChangeRight',
				mapping : 'HRSAccountApply$isChangeRight'
			},
		
			{
				name : 'HRSAccountApply$delegateApplyTel',
				mapping : 'HRSAccountApply$delegateApplyTel'
			}, {
				name : 'HRSAccountApply$delegateApplyUser',
				mapping : 'HRSAccountApply$delegateApplyUser'
			}, {
				name : 'HRSAccountApply$applyDate',
				mapping : 'HRSAccountApply$applyDate'
			}]),
			title : "HRS�ʺ���Ա��������",
			items : [{
			xtype : 'fieldset',
		    title : '��������Ϣ',
		    layout : 'table',
		    anchor : '100%',
			autoHeight : true,
			animCollapse:true,
		    collapsible :true,
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
				id : 'HRSAccountApply$name',
				name : 'HRSAccountApply$name',
				style : '',
				width : 200,
				value : '',
				readOnly : true,
				emptyText : '�Զ�����',
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
			 {
				html : '��������:',
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
				hideTrigger:true,
				readOnly : true,
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '��������'
			}),
					{
				html : '��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'HRSAccountApply$delegateApplyUser',
				id : 'HRSAccountApply$delegateApplyUserCombo',
				width : 200,
				style : '',
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				hideTrigger:true,
				readOnly : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				typeAhead : true,
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
			}), 
			{
				html : '<font color=red>*</font>����������ϵ�绰:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '����������ϵ�绰',
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
				html : '<font color=red>*</font>�ʺ�����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'HRSAccountApply$accountName',
				id : 'HRSAccountApply$accountNameCombo',
				width : 200,
				style : '',
				fieldLabel : '�ʺ�����',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'accountName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				
				typeAhead : true,
				name : 'HRSAccountApply$accountName',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext + "/accountAction_listHRSAccountName.action",
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
				   var accountName = Ext.getCmp('HRSAccountApply$accountNameCombo').getRawValue();
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
						 	
						 	Ext.getCmp('operationScopeCombo').setValue(r.os);
						 		Ext.getCmp('isReferMoneyCombo').setValue(r.referMoney);
						 			Ext.getCmp('userRightCombo').setValue(r.hr);
                            	
                            	
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
				html : '<font color=red>*</font>������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'HRSAccountApply$applyUser',
				id : 'HRSAccountApply$applyUserCombo',
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
				typeAhead : true,
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
							var userInfo = Ext.getCmp('HRSAccountApply$applyUserCombo').getValue();
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
						 	
						 	Ext.getCmp('sUserInfos$costCenterCode').setValue(r.costCenter);
						 		Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
						 			Ext.getCmp('sUserInfos$sameMailDeptCombo').setValue(r.name);
                            	
                            	
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
				id : 'HRSAccountApply$applyTel',
				name : 'HRSAccountApply$applyTel',
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
			},new Ext.form.ComboBox({
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
				hideTrigger:true,
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
							Ext.getCmp('sUserInfos$userTypeCombo').setValue(Ext
									.getCmp('sUserInfos$userTypeCombo')
									.getValue());
						}
					});
				}
			}),  
				{
				html : '<font color=red>*</font>�ʺ�������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'HRSAccountApply$accountManger',
				id : 'HRSAccountApply$accountMangerCombo',
				width : 200,
				style : '',
				fieldLabel : '�ʺ�������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				typeAhead : true,
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
			}),
			{
				html : '<font color=red>*</font>���ž�������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'HRSAccountApply$confirmUser',
				id : 'HRSAccountApply$confirmUserCombo',
				width : 200,
				style : '',
				fieldLabel : '���ž�������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				typeAhead : true,
				name : 'HRSAccountApply$confirmUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['HRSAccountApply$confirmUser'] == undefined) {
								opt.params['userName'] = Ext
										.getCmp('HRSAccountApply$confirmUserCombo').defaultParam;
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
			})
			]}, 
			
			 {
			xtype : 'fieldset',
		    title : '�����ʺ���Ϣ',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
		    style : 'border:1px dotted #b0acac;margin:15px 0px 0px px',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[
			
			{
				html : '����ԭ��:',
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
				allowBlank : true,
				validator : '',
				fieldLabel : '����ԭ��'
			}), 
			{
				html : '����ְ��:',
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
				allowBlank : true,
				validator : '',
				fieldLabel : '����ְ��'
			}),
				{
				html : '�Ƿ����Ȩ��:',
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
				displayField : 'name',
				name : 'HRSAccountApply$isChangeRight',
				width : 200,
				fieldLabel : '�Ƿ��漰н��',
				listeners : {
					'expand' : function(combo) {
						combo.reset();
					},
					'select':function(combo){
						var result=Ext.getCmp('HRSAccountApply$isChangeRightCombo').getValue();
						
						if(result==1){
							Ext.getCmp('newRight').expand(true);
						}else{
							Ext.getCmp('newRight').collapse(true); 
						}
						
					}
				}
			}),{},{},
			
			
			{
			xtype : 'fieldset',
		    id:'newRight',
			layout : 'table',
		    anchor : '100%',
		    colspan : 4,
			rowspan : 0,
		   animCollapse:true,
			collapsible :true,
			autoHeight : true,
		    style : 'border:1px dotted #b0acac;margin:15px 0px 0px 25px',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[
			{
				html : 'ԭ�û�Ȩ��:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'userRight',
				id : 'userRightCombo',
				width : 200,
				style : '',
				fieldLabel : '�û�Ȩ��',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				hideTrigger:true,
				readOnly : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				typeAhead : true,
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
							Ext
									.getCmp('userRightCombo')
									.setValue(Ext
											.getCmp('userRightCombo')
											.getValue());
						}
					});
				}
			}), {
				html : '������Χ:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'operationScope',
				id : 'operationScopeCombo',
				width : 200,
				style : '',
				fieldLabel : '������Χ',
				colspan : 0,
				rowspan : 0,
				hideTrigger:true,
				readOnly : true,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				typeAhead : true,
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
							id : Ext
									.getCmp('operationScopeCombo')
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
			}), 
				{
				html : '�Ƿ��漰н��:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				id : 'isReferMoneyCombo',
				style : '',
				mode : 'local',
				hideTrigger:true,
				readOnly : true,
				hiddenName : 'isReferMoney',
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
				displayField : 'name',
				name : 'isReferMoney',
				width : 200,
				fieldLabel : '�Ƿ��漰н��',
				listeners : {
					'expand' : function(combo) {
						combo.reset();
					}
				}
			}),
			{
				html : '<font color=red>*</font>���û�Ȩ��:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'HRSAccountApply$userRight',
				id : 'HRSAccountApply$userRightCombo',
				width : 200,
				style : '',
				fieldLabel : '�û�Ȩ��',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				typeAhead : true,
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
							id : Ext.getCmp('HRSAccountApply$userRightCombo')
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
				html : '<font color=red>*</font>�²�����Χ:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'HRSAccountApply$operationScope',
				id : 'HRSAccountApply$operationScopeCombo',
				width : 200,
				style : '',
				fieldLabel : '������Χ',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : true,
				typeAhead : true,
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
			}), 
				{
				html : '�Ƿ��漰н��:',
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
				displayField : 'name',
				name : 'HRSAccountApply$isReferMoney',
				width : 200,
				fieldLabel : '�Ƿ��漰н��',
				listeners : {
					'expand' : function(combo) {
						combo.reset();
					}
				}
			})]}
			, new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$id',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$oldApply',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$oldApply',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '���ǰ����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$processType',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$processType',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '��������'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$status',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$status',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '״̬'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$deleteFlag',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$deleteFlag',
				width : 200,
				style : '',
				value : '',
				fieldLabel : 'ɾ��״̬'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$serviceItem',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$serviceItem',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '��������'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$accountState',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$accountState',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�ʺ�״̬'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$createUser',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$createUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '������'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$modifyUser',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$modifyUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '����޸���'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'HRSAccountApply$modifyDate',
				colspan : 0,
				rowspan : 0,
				name : 'HRSAccountApply$modifyDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '����޸�����'
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
			})]}],
			
	buttons : [{
				text : '����',
				iconCls : 'submit',
				handler : function() {
					if (!Ext.getCmp('panel_HRSAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("��ʾ",
								"ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
						return false;
					}
					var dataId=Ext.getCmp('HRSAccountApply$id').getValue();
				    var accountName=Ext.getCmp('HRSAccountApply$accountNameCombo').getRawValue();
					var userInfo=Ext.getCmp('HRSAccountApply$confirmUserCombo').getValue();
					Ext.Ajax.request({
			        url : webContext+ '/accountAction_saveHRSAccountInfo.action',
			        params : {
							accountName : accountName,
							dataId:dataId,
							userInfo:userInfo
						}, 
						 success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				tempUrl = webContext
							+ '/extjs/workflow?method=getData&taskId=' + tkId
							+ '&users=confirmByDM:' + userInfo
	
				Ext.Ajax.request({
					url : tempUrl,
					method : 'post',
					success : function(response, options) {
						 window.parent.auditContentWin.specialAudit();
					},
					failure : function(response, options) {
						//Ext.getCmp('submitButton').enabled();
					}
				})
			},

			 
				
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
				}
			},
			{
				text : '����',
				iconCls : 'back',
				handler : function() {
					window.parent.auditContentWin.specialNoAudit();
			  }}
				],
				buttonAlign:'center'
			
			
		
		});
		if (this.dataId == "0" || this.dataId == "null") {
			this.formpanel_HRSAccountApply_Input.load({
				     url : webContext
						    + '/accountAction_initPersonAccountDeleteData.action',
				                params : {
							    serviceItemId : this.serviceItemId,
							    processType :this.processType,
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
		}else{
			this.formpanel_HRSAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getHRSApplyDraftData.action?panelName=panel_HRSAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					
					var accountName = Ext.getCmp('HRSAccountApply$accountNameCombo').getRawValue();
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
						 	
						 	Ext.getCmp('operationScopeCombo').setValue(r.os);
						 		Ext.getCmp('isReferMoneyCombo').setValue(r.referMoney);
						 			Ext.getCmp('userRightCombo').setValue(r.hr);
                            	
                            	  Ext.getCmp("HRSAccountApply$applyUserCombo")
							           .initComponent();
							           Ext.getCmp("HRSAccountApply$delegateApplyUserCombo")
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
	 * clientvalidation ��ʼ��
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
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})