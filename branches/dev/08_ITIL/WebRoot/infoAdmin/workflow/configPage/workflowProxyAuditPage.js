//���޸ķ���������ȡ�������������ʧ��,����ѻ�ȡwindow�ķ��������ó���,��֤����js�о���һ���޸���Ȩ��window -- by wanghao
function getModifyWindow(id) {
		var window = new Ext.Window({
			title : '�޸���Ȩ',
			width : 700,
			height : 300,
			frame : true,
			modal : true,
			layout : 'border',
			buttonAlign : 'center',
			buttons : [{
				id : 'okButton',
				text : 'ȷ��',
				pressed : true,
				handler : function() {
								var cmp = Ext.getCmp('gridId');
								var start = Ext.getCmp('start').getValue();
								var end = Ext.getCmp('end').getValue();
								if(start>end) {
									Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ�����С�ڽ���ʱ��!');
									return;
								}
								if(start==''){
									Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ���Ǳ�����!');
									return;
								}
								if(end == ''){
									Ext.Msg.alert('��ʾ��Ϣ','����ʱ���Ǳ�����!');
									return;
								}
								if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
									Ext.Msg.alert('��ʾ��Ϣ','�û����Ǳ�����!');
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
												var start = gridParam[i].get("start");//����ֻ�ܻ�ȡ���һ�е�ʱ�� 
												var end = gridParam[i].get("end");
									}
									//��󱣴�Ҫ�ж�:����û��޸Ŀ�ʼ����ʱ�䲻����û�ֱ�ӱ���
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
									                       		Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��",function(){	 
									                       			window.close();
									                       			Ext.getCmp("gridPanel").getStore().reload();
									                       		});
									                       }else if(r.flag){
									                       		window.close();
									                       		Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��,��Ϊ������Ĵ���ʱ�����ԭ������ļ�¼���д���ʱ�����ظ�");
									                       }else{
								                                Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ɹ�!",function(){ 
								                                	window.close();
								                                	Ext.getCmp("gridPanel").getStore().reload();
								                                },this);
									                       }	                      
									                       
									 },scope:this});	
				}
			}, {
				text : 'ȡ��',
				pressed : true,
				handler : function() {
					window.close();
				}
			}],
			items : [{
				xtype : 'form',
				// title : '������Ȩ',
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
							html : "��ʼʱ��:&nbsp;",
							cls : 'common-text',
							style : 'width:100;text-align:right'
						}, {
							id : 'start',
							xtype : 'datefield',
							format : 'Y-m-d',
							width : 200
						},{
							html : "����ʱ��:&nbsp;",
							cls : 'common-text',
							style : 'width:100;text-align:right'
						}, {
							id : 'end',
							xtype : 'datefield',
							format : 'Y-m-d',
							width : 200
						},{
							html : "������:&nbsp;",
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
							emptyText : '��ѡ��鿴��',
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
							text : '���',
							iconCls : 'add',
							pressed : true,
							handler : function() {
								var start = Ext.getCmp('start').getValue();
								var end = Ext.getCmp('end').getValue();
								//�޸�ҳ��Ҫ��������
								//1)�����û���޸��κ���Ϣֱ�ӵ����Ҫ��ʾ�޸Ĵ�������Ϣ
								var proxyAuditPerson = Ext.getCmp('proxyAuditPerson').getRawValue()+'';
								if(proxyAuditPerson.indexOf('(')==-1) {
									Ext.Msg.alert('��ʾ��Ϣ','����Ӵ�����');
									return;
								}
								//2)�õ�ԭ���Ŀ�ʼʱ��ͽ���ʱ��
								var gridParam = Ext.getCmp('gridId').getStore().getRange(0,
												Ext.getCmp('gridId').getStore().getCount());
								if(Ext.getCmp('gridId').getStore().getCount()>0) {
									var oldStart = gridParam[0].data.start;
									var oldEnd = gridParam[0].data.end;
								}
								if(start>end) {
									Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ�����С�ڽ���ʱ��!');
									return;
								}
								if(start==''){
									Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ���Ǳ�����!');
									return;
								}
								if(end == ''){
									Ext.Msg.alert('��ʾ��Ϣ','����ʱ���Ǳ�����!');
									return;
								}
								
								if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
									Ext.Msg.alert('��ʾ��Ϣ','�û����Ǳ�����!');
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
										Ext.Msg.alert('��ʾ��Ϣ','���Ѿ��޸��˿�ʼ����ʱ��,�������޸ĵ�ʱ��Ϊ׼���±���!');
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
								//�����ȡ������---------------
								var proxyAuditPerson = Ext.getCmp('proxyAuditPerson').getRawValue();
								//��������ظ��û��Ĺ��˹���
								var gridParam = Ext.getCmp('gridId').getStore().getRange(
										0, Ext.getCmp('gridId').getStore().getCount());
								for (var i = 0; i < gridParam.length; i++) {
									if (gridParam[i].data.proxyAuditPerson == proxyAuditPerson) {
										Ext.Msg.alert('��ʾ��Ϣ', '�벻Ҫ�ظ�����û�!');
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
							header : '������',
							dataIndex : 'proxyAuditPerson',
							sortable : true
						}, {
							header : '��ʼʱ��',
							dataIndex : 'start',
							format : 'Y-m-d',
							sortable : true
						}, {
							header : '����ʱ��',
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
							text : '����',
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
									                       		Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��",function(){	 
									                       			this.store.reload();
									                       		});
									                       }
									                       else{
									                       		
								                                Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ɹ�!",function(){ 			                                	
								                                	this.store.reload();
								                                },this);
									                       }	                      
									                       
									                   },scope:this});			
									}
									
									
									
							}
						},{
							xtype : 'button',
							text : 'ɾ��',
							pressed : true,
							scope : this,
							iconCls : 'delete',
							handler : function() {
								var record = Ext.getCmp('gridId').getSelectionModel()
										.getSelected();
								var records = Ext.getCmp('gridId').getSelectionModel()
										.getSelections();
								if (!record) {
									Ext.Msg.alert("��ʾ", "����ѡ��ɾ������!");
									return;
								}
								Ext.MessageBox.confirm("ȷ��ɾ��", "�Ƿ�ȷ��ɾ����������?",
									function(button) {
										if (button == "yes") {
											for (var i = 0; i < records.length; i++) {
												Ext.getCmp('gridId').getStore().remove(records[i])
											}
											Ext.Msg.alert('��ʾ��Ϣ','ɾ���ɹ�!');
										} else {
										}
									});
							}
						},'<font color=red>�����ֻ���޸Ŀ�ʼ�����ʱ�䣬���õ����Ӱ�ť��ȷ���󽫰��������޸ĵ�ʱ��Ϊ׼����</font>']
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
	          	   		Ext.Msg.alert('��ʾ��Ϣ','���ݻ�ȡʧ��');
	           		}	 
	           }, 
	           scope:this
		});
		
		return window;
	
}

