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
			activeTab :0,
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

	getFormpanel_NewITAccountApply_Input : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId=this.serviceItemProcess;
		this.formpanel_NewITAccountApply_Input = new Ext.form.FormPanel({
			id : 'panel_NewITAccountApply_Input',
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
				name : 'itil_ac_PersonFormalAccount$drawSpace',
				mapping : 'itil_ac_PersonFormalAccount$drawSpace'
			},
			{
				name : 'itil_ac_PersonFormalAccount$voip',
				mapping : 'itil_ac_PersonFormalAccount$voip'
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
			},
            {
				name : 'itil_ac_PersonFormalAccount$ifHold',
				mapping : 'itil_ac_PersonFormalAccount$ifHold'
			},
			{
				name : 'www',
				mapping : 'www'
			},
			{
				name : 'te',
				mapping : 'te'
			},
			{
				name : 'mail',
				mapping : 'mail'
			},
			{
				name : 'telephone',
				mapping : 'telephone'
			},
			{
				name : 'mailServer',
				mapping : 'mailServer'
			},
			{
				name : 'telephoneRightsDesc',
				mapping : 'telephoneRightsDesc'
			},
			{
				name : 'wwwRightsDesc',
				mapping : 'wwwRightsDesc'
			},
			{
				name : 'mailRightsDesc',
				mapping : 'mailRightsDesc'
			},
			{
				name : 'domainRightsDesc',
				mapping : 'domainRightsDesc'
			}
			]),
			title : "新员工IT帐号申请",
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
			items : [
			{
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
				hideTrigger:true,
				readOnly : true,
				style : '',
				value : '',
				allowBlank : true,
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
					
				    readOnly : true,
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
				hideTrigger:true,
				readOnly : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
			    name : 'AccountApplyMainTable$applyUser',
				minChars : 50,
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
				html : '申请人联系电话:',
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
			
				readOnly : true,
				value : '',
				allowBlank : true,
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
				html : '邮件等价名部门:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'sUserInfos$sameMailDept',
				id : 'sUserInfos$sameMailDeptCombo',
				width : 200,
				style : '',
				hideTrigger:true,
				readOnly : true,
				fieldLabel : '邮件等价名部门',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'sUserInfos$sameMailDept',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.SameMailDept',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$sameMailDept'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('sUserInfos$sameMailDeptCombo').defaultParam;
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
				html : '工作地点:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'sUserInfos$workSpace',
				id : 'sUserInfos$workSpaceCombo',
				width : 200,
				style : '',
				fieldLabel : '工作地点',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				hideTrigger:true,
				readOnly : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'sUserInfos$workSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
													+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.WorkSpace',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['sUserInfos$workSpace'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('sUserInfos$workSpaceCombo').defaultParam;
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
				html : '邮件服务器:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '邮件服务器',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$mailServer',
				name : 'itil_ac_PersonFormalAccount$mailServer',
				style : '',
				width : 200,
				readOnly : true,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
			
				{
				html : '审批人:',
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
				hideTrigger:true,
				readOnly : true,
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
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
					var mailvalue=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getRawValue();
					if(mailvalue=='无'){
					Ext.MessageBox.alert("提示","请您选择具体的帐号审批人,谢谢您的合作！",function(btn){
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
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:20px 0px 0px 0px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns :1
			},
			
			items:[ 
			{
							xtype : 'fieldset',
							collapsible : false,
							title : '域账号(必选)',
							autoHeight : true,
							
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
							cls : 'common-text',
							defaultType : 'textfield',
							html : '<font color=red>必选项，由账号管理员配置生成！</font>'
		  },
		  {
			xtype : 'fieldset',
		    title : '邮件帐号信息(必选)',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :false,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[
			{
				html : '<font color=red>*</font>邮箱容量:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$mailValue',
				id : 'itil_ac_PersonFormalAccount$mailValueCombo',
				width : 200,
				style : '',
				fieldLabel : '邮箱容量',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'volume',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : false,
				typeAhead : true,
				name : 'itil_ac_PersonFormalAccount$mailValue',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.MailVolume',
					fields : ['id', 'volume'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$mailValue'] == undefined) {
								opt.params['volume'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$mailValueCombo').defaultParam;
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
								volume : param,
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
									.getCmp('itil_ac_PersonFormalAccount$mailValueCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('itil_ac_PersonFormalAccount$mailValueCombo')
									.setValue(Ext
											.getCmp('itil_ac_PersonFormalAccount$mailValueCombo')
											.getValue());
						}
					});
				}
			}), 
			{
				html : '邮件服务器:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '邮件服务器',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'mailServer',
				name : 'mailServer',
				style : '',
				width : 200,
				value : '',
			    readOnly : true,
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
			{
				html : '初始密码:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				id : 'itil_ac_PersonFormalAccount$password',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$password',
				width : 200,
			    style : 'color:red',
				emptyText : 'password123',
				value : 'password123',
				allowBlank : true,
				validator : '',
				fieldLabel : '备注说明'
			}),
			
			{
				html : '备注说明:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				id : 'itil_ac_PersonFormalAccount$remarkDesc',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$remarkDesc',
				width : 200,
			
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				fieldLabel : '备注说明'
			})
			]}, 
			{
			xtype : 'fieldset',
		    title : 'WWW帐号',
			layout : 'table',
		    anchor : '100%',
		    id:"wwwAccount",
		    animCollapse:true,
			collapsible :false,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[
			{
				html : '<font color=red>*</font>www帐号额度:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$wwwAccountValue',
				id : 'itil_ac_PersonFormalAccount$wwwAccountValueCombo',
				width : 200,
				style : '',
				fieldLabel : 'www帐号额度',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'type',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'itil_ac_PersonFormalAccount$wwwAccountValue',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.WWWScanType',
					fields : ['id', 'type'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$wwwAccountValue'] == undefined) {
								opt.params['type'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').defaultParam;
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
								type : param,
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
									.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
									.setValue(Ext
											.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo')
											.getValue());
						}
					});
				}
			}), 
			{
				html : '备注说明:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				id : 'itil_ac_PersonFormalAccount$applyReason',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$applyReason',
				width : 200,
				
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				fieldLabel : '申请原因'
			})]}, 
			{
			xtype : 'fieldset',
		    title : '电话申请',
			layout : 'table',
		    anchor : '100%',
		    id:'telephoneApply',
		    animCollapse:true,
			collapsible :false,
			autoHeight : true,
			style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[
			{
				html : '<font color=red>*</font>电话类型:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$telephoneType',
				id : 'itil_ac_PersonFormalAccount$telephoneTypeCombo',
				width : 200,
				style : '',
				fieldLabel : '电话类型',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'type',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'itil_ac_PersonFormalAccount$telephoneType',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.TelephoneType',
					fields : ['id', 'type'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$telephoneType'] == undefined) {
								opt.params['type'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').defaultParam;
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
								type : param,
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
									.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo')
									.getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext
									.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo')
									.setValue(Ext
											.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo')
											.getValue());
						}
					});
				}
			}),
			{
				html : '<font color=red>*</font>工位号码:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '工位号码',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$stationNumber',
				name : 'itil_ac_PersonFormalAccount$stationNumber',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '<font color=red>*</font>财年额度:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '财年额度',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$yearMoney',
				name : 'itil_ac_PersonFormalAccount$yearMoney',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			})]},    new Ext.form.Hidden({
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
				id : 'itil_ac_PersonFormalAccount$cardState',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$cardState',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '卡状态'
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
			}), 
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'telephone',
				colspan : 0,
				rowspan : 0,
				name : 'telephone',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '电话'
			}),
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'te',
				colspan : 0,
				rowspan : 0,
				name : 'te',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '电话'
			}),
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'mail',
				colspan : 0,
				rowspan : 0,
				name : 'mail',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '电话'
			}),
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'www',
				colspan : 0,
				rowspan : 0,
				name : 'www',
				width : 200,
				style : '',
				value : '',
				fieldLabel : 'www'
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
				html : '域帐号权限说明:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'domainRightsDesc',
				colspan : 3,
				rowspan : 0,
				name : 'domainRightsDesc',
				width : 530,
				
				style : '',
				value : '',
			
				validator : '',
				fieldLabel : '权限说明'
			}) ,
			{
				html : '邮件帐号权限说明:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'mailRightsDesc',
				colspan : 3,
				rowspan : 0,
				name : 'mailRightsDesc',
				width : 530,
				
				style : 'margin:5px 0px 5px 0px',
				value : '',
			
				validator : '',
				fieldLabel : '权限说明'
			}) ,
			{
				html : 'www帐号权限说明:',
				id:'wwwDesc',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'wwwRightsDesc',
				colspan : 3,
				rowspan : 0,
				name : 'wwwRightsDesc',
				width : 530,
				
				style : 'margin:5px 0px 5px 0px',
				value : '',
			
				validator : '',
				fieldLabel : '权限说明'
			}),
			{
				html : '座机权限说明:',
				cls : 'common-text',
			    id:'TeleDesc',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextArea({
				xtype : 'textarea',
				id : 'telephoneRightsDesc',
				colspan : 3,
				rowspan : 0,
				name : 'telephoneRightsDesc',
				width : 530,
				style : '',
				value : '',
			    validator : '',
				fieldLabel : '权限说明'
			}),
			{
				html : '分配电话号码:',
				cls : 'common-text',
				id:'number',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '电话号码',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$telephoneNumber',
				name : 'itil_ac_PersonFormalAccount$telephoneNumber',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
			{
				html : '分配的VOIP号码:',
				cls : 'common-text',
				id:'voip',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '电话号码',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$voip',
				name : 'itil_ac_PersonFormalAccount$voip',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
			{
				html : '附件:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, 
			{
			xtype:'panel',
			colspan : 3,
				rowspan : 0,
			layout:'table',width:250,layoutConfig:{columns:4},
			fieldLabel:'附件地址',defaults:{baseCls:'margin : 10 15 12 15'},
			items:[
			{
			fieldLabel:'附件',
			xtype:'button',
			text:'<font color=red>上 传</font>',
			width:50,
			scope:this,
			handler:function(){
			var attachmentFlag = Ext.getCmp('itil_ac_PersonFormalAccount$attachment').getValue();
			if(attachmentFlag==''||attachmentFlag=='null'){
			attachmentFlag = Date.parse(new Date());
			Ext.getCmp('itil_ac_PersonFormalAccount$attachment').setValue(attachmentFlag);
			var ud=new UpDownLoadFile();
			//String hiddenId = String.valueOf(System.currentTimeMillis());
			ud.getUpDownLoadFileSu(attachmentFlag,'7643','com.digitalchina.info.appframework.metadata.entity.SystemFile','newITAccount');
			}else{
			var ud=new UpDownLoadFile();
			ud.getUpDownLoadFileSu(attachmentFlag,'7643','com.digitalchina.info.appframework.metadata.entity.SystemFile','newITAccount');
			}}
			},
			{id:'newITAccount',width:600,border : true,html:'',cls : 'common-text',style : 'width:100;text-align:left'}]}, 
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_PersonFormalAccount$attachment',
				name : 'itil_ac_PersonFormalAccount$attachment',
				style : '',
				value : 'null',
				allowBlank : true,
				fieldLabel : 'nowtime'
			})
			]
			}
			],
	      buttons : [{
				text : '完成并提交',
				iconCls : 'submit',
				handler : function() {
				    var telephoneNumber=Ext.getCmp('itil_ac_PersonFormalAccount$telephoneNumber').getValue();
					if(Ext.getCmp('te').getValue()=="1"&&(telephoneNumber==""||telephoneNumber==null)){
					Ext.MessageBox.alert("提示","请填写分配的座机号码！");	
					return false;
					}
					//add by liuying for 座机管理员填写座机号码时不可以填写不完整的号码 at 20100415 start
					if(Ext.getCmp('te').getValue()=="1"&&telephoneNumber.length<7){
					Ext.MessageBox.alert("提示","请填写完整的座机号码！");
					return false;
					}
					//add by liuying for 座机管理员填写座机号码时不可以填写不完整的号码 at 20100415 end
					var account=Ext.getCmp('mail').getValue();
				    var attachment=Ext.getCmp('itil_ac_PersonFormalAccount$attachment').getValue();
					if(account=="mailAccount"&&attachment==""){
					Ext.MessageBox.alert("提示","您选择处理邮件帐号后必须上传附件!");
					return false;
					};
				     var dataId=Ext.getCmp('AccountApplyMainTable$id').getValue();
				     var domainRightsDesc=getEncodeValue('domainRightsDesc');
				     var mailRightsDesc=getEncodeValue('mailRightsDesc');
				     var wwwRightsDesc=getEncodeValue('wwwRightsDesc');
				     var telephoneRightsDesc=getEncodeValue('telephoneRightsDesc');
				     var telephoneNumber=Ext.getCmp('itil_ac_PersonFormalAccount$telephoneNumber').getValue();
				     var voip=Ext.getCmp('itil_ac_PersonFormalAccount$voip').getValue();
					 var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					 Ext.Ajax.request({
						url : webContext+ '/accountAction_saveNewITAccountInfo.action',
						params : {
							domainRightsDesc:domainRightsDesc,
							mailRightsDesc : mailRightsDesc,
							telephoneNumber:telephoneNumber,
							wwwRightsDesc:wwwRightsDesc,
							attachment:attachment,
							telephoneRightsDesc:telephoneRightsDesc,
							userInfo:userInfo,
							voip:voip,
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
			},{
				text : '驳回',
				iconCls : 'back',
				handler : function() {
					window.parent.auditContentWin.specialNoAudit();
			  }	
			}],
			buttonAlign:'center'
		});
		if (this.dataId == "0" || this.dataId == "null") {
	
							 Ext.getCmp('panel_NewITAccountApply_Input').form.load({
				                url : webContext
						    + '/accountAction_initNewApplyData.action',
				                params : {
							    serviceItemId : this.serviceItemId,
							    processType :this.processType,
							    panelName : 'panel_NewITAccountApply_Input'
				                },
				              timeout : 30,
				             success : function(action, form) {
                                 Ext.getCmp("AccountApplyMainTable$applyUserCombo").
							           setValue("");
							    Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
					
					             Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							     .initComponent();
				               },
				             failure : function(response, options) {
							   
		                      	
				              }
		                      })
		                  
							
						
				
			
			
			} else {
				
			var data=this.dataId;
			this.formpanel_NewITAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getNewITAccountDraftData.action?panelName=panel_NewITAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
				//add by liuying at 20100303 for 账号管理员处理时如已上传附件显示附件 start
				var da=new DataAction();
					var url=webContext
						+ '/accountAction_getNewITApplyFileList.action?clazzName=com.digitalchina.itil.account.entity.PersonFormalAccount&dataId='
						+ data+"&columnName=attachment&columnid=7643&hiddenId=newITAccount";
					
					var value=da.ajaxGetData(url);
//					alert(value.file);
					document.getElementById("newITAccount").innerHTML=value.file;
				//add by liuying at 20100303 for 账号管理员处理时如已上传附件显示附件 end
			    Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
					
			     Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							     .initComponent();
							     
							     Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .initComponent();

							    
				  if(Ext.getCmp('www').getValue()=="0"){
				   	Ext.getCmp("wwwAccount").hide(),
				   	Ext.getCmp('wwwRightsDesc').hide(),
				   	Ext.getCmp('wwwDesc').hide()
				}
				if(Ext.getCmp('telephone').getValue()=="0"){
					Ext.getCmp("telephoneApply").hide(),
						Ext.getCmp('telephoneRightsDesc').hide(),
							Ext.getCmp('itil_ac_PersonFormalAccount$telephoneNumber').hide(),
								Ext.getCmp('TeleDesc').hide(),	Ext.getCmp('number').hide(),
								Ext.getCmp('voip').hide(),
								Ext.getCmp('itil_ac_PersonFormalAccount$voip').hide()
				}
					Ext.getCmp("sUserInfos$sameMailDeptCombo")
							           .initComponent();
							           Ext.getCmp("sUserInfos$workSpaceCombo")
							           .initComponent();

//				if(Ext.getCmp('mail').getValue()=="1"){
//					Ext.MessageBox.confirm("温馨提示","如果您要处理邮件帐号,请您不要忘记上传附件!,您是否处理邮件帐号？",
//								function(button, text) {
//															if (button == 'yes') {
//																Ext.getCmp('mail').setValue("mailAccount");
//															} else {
//																
//															}
//
//														}
//					);
//				}
				
				}
			})
	   };
		return this.formpanel_NewITAccountApply_Input;
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
		this.model = "ar_NewITAccountApply";
		

		this.getFormpanel_NewITAccountApply_Input();
		this.pa.push(this.formpanel_NewITAccountApply_Input);
		this.formname.push("panel_NewITAccountApply_Input");
		temp.push(this.formpanel_NewITAccountApply_Input);
		temp.push(histroyForm);
	    items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})