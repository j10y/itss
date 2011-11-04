TargetUploadResultForm = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		TargetUploadResultForm.superclass.constructor.call(this, {
			id : "TargetUploadResultForm",
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
			title : "目标数据上传结果",
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
			html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/kpi/getUploadResultHrPaAssessmenttasksassigned.do?templateId=" + this.templateId + "&uploadFileType=" + this.uploadFileType + "&stamp=" + Math.random() +"'>"
		});
	}
});