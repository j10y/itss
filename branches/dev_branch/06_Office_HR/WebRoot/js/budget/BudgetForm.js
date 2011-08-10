BudgetForm = Ext.extend(Ext.Panel, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		BudgetForm.superclass.constructor.call(this, {
			id : "BudgetFormWin",
			layout : "border",
			scope : this,
			items : [this.leftTypePanel,this.itemFormPanel, {xtype:'container', height:100, region:'north', items: this.mainFormPanel}],
			//modal : true,
			//height : 400,
			//iconCls : "menu-arch-rec-type",
			//width : 600,
			maximizable : true,
			title : "预算详细信息"/*,
			buttonAlign : "center",
			buttons : this.buttons*/
		});
	},
	initUIComponents : function() {
		var a = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var b = new TreeSelector("BudgetForm.depName", a, "所属部门", "belongDept.depId",
				false);
		
		/**
		 * 预算主panel
		 */
		this.mainFormPanel = new Ext.FormPanel({
			layout : "tableform",
			layoutConfig:{
				columns:2
			},
			height:100,
			frame:true,
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/budget/saveBudget.do",
			id : "BudgetForm1",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [ {
				name : "budget.budgetId",
				id : "budgetId1",
				xtype : "hidden",
				value : this.budgetId == null ? "" : this.budgetId
			}, {
				xtype : "hidden",
				name : "budget.belongDept.depId",
				id : "belongDept.depId1"
			}, {
				fieldLabel : "名称",
				allowBlank : false,
				name : "budget.name",
				id : "name1"
			}, b ]
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
			url : __ctxPath + "/budget/saveBudget.do",
			id : "BudgetForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [ {
				name : "budget.budgetId",
				id : "budgetId",
				xtype : "hidden",
				value : this.budgetId == null ? "" : this.budgetId
			}, {
				xtype : "hidden",
				name : "budget.belongDept.depId",
				id : "belongDept.depId"
			}, {
				fieldLabel : "名称",
				allowBlank : false,
				name : "budget.name",
				id : "name"
			}, b ]
		});
		
		
		
		if (this.budgetId != null && this.budgetId != "undefined") {
			this.formPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath
								+ "/budget/getBudget.do?budgetId="
								+ this.budgetId,
						waitMsg : "正在载入数据...",
						success : function(c, d) {
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
		if (isGranted("_ArchivesTypeAdd")) {
			b.push({
				text : "新建分类",
				scope : this,
				iconCls : "btn-add",
				handler : function() {
					new ArchivesTypeForm().show();
				}
			});
		}
		if (isGranted("_ArchivesTypeEdit")) {
			b.push({
				text : "修改分类",
				scope : this,
				iconCls : "btn-edit",
				handler : function() {
					new ArchivesTypeForm({
						typeId : this.leftTypePanel.selectedNode.id
					}).show();
				}
			});
		}
		if (isGranted("_ArchivesTypeDel")) {
			b.push({
				text : "删除分类",
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
			id : "archivesTypeTree",
			title : "成本要素",
			collapsible : true,
			split : true,
			width : 200,
			url : __ctxPath + "/archive/treeArchivesType.do",
			scope : this,
			onclick : function(e) {
				var d = e.id;
				if (e.id == 0) {
					a.setTitle("所有模板");
					d = null;
				} else {
					a.setTitle("[" + e.text + "]模板列表");
				}
				a.setTypeId(e.id);
				var c = a.gridPanel.getStore();
				c.url = __ctxPath + "/archive/listArchTemplate.do";
				c.baseParams = {
					"Q_archivesType.typeId_L_EQ" : d
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
				});
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
	save : function(a, b) {
		if (a.getForm().isValid()) {
			a.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("BudgetGrid");
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