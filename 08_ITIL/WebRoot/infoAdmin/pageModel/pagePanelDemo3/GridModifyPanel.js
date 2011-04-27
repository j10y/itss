
GridModifyPanel =Ext.extend(Ext.Panel, {
		title: '������������ϵ',
		width: 'auto',
		id : 'gridModifyPanel',
		colspan:2,
		autoScroll: true,
		animate: true,
		containerScroll: true,  
	    height: 150,
		viewConfig : {//����Ӧ���
			autoFill : true,
			forceFit : true
		},
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
               Ext.Msg.alert("��ʾ","��ѡ����Ҫɾ������");
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
           var m = Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����ɾ��,һ��ɾ�����ɻָ�!",function(re){
               if(re=="yes"){
                   Ext.Ajax.request({
                      // url:webContext+'/deleteBook.action',
                    url:webContext+'/pageModel/pagePanelManage.do?methodCall=toForeignTableRemove',	
                       params:{
                           'ids':ids
                       },
                       mothod:'POST',
                       timeout:100000,
                       success:function(response){
                           var r=Ext.decode(response.responseText);
                           if(!r.success)Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
                           else{
                                Ext.Msg.alert("��ʾ��Ϣ","�ɹ�ɾ������!",function(){
                                    stored.reload();  
                                },this);
                           }
                           stored.reload();    
                       }, 
                       scope:this
                   });
               }
           },this);
           
        },
         saveData:function(){
	    	Ext.MessageBox.confirm('��ʾ��Ϣ', 'ȷʵҪ�����޸���?  ',function(ret){
				if(ret!="yes"){
					this.store.reload();
					return;
				}
				
				
			var gridParam = this.grid.getStore().getModifiedRecords(0, //ֻȡ�޸ĵ�
				 this.grid.getStore().getCount());
			var price = "";
			// alert(gridParam.length);
			// return;
			for (i = 0; i < gridParam.length; i++) {
				price += Ext.encode(gridParam[i].data) + ",";
			}
//			alert(price);
//			price = unicode(price);
//			alert(price);
				
				this.grid.getStore().each(function(record){
					if(record.dirty){
						//this.store.reload();
						Ext.Ajax.request({
							//url: 'test/TableColumnList.action?id='+id,
						
					         url:webContext+'/pageModel/pagePanelManage.do?methodCall=toForeignTableSave',				         
               				 method:"POST",
               				 params:{
               				 	'price' : unicode(price),
               				 	'ppId' : unicode(ppId)
               				 },
					         success:function(response){
						        if(response.responseText.indexOf("�ܾ�") != -1){
						        	Ext.Msg.alert("��ʾ��Ϣ","��û��Ȩ���޸���Ϣ");
						        }else{						        	
							    
							        Ext.Msg.alert("��ʾ��Ϣ","�������ݳɹ���");
							        this.store.reload();
						        }
						        this.store.reload();
					        },
					        scope:this
						});
					}
				},this);	
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
			var r = obj.record;
			var modifyField = obj.field; 
			var value = r.get("systemMainTable");
			//alert(value);
			var reg=/^[\u4e00-\u9fa5]+$/g; //����
			if(value==null||reg.test(value)){
				this.foreignstore.removeAll();
			}else if(value!=reg){
				this.foreignstore.load({
					params: {
						  'value' : unicode(value)	
					}
				});
				this.foreignColstore.reload();
			}
		}, 

		validateedit:function(obj){//�Ӹ�ͬ�����жϸ��ڶ��������б����Ĭ��ֵ
			if(obj.field=="foreignTableColumn"&&obj.originalValue!=obj.value){
				var foreignTableColumn = obj.value;
				var code="";
				Ext.Ajax.request({
                    url:webContext+'/pageModel/pagePanelManage.do?methodCall=findForeignByColumnID',	
                     method:"POST",
                    params:{
                           'foreignTableColumn':foreignTableColumn
                       },
                    success:function(response,request){
                    	code=response.responseText;
                    	obj.record.set("foreignTable",code);
                    },
                    failure:function(){
                    alert("��������ʧ��");
                    },
                     scope:this
				});
				//this.foreignColstore.reload();
			}
			return true;
		}, 
		
		//************************************
		

		
	//*******************************************	
        initComponent : function(){ 
        	var combostore = new Ext.data.JsonStore({
										url:webContext+'/pageModel/pagePanelManage.do?methodCall=findAllmainTable',
										fields:['name','code'],
										totalProperty:"rowCount",
										root:"data"
								
										}
									);
					this.foreignstore = new Ext.data.JsonStore({
										url:webContext+'/pageModel/pagePanelManage.do?methodCall=findAllForeignCol',
										fields:['name','code'],
										totalProperty:"rowCount",
										root:"data"
										}
									);	
									
					this.foreignColstore = new Ext.data.JsonStore({		
						                //url:webContext+'/pageModel/pagePanelManage.do?methodCall=findForeignByColumnID',
						                url:webContext+'/pageModel/pagePanelManage.do?methodCall=findAllmainTable',
										fields:['name','code'],
										totalProperty:"rowCount",
										root:"data"
										}
									);
			this.tempcolumn="";
           var csm = new Ext.grid.CheckboxSelectionModel();
           this.storeMapping = ['id','systemMainTable', 'foreignTableColumn','foreignTable']; 
           this.cm = new Ext.grid.ColumnModel([csm,{
           				header: "ID",
                        sortable: true,
                        hidden:true,
                        dataIndex: "id",
                        editor: new Ext.form.TextField()
                    },{
                        header: "��������",
                        sortable: true,
                        renderer:function(value){
				        var comboObject = Ext.getCmp('comboxid');
							var combostore = comboObject.store;
							if (combostore.find('code', value) !=-1){ 
						           return combostore.getAt(combostore.find('code', value)).get('name');
							     } 
							else{
								     return  value;
							     } 
                        },
                        dataIndex: "systemMainTable",
                         editor: new Ext.form.ComboBox({                        
						    		name:"pagePanelTableRelation.systemMainTable",
						    		id:"comboxid",
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
						    		selectOnFocus:true,
						    		pageSize : 7
						      })
                    },
                    	{
                        header: "���",
                        id:'temp2',
                        sortable: true,
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
                        dataIndex: "foreignTableColumn",
                            editor: new Ext.form.ComboBox({                        
						    		name:"pagePanelTableRelation.foreignTableColumn",
						    		id:"combox2id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		mode:'local',
						    		forceSelection : true,
									store:this.foreignstore,
								    editable:false,
						    		typeAhead: true,
						    		triggerAction: 'all',
						    		emptyText:'',
						    		selectOnFocus:true,
						    		pageSize : 7
						    		
//						    		listeners:{//��form�п���
//						    			select : function(combo,record,index){
//						    				alert(record.get("code"));
//						    				Ext.getCmp('combox3id').setValue('10');
//								           change : function(box, newv,oldv){
//								           			alert(newv);
//								           			//var temp='10';//fn(newv);
//								           			alert(Ext.getCmp('combox3id'));
//									           		Ext.getCmp('combox3id').setValue('10');
//									           		store.load({
//														params:{
//															id:temp
//														},
//														callback:function(r, options, success){
//															Ext.getCmp('combox3id').setValue(temp);
//														}
//													});
//									           }      
//								   		  }
						      })
                    },{
                        header: "�����",
                        sortable: true,
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
                        dataIndex: "foreignTable",
                       // editor : new Ext.form.TextField()
                        editor: new Ext.form.ComboBox({                        
						    		name:"pagePanelTableRelation.foreignTable",
						    		id:"combox3id",
						    		displayField:'name',
						    		valueField:"code",
						    		hiddenName:"name",
						    		mode:'remote',
						    		forceSelection : true,
									store:this.foreignColstore,
								    editable:false,
						    		triggerAction: 'all',
						    		typeAhead:true,
						    		emptyText:'',
						    		selectOnFocus:true,
						    		pageSize : 7
//						    		listeners:{        //��form�п���
//								           select : function(combo, record,index)   
//									           {     
//									           		var code = this.foreignstore.code;
//									           		var value = combo.value;
//									           		alert(code);
//									           }      
//								   		  }
						      })
                    }
             ]);
          GridModifyPanel.superclass.initComponent.call(this);
          
           this.store = new Ext.data.JsonStore({
                url:webContext+'/pageModel/pagePanelManage.do?methodCall=toForeignTableList&ppId='+ppId,
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
  			
            //������
            this.grid=new Ext.grid.EditorGridPanel({        //GridPanel�ĳ�EditorGridPanel
                store: this.store,
                cm: this.cm,
                sm: csm,
                width:1200,
                height:300,
                clickToEdit: 2,
                viewConfig: {
                    autoFill: true,
                    forceFit: true
                },
             
                tbar : ['   ',
                     
                    {    
                        text: '����',  
                        pressed: true, 
//                        id:'exportBtn',
                        handler: this.addBook,
                        scope:this,
                        iconCls:'add',
                        cls:"x-btn-text-icon"
                    }
                    ,'&nbsp;|&nbsp;',
                    {
                        text:"ɾ��",
                        pressed: true,
                        handler:this.deleBook,
                        scope:this,
                        iconCls:'delete',
                        cls:"x-btn-text-icon"
                    }
                    ,'&nbsp;|&nbsp;',
                    {
                        text:"����",
                        pressed:true,
                        handler:this.saveData,
                        scope:this,
                        iconCls:"save",
                        cls:"x-btn-text-icon"
                    }
                   
                    
                ]
            });   
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