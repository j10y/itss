﻿﻿﻿﻿﻿﻿
//角色管理Panel

	RoleManagePanel = Ext.extend(Ext.Panel,{
		id:"roleManagePanel",
		title:"角色管理",
		closable: true,
		viewConfig: {
	        autoFill: true,
	      	forceFit: true
	    },
	  	layout: 'fit',
	  	
		addRole : function(){
		  
		 var rightListGrid = new RightListGrid();
		 var roleDetailForm = new RoleDetailForm();
		 var roleForm = roleDetailForm.form;
		 var detailWin = new Ext.Window({
		 	title:'新建角色',
		 	modal: true,
		 	height:560,
	        width:475,
	        resizable:true,
	        viewConfig: {
		        autoFill: true,
		      	forceFit: true
	        },
	        layout: 'absolute',
	        buttons:[
	        	{xtype:'button',
	        	handler:function(){
        			var records = rightListGrid.getSelectionModel().getSelections();
                    var authIds = new Array();
                    for (var i = 0; i < records.length; i++) {
                        authIds[i] = records[i].data.id;
                    }
                    var name = roleDetailForm.name.getValue();
                    var descn= roleDetailForm.descn.getValue();
                    
                    var request = roleForm.form.getValues(false);
                    request.authId = authIds;
                    request.name =  unicode(name);
                    request.descn = unicode(descn);
                    //alert(Ext.encode(request));
                    Ext.Ajax.request({
                        url: webContext+'/sysManage/roleAction.do?methodCall=saveRoles',
                        method: 'POST',
                        params: request,
                        success: function(result, options){
                        	 detailWin.hide();
                        	 this.store.reload();
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("警告", "保存角色授权失败！");
                        },
                        scope:this
                    });
	        	},
	        	text:'保存角色授权',
	        	scope:this
	        	},
	        	{xtype:'button',
	        	handler:function(){detailWin.hide()},
	        	text:'关闭',
	        	scope:this
	        	}
	        ],
	        items:[roleForm,rightListGrid]
		});
		detailWin.show();
	  },
	  
	  modify: function(){
		  	var record = this.grid.getSelectionModel().getSelected();
	    	var records = this.grid.getSelectionModel().getSelections();
			if(!record){
				Ext.Msg.alert("提示","请先选择要修改的行!");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("提示","修改时只能选择一行!");
				return;
			}
		    var id = record.get("id");
		    var department = record.get("department");
		    var deptmt = record.get("deptmt");
		    
		    var roleDetailEditForm = new RoleDetailEditForm(id,department,deptmt);
		    var roleEditForm = roleDetailEditForm.form;
			var rightEditGrid = new EditRightListGrid(id).grid;
			
	        editWin = new Ext.Window({
			 	title:'修改角色',
			 	modal: true,
			 	height:540,
		        width:475,
		        resizable:false,
		        viewConfig: {
			        autoFill: true,
			      	forceFit: true
		        },
		        layout: 'absolute',
		        buttons:[
		        	{xtype:'button',
		        	handler:function(){
		        		
		        		var store = rightEditGrid.getStore();
		        		
		        		var json = [];
						for(i=0,cnt=store.getCount();i<cnt;i++){
							var record = store.getAt(i);
							if(record.dirty) // 得到所有修改过的数据
							json.push(record.data);
						}
						
	                    var name = roleDetailEditForm.name.getValue();
	                    var descn = roleDetailEditForm.descn.getValue();
	                    var id = roleDetailEditForm.id.getValue();
	                    var dataView = roleDetailEditForm.dataView.getValue();
	                    
	                    var request = roleEditForm.form.getValues(false);
	                    request.data =  Ext.encode(json);
	                    request.name = unicode(name);
	                    request.descn = unicode(descn);
	                    request.dataView = dataView;
	                    request.id = id;

	                    Ext.Ajax.request({
	                        url: webContext+'/sysManage/roleAction.do?methodCall=modifyRole',
	                        method: 'POST',
	                        params: request,
	                        success: function(result, options){
	                        	ReturnValue = Ext.MessageBox.alert("警告", "成功修改角色授权！");
	                        	editWin.close();
								this.store.reload();	
	                        },
	                        failure: function(response, options){
	                            ReturnValue = Ext.MessageBox.alert("警告", "修改角色授权失败！");
	                        },
	                        scope:this
	                    });
		        	},
		        	text:'保存',
		        	scope:this
		        	},
		        	{xtype:'button',
		        	handler:function(){editWin.close()},
		        	text:'关闭',
		        	scope:this
		        	}
		        ],
		        items:[roleEditForm,rightEditGrid]
		});
		editWin.show();
	  },
	  
	  removeData : function(){
	  	var record=this.grid.getSelectionModel().getSelected();
		var records=this.grid.getSelectionModel().getSelections();
		if(!record){
			Ext.Msg.alert("提示","请先选择要删除的行!");
			return;
		}
	    var  roleIds = new Array();
        for (var i = 0; i < records.length; i++) {
            roleIds[i] = records[i].data.id;
        }

		var m=Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
		if(ret=="yes"){
			  Ext.Ajax.request({
	            url:webContext+'/sysManage/roleAction.do?methodCall=removeRoles',
	            params:{
	                'id':roleIds
	            },
	            method:'POST',
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
		}},this);
	  },	
	  initComponent : function(){  
		   var csm = new Ext.grid.CheckboxSelectionModel();
		   this.storeMapping = ['name','descn','id','department','deptmt']; 
		   this.cm=new Ext.grid.ColumnModel([csm,{
	                    header: "角色名称",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "name",
	                    width: 260,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "描述",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "descn",
	                    width: 260,
	                    editor: new Ext.form.TextField()
                	},{ header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     		},{ header: "隶属部门",
                		sortable: true,
			            hidden: false,
			            dataIndex: "department"
		     		},{
		     			header: "可用菜单",
                		sortable: true,
			            hidden: true,
			            dataIndex: "deptmt"
		     		}
		     ]);
	       RoleManagePanel.superclass.initComponent.call(this);
	       //数据
		   this.store = new Ext.data.JsonStore({
				id:"id",
		       	url: webContext+'/sysManage/roleAction.do',
		       	root:"roles",
		  		totalProperty:"rowCount",
		  		remoteSort:false,  
		  		timeout:3000000,
	  			fields:this.storeMapping
	  		});
	  		
	      	this.store.paramNames.sort="orderBy";
		 	this.store.paramNames.dir="orderType";	  
	      	this.cm.defaultSortable=true;
	        var viewConfig=Ext.apply({forceFit:true},this.gridViewConfig);  
	        //分页
	        this.pageBar = new Ext.PagingToolbarExt({
	            pageSize: 25,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '当前显示 {0}-{1}条记录 /共{2}条记录',
	            emptyMsg: "无显示数据"
	        });
	        this.formValue = '';
	        this.pageBar.formValue = this.formValue;
	   		//工具栏
	   		this.grid=new Ext.grid.GridPanel({
		        store: this.store,
		        cm: this.cm,
		        sm: csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height:"auto",
		        y:0,
		        anchor: '0 -100',
		        viewConfig: {
			        autoFill: false,
		          	forceFit: true
		        },
		        clicksToEdit:1,
		        tbar : ['   ',
		       		 {    
		                text: '新建角色',  
		                pressed: true,           
		                handler: this.addRole,
		                scope:this,
		                iconCls:'add'
		            },'   ',		            
		            '&nbsp;',
		            {    
		                text: '修改',  
		                pressed: true, 
		                id:'modify',
		                handler: this.modify,
		                scope:this,
		                iconCls:'edit'
		            },'   ',		            
		            '&nbsp;',
		            {    
		                text: '删除',  
		                pressed: true, 
		                id:'delete',
		                handler: this.removeData,
		                scope:this,
		                iconCls:'delete'
		            }
		        ],
		        bbar: this.pageBar
	   		});   
			
	   		this.grid.on("rowdblclick",this.modify,this);
	   		this.add(this.grid);	   		
	   		
		},
		initData:function(){
			var param = {'methodCall':'listRoles','start':1};
	   		this.pageBar.formValue = param;
	   		this.store.removeAll();
	   		this.store.load({params:param});
		}

});

