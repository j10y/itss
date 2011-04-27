<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>��Դ����</title>
		<%@include file="/includefiles.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/grid-examples.css" />
		
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resources/css/crm-grid.css" />
		<script type="text/javascript" src="../extEngine/examples.js"></script>
		
<script type="text/javascript">
	//�޸���Դ
	var moduleData = new Ext.data.JsonStore({
			    	url: '${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=getMoudle',
			    	fields: ['id','name'],
				  	root:'moudleList',
				  	sortInfo:{field: "id", direction: "ASC"}
    });
    
    function loadData(form,id){
    	form.getForm().load({url:'/crm/admin/resourceManage.do?methodCall=findResource&id='+id,waitMsg:'loading ...'});
    	return form;
    }
	
	function reset(f) {
        	f.getForm().reset();
    }
    var mwin;   //��Դ�޸Ķ�Ӧ�Ĵ���
    var mForm;
    function modify(rid){ 
    
        //alert(rid);
     if(!mForm){   	
	    mForm = new Ext.FormPanel({
			        baseCls: 'x-plain',
			        labelWidth: 95,
			        defaultType: 'textfield',
			        labelAlign : 'right',
			        reader : new Ext.data.JsonReader({
						root : 'resource',
						successProperty : '@success'
						}, 
						[ {name: 'mname', mapping:'mname'},
						  {name: 'rname', mapping:'rname'},
						  {name: 'type', mapping:'type'},
						  {name: 'className', mapping:'className'},
						  {name: 'methodName', mapping:'methodName'},
						  {name: 'rid', mapping:'rid'},
						  {name: 'mid', mapping:'moduleId'}	
					   ]
					   ),					
			        items: [			        	
			        	new Ext.form.ComboBox({ 
			        		name:'moduleSelect',
			                fieldLabel:'����ģ��',   
			                editable:false,   
			                valueField:'id',   
			                displayField:'mname', 
			                triggerAction : 'all',  
			                mode: 'remote',   
			                anchor: '100%',
			                store: new Ext.data.JsonStore({
						    	url: '${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=getMoudle',
						    	fields: ['id','name'],
							  	root:'moudleList',
							  	sortInfo:{field: "id", direction: "ASC"}
			    			}),
			                hiddenName:'moduleId',
			                listeners:{select:{fn:function(combo, value) {
					                       Ext.get('moduleId').dom.value = Ext.get('moduleSelect').dom.value;
					                   }}
					        }
            		}),
			        {
			            fieldLabel: '��Դ����',
			            name: 'rname',
			            anchor:'100%'  
			        },{
			            fieldLabel: '��Դ����',
			            name: 'type',
			            anchor: '100%' 
			        },{
			            fieldLabel: '��/ģ����',
			            name: 'className',
			            anchor: '100%'  
			        },{
			            fieldLabel: '����/URL��׺',
			            name: 'methodName',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '��ԴID',
					    name : 'rid'
					},{
					    xtype : 'hidden',
					    fieldLabel : 'ģ��ID',
					    name : 'moduleId'
					}
					]
			    });
		}	    
		 //var xForm =  loadData(mForm,rid);
	     if(!mwin){  
				mwin = new Ext.Window({
				             el:'modify-win',
				             layout:'fit',
				             title:'��Դ�޸�',
				             width:500,
				             height:240,
				             closeAction:'hide',
				             plain: false,
				             items: mForm,
				             buttons: [{
				             	text:'����',
				             	handler:function(){   
				             		 		var name = Ext.get('name').dom.value;
										   	if(name == ''){
										   	  	 Ext.MessageBox.alert('����', '��Դ���Ʋ���Ϊ�գ�');
										   	  	 Ext.get('name').focus();
										   	  	 return;
										   	 }
										   	var type = Ext.get('type').dom.value;
										   	if(type == ''){
										   	  	 Ext.MessageBox.alert('����', '��Դ���Ͳ���Ϊ�գ�');
										   	  	 Ext.get('type').focus();
										   	  	 return;
										   	}
										   var className = Ext.get('className').dom.value;
										   	if(className == ''){
										   	  	 Ext.MessageBox.alert('����', '��/ģ��������Ϊ�գ�');
										   	  	 Ext.get('className').focus();
										   	  	 return;
										   }
										   var methodName = Ext.get('methodName').dom.value;
										   	if(methodName == ''){
										   	  	 Ext.MessageBox.alert('����', '����/URL��׺����Ϊ�գ�');
										   	  	 Ext.get('methodName').focus();
										   	  	 return;
										   }
				             		 mForm.getForm().submit({
									             url:'${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=saveResource', 
									             waitMsg:'Saving Data...',
									             success: function (form, action){
									                //alert(action.result.resource);
									                 
									                 reset(mForm);
									                 mwin.hide();
									              },
									              failure: function (form, action) {
									              	reset(mForm);
									              	mwin.hide();
												  }
									             });
								 }
							   },	 
				               {
				                text: 'ȡ��',
				                handler: function(){
				                	reset(mForm);
				                    mwin.hide();
				                }
				             }]
				          });
		       	}
		        mwin.show();
		        loadData(mForm,rid);
		        
    	} //modify end
   
    //////////////////////////////	
    var grid;
    var store;		
	Ext.onReady(function(){
	   	  Ext.QuickTips.init();  			   	   
		  Ext.Ajax.request({
			url : '${pageContext.request.contextPath}/admin/resourceManage.do', 
		    params : { methodCall : 'listResources' },
			method: 'POST',
			success: function(result, request){
				try{
					var responseArray = Ext.util.JSON.decode(result.responseText);
					//alert(result.responseText);
				}catch(e){
					alert(e);
				}
				//alert(responseArray.success);
				if(responseArray.success==true){ 
				    var colM = new Ext.grid.ColumnModel([
				        {	
				            id : "name",
				            header: "��Դ����",
				            sortable: true,
				            hideable: false,
				            dataIndex: "rname",
				            width : 160,
				            editor:new Ext.form.TextField()
				        },
			        	{
			        		id : "type",
			        		header: "��Դ����",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "type",
			        		width : 100,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		id : "className",
			        		header: "��/ģ����",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "className",
			        		width : 160,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		id : "methodName",
			        		header: "����/URL��׺",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "methodName",
			        		width : 100,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		id : "moduleName",
			        		header: "����ģ��",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "mname",
			        		width : 160,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		header: "�޸�",
			        		width : 60,
			        		sortable: true,
				            hideable: false,
				            dataIndex: "rid",
			        	    renderer: function(value){
			        	    	var tempValue = value;
				        		return '<input type="button" id='+tempValue+' value="�޸�" onclick="modify(this.id);">';
			        	    }
			        	},
			        	{
			        		header: "ɾ��",
			        		width : 50,
			        	    sortable: true,
				            hideable: false,
				            dataIndex: "rid",
			        		renderer: function(value){
			        		    var tempValue = value;
			        		    return '<input type="button" id='+tempValue+' value="ɾ��" onclick="doDel(this.id);">';
			        		}
			        	}
			  		]);
		store = new Ext.data.JsonStore({
								  root : 'jsonString',
								  data : responseArray,
								  fields: ['rname','type','className','methodName','mname','rid'],
								  autoLoad:true
		});
		
	  var Plant = Ext.data.Record.create([
			           {name: 'rname', type: 'string'},
			           {name: 'type', type: 'string'},
			           {name: 'className', type: 'string'},
			           {name: 'methodName', type: 'string'},
			           {name: 'rid', type: 'int'}
	 ]);
			    
     var win;
     //var reset = function(f) {
     //   	f.form.reset();
     //};
        
    	var cobDate = new Ext.data.JsonStore({
			    	url: '${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=getMoudle',
			    	fields: ['id','name'],
				  	root:'moudleList',
				  	sortInfo:{field: "id", direction: "ASC"}
		});
		
        var form = new Ext.form.FormPanel({
			        baseCls: 'x-plain',
			        labelWidth: 95,
			        url:'save-form.php',
			        defaultType: 'textfield',
			        labelAlign : 'right',
			        items: [
			        new Ext.form.ComboBox({ 
			        		name:'moduleSelect',
			                fieldLabel:'����ģ��',   
			                editable:false,   
			                valueField:'id',   
			                displayField:'name',
			                triggerAction : 'all',  
			                mode: 'remote',   
			                anchor: '100%',
			                store:cobDate  ,
			                hiddenName:'moduleId',
			                listeners:{select:{fn:function(combo, value) {
					                       Ext.get('moduleId').dom.value = Ext.get('moduleSelect').dom.value;
					                   }}
					        }
            		}),
			        {
			            fieldLabel: '��Դ����',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: '��Դ����',
			            name: 'type',
			            anchor: '100%' 
			        },{
			            fieldLabel: '��/ģ����',
			            name: 'className',
			            anchor: '100%'  
			        },{
			            fieldLabel: '����/URL��׺',
			            name: 'methodName',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '��ԴID',
					    name : 'resourceId',
					    id : 'rid' 
					}]
			    }); 
	 grid = new Ext.grid.GridPanel({
					renderTo : "grid",
					frame:true,
					width: 860,
					height: 500,
					clicksToEdit: 1,
					collapsible: true,
					animCollapse: true,
					trackMouseOver: true,
					iconCls: 'icon-grid',
					title : "��Դ����",
					cm : colM,
					ds : store,
					autoHeight: true,
					tbar: [
					{
					  text: '�����Դ',
					  iconCls: 'add',
					  handler : function(){
						 if(!win){
							win = new Ext.Window({
							  el:'insert-win',
							  layout:'fit',
							  title:'�����Դ',
							  width:400,
							  height:200,
							  closeAction:'hide',
							  plain: true,
							  items:form,
							  buttons: [{
									     text:'�ύ',
									     handler:function(){
									     	var name = Ext.get('name').dom.value;
										   	if(name == ''){
										   	  	 Ext.MessageBox.alert('����', '��Դ���Ʋ���Ϊ�գ�');
										   	  	 Ext.get('name').focus();
										   	  	 return;
										   	 }
										   	var type = Ext.get('type').dom.value;
										   	if(type == ''){
										   	  	 Ext.MessageBox.alert('����', '��Դ���Ͳ���Ϊ�գ�');
										   	  	 Ext.get('type').focus();
										   	  	 return;
										   	}
										   var className = Ext.get('className').dom.value;
										   	if(className == ''){
										   	  	 Ext.MessageBox.alert('����', '��/ģ��������Ϊ�գ�');
										   	  	 Ext.get('className').focus();
										   	  	 return;
										   }
										   var methodName = Ext.get('methodName').dom.value;
										   	if(methodName == ''){
										   	  	 Ext.MessageBox.alert('����', '����/URL��׺����Ϊ�գ�');
										   	  	 Ext.get('methodName').focus();
										   	  	 return;
										   }
									        form.getForm().submit({
									             url:'${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=saveResource', 
									             waitMsg:'Saving Data...',
									             success: function (form, action){
									                alert(action.result);
									                
									                 /*var p = new Plant({
													      rname: Ext.get('name').dom.value,
													      type: Ext.get('type').dom.value,
													      className: Ext.get('className').dom.value,
													      methodName: Ext.get('methodName').dom.value,
													      rid: 0
													   });
													 grid.stopEditing();
													 store.insert(0, p); */
													 
													 //var returnValue = Ext.util.JSON.decode(action.result);
													 //alert(action.result);
													 //var id = returnValue.resource;
													 var p = new Plant({
													      rname: Ext.get('name').dom.value,
													      type: Ext.get('type').dom.value,
													      className: Ext.get('className').dom.value,
													      methodName: Ext.get('methodName').dom.value,
													      rid: id
													   });
													 grid.stopEditing();
													 store.insert(0, p);
													 //reset(form);
													 win.hide();
									              },
									              failure: function (form, action) {
									              			
									                   			Ext.MessageBox.alert('����', '�����Դʧ��!');
									                 			
															}
									             });
									           }
									                    
									         },
									         {
									           text: '�ر�',
									           autoShow :'false',
									           handler: function(){
									             //reset(form);
									           	 win.hide();
									         }
									   }]
								});
						      }
							   win.show(this);
						    }
						}]        
					});		                              
			      }else{      
			            Ext.Msg.alert('����','��ȡ���ݳ���');          
			       }      
			     }      
			});        			      
	});
	function doDel(rid){  //rid:��ԴID
	     //alert(rid);
	     var record = grid.getSelectionModel().getSelected();
         Ext.MessageBox.confirm('ȷ��ɾ��', '�����Ҫɾ����ѡ��Դ��?', 
               function(btn) {
                 if(btn == 'yes'){
                    Ext.Ajax.request({
						url : '${pageContext.request.contextPath}/admin/resourceManage.do', 
					    params : { methodCall : 'removeResource',
					    		   id : rid
					    		 },
						method: 'POST',
						success: function( result, request){
						    var returnResult = Ext.util.JSON.decode(result.responseText); 
							if(returnResult.success==true){ 
		                             store.remove(record);
		                             store.reload();
		                    }
                       }
                    });
                }
			 }) 
  }		
</script>
</head>
<body>
<div id="grid"></div>
<div id="insert-win"></div>
<div id="modify-win"></div>
<div id="mGrid"></div>
</body>
</html>