UserForm = Ext.extend(Ext.Window, {
	formPanel : null,
	groupPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		UserForm.superclass.constructor.call(this, {
			id : "UserFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 500,
			width : 700,
			title : "添加/修改用户",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		var dept = webContext + '/menu/loadTree?methodCall=childLevel';
		var departments = new TreeSelector("deptName", dept, "所属部门", "o");
		departments.addListener("expand", function() {
			Ext.getCmp("deptNameTree").addListener("click", function() {
				Ext.Ajax.request({
					url : webContext + "/dept",
					params : {
						methodCall : "getDetailByDeptRDN",
						deptRDN : Ext.getCmp("o").getValue()
					},
					success : function(f) {
						var g = Ext.util.JSON.decode(f.responseText);
						Ext.getCmp("departmentNumber").setValue(g.data.deptNo);
					}
				});
			});
		});
		
		var k = new Ext.data.Store({
			url : webContext + "/group?methodCall=findByUserRDN&userRDN=" + this.userRDN,
			reader : new Ext.data.JsonReader({
				root : "result",
				fields : [
					"rdn",
					"displayName"
				]
			}),
			remoteSort : true
		});
		k.load();
		var l = new Ext.grid.ColumnModel({
			columns : [
				new Ext.grid.RowNumberer(),
				{
					header : "rdn",
					dataIndex : "rdn",
					hidden : true
				}, {
					header : "名称",
					dataIndex : "displayName"
				}, {
					header : "管理",
					dataIndex : "dn",
					sortable : false,
					width : 60,
					renderer : function(r, q, o, u, p) {
						var t = o.data.rdn;
						var s = "";
						s += '<a href="#" title="删除" onclick="UserForm.del()">删除</a>';
						return s;
					}
				}
			],
			defaults : {
				sortable : true,
				menuDisable : true,
				width : 100
			}
		});
		this.groupPanel = new Ext.grid.GridPanel({
			id : "GroupPanel",
			width : 640,
			height : 200,
			border : true,
			store : k,
			autoScroll : true,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			tbar : this.topbar,
			cm : l,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});

		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			id : "UserForm",
			fileUpload :true,
			enctype:'multipart/form-data',
			autoScroll : true,
			items : [
				{
					xtype : "container",
					layout : "column",
					items : [
						{
							layout : "form",
							border : false,
							columnWidth : 0.5,
							defaultType : "textfield",
							defaults : {
								anchor : "95%,95%"
							},
							items : [
								{
									name : "dn",
									id : "dn",
									hidden : "true"
								}, {
									name : "uid",
									id : "uid",
									fieldLabel : "用户账号",
									value : this.uid == null ? "" : this.uid,
									allowBlank : false
								}, {
									name : "sn",
									id : "sn",
									fieldLabel : "姓",
									allowBlank : false
								}, {
									name : "givenName",
									id : "givenName",
									fieldLabel : "名",
									allowBlank : false
								}, {
									name : "cn",
									id : "cn",
									fieldLabel : "cn",
									allowBlank : false
								}, {
									name : "displayName",
									id : "displayName",
									fieldLabel : "显示名字",
									allowBlank : false
								}, {
									name : "o",
									id : "o",
									hidden : "true"
								},
								departments,
								{
									hiddenName : "title",
									id : "titleName",
									xtype : "combo",
									fieldLabel : "职务",
									triggerAction : "all",
									mode : "local",
									valueField : "dn",
									displayField : "title",
									store : new Ext.data.SimpleStore({
										url : webContext + "/duty?methodCall=combo",
										fields : ["dn", "title"],
										remoteSort : true
									}),
									listeners : {
										beforequery : function(queryEvent) {
											var store = queryEvent.combo.store;
											store.baseParams = {
												"param" : queryEvent.query
											};
											store.load();
											return false;
										}
									}
								}, {
									name : "displayOrder",
									id : "displayOrder",
									fieldLabel : "排序",
									value : "0",
									allowBlank : false
								}, {
									hiddenName : "employeeType",
									id : "employeeTypeName",
									xtype : "combo",
									fieldLabel : "员工类型",
									allowBlank : false,
									editable : false,
									triggerAction : "all",
									value : "01",
									rawValue : "在编",
									store : [
										[
											"01",
											"在编"
										], [
											"02",
											"非在编"
										], [
											"03",
											"外包"
										]
									]
								}, {
									name : "password",
									id : "password",
									inputType : "password",
									fieldLabel : "密码",
									allowBlank : false
								}, {
									name : "mail",
									id : "mail",
									fieldLabel : "邮箱"
								}, {
									name : "mobile",
									id : "mobile",
									fieldLabel : "手机号码"
								}, {
									hiddenName : "status",
									id : "statusName",
									xtype : "combo",
									fieldLabel : "状态",
									mode : "local",
									allowBlank : false,
									editable : false,
									value : "0",
									rawValue : "正常",
									triggerAction : "all",
									store : [
										[
											"0",
											"正常"
										], [
											"1",
											"锁定"
										], [
											"2",
											"未启用"
										], [
											"3",
											"注销"
										]
									]
								}, {
									name : "description",
									id : "description",
									fieldLabel : "描述信息",
									xtype : "textarea"
								}, {
									name : "userType",
									id : "userType",
									hidden : true,
									value : "1"
								}, {
									name : "departmentNumber",
									id : "departmentNumber",
									hidden : true
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.5,
							defaultType : "textfield",
							items : [
								{
									name : "photoPanel",
									id : "photoPanel",
									width : 300,
									height : 350,
									xtype : "panel",
									border : true,
									html : "<div id='imgTarget'></div>"
								}, {
									fieldLabel : "选择图片",
									maxLength : 64,
									name:"photo",
									vtype:'jpegfile',
									inputType:'file',
									id:'photo',
									xtype:"textfield"
								}, {
									text : "点击预览",
									fieldLabel : "图片预览",
									xtype : "button",
									handler : function() {
										var img = Ext.getCmp("photo").getValue();
										var uid = Ext.getCmp("UserForm").form.getValues().uid;
										var fex = img.substr(img.lastIndexOf("."));
										if(img == "") {
											Ext.Msg.alert("提示", "请选择要上传的图片！");
										} else {
											//临时上传图片
											document.getElementById("imgTarget").innerHTML = "";
											Ext.getCmp("UserForm").form.submit({
												method : "post",
												url : webContext + '/user?methodCall=tempUpload',
												success : function(form, action) {
													document.getElementById("imgTarget").innerHTML="<IMG onerror='this.src=\""+ webContext +"/images/default.jpg\"' src='"+ webContext +"/images/userphoto/" + action.result.filePath +"'></IMG>";
												},
												failure : function(form, action) {
													Ext.Msg.alert("提示", "修改失败！");
												}
											});
										}
									}
								}
							]
						}
					]
				}, {
					xtype : "container",
					layout : "column",
					items : [
						{
							layout : "form",
							border : false,
							columnWidth : 0.45,
							items : [
								{
									id : "groupName",
									xtype : "combo",
									fieldLabel : "用户组",
									triggerAction : "all",
									mode : "local",
									valueField : "rdn",
									displayField : "displayName",
									store : new Ext.data.SimpleStore({
										url : webContext + "/group?methodCall=combo",
										fields : ["rdn", "displayName"],
										remoteSort : true
									}),
									listeners : {
										beforequery : function(queryEvent) {
											var store = queryEvent.combo.store;
											store.baseParams = {
												"param" : queryEvent.query
											};
											store.load();
											return false;
										}
									}
								}
							]
						}, {
							layout : "form",
							border : false,
							columnWidth : 0.55,
							items : [
								{
									xtype : "button",
									text : "添加",
									handler : function() {
										var f = Ext.getCmp("GroupPanel");
										for(var i = 0; i < f.getStore().getCount(); i++) {
											if(f.getStore().getAt(i).data.rdn == Ext.getCmp("groupName").getValue()) {
												Ext.MessageBox.show({
													title : "操作信息",
													msg : "您添加了重复的组，请核实！",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
												return ;
											}
										}
										var newRecord = Ext.data.Record.create([
											{
												name : "rdn",
												type : "string"
											}, {
												name : "displayName",
												type : "string"
											}
										]);
										var newData = new newRecord({
											"rdn" : Ext.getCmp("groupName").getValue(),
											"displayName" : Ext.getCmp("groupName").getRawValue()
										});
										f.getStore().add(newData);
									}
								}
							]
						}
					]
				},
				this.groupPanel
			]
		});
		if(this.uid != null && this.uid != "undefined") {
			Ext.getCmp("uid").readOnly = true;
			this.formPanel.getForm().load({
				deferredRender : false,
				url : webContext + "/user?methodCall=getDetailByUid&uid=" + this.uid,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					if(e.data.employeeType == "01" || e.data.employeeType == "") {
						Ext.getCmp("employeeTypeName").setValue("01");
						Ext.getCmp("employeeTypeName").setRawValue("在编");
					} else if(e.data.employeeType == "02") {
						Ext.getCmp("employeeTypeName").setValue("02");
						Ext.getCmp("employeeTypeName").setRawValue("非在编");
					} else if(e.data.employeeType == "03") {
						Ext.getCmp("employeeTypeName").setValue("03");
						Ext.getCmp("employeeTypeName").setRawValue("外包");
					}
					if(e.data.status == "0" || e.data.status == "") {
						Ext.getCmp("statusName").setValue("0");
						Ext.getCmp("statusName").setRawValue("正常");
					} else if(e.data.status == "1") {
						Ext.getCmp("statusName").setValue("1");
						Ext.getCmp("statusName").setRawValue("锁定");
					} else if(e.data.status == "2") {
						Ext.getCmp("statusName").setValue("2");
						Ext.getCmp("statusName").setRawValue("未启用");
					} else if(e.data.status == "3") {
						Ext.getCmp("statusName").setValue("3");
						Ext.getCmp("statusName").setRawValue("注销");
					}
					Ext.getCmp("o").setValue(e.data.o);
					//Ext.getCmp("deptName").setValue(e.data.deptName);
					//document.getElementById("imgTarget").innerHTML="<IMG onerror='this.src=\""+ webContext +"/images/default.jpg\"' src='"+ webContext +"/images/userphoto/" + e.data.photo +"'></IMG>";
					Ext.Ajax.request({
						url : webContext + "/dept",
						params : {
							methodCall : "getDetailByDeptRDN",
							deptRDN : e.data.o
						},
						success : function(f) {
							var g = Ext.util.JSON.decode(f.responseText);
							if(g.data.deptName != null) {
								Ext.getCmp("deptName").setValue(g.data.deptName);
							}
						}
					});
					Ext.Ajax.request({
						url : webContext + "/duty",
						params : {
							methodCall : "getDetailByDutyRDN",
							dutyRDN : e.data.title
						},
						success : function(f) {
							var g = Ext.util.JSON.decode(f.responseText);
							if(g.data.title != null) {
								Ext.getCmp("titleName").setValue(g.data.title);
							}
						}
					});
				},
				failure : function() {
					
				}
			});
		}
		this.buttons = [
			{
				text : "保存",
				handler : this.save.createCallback(this, this.formPanel)
			}, {
				text : "取消",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	save : function(a, b) {
		if(b.getForm().isValid()) {
			//保存组信息
			var groupsStr = "";
			var f = Ext.getCmp("GroupPanel");
			for(var i = 0; i < f.getStore().getCount(); i++) {
				groupsStr += f.getStore().getAt(i).data.rdn + "___";
			}
			if(a.isModify) {
				//修改
				b.getForm().submit({
					url : webContext + "/user?methodCall=modify&groups=" + groupsStr,
					method : "post",
					waitMsg : "正在提交数据……",
					success : function(c, d) {
						a.close();
						Ext.getCmp("UserList").getStore().reload();
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "保存成功！",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
					},
					failure : function(c, d) {
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "保存失败，请联系管理员！",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
						return ;
					}
				});
			} else {
				//新增
				b.getForm().submit({
					url : webContext + "/user?methodCall=add&groups=" + groupsStr,
					method : "post",
					waitMsg : "正在提交数据……",
					success : function(c, d) {
						a.close();
						Ext.getCmp("UserList").getStore().reload();
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "保存成功！",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
					},
					failure : function(c, d) {
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "保存失败，请联系管理员！",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
						return ;
					}
				});
			}
		}
	},
	cancel : function(a) {
		a.close();
	}
});
UserForm.del = function() {
	var e = Ext.getCmp("GroupPanel");
	var c = e.getSelectionModel().getSelections();
	if(c.length == 0) {
		Ext.ux.Toast.msg("提示信息","请选择要删除的记录！");
		return ;
	}
	//只是前台删除，数据库并不真正删除
	var allStore = e.getStore();
	Ext.each(c, function(item) {
		allStore.remove(item);
	});
}