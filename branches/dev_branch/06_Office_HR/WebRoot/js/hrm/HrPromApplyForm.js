HrPromApplyForm = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPromApplyForm.superclass.constructor.call(this, {
			id : "HrPromApplyForm",
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
			title : "晋升申请页面",
			listeners : {
				close : function() {
					Ext.getCmp("HrPromApplyView").gridPanel.store.reload({
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
			html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/hrm/previewHrPromApply.do?id=" + this.applyId + "'>"
		});
	}
});