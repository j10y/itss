/*
 * Ŀ���������
 */
 
callBackFuns = function(result){
	if(!result){
		alert("���϶����ظ���ֵ�����������϶�");
		Ext.getCmp("tree").marker='1';
		//alert(Ext.getCmp("tree").marker+'==============');
	 	return ;
	}else{
		Ext.getCmp("tree").marker='0'
	}
}
 
//Ŀ��ڵ㣬�ӽڵ㣬��ѡ�������
var ddFunction = function(node, refNode, selections) { 
	
    for(var i = 0; i < selections.length; i ++) {
//    	alert(node.indexOf(node.lastChild));
//    	var order = node.indexOf(node.lastChild)+1;
    	
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
         //������Ŀ�Ƿ���������(��Ҷ�ӽ���cls��������־)
         //var flag = record.get('cls');
         //var nodeParent = refNode;
         //�ڵ����
         //alert()
         var order = node.indexOf(node.lastChild) ;//
     	 //alert(pagePanelId);
         
//     PageModelTemplateManager.ajaxTestRepeatePanel(parentId, pagePanelId, pageModelId,callBackFuns);   

     //ͬ�����浽���ݿ�   
     DWREngine.setAsync(false);
     PageModelTemplateManager.ajaxSavePageModelPanel(id, parentId, name, pagePanelId, pageModelId,order);
     DWREngine.setAsync(true);    
     //���ڵ��������
     node.reload();     
    }   
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
	height: 320,
	enableDD: true,   
    ddGroup: "tgDD", 
    removeFlag: false,
    listeners: { //��ק֮ǰ�ᴥ����������Ա������ק������
       	
        beforenodedrop: function(dropEvent) {   
        	var node = dropEvent.target;    // Ŀ����   
            var data = dropEvent.data;      // ��ק������   
            var point = dropEvent.point;    // ��ק��Ŀ�����λ��     
            var mId = this.modId ;               					
		    
		    // ���data.nodeΪ�գ�����tree�������ק�����Ǵ�grid��tree����ק����Ҫ����   
            if(!data.node) {  
            	
            	for(var i = 0; i < data.selections.length; i++) {	 	    	    	
		        var record = data.selections[i];//���record��ʾ���ѡ�е�����
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
                        // ���ʱ��Ŀ����Ϊnode���ŵ��ӽ��Ϊ�յ��������   
                    	alert(node.id);
                    	ddFunction(node, null, data.selections);
                        
                        //��������Ϣ(templateItem:id,����/��չ��Id,mainTableItemId,ģ��Id)
                        break;   
                    case 'above':   
                        // ���뵽node֮�ϣ�Ŀ����Ϊnode��parentNode���ӽ��Ϊnode   
                    	alert(node.id);
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
		// ���ڵ�ɾ��ʱ�����¼�
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
		         text : 'ɾ�����',
		         handler : function(){
		         //ɾ�������Ϣ
		            Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�",function(ret){
					if(ret=="yes" && node.id!='100'){
			          var nodeId = node.id;
			          PageModelTemplateManager.ajaxDeletePageModelPanel(nodeId,moduleId);
			          alert(node.parentNode.id);
			          node.parentNode.reload();
			        }else if(node.id=='100'){
				    	alert("���治��ɾ�����ĸ��ڵ㣡");
				                             }});
		            }
		           },{
		                text : '�༭',		                
		                handler : function(){
		                //���û���ʾ�Ƿ���ʾ�����
		                	var win;
		                	var isDisplay = new Ext.form.ComboBox({
		                		 store : new Ext.data.SimpleStore({
									data:[['0','��'],['1','��']],
									fields: ['display','name']
								}),
								valueField : "display",
								displayField : "name",
					            emptyText: '��ѡ���Ƿ���ʾ',
								mode: 'local',
								forceSelection : true,
								hiddenName : "display",//������̬��ȡ���ݴ洢����store�е�����ֵ�������е�ֵ�����Ǻͺ�̨��ô������
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
		                	//alert("2");
		                	var editForm = new Ext.form.FormPanel({
		                			
									layout: 'table',
									height : 150,
									width : 'auto',
									frame : true,
									layoutConfig: {columns: 1},	    		
									items:[
										{html: "�Ƿ�ɼ�:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										isDisplay,
										{html: "�������:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
										panelType
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
//					        		        	alert(Ext.encode(editForm.form.getValues()));
//					        		        	return;
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
		           }]);
		             menu.showAt(e.getPoint()); 
		           }   
		}
	},	
    
	initComponent: function() {
		this.marker = '0';
		this.modId = modId;
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


	
	