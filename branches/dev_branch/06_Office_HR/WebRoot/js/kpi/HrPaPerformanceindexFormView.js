HrPaPerformanceindexFormView = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaPerformanceindexFormView.superclass.constructor.call(this, {
			id : "HrPaPerformanceindexFormViewWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 360,
			width : 400,
			title : "绩效考核项目录入",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			id : "HrPaPerformanceindexForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [
				{
					fieldLabel : "考核项目名称",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindex.paName",
					id : "paName",
					readOnly : true,
					allowBlank : false,
					blankText : "考核项目名称不能为空！"
				}, {
					fieldLabel : "考核项目类型",
					hiddenName : "hrPaPerformanceindex.paType",
					labelStyle : "text-align:right",
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
					hiddenName : "hrPaPerformanceindex.paFrequency",
					labelStyle : "text-align:right",
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
					hiddenName : "hrPaPerformanceindex.paMode",
					labelStyle : "text-align:right",
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
					fieldLabel : "是否唯一否决",
					labelStyle : "text-align:right",
					xtype : "checkboxgroup",
					columns : 1,
					items : [
						{
							boxLabel : "是",
							name : "hrPaPerformanceindex.paIsOnlyNegative",
							id : "paIsOnlyNegative",
							inputValue : 1
						}
					]
				}, {
					fieldLabel : "考核项目描述",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindex.paDesc",
					id : "paDesc",
					xtype : "textarea",
					readOnly : true
				}, {
					fieldLabel : "备注信息",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindex.remark",
					id : "remark",
					xtype : "textarea",
					readOnly : true
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
					Ext.getCmp("typeName").setValue(e.data.type.id);
					Ext.getCmp("typeName").setRawValue(e.data.type.name);
					Ext.getCmp("frequencyName").setValue(e.data.frequency.id);
					Ext.getCmp("frequencyName").setRawValue(e.data.frequency.name);
					Ext.getCmp("modeName").setValue(e.data.mode.id);
					Ext.getCmp("modeName").setRawValue(e.data.mode.name);
					if(e.data.paIsOnlyNegative == "1") {
						Ext.getCmp("paIsOnlyNegative").setValue(true);
					}
				},
				failure : function() {
					
				}
			});
		}
	}
});