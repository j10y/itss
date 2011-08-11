BudgetForm = Ext.extend(Ext.Panel, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		BudgetForm.superclass.constructor.call(this, {
			id : "BudgetFormWin",
			layout : "border",
			scope : this,
//			items : [this.leftTypePanel,this.itemFormPanel, {xtype:'container', height:150, region:'north', items: this.mainFormPanel}],
			items : [this.leftTypePanel,this.itemFormPanel, this.mainFormPanel],
			maximizable : true,
			title : "预算详细信息"
		});
	},
	initUIComponents : function() {
		var a = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var b = new TreeSelector("BudgetForm.depName", a, "所属部门", "budget.depId",
				false);
		
		/**
		 * 预算主panel
		 */
		this.mainFormPanel = new Ext.FormPanel({
			id:"mainFormPanel",
			region:"north",
			title:"预算基本信息",
			layout : "tableform",
			layoutConfig:{
				columns:2
			},
			height:150,
			frame:true,
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/budget/saveBudget.do",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			reader : new Ext.data.JsonReader({
				root : "data"
			}, [ {
				name : "budget.budgetId",
				mapping : "budgetId"
			}, {
				name : "budget.belongDept.depId",
				mapping : "belongDept.depId"
			}, {
				name : "budget.depId",
				mapping : "belongDept.depId"
			}, {
				name : "budget.name",
				mapping : "name"
			},  {
				name : "budget.beginDate",
				mapping : "beginDate"
			}, {
				name : "budget.endDate",
				mapping : "endDate"
			} ]),
			items : [ {
				name : "budget.budgetId",
				id : "budget.budgetId",
				xtype : "hidden",
				value : this.budgetId == null ? "" : this.budgetId
			}, {
				xtype : "hidden",
				name : "budget.belongDept.depId",
				id : "budget.belongDept.depId"
			}, {
				fieldLabel : "预算名称",
				allowBlank : false,
				name : "budget.name",
				id : "budget.name"
			}, b 
			, {
				fieldLabel : "起始时间",
				allowBlank : false,
				name : "budget.beginDate",
				id : "budget.beginDate",
				xtype:"datefield",
				format:"Y-m-d"
			},{
				fieldLabel : "结束时间",
				allowBlank : false,
				name : "budget.endDate",
				id : "budget.endDate",
				xtype:"datefield",
				format:"Y-m-d"
			}],
			buttonAlign : "right",
			buttons : [{
				text : "提交",
				iconCls : "btn-submit",
				handler : this.submit.createCallback(this)
			},{
				text : "保存",
				iconCls : "btn-save",
				handler : this.save.createCallback(this)
			}, {
				text : "重置",
				iconCls : "btn-reset",
				handler : this.reset.createCallback(this)
			}/*, {
				text : "取消",
				iconCls : "btn-cancel",
				handler : this.cancel.createCallback(this)
			}*/]
			
		});
		
		
		/**
		 * 成本要素详细信息
		 */
		this.itemFormPanel = new Ext.FormPanel({
			title:"成本要素详细信息",
			layout : "form",
			region: 'center',
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/budget/saveBudgetItem.do",
			id : "BudgetForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [ {
				name : "budgetItem.budgetItemId",
				id : "budgetItemId",
				xtype : "hidden",
				value : this.budgetItemId == null ? "" : this.budgetItemId
			}, {
				xtype : "hidden",
				name : "budgetItem.budget.budgetId",
				id : "budget.budgetId"
			}, {
				fieldLabel : "成本要素名称",
				allowBlank : false,
				name : "budgetItem.name",
				id : "name"
			}, {
				fieldLabel : "成本要素编号",
				allowBlank : false,
				name : "budgetItem.code",
				id : "code"
			}, {
				fieldLabel : "成本要素缩写",
				allowBlank : false,
				name : "budgetItem.key",
				id : "key"
			}, {
				fieldLabel : "预算金额",
				allowBlank : false,
				name : "budgetItem.value",
				id : "value"
			}, {
				fieldLabel : "控制阀值",
				allowBlank : false,
				name : "budgetItem.threshold",
				id : "threshold"
			}, {
				xtype : "hidden",
				name : "budgetItem.parent.budgetItemId",
				id : "parent.budgetItemId"
			}, {
				xtype : "hidden",
				name : "budgetItem.deleteFlag",
				id : "deleteFlag"
			} ]
		});
		
		
		
		if (this.budgetId != null && this.budgetId != "undefined") {
			this.mainFormPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath
								+ "/budget/getBudget.do?budgetId="
								+ this.budgetId,
						waitMsg : "正在载入数据...",
						success : function(c, d) {
							alert(Ext.encode(d.result.data));
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
		
		var b = [];
		if (isGranted("_BudgetItemAdd")) {
			b.push({
				text : "新建子成本要素",
				scope : this,
				iconCls : "btn-add",
				handler : function() {
					new ArchivesTypeForm().show();
				}
			});
		}
		if (isGranted("_BudgetItemEdit")) {
			b.push({
				text : "修改成本要素",
				scope : this,
				iconCls : "btn-edit",
				handler : function() {
					new ArchivesTypeForm({
						typeId : this.leftTypePanel.selectedNode.id
					}).show();
				}
			});
		}
		if (isGranted("_BudgetItemDel")) {
			b.push({
				text : "删除删除",
				scope : this,
				iconCls : "btn-delete",
				handler : function() {
					var c = this.leftTypePanel;
					var d = c.selectedNode.id;
					Ext.Ajax.request({
						url : __ctxPath + "/archive/multiDelArchivesType.do",
						params : {
							ids : d
						},
						method : "POST",
						success : function(e, f) {
							Ext.ux.Toast.msg("操作信息", "成功删除该公文分类！");
							c.root.reload();
						},
						failure : function(e, f) {
							Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
						}
					});
				}
			});
		}
		this.leftTypePanel = new xpsoft.ux.TreePanelEditor({
			region : "west",
			id : "budgetItemTree",
			title : "成本要素",
			collapsible : true,
			split : true,
			width : 200,
			url : __ctxPath + "/budget/treeBudgetItem.do",
			scope : this,
			onclick : function(e) {
				alert("获取详细信息！");
				/*var d = e.id;
				if (e.id == 0) {
					a.setTitle("所有模板");
					d = null;
				} else {
					a.setTitle("[" + e.text + "]模板列表");
				}
				a.setTypeId(e.id);
				var c = a.gridPanel.getStore();
				c.url = __ctxPath + "/budget/listBudgetItem.do";
				c.baseParams = {
					"Q_budgetItem.budgetItemId_L_EQ" : d
				};
				c.params = {
					start : 0,
					limit : 25
				};
				c.reload({
					params : {
						start : 0,
						limit : 25
					}
				});*/
			},
			contextMenuItems : b
		});
	},
	reset : function(a) {
		a.getForm().reset();
	},
	cancel : function(a) {
		a.close();
	},
	save : function(a) {
		if (a.mainFormPanel.getForm().isValid()) {
			a.mainFormPanel.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("BudgetGrid");
					if (d != null) {
						d.getStore().reload();
					}
					a.close();
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					a.close();
				}
			});
		}
	},
	submit : function(a, b) {
		
		
	}
});