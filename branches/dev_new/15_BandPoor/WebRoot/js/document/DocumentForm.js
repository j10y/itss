var DocumentForm = function(b) {
	this.docId = b;
	var a = this.setup();
	var c = new Ext.Window({
		id : "DocumentFormWin",
		title : "文档详细信息",
		iconCls : "menu-personal-doc",
		width : 700,
		height : 500,
		modal : true,
		maximizable : true,
		layout : "fit",
		buttonAlign : "center",
		items : [ this.setup() ],
		buttons : [ {
			text : "保存",
			iconCls : "btn-save",
			handler : function() {
				var d = Ext.getCmp("DocumentForm");
				var e = Ext.getCmp("folderSelect").getValue();
				if (e != null && e != "" && e != "undefined") {
					if (d.getForm().isValid()) {
						d.getForm().submit({
							method : "post",
							waitMsg : "正在提交数据...",
							success : function(f, g) {
								Ext.ux.Toast.msg("操作信息", "成功信息保存！");
								Ext.getCmp("DocumentGrid").getStore().reload();
								c.close();
							},
							failure : function(f, g) {
								Ext.MessageBox.show({
									title : "操作信息",
									msg : "信息保存出错，请联系管理员！",
									buttons : Ext.MessageBox.OK,
									icon : "ext-mb-error"
								});
								c.close();
							}
						});
					}
				} else {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "请选择文件夹！",
						buttons : Ext.MessageBox.OK,
						icon : "ext-mb-error"
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "btn-cancel",
			handler : function() {
				c.close();
			}
		} ]
	});
	c.show();
};
DocumentForm.prototype.setup = function() {
	var b = __ctxPath + "/document/listDocFolder.do?method=1";
	var c = new TreeSelector("folderSelect", b, "文件夹*", "DocumentForm.folderId");
	var a = new Ext.FormPanel(
			{
				url : __ctxPath + "/document/saveDocument.do",
				id : "DocumentForm",
				bodyStyle : "padding:5px;",
				frame : false,
				border : false,
				formId : "DocumentFormId",
				defaults : {
					anchor : "98%,98%",
					margins : {
						top : 4,
						right : 4,
						bottom : 4,
						left : 4
					}
				},
				reader : new Ext.data.JsonReader({
					root : "data"
				}, [ {
					name : "DocumentForm.folderId",
					mapping : "folderId"
				}, {
					name : "DocumentForm.docId",
					mapping : "docId"
				}, {
					name : "DocumentForm.docName",
					mapping : "docName"
				}, {
					name : "DocumentForm.content",
					mapping : "content"
				}, {
					name : "DocumentForm.fileIds",
					mapping : "fileIds"
				} ]),
				items : [
						{
							name : "folderId",
							id : "DocumentForm.folderId",
							xtype : "hidden"
						},
						{
							name : "document.docId",
							id : "DocumentForm.docId",
							xtype : "hidden",
							value : this.docId == null ? "" : this.docId
						},
						c,
						{
							xtype : "textfield",
							fieldLabel : "文档名称",
							name : "document.docName",
							id : "DocumentForm.docName",
							anchor : "98%",
							allowBlank : false
						},
						{
							height : 280,
							anchor : "98%",
							xtype : "fckeditor",
							fieldLabel : "内容",
							name : "document.content",
							id : "DocumentForm.content",
							allowBlank : false
						},
						{
							layout : "column",
							border : false,
							defaults : {
								border : false
							},
							items : [
									{
										columnWidth : 0.7,
										layout : "form",
										border : false,
										items : [ {
											fieldLabel : "附件",
											xtype : "panel",
											id : "personFilePanel",
											frame : false,
											border : true,
											bodyStyle : "padding:4px 4px 4px 4px",
											height : 80,
											autoScroll : true,
											html : ""
										} ]
									},
									{
										columnWidth : 0.3,
										items : [
												{
													border : false,
													xtype : "button",
													text : "添加附件",
													iconCls : "menu-attachment",
													handler : function() {
														var d = App
																.createUploadDialog({
																	file_cat : "document",
																	callback : function(
																			h) {
																		var e = Ext
																				.getCmp("DocumentForm.fileIds");
																		var g = Ext
																				.getCmp("personFilePanel");
																		for ( var f = 0; f < h.length; f++) {
																			if (e
																					.getValue() != "") {
																				e
																						.setValue(e
																								.getValue()
																								+ ",");
																			}
																			e
																					.setValue(e
																							.getValue()
																							+ h[f].fileId);
																			Ext.DomHelper
																					.append(
																							g.body,
																							'<span><a href="#" onclick="FileAttachDetail.show('
																									+ h[f].fileId
																									+ ')">'
																									+ h[f].filename
																									+ '</a> <img class="img-delete" src="'
																									+ __ctxPath
																									+ '/images/system/delete.gif" onclick="removeFile(this,'
																									+ h[f].fileId
																									+ ')"/>&nbsp;|&nbsp;</span>');
																		}
																	}
																});
														d.show(this);
													}
												},
												{
													xtype : "button",
													text : "清除附件",
													iconCls : "reset",
													handler : function() {
														var e = Ext
																.getCmp("DocumentForm.fileIds");
														var d = Ext
																.getCmp("personFilePanel");
														d.body.update("");
														e.setValue("");
													}
												},
												{
													xtype : "hidden",
													id : "DocumentForm.fileIds",
													name : "fileIds"
												} ]
									} ]
						} ]
			});
	if (this.docId != null && this.docId != "undefined") {
		a
				.getForm()
				.load(
						{
							url : __ctxPath + "/document/getDocument.do?docId="
									+ this.docId,
							waitMsg : "正在载入数据...",
							success : function(d, g) {
								var k = Ext.util.JSON
										.decode(g.response.responseText).data[0];
								var e = k.docFolder.folderId;
								var f = k.docFolder.folderName;
								Ext.getCmp("DocumentForm.folderId").setValue(e);
								Ext.getCmp("folderSelect").setValue(f);
								var l = k.attachFiles;
								var j = Ext.getCmp("personFilePanel");
								var m = Ext.getCmp("DocumentForm.fileIds");
								for ( var h = 0; h < l.length; h++) {
									if (m.getValue() != "") {
										m.setValue(m.getValue() + ",");
									}
									m.setValue(m.getValue() + l[h].fileId);
									Ext.DomHelper
											.append(
													j.body,
													'<span><a href="#" onclick="FileAttachDetail.show('
															+ l[h].fileId
															+ ')">'
															+ l[h].fileName
															+ '</a><img class="img-delete" src="'
															+ __ctxPath
															+ '/images/system/delete.gif" onclick="removeFile(this,'
															+ l[h].fileId
															+ ')"/>&nbsp;|&nbsp;</span>');
								}
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
	return a;
};
function removeFile(e, a) {
	var b = Ext.getCmp("DocumentForm.fileIds");
	var d = b.getValue();
	if (d.indexOf(",") < 0) {
		b.setValue("");
	} else {
		d = d.replace("," + a, "").replace(a + ",", "");
		b.setValue(d);
	}
	var c = Ext.get(e.parentNode);
	c.remove();
}