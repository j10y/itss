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
	            	id : "tree1",
	                text: '�����������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '��������������',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            {
	            	id : "tree3",
	                text: '�����б�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '�������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: 'IT����',
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
	                text: '�����������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '��������������',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            {
	            	id : "tree3",
	                text: '�����б�',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: '�������',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: 'IT����',
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
				//title : '��ѯ���',
				autoScroll : true,
				columnWidth : 1.00,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '�����ѯ',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '��Ŀͳ��',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
		        ],
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ tempJsp,
					text : "ҳ�����ڼ�����......",
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
			//title : '��ѯ���',
			columnWidth : 1.00,
			autoScroll : true,
			id : "menuPanel",// ע����Ҫ������ı���һ����
			tbar: [{                   // <-- Add the action directly to a toolbar
	                text: '�����ѯ',
	                menu: menu         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
		                text: '��Ŀͳ��',
		                menu: menu1         // <-- Add the action directly to a menu
		            }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/user/require/report/requireApplyMySelfReport.jsp",
				text : "ҳ�����ڼ�����......",
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
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		this.items = this.panelNode();
		PagePanel.superclass.initComponent.call(this);
	}
})