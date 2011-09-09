HrPaAssessmentcriteriaFormView = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaAssessmentcriteriaFormView.superclass.constructor.call(this, {
			id : "HrPaAssessmentcriteriaFormViewWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 270,
			width : 400,
			title : "绩效考核标准查看"
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			id : "HrPaAssessmentcriteriaFormView",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					fieldLabel : "考核标准名称",
					labelStyle : "text-align:right;width:120px;",
					name : "hrPaAssessmentcriteria.acName",
					id : "acName",
					readOnly : true,
					allowBlank : false,
					blankText : "考核标准名称不能为空！"
				}, {
					fieldLabel : "考核标准关键字",
					labelStyle : "text-align:right;width:120px;",
					name : "hrPaAssessmentcriteria.acKey",
					id : "acKey",
					readOnly : true,
					allowBlank : false,
					blankText : "考核标准关键字不能为空！"
				}, {
					fieldLabel : "是否销售类定量考核",
					labelStyle : "text-align:right;width:120px;",
					xtype : "checkboxgroup",
					columns : 1,
					items : [
						{
							boxLabel : "是",
							name : "hrPaAssessmentcriteria.isSalesAC",
							id : "isSalesAC",
							inputValue : 1
						}
					]
				}, {
					fieldLabel : "考核标准描述",
					labelStyle : "text-align:right;width:120px;",
					name : "hrPaAssessmentcriteria.acDesc",
					height : 100,
					id : "acDesc",
					xtype : "textarea",
					readOnly : true
				}
			]
		});
		if(this.acId != null && this.acId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/kpi/getHrPaAssessmentcriteria.do?id=" + this.acId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					if(e.data.isSalesAC == "1") {
						Ext.getCmp("isSalesAC").setValue(true);
					}
				},
				failure : function() {
					
				}
			});
		}
	}
});