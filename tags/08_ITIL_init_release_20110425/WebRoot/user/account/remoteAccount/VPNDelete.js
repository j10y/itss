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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>1.页面中带红色<font color=red>*</font>号的必填项，请在填写完整后再提交申请！</font>')]
			
		});
		return this.tabPanel;

	},

	getFormpanel_PersonAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId=this.serviceItemProcess;
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
				name : 'itil_ac_PersonFormalAccount$vpnType',
				mapping : 'itil_ac_PersonFormalAccount$vpnType'
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
			title : "远程接入帐号删除申请",
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
					fieldLabel : '<font color=red>*</font>联系电话',
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
				html : '<font color=red>*</font>申请人:',
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
							 Ext.MessageBox.alert("提示","申请人已存在审批中的远程接入帐号删除申请,不能够再提该申请！",function(btn){
		                      Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
		                      return false;
		                      });
							 }else{
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getUserPersonAccount.action',
								params : {
									userInfo : userInfo,
									accountType : '远程接入帐号'
								},

							success : function(response, options) {
							   var r = Ext.decode(response.responseText);
						       if (!r.success) {
                                   Ext.MessageBox.alert("提示","申请人不存在可删除的远程接入帐号！");
								var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
									return false;
															
                            } else {
                            var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
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
									Ext.getCmp('sUserInfos$userTypeCombo').setValue(r.userType);
									Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);
									
									 Ext.Ajax.request({
										url : webContext
												+ '/accountAction_getVpnAccountType.action',
										params : {
											userInfo : userInfo
											},
					                      success : function(response, options) {
											var r = Ext.decode(response.responseText);
										    if (r.success) {
					                                Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').setValue('0');
					                                Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').show();
					                                Ext.getCmp('htmldrawspace').show();
											 } else {
													Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').setValue('1');
											        Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').hide();
											        Ext.getCmp('htmldrawspace').hide();
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
				html : '<font color=red>*</font>还卡地点:',
				cls : 'common-text',
				id : 'htmldrawspace',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$drawSpace',
				id : 'itil_ac_PersonFormalAccount$drawSpaceCombo',
				width : 200,
				style : '',
				fieldLabel : '领卡地点',
				colspan : 0,
				rowspan : 0,
				editable:false,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				
				name : 'itil_ac_PersonFormalAccount$drawSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.AR_DrawSpace',
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
					},
				'select' : function(combo, record, index) {
					var mailvalue=Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').getRawValue();
					if(mailvalue=='无'){
					Ext.MessageBox.alert("提示","请您选择具体的还卡地点,谢谢您的合作！",function(btn){
					Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').setValue("");
					return false;
					});	
					}
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
 
						
			]},
			 
			 {
			xtype : 'fieldset',
		    title : '帐号基本信息',
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
		
		  {
				html : '<font color=red>*</font>删除原因:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'itil_ac_PersonFormalAccount$applyReason',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$applyReason',
				width : 530,
				
				style : '',
				value : '',
				allowBlank : false,
				validator : '',
				fieldLabel : '申请原因'
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
			}),new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_PersonFormalAccount$vpnType',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$vpnType',
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
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
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
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
                    var applyReason=getEncodeValue('itil_ac_PersonFormalAccount$applyReason');
					var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
				   var drawSpace=Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').getValue();
				   var vpnType=Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').getValue();
				   if(vpnType=='1'){
				   	 drawSpace='1';
				   }
                   Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveAccountDeleteDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
						    processInfoId:processInfoId,
							processType:processType,
							applyReason:applyReason,
							drawSpace:drawSpace,
							applyUser:applyUser,
							accountType:'远程接入帐号',
							panelName : 'panel_PersonAccountApply_Input'
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
									var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
                           
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
						 			Ext.getCmp('sUserInfos$sameMailDept').setValue(r.name);
                            	
                            	
                            	}
								},

								failure : function(response, options) {

								}
							}, this);
					 Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
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
							Ext.MessageBox.alert("提示","保存失败,请检查页面数据是否选择正确!");
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
					
					var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
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
							 	Ext.MessageBox.alert("提示","申请人已存在审批中的远程接入帐号删除申请,不能够再提该申请！",function(btn){
		                      	window.history.back(-1);
		                      	});
							 }else{
//						Ext.Ajax.request({
//								url : webContext
//										+ '/accountAction_getUserPersonAccount.action',
//								params : {
//									userInfo : userInfo,
//									accountType : '远程接入帐号'
//					},
//                   success : function(response, options) {
//									var r = Ext.decode(response.responseText);
//									 if (!r.success) {
//                                   Ext.MessageBox.alert("提示","申请人不存在可删除的远程接入帐号！");
//                                   return false;
//									 }else{
//									if(r.card==1){
//										 Ext.MessageBox.alert("提示","该申请人令牌卡未归还,请归令牌卡后在提交申请！");
//										 return false;
//									}
					if(!Ext.getCmp('panel_PersonAccountApply_Input').form.isValid()){
					        Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					         return false;
					}
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
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
					 Ext.MessageBox.alert("提示","代申请人不能和审批人相同,请确认后再保存！");
		                return false;
					 }
					
				   var vpnType=Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').getValue();
				   
				   if(vpnType=='1'){
				   	  		Ext.getCmp("save").disable();
							Ext.getCmp("submit").disable();
							Ext.getCmp("back").disable();
							
							var applyReason=getEncodeValue('itil_ac_PersonFormalAccount$applyReason');
							var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
							 
							Ext.Ajax.request({
								url : webContext
										+ '/accountAction_saveAccountDeleteDraft.action',
								
								params : {
									info : info,
									serviceItemId : curscid,
								    processInfoId:processInfoId,
									processType:processType,
									applyReason:applyReason,
									drawSpace:'1',//如果为软令牌默认由还卡地点是1：北京_上地 帐号管理员处理
									applyUser:applyUser,
									accountType:'远程接入帐号',
									panelName : 'panel_PersonAccountApply_Input'
							},
								success : function(response, options) {
									var responseArray = Ext.util.JSON.decode(response.responseText);
									var curId = responseArray.id;
									var curName = responseArray.applyId;
								    var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
								    var drawSpace=Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').getValue();
									Ext.Ajax.request({
										url : webContext
												+ '/accountWorkflow_applyRemoteAccessAccount.action',
										params : {
											dataId : curId,
											userInfo:userInfo,
											drawSpace:'1',
											bzparam : "{dataId :'" + curId
													+ "',applyId : '" + curId
													+ "',accountName:'" + curName
													+ "',applyType: 'acproject'," 
													+ "applyTypeName: '远程接入帐号删除申请',"
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
											Ext.MessageBox.alert("提示", "提交申请失败,请检查页面数据是否选择正确!");
											 Ext.getCmp("save").enable();
							                Ext.getCmp("submit").enable();
							                Ext.getCmp("back").enable();
										}
									}, this);
		
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("提示", "提交申请失败,请检查页面数据是否选择正确!");
									 Ext.getCmp("save").enable();
							         Ext.getCmp("submit").enable();
							         Ext.getCmp("back").enable();
								}
							}, this);
								
								
							
				
				   }else{
					   var drawSpace=Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').getValue();
					   if(drawSpace==''||drawSpace==null){
						      Ext.MessageBox.alert("提示","还卡地点必须从下拉列表中选择,谢谢您的合作!");
						      return false;
					     }
					     
					    Ext.getCmp("save").disable();
						Ext.getCmp("submit").disable();
						Ext.getCmp("back").disable();
						Ext.MessageBox.alert("提示","您令牌卡还没有归还,请在提交申请后将令牌卡归还到指定的平台工程师处,谢谢您的合作!",function(btn){
						if(btn =='ok'){
						var applyReason=getEncodeValue('itil_ac_PersonFormalAccount$applyReason');
						var info = Ext.encode(getFormParam('panel_PersonAccountApply_Input'));
						var drawSpace=Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').getValue();
						 
						Ext.Ajax.request({
							url : webContext
									+ '/accountAction_saveAccountDeleteDraft.action',
							
							params : {
								info : info,
								serviceItemId : curscid,
							    processInfoId:processInfoId,
								processType:processType,
								applyReason:applyReason,
								drawSpace:drawSpace,
								applyUser:applyUser,
								accountType:'远程接入帐号',
								panelName : 'panel_PersonAccountApply_Input'
						},
							success : function(response, options) {
								var responseArray = Ext.util.JSON.decode(response.responseText);
								var curId = responseArray.id;
								var curName = responseArray.applyId;
							    var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
							    var drawSpace=Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').getValue();
								Ext.Ajax.request({
									url : webContext
											+ '/accountWorkflow_applyRemoteAccessAccount.action',
									params : {
										dataId : curId,
										userInfo:userInfo,
										drawSpace:drawSpace,
										bzparam : "{dataId :'" + curId
												+ "',applyId : '" + curId
												+ "',accountName:'" + curName
												+ "',applyType: 'acproject'," 
												+ "applyTypeName: '远程接入帐号删除申请',"
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
										Ext.MessageBox.alert("提示", "提交申请失败,请检查页面数据是否选择正确!");
										 Ext.getCmp("save").enable();
						                Ext.getCmp("submit").enable();
						                Ext.getCmp("back").enable();
									}
								}, this);
	
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("提示", "提交申请失败,请检查页面数据是否选择正确!");
								 Ext.getCmp("save").enable();
						         Ext.getCmp("submit").enable();
						         Ext.getCmp("back").enable();
							}
						}, this);
							
							}
						});
				   }
					
				}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "请检查申请人是否选择正确!");
						}
					}, this);
						 }
						 
