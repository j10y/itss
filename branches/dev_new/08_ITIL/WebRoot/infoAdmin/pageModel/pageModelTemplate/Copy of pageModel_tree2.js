/*
 * 目标数据面板
 */
 
callBackFuns = function(result){
	if(!result){
		alert("您拖动了重复的值，请您重新拖动");
		Ext.getCmp("tree").marker='1';
		//alert(Ext.getCmp("tree").marker+'==============');
	 	return ;
	}else{
		Ext.getCmp("tree").marker='0'
	}
}
 
//目标节点，子节点，和选择的数据
var ddFunction = function(node, refNode, selections) { 
	
    for(var i = 0; i < selections.length; i ++) {
//    	alert(node.indexOf(node.lastChild));
//    	var order = node.indexOf(node.lastChild)+1;
    	
        var record = selections[i];//这个record表示表格选中的数据
        
        
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),             
            id: record.get('id'),   
            leaf: record.get('leaf')   
        }), refNode); 
        
       
         //模板
         var id = "";
 		 //模板行项目父节点ID
    	 var parentId = node.id;
 	 
         //表格数据id
 		 var pagePanelId = record.get('id');
         //表格数据名称
         var name = record.get('name');
         //表格数据是否是叶子
         var isLeaf = record.get('leaf');
         
         
         //页面模板id
         var pageModelId = modId;
         //数据条目是否来自主表(用叶子结点的cls属性来标志)
         //var flag = record.get('cls');
         //var nodeParent = refNode;
         //节点序号
         //alert()
         var order = node.indexOf(node.lastChild) ;//
     	 //alert(pagePanelId);
         
