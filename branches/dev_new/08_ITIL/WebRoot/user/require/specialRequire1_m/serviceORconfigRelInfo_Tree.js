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
testNodeKernel = function(result){
	if(result=='SERVICE'){
		Ext.getCmp("tree").kernel = 'SERVICE';
		return ;
	}else{
		Ext.getCmp("tree").kernel = 'CONFIG';
		return ;
	}
}
 
//目标节点，子节点，和选择的数据
var ddFunction = function(root,node, refNode, selections,dItemFlag) { 
	
    for(var i = 0; i < selections.length; i ++) {

    	var record = selections[i];//这个record表示表格选中的数据        
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),             
            id: record.get('id'),   
            leaf: record.get('leaf')   
        }), refNode);      
         //模板
         var id = "";//应该是关系的id
 		 //模板行项目父节点ID,
    	 var parentId = node.id; //此配置项的父配置项的id   
    	 //alert(node.id);
         //表格配置项id
 		 var configItemId = record.get('id'); //配置项的id	**********要知道往树上拖的是配置项还是服务项
 		 //alert(configItemId);
         //表格数据名称
         var configItemName = record.get('name');//配置项的名字        
         //页面模板id
         var itemId = this.itemId;//配置项关系名的id，不是关系id 
         //alert(itemId);
         var order = node.indexOf(node.lastChild);	//当前的父节点下的子节点的排号	
        // alert(dItemFlag);
     //同步保存到数据库   
     DWREngine.setAsync(false);
     //ConfigItemRelationManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order);
     ConfigItemRelationNewManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order,dItemFlag);
     DWREngine.setAsync(false);    
     //父节点加载数据
     //node.reload();
     
    }   
    Ext.getCmp("tree").root.reload();
    // return true;
}

PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '<font color=red>服务项与配置项关系维护</font>',
	animate: true,
	autoScroll: true,
	containerScroll: true,
	lines: true,
	width:300,
	height: 530,
	enableDD: true,   
    ddGroup: "tgDD", 
    removeFlag: false,
	frame:true,
	listeners: {
		//当节点移动时触发事件
        movenode: function(tree, node, oldParent, newParent, index) {
        		//alert(tree.root.id);
        		DWREngine.setAsync(false);
	       		ConfigItemRelationNewManager.ajaxMoveConfigItem(tree.root.text,node.id, oldParent.id, newParent.id, index);
				DWREngine.setAsync(false);
				tree.root.reload();
		
        },		
		contextmenu: function(node, e){
			//alert(node.id);
				DWREngine.setAsync(false);
				RequirementCIRelationManager.getKernel(node.id,testNodeKernel);
				var kernel = Ext.getCmp("tree").kernel;
	            var menu;
	            var menuc;
	        	var win;
	        	var win2
	        	var winc;
	        	var winc2;
	        	var parentId = node.id;//父关系id
	        	var relationRelStore = new Ext.data.JsonStore({
					url: webContext+'/ciRelationShip_findRelationTypeByEntity.action',
					fields: ['typeId','name'],
			    	root:'data',									
					sortInfo: {field: "typeId", direction: "ASC"}
				});
				//是否显示标题store
				var relationGraStore = new Ext.data.JsonStore({
					url: webContext+'/ciRelationShip_findRelationGradeByEntity.action',
					fields: ['gradeId','name'],
			    	root:'data',		
					sortInfo: {field: "gradeId", direction: "ASC"}
				});
	        	var reqId = this.idd; 
	        	//var reqClass = this.reqCl;
				var pId = this.picId;//关系名id
				if(kernel=="SERVICE"){
		        if (!menu){
		        	var root = this.root;
		        	var picId = this.picId;
		         menu = new Ext.menu.Menu([
		         		{
		         		text : '添加新建配置项',
		         		iconCls: 'add',
		                handler : function(){
							//alert(reqId+"添加新建配置项"+reqClass);
		                	
		                	//shipPicId = 
		                	var order = node.indexOf(node.lastChild)+1;
		                	//alert(order);
		                	var shipPicId = pId;
		                	
		                	window.location =webContext+'/user/require/specialRequire/configItemAddInfo.jsp?parentId='+parentId+
		                	'&order='+order+'&shipPicId='+shipPicId+'&dtId='+reqId
		                }
		           },{
		           		text : '添加已有配置项',
		           		iconCls: 'add',
		                handler : function(){
		                	var addPanel = new AddExistConfigPanel({root:root,node:node,picId:picId});
		                	var formP =  addPanel.getSearchForm()
							var gridP= addPanel.grid;
		              		   	if(!win){
			                	win = new Ext.Window({
									id : 'newConfig',
									title : "已有配置项",
									width : 600,
									height : 400,
									maximizable : true,
									modal : true,
									items: [formP,gridP]													
								});
		                	}
		                	
		              		 win.show();
		                }
		           
		           		}
		           ]);
		             menu.showAt(e.getPoint()); 
		           }   
		      }//zaici
		      else{
		      
		      	if (!menuc){
		      		var idd = parentId;
		      		var root = this.root;
		      		var picId = this.picId;
		         menuc = new Ext.menu.Menu([
		         		{
		         		text : '添加新建配置项',
		         		iconCls: 'add',
		                handler : function(){
		                	var parentId = node.id;//父关系id
		                	var order = node.indexOf(node.lastChild)+1;
		                	var shipPicId = pId;
		                	
		                	window.location =webContext+'/user/require/specialRequire/configItemAddInfo.jsp?parentId='+parentId+
		                	'&order='+order+'&shipPicId='+shipPicId+'&dtId='+reqId
		                }
		           },{
		           		text : '添加已有配置项',
		           		iconCls: 'add',
		                handler : function(){
		                	var addPanel = new AddExistConfigPanel({root:root,node:node,picId:picId});//root是node.root ,node是当前节点,picId是关系图名Id
		                	var formP =  addPanel.getSearchForm()
							var gridP= addPanel.grid;
		              		   	if(!winc){
			                	winc = new Ext.Window({
									id : 'newConfig',
									title : "已有配置项",
									width : 600,
									height : 400,
									maximizable : true,
									modal : true,
									items: [formP,gridP]													
								});
		                	}
		                	
		              		 winc.show();
		                //	window.location =webContext+'/user/require/individuation/configItemAddList.jsp';
		                }
		           
		           		}
		           		,{
				         text : '删除结点',
				         iconCls: 'remove',
				         handler : function(){
				         //删除结点信息
				            Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
							//if(ret=="yes" && node.id!='-100'){
					          var nodeId = node.id;
					          var itemId = this.picId;
					          DWREngine.setAsync(false);
					          ConfigItemRelationNewManager.ajaxDeleteConfigItem(nodeId,itemId);
					          DWREngine.setAsync(true);	
					          root.reload();
						      });
				            }
		           },{
		           		text : '编辑',
		           		iconCls: 'edit',
		           		handler : function(){
		           			var win;		                	
		                	this.relationType = new Ext.form.ComboBox({		                		
		                		store : relationRelStore,
								valueField : "typeId",
								displayField : "name",
					            emptyText:"请选择...",					            
								mode: 'remote',
								forceSelection : true,
								hiddenName : "typeId",
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "typeId",
								selectOnFocus: true,
								width: 200
		                	});
		                	this.relationGrade = new Ext.form.ComboBox({
		                		store : relationGraStore,
								valueField : "gradeId",
								displayField : "name",
								emptyText:"请选择...",
								mode: 'remote',
								forceSelection : true,
								hiddenName : "gradeId",
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "gradeId",
								selectOnFocus: true,
								width: 200
		                	});
		                	this.attachQuotiety = new Ext.form.TextField({
		                		xtype:"textfield",
		                		name :'quotiety',
		                		fieldLabel : '归集系数',
		                		allowBlank : false,
		                		blankText : '该项必须填写'
		                	});
		                	this.atechnoInfo = new Ext.form.TextField({
		                		xtype:"textfield",
		                		name :'atechnoInfo',
		                		fieldLabel : 'A端技术信息'
		                	});
		                	this.btechnoInfo = new Ext.form.TextField({
		                		xtype:"textfield",
		                		name :'btechnoInfo',
		                		fieldLabel : 'B端技术信息'
		                	});
		                	this.otherInfo = new Ext.form.TextField({
		                		xtype:"textfield",
		                		name :'otherInfo',
		                		fieldLabel : '其他信息'
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
							              name: 'gradeId',
							              mapping: 'gradeId'
							            },{
							              name: 'quotiety',
							              mapping: 'quotiety'
							            },
							              {
							              name: 'atechnoInfo',
							              mapping: 'atechnoInfo'
							            },{
							              name: 'btechnoInfo',
							              mapping: 'btechnoInfo'
							            },{
							              name: 'otherInfo',
							              mapping: 'otherInfo'
							            }
							        ]),
									layoutConfig: {columns: 2},	    		
									items:[				
										{html: "关系类型:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.relationType,									
										{html: "关系级别:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.relationGrade,										
										{html: "归集系数:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.attachQuotiety,
										{html: "A端技术信息:&nbsp;",cls: 'common-text',style:'width:140;height:20;text-align:left;margin:5 0 5 0;'},	
										this.atechnoInfo,
										{html: "B端技术信息:&nbsp;",cls: 'common-text',style:'width:140;height:20;text-align:left;margin:5 0 5 0;'},	
										this.btechnoInfo,
										{html: "其他信息:&nbsp;",cls: 'common-text',style:'width:140;height:20;text-align:left;margin:5 0 5 0;'},	
										this.otherInfo
										]
							});	
							
							this.editForm.load({//用来加载form表单的数据
							 url: webContext+'/ciRelationShip_findConfigRelFormValue.action?id='+idd,
							 timeout: 3000,
							 success: function(action,editorForm){
							 	
							 	relationRelStore.load({							 		
							 		callback: function(r, options, success){							 			
							 			var value = editorForm.form.findField('typeId').getValue();								 			
							 			editorForm.form.findField('typeId').setValue(value)
							 		}
							 	});							 	
							 	relationGraStore.load({
							 		callback: function(r, options, success){
							 			var value = editorForm.form.findField('gradeId').getValue();							 			
							 			editorForm.form.findField('gradeId').setValue(value);
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
					        	       		text:'保存',
					        		        handler:function(){
					        		        	var da =  new DataAction();
					        		        	//alert(itemName);
					        		        	if(validate_double(this.attachQuotiety.getValue())){
					        		        	Ext.Ajax.request({
													//调用action中存储的方法。												
													url: webContext+'/ciRelationShip_modifyAndSaveConfigRelMessage.action?&configId='+node.id,
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
															Ext.MessageBox.alert("提示信息：", "保存平台数据成功",function(){
															
																win.close();
														 		this.store.reload();
															});
														 	
														}																											
													},
							                        scope:this
												});	
												}else{
													Ext.MessageBox.alert("提示信息：", "归集系数格式不正确");
												}										
					        			},					        			
				        			
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
		           
		           }
		           ]);
		             menuc.showAt(e.getPoint()); 
		           }   
		      	
		      	
		      }
		}
	},
	tbar:this.tbar,	
	initComponent: function() {
	    this.idd = this.dataId
	    this.kernel = '';
		var sra = new SRAction();
		this.tbar=[new Ext.Toolbar.TextItem('<font color=blue>右键点击节点可进行编辑</font>')];
		var data = sra.getCIRelationShipPicInfo(this.dataId);
		var serviceId = sra.getReqServiceItemId(this.dataId);
		
		var serviceName =''; 
		var rootId = '';
		if(serviceId!='0'){
			var ciRelationShip = sra.getReqServiceItemShip(serviceId);//得到服务项的关系
			serviceName = ciRelationShip.name;
			rootId = ciRelationShip.id;
		}
		this.picId=data.id;
		this.symbol = '0';
		this.markerPanel = '0';
		this.marker = '0';
		this.typeName ='';
		this.display ='';
		this.titleDisplay = '';
		this.readonly ='';
		this.root = new Ext.tree.AsyncTreeNode({
			text: serviceName,//data.name
			draggable: false,
			expanded:true,
			id: rootId   
		}),
		this.loader = new Ext.tree.TreeLoader({
			 dataUrl:webContext+'/ciRelationShip_loadConfigItemRelation.action'
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					treeloader.baseParams = {
						id : node.id,//关系id						
						configId : " ",    //这个就是上面表单保存时的id,关系名的id
						configItemId : ""//配置项的id
					};
					

		}, this);
		
						 
		PagteModelTreePanel.superclass.initComponent.call(this);
	}
});


	
	