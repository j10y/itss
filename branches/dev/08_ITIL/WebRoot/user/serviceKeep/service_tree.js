/*
* Ŀ���������
*/

test = function(result){
	if(result=='ERROR_ADD'){
		alert("����Ŀ¼�����ڷ������½���");
		Ext.getCmp("tree").error = '1';
		return ;
	}else if(result=='ERROR_ITEM'){
		alert("��������ϵ���������");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_MOVE'){
		alert("����Ŀ¼�����ϵ���������");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_RING'){
		alert("���ڵ��Ѿ����ڴ˷�����������û�״����");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_DOUBLE'){
		alert("���������ͬ�ķ�����");
		Ext.getCmp("tree").error = '1';
	}else{
		Ext.getCmp("tree").error = '0';
	}
}
testKernel = function(result){
	if(result=='KERNEL_ITEM'){
		Ext.getCmp("tree").kernel = 'item';
		return ;
	}else{
		Ext.getCmp("tree").kernel = 'cata';
		return ;
	}
}

//Ŀ��ڵ㣬�ӽڵ㣬��ѡ�������
var ddFunction = function(node, refNode, selections) {

	for(var i = 0; i < selections.length; i ++) {

		var record = selections[i];//���record��ʾ���ѡ�е�����
		node.insertBefore(new Ext.tree.TreeNode({
			text: record.get('name'),
			id: record.get('id'),
			leaf: record.get('leaf')
		}), refNode);

		var parentId = node.id;

		var id = record.get('id');

		var order = node.indexOf(node.lastChild) ;

		DWREngine.setAsync(false);
		SCIRelationShipManager.ajaxAddByCI(id, parentId,order,test);
		if(this.error=='1'){
			return false;
		}
		//���ڵ��������
		node.reload();
	}
}

PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '����Ŀ¼��ϵ',
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
	columnWidth:.5,
	lines: true,
	height: 500,
	enableDD: true,
	ddGroup: "tgDD",
	removeFlag: false,
	listeners: { //��ק֮ǰ�ᴥ����������Ա������ק������

		beforenodedrop: function(dropEvent) {
			var node = dropEvent.target;    // Ŀ����
			var data = dropEvent.data;      // ��ק������
			var point = dropEvent.point;    // ��ק��Ŀ�����λ��

			// ���data.nodeΪ�գ�����tree�������ק�����Ǵ�grid��tree����ק����Ҫ����
			if(!data.node) {

				for(var i = 0; i < data.selections.length; i++) {
					var record = data.selections[i];//���record��ʾ���ѡ�е�����
					var pagePanelId = record.get('id');
				}

				switch(point) {
					case 'append':
					// ���ʱ��Ŀ����Ϊnode���ŵ��ӽ��Ϊ�յ��������
					//alert(node.id);
					ddFunction(node, null, data.selections);
					break;
					case 'above':
					// ���뵽node֮�ϣ�Ŀ����Ϊnode��parentNode���ӽ��Ϊnode
					//alert(node.id);
					ddFunction(node.parentNode, node, data.selections);
					break;
					case 'below':
					// ���뵽node֮�£�Ŀ����Ϊnode��parentNode���ӽ��Ϊnode��nextSibling
					ddFunction(node.parentNode, node.nextSibling, data.selections);
					break;
				}
			}
		},

		//���ڵ��ƶ�ʱ�����¼�
		beforemovenode: function(tree, node, oldParent, newParent, index) {
			//alert(index);
			DWREngine.setAsync(false);
			SCIRelationShipManager.ajaxMoveRelationShip(node.id, oldParent.id, newParent.id, index, test);
			if(this.error=='1'){
				return false;
			}

		},
		contextmenu: function(node, e){

			var menu;
			var dataId = dataId;
			var parenteId = node.id;
			var order = node.indexOf(node.lastChild)+1;
			DWREngine.setAsync(false);
			SCIRelationShipManager.ajaxGetKernel(node.id,testKernel);
			var kernel = Ext.getCmp("tree").kernel;

			if (!menu&&kernel=='item'){
				menu = new Ext.menu.Menu([
				{
					text : '�鿴',
					handler : function(){
						Ext.Ajax.request({											
								url: webContext+'/sciRelationShip_findChildForEdit.action',
									params : {
										childId : node.id,
										target : "serviceItem"
									},
								method : 'post',
								success : function(response) {
									var result = Ext.decode(response.responseText);
									var type = result.type;													
									if(type=="item"){
										var id =result.id;
										var win1 = new Ext.Window({
												title : '�鿴��������Ϣ',
												height:500,
												width:800,
												resizable:false,
												draggable:true,
												autoLoad:{
													url:webContext+"/tabFrame.jsp?url="+webContext+"/user/serviceKeep/configItemServiceView.jsp?dataId="+id,
													text:"ҳ�������......",
													method:'post',
													scripts:true,
													scope:this
												},
												viewConfig:{
													autoFill:true,
													forceFit:true
												},
												layout:'fit',
												buttons : [
													{
													text : '�ر�',
													handler : function() {
														//location = 'ibmCust.jsp'
														win1.close();
														
													},
													listeners: {
														'beforeclose':  function(p) {
															return true;
														}
								
													},
													scope : this
												}]
											});
											
										win1.show();
									}
								}
							});
						}	
				},{
		           		text : '�޸ķ�����۸�',
		           		handler : function(){
		           			Ext.Ajax.request({											
								url: webContext+'/sciRelationShip_findChildForEdit.action',
									params : {
										childId : node.id,
										target : "serviceItem"
									},
									method : 'post',
									success : function(response) {
									var result = Ext.decode(response.responseText);
									var type = result.type;													
//									if(type=="cata"){
//										alert("����Ŀ¼û�м۸�");
//									}else if(type=="item"){

										var id = node.id;
										Ext.Ajax.request({//д��action���SCIRelationShip����ֵ ����json serviceItem��serviceItemfee							
											url: webContext+'/sciRelationShip_findSCIRelationShip.action',
											params : {
												childId : node.id
											},
										method : 'post',
										success : function(response) {
											var win;	
										var result = Ext.decode(response.responseText);
										var serviceItem = result.serviceItem;
										var serviceItemFee = result.serviceItemFee;
										var dispFlag = result.dispFlag;
										//var value = editForm.form.findField('typeId').getValue();								 			
										if(serviceItemFee=='null'){
											serviceItemFee = "";
										}	
					                	var idField = {
					                		id : "id",
					                		xtype : "hidden",
					                		name : "id",
					                		value : id,
					                		fieldLabel : "ID",
					                		hidden : true,
					                		allowBlank : false,
					                		selectOnFocus : true
					                	};
										
					                	var serviceItemField = {
					                		id : "serviceItem",
					                		xtype : "textfield",
					                		name : "serviceItem",
					                		value : serviceItem,
					                		fieldLabel : "����������",
					                		allowBlank : false,
					                		selectOnFocus : true,
					                		readOnly : true
					                	};
					                	
					                	var serviceItemFeeField = {
					                		id : "serviceItemFee",
					                		xtype : "textfield",
					                		name : "serviceItemFee",
					                		value : serviceItemFee,
					                		fieldLabel : "������۸�",
					                		//allowBlank : false,//�Ƿ������ֵ
					                		selectOnFocus : true
//					                		emptyText : '����д�۸�'
					                	};
					                	this.store = new Ext.data.SimpleStore({
		                                		fields:['name','value'],
		                                		data:[["0","��"],["1","��"]]
		                                	});
										var serviceItemDispFlag ={
											id:"dispFlag",
											xtype:'combo',
											typeAhead: true,
           									triggerAction: 'all',
			                				displayField:'value',
			                				hiddenName:'name',
			                				name:'name',
			                				width:125,
			                				value:2,//����Ĭ��ֵ
		                                	valueField:'name',
		                                	store:this.store,
		                                	forceSelection:true,
		                                	mode:'local'
										};
					                	var editForm = new Ext.form.FormPanel({
					                		id : 'editChildCataPanel',
					                		layout: 'table',
											height : 230,
											width : 'auto',
											frame : true,
											reader: new Ext.data.JsonReader({//��������þ��൱�ڰѺ�̨���͵�typeӳ�䵽��Ӧ�Ŀؼ�type�����ˣ�
										//��ȡ��֮���Ұ����ݷŵ�record���У���store�ļ�����һ����
								    		root: 'list',
									        successProperty: '@success'
										    },[{
									              name: 'serviceItem',//�Ƕ�Ӧ��record�е�hiddenName
									              mapping: 'serviceItem'  //��Ӧ���Ƕ�ȡ֮���record
									            },{
									              name: 'serviceItemFee',
									              mapping: 'serviceItemFee'
									            },{
									              name: 'name',
									              mapping: 'name'
									            }
									        ]),
											layoutConfig: {columns: 2},	    		
											items:[
												{html: "����������:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
												serviceItemField,		
												{html: "������۸�:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
												serviceItemFeeField,
												{html: "�Ƿ�����:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
												serviceItemDispFlag,
												idField		
												]
												
					                	});	
					                	if(dispFlag=="null"||dispFlag==null){
					                		var disp = "1";//1���������ģ�����Ĭ��ֵ
					                		Ext.getCmp("dispFlag").setValue(disp);
					                	}else{
					                		Ext.getCmp("dispFlag").setValue(dispFlag);
					                	}
//					                	var value = editForm.form.findField("name").getValue();	
//					                	editForm.form.findField("name").setValue(value)
					                	if(!win){
						                	win = new Ext.Window({
												id : 'editWin',
												title : "������۸�༭����",
												width : 300,
												height : 240,
												maximizable : true,
												modal : true,
												items: editForm,								
												buttons:[
								        	       {	xtype:'button',
								        		        handler:function(){
								        		        var info = Ext.getCmp('editChildCataPanel').getForm().getValues();
//								        		        alert(Ext.encode(info));
								        				Ext.Ajax.request({											
															url: webContext+'/sciRelationShip_saveSCIRelationShip.action',
															params : {
																id : info.id,
																serviceItem : info.serviceItem,
																serviceItemFee : info.serviceItemFee,
																dispFlag:info.name
																
															},
															method : 'post',
															//�洢�ɹ�����õĺ�����
															success : function(response) {
																win.close();
																node.parentNode.reload();																									
																},
										                        scope:this
															});											
								        			},					        			
							        			text:'����',
							        			scope:this
							        		},
							        		{	xtype:'button',
							        	 		handler:function(){
							        				win.close();
							        			},
							        			text:'�ر�',
							        			scope:this
							        		}]						
											});
					                	}
										win.show();
										}
										})
									
									}
									}
//		           				}
									)
		           			}
		           			
		           	},{
					text : 'ɾ��',
					handler : function(){
						//ɾ�������Ϣ
						Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�",function(ret){
							if(ret=="yes" && node.id!=rootId){
								var nodeId = node.id;
								DWREngine.setAsync(false);
								SCIRelationShipManager.ajaxRemove(nodeId);
								DWREngine.setAsync(true);
								node.parentNode.reload();
							}else if(node.id==rootId){
								alert("���治��ɾ�����ĸ��ڵ㣡");
							}});
						}
					}]);
					menu.showAt(e.getPoint());
				}
				if (!menu&&kernel=='cata'){
					menu = new Ext.menu.Menu([
					{
						text : '�½�����Ŀ¼',
						handler : function(){
							var win;

							var idField = {
								id : "id",
								xtype : "hidden",
								name : "id",
								fieldLabel : "����Ŀ¼ID",
								hidden : true,
								allowBlank : false,
								selectOnFocus : true
							};

							var nameField = {
								id : "name",
								xtype : "textfield",
								name : "name",
								fieldLabel : "����Ŀ¼����",
								allowBlank : false,
								selectOnFocus : true
							};

							var descField = {
								id : "descn",
								xtype : "textarea",
								name : "descn",
								fieldLabel : "����Ŀ¼����",
								//allowBlank : false,
//								maxLength : 100,
//								maxLengthText : '�������ܳ���100',
								preventScrollbars : false,
								width : 200,
								height: 150,
								selectOnFocus : true
							};

							var newPanel = new Ext.form.FormPanel({
								id : 'childCataPanel',
								layout: 'table',
								height : 250,
								width : 'auto',
								frame : true,
								layoutConfig: {columns: 2},
								items:[
								{html: "����Ŀ¼����:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
								nameField,
								{html: "����Ŀ¼����:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
								descField,
								idField
								]
							});

							if(!win){
								win = new Ext.Window({
									id : 'newWin',
									title : "�½�����Ŀ¼",
									width : 400,
									height : 300,
									maximizable : true,
									modal : true,
									items: newPanel,
									buttons:[{
										xtype:'button',
										handler:function(){
											var info = Ext.getCmp('childCataPanel').getForm().getValues();
//											if(info.descn.length>100){
//												alert("������"+info.descn.length+",�������ܱ���");
//												return;
//											}
											if(info.name==""){
												Ext.Msg.alert("��ʾ","����д����Ŀ¼����!");
												return;
											}
											Ext.Ajax.request({
												url: webContext+'/sciRelationShip_saveChildSCIRelationShip.action',
												params : {
													id : info.id,
													name : info.name,
													descn : info.descn,
													parentId : node.id
												},
												method : 'post',
												//�洢�ɹ�����õĺ�����
												success : function(response) {
													win.close();
													node.reload();
													/*var result = Ext.decode(response.responseText);
													if(result.failure){
													Ext.MessageBox.alert("��ʾ��Ϣ��", "��������ʧ�ܣ�ԭ����һ�����Ų������ڶ��ƽ̨��������������ͬ��ֵ");
													win.close();
													node.reload();
													}else{
													Ext.MessageBox.alert("��ʾ��Ϣ��", "����ɹ�");
													win.close();
													node.reload();
													//}*/
												},
												scope:this
											});
										},
										text:'����',
										scope:this
									},
									{	xtype:'button',
										handler:function(){
											win.close();
										},
										text:'�ر�',
										scope:this
									}]
								});
							}
							win.show();
						}
					},{
						text : '�༭',
						handler : function(){
							Ext.Ajax.request({											
								url: webContext+'/sciRelationShip_findChildForEdit.action',
									params : {
										childId : node.id,
										target : "cata"
									},
								method : 'post',
								success : function(response) {
									var result = Ext.decode(response.responseText);
									var type = result.type;													
									if(type=="cata"){
										var id = result.id;
										var name = result.name;
										var descn = result.descn;
										var disp = result.dispFlag;
										var serviceItemFee = result.serviceItemFee;
										if(serviceItemFee=='null'){
											serviceItemFee = "";
										}
										var win;		                	
					                	var idField = {
					                		id : "id",
					                		xtype : "hidden",
					                		name : "id",
					                		value : id,
					                		fieldLabel : "����Ŀ¼����",
					                		hidden : true,
					                		allowBlank : false,
					                		selectOnFocus : true
					                	};
										
					                	var nameField = {
					                		id : "name",
					                		xtype : "textfield",
					                		name : "name",
					                		value : name,
					                		fieldLabel : "����Ŀ¼����",
					                		allowBlank : false,
					                		selectOnFocus : true
					                	};
					                	
					                	var serviceItemFeeField = {
					                		id : "serviceItemFee",
					                		xtype : "textfield",
					                		name : "serviceItemFee",
					                		value : serviceItemFee,
					                		fieldLabel : "������۸�",
					                		//allowBlank : false,//�Ƿ������ֵ
					                		selectOnFocus : true
//					                		emptyText : '����д�۸�'
					                	};
					                	
					                	var descField = {
					                		id : "descn",
					                		xtype : "textarea",
					                		name : "descn",
					                		fieldLabel : "����Ŀ¼����",
					                		value : descn,
//					                		maxLength : 100,
//					                		maxLengthText : '�������ܳ���100',
					                		preventScrollbars : false,
					                		width : 200,
					                		height: 120,
					                		selectOnFocus : true
					                	};
										var cataStore = new Ext.data.SimpleStore({
		                                		fields:['disp','value'],
		                                		data:[["0","��"],["1","��"]]
		                                	});
										var serviceItemCataDispFlag ={
											id:"cataDispFlag",
											xtype:'combo',
											typeAhead: true,
           									triggerAction: 'all',
			                				displayField:'value',
			                				hiddenName:'disp',
			                				name:'disp',
			                				width:125,
			                				value:2,//����Ĭ��ֵ
		                                	valueField:'disp',
		                                	store:cataStore,
		                                	forceSelection:true,
		                                	mode:'local'
										};
					                	var editForm = new Ext.form.FormPanel({
					                		id : 'editChildCataPanel',
					                		layout: 'table',
											height : 250,
											width : 'auto',
											frame : true,
											layoutConfig: {columns: 2},	    		
											items:[
												{html: "����Ŀ¼����:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												nameField,
												
												{html: "������۸�:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												serviceItemFeeField,
												
												{html: "�Ƿ�����:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												serviceItemCataDispFlag,
												
												{html: "����Ŀ¼����:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												descField,
												
												idField		
												]
												
					                	});	
					                	if(disp=="null"||disp==null){
					                		var cataDisp = "1";//1���������ģ�����Ĭ��ֵ
					                		Ext.getCmp("cataDispFlag").setValue(cataDisp);
					                	}else{
					                		Ext.getCmp("cataDispFlag").setValue(disp);
					                	}
					                	if(!win){
					                		var curRoot = Ext.getCmp("tree").root;
						                	win = new Ext.Window({
												id : 'editWin',
												title : "����Ŀ¼�༭����",
												width : 400,
												height : 300,
												maximizable : true,
												modal : true,
												items: editForm,								
												buttons:[
								        	       {	xtype:'button',
								        		        handler:function(){
								        		        var info = Ext.getCmp('editChildCataPanel').getForm().getValues();
//								        				if(info.descn.length>100){
//							        		        		alert("������"+info.descn.length+",�������ܱ���");
//							        		        		return;
//							        		        	}
								        				Ext.Ajax.request({											
															url: webContext+'/sciRelationShip_saveChildSCIRelationShip.action',
															params : {
																id : info.id,
																name : info.name,
																descn : info.descn,
																parentId : node.id,
																serviceItemFee : info.serviceItemFee,
																dispFlag:info.disp
															},
															method : 'post',
															//�洢�ɹ�����õĺ�����
															success : function(response) {
																win.close();
																//Ext.getCmp("tree").root.reload();
																if(node==curRoot){
																	window.location = webContext
																		+ "/sciRelationShip_getRootRelationShipData.action?rootCataId="
																		+ this.dataId+"&type=back";
																}else{
																	node.parentNode.reload();
																}
																/*var result = Ext.decode(response.responseText);													
																if(result.failure){
																	Ext.MessageBox.alert("��ʾ��Ϣ��", "��������ʧ�ܣ�ԭ����һ�����Ų������ڶ��ƽ̨��������������ͬ��ֵ");
																	win.close();															
																	node.reload();
																}else{
																	Ext.MessageBox.alert("��ʾ��Ϣ��", "����ɹ�");
																 	win.close();														 	
																 	node.reload();
																}*/																										
																},
										                        scope:this
															});											
								        			},					        			
							        			text:'����',
							        			scope:this
							        		},
							        		{	xtype:'button',
							        	 		handler:function(){
							        				win.close();
							        			},
							        			text:'�ر�',
							        			scope:this
							        		}]						
											});
					                	}
		                			win.show();
									}
								}
							});	
						}
					},{
						text : 'ɾ��',
						handler : function(){
							//ɾ�������Ϣ
							Ext.MessageBox.confirm("ɾ����ʾ","�Ƿ����Ҫɾ�����ݣ�",function(ret){
								if(ret=="yes" && node.id!=rootId){
									var nodeId = node.id;
									DWREngine.setAsync(false);
									SCIRelationShipManager.ajaxRemove(nodeId);
									DWREngine.setAsync(true);
									node.parentNode.reload();
								}else if(node.id==rootId){
									alert("���治��ɾ�����ĸ��ڵ㣡");
								}});
							}
						}]);
						menu.showAt(e.getPoint());
					}
				}
			},
			initComponent: function() {
			
				this.error = '0';
				this.kernel = ''; //�жϹ�ϵ�а������Ƿ���Ŀ¼���Ƿ�����
				if(dispFlag=="0"){
					this.root = new Ext.tree.AsyncTreeNode({
						text: rootText,
						draggable: false,
						id: rootId,
						icon : webContext+'/images/other/dispClass.gif',
						cls : "x-btn-text-icon"
					})
				}else{
					this.root = new Ext.tree.AsyncTreeNode({
						text: rootText,
						draggable: false,
						id: rootId,
						icon : webContext+'/images/other/class.gif',
						cls : "x-btn-text-icon"
					})
				
				}
				//���ݴӱ��õ����ݵ�id ��ҳ��ģ���id �����غ�̨����
				this.loader = new Ext.tree.TreeLoader({
					dataUrl:webContext+'/sciRelationShip_loadSCIRelationShip.action'
				});
				this.loader.on('beforeload', function(treeloader, node) {

					treeloader.baseParams = {
						id : node.id,
						rootId : rootId
					};

				}, this);
				PagteModelTreePanel.superclass.initComponent.call(this);
			}
		});