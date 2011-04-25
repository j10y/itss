ConfigUnitNodeMailPanel = Ext.extend(Ext.Panel, {
	id : "configUnitMailNodeSenderPanel",
	title : '邮件节点面板',
	layout : 'table',
	height : 'auto',
	width : 'auto',
	autoScroll : true,
	align : 'center',
	foredit : true,
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px',
		overflow : 'auto'
	},
	layoutConfig : {
		columns : 1
	},
	disableEdit : function(){			
		this.mailSubject.disable();
		this.mailContent.disable();
		Ext.getCmp('addBtn').disable();
		Ext.getCmp('removeBtn').disable();
		Ext.getCmp('noAddBtn').disable();
		Ext.getCmp('noRemoveBtn').disable();
		this.cm.setEditable(1,false);//制定某一列是否可以编辑
		this.cm.setEditable(2,false);
		this.ncm.setEditable(1,false);
		this.ncm.setEditable(2,false);
	},
	enableEdit : function(){		
		Ext.getCmp("subject").enable();
		Ext.getCmp("content").enable();
		Ext.getCmp('addBtn').enable();
		Ext.getCmp('removeBtn').enable();
		Ext.getCmp('noAddBtn').enable();
		Ext.getCmp('noRemoveBtn').enable();
		this.cm.setEditable(1,true);
		this.cm.setEditable(2,true);
		this.ncm.setEditable(1,true);
		this.ncm.setEditable(2,true);
		Ext.getCmp('saveBtn').setDisabled(false);
		Ext.getCmp('editBtn').setDisabled(true);
	},		
	initComponent : function() {		
		//邮件内容信息fieldset中内容
		this.mailSubject = new Ext.form.TextField({
			id : 'subject',
			name : 'mailSubject',
			fieldLabel : '邮件标题',	
			allowBlank:false,
			width: 300,
			readOnly :false
		});
		
		this.mailContent = new Ext.form.TextArea({
			id : 'content',
			name : 'mailContent',
			fieldLabel : '邮件内容',
			allowBlank:false,
			width: 300,
			readOnly :false
		});	
		
		//邮件收信人信息内容
		this.csm = new Ext.grid.CheckboxSelectionModel();
		this.ncsm = new Ext.grid.CheckboxSelectionModel();
		//集团内部客户的email地址
		var recipientStore= new Ext.data.JsonStore({ 	
				id: 'CcStore',
				url: webContext+ '/configUnit_showMailNodeSenderMessage.action?virId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
				fields: ['id','name','mail'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.store = recipientStore;
		/*******************************非集团用户的数据store和列模式***********************************************************/
		this.noConbineStore= new Ext.data.JsonStore({ 	
				id: 'noCombinStore',
				url: webContext+ '/configUnit_showNoCombineMailNodeSenderMessage.action?virId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
				fields: ['id','name','mail'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		
		this.ncm = new Ext.grid.ColumnModel([
		   this.ncsm,{
		   				header: "收信人姓名",
	                    sortable: true,
	                    width: 100,
	                    editor: new Ext.form.TextField(),
	                    dataIndex: "name"	              
		   			},
                	{
	                    header: "收信人邮件地址",
	                    sortable: true,
	                    width: 100,
	                    editor: new Ext.form.TextField(),
	                    dataIndex: "mail"	                              
                	},     	
                	{
                		header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     		}		    
		]); 
		/*******************************非集团用户的数据store和列模式***********************************************************/
		//集团内部客户收信人comboBox的store
		this.copyStore = new Ext.data.JsonStore({ 	
				id: 'copyStore',
				url: webContext+ '/configUnit_showAllUsernName.action',
				fields: ['id','userName'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		//集团内部客户的列模式
		this.cm = new Ext.grid.ColumnModel([
		   	this.csm,{
	                    header: "收信人",
	                    sortable: true,
	                    width: 100,
	                    //集团内部用户的用户名查询conbox
	                    editor: new Ext.form.ComboBox({	
	                    	id : 'department',		
							displayField : "userName",
				            emptyText: '请选择收信人姓名',
							mode : 'remote',
							forceSelection : true,
							hiddenName : "id",
							editable : true,
							triggerAction : 'all', 
							lazyRender: true,
				            typeAhead: false,
				            autoScroll:true,
							allowBlank : true,
							name : "department",
							selectOnFocus: true,
							width: 300,
							//*******************************************************************
							store:new Ext.data.JsonStore({
								url:webContext+'/configUnit_queryUserInfoName.action?displayField=userName',
								fields: ['id', 'userName'],
								totalProperty:'rowCount',
								root:'data',
								sortInfo : {
									field : "id",
									direction : "ASC"
								},
								listeners:{
									beforeload:function(store,opt){	
										if(opt.params['userName']== undefined){
											opt.params['userName']=Ext.getCmp("department").defaultParam;
										}
									}
								}
							}),							
							//*******************************************************************
							pageSize:10,
							listeners : {
								blur : function(combo) {// 当其失去焦点的时候
									if (combo.getRawValue() == '') {
										combo.reset();
									}
								},
								beforequery : function(queryEvent) {
				
									var param = queryEvent.query;
									this.defaultParam = param;
									this.store.load({
										params : {
											userName : param,
											start : 0
										}
									});
									return true;
								}
							}				
						}),
	                    dataIndex: "name"	
		   			},		
                	{
	                    header: "收信人邮件地址",
	                    sortable: true,
	                    width: 100,
	                    editor: new Ext.form.TextField(),
	                    dataIndex: "mail"	                              
                	},     	
                	{
                		header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     		}		    
		]); 
		/******************************************************************************************/
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		
		// 创建分页ToolBar
		this.pageBar = new Ext.PagingToolbarExt({			
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : '无显示数据'
		});
		
		this.formValue = '';
		this.pageBar.formValue = this.formValue;		
		
		/*****************************************非集团用户的grid**************************************************/
		// 创建分页ToolBar
		this.noPageBar = new Ext.PagingToolbarExt({			
				pageSize : 10,
				store :  this.noConbineStore,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : '无显示数据'
		});
		
		this.formValue = '';
		this.noPageBar.formValue = this.formValue;		
		this.noCombinGrid = new Ext.grid.EditorGridPanel({ 			
				id :'noCombineGrid',
		        store: this.noConbineStore,
		        cm: this.ncm,
		        sm: this.ncsm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 300,
		        width: 615,
		        y : 0,
				anchor : '0 -0',
		        frame:true,
		        ddGroup: "tgDD",
				enableDragDrop: true,    
		        autoScroll: true,
		        autoEncode : true,		        
		        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
		        tbar : ['&nbsp;|&nbsp;', {
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					id : 'noAddBtn',
					text : '增加',
					scope : this,
					iconCls : 'add',
					handler : function() {
					var store = this.noConbineStore;
					if (store.recordType) {
						var rec = new store.recordType({
							newRecord : true
						});
						rec.fields.each(function(f) {
								rec.data['name'] = null;
								rec.data['mail'] = null;								
						});
						rec.commit();
						store.add(rec);
						return rec;
					}
					return false;
					}
					},'&nbsp;|&nbsp;', {
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					id : 'noRemoveBtn',
					text : ' 删除',					
					handler :  function() {
							var record = this.noCombinGrid.getSelectionModel().getSelected();
							var records = this.noCombinGrid.getSelectionModel().getSelections();
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
											this.removeIds = new Array();
											for (var i = 0; i < records.length; i++) {
												this.removeIds.push(records[i].get("id"));
												 this.noConbineStore.remove(records[i]);
											}										
											}								
										Ext.Ajax.request({						
							                   url: webContext+ '/configUnit_removeNoCombineMailNodeSenderMessage.action',
						                       params:{
						                           'ids':this.removeIds	,
						                           'virId':virtualDefinitionInfoId,
						                           'nodeId':nodeId
						                       },
						                       mothod:'POST',
						                       success:function(response){
						                           var r=Ext.decode(response.responseText);
						                           if(!r.success){
						                       		Ext.Msg.alert("提示信息","数据删除失败",function(){	 
						                       			this.noConbineStore.reload();
						                       		});
						                       }
						                       else{
						                       		
					                                Ext.Msg.alert("提示信息","数据删除成功!",function(){ 
					                                	this.noConbineStore.reload();
					                                },this);
						                       }	 
						                       }, 
						                       scope:this
				                   		});
											}
										}, this)
									}
						this.removedButtons = "";
					},
					scope : this,
					iconCls : 'remove'
				}],
				bbar : this.noPageBar
		});
		/*****************************************集团用户的grid***************************************************/
		
		
		//集团内部用户的表格
		this.grid = new Ext.grid.EditorGridPanel({ 			
				id :'grid',
		        store: this.store,
		        cm: this.cm,
		        sm: this.csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 300,
		        width: 615,
		        y : 0,
				anchor : '0 -0',
		        frame:true,
		        ddGroup: "tgDD",
				enableDragDrop: true,    
		        autoScroll: true,
		        autoEncode : true,		        
		        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
		        tbar : ['&nbsp;|&nbsp;', {
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					id : 'addBtn',
					text : '增加',
					scope : this,
					iconCls : 'add',
					handler : function() {
					var store = this.store;
					if (store.recordType) {
						var rec = new store.recordType({
							newRecord : true
						});
						rec.fields.each(function(f) {
								rec.data['name'] = null;
								rec.data['mail'] = null;								
						});
						rec.commit();
						store.add(rec);
						return rec;
					}
					return false;
					}
					},'&nbsp;|&nbsp;', {
					xtype : 'button',
					style : 'margin:4px 10px 4px 0',
					id : 'removeBtn',
					text : ' 删除',					
					handler :  function() {
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
											this.removeIds = new Array();
											for (var i = 0; i < records.length; i++) {
												this.removeIds.push(records[i].get("id"));
												this.store.remove(records[i]);
											}										
											}								
										Ext.Ajax.request({						
							                   url: webContext+ '/configUnit_removeMailNodeSenderMessage.action',
						                       params:{
						                           'ids':this.removeIds				                           
						                       },
						                       mothod:'POST',
						                       success:function(response){
						                           var r=Ext.decode(response.responseText);
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
						                       }, 
						                       scope:this
				                   		});
											}
										}, this)
									}
						this.removedButtons = "";
					},
					scope : this,
					iconCls : 'remove'
				}],
				bbar : this.pageBar
		}); 

		var param = {
			'start' : 1
		};
		this.pageBar.formValue = param;
		this.store.load({
			params : param
		});
		
		/*****************************集团外部用户************************************/
		var params= {
			'start' : 1
		};
		this.noConbineStore.load({
			params : params
		});
		//邮件收信人信息内容
		this.mailPanel = new Ext.form.FormPanel({
			id : 'mailPanel',
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px',
				overflow : 'auto'
			},
			layoutConfig : {columns : 1},
			reader: new Ext.data.JsonReader({//这个的作用就相当于把后台发送的type映射到相应的控件type当中了，
										//读取到之后并且把数据放到record当中，和store的加载是一样的
				    		root: 'list',
			                successProperty: '@success'
				    },[{
			              name: 'mailSubject',//是对应的record中的hiddenName
			              mapping: 'mailSubject'  //对应的是读取之后的record
			            },{
			              name: 'mailContent',
			              mapping: 'mailContent'
			            }
			]),
			items:[
				{   
					layout : 'table',
					layoutConfig: {columns: 2},
					defaults : {bodyStyle : 'padding:4px'},
					xtype:"fieldset",
					title : "邮件内容信息",
					items : [
					{html: "邮件标题:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},	
					this.mailSubject,
		    		{html: "邮件内容:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
		    		this.mailContent		    		
    		   		]
		  	   }
			 , {	layout : 'table',
					layoutConfig: {columns: 4},
					defaults : {
						bodyStyle : 'padding:4px'
					},
					xtype:"fieldset",
					title : "邮件收信人（集团内部用户）",
					items : [this.grid]
			   },{
			   		layout : 'table',
					layoutConfig: {columns: 4},
					defaults : {
						bodyStyle : 'padding:4px'
					},
					xtype:"fieldset",
					title : "邮件收信人（非集团用户）",
					items : [this.noCombinGrid]
			   }
				  ],
			buttons:[{
				id : 'saveBtn',
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '保存',
				scope : this,
				handler : function(){
							var product = '';
							this.nProduct = '';
							var subject = Ext.getCmp("subject").getValue();
							var content = Ext.getCmp("content").getValue();
							/************************************************非集团用户的grid数据*******************************************/
							var  noCombine = Ext.getCmp('noCombineGrid');
							var  noGridParam = noCombine.getStore().getModifiedRecords();
							noGridParam = [];
							var noCombineCount = noCombine.getStore().getCount();
							for (var i = 0; i < noCombineCount; i++) {
								noGridParam[i] = noCombine.getStore().getAt(i);
							}
							//把所有记录拼装成一个串传到后台						
							for (i = 0; i < noGridParam.length; i++) {
									this.nProduct += Ext.encode(noGridParam[i].data) + ",";
							}	
//							alert("++++++++++++++"+this.nProduct+"++++++++++++++");return;
							this.nProduct = unicode(this.nProduct);
							
							/************************************************集团用户的grid数据*******************************************/
							var cmp = Ext.getCmp('grid');
							var gridParam = cmp.getStore().getModifiedRecords();
							gridParam = [];
							var count = cmp.getStore().getCount();
							for (var i = 0; i < count; i++) {
								gridParam[i] = cmp.getStore().getAt(i);
							}
							//把所有记录拼装成一个串传到后台						
							for (i = 0; i < gridParam.length; i++) {
									product += Ext.encode(gridParam[i].data) + ",";
							}	
							product = unicode(product);
						/***************************************数据校验***********************************************************/
						if(!this.mailPanel.form.isValid()){
							Ext.Msg.alert('提示','带红色线的项必须正确填写');
							return ;
						}							
						subject = unicode(subject);
						content = unicode(content);	
						if(gridParam.length<1){
							Ext.Msg.alert('提示', '必须增加抄送人！');
								return;
						}
						//判断输入的数据是否合法	（集团内部）				
						for (i = 0; i < gridParam.length; i++) {							
							var reg = new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);							
							if(!reg.test(gridParam[i].get("mail"))){
								Ext.Msg.alert('提示', '您输入的集团内部客户邮件地址不合法！');
								return;
							}								
						}		
						
						//判断输入的数据是否合法	（集团外部）				
						for (i = 0; i < noGridParam.length; i++) {							
							var reg = new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);							
							if(!reg.test(noGridParam[i].get("mail"))){
								Ext.Msg.alert('提示', '您输入的集团外部客户邮件地址不合法！');
								return;
							}								
						}	
						
						/*************************grid表格数据的保存(分为集团内部和集团外部s)***********************************************************/
						
						Ext.getCmp('saveBtn').setDisabled(true);
						var  noStore = this.noConbineStore;
						/***********************************************保存非集团用户***********************************************/
						//alert(this.nProduct);
//						var conn = Ext.lib.Ajax.getConnectionObject().conn;
//				   		conn.open("POST", webContext+'/configUnit_saveNoConbineMailNodeSenderMessage.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId+"&nProduct="+this.nProduct, false);
//				    	conn.send(null);
//				    	var noData = Ext.util.JSON.decode(conn.responseText);
//				    	if(noData.success=="true"){
//	    						Ext.MessageBox.alert("提示", "保存成功", function() {											
//								recipientStore.reload();
//								Ext.getCmp('editBtn').enable();
//								Ext.getCmp('configUnitMailNodeSenderPanel').disableEdit();	}
//								);
//				    	}else if(noData.success=="false"){
//				    			Ext.MessageBox.alert("保存失败");
//								Ext.getCmp('saveBtn').enable();
//				    	}
				    	/***********************************************保存集团用户***********************************************/
//				    	var combinUser = Ext.lib.Ajax.getConnectionObject().conn;
//				   		combinUser.open("POST", webContext+'/configUnit_saveMailNodeSenderMessage.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId+"&product="+product+"&subject="+subject+"&content="+content, false);
//				    	combinUser.send(null);
//				    	var data = Ext.util.JSON.decode(combinUser.responseText);
//				    	if(data.success=="true"){
//	    						Ext.MessageBox.alert("提示", "保存成功", function() {											
//								recipientStore.reload();
//								Ext.getCmp('editBtn').enable();
//								Ext.getCmp('configUnitMailNodeSenderPanel').disableEdit();	}
//								);
//				    	}else if(data.success=="false"){
//				    			Ext.MessageBox.alert("保存失败");
//								Ext.getCmp('saveBtn').enable();
//				    	}
						Ext.Ajax.request({
									url: webContext+ '/configUnit_saveMailNodeSenderMessage.action',
									params : {							        	
										product : product,//集团内部用户
										nProduct : this.nProduct ,
										virtualDefinitionInfoId : virtualDefinitionInfoId,
										nodeId : nodeId,
										subject : subject,
										content : content
									},
									success : function(response, options) {
										Ext.MessageBox.alert("提示", "保存成功", function() {		
											recipientStore.reload();
											noStore.reload();
											Ext.getCmp('noCombinStore').reload();
											Ext.getCmp('editBtn').enable();
											Ext.getCmp('configUnitMailNodeSenderPanel').disableEdit();
											});
					
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("保存失败");
										Ext.getCmp('saveBtn').enable();
									}						
						})	
						
					}
				}, {
				id : 'editBtn',
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				disabled:true,
				handler : function(){
					Ext.getCmp('configUnitNodeMailPanel').enableEdit();
				},
				text : '编辑'		
				}
			]				  
		});	
		/************************************用来加载form表单的数据********************************************************************/
		Ext.Ajax.request({
				url: webContext+ '/configUnit_loadMailNodeSenderFormMessage.action?virId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
				success : function(response, options) {										
						var obj = Ext.decode(response.responseText);						
						Ext.getCmp("subject").setValue(obj.list.mailSubject);
						Ext.getCmp("content").setValue(obj.list.mailContent);					
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("保存失败");
				}						
		});
		/************************************用来加载form表单的数据********************************************************************/
		this.items = [this.mailPanel];		
		ConfigUnitNodeMailPanel.superclass.initComponent.call(this);
	}
});
