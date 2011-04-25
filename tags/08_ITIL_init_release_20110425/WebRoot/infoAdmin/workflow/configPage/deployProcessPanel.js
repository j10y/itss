// 流程信息列表
DeployProcessPanel = Ext.extend(Ext.Panel, {
	id : "DeployProcessPanel",
	title : "流程列表",
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
	// 搜索
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

		Ext.MessageBox.confirm("请确认", "是否真的要发布这个新流程", function(button, text) {
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

						Ext.Msg.alert("提示", "发布成功", function(button) {
							location = 'deployProcess.jsp'
						}, this);
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("发布失败");
					}
				});
			}
		});

	},

	/* 填写部门信息 */
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
			fieldLabel : "隶属部门",
			emptyText : '请选择隶属部门',
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
				fieldLabel : "本地打包文件",
				inputType : "file"},
			    {
				id:'addRuleField',
				name : 'ruleName',
				fieldLabel : "此流程涉及的规则文件"}]
		});

		var win = new Ext.Window({
			title : '发布新流程',
			width : 500,
			height : 200,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "提交",
				handler : function() {
					var ruleName=Ext.getCmp('com.digitalchina.writeInfo').getForm().findField('addRuleField').getValue();
					var dept = Ext.getCmp('com.digitalchina.writeInfo').getForm().findField('department').getValue();
					alert(ruleName)
					alert(dept)
					Ext.MessageBox.confirm("请确认", "是否真的要提交", function(button,
							text) {
						if (button == 'yes') {
							Ext.Ajax.request({
								url : webContext + '/upload',
															
								success : function(response, options) {

									Ext.Msg.alert("提示", "提交成功",
											function(button) {
												location = 'deployProcess.jsp'
											}, this);
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("提交失败");
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
				text : '关闭',
				scope : this
			}],
			items : [writeInfo]
		});
		win.show();
	},

	/* 发布新流程 */
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
				fieldLabel : "本地打包文件",
				inputType : "file"
			}]

		});

		var win = new Ext.Window({
			title : '发布新流程',
			width : 400,
			height : 200,
			modal : true,
			buttons : [{
				xtype : 'button',
				text : "发布",
				handler : this.deploysubmit
			}, {
				xtype : 'button',
				handler : function() {
					win.close();
				},
				text : '关闭',
				scope : this
			}],
			items : [deployProcess]
		});
		win.show();

	},
	// 初始化，列出所有流程
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
				fieldLabel : "流程名称"
			}, {
				id : 'description',
				xtype : "textfield",
				width : 180,
				name : 'description',
				fieldLabel : "流程描述"
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
			header : "流程名称",
			dataIndex : "name"
		};
		columns[2] = {
			header : "描述",
			dataIndex : "description"
		};
		columns[3] = {
			header : "所属部门",
			dataIndex : "dept"
		};
		columns[4] = {
			header : "版本号",
			dataIndex : "version"
		};

		this.colM = new Ext.grid.ColumnModel(columns
				// [{
				// header : "流程名称",
				// dataIndex : "name"
				// }, {
				// header : "描述",
				// dataIndex : "description"
				// }, {
				// header : "所属部门",
				// dataIndex : "dept"
				// }, {
				// header : "版本号",
				// dataIndex : "version"
				// }]
		);

		this.pageBar = new Ext.PagingToolbarExt({
			pageSize : sys_pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 ',
			emptyMsg : "无显示数据"
		});
		this.grid = new Ext.grid.GridPanel({

			id : 'com.digitalchina.grid',
			title : "双击某个流程行填写部门信息",
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
				text : '查询',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, {
				text : ' 重置',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			}, {
				text : ' 发布新流程',
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
