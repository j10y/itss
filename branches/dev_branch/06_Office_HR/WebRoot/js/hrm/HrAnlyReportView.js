Ext.ns("HrAnlyReportView");
HrAnlyReportView = Ext
		.extend(Ext.Panel,
				{
					searchPanel : null,
					gridPanel : null,
					store : null,
					topbar : null,
					constructor : function(a) {
						Ext.applyIf(this, a);
						HrAnlyReportView.superclass.constructor.call(this, {
							id : "HrAnlyReportView",
							title : "人力资源分析报表",
							iconCls : "menu-notice",
							region : "center",
							layout : "border",
							autoLoad:{
								url: __ctxPath + '/reportJsp/forword.jsp'
							}
						});
					}
				});
