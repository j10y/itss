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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 800,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab,
			tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>����ʱҪ�ϸ���Ȩ�����������˲�ͨ��������ҳ���·��ġ���������ť,�ڵ����Ĵ�������д�������,ѡ��ܾ���ѡ��!</font>')]

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
				name : 'sUserInfos$employeeCode',
				mapping : 'sUserInfos$employeeCode'
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
				name : 'telephone',
				mapping : 'telephone'
			}]),
			title : "��Ա��IT�ʺ�����",
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
			items : [
			{
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
				hideTrigger:true,
				readOnly : true,
				style : '',
				value : '',
				allowBlank : true,
				validator : '',
				format : 'Y-m-d',
				fieldLabel : '��������'
			}), {
					html : '��������:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'AccountApplyMainTable$delegateApplyUser',
					id : 'AccountApplyMainTable$delegateApplyUserCombo',
					width : 200,
					style : '',
					fieldLabel : '��������',
					colspan : 0,
					rowspan : 0,
					readOnly : true,
					
					hideTrigger:true,
				    readOnly : true,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '��ѡ��...',
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
					html : '<font color=red>*</font>��������ϵ�绰:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.TextField({
					fieldLabel : '<font color=red>*</font>��������ϵ�绰',
					xtype : 'textfield',
					colspan : 0,
					rowspan : 0,
				
				    readOnly : true,
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
				html : '������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
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
				emptyText : '��ѡ��...',
				hideTrigger:true,
				readOnly : true,
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
				html : '��������ϵ�绰:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '��������ϵ�绰',
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
				hideTrigger:true,
				readOnly : true,
				vtype : ''
			}), 
			{
				html : 'Ա�����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : 'Ա�����',
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
//			 {
//				html : '��������:',
//				cls : 'common-text',
//				style : 'width:135;text-align:right'
//			}, new Ext.form.ComboBox({
//				xtype : 'combo',
//				hiddenName : 'itil_ac_PersonFormalAccount$department',
//				id : 'itil_ac_PersonFormalAccount$departmentCombo',
//				width : 200,
//				style : '',
//				fieldLabel : '��������',
//				colspan : 0,
//				rowspan : 0,
//				lazyRender : true,
//				hideTrigger:true,
//				readOnly : true,
//				displayField : 'departName',
//				valueField : 'id',
//				emptyText : '��ѡ��...',
//				allowBlank : true,
//				typeAhead : true,
//				name : 'itil_ac_PersonFormalAccount$department',
//				triggerAction : 'all',
//				minChars : 50,
//				queryDelay : 700,
//				store : new Ext.data.JsonStore({
//					url : webContext
//							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.Department',
//					fields : ['id', 'departName'],
//					listeners : {
//						beforeload : function(store, opt) {
//							if (opt.params['itil_ac_PersonFormalAccount$department'] == undefined) {
//								opt.params['departName'] = Ext
//										.getCmp('itil_ac_PersonFormalAccount$departmentCombo').defaultParam;
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
//								departName : param,
//								start : 0
//							}
//						});
//						return true;
//					}
//				},
//				initComponent : function() {
//					this.store.load({
//						params : {
//							id : Ext
//									.getCmp('itil_ac_PersonFormalAccount$departmentCombo')
//									.getValue(),
//							start : 0
//						},
//						callback : function(r, options, success) {
//							Ext
//									.getCmp('itil_ac_PersonFormalAccount$departmentCombo')
//									.setValue(Ext
//											.getCmp('itil_ac_PersonFormalAccount$departmentCombo')
//											.getValue());
//						}
//					});
//				}
//			}),
			{
				html : '�ɱ����ĺ�:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '�ɱ����ĺ�',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itil_ac_PersonFormalAccount$costCenterCode',
				name : 'itil_ac_PersonFormalAccount$costCenterCode',
				style : '',
				width : 200,
				readOnly : true,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}),  {
				html : '�ʼ��ȼ�������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$sameMailDept',
				id : 'itil_ac_PersonFormalAccount$sameMailDeptCombo',
				width : 200,
				style : '',
				fieldLabel : '�ʼ��ȼ�������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'name',
				hideTrigger:true,
			    readOnly : true,
				valueField : 'id',
				emptyText : '��ѡ��...',
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
				html : '�����ص�:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
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
			    readOnly : true,
				displayField : 'name',
				valueField : 'id',
				emptyText : '��ѡ��...',
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
				html : '�ʼ�������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '�ʼ�������',
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
				html : '������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$confirmUser',
				id : 'AccountApplyMainTable$confirmUserCombo',
				width : 200,
				style : '',
				fieldLabel : '������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				hideTrigger:true,
			    readOnly : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '��ѡ��...',
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
			})]},
			{
			xtype : 'fieldset',
		    title : '�����ʺ���Ϣ',
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
							title : '<font color=red>�����շ�˵��</font>',
							autoHeight : true,
							
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
							cls : 'common-text',
							defaultType : 'textfield',
				html : ' �²��깫˾�涨���ʼ��ʺŵ���������ֻ�����ֹ�񣨰��������ʺ����ͣ���������ʽ�ʺš����������ʼ��ʺź���ʱ�ʼ��ʺţ������������������Ĵ�С�����շѡ������ʺ�ȷ��ʱ��ѡ����Ӧ������������<font color=blue>����һ��ȷ�����ڱ����꽫���ܱ��</font>����ϸ��Ϣ���£� <font align=center><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp50M���䣨������ʽ�ʺ������Ĭ�ϴ�С��8Ԫ/��<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp100M���� 16Ԫ/��<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp150M���� 32Ԫ/��<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp200M���� 64Ԫ/��<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp�˹涨�Ľ���Ȩ����Ϣ��������</font> '
		  },
			
			{
							xtype : 'fieldset',
							collapsible : false,
							title : '���ʺ�(��ѡ)',
							autoHeight : true,
							
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
							cls : 'common-text',
							defaultType : 'textfield',
							html : '<font color=red>��ѡ����ʺŹ���Ա�������ɣ�</font>'
		  },
		  {
			xtype : 'fieldset',
		    title : '�ʼ��ʺ���Ϣ(��ѡ)',
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
				html : '<font color=red>*</font>��������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$mailValue',
				id : 'itil_ac_PersonFormalAccount$mailValueCombo',
				width : 200,
				style : '',
				fieldLabel : '��������',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'volume',
				valueField : 'id',
				emptyText : '��ѡ��...',
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
				html : '��ע˵��:',
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
				fieldLabel : '��ע˵��'
			})
			]}, 
			{
			xtype : 'fieldset',
		    title : 'WWW�ʺ�',
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
				html : '<font color=red>*</font>www�ʺŶ��:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$wwwAccountValue',
				id : 'itil_ac_PersonFormalAccount$wwwAccountValueCombo',
				width : 200,
				style : '',
				fieldLabel : 'www�ʺŶ��',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'type',
				valueField : 'id',
				emptyText : '��ѡ��...',
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
				html : '��ע˵��:',
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
				fieldLabel : '����ԭ��'
			})]}, 
			{
			xtype : 'fieldset',
		    title : '�绰����',
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
				html : '<font color=red>*</font>�绰����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, 
		
			new Ext.form.ComboBox({
				xtype : 'combo',
				hiddenName : 'itil_ac_PersonFormalAccount$telephoneType',
				id : 'itil_ac_PersonFormalAccount$telephoneTypeCombo',
				width : 200,
				style : '',
				fieldLabel : '�绰����',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'type',
				valueField : 'id',
				emptyText : '��ѡ��...',
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
				html : '<font color=red>*</font>��λ����:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '��λ����',
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
				html : '<font color=red>*</font>������:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.TextField({
				fieldLabel : '������',
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
				id : 'itil_ac_PersonFormalAccount$cardState',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$cardState',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '��״̬'
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
				fieldLabel : '�绰'
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
			})
			]}],
	      buttons : [{
				text : '����',
				iconCls : 'submit',
				handler : function() {
					window.parent.auditContentWin.audit();
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
				
			
			this.formpanel_NewITAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getNewITAccountDraftData.action?panelName=panel_NewITAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {
					  Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo")
							           .initComponent();
					
					             Ext.getCmp("AccountApplyMainTable$confirmUserCombo")
							.initComponent();
							 Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							           .initComponent();
				 if(Ext.getCmp('www').getValue()=="0"){
				   	
					Ext.getCmp("wwwAccount").hide()
				}
					if(Ext.getCmp('telephone').getValue()=="0"){
					Ext.getCmp("telephoneApply").hide()
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
	 * clientvalidation ��ʼ��
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