buttonType = Ext.extend(Ext.Panel, {
	id: 'button',
	title: '��尴ť',
	autoScroll: true,
	frame:true,
	layout : 'fit',
    afterEdit:function(obj){	
    		var mod = this.modelId;
    		//alert(mod);
			var r = obj.record;
			var modifyField = obj.field; 
			var id = r.get("id");
			var btnName = r.get("btnName");		
			var link = r.get("link");
			var imageUrl = r.get("imageUrl");
			var order = r.get("order");
			var pageModelName = r.get("pageModelName");
			var openWinFlag = r.get("openWinFlag");
			var isDisplay = r.get("isDisplay");
			var method = r.get("method");
			var i = 0;
			var oldValue = obj.originalValue;//�༭ǰԭ����ֵ
			var store = this.store;
			this.store.each(function(f){				
				if(f.data.btnName==btnName){
					i = i+1;				
				}				
			});
			if(i!=1){				
				Ext.Msg.alert("��ʾ��Ϣ","������İ�ťֵ�Ѿ����ڣ�������������");
				r.set("btnName",oldValue);
				this.store.reload();
				return false;
			};
			var param = "btnName="+btnName+"&id="+id+"&link="+link+"&imageUrl="+imageUrl+"&order="+order+"&pageModelName="+pageModelName+"&ModelId="+mod+"&openWinFlag="+openWinFlag+"&isDisplay="+isDisplay+"&method="+method;
			Ext.Ajax.request({
		        url:webContext+'/pageModel/pageModelManage.do?methodCall=modifyButtonTypeByCustomer&'+param,
		        method:'get',
		        success:function(response){
			        if(response.responseText.indexOf("�ܾ�") != -1){
			        	Ext.Msg.alert("��ʾ��Ϣ","��û��Ȩ���޸���Ϣ");
			        	if(modifyField=="btnName"){
			        		r.set("btnName",oldValue);
			        	}else if(modifyField.equals("link")){
			        		r.set("link",oldValue);
			        	}else if(modifyField=="imageUrl"){
		        		    r.set("imageUrl",oldValue);
			        	}else if(modifyField.equals("order")){
			        		r.set("order",oldValue);
			        	}else if(modifyField.equals("pageModelName")){
			        		r.set("pageModelName",oldValue);
			        	}
			        	
			        }else{
				        var result = Ext.decode(response.responseText);
				        if(!result.success){
				        	Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
				        }
			        }
			        store.reload();
		        }
			});
			
//		    Ext.MessageBox.confirm(function(ret){
//				if(ret=="yes"){
//					var param = "btnName="+btnName+"&id="+id+"&link="+link+"&order="+order+"&pageModelName="+pageModelName+"&ModelId="+mod+"&openWinFlag="+openWinFlag+"&isDisplay="+isDisplay;
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
//					        	}else if(modifyField.equals("pageModelName")){
//					        		r.set("pageModelName",oldValue);
//					        	}
//					        	
//					        }else{
//						        var result = Ext.decode(response.responseText);
//						        if(!result.success){
//						        	Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
//						        }
//					        }
//					        store.reload();
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
//		        	}else if(modifyField=="pageModelName"){
//		        		r.set("pageModelName",oldValue);
//		        	}
//		   		}	
//	   	   });		
		},
    
	initComponent: function(){
		this.modelId = modId;
		var csm = new Ext.grid.CheckboxSelectionModel();
		var openWinFlagcombo=new Ext.grid.CheckColumn({
       		header: "�Ƿ񵯳�����",
       		dataIndex: 'openWinFlag',
       		width: 55
    	});
    	var isDisplay=new Ext.grid.CheckColumn({
       		header: "�Ƿ���ʾ",
       		dataIndex: 'isDisplay',
       		width: 55
    	});

		this.store = new Ext.data.JsonStore({ 				
				url: webContext+'/pageModel/pageModelManage.do?methodCall=findButtonTypeByPageModel&pageModelId='+this.modelId,
				fields: ['id','btnName','method','order','link','imageUrl','pageModelName','openWinFlag','isDisplay'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
		});
		this.removedButtons="";
		 this.cm = new Ext.grid.ColumnModel([csm,{//������Ǳ��ı�ͷ
	                    header: "��ť����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "btnName",
	                    width: 100,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "Ŀ��ҳ��",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "pageModelName",
	                    width: 150,					
	                    editor: new Ext.form.ComboBox({
							store : new Ext.data.JsonStore({
								url: webContext+'/pageModel/pageModelManage.do?methodCall=findPageModelBtn',
								fields: ['pageModelId','name'],
							    root:'data',
								sortInfo: {field: "pageModelId", direction: "ASC"}
							}), 
							valueField : "name",
							displayField : "name",
							id : 'comboxId',
			                emptyText: '',
							mode : 'remote',
							forceSelection : true,
							hiddenName : "name",
							editable : false,
							triggerAction : 'all', 
							lazyRender: true,
				            typeAhead: true,
							allowBlank : true,
							name : "name",
							selectOnFocus: true
						})
                	}, {
	                    header: "����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "method",
	                    width: 100,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "link",
	                    width: 150,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "ͼ��",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "imageUrl",
	                    width: 250,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "�����",
	                    sortable: true,
	                    hidden: false,
	                    dataIndex: "order",
	                    width: 80,
	                    editor: new Ext.form.TextField()
                	},  
                		openWinFlagcombo,
                		isDisplay,      
                	{
                		header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     		}
		     ]);
		     this.grid = new Ext.grid.EditorGridPanel({
		        store: this.store,
		        cm: this.cm,
		        sm: csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 200,
		        frame:true,
		        autoEncode : true,
		        plugins:[openWinFlagcombo,isDisplay],
		        clickToEdit: 1,
				tbar : [{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '����',
				pressed : true,
				scope : this,
				iconCls : 'add',
				handler : function() {
					var store = this.store;
					if (store.recordType) {
						
						var rec = new store.recordType({
							newRecord : true
						});
						rec.fields.each(function(f) {
							rec.data[f.name] = f.defaultValue || null;							
						});
						rec.commit();
						store.add(rec);
						return rec;
					}
					return false;
				}

			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : 'ɾ��',
				pressed : true,
				iconCls : 'remove',
				scope : this,
				handler : function() {
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
					} else {
						Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
							if (btn == 'yes') {
								if (records) {
									//alert(records.length);
									for (var i = 0; i < records.length; i++) {
										this.removedButtons += (i==0)?records[i].get("id"):","+records[i].get("id");
										this.grid.getStore().reload();
									}
										
									}								
								Ext.Ajax.request({						
					                   url: webContext+'/pageModel/pageModelManage.do?methodCall=removePagePanelBtn',
				                       params:{
				                           'ids':this.removedButtons,
				                           'modId': this.modelId
				                       },
				                       mothod:'POST',
				                       timeout:100000,
				                       success:function(response){
				                           var r=Ext.decode(response.responseText);
				                           if(!r.success)Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
				                          // else{
				                           //     Ext.Msg.alert("��ʾ��Ϣ","�ɹ�ɾ������!",function(){  
				                           //     },this);
				                           //}
				                           this.store.reload();    
				                       }, 
				                       scope:this
		                   		});
									}
								}, this)
							}
				this.removedButtons = "";
				}
			}]				
		});	
		this.grid.on("afteredit",this.afterEdit,this);
		this.items = [this.grid] ;
			
		buttonType.superclass.initComponent.call(this);
}
    
});

Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
	
    init : function(grid){
    	//alert("����");
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
    	
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            //alert("����֯");
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
//            alert("openWinFlag"+record.get("openWinFlag"));
//            alert("isDisplay"+record.get("isDisplay"));
            record.set(this.dataIndex, (!record.data[this.dataIndex]?1:0));
            Ext.Ajax.request({						
		                   url: webContext+'/pageModel/pageModelManage.do?methodCall=modifyCheckColumn',
	                       params:{
	                           'openWinFlag':record.get("openWinFlag"),
	                           'isDisplay':record.get("isDisplay"),
	                           'id':record.get("id")	                           
	                       },
	                       mothod:'POST',
	                       timeout:100000,
	                       success:function(response){
//	                           var r=Ext.decode(response.responseText);
//	                           if(!r.success)Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
//	                           else{
//	                                Ext.Msg.alert("��ʾ��Ϣ","�ɹ�ɾ������!",function(){	                                      
//	                                },this);
//	                           }	                            
	                       }, 
	                       scope:this
                   });
           this.grid.store.reload();
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td'; 
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};