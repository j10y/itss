<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>资源管理</title>
		<%@include file="/includefiles.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/grid-examples.css" />
		
		<link rel="stylesheet" type="text/css" href="../extEngine/resource/css/examples.css" />
		<link rel="stylesheet" type="text/css" href="../extEngine/resources/css/crm-grid.css" />
		<script type="text/javascript" src="../extEngine/examples.js"></script>
		
<script type="text/javascript">
	//修改资源
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
    var mwin;   //资源修改对应的窗体
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
			                fieldLabel:'所属模块',   
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
			            fieldLabel: '资源名称',
			            name: 'rname',
			            anchor:'100%'  
			        },{
			            fieldLabel: '资源类型',
			            name: 'type',
			            anchor: '100%' 
			        },{
			            fieldLabel: '类/模块名',
			            name: 'className',
			            anchor: '100%'  
			        },{
			            fieldLabel: '方法/URL后缀',
			            name: 'methodName',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '资源ID',
					    name : 'rid'
					},{
					    xtype : 'hidden',
					    fieldLabel : '模块ID',
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
				             title:'资源修改',
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
										   	  	 Ext.MessageBox.alert('警告', '资源名称不能为空！');
										   	  	 Ext.get('name').focus();
										   	  	 return;
										   	 }
										   	var type = Ext.get('type').dom.value;
										   	if(type == ''){
										   	  	 Ext.MessageBox.alert('警告', '资源类型不能为空！');
										   	  	 Ext.get('type').focus();
										   	  	 return;
										   	}
										   var className = Ext.get('className').dom.value;
										   	if(className == ''){
										   	  	 Ext.MessageBox.alert('警告', '类/模块名不能为空！');
										   	  	 Ext.get('className').focus();
										   	  	 return;
										   }
										   var methodName = Ext.get('methodName').dom.value;
										   	if(methodName == ''){
										   	  	 Ext.MessageBox.alert('警告', '方法/URL后缀不能为空！');
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
				                text: '取消',
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
				            header: "资源名称",
				            sortable: true,
				            hideable: false,
				            dataIndex: "rname",
				            width : 160,
				            editor:new Ext.form.TextField()
				        },
			        	{
			        		id : "type",
			        		header: "资源类型",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "type",
			        		width : 100,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		id : "className",
			        		header: "类/模块名",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "className",
			        		width : 160,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		id : "methodName",
			        		header: "方法/URL后缀",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "methodName",
			        		width : 100,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		id : "moduleName",
			        		header: "所属模块",
			        		sortable: true,
				            hideable: false,
			        		dataIndex: "mname",
			        		width : 160,
			        		editor:new Ext.form.TextField() 
			        	},
			        	{
			        		header: "修改",
			        		width : 60,
			        		sortable: true,
				            hideable: false,
				            dataIndex: "rid",
			        	    renderer: function(value){
			        	    	var tempValue = value;
				        		return '<input type="button" id='+tempValue+' value="修改" onclick="modify(this.id);">';
			        	    }
			        	},
			        	{
			        		header: "删除",
			        		width : 50,
			        	    sortable: true,
				            hideable: false,
				            dataIndex: "rid",
			        		renderer: function(value){
			        		    var tempValue = value;
			        		    return '<input type="button" id='+tempValue+' value="删除" onclick="doDel(this.id);">';
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
			                fieldLabel:'所属模块',   
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
			            fieldLabel: '资源名称',
			            name: 'name',
			            anchor:'100%'  
			        },{
			            fieldLabel: '资源类型',
			            name: 'type',
			            anchor: '100%' 
			        },{
			            fieldLabel: '类/模块名',
			            name: 'className',
			            anchor: '100%'  
			        },{
			            fieldLabel: '方法/URL后缀',
			            name: 'methodName',
			            anchor: '100%'  
			        },{
					    xtype : 'hidden',
					    fieldLabel : '资源ID',
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
					title : "资源管理",
					cm : colM,
					ds : store,
					autoHeight: true,
					tbar: [
					{
					  text: '添加资源',
					  iconCls: 'add',
					  handler : function(){
						 if(!win){
							win = new Ext.Window({
							  el:'insert-win',
							  layout:'fit',
							  title:'添加资源',
							  width:400,
							  height:200,
							  closeAction:'hide',
							  plain: true,
							  items:form,
							  buttons: [{
									     text:'提交',
									     handler:function(){
									     	var name = Ext.get('name').dom.value;
										   	if(name == ''){
										   	  	 Ext.MessageBox.alert('警告', '资源名称不能为空！');
										   	  	 Ext.get('name').focus();
										   	  	 return;
										   	 }
										   	var type = Ext.get('type').dom.value;
										   	if(type == ''){
										   	  	 Ext.MessageBox.alert('警告', '资源类型不能为空！');
										   	  	 Ext.get('type').focus();
										   	  	 return;
										   	}
										   var className = Ext.get('className').dom.value;
										   	if(className == ''){
										   	  	 Ext.MessageBox.alert('警告', '类/模块名不能为空！');
										   	  	 Ext.get('className').focus();
										   	  	 return;
										   }
										   var methodName = Ext.get('methodName').dom.value;
										   	if(methodName == ''){
										   	  	 Ext.MessageBox.alert('警告', '方法/URL后缀不能为空！');
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
									              			
									                   			Ext.MessageBox.alert('警告', '添加资源失败!');
									                 			
															}
									             });
									           }
									                    
									         },
									         {
									           text: '关闭',
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
			            Ext.Msg.alert('警告','获取数据出错');          
			       }      
			     }      
			});        			      
	});
	function doDel(rid){  //rid:资源ID
	     //alert(rid);
	     var record = grid.getSelectionModel().getSelected();
         Ext.MessageBox.confirm('确认删除', '你真的要删除所选资源吗?', 
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