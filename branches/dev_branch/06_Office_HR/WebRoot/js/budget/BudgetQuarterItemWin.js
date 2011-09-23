BudgetQuarterItemWin = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		BudgetQuarterItemWin.superclass.constructor.call(this, {
			id : "BudgetQuarterItemWin",
			layout : "fit",
			scope : this,
			items : [this.formPanel],
			modal : true,
			width:350,
			height:250,
			maximizable : true,
			title : "（季度）成本要素详细信息",
			buttonAlign : "right",
			buttons : this.buttons
		});
	},
	initUIComponents : function() {
		
		/**
		 * 成本要素详细信息
		 */
		this.formPanel = new Ext.FormPanel({
			//title:"成本要素详细信息",
			layout : "form",
			region: 'center',
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/budget/saveBudgetItem.do",
			id : "BudgetItemForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			reader : new Ext.data.JsonReader({
				root : "data"
			}, [ {
				name : "budgetQuarterItem.budgetItemId",
				mapping : "budgetItemId"
			}, {
				name : "budgetQuarterItem.budget.budgetId",
				mapping : "budget.budgetId"
			}, {
				name : "budgetQuarterItem.name",
				mapping : "name"
			}, {
				name : "budgetQuarterItem.code",
				mapping : "code"
			}, {
				name : "budgetQuarterItem.key",
				mapping : "key"
			}, {
				name : "budgetQuarterItem.value",
				mapping : "value"
			}, {
				name : "budgetQuarterItem.threshold",
				mapping : "threshold"
			}, {
				name : "budgetQuarterItem.thresholdTmp",
				mapping : "threshold",
				convert : function(v, rec){
					if(v!=null){
						return v*100;
					}else{
						return 0;
					}
				}
			}, {
				name : "budgetQuarterItem.parent.budgetItemId",
				//mapping : "parent.budgetItemId",
				mapping : "parent",
				convert : function(v, rec){
					if(v!=null){
						return v.budgetItemId;
					}else{
						return 0;
					}
				}
			}, {
				name : "budgetQuarterItem.belongItem.budgetItemId",
				//mapping : "parent.budgetItemId",
				mapping : "belongItem",
				convert : function(v, rec){
					if(v!=null){
						return v.budgetItemId;
					}else{
						return 0;
					}
				}
			}, {
				name : "budgetQuarterItem.belongItem.name",
				//mapping : "parent.budgetItemId",
				mapping : "belongItem",
				convert : function(v, rec){
					if(v!=null){
						return v.name;
					}else{
						return 0;
					}
				}
			}, {
				name : "budgetQuarterItem.deleteFlag",
				mapping : "deleteFlag"
			} ]),
			items : [ {
				name : "budgetItem.budgetItemId",
				id : "budgetQuarterItem.budgetItemId",
				xtype : "hidden",
				value : this.budgetItemId == null ? "" : this.budgetItemId
			}, {
				xtype : "hidden",
				name : "budgetItem.budget.budgetId",
				id : "budgetQuarterItem.budget.budgetId",
				value : this.budgetId == null ? "" : this.budgetId
			}, {
				xtype : 'combo',
				anchor : '98%',
				fieldLabel : "所属年度成本要素",
				id : "budgetQuarterItem.belongItem.name",
				mode : "remote",
				valueField : "name",
				allowBlank : false,
				displayField : "name",
				triggerAction : "all",
				store : new Ext.data.JsonStore({
					url : __ctxPath
							+ "/budget/listBudgetItem.do",
					fields : [ "budgetItemId", "name" ],
					root : "result",
					totalProperty : "totalCounts",
					remoteSort : true
					
				}),
				listeners : {
					select : function(l, h, k) {
						
						Ext.getCmp("budgetQuarterItem.belongItem.budgetItemId").setValue(h.data.budgetItemId);
						//这里需要设置值。。。。。。。。。。。。。。。。。。。。。
					},
					beforequery : function(queryEvent) {
						//alert(Ext.getCmp("BudgetQuarterItemWin").budgetId);
						var store=queryEvent.combo.store;
						store.baseParams={
							"Q_name_S_LK": queryEvent.query,
							"Q_deleteFlag_N_EQ": 0,
							"Q_budget.budgetId_L_EQ": Ext.getCmp("BudgetQuarterItemWin").belongBudget
						};
						store.load({
							params:{
								start : 0,
								limit : 25
							}
						});
						return false;
					}
				}
			}, {
				fieldLabel : "成本要素名称",
				allowBlank : false,
				name : "budgetItem.name",
				id : "budgetQuarterItem.name"
			}, {
				fieldLabel : "成本要素编号",
				allowBlank : false,
				name : "budgetItem.code",
				id : "budgetQuarterItem.code"
			}, {
				fieldLabel : "成本要素缩写",
				allowBlank : false,
				name : "budgetItem.key",
				id : "budgetQuarterItem.key"
			}, {
				fieldLabel : "预算金额",
				allowBlank : false,
				name : "budgetItem.value",
				id : "budgetQuarterItem.value",
				xtype:"numberfield"
			}/*, {
				fieldLabel : "控制阀值(%)",
				allowBlank : false,
				name : "budgetItem.threshold",
				id : "budgetQuarterItem.threshold"
					
			}*/			
			, {
				xtype : "hidden",
				name : "budgetItem.threshold",
				id : "budgetQuarterItem.threshold"
			}, {
				fieldLabel : "控制阀值(%)",
				id : "budgetQuarterItem.thresholdTmp",
				listeners : {
					change : function(field, newValue, oldValue){
						Ext.getCmp("budgetItem.threshold").setValue(newValue/100);
					}
				},
				xtype:"numberfield"
			}, {
				xtype : "hidden",
				name : "budgetItem.parent.budgetItemId",
				id : "budgetQuarterItem.parent.budgetItemId",
				value : this.parentBudgetItemId == null ? "" : this.parentBudgetItemId
			}, {
				xtype : "hidden",
				name : "budgetItem.belongItem.budgetItemId",
				id : "budgetQuarterItem.belongItem.budgetItemId"
			}, {
				xtype : "hidden",
				name : "budgetItem.deleteFlag",
				id : "budgetQuarterItem.deleteFlag",
				value : 0
			} ]
		});		
		var buttons = [ {
			text : "保存",
			iconCls : "btn-save",
			handler : this.save.createCallback(this.formPanel, this)
		}/*, {
			text : "重置",
			iconCls : "btn-reset",
			handler : this.reset.createCallback(this.formPanel)
		}*/, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : this.cancel.createCallback(this)
		} ];
		this.buttons = buttons;
		
		if (this.budgetItemId != null && this.budgetItemId != "undefined") {
			Ext.getCmp("budgetQuarterItem.belongItem.name").disable();
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath
						+ "/budget/loadBudgetItem.do?budgetItemId="
						+ this.budgetItemId,
				waitMsg : "正在载入数据...",
				success : function(c, d) {
					//alert(d.response.responseText);
					//alert(Ext.getCmp("budgetQuarterItem.parent.budgetItemId").getValue());
					return;
					var e = d.result.data.department;
					Ext.getCmp("BudgetForm.depName").setValue(
							e.depName);
					Ext.getCmp("depId").setValue(e.depId);
				},
				failure : function(c, d) {
					
				}
			});
		}
		
	},
	reset : function(a) {
		a.getForm().reset();
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a) {
		if (a.getForm().isValid()) {
			//校验某一级成本预算金额的和小于上一级别
			
			a.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					var data = Ext.decode(e.response.responseText);
					Ext.getCmp("budgetQuarterItem.budgetItemId").setValue(data.budgetItemId);
					Ext.getCmp("budgetItemQuarterTree").root.reload();
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					Ext.getCmp("BudgetQuarterItemWin").close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					Ext.getCmp("BudgetQuarterItemWin").close();
				}
			});
		}
	},
	submit : function(a, b) {
		
		
	}
});