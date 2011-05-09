SelectConfigItemPanel=Ext.extend(Ext.Panel, {
	id:'selectConfigItemPanel',
	title:'选择配置项',
	closable:true,
	layout:'border',
	autoScroll : true,
	frame : true,
	initComponent: function(){
		var modifyId=this.modifyId;
		var modifyConfigItem=function(itemsId,cisn,win){
			if(Ext.getCmp('modifyConfigItem')!=undefined){
				Ext.getCmp('modifyConfigItem').disable();
			}
			if(Ext.getCmp('modifyConfigItemShortcut')!=undefined){
				Ext.getCmp('modifyConfigItemShortcut').disable();
			}
			Ext.Ajax.request({
				url : webContext+ '/configItemAction_modifyConfigItemValidate.action',
				params : {
						cisn : cisn,
						modifyId:modifyId
				},
				success : function(response, options) {
					var result=Ext.decode(response.responseText);
					if(result.exist){
						Ext.MessageBox.alert("提示","您已存在针对配置项"+cisn+"的变更草稿！",function(){
							if(Ext.getCmp('modifyConfigItem')!=undefined){
								Ext.getCmp('modifyConfigItem').enable();
							}
							if(Ext.getCmp('modifyConfigItemShortcut')!=undefined){
								Ext.getCmp('modifyConfigItemShortcut').enable();
							}
						});
						return;
					}else{
						var url=webContext+'/user/configItem/configItemBatchModify/configItemInfo.jsp?oldId='+itemsId+
																						     "****modifyId="+modifyId;
						var panel=new Ext.Panel({
							title:'变更配置项',
							closable:true,
							autoLoad : {
								url : webContext + "/tabFrame.jsp?url="+url,
								text : "页面正在加载中......",
								method : 'post',
								scope : this
							}
						})
						Ext.getCmp('modifyTab').add(panel);
						Ext.getCmp('modifyTab').doLayout();
						Ext.getCmp('modifyTab').activate(panel);
						if(win!=undefined){
							win.close();
						}
						if(Ext.getCmp('modifyConfigItemShortcut')!=undefined){
							Ext.getCmp('modifyConfigItemShortcut').enable();
						}
					}
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("提示","系统异常!",function(){
						if(Ext.getCmp('modifyConfigItem')!=undefined){
							Ext.getCmp('modifyConfigItem').enable();
						}
						if(Ext.getCmp('modifyConfigItemShortcut')!=undefined){
							Ext.getCmp('modifyConfigItemShortcut').enable();
						}				
					});
				}
			})
		}
		var replaceConfigItemRel=function(itemCode,TypeId,name,win){
			function unicode(s) {
				var len = s.length;
				var rs = "";
				for (var i = 0; i < len; i++) {
					var k = s.substring(i, i + 1);
					rs += "@" + s.charCodeAt(i) + ";";
				}
				return rs;
			}
			var itemName=unicode(name);
			var url=webContext+'/user/configItem/configItemBatchModify/configItemRelReplaceList.jsp?itemCode='+itemCode+
																								"****modifyId="+modifyId+
																								"****itemName="+itemName+
																								"****TypeId="+TypeId;
			var panel=new Ext.Panel({
				id:'replaceRelPanel',
				title:'关系替换',
				closable:true,
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+url,
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				}
			})
			if(Ext.getCmp("replaceRelPanel")!=undefined){
				Ext.getCmp('modifyTab').remove("replaceRelPanel");
			}
			Ext.getCmp('modifyTab').add(panel);
			Ext.getCmp('modifyTab').doLayout();
			Ext.getCmp('modifyTab').activate(panel);
			if(win!=undefined){
				win.close();
			}
		}
		var maintenanceNecessaryRel=function(itemId,itemCode,TypeId,win){
			Ext.Ajax.request({
				url : webContext+ '/configItemAction_findIsOrphanCI.action',
				params:{
					itemCode:itemCode,
					modifyId:modifyId,
					TypeId:TypeId
				},
				success:function(response){
					if(response.responseText.trim()!=''){
						var result=Ext.decode(response.responseText);
						if(result.isOrphan!=undefined){
							Ext.MessageBox.alert("提示","配置项状态为:\""+result.status+"\"时不允许维护必要关系!",function(){
							});
							return;
						}
						if(result.noneNecessaryRel!=undefined){
							Ext.MessageBox.alert("提示","没有需要维护的关系!",function(){
							});
							return;
						}
					}
					var store = new Ext.data.JsonStore({ 				
						url: webContext+'/configItemAction_findCINecessaryRel.action?itemCode='+itemCode+"&TypeId="+TypeId+"&modifyId="+modifyId,
						fields: ['configItemType','otherConfigItemType','parentOrChildType',"isOptional","isExist"],
					    root:'data'
					}); 
					var cm = new Ext.grid.ColumnModel([
						    {header: "配置项类型",  sortable: true, dataIndex: 'configItemType'}, 
						    {header: "关联配置项类型", sortable: true, dataIndex: 'otherConfigItemType'},
						    {header: "关系种类", sortable: true, dataIndex: 'parentOrChildType'},
						     {header: "是否可选", sortable: true, dataIndex: 'isOptional'},
						    {header: "是否已存在",  sortable: true, dataIndex: 'isExist'}	
					]); 
					var grid = new Ext.grid.GridPanel({
					        store: store,
					        cm: cm,
					        autoScroll : true,
					        frame:true,
					        loadMask: true
					}); 			
					var necessaryRelWin=new Ext.Window({
						title:"必要关系查看",
						width:530,
						height:300,
						layout:'fit',
						maximizable:true,
						autoScroll :true,
						modal:true,
						buttonAlign:'center',
						items:grid,
						buttons:[{
									text:'维护必要关系',
									id:'maintenance',
									disabled:true,
									handler:function(){
										var maintenanceNecessary=function(createAllNecessaryRel){
											Ext.Ajax.request({
												url:webContext+"/configItemAction_maintenanceNecessaryRel.action",
												params:{
													dataId:itemId,
													modifyId:modifyId,
													createAllNecessaryRel:createAllNecessaryRel
												},
												success:function(response){
													if(response.responseText.trim()!=""){
														var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoMaintenanceRel.jsp?dataId='+itemId+"****modifyId="+modifyId+"****createAllNecessaryRel="+createAllNecessaryRel;
														var panel=new Ext.Panel({
															title:'维护必要关系',
															closable:true,
															autoLoad : {
																url : webContext + "/tabFrame.jsp?url="+url,
																text : "页面正在加载中......",
																method : 'post',
																scope : this
															}
														})
														Ext.getCmp('modifyTab').add(panel);
														Ext.getCmp('modifyTab').doLayout();
														Ext.getCmp('modifyTab').activate(panel);
														necessaryRelWin.close();
														if(win!=undefined){
															win.close();
														}
													}else{
														Ext.Msg.alert("提示","维护成功,生成关系已进入变更配置单！",function(){
															Ext.getCmp('modifyRelGrid').getStore().reload();
															necessaryRelWin.close();
															if(win!=undefined){
																win.close();
															}
														});
													}
												},
												failure:function(){
													Ext.Msg.alert("提示","系统异常");
												}
											})
										}
										Ext.Ajax.request({
											url:webContext+"/configItemAction_findHasNotExistOptionalRel.action",
											params:{
												modifyId:modifyId,
												cid:itemId,
												cisn:itemCode,
												configItemTypeId:TypeId
											},
											success : function(response, options) {
												if(response.responseText!=""){
													var result=Ext.decode(response.responseText);
														new Ext.Window({
															id:"alertWin",
															title:"<font color=red>是否生成可选关系？</font>",
															width:350,
															height:200,
															closable :false,
															maximizable:true,
															autoScroll :true,
															modal:true,
															buttonAlign:'center',
															html :result.message+"<p align='center'><font color=red>是否生成可选关系？</font></p>",
															buttons:[{
																text:'是',
																handler:function(){
																	Ext.getCmp('alertWin').close();
																	maintenanceNecessary(true);
																}
															},
															{
																text:'否',
																handler:function(){
																	Ext.getCmp('alertWin').close();
																	maintenanceNecessary(false);
																}
															}
															]
														}).show();
												}else{
													maintenanceNecessary(false);
												}
											},
											failure : function(response, options) {
												maintenanceNecessary(false);
											}
										})
									}
						},
						{
							text:'关闭',
							handler:function(){
								necessaryRelWin.close();
							}
						}
						]
					});
					necessaryRelWin.show();
					store.load({
						callback:function(r){
							for(var i=0;i<r.length;i++){
								if(r[i].get("isExist")=='不存在'){
									Ext.getCmp("maintenance").enable();
									break;
								}
							}
						}
					});
				},
				failure:function(){
					Ext.MessageBox.alert("提示","系统异常!",function(){
					});
				}
			})
		}
		var	searchConfigItem= function() {
			var name=Ext.encode(Ext.getCmp('itemNameCI').getValue());
			name=name.substring(1,name.length-1);
			var type = Ext.getCmp('itemTypeCI').getValue();
			var code = Ext.getCmp('itemCodeCI').getValue();
			var param = {
					item : 'ci',
					name : name,
					type:type,
					code:code,
					start: 0
				};
			store.baseParams=param;
			store.load();
		}
		var store = new Ext.data.JsonStore({ 				
						url: webContext+'/configItemAction_findItem.action',
						fields: ['id','name','Type',"TypeId",'itemCode'],//configType
					    root:'data',
					    totalProperty : 'rowCount',
						sortInfo: {field: "id", direction: "ASC"}
		}); 
		var sm = new Ext.grid.CheckboxSelectionModel();		
		var cm = new Ext.grid.ColumnModel([sm,
				    {header: "名称",  sortable: true, dataIndex: 'name'}, 
				    {header: "类型", sortable: true, dataIndex: 'Type'},
				    {header: "编号", sortable: true, dataIndex: 'itemCode'},
				    {header: "id",  sortable: true, dataIndex: 'id',hidden:true}	
			]); 
				
				
			var itemName = new Ext.form.TextField({
				id:"itemNameCI",
				name : 'name'
			});
				
			var itemType = new Ext.form.ComboBox({
				store : new Ext.data.JsonStore({
					url: webContext+'/configItemAction_findItemTypeByItem.action',
					fields: ['id','name'],
				    root:'data',
					sortInfo: {field: "id", direction: "ASC"},
					listeners:{
						beforeload :function(store,options){
								store.baseParams={
									item:'ci'
								};
							}
						} 
				}),
				id:'itemTypeCI',
				valueField : "id",
				displayField : "name",
	            emptyText: '请选择',
				mode : 'remote',
				hiddenName : "type",
				maxHeight:200,
				editable : false,
				triggerAction : 'all', 
				allowBlank : true
			});
				
			var itemCode = new Ext.form.TextField({
				id:"itemCodeCI",
				name : 'code'
			});
				
			var pageBar = new Ext.PagingToolbar({
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
					emptyMsg : '无显示数据',
					plugins :new Ext.ux.ProgressBarPager()
			});
				
			var searchForm = new Ext.form.FormPanel({
				id : "search",
				region:'north',
				layout : 'table',
				frame : true,
				height:43,
				autoScroll : true,
				keys:{
				    key:Ext.EventObject.ENTER,
				    fn: function(){
				    	searchConfigItem();
				    },
				    scope: this
				},
				layoutConfig : {columns: 6},
				items : [
					{html: "&nbsp;&nbsp;名称:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;margin:5 0 5 0;'},
					itemName,
					{html: "&nbsp;&nbsp;编号:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;margin:5 0 5 0;'},
					itemCode,
					{html: "&nbsp;&nbsp;类型:" ,cls: 'common-text', style:'width:50;height:20;text-align:left;margin:5 0 5 0;'},
					itemType
					]
			});
				var grid = new Ext.grid.GridPanel({
						id :'addGrid',
				        store: store,
				        sm:sm,
				        cm: cm,
				        region:'center',
				        autoScroll : true,
				        frame:true,
				        loadMask: true,
				        tbar : ['   ', {
						text : ' 查询',
						handler : searchConfigItem,
						scope : this,
						iconCls : 'search',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' 重置',
						handler : function(){
							searchForm.form.reset();
						},
						scope : this,
						iconCls : 'reset',
						cls : "x-btn-text-icon"
						},"-",
						{
						text : ' 查看',
						handler : function(){
							grid.fireEvent('rowdblclick',grid);
						},
						scope : this,
						iconCls : 'look',
						cls : "x-btn-text-icon"
						},"-",{
						id:'modifyConfigItemShortcut',
						text : ' 变更',
						handler : function(){
							var records = grid.getSelectionModel().getSelections();
							if(records.length==0){
								Ext.Msg.alert("提示","请选择配置项!");
								return;
							}
							if(records.length>1){
								Ext.Msg.alert("提示","只能选择一个配置项!");
								return;
							}
							var itemsId=records[0].get('id');
							var itemCode=records[0].get('itemCode');
							modifyConfigItem(itemsId,itemCode);
						},
						scope : this,
						iconCls : 'add',
						cls : "x-btn-text-icon"
						},"-",{
						id:'replaceConfigItemRelShortcut',
						text : '关系替换',
						handler : function(){
							var records = grid.getSelectionModel().getSelections();
							if(records.length==0){
								Ext.Msg.alert("提示","请选择配置项!");
								return;
							}
							if(records.length>1){
								Ext.Msg.alert("提示","只能选择一个配置项!");
								return;
							}
							var itemCode=records[0].get('itemCode');
							var TypeId=records[0].get('TypeId');
							var name=records[0].get('name');
							replaceConfigItemRel(itemCode,TypeId,name);
						},
						scope : this,
						iconCls : 'add',
						cls : "x-btn-text-icon"
						},"-",{
								text : '必要关系查看',
								scope : this,
								iconCls : 'look',
								cls : "x-btn-text-icon",
								handler:function(){
									var records = grid.getSelectionModel().getSelections();
									if(records.length==0){
										Ext.Msg.alert("提示","请选择配置项!");
										return;
									}
									if(records.length>1){
										Ext.Msg.alert("提示","只能选择一个配置项!");
										return;
									}
									var itemId=records[0].get('id');
									var itemCode=records[0].get('itemCode');
									var TypeId=records[0].get('TypeId');
									maintenanceNecessaryRel(itemId,itemCode,TypeId);
								}
						}
						],
						bbar : pageBar
				}); 
				grid.on('rowdblclick',function(grid,rowIndex,eventObject){
					var records = grid.getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("提示","请选择配置项!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("提示","只能选择一个配置项!");
						return;
					}
					var itemsId=records[0].get('id');
					var itemCode=records[0].get('itemCode');
					var name=records[0].get('name');
					var TypeId=records[0].get('TypeId');
					var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoForLook.jsp';
					var win=new Ext.Window({
						title:'配置项信息',
						width:730,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:350,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?dataId="+itemsId,
							text : "页面正在加载中......",
							method : 'post',
							scope : this
						},
						buttonAlign:"center",
						buttons:[{
							id:'modifyConfigItem',
							text:'变更',
							iconCls:'save',
							handler:function(){
								modifyConfigItem(itemsId,itemCode,win);
							}
					 	 },{
							id:'replaceConfigItemRel',
							text:'关系替换',
							iconCls:'save',
							handler:function(){
								replaceConfigItemRel(itemCode,TypeId,name,win);
							}
					 	 },{
								text : '必要关系查看',
								scope : this,
								iconCls : 'look',
								cls : "x-btn-text-icon",
								handler:function(){
									maintenanceNecessaryRel(itemsId,itemCode,TypeId,win);
								}
						},{
							text:'关闭',
							handler:function(){
								win.close();
							}
						}
						]
					});
					win.show();
			});
			var param = {
					item:"ci",
					start:0
			};
			store.baseParams=param;
			store.load();
			this.items = [searchForm,grid];	
			SelectConfigItemPanel.superclass.initComponent.call(this);	
	}
})

PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout:'fit',
	autoScroll : true,
	frame:true,
	buttonAlign:'right',
	
 getFormpagePanel_CIBatchModify: function() {
      var da = new DataAction();
	  var data = null;
	  var biddata = "";
	  if (this.dataId != '') {
		data = da.getSingleFormPanelElementsForEdit("pagePanel_CIBatchModify", this.dataId);
		for(var i=0;i<data.length;i++){
			if(data[i].id=="CIBatchModify$modifyNo"){
				data[i].readOnly=true;
			}
		}
		biddata = da.split(data);
	 } else {
		data = da.getPanelElementsForAdd("pagePanel_CIBatchModify");
		for(var i=0;i<data.length;i++){
			if(data[i].id=="CIBatchModify$modifyNo"){
				data[i].readOnly=true;
				data[i].emptyText ="编号自动生成";
				
			}
			if(data[i].id=="CIBatchModify$emergentCombo"){
				data[i].value=0;
			}
		}
		biddata = da.split(data);
	 }
		this.formpagePanel_CIBatchModify= new Ext.form.FormPanel({
		id : 'pagePanel_CIBatchModify',
		layout : 'table',
		width:740,
		title:'变更申请',
		buttonAlign:'center',
		autoScroll : true,
		frame : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		items : biddata,
		buttons:[{text : '保存变更申请',
			id:"modifySave",
			handler : this.saveModify,
			scope : this,
			iconCls : 'save',
			cls : "x-btn-text-icon"
		}
			]
		});
		return this.formpagePanel_CIBatchModify;
	},
	
	getModifyConfigGrid:function(modifyId){
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'cid',dataIndex:'cid',hidden:true,sortable:true},
											{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
											{header:'oldId',dataIndex:'oldId',hidden:true,sortable:true},
											{header:'变更类型',dataIndex:'status',sortable:true},
											{header:'配置项类型',dataIndex:'configItemType',sortable:true},
											{header:'配置项名称',dataIndex:'configItemName',sortable:true},
											{header:'配置项编号',dataIndex:'configItemCisn',sortable:true},
											{header:'实施内容',dataIndex:'descn',sortable:true},
											{header:'实施工程师',dataIndex:'officer',sortable:true},
											{header:'开始日期',dataIndex:'startDate',sortable:true},
											{header:'结束日期',dataIndex:'endDate',sortable:true}
											]);
		this.storeChild=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getBatchModifyConfigItemList.action?modifyId='+modifyId,
				fields : ['cid', 'pid','oldId','configItemName','configItemCisn','configItemType','descn','officer','startDate','endDate','status'],
				totalProperty : 'rowCount',
				root : 'data'
				
		});
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"modifyGrid",
			title:"变更的配置项",
			store : this.storeChild,
			width:740,
			cm : cm,
			sm : sm,
			height:320,
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar,
			tbar:[
				{text : ' 新建配置项',
				handler : function(){
					var url=webContext+'/user/configItem/configItemBatchModify/configItemInfo.jsp';
					var panel=new Ext.Panel({
						title:'新建配置项',
						closable:true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?modifyId="+modifyId,
							text : "页面正在加载中......",
							method : 'post',
							scope : this
						}
					})
					Ext.getCmp('modifyTab').add(panel);
					Ext.getCmp('modifyTab').doLayout();
					Ext.getCmp('modifyTab').activate(panel);
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},'-',
			{text : ' 选择配置项',
				handler : function(){
					if(Ext.getCmp('selectConfigItemPanel')!=undefined){
						Ext.getCmp('modifyTab').remove(Ext.getCmp('selectConfigItemPanel'));
					};
					var selectConfigItemPanel =new SelectConfigItemPanel({modifyId:modifyId});
					Ext.getCmp('modifyTab').add(selectConfigItemPanel);
					Ext.getCmp('modifyTab').doLayout();
					Ext.getCmp('modifyTab').activate('selectConfigItemPanel');
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},'-',
			{   text : ' 删除配置项草稿',
				id:"removeCIDraft",
				handler : function(){
					var records = Ext.getCmp('modifyGrid').getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("提示","请选择要删除的配置项!");
						return;
					}
					var planId=new Array();
					for(var i=0;i< records.length;i++){
						var pid=records[i].get('pid');
						planId.push(pid);
					}
					planId=Ext.encode(planId);
					Ext.MessageBox.confirm("提示","确认删除？",function(ret){
						if(ret=='yes'){
							Ext.getCmp("removeCIDraft").disable();
							Ext.Ajax.request({
									url : webContext
											+ '/configItemAction_removeBatchModifyPlans.action',
									params : {
										planId:planId
									},
									success : function(response, options) {
										Ext.Msg.alert("提示", "删除成功", function() {
											Ext.getCmp("removeCIDraft").enable();
											Ext.getCmp('modifyGrid').getStore().reload();
											Ext.getCmp('modifyRelGrid').getStore().reload();
										});
			
									},
									failure : function(response, options) {
										Ext.Msg.alert("提示", "删除失败",function(){
											Ext.getCmp("removeCIDraft").enable();
										});
									}
								}, this);
						}
					});
				},
				scope : this,
				iconCls : 'remove',
				cls : "x-btn-text-icon"
				
			}]
			
			
			});
		var param = {
			'start' : 0
		};
		this.storeChild.load({
			params : param
		});
		this.grid.on("rowdblclick",function(grid,rowIndex,eventObject){
			var record = grid.getSelectionModel().getSelected();
			var cid=record.get('cid');
			var pid=record.get('pid');
			var oldId=record.get('oldId');
			var status=record.get('status');
			if(Ext.getCmp(pid+"$")!=undefined){
				Ext.getCmp('modifyTab').remove(pid+"$");
			}
			if(status=='维护必要关系')	{
				var title='维护必要关系';
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoMaintenanceRel.jsp?dataId='+cid+
																									     "****modifyId="+modifyId+
																									     "****pid="+pid;
			}else{	
				var title=status+'配置项';
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfo.jsp?dataId='+cid+
																				     "****oldId="+oldId+
																				     "****modifyId="+modifyId+
																				     "****pid="+pid;
			}											     
			var panel=new Ext.Panel({
				id:pid+"$",
				title:title,
				closable:true,
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+url,
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				}
			})
			Ext.getCmp('modifyTab').add(panel);
			Ext.getCmp('modifyTab').doLayout();
			Ext.getCmp('modifyTab').activate(panel);	
		},this);
		return this.grid; 
	
	},
	getModifyRelGrid:function(modifyId){
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'rid',dataIndex:'rid',hidden:true,sortable:true},
											{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
											{header:'oldRid',dataIndex:'oldRid',hidden:true,sortable:true},
											{header:'变更类型',dataIndex:'status',sortable:true},
											{header:'父类型',dataIndex:'parentType',sortable:true},
											{header:'父名称',dataIndex:'parentName',sortable:true},
											{header:'父编号',dataIndex:'parentCode',sortable:true},
											{header:'子类型',dataIndex:'childType',sortable:true},
											{header:'子名称',dataIndex:'childName',sortable:true},
											{header:'子编号',dataIndex:'childCode',sortable:true},
											{header:'实施内容',dataIndex:'descn',sortable:true},
											{header:'实施工程师',dataIndex:'officer',sortable:true},
											{header:'开始日期',dataIndex:'startDate',sortable:true},
											{header:'结束日期',dataIndex:'endDate',sortable:true}
											]);
		this.storeChild=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getBatchModifyRelList.action?modifyId='+modifyId,
				fields : ['rid', 'pid','oldRid','parentType','parentName','parentCode','childType','childName','childCode','descn','officer','startDate','endDate','status'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,// 使用的是系统默认值
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
			
		});
		this.grid = new Ext.grid.GridPanel({
			id:"modifyRelGrid",
			title:"变更的关系",
			store : this.storeChild,
			width:740,
			cm : cm,
			sm : sm,
			height:320,
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar,
			tbar:[
				{text : ' 新建关系',
				handler : function(){
					var url=webContext+'/user/configItem/configItemBatchModify/configItemRelCreate.jsp';
					var panel=new Ext.Panel({
						title:'新建关系',
						closable:true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?modifyId="+modifyId,
							text : "页面正在加载中......",
							method : 'post',
							scope : this
						}
					})
					Ext.getCmp('modifyTab').add(panel);
					Ext.getCmp('modifyTab').doLayout();
					Ext.getCmp('modifyTab').activate(panel);
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},'-',
			{text : '选择关系',
				handler : function(){
					if(Ext.getCmp('relListPanel')!=undefined){
						Ext.getCmp('modifyTab').remove(Ext.getCmp('relListPanel'));
					};
					var relListPanel =new RelListPanel({modifyId:modifyId});
					Ext.getCmp('modifyTab').add(relListPanel);
					Ext.getCmp('modifyTab').doLayout();
					Ext.getCmp('modifyTab').activate('relListPanel');
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},'-',
			{   text : ' 删除关系草稿',
				id:"removeRelDraft",
				handler : function(){
					var records = Ext.getCmp('modifyRelGrid').getSelectionModel().getSelections();
					if(records.length==0){
						Ext.Msg.alert("提示","请选择要删除的关系!");
						return;
					}
					var planId=new Array();
					for(var i=0;i< records.length;i++){
						var pid=records[i].get('pid');
						planId.push(pid);
					}
					planId=Ext.encode(planId);
					Ext.MessageBox.confirm("提示","确认删除？",function(ret){
						if(ret=='yes'){
							Ext.getCmp("removeRelDraft").disable();
							Ext.Ajax.request({
									url : webContext
											+ '/configItemAction_removeBatchModifyPlans.action',
									params : {
										planId:planId
									},
									success : function(response, options) {
										Ext.Msg.alert("提示", "删除成功", function() {
											Ext.getCmp("removeRelDraft").enable();
											Ext.getCmp('modifyRelGrid').getStore().reload();
										});
			
									},
									failure : function(response, options) {
										Ext.Msg.alert("提示", "删除失败",function(){
											Ext.getCmp("removeRelDraft").enable();
										});
									}
								}, this);
						}
					});
				},
				scope : this,
				iconCls : 'remove',
				cls : "x-btn-text-icon"
				
			}]
			
			
			});
		var param = {
			'start' : 0
		};
		this.storeChild.load({
			params : param
		});
		this.grid.on("rowdblclick",function(grid,rowIndex,eventObject){
			var record = grid.getSelectionModel().getSelected();
			var rid=record.get('rid');
			var pid=record.get('pid');
			var oldRid=record.get('oldRid');
			var status=record.get('status');
			if(Ext.getCmp(pid+"$")!=undefined){
				Ext.getCmp('modifyTab').remove(pid+"$");
			}
			if(status=='删除'){
				var url=webContext+'/user/configItem/configItemBatchModify/configItemRelDelete.jsp?rid='+rid+
																	     "****oldRid="+oldRid+
																	     "****modifyId="+modifyId+
																	     "****pid="+pid;
			}else if(status=='变更'){
				var url=webContext+'/user/configItem/configItemBatchModify/configItemRelModify.jsp?rid='+rid+
																	     "****oldRid="+oldRid+
																	     "****modifyId="+modifyId+
																	     "****pid="+pid;

			}else{
				var url=webContext+'/user/configItem/configItemBatchModify/configItemRelCreate.jsp?rid='+rid+
																	     "****oldRid="+oldRid+
																	     "****modifyId="+modifyId+
																	     "****pid="+pid;
			}
			var panel=new Ext.Panel({
				id:pid+"$",
				title:status+'关系',
				closable:true,
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+url,
					text : "页面正在加载中......",
					method : 'post',
					scope : this
				}
			})
			Ext.getCmp('modifyTab').add(panel);
			Ext.getCmp('modifyTab').doLayout();
			Ext.getCmp('modifyTab').activate(panel);
		},this);
		return this.grid; 
	
	},
	saveModify:function(){
		if(!Ext.getCmp('pagePanel_CIBatchModify').form.isValid()){
			Ext.MessageBox.alert("提示",'您填写的信息不完整或不正确.');
			return;
		}
		Ext.getCmp("modifySave").disable();
		var ciModifyPanelParam = Ext.encode(getFormParam("pagePanel_CIBatchModify"));
		var typeId = this.typeId;
		var type = this.type;
		var backUrl = this.backUrl;
		Ext.Ajax.request({
						url : webContext+ '/configItemAction_saveCIBatchModify.action',
						params : {
								ciModifyPanelParam : ciModifyPanelParam,
								typeId : typeId,
								type:type
							},
						success : function(response, options) {
							var dataId  = response.responseText;
							window.location = webContext+ "/user/configItem/configItemBatchModify/configItemBatchModify.jsp?dataId="+dataId+"&typeId="+typeId+"&type="+type+"&backUrl="+backUrl;
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示","保存变更申请失败!",function(){
								Ext.getCmp("modifySubmit").enable();
							});
						}
					}, this);
	},
	applyModify:function(modifyId,type,backUrl){
		Ext.getCmp("modifySubmit").disable();
		Ext.Ajax.request({
			url : webContext+ '/configItemAction_modifySubmitValidate.action',
			params : {
					modifyId:modifyId
			},
			success : function(response, options) {
				var applyName=Ext.getCmp("CIBatchModify$name").getValue();
				var applyNum=Ext.getCmp("CIBatchModify$modifyNo").getValue();
				var submit=function(){
					Ext.Ajax.request({
							url :webContext+'/configModifyWorkflow_apply.action',
							params : {
								dataId : modifyId,
								bzparam : "{applyId:'"+ modifyId+"',dataId:'"+modifyId+"',applyName:'"+applyName+"',applyNum:'"+applyNum+"',modifyType : '"+type+"',applyType: 'mproject',applyTypeName: '配置项变更处理','workflowHistory':'com.zsgj.itil.config.entity.CIBatchModifyAuditHis'}",						
								defname : "ConfigItemModify1278482630373"
							},
							success : function(response, options) {
								var meg = response.responseText;
								if (meg.trim()=="") {
									Ext.Msg.alert("提示", "变更提交成功!", function() {
										window.location = unescape(backUrl);
									});
								} else {
									Ext.Msg.alert("提示", meg, function() {
										Ext.getCmp("modifySubmit").enable();
									});
								}			
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("提示","提交失败!",function(){
									Ext.getCmp("modifySubmit").enable();
								});
							}
						}, this);
				}
				var result=response.responseText;
				if(result.trim()!=""){
					result=Ext.decode(result);
					if(result.error!=undefined){
						new Ext.Window({
							id:"errorWin",
							title:"<font color=red >错误</font>",
							width:350,
							height:200,
							closable :false,
							maximizable:true,
							autoScroll :true,
							modal:true,
							buttonAlign:'center',
							html :result.error,
							buttons:[
							{
								text:'关闭',
								handler:function(){
									Ext.getCmp('errorWin').close();
									Ext.getCmp("modifySubmit").enable();
								}
							}
							]
						}).show();
					}else if(result.confirm!=undefined){
						new Ext.Window({
							id:"alertWin",
							title:"<font color=red >继续提交？</font>",
							width:350,
							height:200,
							closable :false,
							maximizable:true,
							autoScroll :true,
							modal:true,
							buttonAlign:'center',
							html :result.confirm+'<font color=red ><p>如果配置项或关系已存在或已被变更,继续提交视为在已存在或已被变更的配置项或关系的基础之上做变更!</p><p>如果原配置项或关系已被删除,本次申请中状态为变更,继续提交视为新建!</p></font>',
							buttons:[{
								text:'是',
								handler:function(){
									Ext.getCmp('alertWin').close();
									Ext.Ajax.request({
										url : webContext+ '/configItemAction_saveOrUpdateOldCIAndOldRel.action',
										params : {
												modifyId:modifyId
										},
										success : function(response, options) {
											submit();
										},
										failure : function(response, options) {
											Ext.MessageBox.alert("提示","提交失败!",function(){
												Ext.getCmp("modifySubmit").enable();
											});
										}
									})
								}
							},
							{
								text:'否',
								handler:function(){
									Ext.getCmp("modifySubmit").enable();
									Ext.getCmp('alertWin').close();
								}
							}
							]
						}).show();
					}
				}else{
					submit();
				}
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("提示","系统异常!",function(){
						Ext.getCmp("modifySubmit").enable();
					});
			}
		})
	},
	getExtendPanel:function(type,typeId){
		var da = new DataAction();
		var data;
		var title="";
		if(type=='problem'){
			data=da.getSingleFormPanelElementsForEdit("problemDetail_pagepanel",typeId);
			title="问题信息";
		}else{
			data=da.getSingleFormPanelElementsForEdit("panel_SpecialRequireDevConfirm_Input",typeId);
			title="需求信息";
		}
		for(var i=0;i<data.length;i++){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			if(data[i].xtype=="panel"){
				data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
            }			
		}
		data = da.split(data);
		 var extendPanel= new Ext.form.FormPanel({
			autoScroll : true,
			layout : 'table',
			frame:true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : title,
			items:data
 		});
		return extendPanel;
	},
  items : this.items,
	initComponent : function() {
	var applyModify=this.applyModify;
	var modifyId=this.dataId;
	var backUrl=this.backUrl;
	var type=this.type;
	var itemArray=new Array();
	if(this.type!=''){
		itemArray.push(this.getExtendPanel(this.type,this.typeId));
	}
	itemArray.push(this.getFormpagePanel_CIBatchModify());
	var tabPanel=new Ext.TabPanel({
			id:'modifyTab',
			activeTab:0,
			enableTabScroll : true,
			layoutOnTabChange:true,
			width:740,
			deferredRender : false,
			forceLayout:true,
			items : itemArray
	});
	var buttons=new Array();
	if(this.dataId!=''){
		tabPanel.add(this.getModifyConfigGrid(this.dataId));
		tabPanel.add(this.getModifyRelGrid(this.dataId));
		tabPanel.doLayout();
		tabPanel.activate('modifyGrid');
		buttons.push({text : ' 提交变更申请',
			id:"modifySubmit",
			handler : function(){
				applyModify(modifyId,type,backUrl);
			},
			scope : this,
			iconCls : 'submit',
			cls : "x-btn-text-icon"
		});
	}else{
		tabPanel.activate('pagePanel_CIBatchModify');
	}
	
	buttons.push({text :'返回',
			id:'back',
			handler : function(){
				window.location = unescape(backUrl);
			},
			scope : this,
			iconCls : 'back',
			cls : "x-btn-text-icon"
		});
	
	this.buttons=buttons,
    this.items = [tabPanel];
	PageTemplates.superclass.initComponent.call(this);
	}
})