RewardsPunishmentsTypeForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		RewardsPunishmentsTypeForm.superclass.constructor.call(this, {
			id : "RewardsPunishmentsTypeFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 200,
			iconCls : "menu-arch-rec-type",
			width : 400,
			maximizable : true,
			title : "奖惩分类详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/rewardsPunishments/saveRewardsPunishmentsType.do",
			id : "RewardsPunishmentsTypeForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [ {
				name : "rewardsPunishmentsType.typeId",
				id : "typeId",
				xtype : "hidden",
				value : this.recTypeId == null ? "" : this.recTypeId
			}, {
				fieldLabel : "分类名称",
				allowBlank : false,
				name : "rewardsPunishmentsType.typeName",
				id : "typeName"
			}, {
				xtype : "combo",
				fieldLabel : "符号",
				allowBlank : false,
				hiddenName : "rewardsPunishmentsType.operation",
				id : "operation",
				xtype : "combo",
				allowBlank : false,
				emptyText : "请选择符号",
				mode : "local",
				editable : false,
				triggerAction : "all",
				store : [ [ "1", "+" ], [ "0", "-" ]
				],
				anchor : "98%"
			
			}, {
				fieldLabel : "分类描述",
				name : "rewardsPunishmentsType.typeDesc",
				id : "typeDesc"
			}]
		});
		if (this.rpTypeId != null && this.rpTypeId != "undefined") {
			this.formPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath
								+ "/rewardsPunishments/getRewardsPunishmentsType.do?rpTypeId="
								+ this.rpTypeId,
						waitMsg : "正在载入数据...",
						success : function(c, d) {
							var e = d.result.data.department;
						},
						failure : function(c, d) {
						}
					});
		}
		this.buttons = [ {
			text : "保存",
			iconCls : "btn-save",
			handler : this.save.createCallback(this.formPanel, this)
		}, {
			text : "重置",
			iconCls : "btn-reset",
			handler : this.reset.createCallback(this.formPanel)
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : this.cancel.createCallback(this)
		} ];
	},
	reset : function(a) {
		a.getForm().reset();
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a, b) {
		if (a.getForm().isValid()) {
			a.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("RewardsPunishmentsTypeGrid");
					if (d != null) {
						d.getStore().reload();
					}
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					b.close();
				}
			});
		}
	}
});