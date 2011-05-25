PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	frame : true,
	layout:'fit',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	panelNode : function() {
		  var menu = new Ext.menu.Menu({
	        id: 'mainMenu',
	        items: [
	            {
	            	id : "tree01",
	                text: '基本配置信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree02",
	                text: '基本关系信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree03",
	                text: '未设置关系的配置信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            	{
	            	id : "tree11",
	                text: '变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*	{
	            	id : "tree12",
	                text: '基于事件的变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '基于问题的变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },	            	{
	            	id : "tree14",
	                text: '基于需求的变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            	{
	            	id : "tree15",
	                text: '基于配置项的反向复杂查询',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree21",
	                text: '缺少必要关系的配置项查询',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            ]
            });  


        function onItemClick(node){

		  var menu = new Ext.menu.Menu({
	        id: 'mainMenu',
	        items: [
	            {
	            	id : "tree01",
	                text: '基本配置信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree02",
	                text: '基本关系信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree03",
	                text: '未设置关系的配置信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            	{
	            	id : "tree11",
	                text: '变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*	{
	            	id : "tree12",
	                text: '基于事件的变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '基于问题的变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },	            	{
	            	id : "tree14",
	                text: '基于需求的变更信息查询',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            	{
	            	id : "tree15",
	                text: '基于配置项的反向复杂查询',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });     
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree21",
	                text: '缺少必要关系的配置项查询',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            ]
            });  

        	//--------
	       	var tempJsp =webContext+ "/reportJsp/showReport.jsp?raq=/configItem.raq";
	       	//var menu=this.menu;
			if (node.id == "tree01") {
				tempJsp =webContext+"/reportJsp/showReport.jsp?raq=/configItem.raq";
			} else if (node.id == "tree02") {
				tempJsp = webContext+"/reportJsp/showReport.jsp?raq=/relationTypeInfoReport.raq";
			} else if (node.id == "tree03") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/noRelationTypeConfigItemReport.raq";
			} else if (node.id == "tree11") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/cIBatchModifyReport.raq";
			} else if (node.id == "tree12") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/eventConfigItemReport.raq";
			} else if (node.id == "tree13") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/problemConfigItemReport.raq";
			} else if (node.id == "tree14") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/requireConfigItemReport.raq";
			} else if (node.id == "tree15") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/reverseConfigItemReport.raq";
			}else if (node.id == "tree21") {
				tempJsp = "/reportJsp/showReport.jsp?raq=/configItemNecessaryRelation.raq";
			}
			var item = {
				xtype : "panel",
				autoScroll : true,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '配置查询',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '变更查询',
		                menu: menu1         // <-- Add the action directly to a menu
		            
		            },{                   // <-- Add the action directly to a toolbar
		                text: '必要关系查询',
		                menu: menu2        // <-- Add the action directly to a menu
		            }
		        ],
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ tempJsp,
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				}
			};
			Ext.getCmp("PageTemplates").remove('menuPanel');
			Ext.getCmp("PageTemplates").add(item);
			Ext.getCmp("PageTemplates").doLayout();
	    }
		var item = {
			xtype : "panel",
			autoScroll : true,
			id : "menuPanel",// 注意这要和上面的保持一致性
			tbar: [{                   // <-- Add the action directly to a toolbar
	                text: '配置查询',
	                menu: menu         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
	                text: '变更查询',
	                menu: menu1         // <-- Add the action directly to a menu
		        },{                   // <-- Add the action directly to a toolbar
		           	text: '必要关系查询',
		            menu: menu2        // <-- Add the action directly to a menu
		         }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/reportJsp/showReport.jsp?raq=/configItem.raq",
				text : "页面正在加载中......",
				method : 'post',
				scope : this
			}
		};

		var items = new Array();
		//items.push( this.tree);
		items.push(item);
		return items;
	},
	initComponent : function() {
		this.items = this.panelNode();
		PagePanel.superclass.initComponent.call(this);
	}
})