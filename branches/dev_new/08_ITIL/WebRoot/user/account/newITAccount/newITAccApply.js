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
			tbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>1.页面中带红色<font color=red>*</font>号和红色<font color=red>~~~~~~~~</font>的必填项，请在填写完整后再提交申请！<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2.HR员工自助服务<font color=red>（ESS帐号）不需单独申请</font>，新员工邮件帐号生效后会收到ESS用户口令的通知')]

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
					value : '',
					allowBlank : false,
					validator : '',
					vtype : ''
				}),
				{
				html : '<font color=red>*</font>申请人:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.Panel({
				layout:'table',
				layoutConfig:{
					columns:2
				},
				items:[
					new Ext.form.ComboBox({forceSelection:true,
						hideLabel:true,
						hiddenName : 'AccountApplyMainTable$applyUser',
						id : 'AccountApplyMainTable$applyUserCombo',
						width : 150,
						colspan : 0,
						rowspan : 0,
						//readOnly : true,
						//hideTrigger:true,
					    lazyRender : true,
					    listWidth:500,
						displayField : 'userName',
						valueField : 'id',
						emptyText : '请输入ITCODE进行选择...',
						allowBlank : false,
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
						},
					//add by lee for 让申请人既能查询又能读R3 in 20100126 begin
						'select':function(){
							var id = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
							Ext.Ajax.request({
								url : webContext + '/accountAction_getUserApply.action',
								params : {
									serviceItemProcess:processInfoId,
									processType:processType,
									userInfo:id
								},
								success : function(response, options) {
									var responseArray = Ext.util.JSON.decode(response.responseText);
									 if(responseArray.success){	
									 	Ext.MessageBox.alert("提示","申请人已存在审批中的新员工IT帐号申请,不能够再提该申请！",function(btn){
				                      Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
				                      return false;
				                      });
									 }else{
									Ext.Ajax.request({
										url : webContext + '/accountAction_getUserPersonAccount.action',
										params : {
											userInfo : id,
											accountType : '邮件帐号'
		                                },
		
							             success : function(response, options) {
											var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
											var r = Ext.decode(response.responseText);
											if (!r.success) {
									      Ext.Ajax.request({
										url : webContext
												+ '/accountAction_initUserInfoNewITApplyData.action',
										params : {
											userInfo : id
										},
		
										success : function(response, options) {
											var r = Ext.decode(response.responseText);
									
//											if(r.costCenter=="null"||r.costCenter==null||r.costCenter=="00000000"){
//												Ext.MessageBox.alert("系统提示","如果申请人成本中心没正常带出，请不要提交申请！有问题联系管理员处理。");
//												 Ext
//											   .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
//												return false;
//											}
											/*zanshiqudiaocipanduan
											if(r.employeeCode=="null"||r.employeeCode==null||r.employeeCode=="00000000"){
												Ext.MessageBox.alert("提示","如果申请人员工编号没正常带出，请不要提交申请！有问题联系管理员处理。");
												 Ext
											     .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
												return false;
											}*/
											Ext.getCmp('itil_ac_PersonFormalAccount$costCenterCode').setValue(r.costCenter);
											Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
											Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);	
										},
		
									    failure : function(response, options) {
										Ext.MessageBox.alert("提示","申请人尚没有权限提交该申请！有问题联系管理员处理。");
										var userInfo = Ext .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
												return false;
										}
									}, this);
										} else {
												Ext.MessageBox.alert("提示","申请人已经存在可用的邮件帐号,不能够再申请！");
												var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
												return false;
											}
										},
										failure : function(response, options) {
										}
									}, this);
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
					//add by lee for 让申请人既能查询又能读R3 in 20100126 end
					}),
					new Ext.Button({text:'添加',handler:function(){
						Ext.Msg.prompt("提示",'请输入itcode进行添加:',function(button,value){
							if(button=='ok'){
								Ext.Ajax.request({
									url: webContext+'/accountAction_findApplyUser.action',
									params: {itcode:value},
									success: function(result){
										result=Ext.decode(result.responseText);
										if(result.success==true){
											var id=result.userInfo.id;
											var info=result.userInfo.info;
												Ext.Ajax.request({
													url : webContext
															+ '/accountAction_getUserApply.action',
													params : {
														serviceItemProcess:processInfoId,
														processType:processType,
														userInfo:id
													},
							
													success : function(response, options) {
														var responseArray = Ext.util.JSON
																.decode(response.responseText);
														 if(responseArray.success){	
														 	Ext.MessageBox.alert("提示","申请人已存在审批中的新员工IT帐号申请,不能够再提该申请！",function(btn){
									                      Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
									                      return false;
									                      });
														 }else{
														Ext.Ajax.request({
															url : webContext
																	+ '/accountAction_getUserPersonAccount.action',
															params : {
																userInfo : id,
																accountType : '邮件帐号'
							                                },
							
												             success : function(response, options) {
																var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
																var r = Ext.decode(response.responseText);
																if (!r.success) {
														      Ext.Ajax.request({
															url : webContext
																	+ '/accountAction_initUserInfoNewITApplyData.action',
															params : {
																userInfo : id
															},
							
															success : function(response, options) {
																var r = Ext.decode(response.responseText);
														
//																if(r.costCenter=="null"||r.costCenter==null||r.costCenter=="00000000"){
//																	Ext.MessageBox.alert("系统提示","如果申请人成本中心没正常带出，请不要提交申请！有问题联系管理员处理。");
//																	 Ext .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
//																	return false;
//																}
																if(r.employeeCode=="null"||r.employeeCode==null||r.employeeCode=="00000000"){
																	Ext.MessageBox.alert("提示","如果申请人员工编号没正常带出，请不要提交申请！有问题联系管理员处理。");
																	 Ext
																     .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
																	return false;
																}
																
																
																Ext.getCmp('itil_ac_PersonFormalAccount$costCenterCode').setValue(r.costCenter);
																Ext.getCmp('sUserInfos$employeeCode').setValue(r.employeeCode);
																Ext.getCmp('AccountApplyMainTable$applyUserTel').setValue(r.telephone);
							//									Ext.getCmp('sUserInfos$userTypeCombo').setValue(r.userType);
																
															},
							
														    failure : function(response, options) {
															Ext.MessageBox.alert("提示","申请人尚没有权限提交该申请！有问题联系管理员处理。");
															var userInfo = Ext
																   .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
																	return false;
							
															}
														}, this);
																	
															} else {
																	Ext.MessageBox.alert("提示","申请人已经存在可用的邮件帐号,不能够再申请！");
																	var userInfo = Ext
																   .getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
																	return false;
							 
																}
															},
															failure : function(response, options) {
															}
														}, this);
							
													}
													},
													failure : function(response, options) {
							
															}
														}, this);
											var applyUserCombo=Ext.getCmp('AccountApplyMainTable$applyUserCombo');
											applyUserCombo.setValue(id);
											applyUserCombo.setRawValue(info);
										}else{
											Ext.Msg.alert("提示","itcode不存在！");
										}
									},
  								    failure: function(result){
  								    	Ext.Msg.alert("提示","itcode不存在！");
									}
								})
							}
						})
					}})
				]
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
				html : '<font color=red>*</font>邮件等价名部门:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
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
				allowBlank : false,
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
					},
					'select' : function(combo, record, index) {
					var mailvalue=Ext.getCmp('itil_ac_PersonFormalAccount$sameMailDeptCombo').getRawValue();
					if(mailvalue=='无'){
					Ext.MessageBox.alert("提示","请您选择具体的邮件等价名部门,谢谢您的合作！",function(btn){
					Ext.getCmp('itil_ac_PersonFormalAccount$sameMailDeptCombo').setValue("");
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
				html : '<font color=red>*</font>工作地点:',
				cls : 'common-text',
				style : 'width:135;text-align:right'
			}, new Ext.form.ComboBox({forceSelection:true,
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
				allowBlank : false,
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
					var mailvalue=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getRawValue();
					if(mailvalue=='无'){
					Ext.MessageBox.alert("提示","请您选择具体的工作地点,谢谢您的合作！",function(btn){
					Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').setValue("");
					return false;
					});	
					}
					var workSpace=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getValue();
					if(workSpace==2||workSpace==3){
						var data=[['1', '普通电话'], ['2', 'IP软电话'],['3', 'IP硬电话']]
						Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').store.loadData(data);
					}
					else{
						var data=[['1', '普通电话']]
						Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').store.loadData(data);
					}
				
					Ext.Ajax.request({
								url : webContext
										+ '/accountAction_getMailServer.action',
								params : {
									workSpace : workSpace
									},

								success : function(response, options) {
						        var r = Ext.decode(response.responseText);
						        var mail=r.mailServer;
						        Ext.getCmp("itil_ac_PersonFormalAccount$mailServer").setValue(mail);
						        
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
				readOnly : true,
				allowBlank : true,
				validator : '',
				vtype : ''
			})
			/*,
			
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
				typeAhead : true,
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
												"请您选择具体的审批人,谢谢您的合作！",
												function(btn) {
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
			})*/
			]},
			
			{
			xtype : 'fieldset',
		    title : '<font color=red>帐号使用说明</font>',
			layout : 'table',
		    anchor : '100%',
		    animCollapse:true,
			collapsible :true,
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
				html : '<font size=2px> 暂无说明</font>',
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
			style : 'border:1px dotted #b0acac;margin:5px 0px 0px 0px',
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
				html : ' 暂无说明'//新财年公司规定对邮件帐号的邮箱容量只有三种规格（包括所有帐号类型，即个人正式帐号、部门特殊邮件帐号和临时邮件帐号），并按照邮箱容量的大小进行收费。所以帐号确认时请选择相应的邮箱容量。<font color=blue>容量一经确定，在本财年将不能变更</font>。详细信息如下： <font align=center><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp50M邮箱（个人正式帐号邮箱的默认大小）8元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp100M邮箱 16元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp150M邮箱 32元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp200M邮箱 64元/月<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp此规定的解释权在信息化管理部。</font> '
		  },
			
			{
							xtype : 'fieldset',
							collapsible : false,
							title : '域帐号(必选)',
							autoHeight : true,
							
							style : 'border:1px dotted #b0acac;margin:5px 0px 0px 15px',
							cls : 'common-text',
							defaultType : 'textfield',
							html : '<font color=red>必选项，由帐号管理员配置生成！</font>'
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
				editable:false,
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
					},
					'select' : function(combo, record, index) {
					var mailvalue=Ext.getCmp('itil_ac_PersonFormalAccount$mailValueCombo').getRawValue();
					if(mailvalue=='无'){
					Ext.MessageBox.alert("提示","请您选择具体的邮箱容量,谢谢您的合作！",function(btn){
					Ext.getCmp('itil_ac_PersonFormalAccount$mailValueCombo').setValue("");
					});
					}
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
		    title : '<font color=red>是否申请WWW帐号</font>',
			layout : 'table',
		    anchor : '100%',
		    id:"wwwAccount",
		    checkboxToggle:'true',
		    checkboxName:'www',
		    collapsed:true,
		    animCollapse:false,
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
				editable:false,
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
					},
					'select' : function(combo, record, index) {
					var mailvalue=Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getRawValue();
					if(mailvalue=='无'){
					Ext.MessageBox.alert("提示","请您选择具体的WWW帐号额度,谢谢您的合作！",function(btn){
					Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').setValue("");
					});
					}
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
		    title : '<font color=red>是否申请座机</font>',
			layout : 'table',
		    anchor : '100%',
		    checkboxToggle:'true',
		    id:"tel",
		    checkboxName:'telephone',
		    collapsed:true,
		    animCollapse:false,
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
			},
             new Ext.form.ComboBox({forceSelection:true,
				xtype : 'combo',
				id : 'itil_ac_PersonFormalAccount$telephoneTypeCombo',
				style : 'float:left;align:left',
				mode : 'local',
				hiddenName : 'itil_ac_PersonFormalAccount$telephoneType',
				colspan : 0,
				rowspan : 0,
				editable:false,
				triggerAction : 'all',
				typeAhead : true,
				forceSelection : true,
				allowBlank : true,
				store : new Ext.data.SimpleStore({
					fields : ['id', 'type'],
					data : [['1', '普通电话'], ['2', 'IP软电话'],['3', 'IP硬电话']]
				}),
				emptyText : '请选择...',
				valueField : 'id',
				value : '',
				displayField : 'type',
				name : 'itil_ac_PersonFormalAccount$telephoneType',
				width : 200,
				fieldLabel : '是否涉及薪酬',
				listeners : {
					'expand' : function(combo) {
						combo.reset();
					},
				   'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
						var pid = Ext
							.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo')
							.getValue();
					if (pid == "") {
						Ext.Msg.alert("提示", "请先选择工作地点!");
						return false;
					}
					
				     }
				}
			}),	
		
			{
				html : '<font color=red>*</font>工位号码(以V开头):',
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
			}, new Ext.form.NumberField({
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
				id : 'itil_ac_PersonFormalAccount$password',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$password',
				width : 200,
				style : '',
				value : '',
				fieldLabel : '密码'
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
			})]}],
			buttons : [{
				text : '保存为草稿',
				id:'save',
				iconCls : 'save',
				handler : function() {
					if(Ext.getDom('www').checked){
						Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').allowBlank=false;
					}
					if(Ext.getDom('telephone').checked){
						Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').allowBlank=false;
						Ext.getCmp('itil_ac_PersonFormalAccount$stationNumber').allowBlank=false;
						Ext.getCmp('itil_ac_PersonFormalAccount$yearMoney').allowBlank=false;
					}
					if(!Ext.getCmp('panel_NewITAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线或星号的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
//					var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
//					if(applyUser==confirmUser){
//					 Ext.MessageBox.alert("提示","申请的人不能和审批人相同,请确认后再保存！");
//		                return false;
//					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var info = Ext.encode(getFormParam('panel_NewITAccountApply_Input'));
					var mialValue=Ext.getCmp('itil_ac_PersonFormalAccount$mailValueCombo').getValue();
					var remarkDesc=Ext.getCmp('itil_ac_PersonFormalAccount$remarkDesc').getValue();
					var wwwValue=Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getValue();
					var applyReason=Ext.getCmp('itil_ac_PersonFormalAccount$applyReason').getValue();
					
					var yearMoney=Ext.getCmp('itil_ac_PersonFormalAccount$yearMoney').getValue();
					var telephoneType=Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').getValue();
					var stationNumber=Ext.getCmp('itil_ac_PersonFormalAccount$stationNumber').getValue();
					var user=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var mailServer=Ext.getCmp('itil_ac_PersonFormalAccount$mailServer').getValue();
					var workSpace=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getValue();
					var sameDeptMail=Ext.getCmp('itil_ac_PersonFormalAccount$sameMailDeptCombo').getValue();
					
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveNewITAccountDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
						    processInfoId:processInfoId,
						    user:user,
						    mailServer:mailServer,
						    workSpace:workSpace,
						    sameDeptMail:sameDeptMail,
						    mialValue:mialValue,
						    remarkDesc:remarkDesc,
						    applyReason:applyReason,
						    wwwValue:wwwValue,
						    yearMoney:yearMoney,
						    telephoneType:telephoneType,
						    stationNumber:stationNumber,
						    processType:processType,
							panelName : 'panel_NewITAccountApply_Input'
					       },

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('panel_NewITAccountApply_Input').load({
								url : webContext
										+ '/accountAction_getNewITAccountDraftData.action?panelName=panel_NewITAccountApply_Input&dataId='
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
				id:'submit',
				handler : function() {
				var userInfo = Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_getUserApply.action',
						params : {
							serviceItemProcess:processInfoId,
							processType:processType,
							userInfo:userInfo
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							 if(responseArray.success){	
							  Ext.MessageBox.alert("提示","申请人已存在审批中的新员工IT帐号申请,不能够再提该申请！",function(btn){
		                      Ext.getCmp('AccountApplyMainTable$applyUserCombo').setValue("");
		                      return false;
		                      });
							 }else{
				
				if(Ext.getDom('www').checked){
					Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').allowBlank=false;
				 }else{
					Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').setValue("");
					Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').allowBlank=true;
		           }
				if(Ext.getDom('telephone').checked){
						Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').allowBlank=false;
						Ext.getCmp('itil_ac_PersonFormalAccount$stationNumber').allowBlank=false;
						Ext.getCmp('itil_ac_PersonFormalAccount$yearMoney').allowBlank=false;
					}else{
						Ext.getCmp('itil_ac_PersonFormalAccount$yearMoney').setValue("");
						Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').allowBlank=true;
						Ext.getCmp('itil_ac_PersonFormalAccount$stationNumber').allowBlank=true;
						Ext.getCmp('itil_ac_PersonFormalAccount$yearMoney').allowBlank=true;
					}
					if(!Ext.getCmp('panel_NewITAccountApply_Input').form.isValid()){
					Ext.MessageBox.alert("提示","页面中带红色波浪线或星号的为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var applyUser=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var sameMailDept=Ext.getCmp('itil_ac_PersonFormalAccount$sameMailDeptCombo').getValue();
					var workSpace=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getValue();
					if(sameMailDept==''||sameMailDept==null){
						Ext.MessageBox.alert("提示","邮件等价名部门必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					if(workSpace==''||workSpace==null){
						Ext.MessageBox.alert("提示","工作地点必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
					//var confirmUser=Ext.getCmp('AccountApplyMainTable$confirmUserCombo').getValue();
					if(applyUser==''||applyUser==null){
						Ext.MessageBox.alert("提示","申请人必须从下拉列表中选择,谢谢您的合作!");
						return false;
					}
//					if(confirmUser==''||confirmUser==null){
//						Ext.MessageBox.alert("提示","审批人必须从下拉列表中选择,谢谢您的合作!");
//						return false;
//					}
//					if(applyUser==confirmUser){
//					 Ext.MessageBox.alert("提示","申请的人不能和审批人相同,请确认后再保存！");
//		                return false;
//					}
//					var delegateApplyUser=Ext.getCmp('AccountApplyMainTable$delegateApplyUserCombo').getValue();
//					if(delegateApplyUser==confirmUser){
//					 Ext.MessageBox.alert("提示","代申请的人不能和审批人相同,请确认后再保存！");
//		                return false;
//					}
					Ext.getCmp("save").disable();
					Ext.getCmp("submit").disable();
					Ext.getCmp("back").disable();
					var info = Ext.encode(getFormParam('panel_NewITAccountApply_Input'));
					var mialValue=Ext.getCmp('itil_ac_PersonFormalAccount$mailValueCombo').getValue();
					var remarkDesc=Ext.getCmp('itil_ac_PersonFormalAccount$remarkDesc').getValue();
					
					
					var wwwValue=Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getValue();
					var applyReason=Ext.getCmp('itil_ac_PersonFormalAccount$applyReason').getValue();
					
					var yearMoney=Ext.getCmp('itil_ac_PersonFormalAccount$yearMoney').getValue();
					var telephoneType=Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').getValue();
					var stationNumber=Ext.getCmp('itil_ac_PersonFormalAccount$stationNumber').getValue();
					var user=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
					var mailServer=Ext.getCmp('itil_ac_PersonFormalAccount$mailServer').getValue();
					var workSpace=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getValue();
					var sameDeptMail=Ext.getCmp('itil_ac_PersonFormalAccount$sameMailDeptCombo').getValue();
					
					Ext.Ajax.request({
						url : webContext
								+ '/accountAction_saveNewITAccountDraft.action',
						params : {
							info : info,
							serviceItemId : curscid,
						    processInfoId:processInfoId,
						    user:user,
						    mailServer:mailServer,
						    workSpace:workSpace,
						    sameDeptMail:sameDeptMail,
						    mialValue:mialValue,
						    remarkDesc:remarkDesc,
						    applyReason:applyReason,
						    wwwValue:wwwValue,
						    yearMoney:yearMoney,
						    telephoneType:telephoneType,
						    stationNumber:stationNumber,
						    processType:processType,
							panelName : 'panel_NewITAccountApply_Input'
					},
						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							var curId = responseArray.id;
							var curName = responseArray.applyId;
						    var userInfo=Ext.getCmp('AccountApplyMainTable$applyUserCombo').getValue();
						    var workSpace=Ext.getCmp('itil_ac_PersonFormalAccount$workSpaceCombo').getRawValue();
					 		// Ext.MessageBox.alert("保存成功");
							// //////////////////////////////////////////////////////////////////
							Ext.Ajax.request({
								url : webContext
										+ '/accountWorkflow_newITAccountApply.action',
								params : {
									dataId : curId,
									userInfo:userInfo,
									workSpace:workSpace,
									bzparam : "{dataId :'" + curId
											+ "',applyId : '" + curId
											+ "',accountName:'" + curName
											+ "',applyType: 'acproject'," 
											+ "applyTypeName: '新员工IT帐号申请',"
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
									Ext.MessageBox.alert("提示", "提交申请失败,请检查审批人是否选择正确!");
									Ext.getCmp("save").enable();
					                Ext.getCmp("submit").enable();
					                Ext.getCmp("back").enable();
								}
							}, this);

							// ///////////////////////////////////////////////////////////////////
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "提交申请失败,请检查申请人是否选择正确!");
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
				id:'back',
				iconCls : 'back',
				handler : function() {
				window.history.back(-1);
				}
			}],
			buttonAlign:'center'
		});
		if (this.dataId == "0" || this.dataId == "null") {
			
	
					 Ext.getCmp('panel_NewITAccountApply_Input').form.load({
		                url : webContext + '/accountAction_initNewApplyData.action',
		                params : {
					    serviceItemId : this.serviceItemId,
					    processType :this.processType,
					    panelName : 'panel_NewITAccountApply_Input'
		                },
		              timeout : 30,
		              success : function(form,action) {
		              var responseArray = Ext.util.JSON.decode(action.response.responseText);
							 if(responseArray.success){	
		                         Ext.getCmp("AccountApplyMainTable$applyUserCombo").setValue("");
							     Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
					             //Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
							 }
		              },
		             failure : function(response, options) {
                      	
		              }
                      })
			
			
			} else {
				
			this.formpanel_NewITAccountApply_Input.load({
				url : webContext
						+ '/accountAction_getNewITAccountDraftDataToApply.action?panelName=panel_NewITAccountApply_Input&dataId='
						+ this.dataId,
				timeout : 30,
				success : function(form,action) {
					 var responseArray = Ext.util.JSON.decode(action.response.responseText);
					if(responseArray.success){	
						Ext.getCmp("AccountApplyMainTable$delegateApplyUserCombo").initComponent();
			            Ext.getCmp("itil_ac_PersonFormalAccount$sameMailDeptCombo").initComponent();
			            Ext.getCmp("itil_ac_PersonFormalAccount$workSpaceCombo").initComponent();
			            //Ext.getCmp("AccountApplyMainTable$confirmUserCombo").initComponent();
				        Ext.getCmp("AccountApplyMainTable$applyUserCombo").initComponent();
			            Ext.getCmp("itil_ac_PersonFormalAccount$telephoneTypeCombo").initComponent();
			            Ext.getCmp("itil_ac_PersonFormalAccount$sameMailDeptCombo").initComponent();
			            Ext.getCmp("itil_ac_PersonFormalAccount$workSpaceCombo").initComponent();
						var wwwValue=Ext.getCmp('itil_ac_PersonFormalAccount$wwwAccountValueCombo').getValue();
					      if(wwwValue!=null&&wwwValue!=''){
						      Ext.getDom('www').checked=true;
						      Ext.getCmp("wwwAccount").expand(true);
					      }	    
					      
					    var telephoneValue=Ext.getCmp('itil_ac_PersonFormalAccount$telephoneTypeCombo').getValue();
					      if(telephoneValue!=null&&telephoneValue!=''){
						      Ext.getDom('telephone').checked=true;
						      Ext.getCmp("tel").expand(true);
					      }	
					 }				           
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