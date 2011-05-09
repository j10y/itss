
GridModifyPanel =Ext.extend(Ext.Panel, {
		title: '面板关系',
		width: window.screen.availWidth-224,
		colspan:2,
		autoScroll: true,
		frame:true,
		layout:'fit',
	//*******************************************	
		 search:function(){
			 	//alert("1231231");
	           this.store.removeAll();
	           this.store.load();
	        },
         deleBook:function(){
           var record = this.grid.getSelectionModel().getSelected();
           var records = this.grid.getSelectionModel().getSelections();
           if(!record){
               Ext.Msg.alert("提示","请选择需要删除的行");
               return;
           }
//           var ids = new Array();
//           for(var i=0;i<records.length;i++){
//               ids[i] = records[i].data.id;
//           }
           var ids = "";
			for(var i=0;i<records.length;i++){			
				ids += (i==0)?records[i].get("id"):","+records[i].get("id");
				
			}
		var stored = this.store;
           var m = Ext.MessageBox.confirm("删除提示","是否真的删除,一旦删除不可恢复!",function(re){
               if(re=="yes"){
                   Ext.Ajax.request({
                      // url:webContext+'/deleteBook.action',
                    url:webContext+'/pageModel/pageModelManage.do?methodCall=toPageModelPanelTableRemove',	
                       params:{
                           'ids':ids
                       },
                       mothod:'POST',
                       timeout:100000,
                       success:function(response){
                           var r=Ext.decode(response.responseText);
                           if(!r.success)Ext.Msg.alert("提示信息","数据删除失败，由以下原因所致：<br/>"+(r.errors.msg?r.errors.msg:"未知原因"));
                           else{
                                Ext.Msg.alert("提示信息","成功删除数据!",function(){
                                    
                                });
                           }
                            this.search(); 
                       }, 
                       scope:this
                   });
               }
           },this);
           
        },
          saveData:function(){
	    	Ext.MessageBox.confirm('提示信息', '确实要保存修改吗?  ',function(ret){
				if(ret!="yes"){
					this.store.removeAll();
					this.store.reload();
					return;
				}
				
//			var gridParam = this.store.getModifiedRecords(0,//getRange(0,// //只取修改的
//				 this.grid.getStore().getCount());
			var price = "";
//			alert(gridParam.length);
			// return;
			this.store.each(function(record){
					if(record.dirty){
						price += Ext.encode(record.data)+ ",";
					}
			});		
					
//			for (i = 0; i < gridParam.length; i++) {
//				price += Ext.encode(gridParam[i].data) + ",";
//			}
//			alert(price);
				
//				this.grid.getStore().each(function(record){
//					if(record.dirty){
						Ext.Ajax.request({
					         url:webContext+'/pageModel/pageModelManage.do?methodCall=toForeignTableSave',				         
               				 method:"POST",
               				 params:{
               				 	'price' : unicode(price),
               				 	'ppId' : unicode(modId)
               				 },
					         success:function(response){
						        if(response.responseText.indexOf("拒绝") != -1){
						        	Ext.Msg.alert("提示信息","您没有权限修改信息");
						        }else{						        	
							    
							        Ext.Msg.alert("提示信息","保存数据成功！");
							        this.store.removeAll();
							        this.store.reload();
						        }
						        this.store.removeAll();
						        this.store.reload();
					        },
					        scope:this
						});
//					}
//				},this);	
	   	   },this);
	    },
        
        
	 addBook : function(){
            
//            window.location.href = 'addBook.jsp';
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
				
        },
  
		//***********************************
	beforeedit:function(obj){
			//alert(document.body.clientWidth);
			var r = obj.record;
			var modifyField = obj.field; 
			var value = r.get("subPagePanel");
			var id=r.get("id");
			var reg=/^[\u4e00-\u9fa5]+$/g; //中文
			if(value==null||value==""){
				this.foreignstore.removeAll();
			}else{
				this.foreignstore.load({
					params: {
						  'value' : unicode(value),
						  'id' : id
					}
				});
			}
			var subPanelTableValue = r.get("subPanelTable");
			if(subPanelTableValue==null||subPanelTableValue==""){
				this.foreignColstore.removeAll();
			}else {
				this.foreignColstore.load({
					params: {
						  'subPanelTableValue' : unicode(subPanelTableValue),
						  'id' : id
					}
				});
			}
			
			var value2 = r.get("parentPagePanel");
			if(value2==null||value2==""){
				//alert("value2==null||reg.test(value2)"+value2);
				this.foreignstore2.removeAll();
			}else {
				//alert("value2!=reg"+value2);
				this.foreignstore2.load({
					params: {
						  'value' : unicode(value2),
						   'id' : id
					}
				});
			}
			var reg2 = /[\u4e00-\u9fa5]+/g;
			var value3= r.get("parentPanelTable");
//			alert("value3"+value3);
//			alert(id);
//			alert(value3.match(reg2));
			if(value3==null||value3==""){
//				alert("进来的是中文=="+value3);
				this.foreignColstore2.removeAll();
				}else {
//					alert("非中文=="+value3);
					this.foreignColstore2.load({
						params: {
							  'parentPanelTable' : unicode(value3),
							   'id' : id
						}
					});
			}
		}, 

		
		
		//************************************
		validateedit:function(obj){
			var reg=/^[\u4e00-\u9fa5]+$/g; //中文
			if(obj.field=="parentPanelTable"&&obj.originalValue!=obj.value){
				var parentPanelTable = obj.value;
				var code="";
			//	alert("加载数据");
				if(parentPanelTable==null||reg.test(parentPanelTable)){
						this.foreignColstore2.removeAll();
					}else if(parentPanelTable!=reg){
					this.foreignColstore2.load({
					params: {
						  'parentPanelTable' : unicode(parentPanelTable)	
					}
				});
			}
				Ext.Ajax.request({
                    url:webContext+'/pageModel/pageModelManage.do?methodCall=findPrimaryKeyColumnID',	
                     method:"POST",
                    params:{
                           'parentPanelTable':unicode(parentPanelTable)
                       },
                    success:function(response,request){
                    //	alert("set值");
                    	code=response.responseText;
                    	obj.record.set("parentPanelTablePColumn",code);
                    	this.foreignColstore2.reload();
                    },
                    failure:function(){
                    alert("返回数据失败");
                    },
                     scope:this
				});
				//this.foreignColstore2.reload();
				
			}
			return true;
		}, 
		
	
				
				//**********获得grid所有数据******* 怎么只获得修改的？？
	
		
	//*******************************************	
        initComponent : function(){ 
        	var combostore = new Ext.data.JsonStore({
										url:webContext+'/pageModel/pageModelManage.do?methodCall=findAllPageModel',
										fields:['code','name'],
										totalProperty:"rowCount",
										root:"data"
								
										}
									);
				this.foreignstore = new Ext.data.JsonStore({
										url:webContext+'/pageModel/pageModelManage.do?methodCall=findAllMainTable',
										fields:['code','name'],
										totalProperty:"rowCount",
										root:"data"
										}
									);	
									
					this.foreignColstore = new Ext.data.JsonStore({				
						                url:webContext+'/pageModel/pageModelManage.do?methodCall=findAllForeignCol',
										fields:['code','name'],
										totalProperty:"rowCount",
										root:"data"
										}
									);	
			var combostore2 = new Ext.data.JsonStore({
										url:webContext+'/pageModel/pageModelManage.do?methodCall=findAllPageModel',
										fields:['code','name'],
										totalProperty:"rowCount",
										root:"data"
								
										}
									);
				this.foreignstore2 = new Ext.data.JsonStore({
										url:webContext+'/pageModel/pageModelManage.do?methodCall=findAllMainTable2',
										fields:['code','name'],
										totalProperty:"rowCount",
										root:"data"
										}
									);	
									
					this.foreignColstore2 = new Ext.data.JsonStore({
						                id:'foreignColstore2',
						                url:webContext+'/pageModel/pageModelManage.do?methodCall=findAllForeignCol2',//findPrimaryKeyColumn',
										fields:['code','name'],
										totalProperty:"rowCount",
										root:"data"
										}
									);									
           var csm = new Ext.grid.CheckboxSelectionModel();
           this.storeMapping = ['id','subPagePanel', 'subPanelTable','subPanelTableFColumn', 'parentPagePanel', 'parentPanelTable', 'parentPanelTablePColumn']; 
           this.cm = new Ext.grid.ColumnModel([csm,{
           				header: "ID",
                        sortable: true,
                        hidden:true,
                        dataIndex: "id",
                        editor: new Ext.form.TextField()
                    },{
                        header: "子面板名称",
                        sortable: true,   
                        width:150,
                        renderer:function(value){
				        var comboObject = Ext.getCmp('combox1id');
							var combostore = comboObject.store;
							if (combostore.find('code', value) !=-1){ 
						           return combostore.getAt(combostore.find('code', value)).get('name');
							     } 
							else{
								     return  value;
							     } 
                        },
                        dataIndex: "subPagePanel",
                        editor: new Ext.form.ComboBox({                        
						    		name:"pageModelPanelTable.subPagePanel",
						    		id:"combox1id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		code:"code",
						    		mode:'remote',
						    		forceSelection : true,
									store:combostore,
								    editable:false,
						    		typeAhead: true,
						    		triggerAction: 'all',
						    		emptyText:'',
						    		selectOnFocus:true
						      })             		
                    },
                    	{
                        header: "子面板主表",
                        sortable: true,
                        width:150,
                        renderer:function(value){
				        var comboObject = Ext.getCmp('combox2id');
							this.foreignstore = comboObject.store;
							if (this.foreignstore.find('code', value) !=-1){ 
						           return this.foreignstore.getAt(this.foreignstore.find('code', value)).get('name');
							     } 
							else{
								     return  value;
							     } 
                        },
                        dataIndex: "subPanelTable",
                         editor: new Ext.form.ComboBox({                        
						    		name:"pageModelPanelTable.subPanelTable",
						    		id:"combox2id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		code:"code",
						    		mode:'local',
						    		forceSelection : true,
									store:this.foreignstore,
								    editable:false,
						    		typeAhead: true,
						    		triggerAction: 'all',
						    		emptyText:'',
						    		selectOnFocus:true
						      })           
                    },{
                        header: "子面板外键字段",
                        sortable: true,
                        width:150,
                        renderer:function(value){
				        var comboObject = Ext.getCmp('combox3id');
							this.foreignColstore = comboObject.store;
							if (this.foreignColstore.find('code', value) !=-1){ 
						           return this.foreignColstore.getAt(this.foreignColstore.find('code', value)).get('name');
							     } 
							else{
								     return  value;
							     } 
                        },
                        dataIndex: "subPanelTableFColumn",
                        editor: new Ext.form.ComboBox({                        
						    		name:"pageModelPanelTable.subPanelTableFColumn",
						    		id:"combox3id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		code:"code",
						    		mode:'local',
						    		forceSelection : true,
									store:this.foreignColstore,
								    editable:false,
						    		typeAhead: true,
						    		triggerAction: 'all',
						    		emptyText:'',
						    		selectOnFocus:true
						      })           
                    },{
                        header: "关联父面板名称",
                        sortable: true,
                        width:150,
                         renderer:function(value){
				        var comboObject = Ext.getCmp('combox4id');
							var combostore2 = comboObject.store;
							if (combostore2.find('code', value) !=-1){ 
						           return combostore2.getAt(combostore2.find('code', value)).get('name');
							     } 
							else{
								     return  value;
							     } 
                        },
                        dataIndex: "parentPagePanel",
                        editor: new Ext.form.ComboBox({                        
						    		name:"pageModelPanelTable.parentPagePanel",
						    		id:"combox4id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		code:"code",
						    		mode:'remote',
						    		forceSelection : true,
									store:combostore2,
								    editable:false,
						    		typeAhead: true,
						    		triggerAction: 'all',
						    		emptyText:'',
						    		selectOnFocus:true
						      })             		  
                    },{
                        header: "关联父面板主表",
                        sortable: true,
                        width:150,
                         renderer:function(value){
				        var comboObject = Ext.getCmp('combox5id');
//				        var store = this.foreignColstore2;
							this.foreignstore2 = comboObject.store;
							if (this.foreignstore2.find('code', value) !=-1){ 
						           return this.foreignstore2.getAt(this.foreignstore2.find('code', value)).get('name');
							     } 
							else{
								     return  value;
							     } 
                        },
                        dataIndex: "parentPanelTable",
                       editor: new Ext.form.ComboBox({                        
						    		name:"pageModelPanelTable.parentPanelTable",
						    		id:"combox5id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		code:"code",
						    		mode:'local',
						    		forceSelection : true,
									store:this.foreignstore2,
								    editable:false,
						    		typeAhead: true,
						    		triggerAction: 'all',
						    		emptyText:'',
						    		selectOnFocus:true
						    		
						      })           
                    },{
                        header: "关联父面板主键字段",
                        sortable: true,
                        width:150,
                         renderer:function(value){
				        var comboObject = Ext.getCmp('combox6id');
							this.foreignColstore2 = comboObject.store;
							if (this.foreignColstore2.find('code', value) !=-1){ 
						           return this.foreignColstore2.getAt(this.foreignColstore2.find('code', value)).get('name');
							     } 
							else{
								     return  value;
							     } 
                        },
                        dataIndex: "parentPanelTablePColumn",
                        editor: new Ext.form.ComboBox({                        
						    		name:"pageModelPanelTable.parentPanelTablePColumn",
						    		id:"combox6id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		code:"code",
						    		mode:'local',
						    		forceSelection : true,
									store:this.foreignColstore2,
								    editable:false,
						    		typeAhead: true,
						    		triggerAction: 'all',
						    		emptyText:'',
						    		selectOnFocus:true
						      })           
                    }
             ]);
          GridModifyPanel.superclass.initComponent.call(this);
          
           this.store = new Ext.data.JsonStore({
                url:webContext+'/pageModel/pageModelManage.do?methodCall=toPageModelList&pmID='+modId,
                root:"data",
                totalProperty:"rowCount",
                remoteSort:false,  
                timeout:3000000,
                fields:this.storeMapping
            });
            
            this.store.paramNames.sort="orderBy";
            this.store.paramNames.dir="orderType";    
            this.cm.defaultSortable=true;    
            var viewConfig=Ext.apply({forceFit:true},this.gridViewConfig);
  			GridModifyPanel.superclass.initComponent.call(this);
  			
            //工具栏
            this.grid=new Ext.grid.EditorGridPanel({        //GridPanel改成EditorGridPanel
                store: this.store,
                cm: this.cm,
                sm: csm,
                width:this.width-25,
                height:200,
                clickToEdit: 2,
             	frame:true,
                tbar : ['   ',
                     
                    {    
                        text: '增加',  
                        pressed: true, 
//                        id:'exportBtn',
                        handler: this.addBook,
                        scope:this,
                        iconCls:'add',
                        cls:"x-btn-text-icon"
                    }
                    ,'&nbsp;|&nbsp;',
                    {
                        text:"删除",
                        pressed: true,
                        handler:this.deleBook,
                        scope:this,
                        iconCls:'delete',
                        cls:"x-btn-text-icon"
                    }
                    ,'&nbsp;|&nbsp;',
                    {
                        text:"保存",
                        pressed:true,
                        handler:this.saveData,
                        scope:this,
                        iconCls:"save",
                        cls:"x-btn-text-icon"
                    }
                   
                    
                ]
            });   
//            this.grid.on("afteredit",this.afterEdit,this); 
            this.grid.on("validateedit",this.validateedit,this); 
            this.grid.on("beforeedit",this.beforeedit,this); 
            this.grid.on("headerdblclick",this.fitWidth,this);  
            this.add(this.grid);
//           this.items = [this.grid];
            
        },
        
        initData:function(){
            
            var param = {'methodCall':'list','start':1};
            this.pageBar.formValue = param;
            this.store.removeAll();
            this.store.load({params:param});
        },
        
       fitWidth:function(grid, columnIndex, e){  
            var c = columnIndex;
            var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
            for (var i = 0, l = grid.store.getCount(); i < l; i++) {
                w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
            }
            grid.colModel.setColumnWidth(c, w); 
        }
});