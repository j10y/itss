ConfigUnitRoleListPanel = Ext.extend(Ext.Panel, {
	id : "configUnitRoleListPanel",	
	closable : true,
	viewConfig : {//����Ӧ���
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	
	checkRolEdit : function(){
		 var userDetailForm = new UserRolForm();
		 var userForm = userDetailForm.form;			 
		 var roleGrid = new RoleGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'ѡ���ɫ',
		 	modal: true,
		 	height:300,
	        width:450,
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
						
						Ext.getCmp("roleEditGrid").store.add([dataRecord]);
	    			}
	    			userDetailWin.close();
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
	createCheck: function(){
		var checkValue = Ext.getCmp("createPer").getValue();
		var sendValue = Ext.getCmp("sendMail").getValue();
		var nodeType = this.type.getValue();
		var param = {
			checkValue : checkValue,
			virtualDesc : virtualDesc,
			processName : processName,
			nodeName : nodeName,
			nodeType : nodeType,
			sendValue : sendValue
		};
		Ext.Ajax.request({
			
	            url: webContext+'/configUnitRole_configUnitRoleCreateCheckSave.action?virtualDefinitionId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
	            method: 'POST',
	            params: param,
	            success: function(result, options){                        	
	            	                      		
	            	 Ext.Msg.alert("����", "�ɹ�������Ϣ��");
	            	
	            },
	            failure: function(response, options){
	                ReturnValue = Ext.MessageBox.alert("����", "������Ϣʧ�ܣ�");
	            },
	            scope:this
        });
	},
	sendMail : function(){
		var checkValue = Ext.getCmp("sendMail").getValue();
		var giveValue = Ext.getCmp("createPer").getValue();
		var nodeType = this.type.getValue();
		var param = {
			checkValue : checkValue,
			virtualDesc : virtualDesc,
			processName : processName,
			nodeName : nodeName,
			nodeType : nodeType,
			giveValue : giveValue
		};
		Ext.Ajax.request({
			
	            url: webContext+'/configUnitRole_configUnitRoleSendMailCheckSave.action?virtualDefinitionId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
	            method: 'POST',
	            params: param,
	            success: function(response, options){                        	
	                                   		
	            	 Ext.Msg.alert("����", "�ɹ�������Ϣ��");
	            	
	            },
	            failure: function(response, options){
	                ReturnValue = Ext.MessageBox.alert("����", "������Ϣʧ�ܣ�");
	            },
	            scope:this
        });
	
	},
	modify: function(){
			var gridStore = this.store;
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
		    var department = record.get("depName");				   
		    var userDetailEditForm = new UserDetailEditForm(id,department);
		    var userEditForm =  userDetailEditForm.form;
		 
	        var roleEditGrid = new RoleListEditGrid(id).grid;
	        
	        var editWin = new Ext.Window({
			 	title:'�޸���Ϣ',
			 	modal: true,
			 	height:300,
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
						
		        	   var record = this.grid.getSelectionModel().getSelected();	
		        	  // var records = roleListGrid.getStore().getRange(0,roleListGrid.getStore().getCount()); 
					   var records = roleEditGrid.getSelectionModel().getSelections();
					   var roles = new Array();
	                   for (var i = 0; i < records.length; i++) {
	                        roles[i] = records[i].data.id;                        
	                   }
	                   var flag = 0;	           
	                   for(var j=0; j< records.length;j++){
	                   		for(var p =0;p<records.length;p++){	                   			
	                   			if(roles[j]==roles[p]){	                   				
	                   				flag++;
	                   			}
	                   		}
	                   		 if(flag>=2){
		                   		Ext.Msg.alert("��ʾ","��ѡ����������ظ���ֵ!");
		                   		return;
		                   	 }else{
		                   	 	//alert(flag);
		                   		flag = 0;
		                   	 }
	                   }
	                   var definitonName = userDetailEditForm.definitonName.getValue();
	                   var nodeName = userDetailEditForm.nodeName.getValue();
	                   var department = record.get("depName");
	                   var nodeDesc = userDetailEditForm.nodeDesc.getValue();
	                   var roleType = userDetailEditForm.type.getValue();
                       
                      var id = record.get("id");					  
	                  var request = {
                        roleId: roles,
                        definitonName: unicode(definitonName),
                        nodeName: nodeName,
                        nodeDesc:nodeDesc,
                        department: department,
                        roleType : roleType,
                        id:id
                    };
                    if(!roles.length){
						Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
						return;
					}					
	                Ext.Ajax.request({
                        url: webContext+'/configUnitRole_configUnitRoleSave.action',
                        method: 'POST',
                        params: request,
                        success: function(result, options){
                        	var responseArray = Ext.util.JSON.decode(result.responseText);  
                        	if(!responseArray.success){
                        		Ext.MessageBox.alert("����", "�����û���Ϣʧ��(ԭ���������ѡ��Ľ�ɫ�Ѿ�����)��");
                        	}else{                        		
                        	 Ext.Msg.alert("����", "�ɹ�������Ϣ��",function(){                        	 	
                        	 	gridStore.reload();
                        	 	editWin.close() ;                  	 	
                        	 });
                        	}
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("����", "�����û���Ϣʧ��(ԭ���������ѡ��Ľ�ɫ�Ѿ�����)��");
                        },
                        scope:this
                    });
		        	},
		        	text:'����',
		        	scope:this
		        	},
		        	{xtype:'button',
		        	handler:function(){editWin.close()},
		        	text:'�ر�',
		        	scope:this
		        	}
		        ],
		        items:[userEditForm,roleEditGrid]
		});
		editWin.show();
	  },
	remove : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
			return;
		}
		var id = new Array();
		for(var i=0;i<records.length;i++){			
			id[i] = records[i].data.id;		
			this.grid.getStore().remove(records[i]);
		}

		var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
			if (button == 'yes') {
				Ext.Ajax.request({
						url : webContext
								+ '/configUnitRole_configUnitRoleDelete.action?virtualDefinitionId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
						params : {							
							removeIds : id
						},	
						timeout:1000000,
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){	                       			
	                       			this.store.reload();
	                       		});
	                       }
	                       else{
                                Ext.Msg.alert("��ʾ��Ϣ","����ɾ���ɹ�!",function(){                                 	
                                	this.store.reload();
                                },this);
	                       }	                      
	                       
	                   },scope:this							
					});
			}
		}, this);

	},
	checkRol : function(){
		 var userDetailForm = new UserRolForm();
		 var userForm = userDetailForm.form;		
		
		 var roleGrid = new RoleGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'ѡ���ɫ',
		 	modal: true,
		 	height:330,
		 	autoScroll:true,
	        width:450,
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
	    			if(records.length>1){
	    				Ext.Msg.alert("��ʾ", "һ����ɫֻ��Ӧһ����¼!");
						return;
	    			}
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
	    			userDetailWin.close();
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
	//�����ڵ��ɫ
	addUser : function(){
		
		 var gridStore = this.store;
		 var userDetailForm = new UserDetailForm();
		 var userForm = userDetailForm.form;
		 roleListGrid = new RoleListGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'�½��ڵ��ɫ',
		 	modal: true,
		 	height:300,
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
	        		
	        		var records = roleListGrid.getStore().getRange(0,roleListGrid.getStore().getCount()); 
        			//var records = roleListGrid.getSelectionModel().getSelections();        			
        			if (records.length<1) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ӵ���!");
						return;
					}	
                    var roles = new Array();
                    for (var i = 0; i < records.length; i++) {
                        roles[i] = records[i].data.id;                        
                    }
                   
	                var definitonName = userDetailForm.definitonName.getValue();
                    var nodeName = userDetailForm.nodeName.getValue();                	
          			var nodeDesc = userDetailForm.nodeDesc.getValue();
          			var roleType = userDetailForm.type.getValue();
          			var nodeType = this.type.getValue();
          			var checkCreate = Ext.getCmp("createPer").getValue();
          			var sendMail = Ext.getCmp("sendMail").getValue();
          			
          			if(nodeType==""||nodeType==null){          				
          				Ext.MessageBox.alert("����", "����ѡ��ڵ���������");
          				return;
          			}
                    var request = {
                        roleId: roles,
                        definitonName: unicode(definitonName),
                        nodeName: nodeName,
                        nodeDesc:nodeDesc,
                        roleType:roleType,
                        nodeType:nodeType,
                        checkCreate:checkCreate,
                        sendMail:sendMail
                    };
					
                    this.gridStore = Ext.getCmp("oldGrid").store;
                    Ext.Ajax.request({
                        url: webContext+'/configUnitRole_configUnitRoleSave.action?virtualDefinitionId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
                        method: 'POST',
                        params: request,
                        success: function(result, options){                        	
                        	var responseArray = Ext.util.JSON.decode(result.responseText);                          	
                        	if(!responseArray.success){
                        		Ext.MessageBox.alert("����", "�����û���Ϣʧ��(ԭ���������ѡ��Ľ�ɫ�Ѿ�����)��");
                        	}else{                        		
                        	 Ext.Msg.alert("����", "�ɹ�������Ϣ��",function(){                        	 	
                        	 	gridStore.reload();
                        	 	userDetailWin.close()                  	 	
                        	 });
                        	}
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("����", "������Ϣʧ�ܣ�");
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

	// ��ʼ��
	initComponent : function() {
		
		this.removeIds='';
		this.configItemName = '';		
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/configUnitRole_getConfigUnitRole.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
			root : "data",
			fields : ["id", "virtualDesc", "nodeName", "roleName", "nodeDesc","roleType","nodeType"]
		});
		
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
   		conn.open("POST", webContext+'/configUnitRole_getConfigUnitFlag.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId, false);
    	conn.send(null);
    	var data = Ext.util.JSON.decode(conn.responseText);
    	this.testFlag = "";
    	if(data.flag==1){
    		this.testFlag = "��һ������";
    	}else{
   		this.testFlag = "���������";
    	}
		var codes = [['0','���������'], ['1','��һ������']];
		var stores = new Ext.data.SimpleStore({
			fields : ['id','type'],
			data : codes
		});
		

	this.type= new Ext.form.ComboBox({
		       id:"nodeType",
				name : 'typeName',
				store : stores,
				triggerAction : 'all',
				displayField : 'type',
				valueField :'id',
				value : this.testFlag,
				mode : 'local',
				width : 200
	});	
		var obj=[{
			header : "������������",
			dataIndex : "virtualDesc"
		}, {
			header : "�ڵ�����",
			dataIndex : "nodeName"
		},
		{
			header : "�ڵ��ɫ",
			width :300,
			dataIndex : "roleName"
		},{
			header : "�ڵ�����",
			dataIndex : "nodeDesc"
		},
		{
			header : "��ɫ��������",
			width :200,
			dataIndex : "roleType"
		},{
			header : "�ڵ���������",
			width :200,
			dataIndex : "nodeType"
		}
		];
		var columns = new Array();
		var fields = new Array();

		columns[0] = sm;		
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;

			var isHidden = false;
			if (propertyName == 'id') {
				isHidden = true;
			}

			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
			columns[i + 1] = columnItem;
			
			fields[i] = propertyName;
		}
//		for(var i=0;i<columns.length;i++){
//				if(columns[i].dataIndex=="roleName"){
//					columns[i].width=300;
//				}
//				
//			}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);

		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;//����Ĭ��ֵ
		

		ConfigUnitRoleListPanel.superclass.initComponent.call(this);//�ø����ȳ�ʼ��
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,//ʹ�õ���ϵͳĬ��ֵ
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		this.isCreatePer = new Ext.form.Checkbox({
			id : 'createPer',
			xtype : 'checkbox',
			name : 'create',
			boxLabel : "���������",
			checked : false
		});
		this.isSendMail = new Ext.form.Checkbox({
			id : 'sendMail',
			xtype : 'checkbox',
			name : 'send',
			boxLabel : "�Ƿ���Ҫ�����ʼ�",
			checked : false
		});
		this.grid = new Ext.grid.GridPanel({
			id : 'oldGrid',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			height:350,
			tbar : [new Ext.Toolbar.TextItem('�ڵ��������ͣ�'), this.type, '    ', this.isCreatePer,this.isSendMail,'    ',{
				
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				id : 'addBtn',
				text : '�����ڵ��ɫ',
				handler : this.addUser,
				scope : this,
				iconCls : 'add'
				
			}, '   ',{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				id : 'delBtn',
				text : 'ɾ��',
				handler : this.remove,
				scope : this,
				iconCls : 'remove'
				
			},'     ',{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				id : 'modBtn',
				text : '�޸�',
				handler : this.modify,
				scope : this,
				iconCls : 'edit'
				
			}],
			bbar : this.pageBar
		});	
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
   		conn.open("POST", webContext+'/configUnitRole_getConfigUnitCreateFlag.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId, false);
    	conn.send(null);
    	var data = Ext.util.JSON.decode(conn.responseText);
    	if(data.flag==1){
    		Ext.getCmp("createPer").setValue(1);
    	}else{
    		Ext.getCmp("createPer").setValue(0);
    	}
    	if(data.sendMailFlag==1){
    		Ext.getCmp("sendMail").setValue(1);//����Ҫ�����ʼ�
    	}else{
    		Ext.getCmp("sendMail").setValue(0);
    	}
		Ext.getCmp("createPer").on("check", this.createCheck, this);
		Ext.getCmp("sendMail").on("check", this.sendMail, this);
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick",this.modify,this);//˫���м���
		this.add(this.grid);//������м�������С��壬һ���Ų�ѯ������һ���Ų�ѯ��������
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
			
		};
//		this.formValue = param;
//		this.pageBar.formValue = this.formValue;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();//ɾ�����ݴ洢���е���������
		this.store.load({
			params : param
		});

	},
fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
	initData : function() {
		var param = {
			'methodCall' : 'list',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.load({
			params : param
		});
	}
});
