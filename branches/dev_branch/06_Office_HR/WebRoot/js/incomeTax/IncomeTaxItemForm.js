IncomeTaxItemForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		IncomeTaxItemForm.superclass.constructor.call(this, {
			id : "IncomeTaxItemFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 200,
			width : 400,
			title : "预算执行信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {

		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/incomeTaxItem/saveIncomeTaxItem.do",
			id : "IncomeTaxItemForm",
			defaults : {
				anchor : "98%,98%"
			},
			formId : "IncomeTaxItemFormId",
			defaultType : "textfield",
			reader : new Ext.data.JsonReader({
				root : "data"
			}, [ {
				name : "itiId",
				mapping : "itiId"
			}, {
				name : "incomeTax.itId",
				mapping : "incomeTax.itId"
			}, {
				name : "limitAmount",
				mapping : "limitAmount"
			}, {
				name : "lowerAmount",
				mapping : "lowerAmount"
			}, {
				name : "taxValue",
				mapping : "taxValue"
			}, {
				name : "taxValueTmp",
				mapping : "taxValue",
				convert : function(v, rec){
					if(v!=null){
						return v*100;
					}else{
						return 0;
					}
				}
			}  ]),
			items : [ {
				name : "incomeTaxItem.itiId",
				id : "itiId",
				xtype : "hidden",
				value : this.itiId == null ? "" : this.itiId
			}, {
				name : "incomeTaxItem.incomeTax.itId",
				id : "incomeTax.itId",
				xtype : "hidden",
				value : this.itId == null ? "" : this.itId
			}, {
				fieldLabel : "上限值",
				name : "incomeTaxItem.limitAmount",
				id : "limitAmount",
				allowBlank : false,
				xtype:"numberfield"
			}, {
				fieldLabel : "下限值",
				name : "incomeTaxItem.lowerAmount",
				id : "lowerAmount",
				allowBlank : false,
				xtype:"numberfield"
			}, {
				xtype:"hidden",
				readOnly:true,
				name : "incomeTaxItem.taxValue",
				id : "taxValue"
			}, {
				fieldLabel : "比例(%)",
				id : "taxValueTmp",
				allowBlank : false,
				listeners : {
					change : function(field, newValue, oldValue){
						Ext.getCmp("taxValue").setValue(newValue/100);
					}
				},
				xtype:"numberfield"
			}  ]
		});
		if (this.itiId != null && this.itiId != "undefined") {
			this.formPanel.getForm().load({
						deferredRender : false,
						url : __ctxPath + "/incomeTaxItem/getIncomeTaxItem.do?itiId="
								+ this.itiId,
						waitMsg : "正在载入数据...",
						success : function(a, b) {
						},
						failure : function(a, b) {
						}
					});
		}
		this.buttons = [ {
			text : "保存",
			iconCls : "btn-save",
			handler : function() {
				var a = Ext.getCmp("IncomeTaxItemForm");
				if (a.getForm().isValid()) {
					a.getForm().submit({
						method : "POST",
						waitMsg : "正在提交数据...",
						success : function(b, d) {
							Ext.ux.Toast.msg("操作信息", "成功保存信息！");
							var c = Ext.getCmp("IncomeTaxViewPanel");
							if (c != null) {
								c.gridPanel.store.reload();
							}
							Ext.getCmp("IncomeTaxItemFormWin").close();
						},
						failure : function(b, c) {
							Ext.MessageBox.show({
								title : "操作信息",
								msg : "信息保存出错，请联系管理员！",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
							Ext.getCmp("IncomeTaxItemFormWin").close();
						}
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : function() {
				Ext.getCmp("IncomeTaxItemFormWin").close();
			}
		} ];
	}
});