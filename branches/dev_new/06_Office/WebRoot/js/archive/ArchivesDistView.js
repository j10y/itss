Ext.ns("ArchivesDistView");
ArchivesDistView = Ext
		.extend(
				Ext.Panel,
				{
					searchPanel : null,
					gridPanel : null,
					store : null,
					topbar : null,
					constructor : function(a) {
						Ext.applyIf(this, a);
						this.initUIComponents();
						ArchivesDistView.superclass.constructor.call(this, {
							id : "ArchivesDistView",
							iconCls : "menu-archive-sign",
							title : "待阅公文",
							region : "center",
							layout : "border",
							items : [ this.searchPanel, this.gridPanel ]
						});
						
					},
					initUIComponents : function() {
						this.searchPanel = new Ext.FormPanel( {
							height : 35,
							region : "north",
							frame : false,
							border : false,
							layout : "hbox",
							layoutConfig : {
								padding : "5",
								align : "middle"
							},
							defaults : {
								style : "padding:0px 5px 0px 5px;",
								border : false,
								anchor : "98%,98%",
								labelWidth : 75,
								xtype : "label"
							},
							items : [ {
								text : "类型名称"
							}, {
								name : "Q_typeName_S_LK",
								width : 100,
								xtype : "textfield"
							}, {
								text : "发文字号"
							}, {
								width : 100,
								name : "Q_archivesNo_S_LK",
								xtype : "textfield"
							}, {
								text : "文件标题"
							}, {
								width : 100,
								name : "Q_subject_S_LK",
								xtype : "textfield"
							}, {
								xtype : "button",
								text : "查询",
								iconCls : "search",
								handler : this.search.createCallback(this)
							}, {
								xtype : "hidden",
								name : "Q_archives.archType_SN_EQ",
								value : 0
							} ]
						});
						this.store = new Ext.data.JsonStore( {
							url : __ctxPath + "/archive/listArchivesDist.do",
							root : "result",
							baseParams : {
								"Q_status_SN_EQ" : 0
							},
							totalProperty : "totalCounts",
							remoteSort : true,
							fields : [ {
								name : "archDistId",
								type : "int"
							}, "archives" ]
						});
						this.store.setDefaultSort("archDistId", "desc");
						this.store.load( {
							params : {
								start : 0,
								limit : 25
							}
						});
						var b = new Ext.grid.CheckboxSelectionModel();
						var a = new Ext.grid.ColumnModel(
								{
									columns : [
											b,
											new Ext.grid.RowNumberer(),
											{
												header : "archDistId",
												dataIndex : "archDistId",
												hidden : true
											},
											{
												header : "公文类型名称",
												dataIndex : "archives",
												renderer : function(c) {
													return c.typeName;
												}
											},
											{
												header : "发文字号",
												dataIndex : "archives",
												renderer : function(c) {
													return c.archivesNo;
												}
											},
											{
												header : "发文机关或部门",
												dataIndex : "archives",
												renderer : function(c) {
													return c.issueDep;
												}
											},
											{
												header : "文件标题",
												dataIndex : "archives",
												renderer : function(c) {
													return c.subject;
												}
											},
											{
												header : "公文状态",
												dataIndex : "archives",
												renderer : function(c) {
													if (c.status == 7) {
														return "已归档";
													} else {
														return "";
													}
												}
											},
											{
												header : "秘密等级",
												dataIndex : "archives",
												renderer : function(c) {
													return c.privacyLevel;
												}
											},
											{
												header : "紧急程度",
												dataIndex : "archives",
												renderer : function(c) {
													return c.urgentLevel;
												}
											},
											{
												header : "发文时间",
												dataIndex : "archives",
												renderer : function(c) {
													return c.createtime
															.substring(0, 10);
												}
											},
											{
												header : "管理",
												width : 100,
												dataIndex : "archives",
												sortable : false,
												renderer : function(j, i, e, h,
														k) {
													var c = j.archivesId;
													var d = j.status;
													var f = e.data.archDistId;
													var g = "";
													if (isGranted("_ArchivesDistQuery")) {
														g += '<button title="查阅详情" value=" " class="btn-archives-detail" onclick="ArchivesDistView.detail(' + c + ',' + f + ')">&nbsp;&nbsp;</button>';
													}
													return g;
												}
											} ],
									defaults : {
										sortable : true,
										menuDisabled : false,
										width : 100
									}
								});
						this.topbar = new Ext.Toolbar( {
							height : 30,
							bodyStyle : "text-align:left",
							items : []
						});
						this.gridPanel = new Ext.grid.GridPanel( {
							id : "ArchivesDistGrid",
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
							bbar : new Ext.PagingToolbar( {
								pageSize : 25,
								store : this.store,
								displayInfo : true,
								displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
								emptyMsg : "当前没有记录"
							})
						});
						this.gridPanel.addListener("rowdblclick", function(d,
								c, f) {
							d.getSelectionModel().each( function(e) {
							});
						});
					},
					search : function(a) {
						if (a.searchPanel.getForm().isValid()) {
							a.searchPanel
									.getForm()
									.submit(
											{
												waitMsg : "正在提交查询",
												url : __ctxPath + "/archive/listArchivesDist.do?Q_status_SN_EQ=0",
												success : function(c, d) {
													var b = Ext.util.JSON
															.decode(d.response.responseText);
													a.gridPanel.getStore()
															.loadData(b);
												}
											});
						}
					},
					createRecord : function() {
						new ArchivesForm().show();
					}
				});
ArchivesDistView.detail = function(archivesId,archDistId) {
	new ArchivesDetailWin( {
		archivesId : archivesId
	}).show();
	//修改状态，重新load
	
	Ext.Ajax.request({
		url : __ctxPath
				+ "/archive/viewArchivesDist.do",
		params : {
			archDistId : archDistId												
		},
		success : function() {
			Ext.getCmp("ArchivesDistView").gridPanel.store.reload();
		}
	});
	Ext.getCmp("ArchivesDistView").gridPanel.store.reload();
	
};
ArchivesDistView.attach = function(d, a) {
	var b = Ext.getCmp("centerTabPanel");
	var c = Ext.getCmp("ArchivesRecForm");
	if (c == null) {
		c = new ArchivesRecForm( {
			archivesId : d,
			archDistId : a,
			isSign : true
		});
		b.add(c);
	} else {
		b.remove("ArchivesRecForm");
		c = new ArchivesRecForm( {
			archivesId : d,
			archDistId : a,
			isSign : true
		});
		b.add(c);
	}
	b.activate(c);
};