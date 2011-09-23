BudgetQuarterForm = Ext.extend(Ext.Panel, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		BudgetQuarterForm.superclass.constructor.call(this, {
			id : "BudgetQuarterFormWin",
			layout : "border",
			scope : this,
//			items : [this.leftTypePanel,this.itemFormPanel, {xtype:'container', height:150, region:'north', items: this.mainFormPanel}],
			items : [this.leftTypePanel,this.itemFormPanel, this.mainFormPanel],
			maximizable : true,
			title : "季度预算详细信息"
		});
	},
	initUIComponents : function() {
		var a = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var b = new TreeSelector("budgetQuarter.belongDept.depName", a, "所属部门", "budgetQuarter.belongDept.depId",
				false);
		b.readOnly = this.isEdit==true ? false:true;
		var buttons = [];
		
		
		if(this.isEdit!=null && this.isEdit!="undefined" && this.isEdit==true){
			buttons = [{
				text : "发布",
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
				text : "关闭",
				iconCls : "btn-close",
				handler : this.cancel.createCallback(this)
			}];
		}
		
		/**
		 * 预算主panel
		 */
		this.mainFormPanel = new Ext.FormPanel({
			id:"mainQuarterFormPanel",
			region:"north",
			title:"预算基本信息",
			layout : "tableform",
			layoutConfig:{
				columns:2
			},
			height:230,
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
				name : "budgetQuarter.budgetId",
				mapping : "budgetId"
			}, {
				name : "budgetQuarter.belongDept.depId",
				mapping : "belongDept.depId"
			}, {
				name : "budgetQuarter.belongDept.depName",
				mapping : "belongDept.depName"
			}, {
				name : "budgetQuarter.depId",
				mapping : "belongDept.depId"
			}, {
				name : "budgetQuarter.name",
				mapping : "name"
			}, {
				name : "budgetQuarter.beginDate",
				mapping : "beginDate"
			}, {
				name : "budgetQuarter.endDate",
				mapping : "endDate"
			}, {
				name : "budgetQuarter.remark",
				mapping : "remark"
			}, {
				name : "budgetQuarter.publishStatus",
				mapping : "publishStatus"
			}, {
				name : "budgetQuarter.budgetType",
				mapping : "budgetType"
			}, {
				name : "budgetQuarter.belongBudget.budgetId",
				mapping : "belongBudget",
				convert:function(v,record){
					if(v!=null){
						return v.budgetId;
					}
				}


			}, {
				name : "budgetQuarter.belongBudget.name",
				mapping : "belongBudget",
				convert:function(v,record){
					if(v!=null){
						return v.name;
					}
				}
			}]),
			items : [ {
				name : "budget.budgetId",
				id : "budgetQuarter.budgetId",
				xtype : "hidden",
				value : this.budgetId == null ? "" : this.budgetId
			}, {
				xtype : "hidden",
				name : "budget.belongDept.depId",
				id : "budgetQuarter.belongDept.depId"
			}, {
				fieldLabel : "预算名称",
				allowBlank : false,
				name : "budget.name",
				id : "budgetQuarter.name",
				readOnly:(this.isEdit==true ? false:true)
			}, b 
			, {
				fieldLabel : "预算类型",
				hiddenName : "budget.budgetType",
				id : "budgetQuarter.budgetType",
				xtype : "combo",
				anchor : '98%',
				allowBlank : false,
				emptyText : "请选择类型",
				mode : "local",
				editable : false,
				allowBlank : false,
				triggerAction : "all",
				readOnly:true,
				//readOnly:(this.isEdit==true ? false:true),
				store : [ [ "1", "年度" ], [ "2", "季度" ] 
					//, [ "3", "阅读" ]
				],
				value : 2,
				listeners : {
					select : function(l, h, k) {
						var type = l.getValue();
						if(type=="2"){
							Ext.getCmp("budgetQuarter.belongBudget.name").allowBlank = false;
						}else{
							Ext.getCmp("budgetQuarter.belongBudget.name").clearInvalid();
							Ext.getCmp("budgetQuarter.belongBudget.name").allowBlank = true;
						}
					}
				}
			},{
				xtype : 'combo',
				anchor : '98%',
				fieldLabel : "所属年度预算",
				name : "budget.belongBudget.name",
				id : "budgetQuarter.belongBudget.name",
				mode : "remote",
				valueField : "name",
				allowBlank : false,
				displayField : "name",
				triggerAction : "all",
				readOnly:(this.isEdit==true ? false:true),
				store : new Ext.data.JsonStore({
					url : __ctxPath
							+ "/budget/listBudget.do",
					fields : [ "budgetId", "name" ],
					root : "result",
					totalProperty : "totalCounts",
					remoteSort : true
					
				}),
				listeners : {
					select : function(l, h, k) {
						Ext.getCmp("budgetQuarter.belongBudget.budgetId").setValue(h.data.budgetId);
//						var j = Ext.getCmp("ArchFlowConfGrid")
//								.getStore();
//						var m = j.getAt(f);
//						m.set("deptUserId", h.data.userId);
//						m.set("deptFullname", h.data.fullname);
					},
					beforequery : function(queryEvent) {
						if(Ext.getCmp("budgetQuarter.belongDept.depId").getValue()=="" || Ext.getCmp("budgetQuarter.belongDept.depId").getValue()=="0"){
							Ext.ux.Toast.msg("操作信息", "请首先选择所属部门！");
							queryEvent.cancel=true;
							return;
						}
						var store=queryEvent.combo.store;
						store.baseParams={
							"Q_name_S_LK":queryEvent.query,
							"Q_budgetType_N_EQ":1,
							"Q_belongDept.depId_L_EQ":Ext.getCmp("budgetQuarter.belongDept.depId").getValue()
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
			},{
				fieldLabel : "起始时间",
				allowBlank : false,
				name : "budget.beginDate",
				id : "budgetQuarter.beginDate",
				xtype:"datefield",
				format:"Y-m-d",
				length:50,
				readOnly:(this.isEdit==true ? false:true)
			},{
				fieldLabel : "结束时间",
				allowBlank : false,
				name : "budget.endDate",
				id : "budgetQuarter.endDate",
				xtype:"datefield",
				format:"Y-m-d",
				length:50,
				readOnly:(this.isEdit==true ? false:true)
			}, {
				xtype : "textarea",
				fieldLabel : "备注信息",
				name : "budget.remark",
				id : "budgetQuarter.remark",
				colspan:2,
				readOnly:(this.isEdit==true ? false:true)
			}, {
				xtype : "hidden",
				name : "budget.belongBudget.budgetId",
				id : "budgetQuarter.belongBudget.budgetId"
			}, {
				xtype : "hidden",
				//fieldLabel : "预算状态",
				allowBlank : false,
				name : "budget.publishStatus",
				id : "budgetQuarter.publishStatus",
				hidden : true,
				value : 0
			}],
			buttonAlign : "right",
			/*buttons : [{
				text : "提交",
				iconCls : "btn-submit",
				handler : this.submit.createCallback(this)
			},{
				text : "保存",
				iconCls : "btn-save",
				handler : this.save.createCallback(this)
			}, {
				text : "取消",
				iconCls : "btn-cancel",
				handler : this.cancel.createCallback(this)
			}]*/
			buttons: buttons
			
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
			id : "itemQuarterFormPanel",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			reader : new Ext.data.JsonReader({
				root : "data"
			}, [ {
				name : "budgetQuarter.item.budgetItemId",
				mapping : "budgetItemId"
			}, {
				name : "budgetQuarter.item.budgetItem.budgetId",
				mapping : "budget.budgetId"
			}, {
				name : "budgetQuarter.item.name",
				mapping : "name"
			}, {
				name : "budgetQuarter.item.code",
				mapping : "code"
			}, {
				name : "budgetQuarter.item.key",
				mapping : "key"
			}, {
				name : "budgetQuarter.item.value",
				mapping : "value"
			}, {
				name : "budgetQuarter.item.threshold",
				mapping : "threshold"
			}, {
				name : "budgetQuarter.item.thresholdTmp",
				mapping : "threshold",
				convert : function(v, rec){
					if(v!=null){
						return v*100;
					}else{
						return 0;
					}
				}
			}, {
				name : "budgetQuarter.item.parent.budgetItemId",
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
				name : "budgetQuarter.item.deleteFlag",
				mapping : "deleteFlag"
			} ]),
			items : [ {
				name : "budgetItem.budgetItemId",
				id : "budgetQuarter.item.budgetItemId",
				xtype : "hidden",
				value : this.budgetItemId == null ? "" : this.budgetItemId
			}, {
				xtype : "hidden",
				name : "budgetItem.budget.budgetId",
				id : "budgetQuarter.item.budgetItem.budgetId"
			}, {
				fieldLabel : "成本要素名称",
				name : "budgetItem.name",
				readOnly : true,
				id : "budgetQuarter.item.name"
			}, {
				fieldLabel : "成本要素编号",
				name : "budgetItem.code",
				readOnly : true,
				id : "budgetQuarter.item.code"
			}, {
				fieldLabel : "成本要素缩写",
				name : "budgetItem.key",
				readOnly : true,
				id : "budgetQuarter.item.key"
			}, {
				fieldLabel : "预算金额",
				readOnly : true,
				name : "budgetItem.value",
				id : "budgetQuarter.item.value"
			}, {
				xtype : "hidden",
				name : "budgetItem.threshold",
				id : "budgetQuarter.item.threshold"
			}, {
				fieldLabel : "控制阀值(%)",
				readOnly : true,
				id : "budgetQuarter.item.thresholdTmp",
				listeners : {
					change : function(field, newValue, oldValue){
						Ext.getCmp("budgetQuarter.item.threshold").setValue(newValue/100);
					}
				}
			}, {
				xtype : "hidden",
				name : "budgetItem.parent.budgetItemId",
				id : "budgetQuarter.item.parent.budgetItemId"
			}, {
				xtype : "hidden",
				name : "budgetItem.deleteFlag",
				id : "budgetQuarter.item.deleteFlag"
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
							Ext.getCmp("BudgetQuarterForm.depName").setValue(
									e.depName);
							Ext.getCmp("depId").setValue(e.depId);
						},
						failure : function(c, d) {
							
						}
					});
		}
		
		var b = [];
		if (isGranted("_BudgetItemAdd")) {
			b.push({
				text : "新建子成本要素",
				scope : this,
				iconCls : "btn-add",
				handler : function() {
					//alert(Ext.getCmp("budgetItemQuarterTree").selectedNode.id);
					//首先校验是否已经保存预算主数据
					var budgetId = Ext.getCmp("budgetQuarter.budgetId").getValue();
					if(budgetId==null || budgetId=="" || budgetId==undefined|| budgetId==0){
						Ext.ux.Toast.msg("操作信息", "请首先保存预算基本信息！");
					}else{
						new BudgetQuarterItemWin({
							belongBudget :Ext.getCmp("budgetQuarter.belongBudget.budgetId").getValue(),
							budgetId : budgetId,
							parentBudgetItemId : Ext.getCmp("budgetItemQuarterTree").selectedNode.id//顶层为0,
						}).show();
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
					var selectedNode = Ext.getCmp("budgetItemQuarterTree").selectedNode;		
					if(selectedNode.id==0){
						Ext.ux.Toast.msg("操作信息", "根节点【" + Ext.getCmp("budgetItemQuarterTree").selectedNode.text + "】不可以修改！");
					}else{
						//load
						var budgetId = Ext.getCmp("budgetQuarter.budgetId").getValue();
						if(budgetId==null || budgetId=="" || budgetId==undefined|| budgetId==0){
							Ext.ux.Toast.msg("操作信息", "请首先保存预算基本信息！");
						}else{
							
							new BudgetQuarterItemWin({
								belongBudget :Ext.getCmp("budgetQuarter.belongBudget.budgetId").getValue(),
								budgetItemId : selectedNode.id
							}).show();
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
					var selectedNode = Ext.getCmp("budgetItemQuarterTree").selectedNode;
					if(selectedNode.attributes.attributes.isDefault==1){
						Ext.ux.Toast.msg("操作信息", "默认成本要素不能删除！");
						return;
					}
					Ext.Ajax.request({
						url : __ctxPath + "/budget/multiDelBudgetItem.do",
						params : {
							ids : selectedNode.id
						},
						method : "POST",
						success : function(e, f) {
							Ext.ux.Toast.msg("操作信息", "成功删除该成本要素！");
							Ext.getCmp("budgetItemQuarterTree").root.reload();
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
			id : "budgetItemQuarterTree",
			title : "成本要素",
			collapsible : true,
			split : true,
			width : 200,
			url : __ctxPath + "/budget/treeBudgetItem.do?budgetId=" + (this.budgetId == null ? "0" : this.budgetId),
			scope : this,
			showContextMenu : (this.isEdit==true ? true:false),
			contextHandler : function contextmenu(a, b) {
//				if(a.attributes.isDefault=="1"&&a.id!="0"){
//					return;
//				}
				this.selectedNode = new Ext.tree.TreeNode({
					id : a.id,
					text : a.text,
					attributes : a.attributes
				});
				this.contextMenu.showAt(b.getXY());
			},
			onclick : function(e) {
				
				var budgetItemId = e.id;
				if(budgetItemId!="0"){
//					if(e.attributes.isDefault!=1 && (e.leaf==undefined || e.leaf==null || e.leaf==false)){
//
//						//初始化
//						
//						Ext.getCmp("itemQuarterFormPanel").getForm().setValues({
//							"budgetItem.name" : e.attributes.data.name,
//							"budgetItem.code" : e.attributes.data.code,
//							"budgetItem.key" : e.attributes.data.key,
//							"budgetItem.value" : e.attributes.data.value,
//							"budgetItem.threshold" : e.attributes.data.threshold,
//							"thresholdTmp" : ""
//						});
//						return ;
//					}
					Ext.getCmp("itemQuarterFormPanel").getForm().load({
						deferredRender : false,
						url : __ctxPath
								+ "/budget/getBudgetItem.do?budgetItemId="
								+ budgetItemId,
						waitMsg : "正在载入数据...",
						success : function(c, d) {
							//alert(d.response.responseText);
							return;
							var e = d.result.data.department;
							Ext.getCmp("BudgetQuarterForm.depName").setValue(
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
	save : function(a, b) {
		if (a.mainFormPanel.getForm().isValid()) {
			a.mainFormPanel.getForm().submit({
				method : "POST",
				waitMsg : "正在提交数据...",
				success : function(c, e) {
					var data = Ext.decode(e.response.responseText);
					Ext.getCmp("budgetQuarter.budgetId").setValue(data.budgetId);
					this.budgetId = data.budgetId;
					Ext.ux.Toast.msg("操作信息", "成功保存信息！");
					var d = Ext.getCmp("BudgetGrid");
					if (d != null) {
						d.getStore().reload();
					}
					//a.close();
					//增加其他左侧成本要素树加载的逻辑
					Ext.getCmp("budgetItemQuarterTree").getLoader().url = __ctxPath + "/budget/treeBudgetItem.do?budgetId=" + data.budgetId;
					Ext.getCmp("budgetItemQuarterTree").root.reload();
					if(b){
						var tabs = Ext.getCmp("centerTabPanel");
						tabs.remove(a);
					}
				},
				failure : function(c, d) {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "信息保存出错，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
					var tabs = Ext.getCmp("centerTabPanel");
					tabs.remove(a);
				}
			});
		}
	},
	submit : function(a, b) {
		Ext.getCmp("budgetQuarter.publishStatus").setValue(3);
		a.save(a,true);
		//Ext.Msg.alert("提示信息", "流程信息待实现！");		
	}
});