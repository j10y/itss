//ButtonTypePanel = Ext.extend(Ext.Panel,{
//	id:"button",
//	title: 'ҳ�水ť',	
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
//			var oldValue = obj.originalValue;//�༭ǰԭ����ֵ
//		    Ext.MessageBox.confirm('��ʾ��Ϣ', '�Ƿ����Ҫ�޸�',function(ret){
//				if(ret=="yes"){
//					var param = "btnName="+btnName+"&id="+id+"&link="+link+"&order="+order+"&imageUrl="+imageUrl+"&pageModelName="+pageModelName;//
//					Ext.Ajax.request({
//				        url:webContext+'/pageModel/pageModelManage.do?methodCall=modifyButtonTypeByCustomer&'+param,
//				        method:'get',
//				        success:function(response){
//					        if(response.responseText.indexOf("�ܾ�") != -1){
//					        	Ext.Msg.alert("��ʾ��Ϣ","��û��Ȩ���޸���Ϣ");
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
//						        	Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
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
//		 this.cm = new Ext.grid.ColumnModel([csm,{//������Ǳ��ı�ͷ
//	                    header: "��ť����",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "btnName",
//	                    width: 250,
//	                    editor: new Ext.form.TextField()
//                	}, {
//	                    header: "Ŀ��ҳ��",
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
//	                    header: "����",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "link",
//	                    width: 250,
//	                    editor: new Ext.form.TextField()
//                	}, {
//	                    header: "ͼ��",
//	                    sortable: true,
//	                    hideable: false,
//	                    dataIndex: "imageUrl",
//	                    width: 250,
//	                    editor: new Ext.form.TextField()
//                	}, {
//	                    header: "�����",
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