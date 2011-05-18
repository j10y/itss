/**
 * Index Component
 */
com.dc.ui.IndexPage = {
	init : function(isAdmin, webContext, copyRight) {

		this.buildTools(isAdmin, webContext);
		var menu = this.buildMenu(webContext);
		
		var helpInfo = new Ext.Panel({
				title : '��ϵ����',
				border : false,
				iconCls : 'nav',
				autoScroll : true,
				html:'IT�������Ա������(liuhuip/6106-5388)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���㷢(fanhf/6106-5280)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���(wennan/6106-7604)<br> �ֻ����ʺţ���Ȼ��maran/6106-5093��<br>�������ʺţ������ң�liuqz/6106-7966��<br>BI���ʺţ�7888-4  <br> EL/EB���˺ţ�7888-5<br>HRS���˺ţ��櫣�lidy/6106-7931��<br> SCM���˺ţ������棨caowf/6100-3385��<br> �����˺������⣺7888-1'
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
				title : 'IT����ϵͳ',
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
				bbar:[{xtype: 'tbtext',text:"IT��������:7888&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IT��������:it@DC&nbsp;&nbsp;&nbsp;&nbsp;ITͶ�߽���:7888-0&nbsp;&nbsp;&nbsp;ITͶ�߽�������:it-manage@DC"}],
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
					text : "ע��",
					handler : logout
				},"-", "-","-",{
					text : "DCone",
					handler : toDCone
				},"-", {
					text : "ITcenter",
					handler : toITService
				},"-", {
					text : "��������",
					handler : toFlowCenter
				},"-", {
					text : "ҵ��Ӧ��",
					handler : toOperationApp
				},
				"-",{
					text : "�����̨",
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
					text : "ע��",
					handler : logout
				},"-", "-","-",{ 
					text : "DCone",
					handler : toDCone
				},"-", {
					text : "IT����ר��",
					handler : toITService
				},"-", {
					text : "��������",
					handler : toFlowCenter
				},"-", {
					text : "ҵ��Ӧ��",
					handler : toOperationApp
				}
				]
			});
		}
		var suggestioBox = new Ext.Window({
				id:'suggestioBox',
		    	title:"���&������",
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
		        html:'<div class="itil-sugBox-content">��л��ʹ�������ṩ��IT���񣬲������ǵķ����������ͽ��飡</div>'
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
			Ext.MessageBox.confirm("��ȷ��", "�����Ҫ�˳�ϵͳ��?", function(button, text) {
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
			Ext.MessageBox.alert('��ʾ��Ϣ', '��û�п�����ʾ�Ĳ˵�������ϵ���Ź���Ա', toLogin);
		}
		// �����˵������
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
				//contentEl : 'west_' + i,�벻ͨ�����˾�ֻ�ܳ�8�������˾���
				title : obj[i].text, // ��Ʒ����(����������)
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

