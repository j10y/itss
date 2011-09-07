ExportSalaryItemForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		ExportSalaryItemForm.superclass.constructor.call(this, {
			layout : "fit",
			id : "ExportSalaryItemFormWin",
			iconCls : "menu-notice",
			title : "设定详细信息",
			width : 500,
			height : 260,
			minWidth : 499,
			minHeight : 259,
			items : this.formPanel,
			maximizable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath + "/hrm/saveExSaItemExportSalary.do",
			layout : "form",
			id : "ExportSalaryItemForm",
			bodyStyle : "padding:5px;",
			frame : false,
			width : 480,
			formId : "ExportSalaryItemFormId",
			defaultType : "textfield",
			items : [ {
				name : "exportSalaryItemOrder.id",
				id : "exportSalaryItemOrder.id",
				xtype : "hidden",
				value : this.id == null ? "" : this.id
			},{
				name : "exportSalaryItemOrder.exportSalId.id",
				id : "exportSalaryItemOrder.exportSalId",
				xtype : "hidden",
				value : this.exportSaId == null ? "" : this.exportSaId
			},
			{
				name : "exportSalaryItemOrder.orderCol",
				id : "exportSalaryItemOrder.orderCol",
				xtype : "hidden"
			},
			{
				name : "exportSalaryItemOrder.isDefaut",
				id : "exportSalaryItemOrder.isDefaut",
				xtype : "hidden"
			},{
				fieldLabel : "导出字段名称",
				width:300,
				name : "exportSalaryItemOrder.exportName",
				id : "exportSalaryItemOrder.exportName"
			},{
				name : "exportSalaryItemOrder.fromTable",
				id : "exportSalaryItemOrder.fromTable",
				xtype : "hidden"
			},{
				fieldLabel : "字段来源表",
				width:300,
				name : "exportSalaryItemOrder.fromTableName",
				id : "exportSalaryItemOrder.fromTableName",
				xtype : "combo",
				triggerAction : "all",
				store : [["1","薪标项"],["2","奖惩类型表"]]
			},{
				name : "exportSalaryItemOrder.fromTableType",
				id : "exportSalaryItemOrder.fromTableType",
				xtype : "hidden"
			},{
				fieldLabel : "字段类型",
				width:300,
				name : "exportSalaryItemOrder.fromTableTypeName",
				id : "exportSalaryItemOrder.fromTableTypeName",
				xtype : "combo",
				mode : "local",
				allowBlank : false,
				editable : false,
				valueField : "fromTableTypeId",
				displayField : "fromTableTypeName",
				triggerAction : "all",
				store : new Ext.data.SimpleStore(
						{
							url : __ctxPath
									+ "/hrm/comboExportSalary.do",
							fields : [
									"fromTableTypeId",
									"fromTableTypeName"]
						}),
				listeners : {
					focus : function(
							d) {
						var c = Ext
								.getCmp(
										"exportSalaryItemOrder.fromTableName")
								.getValue();
						if (c != null
								&& c != ""
								&& c != "undefined") {
							Ext.getCmp("exportSalaryItemOrder.fromTable").setValue(c);	
							Ext.getCmp("exportSalaryItemOrder.fromTableTypeName").getStore().reload(
											{
												params : {
													fromTable : c
												}
											});
						} else {
							Ext.ux.Toast
									.msg(
											"操作信息",
											"请先选择字段来源表！");
						}
					},
					select:function(e,c,d){
						var typename=Ext.getCmp("exportSalaryItemOrder.fromTableTypeName").getValue();
						Ext.getCmp("exportSalaryItemOrder.fromTableType").setValue(typename);
					}
			}		
			}]
		});
		if (this.id != null && this.id != "undefined") {
			this.formPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath + "/hrm/getExSaItemExportSalary.do?id="
								+ this.id,
						waitMsg : "正在载入数据...",
						success : function(b, c) {
							var f = Ext.util.JSON.decode(c.response.responseText).data[0];
							Ext.getCmp("exportSalaryItemOrder.id").setValue(f.id);
							Ext.getCmp("exportSalaryItemOrder.exportSalId").setValue(f.exportSalId.id);
							Ext.getCmp("exportSalaryItemOrder.orderCol").setValue(f.orderCol);
							Ext.getCmp("exportSalaryItemOrder.isDefaut").setValue(f.isDefaut);
							Ext.getCmp("exportSalaryItemOrder.exportName").setValue(f.exportName);
							Ext.getCmp("exportSalaryItemOrder.fromTable").setValue(f.fromTable);
							Ext.getCmp("exportSalaryItemOrder.fromTableName").setValue(f.fromTableName);
							Ext.getCmp("exportSalaryItemOrder.fromTableType").setValue(f.fromTableType);
							Ext.getCmp("exportSalaryItemOrder.fromTableTypeName").setValue(f.fromTableTypeName);
						},
						failure : function(a, b) {
							Ext.ux.Toast.msg("编辑", "载入失败");
						}
					});
		}
		this.buttons = [ {
			text : "保存",
			iconCls : "btn-save",
			handler : function() {
				var a = Ext.getCmp("ExportSalaryItemForm");
				if (a.getForm().isValid()) {
					a.getForm().submit({
						method : "post",
						waitMsg : "正在提交数据...",
						success : function(b, c) {
							Ext.ux.Toast.msg("操作信息", "成功保存信息！");
							Ext.getCmp("ExportSalaryItemGrid").getStore().reload();
							Ext.getCmp("ExportSalaryItemFormWin").close();
						},
						failure : function(b, c) {
							Ext.MessageBox.show({
								title : "操作信息",
								msg : "信息保存出错，请联系管理员！",
								buttons : Ext.MessageBox.OK,
								icon : "ext-mb-error"
							});
							Ext.getCmp("ExportSalaryItemFormWin").close();
						}
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : function() {
				Ext.getCmp("ExportSalaryItemFormWin").close();
			}
		} ];
	}
});