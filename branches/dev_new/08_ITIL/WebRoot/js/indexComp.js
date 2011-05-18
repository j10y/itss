/**
 * Index Component
 */
com.dc.ui.IndexPage = {
	init : function(isAdmin, webContext, copyRight) {

		this.buildTools(isAdmin, webContext);
		var menu = this.buildMenu(webContext);
		
		var helpInfo = new Ext.Panel({
				title : '联系我们',
				border : false,
				iconCls : 'nav',
				autoScroll : true,
				html:'IT需求管理员：刘辉(liuhuip/6106-5388)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;樊恒发(fanhf/6106-5280)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文楠(wennan/6106-7604)<br> 手机类帐号：马然（maran/6106-5093）<br>座机类帐号：刘庆忠（liuqz/6106-7966）<br>BI类帐号：7888-4  <br> EL/EB类账号：7888-5<br>HRS类账号：李东娅（lidy/6106-7931）<br> SCM类账号：曹卫锋（caowf/6100-3385）<br> 其他账号类问题：7888-1'
			});
		menu.push(helpInfo);
			
//		var buttom = com.faceye.ui.BottomLayout.init(copyRight);
		var viewport = new Ext.Viewport({
			id:'mainViewport',
			layout : 'border',
			items : [new Ext.BoxComponent({
				region : 'north',
				el : 'north',
				height : 'auto'
			}),{
				region : 'west',
				id : 'west-panel',
				title : 'IT服务系统',
				cls : "x-btn-text-icon",
				split : true,
				iconCls : 'forward',
				width : 300,
				minSize : 175,
				maxSize : 400,
				collapsible : true,
				margins : '0 0 0 3',
				layout : 'accordion',
				layoutConfig : {
					animate : true
				},        
				
				items : menu
			},new Ext.Panel({
				region : 'center',
				layout:'fit',
				bbar:[{xtype: 'tbtext',text:"IT服务热线:7888&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IT服务邮箱:it@DC&nbsp;&nbsp;&nbsp;&nbsp;IT投诉建议:7888-0&nbsp;&nbsp;&nbsp;IT投诉建议邮箱:it-manage@DC"}],
				items:mainPanel
				})]
		});
		viewport.doLayout();
	},

	buildTools : function(isAdmin, webContext) {
		var toolbar;
		if (isAdmin == 'true') {
			toolbar = new Ext.Toolbar({
				id:'tools_Bywanghe',
				renderTo : 'down',
				cls:'x-toolbar-whDefine',
				items : ["->", new Ext.Toolbar.TextItem(userName),
				"-", {
					text : "注销",
					handler : logout
				},"-", "-","-",{
					text : "DCone",
					handler : toDCone
				},"-", {
					text : "ITcenter",
					handler : toITService
				},"-", {
					text : "流程中心",
					handler : toFlowCenter
				},"-", {
					text : "业务应用",
					handler : toOperationApp
				},
				"-",{
					text : "进入后台",
					handler : redirctAdmin
				}]
			});
		} else {
			toolbar = new Ext.Toolbar({
				id:'tools_Bywanghe',
				renderTo : 'down',
				cls:'x-toolbar-whDefine',
				items :["->", new Ext.Toolbar.TextItem(userName),
				"-", {
					text : "注销",
					handler : logout
				},"-", "-","-",{ 
					text : "DCone",
					handler : toDCone
				},"-", {
					text : "IT服务专栏",
					handler : toITService
				},"-", {
					text : "流程中心",
					handler : toFlowCenter
				},"-", {
					text : "业务应用",
					handler : toOperationApp
				}
				]
			});
		}
		var suggestioBox = new Ext.Window({
				id:'suggestioBox',
		    	title:"意见&建议箱",
				width:460,
				height:160,
				bodyStyle:'background-color:#ffffff;',
				maximizable:false,
		        animateTarget:'up',
		        animCollapse: true,
		        layout: 'fit',
		        resizable:false,
		        frame:true,
		        modal:true,
		        closeAction:'hide',
		        html:'<div class="itil-sugBox-content">感谢您使用我们提供的IT服务，并对我们的服务提出意见和建议！</div>'
	   		 });
		function toDCone() {
			window.open("http://dcone.zsgj.com","_blank");
		}
		function toITService() {
			window.open("http://itss.zsgj.com/ITcenter","_blank");
		}
		function toFlowCenter() {
			window.open("http://10.1.120.248/column/FY09Aris/workflow.html","_blank");
		}
		function toOperationApp() {
			window.open("http://10.1.120.248/dconelog/dc/apps/before_login/default.htm","_blank");
		}
		function toSuggestionBox() {
			suggestioBox.show();
		}
		
		function logout() {
			Ext.MessageBox.confirm("请确认", "您真的要退出系统吗?", function(button, text) {
				if (button == "yes") {
					window.location.href = webContext + '/j_logout.do';
				}
			});
		}
		function redirctAdmin() {
			toadmin = true;
			window.location.href = webContext + '/infoAdmin/main.jsp';
		}
		return toolbar;
	},

	buildMenu : function(webContext) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("POST", webContext + '/servlet/loadNavigateTitle?userId='
				+ userId, false);
		conn.send(null);

		var obj = eval(conn.responseText);
		var result = conn.responseText;
		if (result == "{success:false}") {
			Ext.MessageBox.alert('提示信息', '您没有可以显示的菜单，请联系部门管理员', toLogin);
		}
		// 导航菜单面板组
		var panels = [];
		for (var i = 0; i < obj.length; i++) {
			var menuItemId = obj[i].id;
			var menuText = obj[i].text;
			

			var loader = new Ext.tree.TreeLoader({
				url : webContext + '/servlet/loadMenuNodes'
			});
			loader.on('beforeload', function(treeloader, node) {	
				treeloader.baseParams = {
					userId : userId,
					id : node.id,
					method : 'tree'
				};
			});
			var item = new Ext.Panel({
				id : 'panel' + i,
				//contentEl : 'west_' + i,想不通，加了就只能出8个，多了就乱
				title : obj[i].text, // 产品中心(导航面板标题)
				border : false,
				iconCls : 'nav',
				autoScroll : true,
				items : new Ext.tree.TreePanel({
					id : "west_" + i,
					animate : true,
					containerScroll : true,
					region : "west_" + i,
					collapsible : true,
					overflow : 'auto',
					loader : loader,
					containerScroll : true,
					root : new Ext.tree.AsyncTreeNode({
						id : menuItemId,
						text : menuText,
						expanded : true
					}),
					bodyBorder : false,
					autoScroll : true
				})
					// ,
					// cls: "x-btn-text-icon"
					// collapsible: true,
					// animate: true
			});

			panels.push(item);
		}
		return panels;
	}
}

