/**
*portal style
*/
com.faceye.portal.PortalStyle={
	init:function(){
		 	 // create the Data Store
    var store = new Ext.data.Store({
        // load using script tags for cross domain, if the data in on the same domain as
        // this page, an HttpProxy would be better
        proxy: new Ext.data.HttpProxy({
            url: BP + 'portalStyleAction.do?method=index'
        }),

        // create reader that reads the Topic records
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'total',
            id: 'id',
            fields: [
               'id','name'
            ]
        }
        )
    });

    function renderTopic(value, p, record){
          //ȡ�õ�ǰ���ڵ���ļ�¼��ID
          var id=record.data.id;
          return String.format('<a href="#" onclick="onClickLink(\''+id+'\')">{0}</a>',record.get('name'));
    }
  
    function renderLast(value, p, r){
        return String.format('{0}<br/>by {1}', value.dateFormat('M j, Y, g:i a'), r.data['lastposter']);
    }
    
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.CheckboxSelectionModel(),{
        	id: 'id', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
        	header:'ID',
        	dataIndex:'id',
        	hidden:true
        },
        {
           header: "����",
           dataIndex: 'name',
           renderer:renderTopic
        }
        ]);

    // by default columns are sortable
    cm.defaultSortable = true;
    //����¼���޸ĵ�����window
    var win;
   //����༭����window
   var updateWin;
    var grid = new Ext.grid.GridPanel({
        el:'topic-grid',
        title:'��ʽ�б�',
        autoHeight:true,
        bodyStyle:'width:100%',
        loadMask:true,
        stripeRows: true,
        trackMouseOver:true,
        layoutConfig:{
        	  autoWidth:true
        },
        store: store,
        cm: cm,
        trackMouseOver:false,
        sm:new Ext.grid.CheckboxSelectionModel(),
        loadMask: true,
        viewConfig: {
            forceFit:true,
            enableRowBody:true,
            showPreview:true
        },
        bbar: com.faceye.ui.util.PaggingToolBar(15,store),
        tbar:[{
        	text:'���',
            tooltip:'Add a new row',
            iconCls:'default-tabs-tool-bar-add',
            handler:function(){
            	/**
            	 * ----------------Start Add button config-----------------------
            	 */      	 
            	if(!win){
            ����

                          
                 var innerForm=new Ext.FormPanel({
            	 	labelWidth: 80, // label settings here cascade unless overridden
                    url: BP + 'portalStyleAction.do?method=save',
                    frame:true,
                    // title: 'Simple Form',
                    bodyStyle:'padding:5px 5px 0',
                    width: 370,
//                    defaults: {width: 210},
                    renderTo:win,
                    layout:'form',
                    defaultType: 'textfield',
                    items: [{
                    	    name:'id',
                    	    hidden:true,
                    	    hideLabel:true
                          },
                          {
                             fieldLabel: '��ʽ����',
                             name: 'name',
                             width:300,
                             allowBlank:false,
                             vtypeText:'��ʽ���ֲ���Ϊ��'
                           }
                          ]
            	    });
            	   
//            	    innerForm.addListener('click', function(){alert('keup');});
            	    //����� ����ʽ�����ʱ,����click�¼�.
            	   
            		win=new Ext.Window({
            			layout:'fit',
            			//ģʽ����
            			modal:true,
            			width:450,
                        height:200,
                        closeAction:'hide',
                        plain: true,
                        title:'�������ʽ',
                        buttonAlign:'center',
                        buttons: [{
                           text:'ȷ��',
                           scope:com.faceye.portal.PortalStyle,
                           type:'submit',
                           disabled:false,
                           handler:function(){
                           innerForm.getForm().submit({
                           		method:'POST',
                           		params:{
                           		entityClass:'com.faceye.components.portal.dao.model.PortalStyle'	
                           		},
                           		waitMsg:'���ڱ�������...',
                           		//reset:'/faceye/portalStyleAction.do?method=generatorJSONList&entityClass=com.faceye.core.componentsupport.dao.model.DataType',
                           		success:function(form,action){
//                                    grid.getView().refresh();
                           			form.reset();
                           			Ext.Msg.alert('��ʽ����','��ʽ����ɹ�!');
                           			this.disabled=false; 
                           			win.hide();
                           			//���¼������ݵ�grid
                           			store.load();
                           		},
                           		failure:function(){
                           			Ext.Msg.alert('��ʽ����','��ʽ����ʧ��!');
                           			this.disabled=false;
                           		}
                           	 }
                           	);
                           }
                              },{
                           text: '����',
                           handler: function(){
                           	�� innerForm.getForm().reset();
                              win.hide();
                               }
                          },{
                          	text:'����',
                          	handler:function(){
                          		innerForm.getForm().reset();
                          	}
                          }
                          ]
            		});
            		win.add(innerForm);
            	}
        		win.show(this);
         
            	
            	 /**
            	  * ---------------End Add button config------------------------
            	  */
            	
               }
        },{
        	text:'�༭',
        	tooltip:'�༭ѡ�еļ�¼��һ��ֻ���Ա༭һ����',
            iconCls:'edit',
            handler:function(){         	
            	var selectionModel=grid.getSelectionModel();
            	//ȡ�ù�ѡ���˶�������¼��
            	var selectedCount=selectionModel.getCount();
            	if(selectedCount==0){
            		Ext.Msg.alert('�༭����','��û��ѡ���κ����ݣ���ѡ����Ҫ�༭�����ݣ�');
            		return;
            	}else if(selectedCount>1){
            		Ext.Msg.alert('�༭����','��ֻ��ѡ��һ�����ݣ�����ͬʱѡ��������ݣ���ѡ����Ҫ�༭�����ݣ�');
            		return;
            	}else{
            		//ȡ�ñ�ѡ�е�����
            		var record=selectionModel.getSelected();
            		//ȡ�ñ�ѡ�����ݵ�����
            		var id=record.id;   
                    
                    	
                    	var updateForm=new Ext.FormPanel({
            	 	labelWidth: 80, // label settings here cascade unless overridden
                    url: BP + 'portalStyleAction.do?method=save',
                    frame:true,
                    // title: 'Simple Form',
                    bodyStyle:'padding:5px 5px 0',
                    width: 450,
//                    defaults: {width: 230},
                    layout:'form',
                    defaultType: 'textfield',
                    reader:new Ext.data.JsonReader({
                        root: 'rows',
//                        totalProperty: 'total',
                        success:true,
                       fields: [
                             'id','name'
                            ]
                        }
                     ),
                    items: [
                           {
                           	xtype:'hidden',
                            name:'id'	
                            },
//                         parentIdText,
                         {
                             fieldLabel: '��ʽ����',
                             name: 'name',
                             allowBlank:false,
                             width:300
                           }
                          ]
            	    });
            	  
                    	updateWin=new Ext.Window({
            			layout:'fit',
            			//ģʽ����
            			modal:true,
            			width:450,
                        height:200,
                        closeAction:'hide',
                        plain: true,
                        buttonAlign:'center',
                        buttons: [{
                           text:'�ύ',
                           scope:com.faceye.portal.PortalStyle,
                           type:'submit',
                           disabled:false,
                           handler:function(){
                           updateForm.getForm().submit({
                           		method:'POST',
                           		params:{
                           			entityClass:'com.faceye.components.portal.dao.model.PortalStyle'
                           		},
                           		waitMsg:'���ڱ�������......',
                           		//reset:'/faceye/portalStyleAction.do?method=generatorJSONList&entityClass=com.faceye.core.componentsupport.dao.model.DataType',
                           		success:function(form,action){
//                                    grid.getView().refresh();	
                           			Ext.Msg.alert('��ʽ����','��ʽ����ɹ�!');
                           			form.reset();
                           			this.disabled=false; 
                           			updateWin.hide();
                           			//���¼������ݵ�grid
                           			store.load();
                           		},
                           		failure:function(){
                           			Ext.Msg.alert('��ʽ����','��ʽ����ʧ��!');
                           			this.disabled=false;
                           		}
                           	 }
                           	);
                           }
                              },{
                           text: '�ر�',
                           handler: function(){
                              updateWin.hide();
                               }
                          },{
                          	text:'����',
                          	handler:function(){
                          		updateForm.getForm().reset();
                          		updateWin.hide();
                          	}
                          }
                          ]
            		});
                    updateWin.add(updateForm);
            		updateForm.getForm().load({
            	    	url: BP + 'portalStyleAction.do?method=update&entityClass=com.faceye.components.portal.dao.model.PortalStyle&id='+id,
            	    	waitMsg:'���ڼ������ݣ����Ժ�...'
            	    });
                    updateWin.show(this);
//                    loadStore.load();
//                    alert(loadStore.getTotalCount());
//                    var loadRecord=loadStore.getAt(0);
//                    alert(loadRecord.name)
                    //alert(loadRecord.id);
                   
            	}
            }
        },{
        	text:'ɾ��',
            tooltip:'ɾ��ѡ�еļ�¼��һ�ο���ɾ��������',
            iconCls:'default-tabs-tool-bar-remove',
            handler:function(){
            	var selectionModel=grid.getSelectionModel();
            	//ȡ�ù�ѡ���˶�������¼��
            	var selectedCount=selectionModel.getCount();
            	if(selectedCount==0){
            		Ext.Msg.alert('ɾ������','��û��ѡ��Ҫɾ�������ݣ���ѡ����׼��ɾ��������');
            		return;
            	}else{
            		//ȡ��Ҫɾ�������ݵ�ID
            		Ext.Msg.confirm('ɾ������','��ȷ��Ҫɾ��ѡ�е�������?',function(btn,text){
            		if(btn=='yes'){
            		var records = selectionModel.getSelections();
            		var _ids='';
            		for(var i=0;i<records.length;i++){
            			_ids+=records[i].id;
            		    _ids+='_';
            		    }
	        //����ɾ�����ݵ�����		
            		       Ext.Ajax.request({
            			     url: BP + 'portalStyleAction.do?method=remove',
            			     failure:function(){
            				 Ext.Msg.alert('��ʽɾ��','��ʽɾ��ʧ�ܣ�');
            			    },
            			   success:function(){
            				 Ext.Msg.alert('��ʽɾ��','��ʽɾ���ɹ���');
            			    },
            			   params:{
            				entityClass:'com.faceye.components.portal.dao.model.PortalStyle',
            				ids:_ids
            			    }
            		       });
            		    store.load();
            		
//            		alert(nodesStore);
//            		alert(nodesStore);
            		
            			}else{
            				return;
            			}
            		});
            	}
            	
            }
        }
        ]
        
    });
    grid.render();
    // trigger the data store load
    store.load({params:{start:0, limit:15}});
    function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    }
	}
};
/**
 * ������ϸ��ʾ
 */
  function onClickLink(id){
    	var win;
    	if(!win){
    		win=new Ext.Window({
            			layout:'fit',
            			width:450,
                        height:200,
                        //ģʽ����
                        modal:true,
                        closeAction:'hide',
                        title:'��ʽ��ϸ',
                        plain: true,
                        buttonAlign:'center',
                        buttons:[{
                        	text:'�ر�',
                            type:'submit',
                            disabled:false,
                            handler:function(){
                            	   win.hide(this);
                                    }
                               }
                        	]
                        }
                        );
               var tpl=new Ext.Template(
               '<p>��ʽ����:{name}</p>' 
               );
               var nodes=new Ext.data.JsonStore({
                         url: BP + 'portalStyleAction.do?method=detail',
                         root : 'rows',
			fields : ['id', 'name']
            		});
//            		tpl.overwrite(win.body,nodes);
            		nodes.load({
            			params:{
            				id:id,
            				entityClass:'com.faceye.components.portal.dao.model.PortalStyle'
            			},
            			callback:function(r,options,success){
            				var record=nodes.getAt(0);
            				tpl.overwrite(win.body, record.data);
            			}
            		});
            		
    	}
    	win.show(this);
      };


 
