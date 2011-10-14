Ext.ns("HrBudgetReportView");
HrBudgetReportView = Ext
		.extend(
				Ext.Panel,
				{
				  tabsPanel : null,
				  constructor : function(a) {
						this.initUIComponents();
						HrBudgetReportView.superclass.constructor.call(this, {
							id : "HrBudgetReportView",
							title : "人力成本分析",
							iconCls : "menu-notice",
							region : "center",
							layout : "border",
							items : [this.tabsPanel]
						});
					},
					initUIComponents : function() {
					var myDate = new Date();
					var m=(myDate.getMonth()+1)+"";
					if(m.length==1){
						m="0"+m
					}
					var curMonth=myDate.getFullYear()+"-"+m;
					//alert(curMonth);
						this.tabsPanel = new Ext.TabPanel({
					        width:1200,
					        activeTab: 0,
					        frame:true,
					        height: 700,
					        items:[
					            {title: '部门成本分析',
					            autoLoad : {
									url : __ctxPath+"/pages/hrm/hrbudgetreportbydepart.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }, {title: '门店成本分析',
					            autoLoad : {
									url : __ctxPath+"/pages/hrm/hrbudgetreportbyshop.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }          		            	            
					        ]
				    	});
					}
		});