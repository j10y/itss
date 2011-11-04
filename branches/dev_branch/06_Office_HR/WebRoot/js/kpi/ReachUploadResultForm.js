ReachUploadResultForm = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		ReachUploadResultForm.superclass.constructor.call(this, {
			id : "ReachUploadResultForm",
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
			title : "达成数据上传结果",
			listeners : {
				close : function() {

				}
			}
		});
	},
	initComponents : function() {
		this.panel = new Ext.Panel({
			border : false,
			bodyBorder : false,
			html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/kpi/getUploadResultHrPaAcreached.do?templateId=" + this.templateId + "&uploadFileType=" + this.uploadFileType + "&stamp=" + Math.random() +"'>"
		});
	}
});