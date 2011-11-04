Ext.ns("HrPositionAtlasView");
HrPositionAtlasView = Ext
		.extend(
				Ext.Panel,
				{
				  tabsPanel : null,
				  constructor : function(a) {
						this.initUIComponents();
						HrPositionAtlasView.superclass.constructor.call(this, {
							id : "HrPositionAtlasView",
							title : "岗位图谱",
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
						this.tabsPanel = new Ext.TabPanel({
					        width:1200,
					        activeTab: 0,
					        frame:true,
					        height: 700,
					        items:[
					            {title: '岗位图谱',
					            autoLoad : {
									url : __ctxPath+"/pages/hrm/hrpositionatlasview.jsp",
									text : "页面正在加载中......",
									method : 'post',
									scope : this
									}
					            }       		            	            
					        ]
				    	});
					}
		});