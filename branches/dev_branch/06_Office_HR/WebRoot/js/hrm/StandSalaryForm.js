var StandSalaryForm = function (a) {
	this.standardId = a;
	return new Ext.Panel({
			id : "StandSalaryForm",
			iconCls : "menu-development",
			tbar : this.initToolbar(),
			border : false,
			title : "薪酬标准详细信息",
			items : [this.setup(a)]
		});
};
StandSalaryForm.prototype.setup = function (c) {
	var b = new StandSalaryItemView(c);
	var a = new Ext.FormPanel({
				id : "StandSalaryFormPanel",
				url : __ctxPath + "/hrm/saveStandSalary.do",
				layout : "form",
				bodyStyle : "padding:5px 10px 10px 10px;",
				formId : "StandSalaryFormId",
				defaultType : "textfield",
				border : false,
				reader : new Ext.data.JsonReader({
						root : "data"
					}, [{
							name : "standSalaryForm.standardId",
							mapping : "standardId"
						}, {
							name : "standSalaryForm.setdownTime",
							mapping : "setdownTime"
						}, {
							name : "standSalaryForm.framer",
							mapping : "framer"
						}, {
							name : "standSalaryForm.checkName",
							mapping : "checkName"
						}, {
							name : "standSalaryForm.checkTime",
							mapping : "checkTime"
						}, {
							name : "standSalaryForm.checkOpinion",
							mapping : "checkOpinion"
						}, {
							name : "standSalaryForm.standardNo",
							mapping : "standardNo"
						}, {
							name : "standSalaryForm.standardName",
							mapping : "standardName"
						}, {
							name : "standSalaryForm.totalMoney",
							mapping : "totalMoney"
						}, {
							name : "standSalaryForm.perCoefficient",
							mapping : "perCoefficient"
						}, {
							name : "standSalaryForm.yearEndBonusCoefficient",
							mapping : "yearEndBonusCoefficient"
						}, {
							name : "standSalaryForm.yearTotalMoney",
							mapping : "yearTotalMoney"
						}, {
							name : "standSalaryForm.memo",
							mapping : "memo"
						}, {
							name : "standSalaryForm.baseMoney",
							mapping : "baseMoney"
						}
					]),
				items : [{
						name : "standSalary.standardId",
						id : "standSalaryForm.standardId",
						xtype : "hidden",
						value : this.standardId == null ? "" : this.standardId
					}, {
						name : "standSalary.setdownTime",
						id : "standSalaryForm.setdownTime",
						xtype : "hidden"
					}, {
						name : "standSalary.framer",
						id : "standSalaryForm.framer",
						xtype : "hidden"
					}, {
						name : "standSalary.checkName",
						id : "standSalaryForm.checkName",
						xtype : "hidden"
					}, {
						name : "standSalary.checkTime",
						id : "standSalaryForm.checkTime",
						xtype : "hidden"
					}, {
						name : "standSalary.checkOpinion",
						id : "standSalaryForm.checkOpinion",
						xtype : "hidden"
					}, {
						name : "deleteItemIds",
						id : "deleteItemIds",
						xtype : "hidden"
					}, {
						xtype : "fieldset",
						title : "薪酬信息",
						anchor : "100%",
						layout : "form",
						items : [{
								xtype : "container",
								layout : "column",
								style : "padding-left:0px;",
								items : [{
										xtype : "container",
										defaultType : "textfield",
										style : "padding-left:0px;",
										columnWidth : 0.5,
										defaults : {
											anchor : "100%,100%"
										},
										layout : "form",
										items : [{
												fieldLabel : "标准编号",
												allowBlank : false,
												blankText : "标准编号不能为空!",
												name : "standSalary.standardNo",
												id : "standSalaryForm.standardNo"
											}, {
												fieldLabel : "标准名称",
												xtype : "textfield",
												name : "standSalary.standardName",
												allowBlank : false,
												blankText : "标准名称不能为空!",
												vtype : "standSalaryName",
												id : "standSalaryForm.standardName"
											}, {
												fieldLabel : "绩效基数",
												name : "standSalary.perCoefficient",
												id : "standSalaryForm.perCoefficient",
												xtype:"numberfield",
												allowBlank : false,
												emptyText : "0",
												anchor : "100%",
												listeners : {
													change : function(field, newValue, oldValue ) {
														var baseMoney = Ext.getCmp("standSalaryForm.baseMoney").getValue();
														var perCoefficient = Ext.getCmp("standSalaryForm.perCoefficient").getValue();
														Ext.getCmp("standSalaryForm.totalMoney").setValue(baseMoney + perCoefficient);
														
													}
												}
											}, {
												fieldLabel : "年度总薪酬",
												name : "standSalary.yearTotalMoney",
												id : "standSalaryForm.yearTotalMoney",
												xtype : "numberfield",
												allowBlank : false,
												anchor : "100%"
											}
										]
									}, {
										xtype : "container",
										columnWidth : 0.5,
										style : "padding-left:0px;",
										defaults : {
											anchor : "100%,100%"
										},
										layout : "form",
										items : [{
												id : "getStandardNoButton",
												xtype : "button",
												autoWidth : true,
												text : "系统生成",
												iconCls : "btn-system-setting",
												handler : function () {
													Ext.Ajax.request({
															url : __ctxPath + "/hrm/numberStandSalary.do",
															success : function (e) {
																var d = Ext.util.JSON.decode(e.responseText);
																Ext.getCmp("standSalaryForm.standardNo").setValue(d.standardNo);
															}
														});
												}
											}, {
												fieldLabel : "固定薪资",
												name : "standSalary.baseMoney",
												id : "standSalaryForm.baseMoney",
												xtype : "numberfield",
												allowBlank : false,
												anchor : "100%",
												emptyText : "0",
												listeners : {
													change : function(field, newValue, oldValue ) {
														var baseMoney = Ext.getCmp("standSalaryForm.baseMoney").getValue();
														var perCoefficient = Ext.getCmp("standSalaryForm.perCoefficient").getValue();
														Ext.getCmp("standSalaryForm.totalMoney").setValue(baseMoney + perCoefficient);
														
													}
												}
											}, {
												fieldLabel : "月度薪资总额",
												name : "standSalary.totalMoney",
												id : "standSalaryForm.totalMoney",
												xtype : "textfield",
												readOnly : true,
												emptyText : "0",
												anchor : "100%"
											}, {
												fieldLabel : "年终奖金绩效基数",
												name : "standSalary.yearEndBonusCoefficient",
												id : "standSalaryForm.yearEndBonusCoefficient",
												xtype:"numberfield",
												allowBlank : false,
												anchor : "100%"
											}
										]
									}
								]
							}, {
								fieldLabel : "备注",
								name : "standSalary.memo",
								id : "standSalaryForm.memo",
								xtype : "textarea",
								anchor : "99%"
							}
						]
					//}, b]
					}]
			});
	if (this.standardId != null && this.standardId != "undefined") {
		a.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/hrm/getStandSalary.do?standardId=" + this.standardId,
				waitMsg : "正在载入数据...",
				success : function (d, e) {
					Ext.getCmp("getStandardNoButton").disable();
					Ext.getCmp("standSalaryForm.standardNo").getEl().dom.readOnly = true;
				},
				failure : function (d, e) {}
				
			});
	}
	return a;
};
StandSalaryForm.prototype.initToolbar = function () {
	var a = new Ext.Toolbar({
				width : "100%",
				height : 30,
				items : [{
						text : "保存",
						iconCls : "btn-save",
						handler : function () {
							StandSalaryForm.saveStandSalary();
						}
					}, {
						text : "取消",
						iconCls : "btn-cancel",
						handler : function () {
							var b = Ext.getCmp("centerTabPanel");
							b.remove("StandSalaryForm");
						}
					}
				]
			});
	return a;
};
StandSalaryForm.saveStandSalary = function () {
	var c = Ext.getCmp("StandSalaryFormPanel");
	var d = [];
	/*StandSalaryItemView.onCalcTotalMoney();
	var c = Ext.getCmp("StandSalaryFormPanel");
	var b = Ext.getCmp("StandSalaryItemGrid").getStore();
	var d = [];
	for (i = 0, cnt = b.getCount(); i < cnt; i += 1) {
		var a = b.getAt(i);
		if (a.data.itemId == "" || a.data.itemId == null) {
			a.set("itemId", -1);
		}
		if (a.dirty) {
			d.push(a.data);
		}
	}*/
	//标准名称正确格式：名称说明_Band_档
	/*
	var standSalaryNames = Ext.getCmp("standSalaryForm.standardName").getValue().split("_");
	if(standSalaryNames.length != 3) {
		Ext.MessageBox.show({
			title : "操作信息",
			msg : "请填写正确格式的标准名称。",
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
		return ;
	} else {
		for(var i = 0; i < standSalaryNames.length; i++) {
			if(standSalaryNames[i].replace(/(^\s*)|(\s*$)/g, "") == "") {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "请填写正确格式的标准名称。",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
		}
	}
	*/
	if (c.getForm().isValid()) {
		c.getForm().submit({
				method : "post",
				params : {
					data : Ext.encode(d)
				},
				waitMsg : "正在提交数据...",
				success : function (e, f) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
				},
				failure : function (e, f) {
					Ext.MessageBox.show({
							title : "操作信息",
							msg : f.result.msg,
							buttons : Ext.MessageBox.OK,
							icon : "ext-mb-error"
						});
					Ext.getCmp("standSalaryForm.standardNo").setValue("");
				}
			});
	}
};
 