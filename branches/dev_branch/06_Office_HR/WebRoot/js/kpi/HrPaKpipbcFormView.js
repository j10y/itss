HrPaKpipbcFormView = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaKpipbcFormView.superclass.constructor.call(this, {
			id : "HrPaKpipbcFormView",
			region : "center",
			layout : "fit",
			border : false,
			items : [
				this.panel
			],
			modal : true,
			height : 600,
			width : 850,
			autoScroll : true,
			title : "PBC预览"
		});
	},
	initComponents : function() {
		this.panel = new Ext.Panel({
			border : false,
			bodyBorder : false,
			html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/kpi/previewHrPaKpipbc.do?id=" + this.pbcId + "'>"
		});
	}
});