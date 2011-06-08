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
				name : 'telephoneNumber',
				mapping : 'telephoneNumber'
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
				name : 'itil_ac_PersonFormalAccount$telephoneNumber',
				mapping : 'itil_ac_PersonFormalAccount$telephoneNumber'
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
				name : 'domainRightsDesc',
				mapping : 'domainRightsDesc'
			},
			{
				name : 'mailRightsDesc',
				mapping : 'mailRightsDesc'
			},
			{
				name : 'wwwRightsDesc',
				mapping : 'wwwRightsDesc'
			},
			{
				name : 'telephoneRightsDesc',
				mapping : 'telephoneRightsDesc'
			},
			{
				name : 'www',
				mapping : 'www'
			},
			{
				name : 'telephone',
				mapping : 'telephone'
			},
            {
				name : 'itil_ac_PersonFormalAccount$ifHold',
				mapping : 'itil_ac_PersonFormalAccount$ifHold'
			}]),
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
				}, new Ext.form.ComboBox({
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
					html : '<font color=red>*</font>代办人联系电话:',
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
			}, new Ext.form.ComboBox({
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
				allowBlank : true,
				typeAhead : true,
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
			}),    {
				html : '邮件等价名部门:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$sameMailDept',
				id : 'itil_ac_PersonFormalAccount$sameMailDeptCombo',
				width : 200,
				style : '',
				fieldLabel : '邮件等价名部门',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'itil_ac_PersonFormalAccount$sameMailDept',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.SameMailDept',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['itil_ac_PersonFormalAccount$sameMailDept'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('itil_ac_PersonFormalAccount$sameMailDeptCombo').defaultParam;
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
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$workSpace',
				id : 'itil_ac_PersonFormalAccount$workSpaceCombo',
				width : 200,
				style : '',
				fieldLabel : '工作地点',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '请选择...',
				allowBlank : true,
				typeAhead : true,
				name : 'itil_ac_PersonFormalAccount$workSpace',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
													+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.WorkSpace',
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
					var workSpace=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getValue();
					if(workSpace!="1"||workSpace!="2"){
					
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
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			})
			/*,
			
				{
				html : '审批人:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
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
				allowBlank : true,
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
			})
			*/
			]},
			{
			xtype : 'fieldset',
		    title : '<font color=red>帐号使用说明</font>',
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
				html : '<font size=2px> 说明</font>',
				cls : 'common-text',
				style : ';margin:0px 0px 0px 70px'
				
			}
			]
			},
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
							title : '<font color=red>邮箱收费说明</font>',
							autoHeight : true,
							
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
							cls : 'common-text',
							defaultType : 'textfield',
				html : ' 新财年公司规定对邮件帐号的邮箱容量只有三种规格（包括所有帐号类型，即个人正式帐号、部门特殊邮件帐号和临时邮件帐号），并按照邮箱容量的大小进行收费。所以帐号确认时请选择相应的邮箱容量。<font color=blue>容量一经确定，在本财年将不能变更</font>。详细信息如下： <font align=center><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp50M邮箱（个人正式帐号邮箱的默认大小）8元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp100M邮箱 16元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp150M邮箱 32元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp200M邮箱 64元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp此规定的解释权在信息化管理部。</font> '
		  },
			
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
			}, new Ext.form.ComboBox({
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
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.MailVolume',
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
			    readOnly : true,
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
			}, new Ext.form.ComboBox({
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
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.WWWScanType',
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
			}, new Ext.form.ComboBox({
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
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.TelephoneType',
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
			}),	new Ext.form.Hidden({
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
				id : 'www',
				colspan : 0,
				rowspan : 0,
				name : 'www',
				width : 200,
				style : '',
				value : '',
				fieldLabel : 'www'
			}),
			 new Ext.form.Hidden({
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
			{xtype:'panel',
			colspan : 3,
				rowspan : 0,
			layout:'table',width:250,layoutConfig:{columns:4},
			fieldLabel:'附件地址',defaults:{baseCls:'margin : 10 15 12 15'},
			items:[
			{id:'newITAccount',width:600,border : true,html:'',cls : 'common-text',style : 'width:100;text-align:left'}]}, 
			new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'itil_ac_PersonFormalAccount$attachment',
				name : 'itil_ac_PersonFormalAccount$attachment',
				style : '',
				value : 'null',
				fieldLabel : 'nowtime'
			})
			
			
			]
			},
			{
			xtype : 'fieldset',
		    title : '申请帐号最终信息',
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
			
			items:[{
			xtype : 'fieldset',
		    title : '域帐号信息',
		    layout : 'table',
		    anchor : '100%',
			autoHeight : true,
			animCollapse:true,
		    collapsible : false,
		   style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items:[
			{
				html : '<font color=red>*</font>您的zsgj域帐号是:',
				cls : 'common-text',
				style : 'text-align:left'
			}, 
			
	     	new Ext.form.TextField({
				fieldLabel : '域帐号',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$accountName',
				name : 'itil_ac_PersonFormalAccount$accountName',
				style : 'color:red',
				width : 200,
				value : '',
				readOnly:'true',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),
		
			{
				cls : 'common-text',
				style : 'text-align:left',
				colspan : 2,
			    html : ' 1、其初始密码为password123。<br>2、当您拿到邮件帐号和域帐号之后，就可以正确配置您的计算机名并将计算机加入zsgj域了。<br>3、第一次登录域时，系统一般会提示您更改密码，具体操作方法请见：WIN98系统： WIN2000系统： WINXP系统：</font> '
			}
				
			
			]
			
							
				            
		  },
		  {
							xtype : 'fieldset',
							collapsible : false,
							title : '<font color=red>邮件帐号处理结果</font>',
							autoHeight : true,
							
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
							cls : 'common-text',
							defaultType : 'textfield',
				html : ' 1、您的邮箱ID请见附件，初始密码为"password123"。  '
		  },
		  {
							xtype : 'fieldset',
							collapsible : false,
							title : '<font color=red>WWW帐号处理结果</font>',
							autoHeight : true,
							id:'wwwResult',
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
							cls : 'common-text',
							defaultType : 'textfield',
				html : ' 1、您的WWW帐号同域帐号，其初始密码为"123"。 '
		  },
		   {
							xtype : 'fieldset',
							collapsible : false,
							title : '<font color=red>座机电话处理结果</font>',
							autoHeight : true,
							id:'telephoneResult',
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
							cls : 'common-text',
							defaultType : 'textfield',
				html : ' 1、无'
		  }
		  ]
			}],
				buttons : [{
				text : '返回',
				iconCls : 'back',
				handler : function() {
				window.history.back(-1);
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
					
//					             Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
//							     .initComponent();
				               },
				             failure : function(response, options) {
							   
		                      	
				              }
		                      })
		                  
							
						
				
			
			
			} else {
				
			
			this.formpanel_NewITAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getNewITAccountEnd.action?panelName=panel_NewITAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				scope:this,
				success : function(action, form) {
					var da=new DataAction();
					var url=webContext
						+ '/accountAction_getNewITApplyFileList.action?clazzName=com.zsgj.itil.account.entity.PersonFormalAccount&dataId='
						+ this.dataId+"&columnName=attachment&columnid=7643&hiddenId=newITAccount";
					var value=da.ajaxGetData(url);
//					alert(value.file);
					document.getElementById("newITAccount").innerHTML=value.file;
					
					var accountName=Ext.getCmp('itil_ac_PersonFormalAccount$accountName').getValue();
					Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
					
//					             Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
//							    .initComponent();
							     Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							    .initComponent();
							    Ext.getCmp('mailServer').setValue(Ext.getCmp('itil_ac_PersonFormalAccount$mailServer').getValue());
					
//					Ext.getCmp('domainAccount').setValue(accountName);
				   	  if(Ext.getCmp('www').getValue()=="0"){
				   	Ext.getCmp("wwwAccount").hide(),
				   	Ext.getCmp('wwwRightsDesc').hide(),
				   		Ext.getCmp('wwwDesc').hide(),
				   		Ext.getCmp('wwwResult').hide()
				  }
					if(Ext.getCmp('telephone').getValue()=="0"){
					Ext.getCmp('telephoneResult').hide(),
					Ext.getCmp('TeleDesc').hide(),	
					Ext.getCmp("telephoneApply").hide(),
						Ext.getCmp('telephoneRightsDesc').hide(),
							Ext.getCmp('itil_ac_PersonFormalAccount$telephoneNumber').hide(),
							Ext.getCmp('number').hide(),
							Ext.getCmp('voip').hide(),
								Ext.getCmp('itil_ac_PersonFormalAccount$voip').hide()
								
				}
					Ext.getCmp("itil_ac_PersonFormalAccount$sameMailDeptCombo")
							           .initComponent();
							           Ext.getCmp("itil_ac_PersonFormalAccount$workSpaceCombo")
							           .initComponent();
					
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