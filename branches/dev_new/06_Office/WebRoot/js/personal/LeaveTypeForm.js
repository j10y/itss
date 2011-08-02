LeaveTypeForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		LeaveTypeForm.superclass.constructor.call(this, {
			id : "LeaveTypeFormWin",
			layout : "fit",
			items : this.formPanel,
			modal : true,
			height : 150,
			iconCls : "menu-arch-rec-type",
			width : 400,
			maximizable : true,
			title : "请假分类详细信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initUIComponents : function() {
		
		this.formPanel = new Ext.FormPanel({
			layout : "form",
			bodyStyle : "padding:10px 10px 10px 10px",
			border : false,
			url : __ctxPath + "/personal/saveLeaveType.do",
			id : "LeaveTypeForm",
			defaults : {
				anchor : "98%,98%"
			},
			defaultType : "textfield",
			items : [ {
				name : "leaveType.typeId",
				id : "typeId",
				xtype : "hidden",
				value : this.typeId == null ? "" : this.typeId
			},{
				fieldLabel:'分类名称',
				name : "leaveType.typeName",
				id : "typeName",
				xtype : "textfield",
				allowBlank : false
			}, {
				fieldLabel:'流程Id',
				name : "leaveType.processDefId",
				id : "processDefId",
				xtype : "hidden"
			}, {
				fieldLabel:'流程名称',
				xtype : "combo",
				//mode : "local",
				anchor : "74%",
				allowBlank : false,
				editable : false,
				id : "leaveType.processDefName.combo",
				hiddenName : "leaveType.processDefName",
				valueField : "processDefName",
				displayField : "processDefName",
				triggerAction : "all",
				store : new Ext.data.SimpleStore({
					url : __ctxPath 
							+ "/flow/selectProDefinition.do?typeId=3",
					params : {
						typeId : 3
					},
					fields : [ "defId", "processDefName"]
				}),
				listeners : {
					select : function(l, h, k) {
						Ext.getCmp("processDefId").setValue(h.data.defId);
					}
				}
			
			} ]
		});
		if (this.typeId != null && this.typeId != "undefined") {
			this.formPanel.getForm().load(
					{
						deferredRender : false,
						url : __ctxPath + "/personal/getLeaveType.do?typeId="
								+ this.typeId,
						waitMsg : "正在载入数据...",
						success : function(c, d) {
							Ext.getCmp("leaveType.processDefName.combo").setValue(Ext.decode(d.response.responseText).data.processDefName);
						},
						failure : function(c, d) {
						}
					});
		}
		this.buttons = [ {
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
					var d = Ext.getCmp("LeaveTypeGrid");
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