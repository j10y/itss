// 所有流程列表
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
	// 搜索
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

	// 删除
	removeData : function() {
	},
	// 导出
	exportExcel : function() {
	},
	// 上传
	upload : function() {
	},

	view : function(grid) {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要配置的流程行!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行!");
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
	// 动态的为虚拟流程名字提供一个默认值
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
			Ext.Msg.alert("提示", "请先选择流程定义!");
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
			fieldLabel : "隶属部门",
			emptyText : '请选择隶属部门',
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
		// 动态的把虚拟流程的名字给个默认值
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
			fieldLabel : "流程分类",
			emptyText : '请选择类型',
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
				fieldLabel : '流程定义名称',
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
				fieldLabel : "流程的名称（中文描述）"
			}, {
				id : 'addRuleField',
				inputType : 'file',
				width : 340,
				allowBlank : false,
				// permitted_extensions:['drl'],
				height : 30,
				name : 'ruleFileName',
				fieldLabel : "此流程涉及的规则文件"
			}]
		});

		var win = new Ext.Window({
			title : '增加流程信息',
			width : 530,
			height : 220,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "保存",
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
						Ext.Msg.alert('提示', '隶属部门不能为空！');
						return;
					}
					if (virtualProcessDesc == null || virtualProcessDesc == '') {
						Ext.Msg.alert('提示', '虚拟流程名称不能为空！');
						return;
					}
					if (processType == null || processType == '') {
						Ext.Msg.alert('提示', '流程类型不能为空！');
						return;
					}
					if(ruleFile!=null&&ruleFile!=""){
						  if (ruleFile.substring(ruleFile.lastIndexOf(".")) !=
						 '.drl') {
						 Ext.Msg.alert('提示', '规则文件只允许上传以.drl结尾！');
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
							Ext.MessageBox.alert("保存失败");
						},
						success : function(form1, action) {
							Ext.Msg.alert("提示", "保存成功", function(button) {
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
				text : '关闭',
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
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
			return;
		}

		Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {
				for (var i = 0; i < records.length; i++) {
					var record = records[i];
					var id = record.get('id');
					removeIds += id;
					removeIds += ",";
					this.store.remove(records[i]);
				}
				// 删除需要删除的行信息；同时删除save方法中的相关逻辑 ADD by DJ ；

				processAction.deleteVirtualDefinitionInfo(removeIds);
				// 清空删除列表
				removeIds = '';
			}
		}, this);
	},

	updateVirtualProcess : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		if (records.length > 1) {
			Ext.MessageBox.alert('警告', '最少选择一条信息，进行修改!');
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
			fieldLabel : "隶属部门",
			emptyText : '请选择隶属部门',
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
				blur : function(combo) {// 当其失去焦点的时候
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
		// 动态的把虚拟流程的名字给个默认值
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
			value : typeName,//将保存到数据库中的数据取出
			fieldLabel : "流程分类",
			emptyText : '请选择类型',
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
				fieldLabel : '流程定义名称',
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
				fieldLabel : "流程名称（中文描述）"
			}
			]
		});

		var win = new Ext.Window({
			title : '修改流程信息',
			width : 510,
			height : 200,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "保存",
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
				text : '关闭',
				scope : this
			}],
			items : [writeInfo]
		});
		win.show();

	},

	// 初始化
	initComponent : function() {
		ListProcessPanel.superclass.initComponent.call(this);
		var da = new DataAction();
		var processAction = new ProcessAction();
		var processStore = processAction.getProcessStore();

		var processSearch = new Ext.form.ComboBox({
			id : "processSearch",
			xtype : 'combo',
			fieldLabel : "流程名称",
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
			emptyText : '请选择流程名称',
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
			header : "流程定义名称（中文描述）",
			dataIndex : "realDefinitionDesc",
			width : 150
		}, {
			header : "流程名称（中文描述）",
			dataIndex : "virtualDefinitionDesc",
			width : 300
		}, {
			header : "所属部门",
			dataIndex : "deptName",
			width : 150
		}, {
			header : "规则文件名称",
			dataIndex : "ruleFileName",
			width : 300
		}, {
			header : "流程名字",
			dataIndex : "virtualDefinitionName",
			width : 200
		}, {
			header : "流程版本",
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
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;

		this.grid = new Ext.grid.GridPanel({
			id : 'mainGrid',
			title : '流程列表',
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
			tbar : [new Ext.Toolbar.TextItem('请选择流程定义：'), processSearch, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '查询',
				scope : this,
				pressed : true,
				iconCls : 'search',
				handler : this.search
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '所有流程列表',
				scope : this,
				pressed : true,
				iconCls : 'details',
				handler : this.listAll
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '增加流程',
				scope : this,
				pressed : true,
				iconCls : 'add',
				handler : this.addVirtualProcess
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '删除',
				scope : this,
				pressed : true,
				iconCls : 'delete',
				handler : this.deleteVirtualProcess
			}, {
				xtype : 'button',
				style : 'margin:4px 10px 4px 10px',
				text : '修改',
				scope : this,
				pressed : true,
				iconCls : 'edit',
				handler : this.updateVirtualProcess
			}, new Ext.Toolbar.TextItem('<font color=red>双击某个流程行查看节点信息</font>')

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
