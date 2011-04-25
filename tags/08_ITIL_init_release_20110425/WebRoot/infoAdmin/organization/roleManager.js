﻿﻿﻿﻿RoleManagePanel = Ext.extend(Ext.Panel, {
	id : "roleManagePanel",
	// title : "角色管理",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	// 角色基本信息面板+++++++++++++++++++++++++++++++++++++++(方法1)
	baseRolsMessage : function(baseValue) {
		this.name = new Ext.form.TextField({
			fieldLabel : '角色名称',
            allowBlank : false,                  
			name : 'name',
            id:'RolesName',
			width : 360
		});
		this.descn = new Ext.form.TextArea({
			fieldLabel : '描述',
			name : 'descn',
            id:'RolesDescn',
			width : 9999
		});
		this.dataView = new Ext.form.ComboBox({
			xtype : 'combo',
			mode : 'local',
			fieldLabel : '数据查看范围',
			name : 'dataView',
			hiddenName : 'dataView',
			store : new Ext.data.SimpleStore({
				fields : ["id", "name"],
				data : [[0, '查看自己'], [1, '查看全部']]
			}),
			emptyText : '请选择...',
			valueField : "id",
			displayField : "name",
			forceSelection : true,
			editable : false,
			triggerAction : 'all',
			width : 360
		});
		this.department = new Ext.form.ComboBox({
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.Department',
				fields : ['id', 'departName'],
				totalProperty : 'rowCount',
				root : 'data'
			}),
			id : 'deptMenuTemplate',
			fieldLabel : '隶属部门',
			valueField : "id",
			displayField : "departName",
			forceSelection : true,
			emptyText : '请选择隶属部门',
			hiddenName : "department",
			allowBlank : false,
			name : "deptMenuTemplate",
			triggerAction : 'all',
			width : 360,
			pageSize : 10,
			listeners : {
				'beforeselect':function(combo,record,index){
					if(combo.getValue()!=record.get('id')){  
						Ext.getCmp('deptMenuCombo').reset();   
					}
					
				},
				'beforequery' : function(queryEvent) {
						var store=queryEvent.combo.store;
						store.baseParams={
							departName:queryEvent.combo.getRawValue(),
							start:0
						}
						store.load();
						return false;
					}
			},
			initComponent : function() {
					if(this.getValue()!=''){
						var combo=this;
						this.store.load({
							params : {
								id : combo.getValue(),
								start : 0},
							callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
							}
						});
					}
				}
                 
		});
		this.deptMenu=new Ext.form.ComboBox({
			id : 'deptMenuCombo',
			fieldLabel : '部门菜单(新)',
			valueField : "id",
			displayField : "name",
			hiddenName : "deptMenu",
			emptyText : '请选择部门菜单',
			triggerAction : 'all',
			width : 360,
			//pageSize : 10,
			//forceSelection : true,
			store : new Ext.data.JsonStore({
				url : webContext + '/menu_findDeptMenuByDept.action',
						//+ '/extjs/comboDataAction?clazz=com.digitalchina.info.appframework.menu.entity.DeptMenu',
				fields : ['id', 'name'],
				//totalProperty : 'rowCount',
				root : 'data'
			}),
			listeners : {
					'beforequery' : function(queryEvent) {
						var dept=Ext.getCmp('deptMenuTemplate').getValue();
						if(dept==""){
							if(queryEvent.combo.getRawValue()==""){
								return false;
							}else{
								Ext.Msg.alert("提示",'请选择部门！');
								return false;
							}
						}
						var store=queryEvent.combo.store;
						store.baseParams={
							name:queryEvent.combo.getRawValue(),
							//start:0,
							department:dept
							
						}
						store.load();
						return false;
					}
				},
				initComponent : function() {
					if(this.getValue()!=''){
						var combo=this;
						this.store.load({
							params : {
								id : combo.getValue(),
								start : 0},
							callback : function(r, options, success) {
									if(r.length>0){
										combo.setRawValue(r[0].get(combo.displayField));
									}
							}
						});
					}
				}
		});
