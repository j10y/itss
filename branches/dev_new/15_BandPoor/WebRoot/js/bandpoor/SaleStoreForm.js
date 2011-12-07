SaleStoreForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		SaleStoreForm.superclass.constructor.call(this, {
			id : "SaleStoreFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 240,
			width : 350,
			title : "销售商场详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveSaleStore.do",
			id : "SaleStoreForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "saleStore.id",
					id : "saleStoreId",
					xtype : "hidden",
					value : this.saleStoreId == null ? "" : this.saleStoreId
				}, {
					fieldLabel : "商场名称",
					name : "saleStore.storeName",
					id : "storeName",
					allowBlank : false,
					blankText : "编号不能为空！"
				},  {
					hiddenName : "saleStore.allowAreaId.id",
					id : "saleAreaName",
					xtype : "combo",
					fieldLabel : "所属商圈",
					triggerAction : "all",
					mode : "local",
					valueField : "id",
					displayField : "name",
					allowBlank : false,
					store : new Ext.data.SimpleStore({
						url : __ctxPath + "/bandpoor/comboBusinessArea.do",
						fields : ["id", "name"],
						remoteSort : true
					}),
					listeners : {
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"Q_areaName_S_LK" : queryEvent.query,
								"Q_flag_N_EQ" : 1
							};
							store.load();
							return false;
						}
					}
				}, {
					fieldLabel : "商场评分",
					name : "saleStore.storeScore",
					id : "storeScore"
				},
				{
					fieldLabel : "商场描述",
					name : "saleStore.storeDesc",
					id : "storeDesc",
					xtype: "textarea"
				}
			]
		});
		if(this.saleStoreId != null && this.saleStoreId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getSaleStore.do?id=" + this.saleStoreId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("saleAreaName").setValue(e.data.allowAreaId.id);
					Ext.getCmp("saleAreaName").setRawValue(e.data.allowAreaId.areaName);
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
					Ext.getCmp("SaleStoreView").gridPanel.store.reload({
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