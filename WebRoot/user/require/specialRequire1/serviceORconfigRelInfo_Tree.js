/*
 * Ŀ���������
 */
 
callBackTestMainPanel = function(result){
	
	if(!result){
		alert("���϶�����������ѡ�������岻һ��");
		Ext.getCmp("tree").markerPanel='1';
		return ;
	}else{
		Ext.getCmp("tree").symbol = '1';
		Ext.getCmp("tree").markerPanel='0';
	}
	
}
 
callBackFuns = function(result){
	if(!result){
		alert("���϶����ظ���ֵ�����������϶�");
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
 
//Ŀ��ڵ㣬�ӽڵ㣬��ѡ�������
var ddFunction = function(root,node, refNode, selections,dItemFlag) { 
	
    for(var i = 0; i < selections.length; i ++) {

    	var record = selections[i];//���record��ʾ���ѡ�е�����        
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),             
            id: record.get('id'),   
            leaf: record.get('leaf')   
        }), refNode);      
         //ģ��
         var id = "";//Ӧ���ǹ�ϵ��id
 		 //ģ������Ŀ���ڵ�ID,
    	 var parentId = node.id; //��������ĸ��������id   
    	 //alert(node.id);
         //���������id
 		 var configItemId = record.get('id'); //�������id	**********Ҫ֪���������ϵ���������Ƿ�����
 		 //alert(configItemId);
         //�����������
         var configItemName = record.get('name');//�����������        
         //ҳ��ģ��id
         var itemId = this.itemId;//�������ϵ����id�����ǹ�ϵid 
         //alert(itemId);
         var order = node.indexOf(node.lastChild);	//��ǰ�ĸ��ڵ��µ��ӽڵ���ź�	
        // alert(dItemFlag);
     //ͬ�����浽���ݿ�   
     DWREngine.setAsync(false);
     //ConfigItemRelationManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order);
     ConfigItemRelationNewManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order,dItemFlag);
     DWREngine.setAsync(false);    
     //���ڵ��������
     //node.reload();
     
    }   
    Ext.getCmp("tree").root.reload();
    // return true;
}

PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '<font color=red>���������������ϵά��</font>',
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
		//���ڵ��ƶ�ʱ�����¼�
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
	        	var parentId = node.id;//����ϵid
	        	var relationRelStore = new Ext.data.JsonStore({
					url: webContext+'/ciRelationShip_findRelationTypeByEntity.action',
					fields: ['typeId','name'],
			    	root:'data',									
					sortInfo: {field: "typeId", direction: "ASC"}
				});
				//�Ƿ���ʾ����store
				var relationGraStore = new Ext.data.JsonStore({
					url: webContext+'/ciRelationShip_findRelationGradeByEntity.action',
					fields: ['gradeId','name'],
			    	root:'data',		
					sortInfo: {field: "gradeId", direction: "ASC"}
				});
	        	var reqId = this.idd; 
	        	//var reqClass = this.reqCl;
				var pId = this.picId;//��ϵ��id
				if(kernel=="SERVICE"){
		        if (!menu){
		        	var root = this.root;
		        	var picId = this.picId;
		         menu = new Ext.menu.Menu([
		         		{
		         		text : '����½�������',
		         		iconCls: 'add',
		                handler : function(){
							//alert(reqId+"����½�������"+reqClass);
		                	
		                	//shipPicId = 
		                	var order = node.indexOf(node.lastChild)+1;
		                	//alert(order);
		                	var shipPicId = pId;
		                	
		                	window.location =webContext+'/user/require/specialRequire/configItemAddInfo.jsp?parentId='+parentId+
		                	'&order='+order+'&shipPicId='+shipPicId+'&dtId='+reqId
		                }
		           },{
		           		text : '�������������',
		           		iconCls: 'add',
		                handler : function(){
		                	var addPanel = new AddExistConfigPanel({root:root,node:node,picId:picId});
		                	var formP =  addPanel.getSearchForm()
							var gridP= addPanel.grid;
		              		   	if(!win){
			                	win = new Ext.Window({
									id : 'newConfig',
									title : "����������",
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
		         		text : '����½�������',
		         		iconCls: 'add',
		                handler : function(){
		                	var parentId = node.id;//����ϵid
		                	var order = node.indexOf(node.lastChild)+1;
		                	var shipPicId = pId;
		                	
		                	window.location =webContext+'/user/require/specialRequire/configItemAddInfo.jsp?parentId='+parentId+
		                	'&order='+order+'&shipPicId='+shipPicId+'&dtId='+reqId
		                }
		           },{
		           		text : '�������������',
		           		iconCls: 'add',
		                handler : function(){
		                	var addPanel = new AddExistConfigPanel({root:root,node:node,picId:picId});//root��node.root ,node�ǵ�ǰ�ڵ�,picId�ǹ�ϵͼ��Id
		                	var formP =  addPanel.getSearchForm()
							var gridP= addPanel.grid;
		              		   	if(!winc){
			                	winc = new Ext.Window({
									id : 'newConfig',
									title : "����������",
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
				         text : 'ɾ�����',
				         iconCls: 'remove',
				         handler : function(){
				         //ɾ�������Ϣ
				            Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�",function(ret){
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
		           		text : '�༭',
		           		iconCls: 'edit',
		           		handler : function(){
		           			var win;		                	
		                	this.relationType = new Ext.form.ComboBox({		                		
		                		store : relationRelStore,
								valueField : "typeId",
								displayField : "name",
					            emptyText:"��ѡ��...",					            
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
								emptyText:"��ѡ��...",
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
		                		fieldLabel : '�鼯ϵ��',
		                		allowBlank : false,
		                		blankText : '���������д'
		                	});
		                	this.atechnoInfo = new Ext.form.TextField({
		                		xtype:"textfield",
		                		name :'atechnoInfo',
		                		fieldLabel : 'A�˼�����Ϣ'
		                	});
		                	this.btechnoInfo = new Ext.form.TextField({
		                		xtype:"textfield",
		                		name :'btechnoInfo',
		                		fieldLabel : 'B�˼�����Ϣ'
		                	});
		                	this.otherInfo = new Ext.form.TextField({
		                		xtype:"textfield",
		                		name :'otherInfo',
		                		fieldLabel : '������Ϣ'
		                	});
		                	        			                	
		               	this.editForm = new Ext.form.FormPanel({
		                			
									layout: 'table',
									height : 250,
									width : 'auto',
									frame : true,
									reader: new Ext.data.JsonReader({//��������þ��൱�ڰѺ�̨���͵�typeӳ�䵽��Ӧ�Ŀؼ�type�����ˣ�
										//��ȡ��֮���Ұ����ݷŵ�record���У���store�ļ�����һ����
								    		root: 'list',
							                successProperty: '@success'
								    },[{
							              name: 'typeId',//�Ƕ�Ӧ��record�е�hiddenName
							              mapping: 'typeId'  //��Ӧ���Ƕ�ȡ֮���record
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
										{html: "��ϵ����:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.relationType,									
										{html: "��ϵ����:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.relationGrade,										
										{html: "�鼯ϵ��:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.attachQuotiety,
										{html: "A�˼�����Ϣ:&nbsp;",cls: 'common-text',style:'width:140;height:20;text-align:left;margin:5 0 5 0;'},	
										this.atechnoInfo,
										{html: "B�˼�����Ϣ:&nbsp;",cls: 'common-text',style:'width:140;height:20;text-align:left;margin:5 0 5 0;'},	
										this.btechnoInfo,
										{html: "������Ϣ:&nbsp;",cls: 'common-text',style:'width:140;height:20;text-align:left;margin:5 0 5 0;'},	
										this.otherInfo
										]
							});	
							
							this.editForm.load({//��������form��������
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
									title : "���˵��༭����",
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items: this.editForm,								
									buttons:[
					        	       {	xtype:'button',
					        	       		text:'����',
					        		        handler:function(){
					        		        	var da =  new DataAction();
					        		        	//alert(itemName);
					        		        	if(validate_double(this.attachQuotiety.getValue())){
					        		        	Ext.Ajax.request({
													//����action�д洢�ķ�����												
													url: webContext+'/ciRelationShip_modifyAndSaveConfigRelMessage.action?&configId='+node.id,
													params : this.editForm.form.getValues(),
													method : 'post',
													//�洢�ɹ�����õĺ�����
													success : function(response) {
														var result = Ext.decode(response.responseText);													
														if(result.failure){
															Ext.MessageBox.alert("��ʾ��Ϣ��", "��������ʧ�ܣ�ԭ����һ�����Ų������ڶ��ƽ̨��������������ͬ��ֵ");
															win.close();
															
															this.store.reload();
														}else{
															Ext.MessageBox.alert("��ʾ��Ϣ��", "����ƽ̨���ݳɹ�",function(){
															
																win.close();
														 		this.store.reload();
															});
														 	
														}																											
													},
							                        scope:this
												});	
												}else{
													Ext.MessageBox.alert("��ʾ��Ϣ��", "�鼯ϵ����ʽ����ȷ");
												}										
					        			},					        			
				        			
				        			scope:this
				        		},
				        		{	xtype:'button',
				        	 		handler:function(){
				        				win.close();
				        			},
				        			text:'�ر�',
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
		this.tbar=[new Ext.Toolbar.TextItem('<font color=blue>�Ҽ�����ڵ�ɽ��б༭</font>')];
		var data = sra.getCIRelationShipPicInfo(this.dataId);
		var serviceId = sra.getReqServiceItemId(this.dataId);
		
		var serviceName =''; 
		var rootId = '';
		if(serviceId!='0'){
			var ciRelationShip = sra.getReqServiceItemShip(serviceId);//�õ�������Ĺ�ϵ
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
						id : node.id,//��ϵid						
						configId : " ",    //����������������ʱ��id,��ϵ����id
						configItemId : ""//�������id
					};
					

		}, this);
		
						 
		PagteModelTreePanel.superclass.initComponent.call(this);
	}
});


	
	