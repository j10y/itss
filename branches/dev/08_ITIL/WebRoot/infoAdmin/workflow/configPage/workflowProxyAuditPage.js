//把修改方法单独抽取出来放在组件中失败,这里把获取window的方法单独拿出来,保证整个js中就这一个修改授权的window -- by wanghao
function getModifyWindow(id) {
		var window = new Ext.Window({
			title : '修改授权',
			width : 700,
			height : 300,
			frame : true,
			modal : true,
			layout : 'border',
			buttonAlign : 'center',
			buttons : [{
				id : 'okButton',
				text : '确定',
				pressed : true,
				handler : function() {
								var cmp = Ext.getCmp('gridId');
								var start = Ext.getCmp('start').getValue();
								var end = Ext.getCmp('end').getValue();
								if(start>end) {
									Ext.Msg.alert('提示信息','开始时间必须小于结束时间!');
									return;
								}
								if(start==''){
									Ext.Msg.alert('提示信息','开始时间是必填项!');
									return;
								}
								if(end == ''){
									Ext.Msg.alert('提示信息','结束时间是必填项!');
									return;
								}
								if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
									Ext.Msg.alert('提示信息','用户名是必填项!');
									return;
								}
					
									var gridParam = cmp.getStore().getRange(0,cmp.getStore().getCount());
									gridParam = [];
									var count = cmp.getStore().getCount();
									var data = "";					
									var proxyAuditPerson = "";
									var start = "";
									var end = "";
								    for (var i = 0; i < count; i++) {
												gridParam[i] = cmp.getStore().getAt(i);	
												proxyAuditPerson = proxyAuditPerson +gridParam[i].get("proxyAuditPerson");
												proxyAuditPerson = proxyAuditPerson + ',';
												var start = gridParam[i].get("start");//这样只能获取最后一行的时间 
												var end = gridParam[i].get("end");
									}
									//最后保存要判断:如果用户修改开始结束时间不添加用户直接保存
									var oldStart = Ext.getCmp('start').getValue();
									var oldEnd = Ext.getCmp('end').getValue();
									if(oldStart != start) {
										start = oldStart;
									}
									if(oldEnd != end) {
										end = oldEnd;
									}
									start = start.format('Y-m-d');
									end = end.format('Y-m-d');
									Ext.Ajax.request({
												        url: webContext+ '/workflow/preassign.do?methodCall=modifyUserInfoWorkmates&modifyPreAssignId='+id,
												        params : {
												        	id : data,
															proxyAuditPerson :unicode(proxyAuditPerson),
															start : start,
															end : end
														},
												        success:function(response){
									                       var r =Ext.decode(response.responseText);				                       
									                       if(!r.success){
									                       		Ext.Msg.alert("提示信息","数据保存失败",function(){	 
									                       			window.close();
									                       			Ext.getCmp("gridPanel").getStore().reload();
									                       		});
									                       }else if(r.flag){
									                       		window.close();
									                       		Ext.Msg.alert("提示信息","数据保存失败,因为您输入的待审时间和您原来输入的记录中有待审时间有重复");
									                       }else{
								                                Ext.Msg.alert("提示信息","数据保存成功!",function(){ 
								                                	window.close();
								                                	Ext.getCmp("gridPanel").getStore().reload();
								                                },this);
									                       }	                      
									                       
									 },scope:this});	
				}
			}, {
				text : '取消',
				pressed : true,
				handler : function() {
					window.close();
				}
			}],
			items : [{
				xtype : 'form',
				// title : '增加授权',
				region : "north",
				layout : 'table',
				frame : true,
				collapsible : true,
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:8px 0px 8px 0px'
				},
				items : [{
							html : "开始时间:&nbsp;",
							cls : 'common-text',
							style : 'width:100;text-align:right'
						}, {
							id : 'start',
							xtype : 'datefield',
							format : 'Y-m-d',
							width : 200
						},{
							html : "结束时间:&nbsp;",
							cls : 'common-text',
							style : 'width:100;text-align:right'
						}, {
							id : 'end',
							xtype : 'datefield',
							format : 'Y-m-d',
							width : 200
						},{
							html : "代审人:&nbsp;",
							cls : 'common-text',
							style : 'width:100;text-align:right'
						}, {
							id : 'proxyAuditPerson',
							xtype : 'combo',
							hiddenName : 'auditPerson',
							displayField : 'realName',
							valueField : 'id',
							triggerAction : 'all',
							store : new Ext.data.JsonStore({
										url :  webContext+'/workflow/preassign.do?methodCall=getUserInfoWorkmates'+'&displayField=realName',
										fields : ['id', 'realName'],
										totalProperty : 'rowCount',
										root : 'data',
											sortInfo : {
											field : "id",
											direction : "ASC"
										},
										listeners : {
											beforeload : function(store, opt) {
												if (opt.params['realName'] == undefined) {
													opt.params['realName'] = unicode(Ext.getCmp("proxyAuditPerson").defaultParam);
												}else{
													opt.params['realName'] = unicode(opt.params['realName']);
												}
											}
										}
							}),
							width : 200,
							pageSize : 10,
							emptyText : '请选择查看人',
							name : "realName",
							mode : 'remote',
							forceSelection : true,
							editable : true,
							triggerAction : 'all',
							lazyRender : true,
							typeAhead : false,
							allowBlank : false,
							selectOnFocus : true,
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
												realName : param,
												start : 0
											},
											callback : function(r, options, success) {
												userName.validate();
											}
										});
										return true;
								}
									}
						} ,{
							xtype : 'button',
							text : '添加',
							iconCls : 'add',
							pressed : true,
							handler : function() {
								var start = Ext.getCmp('start').getValue();
								var end = Ext.getCmp('end').getValue();
								//修改页面要加上限制
								//1)如果是没有修改任何信息直接点添加要提示修改待审人信息
								var proxyAuditPerson = Ext.getCmp('proxyAuditPerson').getRawValue()+'';
								if(proxyAuditPerson.indexOf('(')==-1) {
									Ext.Msg.alert('提示信息','请添加待审人');
									return;
								}
								//2)得到原来的开始时间和结束时间
								var gridParam = Ext.getCmp('gridId').getStore().getRange(0,
												Ext.getCmp('gridId').getStore().getCount());
								if(Ext.getCmp('gridId').getStore().getCount()>0) {
									var oldStart = gridParam[0].data.start;
									var oldEnd = gridParam[0].data.end;
								}
								if(start>end) {
									Ext.Msg.alert('提示信息','开始时间必须小于结束时间!');
									return;
								}
								if(start==''){
									Ext.Msg.alert('提示信息','开始时间是必填项!');
									return;
								}
								if(end == ''){
									Ext.Msg.alert('提示信息','结束时间是必填项!');
									return;
								}
								
								if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
									Ext.Msg.alert('提示信息','用户名是必填项!');
									return;
								}
								
								if(start!= '') {
									start = start.format('Y-m-d');
								}
								if(end != '') {
									end = end.format('Y-m-d');
								}
								if(oldStart != undefined && oldEnd != undefined) {
									if(start!=oldStart || end != oldEnd) {
										Ext.Msg.alert('提示信息','您已经修改了开始结束时间,将以您修改的时间为准重新保存!');
									}
								}
								for(i=0 ; i<gridParam.length; i++) {
									if(oldStart != start) {
										gridParam[i].set('start',start);
										gridParam[i].commit();
									}
									if(oldEnd != end) {
										gridParam[i].set('end',end);
										gridParam[i].commit();
									}
								}
								//这里获取代审人---------------
								var proxyAuditPerson = Ext.getCmp('proxyAuditPerson').getRawValue();
								//这里加上重复用户的过滤功能
								var gridParam = Ext.getCmp('gridId').getStore().getRange(
										0, Ext.getCmp('gridId').getStore().getCount());
								for (var i = 0; i < gridParam.length; i++) {
									if (gridParam[i].data.proxyAuditPerson == proxyAuditPerson) {
										Ext.Msg.alert('提示信息', '请不要重复添加用户!');
										return;
									}
								}
								var store = Ext.getCmp('gridId').store
								if (store.recordType) {
									var rec = new store.recordType({
												newRecord : true
											});
									rec.fields.each(function(f) {
										rec.data['start'] = start;
										rec.data['end'] = end;
										rec.data['proxyAuditPerson'] = proxyAuditPerson;
									});
									rec.commit();
									store.add(rec);
									record = rec;
									return rec;
								}
							}
						}]
			}, {
				id : 'gridId',
				xtype : 'grid',
				region : 'center',
				sm : new Ext.grid.CheckboxSelectionModel(),
				cm : new Ext.grid.ColumnModel([
						new Ext.grid.CheckboxSelectionModel(), {
							header : '代审人',
							dataIndex : 'proxyAuditPerson',
							sortable : true
						}, {
							header : '开始时间',
							dataIndex : 'start',
							format : 'Y-m-d',
							sortable : true
						}, {
							header : '结束时间',
							dataIndex : 'end',
							format : 'Y-m-d',
							sortable : true
						}]),
				store : new Ext.data.JsonStore({
					url: webContext+ '/workflow/preassign.do?methodCall=getTaskPreAssignById&id='+id,
					 root:'data',
					autoLoad : true,
					fields : ['proxyAuditPerson', 'start', 'end']
				}) ,
				tbar : [{
							xtype : 'button',
							text : '保存',
							pressed : true,
							scope : this,
							hidden:true,
							iconCls : 'add',
							handler : function() {
								
									var cmp = Ext.getCmp('gridId');
									var gridParam = cmp.getStore().getRange(0,cmp.getStore().getCount());
									gridParam = [];
									var count = cmp.getStore().getCount();
									var data = "";					
									
									var proxyPerson = gridParam[i].get("proxyAuditPerson");
				                    var start = gridParam[i].get("start");
				                    var end = gridParam[i].get("end");
				                    var request = {
						                userName: unicode(proxyPerson),
						                start: start,
						                end:end
					           		};
					           		alert(proxyPerson+"==="+start);
					           		alert(unicode(proxyPerson));
								    for (var i = 0; i < count; i++) {
												gridParam[i] = cmp.getStore().getAt(i);	
												Ext.Ajax.request({
												        url: webContext+ '/workflow/preassign.do?methodCall=saveUserInfoWorkmates',
												         method: 'POST',
												         params:request,
//															        params : {
//															        	id : data,
//																		proxyPerson : unicode(gridParam[i].get("proxyAuditPerson")),
//																		start : gridParam[i].get("start"),
//																		end : gridParam[i].get("end")
//																	},
												        success:function(response){
									                       var r =Ext.decode(response.responseText);				                       
									                       if(!r.success){
									                       		Ext.Msg.alert("提示信息","数据保存失败",function(){	 
									                       			this.store.reload();
									                       		});
									                       }
									                       else{
									                       		
								                                Ext.Msg.alert("提示信息","数据保存成功!",function(){ 			                                	
								                                	this.store.reload();
								                                },this);
									                       }	                      
									                       
									                   },scope:this});			
									}
									
									
									
							}
						},{
							xtype : 'button',
							text : '删除',
							pressed : true,
							scope : this,
							iconCls : 'delete',
							handler : function() {
								var record = Ext.getCmp('gridId').getSelectionModel()
										.getSelected();
								var records = Ext.getCmp('gridId').getSelectionModel()
										.getSelections();
								if (!record) {
									Ext.Msg.alert("提示", "请先选择删除的行!");
									return;
								}
								Ext.MessageBox.confirm("确认删除", "是否确认删除该审批人?",
									function(button) {
										if (button == "yes") {
											for (var i = 0; i < records.length; i++) {
												Ext.getCmp('gridId').getStore().remove(records[i])
											}
											Ext.Msg.alert('提示信息','删除成功!');
										} else {
										}
									});
							}
						},'<font color=red>【如果只需修改开始或结束时间，不用点击添加按钮，确定后将按照您新修改的时间为准。】</font>']
			}]
		});
		
		Ext.Ajax.request({						
	           url: webContext+ '/workflow/preassign.do?methodCall=getTaskPreAssignFormById',
	           params:{
	               id:id			                           
	           },
	           mothod:'POST',
	           success:function(response){
	               	var r=Ext.decode(response.responseText);
	               	if(r.success){
	               		Ext.getCmp('start').setValue(r.start);
	               		Ext.getCmp('end').setValue(r.end);
	               		Ext.getCmp('proxyAuditPerson').setValue(r.proxyAuditPerson);
	               	}
	          	   	else{
	          	   		Ext.Msg.alert('提示信息','数据获取失败');
	           		}	 
	           }, 
	           scope:this
		});
		
		return window;
	
}

