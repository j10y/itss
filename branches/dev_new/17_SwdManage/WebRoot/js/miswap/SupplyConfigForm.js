SupplyConfigForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SupplyConfigForm.superclass.constructor.call(this, {
			id : "SupplyConfigFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 240,
			width : 500,
			title : "供应商发送配置",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/miswap/saveSupplyConfig.do",
			id : "SupplyConfigForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "supplyConfig.id",
					id : "id",
					xtype : "hidden"
				}, {
					name : "supplyConfig.supplyInfoSid",
					id : "supplyInfoSid",
					xtype : "hidden",
					value : this.supplyInfoSid
				}, {
					fieldLabel : "供应商编码",
					id : "supplyConfigSupplyId",
					value : "aaaaa"
				}, {
					fieldLabel : "供应商名称",
					id : "supplyConfigCompanyName",
					value : "aaaaa"
				}, {
					fieldLabel : "是否发送短信",
					xtype : "checkboxgroup",
					columns : 1,
					items : [
						{
							boxLabel : "是",
							name : "supplyConfig.receiveTm",
							id : "receiveTm",
							inputValue : 1
						}
					]
				}, {
					hiddenName : "supplyConfig.tmId",
					id : "tmName",
					xtype : "combo",
					fieldLabel : "短信模板",
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/miswap/comboTmTemplate.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"Q_name_S_LK" : queryEvent.query
							};
							store.load();
							return false;
						}
					}
				}, {
					fieldLabel : "是否发送邮件",
					xtype : "checkboxgroup",
					columns : 1,
					items : [
						{
							boxLabel : "是",
							name : "supplyConfig.receiveEmail",
							id : "receiveEmail",
							inputValue : 1
						}
					]
				}, {
					hiddenName : "supplyConfig.emailId",
					id : "emailName",
					xtype : "combo",
					fieldLabel : "邮件模板",
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/miswap/comboEmailTemplate.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"Q_name_S_LK" : queryEvent.query
							};
							store.load();
							return false;
						}
					}
				}
			]
		});
		if(this.supplyInfoSid != null && this.supplyInfoSid != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/miswap/getSupplyConfig.do?Q_supplyInfoSid_L_EQ=" + this.supplyInfoSid,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("tmName").setValue(e.data.tmId);
					Ext.getCmp("tmName").setRawValue(e.data.tmName);
					Ext.getCmp("emailName").setValue(e.data.emailId);
					Ext.getCmp("emailName").setRawValue(e.data.emailName);
					if(e.data.receiveTm == "1") {
						Ext.getCmp("receiveTm").setValue(true);
					}
					if(e.data.receiveEmail == "1") {
						Ext.getCmp("receiveEmail").setValue(true);
					}
				},
				failure : function() {
					
				}
			});
		}
		this.buttons = [
			{
				text : "保存",
				iconCls : "btn-save",
				handler : this.save.createCallback(this.formPanel, this)
			}, {
				text : "取消",
				iconCls : "btn-cancel",
				handler : this.cancel.createCallback(this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a, b) {
		if(a.getForm().isValid()) {
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function() {
					Ext.ux.Toast.msg("提示信息","保存成功！");
					Ext.getCmp("SupplyInfoView").gridPanel.store.reload({
						params : {
							start : 0,
							limit : 25
						}
					});
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : d.result.msg,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			});
		}
	}
});