//		
//		this.menuTemplate = new Ext.form.ComboBox({
//			id : 'sys_role_menutpl',
//			fieldLabel : '可用菜单模板',
//			valueField : "id",
//			displayField : "templateName",
//			emptyText : '请选择可用菜单模板',
//			hiddenName : "deptmt",
//			name : "deptMT",
//			triggerAction : 'all',
//			width : 360,
//			pageSize : 10,
//			forceSelection : true,
//			store : new Ext.data.JsonStore({
//				url : webContext
//						+ '/extjs/comboDataAction?clazz=com.digitalchina.info.appframework.template.entity.DeptMenuTemplate',
//				fields : ['id', 'templateName'],
//				totalProperty : 'rowCount',
//				root : 'data'
//			}),
//			listeners : {
//					'beforequery' : function(queryEvent) {
//						var dept=Ext.getCmp('deptMenuTemplate').getValue();
//						if(dept==""){
//							if(queryEvent.combo.getRawValue()==""){
//								return false;
//							}else{
//								Ext.Msg.alert("提示",'请选择部门！');
//								return false;
//							}
//						}
//						var store=queryEvent.combo.store;
//						store.baseParams={
//							templateName:queryEvent.combo.getRawValue(),
//							start:0,
//							dept:dept
//							
//						}
//						store.load();
//						return false;
//					}
//				},
//				initComponent : function() {
//					if(this.getValue()!=''){
//						var combo=this;
//						this.store.load({
//							params : {
//								id : combo.getValue(),
//								start : 0},
//							callback : function(r, options, success) {
//									if(r.length>0){
//										combo.setRawValue(r[0].get(combo.displayField));
//									}
//							}
//						});
//					}
//				}
//		});
		var item = [this.name,this.dataView,this.department,this.deptMenu,this.descn];
		var items = this.splitBySu(item);
		this.baseForm = new Ext.form.FormPanel({
			buttonAlign : 'center',
			// region:'north',
            id:'baseFormRose',
			labelAlign : 'right',
			labelWidth : 80,
			height : 'auto',
			layout : 'table',
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:5px'
			},
			frame : true,
			reader : new Ext.data.JsonReader({
				root : 'role',
				successProperty : '@success'
			}, [{
				name : 'name',
				mapping : 'name'
			}, {
				name : 'dataView',
				mapping : 'dataView'
			}, {
				name : 'department',
				mapping : 'department'
			}, {
				name : 'deptmt',
				mapping : 'deptmt'
			}, {
				name : 'deptMenu',
				mapping : 'deptMenu'
			}, {
				name : 'descn',
				mapping : 'descn'
			}, {
				name : 'id',
				mapping : 'id'
			}]),
			items : items
		});

		this.name.on("blur", function(field) {
			var value = field.getValue();
			if (value.length == 0) {
				field.markInvalid("角色名称不能为空");
				showVailed('角色名称不能为空');
			}
		});
		if (baseValue != 0) {
			this.baseForm.load({
				url : webContext
						+ '/sysManage/roleAction.do?methodCall=findRole&id='
						+ baseValue,
				success : function() {
					Ext.getCmp("deptMenuTemplate").initComponent();
					Ext.getCmp("deptMenuCombo").initComponent();
					//Ext.getCmp("sys_role_menutpl").initComponent();
				}
			});
		}
		return this.baseForm;
	},
	// 权限列表+++++++++++++++++++++++++++++++++++++++++++++(方法2)
	purviewGrid : function(pruviewValue) {
         var  flagLoad=true;
        if(pruviewValue=="0"){
            flagLoad=false;
        }
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		});
		var columns = [sm, {
			header : '权限名称',
			width : 420,
			dataIndex : 'name'
		}, {
			hidden : true,
			dataIndex : "id",
			name : 'authId',
			width : 10
		}];
		var roleCM = new Ext.grid.ColumnModel(columns);
		var purviewStore = new Ext.data.JsonStore({
			root : 'auths',
			url : webContext
					+ '/sysManage/roleAction.do?methodCall=findAuthsByRoleId',
			baseParams : {
				id : pruviewValue
			},
			fields : ['moduleName', 'name', 'checked', 'id'],
			autoLoad : flagLoad
		});
		var purviewGrid = new Ext.grid.GridPanel({
			id :'rolesExit',
			// region:'center',
			title : "权限添加面板<font color=red>【可以一次添加多个权限也可是删除多个权限】</font>",
			store : purviewStore,
			cm : roleCM,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			height : 220,
			width : 461,
			autoScroll : true,
			// y : 180,
			tbar : ['', {
				text : '添加权限',
				pressed : true,
				handler : this.addPurviewGrid,
				scope : this,
				iconCls : 'add'
			}, '-', {
				text : '删除权限',
				pressed : true,
				handler : function() {
					var record = purviewGrid.getSelectionModel().getSelected();
					var records = purviewGrid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("提示", "请先选择要删除的行!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					} else {
						Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(
								btn) {
							if (btn == 'yes') {
								if (records) {
									for (var i = 0; i < records.length; i++) {
										purviewGrid.store.remove(records[i]);
										purviewGrid.editRemovedIds += records[i]
												.get("id")
												+ ",";
									}
								}
							}
						}, this)
					}

				},
				scope : this,
				iconCls : 'remove'
			}],
			anchor : '0 -150'
		});
		return purviewGrid;
	},
	// 增加角色++++++++++++++++++++++++++++++++++++++++++++(方法3)
	addRole : function() {
		var rightListGrid = this.purviewGrid("0");
		var roleForm = this.baseRolsMessage("0");
		var userDetailWin = new Ext.Window({
			title : '增加角色【<font color=red>根据不同的隶属部门选择不同的可用菜单模板</font>】',
			modal : true,
			height : 468,
			width : 475,
			layout : 'table',
			layoutConfig : {
				columns : 1
			},
			buttons : [{
				text : '添加',
				handler : function() {
					if (Ext.getCmp('baseFormRose').getForm().isValid()) {
	                    var gP = Ext.getCmp("rolesExit").getStore().getRange(0,
	                             Ext.getCmp("rolesExit").getStore().getCount());
						var authIds = new Array();
						for (var i = 0; i < gP.length; i++) {
							authIds[i] = gP[i].data.id;
						}                                
						var name = Ext.getCmp('RolesName').getValue();
						var descn = Ext.getCmp('RolesDescn').getValue();
						var request = Ext.getCmp('baseFormRose').form.getValues(false);
						request.authId = authIds;
						request.name = unicode(name);
						request.descn = unicode(descn);
						//alert(Ext.encode(request));
						Ext.Ajax.request({
							url : webContext
									+ '/sysManage/roleAction.do?methodCall=saveRoles',
							method : 'POST',
							params : request,
							success : function(result, options) {
								Ext.getCmp('baseId').store.reload();
                                userDetailWin.close();                                                
							},
							failure : function(response, options) {
								ReturnValue = Ext.MessageBox.alert("警告","保存角色授权失败！");
							},
							scope : this
						});

					 }else{ Ext.Msg.alert("提示","带红线的为必填项"); }
				},
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					userDetailWin.close();
				},
				text : '关闭',
				scope : this
			}],
			items : [roleForm, rightListGrid]
		});
		userDetailWin.show();

	},
	// 修改角色信息+++++++++++++++++++++++++++++++++++++++++++++(方法4)
	modify : function() {
		var record = Ext.getCmp('baseId').getSelectionModel().getSelected();
		var records = Ext.getCmp('baseId').getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行!");
			return;
		}
		var id = record.get("id");
		var rightEditGrid = this.purviewGrid(id);
		// var roleDetailForm = new RoleDetailForm();
		var roleEditForm = this.baseRolsMessage(id);
		var userDetailWin = new Ext.Window({
			title : '修改角色【<font color=red>根据不同的隶属部门选择不同的可用菜单模板</font>】',
			modal : true,
			height : 468,
			width : 475,
			layout : 'table',
			layoutConfig : {
				columns : 1
			},
			buttons : [{
				xtype : 'button',
				text : '添加',
				handler : function() {
					if (Ext.getCmp('baseFormRose').getForm().isValid()) {
						var gP = Ext.getCmp("rolesExit").getStore().getRange(0,
								 Ext.getCmp("rolesExit").getStore().getCount());
						var authIds = new Array();
						for (var i = 0; i < gP.length; i++) {
							authIds[i] = gP[i].data.id;
						}
						var name = Ext.getCmp('RolesName').getValue();
						var descn = Ext.getCmp('RolesDescn').getValue();
						var request = Ext.getCmp('baseFormRose').form.getValues(false);
						request.authId = authIds;
						request.name = unicode(name);
						request.descn = unicode(descn);
                        request.id = id;
						Ext.Ajax.request({
							url : webContext
									+ '/sysManage/roleAction.do?methodCall=modifyRole',
							method : 'POST',
							params : request,
							success : function(result, options) {
								Ext.getCmp('baseId').store.reload();
								userDetailWin.close();
							},
							failure : function(response, options) {
								ReturnValue = Ext.MessageBox.alert("警告","保存角色授权失败！");
							},
							scope : this
						});

					} else {
						Ext.Msg.alert("提示", "带红线的为必填项");
					}
				},
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					userDetailWin.close();
				},
				text : '关闭',
				scope : this
			}],
			items : [roleEditForm, rightEditGrid]
		});
		userDetailWin.show();
	},
	// 删除数据++++++++++++++++++++++++++++++++++++++++++++++++++++++(方法5)
	removeData : function() {
		var record = Ext.getCmp('baseId').getSelectionModel().getSelected();
		var records =Ext.getCmp('baseId').getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		var roleIds = new Array();
		for (var i = 0; i < records.length; i++) {
			roleIds[i] = records[i].data.id;
		}

		var m = Ext.MessageBox.confirm("删除提示", "是否真的要删除数据？", function(ret) {
			if (ret == "yes") {
				Ext.Ajax.request({
					url : webContext
							+ '/sysManage/roleAction.do?methodCall=removeRoles',
					params : {
						'id' : roleIds
					},
					method : 'POST',
					timeout : 100000,
					success : function(response) {
						var r = Ext.decode(response.responseText);
						if (!r.success)
							Ext.Msg.alert("提示信息", "数据删除失败，由以下原因所致：<br/>"
									+ (r.errors.msg ? r.errors.msg : "未知原因"));
						else {
							Ext.Msg.alert("提示信息", "成功删除数据!", function() {
									// this.store.reload();
								}, this);
						}
						this.store.reload();
					},
					scope : this
				});
			}
		}, this);
	},
	// 新增权限列表+++++++++++++++++++++++++++++++++++++++++++++(方法6)
	addPurviewGrid : function() {
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		});
		var columns = [sm, {
			header : '<font color=green>权限名称</font>',
			width : 420,
			dataIndex : 'name'
		}, {
			hidden : true,
			dataIndex : "id",
			// name : 'authId',
			width : 10
		}];
		var roleCM = new Ext.grid.ColumnModel(columns);
		var purviewStore = new Ext.data.JsonStore({
			root : 'auths',
			url : webContext+ '/sysManage/roleAction.do?methodCall=addAuthorization',
			fields : ['name','id'],
			autoLoad : true
		});
		this.addgrid = new Ext.grid.GridPanel({
			id : 'addRoleGrid',
			store : purviewStore,
			cm : roleCM,
			sm : sm,
			height : 290,
			width : 461,
			autoScroll : true,
			anchor : '0 -150'
		});
		var userDetailWin = new Ext.Window({
			title : '添加权限【<font color=red>每一个角色可以添加多个权限</font>】',
			modal : true,
			height : 350,
			width : 475,
			layout : 'table',
			layoutConfig : {
				columns : 1
			},
			buttons : [{
				xtype : 'button',
				text : '添加',
				handler : function() {
					var record = this.addgrid.getSelectionModel().getSelected();
					if (!record) {
						Ext.Msg.alert("提示", "请先选择要添加的行!");
						return;
					}
					var records = this.addgrid.getSelectionModel().getSelections();
					var gP = Ext.getCmp("rolesExit").getStore().getRange(0,
							Ext.getCmp("rolesExit").getStore().getCount());
					var tempName = "";
					for (var i = 0; i < records.length; i++) {
						var id = records[i].get("id");
						var name = records[i].get("name");
						var flag = 1;
						// 为了去掉重复角色
						for (j = 0; j < gP.length; j++) {
							if (id == gP[j].data.id) {
								tempName = name;
								flag = 0;
							}
						}
						if (flag == 0) {
							Ext.Msg.alert("提示", "角色：<font color=red>"+ tempName + "</font>已经存在不能重复添加");
							continue;
						}
						var data = [{
							id : 'id',
							mapping : 'id'
						}, {
							name : 'name',
							mapping : 'name'
						}]
						var gridRecord = Ext.data.Record.create(data);
						var dataRecord = new gridRecord({
							id : id,
							name : name
						});
						Ext.getCmp("rolesExit").store.add([dataRecord]);
					}
					userDetailWin.close();
				},
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					userDetailWin.close();
				},
				text : '关闭',
				scope : this
			}],
			items : [this.addgrid]
		});
		userDetailWin.show();
	},

	// 4列form布局
	splitBySu : function(data) {
		var labellen = 100;
		var itemlen = 330;
		var throulen = 400;
		if (Ext.isIE) {
			throulen = 550;
		} else {
			throulen = 560;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		// alert(this.dataId);
		for (i = 0; i < data.length; i++) {

			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			// alert(data[i].width+data[i].name);
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// 通栏
					// alert("data");
					if ((i - hid + longitems) % 2 == 1) {// 在右侧栏，前一栏贯通
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// 多占一栏
						longitems++;
					}
					data[i].colspan = 3;// 本栏贯通
					data[i].width = 330;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {// 正常长度，半栏
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			// alert(data[i].width+data[i].name);
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
	// 重置方法
	reset : function() {
		Ext.getCmp("userQueryForm").form.reset();
	},
	// 查询按钮对象
	search : function() {
		var param = Ext.getCmp('userQueryForm').form.getValues(false);
		var param = {
			name : unicode(param.rolsName)
		};
		param.methodCall = 'listUsers';
		//this.formValue = param;
		//Ext.getCmp('baseId').bbar.formValue = this.formValue;
		param.start = 0;
		Ext.getCmp('baseId').store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		Ext.getCmp('baseId').store.removeAll();
		Ext.getCmp('baseId').store.load({
			params : param
		});
	},
	// 查询面板
	queryPanel : function() {
		this.rolsName = new Ext.form.TextField({
			xtype : 'textfield',
			id : 'rolsName',
			// emptyText : " 可以输入如：张三",
			name : 'rolsName',
			width : 200
		});
		this.userQueryForm = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			keys : [{
				key : [10, 13],
				fn : this.search
			}],                  
			id : "userQueryForm",
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "<font color=green>用户查询列表</font>",
			items : [{
				html : "角色名称:&nbsp;",
				cls : 'common-text',
				style : 'width:150;text-align:right'
			}, this.rolsName]
		});
		return this.userQueryForm;
	},
	initComponent : function() {
		this.searchForm = this.queryPanel();
		var csm = new Ext.grid.CheckboxSelectionModel();
		this.storeMapping = ['name', 'descn', 'id', 'department', 'deptMenuTemplate','deptMenu'];
		this.cm = new Ext.grid.ColumnModel([csm, {
			header : "角色名称",
			sortable : true,
			hideable : false,
			dataIndex : "name",
			width : 150,
			editor : new Ext.form.TextField()
		}, {
			header : "id",
			sortable : true,
			hidden : true,
			dataIndex : "id"
		}, {
			header : "隶属部门",
			sortable : true,
			hidden : false,
			width : 150,
			dataIndex : "department"
		}, {
			header : "部门菜单(新)",
			sortable : true,
			hidden : false,
			width : 150,
			dataIndex : "deptMenu"
		},  {
			header : "描述",
			sortable : true,
			hideable : false,
			dataIndex : "descn",
			width : 350,
			editor : new Ext.form.TextField()
		}]);
		RoleManagePanel.superclass.initComponent.call(this);
		// 数据
		this.store = new Ext.data.JsonStore({
			id : "id",
			url : webContext + '/sysManage/roleAction.do?methodCall=querylistRoles',
			root : "roles",
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 3000000,
			fields : this.storeMapping
		});

		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		// 分页
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		// 工具栏
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			id : 'baseId',
			store : this.store,
			cm : this.cm,
			sm : csm,
			width : 800,
			trackMouseOver : false,
			loadMask : true,
			y : 0,
			anchor : '0 -35',
			clicksToEdit : 1,
			tbar : [{
				text : '查询',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '&nbsp;|&nbsp;', {
				text : '新建角色',
				pressed : true,
				handler : this.addRole,
				scope : this,
				iconCls : 'add'
			}, '&nbsp;|&nbsp;', {
				text : '修改',
				pressed : true,
				id : 'modify',
				handler : this.modify,
				scope : this,
				iconCls : 'edit'
			}, '&nbsp;|&nbsp;', {
				text : '删除',
				pressed : true,
				id : 'delete',
				handler : this.removeData,
				scope : this,
				iconCls : 'delete'
			}, '&nbsp;|&nbsp;', {
				text : '重置',
				pressed : true,
				handler : function() {
					Ext.getCmp("userQueryForm").form.reset();
				},
				scope : this,
				iconCls : 'reset'
			}],
			bbar : this.pageBar
		});

		this.grid.on("rowdblclick", this.modify, this);
		this.add(this.searchForm);
		this.add(this.grid);
		var param = {
			'methodCall' : 'listRoles',
			'start' : 0
		};
		//this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();
		this.store.load({
			params : param
		});

	}
});