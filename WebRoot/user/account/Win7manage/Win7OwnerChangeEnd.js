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

	getFormpanel_SpecailAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId=this.serviceItemProcess;
		this.formpanel_SpecailAccountApply_Input = new Ext.form.FormPanel({
			id : 'panel_SpecailAccountApply_Input',
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
			}, {
				name : 'itil_ac_SpecialAccount$id',
				mapping : 'itil_ac_SpecialAccount$id'
			}, {
				name : 'itil_ac_SpecialAccount$accountName',
				mapping : 'itil_ac_SpecialAccount$accountName'
			}, {
				name : 'itil_ac_SpecialAccount$password',
				mapping : 'itil_ac_SpecialAccount$password'
			}, {
				name : 'itil_ac_SpecialAccount$accountType',
				mapping : 'itil_ac_SpecialAccount$accountType'
			}, {
				name : 'itil_ac_SpecialAccount$accountOldUser',
				mapping : 'itil_ac_SpecialAccount$accountOldUser'
			}, {
				name : 'itil_ac_SpecialAccount$accountNowUser',
				mapping : 'itil_ac_SpecialAccount$accountNowUser'
			}, {
				name : 'itil_ac_SpecialAccount$accountState',
				mapping : 'itil_ac_SpecialAccount$accountState'
			}, {
				name : 'itil_ac_SpecialAccount$createDate',
				mapping : 'itil_ac_SpecialAccount$createDate'
			}, {
				name : 'itil_ac_SpecialAccount$registerServiceRightDesc',
				mapping : 'itil_ac_SpecialAccount$registerServiceRightDesc'
			}, {
				name : 'itil_ac_SpecialAccount$sameRightAccount',
				mapping : 'itil_ac_SpecialAccount$sameRightAccount'
			}, {
				name : 'itil_ac_SpecialAccount$rightsDesc',
				mapping : 'itil_ac_SpecialAccount$rightsDesc'
			}, {
				name : 'itil_ac_SpecialAccount$remarkDesc',
				mapping : 'itil_ac_SpecialAccount$remarkDesc'
			}, {
				name : 'itil_ac_SpecialAccount$attachment',
				mapping : 'itil_ac_SpecialAccount$attachment'
			}, {
				name : 'itil_ac_SpecialAccount$applyReason',
				mapping : 'itil_ac_SpecialAccount$applyReason'
			}, {
				name : 'itil_ac_SpecialAccount$confirmUser',
				mapping : 'itil_ac_SpecialAccount$confirmUser'
			}, {
				name : 'itil_ac_SpecialAccount$mailValue',
				mapping : 'itil_ac_SpecialAccount$mailValue'
			}, {
				name : 'itil_ac_SpecialAccount$wwwAccountValue',
				mapping : 'itil_ac_SpecialAccount$wwwAccountValue'
			}, {
				name : 'itil_ac_SpecialAccount$referSalary',
				mapping : 'itil_ac_SpecialAccount$referSalary'
			}, {
				name : 'itil_ac_SpecialAccount$telephone',
				mapping : 'itil_ac_SpecialAccount$telephone'
			}, {
				name : 'itil_ac_SpecialAccount$registerServiceType',
				mapping : 'itil_ac_SpecialAccount$registerServiceType'
			}, {
				name : 'itil_ac_SpecialAccount$dutyName',
				mapping : 'itil_ac_SpecialAccount$dutyName'
			}, {
				name : 'itil_ac_SpecialAccount$thingCode',
				mapping : 'itil_ac_SpecialAccount$thingCode'
			}, {
				name : 'itil_ac_SpecialAccount$controlScope',
				mapping : 'itil_ac_SpecialAccount$controlScope'
			}, {
				name : 'itil_ac_SpecialAccount$userRight',
				mapping : 'itil_ac_SpecialAccount$userRight'
			}, {
				name : 'itil_ac_SpecialAccount$operatorScope',
				mapping : 'itil_ac_SpecialAccount$operatorScope'
			}, {
				name : 'itil_ac_SpecialAccount$erpUserName',
				mapping : 'itil_ac_SpecialAccount$erpUserName'
			}, {
				name : 'itil_ac_SpecialAccount$workSpace',
				mapping : 'itil_ac_SpecialAccount$workSpace'
			}, {
				name : 'itil_ac_SpecialAccount$mailServer',
				mapping : 'itil_ac_SpecialAccount$mailServer'
			}, {
				name : 'itil_ac_SpecialAccount$endDate',
				mapping : 'itil_ac_SpecialAccount$endDate'
			}, {
				name : 'itil_ac_SpecialAccount$otherLinkCompany',
				mapping : 'itil_ac_SpecialAccount$otherLinkCompany'
			}, {
				name : 'itil_ac_SpecialAccount$drawSpace',
				mapping : 'itil_ac_SpecialAccount$drawSpace'
			}, {
				name : 'itil_ac_SpecialAccount$ifHold',
				mapping : 'itil_ac_SpecialAccount$ifHold'
			}, {
				name : 'itil_ac_SpecialAccount$ifLocked',
				mapping : 'itil_ac_SpecialAccount$ifLocked'
			}, {
				name : 'itil_ac_SpecialAccount$olodApplyAccount',
				mapping : 'itil_ac_SpecialAccount$olodApplyAccount'
			}, {
				name : 'itil_ac_SpecialAccount$applyId',
				mapping : 'itil_ac_SpecialAccount$applyId'
			}]),
			title : "Win7�ʺ������߱������",
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
			
			items:[{
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
				hideTrigger:true,
				readOnly : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '��������'
			}),
			{
				html : '<font color=red>*</font>��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$delegateApplyUser',
				id : 'AccountApplyMainTable$delegateApplyUserCombo',
				width : 200,
				style : '',
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				hideTrigger:true,
				readOnly : true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				
				name : 'AccountApplyMainTable$delegateApplyUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
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
				html : '<font color=red>*</font>�ʺ�ԭ������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_SpecialAccount$accountOldUser',
				id : 'itil_ac_SpecialAccount$accountOldUserCombo',
				width : 200,
				style : '',
				fieldLabel : '�ʺ�ԭ������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				
				name : 'itil_ac_SpecialAccount$accountOldUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_SpecialAccount$accountOldUser'] == undefined) {
								opt.params['userName'] = Ext
										.getCmp('itil_ac_SpecialAccount$accountOldUserCombo').defaultParam;
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
					},
						'select':function(combo, record, index){
							
										Ext.getCmp('itil_ac_SpecialAccount$accountNameCombo').setValue('');
										var userInfo=Ext.getCmp('itil_ac_SpecialAccount$accountOldUserCombo').getValue();
										Ext.Ajax.request({
												url : webContext
														+ '/accountAction_getUserSpecailAccount.action',
												params : {
													accountType:'Win7',
													userInfo:userInfo
												},
						
												success : function(response, options) {
												var responseArray = Ext.util.JSON.decode(response.responseText);
												if(responseArray.success){
													   var userInfo=Ext.getCmp('itil_ac_SpecialAccount$accountOldUserCombo').getValue();
													   Ext.Ajax.request({
																url : webContext
																		+ '/accountAction_initUserInfoData.action',
																params : {
																	userInfo : userInfo
																},
								
																success : function(response, options) {
																	var r = Ext.decode(response.responseText);
																	Ext.getCmp('itil_ac_SpecialAccount$telephone').setValue(r.telephone);
																},
								
															    failure : function(response, options) {
																
								
																}
															}, this);
													
												}else{
											
														Ext.Msg.show({
																		   title:'��ʾ',
																		   msg: "���û�����û��Win7�ʺ�!",
																		   buttons: Ext.Msg.OK,
																		   fn:function(button,text){
																		    if(button=='ok'){
																				Ext.getCmp('itil_ac_SpecialAccount$accountOldUserCombo').setValue('');
																		    }
																		   },
																		   icon: Ext.MessageBox.INFO
																		});
													
												}	
												},
												failure : function(response, options) {
													
														
												}
											}, this);
							//
							
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
				html : '<font color=red>*</font>��ϵ�绰:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '�ֻ�����',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_SpecialAccount$telephone',
				name : 'itil_ac_SpecialAccount$telephone',
				style : '',
				width : 200,
				value : '',
				allowBlank : false,
				validator : '',
				vtype : ''
			}),
			
			
			{
				html : '<font color=red>*</font>�ʺ���������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
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
				
				name : 'AccountApplyMainTable$applyUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
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
					},
				'select':function(combo, record, index){
					 var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					  Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
								},

								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									Ext.getCmp('sUserInfos$costCenterCode').setValue(r.costCenter);
									Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
									Ext.getCmp('sUserInfos$userTypeCombo').setValue(r.userType);
									Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);
						
									
								},

							    failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ","���������ݼ���ʧ�ܣ�����ϵ����Ա����");
								var userInfo = Ext
									   .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
										return false;

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
				html : '<font color=red>*</font>��ϵ�绰:',
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
			}), 
			
				{
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
			}), 
			
			{
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
				html : '<font color=red>*</font>������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$confirmUser',
				id : 'AccountApplyMainTable$confirmUserCombo',
				width : 200,
				style : '',
				fieldLabel : '������',hideTrigger : true,
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
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
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
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							queryEvent.combo.cleanValue();
							return false;
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
			})]}, 
			{
			xtype : 'fieldset',
            title : '�����ʺ���Ϣ',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :false,
		    style : 'border:1px dotted #b0acac;margin:15px 0px 0px px',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items:[
			
			 {
				html : '<font color=red>*</font>�ʺ�����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				
				xtype : 'combo',
				hiddenName : 'itil_ac_SpecialAccount$accountName',
				id : 'itil_ac_SpecialAccount$accountNameCombo',
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
				name : 'itil_ac_SpecialAccount$accountName',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext + "/accountAction_listWin7AccountName.action",
					fields : ['id', 'accountName'],
					totalProperty : 'rowCount',
					root : 'data'
				}),
				listeners : {
					'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
					var olduser=Ext.getCmp('itil_ac_SpecialAccount$accountOldUserCombo').getValue();
					if(olduser==null||olduser==''){
						 Ext.MessageBox.alert("��ʾ","����ѡ���ʺ�ԭ������,лл���ĺ�����");
						 return false;
					}
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.baseParams={
							accountName : param,
							userInfo : olduser,
							start : 0
						}
						this.store.load();
						return false;
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
          
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :false,
		    style : 'border:0px dotted #b0acac;',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items:[
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
				id : 'itil_ac_SpecialAccount$id',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$id',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Զ����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$password',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$password',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$accountType',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$accountType',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�ʺ�����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$accountState',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$accountState',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�ʺ�״̬'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$createDate',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$createDate',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '����ʱ��'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$confirmUser',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$confirmUser',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�ʺŹ���Ա'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$ifHold',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$ifHold',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Ƿ���'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$ifLocked',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$ifLocked',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '�Ƿ�����'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$olodApplyAccount',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$olodApplyAccount',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '����ǰ�ʺ�'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_SpecialAccount$applyId',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$applyId',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '������'
			})]}],
				buttons:[
			 {
				text : '����',
				iconCls : 'back',
				id:'back',
				handler : function() {
				window.history.back(-1);
				}
			}
			],
			buttonAlign:'center'
		});
		if (this.dataId == "0" || this.dataId == "null") {
						
		} else {
			
		this.formpanel_SpecailAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getSpecailDraftData.action?panelName=panel_SpecailAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
							  Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
					          Ext.getCmp("AccountApplyMainTable$applyUserCombo").initComponent();
							  Ext.getCmp("itil_ac_SpecialAccount$accountOldUserCombo").initComponent();
							  Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
							  Ext.getCmp("itil_ac_SpecialAccount$accountNameCombo").initComponent();
				}
			});
					
			
				
		}
		return this.formpanel_SpecailAccountApply_Input;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {

		  var histroyForm = new HistroyForm({
		   reqId : this.dataId,
		   reqClass : "com.digitalchina.itil.require.entity.AccountApplyMainTable"
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
		this.model = "ac_DeptSMailOwnerChange";

		this.getFormpanel_SpecailAccountApply_Input();
		this.pa.push(this.formpanel_SpecailAccountApply_Input);
		this.formname.push("panel_SpecailAccountApply_Input");
		temp.push(this.formpanel_SpecailAccountApply_Input);
		if(this.dataId != "0" &&this.dataId != "null"){
		  temp.push(histroyForm);
		 }
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
		
	}
})