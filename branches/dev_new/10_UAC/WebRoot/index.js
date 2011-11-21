/**
 * Index Component
 */
com.dc.ui.IndexPage = {
	init : function(isAdmin, webContext, copyRight) {
	
		this.buildTools(isAdmin, webContext);
		var menu = this.buildMenu(webContext);
		var sysMenu = new Ext.tree.TreePanel({
				id : 'sysMenu',
				title : '系统菜单',
				border : false,
				iconCls : 'nav',
				useArrows: true,
			    autoScroll: true,
			    animate: true,
			    enableDD: true,
			    containerScroll: true,
			    border: false,
			    // auto create TreeLoader
			    dataUrl: webContext + '/menu/menujs.js',
			    rootVisible : false,
				root: {
			        nodeType: 'async',
			        text: 'Ext JS',
			        draggable: false,
			        id: 'source'
			    },
			    listeners : {
					"click" : function(node,event) {
						//alert(Ext.encode(node.attributes.url));
						updateTab(node.id, node.text, node.attributes.url);
					}
				}
			});
		var helpInfo = new Ext.Panel({
			title : '联系我们',
			border : false,
			iconCls : 'nav',
			autoScroll : true,
			html:'请联系信息化系统部运维中心'
		});
		menu.push(sysMenu);	
		menu.push(helpInfo);	
		if(currentUser=='admin'){
			//显示按钮
		    Ext.getCmp("btnUserSelect").setVisible(true);
		    Ext.getCmp("btnUserAdd").setVisible(true);
		    Ext.getCmp("btnUserDel").setVisible(true);
		    //uid置为非只读
		    Ext.getCmp("userUid").setReadOnly(false);
			mainPanel = new Ext.TabPanel({
			 	id:'sysmainpanel',
				activeTab:0,
		        region:'center',
		        enableTabScroll:true,
		        defaults: {autoScroll:true},
		        items:[/*{
		        	title:'首页',
	 				layout:'anchor',
					iconCls : 'tabs',
		            items:[]
		        },
		        userPanel,
		        deptPanel,
		        userImpPanel*/],
		        plugins: new Ext.ux.TabCloseMenu()
		    });
		}else{		
			//隐藏按钮
		    Ext.getCmp("btnUserSelect").setVisible(false);
		    Ext.getCmp("btnUserAdd").setVisible(false);
		    Ext.getCmp("btnUserDel").setVisible(false);
		    //uid置为readonly
		    Ext.getCmp("userUid").setReadOnly(true);
			mainPanel = new Ext.TabPanel({
			 	id:'sysmainpanel',
				activeTab:0,
		        region:'center',
		        enableTabScroll:true,
		        defaults: {autoScroll:true},
		        items:[/*{
		        	title:'首页',
	 				layout:'anchor',
					iconCls : 'tabs',
		            items:[userPanel]
		        },userPanel*/],
		        plugins: new Ext.ux.TabCloseMenu()
		    });		
		    
		    mainPanel.setActiveTab(userPanel);
		}
		
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
				title : '统一用户管理系统',
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
				bbar:[{xtype: 'tbtext',text:""}],
				items: mainPanel
				})]
		});
		viewport.doLayout();
		//初始化数据
	    Ext.Ajax.request({  
	        url: webContext + '/user?methodCall=getDetailByUid', 
	        params : {
	        	uid : currentUser
	        },
	        async: false,
	        method: 'POST',
	        success: function(response, opts) {
	        	obj = Ext.util.JSON.decode(response.responseText);
	        	if(obj.success){
	        		Ext.getCmp("userPanel").form.setValues(obj);
	        		Ext.getCmp("uidCopy").setValue(obj.uid);
	        		Ext.getCmp("departmentNumber1").setValue(obj.deptNo);
	        		Ext.getCmp("departmentNumber1").setRawValue(obj.deptName);
	        		if(currentUser!='admin'){
	        			Ext.getCmp("userTypeCombo").setReadOnly(true);
	        		}
				}			         	
	        },
	        failure: function(response, opts) {
	         	Ext.Msg.alert("提示", "DN为：" + dn + "的用户信息获取失败！");
	        }   
	    });
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
				}]
			});
		}	   		 		
		function logout() {
			Ext.MessageBox.confirm("请确认", "您真的要退出系统吗?", function(button, text) {
				if (button == "yes") {
					window.location.href = webContext + '/logout.jsp';					
				}
			});
		}
		return toolbar;
	},
	
	buildMenu : function(webContext) {
		//var panels = [];
		var obj;
		Ext.Ajax.request({  
	        url: webContext + '/menu/loadTree?methodCall=rootLevel',  
	        async: false,  
	        success: function(response, opts) {
	         	obj = Ext.util.JSON.decode(response.responseText).data;
	         	
	         	
	        },
	        failure: function(response, opts) {
	         	obj = Ext.util.JSON.decode(response.responseText);
	         	
	         	
	        }   
	    }); 
		//if (obj.success == false) {
		//	Ext.MessageBox.alert('提示信息', '您没有可以显示的菜单，请联系部门管理员', toLogin);
		//}
		// 导航菜单面板组
		//var panels = [];
		for (var i = 0; i < obj.length; i++) {
			var menuItemId = obj[i].id;
			var menuText = obj[i].text;
			
			var loader = new Ext.tree.TreeLoader({
				url : webContext + '/menu/loadTree?methodCall=childLevel'
			});
			loader.on('beforeload', function(treeloader, node) {
				treeloader.baseParams = {
					//userId : currentUser,
					parentDN : node.id,
					method : 'tree'
				};
			});
			var item = new Ext.Panel({
				id : 'panel' + i,
				title : obj[i].text, // 产品中心(导航面板标题)
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
					autoScroll : true,
					listeners : {
						'click' : function(node, e ){
							//if(currentUser!='admin') return;
							var dn = node.id;
							//判断是部门还是人员
							if(dn.indexOf('ou=orgnizations')!=-1){
								if(currentUser!='admin'){
									return;
								}
								if(mainPanel.getActiveTab().id!='deptPanel'){
									//mainPanel.setActiveTab('deptPanel');
									mainPanel.setActiveTab(Ext.getCmp("deptPanel"));
								}
								var formValues = {};
								formValues.dn = node.id;
								formValues.deptNo = node.id.substring(node.id.indexOf('=')+1,node.id.indexOf(','));
								formValues.deptDesc = node.text;						
								Ext.getCmp("deptPanel").form.setValues(formValues);
							}
							if(dn.indexOf('u=users')!=-1&&dn.indexOf('uid=')!=-1){
								if(mainPanel.getActiveTab().id!='userPanel'){
									//mainPanel.setActiveTab('userPanel');
									mainPanel.setActiveTab(Ext.getCmp("userPanel"));
								}
								var formValues = {};
								Ext.Ajax.request({  
							        url: webContext + '/user?methodCall=getDetail', 
							        params : {
							        	dn : dn
							        },
							        async: false,
							        method: 'POST',
							        success: function(response, opts) {	
							        	obj = Ext.util.JSON.decode(response.responseText);
							        	if(obj.success){
							        		Ext.getCmp("userPanel").form.setValues(obj);
							        		Ext.getCmp("uidCopy").setValue(obj.uid);				        		
							        		Ext.getCmp("departmentNumber1").setValue(obj.deptNo);
							        		Ext.getCmp("departmentNumber1").setRawValue(obj.deptName);
							        									        		
							        		//如果非管理员查看非本人信息的话，隐藏修改按钮
							        		if(currentUser!='admin'){
							        			Ext.getCmp("userTypeCombo").setReadOnly(true);
							        			Ext.getCmp("departmentNumber1").setReadOnly(true);
							        			if(obj.uid != currentUser){
							        				Ext.getCmp("btnUserModify").setVisible(false);
							        				Ext.getCmp("userPanel").setTitle(obj.uid + "基本信息");
							        			}else{							        				
								        			Ext.getCmp("userPanel").setTitle("个人基本信息");
								        			Ext.getCmp("btnUserModify").setVisible(true);
							        			}
							        		}
										}			         	
							        },
							        failure: function(response, opts) {
							         	Ext.Msg.alert("提示", "DN为：" + dn + "的用户信息获取失败！");
							        }   
							    });						
								Ext.getCmp("deptPanel").form.setValues(formValues);
							}
							
						}
					}
				})
			});
			//panels.push(item);
		}
		return panels;
	}	
	
}
