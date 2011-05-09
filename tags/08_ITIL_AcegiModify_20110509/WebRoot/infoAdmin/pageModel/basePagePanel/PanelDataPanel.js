PanelDataPanel = Ext.extend(Ext.tree.TreePanel, {
	title: '页面面板字段',
	width: window.screen.availWidth/2-112,
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
	columnWidth:.5,
	lines: true,
	height:495,
	id:"tree",
	//autoHeight　: true,
	expandable:true,
	enableDD: true,   
    ddGroup: "tgDD",
    ddScroll:true,
    removeFlag: false,

    listeners: { 
        //当节点移动时触发事件
        movenode: function(tree, node, oldParent, newParent, index) {
				PanelManager.ajaxMoveNode(node.id, oldParent.id, newParent.id, index);
				node.reload();
        },		
		// 当节点删除时触发事件
//		remove:  function(tree, parentNode, node) {
//				if (this.removeFlag) {
//				   PanelManager.ajaxRemoveNode(node.id);
//				}
//		},
		contextmenu: function(node, e){
			var ppId = pagePanelId;
			 var order = node.indexOf(node.lastChild)+2;//从一开始排序
			if(node.id=="0"){
		            var menu;
		            var win;
		            if (!menu){
		                menu = new Ext.menu.Menu([{
		                    text : '创建FieldSet',
		                    iconCls:"add",
		                    handler : function(){
		                  			var nameField = {
					                		id : "name",
					                		xtype : "textfield",
					                		name : "name",
					                		fieldLabel : "fieldSet名称",
					                		allowBlank : false,
					                		selectOnFocus : true
					                	};
					                var editForm = new Ext.form.FormPanel({
					                		id : 'editPanel',
					                		layout: 'table',
											height : 150,
											width : 'auto',
											frame : true,
											layoutConfig: {columns: 2},	    		
											items:[
												{html: "fieldSet名称:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												nameField
												]
												
					                	});
					               if(!win){
					                	win = new Ext.Window({
											id : 'editWin',
											title : "fieldSet编辑窗口",
											width : 300,
											height : 170,
											maximizable : true,
											modal : true,
											items: editForm,
											buttons:[
													{
														xtype:'button',
														text:'保存',
									        			scope:this,
									        	 		handler:function(){
									        	 			var info = Ext.getCmp('editPanel').getForm().getValues();
									        	 			var title = unicode(info.name);
									        				Ext.Ajax.request({											
																url: webContext+'/pageModel/pagePanelManage.do?methodCall=addFieldSet',
																params : {
																	pagePanelId : ppId,
																	title : title,
																	index : order
																},
																method : 'post',
																//存储成功后调用的函数。
																success : function(response) {
																	Ext.getCmp("tree").root.reload();
																	win.close();
																	},
											                        scope:this
																});	
									        				}
													},
													{
														xtype:'button',
														text:'关闭',
									        			scope:this,
									        	 		handler:function(){
									        					win.close();
									        			}
								        			}
							        			]
					                	});
					               }
					               win.show();
		                   	 }
		               	 }]);
		           	  menu.showAt(e.getPoint()); 
		           }   
			}
		},
		checkchange:function(node, checked){
			var nodeId = node.id;
			if(checked == true){
				PanelManager.ajaxIsDisplayNode(nodeId, 1);
			}else{
				PanelManager.ajaxIsDisplayNode(nodeId, 0);
			}
		}
	},
    
	initComponent: function() {
		
		//创建此模板时需要传入参数text:树根结点的名称
		this.root = new Ext.tree.AsyncTreeNode({
			text: ppTitle,
			draggable: false,
			id: '0'
		}),
		
		//创建此模板时需要传入参数dataUrl:加载数据的url
		this.loader = new Ext.tree.TreeLoader({
			dataUrl:webContext+'/pageModel/pagePanelManage.do?methodCall=loadPagePanel'
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id,
						ppId : ppId
					};
		}, this);
				 
		PanelDataPanel.superclass.initComponent.call(this);
	}
});