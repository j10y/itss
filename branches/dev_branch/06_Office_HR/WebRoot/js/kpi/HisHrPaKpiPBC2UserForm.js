HisHrPaKpiPBC2UserForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HisHrPaKpiPBC2UserForm.superclass.constructor.call(this, {
			id : "HisHrPaKpiPBC2UserFormWin",
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
			id : "HisHrPaKpiPBC2UserFormPanel",
			autoHeight : true,
			border : false,
			autoLoad : {
				url : __ctxPath + "/kpi/listHisHrPaKpiPBC2UserAuditHis.do?hrPaKpiPBC2UserId="
						+ this.recordId
			}
		});
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			border : false,
			url : __ctxPath + "/kpi/checkHrPaKpiPBC2UserAuditHis.do?hrPaKpiPBC2UserId="
					+ this.recordId,
			id : "HisHrPaKpiPBC2UserForm",
			bodyStyle : "padding:0 0 0 10px;",
			defaultType : "recordId",
			items : [ ]
		});
		this.buttons = [  {
			text : "关闭",
			iconCls : "btn-cancel",
			handler : this.cancel.createCallback(this)
		} ];
	},
	refuse : function(a, b) {
		Ext.getCmp("HisHrPaKpiPBC2UserForm.checkStatus").setValue("0");
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
		Ext.getCmp("HisHrPaKpiPBC2UserForm.checkStatus").setValue("1");
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