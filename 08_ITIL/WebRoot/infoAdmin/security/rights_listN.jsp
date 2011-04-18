<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>��Դ�б�</title>
		<%@include file="/includefiles.jsp"%>
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/grid-examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resources/css/crm-grid.css" />
		<script type="text/javascript" src="../extEngine/examples.js"></script>
		
<script type="text/javascript">
  
  function loadData(form,id){
    	form.getForm().load({url:'/crm/admin/rightManage.do?methodCall=findRight&id='+id});
  }	
  function reset(f) {
       f.form.reset();
  }
  var mwin;  //Ȩ���޸Ĵ���
  var mForm;
  function modify(id){ 
     //alert(id);
     if(!mForm){	
	    mForm = 
	 		new Ext.FormPanel({
			   baseCls: 'x-plain',
			   labelWidth: 95,
			   defaultType: 'textfield',
			   labelAlign : 'right',
			   reader : new Ext.data.JsonReader({
				   root : 'right',
				   successProperty : '@success'
			   }, 
			  [ {name: 'name', mapping:'name'},
				{name: 'keyName', mapping:'keyName'},
				{name: 'descn', mapping:'descn'},
				{name: 'id', mapping:'id'}
			 ]
			),					
			 items: [
			        {   fieldLabel: 'Ȩ������',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: 'Ȩ�޹ؼ���',
			            name: 'keyName',
			            anchor: '100%' 
			        },{
			            fieldLabel: '����',
			            name: 'descn',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : 'Ȩ��ID',
					    name : 'id'
					}
					]
		 });
	   } 
		 
		 //alert(mwin);
	     if(!mwin){  
				mwin = new Ext.Window({
				             el:'modify-win',
				             layout:'fit',
				             title:'Ȩ���޸�',
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
										 Ext.MessageBox.alert('����', 'Ȩ�����Ʋ���Ϊ�գ�');
										 Ext.get('name').focus();
										   	return;
									}
									var keyName = Ext.get('keyName').dom.value;
									if(keyName == ''){
										 Ext.MessageBox.alert('����', 'Ȩ�޹ؼ��ֲ���Ϊ�գ�');
										 Ext.get('keyName').focus();
										   	return;
									}
									var descn = Ext.get('descn').dom.value;
									if(descn == ''){
										 Ext.MessageBox.alert('����', '��������Ϊ�գ�');
										 Ext.get('descn').focus();
										   	 return;
									}	   
				             	mForm.getForm().submit({
									             url:'${pageContext.request.contextPath}/admin/rightManage.do?methodCall=saveRight', 
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
		        mwin.show();
		        loadData(mForm,id);
    	} //modify end
  /////////////////////////////////////////////////////////////////////////////////////////
  var grid;
  var store;
  Ext.onReady(function(){
	  Ext.Ajax.request({
		  url : '${pageContext.request.contextPath}/admin/rightManage.do', 
		  params : {methodCall : 'listRights'},
		  method: 'POST',
		  success: function(result, request){
		   //alert(result.responseText);
		  var responseArray = Ext.util.JSON.decode(result.responseText);	
			  if(responseArray.success){     
				var cm = new Ext.grid.ColumnModel([
				      {	
				        id : "name",
				        header: "Ȩ������",
				        sortable: true,
				        hideable: false,
				        dataIndex: "name",
				        width : 200,
				        editor:new Ext.form.TextField()
				      },
			          {
			            id : "keyName",
			            header: "Ȩ�޹ؼ���",
			        	sortable: true,
				        hideable: false,
			            dataIndex: "keyName",
			        	width : 200,
			        	editor:new Ext.form.TextField() 
			          },
			         {
			        	id : "descn",
			        	header: "����",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "descn",
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
			store = new Ext.data.JsonStore({
							root : 'rights',
							data : responseArray,
							fields: ['name','keyName','descn','id'],
							autoLoad:true
					});	
			var form = new Ext.form.FormPanel({
			        baseCls: 'x-plain',
			        labelWidth: 95,
			        defaultType: 'textfield',
			        labelAlign : 'right',
			        items: [
			        {
			            fieldLabel: 'Ȩ������',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: 'Ȩ�޹ؼ���',
			            name: 'keyName',
			            anchor: '100%' 
			        },{
			            fieldLabel: '����',
			            name: 'descn',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : 'Ȩ��ID',
					    name : 'id'
					}]
			    }); 		
			var win;			        
			grid = new Ext.grid.GridPanel({
							renderTo : "right",
							frame:true,
							width: 840,
							height: 300,
							clicksToEdit: 1,
							collapsible: true,
							animCollapse: false,
							trackMouseOver: false,
							iconCls: 'icon-grid',
							title : " Ȩ�޹���",
							autoHeight: true,
							cm : cm,
							ds : store,
							tbar: [
								{
								text: '��Ӽ�¼',
								iconCls: 'add',
								handler : function(){
								   if(!win){
									   win = new Ext.Window({
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
												var name = Ext.get('name').dom.value;
												if(name == ''){
													Ext.MessageBox.alert('����', 'Ȩ�����Ʋ���Ϊ�գ�');
													Ext.get('name').focus();
														  return;
													}
												var keyName = Ext.get('keyName').dom.value;
												if(keyName == ''){
												    Ext.MessageBox.alert('����', 'Ȩ�޹ؼ��ֲ���Ϊ�գ�');
												    Ext.get('keyName').focus();
														 return;
												}
												var descn = Ext.get('descn').dom.value;
												if(descn == ''){
												    Ext.MessageBox.alert('����', '��������Ϊ�գ�');
												    Ext.get('descn').focus();
														 return;
												}
							Ext.Ajax.request({
								url : '${pageContext.request.contextPath}/admin/rightManage.do', 
							    params : {methodCall : 'saveRight',
							    		  name: Ext.get('name').dom.value,
										  keyName: Ext.get('keyName').dom.value,
										  descn: Ext.get('descn').dom.value
							    		 },
								method: 'POST',
								success: function(result, request){
									//alert(result.responseText);
								    var responseArray = Ext.util.JSON.decode(result.responseText);
									if(responseArray.success){
										var p = new Plant({
										   name: Ext.get('name').dom.value,
										   keyName: Ext.get('keyName').dom.value,
										   descn: Ext.get('descn').dom.value
										   //id: Ext.get('id').dom.value
										});	
										  grid.stopEditing();
										  store.insert(0, p);
										  Ext.MessageBox.alert('��ʾ','��¼����ɹ���');
										  reset(form);
										  win.hide();
									}
								 }					
							});
										/*form.getForm().submit({
										url:'${pageContext.request.contextPath}/admin/rightManage.do?methodCall=saveRight', 
										waitMsg:'Saving Data...',
										success: function (form, action){
										alert(action.result);
											var p = new Plant({
											name: Ext.get('name').dom.value,
											keyName: Ext.get('keyName').dom.value,
											descn: Ext.get('descn').dom.value,
											id: Ext.get('id').dom.value
										});
											 grid.stopEditing();
											 store.insert(0, p);
											 reset(form);
											 win.hide();
										},
										failure: function (form, action) {
											Ext.MessageBox.alert('����', '��Ӽ�¼ʧ��!');			
										}
									}); */
								}
							},
						{
							text: '�ر�',
							autoShow :'false',
							handler: function(){
									reset(form);
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
    function doDel(id){  //id:Ȩ��id
	    //alert(id);
	     var record = grid.getSelectionModel().getSelected();
         Ext.MessageBox.confirm('ȷ��ɾ��', '�����Ҫɾ����ѡȨ����?', 
               function(btn) {
                 if(btn == 'yes'){
                    Ext.Ajax.request({
						url : '${pageContext.request.contextPath}/admin/rightManage.do', 
					    params : { methodCall : 'removeRight',
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
<div id="right"></div>
<div id="show-win"></div>
<div id="modify-win"></div>
</body>
</html>