WorkflowProxyAuditPagePanel = Ext.extend(Ext.Panel, {
	id : "workflowProxyAuditPagePanel",
	// title : "��ͬ��������",
	layout : 'fit',
	items : this.items,
	scope : this,
	
	//����ҳ�������gridPanel
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
					header : '�Զ����',
					dataIndex : 'id',
					hidden : true,
					sortable : true
				}, {
					header : '����������',
					dataIndex : 'userName',
					sortable : true
				}, {
					header : '����������',
					dataIndex : 'proxyName',
					sortable : true
				}, {
					header : '����ʼʱ��',
					dataIndex : 'proxyStartDate',
					sortable : true
				},{
					header : '�������ʱ��',
					dataIndex : 'proxyEndDate',
					sortable : true
				}]);
		var bbar = new Ext.PagingToolbar({
					pageSize : 10,
					store : store,
					displayInfo : true,
					displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
					emptyMsg : '����ʾ����'
				});
				
		var tbar = new Ext.Toolbar([{
					text : '������Ȩ',
					pressed : true,
					iconCls : 'add',
					handler : function() {
						var window = new Ext.Window({
						title : '������Ȩ',
						width : 700,
						height : 300,
						frame : true,
						modal : true,
						layout : 'border',
						buttonAlign : 'center',
						buttons : [{
							id : 'okButton',
							text : 'ȷ��',
							pressed : true,
							handler : function() {
											var cmp = Ext.getCmp('gridId');
											var start = Ext.getCmp('start').getValue();
											var end = Ext.getCmp('end').getValue();
											if(start>end) {
												Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ�����С�ڽ���ʱ��!');
												return;
											}
											if(start==''){
												Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ���Ǳ�����!');
												return;
											}
											if(end == ''){
												Ext.Msg.alert('��ʾ��Ϣ','����ʱ���Ǳ�����!');
												return;
											}
											if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
												Ext.Msg.alert('��ʾ��Ϣ','�û����Ǳ�����!');
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
												                       		Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��",function(){	 
												                       			window.close();
												                       			Ext.getCmp("gridPanel").getStore().reload();
												                       		});
												                       }else if(r.flag){
												                       		window.close();
									                       					Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��,��Ϊ������Ĵ���ʱ�����ԭ������ļ�¼���д���ʱ�����ظ�");
									                       			   }else{
											                                Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ɹ�!",function(){ 	
											                                	window.close();
											                                	Ext.getCmp("gridPanel").getStore().reload();
											                                },this);
												                       }	                      
												                       
												 },scope:this});	
							}
						}, {
							text : 'ȡ��',
							pressed : true,
							handler : function() {
								window.close();
							}
						}],
						items : [{
							xtype : 'form',
							// title : '������Ȩ',
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
										html : "��ʼʱ��:&nbsp;",
										cls : 'common-text',
										style : 'width:100;text-align:right'
									}, {
										id : 'start',
										xtype : 'datefield',
										format : 'Y-m-d',
										width : 200
									},{
										html : "����ʱ��:&nbsp;",
										cls : 'common-text',
										style : 'width:100;text-align:right'
									}, {
										id : 'end',
										xtype : 'datefield',
										format : 'Y-m-d',
										width : 200
									},{
										html : "������:&nbsp;",
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
										emptyText : '��ѡ��鿴��',
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
															realName : param,
															start : 0
														}
//														callback : function(r, options, success) {
//															����userName.validate();
//														}
													});
													return true;
											}
												}
									} ,{
										xtype : 'button',
										text : '���',
										pressed : true,
										iconCls : 'add',
										handler : function() {
											var start = Ext.getCmp('start').getValue();
											var end = Ext.getCmp('end').getValue();
											if(start>end) {
												Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ�����С�ڽ���ʱ��!');
												return;
											}
											if(start==''){
												Ext.Msg.alert('��ʾ��Ϣ','��ʼʱ���Ǳ�����!');
												return;
											}
											if(end == ''){
												Ext.Msg.alert('��ʾ��Ϣ','����ʱ���Ǳ�����!');
												return;
											}
											if( Ext.getCmp('proxyAuditPerson').getRawValue()==''){
												Ext.Msg.alert('��ʾ��Ϣ','�û����Ǳ�����!');
												return;
											}
											if(start!= '') {
												start = start.format('Y-m-d');
											}
											if(end != '') {
												end = end.format('Y-m-d');
											}
											//�����ȡ������---------------
											var proxyAuditPerson = Ext.getCmp('proxyAuditPerson').getRawValue();
											//��������ظ��û��Ĺ��˹���
											var gridParam = Ext.getCmp('gridId').getStore().getRange(
													0, Ext.getCmp('gridId').getStore().getCount());
											for (var i = 0; i < gridParam.length; i++) {
												if (gridParam[i].data.proxyAuditPerson == proxyAuditPerson) {
													Ext.Msg.alert('��ʾ��Ϣ', '�벻Ҫ�ظ�����û�!');
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
										header : '������',
										dataIndex : 'proxyAuditPerson',
										sortable : true
									}, {
										header : '��ʼʱ��',
										dataIndex : 'start',
										format : 'Y-m-d',
										sortable : true
									}, {
										header : '����ʱ��',
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
										text : '����',
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
												                       		Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ʧ��",function(){	 
												                       			this.store.reload();
												                       		});
												                       }
												                       else{
												                       		
											                                Ext.Msg.alert("��ʾ��Ϣ","���ݱ���ɹ�!",function(){ 			                                	
											                                	this.store.reload();
											                                },this);
												                       }	                      
												                       
												                   },scope:this});			
												}
												
												
												
										}
									},{
										xtype : 'button',
										text : 'ɾ��',
										pressed : true,
										scope : this,
										iconCls : 'delete',
										handler : function() {
											var record = Ext.getCmp('gridId').getSelectionModel()
													.getSelected();
											var records = Ext.getCmp('gridId').getSelectionModel()
													.getSelections();
											if (!record) {
												Ext.Msg.alert("��ʾ", "����ѡ��ɾ������!");
												return;
											}
											Ext.MessageBox.confirm("ȷ��ɾ��", "�Ƿ�ȷ��ɾ����������?",
												function(button) {
													if (button == "yes") {
														for (var i = 0; i < records.length; i++) {
															Ext.getCmp('gridId').getStore().remove(records[i])
														}
														Ext.Msg.alert('��ʾ��Ϣ','ɾ���ɹ�!');
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
					text : '�޸���Ȩ',
					pressed : true,
					iconCls : 'edit',
					handler : function() {
						var record =Ext.getCmp('gridPanel').getSelectionModel().getSelected();
						var records = Ext.getCmp('gridPanel').getSelectionModel().getSelections();
						if (!record) {
							Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
							return;
						}
						
						var id = record.get("id");
						var window = getModifyWindow(id);
						window.show();
					}
				}, {
					text : 'ɾ����Ȩ',
					pressed : true,
					iconCls : 'delete',
					handler : function() {
						var record =Ext.getCmp('gridPanel').getSelectionModel().getSelected();
						var records = Ext.getCmp('gridPanel').getSelectionModel().getSelections();
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
				                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){	 
				                       			Ext.getCmp("gridPanel").getStore().reload();
				                       		});
				                       }
				                       else{
				                       		
			                                Ext.Msg.alert("��ʾ��Ϣ","����ɾ���ɹ�!",function(){ 
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
				},'<font color=red>��˫���޸���Ȩ��Ϣ��������Ȩ���ڱ�ϵͳ��Ч��</font>'])
				
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
