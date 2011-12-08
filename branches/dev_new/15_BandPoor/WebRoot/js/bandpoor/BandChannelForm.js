BandChannelForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BandChannelForm.superclass.constructor.call(this, {
			id : "BandChannelFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 180,
			width : 350,
			title : "品牌渠道详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveBandChannel.do",
			id : "BandChannelForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "bandChannel.id",
					id : "bandChannelId",
					xtype : "hidden",
					value : this.bandChannelId == null ? "" : this.bandChannelId
				}, {
					fieldLabel : "名称",
					name : "bandChannel.channelName",
					id : "channelName",
					allowBlank : false,
					blankText : "名称不能为空！"
				}, {
					fieldLabel : "描述",
					name : "bandChannel.channelDesc",
					id : "channelDesc",
					xtype : "textarea"
				}
			]
		});
		if(this.bandChannelId != null && this.bandChannelId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBandChannel.do?id=" + this.bandChannelId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					
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
					Ext.getCmp("BandChannelView").gridPanel.store.reload({
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
						msg : d.msg,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					return ;
				}
			});
		}
	}
});