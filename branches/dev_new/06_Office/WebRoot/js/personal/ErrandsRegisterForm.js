var ErrandsRegisterForm = function(c) {
	this.dateId = c;
	var a = this.setup();
	var b = new Ext.Window(
			{
				id : "ErrandsRegisterFormWin",
				title : "请假单详细信息",
				width : 400,
				height : 250,
				modal : true,
				layout : "fit",
				buttonAlign : "center",
				items : [ this.setup() ],
				buttons : [
						{
							text : "保存",
							iconCls : "btn-save",
							handler : function() {
								var d = Ext.getCmp("ErrandsRegisterForm");
								if (d.getForm().isValid()) {
									d.getForm().submit({
														method : "post",
														waitMsg : "正在提交数据...",
														success : function(e, f) {
															Ext.ux.Toast.msg(
																	"操作信息",
																	"成功保存信息！");
															Ext
																	.getCmp(
																			"ErrandsRegisterGrid")
																	.getStore()
																	.reload();
															b.close();
														},
														failure : function(e, f) {
															Ext.MessageBox
																	.show({
																		title : "操作信息",
																		msg : "信息保存出错，请联系管理员！",
																		buttons : Ext.MessageBox.OK,
																		icon : "ext-mb-error"
																	});
															b.close();
														}
													});
								}
							}
						}, {
							text : "取消",
							iconCls : "btn-cancel",
							handler : function() {
								b.close();
							}
						} ]
			});
	b.show();
};
ErrandsRegisterForm.prototype.setup = function() {
	var a = new Ext.FormPanel({
		url : __ctxPath + "/personal/saveErrandsRegister.do",
		layout : "form",
		id : "ErrandsRegisterForm",
		border : false,
		bodyStyle : "padding:5px;",
		defaults : {
			width : 400,
			anchor : "98%,98%"
		},
		formId : "ErrandsRegisterFormId",
		defaultType : "textfield",
		items : [ {
			name : "errandsRegister.dateId",
			id : "dateId",
			xtype : "hidden",
			value : this.dateId == null ? "" : this.dateId
		}, {
			name : "errandsRegister.flag",
			id : "flag",
			xtype : "hidden",
			value : 1
		}, {
			xtype : "hidden",
			name : "errandsRegister.userId",
			id : "userId"
		}, {
			xtype : "hidden",
			name : "errandsRegister.status",
			id : "status"
		}, {
			xtype : "hidden",
			name : "errandsRegister.approvalOption",
			id : "approvalOption"
		}, {
			xtype : "hidden",
			name : "errandsRegister.approvalName",
			id : "approvalName"
		}, {
			xtype : "hidden",
			name : "errandsRegister.approvalId",
			id : "approvalId"
		}, {
			fieldLabel : "描述",
			xtype : "textarea",
			name : "errandsRegister.descp",
			allowBlank : false,
			id : "descp"
		}, {
			fieldLabel : "开始时间",
			name : "errandsRegister.startTime",
			xtype : "datetimefield",
			format : "Y-m-d H:i:s",
			allowBlank : false,
			id : "startTime"
		}, {
			fieldLabel : "结束时间",
			name : "errandsRegister.endTime",
			xtype : "datetimefield",
			format : "Y-m-d H:i:s",
			allowBlank : false,
			id : "endTime"
		}, {

			xtype : "hidden",
			name : "errandsRegister.leaveTypeId",
			id : "leaveTypeId"
		}, {

			xtype : "hidden",
			name : "processDefId",
			id : "processDefId"
		}, {

			fieldLabel : "请假类型",
			hiddenName : "errandsRegister.leaveTypeName",
			id : "leaveTypeName",
			emptyText : "请选择请假类型",
			xtype : "combo",
			mode : "local",
			anchor : "98%",
			allowBlank : false,
			editable : false,
			valueField : "typeName",
			displayField : "typeName",
			triggerAction : "all",
			store : new Ext.data.SimpleStore({
				autoLoad : true,
				url : __ctxPath + "/personal/comboLeaveType.do",
				fields : [ "typeId", "typeName", "processDefId"]
			}),
			listeners : {
				select : function(d, b, c) {
					Ext.getCmp("leaveTypeId").setValue(b.data.typeId);
					Ext.getCmp("processDefId").setValue(b.data.processDefId);
				}
			}
		
		} ]
	});
	if (this.dateId != null && this.dateId != "undefined") {
		a.getForm().load(
				{
					deferredRender : false,
					url : __ctxPath + "/personal/getErrandsRegister.do?dateId="
							+ this.dateId,
					waitMsg : "正在载入数据...",
					success : function(e, f) {
						var b = f.result.data;
						var d = getDateFromFormat(b.startTime,
								"yyyy-MM-dd HH:mm:ss");
						var c = getDateFromFormat(b.endTime,
								"yyyy-MM-dd HH:mm:ss");
						Ext.getCmp("startTime").setValue(new Date(d));
						Ext.getCmp("endTime").setValue(new Date(c));
					},
					failure : function(b, c) {
						Ext.ux.Toast.msg("编辑", "载入失败");
					}
				});
	}else{
		//初始化，获取当前人的部门负责人，写入审批人信息
		
		Ext.Ajax.request({
			url : __ctxPath
					+ "/archive/getByCurrentUserArchRecUser.do",
			success : function(h, j) {
				var k = Ext.util.JSON
						.decode(h.responseText).data;
				Ext.getCmp("approvalId").setValue(
						k.deptUserId);
				Ext.getCmp("approvalName").setValue(
						k.deptFullname);
			}
		});
		
	}
	return a;
};