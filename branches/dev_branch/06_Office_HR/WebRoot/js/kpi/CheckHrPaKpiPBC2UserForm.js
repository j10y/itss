CheckHrPaKpiPBC2UserForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		CheckHrPaKpiPBC2UserForm.superclass.constructor.call(this, {
			id : "CheckHrPaKpiPBC2UserFormWin",
			iconCls : "btn-empProfile-pass",
			layout : "form",
			items : [ this.displayPanel, this.formPanel ],
			modal : true,
			height : 415,
			shadow : false,
			autoScroll : true,
			width : 600,
			maximizable : true,
			title : "个人PBC得分审核",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.displayPanel = new Ext.Panel({
			id : "CheckHrPaKpiPBC2UserFormPanel",
			autoHeight : true,
			border : false,
			autoLoad : {
				url : __ctxPath + "/kpi/getViewHrPaKpiPBC2User.do?id="
						+ this.recordId
			}
		});
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			border : false,
			url : __ctxPath + "/kpi/checkHrPaKpiPBC2UserAuditHis.do?hrPaKpiPBC2UserId="
					+ this.recordId,
			id : "CheckHrPaKpiPBC2UserForm",
			bodyStyle : "padding:0 0 0 10px;",
			defaultType : "recordId",
			items : [ {
				fieldLabel : "审核意见",
				xtype : "textarea",
				anchor : "98%",
				allowBlank : false,
				blankText : "审核意见为必填!",
				name : "hrPaKpiPBC2UserAuditHis.checkRemark",
				id : "CheckHrPaKpiPBC2UserForm.checkRemark"
			}, {
				xtype : "hidden",
				name : "hrPaKpiPBC2UserAuditHis.checkStatus",
				id : "CheckHrPaKpiPBC2UserForm.checkStatus"
			} ]
		});
		this.buttons = [ {
			text : "审核通过",
			iconCls : "btn-salaryPayoff-pass",
			id : "hrPaKpiPBC2UserbtnY",
			handler : this.check.createCallback(this.formPanel, this)
		}, {
			text : "审核未通过",
			id : "hrPaKpiPBC2UserbtnN",
			iconCls : "btn-salaryPayoff-notpass",
			handler : this.refuse.createCallback(this.formPanel, this)
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : this.cancel.createCallback(this)
		} ];
	},
	refuse : function(a, b) {
		Ext.getCmp("CheckHrPaKpiPBC2UserForm.checkStatus").setValue("0");
		if (a.getForm().isValid()) {
			a.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("ForAuditHrPaKpiPBC2UserGrid");
					if (d != null) {
						d.getStore().reload();
					}
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					b.close();
				}
			});
		}
	},
	cancel : function(a) {
		a.close();
	},
	check : function(a, b) {
		Ext.getCmp("CheckHrPaKpiPBC2UserForm.checkStatus").setValue("1");
		if (a.getForm().isValid()) {
			a.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("ForAuditHrPaKpiPBC2UserGrid");
					if (d != null) {
						d.getStore().reload();
					}
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					b.close();
				}
			});
		}
	}
});