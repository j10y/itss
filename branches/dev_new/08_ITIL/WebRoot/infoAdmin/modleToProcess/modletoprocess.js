ModelProcessPanel = Ext.extend(Ext.Panel, {
	id : "processPanel",
	title : 'ģ�����̹�����Ϣ',
	//closable : true,
	width : 1020,
	height : 400,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	frame : true,
	items : this.items,
	getServiceItemProcessPanel : function(tempId) {
		this.serviceItemProcessPanel = new Ext.form.FormPanel({
			id : 'serviceItemProcessPanel',
			height : '300',
			width : 700,
			frame : true,
			collapsible : true,
			buttonAlign : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			reader : new Ext.data.JsonReader({
				root : 'form',
				successProperty : 'success'
			}, [{
				name : 'ModleToProcess$id',
				mapping : 'ModleToProcess$id'
			},{
				name : 'ModleToProcess$processInfo',
				mapping : 'ModleToProcess$processInfo'
			}, {
				name : 'ModleToProcess$processStatusType',
				mapping : 'ModleToProcess$processStatusType'
			}, {
				name : 'ModleToProcess$modleType',
				mapping : 'ModleToProcess$modleType'
			}]),
			// title : "��������������",
			items : [{
				xtype : 'fieldset',
				title : '<font color=red>������Ϣ</font>',
				layout : 'column',
				anchor : '100%',
				labelwidth : 50,
				autoHeight : true,
				items : [
							{
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								xtype : 'combo',
								hiddenName : 'ModleToProcess$processInfo',
								id : 'ModleToProcess$processInfoCombo',
								width : 200,
								style : '',
								fieldLabel : '����',
								colspan : 0,
								rowspan : 0,
								lazyRender : true,
								displayField : 'processName',
								valueField : 'id',
								emptyText : '��ѡ��...',
								allowBlank : true,
								typeAhead : true,
								name : 'ModleToProcess$processInfo',
								triggerAction : 'all',
								minChars : 50,
								queryDelay : 700,
								store : new Ext.data.JsonStore({
									url : webContext+ '/sciProcess_getProcessComboData.action',
									fields : ['id', 'processName'],
									listeners : {
										beforeload : function(store, opt) {
											if (opt.params['ModleToProcess$processInfo'] == undefined) {
												opt.params['ModleToProcess$processInfo'] = Ext.getCmp('ModleToProcess$processInfoCombo').defaultParam;
											}
										}
									},
									totalProperty : 'rowCount',
									root : 'data',
									id : 'id'
								}),
								pageSize : 10,
								listeners : {
									'beforequery' : function(queryEvent) {
										var param = queryEvent.combo.getRawValue();
										this.defaultParam = param;
										if (queryEvent.query == '') {
											param = '';
										}
										this.store.load({
											params : {
												processName : param,
												start : 0
											}
										});
										return true;
									}
								},
								initComponent : function() {
									this.store.load({
										params : {
											id : Ext.getCmp('ModleToProcess$processInfoCombo').getValue(),
											start : 0
										},
										callback : function(r, options, success) {
											Ext.getCmp('ModleToProcess$processInfoCombo').setValue(Ext.getCmp('ModleToProcess$processInfoCombo').getValue());
										}
									});
								}
							})]
						}, 
						{
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								name : "ModleToProcess$processStatusType",
								allowBlank : false,
								fieldLabel : "��������",
								id : 'ModleToProcess$processStatusTypeCombo',
								width : 200,
								mode : 'local',
								defaultParam : '',
								allowBlank : false,
								hiddenName : 'ModleToProcess$processStatusType',
								xtype : 'combo',
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								typeAhead : true,
								forceSelection : true,
								emptyText : '��ѡ��',
								store : new Ext.data.SimpleStore({
									fields : ['id', 'name'],
									data : [['0', '��������'], ['1', '�������'],['2', 'ɾ������']]
								})//,
							})]
						},	
							{
							columnWidth : .5,
							layout : 'form',
							border : false,
							items : [new Ext.form.ComboBox({
								name : "ModleToProcess$modleType",
								allowBlank : false,
								fieldLabel : "��������ģ��",
								id : 'ModleToProcess$modleTypeCombo',
								width : 200,
								mode : 'local',
								defaultParam : '',
								allowBlank : false,
								hiddenName : 'ModleToProcess$modleType',
								xtype : 'combo',
								displayField : 'name',
								valueField : 'id',
								triggerAction : 'all',
								typeAhead : true,
								forceSelection : true,
								emptyText : '��ѡ��',
								store : new Ext.data.SimpleStore({
									fields : ['id', 'name'],
									data : [['CI', '������'], ['SCI', '������'],['SCIC', '����Ŀ¼'],['Event', '�¼�'],['Notice', '����'],
									['Kno_Solution', '�������'],['Kno_Contract', '��ͬ'],['Kno_File', '�ļ�']]
								})//,
							})]
						},
						new Ext.form.Hidden({
							xtype : 'hidden',
							id : 'ModleToProcess$id',
							colspan : 0,
							rowspan : 0,
							name : 'ModleToProcess$id',
							width : 200,
							style : '',
							value : '',
							fieldLabel : '�Զ����'
						})]
			}]
		});
		if (tempId != 0) {
			this.serviceItemProcessPanel.load({
				//url : webContext+ '/sciProcess_getSciProcessInfo.action?sciProcessId='+ tempId,
				url : webContext+ '/configWorkflow_findModleToProcess.action?dataId='+ tempId,
				timeout : 30,
				success : function(action, form) {
					Ext.getCmp('ModleToProcess$processStatusTypeCombo').initComponent();
					Ext.getCmp('ModleToProcess$processInfoCombo').initComponent();
				}
			});
		}
		return this.serviceItemProcessPanel;
	},
	showtt : function() {
		var record = this.processgrid.getSelectionModel().getSelected();
		var id = record.get("id");
		this.getServiceItemProcessPanel(id);
		var win1 = new Ext.Window({
			title : '������������Ϣ',
			proxy : '0',
			height : 300,
			width : 700,
			buttonAlign : 'center',
			resizable : false,
			draggable : true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'fit',
			items : [this.serviceItemProcessPanel],
			buttons : [{
				text : '����',
				handler : function() {
					var info = Ext.encode(getFormParam('serviceItemProcessPanel'));
					Ext.Ajax.request({
						url : webContext + '/configWorkflow_saveModleToProcess.action',
						params : {
							info : info
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							var curId = responseArray.id;
							Ext.getCmp('serviceItemProcessPanel').load({
								url : webContext+ '/configWorkflow_getModleProcess.action',
								timeout : 30,
								success : function(action, form) {

								}
							});
							
							Ext.MessageBox.alert("����ɹ�");
							Ext.getCmp('processgridpanel').getStore().removeAll();
							Ext.getCmp('processgridpanel').getStore().reload();
							win1.close();
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("����ʧ��");
						}
					}, this);
				}
			}, {
				text : '�ر�',
				handler : function() {
					win1.close();
				},
				listeners : {
					'beforeclose' : function(p) {
						return true;
					}
				},
				scope : this
			}]
		});
		win1.show();
	},
	create : function() {
		this.getServiceItemProcessPanel(0);
		var curSciId = this.serviceItemId;
		var win1 = new Ext.Window({
			title : '������������Ϣ',
			height : 300,
			width : 700,
			buttonAlign : 'center',
			layout : 'fit',
			items : [this.serviceItemProcessPanel],
			buttons : [{
				text : '����',
				handler : function() {
					var info = Ext.encode(getFormParam('serviceItemProcessPanel'));
					Ext.Ajax.request({
						url : webContext + '/configWorkflow_saveModleToProcess.action',
						params : {
							serviceItemId : curSciId,
							info : info
						},

						success : function(response, options) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							Ext.getCmp('serviceItemProcessPanel').load({
								url : webContext+ '/configWorkflow_getModleProcess.action',
								timeout : 30,
								success : function(action, form) {

								}
							});
							Ext.MessageBox.alert("����ɹ�");
							win1.close();
							Ext.getCmp('processgridpanel').getStore().removeAll();
							Ext.getCmp('processgridpanel').getStore().reload();
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("����ʧ��");
						}
					}, this);
				}
			}, {
				text : '�ر�',
				handler : function() {
					win1.close();
				},
				listeners : {
					'beforeclose' : function(p) {
						return true;
					}
				},
				scope : this
			}]
		});
		win1.show();
	},
	remove : function() {
		var record = this.processgrid.getSelectionModel().getSelected();
		var id = record.get("id");
		Ext.Ajax.request({
			url : webContext + '/configWorkflow_removeModleToProcess.action',
			params : {
				dataId : id
			},

			success : function(response, options) {
				Ext.getCmp('processgridpanel').getStore().removeAll();
				Ext.getCmp('processgridpanel').getStore().reload();
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	initComponent : function() {
		// �����������
		this.store = new Ext.data.JsonStore({
			url : webContext+ '/configWorkflow_getModleProcess.action',
			fields : ['id', 'modleType', 'processStatusType', 'definitionName'],
			totalProperty : "rowCount",
			root : "data",
			id : 'id'
		});
		this.pageBar = new Ext.PagingToolbar({
			id : 'pagebar',
			pageSize : 15,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "û�з�������������"
		});
		var sm = new Ext.grid.CheckboxSelectionModel();

		// ����Grid������
		this.processgrid = new Ext.grid.GridPanel({
			width : 'auto',// 800,
			height : 550,
			loadMask : true,
			id : 'processgridpanel',
			frame : true,
			store : this.store,
			bbar : this.pageBar,
			autoScroll : true,
			trackMouseOve : true,
			tbar : [{
				pressed : true,
				text : "������ģ�����̹���",
				iconCls : 'add',
				scope : this,
				handler : this.create
			}, {
				pressed : true,
				text : "ɾ��ģ�����̹���",
				iconCls : 'delete',
				scope : this,
				handler : this.remove
			}],
			sm : sm,
			columns : [sm, {
				id : 'id',
				header : "id",
				width : 50,
				sortable : true,
				dataIndex : 'id',
				hidden : true
			}, {
				header : "��������ģ��",
				width : 100,
				sortable : true,
				dataIndex : 'modleType'
			}, {
				header : "��������",
				width : 200,
				sortable : true,
				dataIndex : 'processStatusType'
			}, {
				header : "��Ӧ����",
				width : 200,
				sortable : true,
				dataIndex : 'definitionName'
			}]
		})
		var param = {
			pageSize : 15,
			start : 0
		};
		this.store.baseParams=param;
		this.store.removeAll();
		this.store.load();
		this.processgrid.on("rowdblclick", this.showtt, this);
		var item = new Array();
		item.push(this.processgrid);
		this.items = item;
		ModelProcessPanel.superclass.initComponent.call(this);
	}
});
