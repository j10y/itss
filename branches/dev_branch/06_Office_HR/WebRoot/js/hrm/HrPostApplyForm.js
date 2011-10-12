HrPostApplyForm = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPostApplyForm.superclass.constructor.call(this, {
			id : "HrPostApplyForm",
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
			title : "转正申请页面",
			listeners : {
				close : function() {
					Ext.getCmp("HrPostApplyView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			}
		});
	},
	initComponents : function() {
		this.panel = new Ext.Panel({
			border : false,
			bodyBorder : false,
			html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/hrm/previewHrPostApply.do?id=" + this.applyId + "'>"
		});
	}
});