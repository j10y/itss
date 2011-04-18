<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>权限管理</title>
		<%@include file="/includefiles.jsp"%>
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/grid-examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resources/css/crm-grid.css" />
		<script type="text/javascript" src="../extEngine/examples.js"></script>
		
<script type="text/javascript">
   //修改窗口的权限和资源ID,以及修改数据同步到后台(点击修改两次只有第一次弹出窗体或只有一次能加载数据)	
   function loadData(form,id){
    	form.getForm().load({url:'/crm/admin/authorizationManage.do?methodCall=findAuthorization&id='+id,waitMsg:'loading...'});
  }		  
   function reset(f) {
       f.form.reset();
  }
  var mwin;  //权限修改窗体
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
			        {   fieldLabel: '授权名称',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: '权限',
			            name: 'keyName',
			            anchor: '100%' 
			        },{
			            fieldLabel: '资源',
			            name: 'resourceName',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '授权ID',
					    name : 'id'
					}
					]
		 });
	    }	 

	    if(!mwin){  
				mwin = new Ext.Window({
				             el:'modify-win',
				             layout:'fit',
				             title:'修改授权',
				             width:400,
				             height:160,
				             closeAction:'hide',
				             plain: false,
				             items: mForm,
				             buttons: [{
				             	text:'保存',
				             	handler:function(){ 
				             		var name = Ext.get('name').dom.value;
									if(name == ''){
										 Ext.MessageBox.alert('警告', '授权名称不能为空！');
										 Ext.get('name').focus();
										   	return;
									}
									var keyName = Ext.get('keyName').dom.value;
									if(keyName == ''){
										 Ext.MessageBox.alert('警告', '权限关键字不能为空！');
										 Ext.get('keyName').focus();
										   	return;
									}
									var resourceName = Ext.get('resourceName').dom.value;
									if(resourceName == ''){
										 Ext.MessageBox.alert('警告', '资源不能为空！');
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
				                text: '取消',
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
				        header: "授权名称",
				        sortable: true,
				        hideable: false,
				        dataIndex: "name",
				        width : 200,
				        editor:new Ext.form.TextField()
				      },
			          {
			            id : "keyName",
			            header: "权限",
			        	sortable: true,
				        hideable: false,
			            dataIndex: "keyName",
			        	width : 200,
			        	editor:new Ext.form.TextField() 
			          },
			         {
			        	id : "resourceName",
			        	header: "资源",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "resourceName",
			        	width : 200,
			        	editor:new Ext.form.TextField() 
			         },
			        {
			        	id : "modify",
			        	header: "修改",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "id",
			        	width : 100,
			        	renderer: function(value){
			        	    var tempValue = value;
				        	return '<input type="button" id='+tempValue+' value="修改" onclick="modify(this.id);">';
			        	}
			        },
			        {
			        	id : "delete",
			        	header: "删除",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "id",
			        	width : 100,
			        	renderer: function(value){
			        	    var tempValue = value;
				        	return '<input type="button" id='+tempValue+' value="删除" onclick="doDel(this.id);">';
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
			                fieldLabel:'权限',   
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
			                fieldLabel:'资源',   
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
			            fieldLabel: '授权名称',
			            name: 'name',
			            anchor:'100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '授权ID',
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
							title : " 授权管理",
							autoHeight: true,
							cm : cm,
							ds : store,
							tbar: [
								{
								text: '添加授权记录',
								iconCls: 'add',
								handler : function(){
								   if(!win){
									  win = new Ext.Window({
									    id: 'formWin',
										el:'show-win',
									    layout:'fit',
										title:'添加记录',
									    width:400,
									    height:160,
										closeAction:'hide',
										plain: true,
										items:form,
										buttons: [{
											text:'保存',
											handler:function(){
												var rightId = Ext.get('rightId').dom.value;
												if(rightId == ''){
												    Ext.MessageBox.alert('警告', '权限不能为空！');
												    Ext.get('rightId').focus();
														 return;
												}
												var resourceId = Ext.get('resourceId').dom.value;
												if(resourceId == ''){
												    Ext.MessageBox.alert('警告', '资源不能为空！');
												    Ext.get('resourceId').focus();
														 return;
												}
												var name = Ext.get('name').dom.value;
												if(name == ''){
													Ext.MessageBox.alert('警告', '授权名称不能为空！');
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
											Ext.MessageBox.alert('警告', '添加授权记录失败!');			
										}
									}); 
								}
							},
						{
							text: '取消',
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
			Ext.Msg.alert('警告','获取数据出错');          
			}      
		}      
	}); 				
});
	 function doDel(id){  //id:权限id
	    //alert(id);
	     var record = grid.getSelectionModel().getSelected();
         Ext.MessageBox.confirm('确认删除', '你真的要删除所选授权吗?', 
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