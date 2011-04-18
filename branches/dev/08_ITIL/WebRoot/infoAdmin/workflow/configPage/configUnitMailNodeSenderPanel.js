ConfigUnitNodeMailPanel = Ext.extend(Ext.Panel, {
	id : "configUnitMailNodeSenderPanel",
	title : '�ʼ��ڵ����',
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
		this.cm.setEditable(1,false);//�ƶ�ĳһ���Ƿ���Ա༭
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
		//�ʼ�������Ϣfieldset������
		this.mailSubject = new Ext.form.TextField({
			id : 'subject',
			name : 'mailSubject',
			fieldLabel : '�ʼ�����',	
			allowBlank:false,
			width: 300,
			readOnly :false
		});
		
		this.mailContent = new Ext.form.TextArea({
			id : 'content',
			name : 'mailContent',
			fieldLabel : '�ʼ�����',
			allowBlank:false,
			width: 300,
			readOnly :false
		});	
		
		//�ʼ���������Ϣ����
		this.csm = new Ext.grid.CheckboxSelectionModel();
		this.ncsm = new Ext.grid.CheckboxSelectionModel();
		//�����ڲ��ͻ���email��ַ
		var recipientStore= new Ext.data.JsonStore({ 	
				id: 'CcStore',
				url: webContext+ '/configUnit_showMailNodeSenderMessage.action?virId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
				fields: ['id','name','mail'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.store = recipientStore;
		/*******************************�Ǽ����û�������store����ģʽ***********************************************************/
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
		   				header: "����������",
	                    sortable: true,
	                    width: 100,
	                    editor: new Ext.form.TextField(),
	                    dataIndex: "name"	              
		   			},
                	{
	                    header: "�������ʼ���ַ",
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
		/*******************************�Ǽ����û�������store����ģʽ***********************************************************/
		//�����ڲ��ͻ�������comboBox��store
		this.copyStore = new Ext.data.JsonStore({ 	
				id: 'copyStore',
				url: webContext+ '/configUnit_showAllUsernName.action',
				fields: ['id','userName'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		//�����ڲ��ͻ�����ģʽ
		this.cm = new Ext.grid.ColumnModel([
		   	this.csm,{
	                    header: "������",
	                    sortable: true,
	                    width: 100,
	                    //�����ڲ��û����û�����ѯconbox
	                    editor: new Ext.form.ComboBox({	
	                    	id : 'department',		
							displayField : "userName",
				            emptyText: '��ѡ������������',
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
								blur : function(combo) {// ����ʧȥ�����ʱ��
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
	                    header: "�������ʼ���ַ",
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
		
		// ������ҳToolBar
		this.pageBar = new Ext.PagingToolbarExt({			
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
				emptyMsg : '����ʾ����'
		});
		
		this.formValue = '';
		this.pageBar.formValue = this.formValue;		
		
		/*****************************************�Ǽ����û���grid**************************************************/
		// ������ҳToolBar
		this.noPageBar = new Ext.PagingToolbarExt({			
				pageSize : 10,
				store :  this.noConbineStore,
				displayInfo : true,
				displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
				emptyMsg : '����ʾ����'
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
					text : '����',
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
					text : ' ɾ��',					
					handler :  function() {
							var record = this.noCombinGrid.getSelectionModel().getSelected();
							var records = this.noCombinGrid.getSelectionModel().getSelections();
							if (!record) {
								Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
								return;
							}
							if (records.length == 0) {
								Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
							} else {
								Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
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
						                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){	 
						                       			this.noConbineStore.reload();
						                       		});
						                       }
						                       else{
						                       		
					                                Ext.Msg.alert("��ʾ��Ϣ","����ɾ���ɹ�!",function(){ 
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
		/*****************************************�����û���grid***************************************************/
		
		
		//�����ڲ��û��ı��
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
					text : '����',
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
					text : ' ɾ��',					
					handler :  function() {
							var record = this.grid.getSelectionModel().getSelected();
							var records = this.grid.getSelectionModel().getSelections();
							if (!record) {
								Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
								return;
							}
							if (records.length == 0) {
								Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
							} else {
								Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
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
						                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){	 
						                       			this.store.reload();
						                       		});
						                       }
						                       else{
						                       		
					                                Ext.Msg.alert("��ʾ��Ϣ","����ɾ���ɹ�!",function(){ 
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
		
		/*****************************�����ⲿ�û�************************************/
		var params= {
			'start' : 1
		};
		this.noConbineStore.load({
			params : params
		});
		//�ʼ���������Ϣ����
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
			reader: new Ext.data.JsonReader({//��������þ��൱�ڰѺ�̨���͵�typeӳ�䵽��Ӧ�Ŀؼ�type�����ˣ�
										//��ȡ��֮���Ұ����ݷŵ�record���У���store�ļ�����һ����
				    		root: 'list',
			                successProperty: '@success'
				    },[{
			              name: 'mailSubject',//�Ƕ�Ӧ��record�е�hiddenName
			              mapping: 'mailSubject'  //��Ӧ���Ƕ�ȡ֮���record
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
					title : "�ʼ�������Ϣ",
					items : [
					{html: "�ʼ�����:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},	
					this.mailSubject,
		    		{html: "�ʼ�����:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
		    		this.mailContent		    		
    		   		]
		  	   }
			 , {	layout : 'table',
					layoutConfig: {columns: 4},
					defaults : {
						bodyStyle : 'padding:4px'
					},
					xtype:"fieldset",
					title : "�ʼ������ˣ������ڲ��û���",
					items : [this.grid]
			   },{
			   		layout : 'table',
					layoutConfig: {columns: 4},
					defaults : {
						bodyStyle : 'padding:4px'
					},
					xtype:"fieldset",
					title : "�ʼ������ˣ��Ǽ����û���",
					items : [this.noCombinGrid]
			   }
				  ],
			buttons:[{
				id : 'saveBtn',
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '����',
				scope : this,
				handler : function(){
							var product = '';
							this.nProduct = '';
							var subject = Ext.getCmp("subject").getValue();
							var content = Ext.getCmp("content").getValue();
							/************************************************�Ǽ����û���grid����*******************************************/
							var  noCombine = Ext.getCmp('noCombineGrid');
							var  noGridParam = noCombine.getStore().getModifiedRecords();
							noGridParam = [];
							var noCombineCount = noCombine.getStore().getCount();
							for (var i = 0; i < noCombineCount; i++) {
								noGridParam[i] = noCombine.getStore().getAt(i);
							}
							//�����м�¼ƴװ��һ����������̨						
							for (i = 0; i < noGridParam.length; i++) {
									this.nProduct += Ext.encode(noGridParam[i].data) + ",";
							}	
//							alert("++++++++++++++"+this.nProduct+"++++++++++++++");return;
							this.nProduct = unicode(this.nProduct);
							
							/************************************************�����û���grid����*******************************************/
							var cmp = Ext.getCmp('grid');
							var gridParam = cmp.getStore().getModifiedRecords();
							gridParam = [];
							var count = cmp.getStore().getCount();
							for (var i = 0; i < count; i++) {
								gridParam[i] = cmp.getStore().getAt(i);
							}
							//�����м�¼ƴװ��һ����������̨						
							for (i = 0; i < gridParam.length; i++) {
									product += Ext.encode(gridParam[i].data) + ",";
							}	
							product = unicode(product);
						/***************************************����У��***********************************************************/
						if(!this.mailPanel.form.isValid()){
							Ext.Msg.alert('��ʾ','����ɫ�ߵ��������ȷ��д');
							return ;
						}							
						subject = unicode(subject);
						content = unicode(content);	
						if(gridParam.length<1){
							Ext.Msg.alert('��ʾ', '�������ӳ����ˣ�');
								return;
						}
						//�ж�����������Ƿ�Ϸ�	�������ڲ���				
						for (i = 0; i < gridParam.length; i++) {							
							var reg = new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);							
							if(!reg.test(gridParam[i].get("mail"))){
								Ext.Msg.alert('��ʾ', '������ļ����ڲ��ͻ��ʼ���ַ���Ϸ���');
								return;
							}								
						}		
						
						//�ж�����������Ƿ�Ϸ�	�������ⲿ��				
						for (i = 0; i < noGridParam.length; i++) {							
							var reg = new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);							
							if(!reg.test(noGridParam[i].get("mail"))){
								Ext.Msg.alert('��ʾ', '������ļ����ⲿ�ͻ��ʼ���ַ���Ϸ���');
								return;
							}								
						}	
						
						/*************************grid������ݵı���(��Ϊ�����ڲ��ͼ����ⲿs)***********************************************************/
						
						Ext.getCmp('saveBtn').setDisabled(true);
						var  noStore = this.noConbineStore;
						/***********************************************����Ǽ����û�***********************************************/
						//alert(this.nProduct);
//						var conn = Ext.lib.Ajax.getConnectionObject().conn;
//				   		conn.open("POST", webContext+'/configUnit_saveNoConbineMailNodeSenderMessage.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId+"&nProduct="+this.nProduct, false);
//				    	conn.send(null);
//				    	var noData = Ext.util.JSON.decode(conn.responseText);
//				    	if(noData.success=="true"){
//	    						Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {											
//								recipientStore.reload();
//								Ext.getCmp('editBtn').enable();
//								Ext.getCmp('configUnitMailNodeSenderPanel').disableEdit();	}
//								);
//				    	}else if(noData.success=="false"){
//				    			Ext.MessageBox.alert("����ʧ��");
//								Ext.getCmp('saveBtn').enable();
//				    	}
				    	/***********************************************���漯���û�***********************************************/
//				    	var combinUser = Ext.lib.Ajax.getConnectionObject().conn;
//				   		combinUser.open("POST", webContext+'/configUnit_saveMailNodeSenderMessage.action?virtualDefinitionInfoId='+virtualDefinitionInfoId+"&nodeId="+nodeId+"&product="+product+"&subject="+subject+"&content="+content, false);
//				    	combinUser.send(null);
//				    	var data = Ext.util.JSON.decode(combinUser.responseText);
//				    	if(data.success=="true"){
//	    						Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {											
//								recipientStore.reload();
//								Ext.getCmp('editBtn').enable();
//								Ext.getCmp('configUnitMailNodeSenderPanel').disableEdit();	}
//								);
//				    	}else if(data.success=="false"){
//				    			Ext.MessageBox.alert("����ʧ��");
//								Ext.getCmp('saveBtn').enable();
//				    	}
						Ext.Ajax.request({
									url: webContext+ '/configUnit_saveMailNodeSenderMessage.action',
									params : {							        	
										product : product,//�����ڲ��û�
										nProduct : this.nProduct ,
										virtualDefinitionInfoId : virtualDefinitionInfoId,
										nodeId : nodeId,
										subject : subject,
										content : content
									},
									success : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {		
											recipientStore.reload();
											noStore.reload();
											Ext.getCmp('noCombinStore').reload();
											Ext.getCmp('editBtn').enable();
											Ext.getCmp('configUnitMailNodeSenderPanel').disableEdit();
											});
					
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("����ʧ��");
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
				text : '�༭'		
				}
			]				  
		});	
		/************************************��������form��������********************************************************************/
		Ext.Ajax.request({
				url: webContext+ '/configUnit_loadMailNodeSenderFormMessage.action?virId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
				success : function(response, options) {										
						var obj = Ext.decode(response.responseText);						
						Ext.getCmp("subject").setValue(obj.list.mailSubject);
						Ext.getCmp("content").setValue(obj.list.mailContent);					
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("����ʧ��");
				}						
		});
		/************************************��������form��������********************************************************************/
		this.items = [this.mailPanel];		
		ConfigUnitNodeMailPanel.superclass.initComponent.call(this);
	}
});
