HrPaAssessmentcriteriaForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaAssessmentcriteriaForm.superclass.constructor.call(this, {
			id : "HrPaAssessmentcriteriaFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 270,
			width : 400,
			title : "绩效考核标准录入",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/kpi/saveHrPaAssessmentcriteria.do",
			id : "HrPaAssessmentcriteriaForm",
			defaults : {
				anchor : "95%,95%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "hrPaAssessmentcriteria.id",
					id : "acId",
					xtype : "hidden",
					value : this.acId == null ? "" : this.acId
				}, {
					fieldLabel : "考核标准名称",
					labelStyle : "text-align:right;width:120px;",
					name : "hrPaAssessmentcriteria.acName",
					id : "acName",
					allowBlank : false,
					blankText : "考核标准名称不能为空！"
				}, {
					fieldLabel : "考核标准关键字",
					labelStyle : "text-align:right;width:120px;",
					name : "hrPaAssessmentcriteria.acKey",
					id : "acKey",
					allowBlank : false,
					blankText : "考核标准关键字不能为空！"
				}, {
					fieldLabel : "是否销售类定量考核",
					labelStyle : "text-align:right;width:120px;",
					xtype : "checkboxgroup",
					columns : 1,
					items : [
						{
							boxLabel : "是",
							name : "hrPaAssessmentcriteria.isSalesAC",
							id : "isSalesAC",
							inputValue : 1
						}
					]
				}, {
					fieldLabel : "考核标准描述",
					labelStyle : "text-align:right;width:120px;",
					name : "hrPaAssessmentcriteria.acDesc",
					height : 100,
					id : "acDesc",
					xtype : "textarea"
				}, {
					fieldLabel : "创建时间",
					name : "hrPaAssessmentcriteria.createDate",
					id : "createDate",
					xtype : "hidden"
				}, {
					fieldLabel : "创建人",
					name : "hrPaAssessmentcriteria.createPerson",
					id : "createPerson",
					xtype : "hidden"
				}, {
					fieldLabel : "状态",
					name : "hrPaAssessmentcriteria.publishStatus",
					id : "publishStatus",
					xtype : "hidden"
				}, {
					fieldLabel : "fromAc",
					name : "hrPaAssessmentcriteria.fromAc",
					id : "fromAc",
					xtype : "hidden"
				}
			]
		});
		if(this.acId != null && this.acId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/kpi/getHrPaAssessmentcriteria.do?id=" + this.acId,
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					if(e.data.isSalesAC == "1") {
						Ext.getCmp("isSalesAC").setValue(true);
					}
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
				text : "保存草稿",
				handler : this.saveAsDraft.createCallback(this.formPanel, this)
			}, {
				text : "确认发布",
				handler : this.saveToPublish.createCallback(this.formPanel, this)
			}
		];
	},
	cancel : function(a) {
		a.close();
	},
	saveAsDraft : function(a, b) {
		if(a.getForm().isValid()) {
			Ext.getCmp("publishStatus").setValue(0);
			a.getForm().submit({
				method : "post",
				
				waitMsg : "正在提交数据……",
				success : function(c, d) {
					Ext.ux.Toast.msg("提示信息","保存草稿成功！");
					if(b.from == "draft") {
						Ext.getCmp("DraftHrPaAssessmentcriteriaView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					} else if(b.from == "publish") {
						Ext.getCmp("PublishHrPaAssessmentcriteriaView").gridPanel.store.reload({
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
	},
	saveToPublish : function(a, b) {
		if(a.getForm().isValid()) {
			Ext.getCmp("publishStatus").setValue(3);
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据……",
				success : function(c, d) {
					Ext.ux.Toast.msg("提示信息","成功发布！");
					if(b.from == "draft") {
						Ext.getCmp("DraftHrPaAssessmentcriteriaView").gridPanel.store.reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					} else if(b.from == "publish") {
						Ext.getCmp("PublishHrPaAssessmentcriteriaView").gridPanel.store.reload({
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