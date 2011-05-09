/*
 * Դ�������
 */
ConfigSourceDataPanel = Ext.extend(Ext.grid.GridPanel, {
	title: 'ϵͳ�����ֶ�����',
	width: 1200,
	closable : true,
	viewConfig : {//����Ӧ���
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
        reset:function(){
            this.fp.form.reset();
        },
        
        //���ҷ���
        search:function(){
            //��ȡ�������������
            //var param = this.fp.form.getValues();
            //this.formValue = param;
            //this.pageBar.formValue = this.formValue;
            //ɾ�����е�����
          //  this.store.removeAll();
            //��store���¼�������
            //this.store.load({params:param});
        },
        
        modifyBook:function(){
          var record = this.grid.getSelectionModel().getSelected();
          var records = this.grid.getSelectionModel().getSelections();
          if(!record){
          	window.location.href = "configItem.jsp?id="+id;
              // Ext.Msg.alert("��ʾ","��ѡ����Ҫ�޸ĵ���");
              // return;
          }
          if(records.length>1){
               Ext.Msg.alert("��ʾ","һ��ֻ���޸�һ��");
               return ;
          }
          var id = record.data.id;
          window.location.href = "configItem.jsp?id="+id;
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
           
           var m = Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����ɾ��,һ��ɾ�����ɻָ�!",function(re){
               if(re=="yes"){
                   Ext.Ajax.request({
                      // url:webContext+'/deleteBook.action',
                   	url:"frameBookDelete.action",
                       params:{
                           'id':ids
                       },
                       mothod:'POST',
                       timeout:100000,
                       success:function(response){
                           var r=Ext.decode(response.responseText);
                           if(!r.success)Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"+(r.errors.msg?r.errors.msg:"δ֪ԭ��"));
                           else{
                                Ext.Msg.alert("��ʾ��Ϣ","�ɹ�ɾ������!",function(){
                                    //this.store.reload();  
                                },this);
                           }
                           this.store.reload();    
                       }, 
                       scope:this
                   });
               }
           },this);
           
        },
  
        addBook : function(){
            
            window.location.href = 'addBook.jsp';
        },
  

        saveData:function(){
	    	Ext.MessageBox.confirm('��ʾ��Ϣ', 'ȷʵҪ�����޸���?  ',function(ret){
				if(ret!="yes"){
					return;
				}
				this.grid.getStore().each(function(record){
					if(record.dirty){
//						var id = record.get("id");
//						var bookName = record.get("bookName");
//						var bookCode = record.get("bookCode");
//						var author = record.get("author");
//						var id = record.get("id");
////						var param = {"id":id};
////						
//						var param = {'methodCall':'list','start':1};						
//						param.partStyle = partStyle;
//						param.name = name;
						Ext.Ajax.request({
//					        url:'/b2b/frameBookSave.action.do?methodCall=save&'+Ext.urlEncode(param),
//					        method:'get',
					         url:"frameBookSave.action",
               				 method:"POST",
					        success:function(response){
						        if(response.responseText.indexOf("�ܾ�") != -1){
						        	Ext.Msg.alert("��ʾ��Ϣ","��û��Ȩ���޸���Ϣ");
						        }else{						        	
							    
							        Ext.Msg.alert("��ʾ��Ϣ","�������ݳɹ���");
							        this.store.reload();
						        }
						        
					        },
					        scope:this
						});
					}
				},this);	
	   	   },this);
	    },
        
        
        initComponent : function(){ 

           //this.fp = borrowForm.form;
            //��ͷ
           var csm = new Ext.grid.CheckboxSelectionModel();
           this.storeMapping = ['id', 'PropertyName', 'ColumnType', 'ColumnName', 'ColumnCnName','SystemMainTableColumnTypeID','ForeignTableID','aa','bb']; 
           this.cm = new Ext.grid.ColumnModel([csm,{
                        header: "ID",
                        sortable: true,
                        hidden: false,
                        dataIndex: "id",
                          width: 100
                    }, {
                        header: "����",
                        sortable: true,
                        //hideable: false,
                        dataIndex: "PropertyName",
                          width: 100 
                    },{
                        header: "�ֶ�",
                        width: 100,
                        sortable: true,
                        //hideable: false,
                        dataIndex: "ColumnName"
                        
                    },{
                        header: "�ֶ���������",
                        sortable: true,
                        //hideable: false,
                        dataIndex: "ColumnCnName",
                        width: 200,
                        editor: new Ext.form.TextField()
                    },{
                        header: "������",
                        sortable: true,
                       // hideable: false,
                        dataIndex: "SystemMainTableColumnTypeID",
                        width: 100,
                        editor: new Ext.form.TextField()
                    }, {
                        header: "������",
                        sortable: true,
                       //hideable: false,
                        dataIndex: "ColumnType",
                        width: 100,
                        editor: new Ext.form.TextField()
                    },
                    {
                        header: "��������ʾ�ֶ�",
                        sortable: true,
                       // hideable: false,
                        dataIndex: "aa",
                        width: 100,
                        editor: new Ext.form.TextField()
                    },
                    {
                        header: "������֤����",
                        sortable: true,
                       // hideable: false,
                        dataIndex: "bb",
                        width: 100,
                        editor: new Ext.form.TextField()
                    }
             ]);
             this.cm.defaultSortable=true;    
             
           ConfigSourceDataPanel.superclass.initComponent.call(this);
          
           this.store = new Ext.data.JsonStore({
                id:"id",
                //��������ʱ�õ�url,store��load()����������ͨ��load({params:xxx})�����������ݴ��ݣ������൱��Ajax����
                url: '/b2b/frameBookList.action',
                //���ڵ������
                root:"data",
                //�����������ƣ����ڷ�ҳ
                totalProperty:"rowCount",
                remoteSort:false,  
                timeout:3000000,
                //�����ֶ�,����ʱһ������
                fields:this.storeMapping
            });
            
            this.store.paramNames.sort="orderBy";
            this.store.paramNames.dir="orderType";    
                
 //           var viewConfig=Ext.apply({forceFit:true},this.gridViewConfig);  
            //��ҳ
//            this.pageBar = new Ext.PagingToolbarExt({
//                pageSize: 10,
//                store: this.store,
//                displayInfo: true,
//                displayMsg: '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
//                emptyMsg: "����ʾ����"
//            });
//            this.formValue = '';
//            this.pageBar.formValue = this.formValue;
            
            //������
            this.grid=new Ext.grid.EditorGridPanel({        //GridPanel�ĳ�EditorGridPanel
                store: this.store,
                cm: this.cm,
                sm: csm,
                trackMouseOver:false,    
                loadMask: true,
                y:60,
                anchor: '0 -60',
                viewConfig: {
                    autoFill: true,
                    forceFit: true
                },
             
                tbar : ['   ',
                     {    
                        text: ' ��ѯ',  
                        pressed: true,           
                        handler: this.search,
                        scope:this,
                        iconCls:'search',
                        cls:"x-btn-text-icon"
                    },'&nbsp;|&nbsp;',
                    {    
                        text: '����',  
                        pressed: true, 
                        id:'exportBtn',
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
                        text:"�޸�",
                        pressed:true,
                        handler:this.modifyBook,
                        scope:this,
                        iconCls:"modify",
                        cls:"x-btn-text-icon"
                    }
                    ,'&nbsp;|&nbsp;'
                    
                ]
                
             //   bbar: this.pageBar
            });   
            this.grid.on("afteredit",this.afterEdit,this); 
            this.grid.on("headerdblclick",this.fitWidth,this);  

//            this.fp.height="200";
//            this.fp.width="1000";
            this.grid.height='200';
         //   this.add(this.fp);          
            this.add(this.grid);
            
        },
        
        initData:function(){
            
            var param = {'methodCall':'list','start':1};
           //this.pageBar.formValue = param;
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