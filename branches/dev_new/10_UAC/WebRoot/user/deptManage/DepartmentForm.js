DepartmentForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		DepartmentForm.superclass.constructor.call(this, {
			id : "DepartmentFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 300,
			width : 400,
			title : "添加/修改部门信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		var dept = webContext + '/menu/loadTree?methodCall=childLevel';
		var departments = new TreeSelector("parentName", dept, "父部门", "parentNo");
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			id : "DepartmentForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "deptRDN",
					id : "deptRDN",
					xtype : "hidden",
					value : ""
				}, {
					name : "parentNo",
					id : "parentNo",
					xtype : "hidden",
					value : ""
				},
				departments,
				{
					name : "deptName",
					id : "deptName",
					fieldLabel : "部门名称",
					allowBlank : false
				}, {
					name : "erpId",
					id : "erpId",
					fieldLabel : "ERP部门CODE"
				}, {
					name : "displayOrder",
					id : "displayOrder",
					fieldLabel : "部门排序",
					allowBlank : false
				}, {
					name : "deptDesc",
					id : "deptDesc",
					fieldLabel : "部门描述",
					xtype : "textarea"
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
						]
					]
				}
			]
		});
		if(this.deptRDN != null && this.deptRDN != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : webContext + "/dept?methodCall=getDetailByDeptRDN&deptRDN=" + this.deptRDN,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					if(e.data.status == "0" || e.data.status == "") {
						Ext.getCmp("statusName").setValue(0);
						Ext.getCmp("statusName").setRawValue("正常");
					} else if(e.data.status == "1") {
						Ext.getCmp("statusName").setValue(1);
						Ext.getCmp("statusName").setRawValue("锁定");
					}
					Ext.getCmp("parentNo").setValue(e.data.parentNo);
					Ext.Ajax.request({
						url : webContext + "/dept",
						params : {
							methodCall : "getDetailByDeptRDN",
							deptRDN : e.data.parentNo
						},
						success : function(f) {
							var g = Ext.util.JSON.decode(f.responseText);
							Ext.getCmp("parentName").setValue(g.data.deptName);
						}
					});
				},
				failure : function() {
					
				}
			});
			Ext.getCmp("deptRDN").setValue(this.deptRDN);
		} else {
			Ext.getCmp("parentNo").setValue(this.parentRDN);
			Ext.getCmp("parentName").setValue(this.parentDeptName);
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
			if(a.isModify) {
				//修改
				b.getForm().submit({
					url : webContext + "/dept?methodCall=modify",
					method : "post",
					waitMsg : "正在提交数据……",
					success : function(c, d) {
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "保存成功！",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.INFO
						});
						a.close();
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
					url : webContext + "/dept?methodCall=add",
					method : "post",
					waitMsg : "正在提交数据……",
					success : function(c, d) {
						alert("保存成功！");
						a.close();
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