//�û�����Panel

	UserManagePanel = Ext.extend(Ext.Panel,{
		id:"userManagePanel",
		title:"�û�����",
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
		 	title:'ѡ���ɫ',
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
	        	text:'���',
	        	handler:function(){ 
	        		var record = roleGrid.getSelectionModel().getSelected();
	        		if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ӵ���!");
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
	        	text:'�ر�',
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
		 	title:'ѡ���ɫ',
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
	        	text:'���',
	        	handler:function(){
	        		var record = roleGrid.getSelectionModel().getSelected();
	        		if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ӵ���!");
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
	        	text:'�ر�',
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
		 	title:'�½��û�',
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
                    
//                    alert("�Ƿ��������û�(0:��,1:��)��"+specialUser);
//                    alert("���ű��: "+department);
                    
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
                        	 ReturnValue = Ext.MessageBox.alert("����", "�ɹ������û���Ϣ��");
                        	 userDetailWin.destory();
                        	 this.store.reload();
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("����", "�����û���Ϣʧ�ܣ�");
                        },
                        scope:this
                    });
	        	},
	        	text:'����',
	        	scope:this
	        	},
	        	{xtype:'button',
	        	handler:function(){userDetailWin.close();},
	        	text:'�ر�',
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
				Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
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
			 	title:'�޸��û�',
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
//							Ext.Msg.alert('��Ϣ','û�ж����ݽ����κθ���');
//							return;
//						}
//						else{
//							Ext.Msg.alert('��Ϣ',Ext.encode(json));
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
	                        waitMsg:'���ڱ����û���Ϣ......',
	                        success: function(result, options){
	                        	 ReturnValue = Ext.MessageBox.alert("����", "�ɹ��޸��û���Ϣ��");
								 editWin.close();
								 this.store.reload();	
	                        },
	                        failure: function(response, options){
	                            ReturnValue = Ext.MessageBox.alert("����", "�޸��û���Ϣʧ�ܣ�");
	                        },
	                        scope:this
	                    });
		        	},
		        	text:'����',
		        	scope:this
		        	},
		        	{xtype:'button',
//modify by oucy	handler:function(){editWin.hide()},
					handler:function(){editWin.close()},
		        	text:'�ر�',
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
			Ext.Msg.alert("��ʾ","����ѡ��Ҫɾ������!");
			return;
		}
		var ids = new Array();
		for(var i=0;i<records.length;i++){			
			ids[i] = records[i].data.id;
		}
		var m=Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�һ��ɾ��,���ɻָ�!",function(ret){
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
	                    header: "����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "realName",
	                    width: 150,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "�û���",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "userName",
	                    width: 150,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "password",
	                    width: 100,
	                    editor: new Ext.form.TextField()
                    },{
	                    header: "�����ʼ�",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "email",
	                    width: 160,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "��������",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "department",
	                    width: 200,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "�Ƿ����",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "enabled",
	                    width: 70,
	                    editor: new Ext.form.TextField()
                	},{
	                    header: "�Ƿ������û�",
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
	       //����
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
	        //��ҳ
	        this.pageBar = new Ext.PagingToolbarExt({
	            pageSize: 10,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
	            emptyMsg: "����ʾ����"
	        });
	        this.formValue = '';
	        this.pageBar.formValue = this.formValue;
	   		//������
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
		                text: '��ѯ',  
		                pressed: true,           
		                handler: this.search,
		                scope:this,
		                iconCls:'search'
		            },'&nbsp;',
		            {    
		                text: '����',  
		                pressed: true,           
		                handler: this.reset,
		                scope:this,
		                iconCls:'reset'
		            },'&nbsp;|&nbsp;',
		       		 {    
		                text: '�½��û�',  
		                pressed: true,           
		                handler: this.addUser,
		                scope:this,
		                iconCls:'add'
		            },'&nbsp;',
		            {    
		                text: '�޸�',  
		                pressed: true, 
		                id:'modify',
		                handler: this.modify,
		                scope:this,
		                iconCls:'edit'
		            },'&nbsp;',
		            {    
		                text: 'ɾ��',
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
