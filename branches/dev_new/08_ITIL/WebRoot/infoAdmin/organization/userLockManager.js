UserLockManagePanel = Ext.extend(Ext.Panel, {
	id : "userLockManagePanel",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	// ����ת��+++++++++++++++++++++++++++++++++++++++(����1)
	unicodesu : function(s) {
		var len = s.length;
		var rs = "";
		for (var i = 0; i < len; i++) {
			var k = s.substring(i, i + 1);
			rs += "&#" + s.charCodeAt(i) + ";";
		}
		return rs;
	},
	// ���û���д����Ϣ����֮ǰ���һ����ҳ������֤����д����Ϣ�ĺϷ���+++++++++++++++++++(����2)
	lastValisateUserMessage : function() {
		var realName = this.realName.getValue();
		var userName = this.userName.getValue();
		var password = this.password.getValue();
		var email = this.email.getValue();
		var department = this.department.getValue();
		// alert("1"+realName+"2"+userName+"3"+password+"4"+email+"4"+department);
		if (realName == "") {
			Ext.MessageBox.alert("��ʾ", "�û���������Ϊ��");
			return false;
		}
		if (userName == "") {
			Ext.MessageBox.alert("��ʾ", "�û�������Ϊ��");
			return false;
		}
		if (password == "") {
			Ext.MessageBox.alert("��ʾ", "���벻��Ϊ��");
			return false;
		}
		if (email == "") {
			Ext.Msg.alert("��ʾ", "�����ʼ�����Ϊ��,��ʽ�磺zhangsan@domain.com");
			return false;
		}
		if (email != 0) {
			var len = email.length;
			var index1 = email.indexOf('@');
			var index2 = email.indexOf('.');
			if (index1 == 0 || index1 == -1 || index2 == -1 || index1 > index2
					|| index2 - index1 == 1 || index2 == len - 1) {
				Ext.Msg.alert("��ʾ", "�����ʼ���ʽ����ȷ,���磺zhangsan@domain.com");
				return false;
			}
		}
		if (department == "") {
			Ext.MessageBox.alert("��ʾ", "�������ű���ѡ��");
			return false;
		}
		if (userName != "" && userName.match(/[^0-9A-Za-z]/g)) {
			Ext.MessageBox.alert("��ʾ", "�û���������������");
			return false;
		}
		return true;
	},
	// ��ɫ����ʼ��++++++++++++++++++++++++++++++++++++(����3)
	creatRole : function() {
		var department = new Ext.form.ComboBox({
			xtype : 'combo',
			id : 'department2',
			defaultParam : '',
			allowBlank : false,
			colspan : 3,
			hiddenName : 'department',
			fieldLabel : '����',
			displayField : 'departName',
			valueField : 'id',
			emptyText : '��ѡ����������',
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
		// ע���¼�
		department.on('select', this.OnBlurEvent, this);// �����淽��7�й�����
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
				html : "�������ţ�&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, department]
		});

		// ��ɫgrid
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
			header : '��ɫ����',
			width : 180,
			dataIndex : 'name',
			sortable : true
		}, {
			header : '��������',
			width : 180,
			dataIndex : 'department',
			sortable : true
		}, {
			header : '����',
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
			title : "���н�ɫ��Ϣ<font color=red>������һ����Ӷ����ɫ��</font>",
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
			title : 'ѡ���ɫ��<font color=red>ͨ����Ӧ�Ĳ���ѡ����Ӧ�Ľ�ɫ��Ĭ�ϲ�������Ʒ�ۿ�</font>��',
			modal : true,
			height : 320,
			width : 520,
			resizable : false,
			layout : 'absolute',
			buttons : [{
				xtype : 'button',
				text : '���',
				handler : function() {
					var record = roleGrid.getSelectionModel().getSelected();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ӵ���!");
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
						// Ϊ��ȥ���ظ���ɫ
						for (j = 0; j < gP.length; j++) {
							if (id == gP[j].data.id) {
								tempName = name;
								flag = 0;
							}
						}
						if (flag == 0) {
							Ext.Msg.alert("��ʾ", "��ɫ��<font color=red>"
									+ tempName + "</font>�Ѿ����ڲ����ظ����");
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
				text : '�ر�',
				scope : this
			}],
			items : [departmentForm, roleGrid]
		});
		userDetailWin.show();
	},

	// �û�����幹��+++++++++++++++++++++++++++++++++(����4)
	creatUserFormPanel : function(userId) {
		this.realName = new Ext.form.TextField({
			xtype : 'textfield',
			name : 'realName',
			emptyText : "������",
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
			fieldLabel : '�绰',
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
			fieldLabel : '����',
			displayField : 'departName',
			valueField : 'id',
			emptyText : '��ѡ����������',
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
			fieldLabel : 'ƽ̨',
			displayField : 'platName',
			valueField : 'id',
			emptyText : '��ѡ������ƽ̨',
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
			id : 'isLockHid',// ���id����Ҫ���ܺ���������ˣ�������޷�����������ֵ��
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
			id : 'specialUserHid',// ���id����Ҫ���ܺ���������ˣ�������޷�����������ֵ��
			name : 'specialUser',
			value : 0
		});
		// �����
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
				html : "��ʵ����:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.realName, {
				html : "�û���:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.userName, {
				html : "����:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.password, {
				html : "�����ʼ�:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.email, {
				html : "�绰:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.telephone, {
				html : "�ֻ�:&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.mobilePhone, {
				html : "�������ţ�&nbsp;",
				cls : 'common-text',
				style : 'width:90;text-align:right'
			}, this.department, {
				html : "����ƽ̨��&nbsp;",
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
					html : "�Ƿ����:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				}, this.enabled, {
					html : "�Ƿ������û�:&nbsp;",
					cls : 'common-text',
					style : 'width:90;text-align:right'
				}, this.specialUser, {
					html : "�Ƿ�����:&nbsp;",
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

	// �����ɫ�б����++++++++++++++++++++++++++++++++++++++++++++(����5)
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
			header : '��ɫ����',
			width : 150,
			dataIndex : 'name',
			sortable : true
		}, {
			header : '��������',
			width : 150,
			dataIndex : 'department',
			sortable : true
		}, {
			header : '����',
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
			title : "��ɫ������<font color=red>������һ����Ӷ����ɫҲ����ɾ�������ɫ��</font>",
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
				text : '��ӽ�ɫ',
				pressed : true,
				handler : this.creatRole,
				scope : this,
				iconCls : 'add'
			}, '-', {
				text : 'ɾ����ɫ',
				pressed : true,
				handler : function() {
					var record = this.grid.getSelectionModel().getSelected();
					var records = this.grid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
					} else {
						Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(
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

	// �½��û�����++++++++++++++++++++++++++++++++++++++++++++++++++(����6)
	addUser : function() {
		var userForm = this.creatUserFormPanel("0"); // ���淽��4�й����
		var roleListGrid = this.roleListGrid(); // ���淽��5�й����
		var userDetailWin = new Ext.Window({
			title : '�½��û�<font color=red>���밴���ı����л�ɫģ����д��Ӧ��Ϣ��</font>',
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
					if (!this.lastValisateUserMessage()) {// ���淽��2��
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
								ExtExt.MessageBox.alert("����", userNameTring
										+ "�Ѿ�������");
								Ext.getCmp("saveNewUserButton").enable();
							} else if (flagSign == "exit") {
								var userNameTring = eval("("
										+ response.responseText + ")").userNameTring;
								ExtExt.MessageBox.alert("����", userNameTring
										+ "�Ѿ�����");
								Ext.getCmp("saveNewUserButton").enable();
							} else {
								Ext.MessageBox.alert("��ʾ", "�ɹ������û���Ϣ��");
								userDetailWin.close();
								Ext.getCmp('mainGrid').store.reload();

							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("����", "�����û���Ϣʧ�ܣ�");
							Ext.getCmp("saveNewUserButton").enable();
						},
						scope : this
					});
				},
				text : '����',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					userDetailWin.close();
				},
				text : '�ر�',
				scope : this
			}],
			items : [userForm, roleListGrid]
		});
		userDetailWin.show();
	},
	// ����������¼��������ҽ�ɫ++++++++++++++++++++++++++++++++++++(����7)
	OnBlurEvent : function() {
		var departmentTemp = Ext.getCmp("department2").getValue();
		Ext.getCmp("roleGrid2").store.baseParams.deptCode = departmentTemp;
		Ext.getCmp("roleGrid2").store.removeAll();
		Ext.getCmp("roleGrid2").store.reload();
	},

	// �޸��û���Ϣ+++++++++++++++++++++++++++++++++++++++++++++++++(����8)
	modify : function(grid) {

		var record = Ext.getCmp("mainGrid").getSelectionModel().getSelected();
		var records = Ext.getCmp("mainGrid").getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ���!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ��!");
			return;
		}
		var id = record.get("id");
		var departmentPage = record.get("department");
		var platformPage = record.get("platform");
		var userForm = this.creatUserFormPanel(id); // ���淽��4�й����
		var roleListGrid = this.roleListGrid(id); // ���淽��5�й����
		var userDetailWin = new Ext.Window({
			title : '�޸��û�<font color=red>���밴�չ̶���ʽ�޸���Ӧ��Ϣ��</font>',
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
					if (!this.lastValisateUserMessage()) {// ���淽��2��
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
								ExtExt.MessageBox.alert("����", userNameTring
										+ "�Ѿ�������");
								Ext.getCmp("modifyUserButton").enable();
							} else if (flagSign == "exit") {
								var userNameTring = eval("("
										+ response.responseText + ")").userNameTring;
								ExtExt.MessageBox.alert("����", userNameTring
										+ "�Ѿ�����");
								Ext.getCmp("modifyUserButton").enable();
							} else {
								Ext.MessageBox.alert("��ʾ", "�ɹ������û���Ϣ��");
								userDetailWin.close();
								Ext.getCmp('mainGrid').store.reload();
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("����", "�����û���Ϣʧ�ܣ�");
							Ext.getCmp("modifyUserButton").enable();
						},
						scope : this
					});
				},
				text : '����',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					userDetailWin.close();
				},
				text : '�ر�',
				scope : this
			}],
			items : [userForm, roleListGrid]
		});
		userDetailWin.show();
	},
	// ɾ���û����൱�ڱ�����+++++++++++++++++++++++++++++++++++++++++++++++++++(����9)
	removeData : function() {
		var record = Ext.getCmp("mainGrid").getSelectionModel().getSelected();
		var records = Ext.getCmp("mainGrid").getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		var ids = new Array();
		for (var i = 0; i < records.length; i++) {
			ids[i] = records[i].data.id;
		}

		var m = Ext.MessageBox.confirm("ɾ����ʾ", "�Ƿ����Ҫɾ�����ݣ�һ��ɾ��,���ɻָ�!",
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
									Ext.Msg.alert("��ʾ��Ϣ",
											"����ɾ��ʧ�ܣ�������ԭ�����£�<br/>"
													+ (r.errors.msg
															? r.errors.msg
															: "δ֪ԭ��"));
								else {
									Ext.Msg.alert("��ʾ��Ϣ", "�ɹ�ɾ������!",
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
	// ��ѯ��ť����
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

	// ��ѯ���
	queryPanel : function() {
		this.realName = new Ext.form.TextField({
			xtype : 'textfield',
			id : 'realNamequery',
			// emptyText : " ���������磺����",
			name : 'realName',
			width : 200
		});

		this.userName = new Ext.form.TextField({
			xtype : 'textfield',
			// emptyText : " ���������磺zhangsan",
			id : 'userNamequery',// idһ�������أ�������๦�ܶ������ã���������
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
			title : "<font color=green>�û���ѯ�б�</font> _<font color=red> (���Ϊ��ʵ�����磺�������Ҳ�Ϊ��½���磺zhangsan)</font>",
			items : [{
				html : "��ʵ����:&nbsp;",
				cls : 'common-text',
				style : 'width:150;text-align:right'
			}, this.realName, {
				html : "�û���:&nbsp;",
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
			header : "����",
			sortable : true,
			hideable : false,
			dataIndex : "realName",
			width : 100,// 090613lee��150�޸�Ϊ100
			editor : new Ext.form.TextField()
		}, {
			header : "�û���",
			sortable : true,
			hideable : false,
			dataIndex : "userName",
			width : 100,
			editor : new Ext.form.TextField()
		}, {
			header : "����",
			sortable : true,
			hideable : false,
			dataIndex : "password",
			width : 100,
			editor : new Ext.form.TextField()
		}, {
			header : "�����ʼ�",
			sortable : true,
			hideable : false,
			dataIndex : "email",
			width : 200,// 090613lee��220�޸�Ϊ200
			editor : new Ext.form.TextField()
		}, {
			header : "��������",
			sortable : true,
			hideable : false,
			dataIndex : "department",
			width : 150,// 090613lee��220�޸�Ϊ150
			editor : new Ext.form.TextField()
		}, {
			header : "����ƽ̨",
			sortable : true,
			hideable : false,
			dataIndex : "platform",
			width : 150,// 090613lee��220�޸�Ϊ150
			editor : new Ext.form.TextField()
		}, {
			header : "�Ƿ����",
			sortable : true,
			hideable : false,
			dataIndex : "enabled",
			renderer : function(value) {
				if (value == "��") {
					value = '<span style="color:red;">' + value + '</span>';
				} else {
					value = '<span style="color:green;">' + value + '</span>';
				}
				return value;
			},
			width : 100,
			editor : new Ext.form.TextField()
		}, {
			header : "�Ƿ������û�",
			sortable : true,
			hideable : false,
			dataIndex : "specialUser",
			width : 100,
			renderer : function(value) {
				if (value == "��") {
					value = '<span style="color:red;">' + value + '</span>';
				} else {
					value = '<span style="color:green;">' + value + '</span>';
				}
				return value;
			},
			editor : new Ext.form.TextField()
		}, {
			header : "�Ƿ�����",
			sortable : true,
			hideable : false,
			dataIndex : "isLock",
			width : 100,
			renderer : function(value) {
				if (value == "��") {
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

		// ����
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
		// ��ҳ
		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		// ������
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			store : this.store,
			cm : this.cm,
			id : "mainGrid",
			sm : csm,
			title : "<font color=blue>�û���ϸ��Ϣ�б�</font>[<font color=red>˫���ɽ����޸�</font>]",
			trackMouseOver : false,
			loadMask : true,
			y : 35,
			anchor : '0 -35',
			clicksToEdit : 1,
			tbar : [{
				text : '��ѯ�û�',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '&nbsp;|&nbsp;', {
				text : '�½��û�',
				pressed : true,
				handler : this.addUser,
				scope : this,
				iconCls : 'add'
			}, '&nbsp;|&nbsp;', {
				text : '�޸�',
				pressed : true,
				id : 'modify',
				scope : this,
				handler : this.modify,
				iconCls : 'edit'
			}, '&nbsp;|&nbsp;', {
				text : 'ɾ��',
				pressed : true,
				id : 'delete',
				handler : this.removeData,
				scope : this,
				iconCls : 'delete'
			}, '&nbsp;|&nbsp;', {
				text : '����',
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
