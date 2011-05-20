PagePanel = Ext.extend(Ext.Panel, {
	closable : true,
//	viewConfig : {
//		autoFill : true,
//		forceFit : true 
//	},
	height:800,
	layout : 'border',
	getSearchForm : function() {
		var applyNum = new Ext.form.TextField({
			name : "ownerName",
			fieldLabel : "所有者",
			id : "ownerName",
			width : 150
		});
		var applyName = new Ext.form.TextField({
			name : "managerName",
			fieldLabel : "管理员",
			id : "managerName",
			width : 150
		});

		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 60,
			frame : true,
			title:'查询条件',
			//collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				html : "使用人ITCODE:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyNum, {
				html : "管理员ITCODE:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyName
		
			]
		});

		return this.panel;
	},
	getAddform :function(){
	
		var addform= new Ext.form.FormPanel({
			id:"addForm",
			layout : 'table',
			title : '增加帐号信息',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			
			items : [{
				html : "使用人:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
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
				allowBlank : true,
				name : 'AccountApplyMainTable$applyUser',
				listWidth : 450,
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
									Ext.getCmp('ownerempcode').setValue(r.employeeCode);
									
								},

								failure : function(response, options) {
									
								}
							}, this);
						
					}
					
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('AccountApplyMainTable$applyUserCombo')
									.setValue(Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue());
						}
					});
				}
			}),{
				html : "管理员:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				name : "managerName",
				fieldLabel : "管理员",
				allowBlank : false,
				readOnly : false,
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$managerName',
				id : 'AccountApplyMainTable$managerNameCombo',
				width : 200,
				style : '',
				colspan : 0,
				listWidth : 450,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请输入ITCODE进行选择...',
				name : 'AccountApplyMainTable$managerName',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['AccountApplyMainTable$managerName'] == undefined) {
								opt.params['userName'] = Ext
										.getCmp('AccountApplyMainTable$managerNameCombo').defaultParam;
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
					
								var userInfo = Ext.getCmp('AccountApplyMainTable$managerNameCombo').getValue();		
						        Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
									
                               },
								success : function(response, options) {
									 
								var r = Ext.decode(response.responseText);
								Ext.getCmp('managerempcode').setValue(r.employeeCode);
									
								},

								failure : function(response, options) {
									
								}
							}, this);
						
					}
					
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('AccountApplyMainTable$managerNameCombo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('AccountApplyMainTable$managerNameCombo')
									.setValue(Ext.getCmp('AccountApplyMainTable$managerNameCombo').getValue());
						}
					});
				}
			
			}),{
				html : "使用人员工编号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "ownerempcode",
				fieldLabel : "使用人员工编号",
				id : "ownerempcode",
				allowBlank : true,
				readOnly: true,
				width : 200
			}),{
				html : "管理员员工编号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "managerempcode",
				fieldLabel : "管理员员工编号",
				id : "managerempcode",
				allowBlank : true,
				readOnly: true,
				width : 200
			}),{
				html : "设备型号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				name : "deviceId",
				fieldLabel : "设备型号",
				id : "mDeviceId",
					xtype : 'combo',
					hiddenName : 'deviceId',
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
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/accountAction_listDeviceTypeName.action',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['deviceId'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('mDeviceId').defaultParam;
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
										.getCmp('mDeviceId')
										.getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext
										.getCmp('mDeviceId')
										.setValue(Ext
												.getCmp('mDeviceId')
												.getValue());
							}
						});
					}
				
			}),{
				html : "隶属平台:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			},new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_MobileTelephoneApply$platForm',
					id : 'itil_ac_MobileTelephoneApply$platFormCombo',
					width : 200,
					style : '',
					fieldLabel : '工作地点',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'name',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : false,
					name : 'itil_ac_MobileTelephoneApply$platForm',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/accountAction_listWin7PlatFormName.action',
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
						}
					},
					initComponent : function() {
						this.store.load({
							params : {
								id : Ext
										.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
										.getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext
										.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
										.setValue(Ext
												.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
												.getValue());
							}
						});
					}
				}),{
					html : "硬件序列号:",
					cls : 'common-text',
					width : 100,
					style : 'width:150;text-align:right'
				}, new Ext.form.TextField({
					name : "ahardwareId",
					fieldLabel : "硬件序列号",
					id : "ahardwareId",
					allowBlank : true,
					width : 200
				}),{},{},{
					html : "备注说明:",
					cls : 'common-text',
					width : 100,
					style : 'width:150;text-align:right'
				},new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'itil_ac_MobileTelephoneApply$rightsDesc',
					colspan : 3,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$rightsDesc',
					width : 495,
					style : '',
				    validator : '',
					fieldLabel : '备注说明'
				})
			],
			buttons : [{
				text : '保存',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('addForm').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var mowner=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var mplatform=Ext.getCmp('itil_ac_MobileTelephoneApply$platFormCombo').getValue();
					var mdeviceid=Ext.getCmp('mDeviceId').getValue().trim();
					var mrightsDesc=getEncodeValue('itil_ac_MobileTelephoneApply$rightsDesc');
					var mcfuid=Ext.getCmp('AccountApplyMainTable$managerNameCombo').getValue();
					var hwId=Ext.getCmp('ahardwareId').getValue();
					
					if(mcfuid==''||mcfuid==null){
						Ext.MessageBox.alert("提示","管理员必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(mplatform==''||mplatform==null){
						Ext.MessageBox.alert("提示","隶属平台必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(mdeviceid==''||mdeviceid==null){
						Ext.MessageBox.alert("提示","设备型号不能为空,谢谢您的合作!");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_addWin7Account.action',
						params : {
							owner : mowner,
							platform : mplatform,
							deviceId : mdeviceid,
							rightsDesc : mrightsDesc,
							confirmUser : mcfuid,
							hardwareId : hwId
						},
						success : function(response, options) {
							Ext.Msg.alert("提示", "保存成功", function() {
								Ext.getCmp('grid').store.load();
								Ext.getCmp('addwin').close();
							});
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "保存失败!");
							Ext.getCmp("save").enable();
			                Ext.getCmp("back").enable();
						}
					}, this);
				}
			},  {
				text : '取消',
			    id:'back',
				iconCls : 'back',
				handler : function() {
					Ext.getCmp('addwin').close();
				}
			}]
		});
		return addform;
	
	},
	getModifyform :function(mid,maccountNowUser,maccountNowUserId,maccountManager,maccountManagerId,mdeviceId,mplatFormId,mplatForm,mremarkDesc,mhardwareId){
		var modifyform= new Ext.form.FormPanel({
			id:"modifyForm",
			layout : 'table',
			title : '修改帐号信息',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			
			items : [{
				html : "使用人:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
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
				listWidth : 450,
				allowBlank : false,
				value : maccountNowUserId,
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
									Ext.getCmp('mownerempcode').setValue(r.employeeCode);
									
								},

								failure : function(response, options) {
									
								}
							}, this);
						
					},
					'render' : function(combo, record, index) {
								var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();		
						        Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
									
                               },
								success : function(response, options) {
									 
									var r = Ext.decode(response.responseText);
									Ext.getCmp('mownerempcode').setValue(r.employeeCode);
									
								},

								failure : function(response, options) {
									
								}
							}, this);
						
					}
					
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('AccountApplyMainTable$applyUserCombo')
									.setValue(Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue());
						}
					});
				}
			}),{
				html : "管理员:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				fieldLabel : "管理员",
				value : maccountManagerId,
				allowBlank : false,
				readOnly : false,
				xtype : 'combo',
				hiddenName : 'mAccountApplyMainTable$managerName',
				id : 'mAccountApplyMainTable$managerNameCombo',
				width : 200,
				style : '',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				listWidth : 450,
				valueField : 'id',
				emptyText : '请输入ITCODE进行选择...',
				name : 'mAccountApplyMainTable$managerName',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['mAccountApplyMainTable$managerName'] == undefined) {
								opt.params['userName'] = Ext
										.getCmp('mAccountApplyMainTable$managerNameCombo').defaultParam;
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
					'render' : function(combo, record, index) {
								var userInfo = Ext.getCmp('mAccountApplyMainTable$managerNameCombo').getValue();		
						        Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
									
                               },
								success : function(response, options) {
									 
									var r = Ext.decode(response.responseText);
									Ext.getCmp('managerempcode').setValue(r.employeeCode);
									
								},

								failure : function(response, options) {
									
								}
							}, this);
						
					},
					'select' : function(combo, record, index) {
								var userInfo = Ext.getCmp('mAccountApplyMainTable$managerNameCombo').getValue();		
						        Ext.Ajax.request({
								url : webContext
										+ '/accountAction_initUserInfoData.action',
								params : {
									userInfo : userInfo
									
                               },
								success : function(response, options) {
									 
									var r = Ext.decode(response.responseText);
									Ext.getCmp('managerempcode').setValue(r.employeeCode);
									
								},

								failure : function(response, options) {
									
								}
							}, this);
						
					}
					
				},
				initComponent : function() {
					this.store.load({
						params : {
							id : Ext.getCmp('mAccountApplyMainTable$managerNameCombo').getValue(),
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('mAccountApplyMainTable$managerNameCombo')
									.setValue(Ext.getCmp('mAccountApplyMainTable$managerNameCombo').getValue());
						}
					});
				}
			
			}),{
				html : "使用人员工编号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mownerempcode",
				fieldLabel : "使用人员工编号",
				id : "mownerempcode",
				allowBlank : true,
				readOnly: true,
				width : 200
			}),{
				html : "管理员员工编号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "managerempcode",
				fieldLabel : "管理员员工编号",
				id : "managerempcode",
				allowBlank : true,
				readOnly: true,
				width : 200
			}),{
				html : "设备型号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
				value : mdeviceId,
				name : "deviceId",
				fieldLabel : "设备型号",
				id : "mDeviceId",
					xtype : 'combo',
					hiddenName : 'deviceId',
					width : 200,
					style : '',
					colspan : 0,
					rowspan : 0,
					listWidth : 450,
					lazyRender : true,
					displayField : 'name',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : false,
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/accountAction_listDeviceTypeName.action',
						fields : ['id', 'name'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['deviceId'] == undefined) {
									opt.params['name'] = Ext
											.getCmp('mDeviceId').defaultParam;
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
										.getCmp('mDeviceId')
										.getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext
										.getCmp('mDeviceId')
										.setValue(Ext
												.getCmp('mDeviceId')
												.getValue());
							}
						});
					}
				
			
			}),{
				html : "隶属平台:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			},new Ext.form.ComboBox({forceSelection:true,
					xtype : 'combo',
					hiddenName : 'itil_ac_MobileTelephoneApply$platForm',
					id : 'itil_ac_MobileTelephoneApply$platFormCombo',
					width : 200,
					style : '',
					fieldLabel : '工作地点',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					value : mplatFormId,
					displayField : 'name',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : false,
					name : 'itil_ac_MobileTelephoneApply$platForm',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ 'accountAction_listWin7PlatFormName.action',
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
						}
					},
					initComponent : function() {
						this.store.load({
							params : {
								id : Ext
										.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
										.getValue(),
								start : 0
							},
							callback : function(r, options, success) {
								Ext
										.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
										.setValue(Ext
												.getCmp('itil_ac_MobileTelephoneApply$platFormCombo')
												.getValue());
							}
						});
					}
				}),{
					html : "硬件序列号:",
					cls : 'common-text',
					width : 100,
					style : 'width:150;text-align:right'
				}, new Ext.form.TextField({
					name : "mhardwareId",
					fieldLabel : "硬件序列号",
					id : "mhardwareId",
					allowBlank : true,
					value : mhardwareId,
					width : 200
				}),{},{},{
					html : "备注说明:",
					cls : 'common-text',
					width : 100,
					style : 'width:150;text-align:right'
				},new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'itil_ac_MobileTelephoneApply$rightsDesc',
					colspan : 3,
					rowspan : 0,
					name : 'itil_ac_MobileTelephoneApply$rightsDesc',
					width : 495,
					style : '',
					value : mremarkDesc,
				    validator : '',
					fieldLabel : '备注说明'
				}),{
					html : "修改历史记录:",
					cls : 'common-text',
					width : 100,
					style : 'width:150;text-align:right'
				},new Ext.form.TextArea({
					xtype : 'textarea',
					id : 'modifyDesc',
					readOnly:true,
					colspan : 3,
					rowspan : 0,
					name : 'modifyDesc',
					width : 495,
					style : 'margin: 4 0 0 0',
				    validator : '',
					fieldLabel : '修改历史记录'
				}), new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'mspid',
					colspan : 0,
					rowspan : 0,
					name : 'mspid',
					width : 200,
					style : '',
					value : mid,
					fieldLabel : 'isTemp'
				})
			],
			buttons : [{
				text : '保存修改',
				iconCls : 'save',
				id:'save',
				handler : function() {
					Ext.MessageBox.confirm("提示","是否确定修改？",function(button, text) {
															if (button == 'yes') {
																if(!Ext.getCmp('modifyForm').form.isValid()){
																Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
																return false;
																}
																var mowner=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
																var mplatform=Ext.getCmp('itil_ac_MobileTelephoneApply$platFormCombo').getValue();
																var mdeviceid=Ext.getCmp('mDeviceId').getValue();
																var mrightsDesc=getEncodeValue('itil_ac_MobileTelephoneApply$rightsDesc');
																var mcfuid=Ext.getCmp('mAccountApplyMainTable$managerNameCombo').getValue();
																var mid=Ext.getCmp('mspid').getValue();
																var mhardwardId=Ext.getCmp('mhardwareId').getValue();
																if(mowner==''||mowner==null){
																	Ext.MessageBox.alert("提示","使用人必须从下拉列表中选择,谢谢您的合作!");
																	return false;
																}
																if(mcfuid==''||mcfuid==null){
																	Ext.MessageBox.alert("提示","管理员必须从下拉列表中选择,谢谢您的合作!");
																	return false;
																}
																if(mplatform==''||mplatform==null){
																	Ext.MessageBox.alert("提示","隶属平台必须从下拉列表中选择,谢谢您的合作!");
																	return false;
																}
																if(mdeviceid==''||mdeviceid==null){
																	Ext.MessageBox.alert("提示","设备型号不能为空,谢谢您的合作!");
																	return false;
																}
																Ext.getCmp("save").disable();
																Ext.getCmp("back").disable();
																Ext.Ajax.request({
																	url : webContext
																			+ '/accountAction_modifyWin7Account.action',
																	params : {
																		owner : mowner,
																		platform : mplatform,
																		deviceId : mdeviceid,
																		rightsDesc : mrightsDesc,
																		confirmUser : mcfuid,
																		accountid : mid,
																		hardwardId : mhardwardId
																	},
																	success : function(response, options) {
																		Ext.Msg.alert("提示", "修改成功", function() {
																			Ext.getCmp('grid').store.load();
																			Ext.getCmp('modifywin').close();
																		});
																	},
																	failure : function(response, options) {
																		Ext.MessageBox.alert("提示", "保存失败!");
																		Ext.getCmp("save").enable();
														                Ext.getCmp("back").enable();
																	}
																}, this);
															} else {
																
															}
														})
					
				}
			},  {
				text : '取消',
			    id:'back',
				iconCls : 'back',
				handler : function() {
					Ext.getCmp('modifywin').close();
				}
			}]
		});
		Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getModifyDesc.action',
						params : {
							accountId : mid
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							Ext.getCmp('modifyDesc').setValue(responseArray.his);
						},
						failure : function(response, options) {
							
						}
					}, this);
		return modifyform;
	},
	
	items : this.items,
	initComponent : function(){
		var getModifyform=this.getModifyform;
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		this.fp = this.getSearchForm();
//		var viewConfig = Ext.apply({
//			forceFit : true
//		}, this.gridViewConfig);
		this.store = new Ext.data.JsonStore({
				url : webContext + "/accountAction_listAllWin7Account.action",
				fields : ['id', 'accountNowUser', 'accountNowUserId', 'accountManager',
						'accountManagerId','deviceId','deviceName','platFormId','platForm','remarkDesc','createDate','accountName','hardwareId'],
				root : 'data',
				totalProperty : 'rowCount',
				sortInfo : {
					field : "id",
					direction : "DESC"
				},
				baseParams : {
				}
			});
		var pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		
		
		
		
		var searchConfig=function(){
			var param = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("grid").getStore();
	        param.start = 0;  
	        store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
	        store.load({
	            params : param
	        });
		};
		var searchButton = new Ext.Button({
			style : 'margin:2px 0px 2px 5px',
			handler :searchConfig,
			pressed:true,
			text : '查询',
			iconCls : 'search'
		});
		var resetButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			handler :function(){
				Ext.getCmp("searchForm").getForm().reset();
			},
			text : '重置',
			iconCls : 'reset'
		});
		var AddButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler :function(){
				var addwin= new Ext.Window({ 
					id:'addwin',
	                width:680, 
	                modal : true,
	                height:300,   
	                items: [this.getAddform()]   
	           	});   
	           	addwin.show();
			},
			text : '添加',
			iconCls : 'add'
		});
		var removeButton = new Ext.Button({
			text:'修改',
			pressed:true,
			iconCls:'edit',
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要修改的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('提示', '请选择一条信息进行修改!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('提示', '每次只能修改一条记录!');
					return;
				}
			
				var maccountManagerId=record.get("accountManagerId");
				var mid=record.get("id");
				var maccountNowUser=record.get("accountNowUser");
				var maccountNowUserId=record.get("accountNowUserId");
				var maccountManager=record.get("accountManager");
				var mdeviceId=record.get("deviceId");
				var mplatFormId=record.get("platFormId");
				var mplatForm=record.get("platForm");
				var mremarkDesc=record.get("remarkDesc");
				var mhardwareId=record.get("hardwareId");
				var win= new Ext.Window({ 
					modal : true,
					id:'modifywin',
	                width:680,   
	                height:340,   
	                items: [getModifyform(mid,maccountNowUser,maccountNowUserId,maccountManager,maccountManagerId,mdeviceId,mplatFormId,mplatForm,mremarkDesc,mhardwareId)]   
	           	});   
	           	win.show();

			}
		});
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		mybuttons.push(searchButton);
		mybuttons.push(resetButton);
		mybuttons.push(AddButton);
		mybuttons.push(removeButton);
		this.grid = new Ext.grid.EditorGridPanel({
			id:"grid",
			name:"grid",
			region : "center",
			store : this.store,
			viewConfig : {
				forceFit : true
			},
			columns : [sm, {
				header : '自动编号',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				hidden : true
			}, {
				header : '使用人',
				dataIndex : 'accountNowUser',
				align : 'center',
				sortable : false,
				hidden : false
			}, {
				header : '所有者ID',
				dataIndex : 'accountNowUserId',
				align : 'center',
				sortable : true,
				hidden : true
			}, {
				header : '管理员ITcode',
				dataIndex : 'accountManager',
				align : 'center',
				sortable : false,
				hidden : false
			}, {
				header : '管理员ID',
				dataIndex : 'accountManagerId',
				align : 'center',
				sortable : true,
				hidden : true
			},{
				header : '帐号名',
				dataIndex : 'accountName',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '设备型号Id',
				dataIndex : 'deviceId',
				align : 'center',
				sortable : false,
				hidden : true
			},
			{
				header : '设备型号',
				dataIndex : 'deviceName',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '硬件序列号',
				dataIndex : 'hardwareId',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '平台ID',
				dataIndex : 'platFormId',
				align : 'center',
				sortable : false,
				hidden : true
			},
			{
				header : '平台',
				dataIndex : 'platForm',
				align : 'center',
				sortable : false,
				hidden : false
			},{
				header : '备注',
				//xtype : 'textarea',
				dataIndex : 'remarkDesc',
				align : 'center',
				sortable : false,
				hidden : false
			},{
				header : '发货日期/首次安装日期',
				dataIndex : 'createDate',
				align : 'center',
				sortable : false,
				hidden : false
			}
			],
			sm : sm,
			loadMask : true,
			tbar : mybuttons,
			bbar : pageBar,
			listeners :{'rowdblclick':function(gd,num){
				var maccountManagerId=gd.store.getAt(num).get("accountManagerId");
				var mid=gd.store.getAt(num).get("id");
				var maccountNowUser=gd.store.getAt(num).get("accountNowUser");
				var maccountNowUserId=gd.store.getAt(num).get("accountNowUserId");
				var maccountManager=gd.store.getAt(num).get("accountManager");
				var mdeviceId=gd.store.getAt(num).get("deviceId");
				var mplatFormId=gd.store.getAt(num).get("platFormId");
				var mplatForm=gd.store.getAt(num).get("platForm");
				var mremarkDesc=gd.store.getAt(num).get("remarkDesc");
				var mhardwareId=gd.store.getAt(num).get('hardwareId');
				var win= new Ext.Window({ 
					modal : true,
					id:'modifywin',
	                width:680,   
	                height:340,   
	                items: [getModifyform(mid,maccountNowUser,maccountNowUserId,maccountManager,maccountManagerId,mdeviceId,mplatFormId,mplatForm,mremarkDesc,mhardwareId)]   
	           	});   
	           	win.show();
			}} 
		});
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		var params={start:0};
		this.store.baseParams=params;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});

        this.store.load({
            params :params
        });
		PagePanel.superclass.initComponent.call(this);
	}
});