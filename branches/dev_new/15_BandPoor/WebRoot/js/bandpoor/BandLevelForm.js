BandLevelForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BandLevelForm.superclass.constructor.call(this, {
			id : "BandLevelFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 180,
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
			url : __ctxPath + "/bandpoor/saveBandLevel.do",
			id : "BandLevelForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "bandLevel.id",
					id : "bandLevelId",
					xtype : "hidden",
					value : this.bandLevelId == null ? "" : this.bandLevelId
				}, {
					fieldLabel : "品牌池名称",
					name : "bandLevel.levelName",
					id : "levelName",
					allowBlank : false,
					blankText : "品牌池名称不能为空！"
				}, {
					fieldLabel : "品牌池描述",
					name : "bandLevel.levelDesc",
					id : "levelDesc",
					xtype:"textarea"
				}
			]
		});
		if(this.bandLevelId != null && this.bandLevelId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBandLevel.do?id=" + this.bandLevelId,
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
					Ext.getCmp("BandLevelView").gridPanel.store.reload({
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