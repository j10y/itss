buttonType = Ext.extend(Ext.Panel, {
	id: 'button',
	title: '面板按钮',
	width: 'auto',
	autoScroll: true,	
	animate: true,
	layout : 'absolute',
	border : false,
	containerScroll: true,
    height: 200,
	align : 'center',
    afterEdit:function(obj){	
    		var mod = this.modelId;
//    		alert(mod);
			var r = obj.record;
			var modifyField = obj.field; 
			var id = r.get("id");
			var btnName = r.get("btnName");		
			var link = r.get("link");
			var imageUrl = r.get("imageUrl");
			var order = r.get("order");
			var pageModelName = r.get("pageModelName");
			
			var oldValue = obj.originalValue;//编辑前原来的值
		    Ext.MessageBox.confirm('提示信息', '是否真的要修改',function(ret){
				if(ret=="yes"){
					var param = "btnName="+btnName+"&id="+id+"&link="+link+"&imageUrl="+imageUrl+"&order="+order+"&pageModelName="+pageModelName+"&ModelId="+mod;
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
				        },
				        scope:this
					});			
		   		}else{
		   			if(modifyField=="btnName"){
		        		r.set("btnName",oldValue);
		        	}else if(modifyField=="link"){
		        		r.set("link",oldValue);
		        	}else if(modifyField=="imageUrl"){
				        r.set("imageUrl",oldValue);
		        	}else if(modifyField=="order"){
		        		r.set("order",oldValue);
		        	}else if(modifyField=="pageModelName"){
		        		r.set("pageModelName",oldValue);
		        	}
		   		}	
	   	   });		
		},
    
	initComponent: function(){
		this.modelId = modId;
		var csm = new Ext.grid.CheckboxSelectionModel();

		this.store = new Ext.data.JsonStore({ 				
				url: webContext+'/pageModel/pageModelManage.do?methodCall=findButtonTypeByPageModel',
				fields: ['id','btnName','order','imageUrl','link','pageModelName'],
			    root:'data',
				sortInfo: {field: "id", direction: "ASC"}
		});
		this.removedButtons="";
		 this.cm = new Ext.grid.ColumnModel([csm,{//这个就是表格的表头
	                    header: "按钮名称",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "btnName",
	                    width: 250,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "下个页面",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "pageModelName",
	                    width: 250,					
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
	                    header: "链接",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "link",
	                    width: 250,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "图标",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "imageUrl",
	                    width: 250,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "排序",
	                    sortable: true,
	                    hidden: true,
	                    dataIndex: "order",
	                    width: 250,
	                    editor: new Ext.form.TextField()
                	},                 	
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
		        align:'center',
		        autoEncode : true,
		        clickToEdit: 1,
				y : 0,
				anchor : '0 -0',
				viewConfig : {
					autoFill : true,
					forceFit : true
				},
				tbar : [{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '增加按钮',
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
									for (var i = 0; i < records.length; i++) {
										this.removedButtons += (i==0)?records[i].get("id"):","+records[i].get("id")
									}
									//alert(this.removedButtons);	
									}								
					Ext.Ajax.request({						
		                   url: webContext+'/pageModel/pageModelManage.do?methodCall=removePagePanelBtn',
	                       params:{
	                           'ids':this.removedButtons
	                       },
	                       mothod:'POST',
	                       timeout:100000,
	                       success:function(response){
	                           var r=Ext.decode(response.responseText);
	                           if(!r.success)Ext.Msg.alert("提示信息","数据删除失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
	                           else{
	                                Ext.Msg.alert("提示信息","成功删除数据!",function(){
	                                    //this.store.reload();  
	                                },this);
	                           }
	                           this.store.reload();    
	                       }, 
	                       scope:this
                   });
							}
						}, this)
					}

				}
			}]				
		});	
		this.grid.on("afteredit",this.afterEdit,this);
		this.items = [this.grid] ;
			
		buttonType.superclass.initComponent.call(this);
}
    
});