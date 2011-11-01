HrPaPerformanceindexForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaPerformanceindexForm.superclass.constructor.call(this, {
			id : "HrPaPerformanceindexFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 410,
			width : 600,
			title : "绩效考核指标录入",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		var dept = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var departments = new TreeSelector("hrPaPerformanceindex.belongDept.depName", dept, "所属部门", "hrPaPerformanceindex.belongDept.depId");
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/kpi/saveAsDraftHrPaPerformanceindex.do",
			id : "HrPaPerformanceindexForm",
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
				url : __ctxPath + "/kpi/getHrPaPerformanceindex.do?id=" + this.piId,
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
				text : "下一步",
				handler : this.next.createCallback(this.formPanel, this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	next : function(a, b) {
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
			b.hide();
			var paModeValue = Ext.getCmp("modeName").getValue();
			var piId = Ext.getCmp("piId").getValue();
			new HrPaPerformanceindexscoreView({
				piId : piId == null ? 0 : piId,
				paMode : paModeValue,
				from : b.from
			}).show();
		}
	}
});