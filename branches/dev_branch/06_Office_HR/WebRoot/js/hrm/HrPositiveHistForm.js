HrPositiveHistForm = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPositiveHistForm.superclass.constructor.call(this, {
			id : "HrPositiveHistForm",
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
			title : "转正历史详情",
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
			autoScroll : true,
			items : [
				{
					xtype : "container",
					border : false,
					layout : "column",
					width : 1600,
					items : [
						{
							layout : "form",
							columnWidth : 0.5,
							items : [
								new Ext.Panel({
									border : false,
									height : 800,
									html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/reportJsp/showReport.jsp?raq=/hrpostapply.raq&id=" + this.applyId + "'>"
								})
							]
						}, {
							layout : "form",
							columnWidth : 0.5,
							items : [
								new Ext.Panel({
									border : false,
									height : 800,
									html : "<iframe frameborder='no' width=100% height=100% src='" + __ctxPath + "/reportJsp/showReport.jsp?raq=/hrpostassessment.raq&id=" + this.assessmentId + "'>"
								})
							]
						}
					]
				},
				new Ext.Panel({
					title : "流程审批信息",
					region : "center",
					width : 800,
					autoScroll : true,
					autoLoad : {
						url : __ctxPath + "/hrm/processHistHrPostAssessment.do?applyId=" + this.applyId
					}
				})
			]
		});
	}
});