PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'column',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
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
	            	id : "tree1",
	                text: '我提出的需求',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '我审批过的需求',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            {
	            	id : "tree3",
	                text: '需求列表',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '需求分类',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: 'IT需求',
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
	            	id : "tree1",
	                text: '我提出的需求',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '我审批过的需求',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            {
	            	id : "tree3",
	                text: '需求列表',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '需求分类',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: 'IT需求',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
        	//--------
	       	var tempJsp = "/user/require/report/requireApproveReport.jsp";
	       	//var menu=this.menu;
			if (node.id == "tree1") {
				tempJsp ="/user/require/report/requireApplyMySelfReport.jsp";
			} else if (node.id == "tree2") {
				tempJsp = "/user/require/report/requireApproveReport.jsp";
			}  else if (node.id == "tree3") {
				tempJsp = "/user/require/report/requirementAnalyse.jsp";
			}  else if (node.id == "tree4") {
				tempJsp = "/user/require/report/requireType.jsp";
			}  else if (node.id == "tree5") {
				tempJsp = "/user/require/report/requirementforit.jsp";
			} 
			var item = {
				xtype : "panel",
				//title : '查询结果',
				autoScroll : true,
				columnWidth : 1.00,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '需求查询',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '项目统计',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
		        ],
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ tempJsp,
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				},
				width : 1000,
				height : 430
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
	                text: '需求查询',
	                menu: menu         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
		                text: '项目统计',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/user/require/report/requireApplyMySelfReport.jsp",
				text : "页面正在加载中......",
				method : 'post',
				scope : this
			},
			width : 800,
			height : 430
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