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
 
//Ŀ��ڵ㣬�ӽڵ㣬��ѡ�������
var ddFunction = function(node, refNode, selections) { 
	
    for(var i = 0; i < selections.length; i ++) {

    	var record = selections[i];//���record��ʾ���ѡ�е�����        
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),             
            id: record.get('id'),   
            leaf: record.get('leaf')   
        }), refNode);      
         //ģ��
         var id = "";
 		 //ģ������Ŀ���ڵ�ID
    	 var parentId = node.id;//���Ͻڵ��id
         //�������id
 		 var pagePanelId = record.get('id');
         //�����������
         var name = record.get('name');
         //��������Ƿ���Ҷ��
         var isLeaf = record.get('leaf');         
         //ҳ��ģ��id
         var groupPagePanelId = ppId;
        
         var order = node.indexOf(node.lastChild) ;
		 
          alert(order);
     //ͬ�����浽���ݿ�   
     DWREngine.setAsync(false);
     PanelManager.ajaxSavePagePanel(id, parentId, name, pagePanelId, groupPagePanelId,order);
     DWREngine.setAsync(true);    
     //���ڵ��������
     node.reload();     
    }   
}
//�õ���Ӧ������Ĭ��ֵ
var comboBoxValue = function(pNode){
	
	 Ext.Ajax.request({
						//����action�д洢�ķ�����		�����Ϊ�˵õ���Ӧ��������										
						url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=gainDefaultValue&pnode='+pNode,
						method : 'post',
						//�洢�ɹ�����õĺ�����
						success : function(response) {			
							
							var obj = Ext.decode(response.responseText);							
							if(obj.display==1){
								Ext.getCmp("tree").isDisplay = '��';
							}else{
								Ext.getCmp("tree").isDisplay = '��';
							}
							if(obj.onlyRead==1){
								Ext.getCmp("tree").ReadOnly = '��';
							}else{
								Ext.getCmp("tree").ReadOnly = '��';
							}
							if(obj.title==1){
								Ext.getCmp("tree").titleFlag = '��';
							}else{
								Ext.getCmp("tree").titleFlag = '��';
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
	title: '���ù�ϵ��',
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
    listeners: { //��ק֮ǰ�ᴥ����������Ա������ק������
       	
        beforenodedrop: function(dropEvent) {   
        	var node = dropEvent.target;    // Ŀ����   
            var data = dropEvent.data;      // ��ק������             
            var point = dropEvent.point;    // ��ק��Ŀ�����λ��     
            var ppId = this.ppId ;       
            var symbol = this.symbol;
		    
		    // ���data.nodeΪ�գ�����tree�������ק�����Ǵ�grid��tree����ק����Ҫ����   
            if(!data.node) {  
            	
            	for(var i = 0; i < data.selections.length; i++) {	 	    	    	
			        var record = data.selections[i];//���record��ʾ���ѡ�е�����
			        var pagePanelId = record.get('id');		        
		    	}	
   	
		    	//���������ж��ǲ���
			    DWREngine.setAsync(false);
			    PanelManager.ajaxTestRepeatePanel(ppId,pagePanelId,callBackFuns);
				if(this.marker=='1'){
					return;
				} 
				
                switch(point) {   
                    case 'append':   
                        // ���ʱ��Ŀ����Ϊnode���ŵ��ӽ��Ϊ�յ��������   
                    	//alert(node.id);
                    	ddFunction(node, null, data.selections);
                        
                        //��������Ϣ(templateItem:id,����/��չ��Id,mainTableIteppId,ģ��Id)
                        break;   
                    case 'above':   
                        // ���뵽node֮�ϣ�Ŀ����Ϊnode��parentNode���ӽ��Ϊnode   
                    	//alert(node.id);
                        ddFunction(node.parentNode, node, data.selections);   
                        break;   
                    case 'below':   
                        // ���뵽node֮�£�Ŀ����Ϊnode��parentNode���ӽ��Ϊnode��nextSibling   
                        ddFunction(node.parentNode, node.nextSibling, data.selections);
                        break;   
                }   
            }    
        },    
        
        //���ڵ��ƶ�ʱ�����¼�
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
				//�༭��store
				var editorStore = new Ext.data.JsonStore({
					url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=findPanelTypeByEntity&id='+parenteId,//?typeName=+unicode(Ext.getCmp("tree").typeName),//��ȫ����ʾ����
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
				//�Ƿ���ʾ��store
				var DisplayStore = new Ext.data.JsonStore({
					url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=idDisplayByPagePanel&id='+parenteId,
					fields: ['display','name'],
			    	root:'data',		
					sortInfo: {field: "display", direction: "ASC"}
				});
				//�Ƿ���ʾ����store
				var titleStore = new Ext.data.JsonStore({
					url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=isTitleDisplayByPagePanel&id='+parenteId,
					fields: ['titleDisplay','name'],
			    	root:'data',		
					sortInfo: {field: "titleDisplay", direction: "ASC"}
				});
				//�Ƿ�ֻ��
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
		         		text : '�༭',		                
		                handler : function(){
		                //���û���ʾ�Ƿ���ʾ�����
		                	var win;		
//		                	var id = parenteId;
		                	this.panelType = new Ext.form.ComboBox({
		                		store : editorStore,
								valueField : "typeId",
								displayField : "name",
					            emptyText:'��ѡ��...',
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
					            emptyText:'��ѡ��...',
								forceSelection : true,
								hiddenName : "display",//hiddenName������ĵ�domԪ�صı�ʶ,�����ܺʹ�comboBox��id������
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
					           emptyText:'��ѡ��...',
								forceSelection : true,
								hiddenName : "titleDisplay",//������̬��ȡ���ݴ洢����store�е�����ֵ�������е�ֵ�����Ǻͺ�̨��ô������
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
								valueField : "readonly",//������ѡ�е�ֵ��id
								displayField : "name",//������ѡ�е�name
					            emptyText:'��ѡ��...',
								forceSelection : true,
								hiddenName : "readonly",//������̬��ȡ���ݴ洢����store�е�����ֵ�������е�ֵ�����Ǻͺ�̨��ô������
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "readonly",//���Ҫ��idһ�£�����getCmp()��ʱ����ܵõ�
								selectOnFocus: true,
								width: 200
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
										{html: "�������:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.panelType,										
										{html: "�Ƿ�ֻ��:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.readonly,
										{html: "�Ƿ���ʾ������:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
										this.isTitleDisplayFlag,
										{html: "�Ƿ�ɼ�:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										this.isDisplay
										]
							});	
						this.editForm.load({//��������form��������
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
							 	
							 	titleStore.load({//����
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
									title : "���˵��༭����",
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items: this.editForm,								
									buttons:[
					        	       {	xtype:'button',
					        		        handler:function(){
								        		Ext.Ajax.request({
													//����action�д洢�ķ�����												
													url: webContext+'/pageModel/pageGroupPanelManage.do?methodCall=modifyAndSavePanelMessage&clazz=com.zsgj.info.appframework.pagemodel.entity.PageModelPanel&panelId='+node.id,
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
															Ext.MessageBox.alert("��ʾ��Ϣ��", "����ƽ̨����");
														 	win.close();
														 	this.store.reload();
														}																											
													},
							                        scope:this
												});											
					        			},					        			
				        			text:'����',
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
		           },{
				         text : 'ɾ�����',
				         handler : function(){
				         //ɾ�������Ϣ
				            Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�",function(ret){
							if(ret=="yes" && node.id!='100'){
					          var nodeId = node.id;
					           DWREngine.setAsync(false);
					          PanelManager.ajaxDeletePagePanelRelation(nodeId,ppId);
					           DWREngine.setAsync(true);
					          node.parentNode.reload();
					        }else if(node.id=='100'){
						    	alert("���治��ɾ�����ĸ��ڵ㣡");
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
		//������ģ��ʱ��Ҫ�������text:������������
		this.root = new Ext.tree.AsyncTreeNode({
			text: ppTitle,
			draggable: false,
			id: '-100'     		
		}),
		
		//���ݴӱ��õ����ݵ�id ��ҳ��ģ���id �����غ�̨����
		this.loader = new Ext.tree.TreeLoader({
			dataUrl:webContext+'/pageModel/pageGroupPanelManage.do?methodCall=loadPagePanel'			 
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					
					treeloader.baseParams = {
						id : node.id,						
						ppId : this.ppId     //����������������ʱ��id
					};
					

		}, this);
		
						 
		PanelDataPanel.superclass.initComponent.call(this);
	}
});


	
	