BudgetItemWin = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		BudgetItemWin.superclass.constructor.call(this, {
			id : "BudgetItemWin",
			layout : "fit",
			scope : this,
			items : [this.formPanel],
			modal : true,
			width:350,
			height:250,
			maximizable : true,
			title : "成本要素详细信息",
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
			items : [ {
				name : "budgetItem.budgetItemId",
				id : "budgetItem.budgetItemId",
				xtype : "hidden",
				value : this.budgetItemId == null ? "" : this.budgetItemId
			}, {
				xtype : "hidden",
				name : "budgetItem.budget.budgetId",
				id : "budgetItem.budget.budgetId",
				value : this.budgetId == null ? "" : this.budgetId
			}, {
				fieldLabel : "成本要素名称",
				allowBlank : false,
				name : "budgetItem.name",
				id : "budgetItem.name"
			}, {
				fieldLabel : "成本要素编号",
				allowBlank : false,
				name : "budgetItem.code",
				id : "budgetItem.code"
			}, {
				fieldLabel : "成本要素缩写",
				allowBlank : false,
				name : "budgetItem.key",
				id : "budgetItem.key"
			}, {
				fieldLabel : "预算金额",
				allowBlank : false,
				name : "budgetItem.value",
				id : "budgetItem.value"
			}, {
				fieldLabel : "控制阀值",
				allowBlank : false,
				name : "budgetItem.threshold",
				id : "budgetItem.threshold"
			}, {
				xtype : "hidden",
				name : "budgetItem.parent.budgetItemId",
				id : "budgetItem.parent.budgetItemId",
				value : this.parentBudgetItemId == null ? "" : this.parentBudgetItemId
			}, {
				xtype : "hidden",
				name : "budgetItem.deleteFlag",
				id : "budgetItem.deleteFlag",
				value : 0
			} ]
		});		
		
		var buttons = [ {
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
		this.buttons = buttons;
		
	},
	reset : function(a) {
		a.getForm().reset();
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a) {
		if (a.getForm().isValid()) {
			a.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					var data = Ext.decode(e.response.responseText);
					Ext.getCmp("budgetItem.budgetItemId").setValue(data.budgetItemId);
					Ext.getCmp("budgetItemTree").root.reload();
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					Ext.getCmp("BudgetItemWin").close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					Ext.getCmp("BudgetItemWin").close();
				}
			});
		}
	},
	submit : function(a, b) {
		
		
	}
});