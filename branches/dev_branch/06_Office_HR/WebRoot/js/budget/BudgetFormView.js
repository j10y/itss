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
		var b = new TreeSelector("budgetAlarm.belongDept.depName", a, "所属部门", "budgetAlarm.belongDept.depId",
				false);
		b.readOnly=true;
		
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
			height:200,
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
				name : "budgetAlarm.budgetId",
				mapping : "budgetId"
			}, {
				name : "budgetAlarm.belongDept.depId",
				mapping : "belongDept.depId"
			}, {
				name : "budgetAlarm.belongDept.depName",
				mapping : "belongDept.depName"
			}, {
				name : "budgetAlarm.depId",
				mapping : "belongDept.depId"
			}, {
				name : "budgetAlarm.name",
				mapping : "name"
			}, {
				name : "budgetAlarm.beginDate",
				mapping : "beginDate"
			}, {
				name : "budgetAlarm.endDate",
				mapping : "endDate"
			}, {
				name : "budgetAlarm.remark",
				mapping : "remark"
			} ]),
			items : [ {
				name : "budget.budgetId",
				id : "budgetAlarm.budgetId",
				xtype : "hidden",
				value : this.budgetId == null ? "" : this.budgetId
			}, {
				xtype : "hidden",
				name : "budget.belongDept.depId",
				id : "budgetAlarm.belongDept.depId"
			}, {
				fieldLabel : "预算名称",
				allowBlank : false,
				name : "budget.name",
				readOnly:true,
				id : "budgetAlarm.name"
			}, b 
			, {
				fieldLabel : "起始时间",
				allowBlank : false,
				name : "budget.beginDate",
				id : "budgetAlarm.beginDate",
				xtype:"datefield",
				format:"Y-m-d",
				readOnly:true,
				length:50
			},{
				fieldLabel : "结束时间",
				allowBlank : false,
				name : "budget.endDate",
				id : "budgetAlarm.endDate",
				xtype:"datefield",
				format:"Y-m-d",
				readOnly:true,
				length:50
			}, {
				xtype:"textarea",
				fieldLabel : "备注信息",
				name : "budget.remark",
				id : "budgetAlarm.remark",
				readOnly:true,
				colspan:2
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
			dataUrl: __ctxPath + "/budget/treeRealExecution.do?budgetId=" + this.budgetId,
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
	            align: 'center'
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
	            		if(v==null||v==undefined){
	            			return "";
	            		}
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