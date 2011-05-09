 
    BookSearchPanel = Ext.extend(Ext.Panel,{
        id:"bookSearch",
        title:"系统主表",
        closable: true,
        viewConfig: {
            autoFill: true,
            forceFit: true
        },
        layout: 'absolute',
        
        reset:function(){
            this.fp.form.reset();
        },
        
        //查找方法
        search:function(){
            //获取表单里的所有内容
            var param = this.fp.form.getValues();
            this.formValue = param;
            this.pageBar.formValue = this.formValue;
            //删除所有的数据
            this.store.removeAll();
            //让store重新加载数据
            this.store.load({params:param});
        },
        
        modifyBook:function(){
          var record = this.grid.getSelectionModel().getSelected();
          var records = this.grid.getSelectionModel().getSelections();
          if(!record){
          	//window.location.href = "configItem.jsp?id="+id;
               Ext.Msg.alert("提示","请选择需要修改的行");
               return;
          }
          if(records.length>1){
               Ext.Msg.alert("提示","一次只能修改一个");
               return ;
          }
          var id = record.data.id;
          alert(id);
          window.location.href = "modify.jsp?id="+id;
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
           
           var m = Ext.MessageBox.confirm("删除提示","是否真的删除,一旦删除不可恢复!",function(re){
               if(re=="yes"){
                   Ext.Ajax.request({
                      // url:webContext+'/deleteBook.action',
                   	url:"test/TableRemove.action",
                       params:{
                           'id':ids
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
           },this);
           
        },
  
        addBook : function(){
            
            window.location.href = 'configItem.jsp';
        },
  
       
        
        saveData:function(){
	    	Ext.MessageBox.confirm('提示信息', '确实要保存修改吗?  ',function(ret){
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
					         url:"TableSave.action",
               				 method:"POST",
					        success:function(response){
						        if(response.responseText.indexOf("拒绝") != -1){
						        	Ext.Msg.alert("提示信息","您没有权限修改信息");
						        }else{						        	
							    
							        Ext.Msg.alert("提示信息","保存数据成功！");
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

           this.fp = borrowForm.form;
            //表头
           var csm = new Ext.grid.CheckboxSelectionModel();
           this.storeMapping = ['id', 'tableName', 'tableCnName', 'className', 'primaryKeyColumn','module']; 
           this.cm = new Ext.grid.ColumnModel([csm,{
                        header: "ID",
                        sortable: true,
                        hidden: false,
                        dataIndex: "id"
                    }, {
                        header: "系统表名",
                        sortable: true,
                        hideable: false,
                        dataIndex: "tableName",
                        width: 150,
                        editor: new Ext.form.TextField()
                    },{
                        header: "表名中文",
                        sortable: true,
                        hideable: false,
                        dataIndex: "tableCnName",
                        width: 100,
                        editor: new Ext.form.TextField()
                    },{
                        header: "映射实体类类全名",
                        sortable: true,
                        hideable: false,
                        dataIndex: "className",
                        width: 200,
                        editor: new Ext.form.TextField()
                    },{
                        header: "主键字段",
                        sortable: true,
                        hideable: false,
                        dataIndex: "primaryKeyColumn",
                        width: 100,
                        editor: new Ext.form.TextField()
                    }, {
                        header: "所属模块",
                        sortable: true,
                        hideable: false,
                        dataIndex: "module",
                        width: 100,
                        editor: new Ext.form.TextField()
                    }
             ]);
            
           BookSearchPanel.superclass.initComponent.call(this);
          
           this.store = new Ext.data.JsonStore({
                id:"id",
                //加载数据时用的url,store有load()方法，可以通过load({params:xxx})进行请求数据传递，这里相当于Ajax请求
                url: 'test/TableList.action',
                //根节点的名称
                root:"data",
                //总行数的名称，用于分页
                totalProperty:"rowCount",
                remoteSort:false,  
                timeout:3000000,
                //属性字段,可以时一个数组
                fields:this.storeMapping
            });
            
            this.store.paramNames.sort="orderBy";
            this.store.paramNames.dir="orderType";    
            this.cm.defaultSortable=true;           
            var viewConfig=Ext.apply({forceFit:true},this.gridViewConfig);  
            //分页
            this.pageBar = new Ext.PagingToolbarExt({
                pageSize: 10,
                store: this.store,
                displayInfo: true,
                displayMsg: '当前显示 {0}-{1}条记录 /共{2}条记录',
                emptyMsg: "无显示数据"
            });
            this.formValue = '';
            this.pageBar.formValue = this.formValue;
            //工具栏
            this.grid=new Ext.grid.EditorGridPanel({        //GridPanel改成EditorGridPanel
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
                        text: ' 查询',  
                        pressed: true,           
                        handler: this.search,
                        scope:this,
                        iconCls:'search',
                        cls:"x-btn-text-icon"
                    },'   ',                    
                    {    
                        text: ' 重置',  
                        pressed: true,          
                        handler: this.reset,
                        scope:this,
                        iconCls:'reset',
                        cls:"x-btn-text-icon"
                    },'&nbsp;|&nbsp;',
                    {    
                        text: '增加',  
                        pressed: true, 
                        id:'exportBtn',
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
                        text:"修改",
                        pressed:true,
                        handler:this.modifyBook,
                        scope:this,
                        iconCls:"modify",
                        cls:"x-btn-text-icon"
                    }
                    ,'&nbsp;|&nbsp;'
                    
                ],
                
                bbar: this.pageBar
            });   
            this.grid.on("afteredit",this.afterEdit,this); 
            this.grid.on("headerdblclick",this.fitWidth,this);  

            this.fp.height="200";
            this.fp.width="1000";
            this.grid.height='auto';
            this.add(this.fp);          
            this.add(this.grid);
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

