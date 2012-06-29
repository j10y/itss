Ext.ns("YddjReportView");
YddjReportView = Ext
		.extend(
				Ext.Panel,
				{
				  tabsPanel : null,
				  constructor : function(a) {
						this.initUIComponents();
						YddjReportView.superclass.constructor.call(this, {
							id : "YddjReportView",
							title : "异动登记表",
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
					            {title: '异动登记表',
					            autoLoad : {
									url : __ctxPath+"/pages/hrm/hrmreport.jsp?reporttemplet=changejob",
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }       		            	            
					        ]
				    	});
					}
		});