WorkflowProxyAuditPagePanel = Ext.extend(Ext.Panel, {
	id : "workflowProxyAuditPagePanel",
	// title : "合同申请审批",
	layout : 'fit',
	items : this.items,
	scope : this,
	
	//代审页面主面板gridPanel
	getGridPanel : function() {
		var store = new Ext.data.JsonStore({
			id:'gridStore',
			url: webContext+ '/assignAction_showMyWorkmates.action',
			autoLoad : true,
			 root:'data',
			//data : [{id:1,workFlowName:2,taskName:3,auditPerson:4,proxyAuditPerson:5}],
			fields : ['id', 'userName', 'proxyName', 'proxyStartDate','proxyEndDate'],
			sortInfo: {field: "id", direction: "ASC"}
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm, {
					header : '自动编号',
					dataIndex : 'id',
					hidden : true,
					sortable : true
				}, {
					header : '申请人名称',
					dataIndex : 'userName',
					sortable : true
				}, {
					header : '待审人名称',
					dataIndex : 'proxyName',
					sortable : true
				}, {
					header : '待审开始时间',
					dataIndex : 'proxyStartDate',
					sortable : true
				},{
					header : '待审结束时间',
					dataIndex : 'proxyEndDate',
					sortable : true
				}]);
		var bbar = new Ext.PagingToolbar({
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
					emptyMsg : '无显示数据'
				});
				
		var tbar = new Ext.Toolbar([{
					text : '新增授权',
					pressed : true,
					iconCls : 'add',
					handler : function() {
						var window = new Ext.Window({
						title : '新增授权',
						width : 700,
						height : 300,
						frame : true,
						modal : true,
						layout : 'border',
						buttonAlign : 'center',
						buttons : [{
							id : 'okButton',
							text : '确定',
							pressed : true,
							handler : function() {
											var cmp = Ext.getCmp('gridId');
											var start = Ext.getCmp('start').getValue();
											var end = Ext.getCmp('end').getValue();
											if(start>end) {
												Ext.Msg.alert('提示信息','开始时间必须小于结束时间!');
												return;
											}
											if(start==''){
												Ext.Msg.alert('提示信息','开始时间是必填项!');
												return;
											}
											if(end == ''){
												Ext.Msg.alert('提示信息','结束时间是必填项!');
												return;
											}
											if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
												Ext.Msg.alert('提示信息','用户名是必填项!');
												return;
											}
								
												var gridParam = cmp.getStore().getRange(0,cmp.getStore().getCount());
												gridParam = [];
												var count = cmp.getStore().getCount();
												var data = "";					
												var proxyAuditPerson = "";
												var start = "";
												var end = "";
											    for (var i = 0; i < count; i++) {
															gridParam[i] = cmp.getStore().getAt(i);	
															proxyAuditPerson = proxyAuditPerson +gridParam[i].get("proxyAuditPerson");
															proxyAuditPerson = proxyAuditPerson + ',';
															var start = gridParam[i].get("start");
															var end = gridParam[i].get("end");
												}
												Ext.Ajax.request({
															        url: webContext+ '/workflow/preassign.do?methodCall=saveUserInfoWorkmates',
															        params : {
															        	id : data,
																		proxyAuditPerson :unicode(proxyAuditPerson),
																		start : start,
																		end : end
																	},
															        success:function(response){
												                       var r =Ext.decode(response.responseText);				                       
												                       if(!r.success){
												                       		Ext.Msg.alert("提示信息","数据保存失败",function(){	 
												                       			window.close();
												                       			Ext.getCmp("gridPanel").getStore().reload();
												                       		});
												                       }else if(r.flag){
												                       		window.close();
									                       					Ext.Msg.alert("提示信息","数据保存失败,因为您输入的待审时间和您原来输入的记录中有待审时间有重复");
									                       			   }else{
											                                Ext.Msg.alert("提示信息","数据保存成功!",function(){ 	
											                                	window.close();
											                                	Ext.getCmp("gridPanel").getStore().reload();
											                                },this);
												                       }	                      
												                       
												 },scope:this});	
							}
						}, {
							text : '取消',
							pressed : true,
							handler : function() {
								window.close();
							}
						}],
						items : [{
							xtype : 'form',
							// title : '增加授权',
							region : "north",
							height: 100,
							layout : 'table',
							frame : true,
							collapsible : true,
							layoutConfig : {
								columns : 4
							},
							defaults : {
								bodyStyle : 'padding:8px 0px 8px 0px'
							},
							items : [{
										html : "开始时间:&nbsp;",
										cls : 'common-text',
										style : 'width:100;text-align:right'
									}, {
										id : 'start',
										xtype : 'datefield',
										format : 'Y-m-d',
										width : 200
									},{
										html : "结束时间:&nbsp;",
										cls : 'common-text',
										style : 'width:100;text-align:right'
									}, {
										id : 'end',
										xtype : 'datefield',
										format : 'Y-m-d',
										width : 200
									},{
										html : "代审人:&nbsp;",
										cls : 'common-text',
										style : 'width:100;text-align:right'
									}, {
										id : 'proxyAuditPerson',
										xtype : 'combo',
										hiddenName : 'auditPerson',
										displayField : 'realName',
										valueField : 'id',
										triggerAction : 'all',
										store : new Ext.data.JsonStore({
													url :  webContext+'/workflow/preassign.do?methodCall=getUserInfoWorkmates'+'&displayField=realName',
													fields : ['id', 'realName'],
													totalProperty : 'rowCount',
													root : 'data',
														sortInfo : {
														field : "id",
														direction : "ASC"
													},
													listeners : {
														beforeload : function(store, opt) {
															if (opt.params['realName'] == undefined) {
																opt.params['realName'] = unicode(Ext.getCmp("proxyAuditPerson").defaultParam);
															}else{
																opt.params['realName'] = unicode(opt.params['realName']);
															}
														}
													}
										}),
										width : 200,
										pageSize : 10,
										emptyText : '请选择查看人',
										name : "realName",
										mode : 'remote',
										forceSelection : true,
										editable : true,
										triggerAction : 'all',
										lazyRender : true,
										typeAhead : false,
										allowBlank : false,
										selectOnFocus : true,
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
															realName : param,
															start : 0
														}
//														callback : function(r, options, success) {
//															、、userName.validate();
//														}
													});
													return true;
											}
												}
									} ,{
										xtype : 'button',
										text : '添加',
										pressed : true,
										iconCls : 'add',
										handler : function() {
											var start = Ext.getCmp('start').getValue();
											var end = Ext.getCmp('end').getValue();
											if(start>end) {
												Ext.Msg.alert('提示信息','开始时间必须小于结束时间!');
												return;
											}
											if(start==''){
												Ext.Msg.alert('提示信息','开始时间是必填项!');
												return;
											}
											if(end == ''){
												Ext.Msg.alert('提示信息','结束时间是必填项!');
												return;
											}
											if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
												Ext.Msg.alert('提示信息','用户名是必填项!');
												return;
											}
											if(start!= '') {
												start = start.format('Y-m-d');
											}
											if(end != '') {
												end = end.format('Y-m-d');
											}
											//这里获取代审人---------------
											var proxyAuditPerson = Ext.getCmp('proxyAuditPerson').getRawValue();
											//这里加上重复用户的过滤功能
											var gridParam = Ext.getCmp('gridId').getStore().getRange(
													0, Ext.getCmp('gridId').getStore().getCount());
											for (var i = 0; i < gridParam.length; i++) {
												if (gridParam[i].data.proxyAuditPerson == proxyAuditPerson) {
													Ext.Msg.alert('提示信息', '请不要重复添加用户!');
													return;
												}
											}
											var store = Ext.getCmp('gridId').store
											if (store.recordType) {
												var rec = new store.recordType({
															newRecord : true
														});
												rec.fields.each(function(f) {
													rec.data['start'] = start;
													rec.data['end'] = end;
													rec.data['proxyAuditPerson'] = proxyAuditPerson;
												});
												rec.commit();
												store.add(rec);
												record = rec;
												return rec;
											}
										}
									}]
						}, {
							id : 'gridId',
							xtype : 'grid',
							region : 'center',
							sm : new Ext.grid.CheckboxSelectionModel(),
							cm : new Ext.grid.ColumnModel([
									new Ext.grid.CheckboxSelectionModel(), {
										header : '代审人',
										dataIndex : 'proxyAuditPerson',
										sortable : true
									}, {
										header : '开始时间',
										dataIndex : 'start',
										format : 'Y-m-d',
										sortable : true
									}, {
										header : '结束时间',
										dataIndex : 'end',
										format : 'Y-m-d',
										sortable : true
									}]),
							store : new Ext.data.JsonStore({
								url: webContext+ '/configUnit_showNodeTimer.action',
								 root:'data',
								sortInfo: {field: "id", direction: "ASC"},
								fields : ['proxyAuditPerson', 'start', 'end']
							}) ,
							tbar : [{
										xtype : 'button',
										text : '保存',
										pressed : true,
										scope : this,
										hidden:true,
										iconCls : 'add',
										handler : function() {
											
												var cmp = Ext.getCmp('gridId');
												var gridParam = cmp.getStore().getRange(0,cmp.getStore().getCount());
												gridParam = [];
												var count = cmp.getStore().getCount();
												var data = "";					
												
												var proxyPerson = gridParam[i].get("proxyAuditPerson");
							                    var start = gridParam[i].get("start");
							                    var end = gridParam[i].get("end");
							                    var request = {
									                userName: unicode(proxyPerson),
									                start: start,
									                end:end
								           		};
								           		alert(proxyPerson+"==="+start);
								           		alert(unicode(proxyPerson));
											    for (var i = 0; i < count; i++) {
															gridParam[i] = cmp.getStore().getAt(i);	
															Ext.Ajax.request({
															        url: webContext+ '/workflow/preassign.do?methodCall=saveUserInfoWorkmates',
															         method: 'POST',
															         params:request,
//															        params : {
//															        	id : data,
//																		proxyPerson : unicode(gridParam[i].get("proxyAuditPerson")),
//																		start : gridParam[i].get("start"),
//																		end : gridParam[i].get("end")
//																	},
															        success:function(response){
												                       var r =Ext.decode(response.responseText);				                       
												                       if(!r.success){
												                       		Ext.Msg.alert("提示信息","数据保存失败",function(){	 
												                       			this.store.reload();
												                       		});
												                       }
												                       else{
												                       		
											                                Ext.Msg.alert("提示信息","数据保存成功!",function(){ 			                                	
											                                	this.store.reload();
											                                },this);
												                       }	                      
												                       
												                   },scope:this});			
												}
												
												
												
										}
									},{
										xtype : 'button',
										text : '删除',
										pressed : true,
										scope : this,
										iconCls : 'delete',
										handler : function() {
											var record = Ext.getCmp('gridId').getSelectionModel()
													.getSelected();
											var records = Ext.getCmp('gridId').getSelectionModel()
													.getSelections();
											if (!record) {
												Ext.Msg.alert("提示", "请先选择删除的行!");
												return;
											}
											Ext.MessageBox.confirm("确认删除", "是否确认删除该审批人?",
												function(button) {
													if (button == "yes") {
														for (var i = 0; i < records.length; i++) {
															Ext.getCmp('gridId').getStore().remove(records[i])
														}
														Ext.Msg.alert('提示信息','删除成功!');
													} else {
													}
												});
										}
									}]
						}]
					});
					window.show();
					
					}
				}, {
					text : '修改授权',
					pressed : true,
					iconCls : 'edit',
					handler : function() {
						var record =Ext.getCmp('gridPanel').getSelectionModel().getSelected();
						var records = Ext.getCmp('gridPanel').getSelectionModel().getSelections();
						if (!record) {
							Ext.Msg.alert("提示", "请先选择要修改的行!");
							return;
						}
						
						var id = record.get("id");
						var window = getModifyWindow(id);
						window.show();
					}
				}, {
					text : '删除授权',
					pressed : true,
					iconCls : 'delete',
					handler : function() {
						var record =Ext.getCmp('gridPanel').getSelectionModel().getSelected();
						var records = Ext.getCmp('gridPanel').getSelectionModel().getSelections();
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
										Ext.getCmp("gridPanel").getStore().remove(records[i]);
									}										
									}								
								Ext.Ajax.request({						
					                   url: webContext+ '/workflow/preassign.do?methodCall=removeUserInfoWorkmates',
				                       params:{
				                           'ids':this.removeIds				                           
				                       },
				                       mothod:'POST',
				                       success:function(response){
				                           var r=Ext.decode(response.responseText);
				                           if(!r.success){
				                       		Ext.Msg.alert("提示信息","数据删除失败",function(){	 
				                       			Ext.getCmp("gridPanel").getStore().reload();
				                       		});
				                       }
				                       else{
				                       		
			                                Ext.Msg.alert("提示信息","数据删除成功!",function(){ 
			                                	Ext.getCmp("gridPanel").getStore().reload();
			                                },this);
				                       }	 
				                       }, 
				                       scope:this
		                   		});
									}
								}, this)
							}
					}
				},'<font color=red>【双击修改授权信息】【此授权仅在本系统有效】</font>'])
				
		var gridPanel = new Ext.grid.GridPanel({
					id : 'gridPanel',
					width : 'auto',
					height : 'auto',
					store : store,
					sm : sm,
					cm : cm,
//					autoLoad : true,
					tbar : tbar,
					bbar : bbar,
					listeners : {
						'rowdblclick' : function() {
							var record =Ext.getCmp('gridPanel').getSelectionModel().getSelected();
							var id = record.get("id");
							var window = getModifyWindow(id);
							window.show();
						}
					}
				});
//		gridPanel.getStore().load({
//			params : ''
//		});
		return gridPanel;
	},

	initComponent : function() {
		this.items = this.getGridPanel();
		WorkflowProxyAuditPagePanel.superclass.initComponent.call(this);
	}
});
