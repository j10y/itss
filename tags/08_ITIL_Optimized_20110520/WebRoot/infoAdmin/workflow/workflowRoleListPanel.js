
// 订单申请列表Panel基类
WorkflowRoleListPanel = Ext.extend(Ext.Panel, {
	id : "WorkflowRoleListPanel",
 	title:"流程角色管理",
	closable : true,
	frame : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',

	reset : function() {
		this.fp.form.reset();
	},
	// 搜索
	search : function() {
		var param = this.fp.form.getValues(false);
		param.methodCall = 'query';
		param.deleteFlag = 0;
		this.formValue = param;		
		this.pageBar.formValue = this.formValue;
		param.start = 1;
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	
	
	create : function() {
		var da = new DataAction();
		var clazz = "com.zsgj.info.framework.workflow.entity.WorkflowRole";
		var data = da.getElementsForAdd(clazz);
		var biddata = da.split(data);
		
		this.panel = new Ext.form.FormPanel({
			layout : 'table',
		    height : 'auto',
		    width : 'auto',
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items : biddata
		})
		
		
		var modifyWin = new Ext.Window({
			title : '流程角色管理',
			modal : true,
			height : 150,
			width : 300,
			resizable : true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			scope:this,
			layout : 'fit',
			buttons : [{
				xtype : 'button',
				handler : function() {					
					var bsParam = this.panel.form.getValues(false);
					bsParam.deleteFlag = 0;
					if(da.saveData(clazz,bsParam)){
						//Ext.MessageBox.alert("提示信息：","新建成功！");
						this.grid.getStore().reload();
						modifyWin.close();
					}
				},
				text : '保存',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					modifyWin.close()
				},
				text : '关闭',
				scope : this
			}],
			items : this.panel
		});
		modifyWin.show();
	},

	// 打开编辑框口
	modify : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行!");
			return;
		}
		var id = record.get("id");

		var da = new DataAction();
		var clazz = "com.zsgj.info.framework.workflow.entity.WorkflowRole";
		var data = da.getElementsForEdit(clazz,id);
		var biddata = da.split(data);
		
		this.panel = new Ext.form.FormPanel({
			layout : 'table',
		    height : 'auto',
		    width : 'auto',
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			items : biddata
		})
		
		
		var modifyWin = new Ext.Window({
			title : '流程角色管理',
			modal : true,
			height : 150,
			width : 300,
			resizable : true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			scope:this,
			layout : 'fit',
			buttons : [{
				xtype : 'button',
				handler : function() {					
					var bsParam = this.panel.form.getValues(false);
					if(da.saveData(clazz,bsParam)){
						//Ext.MessageBox.alert("提示信息：","新建成功！");
						this.grid.getStore().reload();
						modifyWin.close();
					}
				},
				text : '保存',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					modifyWin.close()
				},
				text : '关闭',
				scope : this
			}],
			items : this.panel
		});
		modifyWin.show();
		

	},
	// 删除
	removeData : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行!");
			return;
		}
		var id = record.get("id");
		
		var m = Ext.MessageBox.confirm("删除提示", "是否真的要删除数据？", function(ret) {
			if (ret == "yes") {
				var da = new DataAction();
				var clazz = "com.zsgj.info.framework.workflow.entity.WorkflowRole";
				da.saveData(clazz,{id:id,deleteFlag:"1",name:'_',code:''},false);
				this.grid.getStore().reload({param:{deleteFlag:'0'}});
			}
		}, this);
	},
	

	getTopbar : function() {
		return ['   ', {
			text : '新建角色',
			pressed : true,
			handler : this.create,
			scope : this,
			iconCls : 'add'
		},'   ', {
			text : '修改角色',
			pressed : true,
			handler : this.modify,
			scope : this,
			iconCls : 'edit'
		},'   ', {
			text : '删除角色',
			pressed : true,
			handler : this.removeData,
			scope : this,
			iconCls : 'delete'
		},'    |    ', {
			text : '查询',
			pressed : true,
			handler : this.search,
			scope : this,
			iconCls : 'search'
		}, '    ', {
			text : ' 重置',
			pressed : true,
			handler : this.reset,
			scope : this,
			iconCls : 'reset'
		}];
	},

	getSearchForm : function() {
		return new WorkflowRoleSearchForm();
	},

	// 初始化

	initComponent : function() {
		this.fp = this.getSearchForm();
		var clazz = "com.zsgj.info.framework.workflow.entity.WorkflowRole";
		var da = new DataAction();
		var obj = da.getElementsForHead(clazz);

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();

		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;

			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;
			var hidden = headItem.hidden;
			var isHidden = false;
			// if(propertyName=='id'){
			// isHidden = true;
			// }
			if (hidden == 'true') {
				isHidden = true;
			} else {
				isHidden=false;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				width: 400,
				align : alignStyle
			};
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}

		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);

		this.store = da.getJsonStore(clazz);

		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		WorkflowRoleListPanel.superclass.initComponent.call(this);
		this.pageBar = new Ext.PagingToolbarExt({
			pageSize : sys_pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.topbar = this.getTopbar();
		this.grid = new Ext.grid.GridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			frame:true,
			loadMask : true,
			y : 40,
			anchor : '0 -38',
			tbar : this.topbar,
			bbar : this.pageBar
		});
		this.grid.on("rowdblclick", this.modify, this);
		this.grid.on("headerdblclick", this.fitWidth, this);

		this.add(this.fp);
		this.add(this.grid);
		var param = {};//this.getBaseParam();
		param.start = 1;
		param.deleteFlag = 0;
		this.pageBar.formValue = param;
		this.store.load({
			params : param
		});

	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
	initData : function() {
		var param = {
			'methodCall' : 'list',
			'start' : 1
		};
		this.pageBar.formValue = param;
		this.store.load({
			params : param
		});
	}
});
