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

	// 初始化
	initComponent : function() {// 在这里面定义的变量是全局变量
		SubProcessConfigUnit.superclass.initComponent.call(this);// 让父类先初始化
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
		var codes = [['cproject', '配置项审批类型'], ['nproject', '公告管理审批类型']];
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
			title : "子流程配置(<font color=red>填写中文流程名称,必须保证子流程先于父流程发布！</font>)",
			items : [{
				fieldLabel : '子流程名称',
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
								subProcessName : param,
								start : 0
							}
						});
						return true;
					}
				}
			}, {
				fieldLabel : '子流程审批类型',
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
								applyTypeName : param,
								start : 0
							}
						});
						return true;
					}
				}

			}, new Ext.form.TextArea({
				id : "subProcessParam",
				fieldLabel : "启动子流程所需的参数",
				labelWidth : 100,
				value : param,
				width : 300,
				autoScroll : true,
				emptyText : "(形式为name1=value1,name2=value2)"
			}), {
				xtype : 'hidden',
				id : 'id',
				name : 'id',
				width : 'null',
				style : '',
				value : ids,
				fieldLabel : '自动编号'
			}
			// ,
			// {
			// xtype : 'hidden',
			// id : 'applyType',
			// name : 'applyType',
			// width : 'null',
			// style : '',
			// value : Ext.getCmp("applyTypeName").getValue(),
			// fieldLabel : '自动编号'
			// }
			],
			buttons : [{
				handler : this.save,
				scope : this,
				iconCls : 'save',
				text : '保存'
			}]
		})
		this.add(this.subProcessPanel);

	}
});
