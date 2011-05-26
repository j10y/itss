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
				html:''
			});
		menu.push(helpInfo);
		var myApply = new Ext.grid.GridPanel({
			title:'���ύ������',
			tools:[{id:'refresh',qtip:'ˢ��',handler:function(e,tooEl,panel){panel.getStore().reload()}}],
			id : "myApply",
			closable : true,
			autoScroll : true,
			width : 1020,
			height : 600,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			frame : true,
			columns : [ new Ext.grid.RowNumberer(), {header : "������",width : 100,sortable : true,dataIndex : 'applyNum'},
						{header : "��������",width : 100,sortable : true,dataIndex : 'applyName'},
						//{header : "������",width : 100,sortable : true,dataIndex : 'defname',hidden:true}, 
						{header : "��������",width : 100,sortable : true,dataIndex : 'defDesc'}, 
						//{header : "�ڵ���",width : 100,sortable : true,dataIndex : 'nodeName'}, 
						{header : "��ǰ��������",width : 100,sortable : true,dataIndex : 'taskName'}, 
						{header : "��ת����ǰ����ʱ��",width : 100,sortable : true,dataIndex : 'startDate'},
						{header : "���̿�ʼʱ��",width : 100,sortable : true,dataIndex : 'createDate'} ],
			store : new Ext.data.JsonStore({
				url: webContext+'/userSelfWorkflow_applyTasks.action?actorId='+userID,
				fields : ['applyNum','applyName','defname', 'defDesc', 'nodeName', 'taskName','startDate','createDate','applyId','applyType','taskId','serviceItemId','dataType','processId'],
				autoLoad : true,
				root : 'data'
			}),
			listeners:{
	        	rowdblclick:function(){
	        		var record = this.getSelectionModel().getSelected();
			    	var applyId = record.get("applyId");		    	
		        	var applyType = record.get('applyType');
		        	var tmpTaskName = record.get('taskName');
		        	var taskName = record.get('taskName');
		        	var taskId = record.get('taskId');
		        	var serviceItemId = record.get('serviceItemId');
		        	var dataType = record.get('dataType');
		        	var processId = record.get('processId');
		        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
					showMeThePage(applyId, applyType, serviceItemId, dataType,processId);
	        	}
	        }
		});
		var myAudit = new Ext.grid.GridPanel({
			title:'���Ҵ��������',
			tools:[{id:'refresh',qtip:'ˢ��',handler:function(e,tooEl,panel){panel.getStore().reload()}}],
			id : "myAudit",
			closable : true,
			autoScroll : true,
			width : 1020,
			height : 600,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			frame : true,
			columns : [new Ext.grid.RowNumberer(), {header : "������",width : 100,sortable : true,dataIndex : 'applyNum'},
						{header : "������",width : 100,sortable : true,dataIndex : 'applyUser'},
						{header : "��������",width : 100,sortable : true,dataIndex : 'applyName'},
						{header : "��������",width : 100,sortable : true,dataIndex : 'virDesc'}, 
						//{header : "�ڵ���",width : 100,sortable : true,dataIndex : 'nodeName'}, 
						{header : "��ǰ����",width : 100,sortable : true,dataIndex : 'taskName'}, 
						{header : "��ת����ǰ����ʱ��",width : 100,sortable : true,dataIndex : 'startDate'},
						{header : "���̿�ʼʱ��",width : 100,sortable : true,dataIndex : 'createDate'}],
			store : new Ext.data.JsonStore({
				url: webContext+'/userSelfWorkflow_auditTasks.action?actorId='+userID,
				fields : ['applyNum','applyUser','applyName','defname', 'virDesc', 'nodeName', 'taskName','startDate','createDate','applyId','applyType','taskId','serviceItemId','dataType'],
				autoLoad : true,
				root : 'data'
			}),
			listeners:{
	        	rowdblclick:function(){
	        		var record = this.getSelectionModel().getSelected();
			    	var applyId = record.get("applyId");	
		        	var applyType = record.get('applyType');//
		        	var tmpTaskName = record.get('taskName');//
		        	var taskName = record.get('taskName');//
		        	var taskId = record.get('taskId');//    	
		        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
	 				var reqClass = "";
		        	var param = ""+taskId+","+taskName+","+applyType+","+applyId+","+pageUrl;
	          		showAuditWindow(taskId,taskName,applyType,applyId,pageUrl,reqClass);
	        	}
	        }
		});
		 mainPanel = new Ext.TabPanel({
		 	id:'itssmainpanel',
			activeTab:0,
	        region:'center',
	        enableTabScroll:true,
	        defaults: {autoScroll:true},
	        items:[{
	        	title:'��ҳ',
 				layout:'anchor',
				iconCls : 'tabs',
	            items:[{
	                layout:'fit',
	                items:myAudit,
	                //height: '0.5',
	                anchor:'%100 50%',
	                split: true,
	                border:false,
	                region:'center'
	            }, {
	                layout:'fit',
	                items:myApply,
	                //height: '0.5',
	                anchor:'%100 50%',
	                split: true,
	                border:false,
	                region:'south'
	            }]
	        }],
	        plugins: new Ext.ux.TabCloseMenu()
	    });
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
				title : 'IT����֧��ϵͳ',
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
				bbar:[
				      //{xtype: 'tbtext',text:"IT��������:7888&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IT��������:it@DC&nbsp;&nbsp;&nbsp;&nbsp;ITͶ�߽���:7888-0&nbsp;&nbsp;&nbsp;ITͶ�߽�������:it-manage@DC"}
				      ],
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
				},
				//"-",{
				//	text : "DCone",
				//	handler : toDCone
				//},
				"-", {
					text : "ITcenter",
					handler : toITService
				},
				//"-", {
				//	text : "��������",
				//	handler : toFlowCenter
				//},
				//"-", {
				//	text : "ҵ��Ӧ��",
				//	handler : toOperationApp
				//},
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
				},
				//"-",{ 
				//	text : "DCone",
				//	handler : toDCone
				//},
				"-", {
					text : "IT����ר��",
					handler : toITService
				}
				//"-", {
				//	text : "��������",
				//	handler : toFlowCenter
				//},
				//"-", {
				//	text : "ҵ��Ӧ��",
				//	handler : toOperationApp
				//}
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
			window.open("/ITcenter","_blank");
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
					window.location.href = webContext + '/j_spring_security_logout';
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
		var obj;
		Ext.Ajax.request({  
	        url: webContext + '/menu_loadRootUserMenuItem.action?userId=' + userId,  
	        async: false,  
	        success: function(response, opts) {
	         	obj = Ext.util.JSON.decode(response.responseText);
	        }  
	    }); 
		if (obj.success == false) {
			Ext.MessageBox.alert('��ʾ��Ϣ', '��û�п�����ʾ�Ĳ˵�������ϵ���Ź���Ա', toLogin);
		}
		// �����˵������
		var panels = [];
		for (var i = 0; i < obj.length; i++) {
			var menuItemId = obj[i].id;
			var menuText = obj[i].text;
			var loader = new Ext.tree.TreeLoader({
				url : webContext + '/menu_loadUserMenuItem.action'
			});
			loader.on('beforeload', function(treeloader, node) {	
				treeloader.baseParams = {
					userId : userId,
					parentId : node.id,
					method : 'tree'
				};
			});
			var item = new Ext.Panel({
				id : 'panel' + i,
				title : obj[i].text, // ��Ʒ����(����������)
				border : false,
				iconCls : 'nav',
				autoScroll : true,
				items : new Ext.tree.TreePanel({
					id : "west_" + i,
					animate : true,
					containerScroll : true,
					region : "west_" + i,
					overflow : 'auto',
					loader : loader,
					containerScroll : true,
					rootVisible: false,
					root : new Ext.tree.AsyncTreeNode({
						id : menuItemId,
						text : menuText,
						expanded : true
					}),
					bodyBorder : false,
					autoScroll : true
				})
			});
			panels.push(item);
		}
		return panels;
	}
}
