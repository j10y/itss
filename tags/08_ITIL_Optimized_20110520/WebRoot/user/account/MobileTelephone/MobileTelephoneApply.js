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
			title : "手机额度及代缴申请",
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
					id : 'AccountApplyMainTable$name',
					name : 'AccountApplyMainTable$name',
					style : '',
					width : 200,
					value : '',
					readOnly : true,
					emptyText : '自动生成',
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
					hideTrigger : true,
					readOnly : true,
					validator : '',
					format : 'Y-m-d',
					fieldLabel : '申请日期'
				}), {
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
					lazyRender : true,
					hideTrigger : true,
					readOnly : true,
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
					fieldLabel : '代申请人联系电话',
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
									accountType : '手机'
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
													Ext.MessageBox.alert("系统提示","您属于派遣员工，尚没有权限做此申请！",function(btn){
													 Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue('');
													Ext.getCmp('sUserInfos$costCenterCode').setValue('');
													Ext.getCmp('sUserInfos$employeeCode').setValue('');
													Ext.getCmp('sUserInfos$userTypeCombo').setValue('');
													Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue('');
													Ext.getCmp('sUserInfos$postName').setValue('');
													Ext.getCmp('sUserInfos$titleCode').setValue('');
													Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').setValue('');
												})}else if(r.userType==9){
													Ext.MessageBox.alert("系统提示","您属于派遣(厂商)员工，尚没有权限做此申请！",function(btn){
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
												Ext.MessageBox.alert("提示","申请人数据加载失败，请联系管理员处理！");
												var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
												return false;
											}
										}, this);

									} else {
										Ext.MessageBox.alert("提示","申请人已经申请过手机补贴,不能够再申请！");
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
					// hideTrigger:true,
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
					html : '岗位编号:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '岗位编号',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'sUserInfos$titleCode',
					name : 'sUserInfos$titleCode',
					style : '',
					width : 200,
					value : '',
					readOnly : true,
					emptyText : '自动生成',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '岗位名称:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '申请编号',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'sUserInfos$postName',
					name : 'sUserInfos$postName',
					style : '',
					width : 200,
					value : '',
					readOnly : true,
					emptyText : '自动生成',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
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
					rowspan : 0,
					hideTrigger : true,
					readOnly : true,
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
				html : '<font color=red>*</font>隶属部门:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_MobileTelephoneApply$countSign',
				id : 'itil_ac_MobileTelephoneApply$countSignCombo',
				width : 200,
				style : '',
				editable : false,
				fieldLabel : '隶属部门',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'department',
				valueField : 'id',
				emptyText : '请选择...',
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
							if (mailvalue == '无') {
								Ext.MessageBox.alert("提示",
										"请您选择具体的部门,谢谢您的合作！", function(btn) {
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
					html : '<font color=red>*</font>隶属平台:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_MobileTelephoneApply$platForm',
					id : 'itil_ac_MobileTelephoneApply$platFormCombo',
					width : 200,
					style : '',
					fieldLabel : '工作地点',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					editable:false,
					
					displayField : 'name',
					valueField : 'id',
					emptyText : '请选择...',
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
							if (mailvalue == '无') {
								Ext.MessageBox.alert("提示",
										"请您选择具体的平台,谢谢您的合作！", function(btn) {
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
							if (mailvalue == '无') {
								Ext.MessageBox.alert("提示",
										"请您选择具体的审批人,谢谢您的合作！", function(btn) {
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
				html : '<font size=3px><a href="" target="_blank"  style="text-decoration:none"><b>《通讯管理规定》</b></a></font>',
				cls : 'common-text',
				style : 'margin:0px 0px 0px 170px'
				
				
			}
			]
			},
				{
				xtype : 'fieldset',
				title : '手机费补贴申请',
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
					html : '<font color=red>*</font>是否代缴:',
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
						data : [['1', '是'], ['0', '否']]
					}),
					emptyText : '请选择...',
					valueField : 'id',
					value : '',
					displayField : 'name',
					name : 'itil_ac_MobileTelephoneApply$payType',
					width : 200,
					fieldLabel : '是否代缴',
					listeners : {
						'expand' : function(combo) {
							combo.reset();
						}
					}
				}), {
					html : '补贴标准:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '补贴标准',
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
					html : '<font color=red>*</font>手机号码:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '手机号码',
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
					html : '部门确认补贴标准:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '部门补贴标准',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
					id : 'itil_ac_MobileTelephoneApply$deptAllowance',
					name : 'itil_ac_MobileTelephoneApply$deptAllowance',
					style : '',
					width : 200,
					value : '',
					emptyText : '如无特殊补贴标准,可不填',
					allowBlank : true,
					validator : '',
					vtype : ''
				}), {
					html : '<font color=red>*</font>代缴/补贴起始日期:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.NumberField({
					fieldLabel : '代缴变更起始日期',
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
					html : '月1日',
					cls : 'common-text',
					style : 'text-align:left;float:left'
				}, {},
					{
				html : '<font color=red>*</font>申请原因:',
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
					fieldLabel : '变更前申请'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_MobileTelephoneApply$applyId',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$applyId',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '申请编号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_MobileTelephoneApply$id',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '自动编号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'itil_ac_MobileTelephoneApply$accountowner',
					colspan : 0,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$accountowner',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '手机所有者'
				})]
			}],
			buttons : [{
				text : '保存为草稿',
				id : 'save',
				iconCls : 'save',
				handler : function() {
					if (!Ext.getCmp('panel_MobileTelephoneApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					//add by liuying for 增加补贴标准没有带出的验证 at 20100512 start
					var wance=Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').getValue();
					if(wance==0){
						Ext.MessageBox.alert("提示",
								"新入职的员工如果补贴标准没有自动带出，请联系手机管理员确认额度！");
						return false;
					}
					//add by liuying for 增加补贴标准没有带出的验证 at 20100512 end
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
					var info = Ext.encode(getFormParam('panel_MobileTelephoneApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveMobileTelephoneApplyDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processInfoId : processInfoId,
							processType : processType,
							accountType : '手机',
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

					if (!Ext.getCmp('panel_MobileTelephoneApply_Input').form
							.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					//add by liuying for 增加补贴标准没有带出的验证 at 20100512 start
					var wance=Ext.getCmp('itil_ac_MobileTelephoneApply$allowance').getValue();
					if(wance==0){
						Ext.MessageBox.alert("提示",
								"新入职的员工如果补贴标准没有自动带出，请第二天再提交该申请！");
						return false;
					}
					//add by liuying for 增加补贴标准没有带出的验证 at 20100512 end
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
							 	Ext.MessageBox.alert("提示","申请人已存在审批中的手机申请,不能够再提该申请！",function(btn){
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
					 Ext.MessageBox.alert("提示","代申请人不能和审批人相同,请确认后再保存！");
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
							accountType : '手机',
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
							// Ext.MessageBox.alert("保存成功");
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
											+ "applyTypeName: '手机额度及代缴申请',"
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
							Ext.MessageBox.alert("提示",
									"提交申请失败,请检查审批人或申请人是否选择正确!");
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
					accountType : '手机'
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
//								Ext.MessageBox.alert("系统提示","您属于派遣员工，尚没有权限做此申请！",function(btn){
//								window.history.back(-1);
//							})}
//							 if(userType==9){
//								Ext.MessageBox.alert("系统提示","您属于派遣(厂商)员工，尚没有权限做此申请！",function(btn){
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