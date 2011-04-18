// ������Ϣ�б�
DeployProcessPanel = Ext.extend(Ext.Panel, {
	id : "DeployProcessPanel",
	title : "�����б�",
	closable : true,
	autoScroll : true,
	// layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	items : this.items,
	reset : function() {
		this.processSearch.form.reset();
	},
	// ����
	search : function() {
		// var param = this.fp.form.getValues(false);
		var description = this.processSearch.form.findField("description")
				.getValue();
		var processName = this.processSearch.form.findField("name").getValue();
		var param = '';
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		// param.start = 1;
		// param.description=description;
		// param.processName=processName;
		// this.store.removeAll();
		this.store.reload({
			params : {
				start : 1,
				description : description,
				processName : processName
			}
		});
	},

	deploysubmit : function(button) {

		Ext.MessageBox.confirm("��ȷ��", "�Ƿ����Ҫ�������������", function(button, text) {
			if (button == 'yes') {
				Ext.Ajax.request({
					url : webContext + '/upload',
					params : {
						dataId : this.dataId
					// ,
					// info : formParam,
					// product : product
					},
					success : function(response, options) {

						Ext.Msg.alert("��ʾ", "�����ɹ�", function(button) {
							location = 'deployProcess.jsp'
						}, this);
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				});
			}
		});

	},

	/* ��д������Ϣ */
	view : function() {
		
		var department = new Ext.form.ComboBox({
			id:'department',
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/sysManage/userRoleAction.do?methodCall=findAllDept',
				fields : ['id', 'name'],
				root : 'data',
				sortInfo : {
					field : "id",
					direction : "ASC"
				}
			}),
			valueField : "id",
			displayField : "name",
			fieldLabel : "��������",
			emptyText : '��ѡ����������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : false,
			triggerAction : 'all',
			lazyRender : true,
			typeAhead : true,
			allowBlank : true,
			name : "dept",
			selectOnFocus : true,
			width : 390
		});
		

		var writeInfo = new Ext.form.FormPanel({
			id : 'com.digitalchina.writeInfo',
			height : 160,
			frame : true,
			width : 500,
			labelWidth : 70,
			labelAlign : "right",
			defaultType : "field",
			buttonAlign : 'left',
			items : [department,
				{
				id:"uploadFile",
				name : 'File1',
				fieldLabel : "���ش���ļ�",
				inputType : "file"},
			    {
				id:'addRuleField',
				name : 'ruleName',
				fieldLabel : "�������漰�Ĺ����ļ�"}]
		});

		var win = new Ext.Window({
			title : '����������',
			width : 500,
			height : 200,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "�ύ",
				handler : function() {
					var ruleName=Ext.getCmp('com.digitalchina.writeInfo').getForm().findField('addRuleField').getValue();
					var dept = Ext.getCmp('com.digitalchina.writeInfo').getForm().findField('department').getValue();
					alert(ruleName)
					alert(dept)
					Ext.MessageBox.confirm("��ȷ��", "�Ƿ����Ҫ�ύ", function(button,
							text) {
						if (button == 'yes') {
							Ext.Ajax.request({
								url : webContext + '/upload',
															
								success : function(response, options) {

									Ext.Msg.alert("��ʾ", "�ύ�ɹ�",
											function(button) {
												location = 'deployProcess.jsp'
											}, this);
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("�ύʧ��");
								}
							});
						}
					});
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

	/* ���������� */
	deploy : function() {
		
		var deployProcess = new Ext.form.FormPanel({
			// title:'test',
			id : "com.digitalchina.deoploy",
			height : 160,
			frame : true,
			width : 500,
			// url : webContext + '/upload',
			labelWidth : 100,
			labelAlign : "right",
			defaultType : "field",
			buttonAlign : 'left',
			items : [{
				name : 'File1',
				fieldLabel : "���ش���ļ�",
				inputType : "file"
			}]

		});

		var win = new Ext.Window({
			title : '����������',
			width : 400,
			height : 200,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "����",
				handler : this.deploysubmit
			}, {
				xtype : 'button',
				handler : function() {
					win.close();
				},
				text : '�ر�',
				scope : this
			}],
			items : [deployProcess]
		});
		win.show();

	},
	// ��ʼ�����г���������
	initComponent : function() {

		this.processSearch = new Ext.form.FormPanel({
			// title:'test',
			id : 'com.digital.processSearch',
			height : 80,
			frame : true,
			width : 900,
			buttonAlign : 'left',

			items : [{
				id : 'name',
				xtype : 'textfield',
				width : 180,
				name : 'name',
				fieldLabel : "��������"
			}, {
				id : 'description',
				xtype : "textfield",
				width : 180,
				name : 'description',
				fieldLabel : "��������"
			}]

		});

		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext
					+ '/workflow/processconfig.do?methodCall=getProcess',
			root : "data",
			fields : ["id", "name", "description", "dept", "version"]
		});
		var columns = new Array();
		columns[0] = sm;
		columns[1] = {
			header : "��������",
			dataIndex : "name"
		};
		columns[2] = {
			header : "����",
			dataIndex : "description"
		};
		columns[3] = {
			header : "��������",
			dataIndex : "dept"
		};
		columns[4] = {
			header : "�汾��",
			dataIndex : "version"
		};

		this.colM = new Ext.grid.ColumnModel(columns
				// [{
				// header : "��������",
				// dataIndex : "name"
				// }, {
				// header : "����",
				// dataIndex : "description"
				// }, {
				// header : "��������",
				// dataIndex : "dept"
				// }, {
				// header : "�汾��",
				// dataIndex : "version"
				// }]
		);

		this.pageBar = new Ext.PagingToolbarExt({
			pageSize : sys_pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ ',
			emptyMsg : "����ʾ����"
		});
		this.grid = new Ext.grid.GridPanel({

			id : 'com.digitalchina.grid',
			title : "˫��ĳ����������д������Ϣ",
			layout : 'table',
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			height : 500,
			width : 900,
			cm : this.colM,
			store : this.store,
			sm : sm,
			autoExpandColumn : 2,
			frame : true,
			tbar : [{
				text : '��ѯ',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, {
				text : ' ����',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			}, {
				text : ' ����������',
				pressed : true,
				handler : this.view,
				scope : this,
				iconCls : 'reset'
			}],
			bbar : this.pageBar
		});
		var param = {
			'start' : 1
		};
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		this.store.load({
			params : param
		});
		this.grid.on("rowdblclick", this.view, this);
		this.items = [this.processSearch, this.grid];
		DeployProcessPanel.superclass.initComponent.call(this);
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