//     PageModelTemplateManager.ajaxTestRepeatePanel(parentId, pagePanelId, pageModelId,callBackFuns);   

     //同步保存到数据库   
     DWREngine.setAsync(false);
     PageModelTemplateManager.ajaxSavePageModelPanel(id, parentId, name, pagePanelId, pageModelId,order);
     DWREngine.setAsync(true);    
     //父节点加载数据
     node.reload();     
    }   
}
PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '配置关系树',
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
	columnWidth:.5,
	lines: true,
	height: 320,
	enableDD: true,   
    ddGroup: "tgDD", 
    removeFlag: false,
    listeners: { //拖拽之前会触发，这是针对表格到树拖拽的例子
       	
        beforenodedrop: function(dropEvent) {   
        	var node = dropEvent.target;    // 目标结点   
            var data = dropEvent.data;      // 拖拽的数据   
            var point = dropEvent.point;    // 拖拽到目标结点的位置     
            var mId = this.modId ;               					
		    
		    // 如果data.node为空，则不是tree本身的拖拽，而是从grid到tree的拖拽，需要处理   
            if(!data.node) {  
            	
            	for(var i = 0; i < data.selections.length; i++) {	 	    	    	
		        var record = data.selections[i];//这个record表示表格选中的数据
		        var pagePanelId = record.get('id');		        
		    }	
			    DWREngine.setAsync(false);
			    PageModelTemplateManager.ajaxTestRepeatePanel(node.id, pagePanelId, mId,callBackFuns);
	//			alert(this.marker);
				if(this.marker=='1'){
					return;
				}
				
                switch(point) {   
                    case 'append':   
                        // 添加时，目标结点为node，放到子结点为空的这种情况   
                    	alert(node.id);
                    	ddFunction(node, null, data.selections);
                        
                        //保存结点信息(templateItem:id,主表/扩展表Id,mainTableItemId,模板Id)
                        break;   
                    case 'above':   
                        // 插入到node之上，目标结点为node的parentNode，子结点为node   
                    	alert(node.id);
                        ddFunction(node.parentNode, node, data.selections);   
                        break;   
                    case 'below':   
                        // 插入到node之下，目标结点为node的parentNode，子结点为node的nextSibling   
                        ddFunction(node.parentNode, node.nextSibling, data.selections);
                        break;   
                }   
            }    
        },    
        
        //当节点移动时触发事件
        movenode: function(tree, node, oldParent, newParent, index) {
        		//alert(index);
        		DWREngine.setAsync(false);
	       		PageModelTemplateManager.ajaxMovePageModelPanel(tree.root.text,node.id, oldParent.id, newParent.id, index);
				DWREngine.setAsync(false);
		
        },		
		// 当节点删除时触发事件
//		remove:  function(tree, parentNode, node) {
//				if (this.removeFlag) {
//				   PageModelTemplateManager.ajaxDeletePageModelPanel(node.id);
//				}
//		},
		contextmenu: function(node, e){
				//alert("3");
		        var menu;
		        var moduleId = this.modId;
		        //alert(moduleId);
		       if (!menu){
		         menu = new Ext.menu.Menu([{
		         text : '删除结点',
		         handler : function(){
		         //删除结点信息
		            Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
					if(ret=="yes" && node.id!='100'){
			          var nodeId = node.id;
			          PageModelTemplateManager.ajaxDeletePageModelPanel(nodeId,moduleId);
			          alert(node.parentNode.id);
			          node.parentNode.reload();
			        }else if(node.id=='100'){
				    	alert("警告不能删除树的根节点！");
				                             }});
		            }
		           },{
		                text : '编辑',		                
		                handler : function(){
		                //让用户显示是否显示该面板
		                	var win;
		                	var isDisplay = new Ext.form.ComboBox({
		                		 store : new Ext.data.SimpleStore({
									data:[['0','否'],['1','是']],
									fields: ['display','name']
								}),
								valueField : "display",
								displayField : "name",
					            emptyText: '请选择是否显示',
								mode: 'local',
								forceSelection : true,
								hiddenName : "display",//用来动态存取数据存储器上store中的数据值，是所有的值？又是和后台怎么交互的
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "display",
								selectOnFocus: true,
								width: 200
		                	});
		                	//alert("1");
		                	var panelType = new Ext.form.ComboBox({
		                		
		                		 store : new Ext.data.JsonStore({
									url: webContext+'/pageModel/pageModelManage.do?methodCall=findPanelTypeByEntity',
									fields: ['type','name'],
							    	root:'data',									
									sortInfo: {field: "type", direction: "ASC"}
								}),
								valueField : "type",
								displayField : "name",
					            emptyText:"请选择",
								mode: 'remote',
								forceSelection : true,
								hiddenName : "type",
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "type",
								selectOnFocus: true,
								width: 200
		                	});
		                	//alert("2");
		                	var editForm = new Ext.form.FormPanel({
		                			
									layout: 'table',
									height : 150,
									width : 'auto',
									frame : true,
									layoutConfig: {columns: 1},	    		
									items:[
										{html: "是否可见:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										isDisplay,
										{html: "面板类型:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										panelType
										]
							});													
		                	if(!win){
			                	win = new Ext.Window({
									id : 'editWin',
									title : "面板菜单编辑窗口",
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items: editForm,								
									buttons:[
					        	       {	xtype:'button',
					        		        handler:function(){
//					        		        	alert(Ext.encode(editForm.form.getValues()));
//					        		        	return;
								        		Ext.Ajax.request({
													//调用action中存储的方法。												
													url: webContext+'/pageModel/pageModelManage.do?methodCall=modifyAndSavePanelMessage&clazz=com.zsgj.info.appframework.pagemodel.entity.PageModelPanel&panelId='+node.id,
													params : editForm.form.getValues(),
													method : 'post',
													//存储成功后调用的函数。
													success : function(response) {
														var result = Ext.decode(response.responseText);													
														if(result.failure){
															Ext.MessageBox.alert("提示信息：", "保存数据失败，原因是一个部门不能属于多个平台或是您输入了相同的值");
															win.close();
															this.store.reload();
														}else{
															Ext.MessageBox.alert("提示信息：", "保存平台数据");
														 	win.close();
														 	this.store.reload();
														}																											
													},
							                        scope:this
												});											
					        			},					        			
				        			text:'保存',
				        			scope:this
				        		},
				        		{	xtype:'button',
				        	 		handler:function(){
				        				win.close();
				        			},
				        			text:'关闭',
				        			scope:this
				        		}]						
								});
		                	}
		                	win.show();
		                }
		           }]);
		             menu.showAt(e.getPoint()); 
		           }   
		}
	},	
    
	initComponent: function() {
		this.marker = '0';
		this.modId = modId;
		//创建此模板时需要传入参数text:树根结点的名称
		this.root = new Ext.tree.AsyncTreeNode({
			text: pageModuleName,
			draggable: false,
			id: '-100'     		
		}),
		
		//根据从表格得到数据的id 和页面模板的id 来加载后台数据
		this.loader = new Ext.tree.TreeLoader({
			dataUrl:webContext+'/pageModel/pageModelManage.do?methodCall=loadPageModel'
			 
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					
					treeloader.baseParams = {
						id : node.id,						
						mid : this.modId     //这个就是上面表单保存时的id
					};
					

		}, this);
		
						 
		PagteModelTreePanel.superclass.initComponent.call(this);
	}
});


	
	