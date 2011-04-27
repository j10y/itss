<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Ȩ�޹���</title>
		<%@include file="/includefiles.jsp"%>
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/grid-examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resources/css/crm-grid.css" />
		<script type="text/javascript" src="../extEngine/examples.js"></script>
		
<script type="text/javascript">
   //�޸Ĵ��ڵ�Ȩ�޺���ԴID,�Լ��޸�����ͬ������̨(����޸�����ֻ�е�һ�ε��������ֻ��һ���ܼ�������)	
   function loadData(form,id){
    	form.getForm().load({url:'/crm/admin/authorizationManage.do?methodCall=findAuthorization&id='+id,waitMsg:'loading...'});
  }		  
   function reset(f) {
       f.form.reset();
  }
  var mwin;  //Ȩ���޸Ĵ���
  var mForm;
  function modify(rid){ 
  	 
     if(!mForm){	
	  var mForm = new Ext.FormPanel({
			   baseCls: 'x-plain',
			   labelWidth: 95,
			   defaultType: 'textfield',
			   labelAlign : 'right',
			   reader : new Ext.data.JsonReader({
				   root : 'authList',
				   successProperty : '@success'
			   }, 
			  [ {name: 'name', mapping:'name'},
				{name: 'keyName', mapping:'keyName'},
				{name: 'resourceName', mapping:'resourceName'},
				{name: 'id', mapping:'id'}
			 ]
			),					
			 items: [
			        {   fieldLabel: '��Ȩ����',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: 'Ȩ��',
			            name: 'keyName',
			            anchor: '100%' 
			        },{
			            fieldLabel: '��Դ',
			            name: 'resourceName',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '��ȨID',
					    name : 'id'
					}
					]
		 });
	    }	 

	    if(!mwin){  
				mwin = new Ext.Window({
				             el:'modify-win',
				             layout:'fit',
				             title:'�޸���Ȩ',
				             width:400,
				             height:160,
				             closeAction:'hide',
				             plain: false,
				             items: mForm,
				             buttons: [{
				             	text:'����',
				             	handler:function(){ 
				             		var name = Ext.get('name').dom.value;
									if(name == ''){
										 Ext.MessageBox.alert('����', '��Ȩ���Ʋ���Ϊ�գ�');
										 Ext.get('name').focus();
										   	return;
									}
									var keyName = Ext.get('keyName').dom.value;
									if(keyName == ''){
										 Ext.MessageBox.alert('����', 'Ȩ�޹ؼ��ֲ���Ϊ�գ�');
										 Ext.get('keyName').focus();
										   	return;
									}
									var resourceName = Ext.get('resourceName').dom.value;
									if(resourceName == ''){
										 Ext.MessageBox.alert('����', '��Դ����Ϊ�գ�');
										 Ext.get('resourceName').focus();
										   	 return;
									}	   
				             	mForm.getForm().submit({
									             url:'${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=saveAuthorization', 
									             waitMsg:'Saving Data...',
									             success: function (form, action){
									                //alert(action.result.resource);
									                 alert("success");
									                 reset(mForm);
									                 mwin.hide();
									              },
									              failure: function (form, action) {
									                alert("failure");
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
		      mwin.show(this);
		      loadData(mForm,rid);
    } //modify end	
  /////////////////////////////////////////////////////////////////////////////////////////
  
  var grid;
  var store;
  Ext.onReady(function(){
  
	  Ext.Ajax.request({
		  url : '${pageContext.request.contextPath}/admin/authorizationManage.do', 
		  params : {methodCall : 'listAuthorizations'},
		  method: 'POST',
		  success: function(result, request){
		   //alert(result.responseText);
		  var responseArray = Ext.util.JSON.decode(result.responseText);	
			  if(responseArray.success){     
				var cm = new Ext.grid.ColumnModel([
				      {	
				        id : "name",
				        header: "��Ȩ����",
				        sortable: true,
				        hideable: false,
				        dataIndex: "name",
				        width : 200,
				        editor:new Ext.form.TextField()
				      },
			          {
			            id : "keyName",
			            header: "Ȩ��",
			        	sortable: true,
				        hideable: false,
			            dataIndex: "keyName",
			        	width : 200,
			        	editor:new Ext.form.TextField() 
			          },
			         {
			        	id : "resourceName",
			        	header: "��Դ",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "resourceName",
			        	width : 200,
			        	editor:new Ext.form.TextField() 
			         },
			        {
			        	id : "modify",
			        	header: "�޸�",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "id",
			        	width : 100,
			        	renderer: function(value){
			        	    var tempValue = value;
				        	return '<input type="button" id='+tempValue+' value="�޸�" onclick="modify(this.id);">';
			        	}
			        },
			        {
			        	id : "delete",
			        	header: "ɾ��",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "id",
			        	width : 100,
			        	renderer: function(value){
			        	    var tempValue = value;
				        	return '<input type="button" id='+tempValue+' value="ɾ��" onclick="doDel(this.id);">';
			        	}
			        }
			  	  ]);
	 var rightStore = new Ext.data.JsonStore({
			    	url: '${pageContext.request.contextPath}/admin/rightManage.do?methodCall=getRight',
			    	fields: ['id','keyName'],
				  	root:'rightList',
				  	sortInfo:{field: "id", direction: "ASC"}
	 });
	 var resourceStore = new Ext.data.JsonStore({
			    	url: '${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=getResource',
			    	fields: ['rid','rname'],
				  	root:'resourceList',
				  	sortInfo:{field: "rid", direction: "ASC"}
	 });		  	  		
	 store = new Ext.data.JsonStore({
							root : 'authorizations',
							data : responseArray,
							fields: ['name','keyName','resourceName','id'],
							autoLoad:true
					});	
	 var form = new Ext.form.FormPanel({
			       baseCls: 'x-plain',
			       labelWidth: 95,
			       defaultType: 'textfield',
			       labelAlign : 'right',
			       items: [
			           new Ext.form.ComboBox({ 
			        		name:'rightSelect',
			                fieldLabel:'Ȩ��',   
			                editable:false,   
			                valueField:'id',   
			                displayField:'keyName',
			                triggerAction : 'all',  
			                mode: 'remote',   
			                anchor: '100%',
			                store: rightStore,
			                hiddenName:'rightId',
			                listeners:{select:{fn:function(combo, value) {
					                       Ext.get('rightId').dom.value = Ext.get('rightSelect').dom.value;
					                   }}
					        }
            		  }),
            		new Ext.form.ComboBox({ 
			        		name:'resourceSelect',
			                fieldLabel:'��Դ',   
			                editable:false,   
			                valueField:'rid',   
			                displayField:'rname',
			                triggerAction : 'all',  
			                mode: 'remote',   
			                anchor: '100%',
			                store: resourceStore,
			                hiddenName:'resourceId',
			                listeners:{select:{fn:function(combo, value) {
					                       Ext.get('resourceId').dom.value = Ext.get('resourceSelect').dom.value;
					                   }}
					        }
            		}),{
			            fieldLabel: '��Ȩ����',
			            name: 'name',
			            anchor:'100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '��ȨID',
					    name : 'id'
					}]
			    }); 		
		    //var win = Ext.get('formWin');
		    var win;	        
			grid = new Ext.grid.GridPanel({
							renderTo : "authorization",
							frame:true,
							width: 840,
							height: 300,
							clicksToEdit: 1,
							collapsible: true,
							animCollapse: false,
							trackMouseOver: false,
							iconCls: 'icon-grid',
							title : " ��Ȩ����",
							autoHeight: true,
							cm : cm,
							ds : store,
							tbar: [
								{
								text: '�����Ȩ��¼',
								iconCls: 'add',
								handler : function(){
								   if(!win){
									  win = new Ext.Window({
									    id: 'formWin',
										el:'show-win',
									    layout:'fit',
										title:'��Ӽ�¼',
									    width:400,
									    height:160,
										closeAction:'hide',
										plain: true,
										items:form,
										buttons: [{
											text:'����',
											handler:function(){
												var rightId = Ext.get('rightId').dom.value;
												if(rightId == ''){
												    Ext.MessageBox.alert('����', 'Ȩ�޲���Ϊ�գ�');
												    Ext.get('rightId').focus();
														 return;
												}
												var resourceId = Ext.get('resourceId').dom.value;
												if(resourceId == ''){
												    Ext.MessageBox.alert('����', '��Դ����Ϊ�գ�');
												    Ext.get('resourceId').focus();
														 return;
												}
												var name = Ext.get('name').dom.value;
												if(name == ''){
													Ext.MessageBox.alert('����', '��Ȩ���Ʋ���Ϊ�գ�');
													Ext.get('name').focus();
														  return;
												}
								 form.getForm().submit({
										url:'${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=saveAuthorization', 
										waitMsg:'Saving Data...',
										success: function(form, action){
										  if(action.result.success==true){
										     alert('1111');
										  }
										  alert(action.result.success);
											var p = new Plant({
											name: Ext.get('name').dom.value,
											rightId: Ext.get('rightId').dom.value,
											resourceId: Ext.get('resourceId').dom.value,
											id: Ext.get('id').dom.value
										   });
										   alert("22");
											 grid.stopEditing();
											 store.insert(0, p);
											 reset(form);
											 win.hide();
										},
										failure: function (form, action) {
											Ext.MessageBox.alert('����', '�����Ȩ��¼ʧ��!');			
										}
									}); 
								}
							},
						{
							text: 'ȡ��',
							autoShow :'false',
							handler: function(){
									reset(form);
									win.hide();
									//win.destroy(); 
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
	 function doDel(id){  //id:Ȩ��id
	    //alert(id);
	     var record = grid.getSelectionModel().getSelected();
         Ext.MessageBox.confirm('ȷ��ɾ��', '�����Ҫɾ����ѡ��Ȩ��?', 
               function(btn) {
                 if(btn == 'yes'){
                    Ext.Ajax.request({
						url : '${pageContext.request.contextPath}/admin/authorizationManage.do', 
					    params : { methodCall : 'removeAuthorization',
					    		   id : id
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
<div id="authorization"></div>
<div id="show-win"></div>
<div id="modify-win"></div>
</body>
</html>