PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'column',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 800,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	viewConfig : {// 自适应填充
		autoFill : true,
		forceFit : true
	},
	layoutConfig : {
		columns :1
	},
	panelNode : function() {
		  var menu = new Ext.menu.Menu({
	        id: 'mainMenu',
	        items: [
	            {
	            	id : "tree0",
	                text: '事件整体分析（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree1",
	                text: '事件整体分析（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '事件整体分析（年表）',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [       
	        {
	            	id : "tree3",
	                text: '已解决事件清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '处理中事件清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree28",
	                text: '所有事件清单',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [       
	        {
	            	id : "tree5",
	                text: '技能组满意度对比',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: '技能组事件处理时间对比',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree7",
	                text: '技能组一次性解决率对比',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
             var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [       
	        /*{
	            	id : "tree8",
	                text: '部门拨打热线服务分析',
	                iconCls:'search',
	                handler:onItemClick
	            },*/{
	            	id : "tree9",
	                text: '员工拨打热线次数top10',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree10",
	                text: '热线数量按服务分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '热线top10（解决方案问题类型）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree27",
	                text: '热线月统计',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [       
	        {
	            	id : "tree12",
	                text: '问题概要分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree13",
	                text: '服务项问题次数分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree14",
	                text: '配置项问题次数分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree15",
	                text: '问题引发配置项变更分析',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [       
	        {
	            	id : "tree16",
	                text: '工程师能力满意度（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '工程师能力满意度（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: '服务项满意度（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree30",
	                text: '服务项满意度（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            /*,{
	            	id : "tree18",
	                text: '热线满意度（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree26",
	                text: '热线满意度（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            }*/
	            ]
            }); 
            var menu6 = new Ext.menu.Menu({
	        id: 'mainMenu6',
	        items: [       
	        {
	            	id : "tree19",
	                text: '再分配事件百分比（按工程师）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: '再分配事件百分比（按服务项）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: '再分配事件百分比（按支持组）',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu7 = new Ext.menu.Menu({
	        id: 'mainMenu7',
	        items: [{
		            	id : "tree22",
		                text: '工程师一次性正确解决的百分比（日表）',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree24",
		                text: '工程师一次性正确解决的百分比（月表）',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree23",
		                text: '支持组一次性正确解决的百分比（日表）',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree25",
		                text: '支持组一次性正确解决的百分比（月表）',
		                iconCls:'search',
		                handler:onItemClick
		            }]
            }); 
		// ---------
        function onItemClick(node){
        	//---------
        	var menu = new Ext.menu.Menu({
	        id: 'mainMenu',
	        items: [
	            {
	            	id : "tree0",
	                text: '事件整体分析（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree1",
	                text: '事件整体分析（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '事件整体分析（年表）',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [       
	        {
	            	id : "tree3",
	                text: '已解决事件清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '处理中事件清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree28",
	                text: '所有事件清单',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [       
	        {
	            	id : "tree5",
	                text: '技能组满意度对比',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: '技能组事件处理时间对比',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree7",
	                text: '技能组一次性解决率对比',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
             var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [       
	        /*{
	            	id : "tree8",
	                text: '部门拨打热线服务分析',
	                iconCls:'search',
	                handler:onItemClick
	            },*/{
	            	id : "tree9",
	                text: '员工拨打热线次数top10',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree10",
	                text: '热线数量按服务分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '热线top10（解决方案问题类型）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree27",
	                text: '热线月统计',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [       
	        {
	            	id : "tree12",
	                text: '问题概要分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree13",
	                text: '服务项问题次数分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree14",
	                text: '配置项问题次数分析',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree15",
	                text: '问题引发配置项变更分析',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [       
	        {
	            	id : "tree16",
	                text: '工程师能力满意度（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '工程师能力满意度（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: '服务项满意度（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree30",
	                text: '服务项满意度（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            }
	           /* ,{
	            	id : "tree18",
	                text: '热线满意度（日表）',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree26",
	                text: '热线满意度（月表）',
	                iconCls:'search',
	                handler:onItemClick
	            }*/
	            ]
            }); 
            var menu6 = new Ext.menu.Menu({
	        id: 'mainMenu6',
	        items: [       
	        {
	            	id : "tree19",
	                text: '再分配事件百分比(按工程师)',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: '再分配事件百分比(按服务项)',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: '再分配事件百分比(按支持组)',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu7 = new Ext.menu.Menu({
	        id: 'mainMenu7',
	        items: [{
		            	id : "tree22",
		                text: '工程师一次性正确解决的百分比（日表）',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree23",
		                text: '工程师一次性正确解决的百分比（月表）',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree24",
		                text: '支持组一次性正确解决的百分比（日表）',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree25",
		                text: '支持组一次性正确解决的百分比（月表）',
		                iconCls:'search',
		                handler:onItemClick
		            }]
            }); 
        	//--------
	       	var tempJsp = "/reportJsp/showReport.jsp?raq=/eventWholeAnalysisForDay.raq";
	       	//var menu=this.menu;
			if (node.id == "tree0") {
				tempJsp ="/reportJsp/showReport.jsp?raq=/eventWholeAnalysisForDay.raq";
			} else if (node.id == "tree1") {
				tempJsp ="/reportJsp/showReport.jsp?raq=/eventWholeAnalysisForMonth.raq";
			} else if (node.id == "tree2") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/eventWholeAnalysisForYear.raq";
			}  
			
			else if (node.id == "tree3") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/eventsSlovedList.raq";
			}  else if (node.id == "tree4") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/dealingEventsList.raq";
			} else if (node.id == "tree28") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/AllEventDetail.raq";
			}  
			
			/*else if (node.id == "tree5") {
				tempJsp = "/user/event/eventReport/supportGroupSatisfyCompare.jsp";
			} else if (node.id == "tree6") {
				tempJsp = "/user/event/eventReport/supportGroupDealtimeCompare.jsp";
			} else if (node.id == "tree7") {
				tempJsp = "/user/event/eventReport/supportGroupOnetimeSloveCompare.jsp";
			} 
			
			else if (node.id == "tree8") {
				tempJsp = "#";
			} else if (node.id == "tree9") {
				tempJsp = "/user/event/eventReport/userCallTop10.jsp";
			} else if (node.id == "tree10") {
				tempJsp = "/user/event/eventReport/CCAmountServiceAnalysis.jsp";
			} else if (node.id == "tree11") {
				tempJsp = "/user/event/eventReport/problemTypeCallTop10.jsp";
			} else if (node.id == "tree27") {
				tempJsp = "/user/event/eventReport/CCForMonth.jsp";
			} */
			
			
			else if (node.id == "tree12") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/problemAnalysis.raq";
			} else if (node.id == "tree13") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/serviceItemProblmeAnalysis.raq";
			}else if (node.id == "tree14") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/ciProblmeAnalysis.raq";
			} else if (node.id == "tree15") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/problmeCauseCIBatchModifyAnalysis.raq";
			}
			else if (node.id == "tree16") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/engineerAbilitySatisfyForDay.raq";
			}else if(node.id == "tree29"){
				tempJsp = "/reportJsp/showReport.jsp?raq=/engineerAbilitySatisfyForMonth.raq";
			} else if (node.id == "tree17") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/serviceItemSatisfyForDay.raq";
			} else if (node.id == "tree30") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/serviceItemSatisfyForMonth.raq";
			} else if (node.id == "tree18") {
				tempJsp = "/user/event/eventReport/CCSatisfyForDay.jsp";
			} else if (node.id == "tree26") {
				tempJsp = "/user/event/eventReport/CCSatisfyForMonth.jsp";
			} 
			
			else if (node.id == "tree19") {
				tempJsp = "/user/event/eventReport/reassignEventsByEngineer.jsp";
			} else if (node.id == "tree20") {
				tempJsp = "/user/event/eventReport/reassignEventByServiceItem.jsp";
			} else if (node.id == "tree21") {
				tempJsp = "/user/event/eventReport/reassignEventBySupportGroup.jsp";
			} 
			
			else if (node.id == "tree22") {
				tempJsp = "/user/event/eventReport/oneTimeSloveByEngineerForDay.jsp";
			} else if (node.id == "tree23") {
				tempJsp = "/user/event/eventReport/oneTimeSloveByEngineerForMonth.jsp";
			} else if (node.id == "tree24") {
				tempJsp = "/user/event/eventReport/oneTimeSloveBySupportGroupForDay.jsp";
			} else if (node.id == "tree25") {
				tempJsp = "/user/event/eventReport/oneTimeSloveBySupportGroupForMonth.jsp";
			} 
			var item = {
				xtype : "panel",
				//title : '查询结果',
				autoScroll : true,
				columnWidth : 1.00,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '事件整体分析',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '事件清单',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
		            /* ,{                   // <-- Add the action directly to a toolbar
		                text: '技能组对比分析',
		                menu: menu2         // <-- Add the action directly to a menu
		            }
		            
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '热线相关分析',
		                menu: menu3         // <-- Add the action directly to a menu
		            }*/
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '问题相关分析',
		                menu: menu4         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '满意度相关分析',
		                menu: menu5         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '再分配事件百分比',
		                menu: menu6        // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '一次性解决事件的百分比',
		                menu: menu7         // <-- Add the action directly to a menu
		            }
		        ],
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ tempJsp,
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				},
				width : 700,
				height : 540
			};
			Ext.getCmp("PageTemplates").remove('menuPanel');
			Ext.getCmp("PageTemplates").add(item);
			Ext.getCmp("PageTemplates").doLayout();
	    }
		var item = {
			xtype : "panel",
			//title : '查询结果',
			columnWidth : 1.00,
			autoScroll : true,
			id : "menuPanel",// 注意这要和上面的保持一致性
			tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '事件整体分析',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '事件清单',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
		            /*,{                   // <-- Add the action directly to a toolbar
		                text: '技能组对比分析',
		                menu: menu2         // <-- Add the action directly to a menu
		            }
		           
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '热线相关分析',
		                menu: menu3         // <-- Add the action directly to a menu
		            }*/
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '问题相关分析',
		                menu: menu4         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '满意度相关分析',
		                menu: menu5         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '再分配事件百分比',
		                menu: menu6        // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '一次性解决事件的百分比',
		                menu: menu7         // <-- Add the action directly to a menu
		            }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/reportJsp/showReport.jsp?raq=/eventWholeAnalysisForDay.raq",
				text : "页面正在加载中......",
				method : 'post',
				scope : this
			},
			width : 700,
			height : 540
		};

		var items = new Array();
		//items.push( this.tree);
		items.push(item);
		return items;
	},

	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		this.items = this.panelNode();
		PagePanel.superclass.initComponent.call(this);
	}
})