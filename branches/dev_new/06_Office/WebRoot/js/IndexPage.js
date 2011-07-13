var IndexPage = Ext
		.extend(
				Ext.Viewport,
				{
					top : new Ext.Panel({
						region : "north",
						id : "__nortPanel",
						contentEl : "app-header",
						height : 60
					}),
					center : null,
					west : new Ext.Panel({
						region : "west",
						id : "west-panel",
						title : "导航",
						iconCls : "menu-navigation",
						split : true,
						width : 180,
						autoScroll : true,
						layout : "accordion",
						collapsible : true,
						items : []
					}),
					south : new Ext.Panel(
							{
								region : "south",
								height : 28,
								border : false,
								bbar : [
										{
											id : "messageTip",
											xtype : "button",
											hidden : true,
											width : 50,
											height : 20,
											handler : function() {
												var a = Ext
														.getCmp("messageTip");
												var b = Ext.getCmp("win");
												if (b == null) {
													new MessageWin().show();
												}
												a.hide();
											}
										},
										"->",
										{
											xtype : "tbfill"
										},
										{
											xtype : "tbtext",
											text : __companyName + "办公协同管理系统",
											id : "toolbarCompanyName"
										},
										{
											xtype : "tbseparator"
										},
										new Ext.Toolbar.TextItem(
												'技术支持 <a href="http://172.16.100.26/ITC/" target="_blank">上品折扣信息系统部</a>'),
										{
											xtype : "tbseparator"
										},
										{
											pressed : false,
											text : "便签",
											iconCls : "tipsTile",
											handler : function() {
												App
														.clickTopTab("PersonalTipsView");
											}
										},
										{
											xtype : "tbseparator"
										},
										{
											text : "收展",
											iconCls : "btn-expand",
											handler : function() {
												var a = Ext
														.getCmp("__nortPanel");
												if (a.collapsed) {
													a.expand(true);
												} else {
													a.collapse(true);
												}
											}
										}]
							}),
					constructor : function() {
						this.center = new Ext.TabPanel({
							id : "centerTabPanel",
							region : "center",
							deferredRender : true,
							enableTabScroll : true,
							activeTab : 0,
							defaults : {
								autoScroll : true,
								closable : true
							},
							items : [],
							plugins: new Ext.ux.TabCloseMenu()
						});
						IndexPage.superclass.constructor.call(this, {
							layout : "border",
							items : [ this.top, this.west, this.center,
									this.south ]
						});
						this.afterPropertySet();
						this.loadWestMenu();
						this.buildToolBar();
					},
					buildToolBar : function(){
						
						var toolbar = new Ext.Toolbar({
							id : 'down-tool',
							renderTo : 'down',
							cls:'x-toolbar-banner',
							items : [
										{
											id : "searchContent",
											xtype : "textfield",
											width : 100
										},
										{
											id : "searchType",
											width : 60,
											xtype : "combo",
											mode : "local",
											editable : false,
											triggerAction : "all",
											store : [ [ "news", "新闻" ], [ "mail", "邮件" ],
													[ "notice", "公告" ], [ "document", "文档" ] ],
											value : "news"
										},
										{
											xtype : "button",
											text : "搜索",
											iconCls : "search",
											handler : function() {
												var b = Ext.getCmp("searchContent").getValue();
												var a = Ext.getCmp("searchType").value;
												if (a == "news") {
													App.clickTopTab("SearchNews", b, function() {
														AppUtil.removeTab("SearchNews");
													});
												} else {
													if (a == "mail") {
														App
																.clickTopTab(
																		"SearchMail",
																		b,
																		function() {
																			AppUtil
																					.removeTab("SearchMail");
																		});
													} else {
														if (a == "notice") {
															App
																	.clickTopTab(
																			"SearchNotice",
																			b,
																			function() {
																				AppUtil
																						.removeTab("SearchNotice");
																			});
														} else {
															if (a == "document") {
																App
																		.clickTopTab(
																				"SearchDocument",
																				b,
																				function() {
																					AppUtil
																							.removeTab("SearchDocument");
																				});
															}
														}
													}
												}
											}
										} , "-", Ext.getCmp("ChangeTheme")]
						});
						return toolbar;
					},
					
					afterPropertySet : function() {
						var a = this.center;
						var c = function(f) {
							var d = Ext.getCmp("messageTip");
							var g = Ext.getCmp("win");
							var e = Ext.getCmp("wind");
							if (f > 0 && g == null && e == null) {
								d
										.setText('<div style="height:25px;"><img src="'
												+ __ctxPath
												+ '/images/newpm.gif" style="height:12px;"/>你有<strong style="color: red;">'
												+ f + "</strong>信息</div>");
								soundManager.play("msgSound");
								d.show();
							} else {
								d.hide();
							}
						};
						var b = function() {
							Ext.Ajax.request({
								url : __ctxPath + "/info/countInMessage.do",
								method : "POST",
								success : function(e, f) {
									var d = Ext.util.JSON
											.decode(e.responseText);
									count = d.count;
									c(count);
									setTimeout(b, 1000 * 60);
								},
								failure : function(d, e) {
								},
								scope : this
							});
						};
						setTimeout(function() {
							setInterval("CalConv()", 1000);
							soundManager = new SoundManager();
							soundManager.url = __ctxPath + "/js/sound/swf/";
							soundManager.beginDelayedInit();
							soundManager.debugMode = false;
							soundManager.consoleOnly = false;
							soundManager.onload = function() {
								soundManager.createSound({
									id : "msgSound",
									url : __ctxPath + "/js/sound/mp3/msg.mp3"
								});
								b();
							};
						}, 100);
					},
					loadWestMenu : function() {
						var westPanel = Ext.getCmp("west-panel");
						Ext.Ajax
								.request({
									url : __ctxPath + "/panelTreeMenu.do",
									success : function(response, options) {
										var arr = eval(response.responseText);
										var __activedPanelId = getCookie("__activedPanelId");
										for ( var i = 0; i < arr.length; i++) {
											var doc = strToDom(arr[i].subXml);
											var root = doc.documentElement
													|| doc;
											var panel = new Ext.tree.TreePanel(
													{
														id : arr[i].id,
														title : arr[i].text,
														iconCls : arr[i].iconCls,
														layout : "fit",
														animate : true,
														border : false,
														autoScroll : true,
														loader : new xpsoft.ux.TreeXmlLoader(
																{
																	preloadChildren : true
																}),
														root : new Ext.tree.AsyncTreeNode(
																{
																	text : root.tagName,
																	xmlNode : root
																}),
														listeners : {
															"click" : App.clickNode
														},
														rootVisible : false
													});
											westPanel.add(panel);
											panel.on("expand", function(p) {
												var expires = new Date();
												expires.setDate(expires
														.getDate() + 30);
												setCookie("__activedPanelId",
														p.id, expires,
														__ctxPath);
											});
											if (arr[i].id == __activedPanelId) {
												westPanel.layout.activeItem = panel;
											}
										}
										westPanel.doLayout();
									}
								});
					}
				});