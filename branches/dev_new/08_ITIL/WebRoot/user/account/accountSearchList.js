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
	                text: '邮件帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '域帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree3",
	                text: 'MSN帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: 'WWW帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: '远程接入帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: 'BI帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            {
	            	id : "tree8",
	                text: '帐号申请查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree9",
	                text: '帐号变更查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            */{
	            	id : "tree10",
	                text: '帐号删除申请查询',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '帐号总数统计',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu1 = new Ext.menu.Menu({
	        id: 'mainMenu1',
	        items: [
	            	{
	            	id : "tree12",
	                text: '批量导入IT帐号',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree7",
	                text: '帐号清单查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '域帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree14",
	                text: '邮件帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree15",
	                text: 'ERP帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree16",
	                text: 'WWW帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: 'MSN帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree18",
	                text: 'E-Bridge帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree19",
	                text: '远程接入帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: 'HRS帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: 'E-Logistics帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree22",
	                text: 'SCM帐号帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree23",
	                text: 'PushMail帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree24",
	                text: 'BI帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
//	            	{
//	            	id : "tree26",
//	                text: '手机清单',
//	                iconCls:'search',
//	                handler:onItemClick
//	            },
	            	{
	            	id : "tree27",
	                text: '邮件转发',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree31",
	                text: 'www帐号删除清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree33",
	                text: 'msn帐号删除清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree34",
	                text: '远程接入帐号到期清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree36",
	                text: 'Traveler帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            */{
	            	id : "tree37",
	                text: 'Win7帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            ]
            });
            var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [{
	            	id : "tree25",
	                text: '座机清单',
	                iconCls:'search',
	                handler:onItemClick
	            }
	        /*,
	            {
	            	id : "tree30",
	                text: '座机额度变更清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            
	            	{
	            	id : "tree28",
	                text: '手机额度及代缴申请清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '手机变更清单',
	                iconCls:'search',
	                handler:onItemClick
	            }
	            */]
            });
            var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [{
	            	id : "tree32",
	                text: '通讯录',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [{
	            	id : "tree35",
	                text: 'Win7帐号',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu6 = new Ext.menu.Menu({
	        id: 'mainMenu6',
	        items: [{
	            	id : "tree38",
	                text: '帐号邮件补发',
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
	                text: '邮件帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree2",
	                text: '域帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree3",
	                text: 'MSN帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree4",
	                text: 'WWW帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree5",
	                text: '远程接入帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree6",
	                text: 'BI帐号统计',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            */{
	            	id : "tree8",
	                text: '帐号申请查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree9",
	                text: '帐号变更查询',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            {
	            	id : "tree10",
	                text: '帐号删除申请查询',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree11",
	                text: '帐号总数统计',
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
	                text: '批量导入IT帐号',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
            var menu2 = new Ext.menu.Menu({
	        id: 'mainMenu2',
	        items: [
	            	{
	            	id : "tree7",
	                text: '帐号清单查询',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree13",
	                text: '域帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            	{
	            	id : "tree14",
	                text: '邮件帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*{
	            	id : "tree15",
	                text: 'ERP帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree16",
	                text: 'WWW帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree17",
	                text: 'MSN帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree18",
	                text: 'E-Bridge帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree19",
	                text: '远程接入帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree20",
	                text: 'HRS帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree21",
	                text: 'E-Logistics帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree22",
	                text: 'SCM帐号帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree23",
	                text: 'PushMail帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree24",
	                text: 'BI帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
//	            	{
//	            	id : "tree26",
//	                text: '手机清单',
//	                iconCls:'search',
//	                handler:onItemClick
//	            },
	            	{
	            	id : "tree27",
	                text: '邮件转发',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            /*
	            {
	            	id : "tree31",
	                text: 'www帐号删除清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree33",
	                text: 'msn帐号删除清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree34",
	                text: '远程接入帐号到期清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree36",
	                text: 'Traveler帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            },*/
	            {
	            	id : "tree37",
	                text: 'Win7帐号清单',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
             var menu3 = new Ext.menu.Menu({
	        id: 'mainMenu3',
	        items: [{
	            	id : "tree25",
	                text: '座机清单',
	                iconCls:'search',
	                handler:onItemClick
	            }
	        /*,
	             {
	            	id : "tree30",
	                text: '座机额度变更清单',
	                iconCls:'search',
	                handler:onItemClick
	            },
	            {
	            	id : "tree28",
	                text: '手机额度及代缴申请清单',
	                iconCls:'search',
	                handler:onItemClick
	            },{
	            	id : "tree29",
	                text: '手机变更清单',
	                iconCls:'search',
	                handler:onItemClick
	            }*/]
            });
             var menu4 = new Ext.menu.Menu({
	        id: 'mainMenu4',
	        items: [{
	            	id : "tree32",
	                text: '通讯录',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            
            var menu5 = new Ext.menu.Menu({
	        id: 'mainMenu5',
	        items: [{
	            	id : "tree35",
	                text: 'Win7帐号',
	                iconCls:'search',
	                handler:onItemClick
	            }]
            });
            var menu6 = new Ext.menu.Menu({
	        id: 'mainMenu6',
	        items: [{
	            	id : "tree38",
	                text: '帐号邮件补发',
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
				//title : '查询结果',
				autoScroll : true,
				id : "menuPanel",
				tbar: [{                   // <-- Add the action directly to a toolbar
		                text: '帐号查询',
		                menu: menu         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '帐号批量导入',
		                menu: menu1         // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '帐号清单查询',
		                menu: menu2        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '电话清单查询',
		                menu: menu3        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '通讯录',
		                menu: menu4        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: 'Win7帐号',
		                menu: menu5        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '帐号邮件补发',
		                menu: menu6       // <-- Add the action directly to a menu
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
			//title : '查询结果',
			autoScroll : true,
			id : "menuPanel",// 注意这要和上面的保持一致性
			tbar: [{                   // <-- Add the action directly to a toolbar
	                text: '帐号查询',
	                menu: menu         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
	                text: '帐号批量导入',
	                menu: menu1         // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
	                text: '帐号清单查询',
	                menu: menu2        // <-- Add the action directly to a menu
	            },{                   // <-- Add the action directly to a toolbar
		                text: '电话清单查询',
		                menu: menu3        // <-- Add the action directly to a menu
		        },{                   // <-- Add the action directly to a toolbar
		                text: '通讯录',
		                menu: menu4        // <-- Add the action directly to a menu
		        },{                   // <-- Add the action directly to a toolbar
		                text: 'Win7帐号',
		                menu: menu5        // <-- Add the action directly to a menu
		            },{                   // <-- Add the action directly to a toolbar
		                text: '帐号邮件补发',
		                menu: menu6        // <-- Add the action directly to a menu
		            }
	        ],
			autoLoad : {
				url : webContext + "/tabFrame.jsp?url=" + webContext
						+ "/user/account/report/emailAccountReport.jsp",
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

	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		this.items = this.panelNode();
		PagePanel.superclass.initComponent.call(this);
	}
})