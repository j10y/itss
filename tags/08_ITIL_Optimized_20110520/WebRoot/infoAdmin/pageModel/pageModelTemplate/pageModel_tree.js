/*
 * Ŀ���������
 */
 
callBackTestMainPanel = function(result){
	
//	if(!result){
//		alert("���϶�����������ѡ�������岻һ��");
//		Ext.getCmp("tree").markerPanel='1';
//		return ;
//	}else{
//		Ext.getCmp("tree").symbol = '1';
//		Ext.getCmp("tree").markerPanel='0';
//	}
	
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
    	 var parentId = node.id; 	 
         //�������id
 		 var pagePanelId = record.get('id');
         //�����������
         var name = record.get('name');
         //��������Ƿ���Ҷ��
         var isLeaf = record.get('leaf');         
         //ҳ��ģ��id
         var pageModelId = modId;
        
         var order = node.indexOf(node.lastChild) ;
		 
          //alert(order);
     //ͬ�����浽���ݿ�   
     DWREngine.setAsync(false);
     PageModelTemplateManager.ajaxSavePageModelPanel(id, parentId, name, pagePanelId, pageModelId,order);
     DWREngine.setAsync(true);    
     //���ڵ��������
     node.reload();     
    }   
}
//�õ���Ӧ������Ĭ��ֵ
var comboBoxValue = function(pNode){
	
	 Ext.Ajax.request({
						//����action�д洢�ķ�����												
						url: webContext+'/pageModel/pageModelManage.do?methodCall=gainDefaultValue&pnode='+pNode,
						method : 'post',
						//�洢�ɹ�����õĺ�����
						success : function(response) {							
							var obj = Ext.decode(response.responseText);
							if(obj.display==1){
								Ext.getCmp("tree").display = '��';
							}else{
								Ext.getCmp("tree").display = '��';
							}
							if(obj.readonly==1){
								Ext.getCmp("tree").readonly = '��';
							}else{
								Ext.getCmp("tree").readonly = '��';
							}
							if(obj.titleDisplay==1){
								Ext.getCmp("tree").titleDisplay = '��';
							}else{
								Ext.getCmp("tree").titleDisplay = '��';
							}
							Ext.getCmp("tree").typeName = obj.typeName;								
						},
                        scope:this
					});	
}


PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
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
            var mId = this.modId ;       
            var symbol = this.symbol;
		    
		    // ���data.nodeΪ�գ�����tree�������ק�����Ǵ�grid��tree����ק����Ҫ����   
            if(!data.node) {  
            	
            	for(var i = 0; i < data.selections.length; i++) {	 	    	    	
			        var record = data.selections[i];//���record��ʾ���ѡ�е�����
			        var pagePanelId = record.get('id');		        
		    	}	
		    	//���������ж������϶����ǲ��������, �����Ϲ�ȥ��һ�������֮��Ͳ�Ҫ���ж���
		    	//�ѵ����ڵ���ˢ��ҳ���ʱ����������¸�ֵ�ˣ�����Ҫ�ں�̨Ҫ�жϵ�ǰ���ڲ����������
		    	if(this.symbol=='0'){
		    		//alert("963");
		    		DWREngine.setAsync(false);
			    	PageModelTemplateManager.ajaxTestMainPanel(pagePanelId, mId,callBackTestMainPanel);			    	
			    	if(this.markerPanel=='1'){			    		
			    		return;
			    	}
		    	}   	
		    	//���������ж��ǲ���
			    DWREngine.setAsync(false);
			    PageModelTemplateManager.ajaxTestRepeatePanel(node.id, pagePanelId, mId,callBackFuns);
				if(this.marker=='1'){
					return;
				} 
				
                switch(point) {   
                    case 'append':   
                        // ���ʱ��Ŀ����Ϊnode���ŵ��ӽ��Ϊ�յ��������   
                    	//alert(node.id);
                    	ddFunction(node, null, data.selections);
                        
                        //��������Ϣ(templateItem:id,����/��չ��Id,mainTableItemId,ģ��Id)
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
	       		PageModelTemplateManager.ajaxMovePageModelPanel(tree.root.text,node.id, oldParent.id, newParent.id, index);
				DWREngine.setAsync(false);
		
        },		
		contextmenu: function(node, e){
				
		        var menu;
		        var moduleId = this.modId;
		        var parenteId = node.id; 
		        var order = node.indexOf(node.lastChild)+1;		       
				var loader = this.loader;				
				var root = this.root;
				DWREngine.setAsync(false);
            	comboBoxValue(parenteId);
            	DWREngine.setAsync(true);
            	
            	
            	
		        if (!menu){
		         menu = new Ext.menu.Menu([
		         		{
		         		text : '�½����',
		         		handler : function(){
		         			var win;
		         			var panelType = new Ext.form.ComboBox({		                		
		                		 store : new Ext.data.JsonStore({
									url: webContext+'/pageModel/pageModelManage.do?methodCall=findPanelTypeByEntity',
									fields: ['type','name'],
							    	root:'data',									
									sortInfo: {field: "type", direction: "ASC"}
								}),
								valueField : "type",
								displayField : "name",
					            emptyText:"��ѡ��",
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
		                	
		                	var panelNameField = {
		                		id : "panelName",
		                		xtype : "textfield",
		                		name : "panelName",
		                		fieldLabel : "�������",
		                		allowBlank : false,
		                		selectOnFocus : true
		                	};
		                	
		                	var panelTitleField = {
		                		id : "panelTitle",
		                		xtype : "textfield",
		                		name : "panelTitle",
		                		fieldLabel : "�������",
		                		allowBlank : false,
		                		selectOnFocus : true
		                	};
		                	
		                	var sysMainTabel = new Ext.form.ComboBox({		                		
		                		 store : new Ext.data.JsonStore({
									url: webContext+'/pageModel/pageModelManage.do?methodCall=findSystemMainTable&moduleId='+moduleId,
									fields: ['systemMainTable','name'],
							    	root:'data',									
									sortInfo: {field: "systemMainTable", direction: "ASC"}
								}),
								valueField : "systemMainTable",
								displayField : "name",
					            emptyText:"��ѡ��",
								mode: 'remote',
								forceSelection : true,
								hiddenName : "systemMainTable",
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "systemMainTable",
								selectOnFocus: true,
								width: 200
		                	});
		                	
		                	var newPanel = new Ext.form.FormPanel({
		                		layout: 'table',
								height : 250,
								width : 'auto',
								frame : true,
								layoutConfig: {columns: 2},	    		
								items:[
									{html: "�������:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
									panelNameField,		
									{html: "������:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
									panelTitleField,		
									{html: "ѡ��ģ���Ӧ��ϵͳ����:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
									sysMainTabel,
									{html: "ѡ���������:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
									panelType
									]
		                	});
		                	
		                	if(!win){
			                	win = new Ext.Window({
									id : 'newWin',
									title : "�½���崰��",
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items: newPanel,								
									buttons:[
					        	       {	xtype:'button',
					        		        handler:function(){
								        		Ext.Ajax.request({
													//����action�д洢�ķ�����												
													url: webContext+'/pageModel/pageModelManage.do?methodCall=newPagePanelOfPageModel&pageModelId='+moduleId+'&parentPagePanelId='+parenteId+'&order='+order,
													params : newPanel.form.getValues(),
													method : 'post',
													//�洢�ɹ�����õĺ�����
													success : function(response) {
														var result = Ext.decode(response.responseText);													
														if(result.failure){
															Ext.MessageBox.alert("��ʾ��Ϣ��", "��������ʧ�ܣ�ԭ����һ�����Ų������ڶ��ƽ̨��������������ͬ��ֵ");
															win.close();															
															node.reload();
														}else{
															Ext.MessageBox.alert("��ʾ��Ϣ��", "����ɹ�");
														 	win.close();														 	
														 	node.reload();
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
		         		text : '�༭',		                
		                handler : function(){
		                //���û���ʾ�Ƿ���ʾ�����
		                	var win;		                	
		                	var panelType = new Ext.form.ComboBox({		                		
		                		 store : new Ext.data.JsonStore({
									url: webContext+'/pageModel/pageModelManage.do?methodCall=findPanelTypeByEntity',
									fields: ['type','name'],
							    	root:'data',									
									sortInfo: {field: "type", direction: "ASC"}
								}),
								valueField : "type",
								displayField : "name",
					            emptyText:Ext.getCmp("tree").typeName,
					            //value : Ext.getCmp("tree").typeName,
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
		                	var isDisplay = new Ext.form.ComboBox({

		                		store : new Ext.data.JsonStore({
									url: webContext+'/pageModel/pageModelManage.do?methodCall=idDisplayByPagePanel&id='+parenteId,
									fields: ['display','name'],
							    	root:'data',		
									sortInfo: {field: "display", direction: "ASC"}
								}),
								valueField : "display",
								displayField : "name",
								emptyText:Ext.getCmp("tree").display,
								forceSelection : true,
								hiddenName : "display",
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "display",
								selectOnFocus: true,
								width: 200
		                	});
		                	var isTitleDisplayFlag = new Ext.form.ComboBox({
		                		store : new Ext.data.JsonStore({
									url: webContext+'/pageModel/pageModelManage.do?methodCall=isTitleDisplayByPagePanel&id='+parenteId,
									fields: ['titleDisplay','name'],
							    	root:'data',		
									sortInfo: {field: "titleDisplay", direction: "ASC"}
								}),
								valueField : "titleDisplay",
								displayField : "name",
								emptyText:Ext.getCmp("tree").titleDisplay,
								forceSelection : true,
								hiddenName : "titleDisplay",//������̬��ȡ���ݴ洢����store�е�����ֵ�������е�ֵ�����Ǻͺ�̨��ô������
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "titleDisplay",
								selectOnFocus: true,
								width: 200
		                	});
		                	var readonly = new Ext.form.ComboBox({
		                		store : new Ext.data.JsonStore({
									url: webContext+'/pageModel/pageModelManage.do?methodCall=idreadonlyByPagePanel&id='+parenteId,
									fields: ['readonly','name'],
							    	root:'data',		
									sortInfo: {field: "readonly", direction: "ASC"}
								}),
								valueField : "readonly",
								displayField : "name",
								emptyText:Ext.getCmp("tree").readonly,
								forceSelection : true,
								hiddenName : "readonly",//������̬��ȡ���ݴ洢����store�е�����ֵ�������е�ֵ�����Ǻͺ�̨��ô������
								editable : false,
								triggerAction : 'all', 
								lazyRender: true,
					            typeAhead: true,
								allowBlank : true,
								name : "readonly",
								selectOnFocus: true,
								width: 200
		                	});			                			                	
		               	var editForm = new Ext.form.FormPanel({
		                			
									layout: 'table',
									height : 250,
									width : 'auto',
									frame : true,
									layoutConfig: {columns: 2},	    		
									items:[				
										{html: "�������:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										panelType,										
										{html: "�Ƿ�ֻ��:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										readonly,
										{html: "�Ƿ���ʾ������:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
										isTitleDisplayFlag,
										{html: "�Ƿ�ɼ�:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										isDisplay
										]
							});													
		                	if(!win){
			                	win = new Ext.Window({
									id : 'editWin',
									title : "���˵��༭����",
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items: editForm,								
									buttons:[
					        	       {	xtype:'button',
					        		        handler:function(){
								        		Ext.Ajax.request({
													//����action�д洢�ķ�����												
													url: webContext+'/pageModel/pageModelManage.do?methodCall=modifyAndSavePanelMessage&clazz=com.zsgj.info.appframework.pagemodel.entity.PageModelPanel&panelId='+node.id,
													params : editForm.form.getValues(),
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
							if(ret=="yes" && node.id!='-100'){
					          var nodeId = node.id;
					          DWREngine.setAsync(false);
					          PageModelTemplateManager.ajaxDeletePageModelPanel(nodeId,moduleId);
					            DWREngine.setAsync(true);
					          //alert(node.parentNode.id);
					          //root.reload();
					          node.parentNode.reload();
					        }else if(node.id=='-100'){
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
		this.modId = modId;
		this.typeName ='';
		this.display ='';
		this.titleDisplay = '';
		this.readonly ='';
		//������ģ��ʱ��Ҫ�������text:������������
		this.root = new Ext.tree.AsyncTreeNode({
			text: pageModuleName,
			draggable: false,
			id: '-100'     		
		}),
		
		//���ݴӱ��õ����ݵ�id ��ҳ��ģ���id �����غ�̨����
		this.loader = new Ext.tree.TreeLoader({
			dataUrl:webContext+'/pageModel/pageModelManage.do?methodCall=loadPageModel'
			 
		});
		
		this.loader.on('beforeload', function(treeloader, node) {
					
					treeloader.baseParams = {
						id : node.id,						
						mid : this.modId     //����������������ʱ��id
					};
					

		}, this);
		
						 
		PagteModelTreePanel.superclass.initComponent.call(this);
	}
});


	
	