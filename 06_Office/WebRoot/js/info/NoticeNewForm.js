NoticeNewImageWin = Ext.extend(Ext.Window, {
	panel : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUI();
		NoticeNewImageWin.superclass.constructor.call(this, {
			id : "NoticeNewImageWin",
			layout : "fit",
			title : "图片预览",
			modal : true,
			items : this.panel
		});
	},
	initUI : function() {
		this.panel = new Ext.Panel({
			id : "ImageShowPanel",
			width : 360,
			height : 290,
			html : '<img src="' + __ctxPath + "/attachFiles/" + this.imagePath
					+ '" width="360" height="290"/>'
		});
	}
});
NoticeNewForm = Ext
		.extend(
				Ext.Window,
				{
					formPanel : null,
					constructor : function(a) {
						Ext.applyIf(this, a);
						this.initUIComponents();
						NoticeNewForm.superclass.constructor.call(this, {
							layout : "fit",
							items : this.formPanel,
							border : false,
							plain : true,
							id : "NoticeNewFormWin",
							title : "通知详细信息",
							iconCls : "menu-news",
							width : 800,
							minWidth : 799,
							height : 550,
							minHeight : 524,
							maximizable : true,
							modal : true,
							buttonAlign : "center",
							buttons : this.buttons
						});
					},
					initUIComponents : function() {
						this.formPanel = this.setup();
						if (this.newsId != null && this.newsId != "undefined") {
							this.formPanel
									.getForm()
									.load(
											{
												deferredRender : false,
												url : __ctxPath
														+ "/info/getNoticeNew.do?newsId="
														+ this.newsId,
												waitMsg : "正在载入数据...",
												success : function(d, f) {
													var c = Ext
															.getCmp("typeId");
													var h = Ext
															.getCmp("typeTree");
													var e = h
															.getNodeById(c.value);
													var a = Ext
															.getCmp("newsTypeSelect");
													a.setValue(e.text);
													var g = Ext
															.getCmp("displayAtForm");
													var b = Ext
															.getCmp("subjectIcon");
													if (b.value != "") {
														g.body
																.update('<img style="border:0;" width="48" height="48" src="'
																		+ __ctxPath
																		+ "/attachFiles/"
																		+ b.value
																		+ '" border="0"/>');
													}
												},
												failure : function(a, b) {
													Ext.ux.Toast.msg("编辑",
															"载入失败");
												}
											});
						}
						this.buttons = [
								{
									text : "保存",
									iconCls : "btn-save",
									handler : function() {
										var c = Ext.getCmp("NoticeNewForm");
										if (c.getForm().isValid()) {
											Ext.getCmp("issuer").setValue(
													curUserInfo.fullname);
											var b = true;
											if (Ext.getCmp("isDeskImage")
													.getValue()) {
												b = false;
												var a = Ext.getCmp(
														"subjectIcon")
														.getValue();
												if (a != "" && a != null) {
													b = true;
												}
											}
											if (b) {
												c
														.getForm()
														.submit(
																{
																	method : "post",
																	waitMsg : "正在提交数据...",
																	success : function(
																			d,
																			e) {
																		Ext.ux.Toast
																				.msg(
																						"操作信息",
																						"成功信息保存！");
																		Ext
																				.getCmp(
																						"NoticeNewGrid")
																				.getStore()
																				.reload();
																		Ext
																				.getCmp(
																						"NoticeNewFormWin")
																				.close();
																	},
																	failure : function(
																			d,
																			e) {
																		Ext.MessageBox
																				.show({
																					title : "操作信息",
																					msg : "信息保存出错，请联系管理员！",
																					buttons : Ext.MessageBox.OK,
																					icon : "ext-mb-error"
																				});
																		Ext
																				.getCmp(
																						"NoticeNewFormWin")
																				.close();
																	}
																});
											} else {
												Ext.ux.Toast.msg("提示信息",
														"图片通知需要上传图片.");
											}
										}
									}
								},
								{
									text : "取消",
									iconCls : "btn-delete",
									handler : function() {
										Ext.getCmp("NoticeNewFormWin").close();
									}
								} ];
					}
				});
