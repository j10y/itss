<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>资源列表</title>
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
  var mwin;  //权限修改窗体
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
			        {   fieldLabel: '权限名称',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: '权限关键字',
			            name: 'keyName',
			            anchor: '100%' 
			        },{
			            fieldLabel: '描述',
			            name: 'descn',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '权限ID',
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
				             title:'权限修改',
				             width:500,
				             height:240,
				             closeAction:'hide',
				             plain: false,
				             items: mForm,
				             buttons: [{
				             	text:'保存',
				             	handler:function(){   
				             		var name = Ext.get('name').dom.value;
									if(name == ''){
										 Ext.MessageBox.alert('警告', '权限名称不能为空！');
										 Ext.get('name').focus();
										   	return;
									}
									var keyName = Ext.get('keyName').dom.value;
									if(keyName == ''){
										 Ext.MessageBox.alert('警告', '权限关键字不能为空！');
										 Ext.get('keyName').focus();
										   	return;
									}
									var descn = Ext.get('descn').dom.value;
									if(descn == ''){
										 Ext.MessageBox.alert('警告', '描述不能为空！');
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
				                text: '取消',
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
				        header: "权限名称",
				        sortable: true,
				        hideable: false,
				        dataIndex: "name",
				        width : 200,
				        editor:new Ext.form.TextField()
				      },
			          {
			            id : "keyName",
			            header: "权限关键字",
			        	sortable: true,
				        hideable: false,
			            dataIndex: "keyName",
			        	width : 200,
			        	editor:new Ext.form.TextField() 
			          },
			         {
			        	id : "descn",
			        	header: "描述",
			        	sortable: true,
				        hideable: false,
			        	dataIndex: "descn",
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
			            fieldLabel: '权限名称',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: '权限关键字',
			            name: 'keyName',
			            anchor: '100%' 
			        },{
			            fieldLabel: '描述',
			            name: 'descn',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '权限ID',
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
							title : " 权限管理",
							autoHeight: true,
							cm : cm,
							ds : store,
							tbar: [
								{
								text: '添加记录',
								iconCls: 'add',
								handler : function(){
								   if(!win){
									   win = new Ext.Window({
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
												var name = Ext.get('name').dom.value;
												if(name == ''){
													Ext.MessageBox.alert('警告', '权限名称不能为空！');
													Ext.get('name').focus();
														  return;
													}
												var keyName = Ext.get('keyName').dom.value;
												if(keyName == ''){
												    Ext.MessageBox.alert('警告', '权限关键字不能为空！');
												    Ext.get('keyName').focus();
														 return;
												}
												var descn = Ext.get('descn').dom.value;
												if(descn == ''){
												    Ext.MessageBox.alert('警告', '描述不能为空！');
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
										  Ext.MessageBox.alert('提示','记录保存成功！');
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
											Ext.MessageBox.alert('警告', '添加记录失败!');			
										}
									}); */
								}
							},
						{
							text: '关闭',
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
			Ext.Msg.alert('警告','获取数据出错');          
			}      
		}      
	}); 				
});
    function doDel(id){  //id:权限id
	    //alert(id);
	     var record = grid.getSelectionModel().getSelected();
         Ext.MessageBox.confirm('确认删除', '你真的要删除所选权限吗?', 
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