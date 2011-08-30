HrPaPerformanceindexscoreForm2 = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaPerformanceindexscoreForm2.superclass.constructor.call(this, {
			id : "HrPaPerformanceindexscoreForm2Win",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 305,
			width : 580,
			title : "增加/修改评分标准对话框--计算类",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			id : "HrPaPerformanceindexscoreForm2",
			defaultType : "textfield",
			items : [
				{
					name : "hrPaPerformanceindexscore.id",
					id : "pisId",
					xtype : "hidden",
					value : this.pisId == null ? "0" : this.pisId
				}, {
					name : "hrPaPerformanceindexscore.piId",
					id : "piIdForm2",
					xtype : "hidden",
					value : this.piId
				}, {
					name : "hrPaPerformanceindexscore.pisType",
					id : "pisType",
					xtype : "hidden",
					value : this.pisType
				}, {
					fieldLabel : "prId",
					name : "prId",
					xtype : "hidden"
				}, {
					fieldLabel : "得分",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindexscore.pisScore",
					id : "pisScore",
					anchor : "98%,98%",
					maskRe : /^\d*$/,
					allowBlank : false,
					blankText : "分数不能为空！"
				}, {
					xtype : "container",
					layout : "column",
					border : false,
					items : [
						{
							layout : "form",
							border : false,
							items : [
								{
									fieldLabel : "考核标准",
									labelStyle : "text-align:right",
									id : "acName",
									xtype : "combo",
									mode : "local",
									valueField : "key",
									displayField : "text",
									triggerAction : "all",
									store : new Ext.data.SimpleStore({
										fields : ["key","text"]
									}),
									listeners : {
										focus : function(d) {
											var c = Ext.getCmp("acName").getStore();
											if(c.getCount() <= 0) {
												Ext.Ajax.request({
													url : __ctxPath + "/kpi/loadHrPaAssessmentcriteria.do",
													method : "post",
													success : function(f) {
														var e = Ext.util.JSON.decode(f.responseText);
														c.loadData(e);
													}
												});
											}
										},
										select : function(d) {
											Ext.getCmp("target").setText(d.getRawValue() + "的目标");
											Ext.getCmp("real").setText(d.getRawValue() +"的达成");
											Ext.getCmp("target").show();
											Ext.getCmp("real").show();
										}
									}
								}
							]
						}, {
							layout : "form",
							border : false,
							items : [
								{
									text : "目标",
									id : "target",
									style : "margin-left:10px",
									xtype : "button",
									hidden : true,
									listeners : {
										click : function(d) {
											Ext.getCmp("formula").setValue(Ext.getCmp("formula").getValue() + "{" + Ext.getCmp("acName").getValue() + "_t}");
										}
									}
								}
							]
						}, {
							layout : "form",
							border : false,
							items : [
								{
									text : "达成",
									id : "real",
									style : "margin-left:10px",
									xtype : "button",
									hidden : true,
									listeners : {
										click : function(d) {
											Ext.getCmp("formula").setValue(Ext.getCmp("formula").getValue() + "{" + Ext.getCmp("acName").getValue() + "_r}");
										}
									}
								}
							]
						}
					]
				}, {
					xtype : "container",
					layout : "column",
					border : false,
					items : [
						{
							layout : "form",
							border : false,
							items : [
								{
									fieldLabel : "运算符",
									labelStyle : "text-align:right",
									id : "yusuanfu",
									width : 70,
									xtype : "combo",
									triggerAction : "all",
									store : [
										"+",
										"-",
										"*",
										"/",
									],
									listeners : {
										select : function(d) {
											Ext.getCmp("formula").setValue(Ext.getCmp("formula").getValue() + d.getValue());
										}
									}
								}
							]
						}, {
							layout : "form",
							border : false,
							items : [
								{
									fieldLabel : "操作符",
									id : "caozuofu",
									labelStyle : "text-align:right",
									width : 70,
									border : true,
									xtype : "combo",
									triggerAction : "all",
									store : [
										">",
										"<",
										"=",
										">=",
										"<="
									],
									listeners : {
										select : function(d) {
											Ext.getCmp("formula").setValue(Ext.getCmp("formula").getValue() + d.getValue());
										}
									}
								}
							]
						}, {
							layout : "form",
							border : false,
							items : [
								{
									fieldLabel : "逻辑符",
									labelStyle : "text-align:right",
									id : "luojifu",
									width : 70,
									xtype : "combo",
									triggerAction : "all",
									store : [
										"[",
										"]",
										"(",
										")",
										"且",
										"或"
									],
									listeners : {
										select : function(d) {
											Ext.getCmp("formula").setValue(Ext.getCmp("formula").getValue() + d.getValue());
										}
									}
								}
							]
						}
					]
				}, {
					fieldLabel : "计算公式",
					labelStyle : "text-align:right",
					id : "formula",
					anchor : "98%,98%",
					xtype : "textarea",
					allowBlank : false,
					blankText : "计算公式不能为空！"
				}, {
					fieldLabel : "评分说明",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindexscore.pisDesc",
					id : "pisDesc",
					anchor : "98%,98%",
					xtype : "textarea",
				}
			]
		});
		if(this.pisId != null && this.pisId != "undefined" && this.pisId != 0) {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/kpi/getHrPaPerformanceindexscore.do",
				params : {
					id : this.pisId,
					piId : this.piId
				},
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					
				},
				failure : function() {
				
				}
			});
		} else if(this.pisId == 0) {
			var rowStore = Ext.getCmp("HrPaPerformanceindexscoreGrid").getStore().getAt(this.rowNumber);
			Ext.getCmp("pisScore").setValue(rowStore.data.pisScore);
			Ext.getCmp("formula").setValue(rowStore.data.formula);
			Ext.getCmp("pisDesc").setValue(rowStore.data.pisDesc);
		}
		this.buttons = [
			{
				text : "取消",
				handler : this.cancel.createCallback(this)
			}, {
				text : "确认",
				handler : this.save.createCallback(this.formPanel, this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a, b) {
		if(isNaN(Ext.getCmp("pisScore").getValue()) || Ext.getCmp("pisScore").getValue() < 1 || Ext.getCmp("pisScore").getValue() > 5) {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "得分必须是1-5之间的数字，请重新填写。",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		if(a.getForm().isValid()) {
			var d = Ext.getCmp("HrPaPerformanceindexscoreGrid");
			//删除gridPanel里边修改以前的记录
			if(b.rowNumber != null && b.rowNumber != "undefined") {
				d.getStore().removeAt(b.rowNumber);
			}
			var pisRecord = Ext.data.Record.create([
				{
					name : "id",
					type : "int"
				}, {
					name : "pi.id",
					type : "int"
				}, {
					name : "pisScore",
					type : "float"
				}, {
					name : "pisDesc",
					type : "string"
				}, {
					name : "formula",
					type : "string"
				}
			]);
			var pis = new pisRecord({
				"id" : Ext.getCmp("pisId").getValue(),
				"pi.id" : Ext.getCmp("piIdForm2").getValue() == null ? 0 : Ext.getCmp("piIdForm2").getValue(),
				"pisScore" : Ext.getCmp("pisScore").getValue(),
				"pisDesc" : Ext.getCmp("pisDesc").getValue(),
				"formula" : Ext.getCmp("formula").getValue()
			});
			d.getStore().add(pis);
			b.close();
		}
	}
});