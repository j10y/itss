// ���������б�
ListProcessPanel = Ext.extend(Ext.Panel, {
	id : "ListProcessPanel",
	closable : true,
	height : 'auto',
	width : 'auto',
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	items : this.items,
	reset : function() {
		this.fp.form.reset();
	},
	// ����
	search : function() {
		var id = Ext.getCmp("processSearch").getValue();//this.processSearch.getValue();
		var param = '';
		if (id != null && id != '') {
			var realProcessDesc = this.getRealProcessDesc(id)
			param = {
				realProcessDesc : unicode(realProcessDesc),
				start : 0
			};
		}
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},

	listAll : function() {
		var param = '';
		this.formValue = param;
		this.pageBar.formValue = this.formValue;

		this.store.removeAll();
		this.store.load({
			params : param
		});
	},

	// ɾ��
	removeData : function() {
	},
	// ����
	exportExcel : function() {
	},
	// �ϴ�
	upload : function() {
	},

	view : function(grid) {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ���õ�������!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ��!");
			return;
		}
		var id = record.get("id");

		window.location = webContext
				+ "/infoAdmin/workflow/configPage/taskInfo.jsp?virtualDefinitionInfoId="
				+ id;
	},
	getRealProcessDesc : function(id) {
		var url = webContext
				+ '/workflow/update.do?methodCall=getRealProcessDesc&processId='
				+ id;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			// responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			var realProcessDesc = data.realProcessDesc;
			return realProcessDesc
		} else {
			return 'no result';
		}
	},
	getVirtualDefinitionInfo : function(id) {
		var url = webContext
				+ '/workflow/update.do?methodCall=getVirtualDefinitionInfo&id='
				+ id;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			return data
		} else {
			return 'no result';
		}
	},
	// ��̬��Ϊ�������������ṩһ��Ĭ��ֵ
	getCombValue : function(com, record) {
		var param = Ext.getCmp("processSearch").getValue();//this.processSearch.getValue();
		var url = webContext
				+ '/workflow/update.do?methodCall=getRealProcessDesc&processId='
				+ param;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		var realProcessDesc = '';
		var version = '';
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			// responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			realProcessDesc = data.realProcessDesc;
			version = data.version;
		} else {
			return 'no result';
		}
		var value = record.get("departName") + '_' + realProcessDesc + "_["
				+ version + "]";
		Ext.getCmp('com.digitalchina.writeInfo').getForm()
				.findField('virtualProcess').setValue(value);
	},

	addVirtualProcess : function() {
		var processAction = new ProcessAction();
		var param = Ext.getCmp("processSearch").getValue();//this.processSearch.getValue();
		if (param == null || param == '') {
			Ext.Msg.alert("��ʾ", "����ѡ�����̶���!");
			return;
		}
		var realProcessDesc = this.getRealProcessDesc(param);
		var clazz = "com.digitalchina.info.framework.security.entity.Department";

		var department = new Ext.form.ComboBox({
			id : 'department',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/configUnitRole_queryCobom.action?displayField=departName&clazz='
						+ clazz,
				fields : ['id', 'departName'],
				totalProperty : 'rowCount',
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				},
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['departName'] == undefined) {
							opt.params['departName'] = department.defaultParam;
						}
					}
				}
			}),
			valueField : "id",
			displayField : "departName",
			fieldLabel : "��������",
			emptyText : '��ѡ����������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : true,
			triggerAction : 'all',
			lazyRender : true,
			pageSize : 10,
			typeAhead : false,
			allowBlank : false,
			name : "department",
			selectOnFocus : true,
			width : 340,
			// *******************************************************************
			validator : function(value) {
				if (this.store.getCount() == 0 && this.getRawValue() != '') {
					return false;
				}
				if (this.store.getCount() > 0) {
					var valid = false;
					this.store.each(function(r) {
						var rowValue = r.data.departName;
						if (rowValue == value) {
							valid = true;
						}
					});
					if (!valid) {
						return false;
					}
				}
				return true;
			},
			// *******************************************************************

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
							departName : param,
							start : 0
						},
						callback : function(r, options, success) {
							department.validate();
						}
					});
					return true;
				}
			}
		});
		// ��̬�İ��������̵����ָ���Ĭ��ֵ
		department.on("select", this.getCombValue, this);

		var definitionType = new Ext.form.ComboBox({
			id : 'definitionType',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/workflow/update.do?methodCall=getAllDefinitionType',
				fields : ['id', 'name'],
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				}
			}),
			valueField : "id",
			displayField : "name",
			fieldLabel : "���̷���",
			emptyText : '��ѡ������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : false,
			triggerAction : 'all',
			lazyRender : true,
			typeAhead : true,
			allowBlank : false,
			name : "type",
			selectOnFocus : true,
			width : 340
		});

		var writeInfo = new Ext.form.FormPanel({
			id : 'com.digitalchina.writeInfo',
			height : 220,
			frame : true,
			width : 520,
			labelWidth : 150,
			labelAlign : "right",
			defaultType : "field",
			buttonAlign : 'left',
			fileUpload : true,
			items : [{
				name : 'realProcessName',
				fieldLabel : '���̶�������',
				value : realProcessDesc,
				readOnly : true,
				width : 340,
				height : 20
			}, definitionType, department, {
				id : 'virtualProcess',
				width : 340,
				height : 20,
				allowBlank : false,
				name : 'virtualProcessName',
				fieldLabel : "���̵����ƣ�����������"
			}, {
				id : 'addRuleField',
				inputType : 'file',
				width : 340,
				allowBlank : false,
				// permitted_extensions:['drl'],
				height : 30,
				name : 'ruleFileName',
				fieldLabel : "�������漰�Ĺ����ļ�"
			}]
		});

		var win = new Ext.Window({
			title : '����������Ϣ',
			width : 530,
			height : 220,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "����",
				iconCls : 'save',
				handler : function() {
					var processType = Ext.getCmp('com.digitalchina.writeInfo')
							.getForm().findField('definitionType').getValue();
					var department = Ext.getCmp('com.digitalchina.writeInfo')
							.getForm().findField('department').getValue();
					var virtualProcessDesc = Ext
							.getCmp('com.digitalchina.writeInfo').getForm()
							.findField('virtualProcess').getValue();
					var ruleFile = Ext.getCmp('com.digitalchina.writeInfo')
							.getForm().findField('addRuleField').getValue();
					if (department == null || department == '') {
						Ext.Msg.alert('��ʾ', '�������Ų���Ϊ�գ�');
						return;
					}
					if (virtualProcessDesc == null || virtualProcessDesc == '') {
						Ext.Msg.alert('��ʾ', '�����������Ʋ���Ϊ�գ�');
						return;
					}
					if (processType == null || processType == '') {
						Ext.Msg.alert('��ʾ', '�������Ͳ���Ϊ�գ�');
						return;
					}
					if(ruleFile!=null&&ruleFile!=""){
						  if (ruleFile.substring(ruleFile.lastIndexOf(".")) !=
						 '.drl') {
						 Ext.Msg.alert('��ʾ', '�����ļ�ֻ�����ϴ���.drl��β��');
						 return;
						 }
					}
					 
					virtualProcessDesc = unicode(virtualProcessDesc);
					Ext.getCmp('com.digitalchina.writeInfo').getForm().submit({
						url : webContext
								+ '/workflow/processconfig.do?methodCall=uploadRuleFile&id='
								+ param + "&department=" + department
								+ "&processType=" + processType,
						params : {
							virtualProcessDesc : virtualProcessDesc
						},
						method : 'post',
						failure : function(form1, action) {
							Ext.MessageBox.alert("����ʧ��");
						},
						success : function(form1, action) {
							Ext.Msg.alert("��ʾ", "����ɹ�", function(button) {
								window.location = 'listProcess.jsp';
							}, this);
						}
					})

				}
			}, {
				xtype : 'button',
				handler : function() {
					win.close();
				},
				text : '�ر�',
				scope : this
			}],
			items : [writeInfo]
		});
		win.show();
	},
	deleteVirtualProcess : function() {
		var removeIds = '';
		var processAction = new ProcessAction();
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

		Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
			if (button == 'yes') {
				for (var i = 0; i < records.length; i++) {
					var record = records[i];
					var id = record.get('id');
					removeIds += id;
					removeIds += ",";
					this.store.remove(records[i]);
				}
				// ɾ����Ҫɾ��������Ϣ��ͬʱɾ��save�����е�����߼� ADD by DJ ��

				processAction.deleteVirtualDefinitionInfo(removeIds);
				// ���ɾ���б�
				removeIds = '';
			}
		}, this);
	},

	updateVirtualProcess : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
			return;
		}
		if (records.length > 1) {
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ�������޸�!');
			return;
		}
		var virtualDefinitionInfoId = record.get("id");
		var processAction = new ProcessAction();
		var data = this.getVirtualDefinitionInfo(virtualDefinitionInfoId);
		var virtualDefinitionDesc = data.virtualDefinitionDesc;
		var realDefinitionDesc = data.realDefinitionDesc;
		var typeName = data.typeName;
		var deptId = data.deptId + '';
		var ruleFileName = data.ruleFileName;
		var version=data.version;
		var clazz = "com.digitalchina.info.framework.security.entity.Department";
		var department = new Ext.form.ComboBox({
			id : 'department',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/configUnitRole_queryCobom.action?displayField=departName&clazz='
						+ clazz,
				fields : ['id', 'departName'],
				totalProperty : 'rowCount',
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				}
			}),
			valueField : "id",
			displayField : "departName",
			fieldLabel : "��������",
			emptyText : '��ѡ����������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			// value:deptName,
			editable : true,
			triggerAction : 'all',
			lazyRender : true,
			pageSize : 10,
			typeAhead : false,
			allowBlank : false,
			name : "department",
			selectOnFocus : true,
			width : 340,
			// *******************************************************************
			validator : function(value) {
				if (this.store.getCount() == 0 && this.getRawValue() != '') {
					return false;
				}
				if (this.store.getCount() > 0) {
					var valid = false;
					this.store.each(function(r) {
						var rowValue = r.data.departName;
						if (rowValue == value) {
							valid = true;
						}
					});
					if (!valid) {
						return false;
					}
				}
				return true;
			},
			// *******************************************************************

			listeners : {
				blur : function(combo) {// ����ʧȥ�����ʱ��
					if (combo.getRawValue() == '') {
						combo.reset();
					}
				},
				beforequery : function(queryEvent) {
					var param = queryEvent.combo.getRawValue();
					this.store.baseParams.departName=param;
					this.store.load({
						params : {
							start : 0
						},
						callback : function(r, options, success) {
							department.validate();
						}
					});
					return false;
				}
			},
			initComponent : function() {
				this.store.load({
					params : {
						id : deptId,
						start : 0
					},
					callback : function(r, options, success) {
						if (deptId != '')
							Ext.getCmp('department').setValue(deptId);
					}
				});
			}
		});
		// ��̬�İ��������̵����ָ���Ĭ��ֵ
		department.on("select", function(com, record) {
			var value = record.get("departName") + '_' + realDefinitionDesc + "_["
					+ version + "]";
			Ext.getCmp('com.digitalchina.writeInfo').getForm()
					.findField('virtualProcess').setValue(value);

		}, this);

		var definitionType = new Ext.form.ComboBox({
			id : 'definitionType',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/workflow/update.do?methodCall=getAllDefinitionType',
				fields : ['id', 'name'],
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				}
			}),
			valueField : "id",
			displayField : "name",
			value : typeName,//�����浽���ݿ��е�����ȡ��
			fieldLabel : "���̷���",
			emptyText : '��ѡ������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : false,
			triggerAction : 'all',
			lazyRender : true,
			typeAhead : true,
			allowBlank : false,
			name : "type",
			selectOnFocus : true,
			width : 340
		});

		var writeInfo = new Ext.form.FormPanel({
			id : 'com.digitalchina.writeInfo',
			height : 160,
			frame : true,
			width : 500,
			labelWidth : 140,
			labelAlign : "right",
			defaultType : "field",
			buttonAlign : 'left',
			fileUpload : true,
			items : [{
				name : 'realProcessName',
				fieldLabel : '���̶�������',
				value : realDefinitionDesc,
				readOnly : true,
				width : 340,
				height : 20
			}, definitionType, department, {
				id : 'virtualProcess',
				width : 340,
				height : 20,
				allowBlank : false,
				value : virtualDefinitionDesc,
				name : 'virtualProcessName',
				fieldLabel : "�������ƣ�����������"
			}
			]
		});

		var win = new Ext.Window({
			title : '�޸�������Ϣ',
			width : 510,
			height : 200,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "����",
				iconCls : 'save',
				handler : function() {
					var processType = Ext.getCmp('com.digitalchina.writeInfo')
							.getForm().findField('definitionType').getValue();
					var department = Ext.getCmp('com.digitalchina.writeInfo')
							.getForm().findField('department').getValue();
					var virtualProcessDesc = Ext
							.getCmp('com.digitalchina.writeInfo').getForm()
							.findField('virtualProcess').getValue();
					processAction.saveUpdateVirtualProcess(virtualProcessDesc,
							department, processType, virtualDefinitionInfoId, realDefinitionDesc);
					win.close();
				}
			}, {
				xtype : 'button',
				handler : function() {
					win.close();
				},
				text : '�ر�',
				scope : this
			}],
			items : [writeInfo]
		});
		win.show();

	},

	// ��ʼ��
	initComponent : function() {
		ListProcessPanel.superclass.initComponent.call(this);
		var da = new DataAction();
		var processAction = new ProcessAction();
		var processStore = processAction.getProcessStore();

		var processSearch = new Ext.form.ComboBox({
			id : "processSearch",
			xtype : 'combo',
			fieldLabel : "��������",
			name : "processDefinitionName",
			//store : processStore,
			store : new Ext.data.JsonStore({
				id : Ext.id(),
				url : webContext
						+ '/workflow/update.do?methodCall=getProcessDefinitionStore&displayField=description',
				fields : ['id','name','description'],
				totalProperty : 'rowCount',
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				},
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['description'] == undefined) {
							opt.params['description'] = unicode(processSearch.defaultParam);
						}else{
							opt.params['description'] = unicode(opt.params['description']);
						}
					}
				}
			}),
			displayField : 'description',
			valueField : "id",
			emptyText : '��ѡ����������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : true,
			triggerAction : 'all',
			width : 200,
			lazyRender : true,
			typeAhead : false,
			allowBlank : false,
			name : "description",
			pageSize : 10,
			selectOnFocus : true,
			listWidth : 200,
			maxHeight : 240,
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
							description : param,
							start : 0
						},
						callback : function(r, options, success) {
							processSearch.validate();
						}
					});
					return true;
				}
			}
		});

		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/workflow/update.do?methodCall=getAllVirtualDefinitionInfo',
			root : "data",
			fields : ["id", "realDefinitionDesc", "virtualDefinitionDesc",
					"virtualDefinitionName", "ruleFileName", "deptName",
					"version"],
			totalProperty : 'rowCount'
		});
		this.cm = new Ext.grid.ColumnModel([sm, {
			header : "���̶������ƣ�����������",
			dataIndex : "realDefinitionDesc",
			width : 150
		}, {
			header : "�������ƣ�����������",
			dataIndex : "virtualDefinitionDesc",
			width : 300
		}, {
			header : "��������",
			dataIndex : "deptName",
			width : 150
		}, {
			header : "�����ļ�����",
			dataIndex : "ruleFileName",
			width : 300
		}, {
			header : "��������",
			dataIndex : "virtualDefinitionName",
			width : 200
		}, {
			header : "���̰汾",
			dataIndex : "version"
				// width : 200
				}]);
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;

		this.grid = new Ext.grid.GridPanel({
			id : 'mainGrid',
			title : '�����б�',
			layout : 'table',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			autoScroll : true,
			height : 490,
			width : 1000,
			frame : true,
			bbar : this.pageBar,
			tbar : [new Ext.Toolbar.TextItem('��ѡ�����̶��壺'), processSearch, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '��ѯ',
				scope : this,
				pressed : true,
				iconCls : 'search',
				handler : this.search
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '���������б�',
				scope : this,
				pressed : true,
				iconCls : 'details',
				handler : this.listAll
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '��������',
				scope : this,
				pressed : true,
				iconCls : 'add',
				handler : this.addVirtualProcess
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : 'ɾ��',
				scope : this,
				pressed : true,
				iconCls : 'delete',
				handler : this.deleteVirtualProcess
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '�޸�',
				scope : this,
				pressed : true,
				iconCls : 'edit',
				handler : this.updateVirtualProcess
			}, new Ext.Toolbar.TextItem('<font color=red>˫��ĳ�������в鿴�ڵ���Ϣ</font>')

			]

		});
		this.grid.on("rowdblclick", this.view, this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();
		this.store.load({
			params : param

		});
		this.add(this.grid);
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}

});
