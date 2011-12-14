BpShopinPorClassForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BpShopinPorClassForm.superclass.constructor.call(this, {
			id : "BpShopinPorClassFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 140,
			width : 350,
			title : "品类关联信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveBpShopinPC.do",
			id : "BpShopinPorClassForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "bpShopinPorClass.id",
					id : "bpShopinPorClassId",
					xtype : "hidden",
					value : this.bpShopinPorClassId == null ? "" : this.bpShopinPorClassId
				}, {
					hiddenName : "bpShopinPorClass.bpProClassId.id",
					id : "bpProClassName",
					xtype : "combo",
					fieldLabel : "品类名称",
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/bandpoor/comboProClass.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"Q_proClassName_S_LK" : queryEvent.query,
								"Q_flag_N_EQ" : 1
							};
							store.load();
							return false;
						}
					}
				}, {
					hiddenName : "bpShopinPorClass.shopinProClassId.id",
					id : "shopinProClassName",
					xtype : "combo",
					fieldLabel : "上品品类名称",
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/bandpoor/comboShopinPC.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"Q_proClassName_S_LK" : queryEvent.query
							};
							store.load();
							return false;
						}
					}
				}
			]
		});
		if(this.bpShopinPorClassId != null && this.bpShopinPorClassId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBpShopinPC.do?id=" + this.bpShopinPorClassId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("bpProClassName").setValue(e.data.bpProClassId.id);
					Ext.getCmp("bpProClassName").setRawValue(e.data.bpProClassId.proClassName);
					Ext.getCmp("shopinProClassName").setValue(e.data.shopinProClassId.id);
					Ext.getCmp("shopinProClassName").setRawValue(e.data.shopinProClassId.proClassName);
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
					Ext.getCmp("BpShopinPorClassView").gridPanel.store.reload({
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