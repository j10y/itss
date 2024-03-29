var DocumentSharedForm = function(a) {
	this.docId = a;
};
DocumentSharedForm.prototype.getView = function() {
	var b = this.docId;
	var a = new Ext.FormPanel(
			{
				id : "documentSharedForm",
				bodyStyle : "padding:4px 10px 4px 10px",
				reader : new Ext.data.JsonReader({
					root : "data"
				}, [ {
					name : "DocumentSharedForm.docId",
					mapping : "docId"
				}, {
					name : "DocumentSharedForm.sharedUserIds",
					mapping : "sharedUserIds"
				}, {
					name : "DocumentSharedForm.sharedUserNames",
					mapping : "sharedUserNames"
				}, {
					name : "DocumentSharedForm.sharedDepIds",
					mapping : "sharedDepIds"
				}, {
					name : "DocumentSharedForm.sharedDepNames",
					mapping : "sharedDepNames"
				}, {
					name : "DocumentSharedForm.sharedRoleIds",
					mapping : "sharedRoleIds"
				}, {
					name : "DocumentSharedForm.sharedRoleNames",
					mapping : "sharedRoleNames"
				} ]),
				items : [
						{
							xtype : "hidden",
							name : "docId",
							id : "DocumentSharedForm.docId",
							value : this.docId
						},
						{
							xtype : "fieldset",
							border : false,
							layout : "column",
							items : [
									{
										xtype : "label",
										text : "共享人员",
										width : 100
									},
									{
										xtype : "hidden",
										name : "sharedUserIds",
										id : "DocumentSharedForm.sharedUserIds"
									},
									{
										xtype : "textarea",
										name : "sharedUserNames",
										id : "DocumentSharedForm.sharedUserNames",
										width : 300
									},
									{
										xtype : "button",
										text : "选择",
										iconCls : "btn-select",
										width : 80,
										handler : function() {
											UserSelector
													.getView(
															function(e, g) {
																var d = Ext
																		.getCmp("DocumentSharedForm.sharedUserIds");
																var h = Ext
																		.getCmp("DocumentSharedForm.sharedUserNames");
																if (d
																		.getValue() == "") {
																	d
																			.setValue(","
																					+ e
																					+ ",");
																	h
																			.setValue(g);
																	return;
																} else {
																	var i = d
																			.getValue()
																			.split(
																					",");
																	var f = h
																			.getValue()
																			.split(
																					",");
																	d
																			.setValue(uniqueArray(i
																					.concat(e
																							.split(",")))
																					+ ",");
																	h
																			.setValue(uniqueArray(f
																					.concat(g
																							.split(","))));
																}
															}).show();
										}
									},
									{
										xtype : "button",
										iconCls : "btn-clear",
										text : "清空",
										handler : function() {
											var d = Ext
													.getCmp("DocumentSharedForm.sharedUserIds");
											var e = Ext
													.getCmp("DocumentSharedForm.sharedUserNames");
											d.setValue("");
											e.setValue("");
										},
										width : 80
									} ]
						},
						{
							xtype : "fieldset",
							border : false,
							layout : "column",
							items : [
									{
										xtype : "label",
										text : "共享部门",
										width : 100
									},
									{
										name : "sharedDepIds",
										id : "DocumentSharedForm.sharedDepIds",
										xtype : "hidden"
									},
									{
										name : "sharedDepNames",
										id : "DocumentSharedForm.sharedDepNames",
										xtype : "textarea",
										width : 300
									},
									{
										xtype : "button",
										text : "选择",
										iconCls : "btn-select",
										width : 80,
										handler : function() {
											DepSelector
													.getView(
															function(f, h) {
																var e = Ext
																		.getCmp("DocumentSharedForm.sharedDepIds");
																var g = Ext
																		.getCmp("DocumentSharedForm.sharedDepNames");
																if (e
																		.getValue() == "") {
																	e
																			.setValue(","
																					+ f
																					+ ",");
																	g
																			.setValue(h);
																	return;
																}
																var i = e
																		.getValue()
																		.split(
																				",");
																var d = g
																		.getValue()
																		.split(
																				",");
																e
																		.setValue(uniqueArray(i
																				.concat(f
																						.split(",")))
																				+ ",");
																g
																		.setValue(uniqueArray(d
																				.concat(h
																						.split(","))));
															}).show();
										}
									},
									{
										xtype : "button",
										text : "清空",
										iconCls : "btn-clear",
										handler : function() {
											var d = Ext
													.getCmp("DocumentSharedForm.sharedDepIds");
											var e = Ext
													.getCmp("DocumentSharedForm.sharedDepNames");
											d.setValue("");
											e.setValue("");
										},
										width : 80
									} ]
						},
						{
							xtype : "fieldset",
							border : false,
							layout : "column",
							items : [
									{
										xtype : "label",
										text : "共享角色",
										width : 100
									},
									{
										xtype : "hidden",
										id : "DocumentSharedForm.sharedRoleIds",
										name : "sharedRoleIds"
									},
									{
										name : "sharedRoleNames",
										id : "DocumentSharedForm.sharedRoleNames",
										xtype : "textarea",
										width : 300
									},
									{
										xtype : "button",
										text : "选择",
										iconCls : "btn-select",
										width : 80,
										handler : function() {
											RoleSelector
													.getView(
															function(g, h) {
																var d = Ext
																		.getCmp("DocumentSharedForm.sharedRoleIds");
																var e = Ext
																		.getCmp("DocumentSharedForm.sharedRoleNames");
																if (d
																		.getValue() == "") {
																	d
																			.setValue(","
																					+ g
																					+ ",");
																	e
																			.setValue(h);
																	return;
																}
																var i = d
																		.getValue()
																		.split(
																				",");
																var f = e
																		.getValue()
																		.split(
																				",");
																d
																		.setValue(uniqueArray(i
																				.concat(g
																						.split(",")))
																				+ ",");
																e
																		.setValue(uniqueArray(f
																				.concat(h
																						.split(","))));
															}).show();
										}
									},
									{
										xtype : "button",
										text : "清空",
										iconCls : "btn-clear",
										handler : function() {
											var d = Ext
													.getCmp("DocumentSharedForm.sharedRoleIds");
											var e = Ext
													.getCmp("DocumentSharedForm.sharedRoleNames");
											d.setValue("");
											e.setValue("");
										},
										width : 80
									} ]
						} ]
			});
	if (b != null && b != "" && b != "undefined") {
		a.getForm().load({
			deferredRender : false,
			url : __ctxPath + "/document/getDocument.do?docId=" + b,
			waitMsg : "正在载入数据...",
			success : function(d, e) {
			},
			failure : function(d, e) {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "载入信息失败，请联系管理员！",
					buttons : Ext.MessageBox.OK,
					icon : "ext-mb-error"
				});
			}
		});
	}
	var c = new Ext.Window({
		title : "文档授权信息",
		width : 620,
		iconCls : "menu-mail_folder",
		height : 380,
		modal : true,
		layout : "anchor",
		plain : true,
		bodyStyle : "padding:5px;",
		scope : this,
		buttonAlign : "center",
		items : a,
		buttons : [ {
			xtype : "button",
			text : "共享",
			iconCls : "btn-ok",
			handler : function() {
				a.getForm().submit({
					url : __ctxPath + "/document/shareDocument.do",
					method : "post",
					waitMsg : "正在提交...",
					success : function(d, e) {
						c.close();
					}
				});
			}
		}, {
			xtype : "button",
			iconCls : "btn-cancel",
			text : "关闭",
			handler : function() {
				c.close();
			}
		} ]
	});
	return c;
};