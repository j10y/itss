BandStyleForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BandStyleForm.superclass.constructor.call(this, {
			id : "BandStyleFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 240,
			width : 350,
			title : "品牌风格详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveBandStyle.do",
			id : "BandStyleForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "bandStyle.id",
					id : "bandStyleId",
					xtype : "hidden",
					value : this.bandStyleId == null ? "" : this.bandStyleId
				}, {
					fieldLabel : "编号",
					name : "bandStyle.styleNum",
					id : "styleNum",
					allowBlank : false,
					blankText : "编号不能为空！"
				}, {
					fieldLabel : "名称",
					name : "bandStyle.styleName",
					id : "styleName",
					allowBlank : false,
					blankText : "名称不能为空！"
				}, {
					hiddenName : "bandStyle.proClassId.id",
					id : "styleProClassName",
					xtype : "combo",
					fieldLabel : "所属品类",
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
					fieldLabel : "描述",
					name : "bandStyle.styleDesc",
					id : "styleDesc",
					xtype : "textarea"
				}
			]
		});
		if(this.bandStyleId != null && this.bandStyleId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBandStyle.do?id=" + this.bandStyleId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					Ext.getCmp("styleProClassName").setValue(e.data.proClassId.id);
					Ext.getCmp("styleProClassName").setRawValue(e.data.proClassId.proClassName);
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
					Ext.getCmp("BandStyleView").gridPanel.store.reload({
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