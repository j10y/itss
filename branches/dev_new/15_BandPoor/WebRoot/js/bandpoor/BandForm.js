BandForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		BandForm.superclass.constructor.call(this, {
			id : "BandFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 240,
			width : 350,
			title : "品牌详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/bandpoor/saveBand.do",
			id : "BandForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "band.id",
					id : "bandId",
					xtype : "hidden",
					value : this.bandId == null ? "" : this.bandId
				}, {
					fieldLabel : "中文名称",
					name : "band.bandChName",
					id : "bandChName"
				}, {
					fieldLabel : "英文名称",
					name : "band.bandEnName",
					id : "bandEnName"
				}, {
					fieldLabel : "类型",
					hiddenName : "band.bandStatus",
					id : "bandStatusName",
					xtype : "combo",
					value : this.bandStatus ? this.bandStatus : "0",
					rawValue : "常规品牌",
					editable : false,
					triggerAction : "all",
					readOnly : this.lockBandStatus ? true : false,
					store : [
						[
							"0",
							"常规品牌"
						], [
							"1",
							"非常规品牌"
						]
					]
				}, {
					fieldLabel : "描述",
					name : "band.bandDesc",
					id : "bandDesc",
					xtype : "textarea"
				}
			]
		});
		if(this.bandId != null && this.bandId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/bandpoor/getBand.do?id=" + this.bandId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					if(e.data.bandStatus == "0") {
						Ext.getCmp("bandStatusName").setValue(e.data.bandStatus);
						Ext.getCmp("bandStatusName").setRawValue("常规品牌");
					} else if(e.data.bandStatus == "1") {
						Ext.getCmp("bandStatusName").setValue(e.data.bandStatus);
						Ext.getCmp("bandStatusName").setRawValue("非常规品牌");
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
		var chName = Ext.getCmp("bandChName").getValue();
		var enName = Ext.getCmp("bandEnName").getValue();
		if(chName == "" && enName == "") {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "中文名称或英文名称至少需要填写一项！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		if(a.getForm().isValid()) {
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function() {
					Ext.ux.Toast.msg("提示信息","保存成功！");
					if(Ext.getCmp("BandView")){
						Ext.getCmp("BandView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					}					
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