HrPaPerformanceindexscoreForm1 = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		HrPaPerformanceindexscoreForm1.superclass.constructor.call(this, {
			id : "HrPaPerformanceindexscoreForm1Win",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 180,
			width : 400,
			title : "增加/修改评分标准对话框--非计算类",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/kpi/saveHrPaPerformanceindexscore.do",
			id : "HrPaPerformanceindexscoreForm1",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [
				{
					name : "hrPaPerformanceindexscore.id",
					id : "id",
					xtype : "hidden",
					value : this.id == null ? "" : this.id
				}, {
					name : "hrPaPerformanceindexscore.piId",
					id : "piId",
					xtype : "hidden",
					value : this.piId
				}, {
					name : "hrPaPerformanceindexscore.pisType",
					id : "pisType",
					xtype : "hidden",
					value : this.pisType
				}, {
					fieldLabel : "得分",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindexscore.pisScore",
					id : "pisScore",
					allowBlank : false,
					blankText : "分数不能为空！"
				}, {
					fieldLabel : "评分说明",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindexscore.pisDesc",
					id : "pisDesc",
					xtype : "textarea"
				}
			]
		});
		if(this.pisId != null && this.pisId != "undefined") {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + "/kpi/getHrPaPerformanceindexscore.do",
				params : {
					id : this.pisId,
					piId : this.piId
				},
				waitMsg : "正在载入数据……",
				success : function(f, d) {
					
				},
				failure : function() {
					
				}
			});
		}
		this. buttons = [
			{
				text : "取消",
				handler : this.cancel.createCallback(this)
			}, {
				text : "确认",
				handler : this.save.createCallback(this.formPanel, this)
			}
		];
	},
	cancel : function(a) {
		new HrPaPerformanceindexscoreView({
			piId : Ext.getCmp("piId").getValue(),
			paMode : Ext.getCmp("pisType").getValue()
		}).show();
		a.close();
	},
	save : function(a, b) {
		if(a.getForm().isValid()) {
			a.getForm().submit({
				method : "post",
				waitMsg : "正在提交数据…",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息","成功保存信息！");
					new HrPaPerformanceindexscoreView({
						piId : Ext.getCmp("piId").getValue(),
						paMode : Ext.getCmp("pisType").getValue()
					}).show();
					b.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
	}
});