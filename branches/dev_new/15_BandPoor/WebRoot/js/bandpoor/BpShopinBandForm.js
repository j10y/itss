BpShopinBandForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BpShopinBandForm.superclass.constructor.call(this, {
			id : "BpShopinBandFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 140,
			width : 350,
			title : "品牌关联信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveBpShopinBD.do",
			id : "BpShopinBandForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "bpShopinBand.id",
					id : "bpShopinBandId",
					xtype : "hidden",
					value : this.bpShopinBandId == null ? "" : this.bpShopinBandId
				}, {
					hiddenName : "bpShopinBand.bpBandId.id",
					id : "bpBandName",
					xtype : "combo",
					fieldLabel : "品牌名称",
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/bandpoor/comboBand.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"bandName" : queryEvent.query,
								"Q_flag_N_EQ" : 1
							};
							store.load();
							return false;
						}
					}
				}, {
					hiddenName : "bpShopinBand.shopinBandId.id",
					id : "shopinBandName",
					xtype : "combo",
					fieldLabel : "上品品牌名称",
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/bandpoor/comboShopinBD.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"Q_bandName_S_LK" : queryEvent.query
							};
							store.load();
							return false;
						}
					}
				}
			]
		});
		if(this.bpShopinBandId != null && this.bpShopinBandId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBpShopinBD.do?id=" + this.bpShopinBandId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("bpBandName").setValue(e.data.bpBandId.id);
					Ext.getCmp("bpBandName").setRawValue(e.data.bpBandId.bandChName + "/" + e.data.bpBandId.bandEnName);
					Ext.getCmp("shopinBandName").setValue(e.data.shopinBandId.id);
					Ext.getCmp("shopinBandName").setRawValue(e.data.shopinBandId.bandName);
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
					Ext.getCmp("BpShopinBandView").gridPanel.store.reload({
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