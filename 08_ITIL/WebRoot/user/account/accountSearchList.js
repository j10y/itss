PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'fit',
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	panelNode : function() {
		  var menu = new Ext.menu.Menu({
	        id: 'mainMenu',
	        items: [
	            {
	            	id : "tree1",
	                text: '�ʼ��ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '���ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree3",
	                text: 'MSN�ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: 'WWW�ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: 'Զ�̽����ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: 'BI�ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            {
	            	id : "tree8",
	                text: '�ʺ������ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree9",
	                text: '�ʺű����ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            */{
	            	id : "tree10",
	                text: '�ʺ�ɾ�������ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '�ʺ�����ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            	{
	            	id : "tree12",
	                text: '��������IT�ʺ�',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree7",
	                text: '�ʺ��嵥��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '���ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree14",
	                text: '�ʼ��ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree15",
	                text: 'ERP�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree16",
	                text: 'WWW�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: 'MSN�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree18",
	                text: 'E-Bridge�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree19",
	                text: 'Զ�̽����ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: 'HRS�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: 'E-Logistics�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree22",
	                text: 'SCM�ʺ��ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree23",
	                text: 'PushMail�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree24",
	                text: 'BI�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
//	            	{
//	            	id : "tree26",
//	                text: '�ֻ��嵥',
//	                iconCls:'search',
//	                handler:onItemClick
//	            },
	            	{
	            	id : "tree27",
	                text: '�ʼ�ת��',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree31",
	                text: 'www�ʺ�ɾ���嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree33",
	                text: 'msn�ʺ�ɾ���嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree34",
	                text: 'Զ�̽����ʺŵ����嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree36",
	                text: 'Traveler�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            */{
	            	id : "tree37",
	                text: 'Win7�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            ]
            });
            var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [{
	            	id : "tree25",
	                text: '�����嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }
	        /*,
	            {
	            	id : "tree30",
	                text: '������ȱ���嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            
	            	{
	            	id : "tree28",
	                text: '�ֻ���ȼ����������嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '�ֻ�����嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            */]
            });
            var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [{
	            	id : "tree32",
	                text: 'ͨѶ¼',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [{
	            	id : "tree35",
	                text: 'Win7�ʺ�',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu6 = new Ext.menu.Menu({
	        id: 'mainMenu6',
	        items: [{
	            	id : "tree38",
	                text: '�ʺ��ʼ�����',
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
	                text: '�ʼ��ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '���ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree3",
	                text: 'MSN�ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: 'WWW�ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: 'Զ�̽����ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: 'BI�ʺ�ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            */{
	            	id : "tree8",
	                text: '�ʺ������ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree9",
	                text: '�ʺű����ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            {
	            	id : "tree10",
	                text: '�ʺ�ɾ�������ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '�ʺ�����ͳ��',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            ]
            });
             var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            	{
	            	id : "tree12",
	                text: '��������IT�ʺ�',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree7",
	                text: '�ʺ��嵥��ѯ',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '���ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree14",
	                text: '�ʼ��ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree15",
	                text: 'ERP�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree16",
	                text: 'WWW�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: 'MSN�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree18",
	                text: 'E-Bridge�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree19",
	                text: 'Զ�̽����ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: 'HRS�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: 'E-Logistics�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree22",
	                text: 'SCM�ʺ��ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree23",
	                text: 'PushMail�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree24",
	                text: 'BI�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
//	            	{
//	            	id : "tree26",
//	                text: '�ֻ��嵥',
//	                iconCls:'search',
//	                handler:onItemClick
//	            },
	            	{
	            	id : "tree27",
	                text: '�ʼ�ת��',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*
	            {
	            	id : "tree31",
	                text: 'www�ʺ�ɾ���嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree33",
	                text: 'msn�ʺ�ɾ���嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree34",
	                text: 'Զ�̽����ʺŵ����嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree36",
	                text: 'Traveler�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            {
	            	id : "tree37",
	                text: 'Win7�ʺ��嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
             var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [{
	            	id : "tree25",
	                text: '�����嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }
	        /*,
	             {
	            	id : "tree30",
	                text: '������ȱ���嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree28",
	                text: '�ֻ���ȼ����������嵥',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '�ֻ�����嵥',
	                iconCls:'search',
	                handler:onItemClick
	            }*/]
            });
             var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [{
	            	id : "tree32",
	                text: 'ͨѶ¼',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [{
	            	id : "tree35",
	                text: 'Win7�ʺ�',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu6 = new Ext.menu.Menu({
	        id: 'mainMenu6',
	        items: [{
	            	id : "tree38",
	                text: '�ʺ��ʼ�����',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
        	//--------
	       	var tempJsp = "/user/account/report/emailAccountReport.jsp";
	       	//var menu=this.menu;
			if (node.id == "tree1") {
				tempJsp ="/user/account/report/emailAccountReport.jsp";
			} else if (node.id == "tree2") {
				tempJsp = "/user/account/report/domainAccountReport.jsp";
			} else if (node.id == "tree3") {
				tempJsp = "/user/account/report/msnAccountReport.jsp";
			} else if (node.id == "tree4") {
				tempJsp = "/user/account/report/wwwAccountReport.jsp";
			} else if (node.id == "tree5") {
				tempJsp = "/user/account/report/remoteAccessAccountReport.jsp";
			} else if (node.id == "tree6") {
				tempJsp = "/user/account/report/biAccountReport.jsp";
			} else if (node.id == "tree7") {
				tempJsp = "/user/account/report/accountListReport.jsp";
			} else if (node.id == "tree8") {
				tempJsp = "/user/account/report/accountApplyReport.jsp";
			} else if (node.id == "tree9") {
				tempJsp = "/user/account/report/accountChangeReport.jsp";
			} else if (node.id == "tree10") {
				tempJsp = "/user/account/report/accountDeleteReport.jsp";
			} else if (node.id == "tree11") {
				tempJsp = "/user/account/report/accountAllReport.jsp";
			}else if (node.id == "tree12") {
				tempJsp = "/user/account/report/importAccountData.jsp";
			}else if (node.id == "tree13") {
				tempJsp = "/user/account/report/domainAccountListReport.jsp";
			}
			else if (node.id == "tree14") {
				tempJsp = "/user/account/report/mailAccountListReport.jsp";
			}
			else if (node.id == "tree15") {
				tempJsp = "/user/account/report/erpAccountListReport.jsp";
			}else if (node.id == "tree16") {
				tempJsp = "/user/account/report/wwwAccountListReport.jsp";
			}
			else if (node.id == "tree17") {
				tempJsp = "/user/account/report/msnAccountListReport.jsp";
			}
			else if (node.id == "tree18") {
				tempJsp = "/user/account/report/eBridgeAccountListReport.jsp";
			}else if (node.id == "tree19") {
				tempJsp = "/user/account/report/vpnAccountListReport.jsp";
			}else if (node.id == "tree20") {
				tempJsp = "/user/account/report/hrsAccountListReport.jsp";
			}else if (node.id == "tree21") {
				tempJsp = "/user/account/report/ELAccountListReport.jsp";
			}else if (node.id == "tree22") {
				tempJsp = "/user/account/report/scmAccountListReport.jsp";
			}else if (node.id == "tree23") {
				tempJsp = "/user/account/report/pushMailAccountListReport.jsp";
			}else if (node.id == "tree24") {
				tempJsp = "/user/account/report/biAccountListReport.jsp";
			}else if (node.id == "tree25") {
				tempJsp = "/user/account/report/TelephoneAccountListReport.jsp";
			}else if (node.id == "tree26") {
				tempJsp = "/user/account/report/mobyAccountListReport.jsp";
			}else if (node.id == "tree27") {
				tempJsp = "/user/account/report/mailForwordApply.jsp";
			}else if (node.id == "tree28") {
				tempJsp = "/user/account/report/mobyAccountForApply.jsp";
			}else if (node.id == "tree29") {
				tempJsp = "/user/account/report/mobyAccountForChange.jsp";
			}else if (node.id == "tree30") {
				tempJsp = "/user/account/report/telephoneMoneyChange.jsp";
			}else if (node.id == "tree31") {
				tempJsp = "/user/account/report/wwwDeleteReport.jsp";
			}else if (node.id == "tree32") {
				tempJsp = "/user/account/report/dccontacts.jsp";
			}else if (node.id == "tree33") {
				tempJsp = "/user/account/report/msnDeleteReport.jsp";
			}else if (node.id == "tree34") {
				tempJsp = "/user/account/report/vpnAccountExpiresReport.jsp";
			}else if (node.id == "tree35") {
				tempJsp = "/user/account/Win7manage/win7Manage.jsp";
			}else if (node.id == "tree36") {
				tempJsp = "/user/account/report/TravelerAccountListReport.jsp";
			}else if (node.id == "tree37") {
				tempJsp = "/user/account/report/Win7AccountListReport.jsp";
			}else if (node.id == "tree38") {
				tempJsp = "/user/account/mailSendAgain.jsp";
			}
			var item = {
				xtype : "panel",
				//title : '��ѯ���',
				autoScroll : true,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '�ʺŲ�ѯ',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�ʺ���������',
		                menu: menu1         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�ʺ��嵥��ѯ',
		                menu: menu2        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�绰�嵥��ѯ',
		                menu: menu3        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: 'ͨѶ¼',
		                menu: menu4        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: 'Win7�ʺ�',
		                menu: menu5        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�ʺ��ʼ�����',
		                menu: menu6       // <-- Add the action directly to a menu
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
			//title : '��ѯ���',
			autoScroll : true,
			id : "menuPanel",// ע����Ҫ������ı���һ����
			tbar: [{                   // <-- Add the action directly to a toolbar
	                text: '�ʺŲ�ѯ',
	                menu: menu         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
	                text: '�ʺ���������',
	                menu: menu1         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
	                text: '�ʺ��嵥��ѯ',
	                menu: menu2        // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
		                text: '�绰�嵥��ѯ',
		                menu: menu3        // <-- Add the action directly to a menu
		        },{                   // <-- Add the action directly to a toolbar
		                text: 'ͨѶ¼',
		                menu: menu4        // <-- Add the action directly to a menu
		        },{                   // <-- Add the action directly to a toolbar
		                text: 'Win7�ʺ�',
		                menu: menu5        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '�ʺ��ʼ�����',
		                menu: menu6        // <-- Add the action directly to a menu
		            }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/user/account/report/emailAccountReport.jsp",
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

	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		this.items = this.panelNode();
		PagePanel.superclass.initComponent.call(this);
	}
})