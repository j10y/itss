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
			height : 205,
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
					value : this.id == null ? "0" : this.id
				}, {
					name : "hrPaPerformanceindexscore.pi.id",
					id : "piIdForm1",
					xtype : "hidden",
					value : this.piId
				}, {
					fieldLabel : "得分",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindexscore.pisScore",
					id : "pisScore",
					maskRe : /[\d.]/,
					allowBlank : false,
					blankText : "分数不能为空！"
				}, {
					fieldLabel : "绩效系数",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindexscore.coefficient",
					id : "coefficient",
					maskRe : /[\d.]/,
					allowBlank : false,
					blankText : "绩效系数不能为空！"
				}, {
					fieldLabel : "评分说明",
					labelStyle : "text-align:right",
					name : "hrPaPerformanceindexscore.pisDesc",
					id : "pisDesc",
					xtype : "textarea"
				}
			]
		});
		if(this.pisId != null && this.pisId != "undefined" && this.pisId != 0) {
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
		} else if(this.pisId == 0) {
			var rowStore = Ext.getCmp("HrPaPerformanceindexscoreGrid").getStore().getAt(this.rowNumber);
			Ext.getCmp("pisScore").setValue(rowStore.data.pisScore);
			Ext.getCmp("pisDesc").setValue(rowStore.data.pisDesc);
		}
		this.buttons = [
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
		a.close();
	},
	save : function(a, b) {
		if(isNaN(Ext.getCmp("pisScore").getValue()) || Ext.getCmp("pisScore").getValue() < 1 || Ext.getCmp("pisScore").getValue() > 5) {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "得分必须是1-5之间的数字，请重新填写。",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		if(isNaN(Ext.getCmp("coefficient").getValue())) {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "请正确填写绩效系数。",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		if(a.getForm().isValid()) {
			var d = Ext.getCmp("HrPaPerformanceindexscoreGrid");
			//删除gridPanel里边修改以前的记录
			if(b.rowNumber != null && b.rowNumber != "undefined") {
				d.getStore().removeAt(b.rowNumber);
			}
			var pisRecord = Ext.data.Record.create([
				{
					name : "id",
					type : "int"
				}, {
					name : "pi.id",
					type : "int"
				}, {
					name : "pisScore",
					type : "float"
				}, {
					name : "pisDesc",
					type : "string"
				}
			]);
			var pis = new pisRecord({
				"id" : Ext.getCmp("id").getValue(),
				"pi.id" : Ext.getCmp("piIdForm1").getValue() == null ? 0 : Ext.getCmp("piIdForm1").getValue(),
				"pisScore" : Ext.getCmp("pisScore").getValue(),
				"pisDesc" : Ext.getCmp("pisDesc").getValue(),
				"coefficient" : Ext.getCmp("coefficient").getValue()
			});
			d.getStore().add(pis);
			b.close();
		}
	}
});