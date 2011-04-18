//用户管理Panel

	UserManagePanel = Ext.extend(Ext.Panel,{
		id:"userManagePanel",
		title:"用户管理",
		closable: true,
		viewConfig: {
	        autoFill: true,
	      	forceFit: true
	    },
	  	layout: 'absolute',
	  	
	  	//////////////////////////////////////////////////////
		checkRolEdit : function(){
		 var userDetailForm = new UserRolForm();
		 var userForm = userDetailForm.form;
				 
		 roleGrid = new RoleGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'选择角色',
		 	modal: true,
		 	height:400,
	        width:510,
	        resizable:false,
	        viewConfig: {
		        autoFill: true,
		      	forceFit: true
	        },
	        layout: 'absolute',
	        buttons:[
	        	{xtype:'button',
	        	text:'添加',
	        	handler:function(){ 
	        		var record = roleGrid.getSelectionModel().getSelected();
	        		if (!record) {
						Ext.Msg.alert("提示", "请先选择要添加的行!");
						return;
					}
	    			var records = roleGrid.getSelectionModel().getSelections();
	    			for (var i = 0; i < records.length; i++) {
	    				var id = records[i].get("id");
	    				var name = records[i].get("name");
		    			var descn = records[i].get("descn");
		    			var data = [{
							id : 'id',
							mapping : 'id'
						},{
							name : 'name',
							mapping : 'name'
						}, {
							name : 'descn',
							mapping : 'descn'
						}]
						var gridRecord = Ext.data.Record.create(data);
						var dataRecord = new gridRecord({
							id : id,
							name : name,
							descn : descn
						});
					roleEditGrid.store.add([dataRecord]);
	    			}
	        	},
	        	scope:this
	        	},
	        	{xtype:'button',
	        	handler:function(){userDetailWin.close();},
	        	text:'关闭',
	        	scope:this
	        	}
	        ],
	        items:[userForm,roleGrid]
		});
		userDetailWin.show();
	  },
	  	
		checkRol : function(){
		 var userDetailForm = new UserRolForm();
		 var userForm = userDetailForm.form;
				 
		 roleGrid = new RoleGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'选择角色',
		 	modal: true,
		 	height:400,
	        width:510,
	        resizable:false,
	        viewConfig: {
		        autoFill: true,
		      	forceFit: true
	        },
	        layout: 'absolute',
	        buttons:[
	        	{xtype:'button',
	        	text:'添加',
	        	handler:function(){
	        		var record = roleGrid.getSelectionModel().getSelected();
	        		if (!record) {
						Ext.Msg.alert("提示", "请先选择要添加的行!");
						return;
					}
	    			var records = roleGrid.getSelectionModel().getSelections();
	    			for (var i = 0; i < records.length; i++) {
	    				var id = records[i].get("id");
	    				var name = records[i].get("name");
		    			var descn = records[i].get("descn");
		    			var data = [{
							id : 'id',
							mapping : 'id'
						},{
							name : 'name',
							mapping : 'name'
						}, {
							name : 'descn',
							mapping : 'descn'
						}]
						var gridRecord = Ext.data.Record.create(data);
						var dataRecord = new gridRecord({
							id : id,
							name : name,
							descn : descn
						});
					roleListGrid.store.add([dataRecord]);
	    			}
	        	},
	        	scope:this
	        	},
	        	{xtype:'button',
	        	handler:function(){userDetailWin.close();},
	        	text:'关闭',
	        	scope:this
	        	}
	        ],
	        items:[userForm,roleGrid]
		});
		userDetailWin.show();
	  },
	  	//////////////////////////////////////////////////////
	  	
		addUser : function(){
		 var userDetailForm = new UserDetailForm();
		 var userForm = userDetailForm.form;
				 
		 roleListGrid = new RoleListGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'新建用户',
		 	modal: true,
		 	height:400,
	        width:510,
	        resizable:false,
	        viewConfig: {
		        autoFill: true,
		      	forceFit: true
	        },
	        layout: 'absolute',
	        buttons:[
	        	{xtype:'button',
	        	handler:function(){
	        		if(! userDetailForm.beforeSave()){
	        			return ;
	        		}
        			var records = roleListGrid.getStore().getRange(0,
						roleListGrid.getStore().getCount());
                    var roles = new Array();
                    for (var i = 0; i < records.length; i++) {
                        roles[i] = records[i].data.id;
                    }
	                var realName = userDetailForm.realName.getValue();
                    var userName = userDetailForm.userName.getValue();
                    var password = userDetailForm.password.getValue();
                    var email = userDetailForm.email.getValue();
                    var telephone = userDetailForm.telephone.getValue();
                    var mobilePhone = userDetailForm.mobilePhone.getValue();
                    var value = userDetailForm.enabledValue.getValue();	 
                    var specialUser = userDetailForm.specialUserValue.getValue();	
                    var department = userDetailForm.department.getValue();
                    
//                    alert("是否是特殊用户(0:否,1:是)："+specialUser);
//                    alert("部门编号: "+department);
                    
                    var request = {
                        roleId: roles,
                        realName: unicode(realName),
                        userName: userName,
                        password: password,
                        email: email,
                        telephone: telephone,
                        mobilePhone: mobilePhone,
                        enabled: value,
                        specialUser: specialUser,
                        department: department
                    };
                    //alert(Ext.encode(request))
                    Ext.Ajax.request({
                        url: webContext+'/sysManage/userRoleAction.do?methodCall=saveUsers',
                        method: 'POST',
                        params: request,
                        success: function(result, options){
                        	 ReturnValue = Ext.MessageBox.alert("警告", "成功保存用户信息！");
                        	 userDetailWin.destory();
                        	 this.store.reload();
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("警告", "保存用户信息失败！");
                        },
                        scope:this
                    });
	        	},
	        	text:'保存',
	        	scope:this
	        	},
	        	{xtype:'button',
	        	handler:function(){userDetailWin.close();},
	        	text:'关闭',
	        	scope:this
	        	}
	        ],
	        items:[userForm,roleListGrid]
		});
		userDetailWin.show();
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
//		    alert("department: "+department);
		    var userDetailEditForm = new UserDetailEditForm(id,department);
		    var userEditForm =  userDetailEditForm.form;
	        roleEditGrid = new RoleListEditGrid(id).grid;
	        roleEditGrid.editRemovedIds = "";
	        
	        var editWin = new Ext.Window({
			 	title:'修改用户',
			 	modal: true,
			 	height:400,
		        width:520,
		        resizable:false,
		        viewConfig: {
			        autoFill: true,
			      	forceFit: true
		        },
		        layout: 'absolute',
		        buttons:[
		        	{xtype:'button',
		        	handler:function(){
						var records = roleEditGrid.getStore().getRange(0,
						roleEditGrid.getStore().getCount());
                    	var json = new Array();
                   		for (var i = 0; i < records.length; i++) {
                   			json.push(records[i].data)
                   	    }
						
//						if(json.length==0){
//							Ext.Msg.alert('信息','没有对数据进行任何更改');
//							return;
//						}
//						else{
//							Ext.Msg.alert('信息',Ext.encode(json));
//						}
					   	                    
	                   var realName = userDetailEditForm.realName.getValue();
                       var userName = userDetailEditForm.userName.getValue();
                       var password = userDetailEditForm.password.getValue();
                       var email = userDetailEditForm.email.getValue();
                       var telephone = userDetailEditForm.telephone.getValue();
                       var mobilePhone = userDetailEditForm.mobilePhone.getValue();
                       var department = userDetailEditForm.department.getValue();
                       
                       var eValue = userDetailEditForm.enabled.getValue();	 
                       var enabled = 0;
                       
                       if(eValue == true){
                       	   enabled = 1;
                       }else{
                       	   enabled = 0;
                       }
                       var sValue= userDetailEditForm.specialUser.getValue();		
                       var specialUser = 0;
                       if(sValue==true){
                       	   specialUser = 1;
                       }else{
                       	   specialUser = 0;
                       }
                       var id = userDetailEditForm.id.getValue();

	                   var request = {
	                        data: Ext.encode(json),
	                        realName: unicode(realName),
	                        userName: userName,
	                        password: password,
	                        email: email,
	                        telephone: telephone,
	                        mobilePhone: mobilePhone,
	                        department: department,
	                        enabled: enabled,
	                        specialUser: specialUser,
	                        id:id,
	                        editRemovedIds : roleEditGrid.editRemovedIds
	                    };
	                    Ext.Ajax.request({
	                        url: webContext+'/sysManage/userRoleAction.do?methodCall=modifyUser',
	                        method: 'POST',
	                        params: request,
	                        waitMsg:'正在保存用户信息......',
	                        success: function(result, options){
	                        	 ReturnValue = Ext.MessageBox.alert("警告", "成功修改用户信息！");
								 editWin.close();
								 this.store.reload();	
	                        },
	                        failure: function(response, options){
	                            ReturnValue = Ext.MessageBox.alert("警告", "修改用户信息失败！");
	                        },
	                        scope:this
	                    });
		        	},
		        	text:'保存',
		        	scope:this
		        	},
		        	{xtype:'button',
//modify by oucy	handler:function(){editWin.hide()},
					handler:function(){editWin.close()},
		        	text:'关闭',
		        	scope:this
		        	}
		        ],
		        items:[userEditForm,roleEditGrid]
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
		var ids = new Array();
		for(var i=0;i<records.length;i++){			
			ids[i] = records[i].data.id;
		}
		var m=Ext.MessageBox.confirm("删除提示","是否真的要删除数据？一旦删除,不可恢复!",function(ret){
		if(ret=="yes"){
			  Ext.Ajax.request({
	            url:webContext+'/sysManage/userRoleAction.do?methodCall=removeUsers',
	            params:{
	                'id':ids
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
	  
	  reset:function(){
	  		this.searchForm.form.reset();
	  },
	  
	  search : function(){
	  	    if(!userSearchForm.beforeSearch()){
	  	    	return;
	  	    }
	  	    var param = {userName:unicode(userSearchForm.userName.getValue()),
	  	    			 realName:unicode(userSearchForm.realName.getValue())
	  	    
	  	    };
	    	param.methodCall='listUsers';
	    	this.formValue = param;
	    	this.pageBar.formValue = this.formValue;
	    	param.start=1;
	    	this.store.removeAll();
	    	this.store.load({params:param});
	  	    
	  },
	  
	  initComponent : function(){  
	  	   this.searchForm = userSearchForm.form;		
		   var csm = new Ext.grid.CheckboxSelectionModel();
		   this.storeMapping = ['realName', 'userName', 'password', 'email', 'enabled','specialUser', 'department', 'id']; 
		   this.cm=new Ext.grid.ColumnModel([csm,{
	                    header: "姓名",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "realName",
	                    width: 150,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "用户名",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "userName",
	                    width: 150,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "密码",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "password",
	                    width: 100,
	                    editor: new Ext.form.TextField()
                    },{
	                    header: "电子邮件",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "email",
	                    width: 160,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "隶属部门",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "department",
	                    width: 200,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "是否可用",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "enabled",
	                    width: 70,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "是否特殊用户",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "specialUser",
	                    width: 100,
	                    editor: new Ext.form.TextField()
                	}, {
                		header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     		}
		     ]);
	       UserManagePanel.superclass.initComponent.call(this);
	       //数据
		   this.store = new Ext.data.JsonStore({
				id:"id",
		       	url: webContext+'/sysManage/userRoleAction.do',
		       	root: "users",
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
	            pageSize: 10,
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
		        y:35,
		        anchor: '0 -35',
		        clicksToEdit:1,
		        tbar : [{    
		                text: '查询',  
		                pressed: true,           
		                handler: this.search,
		                scope:this,
		                iconCls:'search'
		            },'&nbsp;',
		            {    
		                text: '重置',  
		                pressed: true,           
		                handler: this.reset,
		                scope:this,
		                iconCls:'reset'
		            },'&nbsp;|&nbsp;',
		       		 {    
		                text: '新建用户',  
		                pressed: true,           
		                handler: this.addUser,
		                scope:this,
		                iconCls:'add'
		            },'&nbsp;',
		            {    
		                text: '修改',  
		                pressed: true, 
		                id:'modify',
		                handler: this.modify,
		                scope:this,
		                iconCls:'edit'
		            },'&nbsp;',
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
			  			   		
	   		this.searchForm.height="100";
	   		this.searchForm.width="1000";
	   		this.grid.on("rowdblclick",this.modify,this);
	   		this.add(this.searchForm);	   		
	   		this.add(this.grid);	
		},
		initData:function(){
			var param = {'methodCall':'listUsers','start':1};
	   		this.pageBar.formValue = param;
	   		this.store.removeAll();
	   		this.store.load({params:param});
		}
});
