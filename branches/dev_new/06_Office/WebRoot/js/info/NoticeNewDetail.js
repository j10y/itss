var NoticeNewDetail = function(a) {
	return new Ext.Panel(
			{
				title : "通知详情",
				iconCls : "menu-news",
				id : "NoticeNewDetail",
				autoScroll : true,
				autoWidth : true,
				border : false,
				tbar : new Ext.Toolbar(
						{
							height : 30,
							bodyStyle : "text-align:left",
							defaultType : "button",
							items : [
									{
										text : "关闭",
										iconCls : "btn-mail_remove",
										handler : function() {
											var b = Ext
													.getCmp("centerTabPanel");
											var c = b
													.getItem("NoticeNewDetail");
											b.remove(c);
										}
									},
									{
										text : "上一条",
										iconCls : "btn-up",
										handler : function() {
											var c = document
													.getElementById("__haveNextNoticeNewFlag");
											if (c != null
													&& c.value != "endPre") {
												var b = document
														.getElementById("__curNoticeNewId").value;
												Ext
														.getCmp(
																"HomeNoticeNewDisplayPanel")
														.load(
																{
																	url : __ctxPath
																			+ "/pages/info/noticenewdetail.jsp?opt=_pre&newsId="
																			+ b + "&userId=" + __currentUserId,
																	callback : function() {
																		var d = document
																				.getElementById("__curNoticeNewId").value;
																		var e = Ext
																				.getCmp("NoticeNewCommentContainer");
																		e
																				.removeAll();
																		e
																				.add(NoticeNewCommentDisplayGrid(d));
																		e
																				.doLayout(true);
																	}
																});
											} else {
												Ext.ux.Toast.msg("提示信息",
														"这里已经是第一条了");
											}
										}
									},
									{
										text : "下一条",
										iconCls : "btn-last",
										handler : function() {
											var c = document
													.getElementById("__haveNextNoticeNewFlag");
											if (c != null
													&& c.value != "endNext") {
												var b = document
														.getElementById("__curNoticeNewId").value;
												Ext
														.getCmp(
																"HomeNoticeNewDisplayPanel")
														.load(
																{
																	url : __ctxPath
																			+ "/pages/info/noticenewdetail.jsp?opt=_next&newsId="
																			+ b + "&userId=" + __currentUserId,
																	callback : function() {
																		var d = document
																				.getElementById("__curNoticeNewId").value;
																		var e = Ext
																				.getCmp("NoticeNewCommentContainer");
																		e
																				.removeAll();
																		e
																				.add(NoticeNewCommentDisplayGrid(d));
																		e
																				.doLayout(true);
																	}
																});
											} else {
												Ext.ux.Toast.msg("提示信息",
														"这里已经是最后一条了.");
											}
										}
									} ]
						}),
				defaults : {
					width : "80%"
				},
				items : [
						new Ext.Panel({
							id : "HomeNoticeNewDisplayPanel",
							autoScroll : true,
							style : "padding-left:10%;padding-top:10px;",
							autoHeight : true,
							border : false,
							autoLoad : {
								url : __ctxPath
										+ "/pages/info/noticenewdetail.jsp?newsId="
										+ a  + "&userId=" + __currentUserId
							}
						}),
						{
							xtype : "panel",
							border : false,
							id : "NoticeNewCommentContainer",
							style : "padding-left:10%;padding-top:10px;",
							items : [ new NoticeNewCommentDisplayGrid(a) ]
						},
						new Ext.FormPanel(
								{
									url : __ctxPath
											+ "/info/saveNoticeNewComment.do",
									id : "NoticeNewCommentForm",
									iconCls : "menu-info",
									title : "我要评论",
									autoScroll : true,
									style : "padding-left:10%;padding-top:10px;padding-bottom:25px;",
									autoHeight : true,
									defaultType : "textfield",
									formId : "NoticeNewCommentFormId",
									labelWidth : 55,
									defaults : {
										width : 550
									},
									layout : "form",
									items : [ {
										name : "newsComment.newsId",
										xtype : "hidden",
										id : "newsCommentNoticeNewId"
									}, {
										name : "newsComment.userId",
										xtype : "hidden",
										value : curUserInfo.userId
									}, {
										fieldLabel : "用户",
										name : "newsComment.fullname",
										readOnly : true,
										value : curUserInfo.fullname
									}, {
										fieldLabel : "内容",
										xtype : "textarea",
										blankText : "评论内容为必填!",
										allowBlank : false,
										name : "newsComment.content",
										id : "newsCommentContent"
									}, {
										name : "newsComment.flag",
										xtype : "hidden",
										id : "newsCommentFlag",
										value : 1
									}  ],
									buttonAlign : "center",
									buttons : [
											{
												text : "提交",
												iconCls : "btn-save",
												handler : function() {
													Ext
															.getCmp(
																	"newsCommentNoticeNewId")
															.setValue(
																	document
																			.getElementById("__curNoticeNewId").value);
													var b = Ext
															.getCmp("NoticeNewCommentForm");
													if (b.getForm().isValid()) {
														b
																.getForm()
																.submit(
																		{
																			method : "post",
																			waitMsg : "正在提交数据...",
																			success : function(
																					c,
																					e) {
																				Ext.ux.Toast
																						.msg(
																								"操作信息",
																								"成功保存信息！");
																				Ext
																						.getCmp(
																								"newsCommentContent")
																						.setValue(
																								"");
																				Ext
																						.getCmp(
																								"NoticeNewCommentDispalyGrid")
																						.getStore()
																						.reload();
																				var d = Ext
																						.getCmp("NoticeNewCommentGrid");
																				if (d != null) {
																					d
																							.getStore()
																							.reload();
																				}
																			},
																			failure : function(
																					c,
																					d) {
																				Ext.MessageBox
																						.show({
																							title : "操作信息",
																							msg : "信息保存出错，请联系管理员！",
																							buttons : Ext.MessageBox.OK,
																							icon : Ext.MessageBox.ERROR
																						});
																			}
																		});
													}
												}
											},
											{
												text : "重置",
												iconCls : "reset",
												handler : function() {
													Ext
															.getCmp(
																	"newsCommentContent")
															.setValue("");
												}
											} ]
								}) ]
			});
};
var NoticeNewCommentDisplayGrid = function(d) {
	var a = new Ext.grid.ColumnModel(
			{
				columns : [
						{
							width : 40,
							dataIndex : "start",
							renderer : function(h, g, e, i, f) {
								return parseInt(h) + i + 1 + "楼";
							}
						},
						{
							dataIndex : "commentId",
							hidden : true
						},
						{
							width : 400,
							dataIndex : "content",
							renderer : function(h, g, e, i, f) {
								html = '<table width="100%"><tr><td><font color="gray">评论人:'
										+ e.data.fullname
										+ '</font></td><td align="right"><font color="gray">'
										+ e.data.createtime
										+ '</font></td></tr><tr><td rowspan="2"><font style="font:13px 宋体;color: black;line-height:24px;">'
										+ h + "</font></td></tr></table>";
								return html;
							}
						} ],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});
	var b = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : __ctxPath
					+ "/info/listNoticeNewComment.do?Q_flag_N_EQ=1&Q_news.newsId_L_EQ=" + d
		}),
		reader : new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts",
			id : "id",
			fields : [ {
				name : "commentId",
				type : "int"
			}, "content", "createtime", "fullname", "start" ]
		}),
		remoteSort : false
	});
	b.setDefaultSort("createtime", "asc");
	b.load({
		params : {
			start : 0,
			limit : 10
		}
	});
	var c = new Ext.grid.GridPanel({
		store : b,
		hideHeaders : true,
		trackMouseOver : true,
		disableSelection : false,
		loadMask : true,
		autoHeight : true,
		id : "NoticeNewCommentDispalyGrid",
		autoWidth : true,
		title : "查看评论",
		iconCls : "menu-info",
		collapsible : true,
		collapsed : true,
		cm : a,
		viewConfig : {
			forceFit : true,
			enableRowBody : false,
			showPreview : false
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 10,
			store : b,
			displayInfo : true,
			displayMsg : "当前显示{0}至{1}， 共{2}条记录",
			emptyMsg : "当前没有记录"
		})
	});
	return c;
};