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
				name : 'itil_ac_SpecialAccount$win7PaltForm',
				mapping : 'itil_ac_SpecialAccount$win7PaltForm'
			}, {
				name : 'itil_ac_SpecialAccount$deviceType',
				mapping : 'itil_ac_SpecialAccount$deviceType'
			}, {
				name : 'itil_ac_SpecialAccount$applyId',
				mapping : 'itil_ac_SpecialAccount$applyId'
			}]),
			title : "XP系统改装WIN7申请",
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
				readOnly : true,
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
				readOnly : true,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请输入ITCODE进行选择...',
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
										 			Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
													//Ext.getCmp('sUserInfos$userTypeCombo').setValue(r.userType);
													Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);
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
				readOnly : true,
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
				html : "<font color=red>*</font>隶属平台:",
				cls : 'common-text',
				style : 'width:135;text-align:right'
			},new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_SpecialAccount$win7PaltForm',
					id : 'itil_ac_SpecialAccount$win7PaltFormCombo',
					width : 200,
					style : '',
					fieldLabel : '工作地点',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'name',
					valueField : 'id',
					emptyText : '请选择...',
					readOnly : true,
					allowBlank : false,
					name : 'itil_ac_SpecialAccount$win7PaltForm',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/accountAction_listWin7PlatFormName.action',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['itil_ac_SpecialAccount$win7PaltForm'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('itil_ac_SpecialAccount$win7PaltFormCombo').defaultParam;
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
				html : "<font color=red>*</font>设备型号:",
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				name : "itil_ac_SpecialAccount$deviceType",
				fieldLabel : "设备型号",
				id : "itil_ac_SpecialAccount$deviceTypeCombo",
					xtype : 'combo',
					hiddenName : 'itil_ac_SpecialAccount$deviceType',
					width : 200,
					style : '',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'name',
					listWidth : 450,
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : false,
					readOnly : true,
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/accountAction_listDeviceTypeName.action',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['itil_ac_SpecialAccount$deviceType'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('itil_ac_SpecialAccount$deviceTypeCombo').defaultParam;
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
				fieldLabel : '审批人',hideTrigger : true,
				colspan : 0,
				rowspan : 0,
				readOnly : true,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请输入部门经理的ITCODE后选择...',
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
				{
					html : '<font color=red>*</font>申请原因:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'itil_ac_SpecialAccount$applyReason',
					colspan : 3,
					rowspan : 0,
					name : 'itil_ac_SpecialAccount$applyReason',
					width : 530,
					style : 'margin:0px 0px 0px 25px',
					readOnly : true,
					value : '',
					allowBlank : false,
					validator : '',
					fieldLabel : ''
				})
			]
			
			
			},
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
			})]},{
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
				columns : 2
			},
			items:[
			{
				html : '权限说明:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'itil_ac_SpecialAccount$rightsDesc',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_SpecialAccount$rightsDesc',
				width : 530,
				style : '',
				value : '',
				validator : '',
				fieldLabel : '权限说明'
			}) 
			]
			}],
			buttons : [{
				text : '保存并提交',
				iconCls : 'submit',
				handler : function() {
				
					var dataId=Ext.getCmp('AccountApplyMainTable$id').getValue();
					var rightDesc=getEncodeValue('itil_ac_SpecialAccount$rightsDesc');
					
					Ext.Ajax.request({
						url : webContext+ '/accountAction_saveSpecaiAccountInfo.action',
						params : {
							rightDesc : rightDesc,
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
			this.formpanel_SpecailAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getSpecailDraftData.action?panelName=panel_SpecailAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
								 Ext.getCmp("AccountApplyMainTable$applyUserCombo").initComponent();
					     		 Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
								 Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
								 Ext.getCmp("itil_ac_SpecialAccount$deviceTypeCombo").initComponent();
								 Ext.getCmp("itil_ac_SpecialAccount$win7PaltFormCombo").initComponent();
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
		this.model = "ac_TempMailAccountDelete";
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