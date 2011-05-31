UserLockManagePanel = Ext.extend(Ext.Panel, {
	id : "userLockManagePanel",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	// 编码转换+++++++++++++++++++++++++++++++++++++++(方法1)
	unicodesu : function(s) {
		var len = s.length;
		var rs = "";
		for (var i = 0; i < len; i++) {
			var k = s.substring(i, i + 1);
			rs += "&#" + s.charCodeAt(i) + ";";
		}
		return rs;
	},
	// 当用户填写完信息保存之前最后一次在页面上验证所填写的信息的合法性+++++++++++++++++++(方法2)
	lastValisateUserMessage : function() {
		var realName = this.realName.getValue();
		var userName = this.userName.getValue();
		var password = this.password.getValue();
		var email = this.email.getValue();
		var department = this.department.getValue();
		// alert("1"+realName+"2"+userName+"3"+password+"4"+email+"4"+department);
		if (realName == "") {
			Ext.MessageBox.alert("提示", "用户姓名不能为空");
			return false;
		}
		if (userName == "") {
			Ext.MessageBox.alert("提示", "用户名不能为空");
			return false;
		}
		if (password == "") {
			Ext.MessageBox.alert("提示", "密码不能为空");
			return false;
		}
		if (email == "") {
			Ext.Msg.alert("提示", "电子邮件不能为空,格式如：zhangsan@domain.com");
			return false;
		}
		if (email != 0) {
			var len = email.length;
			var index1 = email.indexOf('@');
			var index2 = email.indexOf('.');
			if (index1 == 0 || index1 == -1 || index2 == -1 || index1 > index2
					|| index2 - index1 == 1 || index2 == len - 1) {
				Ext.Msg.alert("提示", "电子邮件格式不正确,例如：zhangsan@domain.com");
				return false;
			}
		}
		if (department == "") {
			Ext.MessageBox.alert("提示", "隶属部门必须选择");
			return false;
		}
		if (userName != "" && userName.match(/[^0-9A-Za-z]/g)) {
			Ext.MessageBox.alert("提示", "用户名不能输入中文");
			return false;
		}
		return true;
	},
	// 角色面板初始化++++++++++++++++++++++++++++++++++++(方法3)
	creatRole : function() {
		var department = new Ext.form.ComboBox({
			xtype : 'combo',
			id : 'department2',
			defaultParam : '',
			allowBlank : false,
			colspan : 3,
			hiddenName : 'department',
			fieldLabel : '部门',
			displayField : 'departName',
			valueField : 'id',
			emptyText : '请选择隶属部门',
			triggerAction : 'all',
			name : 'department2',
			width : 380,
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
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/sysManage/userMangeAction.do?methodCall=findDeptForCombo',
				fields : ['id', 'departName'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['departName'] == undefined) {
							opt.params['departName'] = Ext
									.getCmp('departmentforSave')
									.unicode(Ext.getCmp('departmentforSave').defaultParam);
						}
					}
				}
			}),
			pageSize : 10,
			listeners : {
				'blur' : function(combo) {
					if (combo.getRawValue() == '') {
						combo.reset();
					}
				},
				'beforequery' : function(queryEvent) {
					var param = queryEvent.query;
					this.defaultParam = param;
					param = this.unicode(param);
					this.store.load({
						params : {
							departName : param,
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('departmentforSave').validate();
						}
					});
					return true;
				}
			},
			unicode : function(s) {
				var len = s.length;
				var rs = "";
				for (var i = 0; i < len; i++) {
					var k = s.substring(i, i + 1);
					rs += "&#" + s.charCodeAt(i) + ";";
				}
				return rs;
			}
		});
		// 注册事件
		department.on('select', this.OnBlurEvent, this);// 在下面方法7中构造了
		var departmentForm = new Ext.form.FormPanel({
			buttonAlign : 'center',
			layout : 'table',
			height : 'auto',
			labelAlign : "left",
			width : 503,
			frame : true,
			//collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			frame : true,
			items : [{
				html : "隶属部门：&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, department]
		});

		// 角色grid
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		});
		var columns = [sm, {
			header : 'id',
			hidden : true,
			width : 215,
			dataIndex : 'id',
			sortable : true
		}, {
			header : '角色名称',
			width : 180,
			dataIndex : 'name',
			sortable : true
		}, {
			header : '隶属部门',
			width : 180,
			dataIndex : 'department',
			sortable : true
		}, {
			header : '描述',
			width : 235,
			dataIndex : 'descn',
			sortable : true
		}];
		var roleCM = new Ext.grid.ColumnModel(columns);
		var roleStore2 = new Ext.data.JsonStore({
			root : 'roles',
			url : webContext
					+ '/sysManage/userMangeAction.do?methodCall=findRoleByDept',
			baseParams : {
				deptCode : '1101'
			},
			fields : ['name', 'department', 'descn', 'id'],
			autoLoad : true
		});
		var roleGrid = new Ext.grid.GridPanel({
			id : 'roleGrid2',
			title : "所有角色信息<font color=red>【可以一次添加多个角色】</font>",
			store : roleStore2,
			cm : roleCM,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			height : 210,
			width : 503,
			autoScroll : true,
			y : 40
		});
		var userDetailWin = new Ext.Window({
			title : '选择角色【<font color=red>通过相应的部门选择相应的角色，默认部门是上品折扣</font>】',
			modal : true,
			height : 320,
			width : 520,
			resizable : false,
			layout : 'absolute',
			buttons : [{
				xtype : 'button',
				text : '添加',
				handler : function() {
					var record = roleGrid.getSelectionModel().getSelected();
					if (!record) {
						Ext.Msg.alert("提示", "请先选择要添加的行!");
						return;
					}
					var records = roleGrid.getSelectionModel().getSelections();
					var gP = Ext.getCmp("roleGrid").getStore().getRange(0,
							Ext.getCmp("roleGrid").getStore().getCount());
					var tempName = "";
					for (var i = 0; i < records.length; i++) {
						var id = records[i].get("id");
						var name = records[i].get("name");
						var department = records[i].get("department");
						var descn = records[i].get("descn");
						var flag = 1;
						// 为了去掉重复角色
						for (j = 0; j < gP.length; j++) {
							if (id == gP[j].data.id) {
								tempName = name;
								flag = 0;
							}
						}
						if (flag == 0) {
							Ext.Msg.alert("提示", "角色：<font color=red>"
									+ tempName + "</font>已经存在不能重复添加");
							continue;
						}
						var data = [{
							id : 'id',
							mapping : 'id'
						}, {
							name : 'name',
							mapping : 'name'
						}, {
							name : 'department',
							mapping : 'department'
						}, {
							name : 'descn',
							mapping : 'descn'
						}]
						var gridRecord = Ext.data.Record.create(data);
						var dataRecord = new gridRecord({
							id : id,
							name : name,
							descn : descn
						});
						Ext.getCmp("roleGrid").store.add([dataRecord]);
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
			items : [departmentForm, roleGrid]
		});
		userDetailWin.show();
	},

	// 用户表单面板构造+++++++++++++++++++++++++++++++++(方法4)
	creatUserFormPanel : function(userId) {
		this.realName = new Ext.form.TextField({
			xtype : 'textfield',
			name : 'realName',
			emptyText : "张三四",
			id : 'realName',
			width : 180
		});
		this.userName = new Ext.form.TextField({
			xtype : 'textfield',
			name : 'userName',
			emptyText : "zhangsansi",
			id : 'userName',
			width : 180
		});
		this.password = new Ext.form.TextField({
			xtype : 'textfield',
			emptyText : "12zhangsani55",
			name : 'password',
			width : 180
		});
		this.email = new Ext.form.TextField({
			name : 'email',
			emptyText : "zhansan@*******.com",
			width : 180
		});
		this.telephone = new Ext.form.TextField({
			xtype : 'textfield',
			fieldLabel : '电话',
			emptyText : "123*******",
			name : 'telephone',
			width : 180
		});
		this.mobilePhone = new Ext.form.TextField({
			xtype : 'textfield',
			name : 'mobilePhone',
			emptyText : "150*********",
			width : 180
		});
		this.enabled = new Ext.form.Checkbox({
			name : 'enabled',
			anchor : '50%',
			align : 'center',
			// checked : true,
			colspan : 1,
			width : 50
		});
		this.specialUser = new Ext.form.Checkbox({
			name : 'specialUser',
			anchor : '50%',
			align : 'center',
			colspan : 1,
			// checked : false,
			width : 50
		});
		this.isLockUser = new Ext.form.Checkbox({
			name : 'isLock',
			anchor : '50%',
			align : 'center',
			colspan : 1,
			// checked : true,
			width : 50
		});
		this.department = new Ext.form.ComboBox({
			xtype : 'combo',
			id : 'departmentforSave',
			defaultParam : '',
			allowBlank : false,
			colspan : 3,
			hiddenName : 'department',
			fieldLabel : '部门',
			displayField : 'departName',
			valueField : 'id',
			emptyText : '请选择隶属部门',
			triggerAction : 'all',
			name : 'departmentforSave',
			width : 450,
			// validator : function(value) {
			// if (this.store.getCount() == 0 && this.getRawValue() != '') {
			// return false;
			// }
			// if (this.store.getCount() > 0) {
			// var valid = false;
			// this.store.each(function(r) {
			// var rowValue = r.data.departName;
			// if (rowValue == value) {
			// valid = true;
			// }
			// });
			// if (!valid) {
			// return false;
			// }
			// }
			// return true;
			// },
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/sysManage/userMangeAction.do?methodCall=findDeptForCombo',
				fields : ['id', 'departName'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['departName'] == undefined) {
							opt.params['departName'] = Ext
									.getCmp('departmentforSave')
									.unicode(Ext.getCmp('departmentforSave').defaultParam);
						}
					}
				}
			}),
			pageSize : 10,
			listeners : {
				'blur' : function(combo) {
					if (combo.getRawValue() == '') {
						combo.reset();
					}
				},
				'beforequery' : function(queryEvent) {
					var param = queryEvent.query;
					this.defaultParam = param;
					param = this.unicode(param);
					this.store.load({
						params : {
							departName : param,
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('departmentforSave').validate();
						}
					});
					return true;
				}
			},
			unicode : function(s) {
				var len = s.length;
				var rs = "";
				for (var i = 0; i < len; i++) {
					var k = s.substring(i, i + 1);
					rs += "&#" + s.charCodeAt(i) + ";";
				}
				return rs;
			}
		});
		// ///////////////////////////////////////////////////////////////////add
		// by lee for add platform start 2009.6.13
		// start/////////////////////////////////////////////////////////////
		this.platform = new Ext.form.ComboBox({
			xtype : 'combo',
			id : 'platformforSave',
			defaultParam : '',
			allowBlank : false,
			colspan : 3,
			hiddenName : 'platform',
			fieldLabel : '平台',
			displayField : 'platName',
			valueField : 'id',
			emptyText : '请选择隶属平台',
			triggerAction : 'all',
			name : 'platformforSave',
			width : 450,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/sysManage/userMangeAction.do?methodCall=findPlatForCombo',
				fields : ['id', 'platName'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id',
				listeners : {
					beforeload : function(store, opt) {
						if (opt.params['platName'] == undefined) {
							opt.params['platName'] = Ext
									.getCmp('platformforSave')
									.unicode(Ext.getCmp('platformforSave').defaultParam);
						}
					}
				}
			}),
			pageSize : 10,
			listeners : {
				'blur' : function(combo) {
					if (combo.getRawValue() == '') {
						combo.reset();
					}
				},
				'beforequery' : function(queryEvent) {
					var param = queryEvent.query;
					this.defaultParam = param;
					param = this.unicode(param);
					this.store.load({
						params : {
							platName : param,
							start : 0
						},
						callback : function(r, options, success) {
							Ext.getCmp('platformforSave').validate();
						}
					});
					return true;
				}
			},
			unicode : function(s) {
				var len = s.length;
				var rs = "";
				for (var i = 0; i < len; i++) {
					var k = s.substring(i, i + 1);
					rs += "&#" + s.charCodeAt(i) + ";";
				}
				return rs;
			}
		});
		// ///////////////////////////////////////////////////////////////////add
		// by lee for add platform start 2009.6.13
		// end/////////////////////////////////////////////////////////////
		this.enabled.on("check", function(field) {

			var value = field.getValue();
			if (value == false) {
				this.enabledValue.setValue(0);
				return;
			} else if (value == true) {
				this.enabledValue.setValue(1);
				return;
			}
		}, this);
		this.enabledValue = new Ext.form.Hidden({
			id : 'enabledHid',
			name : 'enabled',
			value : 0
		});
		this.isLockUser.on("check", function(field) {
			var value = field.getValue();
			if (value == false) {
				this.isLockUserValue.setValue(0);
				return;
			} else if (value == true) {
				this.isLockUserValue.setValue(1);
				return;
			}
		}, this);
		this.isLockUserValue = new Ext.form.Hidden({
			id : 'isLockHid',// 这个id很重要不能和上面的重了，否则就无法带回真正的值了
			name : 'isLock',
			value : 0
		});
		this.specialUser.on("check", function(field) {
			var value = field.getValue();
			if (value == false) {
				this.specialUserValue.setValue(0);
				return;
			} else if (value == true) {
				this.specialUserValue.setValue(1);
				return;
			}
		}, this);
		this.specialUserValue = new Ext.form.Hidden({
			id : 'specialUserHid',// 这个id很重要不能和上面的重了，否则就无法带回真正的值了
			name : 'specialUser',
			value : 0
		});
		// 表单面板
		this.form = new Ext.form.FormPanel({
			buttonAlign : 'center',
			layout : 'table',
			height : 'auto',
			labelAlign : "left",
			width : 'auto',
			frame : true,
			//collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			frame : true,
			reader : new Ext.data.JsonReader({
				root : 'user',
				successProperty : '@success'
			}, [{
				name : 'realName',
				mapping : 'realName'
			}, {
				name : 'userName',
				mapping : 'userName'
			}, {
				name : 'password',
				mapping : 'password'
			}, {
				name : 'email',
				mapping : 'email'
			}, {
				name : 'telephone',
				mapping : 'telephone'
			}, {
				name : 'mobilePhone',
				mapping : 'mobilePhone'
			}, {
				name : 'enabled',
				mapping : 'enabled'
			}, {
				name : 'specialUser',
				mapping : 'specialUser'
			}, {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'departmentforSave',
				mapping : 'name'
			}, {
				name : 'platformforSave',
				mapping : 'platName'
			}, {
				name : 'isLock',
				mapping : 'isLock'
			}]),
			items : [{
				html : "真实姓名:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.realName, {
				html : "用户名:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.userName, {
				html : "密码:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.password, {
				html : "电子邮件:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.email, {
				html : "电话:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.telephone, {
				html : "手机:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.mobilePhone, {
				html : "隶属部门：&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.department, {
				html : "隶属平台：&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.platform, {
				layout : 'table',
				height : 30,
				colspan : 3,
				width : "auto",
				layoutConfig : {
					columns : 6
				},
				items : [{
					html : "是否可用:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				}, this.enabled, {
					html : "是否特殊用户:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				}, this.specialUser, {
					html : "是否锁定:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				}, this.isLockUser, this.enabledValue, this.specialUserValue,
						this.isLockUserValue]
			}]
		});
		if (userId != "0") {
			this.form.load({
				url : webContext
						+ '/sysManage/userMangeAction.do?methodCall=findUserForm&id='
						+ userId,
				timeout : 30,
				success : function(action, form) {
				}
			});
		}

		return this.form;
	},

	// 构造角色列表面板++++++++++++++++++++++++++++++++++++++++++++(方法5)
	roleListGrid : function(UserId) {
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		});
		var columns = [sm, {
			header : 'id',
			hidden : true,
			width : 235,
			dataIndex : 'id',
			sortable : true
		}, {
			header : '角色名称',
			width : 150,
			dataIndex : 'name',
			sortable : true
		}, {
			header : '隶属部门',
			width : 150,
			dataIndex : 'department',
			sortable : true
		}, {
			header : '描述',
			width : 150,
			dataIndex : 'descn',
			sortable : true
		}];
		var roleCM = new Ext.grid.ColumnModel(columns);
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("POST", webContext
				+ '/sysManage/userMangeAction.do?methodCall=findRoles', false);
		conn.send(null);
		var data = Ext.util.JSON.decode(conn.responseText);
		var roleStore = new Ext.data.JsonStore({
			root : 'roles',
			url : webContext
					+ '/sysManage/userMangeAction.do?methodCall=findRolesByUserId',
			baseParams : {
				userId : UserId
			},
			fields : ['name', 'department', 'descn', 'id'],
			autoLoad : true
		});
		this.grid = new Ext.grid.GridPanel({
			id : 'roleGrid',
			title : "角色添加面板<font color=red>【可以一次添加多个角色也可是删除多个角色】</font>",
			store : roleStore,
			cm : roleCM,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			height : 152,
			width : 620,
			autoScroll : true,
			y : 150,
			tbar : ['   ', {
				text : '添加角色',
				pressed : true,
				handler : this.creatRole,
				scope : this,
				iconCls : 'add'
			}, '-', {
				text : '删除角色',
				pressed : true,
				handler : function() {
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
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
										this.grid.store.remove(records[i]);
										this.grid.editRemovedIds += records[i]
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
		// this.grid.store.removeAll();
		return this.grid;
	},

	// 新建用户窗口++++++++++++++++++++++++++++++++++++++++++++++++++(方法6)
	addUser : function() {
		var userForm = this.creatUserFormPanel("0"); // 上面方法4中构造的
		var roleListGrid = this.roleListGrid(); // 上面方法5中构造的
		var userDetailWin = new Ext.Window({
			title : '新建用户<font color=red>【请按照文本框中灰色模板填写相应信息】</font>',
			modal : true,
			height : 400,
			width : 620,
			autoSrolll : true,
			resizable : false,
			layout : 'table',
			layoutConfig : {
				columns : 1
			},
			buttons : [{
				xtype : 'button',
				id : "saveNewUserButton",
				handler : function() {
					Ext.getCmp("saveNewUserButton").disable();
					if (!this.lastValisateUserMessage()) {// 上面方法2中
						Ext.getCmp("saveNewUserButton").enable();
						return;
					}
					var records = roleListGrid.getStore().getRange(0,
							roleListGrid.getStore().getCount());
					var roles = new Array();
					for (var i = 0; i < records.length; i++) {
						roles[i] = records[i].data.id;
					}
					var realName = this.realName.getValue();
					var userName = this.userName.getValue();
					var password = this.password.getValue();
					var email = this.email.getValue();
					var telephone = this.telephone.getValue();
					var mobilePhone = this.mobilePhone.getValue();
					var value = this.enabledValue.getValue();
					var specialUser = this.specialUserValue.getValue();
					var department = this.department.getValue();

					var platform = this.platform.getValue();// //////add by lee
					// for add platform
					// in 090613

					var isLock = this.isLockUserValue.getValue();
					var request = {
						roleId : roles,
						realName : unicode(realName),
						userName : userName,
						password : password,
						email : email,
						telephone : telephone,
						mobilePhone : mobilePhone,
						enabled : value,
						specialUser : specialUser,
						department : department,
						platform : platform,// //////add by lee for add platform
						// in 090613
						isLock : isLock
					};
					// alert(Ext.encode(request))
					Ext.Ajax.request({
						url : webContext
								+ '/sysManage/userMangeAction.do?methodCall=saveUsers',
						method : 'POST',
						params : request,
						success : function(response, options) {
							var flagSign = eval("(" + response.responseText
									+ ")").flagSign;
							if (flagSign == "exitIsLock") {
								var userNameTring = eval("("
										+ response.responseText + ")").userNameTring;
								ExtExt.MessageBox.alert("警告", userNameTring
										+ "已经被锁定");
								Ext.getCmp("saveNewUserButton").enable();
							} else if (flagSign == "exit") {
								var userNameTring = eval("("
										+ response.responseText + ")").userNameTring;
								ExtExt.MessageBox.alert("警告", userNameTring
										+ "已经存在");
								Ext.getCmp("saveNewUserButton").enable();
							} else {
								Ext.MessageBox.alert("提示", "成功保存用户信息！");
								userDetailWin.close();
								Ext.getCmp('mainGrid').store.reload();

							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("警告", "保存用户信息失败！");
							Ext.getCmp("saveNewUserButton").enable();
						},
						scope : this
					});
				},
				text : '保存',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					userDetailWin.close();
				},
				text : '关闭',
				scope : this
			}],
			items : [userForm, roleListGrid]
		});
		userDetailWin.show();
	},
	// 给部门添加事件用来查找角色++++++++++++++++++++++++++++++++++++(方法7)
	OnBlurEvent : function() {
		var departmentTemp = Ext.getCmp("department2").getValue();
		Ext.getCmp("roleGrid2").store.baseParams.deptCode = departmentTemp;
		Ext.getCmp("roleGrid2").store.removeAll();
		Ext.getCmp("roleGrid2").store.reload();
	},

	// 修改用户信息+++++++++++++++++++++++++++++++++++++++++++++++++(方法8)
	modify : function(grid) {

		var record = Ext.getCmp("mainGrid").getSelectionModel().getSelected();
		var records = Ext.getCmp("mainGrid").getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行!");
			return;
		}
		var id = record.get("id");
		var departmentPage = record.get("department");
		var platformPage = record.get("platform");
		var userForm = this.creatUserFormPanel(id); // 上面方法4中构造的
		var roleListGrid = this.roleListGrid(id); // 上面方法5中构造的
		var userDetailWin = new Ext.Window({
			title : '修改用户<font color=red>【请按照固定格式修改相应信息】</font>',
			modal : true,
			height : 400,
			width : 640,
			autoSrolll : true,
			resizable : false,
			layout : 'table',
			layoutConfig : {
				columns : 1
			},
			buttons : [{
				xtype : 'button',
				id : "modifyUserButton",
				handler : function() {
					Ext.getCmp("modifyUserButton").disable();
					if (!this.lastValisateUserMessage()) {// 上面方法2中
						Ext.getCmp("modifyUserButton").enable();
						return;
					}
					var records = roleListGrid.getStore().getRange(0,
							roleListGrid.getStore().getCount());
					var roles = new Array();
					for (var i = 0; i < records.length; i++) {
						roles[i] = records[i].data.id;
					}
					var realName = this.realName.getValue();
					var userName = this.userName.getValue();
					var password = this.password.getValue();
					var email = this.email.getValue();
					var telephone = this.telephone.getValue();
					var mobilePhone = this.mobilePhone.getValue();
					// var value = this.enabledValue.getValue();
					// var specialUser = this.specialUserValue.getValue();
					var department = this.department.getValue();
					if (departmentPage == department) {
						department = "callback";
					}
					// add by lee for add platform in 090613 start
					// //////////////////////
					var platform = this.platform.getValue();
					if (platformPage == platform) {
						platform = "callback";
					}
					// add by lee for add platform in 090613 end
					// //////////////////////
					// var isLock = this.isLockUserValue.getValue();
					var value = this.enabled.getValue();
					var enabled = 0;
					if (value == true) {
						enabled = 1;
					} else {
						enabled = 0;
					}
					var sValue = this.specialUser.getValue();
					var specialUser = 0;
					if (sValue == true) {
						specialUser = 1;
					} else {
						specialUser = 0;
					}
					var isLockValues = this.isLockUser.getValue();
					var isLockValue = 0;
					if (isLockValues == true) {
						isLockValue = 1;
					} else {
						isLockValue = 0;
					}
					var request = {
						roleId : roles,
						realName : unicode(realName),
						userName : userName,
						password : password,
						email : email,
						telephone : telephone,
						mobilePhone : mobilePhone,
						enabled : enabled,
						specialUser : specialUser,
						department : department,
						platform : platform,// add by lee for add platform in
						// 090613
						isLock : isLockValue,
						userId : id,
						removeIds : this.grid.editRemovedIds
					};
					// alert(Ext.encode(request))
					Ext.Ajax.request({
						url : webContext
								+ '/sysManage/userMangeAction.do?methodCall=modifyUser',
						method : 'POST',
						params : request,
						success : function(response, options) {
							var flagSign = eval("(" + response.responseText
									+ ")").flagSign;
							if (flagSign == "exitIsLock") {
								var userNameTring = eval("("
										+ response.responseText + ")").userNameTring;
								ExtExt.MessageBox.alert("警告", userNameTring
										+ "已经被锁定");
								Ext.getCmp("modifyUserButton").enable();
							} else if (flagSign == "exit") {
								var userNameTring = eval("("
										+ response.responseText + ")").userNameTring;
								ExtExt.MessageBox.alert("警告", userNameTring
										+ "已经存在");
								Ext.getCmp("modifyUserButton").enable();
							} else {
								Ext.MessageBox.alert("提示", "成功保存用户信息！");
								userDetailWin.close();
								Ext.getCmp('mainGrid').store.reload();
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("警告", "保存用户信息失败！");
							Ext.getCmp("modifyUserButton").enable();
						},
						scope : this
					});
				},
				text : '保存',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					userDetailWin.close();
				},
				text : '关闭',
				scope : this
			}],
			items : [userForm, roleListGrid]
		});
		userDetailWin.show();
	},
	// 删除用户，相当于被锁定+++++++++++++++++++++++++++++++++++++++++++++++++++(方法9)
	removeData : function() {
		var record = Ext.getCmp("mainGrid").getSelectionModel().getSelected();
		var records = Ext.getCmp("mainGrid").getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		var ids = new Array();
		for (var i = 0; i < records.length; i++) {
			ids[i] = records[i].data.id;
		}

		var m = Ext.MessageBox.confirm("删除提示", "是否真的要删除数据？一旦删除,不可恢复!",
				function(ret) {
					if (ret == "yes") {
						Ext.Ajax.request({
							url : webContext
									+ '/sysManage/userMangeAction.do?methodCall=removeUsers',
							params : {
								'id' : ids
							},
							method : 'POST',
							timeout : 100000,
							success : function(response) {
								var r = Ext.decode(response.responseText);
								if (!r.success)
									Ext.Msg.alert("提示信息",
											"数据删除失败，由以下原因所致：<br/>"
													+ (r.errors.msg
															? r.errors.msg
															: "未知原因"));
								else {
									Ext.Msg.alert("提示信息", "成功删除数据!",
											function() {
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

	reset : function() {
		Ext.getCmp("userQueryForm").form.reset();
	},
	// 查询按钮对象
	search : function() {
		var param = this.userQueryForm.form.getValues(false);
		var param = {
			userName : this.unicodesu(param.userName),
			realName : this.unicodesu(param.realName)
		};
		param.methodCall = 'listUsers';
//		this.formValue = param;
//		this.pageBar.formValue = this.formValue;
		param.start = 0;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},

	// 查询面板
	queryPanel : function() {
		this.realName = new Ext.form.TextField({
			xtype : 'textfield',
			id : 'realNamequery',
			// emptyText : " 可以输入如：张三",
			name : 'realName',
			width : 200
		});

		this.userName = new Ext.form.TextField({
			xtype : 'textfield',
			// emptyText : " 可以输入如：zhangsan",
			id : 'userNamequery',// id一定不能重，重了许多功能都不好用，例如重置
			name : 'userName',
			width : 200
		});

		this.userQueryForm = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			id : "userQueryForm",
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "<font color=green>用户查询列表</font> _<font color=red> (左侧为真实姓名如：张三，右侧为登陆名如：zhangsan)</font>",
			items : [{
				html : "真实姓名:&nbsp;",
				cls : 'common-text',
				style : 'width:150;text-align:right'
			}, this.realName, {
				html : "用户名:&nbsp;",
				cls : 'common-text',
				style : 'width:150;text-align:right'
			}, this.userName]
		});
		return this.userQueryForm;
	},

	initComponent : function() {
		this.searchForm = this.queryPanel();
		var csm = new Ext.grid.CheckboxSelectionModel();
		this.storeMapping = ['realName', 'userName', 'password', 'email',
				'enabled', 'specialUser', 'isLock', 'department', 'platform',
				'id'];
		this.cm = new Ext.grid.ColumnModel([csm, {
			header : "姓名",
			sortable : true,
			hideable : false,
			dataIndex : "realName",
			width : 100,// 090613lee将150修改为100
			editor : new Ext.form.TextField()
		}, {
			header : "用户名",
			sortable : true,
			hideable : false,
			dataIndex : "userName",
			width : 100,
			editor : new Ext.form.TextField()
		}, {
			header : "密码",
			sortable : true,
			hideable : false,
			dataIndex : "password",
			width : 100,
			editor : new Ext.form.TextField()
		}, {
			header : "电子邮件",
			sortable : true,
			hideable : false,
			dataIndex : "email",
			width : 200,// 090613lee将220修改为200
			editor : new Ext.form.TextField()
		}, {
			header : "隶属部门",
			sortable : true,
			hideable : false,
			dataIndex : "department",
			width : 150,// 090613lee将220修改为150
			editor : new Ext.form.TextField()
		}, {
			header : "隶属平台",
			sortable : true,
			hideable : false,
			dataIndex : "platform",
			width : 150,// 090613lee将220修改为150
			editor : new Ext.form.TextField()
		}, {
			header : "是否可用",
			sortable : true,
			hideable : false,
			dataIndex : "enabled",
			renderer : function(value) {
				if (value == "是") {
					value = '<span style="color:red;">' + value + '</span>';
				} else {
					value = '<span style="color:green;">' + value + '</span>';
				}
				return value;
			},
			width : 100,
			editor : new Ext.form.TextField()
		}, {
			header : "是否特殊用户",
			sortable : true,
			hideable : false,
			dataIndex : "specialUser",
			width : 100,
			renderer : function(value) {
				if (value == "是") {
					value = '<span style="color:red;">' + value + '</span>';
				} else {
					value = '<span style="color:green;">' + value + '</span>';
				}
				return value;
			},
			editor : new Ext.form.TextField()
		}, {
			header : "是否被锁定",
			sortable : true,
			hideable : false,
			dataIndex : "isLock",
			width : 100,
			renderer : function(value) {
				if (value == "是") {
					value = '<span style="color:red;">' + value + '</span>';
				} else {
					value = '<span style="color:green;">' + value + '</span>';
				}
				return value;
			},
			editor : new Ext.form.TextField()
		}, {
			header : "id",
			sortable : true,
			hidden : true,
			dataIndex : "id"
		}]);

		// 数据
		this.store = new Ext.data.JsonStore({
			id : "id",
			url : webContext
					+ '/sysManage/userMangeAction.do?methodCall=queryAllLockUserByParameter',
			root : "users",
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 3000000,
			fields : this.storeMapping
		});
		UserLockManagePanel.superclass.initComponent.call(this);
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
			store : this.store,
			cm : this.cm,
			id : "mainGrid",
			sm : csm,
			title : "<font color=blue>用户详细信息列表</font>[<font color=red>双击可进行修改</font>]",
			trackMouseOver : false,
			loadMask : true,
			y : 35,
			anchor : '0 -35',
			clicksToEdit : 1,
			tbar : [{
				text : '查询用户',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '&nbsp;|&nbsp;', {
				text : '新建用户',
				pressed : true,
				handler : this.addUser,
				scope : this,
				iconCls : 'add'
			}, '&nbsp;|&nbsp;', {
				text : '修改',
				pressed : true,
				id : 'modify',
				scope : this,
				handler : this.modify,
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

		this.searchForm.height = "100";
		this.searchForm.width = "1000";
		this.grid.on("rowdblclick", this.modify, this);
		this.add(this.searchForm);
		this.add(this.grid);

		var param = {
			'methodCall' : 'listUsers',
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
