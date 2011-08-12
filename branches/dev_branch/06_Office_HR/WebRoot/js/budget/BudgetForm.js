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
		var b = new TreeSelector("budget.belongDept.depName", a, "所属部门", "budget.belongDept.depId",
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
				name : "budget.belongDept.depName",
				mapping : "belongDept.depName"
			}, {
				name : "budget.depId",
				mapping : "belongDept.depId"
			}, {
				name : "budget.name",
				mapping : "name"
			}, {
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
				format:"Y-m-d",
				length:50
			},{
				fieldLabel : "结束时间",
				allowBlank : false,
				name : "budget.endDate",
				id : "budget.endDate",
				xtype:"datefield",
				format:"Y-m-d",
				length:50
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
			}, /*{
				text : "重置",
				iconCls : "btn-reset",
				handler : this.reset.createCallback(this)
			},*/ {
				text : "取消",
				iconCls : "btn-cancel",
				handler : this.cancel.createCallback(this)
			}]
			
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
			id : "itemFormPanel",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			reader : new Ext.data.JsonReader({
				root : "data"
			}, [ {
				name : "budgetItemId",
				mapping : "budgetItemId"
			}, {
				name : "budgetItem.budgetId",
				mapping : "budget.budgetId"
			}, {
				name : "name",
				mapping : "name"
			}, {
				name : "code",
				mapping : "code"
			}, {
				name : "key",
				mapping : "key"
			}, {
				name : "value",
				mapping : "value"
			}, {
				name : "threshold",
				mapping : "threshold"
			}, {
				name : "parent.budgetItemId",
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
				name : "deleteFlag",
				mapping : "deleteFlag"
			} ]),
			items : [ {
				name : "budgetItem.budgetItemId",
				id : "budgetItemId",
				xtype : "hidden",
				value : this.budgetItemId == null ? "" : this.budgetItemId
			}, {
				xtype : "hidden",
				name : "budgetItem.budget.budgetId",
				id : "budgetItem.budgetId"
			}, {
				fieldLabel : "成本要素名称",
				name : "budgetItem.name",
				readOnly : true,
				id : "name"
			}, {
				fieldLabel : "成本要素编号",
				name : "budgetItem.code",
				readOnly : true,
				id : "code"
			}, {
				fieldLabel : "成本要素缩写",
				name : "budgetItem.key",
				readOnly : true,
				id : "key"
			}, {
				fieldLabel : "预算金额",
				readOnly : true,
				name : "budgetItem.value",
				id : "value"
			}, {
				fieldLabel : "控制阀值",
				readOnly : true,
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
		}/*, {
			text : "重置",
			iconCls : "btn-reset",
			handler : this.reset.createCallback(this.formPanel)
		}*/, {
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
					//首先校验是否已经保存预算主数据
					var budgetId = Ext.getCmp("budget.budgetId").getValue();
					if(budgetId==null || budgetId=="" || budgetId==undefined|| budgetId==0){
						Ext.ux.Toast.msg("操作信息", "请首先保存预算基本信息！");
					}else{
						new BudgetItemWin({budgetId : budgetId,parentBudgetItemId : Ext.getCmp("budgetItemTree").selectedNode.id}).show();
					}
					
				}
			});
		}
		if (isGranted("_BudgetItemEdit")) {
			b.push({
				text : "修改成本要素",
				scope : this,
				iconCls : "btn-edit",
				handler : function() {
					//首先校验是否已经保存预算主数据
					var selectedNode = Ext.getCmp("budgetItemTree").selectedNode.id;		
					if(selectedNode==0){
						Ext.ux.Toast.msg("操作信息", "根节点【" + Ext.getCmp("budgetItemTree").selectedNode.text + "】不可以修改！");
					}else{
						//load
						var budgetId = Ext.getCmp("budget.budgetId").getValue();
						if(budgetId==null || budgetId=="" || budgetId==undefined|| budgetId==0){
							Ext.ux.Toast.msg("操作信息", "请首先保存预算基本信息！");
						}else{
							
							new BudgetItemWin({budgetItemId : selectedNode}).show();
						}
					}
				}
			});
		}
		if (isGranted("_BudgetItemDel")) {
			b.push({
				text : "删除",
				scope : this,
				iconCls : "btn-delete",
				handler : function() {
					var selectedNode = Ext.getCmp("budgetItemTree").selectedNode.id;
					Ext.Ajax.request({
						url : __ctxPath + "/budget/multiDelBudgetItem.do",
						params : {
							ids : selectedNode
						},
						method : "POST",
						success : function(e, f) {
							Ext.ux.Toast.msg("操作信息", "成功删除该成本 要素！");
							Ext.getCmp("budgetItemTree").root.reload();
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
			url : __ctxPath + "/budget/treeBudgetItem.do?budgetId=" + (this.budgetId == null ? "0" : this.budgetId),
			scope : this,
			onclick : function(e) {

				var budgetItemId = e.id;
				if(budgetItemId!="0"){
					Ext.getCmp("itemFormPanel").getForm().load({
						deferredRender : false,
						url : __ctxPath
								+ "/budget/getBudgetItem.do?budgetItemId="
								+ budgetItemId,
						waitMsg : "正在载入数据...",
						success : function(c, d) {
							//alert(d.response.responseText);
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
			contextMenuItems : b
		});
	},
	reset : function(a) {
		a.mainFormPanel.getForm().reset();
	},
	cancel : function(a) {
		//close
		var tabs = Ext.getCmp("centerTabPanel");
		tabs.remove(a);
	},
	save : function(a) {
		if (a.mainFormPanel.getForm().isValid()) {
			a.mainFormPanel.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					var data = Ext.decode(e.response.responseText);
					Ext.getCmp("budget.budgetId").setValue(data.budgetId);
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
		Ext.Msg.alert("提示信息", "流程信息待实现！");		
	}
});