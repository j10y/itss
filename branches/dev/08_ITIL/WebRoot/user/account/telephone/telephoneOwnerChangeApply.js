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
			},{
				name : 'AccountApplyMainTable$telephoneSignUser',
				mapping : 'AccountApplyMainTable$telephoneSignUser'
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
			}, 	{
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
				name : 'itil_ac_PersonFormalAccount$department',
				mapping : 'itil_ac_PersonFormalAccount$department'
			}, {
				name : 'itil_ac_PersonFormalAccount$costCenterCode',
				mapping : 'itil_ac_PersonFormalAccount$costCenterCode'
			}, {
				name : 'itil_ac_PersonFormalAccount$applyId',
				mapping : 'itil_ac_PersonFormalAccount$applyId'
			}, {
				name : 'itil_ac_PersonFormalAccount$beyondMoney',
				mapping : 'itil_ac_PersonFormalAccount$beyondMoney'
			}, {
				name : 'itil_ac_PersonFormalAccount$sameMailDept',
				mapping : 'itil_ac_PersonFormalAccount$sameMailDept'
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
				name : 'itil_ac_PersonFormalAccount$costCenterCode',
				mapping : 'itil_ac_PersonFormalAccount$costCenterCode'
			},{
				name : 'itil_ac_PersonFormalAccount$ifHold',
				mapping : 'itil_ac_PersonFormalAccount$ifHold'
			}]),
			title : "���������߱������",
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
			}, new Ext.form.ComboBox({forceSelection:true,
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
				html : '<font color=red>*</font>��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
					fieldLabel : '��������',
					id : 'itil_ac_PersonFormalAccount$telephoneNumberCombo',
					name : 'itil_ac_PersonFormalAccount$telephoneNumber',
					hiddenName : 'itil_ac_PersonFormalAccount$telephoneNumber',
					xtype : 'combo',
					width : 200,
					style : '',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'number',
					valueField : 'id',
					emptyText : '������������������ѡ��...',
					allowBlank : false,
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/accountAction_listAllTelephoneAccount.action',
						fields : ['id', 'number'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['number'] == undefined) {
									opt.params['number'] = Ext
											.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo').defaultParam;
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
									number : param,
									start : 0
								}
							});
							return true;
						},

						'select' : function(combo, record, index) {
							var number = Ext
									.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo')
									.getValue();
								Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getAccountInfo.action',
								params : {
									number : number
								},
								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									if (r.success) {
										if(r.num==1){
											Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').setValue(r.drawspace);
											Ext.getCmp('oldOwner').setValue(r.name);
											Ext.getCmp("itil_ac_PersonFormalAccount$workSpaceCombo")
											.initComponent();
										}else{
											Ext.Msg.show({
												   title:'��ʾ',
												   msg: '����������Ϊ���˹��õĺ��룬�ǹ���������������������ߣ�',
												   buttons: Ext.Msg.OKCANCEL,
												   fn:function(btn){
													   if(btn=='ok'){
													   	Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').setValue(r.drawspace);
														Ext.getCmp('oldOwner').setValue(r.name);
														Ext.getCmp("itil_ac_PersonFormalAccount$workSpaceCombo")
														.initComponent();
													   }else{
													  	 Ext.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo').setValue('');
													   }
												   },
												   icon: Ext.MessageBox.INFO
												   });
												   
										}
									}else{
						     
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
				html : '<font color=red>*</font>�����ص�:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$workSpace',
				id : 'itil_ac_PersonFormalAccount$workSpaceCombo',
				width : 200,
				style : '',
				fieldLabel : '�����ص�',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				hideTrigger:true,
				displayField : 'name',
				valueField : 'id',
				emptyText : 'ѡ������������Զ�����',
				allowBlank : false,
				//typeAhead : true,
				readOnly:true,
				name : 'itil_ac_PersonFormalAccount$workSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.WorkSpace',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$workSpace'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').defaultParam;
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
					},
					'select' : function(combo, record, index) {
					var mailvalue=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getRawValue();
					if(mailvalue=='��'){
					Ext.MessageBox.alert("��ʾ","����ѡ�����Ĺ����ص�,лл���ĺ�����",function(btn){
					Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').setValue("");
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
			}),
			{
				html : '����ԭ������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			},  new Ext.form.TextField({
				fieldLabel : '�ʺ�ԭ������',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'oldOwner',
				name : 'oldOwner',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				readOnly:true,
				validator : '',
				vtype : ''
			}), 
			
			{
				html : '<font color=red>*</font>������������:',
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
					    var mailvalue=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getRawValue();
					   if(mailvalue=='��'||mailvalue==''){
					   Ext.MessageBox.alert("��ʾ","����ѡ������������,лл���ĺ�����",function(btn){
					         Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
					      });
					      return false;
				    	}
							var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo')
									.getValue();
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getUserPersonAccount.action',
								params : {
									userInfo : userInfo,
									accountType : '�ʼ��ʺ�'

								},

								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									if (!r.success) {

										Ext.MessageBox
												.confirm(
														"��ʾ",
														"ʹ���˻�û���ʼ��ʺ�,�����ύ��Ա��IT�ʺ�����,�Ƿ񴴽������룿",
														function(button, text) {
															if (button == 'yes') {
																window.location = webContext
																		+ "/requireAction_toProcessEnterPage.action?serviceItemProcessId=195";
															} else {
																var userInfo = Ext
																		.getCmp('AccountApplyMainTable$applyUserCombo')
																		.setValue("");
															}

														})
									} else {

													var userInfo = Ext
															.getCmp('AccountApplyMainTable$applyUserCombo')
															.getValue();
													Ext.Ajax.request({
														url : webContext
																+ '/accountAction_initUserInfoData.action',
														params : {
															userInfo : userInfo

														},
														success : function(
																response,
																options) {
															var r = Ext
																	.decode(response.responseText);
															
//															if(r.userType==7){
//																Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲԱ������û��Ȩ���������룡",function(btn){
//																Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
//																return false;
//															})}
//																else if(r.userType==9){
//																Ext.MessageBox.alert("ϵͳ��ʾ","��������ǲ(����)Ա������û��Ȩ���������룡",function(btn){
//																Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
//																return false;
//															})}
//																if(r.userType==4){
//																Ext.MessageBox.alert("ϵͳ��ʾ","��������ʱԱ������û��Ȩ���������룡",function(btn){
//																Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
//																return false;
//															})}
//																else if(r.userType==2){
//																Ext.MessageBox.alert("ϵͳ��ʾ","��������ƸԱ������û��Ȩ���������룡",function(btn){
//																Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
//																return false;
//															})}
//																else if(r.userType==8){
//																Ext.MessageBox.alert("ϵͳ��ʾ","����������Ա������û��Ȩ���������룡",function(btn){
//																Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
//																return false;
//															})}
																															
														},

														failure : function(
																response,
																options) {
															Ext.MessageBox
																	.alert("��ʾ","���������ݼ���ʧ�ܣ�����ϵ����Ա����");
															var userInfo = Ext
																	.getCmp('AccountApplyMainTable$applyUserCombo')
																	.setValue("");
															return false;

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
				}),
			{
				html : '<font color=red>*</font>��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$telephoneSignUser',
				id : 'AccountApplyMainTable$telephoneSignUserCombo',
				width : 200,
				style : '',
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				editable:false,
				displayField : 'department',
				valueField : 'id',
				emptyText : '��ѡ��...',
				allowBlank : false,
				typeAhead : true,
				name : 'AccountApplyMainTable$telephoneSignUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.TelephoneCountSign',
					fields : ['id', 'department'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['AccountApplyMainTable$telephoneSignUser'] == undefined) {
								opt.params['department'] = Ext
										.getCmp('AccountApplyMainTable$telephoneSignUserCombo').defaultParam;
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
					var mailvalue=Ext.getCmp('AccountApplyMainTable$telephoneSignUserCombo').getRawValue();
					if(mailvalue=='��'||mailvalue==''){
					Ext.MessageBox.alert("��ʾ","����ѡ������������,лл���ĺ�����",function(btn){
					Ext.getCmp('AccountApplyMainTable$telephoneSignUserCombo').setValue("");
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
			})]}, 
			
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
				}),{
					html : ' ����˵��������ѡ��',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0,
					style : 'border:1px dotted #b0acac;width:600;text-align:left;margin:0px 0px 0px 75px'
				}, new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'itil_ac_PersonFormalAccount$remarkDesc',
				    colspan : 4,
					rowspan : 0,
					name : 'itil_ac_PersonFormalAccount$remarkDesc',
					width : 600,
					style : 'margin:0px 0px 0px 75px',
					value : '',
					allowBlank : true,
					validator : '',
					fieldLabel : ''
				})]}],
				buttons : [{
				text : '����Ϊ�ݸ�',
				iconCls : 'save',
				id:'save',
				handler : function() {
				
					if(!Ext.getCmp('panel_PersonAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
					return false;
					}
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var telNumber=Ext.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo').getValue();
					
					if(telNumber==''||telNumber==null){
					 Ext.MessageBox.alert("��ʾ","�����������������б���ѡ��,лл���ĺ���!");
		                return false;
					}
					if(applyUser==''||applyUser==null){
					 Ext.MessageBox.alert("��ʾ","�ʺ��������߱���������б���ѡ��,лл���ĺ���!");
		                return false;
					}
					if(confirmUser==''||confirmUser==null){
					      Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
					      return false;
				     }
					if(delegateApplyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","��������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","�ʺ��������߲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					
		           Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
		            var curDataId = this.dataId;
					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
					var number = Ext
									.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo')
									.getValue();
					Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getNumberApply.action',
								params : {
									number : number,
									serviceItemProcess:'317'
									},
								success : function(response, options) {
									var responseArray = Ext.util.JSON
									.decode(response.responseText);
									if(responseArray.success){
									
										Ext.Ajax.request({
											url : webContext
													+ '/accountAction_saveTelephoneOwnerChangeDraft.action',
											params : {
												info : info,
												serviceItemId : curscid,
												processType:processType,
												processInfoId:processInfoId,
												accountType:'����',
												panelName : 'panel_PersonAccountApply_Input'
											},
					
											success : function(response, options) {
												Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {
																	window.location = webContext
																			+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
																			+ curscid;
											});
											Ext.getCmp("save").enable();
												                Ext.getCmp("submit").enable();
												                Ext.getCmp("back").enable();
										   }
										});
										}else{
									Ext.MessageBox.alert("��ʾ","�����������Ѵ��������е������߱������,���ܹ���������룡",function(btn){
				                      	window.history.back(-1);
				                      	});
									
									}
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("��ʾ", "�ύ����ʧ��!");
									Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
								}
							}, this);
			}}
			,
				{
				text : '�ύ����',
				iconCls : 'submit',
					id:'submit',
				handler : function() {
					
					var workSpace = Ext
												.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo')
												.getValue();
					if(workSpace==''||workSpace==null){
					Ext.MessageBox.alert("��ʾ","�����ص�û���Զ�����������ϵ����Ա��");	
					return false;
					}
					if(!Ext.getCmp('panel_PersonAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
					return false;
					}
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var telNumber=Ext.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo').getValue();
					
					if(telNumber==''||telNumber==null){
					 Ext.MessageBox.alert("��ʾ","�����������������б���ѡ��,лл���ĺ���!");
		                return false;
					}
					if(applyUser==''||applyUser==null){
					 Ext.MessageBox.alert("��ʾ","�ʺ��������߱���������б���ѡ��,лл���ĺ���!");
		                return false;
					}
					if(confirmUser==''||confirmUser==null){
					      Ext.MessageBox.alert("��ʾ","�����˱���������б���ѡ��,лл���ĺ���!");
					      return false;
				     }
					if(delegateApplyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","��������˲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("��ʾ","�ʺ��������߲��ܺ���������ͬ,��ȷ�Ϻ��ٱ��棡");
		                return false;
					}
		   
		   			Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
		            var curDataId = this.dataId;
					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
					var number = Ext
									.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo')
									.getValue();
					Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getNumberApply.action',
								params : {
									number : number,
									serviceItemProcess:'317'
									},
								success : function(response, options) {
									var responseArray = Ext.util.JSON
									.decode(response.responseText);
									if(responseArray.success){
									
									Ext.Ajax.request({
											url : webContext
													+ '/accountAction_saveTelephoneOwnerChangeDraft.action',
											params : {
												info : info,
												serviceItemId : curscid,
												processType:processType,
												processInfoId:processInfoId,
												accountType:'����',
												panelName : 'panel_PersonAccountApply_Input'
											},
					
											success : function(response, options) {
												var responseArray = Ext.util.JSON
														.decode(response.responseText);
									     		 var curId = responseArray.id;
											     var curName = responseArray.applyId;
											     var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
											     var workSpace = Ext
																	.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo')
																	.getValue();
												 var department = Ext
																	.getCmp('AccountApplyMainTable$telephoneSignUserCombo')
																	.getValue();
												 Ext.Ajax.request({
													url : webContext
															+ '/accountWorkflow_applyTelephoneAccount.action',
													params : {
														dataId : curId,
														userInfo:userInfo,
														workSpace : workSpace,
														department:department,
														bzparam : "{dataId :'" + curId
																+ "',applyId : '" + curId
																+ "',accountName:'" + curName
																+ "',applyType: 'acproject'," 
																+ "applyTypeName: '���������߱������',"
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
														Ext.MessageBox.alert("��ʾ", "�ύ����ʧ��,�����������Ƿ�ѡ����ȷ!");
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
										   
										});
									
									}else{
									Ext.MessageBox.alert("��ʾ","�����������Ѵ��������е������߱������,���ܹ���������룡",function(btn){
				                      	window.history.back(-1);
				                      	});
									
									}
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("��ʾ", "�ύ����ʧ��!");
									Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
								}
							}, this);
					
				}
			}, {
				text : '����',
			    id:'back',
				iconCls : 'back',
				handler : function() {
				window.history.back(-1);
				}
			}],
			buttonAlign:'center'
		});
		if (this.dataId == "0" || this.dataId == "null") {
			Ext.getCmp('panel_PersonAccountApply_Input').form.load({
				                url : webContext
						    + '/accountAction_initNewApplyData.action',
				                params : {
							    serviceItemId : this.serviceItemId,
							    processType :this.processType,
							    panelName : 'panel_PersonAccountApply_Input'
				                },
				                timeout : 30,
				                success : function(action, form) {
				                	
				                	 Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							.initComponent();
                                Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .setValue("");
					
					             Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
				              },
				             failure : function(response, options) {
							
				              }
		 })											
				    	
						
		} else {
				this.formpanel_SpecailAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getTelOwnerChangeDraftData.action?panelName=panel_PersonAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
						Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							.initComponent();
					    Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
					    Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					    Ext.getCmp("AccountApplyMainTable$telephoneSignUserCombo")
							.initComponent();
						
						var number = Ext
									.getCmp('itil_ac_PersonFormalAccount$telephoneNumberCombo')
									.getValue();
								Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getAccountInfo.action',
								params : {
									number : number
								},
								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									if (r.success) {
										Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').setValue(r.drawspace);
										Ext.getCmp('oldOwner').setValue(r.name);
										Ext.getCmp("itil_ac_PersonFormalAccount$workSpaceCombo")
										.initComponent();
									}else{
						     
									}
								},

								failure : function(response, options) {

								}
							}, this);
						
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
		this.formname.push("panel_PersonAccountApply_Input");
		temp.push(this.formpanel_SpecailAccountApply_Input);
		if(this.dataId != "0" &&this.dataId != "null"){
		  temp.push(histroyForm);
		 }
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
		if(this.status!=0){
			Ext.getCmp("save").hide();
		    Ext.getCmp("submit").hide();
		}
		if (this.readOnly == 1) {
			Ext.getCmp("back").hide();
		}
	}
})