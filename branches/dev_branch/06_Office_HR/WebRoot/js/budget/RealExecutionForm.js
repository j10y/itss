RealExecutionForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		RealExecutionForm.superclass.constructor.call(this, {
			id : "RealExecutionFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 250,
			width : 400,
			title : "预算执行信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function() {

		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/budget/saveRealExecution.do",
			id : "RealExecutionForm",
			defaults : {
				anchor : "98%,98%"
			},
			formId : "RealExecutionFormId",
			defaultType : "textfield",
			reader : new Ext.data.JsonReader({
				root : "data"
			}, [ {
				name : "realExecutionId",
				mapping : "realExecutionId"
			}, {
				name : "realExecutionForm.budget.budgetId",
				mapping : "budget.budgetId"
			}, {
				name : "realExecutionForm.budgetCombo",
				mapping : "budget.name"
			}, {
				name : "realExecutionForm.budgetItem.budgetItemId",
				mapping : "budgetItem.budgetItemId"
			}, {
				name : "realExecutionForm.budgetItemCombo",
				mapping : "budgetItem.name"
			}, {
				name : "realExecutionForm.realValue",
				mapping : "realValue"
			}, {
				name : "realExecutionForm.month",
				mapping : "month"
			}, {
				name : "realExecutionForm.remark",
				mapping : "remark"
			} ]),
			items : [ {
				name : "realExecution.realExecutionId",
				id : "realExecutionId",
				xtype : "hidden",
				value : this.realExecutionId == null ? "" : this.realExecutionId
			}, {
				name : "realExecution.budget.budgetId",
				id : "realExecutionForm.budget.budgetId",
				xtype : "hidden",
				value : "0"
			}, {
				fieldLabel : "所属预算",
				xtype:"combo",
				mode : "remote",
				allowBlank : false,
				editable : false,
				id : "realExecutionForm.budgetCombo",
				valueField : "name",
				displayField : "name",
				triggerAction : "all",
				store : new Ext.data.SimpleStore({
					url : __ctxPath
							+ "/budget/comboBudget.do?Q_publishStatus_N_EQ=3",
					fields : [ "id", "name" ]
				}),
				listeners : {
					select : function(l, h, k) {
						Ext.getCmp("budget.budgetId").setValue(h.data.id);
					}
				}
			},  {
				name : "realExecution.budgetItem.budgetItemId",
				id : "realExecutionForm.budgetItem.budgetItemId",
				xtype : "hidden"
			}, {
				fieldLabel : "所属成本要素",
				xtype:"combo",
				id : "realExecutionForm.budgetItemCombo",
				mode : "local",
				allowBlank : false,
				editable : false,
				valueField : "name",
				displayField : "name",
				triggerAction : "all",
				store : new Ext.data.SimpleStore({
					url : __ctxPath + "/budget/comboBudgetItem.do",
					fields : [ "id", "name" ]
				}),
				listeners : {
					select : function(l, h, k) {
						Ext.getCmp("budgetItem.budgetItemId").setValue(h.data.id);
					},
					beforeQuery : function(queryEvent){
						if(Ext.getCmp("budget.budgetId").getValue()==0){
							Ext.Msg.alert("提示信息","请选择所属预算！");
						}else{
							queryEvent.combo.store.url = __ctxPath + "/budget/comboBudgetItem.do?Q_deleteFlag_N_EQ=0&Q_budget.budgetId_L_EQ=" + Ext.getCmp("budget.budgetId").getValue(),
							queryEvent.combo.store.reload({
								params:{
									"Q_deleteFlag_N_EQ" : 0,
									"Q_budget.budgetId_L_EQ" : Ext.getCmp("budget.budgetId").getValue()
								}
							});
						}
					}
				}
			},  {
				fieldLabel : "执行值",
				name : "realExecution.realValue",
				id : "realExecutionForm.realValue",
				allowBlank : false,
				xtype:"numberfield"
			}, {
				fieldLabel : "月份",
				id : "realExecutionForm.month",
				xtype : "combo",
				allowBlank : false,
				hiddenName : "realExecution.month",
				emptyText : "请选择月份",
				mode : "local",
				editable : false,
				triggerAction : "all",
				store : [ [ "1", "一月" ], [ "2", "二月" ] 
					, [ "3", "三月" ], [ "4", "四月" ]
					, [ "5", "五月" ], [ "6", "六月" ]
					, [ "7", "七月" ], [ "8", "八月" ]
					, [ "9", "九月" ], [ "10", "十月" ]
					, [ "11", "十一月" ], [ "12", "十二月" ]
				],
				anchor : "98%",
				value : new Date().getMonth()+1
			}, {
				fieldLabel : "备注",
				name : "realExecution.remark",
				id : "realExecutionForm.remark"
			} ]
		});
		if (this.realExecutionId != null && this.realExecutionId != "undefined") {
			this.formPanel.getForm().load({
						deferredRender : false,
						url : __ctxPath + "/budget/getRealExecution.do?realExecutionId="
								+ this.realExecutionId,
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
				var a = Ext.getCmp("RealExecutionForm");
				if (a.getForm().isValid()) {
					a.getForm().submit({
						method : "POST",
						waitMsg : "正在提交数据...",
						success : function(b, d) {
							Ext.ux.Toast.msg("操作信息", "成功保存信息！");
							var c = Ext.getCmp("RealExecutionView");
							if (c != null) {
								c.gridPanel.store.reload();
							}
							Ext.getCmp("RealExecutionFormWin").close();
						},
						failure : function(b, c) {
							Ext.MessageBox.show({
								title : "操作信息",
								msg : "信息保存出错，请联系管理员！",
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
							Ext.getCmp("RealExecutionFormWin").close();
						}
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : function() {
				Ext.getCmp("RealExecutionFormWin").close();
			}
		} ];
	}
});