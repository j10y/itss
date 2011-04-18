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
	                text: '����������Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree02",
	                text: '������ϵ��Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree03",
	                text: 'δ���ù�ϵ��������Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            	{
	            	id : "tree11",
	                text: '�����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree12",
	                text: '�����¼��ı����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '��������ı����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },	            	{
	            	id : "tree14",
	                text: '��������ı����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree15",
	                text: '����������ķ����Ӳ�ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            }); 
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree21",
	                text: 'ȱ�ٱ�Ҫ��ϵ���������ѯ',
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
	                text: '����������Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree02",
	                text: '������ϵ��Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree03",
	                text: 'δ���ù�ϵ��������Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            	{
	            	id : "tree11",
	                text: '�����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree12",
	                text: '�����¼��ı����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '��������ı����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },	            	{
	            	id : "tree14",
	                text: '��������ı����Ϣ��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree15",
	                text: '����������ķ����Ӳ�ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });     
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree21",
	                text: 'ȱ�ٱ�Ҫ��ϵ���������ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            ]
            });  

        	//--------
	       	var tempJsp = "/ReportServer?reportlet=/ConfigItem.cpt";
	       	//var menu=this.menu;
			if (node.id == "tree01") {
				tempJsp ="/ReportServer?reportlet=/ConfigItem.cpt";
			} else if (node.id == "tree02") {
				tempJsp = "/ReportServer?reportlet=/relationTypeInfoReport.cpt";
			} else if (node.id == "tree03") {
				tempJsp = "/ReportServer?reportlet=/noRelationTypeConfigItemReport.cpt";
			} else if (node.id == "tree11") {
				tempJsp = "/ReportServer?reportlet=/cIBatchModifyReport.cpt";
			} else if (node.id == "tree12") {
				tempJsp = "/ReportServer?reportlet=/eventConfigItemReport.cpt";
			} else if (node.id == "tree13") {
				tempJsp = "/ReportServer?reportlet=/problemConfigItemReport.cpt";
			} else if (node.id == "tree14") {
				tempJsp = "/ReportServer?reportlet=/requireConfigItemReport.cpt";
			} else if (node.id == "tree15") {
				tempJsp = "/ReportServer?reportlet=/reverseConfigItemReport.cpt";
			}else if (node.id == "tree21") {
				tempJsp = "/ReportServer?reportlet=/configItemNecessaryRelation.cpt";
			}
			var item = {
				xtype : "panel",
				autoScroll : true,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '���ò�ѯ',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�����ѯ',
		                menu: menu1         // <-- Add the action directly to a menu
		            
		            },{                   // <-- Add the action directly to a toolbar
		                text: '��Ҫ��ϵ��ѯ',
		                menu: menu2        // <-- Add the action directly to a menu
		            }
		        ],
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ tempJsp,
					text : "ҳ�����ڼ�����......",
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
			id : "menuPanel",// ע����Ҫ������ı���һ����
			tbar: [{                   // <-- Add the action directly to a toolbar
	                text: '���ò�ѯ',
	                menu: menu         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
	                text: '�����ѯ',
	                menu: menu1         // <-- Add the action directly to a menu
		        },{                   // <-- Add the action directly to a toolbar
		           	text: '��Ҫ��ϵ��ѯ',
		            menu: menu2        // <-- Add the action directly to a menu
		         }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/ReportServer?reportlet=/ConfigItem.cpt",
				text : "ҳ�����ڼ�����......",
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