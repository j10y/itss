BudgetFormView = Ext.extend(Ext.Panel, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		BudgetFormView.superclass.constructor.call(this, {
			id : "BudgetFormViewWin",
			layout : "border",
			scope : this,
//			items : [this.leftTypePanel,this.itemFormPanel, {xtype:'container', height:150, region:'north', items: this.mainFormPanel}],
			items : [this.mainFormPanel, this.treeGrid],
			maximizable : true,
			title : "预算预警详细信息"
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
			}]
			
		});
		
		if (this.budgetId != null && this.budgetId != "undefined") {
			this.mainFormPanel.getForm().load({
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
		
		
//		this.store = new Ext.data.JsonStore({
//			url : __ctxPath + "/budget/listBudgetItem.do",
//			root : "result",
//			totalProperty : "totalCounts",
//			remoteSort : true,
//			fields : [ {
//				name : "budgetId",
//				type : "int"
//			}, "name", {
//				name : "depName",
//				mapping : "belongDept.depName"
//			}, "beginDate" 
//			, "endDate" 
//			, "publishStatus"
//			, "createDate"
//			, "createPerson", {
//				name : "createPerson",
//				mapping : "createPerson.fullname"
//			}]
//		});
//		this.store.setDefaultSort("budgetId", "desc");
//		this.store.load({
//			params : {
//				start : 0,
//				limit : 25
//			}
//		});
		var c = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [ c, new Ext.grid.RowNumberer(), {
				header : "budgetId",
				dataIndex : "budgetId",
				hidden : true
			}, {
				header : "名称",
				dataIndex : "name"
			}, {
				header : "所属部门",
				dataIndex : "depName"
			}, {
				header : "开始时间",
				dataIndex : "beginDate"
			}, {
				header : "结束时间",
				dataIndex : "endDate"
			}, {
				header : "状态",
				dataIndex : "publishStatus",
				renderer : function(v){
					// 0：草稿  1：审核中 2：退回 3：审核完毕，发布 4：删除标记'
					if(v==0){
						return "<font color='red'>草稿</font>"
					}else{
						if(v==1){
							return "<font color='red'>审核中</font>"
						}else{
							if(v==2){
								return "<font color='red'>退回</font>"
							}else{
								if(v==3){
									return "<font color='green'>已发布</font>"
								}else{
									return "<font color='red'>已删除</font>"
								}
							}
						}
					}
				}
			}, {
				header : "创建人",
				dataIndex : "createPerson"
			}, {
				header : "创建时间",
				dataIndex : "createDate"
			}], 
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});

		this.treeGrid = new Ext.ux.tree.TreeGrid({
			id : "BudgetGrid",
			region : "center",
			stripeRows : true,
			tbar : this.topbar,
			//store : this.store,
			dataUrl: __ctxPath + "/budget/treeRealExecution.do",
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			autoHeight : true,
			columns:[{
	            header: '告警',
	            dataIndex: 'alarm',
	            width: 230,
	            tpl: new Ext.XTemplate('{alarm:this.alarm}', {
	            	alarm: function(v) {
	            		if(v==""){
	            			return "---";
	            		}else{
	            			if(v=="0"){
	            				return "<img src='" + __ctxPath + "/images/budget/alarm_green.png'></img>绿色安全";
	            			}else{
	            				if(v=="1"){
	            					return "<img src='" + __ctxPath + "/images/budget/alarm_yellow.png'></img>黄色警报";
	            				}else{
	            					return "<img src='" + __ctxPath + "/images/budget/alarm_red.png'></img>红色危险";
	            				}
	            			}
	            		}
	                }
	            })
	        }, {
	            header: '成本要素名称',
	            dataIndex: 'name',
	            width: 230
	        }, {
	            header: '成本要素编号',
	            width: 100,
	            dataIndex: 'code',
	            align: 'center',
	            sortType: 'asFloat'/*,
	            tpl: new Ext.XTemplate('{duration:this.formatHours}', {
	                formatHours: function(v) {
	                    if(v < 1) {
	                        return Math.round(v * 60) + ' mins';
	                    } else if (Math.floor(v) !== v) {
	                        var min = v - Math.floor(v);
	                        return Math.floor(v) + 'h ' + Math.round(min * 60) + 'm';
	                    } else {
	                        return v + ' hour' + (v === 1 ? '' : 's');
	                    }
	                }
	            })*/
	        }, {
	            header: '缩写',
	            width: 150,
	            dataIndex: 'key'
	        }, {
	            header: '预算金额',
	            width: 150,
	            dataIndex: 'value'
	        }, {
	            header: '控制阀值',
	            width: 150,
	            dataIndex: 'threshold',
	            tpl: new Ext.XTemplate('{threshold:this.formatDouble}', {
	            	formatDouble: function(v) {
	                    return v*100 + "%";
	                }
	            })
	        }, {
	            header: '已执行值',
	            width: 150,
	            dataIndex: 'realValue'
	        }],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			},
			sm : c,
			viewConfig : {
				forceFit : true,
				autoFill : true,
				forceFit : true
			}
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