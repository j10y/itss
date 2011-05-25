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
			tbar : [new Ext.Toolbar.TextItem('<font color=red size=2px>提示：</font><font color=blue size=2px>1.页面中带红色<font color=red>*</font>号的必填项，请在填写完整后再提交申请！<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<font  size=2px>2.页面中<font color=red size=2px>使用人姓名</font>请输入帐号持有人的<font color=red>中文姓名.</font>临时员工帐号申请仅限签订合同的实习生和劳务人员.<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<font color=red>派遣员工</font>提交申请请使用“IT基础类网络/通讯/邮件等帐号及资源申请”→“员工-入职IT服务注册申请（代申请，包括派遣员工）” </font>')]

		});
		return this.tabPanel;

	},

	getFormpanel_SpecailAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		var description=this.description;
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
				name : 'AccountApplyMainTable$platFormHRCountSign',
				mapping : 'AccountApplyMainTable$platFormHRCountSign'
			},
			{
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
				name : 'itil_ac_SpecialAccount$accountowner',
				mapping : 'itil_ac_SpecialAccount$accountowner'
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
			title : "临时人员邮件/域帐号申请",
			items : [{
				xtype : 'fieldset',
				title : '申请人信息',
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
					html : '申请编号:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '申请编号',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					readOnly : true,
					emptyText : '自动生成',
					id : 'AccountApplyMainTable$name',
					name : 'AccountApplyMainTable$name',
					style : '',
					width : 200,
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
					hideTrigger : true,
					allowBlank : true,
					readOnly : true,
					validator : '',
					format : 'Y-m-d',
					fieldLabel : '申请日期'
				}), {
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
					hideTrigger : true,
					readOnly : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请输入ITCODE进行选择...',
					allowBlank : true,
					
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
				}), {
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
					value : '',
					hideTrigger : true,
					readOnly : true,
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
					value : '',
					hideTrigger : true,
					readOnly : true,
					allowBlank : true,
					validator : '',
					vtype : ''
				}),
						// {
						// html : '邮件等价名部门:',
						// cls : 'common-text',
						// style : 'width:135;text-align:right'
						// }, new Ext.form.ComboBox({forceSelection:true,
						// xtype : 'combo',
						// hiddenName : 'sUserInfos$sameMailDept',
						// id : 'sUserInfos$sameMailDeptCombo',
						// width : 200,
						// style : '',
						// fieldLabel : '邮件等价各部门',
						// colspan : 0,
						// rowspan : 0,
						// hideTrigger:true,
						// readOnly : true,
						// lazyRender : true,
						// displayField : 'name',
						// valueField : 'id',
						// emptyText : '请选择...',
						// allowBlank : true,
						// typeAhead : true,
						// name : 'sUserInfos$sameMailDept',
						// triggerAction : 'all',
						// minChars : 50,
						// queryDelay : 700,
						// store : new Ext.data.JsonStore({
						// url : webContext
						// +
						// '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.SameMailDept',
						// fields : ['id', 'name'],
						// listeners : {
						// beforeload : function(store, opt) {
						// if (opt.params['sUserInfos$sameMailDept'] ==
						// undefined) {
						// opt.params['name'] = Ext
						// .getCmp('sUserInfos$sameMailDeptCombo').defaultParam;
						// }
						// }
						// },
						// totalProperty : 'rowCount',
						// root : 'data',
						// id : 'id'
						// }),
						// pageSize : 10,
						// listeners : {
						// 'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						// var param = queryEvent.combo.getRawValue();
						// this.defaultParam = param;
						// if (queryEvent.query == '') {
						// param = '';
						// }
						// this.store.load({
						// params : {
						// name : param,
						// start : 0
						// }
						// });
						// return true;
						// }
						// },
						// initComponent : function() {
						// this.store.load({
						// params : {
						// id : Ext.getCmp('sUserInfos$sameMailDeptCombo')
						// .getValue(),
						// start : 0
						// },
						// callback : function(r, options, success) {
						// Ext
						// .getCmp('sUserInfos$sameMailDeptCombo')
						// .setValue(Ext
						// .getCmp('sUserInfos$sameMailDeptCombo')
						// .getValue());
						// }
						// });
						// }
						// }), {
						// html : '邮件服务器:',
						// cls : 'common-text',
						// style : 'width:135;text-align:right'
						// }, new Ext.form.TextField({
						// fieldLabel : '邮件服务器:',
						// xtype : 'textfield',
						// colspan : 0,
						// rowspan : 0,
						// id : 'mailServer',
						// name : 'mailServer',
						// style : '',
						// width : 200,
						// value : 'BJMS0',
						// readOnly : true,
						// allowBlank : false,
						// validator : '',
						// vtype : ''
						// }),
						{
							html : '用户类别/员工组:',
							cls : 'common-text',
							style : 'width:135;text-align:right'
						}, new Ext.form.ComboBox({forceSelection:true,
							xtype : 'combo',
							hiddenName : 'sUserInfos$userType',
							id : 'sUserInfos$userTypeCombo',
							width : 200,
							style : '',
							fieldLabel : '用户类型',
							colspan : 0,
							hideTrigger : true,
							readOnly : true,
							rowspan : 0,
							lazyRender : true,
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
										id : Ext
												.getCmp('sUserInfos$userTypeCombo')
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
							hideTrigger : true,
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
						{
				html : '所属平台:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$platFormHRCountSign',
				id : 'AccountApplyMainTable$platFormHRCountSignCombo',
				width : 200,
				style : '',
				fieldLabel : '平台HR加签人',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				editable:false,
				displayField : 'department',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				typeAhead : true,
				name : 'AccountApplyMainTable$platFormHRCountSign',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.PlatFormHRCountSign',
					fields : ['id', 'department'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['AccountApplyMainTable$platFormHRCountSign'] == undefined) {
								opt.params['department'] = Ext
										.getCmp('AccountApplyMainTable$platFormHRCountSignCombo').defaultParam;
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
											.getCmp('AccountApplyMainTable$platFormHRCountSignCombo')
											.getRawValue();
									if (mailvalue == '无') {
										Ext.MessageBox.alert("提示",
												"请您选择具体的平台,谢谢您的合作！", function(
														btn) {
													Ext
															.getCmp('AccountApplyMainTable$platFormHRCountSignCombo')
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
							html : '<font color=red>*</font>部门审批人:',
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
									var mailvalue = Ext
											.getCmp('AccountApplyMainTable$confirmUserCombo')
											.getRawValue();
									if (mailvalue == '无') {
										Ext.MessageBox.alert("提示",
												"请您选择具体的审批人,谢谢您的合作！", function(
														btn) {
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
						})
					
						]
			}, {
				xtype : 'fieldset',
				layout : 'table',
				anchor : '100%',
				animCollapse : true,
				collapsible : false,
				autoHeight : true,
				style : 'border:1px dotted #b0acac;margin:5px 0px 0px 0px',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 1
				},

				items : [{
					html : '<font size=1px>说明...</font>',
					cls : 'common-text',
					style : 'margin:0px 0px 0px 70px'

				}]
			},

			{
				xtype : 'fieldset',
				title : '申请帐号信息',
				layout : 'table',
				anchor : '100%',
				animCollapse : true,
				collapsible : true,
				autoHeight : true,
				style : 'border:1px dotted #b0acac;margin:5px 0px 0px 0px',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 1
				},

				items : [

				{
					xtype : 'fieldset',
					collapsible : false,
					title : '域账号(必选)',
					autoHeight : true,
					defaults : {
						width : 210
					},
					style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
					cls : 'common-text',
					defaultType : 'textfield',
					html : '<font color=red>必选项，由帐号管理员配置生成！</font>'
				},

				{
					xtype : 'fieldset',
					title : '邮件帐号信息',
					layout : 'table',
					anchor : '100%',
					animCollapse : true,
					collapsible : true,
					autoHeight : true,
					style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layoutConfig : {
						columns : 4
					},
					items : [{
						html : '<font color=red>*</font>使用人姓名:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.TextField({
						fieldLabel : '帐号名称',
						xtype : 'textfield',
						colspan : 0,
						rowspan : 0,
						id : 'itil_ac_SpecialAccount$erpUserName',
						name : 'itil_ac_SpecialAccount$erpUserName',
						emptyText : '请输入帐号持有人的中文名称',
						style : '',
						width : 200,
						value : '',
						allowBlank : false,
						validator : '',
						regex : /^[\u4E00-\u9FA5]+$/,
						regexText : '只能输入汉字',
						vtype : ''
					}), {
						html : '邮箱容量:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.TextField({
						fieldLabel : '邮箱容量',
						xtype : 'textfield',
						colspan : 0,
						rowspan : 0,
						id : 'mailValue',
						name : 'mailValue',
						style : '',
						width : 200,
						value : '50M',
						allowBlank : true,
						readOnly : true,
						validator : '',
						vtype : ''
					}), {
						html : '<font color=red>*</font>申请原因:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.TextArea({
						xtype : 'textarea',
						id : 'itil_ac_SpecialAccount$applyReason',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$applyReason',
						width : 200,
						// height : null,
						style : '',
						value : '',
						allowBlank : false,
						validator : '',
						fieldLabel : '申请原因'
					}), {
						html : '备注说明:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.TextArea({
						xtype : 'textarea',
						id : 'itil_ac_SpecialAccount$remarkDesc',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$remarkDesc',
						width : 200,
						// height : null,
						style : '',
						value : '',
						allowBlank : true,
						validator : '',
						fieldLabel : '备注说明'
					}), {
						html : '<font color=red>*</font>工作地点:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({forceSelection:true,
						xtype : 'combo',
						hiddenName : 'itil_ac_SpecialAccount$workSpace',
						id : 'itil_ac_SpecialAccount$workSpaceCombo',
						width : 200,
						style : '',
						fieldLabel : '工作地点',
						colspan : 0,
						rowspan : 0,
						editable:false,
						lazyRender : true,
						displayField : 'name',
						valueField : 'id',
						emptyText : '请选择...',
						allowBlank : false,
						
						name : 'itil_ac_SpecialAccount$workSpace',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.WorkSpace',
							fields : ['id', 'name'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['itil_ac_SpecialAccount$workSpace'] == undefined) {
										opt.params['name'] = Ext
												.getCmp('itil_ac_SpecialAccount$workSpaceCombo').defaultParam;
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
								var workSpace=Ext.getCmp('itil_ac_SpecialAccount$workSpaceCombo').getRawValue();
					          if(workSpace=='无'||workSpace==''){
					            Ext.MessageBox.alert("提示","请您选择工作地点,谢谢您的合作！",function(btn){
					            Ext.getCmp('itil_ac_SpecialAccount$workSpaceCombo').setValue("");
					           });
					          }

								var workSpace = Ext
										.getCmp('itil_ac_SpecialAccount$workSpaceCombo')
										.getValue();
								Ext.Ajax.request({
									url : webContext
											+ '/accountAction_getMailServer.action',
									params : {
										workSpace : workSpace
									},

									success : function(response, options) {
										var r = Ext
												.decode(response.responseText);
										var mail = r.mailServer;
										Ext
												.getCmp("itil_ac_SpecialAccount$mailServer")
												.setValue(mail);
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
						html : '初始口令:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.TextField({
						fieldLabel : '初始口令',
						xtype : 'textfield',
						colspan : 0,
						rowspan : 0,
						id : 'itil_ac_SpecialAccount$password',
						name : 'itil_ac_SpecialAccount$password',
						style : 'color:red',
						width : 200,
						readOnly : true,
						emptyText : 'password123',
						value : '123',
						allowBlank : true,
						validator : '',
						vtype : ''
					}),

					new Ext.form.Hidden({
						fieldLabel : '邮件服务器',
						xtype : 'hidden',
						colspan : 0,
						rowspan : 0,
						id : 'itil_ac_SpecialAccount$mailServer',
						name : 'itil_ac_SpecialAccount$mailServer',
						style : '',
						width : 200,
						value : '',
						readOnly : true,
						allowBlank : true,
						validator : '',
						vtype : ''
					}), new Ext.form.Hidden({
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
						id : 'itil_ac_SpecialAccount$id',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$id',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '自动编号'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$accountType',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$accountType',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '帐号类型'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$accountState',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$accountState',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '帐号状态'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$createDate',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$createDate',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '创建时间'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$confirmUser',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$confirmUser',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '帐号管理员'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$ifHold',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$ifHold',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '是否保留'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$ifLocked',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$ifLocked',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '是否锁定'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$olodApplyAccount',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$olodApplyAccount',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '申请前帐号'
					}), new Ext.form.Hidden({
						xtype : 'hidden',
						id : 'itil_ac_SpecialAccount$applyId',
						colspan : 0,
						rowspan : 0,
						name : 'itil_ac_SpecialAccount$applyId',
						width : 200,
						style : '',
						value : '',
						fieldLabel : '申请编号'
					})]
				}]
			}, {
				xtype : 'fieldset',
				anchor : '100%',
				animCollapse : true,
				collapsible : false,
				style : 'border:1px dotted #b0acac;margin:5px 0px 0px 0px;',
				autoHeight : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},

				items : [{
					html : '1、什么是临时员工帐号？<br>&nbsp&nbsp&nbsp&nbsp临时员工帐号为集团临时人员（如顾问人员、实习人员、劳务人员）。常规帐号命名规则为：姓名为两个字的取“汉语拼音全拼”<br>&nbsp&nbsp&nbsp&nbsp附加“阿拉伯数字”标识，名称为三个字或四个字的为姓氏的全拼和名字的首写字母，附加“阿拉伯数字”标识，如王刚命名为<br>&nbsp&nbsp&nbsp&nbspwanggang1。个人临时帐号不予创建邮件中文等价名。<br>2、由谁对临时员工帐号负责？<br>&nbsp&nbsp&nbsp&nbsp申请临时员工帐号的正式员工为临时员工帐号的管理员，该正式员工对临时员工帐号负责。临时员工邮件帐号需要每三个月进行一次<br>&nbsp&nbsp&nbsp&nbsp确认，由管理者进行确认。<br>3、帐号费用如何分摊？<br>&nbsp&nbsp&nbsp&nbsp帐号的费用将分摊到确认人所对应的成本中心。',
					cls : 'common-text',
				    style : 'margin:0px 0px 0px 15px;line-height:18px;'

				}]
			}],
			buttons : [{
				text : '保存为草稿',
				iconCls : 'save',
				id : 'save',
				handler : function() {
					if (!Ext.getCmp('panel_SpecailAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					var curDataId = this.dataId;
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "申请人不能和审批人相同,请确认后再保存！");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();


					var info = Ext.encode(getFormParam('panel_SpecailAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveTempMailOrDomainApplyDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processType : processType,
							processInfoId : processInfoId,
							accountType : '临时人员邮件/域帐号',
							panelName : 'panel_SpecailAccountApply_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_SpecailAccountApply_Input')
									.load({
										url : webContext
												+ '/accountAction_getTempMailDraftData.action?panelName=panel_SpecailAccountApply_Input&dataId='
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
				id : 'submit',
				handler : function() {

					if (!Ext.getCmp('panel_SpecailAccountApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
					
					if(confirmUser==''||confirmUser==null){
					      Ext.MessageBox.alert("提示","部门审批人必须从下拉列表中选择,谢谢您的合作!");
					      return false;
				     }	
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "申请人不能和审批人相同,请确认后再保存！");
						return false;
					}
			
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var curDataId = this.dataId;

					var info = Ext.encode(getFormParam('panel_SpecailAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveTempMailOrDomainApplyDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							accountType : '临时人员邮件/域帐号',
							panelName : 'panel_SpecailAccountApply_Input'
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
							var userInfo = Ext
									.getCmp('AccountApplyMainTable$applyUserCombo')
									.getValue();
									
							// Ext.MessageBox.alert("保存成功");
							// //////////////////////////////////////////////////////////////////
							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_applyTempMail.action',
								params : {
									dataId : curId,
									userInfo : userInfo,
									description:description,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject',"
											+ "applyTypeName: '临时人员邮件/域账号申请',"
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
									Ext.MessageBox.alert("提示",
											"提交申请失败,请检查审批人是否选择正确!");
									Ext.getCmp("save").enable();
									Ext.getCmp("submit").enable();
									Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "提交申请失败,请检查审批人是否选择正确!");
							Ext.getCmp("save").enable();
							Ext.getCmp("submit").enable();
							Ext.getCmp("back").enable();
						}
					}, this);
				}
			}, {
				text : '返回',
				id : 'back',
				iconCls : 'back',
				handler : function() {
					window.history.back(-1);
				}
			}],
			buttonAlign:'center'
		});
		if (this.dataId == "0" || this.dataId == "null") {
			
			Ext.getCmp('panel_SpecailAccountApply_Input').form.load({
				url : webContext
						+ '/accountAction_initSpecailAccountApplyData.action',
				params : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_SpecailAccountApply_Input'
				},
				timeout : 30,
				success : function(action, form) {
					        var userType= Ext.getCmp("sUserInfos$userTypeCombo").getValue();
								if(userType==7){
								Ext.MessageBox.alert("系统提示","您属于派遣员工，尚没有权限做此申请！",function(btn){
								window.history.back(-1);
							})}
								else if(userType==9){
								Ext.MessageBox.alert("系统提示","您属于派遣(厂商)员工，尚没有权限做此申请！",function(btn){
								window.history.back(-1);
							})}
							//modify by liuying for 修改申请人用户类型验证 at 20100428 start
//								else if(userType==4){
//								Ext.MessageBox.alert("系统提示","您属于临时员工，尚没有权限做此申请！",function(btn){
//								window.history.back(-1);
//							})}
							//modify by liuying for 修改申请人用户类型验证 at 20100428 end
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();

					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
							 Ext.getCmp("sUserInfos$personnelScopeCombo")
							.initComponent();
				   
				},
				failure : function(response, options) {

				}
			})

		} else {
			this.formpanel_SpecailAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getSpecailDraftData.action?panelName=panel_SpecailAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
							Ext.getCmp("itil_ac_SpecialAccount$workSpaceCombo")
							.initComponent();

					Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
					Ext.getCmp("AccountApplyMainTable$platFormHRCountSignCombo")
                            .initComponent();
                             Ext.getCmp("sUserInfos$personnelScopeCombo")
							.initComponent();
				}
			});
		}
		return this.formpanel_SpecailAccountApply_Input;
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
		this.model = "ac_TempMailAccountApply";

		this.getFormpanel_SpecailAccountApply_Input();
		this.pa.push(this.formpanel_SpecailAccountApply_Input);
		this.formname.push("panel_SpecailAccountApply_Input");
		temp.push(this.formpanel_SpecailAccountApply_Input);
		// items = temp;
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