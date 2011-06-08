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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue><font color=red>审批时要严格授权</font>，如果单据审核不通过，请点击页面下方的<font color=red>“驳回”</font>按钮</font>')]
			
		});
		return this.tabPanel;

	},

	getFormpanel_PersonAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId=this.serviceItemProcess;
		var tkId = this.taskId;
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
				name : 'AccountApplyMainTable$mail',
				mapping : 'AccountApplyMainTable$mail'
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
			}, 
			{
				name : 'itil_ac_PersonFormalAccount$cardNumber',
				mapping : 'itil_ac_PersonFormalAccount$cardNumber'
			},
				{
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
			//}, {
			//	name : 'itil_ac_PersonFormalAccount$vpnType',
			//	mapping : 'itil_ac_PersonFormalAccount$vpnType'	
			}, {
				name : 'itil_ac_PersonFormalAccount$otherLinkCompany',
				mapping : 'itil_ac_PersonFormalAccount$otherLinkCompany'
			}, {
				name : 'itil_ac_PersonFormalAccount$pingCode',
				mapping : 'itil_ac_PersonFormalAccount$pingCode'
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
				name : 'sap',
				mapping : 'sap'
			},
			{
				name : 'iPsoftphone',
				mapping : 'iPsoftphone'
			},
			{
				name : 'office',
				mapping : 'office'
			},
				{
				name : 'sUserInfos$isAccredited',
				mapping : 'sUserInfos$isAccredited'
			}]),
			title : "远程接入帐号申请",
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
					html : '<font color=red>*</font>代申请人联系电话:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '<font color=red>*</font>代申请人联系电话',
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
				emptyText : '请选择...',
				readOnly : true,
				allowBlank : false,
				name : 'AccountApplyMainTable$applyUser',
				minChars : 50,
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
																		+ "/requireAction_toProcessEnterPage.action?serviceItemProcessId=142";
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
									accountType : '远程接入帐号'
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
									Ext.getCmp('sUserInfos$costCenterCode').setValue(r.costCenter);
									Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
									Ext.getCmp('sUserInfos$userTypeCombo').setValue(r.userType);
									Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);
									Ext.getCmp('sUserInfos$personnelScopeCombo').setValue(r.PersonScope);
								
								},

								failure : function(response, options) {
									Ext.MessageBox.alert("提示","申请人数据加载失败，请联系管理员处理！");
								var userInfo = Ext
									   .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
										return false;

								}
							}, this);
									} else {
										Ext.MessageBox.alert("提示","申请人已经存在可用的远程接入帐号,不能够在申请！");
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
				typeAhead : true,
				name : 'sUserInfos$personnelScope',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.PersonnelScope',
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

		/*	{
				html : '<font color=red>*</font>领卡地点:',
				cls : 'common-text',
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
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				typeAhead : true,
				name : 'itil_ac_PersonFormalAccount$drawSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.AR_DrawSpace',
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
			}),*/
			{
				html : '通知邮箱:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '外部邮箱',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'AccountApplyMainTable$mail',
				name : 'AccountApplyMainTable$mail',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : 'email'
			})
			/*,
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
				emptyText : '请选择...',
				allowBlank : false,
				typeAhead : true,
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
			})*/
			]},
	
			 
			 {
			xtype : 'fieldset',
		    title : '申请帐号信息',
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
			colspan : 2,
			rowspan : 0,
			style : 'border:0px dotted #b0acac;width:600;text-align:left;margin:0px 0px 0px 75px',
			 items:  {
                    xtype:'fieldset',
                    title: '请选择具体权限类型(可多选)',
                    autoHeight: true,
                    defaultType: 'checkbox',  // each item will be a checkbox
            items: [
                  {
                        fieldLabel: '权限类型:',
                        labelSeparator: '',
                        id:'office',
                        boxLabel: '常用办公（访问公司主页,OA系统等）',
                        inputValue:'常用办公',
                        name: 'itil_ac_PersonFormalAccount$remarkDesc'
                    },
            	{
                        fieldLabel: '',
                        labelSeparator: '',
                        id:'sap',
                        boxLabel: '访问SAP系统（仅限SAP系统用户）',
                        inputValue:'访问SAP系统',
                        name: 'itil_ac_PersonFormalAccount$remarkDesc'
                    },{
                        fieldLabel: '',
                        labelSeparator: '',
                        id:'iPsoftphone',
                        boxLabel: '使用IPsoftphone（仅限iPsoftphone用户）',
                        inputValue:'使用IPsoftphone',
                        name: 'itil_ac_PersonFormalAccount$remarkDesc'
                    }
                 
                    ]
			 }

			},
			
		
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
				id : 'AccountApplyMainTable$attachment',
				name : 'AccountApplyMainTable$attachment',
				style : '',
				value : 'null',
				fieldLabel : 'nowtime'
			}), new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_PersonFormalAccount$vpnType',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$vpnType',
				width : 200,
				style : '',
				value : '',
				fieldLabel : 'isAccredited'
			})]},
			{
			
			xtype : 'fieldset',
		    title : '帐号办理信息',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:20px 0px 0px 0px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[
			{
					html : '<font color=red>*</font>令牌类型:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
			},{
				xtype: 'radiogroup',
                isFormField:true,
                width:200,
                items: [
                    {boxLabel: '软令牌', name: 'vpnType', inputValue: '1',checked: true,listeners:{'check':function(radio,check){
                    	if(check){
                    		Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').setValue('1');
                    		Ext.getCmp('htmlcardNumber').show();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$cardNumber').show();
                    		Ext.getCmp('htmlpingCode').show();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$pingCode').show();
                    		Ext.getCmp('htmlrightsDesc').show();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$rightsDesc').show();
                    		Ext.getCmp('setattachment').show();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').hide();
                    		Ext.getCmp('htmldrawSpace').hide();
                    	}else{
                    		Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').setValue('0');
                    		Ext.getCmp('htmlcardNumber').hide();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$cardNumber').hide();
                    		Ext.getCmp('htmlpingCode').hide();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$pingCode').hide();
                    		Ext.getCmp('htmlrightsDesc').hide();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$rightsDesc').hide();
                    		Ext.getCmp('setattachment').hide();
                    		Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').show();
                    		Ext.getCmp('htmldrawSpace').show();
                    	}
                    }}},
                    {boxLabel: '硬令牌', name: 'vpnType', inputValue: '0'}
                ]

			},{
				html : '<font color=red>*</font>领卡地点:',
				cls : 'common-text',
				id : 'htmldrawSpace',
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
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'itil_ac_PersonFormalAccount$drawSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.AR_DrawSpace',
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
			}),{
					html : '<font color=red>*</font>令牌卡号:',
					cls : 'common-text',
					id:'htmlcardNumber',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '<font color=red>*</font>令牌卡号',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_PersonFormalAccount$cardNumber',
					name : 'itil_ac_PersonFormalAccount$cardNumber',
					style : '',
					width : 200,
					value : '',
					allowBlank : true,
					validator : '',
					vtype : ''
			}),
			
			{
				html : '<font color=red>*</font>启动密码:',
				cls : 'common-text',
				id:'htmlpingCode',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '启动密码',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$pingCode',
				name : 'itil_ac_PersonFormalAccount$pingCode',
				style : '',
				width : 200,
				value:null,
				allowBlank : true,
				validator : '',
				vtype : ''
			}), 
			
			{
				html : '权限说明:',
				cls : 'common-text',
				id:'htmlrightsDesc',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'itil_ac_PersonFormalAccount$rightsDesc',
				colspan : 3,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$rightsDesc',
				width : 530,
				
				style : '',
				value : '',
			
				validator : '',
				fieldLabel : '权限说明'
			}) 
			]
			}
			,
			{
			id:'setattachment',
			xtype : 'fieldset',
		    title : '附件信息',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:20px 0px 0px 0px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items:[
			
           {
				html : '附件:',
				cls : 'common-text',
				style : 'width:85;text-align:right'
			},{			
				xtype:'panel',layout:'table',width:600,layoutConfig:{columns:4},
				fieldLabel:'附件地址',defaults:{baseCls:'margin : 10 15 12 15'},
				items:[{
				fieldLabel : '附件',
				xtype : 'button',
				text : '<font color=red>上传</font>',
				defaults:{baseCls:'margin : 10 15 12 15'},
				width : '50',
				scope : this,
				handler : function() {
					var attachmentFlag = Ext.getCmp('AccountApplyMainTable$attachment').getValue();
					if (attachmentFlag == '') {
						attachmentFlag = Date.parse(new Date());
						Ext.getCmp('AccountApplyMainTable$attachment').setValue(attachmentFlag);
						var ud = new UpDownLoadFile();
						ud.getUpDownLoadFileSu(attachmentFlag, '7735','com.zsgj.info.appframework.metadata.entity.SystemFile','NotesIDFile010attachment');
					} else {
						var ud = new UpDownLoadFile();
						ud.getUpDownLoadFileSu(attachmentFlag, '7735','com.zsgj.info.appframework.metadata.entity.SystemFile','NotesIDFile010attachment');
					}
				}
			},
				{id:'NotesIDFile010attachment',width:600,border : true,html:'',cls : 'common-text',style : 'width:100;text-align:left'}
			]
			}]
			}
			
			],
			
			buttons : [{
				text : '保存并提交',
				iconCls : 'submit',
				handler : function() {
				
				if(!Ext.getCmp('panel_PersonAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					return false;
			    }
			    var vpnType=Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').getValue();
			    var dataId=Ext.getCmp('AccountApplyMainTable$id').getValue();
			    if(vpnType=='0'){
			    	var drawSpace=Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').getValue();
			    	if(drawSpace==''||drawSpace==null){
				      Ext.MessageBox.alert("提示","领卡地点必须从下拉列表中选择,谢谢您的合作!");
				      return false;
    				 }
			    	Ext.Ajax.request({
							url : webContext+ '/accountAction_saveRemoteAccountType.action',
							params : {
								drawSpace:drawSpace,
								isSpAccount : '0',//0为正式帐号 1为临时帐号
								vpnType:vpnType,
								dataId:dataId
							},
	                        success : function(response, options) {
	                          var r = Ext.decode(response.responseText);
							  if (r.success){
							  		var signuser = r.user; 
									tempUrl = webContext
											+ '/extjs/workflow?method=getData&taskId=' + tkId
											+ '&users=confirmByAMSign:' + signuser;
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
								}else{
									Ext.MessageBox.alert("保存失败");
								}
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("保存失败");
							}
						}, this);
			    	
			    }else{
			    	var attachmentFlag=Ext.getCmp('AccountApplyMainTable$attachment').getValue();
			    	if(attachmentFlag==''||attachmentFlag=='null'){
						Ext.MessageBox.alert("提示","请上传附件");
						return false;
					}
					var cardNumber=Ext.getCmp('itil_ac_PersonFormalAccount$cardNumber').getValue();
			    	if(cardNumber==''||cardNumber=='null'){
						Ext.MessageBox.alert("提示","请填写令牌卡号");
						return false;
					}
					var pingcode=Ext.getCmp('itil_ac_PersonFormalAccount$pingCode').getValue().trim();
			    	if(pingcode==''||pingcode=='null'){
						Ext.MessageBox.alert("提示","请填写启动密码");
						return false;
					}
					var rightDesc=getEncodeValue('itil_ac_PersonFormalAccount$rightsDesc');
					var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
						Ext.Ajax.request({
							url : webContext+ '/accountAction_saveRemoteAccessAccInfo.action',
							params : {
								rightDesc : rightDesc,
								cardNumber:cardNumber,
								pingCode:pingcode,
								userInfo:userInfo,
								attachmentFlag:attachmentFlag,
								vpnType:vpnType,
								dataId:dataId
							},
	                        success : function(response, options) {
								window.parent.auditContentWin.specialAudit();
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("保存失败");
							}
						}, this);
				}
					
				}
			}
			,{
				text : '驳回',
				iconCls : 'back',
				handler : function() {
					window.parent.auditContentWin.specialNoAudit();
				}
			}],
        buttonAlign:'center'
		
		});
		if (this.dataId == "0" || this.dataId == "null") {
	
			
			
			} else {
			this.formpanel_PersonAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getPersonApplyDraftData.action?panelName=panel_PersonAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
				 		 Ext.getCmp("AccountApplyMainTable$applyUserCombo").initComponent();
					     Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
					   //  Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
						 Ext.getCmp("itil_ac_PersonFormalAccount$drawSpaceCombo").initComponent();
						 Ext.getCmp("sUserInfos$personnelScopeCombo").initComponent();
						 Ext.getCmp('itil_ac_PersonFormalAccount$drawSpaceCombo').hide();
                   	 	 Ext.getCmp('htmldrawSpace').hide();
                   	 	 Ext.getCmp('itil_ac_PersonFormalAccount$vpnType').setValue('1');
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
		this.model = "ac_BIAccountApply";
		

		this.getFormpanel_PersonAccountApply_Input();
		this.pa.push(this.formpanel_PersonAccountApply_Input);
		this.formname.push("panel_PersonAccountApply_Input");
		temp.push(this.formpanel_PersonAccountApply_Input);
		temp.push(histroyForm);
	
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})