Ext.ns("HrDepartureReportView");
HrDepartureReportView = Ext
		.extend(
				Ext.Panel,
				{
				  tabsPanel : null,
				  constructor : function(a) {
						this.initUIComponents();
						HrDepartureReportView.superclass.constructor.call(this, {
							id : "HrDepartureReportView",
							title : "人员离职分析",
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
					            {title: '离职人员清单',
					            autoLoad : {
									url : __ctxPath+"/pages/hrm/departurepersionreport.jsp?curMonth="+myDate.getFullYear(),
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }       		            	            
					        ]
				    	});
					}
		});