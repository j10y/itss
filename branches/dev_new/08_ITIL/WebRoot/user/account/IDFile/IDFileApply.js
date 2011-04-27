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

	getFormpanel_NotesIDFile : function() {
		var curscid = this.serviceItemId;
		var pName = this.processName;
		var processType = this.processType;
		var processInfoId = this.serviceItemProcess;
		this.formpanel_NotesIDFile = new Ext.form.FormPanel({
			id : 'panel_NotesIDFile',
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
				name : 'sUserInfos$employeeCode',
				mapping : 'sUserInfos$employeeCode'
			}, {
				name : 'sUserInfos$costCenterCode',
				mapping : 'sUserInfos$costCenterCode'
			}, {
				name : 'sUserInfos$userType',
				mapping : 'sUserInfos$userType'
			}, {
				name : 'NotesIDFile$id',
				mapping : 'NotesIDFile$id'
			}, {
				name : 'NotesIDFile$fileName',
				mapping : 'NotesIDFile$fileName'
			}, {
				name : 'NotesIDFile$webMail',
				mapping : 'NotesIDFile$webMail'
			}, {
				name : 'NotesIDFile$dcMail',
				mapping : 'NotesIDFile$dcMail'
			}, {
				name : 'NotesIDFile$attachment',
				mapping : 'NotesIDFile$attachment'
			}, {
				name : 'NotesIDFile$noPassword',
				mapping : 'NotesIDFile$noPassword'
			}, {
				name : 'NotesIDFile$createDate',
				mapping : 'NotesIDFile$createDate'
			}]),
			title : "邮件ID文件丢失重置申请",
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
					hideTrigger : true,
					readOnly : true,
					validator : '',
					format : 'Y-m-d',
					fieldLabel : '申请日期'
				}), {
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
					hideTrigger : true,
					colspan : 0,
					rowspan : 0,
					readOnly : true,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请输入ITCODE进行选择...',
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
				}), {
					html : '用户类别/员工组:',
					cls : 'common-text',
					style : 'width:135;text-align:right'
				}, new Ext.form.ComboBox({
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
					hideTrigger : true,
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
				})]
			}, {
				xtype : 'fieldset',
				title : '申请帐号信息',
				layout : 'table',
				anchor : '100%',
				animCollapse : true,
				collapsible : true,
				style : 'border:1px dotted #b0acac;margin:15px 0px 0px 0px',
				autoHeight : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 1
				},
				items : [{
					xtype : 'fieldset',
					title : '<font color=red>重新获取ID文件(必选)</font>',
					layout : 'table',
					anchor : '100%',
					id : "idFile",
					checkboxToggle : true,
					checkboxName : 'file',
					collapsed : true,
					animCollapse : false,
					collapsible : false,
					autoHeight : true,
					style : 'border:1px dotted #b0acac;margin:5px 0px 0px 25px',
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layoutConfig : {
						columns : 4
					},
					items : [{
						html : '<center><a href="http://bjas2/ITHELPME.nsf/00dc6ddbfdcfea6848256d59001efa87/b1e5c359d4f8aab7482575690023cfb6?OpenDocument" target="_blank"><font color=red size="4px" style="text-decoration:none">《重新获取ID文件后如何操作》</font></a></center>',
						cls : 'common-text',
						colspan : 4,
						rowspan : 0,
						style : 'border:1px dotted #b0acac;width:600;text-align:left;margin:0px 0px 0px 75px'
					}, {
						html : '需要获取ID文件的邮件帐号名称:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'NotesIDFile$fileName',
						id : 'NotesIDFile$fileNameCombo',
						width : 200,
						style : '',
						fieldLabel : 'ID文件名',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						displayField : 'userNames',
						valueField : 'userNames',
						emptyText : '请选择...',
						allowBlank : true,
				
						name : 'NotesIDFile$fileName',
					
						minChars : 50,
					
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.itil.account.entity.DCContacts',
							fields : ['id', 'userNames'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['NotesIDFile$fileName'] == undefined) {
										opt.params['userNames'] = Ext
												.getCmp('NotesIDFile$fileNameCombo').defaultParam;
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
							var mailvalue = Ext
									.getCmp('NotesIDFile$fileNameCombo')
									.getRawValue();
							if (mailvalue == '无') {
								Ext.MessageBox.alert("提示",
										"请您选择具体的ID文件,谢谢您的合作！", function(btn) {
											Ext
													.getCmp('NotesIDFile$fileNameCombo')
													.setValue("");
										});
							}
						},
							'blur' : function(field) {
								var fieldName = Ext
										.getCmp("NotesIDFile$fileNameCombo")
										.getValue();
								if (fieldName == "" || fieldName == null) {
									Ext.MessageBox.alert("提示", "请正确选择ID文件名!");
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
						html : '接受此ID文件的内网邮箱:',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.ComboBox({
						xtype : 'combo',
						hiddenName : 'NotesIDFile$dcMail',
						id : 'NotesIDFile$dcMailCombo',
						width : 200,
						style : '',
						fieldLabel : 'DC邮箱',
						colspan : 0,
						rowspan : 0,
						lazyRender : true,
						displayField : 'email',
						valueField : 'email',
						emptyText : '接受此ID文件的内网邮箱...',
						allowBlank : true,
						typeAhead : true,
						name : 'NotesIDFile$dcMail',
						triggerAction : 'all',
						minChars : 50,
						queryDelay : 700,
						store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.itil.account.entity.DCContacts',
							fields : ['id', 'email'],
							listeners : {
								beforeload : function(store, opt) {
									if (opt.params['NotesIDFile$dcMail'] == undefined) {
										opt.params['email'] = Ext
												.getCmp('NotesIDFile$dcMailCombo').defaultParam;
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
										email : param,
										start : 0
									}
								});
								return true;
							},
								'select' : function(combo, record, index) {
							var mailvalue = Ext
									.getCmp('NotesIDFile$dcMailCombo')
									.getRawValue();
							if (mailvalue == '无') {
								Ext.MessageBox.alert("提示",
										"请您选择具体的邮箱,谢谢您的合作！", function(btn) {
											Ext
													.getCmp('NotesIDFile$dcMailCombo')
													.setValue("");
										});
							}
						}
							
						},
						initComponent : function() {
							this.store.load({
								params : {
									id : Ext.getCmp('NotesIDFile$dcMailCombo')
											.getValue(),
									start : 0
								},
								callback : function(r, options, success) {
									Ext
											.getCmp('NotesIDFile$dcMailCombo')
											.setValue(Ext
													.getCmp('NotesIDFile$dcMailCombo')
													.getValue());
								}
							});
						}
					}), {
						html : '也可填入外部邮箱名来接收ID(可选):',
						cls : 'common-text',
						style : 'width:135;text-align:right'
					}, new Ext.form.TextField({
						fieldLabel : '外部邮箱',
						xtype : 'textfield',
						colspan : 0,
						rowspan : 0,
						id : 'NotesIDFile$webMail',
						name : 'NotesIDFile$webMail',
						style : '',
						width : 200,
						value : '',
						allowBlank : true,
						validator : '',
						vtype : 'email'
					})]
				}, {
					xtype : 'fieldset',
                    layout : 'table',
					anchor : '100%',
					animCollapse : true,
					collapsible : false,
					style : 'border:0px dotted #b0acac;margin:0px 0px 0px 0px',
					autoHeight : true,
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layoutConfig : {
						columns : 2
					},

					items : [{
						xtype : 'checkbox', // each item will be a checkbox
						labelSeparator : '',
						colspan : 2,
						cls : 'common-text',
						style : 'margin:0px 0px 0px 30px',
						inputValue : '1',
						id : 'NotesIDFile$noPassword',
						name : 'NotesIDFile$noPassword',
						boxLabel : '<font size=2px>如果您忘记邮箱密码，请选择此项，15分钟后使用初始密码即可登录邮箱。</font>'
					}]
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
					id : 'NotesIDFile$id',
					colspan : 0,
					rowspan : 0,
					name : 'NotesIDFile$id',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '自动编号'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'NotesIDFile$createDate',
					colspan : 0,
					rowspan : 0,
					name : 'NotesIDFile$createDate',
					width : 200,
					style : '',
					value : '',
					fieldLabel : '生效日期'
				})]
			}],
			buttons : [{
				text : '保存为草稿',
				id : 'save',
				iconCls : 'save',
				handler : function() {
					if (Ext.getDom('file').checked) {
						Ext.getCmp('NotesIDFile$dcMailCombo').allowBlank = false;
					}
					if (!Ext.getCmp('panel_NotesIDFile').form.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					var info = Ext.encode(getFormParam('panel_NotesIDFile'));
					if (Ext.getDom('file').checked) {
						var fileId = Ext.getCmp('NotesIDFile$fileNameCombo')
								.getValue();
						var dcMail = Ext.getCmp('NotesIDFile$dcMailCombo')
								.getValue();
						var webMail = Ext.getCmp('NotesIDFile$webMail')
								.getValue();
						if (fileId == null || fileId == "") {
							Ext.MessageBox.alert("提示", "需要获取ID文件的邮件帐号名称必须从下拉列表中选择！");
							return false;
						}
						if (dcMail == "" && webMail == "") {
							Ext.MessageBox.alert("提示",
									"请选择或填写接收ID文件的邮件,已方便管理员给你您发送ID文件");
							return false;
						}
					}
					var noPassword = Ext.getCmp('NotesIDFile$noPassword')
							.getValue();
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();

					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveIDFileDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							fileId : fileId,
							dcMail : dcMail,
							webMail : webMail,
							noPassword : noPassword,
							processInfoId : processInfoId,
							processType : processType,
							panelName : 'panel_NotesIDFile'
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_NotesIDFile').load({
								url : webContext
										+ '/accountAction_getIDFileDraftData.action?panelName=panel_NotesIDFile&dataId='
										+ curId,
								timeout : 30,
								success : function(action, form) {
									Ext
											.getCmp("AccountApplyMainTable$applyUserCombo")
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
				    if (!(Ext.getDom('file').checked||Ext.getDom('NotesIDFile$noPassword').checked)) {
						Ext.MessageBox.alert("提示",
								"你还没有选择操作,请选择操作后在提交该申请！");
						return false;
					}
					if(!Ext.getDom('file').checked){
						Ext.MessageBox.alert("提示",
								"重新获取ID文件为必选项，请选择此操作后在提交申请！");
						return false;
					}
					if (!Ext.getCmp('panel_NotesIDFile').form.isValid()) {
						Ext.MessageBox.alert("提示",
								"页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					var fileId = Ext.getCmp('NotesIDFile$fileNameCombo')
								.getValue();
					if (fileId == null || fileId == "") {
							Ext.MessageBox.alert("提示", "需要获取ID文件的邮件帐号名称必须从下拉列表中选择！");
							return false;
						}
					var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					//modify by liuying at 20100524 for 修改验证条件 start
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getIDfileApply.action',
						params : {
							idname:fileId
						} ,
					//modify by liuying at 20100524 for 修改验证条件 end
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							 if(responseArray.success){	
							 	Ext.MessageBox.alert("提示","此ID已存在审批中的邮件ID文件丢失申请,不能够再提该申请！",function(btn){
		                      	window.history.back(-1);
		                      	});
							 }else{

					var info = Ext.encode(getFormParam('panel_NotesIDFile'));
					if (Ext.getDom('file').checked) {
						var fileId = Ext.getCmp('NotesIDFile$fileNameCombo')
								.getValue();
						var dcMail = Ext.getCmp('NotesIDFile$dcMailCombo')
								.getValue();
						var webMail = Ext.getCmp('NotesIDFile$webMail')
								.getValue();
//						if (fileId == null || fileId == "") {
//							Ext.MessageBox.alert("提示", "ID文件名不能为空");
//							return false;
//						}
						if (dcMail == "" && webMail == "") {
							Ext.MessageBox.alert("提示",
									"请选择或填写接收ID文件的邮件,已方便管理员给你您发送ID文件");
							return false;
						}
					}
					var noPassword = Ext.getCmp('NotesIDFile$noPassword')
							.getValue();
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveIDFileDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
							fileId : fileId,
							dcMail : dcMail,
							webMail : webMail,
							processInfoId : processInfoId,
							processType : processType,
							noPassword : noPassword,
							panelName : 'panel_NotesIDFile'
						},
						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
							// Ext.MessageBox.alert("保存成功");
							// //////////////////////////////////////////////////////////////////
							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_applyIDFile.action',
								params : {
									dataId : curId,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject',"
											+ "applyTypeName: '邮件ID文件丢失重置申请',"
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
									Ext.MessageBox.alert("提示", "提交申请失败,请检查ID文件名是否选择正确!");
									Ext.getCmp("save").enable();
									Ext.getCmp("submit").enable();
									Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////

						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示","保存失败,请检查ID文件名是否选择正确!");
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
				id : 'back',
				iconCls : 'back',
				handler : function() {
					window.history.back(-1);
				}
			}],
			buttonAlign:'center'

		});
		if (this.dataId == "0" || this.dataId == "null") {
			Ext.Ajax.request({
				url : webContext + '/accountAction_getUserPersonAccount.action',
				params : {
					accountType : '邮件帐号'
				},
				success : function(response, options) {
					var responseArray = Ext.util.JSON
							.decode(response.responseText);
					if (responseArray.success) {
					} else {
						Ext.MessageBox.alert("提示",
								"您还不存在邮件帐号,请确认后再提该申请,谢谢您的合作！", function(btn) {
									window.history.back(-1);
								});
					}
				},
				failure : function(response, options) {

				}
			}, this);
			Ext.getCmp('panel_NotesIDFile').form.load({
				url : webContext
						+ '/accountAction_initPersonAccountApplyData.action',
				params : {
					serviceItemId : this.serviceItemId,
					processType : this.processType,
					panelName : 'panel_NotesIDFile'
				},
				timeout : 30,
				success : function(action, form) {
//					var costCenter = Ext.getCmp("sUserInfos$costCenterCode")
//							.getValue();
//					if (costCenter == "" || costCenter == null
//							|| costCenter == "00000000") {
//						Ext.MessageBox.alert("提示", "申请人成本中心加载失败，请联系管理员处理！",
//								function(btn) {
//									window.history.back(-1);
//								});
//
//					}
					var employeeCode = Ext.getCmp("sUserInfos$employeeCode")
							.getValue();
					if (employeeCode == "" || employeeCode == null
							|| employeeCode == "00000000") {
						Ext.MessageBox.alert("提示", "申请人员工编号加载失败，请联系管理员处理！",
								function(btn) {
									window.history.back(-1);
								});

					}
					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("idFile").expand(true);
					Ext.getDom('file').checked = true;

				},
				failure : function(response, options) {

				}
			});
		} else {
			this.formpanel_NotesIDFile.load({
				url : webContext
						+ '/accountAction_getIDFileDraftData.action?panelName=panel_NotesIDFile&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(action, form) {

					Ext.getCmp("AccountApplyMainTable$applyUserCombo")
							.initComponent();
					Ext.getCmp("NotesIDFile$fileNameCombo").initComponent();
					var idFile = Ext.getCmp("NotesIDFile$fileNameCombo")
							.getValue();
					if (idFile != null && idFile != '') {
						Ext.getCmp("idFile").expand(true);
						Ext.getDom('file').checked = true;
					}
					Ext.getCmp("NotesIDFile$dcMailCombo").initComponent();
				}
			});

		}
		return this.formpanel_NotesIDFile;
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
		this.model = "ar_IDFileApply";

		this.getFormpanel_NotesIDFile();
		this.pa.push(this.formpanel_NotesIDFile);
		this.formname.push("panel_NotesIDFile");
		temp.push(this.formpanel_NotesIDFile);

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