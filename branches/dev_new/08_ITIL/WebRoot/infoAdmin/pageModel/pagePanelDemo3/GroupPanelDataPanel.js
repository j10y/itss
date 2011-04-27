/*
 * 目标数据面板
 */
 
callBackTestMainPanel = function(result){
	
	if(!result){
		alert("您拖动的主面板和您选择的主面板不一致");
		Ext.getCmp("tree").markerPanel='1';
		return ;
	}else{
		Ext.getCmp("tree").symbol = '1';
		Ext.getCmp("tree").markerPanel='0';
	}	
}
 
callBackFuns = function(result){
	if(!result){
		alert("您拖动了重复的值，请您重新拖动");
		Ext.getCmp("tree").marker='1';
		return ;
	}else{
		Ext.getCmp("tree").marker='0'
	}
}
 
//目标节点，子节点，和选择的数据
var ddFunction = function(node, refNode, selections) { 
	
    for(var i = 0; i < selections.length; i ++) {

    	var record = selections[i];//这个record表示表格选中的数据        
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),             
            id: record.get('id'),   
            leaf: record.get('leaf')   
        }), refNode);      
         //模板
         var id = "";
 		 //模板行项目父节点ID
    	 var parentId = node.id;//树上节点的id
         //表格数据id
 		 var pagePanelId = record.get('id');
         //表格数据名称
         var name = record.get('name');
         //表格数据是否是叶子
         var isLeaf = record.get('leaf');         
         //页面模板id
         var groupPagePanelId = ppId;
        
         var order = node.indexOf(node.lastChild) ;
		 
          alert(order);
     //同步保存到数据库   
     DWREngine.setAsync(false);
     PanelManager.ajaxSavePagePanel(id, parentId, name, pagePanelId, groupPagePanelId,order);
     DWREngine.setAsync(true);    
     //父节点加载数据
     node.reload();     
    }   
}
//得到相应的类型默认值
var comboBoxValue = function(pNode){
	
	 Ext.Ajax.request({
						//调用action中存储的方法。		这个是为了得到相应的面板参数										
						url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=gainDefaultValue&pnode='+pNode,
						method : 'post',
						//存储成功后调用的函数。
						success : function(response) {			
							
							var obj = Ext.decode(response.responseText);							
							if(obj.display==1){
								Ext.getCmp("tree").isDisplay = '是';
							}else{
								Ext.getCmp("tree").isDisplay = '否';
							}
							if(obj.onlyRead==1){
								Ext.getCmp("tree").ReadOnly = '是';
							}else{
								Ext.getCmp("tree").ReadOnly = '否';
							}
							if(obj.title==1){
								Ext.getCmp("tree").titleFlag = '是';
							}else{
								Ext.getCmp("tree").titleFlag = '否';
							}
							Ext.getCmp("tree").typeName = obj.typeName;	
//							Ext.getCmp("tree").isDisplay = obj.display;
//							Ext.getCmp("tree").ReadOnly = obj.onlyRead;
//							Ext.getCmp("tree").titleFlag = obj.title;
							
							//alert(Ext.getCmp("tree").ReadOnly);
						},
                        scope:this
					});	
}


PanelDataPanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '配置关系树',
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
	columnWidth:.5,
	lines: true,
	height: 500,
	enableDD: true,   
    ddGroup: "tgDD", 
    removeFlag: false,
    listeners: { //拖拽之前会触发，这是针对表格到树拖拽的例子
       	
        beforenodedrop: function(dropEvent) {   
        	var node = dropEvent.target;    // 目标结点   
            var data = dropEvent.data;      // 拖拽的数据             
            var point = dropEvent.point;    // 拖拽到目标结点的位置     
            var ppId = this.ppId ;       
            var symbol = this.symbol;
		    
		    // 如果data.node为空，则不是tree本身的拖拽，而是从grid到tree的拖拽，需要处理   
            if(!data.node) {  
            	
            	for(var i = 0; i < data.selections.length; i++) {	 	    	    	
			        var record = data.selections[i];//这个record表示表格选中的数据
			        var pagePanelId = record.get('id');		        
		    	}	
   	
		    	//这是用来判断是不是
			    DWREngine.setAsync(false);
			    PanelManager.ajaxTestRepeatePanel(ppId,pagePanelId,callBackFuns);
				if(this.marker=='1'){
					return;
				} 
				
                switch(point) {   
                    case 'append':   
                        // 添加时，目标结点为node，放到子结点为空的这种情况   
                    	//alert(node.id);
                    	ddFunction(node, null, data.selections);
                        
                        //保存结点信息(templateItem:id,主表/扩展表Id,mainTableIteppId,模板Id)
                        break;   
                    case 'above':   
                        // 插入到node之上，目标结点为node的parentNode，子结点为node   
                    	//alert(node.id);
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
//        		alert(node.id);
	       		PanelManager.ajaxMovePagePanel(tree.root.text,node.id, oldParent.id, newParent.id, index);
				DWREngine.setAsync(true);
		
        },		
		contextmenu: function(node, e){
				
		        var menu;
		        var ppId = this.ppId;
		        var parenteId = node.id; 
		        //alert(parenteId);
		        var order = node.indexOf(node.lastChild)+1;
				var loader = this.loader;
				//编辑的store
				var editorStore = new Ext.data.JsonStore({
					url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=findPanelTypeByEntity&id='+parenteId,//?typeName=+unicode(Ext.getCmp("tree").typeName),//起到全部显示作用
					fields: ['typeId','name'],
			    	root:'data',		
					sortInfo: {field: "typeId", direction: "ASC"}
//					listener:{
//						beforeload : function(store,opt){
//							if(opt.params['name']==undefined){
//								opt.params['name']=Ext.getCmp("tree").typeName
//							}
//						}
//					}
				});
				//是否显示的store
				var DisplayStore = new Ext.data.JsonStore({
					url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=idDisplayByPagePanel&id='+parenteId,
					fields: ['display','name'],
			    	root:'data',		
					sortInfo: {field: "display", direction: "ASC"}
				});
				//是否显示标题store
				var titleStore = new Ext.data.JsonStore({
					url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=isTitleDisplayByPagePanel&id='+parenteId,
					fields: ['titleDisplay','name'],
			    	root:'data',		
					sortInfo: {field: "titleDisplay", direction: "ASC"}
				});
				//是否只读
				var readOnlyStore = new Ext.data.JsonStore({
					url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=idreadonlyByPagePanel&id='+parenteId,
					fields: ['readonly','name'],
			    	root:'data',		
					sortInfo: {field: "readonly", direction: "ASC"}
				});
				DWREngine.setAsync(false);
            	comboBoxValue(parenteId);
            	DWREngine.setAsync(true);
            	
		        if (!menu){
		         menu = new Ext.menu.Menu([
	         		{
		         		text : '编辑',		                
		                handler : function(){
		                //让用户显示是否显示该面板
		                	var win;		
//		                	var id = parenteId;
		                	this.panelType = new Ext.form.ComboBox({
		                		store : editorStore,
								valueField : "typeId",
								displayField : "name",
					            emptyText:'请选择...',
					           	forceSelection : true,
								hiddenName : "typeId",
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								//name : "typeId",
								selectOnFocus: true,
								width: 200
		                	});
		                	this.isDisplay = new Ext.form.ComboBox({
		                		store : DisplayStore,
								valueField : "display",
								displayField : "name",
					            emptyText:'请选择...',
								forceSelection : true,
								hiddenName : "display",//hiddenName才是真的的dom元素的标识,他不能和次comboBox的id重名啊
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								//name : "display",
								selectOnFocus: true,
								width: 200
		                	});
		                	this.isTitleDisplayFlag = new Ext.form.ComboBox({
		                		 store : titleStore,
								valueField : "titleDisplay",
								displayField : "name",
					           emptyText:'请选择...',
								forceSelection : true,
								hiddenName : "titleDisplay",//用来动态存取数据存储器上store中的数据值，是所有的值？又是和后台怎么交互的
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : false,
								name : "titleDisplay",
								selectOnFocus: true,
								width: 200
		                	});
		                	this.readonly = new Ext.form.ComboBox({
		                		
		                		store : readOnlyStore,
								valueField : "readonly",//代表着选中的值中id
								displayField : "name",//代表着选中的name
					            emptyText:'请选择...',
								forceSelection : true,
								hiddenName : "readonly",//用来动态存取数据存储器上store中的数据值，是所有的值？又是和后台怎么交互的
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "readonly",//这个要和id一致，这样getCmp()的时候才能得到
								selectOnFocus: true,
								width: 200
		                	});			                			                	
		               	this.editForm = new Ext.form.FormPanel({
		                			
									layout: 'table',
									height : 250,
									width : 'auto',
									frame : true,
									reader: new Ext.data.JsonReader({//这个的作用就相当于把后台发送的type映射到相应的控件type当中了，
										//读取到之后并且把数据放到record当中，和store的加载是一样的
								    		root: 'list',
							                successProperty: '@success'
								    },[{
							              name: 'typeId',//是对应的record中的hiddenName
							              mapping: 'typeId'  //对应的是读取之后的record
							            },{
							              name: 'readonly',
							              mapping: 'readonly'
							            },{
							              name: 'titleDisplay',
							              mapping: 'titleDisplay'
							            },{
							              name: 'display',
							              mapping: 'display'
							            }
							        ]),
									layoutConfig: {columns: 2},	    		
									items:[				
										{html: "面板类型:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.panelType,										
										{html: "是否只读:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.readonly,
										{html: "是否显示面板标题:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
										this.isTitleDisplayFlag,
										{html: "是否可见:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.isDisplay
										]
							});	
						this.editForm.load({//用来加载form表单的数据
							 url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=findPanelFormValue&id='+parenteId,
							 timeout: 3000,
							 success: function(action,editorForm){
							 	
							 	editorStore.load({							 		
							 		callback: function(r, options, success){							 			
							 			var value = editorForm.form.findField('typeId').getValue();								 			
							 			editorForm.form.findField('typeId').setValue(value)
							 		}
							 	});
							 	
							 	DisplayStore.load({
							 		callback: function(r, options, success){
							 			var value = editorForm.form.findField('display').getValue();	
							 			//alert("DisplayStore"+value);
							 			editorForm.form.findField('display').setValue(value);
							 		}							 	
							 	});
							 	
							 	titleStore.load({//用来
							 		callback: function(r, options, success){							 			
							 			var value = editorForm.form.findField('titleDisplay').getValue();	
							 			//alert("titleStore"+value);
							 			editorForm.form.findField('titleDisplay').setValue(value);
							 		}
							 	});
							 	
							 	readOnlyStore.load({
							 		callback: function(r, options, success){
							 			var value = editorForm.form.findField('readonly').getValue();	
							 			//alert("readOnlyStore:"+value);
							 			editorForm.form.findField('readonly').setValue(value);
							 		}							 	
							 	});
							 	
							 	
							 }
						});
		                	if(!win){
			                	win = new Ext.Window({
									id : 'editWin',
									title : "面板菜单编辑窗口",
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items: this.editForm,								
									buttons:[
					        	       {	xtype:'button',
					        		        handler:function(){
								        		Ext.Ajax.request({
													//调用action中存储的方法。												
													url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=modifyAndSavePanelMessage&clazz=com.zsgj.info.appframework.pagemodel.entity.PageModelPanel&panelId='+node.id,
													params : this.editForm.form.getValues(),
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
		           },{
				         text : '删除结点',
				         handler : function(){
				         //删除结点信息
				            Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
							if(ret=="yes" && node.id!='100'){
					          var nodeId = node.id;
					           DWREngine.setAsync(false);
					          PanelManager.ajaxDeletePagePanelRelation(nodeId,ppId);
					           DWREngine.setAsync(true);
					          node.parentNode.reload();
					        }else if(node.id=='100'){
						    	alert("警告不能删除树的根节点！");
						                             }});
				            }
		           }]);
		             menu.showAt(e.getPoint()); 
		           }   
		}
	},	
    
	initComponent: function() {
		
		this.symbol = '0';
		this.markerPanel = '0';
		this.marker = '0';
		this.ppId = ppId;
		this.typeName ='';
		this.isDisplay = '';
		this.ReadOnly = '';
		this.titleFlag = '';
//		this.isReadOnly = '';
//		this.title = '';
		//创建此模板时需要传入参数text:树根结点的名称
		this.root = new Ext.tree.AsyncTreeNode({
			text: ppTitle,
			draggable: false,
			id: '-100'     		
		}),
		
		//根据从表格得到数据的id 和页面模板的id 来加载后台数据
		this.loader = new Ext.tree.TreeLoader({
			dataUrl:webContext+'/pageModel/pageGroupPanelManage.do?methodCall=loadPagePanel'			 
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					
					treeloader.baseParams = {
						id : node.id,						
						ppId : this.ppId     //这个就是上面表单保存时的id
					};
					

		}, this);
		
						 
		PanelDataPanel.superclass.initComponent.call(this);
	}
});


	
	