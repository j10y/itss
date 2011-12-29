AreaForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		AreaForm.superclass.constructor.call(this, {
			id : "AreaFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 300,
			width : 350,
			title : "区域详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/system/saveMrbsArea.do",
			id : "AreaForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "mrbsArea.id",
					id : "mrbsAreaId",
					xtype : "hidden",
					value : this.mrbsAreaId == null ? "" : this.mrbsAreaId
				}, {
					fieldLabel : "区域名称",
					name : "mrbsArea.areaName",
					id : "areaName",
					allowBlank:false
				}, {
					fieldLabel : "区域备注",
					name : "mrbsArea.linkman",
					id : "linkman"
				}, {
					fieldLabel : "描述",
					name : "mrbsArea.descn",
					id : "descn",
					xtype : "textarea"
				}, {
					fieldLabel : "简述",
					name : "mrbsArea.shortdescn",
					id : "shortdescn",
					xtype : "textarea"
				}
			]
		});
		if(this.mrbsAreaId != null && this.mrbsAreaId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/system/getMrbsArea.do?id=" + this.mrbsAreaId,
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
		var areaName = Ext.getCmp("areaName").getValue();
		if(areaName == "") {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "区域名称不能为空！",
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
					if(Ext.getCmp("AreaView")){
						Ext.getCmp("AreaView").gridPanel.store.reload({
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