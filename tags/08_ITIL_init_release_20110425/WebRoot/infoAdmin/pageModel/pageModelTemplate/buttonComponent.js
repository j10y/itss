//ButtonTypePanel = Ext.extend(Ext.Panel,{
//	id:"button",
//	title: '页面按钮',	
//	layout : 'absolute',
//	height : 190,
//	align : 'center',
//		
//	afterEdit:function(obj){
//			//alert("789465798");
//			var r = obj.record;
//			var modifyField = obj.field; 
//			var id = r.get("id");
//			var btnName = r.get("btnName");		
//			var link = r.get("link");
//			var order = r.get("order");
//			var imageUrl = r.get("imageUrl");
//			var pageModelName = r.get("pageModelName");
//			//alert(modifyField);
//			var oldValue = obj.originalValue;//编辑前原来的值
//		    Ext.MessageBox.confirm('提示信息', '是否真的要修改',function(ret){
//				if(ret=="yes"){
//					var param = "btnName="+btnName+"&id="+id+"&link="+link+"&order="+order+"&imageUrl="+imageUrl+"&pageModelName="+pageModelName;//
//					Ext.Ajax.request({
//				        url:webContext+'/pageModel/pageModelManage.do?methodCall=modifyButtonTypeByCustomer&'+param,
//				        method:'get',
//				        success:function(response){
//					        if(response.responseText.indexOf("拒绝") != -1){
//					        	Ext.Msg.alert("提示信息","您没有权限修改信息");
//					        	if(modifyField=="btnName"){
//					        		r.set("btnName",oldValue);
//					        	}else if(modifyField.equals("link")){
//					        		r.set("link",oldValue);
//					        	}else if(modifyField.equals("order")){
//					        		r.set("order",oldValue);
//					        	}else if(modifyField.equals("imageUrl")){
//					        		r.set("imageUrl",oldValue);
//					        	}else if(modifyField.equals("pageModelName")){
//					        		r.set("pageModelName",oldValue);
//					        	}
//					        }else{
//						        var result = Ext.decode(response.responseText);
//						        if(!result.success){
//						        	Ext.Msg.alert("提示信息","数据删除失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
//						        }
//					        }
//				        },
//				        scope:this
//					});			
//		   		}else{
//		   			if(modifyField=="btnName"){
//		        		r.set("btnName",oldValue);
//		        	}else if(modifyField=="link"){
//		        		r.set("link",oldValue);
//		        	}else if(modifyField=="order"){
//		        		r.set("order",oldValue);
//		        	}else if(modifyField=="imageUrl"){
//		        		r.set("imageUrl",oldValue);
//		        	}else if(modifyField=="pageModelName"){
//		        		r.set("pageModelName",oldValue);
//		        	}
//		   		}	
//	   	   });		
//		},
//	
//	initComponent: function(){
//		
//		var csm = new Ext.grid.CheckboxSelectionModel();
//		
//		this.store = new Ext.data.JsonStore({ 				
//				url: webContext+'/pageModel/pageModelManage.do?methodCall=findButtonTypeByPageModel',
//				fields: ['id','btnName','order','imageUrl','link','pageModelName'],
//			    root:'data',
//				sortInfo: {field: "id", direction: "ASC"}
//		});
//			
//		 this.cm = new Ext.grid.ColumnModel([csm,{//这个就是表格的表头
//	                    header: "按钮名称",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "btnName",
//	                    width: 250,
//	                    editor: new Ext.form.TextField()
//                	}, {
//	                    header: "目标页面",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "pageModelName",
//	                    width: 250,					
//	                    editor: new Ext.form.ComboBox({
//							store : new Ext.data.JsonStore({
//								url: webContext+'/pageModel/pageModelManage.do?methodCall=findPageModelBtn',
//								fields: ['pageModelId','name'],
//							    root:'data',
//								sortInfo: {field: "pageModelId", direction: "ASC"}
//							}), 
//							valueField : "name",
//							displayField : "name",
//							id : 'comboxId',
//			                emptyText: '',
//							mode : 'remote',
//							forceSelection : true,
//							hiddenName : "name",
//							editable : false,
//							triggerAction : 'all', 
//							lazyRender: true,
//				            typeAhead: true,
//							allowBlank : true,
//							name : "name",
//							selectOnFocus: true
//						})
//                	}, {
//	                    header: "链接",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "link",
//	                    width: 250,
//	                    editor: new Ext.form.TextField()
//                	}, {
//	                    header: "图标",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "imageUrl",
//	                    width: 250,
//	                    editor: new Ext.form.TextField()
//                	}, {
//	                    header: "排序号",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "order",
//	                    width: 250,
//	                    editor: new Ext.form.TextField()
//                	},                 	
//                	{
//                		header: "id",
//                		sortable: true,
//			            hidden: true,
//			            dataIndex: "id"
//		     		}
//		     ]);
//		
//		this.grid = new Ext.grid.EditorGridPanel({
//		        store: this.store,
//		        cm: this.cm,
//		        sm: csm,
//		        trackMouseOver:false,    
//		        loadMask: true,
//		        height: 200,
//		        frame:true,
//		        align:'center',
//		        autoEncode : true,
//		        clickToEdit: 1,
//				y : 0,
//				anchor : '0 -0',
//				viewConfig : {
//					autoFill : true,
//					forceFit : true
//				}
//				
//		});		
//		
//		this.grid.on("afteredit",this.afterEdit,this);
//		this.items = [this.grid] ;
//			
//		ButtonTypePanel.superclass.initComponent.call(this);
//	
//	}
//
//	
//    
//});