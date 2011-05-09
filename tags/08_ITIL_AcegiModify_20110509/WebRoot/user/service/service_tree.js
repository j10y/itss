/*
* 目标数据面板
*/

test = function(result){
	if(result=='ERROR_ADD'){
		alert("服务分类不能在服务项下建立");
		Ext.getCmp("tree").error = '1';
		return ;
	}else if(result=='ERROR_ITEM'){
		alert("服务项不能拖到服务项下");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_MOVE'){
		alert("服务分类不能拖到服务项下");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_RING'){
		alert("父节点已经存在此服务项，不能设置环状依赖");
		Ext.getCmp("tree").error = '1';
	}else if(result=='ERROR_DOUBLE'){
		alert("不能添加相同的服务项");
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

//目标节点，子节点，和选择的数据
var ddFunction = function(node, refNode, selections) {

	for(var i = 0; i < selections.length; i ++) {

		var record = selections[i];//这个record表示表格选中的数据
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
		//父节点加载数据
		node.reload();
	}
}

PagteModelTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '服务目录关系',
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
	listeners: { //拖拽之前会触发，这是针对表格到树拖拽的例子

		beforenodedrop: function(dropEvent) {
			var node = dropEvent.target;    // 目标结点
			var data = dropEvent.data;      // 拖拽的数据
			var point = dropEvent.point;    // 拖拽到目标结点的位置

			// 如果data.node为空，则不是tree本身的拖拽，而是从grid到tree的拖拽，需要处理
			if(!data.node) {

				for(var i = 0; i < data.selections.length; i++) {
					var record = data.selections[i];//这个record表示表格选中的数据
					var pagePanelId = record.get('id');
				}

				switch(point) {
					case 'append':
					// 添加时，目标结点为node，放到子结点为空的这种情况
					//alert(node.id);
					ddFunction(node, null, data.selections);
					break;
					case 'above':
					// 插入到node之上，目标结点为node的parentNode，子结点为node
					//alert(node.id);
					ddFunction(node.parentNode, node, data.selections);
					break;
					case 'below':
					// 插入到node之下，目标结点为node的parentNode，子结点为node的nextSibling
					ddFunction(node.parentNode, node.nextSibling, data.selections);
					break;
				}
			}
		},

		//当节点移动时触发事件
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
					text : '查看',
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
												title : '查看服务项信息',
												height:500,
												width:800,
												resizable:false,
												draggable:true,
												autoLoad:{
													url:webContext+"/tabFrame.jsp?url="+webContext+"/user/service/configItemServiceView.jsp?dataId="+id,
													text:"页面加载中......",
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
													text : '关闭',
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
		           		text : '修改服务项价格',
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
//										alert("服务目录没有价格");
//									}else if(type=="item"){

										var id = node.id;
										Ext.Ajax.request({//写个action查出SCIRelationShip所有值 返回json serviceItem和serviceItemfee							
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
					                		fieldLabel : "服务项名称",
					                		allowBlank : false,
					                		selectOnFocus : true,
					                		readOnly : true
					                	};
					                	
					                	var serviceItemFeeField = {
					                		id : "serviceItemFee",
					                		xtype : "textfield",
					                		name : "serviceItemFee",
					                		value : serviceItemFee,
					                		fieldLabel : "服务项价格",
					                		//allowBlank : false,//是否允许空值
					                		selectOnFocus : true,
					                		emptyText : '请填写价格'
					                	};
			
					                	var editForm = new Ext.form.FormPanel({
					                		id : 'editChildCataPanel',
					                		layout: 'table',
											height : 230,
											width : 'auto',
											frame : true,
											layoutConfig: {columns: 2},	    		
											items:[
												{html: "服务项名称:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
												serviceItemField,		
												{html: "服务项价格:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:center;margin:5 0 5 0;'},
												serviceItemFeeField,
												idField		
												]
												
					                	});					
					                	if(!win){
						                	win = new Ext.Window({
												id : 'editWin',
												title : "服务项价格编辑窗口",
												width : 300,
												height : 240,
												maximizable : true,
												modal : true,
												items: editForm,								
												buttons:[
								        	       {	xtype:'button',
								        		        handler:function(){
								        		        var info = Ext.getCmp('editChildCataPanel').getForm().getValues();
								        				Ext.Ajax.request({											
															url: webContext+'/sciRelationShip_saveSCIRelationShip.action',
															params : {
																id : info.id,
																serviceItem : info.serviceItem,
																serviceItemFee : info.serviceItemFee
															},
															method : 'post',
															//存储成功后调用的函数。
															success : function(response) {
																win.close();
																node.parentNode.reload();																									
																},
										                        scope:this
															});											
								        			},					        			
							        			text:'保存',
							        			scope:this
							        		},
							        		{	xtype:'button',
							        	 		handler:function(){
							        				win.close();
							        			},
							        			text:'关闭',
							        			scope:this
							        		}]						
											});
					                	}
										win.show();
										}
										})
									
									}
									}
									//}
		           				)
		           			}
		           			
		           	},{
					text : '删除',
					handler : function(){
						//删除结点信息
						Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
							if(ret=="yes" && node.id!=rootId){
								var nodeId = node.id;
								DWREngine.setAsync(false);
								SCIRelationShipManager.ajaxRemove(nodeId);
								DWREngine.setAsync(true);
								node.parentNode.reload();
							}else if(node.id==rootId){
								alert("警告不能删除树的根节点！");
							}});
						}
					}]);
					menu.showAt(e.getPoint());
				}
				if (!menu&&kernel=='cata'){
					menu = new Ext.menu.Menu([
					{
						text : '新建服务分类',
						handler : function(){
							var win;

							var idField = {
								id : "id",
								xtype : "hidden",
								name : "id",
								fieldLabel : "服务目录ID",
								hidden : true,
								allowBlank : false,
								selectOnFocus : true
							};

							var nameField = {
								id : "name",
								xtype : "textfield",
								name : "name",
								fieldLabel : "服务分类名称",
								allowBlank : false,
								selectOnFocus : true
							};

							var descField = {
								id : "descn",
								xtype : "textarea",
								name : "descn",
								fieldLabel : "服务分类描述",
								//allowBlank : false,
//								maxLength : 100,
//								maxLengthText : '字数不能超过100',
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
								{html: "服务分类名称:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
								nameField,
								{html: "服务分类描述:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
								descField,
								idField
								]
							});

							if(!win){
								win = new Ext.Window({
									id : 'newWin',
									title : "新建服务目录",
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
//												alert("字数："+info.descn.length+",过量不能保存");
//												return;
//											}
											Ext.Ajax.request({
												url: webContext+'/sciRelationShip_saveChildSCIRelationShip.action',
												params : {
													id : info.id,
													name : info.name,
													descn : info.descn,
													parentId : node.id
												},
												method : 'post',
												//存储成功后调用的函数。
												success : function(response) {
													win.close();
													node.reload();
													/*var result = Ext.decode(response.responseText);
													if(result.failure){
													Ext.MessageBox.alert("提示信息：", "保存数据失败，原因是一个部门不能属于多个平台或是您输入了相同的值");
													win.close();
													node.reload();
													}else{
													Ext.MessageBox.alert("提示信息：", "保存成功");
													win.close();
													node.reload();
													//}*/
												},
												scope:this
											});
										},
										text:'保存',
										scope:this
									},
									{	xtype:'button',
										handler:function(){
											win.close();
										},
										text:'关闭',
										scope:this
									}]
								});
							}
							win.show();
						}
					},{
						text : '编辑',
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
					                		fieldLabel : "服务目录名称",
					                		hidden : true,
					                		allowBlank : false,
					                		selectOnFocus : true
					                	};
										
					                	var nameField = {
					                		id : "name",
					                		xtype : "textfield",
					                		name : "name",
					                		value : name,
					                		fieldLabel : "服务目录名称",
					                		allowBlank : false,
					                		selectOnFocus : true
					                	};
					                	
					                	var serviceItemFeeField = {
					                		id : "serviceItemFee",
					                		xtype : "textfield",
					                		name : "serviceItemFee",
					                		value : serviceItemFee,
					                		fieldLabel : "服务项价格",
					                		//allowBlank : false,//是否允许空值
					                		selectOnFocus : true,
					                		emptyText : '请填写价格'
					                	};
					                	
					                	var descField = {
					                		id : "descn",
					                		xtype : "textarea",
					                		name : "descn",
					                		fieldLabel : "服务目录描述",
					                		value : descn,
//					                		maxLength : 100,
//					                		maxLengthText : '字数不能超过100',
					                		preventScrollbars : false,
					                		width : 200,
					                		height: 150,
					                		selectOnFocus : true
					                	};
			
					                	var editForm = new Ext.form.FormPanel({
					                		id : 'editChildCataPanel',
					                		layout: 'table',
											height : 250,
											width : 'auto',
											frame : true,
											layoutConfig: {columns: 2},	    		
											items:[
												{html: "服务分类名称:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												nameField,		
												
												{html: "服务项价格:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												serviceItemFeeField,
												{html: "服务分类描述:&nbsp;" ,cls: 'common-text', style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},
												descField,
												idField		
												]
												
					                	});					
					                	if(!win){
						                	win = new Ext.Window({
												id : 'editWin',
												title : "服务分类编辑窗口",
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
//							        		        		alert("字数："+info.descn.length+",过量不能保存");
//							        		        		return;
//							        		        	}
								        				Ext.Ajax.request({											
															url: webContext+'/sciRelationShip_saveChildSCIRelationShip.action',
															params : {
																id : info.id,
																name : info.name,
																descn : info.descn,
																parentId : node.id,
																serviceItemFee : info.serviceItemFee
															},
															method : 'post',
															//存储成功后调用的函数。
															success : function(response) {
																win.close();
																node.parentNode.reload();
																/*var result = Ext.decode(response.responseText);													
																if(result.failure){
																	Ext.MessageBox.alert("提示信息：", "保存数据失败，原因是一个部门不能属于多个平台或是您输入了相同的值");
																	win.close();															
																	node.reload();
																}else{
																	Ext.MessageBox.alert("提示信息：", "保存成功");
																 	win.close();														 	
																 	node.reload();
																}*/																										
																},
										                        scope:this
															});											
								        			},					        			
							        			text:'保存',
							        			scope:this
							        		},
							        		{	xtype:'button',
							        	 		handler:function(){
							        				win.close();
							        			},
							        			text:'关闭',
							        			scope:this
							        		}]						
											});
					                	}
		                			win.show();
									}
								}
							});	
						}
					},
						{
						text : '删除',
						handler : function(){
							//删除结点信息
							Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
								if(ret=="yes" && node.id!=rootId){
									var nodeId = node.id;
									DWREngine.setAsync(false);
									SCIRelationShipManager.ajaxRemove(nodeId);
									DWREngine.setAsync(true);
									node.parentNode.reload();
								}else if(node.id==rootId){
									alert("警告不能删除树的根节点！");
								}});
							}
						}]);
						menu.showAt(e.getPoint());
					}
				}
			},

			initComponent: function() {
				this.error = '0';
				this.kernel = ''; //判断关系中包含的是服务分类还是服务项
				this.root = new Ext.tree.AsyncTreeNode({
					text: rootText,
					draggable: false,
					id: rootId
				}),

				//根据从表格得到数据的id 和页面模板的id 来加载后台数据
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