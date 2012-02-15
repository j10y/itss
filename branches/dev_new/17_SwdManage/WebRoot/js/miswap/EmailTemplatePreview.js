EmailTemplatePreview = Ext.extend(Ext.Window, {
	formPanel : null,
	previewPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		EmailTemplatePreview.superclass.constructor.call(this, {
			id : "EmailTemplatePreviewWin",
			layout : "fit",
			items : [
				this.formPanel
			],
			modal : true,
			height : 520,
			width : 900,
			title : "邮件模板预览"
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			id : "SupplyLinkerForm",
			autoScroll : true,
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					fieldLabel : "模板名称",
					id : "name",
					readOnly : true
				}, {
					fieldLabel : "模板描述",
					xtype : "textarea",
					id : "description",
					readOnly : true
				},
				new Ext.Panel({
					id : "previewPanel",
					region : "center",
					title : "界面预览"
				})
			]
		});
		if(this.emailTemplateId != null && this.emailTemplateId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/miswap/getEmailTemplate.do?id=" + this.emailTemplateId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("previewPanel").body.update(e.data.preview);
				},
				failure : function() {
					
				}
			});
		}
	},
	cancel : function(a) {
		a.close();
	}
});