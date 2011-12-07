BusinessAreaForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BusinessAreaForm.superclass.constructor.call(this, {
			id : "BusinessAreaFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 240,
			width : 350,
			title : "商圈详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveBusinessArea.do",
			id : "BusinessAreaForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "businessArea.id",
					id : "businessAreaId",
					xtype : "hidden",
					value : this.businessAreaId == null ? "" : this.businessAreaId
				}, {
					fieldLabel : "商圈名称",
					name : "businessArea.areaName",
					id : "areaName",
					allowBlank : false,
					blankText : "商圈名称不能为空！"
				}, {
					fieldLabel : "商圈描述",
					name : "businessArea.areaDesc",
					id : "areaDesc",
					allowBlank : false,
					blankText : "商圈描述不能为空！"
				}
			]
		});
		if(this.businessAreaId != null && this.businessAreaId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBusinessArea.do?id=" + this.businessAreaId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
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
					Ext.getCmp("BusinessAreaView").gridPanel.store.reload({
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