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
	viewConfig : {// ����Ӧ���
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
	                text: '�¼�����������ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree1",
	                text: '�¼�����������±�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '�¼�������������',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [       
	        {
	            	id : "tree3",
	                text: '�ѽ���¼��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '�������¼��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree28",
	                text: '�����¼��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [       
	        {
	            	id : "tree5",
	                text: '����������ȶԱ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: '�������¼�����ʱ��Ա�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree7",
	                text: '������һ���Խ���ʶԱ�',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
             var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [       
	        /*{
	            	id : "tree8",
	                text: '���Ų������߷������',
	                iconCls:'search',
	                handler:onItemClick
	            },*/{
	            	id : "tree9",
	                text: 'Ա���������ߴ���top10',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree10",
	                text: '�����������������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '����top10����������������ͣ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree27",
	                text: '������ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [       
	        {
	            	id : "tree12",
	                text: '�����Ҫ����',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree13",
	                text: '�����������������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree14",
	                text: '�����������������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree15",
	                text: '��������������������',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [       
	        {
	            	id : "tree16",
	                text: '����ʦ��������ȣ��ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '����ʦ��������ȣ��±�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: '����������ȣ��ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree30",
	                text: '����������ȣ��±�',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            /*,{
	            	id : "tree18",
	                text: '��������ȣ��ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree26",
	                text: '��������ȣ��±�',
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
	                text: '�ٷ����¼��ٷֱȣ�������ʦ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: '�ٷ����¼��ٷֱȣ��������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: '�ٷ����¼��ٷֱȣ���֧���飩',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu7 = new Ext.menu.Menu({
	        id: 'mainMenu7',
	        items: [{
		            	id : "tree22",
		                text: '����ʦһ������ȷ����İٷֱȣ��ձ�',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree24",
		                text: '����ʦһ������ȷ����İٷֱȣ��±�',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree23",
		                text: '֧����һ������ȷ����İٷֱȣ��ձ�',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree25",
		                text: '֧����һ������ȷ����İٷֱȣ��±�',
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
	                text: '�¼�����������ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree1",
	                text: '�¼�����������±�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '�¼�������������',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [       
	        {
	            	id : "tree3",
	                text: '�ѽ���¼��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '�������¼��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree28",
	                text: '�����¼��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [       
	        {
	            	id : "tree5",
	                text: '����������ȶԱ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: '�������¼�����ʱ��Ա�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree7",
	                text: '������һ���Խ���ʶԱ�',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
             var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [       
	        /*{
	            	id : "tree8",
	                text: '���Ų������߷������',
	                iconCls:'search',
	                handler:onItemClick
	            },*/{
	            	id : "tree9",
	                text: 'Ա���������ߴ���top10',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree10",
	                text: '�����������������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '����top10����������������ͣ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree27",
	                text: '������ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [       
	        {
	            	id : "tree12",
	                text: '�����Ҫ����',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree13",
	                text: '�����������������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree14",
	                text: '�����������������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree15",
	                text: '��������������������',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [       
	        {
	            	id : "tree16",
	                text: '����ʦ��������ȣ��ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '����ʦ��������ȣ��±�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: '����������ȣ��ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree30",
	                text: '����������ȣ��±�',
	                iconCls:'search',
	                handler:onItemClick
	            }
	           /* ,{
	            	id : "tree18",
	                text: '��������ȣ��ձ�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree26",
	                text: '��������ȣ��±�',
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
	                text: '�ٷ����¼��ٷֱ�(������ʦ)',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: '�ٷ����¼��ٷֱ�(��������)',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: '�ٷ����¼��ٷֱ�(��֧����)',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu7 = new Ext.menu.Menu({
	        id: 'mainMenu7',
	        items: [{
		            	id : "tree22",
		                text: '����ʦһ������ȷ����İٷֱȣ��ձ�',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree23",
		                text: '����ʦһ������ȷ����İٷֱȣ��±�',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree24",
		                text: '֧����һ������ȷ����İٷֱȣ��ձ�',
		                iconCls:'search',
		                handler:onItemClick
		            },{
		            	id : "tree25",
		                text: '֧����һ������ȷ����İٷֱȣ��±�',
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
				//title : '��ѯ���',
				autoScroll : true,
				columnWidth : 1.00,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '�¼��������',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�¼��嵥',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
		            /* ,{                   // <-- Add the action directly to a toolbar
		                text: '������Աȷ���',
		                menu: menu2         // <-- Add the action directly to a menu
		            }
		            
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '������ط���',
		                menu: menu3         // <-- Add the action directly to a menu
		            }*/
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '������ط���',
		                menu: menu4         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '�������ط���',
		                menu: menu5         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '�ٷ����¼��ٷֱ�',
		                menu: menu6        // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: 'һ���Խ���¼��İٷֱ�',
		                menu: menu7         // <-- Add the action directly to a menu
		            }
		        ],
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ tempJsp,
					text : "ҳ�����ڼ�����......",
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
			//title : '��ѯ���',
			columnWidth : 1.00,
			autoScroll : true,
			id : "menuPanel",// ע����Ҫ������ı���һ����
			tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '�¼��������',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�¼��嵥',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
		            /*,{                   // <-- Add the action directly to a toolbar
		                text: '������Աȷ���',
		                menu: menu2         // <-- Add the action directly to a menu
		            }
		           
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '������ط���',
		                menu: menu3         // <-- Add the action directly to a menu
		            }*/
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '������ط���',
		                menu: menu4         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '�������ط���',
		                menu: menu5         // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: '�ٷ����¼��ٷֱ�',
		                menu: menu6        // <-- Add the action directly to a menu
		            }
		            ,{                   // <-- Add the action directly to a toolbar
		                text: 'һ���Խ���¼��İٷֱ�',
		                menu: menu7         // <-- Add the action directly to a menu
		            }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/reportJsp/showReport.jsp?raq=/eventWholeAnalysisForDay.raq",
				text : "ҳ�����ڼ�����......",
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
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		this.items = this.panelNode();
		PagePanel.superclass.initComponent.call(this);
	}
})