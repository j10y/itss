Ext.ns("HrTZAnlyReportView");
HrTZAnlyReportView = Ext
		.extend(Ext.Panel,
				{
					searchPanel : null,
					gridPanel : null,
					store : null,
					topbar : null,
					constructor : function(a) {
						Ext.applyIf(this, a);
						HrTZAnlyReportView.superclass.constructor.call(this, {
							id : "HrTZAnlyReportView",
							title : "PBC台账报表",
							iconCls : "menu-notice",
							region : "center",
							layout : "border",
							autoLoad:{
								url: __ctxPath + '/reportJsp/tzforword.jsp'
							}
						});
					}
				});