//								},
//								failure : function(response, options) {
//
//								}
//							}, this);
//					
//					
//				
//				}
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
					url : webContext + '/accountAction_getUserPersonAccount.action',//获得个人正式帐号
						params : {
							accountType:'远程接入帐号'
						},

					success : function(response, options) {
					  var r=Ext.decode(response.responseText);
					  if(!r.success){
//			              Ext.MessageBox.alert("提示","您不存在可删除的远程接入帐号！");
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
                                
					             Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
					            
				              },
				             failure : function(response, options) {
							
				              }
		                  })
			                 
			               }else{
			               	    
			                	Ext.getCmp('panel_PersonAccountApply_Input').form.load({
				                url : webContext
						        + '/accountAction_initPersonAccountDeleteData.action',
				                params : {
							    serviceItemId : this.serviceItemId,
							    processType :this.processType,
							    accountType:'远程接入帐号',
							    panelName : 'panel_AccountApplyMainTable_Input'
				                },
				                timeout : 30,
				                success : function(action, form) {
				                	Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .initComponent();
							        Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
					                Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
									   .initComponent();
									Ext.getCmp("itil_ac_PersonFormalAccount$drawSpaceCombo")
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
			this.formpanel_PersonAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getPersonApplyDraftData.action?panelName=panel_PersonAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$applyUserCombo").initComponent();
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
					Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
					Ext.getCmp("itil_ac_PersonFormalAccount$drawSpaceCombo").initComponent();
					var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
			      	Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getVpnAccountType.action',
						params : {
							userInfo : userInfo
							},
	                      success : function(response, options) {
							var r = Ext.decode(response.responseText);
						    if (r.success) {
	                                Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').setValue('0');
	                                Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').show();
	                                Ext.getCmp('htmldrawspace').show();
							 } else {
									Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').setValue('1');
							        Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').hide();
							        Ext.getCmp('htmldrawspace').hide();
	                            }
							},
	                       failure : function(response, options) {
	
							}
					}, this);
					 
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
		 if(this.status==1){
		  var histroyForm = new HistroyForm({
		   reqId : this.dataId,
		   reqClass : "com.digitalchina.itil.require.entity.AccountApplyMainTable"
		  });
		  }else{
		         var histroyForm = new HistroyGrid({
		   reqId : this.dataId,
		   reqClass : "com.digitalchina.itil.require.entity.AccountApplyMainTable"
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
		this.model = "ac_BIAccountApply";
		

		this.getFormpanel_PersonAccountApply_Input();
		this.pa.push(this.formpanel_PersonAccountApply_Input);
		this.formname.push("panel_PersonAccountApply_Input");
		temp.push(this.formpanel_PersonAccountApply_Input);
//		temp.push(histroyForm);
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