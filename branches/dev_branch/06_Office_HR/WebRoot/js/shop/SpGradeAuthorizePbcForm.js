SpGradeAuthorizePbcForm = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SpGradeAuthorizePbcForm.superclass.constructor.call(this, {
			id : "SpGradeAuthorizePbcForm",
			region : "center",
			layout : "fit",
			border : false,
			maximizable : true,
			items : [
				this.panel
			],
			modal : true,
			height : 600,
			width : 850,
			autoScroll : true,
			title : "授权PBC打分"
		});
	},
	initComponents : function() {
		this.panel = new Ext.Panel({
			border : false,
			bodyBorder : false,
			html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/shop/previewSpPaAuthorizepbc.do?id=" + this.pbcId + "'>"
		});
	}
});