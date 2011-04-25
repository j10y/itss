PagePanel = Ext.extend(Ext.Panel, {
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true 
	},
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
			height : 'auto',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				html : "使用人11ITCODE:",
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
				emptyText : '请输入ITCODE进行选择...',
				allowBlank : false,
				name : 'AccountApplyMainTable$applyUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
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
			}, new Ext.form.ComboBox({
				name : "managerName",
				fieldLabel : "管理员",
				value : window.parent.parent.userId,
				allowBlank : false,
				readOnly : true,
				xtype : 'combo',
				hiddenName : 'AccountApplyMainTable$managerName',
				id : 'AccountApplyMainTable$managerNameCombo',
				width : 200,
				style : '',
				colspan : 0,
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
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
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
					'render' : function(combo, record, index) {
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
			}, new Ext.form.TextField({
				name : "deviceId",
				fieldLabel : "设备型号",
				id : "mDeviceId",
				allowBlank : false,
				width : 200
			}),{
				html : "隶属平台:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			},new Ext.form.ComboBox({
					xtype : 'combo',
					hiddenName : 'itil_ac_MobileTelephoneApply$platForm',
					id : 'itil_ac_MobileTelephoneApply$platFormCombo',
					width : 200,
					style : '',
					fieldLabel : '工作地点',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					//editable:false,
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
								+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.WorkSpace',
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
					var mrightsDesc=Ext.encode(Ext.getCmp('itil_ac_MobileTelephoneApply$rightsDesc').getValue());
					var mcfuid=Ext.getCmp('AccountApplyMainTable$managerNameCombo').getValue();
					var hwId=Ext.getCmp('ahardwareId').getValue();
					if(mowner==''||mowner==null){
						Ext.MessageBox.alert("提示","使用人必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(mplatform==''||mplatform==null){
						Ext.MessageBox.alert("提示","隶属平台必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(mdeviceid==''||mdeviceid==null){
						Ext.MessageBox.alert("提示","设备编号不能为空,谢谢您的合作!");
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
				emptyText : '请输入ITCODE进行选择...',
				allowBlank : false,
				value : maccountNowUserId,
				name : 'AccountApplyMainTable$applyUser',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
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
			}, new Ext.form.ComboBox({
				//name : "managerName",
				fieldLabel : "管理员",
				value : window.parent.parent.userId,
				allowBlank : false,
				readOnly : true,
				xtype : 'combo',
				hiddenName : 'mAccountApplyMainTable$managerName',
				id : 'mAccountApplyMainTable$managerNameCombo',
				width : 200,
				style : '',
				colspan : 0,
				rowspan : 0,
				lazyRender : true,
				displayField : 'userName',
				valueField : 'id',
				emptyText : '请输入ITCODE进行选择...',
				name : 'mAccountApplyMainTable$managerName',
				triggerAction : 'all',
				minChars : 50,
				queryDelay : 700,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
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
			}, new Ext.form.TextField({
				name : "deviceId",
				fieldLabel : "设备型号",
				id : "mDeviceId",
				allowBlank : false,
				value : mdeviceId,
				width : 200
			}),{
				html : "隶属平台:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			},new Ext.form.ComboBox({
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
					//editable:false,
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
								+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.WorkSpace',
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
				}) , new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'mcfuid',
					colspan : 0,
					rowspan : 0,
					name : 'mcfuid',
					width : 200,
					style : '',
					value : maccountManagerId,
					fieldLabel : 'isTemp'
				}) 
			],
			buttons : [{
				text : '保存修改',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('modifyForm').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var mowner=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var mplatform=Ext.getCmp('itil_ac_MobileTelephoneApply$platFormCombo').getValue();
					var mdeviceid=Ext.getCmp('mDeviceId').getValue().trim();
					var mrightsDesc=Ext.encode(Ext.getCmp('itil_ac_MobileTelephoneApply$rightsDesc').getValue());
					var mcfuid=Ext.getCmp('mcfuid').getValue();
					var mid=Ext.getCmp('mspid').getValue();
					var mhardwardId=Ext.getCmp('mhardwareId').getValue();
					if(mowner==''||mowner==null){
						Ext.MessageBox.alert("提示","使用人必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(mplatform==''||mplatform==null){
						Ext.MessageBox.alert("提示","隶属平台必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(mdeviceid==''||mdeviceid==null){
						Ext.MessageBox.alert("提示","设备编号不能为空,谢谢您的合作!");
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
		return modifyform;
	},
	
	items : this.items,
	initComponent : function(){
		var getModifyform=this.getModifyform;
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		this.fp = this.getSearchForm();
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.store = new Ext.data.JsonStore({
				url : webContext + "/accountAction_listAllActiveProcessInstance.action",
				fields : ['id', 'auditper', 
						'nodeName',,'processCreator','taskId','virtualDefinitionDesc','dataId','start'],
				root : 'data',
				totalProperty : 'rowCount',
				sortInfo : {
					field : "id",
					direction : "DESC"
				},
				baseParams : {
					//dataId : this.dataId
				}
			});
		var pageBar = new Ext.PagingToolbarExt({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		
		
		
		
		var searchConfig=function(){
			var param = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("grid").getStore();
	        param.start = 1;  
	        //param.status=0;
	        pageBar.formValue=param;
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
				if(maccountManagerId!=window.parent.parent.userId){
					Ext.MessageBox.alert('警告', '您不是这个帐号的管理员，不能修改这个帐号！');
					return;
				}
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
	                height:300,   
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
				header : '流程实例ID',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				hidden : false
			}, {
				header : '当前审批人',
				dataIndex : 'auditper',
				align : 'center',
				sortable : false,
				hidden : false
			},
//			{
//				header : '流程名称',
//				dataIndex : 'definitionName',
//				align : 'center',
//				sortable : true,
//				hidden : true
//			},
//			{
//				header : 'name',
//				dataIndex : 'name',
//				align : 'center',
//				sortable : false,
//				hidden : true
//			},
//			{
//				header : '当前环节描述',
//				dataIndex : 'nodeDesc',
//				align : 'center',
//				sortable : true,
//				hidden : true
//			},
				{
				header : '当前审批环节',
				dataIndex : 'nodeName',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '创建者',
				dataIndex : 'processCreator',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '任务实例ID',
				dataIndex : 'taskId',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '流程名称',
				dataIndex : 'virtualDefinitionDesc',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '主实体ID',
				dataIndex : 'dataId',
				align : 'center',
				sortable : false,
				hidden : false
			},{
				header : '提交时间',
				//xtype : 'textarea',
				dataIndex : 'start',
				align : 'center',
				sortable : false,
				hidden : false
			}
//			,{
//				header : '流程版本',
//				dataIndex : 'defVersion',
//				align : 'center',
//				sortable : false,
//				hidden : true
//			}
			],
			sm : sm,
			stripeRows : true,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : mybuttons,
			bbar : pageBar,
			listeners :{'rowdblclick':function(gd,num){
				var maccountManagerId=gd.store.getAt(num).get("accountManagerId");
				if(maccountManagerId!=window.parent.parent.userId){
					Ext.MessageBox.alert('警告', '您不是这个帐号的管理员，不能修改这个帐号！');
					return;
				}
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
	                height:300,   
	                items: [getModifyform(mid,maccountNowUser,maccountNowUserId,maccountManager,maccountManagerId,mdeviceId,mplatFormId,mplatForm,mremarkDesc,mhardwareId)]   
	           	});   
	           	win.show();
			}} 
		});
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		var params={start:1};
		pageBar.formValue=params;
        this.store.load({
            params :params
        });
		PagePanel.superclass.initComponent.call(this);
	}
});