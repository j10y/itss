CopyHrPaPerformanceindexForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		CopyHrPaPerformanceindexForm.superclass.constructor.call(this, {
			id : "CopyHrPaPerformanceindexFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 475,
			width : 600,
			title : "绩效考核指标拷贝",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		var dept = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var departments = new TreeSelector("hrPaPerformanceindex.belongDept.depName", dept, "所属部门", "hrPaPerformanceindex.belongDept.depId");
		departments.allowBlank = false;
		var newDepartments = new TreeSelector("newDepName", dept, "标准所属部门", "newDepId");
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/kpi/copyHrPaPerformanceindex.do",
			id : "CopyHrPaPerformanceindexForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "hrPaPerformanceindex.id",
					id : "piId",
					xtype : "hidden",
					value : this.piId == null ? "" : this.piId
				}, {
					fieldLabel : "考核指标名称",
					name : "hrPaPerformanceindex.paName",
					id : "paName",
					allowBlank : false,
					blankText : "考核指标名称不能为空！"
				}, {
					id : "hrPaPerformanceindex.belongDept.depId",
					name : "hrPaPerformanceindex.belongDept.depId",
					xtype : "hidden"
				},
				departments,
				{
					fieldLabel : "考核指标类型",
					hiddenName : "hrPaPerformanceindex.type.id",
					maxHeight : 200,
					id : "typeName",
					xtype : "combo",
					mode : "local",
					editable : false,
					valueField : "id",
					displayField : "name",
					triggerAction : "all",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						fields : ["id","name"]
					}),
					listeners : {
						focus : function(d) {
							var c = Ext.getCmp("typeName").getStore();
							if(c.getCount() <= 0) {
								Ext.Ajax.request({
									url : __ctxPath + "/kpi/loadHrPaDatadictionary.do",
									method : "post",
									params : {
										parentId : 2          //考核类型的根节点ID
									},
									success : function(f) {
										var e = Ext.util.JSON.decode(f.responseText);
										c.loadData(e);
									}
								});
							}
						}
					}
				}, {
					fieldLabel : "考核频度",
					hiddenName : "hrPaPerformanceindex.frequency.id",
					maxHeight : 200,
					id : "frequencyName",
					xtype : "combo",
					mode : "local",
					editable : false,
					valueField : "id",
					displayField : "name",
					triggerAction : "all",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						fields : ["id","name"]
					}),
					listeners : {
						focus : function(d) {
							var c = Ext.getCmp("frequencyName").getStore();
							if(c.getCount() <= 0) {
								Ext.Ajax.request({
									url : __ctxPath + "/kpi/loadHrPaDatadictionary.do",
									method : "post",
									params : {
										parentId : 3          //考核频度的根节点ID
									},
									success : function(f) {
										var e = Ext.util.JSON.decode(f.responseText);
										c.loadData(e);
									}
								});
							}
						}
					}
				}, {
					fieldLabel : "考核方式",
					hiddenName : "hrPaPerformanceindex.mode.id",
					maxHeight : 200,
					id : "modeName",
					xtype : "combo",
					mode : "local",
					editable : false,
					valueField : "id",
					displayField : "name",
					triggerAction : "all",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						fields : ["id","name"]
					}),
					listeners : {
						focus : function(d) {
							var c = Ext.getCmp("modeName").getStore();
							if(c.getCount() <= 0) {
								Ext.Ajax.request({
									url : __ctxPath + "/kpi/loadHrPaDatadictionary.do",
									method : "post",
									params : {
										parentId : 4          //考核方式的根节点ID
									},
									success : function(f) {
										var e = Ext.util.JSON.decode(f.responseText);
										c.loadData(e);
									}
								});
							}
						}
					}
				}, {
					fieldLabel : "父考核指标",
					hiddenName : "hrPaPerformanceindex.parentPa.id",
					maxHeight : 200,
					id : "parentPa",
					xtype : "combo",
					mode : "local",
					editable : false,
					valueField : "id",
					displayField : "paName",
					triggerAction : "all",
					store : new Ext.data.JsonStore({
						root : "result",
						remoteSort : true,
						fields : ["id", "paName"]
					}),
					listeners : {
						focus : function() {
							var c = Ext.getCmp("parentPa").getStore();
							var fId = Ext.getCmp("frequencyName").getValue();
							if(fId != "") {
								Ext.Ajax.request({
									url : __ctxPath + "/kpi/comboHrPaPerformanceindex.do",
									method : "post",
									params : {
										frequencyId : fId
									},
									success : function(f) {
										var e = Ext.util.JSON.decode(f.responseText);
										c.loadData(e);
									}
								});
							}
						}
					}
				}, {
					layout : "column",
					xtype : "container",
					border : false,
					items : [
						{
							layout : "form",
							border : false,
							columnWidth : 0.26,
							items : [
								{
									fieldLabel : "是否唯一否决",
									xtype : "checkboxgroup",
									items : [
										{
											boxLabel : "是",
											name : "hrPaPerformanceindex.paIsOnlyNegative",
											id : "paIsOnlyNegative",
											inputValue : 1,
											listeners : {
												"check" : function() {
													if(this.checked) {
														Ext.getCmp("baseScoreLabel").show();
														Ext.getCmp("baseScore").show();
														Ext.getCmp("finalScoreLabel").show();
														Ext.getCmp("finalScore").show();
														Ext.getCmp("finalCoefficientLabel").show();
														Ext.getCmp("finalCoefficient").show();
													} else {
														Ext.getCmp("baseScoreLabel").hide();
														Ext.getCmp("baseScore").hide();
														Ext.getCmp("finalScoreLabel").hide();
														Ext.getCmp("finalScore").hide();
														Ext.getCmp("finalCoefficientLabel").hide();
														Ext.getCmp("finalCoefficient").hide();
													}
												}
											}
										}
									]
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.125,
							items : [
								{
									id : "baseScoreLabel",
									xtype : "label",
									text : "基准分值:",
									style : "margin-left:10px",
									hidden : true
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.11,
							items : [
								{
									name : "hrPaPerformanceindex.baseScore",
									id : "baseScore",
									xtype : "textfield",
									hideLabel : true,
									width : 50,
									style : "margin-left:5px",
									hidden : true
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.16,
							items : [
								{
									id : "finalScoreLabel",
									xtype : "label",
									text : "KPI最终得分:",
									style : "margin-left:10px",
									hidden : true
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.11,
							items : [
								{
									name : "hrPaPerformanceindex.finalScore",
									id : "finalScore",
									xtype : "textfield",
									hideLabel : true,
									width : 50,
									style : "margin-left:5px",
									hidden : true
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.125,
							items : [
								{
									id : "finalCoefficientLabel",
									xtype : "label",
									text : "绩效系数:",
									style : "margin-left:10px",
									hidden : true
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.11,
							items : [
								{
									name : "hrPaPerformanceindex.finalCoefficient",
									id : "finalCoefficient",
									xtype : "textfield",
									hideLabel : true,
									width : 50,
									style : "margin-left:5px",
									hidden : true
								}
							]
						}
					]
				}, {
					fieldLabel : "考核指标描述",
					name : "hrPaPerformanceindex.paDesc",
					id : "paDesc",
					xtype : "textarea"
				}, {
					fieldLabel : "备注信息",
					name : "hrPaPerformanceindex.remark",
					id : "remark",
					xtype : "textarea"
				}, {
					id : "oldAcKey",
					name : "oldAcKey",
					value : "",
					hidden : true
				}, {
					fieldLabel : "原考核标准",
					id : "oldAcName",
					border : false,
					xtype : "textfield",
					readOnly : true,
					value : ""
				}, {
					xtype : "container",
					layout : "column",
					border : false,
					items : [
						{
							layout : "form",
							border : false,
							columnWidth : 0.5,
							items : [
								{
									id : "newDepId",
									xtype : "hidden",
									value : "0"
								},
								newDepartments
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.5,
							items : [
								{
									fieldLabel : "新考核标准",
									id : "newAcName",
									hiddenName : "newAcKey",
									labelStyle : "text-align:right",
									xtype : "combo",
									mode : "local",
									editable : false,
									valueField : "key",
									displayField : "text",
									triggerAction : "all",
									allowBlank : false,
									store : new Ext.data.SimpleStore({
										fields : ["key","text"]
									}),
									listeners : {
										focus : function(d) {
											var c = Ext.getCmp("newAcName").getStore();
											Ext.Ajax.request({
												url : __ctxPath + "/kpi/loadHrPaAssessmentcriteria.do",
												params : {
													depId : Ext.getCmp("newDepId").getValue()
												},
												method : "post",
												success : function(f) {
													var e = Ext.util.JSON.decode(f.responseText);
													c.loadData(e);
												}
											});
										}
									}
								}
							]
						}
					]
				}, {
					fieldLabel : "创建时间",
					name : "hrPaPerformanceindex.createDate",
					id : "createDate",
					xtype : "hidden"
				}, {
					fieldLabel : "创建人",
					name : "hrPaPerformanceindex.createPerson",
					id : "createPerson",
					xtype : "hidden"
				}, {
					fieldLabel : "状态",
					name : "hrPaPerformanceindex.publishStatus",
					id : "publishStatus",
					xtype : "hidden"
				}, {
					fieldLabel : "关联的考核分数",
					name : "indexScores",
					id : "indexScores",
					xtype : "hidden"
				}, {
					fieldLabel : "fromPi",
					name : "hrPaPerformanceindex.fromPi",
					id : "fromPi",
					xtype : "hidden"
				}
			]
		});
		if(this.piId != null && this.piId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/kpi/getForCopyHrPaPerformanceindex.do?id=" + this.piId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("hrPaPerformanceindex.belongDept.depId").setValue(e.data.belongDept.depId);
					Ext.getCmp("hrPaPerformanceindex.belongDept.depName").setRawValue(e.data.belongDept.depName);
					Ext.getCmp("typeName").setValue(e.data.type.id);
					Ext.getCmp("typeName").setRawValue(e.data.type.name);
					Ext.getCmp("frequencyName").setValue(e.data.frequency.id);
					Ext.getCmp("frequencyName").setRawValue(e.data.frequency.name);
					Ext.getCmp("modeName").setValue(e.data.mode.id);
					Ext.getCmp("modeName").setRawValue(e.data.mode.name);
					if(e.data.parentPa != null) {
						Ext.getCmp("parentPa").setValue(e.data.parentPa.id);
						Ext.getCmp("parentPa").setRawValue(e.data.parentPa.paName);
					}
					if(e.data.paIsOnlyNegative == "1") {
						Ext.getCmp("paIsOnlyNegative").setValue(true);
						Ext.getCmp("baseScoreLabel").show();
						Ext.getCmp("baseScore").show();
						Ext.getCmp("finalScoreLabel").show();
						Ext.getCmp("finalScore").show();
						Ext.getCmp("finalCoefficientLabel").show();
						Ext.getCmp("finalCoefficient").show();
					}
					Ext.getCmp("oldAcKey").setValue(e.oldAcKeys);
					Ext.getCmp("oldAcName").setValue(e.oldAcNames);
					if(e.canCopy == "false") {
						Ext.getCmp("publish").hide();
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "该考核指标关联多个考核标准，不允许拷贝！",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
						return ;
					}
				},
				failure : function() {
					
				}
			});
		}
		this.buttons = [
			{
				text : "取消",
				handler : this.cancel.createCallback(this)
			}, {
				id : "publish",
				text : "确认发布",
				handler : this.publish.createCallback(this.formPanel, this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	publish : function(a, b) {
		if(Ext.getCmp("paIsOnlyNegative").checked) {
			var baseScoreValue = Ext.getCmp("baseScore").getValue();
			var finalScoreValue = Ext.getCmp("finalScore").getValue();
			var finalCoefficientValue = Ext.getCmp("finalCoefficient").getValue();
			if(isNaN(baseScoreValue) || baseScoreValue <= 0) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "请正确填写基准分值！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
			if(isNaN(finalScoreValue) || finalScoreValue <= 0) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "请正确填写KPI最终得分！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
			if(isNaN(finalCoefficientValue) || finalCoefficientValue <= 0) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "请正确填写绩效系数！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
		}
		if(a.getForm().isValid()) {
			a.getForm().submit({
				url : __ctxPath + "/kpi/copyHrPaPerformanceindex.do",
				method : "post",
				waitMsg : "正在提交数据……",
				success : function() {
					if(b.from == "draft") {
						Ext.getCmp("DraftHrPaPerformanceindexView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					} else if(b.from == "publish") {
						Ext.getCmp("PublishHrPaPerformanceindexView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					}
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息录入有误，请核实！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			});
		}
	}
});