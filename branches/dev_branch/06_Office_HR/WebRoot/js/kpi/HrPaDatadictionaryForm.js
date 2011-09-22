HrPaDatadictionaryForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaDatadictionaryForm.superclass.constructor.call(this, {
			id : "HrPaDatadictionaryFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 200,
			width : 350,
			title : "字典详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/kpi/saveHrPaDatadictionary.do",
			id : "HrPaDatadictionaryForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "hrPaDatadictionary.id",
					id : "dicId",
					xtype : "hidden",
					value : this.dicId == null ? "" : this.dicId
				}, {
					fieldLabel : "条目",
					hiddenName : "hrPaDatadictionary.parentId",
					maxHeight : 200,
					id : "parentName",
					xtype : "combo",
					mode : "local",
					editable : false,
					valueField : "id",
					displayField : "name",
					triggerAction : "all",
					allowBlank : false,
					blankText : "条目不能为空！",
					store : new Ext.data.SimpleStore({
						fields : ["id","name"]
					}),
					listeners : {
						focus : function(d) {
							var c = Ext.getCmp("parentName").getStore();
							if(c.getCount() <= 0) {
								Ext.Ajax.request({
									url : __ctxPath + "/kpi/loadEntryHrPaDatadictionary.do",
									method : "post",
									success : function(f) {
										var e = Ext.util.JSON.decode(f.responseText);
										c.loadData(e);
									}
								});
							}
						}
					}
				}, {
					fieldLabel : "名称",
					name : "hrPaDatadictionary.name",
					id : "name",
					allowBlank : false,
					blankText : "名称不能为空！"
				}
			]
		});
		if(this.dicId != null && this.dicId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/kpi/getHrPaDatadictionary.do?id=" + this.dicId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("parentName").setValue(e.data.parentId);
					Ext.getCmp("parentName").setRawValue(e.parentName);
				},
				failure : function() {
					
				}
			});
		}
		this.buttons = [
			{
				text : "取消",
				handler : this.cancel.createCallback(this)
			}, {
				text : "保存",
				handler : this.save.createCallback(this.formPanel, this)
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
					Ext.getCmp("HrPaDatadictionaryView").gridPanel.store.reload({
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