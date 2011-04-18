SubProcessConfigUnit = Ext.extend(Ext.Panel, {
	id : "SubProcessConfigUnit",
	height : 400,
	align : 'center',
	foredit : true,
	width : 600,
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	freshPage : function() {
		window.location.reload();
	},

	goBackUrl : function() {
		history.go(-1);
	},

	items : this.items,

	save : function() {
		var orderAction = new OrderAction();
		var bsParam = this.subProcessPanel.form.getValues(false);
		var formParam = Ext.encode(bsParam);
		orderAction.saveSubProcessConfigUnitRecord(formParam,
				this.virtualDefinitionInfoId, this.nodeId, this.desc);
	},

	// ��ʼ��
	initComponent : function() {// �������涨��ı�����ȫ�ֱ���
		SubProcessConfigUnit.superclass.initComponent.call(this);// �ø����ȳ�ʼ��
		var orderAction = new OrderAction();
		var data = orderAction.getSubProcessData(this.virtualDefinitionInfoId,
				this.nodeId);
		// var
		// subProcessStore=orderAction.getSubProcessStore(this.virtualDefinitionInfoId);
		var ids = data.id;
		var subProcessName = data.subProcessName;
		var applyTypeName = data.applyTypeName;
		// var applyType=data.applyType;
		var param = data.param;
		if (ids == null || ids == 'null') {
			ids = '';
		}
		if (subProcessName == null || subProcessName == 'null') {
			subProcessName = '';
		}
		if (applyTypeName == null || applyTypeName == 'null') {
			applyTypeName = '';
		}
		if (param == null || param == 'null') {
			param = '';
		}
		var codes = [['cproject', '��������������'], ['nproject', '���������������']];
		var typeStore = new Ext.data.SimpleStore({
			fields : ['applyType', 'applyTypeName'],
			data : codes

		});
		var processId = this.virtualDefinitionInfoId;
		this.subProcessPanel = new Ext.form.FormPanel({
			id : 'com.subProcessPanel',
			// layout : '',
			height : 340,
			width : 730,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "����������(<font color=red>��д������������,���뱣֤���������ڸ����̷�����</font>)",
			items : [{
				fieldLabel : '����������',
				xtype : 'combo',
				id : 'subProcessName',
				displayField : 'subProcessName',
				valueField : "subProcessName",
				labelWidth : 60,
				value : subProcessName,
				// store:subProcessStore,
				style : '',
				maxHeight : 260,
				width : 300,
				forceSelection : true,
				allowBlank : true,
				lazyRender : true,
				typeAhead : false,
				name : "subProcessName",
				selectOnFocus : true,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/workflow/processconfig.do?methodCall=getSubProcessStore&virtualDefinitionInfoId='
							+ processId,
					root : "data",
					fields : ['id', 'subProcessName'],
					remoteSort : false,
					totalProperty : 'rowCount',
					id : 'id',
					sortInfo : {
						field : "subProcessName",
						direction : "ASC"
					},
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['subProcessName'] == 'undefined') {
								opt.params['subProcessName'] = dep.defaultParam;
							} else {
								if (opt.params['subProcessName'] == null
										|| opt.params['subProcessName'] == '') {
									opt.params['subProcessName'] = opt.params['subProcessName'];
								} else {
									opt.params['subProcessName'] = unicode(opt.params['subProcessName']);
								}
							}
						}
					}
				}),
				// *******************************************************************
				pageSize : 10,
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
								subProcessName : param,
								start : 0
							}
						});
						return true;
					}
				}
			}, {
				fieldLabel : '��������������',
				xtype : 'combo',
				id : 'applyTypeName',
				displayField : 'applyTypeName',
				valueField : "applyType",
				labelWidth : 60,
				value : applyTypeName,
				// store:subProcessStore,
				style : '',
				maxHeight : 260,
				width : 300,
				forceSelection : true,
				allowBlank : true,
				lazyRender : true,
				typeAhead : false,
				name : "applyTypeName",
				selectOnFocus : true,
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/workflow/processconfig.do?methodCall=getApplyTypeStore&virtualDefinitionInfoId='
							+ processId,
					root : "data",
					fields : ['applyType', 'applyTypeName']
				}),
				pageSize : 10,
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
								applyTypeName : param,
								start : 0
							}
						});
						return true;
					}
				}

			}, new Ext.form.TextArea({
				id : "subProcessParam",
				fieldLabel : "��������������Ĳ���",
				labelWidth : 100,
				value : param,
				width : 300,
				autoScroll : true,
				emptyText : "(��ʽΪname1=value1,name2=value2)"
			}), {
				xtype : 'hidden',
				id : 'id',
				name : 'id',
				width : 'null',
				style : '',
				value : ids,
				fieldLabel : '�Զ����'
			}
			// ,
			// {
			// xtype : 'hidden',
			// id : 'applyType',
			// name : 'applyType',
			// width : 'null',
			// style : '',
			// value : Ext.getCmp("applyTypeName").getValue(),
			// fieldLabel : '�Զ����'
			// }
			],
			buttons : [{
				handler : this.save,
				scope : this,
				iconCls : 'save',
				text : '����'
			}]
		})
		this.add(this.subProcessPanel);

	}
});
