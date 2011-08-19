RewardsPunishmentsForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		RewardsPunishmentsForm.superclass.constructor.call(this, {
			id : "RewardsPunishmentsFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 250,
			width : 400,
			title : "人员奖惩信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {

		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/rewardsPunishments/saveRewardsPunishments.do",
			id : "RewardsPunishmentsForm",
			defaults : {
				anchor : "98%,98%"
			},
			formId : "RewardsPunishmentsFormId",
			defaultType : "textfield",
			reader : new Ext.data.JsonReader({
				root : "data"
			}, [ {
				name : "rpId",
				mapping : "rpId"
			}, {
				name : "rewardsPunishmentsForm.appUser.userId",
				mapping : "appUser.userId"
			}, {
				name : "rewardsPunishmentsForm.empProfile.profileId",
				mapping : "empProfile.profileId"
			}, {
				name : "rewardsPunishmentsForm.appUserCombo",
				mapping : "appUser.fullname"
			}, {
				name : "rewardsPunishmentsForm.rpType.dicId",
				mapping : "rpType.dicId"
			}, {
				name : "rewardsPunishmentsForm.rpTypeCombo",
				mapping : "rpTypeStr"
			}, {
				name : "rewardsPunishmentsForm.amount",
				mapping : "amount"
			}, {
				name : "rewardsPunishmentsForm.remark",
				mapping : "remark"
			} ]),
			items : [ {
				name : "rewardsPunishments.rpId",
				id : "rpId",
				xtype : "hidden",
				value : this.rpId == null ? "" : this.rpId
			}, {
				name : "rewardsPunishments.appUser.userId",
				id : "rewardsPunishmentsForm.appUser.userId",
				xtype : "hidden"
			}, {
				name : "rewardsPunishments.empProfile.profileId",
				id : "rewardsPunishmentsForm.empProfile.profileId",
				xtype : "hidden"
			}, {
				fieldLabel : "受奖惩人",
				xtype:"combo",
				mode : "remote",
				allowBlank : false,
				editable : false,
				id : "rewardsPunishmentsForm.appUserCombo",
				valueField : "fullname",
				displayField : "fullname",
				triggerAction : "all",
				store : new Ext.data.JsonStore({
					root : "result",
					totalProperty : "totalCounts",
					remoteSort : true,
					url : __ctxPath
							+ "/hrm/listEmpProfile.do?start=0&limit=25",
					fields : [ "userId", "profileId", "fullname"]
				}),
				listeners : {
					select : function(l, h, k) {
						Ext.getCmp("rewardsPunishmentsForm.empProfile.profileId").setValue(h.data.profileId);
						Ext.getCmp("rewardsPunishmentsForm.appUser.userId").setValue(h.data.userId);
					}
				},
				bbar : new Ext.PagingToolbar({
					pageSize : 25,
					store : this.store,
					displayInfo : true,
					displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
					emptyMsg : "当前没有记录"
				})
			}, {
				name : "rewardsPunishments.rpType.dicId",
				id : "rewardsPunishmentsForm.rpType.dicId",
				xtype : "hidden"
			}, {
				fieldLabel : "奖惩类型",
				hiddenName:"rewardsPunishments.rpTypeStr",
				id : "rewardsPunishmentsForm.rpTypeCombo",
				maxHeight : 200,
				xtype : "combo",
				allowBlank : false,
				mode : "remote",
				editable : true,
				triggerAction : "all",
				displayField: "itemValue",
				valueField: "itemValue",
				store : new Ext.data.SimpleStore({
					url : __ctxPath + "/system/comboDictionary.do",
					baseParams : {
						itemName : "奖惩类型"
					},
					fields : ["dicId", "itemValue"]
				}),
				listeners : {
//					focus : function(d) {
//						var c = Ext.getCmp("rpTypeCombo").getStore();
//						if (c.getCount() <= 0) {
//							Ext.Ajax.request({
//								url : __ctxPath
//										+ "/system/comboDictionary.do",
//								method : "post",
//								params : {
//									itemName : "奖惩类型"
//								},
//								success : function(f) {
//									var e = Ext.util.JSON.decode(f.responseText);
//									c.loadData(e);
//								}
//							});
//						}
//					},
					select : function(l, h, k) {
						Ext.getCmp("rewardsPunishmentsForm.rpType.dicId").setValue(h.data.dicId);
					}
				}
			}, {
				fieldLabel : "金额",
				name : "rewardsPunishments.amount",
				id : "rewardsPunishmentsForm.amount",
				allowBlank : false,
				xtype:"numberfield"
			}, {
				xtype:"textarea",
				fieldLabel : "备注",
				name : "rewardsPunishments.remark",
				id : "rewardsPunishmentsForm.remark"
			} ]
		});
		if (this.rpId != null && this.rpId != "undefined") {
			this.formPanel.getForm().load({
						deferredRender : false,
						url : __ctxPath + "/rewardsPunishments/getRewardsPunishments.do?rpId=" + this.rpId,
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
				var a = Ext.getCmp("RewardsPunishmentsForm");
				if (a.getForm().isValid()) {
					a.getForm().submit({
						method : "POST",
						waitMsg : "正在提交数据...",
						success : function(b, d) {
							Ext.ux.Toast.msg("操作信息", "成功保存信息！");
							var c = Ext.getCmp("RewardsPunishmentsView");
							if (c != null) {
								c.gridPanel.store.reload();
							}
							Ext.getCmp("RewardsPunishmentsFormWin").close();
						},
						failure : function(b, c) {
							Ext.MessageBox.show({
								title : "操作信息",
								msg : "信息保存出错，请联系管理员！",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
							Ext.getCmp("RewardsPunishmentsFormWin").close();
						}
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : function() {
				Ext.getCmp("RewardsPunishmentsFormWin").close();
			}
		} ];
	}
});