IncomeTaxView = Ext.extend(Ext.Panel, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		IncomeTaxView.superclass.constructor.call(this, {
			id : "IncomeTaxViewPanel",
			layout : "border",
			scope : this,
			items : [this.mainFormPanel,this.gridPanel],
			maximizable : true,
			title : "个税详细信息"
		});
	},
	initUIComponents : function() {		
		/**
		 * 个税主panel
		 */
		this.mainFormPanel = new Ext.FormPanel({
			id : "IncomeTaxView",
			closable : true,
			url : __ctxPath + "/incomeTax/addIncomeTax.do",
			title : "个税基本信息",
			layout : "tableform",
			layoutConfig:{
				columns:2
			},
			region:"north",
			height:200,
			autoScroll : true,
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			iconCls : "menu-incomeTax",
			reader : new Ext.data.JsonReader({
				root : "result"
			}, [ {
				name : "itId",
				mapping : "itId"
			}, {
				name : "name",
				mapping : "name"
			}, {
				name : "beginDate",
				mapping : "beginDate"
			}, {
				name : "endDate",
				mapping : "endDate"
			}, {
				name : "remark",
				mapping : "remark"
			}, {
				name : "basicAmount",
				mapping : "basicAmount"
			} ]),
			items : [
					{
						xtype : "textfield",
						fieldLabel : "名称",
						name : "incomeTax.name",
						id : "name",
						allowBlank : false
					},
					{
						fieldLabel : "个税起征点",
						name : "incomeTax.basicAmount",
						id : "basicAmount",
						allowBlank : false,
						xtype:"numberfield"
					},
					{
						xtype : "datefield",
						fieldLabel : "开始执行时间",
						name : "incomeTax.beginDate",
						id : "beginDate",
						allowBlank : false,
						format:"Y-m-d",
						length:50
					},
					{
						xtype : "datefield",
						fieldLabel : "执行结束时间",
						name : "incomeTax.endDate",
						id : "endDate",
						allowBlank : false,
						format:"Y-m-d",
						length:50
					}, {
						xtype : "textarea",
						fieldLabel : "描述信息",
						name : "incomeTax.remark",
						id : "remark",
						colspan:2
					}, 
					{
						xtype : "hidden",
						name : "incomeTax.itId",
						id : "itId"
					} ],
			buttonAlign : "right",
			buttons : [{
				text : "保存",
				iconCls : "btn-save",
				handler : function() {
					var b = Ext.getCmp("IncomeTaxView");
					if (b.getForm().isValid()) {
						b.getForm().submit(
								{
									waitMsg : "正在修改个税基本信息",
									success : function(c, e) {
										var data = Ext.decode(e.response.responseText);
										Ext.getCmp("itId").setValue(data.itId);
										Ext.ux.Toast.msg("操作信息", "个税基本信息保存成功！");
									}
								});
					}
				}
			}, {
				text : "取消",
				iconCls : "btn-cancel",
				handler : function() {
					var b = Ext.getCmp("centerTabPanel");
					b.remove("IncomeTaxViewPanel");
				}
			}]
		});
		Ext.Ajax.request({
			url : __ctxPath + "/incomeTax/checkIncomeTax.do",
			success : function(d, f) {
				var e = Ext.util.JSON.decode(d.responseText);
				if (e.success) {
					Ext.getCmp("IncomeTaxView").form.load({
						url : __ctxPath + "/incomeTax/listIncomeTax.do",
						deferredRender : true,
						layoutOnTabChange : true,
						waitMsg : "正在载入数据...",
						success : function(g, h) {
							
						},
						failure : function(g, h) {
							Ext.ux.Toast.msg("编辑", "载入失败");
						}
					});
				} else {
					Ext.ux.Toast.msg("提示", "还没填写个税基本信息");
				}
			},
			failure : function(d, e) {
			}
		});
		
		this.store = new Ext.data.JsonStore(
				{
					url : __ctxPath + "/incomeTaxItem/listIncomeTaxItem.do",
					root : "result",
					totalProperty : "totalCounts",
					remoteSort : true,
					fields : [ "itiId",
					           {name : "incomeTax", mapping : "incomeTax.name"}, 
					           "limitAmount", 
					           "lowerAmount", 
					           "taxValue"]
				});
		this.store.setDefaultSort("itiId", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		this.rowActions = new Ext.ux.grid.RowActions({
			header : "管理",
			width : 80,
			actions : [ {
				iconCls : "btn-del",
				qtip : "删除",
				style : "margin:0 3px 0 3px"
			}, {
				iconCls : "btn-edit",
				qtip : "编辑",
				style : "margin:0 3px 0 3px"
			} ]
		});
		var b = new Ext.grid.CheckboxSelectionModel();
		var a = new Ext.grid.ColumnModel({
			columns : [ b, new Ext.grid.RowNumberer(), {
				header : "itiId",
				dataIndex : "itiId",
				hidden : true
			}, {
				header : "个税主信息",
				dataIndex : "incomeTax"
			},  {
				header : "上限",
				dataIndex : "limitAmount"
			}, {
				header : "下限",
				dataIndex : "lowerAmount"
			}, {
				header : "比例",
				dataIndex : "taxValue"
			}, this.rowActions ],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : [ {
				iconCls : "btn-add",
				text : "添加",
				xtype : "button",
				handler : this.createRecord
			}, {
				iconCls : "btn-del",
				text : "删除",
				xtype : "button",
				handler : this.delRecords,
				scope : this
			} ]
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "IncomeTaxItemGrid",
			region : "center",
			stripeRows : true,
			tbar : this.topbar,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			autoHeight : true,
			cm : a,
			sm : b,
			plugins : this.rowActions,
			viewConfig : {
				forceFit : true,
				autoFill : true,
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
		this.gridPanel.addListener("rowdblclick", function(d, c, f) {
			d.getSelectionModel().each(function(e) {
				new IncomeTaxItemForm(e.data.itiId).show();
			});
		});
		this.rowActions.on("action", this.onRowAction, this);
		
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
	createRecord : function() {
		if(Ext.getCmp("itId").getValue()!=null 
				&&Ext.getCmp("itId").getValue()!=undefined
				&&Ext.getCmp("itId").getValue()!=""){			
			new IncomeTaxItemForm({itId:Ext.getCmp("itId").getValue()}).show();
		}else{
			Ext.ux.Toast.msg("提示", "还没填写个税基本信息");
		}
	},
	delByIds : function(a) {
		Ext.Msg.confirm("信息确认", "您确认要删除所选记录吗？", function(b) {
			if (b == "yes") {
				Ext.Ajax.request({
					url : __ctxPath + "/incomeTaxItem/multiDelIncomeTaxItem.do",
					params : {
						ids : a
					},
					method : "POST",
					success : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "成功删除该记录！");
						Ext.getCmp("IncomeTaxItemGrid").getStore().reload();
					},
					failure : function(c, d) {
						Ext.ux.Toast.msg("操作信息", "操作出错，请联系管理员！");
					}
				});
			}
		});
	},
	delRecords : function() {
		var c = Ext.getCmp("IncomeTaxItemGrid");
		var a = c.getSelectionModel().getSelections();
		if (a.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var d = Array();
		for ( var b = 0; b < a.length; b++) {
			d.push(a[b].data.itiId);
		}
		this.delByIds(d);
	},
	editRecord : function(a) {
		new IncomeTaxItemForm({
			itiId : a.data.itiId
		}).show();
	},
	onRowAction : function(c, a, d, e, b) {
		switch (d) {
		case "btn-del":
			this.delByIds(a.data.itiId);
			break;
		case "btn-edit":
			this.editRecord(a);
			break;
		default:
			break;
		}
	}
});