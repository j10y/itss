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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>1.页面中带红色<font color=red>*</font>号的必填项，请在填写完整后再提交申请！&nbsp<font color=red>2.审批人必须从下拉列表中选择</font></font>')]			
			
		});
		return this.tabPanel;

	},

	getFormpanel_PersonAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId=this.serviceItemProcess;
		var description=this.description;
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
			title : "Traveler帐号申请",
			items : [{
			xtype : 'fieldset',
		    title : '申请人信息',
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
				hideTrigger:true,
				readOnly : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '申请日期'
			}),  {
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
					
					hideTrigger:true,
				    readOnly : true,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : true,
					typeAhead : true,
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
				}),
				{
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
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请输入ITCODE进行选择...',
				allowBlank : false,
//				typeAhead : true,
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
					var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
							Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getUserApply.action',
						params : {
							serviceItemProcess:processInfoId,
							processType:processType,
							userInfo:userInfo
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							 if(responseArray.success){	
							 	Ext.MessageBox.alert("提示","申请人已存在审批中的Traveler帐号申请,不能够再提该申请！",function(btn){
		                      Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
		                      return false;
		                      });
							 }else{
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getUserPersonAccount.action',
								params : {
									userInfo : userInfo,
									accountType : '邮件帐号'

								},

								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									if (!r.success) {

										Ext.MessageBox
												.confirm(
														"提示",
														"使用人还没有邮件帐号,请先提交新员工IT帐号申请,是否创建该申请？",
														function(button, text) {
															if (button == 'yes') {
																window.location = webContext
																		+ "/requireAction_toProcessEnterPage.action?serviceItemProcessId=195";
															} else {
																var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
															}

														})
									} else {
										
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getUserPersonAccount.action',
								params : {
									userInfo : userInfo,
									accountType : 'Traveler帐号'
                               },

								success : function(response, options) {
									var r = Ext.decode(response.responseText);
									if (!r.success) {
								var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();		
						        Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
									
                               },

								success : function(response, options) {
									
									var r = Ext.decode(response.responseText);
									if(r.userType=='7'||r.userType=='9'){
										Ext.MessageBox.alert("提示","申请人为派遣员工,不够够申请该帐号");
									 Ext
									.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
										return false;
									}
								    Ext.getCmp('sUserInfos$costCenterCode').setValue(r.costCenter);
									Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
									Ext.getCmp('sUserInfos$userTypeCombo').setValue(r.userType);
									Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);
									Ext.getCmp('sUserInfos$personnelScopeCombo').setValue(r.PersonScope);
									Ext.getCmp('sUserInfos$mailServerCombo').setValue(r.mailServer);
								    Ext.getCmp("sUserInfos$personnelScopeCombo")
							.initComponent();
								},

								failure : function(response, options) {
									Ext.MessageBox.alert("提示","申请人数据加载失败，请联系管理员处理！");
								var userInfo = Ext
									   .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
										return false;

								}
							}, this);
										
										
									} else {
										Ext.MessageBox.alert("提示","申请人已经存在可用的Traveler帐号,不能够在申请！");
										var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
										return false;
 
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
			}), 
			{
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
			}), 
