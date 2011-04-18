buttonType = Ext.extend(Ext.Panel, {
	id: 'button',
	title: '面板按钮',
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
			var oldValue = obj.originalValue;//编辑前原来的值
			var store = this.store;
			this.store.each(function(f){				
				if(f.data.btnName==btnName){
					i = i+1;				
				}				
			});
			if(i!=1){				
				Ext.Msg.alert("提示信息","您输入的按钮值已经存在，请您重新输入");
				r.set("btnName",oldValue);
				this.store.reload();
				return false;
			};
			var param = "btnName="+btnName+"&id="+id+"&link="+link+"&imageUrl="+imageUrl+"&order="+order+"&pageModelName="+pageModelName+"&ModelId="+mod+"&openWinFlag="+openWinFlag+"&isDisplay="+isDisplay+"&method="+method;
			Ext.Ajax.request({
		        url:webContext+'/pageModel/pageModelManage.do?methodCall=modifyButtonTypeByCustomer&'+param,
		        method:'get',
		        success:function(response){
			        if(response.responseText.indexOf("拒绝") != -1){
			        	Ext.Msg.alert("提示信息","您没有权限修改信息");
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
				        	Ext.Msg.alert("提示信息","数据删除失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
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
//					        if(response.responseText.indexOf("拒绝") != -1){
//					        	Ext.Msg.alert("提示信息","您没有权限修改信息");
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
//						        	Ext.Msg.alert("提示信息","数据删除失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
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
       		header: "是否弹出窗口",
       		dataIndex: 'openWinFlag',
       		width: 55
    	});
    	var isDisplay=new Ext.grid.CheckColumn({
       		header: "是否显示",
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
		 this.cm = new Ext.grid.ColumnModel([csm,{//这个就是表格的表头
	                    header: "按钮名称",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "btnName",
	                    width: 100,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "目标页面",
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
	                    header: "方法",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "method",
	                    width: 100,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "链接",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "link",
	                    width: 150,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "图标",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "imageUrl",
	                    width: 250,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "排序号",
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
				text : '增加',
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
				text : '删除',
				pressed : true,
				iconCls : 'remove',
				scope : this,
				handler : function() {
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("提示", "请先选择要删除的行!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					} else {
						Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(btn) {
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
				                           if(!r.success)Ext.Msg.alert("提示信息","数据删除失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
				                          // else{
				                           //     Ext.Msg.alert("提示信息","成功删除数据!",function(){  
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
    	//alert("我们");
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
    	
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            //alert("的组织");
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
//	                           if(!r.success)Ext.Msg.alert("提示信息","数据删除失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
//	                           else{
//	                                Ext.Msg.alert("提示信息","成功删除数据!",function(){	                                      
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