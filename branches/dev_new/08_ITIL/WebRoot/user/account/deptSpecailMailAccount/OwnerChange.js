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
			},{
				name : 'AccountApplyMainTable$platFormHRCountSign',
				mapping : 'AccountApplyMainTable$platFormHRCountSign'
			},
			 {
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
			title : "部门特殊邮件所有者变更申请",
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
			}),
			{
				html : '<font color=red>*</font>代申请人:',
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
				hideTrigger:true,
				readOnly : true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				
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
				html : '<font color=red>*</font>帐号原所有者:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_SpecialAccount$accountOldUser',
				id : 'itil_ac_SpecialAccount$accountOldUserCombo',
				width : 200,
				style : '',
				fieldLabel : '帐号原所有者',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				listWidth:500,
				name : 'itil_ac_SpecialAccount$accountOldUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
				    url : webContext
								+ "/actorUtilAction_getAllUserForCombo.action",
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
					 
					 var param ={
			          	serviceItemId : this.serviceItemId,
					    processType :this.processType,
					    userInfo:userInfo,
					    accountType:'部门特殊邮件帐号',
					    panelName : 'panel_SpecailAccountApply_Input'
			          }
//		              Ext.getCmp('account').store.load();
		              Ext.getCmp('account').store.removeAll();
		              Ext.getCmp('account').store.load({
			             params : param
		                })
		              Ext.getCmp('account').view.refresh()
		              
		             
		             
		              
		              
						 
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
				html : '<font color=red>*</font>联系电话:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '手机号码',
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
				html : '<font color=red>*</font>帐号新所有者:',
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
				listWidht:500,
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
								if(r.userType==7){
								Ext.MessageBox.alert("提示","申请人为派遣员工不能够申请该帐号！",function(btn){
								 Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .setValue("");
						    	})}
								else if(r.userType==9){
								Ext.MessageBox.alert("提示","申请人为派遣(厂商)员工不能够申请该帐号！",function(btn){
								 Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .setValue("");
						    	})}
						    	//modify by liuying for 修改申请人用户类型验证 at 20100428 start
//							   else if(r.userType==3){
//								Ext.MessageBox.alert("提示","申请人为试用员工不能够申请该帐号！",function(btn){
//								 Ext.getCmp("AccountApplyMainTable$applyUserCombo")
//							           .setValue("");
//						    	})}
//								else if(r.userType==4){
//								Ext.MessageBox.alert("提示","申请人为临时员工不能够申请该帐号！",function(btn){
//								 Ext.getCmp("AccountApplyMainTable$applyUserCombo")
//							           .setValue("");
//							   })}
								//modify by liuying for 修改申请人用户类型验证 at 20100428 end
							 },

							    failure : function(response, options) {
								Ext.MessageBox.alert("提示","申请人数据加载失败，请联系管理员处理！");
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
				html : '<font color=red>*</font>联系电话:',
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
			}), 
				{
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
				displayField : 'department',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				editable:false,
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
					'beforequery' : function(queryEvent) {
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
					'beforequery' : function(queryEvent) {
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
			})]}, 
			{
			xtype : 'fieldset',
            title : '申请帐号信息',
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
			new Ext.Panel({
		    frame:true,
		    style:'margin:0px 0px 0px 25px',
			items:[this.grid2]}
			)
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
				id : 'itil_ac_SpecialAccount$password',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$password',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '密码'
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
			})]}],
				buttons : [{
				text : '保存为草稿',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('panel_SpecailAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					
					var applyUser = Ext
							.getCmp('AccountApplyMainTable$applyUserCombo')
							.getValue();
					if (applyUser == null || applyUser == '') {
						Ext.MessageBox.alert("提示", "帐号新所有者不能为空或选择不正确！");
						return false;
					}
					var delegateApplyUser = Ext
							.getCmp('AccountApplyMainTable$delegateApplyUserCombo')
							.getValue();
					var confirmUser = Ext
							.getCmp('AccountApplyMainTable$confirmUserCombo')
							.getValue();
					if (applyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "帐号新所有者不能和审批人相同,请确认后再保存！");
						return false;
					}
					if (delegateApplyUser == confirmUser) {
						Ext.MessageBox.alert("提示", "代申请人不能和审批人相同,请确认后再保存！");
						return false;
					}
					
				   var record =  Ext.getCmp('account').getSelectionModel().getSelected();
		           var records =  Ext.getCmp('account').getSelectionModel().getSelections();
		         
		            if (!record) {
			        Ext.Msg.alert("提示", "请先选择要变更的帐号!");
			        return;
		           }
		           if (records.length == 0) {
			       Ext.MessageBox.alert('警告', '最少选择一条信息，进行变更!');
			         return;
		           }
		           Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
		            var curDataId = this.dataId;
					var info = Ext.encode(getFormParam('panel_SpecailAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveAccountApplyInfo.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processType:processType,
							 processInfoId:processInfoId,
							accountType:'部门特殊邮件帐号',
							records:records,
							panelName : 'panel_SpecailAccountApply_Input'
						},

				success : function(response, options) {
				    var responseArray = Ext.util.JSON.decode(response.responseText);
				    var curId = responseArray.id;
			        var accountNewOwner=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
			        var telephone=Ext.getCmp('itil_ac_SpecialAccount$telephone').getValue();
                    var id = new Array();
					var applyReason = new Array();
					var accountName=new Array();
					for(var i=0;i<records.length;i++){
				       id[i] = records[i].get('id');
				       applyReason[i]=records[i].get('applyReason');
				       accountName[i]=records[i].get('accountName');
				       Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveOwnerChangeAccountInfo.action',
						params : {id:id[i],applyReason:applyReason[i],curId:curId,accountNewOwner:accountNewOwner,telephone:telephone,accountName:accountName[i],accountType:'DeptMail'},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       	 Ext.Msg.alert("提示","保存失败");
	                       	 Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
	                       		 return false;	
	                       }
	                       else{
                              
	                       }
	                       
	                   },scope:this});
				}
				Ext.Msg.alert("提示", "保存成功", function() {
										window.location = webContext
												+ "/requireSIAction_toRequireInfoByServiceItemId2.action?id="
												+ curscid;
									});
			Ext.getCmp("save").enable();
			Ext.getCmp("submit").enable();
			Ext.getCmp("back").enable();
			   }
					});
			}}
			,
				{
				text : '提交申请',
				iconCls : 'submit',
					id:'submit',
				handler : function() {
					if(!Ext.getCmp('panel_SpecailAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var OldUser=Ext.getCmp('itil_ac_SpecialAccount$accountOldUserCombo').getValue();
					if(applyUser==''||applyUser==null){
					      Ext.MessageBox.alert("提示","帐号新所有者必须从下拉列表中选择,谢谢您的合作!");
					      return false;
    				 }
					if(confirmUser==''||confirmUser==null){
					      Ext.MessageBox.alert("提示","审批人必须从下拉列表中选择,谢谢您的合作!");
					      return false;
				     }
					if(OldUser==''||OldUser==null){
					 Ext.MessageBox.alert("提示","帐号原所有者必须从下拉列表中选择,谢谢您的合作！");
		                return false;
					}
					
					if(delegateApplyUser==confirmUser){
					 Ext.MessageBox.alert("提示","代申请的人不能和审批人相同,请确认后再保存！");
		                return false;
					}
					if(applyUser==confirmUser){
					 Ext.MessageBox.alert("提示","帐号新所有者不能和审批人相同,请确认后再保存！");
		                return false;
					}
					if(OldUser==confirmUser){
					 Ext.MessageBox.alert("提示","帐号原所有者不能和审批人相同,请确认后再保存！");
		                return false;
					}
					var records = Ext.getCmp('account').getStore().getRange(0,
						          Ext.getCmp('account').getStore().getCount());
					              if (records.length <1) {
			                      Ext.MessageBox.alert('警告', '不存在可变更的部门邮件帐号!');
			                      return false;
					              }
				   var record =  Ext.getCmp('account').getSelectionModel().getSelected();
		           var records =  Ext.getCmp('account').getSelectionModel().getSelections();
		            if (!record) {
			        Ext.Msg.alert("提示", "请先选择要变更的帐号!");
			        return;
		           }
		           if (records.length == 0) {
			       Ext.MessageBox.alert('警告', '最少选择一条信息，进行变更!');
			         return;
		           }
		           Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
		            var curDataId = this.dataId;
					var info = Ext.encode(getFormParam('panel_SpecailAccountApply_Input'));
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveAccountApplyInfo.action',
						params : {
							info : info,
							serviceItemId : curscid,
							processType:processType,
							processInfoId:processInfoId,
							accountType:'部门特殊邮件帐号',
							panelName : 'panel_SpecailAccountApply_Input'
						},
						
						
					success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
				      var curId = responseArray.id;
				      var telephone=Ext.getCmp('itil_ac_SpecialAccount$telephone').getValue();
				      var accountNewOwner=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					  var id = new Array();
					  var applyReason = new Array();
					  var accountName=new Array();
					  for(var i=0;i<records.length;i++){
				       id[i] = records[i].get('id');
				       applyReason[i]=records[i].get('applyReason');
				       accountName[i]=records[i].get('accountName');
				       Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveOwnerChangeAccountInfo.action',
						params : {id:id[i],applyReason:applyReason[i],curId:curId,accountNewOwner:accountNewOwner,telephone:telephone,accountName:accountName[i],accountType:'DeptMail'},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		
	                       }
	                       else{
                  
	                       }
	                       
	                   },scope:this});
				};
				         
						var curName = responseArray.applyId;
						   var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					 		//Ext.MessageBox.alert("保存成功");
							// //////////////////////////////////////////////////////////////////
							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_applyDeptMail.action',
								params : {
									dataId : curId,
									userInfo:userInfo,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject'," 
											+ "applyTypeName: '部门特殊邮件帐号所有者变更申请',"
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
									Ext.MessageBox.alert("提示", "提交申请失败,请检查审批人是否选择正确");
									Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
								}
							}, this);
				
			   },
                 
						failure : function(response, options) {
								Ext.MessageBox.alert("提示", "提交申请失败,请检查帐号所有者是否选择正确");
							Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
						}
					}, this);
				}
			}, {
				text : '返回',
			    id:'back',
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
						    + '/accountAction_initNewApplyData.action',
				                params : {
							    serviceItemId : this.serviceItemId,
							    processType :this.processType,
							    panelName : 'panel_SpecailAccountApply_Input'
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
						+ '/accountAction_getSpecailDraftData.action?panelName=panel_SpecailAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					 Ext.getCmp('account').store.load();
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
					
					          Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
							  Ext.getCmp("itil_ac_SpecialAccount$accountOldUserCombo")
							.initComponent();
						 Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
							  Ext.getCmp("AccountApplyMainTable$platFormHRCountSignCombo")
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
		 if(this.status==1){
		  var histroyForm = new HistroyForm({
		   reqId : this.dataId,
		   reqClass : "com.zsgj.itil.require.entity.AccountApplyMainTable"
		  });
		  }else{
		         var histroyForm = new HistroyGrid({
		   reqId : this.dataId,
		   reqClass : "com.zsgj.itil.require.entity.AccountApplyMainTable"
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
		this.model = "ac_DeptSMailOwnerChange";
        if (this.dataId == "0" || this.dataId == "null") {
		this.store1 = new Ext.data.JsonStore({
			          url : webContext + "/accountAction_listSAOwnerChangeAccount.action",
			          fields: ['id','accountName','accountNowUser','applyReason'],
			          root:'data',
			          totalProperty : 'rowCount',
			          sortInfo: {field: "id", direction: "ASC"},
			          baseParams :{
			          	serviceItemId : this.serviceItemId,
					    processType :this.processType,
					    accountType:'部门特殊邮件帐号',
					    panelName : 'panel_SpecailAccountApply_Input'
			          }
		              });
		 }else{
		      	 this.store1 = new Ext.data.JsonStore({
			         url : webContext + "/accountAction_listSAOwnerUseerChoose.action",
			          fields: ['id','accountName','accountNowUser','applyReason'],
			          root:'data',
			          totalProperty : 'rowCount',
			          sortInfo: {field: "id", direction: "ASC"},
			          baseParams :{
			          	dataId:this.dataId
			          }
		              });
		               this.store1.load();
		                 this.store1.addListener("load",function(){
                        Ext.getCmp("account").getSelectionModel().selectAll();
                        });
		       }
		            var sm = new Ext.grid.CheckboxSelectionModel();
	                this.grid2 = new Ext.grid.EditorGridPanel({
	                   id:'account',
	                   name:'account',
	                   style:'margin:0px 0px 0px 25px;',
	                   store : this.store1,
	                   tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>双击变更原因单元格可以进行编辑</font>')],
			           bbar : [new Ext.Toolbar.TextItem('<font color=blue>如果用户需要变更多个帐号,可以将帐号前面的复选框选中!</font>')],
			           viewConfig: {
                              forceFit:true
                       },
			    columns : [new Ext.grid.RowNumberer(),sm, {header : '<font color=red>自动编号</font>',dataIndex : 'id',align : 'left',sortable : true,hidden : true}, 
						{header : '<font color=red>帐号名称</font>',dataIndex : 'accountName',align : 'center',sortable : true,hidden : false}, 
						{header : '<font color=red>帐号所有者ITcode</font>',dataIndex : 'accountNowUser',align : 'center',sortable : true,hidden : false}, 
						{header : '<font color=red>变更原因</font>',dataIndex : 'applyReason',align : 'center',sortable : true,hidden : false,editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true}))}
						],
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			stripeRows:true,
			width:665,
			autoHeight:true
			
		});
		

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
		if(this.status!=0){
			Ext.getCmp("save").hide();
		    Ext.getCmp("submit").hide();
		}
		if (this.readOnly == 1) {
			Ext.getCmp("back").hide();
		}
	}
})