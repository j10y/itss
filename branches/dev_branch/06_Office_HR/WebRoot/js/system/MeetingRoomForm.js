var NewWorkPlanForm = function(d, e) {
	var a = this.setup(d, e);
	var c = this.initToolbar();
	var b = new Ext.Panel( {
		layout : "fit",
		iconCls : "menu-newplan",
		id : "NewWorkPlanForm",
		title : e == null ? "新建工作计划" : e,
		tbar : c,
		autoScroll : true,
		items : [ a ]
	});
	return b;
};
NewWorkPlanForm.prototype.setup = function(b, c) {
	this.planId = b;
	var a = new Ext.FormPanel(
			{
				url : __ctxPath + "/task/saveWorkPlan.do",
				autoHeight : true,
				frame : false,
				border : false,
				layout : "column",
				id : "NewWorkPlanFormPanel",
				bodyStyle : "padding-left:8%;padding-bottom:20px;",
				formId : "NewWorkPlanFormId",
				items : [
						{
							name : "workPlan.planId",
							id : "planId",
							xtype : "hidden",
							value : this.planId == null ? "" : this.planId
						},
						{
							name : "issueScopeIds",
							id : "issueScopeIds",
							xtype : "hidden"
						},
						{
							name : "participantsIds",
							id : "participantsIds",
							xtype : "hidden"
						},
						{
							name : "principalIds",
							id : "principalIds",
							xtype : "hidden"
						},
						{
							xtype : "panel",
							layout : "form",
							border : false,
							width : 680,
							defaultType : "textfield",
							bodyStyle : "padding-top:10px",
							defaults : {
								border : false
							},
							items : [
									{
										xtype : "radiogroup",
										id : "PersonalOr",
										fieldLabel : "是否为个人计划",
										autoHeight : true,
										columns : 2,
										width : 520,
										items : [
												{
													boxLabel : "个人",
													name : "workPlan.isPersonal",
													inputValue : 1,
													id : "isPersonal1",
													checked : true,
													handler : function(d) {
														if (d.getValue() == true) {
															Ext
																	.getCmp(
																			"IssueScopeContainer")
																	.hide();
															Ext
																	.getCmp(
																			"AttendContainer")
																	.hide();
															Ext
																	.getCmp(
																			"PrincipalContainer")
																	.hide();
														} else {
															Ext
																	.getCmp(
																			"IssueScopeContainer")
																	.show();
															Ext
																	.getCmp(
																			"AttendContainer")
																	.show();
															Ext
																	.getCmp(
																			"PrincipalContainer")
																	.show();
														}
													}
												},
												{
													boxLabel : "部门",
													name : "workPlan.isPersonal",
													inputValue : 0,
													hidden : true,
													id : "isPersonal0"
												} ]
									},
									{
										fieldLabel : "计划类型",
										hiddenName : "workPlan.planType.typeId",
										xtype : "combo",
										width : 520,
										editable : false,
										allowBlank : false,
										triggerAction : "all",
										id : "typeId",
										displayField : "name",
										valueField : "id",
										mode : "local",
										store : new Ext.data.SimpleStore( {
											autoLoad : true,
											url : __ctxPath
													+ "/task/comboPlanType.do",
											fields : [ "id", "name" ]
										})
									},
									{
										fieldLabel : "计划名称",
										name : "workPlan.planName",
										width : 520,
										allowBlank : false,
										id : "planName"
									},
									{
										xtype : "container",
										border : false,
										layout : "hbox",
										layoutConfig : {
											padding : "0",
											align : "middle"
										},
										defaults : {
											xtype : "label",
											margins : {
												top : 0,
												right : 4,
												bottom : 4,
												left : 0
											}
										},
										width : 550,
										items : [
												{
													text : "时间范围:",
													width : 101,
													style : "padding-left:0px;padding-top:3px;"
												},
												{
													text : "从",
													style : "padding-left:0px;padding-top:3px;",
													width : 8
												},
												{
													xtype : "datetimefield",
													width : 200,
													format : "Y-m-d H:i:s",
													allowBlank : false,
													editable : false,
													name : "workPlan.startTime",
													id : "startTime"
												},
												{
													text : "至",
													style : "padding-left:0px;padding-top:3px;",
													width : 8
												}, {
													xtype : "datetimefield",
													width : 200,
													format : "Y-m-d H:i:s",
													allowBlank : false,
													editable : false,
													name : "workPlan.endTime",
													id : "endTime"
												} ]
									},
									{
										fieldLabel : "计划内容",
										name : "workPlan.planContent",
										xtype : "htmleditor",
										height : 180,
										width : 520,
										allowBlank : false,
										id : "planContent"
									},
									{
										layout : "column",
										style : "padding-left:0px;",
										width : 670,
										border : false,
										xtype : "container",
										items : [
												{
													columnWidth : 0.7,
													border : false,
													style : "padding-left:0px;",
													layout : "form",
													items : [ {
														fieldLabel : "附件",
														xtype : "panel",
														frame : false,
														id : "planFilePanel",
														height : 80,
														autoScroll : true,
														html : ""
													} ]
												},
												{
													columnWidth : 0.3,
													border : false,
													items : [
															{
																xtype : "button",
																text : "添加附件",
																handler : function() {
																	var d = App
																			.createUploadDialog( {
																				file_cat : "task",
																				callback : function(
																						h) {
																					var e = Ext
																							.getCmp("planFileIds");
																					var g = Ext
																							.getCmp("planFilePanel");
																					for ( var f = 0; f < h.length; f++) {
																						if (e
																								.getValue() != "") {
																							e
																									.setValue(e
																											.getValue()
																											+ ",");
																						}
																						e
																								.setValue(e
																										.getValue()
																										+ h[f].fileId);
																						Ext.DomHelper
																								.append(
																										g.body,
																										'<span><a href="#" onclick="FileAttachDetail.show('
																												+ h[f].fileId
																												+ ')">'
																												+ h[f].filename
																												+ '</a> <img class="img-delete" src="'
																												+ __ctxPath
																												+ '/images/system/delete.gif" onclick="removePlanFile(this,'
																												+ h[f].fileId
																												+ ')"/>&nbsp;|&nbsp;</span>');
																					}
																				}
																			});
																	d
																			.show(this);
																}
															},
															{
																xtype : "button",
																text : "清除附件",
																handler : function() {
																	var e = Ext
																			.getCmp("planFileIds");
																	var d = Ext
																			.getCmp("planFilePanel");
																	d.body
																			.update("");
																	e
																			.setValue("");
																}
															},
															{
																xtype : "hidden",
																id : "planFileIds",
																name : "planFileIds"
															} ]
												} ]
									},
									{
										xtype : "container",
										id : "IssueScopeContainer",
										hidden : true,
										style : "padding-left:0px;padding-bottom:3px;",
										layout : "column",
										items : [
												{
													xtype : "label",
													style : "padding-left:0px;",
													text : "发布范围:",
													width : 101
												},
												{
													xtype : "textfield",
													name : "workPlan.issueScope",
													id : "issueScope",
													readOnly : true,
													width : 360
												},
												{
													xtype : "button",
													text : "选择部门",
													iconCls : "btn-select",
													handler : function() {
														DepSelector
																.getView(
																		function(
																				e,
																				d) {
																			Ext
																					.getCmp(
																							"issueScope")
																					.setValue(
																							d);
																			Ext
																					.getCmp(
																							"issueScopeIds")
																					.setValue(
																							e);
																		})
																.show();
													}
												}, {
													xtype : "button",
													text : "清除记录"
												} ]
									},
									{
										xtype : "container",
										id : "AttendContainer",
										hidden : true,
										layout : "column",
										style : "padding-left:0px;padding-bottom:3px;",
										items : [
												{
													xtype : "label",
													text : "参与人:",
													style : "padding-left:0px;",
													width : 101
												},
												{
													xtype : "textfield",
													name : "workPlan.participants",
													id : "participants",
													readOnly : true,
													width : 360
												},
												{
													xtype : "button",
													text : "选择人员",
													iconCls : "btn-select",
													handler : function() {
														UserSelector
																.getView(
																		function(
																				e,
																				d) {
																			Ext
																					.getCmp(
																							"participants")
																					.setValue(
																							d);
																			Ext
																					.getCmp(
																							"participantsIds")
																					.setValue(
																							e);
																		})
																.show();
													}
												}, {
													xtype : "button",
													text : "清除记录"
												} ]
									},
									{
										xtype : "container",
										layout : "column",
										hidden : true,
										id : "PrincipalContainer",
										style : "padding-left:0px;padding-bottom:3px;",
										items : [
												{
													xtype : "label",
													text : "负责人:",
													style : "padding-left:0px;",
													width : 101
												},
												{
													xtype : "textfield",
													name : "workPlan.principal",
													id : "principal",
													readOnly : true,
													width : 360
												},
												{
													xtype : "button",
													text : "选择人员",
													iconCls : "btn-select",
													handler : function() {
														UserSelector
																.getView(
																		function(
																				e,
																				d) {
																			Ext
																					.getCmp(
																							"principal")
																					.setValue(
																							d);
																			Ext
																					.getCmp(
																							"principalIds")
																					.setValue(
																							e);
																		})
																.show();
													}
												}, {
													xtype : "button",
													text : "清除记录"
												} ]
									},
									{
										xtype : "radiogroup",
										fieldLabel : "是否启用",
										autoHeight : true,
										columns : 2,
										width : 520,
										items : [ {
											boxLabel : "是",
											name : "workPlan.status",
											inputValue : 1,
											id : "status1",
											checked : true
										}, {
											boxLabel : "否",
											name : "workPlan.status",
											inputValue : 0,
											id : "status0"
										} ]
									},
									{
										fieldLabel : "标识",
										hiddenName : "workPlan.icon",
										id : "icon",
										xtype : "iconcomb",
										mode : "local",
										allowBlank : false,
										width : 520,
										editable : false,
										store : new Ext.data.SimpleStore(
												{
													fields : [ "flagStyle",
															"flagName" ],
													data : [
															[ "ux-flag-blue",
																	"日常计划" ],
															[ "ux-flag-orange",
																	"重要计划" ],
															[ "ux-flag-green",
																	"特殊计划" ],
															[ "ux-flag-pink",
																	"个人计划" ],
															[ "ux-flag-red",
																	"紧急计划" ],
															[ "ux-flag-purple",
																	"部门计划" ],
															[ "ux-flag-yellow",
																	"待定计划" ] ]
												}),
										valueField : "flagStyle",
										displayField : "flagName",
										triggerAction : "all",
										value : "ux-flag-blue"
									}, {
										fieldLabel : "备注",
										name : "workPlan.note",
										xtype : "textarea",
										id : "note",
										width : 520,
										height : 50
									} ]
						} ]
			});
	if (isGranted("_NewDepPlan")) {
		Ext.getCmp("PersonalOr").items[1].hidden = false;
	}
	if (this.planId != null && this.planId != "undefined") {
		a
				.getForm()
				.load(
						{
							deferredRender : false,
							url : __ctxPath + "/task/getWorkPlan.do?planId="
									+ this.planId,
							waitMsg : "正在载入数据...",
							success : function(e, g) {
								var p = g.result.data;
								var h = p.status;
								var m = p.isPersonal;
								Ext.getCmp("status" + h).setValue(true);
								Ext.getCmp("isPersonal" + m).setValue(true);
								if (m == 1) {
									Ext.getCmp("IssueScopeContainer").hide();
									Ext.getCmp("AttendContainer").hide();
									Ext.getCmp("PrincipalContainer").hide();
								} else {
									Ext.getCmp("IssueScopeContainer").show();
									Ext.getCmp("AttendContainer").show();
									Ext.getCmp("PrincipalContainer").show();
								}
								var d = p.planType.typeId;
								Ext.getCmp("typeId").setValue(d);
								var n = p.planFiles;
								var f = getDateFromFormat(p.startTime,
										"yyyy-MM-dd HH:mm:ss");
								var k = getDateFromFormat(p.endTime,
										"yyyy-MM-dd HH:mm:ss");
								Ext.getCmp("startTime").setValue(new Date(f));
								Ext.getCmp("endTime").setValue(new Date(k));
								var l = Ext.getCmp("planFilePanel");
								var o = Ext.getCmp("planFileIds");
								for ( var j = 0; j < n.length; j++) {
									if (o.getValue() != "") {
										o.setValue(o.getValue() + ",");
									}
									o.setValue(o.getValue() + n[j].fileId);
									Ext.DomHelper
											.append(
													l.body,
													'<span><a href="#" onclick="FileAttachDetail.show('
															+ n[j].fileId
															+ ')">'
															+ n[j].fileName
															+ '</a><img class="img-delete" src="'
															+ __ctxPath
															+ '/images/system/delete.gif" onclick="removePlanFile(this,'
															+ n[j].fileId
															+ ')"/>&nbsp;|&nbsp;</span>');
								}
							},
							failure : function(d, e) {
								Ext.ux.Toast.msg("编辑", "载入失败");
							}
						});
	}
	return a;
};
NewWorkPlanForm.prototype.initToolbar = function() {
	var a = new Ext.Toolbar(
			{
				height : 30,
				items : [
						{
							text : "保存",
							iconCls : "btn-save",
							handler : function() {
								var c = Ext.getCmp("NewWorkPlanFormPanel");
								var f = Ext.getCmp("isPersonal0");
								if (f != null && f != "undefined" && f != "") {
									if (f.getValue() == true) {
										var e = Ext.getCmp("issueScope")
												.getValue();
										var b = Ext.getCmp("participants")
												.getValue();
										var d = Ext.getCmp("principal")
												.getValue();
										if ((e != "") || b != "") {
											if (d != "") {
												if (c.getForm().isValid()) {
													c
															.getForm()
															.submit(
																	{
																		method : "post",
																		waitMsg : "正在提交数据...",
																		success : function(
																				g,
																				h) {
																			Ext.ux.Toast
																					.msg(
																							"操作信息",
																							"成功保存信息！");
																		},
																		failure : function(
																				g,
																				h) {
																			Ext.MessageBox
																					.show( {
																						title : "操作信息",
																						msg : "信息保存出错，请联系管理员！",
																						buttons : Ext.MessageBox.OK,
																						icon : "ext-mb-error"
																					});
																		}
																	});
												}
											} else {
												Ext.ux.Toast.msg("操作提示",
														"负责人为必填!");
											}
										} else {
											Ext.ux.Toast.msg("操作提示",
													"发布范围，参与人至少填写一项!");
										}
									} else {
										if (c.getForm().isValid()) {
											c
													.getForm()
													.submit(
															{
																method : "post",
																waitMsg : "正在提交数据...",
																success : function(
																		g, h) {
																	Ext.ux.Toast
																			.msg(
																					"操作信息",
																					"成功保存信息！");
																},
																failure : function(
																		g, h) {
																	Ext.MessageBox
																			.show( {
																				title : "操作信息",
																				msg : "信息保存出错，请联系管理员！",
																				buttons : Ext.MessageBox.OK,
																				icon : "ext-mb-error"
																			});
																}
															});
										}
									}
								}
							}
						}, {
							text : "重置",
							iconCls : "btn-reseted",
							handler : function() {
								var b = Ext.getCmp("NewWorkPlanFormPanel");
								b.getForm().reset();
								var d = Ext.getCmp("planFileIds");
								var c = Ext.getCmp("planFilePanel");
								c.body.update("");
								d.setValue("");
							}
						} ]
			});
	return a;
};
function removePlanFile(e, a) {
	var b = Ext.getCmp("planFileIds");
	var d = b.getValue();
	if (d.indexOf(",") < 0) {
		b.setValue("");
	} else {
		d = d.replace("," + a, "").replace(a + ",", "");
		b.setValue(d);
	}
	var c = Ext.get(e.parentNode);
	c.remove();
}