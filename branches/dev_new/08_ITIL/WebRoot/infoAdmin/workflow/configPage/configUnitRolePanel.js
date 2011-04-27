ConfigUnitRoleListPanel = Ext.extend(Ext.Panel, {
	id : "configUnitRoleListPanel",	
	closable : true,
	viewConfig : {//自适应填充
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	
	checkRolEdit : function(){
		 var userDetailForm = new UserRolForm();
		 var userForm = userDetailForm.form;			 
		 var roleGrid = new RoleGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'选择角色',
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
						
						Ext.getCmp("roleEditGrid").store.add([dataRecord]);
	    			}
	    			userDetailWin.close();
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
	            	                      		
	            	 Ext.Msg.alert("警告", "成功保存信息！");
	            	
	            },
	            failure: function(response, options){
	                ReturnValue = Ext.MessageBox.alert("警告", "保存信息失败！");
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
	                                   		
	            	 Ext.Msg.alert("警告", "成功保存信息！");
	            	
	            },
	            failure: function(response, options){
	                ReturnValue = Ext.MessageBox.alert("警告", "保存信息失败！");
	            },
	            scope:this
        });
	
	},
	modify: function(){
			var gridStore = this.store;
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
		    var department = record.get("depName");				   
		    var userDetailEditForm = new UserDetailEditForm(id,department);
		    var userEditForm =  userDetailEditForm.form;
		 
	        var roleEditGrid = new RoleListEditGrid(id).grid;
	        
	        var editWin = new Ext.Window({
			 	title:'修改信息',
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
		                   		Ext.Msg.alert("提示","您选择的行中有重复的值!");
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
						Ext.Msg.alert("提示","请先选择要修改的行!");
						return;
					}					
	                Ext.Ajax.request({
                        url: webContext+'/configUnitRole_configUnitRoleSave.action',
                        method: 'POST',
                        params: request,
                        success: function(result, options){
                        	var responseArray = Ext.util.JSON.decode(result.responseText);  
                        	if(!responseArray.success){
                        		Ext.MessageBox.alert("警告", "保存用户信息失败(原因可能是您选择的角色已经存在)！");
                        	}else{                        		
                        	 Ext.Msg.alert("警告", "成功保存信息！",function(){                        	 	
                        	 	gridStore.reload();
                        	 	editWin.close() ;                  	 	
                        	 });
                        	}
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("警告", "保存用户信息失败(原因可能是您选择的角色已经存在)！");
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
		        items:[userEditForm,roleEditGrid]
		});
		editWin.show();
	  },
	remove : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
			return;
		}
		var id = new Array();
		for(var i=0;i<records.length;i++){			
			id[i] = records[i].data.id;		
			this.grid.getStore().remove(records[i]);
		}

		var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
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
	                       		Ext.Msg.alert("提示信息","数据删除失败",function(){	                       			
	                       			this.store.reload();
	                       		});
	                       }
	                       else{
                                Ext.Msg.alert("提示信息","数据删除成功!",function(){                                 	
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
		 	title:'选择角色',
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
	        	text:'添加',
	        	handler:function(){
	        		var record = roleGrid.getSelectionModel().getSelected();
	        		if (!record) {
						Ext.Msg.alert("提示", "请先选择要添加的行!");
						return;
					}
	    			var records = roleGrid.getSelectionModel().getSelections();	    			
	    			if(records.length>1){
	    				Ext.Msg.alert("提示", "一个角色只对应一条记录!");
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
	        	text:'关闭',
	        	scope:this
	        	}
	        ],
	        items:[userForm,roleGrid]
		});
		userDetailWin.show();
	  },
	//新增节点角色
	addUser : function(){
		
		 var gridStore = this.store;
		 var userDetailForm = new UserDetailForm();
		 var userForm = userDetailForm.form;
		 roleListGrid = new RoleListGrid().grid;
		 var userDetailWin = new Ext.Window({
		 	title:'新建节点角色',
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
						Ext.Msg.alert("提示", "请先选择要添加的行!");
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
          				Ext.MessageBox.alert("警告", "请您选择节点审批类型");
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
                        		Ext.MessageBox.alert("警告", "保存用户信息失败(原因可能是您选择的角色已经存在)！");
                        	}else{                        		
                        	 Ext.Msg.alert("警告", "成功保存信息！",function(){                        	 	
                        	 	gridStore.reload();
                        	 	userDetailWin.close()                  	 	
                        	 });
                        	}
                        },
                        failure: function(response, options){
                            ReturnValue = Ext.MessageBox.alert("警告", "保存信息失败！");
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

	// 初始化
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
    		this.testFlag = "仅一人审批";
    	}else{
   		this.testFlag = "需多人审批";
    	}
		var codes = [['0','需多人审批'], ['1','仅一人审批']];
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
			header : "虚拟流程名称",
			dataIndex : "virtualDesc"
		}, {
			header : "节点名称",
			dataIndex : "nodeName"
		},
		{
			header : "节点角色",
			width :300,
			dataIndex : "roleName"
		},{
			header : "节点描述",
			dataIndex : "nodeDesc"
		},
		{
			header : "角色审批类型",
			width :200,
			dataIndex : "roleType"
		},{
			header : "节点审批类型",
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
		this.cm.defaultSortable = true;//排序默认值
		

		ConfigUnitRoleListPanel.superclass.initComponent.call(this);//让父类先初始化
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,//使用的是系统默认值
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		this.isCreatePer = new Ext.form.Checkbox({
			id : 'createPer',
			xtype : 'checkbox',
			name : 'create',
			boxLabel : "添加申请人",
			checked : false
		});
		this.isSendMail = new Ext.form.Checkbox({
			id : 'sendMail',
			xtype : 'checkbox',
			name : 'send',
			boxLabel : "是否不需要发送邮件",
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
			tbar : [new Ext.Toolbar.TextItem('节点审批类型：'), this.type, '    ', this.isCreatePer,this.isSendMail,'    ',{
				
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				id : 'addBtn',
				text : '新增节点角色',
				handler : this.addUser,
				scope : this,
				iconCls : 'add'
				
			}, '   ',{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				id : 'delBtn',
				text : '删除',
				handler : this.remove,
				scope : this,
				iconCls : 'remove'
				
			},'     ',{
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				id : 'modBtn',
				text : '修改',
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
    		Ext.getCmp("sendMail").setValue(1);//不需要发送邮件
    	}else{
    		Ext.getCmp("sendMail").setValue(0);
    	}
		Ext.getCmp("createPer").on("check", this.createCheck, this);
		Ext.getCmp("sendMail").on("check", this.sendMail, this);
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick",this.modify,this);//双击行监听
		this.add(this.grid);//大面板中加了两个小面板，一个放查询条件，一个放查询出的数据
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
			
		};
//		this.formValue = param;
//		this.pageBar.formValue = this.formValue;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();//删除数据存储器中的所有数据
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
