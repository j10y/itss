JobSalaryRelationForm = Ext.extend(
				Ext.Window,
				{
					formPanel : null,
					constructor : function(a) {
						if (a == null) {
							a = {};
						}
						Ext.apply(this, a);
						this.initComponents();
						JobSalaryRelationForm.superclass.constructor.call(this,
								{
									id : "JobSalaryRelationFormWin",
									layout : "fit",
									iconCls : "menu-JobSalaryRelation",
									items : this.formPanel,
									modal : true,
									height : 240,
									width : 400,
									maximizable : true,
									title : "岗位薪标详细信息",
									buttonAlign : "center",
									buttons : this.buttons
								});
					},
					initComponents : function() {
						var a = __ctxPath
								+ "/system/listDepartment.do?opt=appUser";
						this.JobSalaryRelationDepartmentName = new TreeSelector(
								"JobSalaryRelationDepartmentName", a, "所属部门",
								"jobSalaryRelationForm.department.depId", false);
						this.JobSalaryRelationDepartmentName.allowBlank=false;
						this.formPanel = new Ext.FormPanel(
								{
									layout : "form",
									bodyStyle : "padding:10px 10px 10px 10px",
									border : false,
									url : __ctxPath
											+ "/hrm/saveJobSalaryRelation.do",
									id : "JobSalaryRelationForm",
									defaults : {
										anchor : "98%,98%"
									},
									defaultType : "textfield",
									items : [
											{
												name : "jobSalaryRelation.relationId",
												id : "jobSalaryRelationForm.relationId",
												xtype : "hidden",
												value : this.relationId == null ? ""
														: this.relationId
											},
											{
												fieldLabel : "所属部门",
												name : "jobSalaryRelation.department.depId",
												id : "jobSalaryRelationForm.department.depId",
												xtype : "hidden"
											},
											this.JobSalaryRelationDepartmentName,{
												xtype:'hidden',
												name : "jobSalaryRelation.job.jobId",
												id : "jobSalaryRelationForm.job.jobId"
											},
											{
												fieldLabel : "岗位",
												id : "jobSalaryRelationForm.job.jobName",
												xtype : "combo",
												mode : "local",
												editable : false,
												allowBlank : false,
												valueField : "jobName",
												displayField : "jobName",
												triggerAction : "all",
												allowBlank : false,
												store : new Ext.data.SimpleStore({
													url : __ctxPath
															+ "/hrm/comboJob.do",
													fields : [ "jobId", "jobName" ]
												}),
												listeners : {
													focus : function(h) {
														var g = Ext.getCmp(
																"jobSalaryRelationForm.department.depId")
																.getValue();
														if (g != null && g != ""
																&& g != "undefined") {
															Ext.getCmp(
																	"jobSalaryRelationForm.job.jobName")
																	.getStore()
																	.reload({
																		params : {
																			depId : g
																		}
																	});
														} else {
															Ext.ux.Toast.msg("操作信息",
																	"请先选择部门！");
														}
													},
													select : function(e, c, d){
														Ext.getCmp("jobSalaryRelationForm.job.jobId").setValue(c.data.jobId);
													}
												}
											},
											{
												fieldLabel : "薪酬标准单编号",
												name : "jobSalaryRelation.standSalary.standardId",
												xtype : "hidden",
												id : "jobSalaryRelationForm.standSalary.standardId"
											},{
												fieldLabel : "薪酬标准单名称",
												id : "jobSalaryRelationForm.standSalary.standardName",
												xtype : "combo",
												mode : "local",
												allowBlank : false,
												editable : false,
												valueField : "standardName",
												displayField : "standardName",
												triggerAction : "all",
												store : new Ext.data.JsonStore(
														{
															url : __ctxPath
																	+ "/hrm/comboStandSalary.do",
															fields : [
																	{
																		name : "standardId",
																		type : "int"
																	},
																	"standardNo",
																	"standardName",
																	"totalMoney",
																	"setdownTime",
																	"status" ]
														}),
												listeners : {
													focus : function() {
														var g = Ext.getCmp("jobSalaryRelationForm.job.jobId").getValue();
														if (g != null && g != ""
																&& g != "undefined") {
															Ext.getCmp("jobSalaryRelationForm.standSalary.standardName")
																	.getStore()
																	.reload();
														} else {
															Ext.ux.Toast.msg("操作信息", "请先选择岗位！");
														}
													},
													select : function(
															e,
															c,
															d) {
														Ext.getCmp("jobSalaryRelationForm.standSalary.standardId")
																.setValue(c.data.standardId);
														//Ext.getCmp("jobSalaryRelationForm.standSalary.standardMiNo")
														//		.setValue(c.data.standardNo);
														Ext.getCmp("jobSalaryRelationForm.standardMoney")
															.setValue(c.data.totalMoney);
														Ext.getCmp("jobSalaryRelationForm.totalMoney")
															.setValue(c.data.totalMoney*Ext.getCmp("jobSalaryRelationForm.empCount").getValue());
													},
													change : function(field,  newValue, oldValue){
														var jobId = Ext.getCmp("jobSalaryRelationForm.job.jobId").getValue();
														var standardId = Ext.getCmp("jobSalaryRelationForm.standSalary.standardId").getValue();
														//查询是否存在指定的jobId和standId deleteFlag=0;
														
														Ext.Ajax.request({
															url : __ctxPath + "/hrm/listJobSalaryRelation.do?Q_deleteFlag_N_EQ=0&Q_job.jobId_L_EQ=" 
															+ jobId + "&Q_standSalary.standardId_L_EQ=" + standardId,
															success : function(h, j) {
																var data = Ext.decode(h.responseText);
																var totalCounts = data.totalCounts;
																if(totalCounts>0){
																	Ext.getCmp("jobSalaryRelationForm.standSalary.standardId").setValue("");
																	field.setValue("");
																	Ext.ux.Toast.msg("操作信息", "薪标[" + newValue + "]和岗位[" + 
																			Ext.getCmp("jobSalaryRelationForm.job.jobName").getValue() + "]已经绑定关系！");
																}
															}
														});
													}
												}
											}, {
												fieldLabel : "岗位人数",
												name : "jobSalaryRelation.empCount",
												xtype : "numberfield",
												decimalPrecision : "0",
												minValue : "0",
												emptyText : "0",
												id : "jobSalaryRelationForm.empCount",
												listeners : {
													change : function(field, newValue, oldValue){
														Ext.getCmp("jobSalaryRelationForm.totalMoney")
															.setValue(Ext.getCmp("jobSalaryRelationForm.standardMoney").getValue()*newValue);
													}
												}
											},  {
												fieldLabel : "在编人数",
												name : "jobSalaryRelation.onEmpCount",
												xtype : "numberfield",
												decimalPrecision : "0",
												minValue : "0",
												emptyText : "0",
												id : "jobSalaryRelationForm.onEmpCount"
											}, {
												fieldLabel : "总金额",
												name : "jobSalaryRelation.totalMoney",
												xtype : "numberfield",
												emptyText : "0",
												id : "jobSalaryRelationForm.totalMoney"
											}, {
												xtype : "hidden",
												emptyText : "0",
												id : "jobSalaryRelationForm.standardMoney"
											} ]
								});
						if (this.relationId != null
								&& this.relationId != "undefined") {
							this.formPanel.getForm().load({
								deferredRender : false,
								url : __ctxPath
										+ "/hrm/getJobSalaryRelation.do?relationId="
										+ this.relationId,
								waitMsg : "正在载入数据...",
								success : function(b, c) {
									Ext.getCmp("JobSalaryRelationDepartmentName").setValue(
													c.result.data.department.depName);	
									Ext.getCmp("jobSalaryRelationForm.department.depId").setValue(
											c.result.data.department.depId);
									Ext.getCmp("jobSalaryRelationForm.job.jobId").setValue(
											c.result.data.job.jobId);
									Ext.getCmp("jobSalaryRelationForm.job.jobName").setValue(
											c.result.data.job.jobName);
									Ext.getCmp("jobSalaryRelationForm.standSalary.standardId").setValue(
											c.result.data.standSalary.standardId);
									Ext.getCmp("jobSalaryRelationForm.standSalary.standardName").setValue(
											c.result.data.standSalary.standardName);
									Ext.getCmp("jobSalaryRelationForm.empCount").setValue(
											c.result.data.empCount);
									Ext.getCmp("jobSalaryRelationForm.onEmpCount").setValue(
											c.result.data.onEmpCount);
									Ext.getCmp("jobSalaryRelationForm.totalMoney").setValue(
											c.result.data.totalMoney);
									Ext.getCmp("jobSalaryRelationForm.standardMoney").setValue(
											c.result.data.standSalary.totalMoney);
									
								},
								failure : function(b, c) {
								}
							});
						}
						this.buttons = [
								{
									text : "保存",
									iconCls : "btn-save",
									handler : this.save.createCallback(
											this.formPanel, this)
								},
								{
									text : "重置",
									iconCls : "btn-reset",
									handler : this.reset
											.createCallback(this.formPanel)
								}, {
									text : "取消",
									iconCls : "btn-cancel",
									handler : this.cancel.createCallback(this)
								} ];
					},
					reset : function(a) {
						a.getForm().reset();
					},
					cancel : function(a) {
						a.close();
					},
					save : function(a, b) {
						if (a.getForm().isValid()) {
							a
									.getForm()
									.submit(
											{
												method : "POST",
												waitMsg : "正在提交数据...",
												success : function(c, e) {
													Ext.ux.Toast.msg("操作信息",
															"成功保存信息！");
													var d = Ext
															.getCmp("JobSalaryRelationGrid");
													if (d != null) {
														d.getStore().reload();
													}
													b.close();
												},
												failure : function(c, d) {
													Ext.MessageBox
															.show({
																title : "操作信息",
																msg : "信息保存出错，请联系管理员！",
																buttons : Ext.MessageBox.OK,
																icon : Ext.MessageBox.ERROR
															});
													b.close();
												}
											});
						}
					}
				});