NoticeNewForm.prototype.setup = function() {
	var b = __ctxPath + "/info/treeNoticeNewType.do?opt=treeSelector";
	var c = new TreeSelector("newsTypeSelect", b, "通知类型", "typeId", false);
	var a = new Ext.FormPanel(
			{
				url : __ctxPath + "/info/saveNoticeNew.do",
				layout : "form",
				id : "NoticeNewForm",
				frame : false,
				bodyStyle : "padding:5px;",
				labelWidth : 55,
				formId : "NoticeNewFormId",
				defaultType : "textfield",
				items : [
						{
							name : "news.newsId",
							id : "newsId",
							xtype : "hidden",
							value : this.newsId == null ? "" : this.newsId
						},
						{
							xtype : "hidden",
							name : "news.issuer",
							id : "issuer"
						},
						{
							name : "news.typeId",
							id : "typeId",
							xtype : "hidden"
						},
						{
							fieldLabel : "通知标题",
							name : "news.subject",
							width : 400,
							id : "subject"
						},
						{
							fieldLabel : "桌面通知",
							name : "isDeskImage",
							id : "isDeskImage",
							xtype : "checkbox",
							handler : function(d) {
								if (d.getValue()) {
									Ext.getCmp("CheckOutImageButton").show();
								} else {
									Ext.getCmp("CheckOutImageButton").hide();
								}
							}
						},
						{
							fieldLabel : "通知状态",
							hiddenName : "news.status",
							id : "status",
							xtype : "combo",
							mode : "local",
							editable : false,
							triggerAction : "all",
							store : [ [ "1", "生效" ], [ "0", "禁用" ] ],
							value : "1"
						},
						c,
						{
							fieldLabel : "通知作者",
							name : "news.author",
							id : "author",
							value : curUserInfo.fullname
						},
						{
							xtype : "container",
							layout : "column",
							id : "ImageToolContainer",
							defaultType : "textfield",
							height : 50,
							items : [
									{
										xtype : "label",
										text : "通知图标:",
										stype : "padding-right:10px;"
									},
									{
										xtype : "hidden",
										name : "news.subjectIcon",
										id : "subjectIcon"
									},
									{
										id : "displayAtForm",
										xtype : "panel",
										border : false,
										html : '<img style="border:0;" width="48" height="48" src="'
												+ __ctxPath
												+ '/images/default_newsIcon.jpg" border="0"/>'
									},
									{
										xtype : "button",
										iconCls : "btn-upload",
										text : "上传",
										handler : function() {
											NoticeNewForm
													.upLoadNoticeNewIcon();
										}
									},
									{
										xtype : "button",
										iconCls : "btn-del",
										text : "删除",
										handler : function() {
											NoticeNewForm
													.deleteNoticeNewIcon();
										}
									},
									{
										xtype : "button",
										hidden : true,
										iconCls : "btn-check",
										text : "查看图片",
										id : "CheckOutImageButton",
										handler : function() {
											var d = Ext.getCmp("subjectIcon")
													.getValue();
											if (d != "" && d != null) {
												new NoticeNewImageWin({
													imagePath : d
												}).show();
											} else {
												Ext.ux.Toast.msg("提示信息",
														"您还未增加图片.");
											}
										}
									} ]
						}, {
							fieldLabel : "内容",
							name : "news.content",
							id : "content",
							xtype : "fckeditor",
							height : 300
						} ]
			});
	return a;
};
function uploadImage(b) {
	var c = Ext.getCmp("displayAtForm");
	var a = Ext.getCmp("subjectIcon");
	a.setValue(b[0].filepath);
	c.body.update('<img style="border:0;" width="48" height="48" src="'
			+ __ctxPath + "/attachFiles/" + b[0].filepath + '" border="0"/>');
}
NoticeNewForm.deleteNoticeNewIcon = function() {
	var a = Ext.getCmp("subjectIcon");
	if (a.value != null && a.value != "" && a.value != "undefined") {
		var b = "图片一旦删除将不可恢复,";
		Ext.Msg
				.confirm(
						"确认信息",
						b + "是否删除?",
						function(c) {
							if (c == "yes") {
								Ext.Ajax
										.request({
											url : __ctxPath
													+ "/system/deleteFileAttach.do",
											method : "post",
											params : {
												filePath : a.value
											},
											success : function() {
												var d = Ext.getCmp("newsId").value;
												if (d != "" && d != null
														&& d != "undefined") {
													Ext.Ajax
															.request({
																url : __ctxPath
																		+ "/info/iconNoticeNew.do",
																method : "post",
																params : {
																	newsId : d
																},
																success : function() {
																	a
																			.setValue("");
																	Ext
																			.getCmp("displayAtForm").body
																			.update('<img style="border:0;" width="48" height="48" src="'
																					+ __ctxPath
																					+ '/images/default_newsIcon.jpg" border="0"/>');
																	Ext
																			.getCmp(
																					"NoticeNewGrid")
																			.getStore()
																			.reload();
																}
															});
												} else {
													a.setValue("");
													Ext.getCmp("displayAtForm").body
															.update('<img style="border:0;" width="48" height="48" src="'
																	+ __ctxPath
																	+ '/images/default_newsIcon.jpg" border="0"/>');
												}
											}
										});
							}
						});
	} else {
		Ext.ux.Toast.msg("提示信息", "您还未增加图标.");
	}
};
NoticeNewForm.upLoadNoticeNewIcon = function() {
	var a = Ext.getCmp("subjectIcon");
	var b = App.createUploadDialog({
		file_cat : "info/news",
		callback : uploadImage
	});
	if (a.value != "" && a.value != null && a.value != "undefined") {
		var c = "再次上传需要先删除原有图片,";
		Ext.Msg
				.confirm(
						"信息确认",
						c + "是否删除？",
						function(d) {
							if (d == "yes") {
								Ext.Ajax
										.request({
											url : __ctxPath
													+ "/system/deleteFileAttach.do",
											method : "post",
											params : {
												filePath : a.value
											},
											success : function() {
												var e = Ext.getCmp("newsId").value;
												if (e != "" && e != null
														&& e != "undefined") {
													Ext.Ajax
															.request({
																url : __ctxPath
																		+ "/info/iconNoticeNew.do",
																method : "post",
																params : {
																	newsId : e
																},
																success : function() {
																	a
																			.setValue("");
																	Ext
																			.getCmp("displayAtForm").body
																			.update('<img style="border:0;" width="48" height="48" src="'
																					+ __ctxPath
																					+ '/images/default_newsIcon.jpg" border="0"/>');
																	Ext
																			.getCmp(
																					"NoticeNewGrid")
																			.getStore()
																			.reload();
																	b
																			.show("queryBtn");
																}
															});
												} else {
													a.setValue("");
													Ext.getCmp("displayAtForm").body
															.update('<img style="border:0;" width="48" height="48" src="'
																	+ __ctxPath
																	+ '/images/default_newsIcon.jpg" border="0"/>');
													b.show("queryBtn");
												}
											}
										});
							}
						});
	} else {
		b.show("queryBtn");
	}
};