//			{
//				html : '邮件等价各部门:',
//				cls : 'common-text',
//				style : 'width:135;text-align:right'
//			}, new Ext.form.ComboBox({forceSelection:true,
//				xtype : 'combo',
//				hiddenName : 'sUserInfos$sameMailDept',
//				id : 'sUserInfos$sameMailDeptCombo',
//				width : 200,
//				style : '',
//				fieldLabel : '邮件等价各部门',
//				colspan : 0,
//				rowspan : 0,
//				lazyRender : true,
//				readOnly : true,
//				hideTrigger:true,
//				displayField : 'name',
//				valueField : 'id',
//				emptyText : '请选择...',
//				allowBlank : true,
//				typeAhead : true,
//				name : 'sUserInfos$sameMailDept',
//				triggerAction : 'all',
//				minChars : 50,
//				queryDelay : 700,
//				store : new Ext.data.JsonStore({
//					url : webContext
//							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.SameMailDept',
//					fields : ['id', 'name'],
//					listeners : {
//						beforeload : function(store, opt) {
//							if (opt.params['sUserInfos$sameMailDept'] == undefined) {
//								opt.params['name'] = Ext
//										.getCmp('sUserInfos$sameMailDeptCombo').defaultParam;
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
//							id : Ext.getCmp('sUserInfos$sameMailDeptCombo')
//									.getValue(),
//							start : 0
//						},
//						callback : function(r, options, success) {
//							Ext
//									.getCmp('sUserInfos$sameMailDeptCombo')
//									.setValue(Ext
//											.getCmp('sUserInfos$sameMailDeptCombo')
//											.getValue());
//						}
//					});
//				}
//			}), {
//				html : '邮件服务器:',
//				cls : 'common-text',
//				style : 'width:135;text-align:right'
//			}, new Ext.form.ComboBox({forceSelection:true,
//				xtype : 'combo',
//				hiddenName : 'sUserInfos$mailServer',
//				id : 'sUserInfos$mailServerCombo',
//				width : 200,
//				style : '',
//				fieldLabel : '邮件服务器',
//				colspan : 0,
//				rowspan : 0,
//				lazyRender : true,
//				readOnly : true,
//				hideTrigger:true,
//				displayField : 'name',
//				valueField : 'id',
//				emptyText : '请选择...',
//				allowBlank : true,
//				typeAhead : true,
//				name : 'sUserInfos$mailServer',
//				triggerAction : 'all',
//				minChars : 50,
//				queryDelay : 700,
//				store : new Ext.data.JsonStore({
//					url : webContext
//							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.MailServer',
//					fields : ['id', 'name'],
//					listeners : {
//						beforeload : function(store, opt) {
//							if (opt.params['sUserInfos$mailServer'] == undefined) {
//								opt.params['name'] = Ext
//										.getCmp('sUserInfos$mailServerCombo').defaultParam;
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
//							id : Ext.getCmp('sUserInfos$mailServerCombo')
//									.getValue(),
//							start : 0
//						},
//						callback : function(r, options, success) {
//							Ext
//									.getCmp('sUserInfos$mailServerCombo')
//									.setValue(Ext
//											.getCmp('sUserInfos$mailServerCombo')
//											.getValue());
//						}
//					});
//				}
//			}),  
			{
				html : '用户类别/员工组:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			},new Ext.form.ComboBox({forceSelection:true,
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
				hideTrigger:true,
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
				html : '人事子范围:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'sUserInfos$personnelScope',
				id : 'sUserInfos$personnelScopeCombo',
				width : 200,
				style : '',
				fieldLabel : '人事子范围',
				colspan : 0,
				rowspan : 0,
				readOnly : true,
				hideTrigger:true,
				lazyRender : true,
				displayField : 'personnelScopeCode',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				
				name : 'sUserInfos$personnelScope',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.PersonnelScope',
					fields : ['id', 'personnelScopeCode'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$personnelScope'] == undefined) {
								opt.params['personnelScopeCode'] = Ext
										.getCmp('sUserInfos$personnelScopeCombo').defaultParam;
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
								personnelScopeCode : param,
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
				html : '邮件服务器:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, 
			new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'sUserInfos$mailServer',
				id : 'sUserInfos$mailServerCombo',
				width : 200,
				style : '',
				fieldLabel : '邮件服务器',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				
				name : 'sUserInfos$mailServer',
				triggerAction : 'all',
				minChars : 50,
				hideTrigger:true,
				readOnly : true,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.MailServer',
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
					this.store.load({
						params : {
							id : Ext.getCmp('sUserInfos$mailServerCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('sUserInfos$mailServerCombo')
									.setValue(Ext
											.getCmp('sUserInfos$mailServerCombo')
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
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请输入部门经理的ITCODE后选择...',
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
			})]},
				
			 
			 {
			xtype : 'fieldset',
		    title : '申请帐号信息',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
		    style : 'border:1px dotted #b0acac;margin:0px 0px 0px px',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items:[{
				html : '<h3><font color=red>Lotus Traveler服务说明:</font></h3><br><a href="/user/account/document/LotusTraveler(iPhone).pdf" target="_blank"><b><font color="blue" size=2px style="text-decoration:none">《LotusTraveler使用手册(iPhone)》</font></b></a><br><a href="/user/account/document/LotusTraveler(Nokia).pdf" target="_blank"><b><font color="blue" size=2px style="text-decoration:none">《LotusTraveler使用手册(Nokia)》</font></b></a><br><a href="/user/account/document/LotusTraveler(Windows Mobile).pdf" target="_blank"><b><font color="blue" size=2px style="text-decoration:none">《LotusTraveler使用手册(Windows Mobile)》</font></b></a><br><br> 1、企业移动终端Lotus Traveler服务目前适用于：中国联通用户、中国电信的GPRS或3G用户，中国移动除神州行之外的用户。<br>2、企业移动终端Lotus Traveler服务支持多种移动设备（包括Windows Mobile 设备，Nokia S60 第三版和第五版设备， Apple iPhone），请您在申请服务时确认您的手机型号是否在支持范围内，具体请查看相应的服务手册。<br>3、企业移动终端Lotus Traveler服务费用：神州数码标准服务费 + 手机网络流量费，其中：神州数码标准服务费：8元/用户月（为FY10试运行期间标准，正式运行的标准根据具体情况核算后另行公布），用户所在部门承担,按月分摊。手机网络流量费：用户承担,由移动运营商计费和收取，具体请查询各地运营商资费标准。<br><br><h3><font color=red>Lotus Traveler注意事项:</font></h3><br><br> 1、申请通过后，后台系统生效最多可能需要2小时，之后可以进行手机端配置；<br>2、请勿“同步通讯录”：Traveler同步通讯录方式为手机通讯录与Notes通讯录单向推送同步，同步操作会将手机的存储信息清空；<br>3、Traveler软件提示“未连接到服务器”时，可能是由于手机Traveler服务异常，可以重启手机设备再进行连接。<br>4、若您的终端已随机附带lotus traveler软件（如Nokia E72、E52等机型），则无需再安装客户端，请使用功能表-电邮服务-新建-lotus traveler-进行配置。',
				cls : 'common-text',
				colspan : 2,
				rowspan : 0,
				style : 'border:1px dotted #b0acac;width:700;text-align:left;margin:0px 0px 0px 25px'
			},
		
			{
				html : '<font color=red>*</font>申请原因：',
				cls : 'common-text',
				colspan : 2,
				rowspan : 0,
				style : 'border:1px dotted #b0acac;width:700;text-align:left;margin:0px 0px 0px 25px'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'itil_ac_PersonFormalAccount$registerServiceRightDesc',
				colspan : 2,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$registerServiceRightDesc',
				width : 705,
				style : 'margin:0px 0px 0px 25px',
				value : '',
				allowBlank : false,
				validator : '',
				fieldLabel : ''
			}),
			  new Ext.form.Hidden({
				xtype : 'textarea',
				id : 'itil_ac_PersonFormalAccount$sameRightAccount',
			    colspan : 2,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$sameRightAccount',
				width : 705,
				style : 'margin:0px 0px 0px 25px',
				value : '',
				allowBlank : true,
				validator : '',
				fieldLabel : ''
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
			})]}],
		buttons : [{
				text : '保存为草稿',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('panel_PersonAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("提示","申请人不能和审批人相同,请确认后再保存！");
		                return false;
					}
					if(delegateApplyUser==confirmUser){
					 Ext.MessageBox.alert("提示","代申请人不能和审批人相同,请确认后再保存！");
		                return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();

					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveApplyDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
						    processInfoId:processInfoId,
							processType:processType,
							accountType:'Traveler帐号',
							panelName : 'panel_AccountApplyMainTable_Input'
					},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_PersonAccountApply_Input').load({
								url : webContext
										+ '/accountAction_getPersonApplyDraftData.action?panelName=panel_PersonAccountApply_Input&dataId='
										+ curId,
								timeout : 30,
								success : function(action, form) {
								}
							});
							Ext.MessageBox.alert("提示", "保存成功", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
							});
							 Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
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
				id:'submit',
				handler : function() {
				if(!Ext.getCmp('panel_PersonAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
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
				         Ext.MessageBox.alert("提示","申请人已存在审批中的Traveler帐号申请,不能够再提该申请！",function(btn){
				                         window.history.back(-1);
				                         });
				        }else{

					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					if(applyUser==''||applyUser==null){
				      Ext.MessageBox.alert("提示","申请人必须从下拉列表中选择,谢谢您的合作!");
				      return false;
    				 }
					if(confirmUser==''||confirmUser==null){
					      Ext.MessageBox.alert("提示","审批人必须从下拉列表中选择,谢谢您的合作!");
					      return false;
				     }
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("提示","申请人不能和审批人相同,请确认后再保存！");
		                return false;
					}
					if(delegateApplyUser==confirmUser){
					 Ext.MessageBox.alert("提示","代申请的人不能和审批人相同,请确认后再保存！");
		                return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();

					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveApplyDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
						    processInfoId:processInfoId,
							processType:processType,
							accountType:'Traveler帐号',
							panelName : 'panel_AccountApplyMainTable_Input'
					},
						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
						     var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					 		//Ext.MessageBox.alert("保存成功");
							// //////////////////////////////////////////////////////////////////
							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_apply2.action',
								params : {
									dataId : curId,
									userInfo:userInfo,
									description:description,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject'," 
											+ "applyTypeName: 'Traveler帐号申请',"
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
									Ext.MessageBox.alert("提示", "提交申请失败,请检查审批人是否选择正确!");
									 Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "提交申请失败,请检查申请人是否选择正确!");
							 Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
						}
					}, this);
					
					}
			      },
			      failure : function(response, options) {
			       Ext.MessageBox.alert("提示", "请检查申请人是否选择正确!");
			      }
			     }, this);
				}
			}, {
				text : '返回',
				iconCls : 'back',
				id:'back',
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
							accountType:'邮件帐号'
						},

						success : function(response, options) {
						var responseArray = Ext.util.JSON
									.decode(response.responseText);
						if(responseArray.success){	
						Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getUserPersonAccount.action',
						params : {
							accountType:'Traveler帐号'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
						    if(responseArray.success){
						    	
		                      	 Ext.getCmp('panel_PersonAccountApply_Input').form.load({
				                url : webContext
						        + '/accountAction_initPersonAccountDeleteData.action',
				                params : {
							    serviceItemId : this.serviceItemId,
							    processType :this.processType,
							    panelName : 'panel_PersonAccountApply_Input'
				                },
				              timeout : 30,
				              success : function(action, form) {
                                
					             	 Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
							         Ext.getCmp("sUserInfos$personnelScopeCombo").initComponent();
					            
				              },
				             failure : function(response, options) {
							
				              }
		                     })
		                      	
		                      }else{
		                      	
		                      	 	
							var curId = responseArray.id;
							 Ext.getCmp('panel_PersonAccountApply_Input').form.load({
				                url : webContext
						    + '/accountAction_initPersonAccountApplyData.action',
				                params : {
							    serviceItemId : this.serviceItemId,
							    processType :this.processType,
							    panelName : 'panel_PersonAccountApply_Input'
				                },
				              timeout : 30,
				              success : function(action, form) {
				              var userType= Ext.getCmp("sUserInfos$userTypeCombo").getValue();
								
							if(userType==4){
								Ext.MessageBox.alert("系统提示","您属于临时员工，尚没有权限做此申请！",function(btn){
								window.history.back(-1);
							})}
                                Ext.getCmp("AccountApplyMainTable$applyUserCombo").initComponent();
								Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
						        Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
								Ext.getCmp("sUserInfos$personnelScopeCombo").initComponent();
							  
				              },
				             failure : function(response, options) {
							   
		                      	
				              }
		                      })
		                      }
							
						},
						failure : function(response, options) {
							
						}
					}, this);
			
						}else{
							Ext.MessageBox.confirm(
														"提示",
														"使用人还没有邮件帐号,请先提交新员工IT帐号申请,是否创建该申请？",
														function(button, text) {
															if (button == 'yes') {
																window.location = webContext
																		+ "/requireAction_toProcessEnterPage.action?serviceItemProcessId=195";
															} else {
																window.history
																		.back(-1);
															}

														})
							
						}	
						},
						failure : function(response, options) {
							
								
						}
					}, this);
			
			
			} else {
			this.formpanel_PersonAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getPersonApplyDraftData.action?panelName=panel_PersonAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					  		 Ext.getCmp("AccountApplyMainTable$applyUserCombo").initComponent();
							Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
					        Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
							Ext.getCmp("sUserInfos$personnelScopeCombo").initComponent();
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
		this.model = "ac_BIAccountApply";
		

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