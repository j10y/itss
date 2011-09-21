Ext.ns("SpReportView");
SpReportView = Ext
		.extend(
				Ext.Panel,
				{
				  tabsPanel : null,
				  constructor : function(a) {
						this.initUIComponents();
						SpReportView.superclass.constructor.call(this, {
							id : "SpReportView",
							title : "门店绩效考核统计",
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
					            {title: '最终得分',
					            autoLoad : {
									url : __ctxPath+"/js/spreport/lasrresultforcheck.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }, {title: '跨部门巡店',
					            autoLoad : {
									url : __ctxPath+"/js/spreport/resultcondeptcheck.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            } , {title: '本部门巡店',
					            autoLoad : {
									url : __ctxPath+"/js/spreport/resultselfdeptcheck.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            } , {title: '客服体系评分汇总',
					            autoLoad : {
									url : __ctxPath+"/js/spreport/customerserviceCheck.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            } , {title: '防损体系评分汇总',
					            autoLoad : {
									url : __ctxPath+"/js/spreport/losspreventioncheck.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            } , {title: '基础数据-跨部门联合巡店',
					            autoLoad : {
									url : __ctxPath+"/js/spreport/basedatacondeptcheck.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }   , {title: '基础数据-本部门联合巡店',
					            autoLoad : {
									url :__ctxPath+"/js/spreport/basedataselfdeptcheck.jsp?curMonth="+curMonth,
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }            		            	            
					        ]
				    	});
					}
		});