ArchivesRecSearchView = Ext
		.extend(
				Ext.Panel,
				{
					constructor : function(a) {
						if (a == null) {
							a = {};
						}
						Ext.apply(this, a);
						this.initComponents();
						ArchivesRecSearchView.superclass.constructor.call(this, {
							id : "ArchivesRecSearchView",
							title : "收文查询",
							iconCls : "menu-arch-controll",
							region : "center",
							layout : "border",
							items : [ this.searchPanel, this.gridPanel ]
						});
					},
					typeId : null,
					searchPanel : null,
					gridPanel : null,
					store : null,
					topbar : null,
					initComponents : function() {
						this.searchPanel = new Ext.FormPanel({
							height : 40,
							region : "north",
							frame : false,
							border : false,
							layout : "hbox",
							layoutConfig : {
								padding : "5",
								align : "middle"
							},
							defaults : {
								xtype : "label",
								margins : {
									top : 0,
									right : 4,
									bottom : 4,
									left : 4
								}
							},
							items : [ {
								text : "发文字号"
							}, {
								name : "Q_archivesNo_S_LK",
								xtype : "textfield",
								anchor : "98%"
							}, {
								text : "文件标题"
							}, {
								name : "Q_subject_S_LK",
								xtype : "textfield",
								anchor : "98%"
							}, {
								xtype : "button",
								text : "查询",
								iconCls : "search",
								handler : this.search.createCallback(this)
							}, {
								xtype : "hidden",
								name : "Q_status_SN_EQ",
								value : 7
							} ]
						});
						this.store = new Ext.data.JsonStore({
							url : __ctxPath + "/archive/listArchives.do",
							baseParams : {
								"Q_status_SN_EQ" : 7,
								"Q_archType_SN_EQ" : 1
							},
							root : "result",
							totalProperty : "totalCounts",
							remoteSort : true,
							fields : [ {
								name : "archivesId",
								type : "int"
							}, "typeId", "typeName", "archivesNo", "issueDep",
									"depId", "subject", "issueDate", "status",
									"shortContent", "fileCounts",
									"handleOpinion", "privacyLevel",
									"urgentLevel", "issuer", "issuerId",
									"keywords", "sources", "archiveType" ]
						});
						this.store.setDefaultSort("archivesId", "desc");
						this.store.load({
							params : {
								start : 0,
								limit : 25
							}
						});
						var b = new Array();
						if (isGranted("_ArchivesRecCtrlQuery")) {
							b.push({
								iconCls : "btn-readdocument",
								qtip : "查看",
								style : "margin:0 3px 0 3px"
							});
						}
						this.rowActions = new Ext.ux.grid.RowActions({
							header : "管理",
							width : 80,
							actions : b
						});
						var c = new Ext.grid.CheckboxSelectionModel();
						var a = new Ext.grid.ColumnModel(
								{
									columns : [
											c,
											new Ext.grid.RowNumberer(),
											{
												header : "archivesId",
												dataIndex : "archivesId",
												hidden : true
											},
											{
												header : "发文字号",
												dataIndex : "archivesNo"
											},
											{
												header : "发文机关或部门",
												dataIndex : "issueDep"
											},
											{
												header : "文件标题",
												dataIndex : "subject"
											},
											{
												header : "发布日期",
												dataIndex : "issueDate"
											},
											{
												header : "秘密等级",
												dataIndex : "privacyLevel"
											},
											{
												header : "紧急程度",
												dataIndex : "urgentLevel"
											},
											{
												header : "公文状态",
												dataIndex : "status",
												renderer : function(c) {
													if (c == 0) {
														return '<font color="red">草稿</font>';
													} else {
														if (c == 1) {
															return '<font color="green">待办公室传阅</font>';
														} else {
															if (c == 2) {
																return '<font color="green">待办公室主任批阅</font>';
															} else {
																if (c == 3) {
																	return '<font color="green">待分管或局领导批示</font>';
																} else {
																	if (c == 4) {
																		return '<font color="green">待科室主任传阅</font>';
																	} else {
																		if (c == 5) {
																			return '<font color="green">待科室承办人处理并归档</font>';
																		} else {
																			if (c == 6) {
																				return '<font color="green">阅读处理中</font>';
																			} else {
																				if (c == 7) {
																					return '<font color="#green">收文归档</font>';
																				} else {
																					return '<font color="red">待指定科室传阅人</font>';
																				}																				
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}, this.rowActions ],
									defaults : {
										sortable : true,
										menuDisabled : false,
										width : 100
									}
								});
						this.gridPanel = new Ext.grid.GridPanel({
							id : "ArchivesGrid",
							region : "center",
							stripeRows : true,
							store : this.store,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							autoHeight : true,
							cm : a,
							sm : c,
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
						this.rowActions.on("action", this.onRowAction, this);
					},
					search : function(a) {
						if (a.searchPanel.getForm().isValid()) {
							a.searchPanel
									.getForm()
									.submit(
											{
												waitMsg : "正在提交查询",
												url : __ctxPath
														+ "/archive/listArchives.do",
												params : {
													"Q_status_SN_EQ" : 7,
													"Q_archType_SN_EQ" : 1
												},
												success : function(c, d) {
													var b = Ext.util.JSON
															.decode(d.response.responseText);
													a.gridPanel.getStore()
															.loadData(b);
												}
											});
						}
					},
					editRecord : function(a) {
						new ArchivesDetail({
							archivesId : a.data.archivesId
						}).show();
					},
					remind : function(b) {
						var a = b.data.status;
						var d = "";
						var c = false;
						if (a == 1 || a == 2) {
							d = "登记拟办";
						} else {
							if (a == 3) {
								d = "领导批示";
							} else {
								if (a == 4) {
									d = "公文分发";
								} else {
									if (a == 5 || a == 6) {
										d = "承办传阅";
									} else {
										c = true;
									}
								}
							}
						}
						if (!c) {
							new ArchHastenForm({
								archivesId : b.data.archivesId,
								archivesNo : b.data.archivesNo,
								activityName : d
							}).show();
						} else {
							Ext.ux.Toast.msg("提示信息", "收文已归档!");
						}
					},
					onRowAction : function(c, a, d, e, b) {
						switch (d) {
						case "btn-readdocument":
							this.editRecord(a);
							break;
						case "btn-archives-remind":
							this.remind(a);
							break;
						default:
							break;
						}
					}
				});