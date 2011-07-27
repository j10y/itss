Ext.ns("NoticeNewView");
NoticeNewView = Ext.extend(Ext.Panel, {
	newsTypeTree : null,
	constructor : function(a) {
		Ext.applyIf(this, a);
		this.initUIComponents();
		NoticeNewView.superclass.constructor.call(this, {
			id : "NoticeNewView",
			title : "通知信息",
			iconCls : "menu-news",
			layout : "border",
			autoScroll : true
		});
	},
	initUIComponents : function() {
		this.newsTypeTree = new NoticeNewTypeTree();
		this.initGridPanel();
		this.items = [ this.newsTypeTree, this.gridPanel ];
	}
});
NoticeNewView.prototype.initGridPanel = function() {
	return this.gridPanel = new Ext.Panel(
			{
				region : "center",
				layout : "form",
				border : false,
				anchor : "100%",
				items : [ {
					items : [
							new Ext.FormPanel(
									{
										id : "NoticeNewSearchForm",
										layout : "column",
										height : 35,
										frame : false,
										border : false,
										layout : "hbox",
										layoutConfig : {
											padding : "5",
											align : "middle"
										},
										defaults : {
											style : "padding:0px 5px 0px 5px;",
											xtype : "label"
										},
										items : [
												{
													text : "请输入条件:"
												},
												{
													text : "标题"
												},
												{
													xtype : "textfield",
													name : "Q_subject_S_LK",
													width : 80
												},
												{
													text : "作者"
												},
												{
													xtype : "textfield",
													name : "Q_author_S_LK",
													width : 80
												},
												{
													xtype : "button",
													text : "查询",
													iconCls : "search",
													handler : function() {
														var a = Ext
																.getCmp("NoticeNewSearchForm");
														var b = Ext
																.getCmp("NoticeNewGrid");
														if (a.getForm()
																.isValid()) {
															a
																	.getForm()
																	.submit(
																			{
																				waitMsg : "正在提交查询",
																				url : __ctxPath
																						+ "/info/listNoticeNew.do",
																				success : function(
																						d,
																						e) {
																					var c = Ext.util.JSON
																							.decode(e.response.responseText);
																					b
																							.getStore()
																							.loadData(
																									c);
																				}
																			});
														}
													}
												},
												{
													xtype : "button",
													text : "重置",
													iconCls : "reset",
													handler : function() {
														var a = Ext
																.getCmp("NoticeNewSearchForm");
														a.getForm().reset();
													}
												} ]
									}), this.setup() ]
				} ]
			});
};
NoticeNewView.prototype.setup = function() {
	return this.grid();
};
NoticeNewView.prototype.grid = function() {
	var d = new Ext.grid.CheckboxSelectionModel();
	var a = new Ext.grid.ColumnModel(
			{
				columns : [
						d,
						new Ext.grid.RowNumberer(),
						{
							header : "newsId",
							dataIndex : "newsId",
							hidden : true
						},
						{
							header : "通知图标",
							width : 120,
							dataIndex : "subjectIcon",
							renderer : function(i, g, e, k, f) {
								var h = e.data.subjectIcon;
								var j = null;
								if (h != "") {
									j = '<img style="border:0;" width="48" height="48" src="'
											+ __ctxPath
											+ "/attachFiles/"
											+ h
											+ '" border="0"/>';
								} else {
									j = '<img style="border:0;" width="48" height="48" src="'
											+ __ctxPath
											+ '/images/default_newsIcon.jpg" border="0"/>';
								}
								return j;
							}
						},
						{
							header : "通知标题",
							width : 210,
							dataIndex : "subject"
						},
						{
							header : "作者",
							width : 120,
							dataIndex : "author"
						},
						{
							header : "创建时间",
							width : 210,
							dataIndex : "createtime",
							renderer : function(e) {
								return e.substring(0, 10);
							}
						},
						{
							header : "回复次数",
							width : 120,
							dataIndex : "replyCounts"
						},
						{
							header : "浏览数",
							width : 120,
							dataIndex : "viewCounts"
						},
						{
							header : "状态",
							width : 120,
							dataIndex : "status",
							renderer : function(e) {
								return e == 0 ? "禁用" : "生效";
							}
						},
						{
							header : "管理",
							dataIndex : "newsId",
							width : 210,
							renderer : function(h, g, e, k, f) {
								var j = e.data.newsId;
								var i = "";
								if (isGranted("_NoticeNewDel")) {
									i = '<button title="删除" value=" " class="btn-del" onclick="NoticeNewView.remove('
											+ j + ')">&nbsp</button>';
								}
								if (isGranted("_NoticeNewEdit")) {
									i += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="NoticeNewView.edit('
											+ j + ')">&nbsp</button>';
								}
								return i;
							}
						} ],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});
	var b = this.store();
	b.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	var c = new Ext.grid.GridPanel({
		id : "NoticeNewGrid",
		tbar : this.topbar(),
		store : b,
		trackMouseOver : true,
		disableSelection : false,
		autoScroll : true,
		loadMask : true,
		height : 445,
		sortable : false,
		cm : a,
		sm : d,
		viewConfig : {
			forceFit : true,
			enableRowBody : false,
			showPreview : false
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 25,
			store : b,
			displayInfo : true,
			displayMsg : "当前显示从{0}至{1}， 共{2}条记录",
			emptyMsg : "当前没有记录"
		})
	});
	c.addListener("rowdblclick", function(g, f, h) {
		g.getSelectionModel().each(function(e) {
			if (isGranted("_NoticeNewEdit")) {
				NoticeNewView.edit(e.data.newsId);
			}
		});
	});
	return c;
};
NoticeNewView.prototype.store = function() {
	var a = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : __ctxPath + "/info/listNoticeNew.do"
		}),
		reader : new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts",
			id : "id",
			fields : [ {
				name : "newsId",
				type : "int"
			}, "typeId", "subjectIcon", "subject", "author", "createtime",
					"replyCounts", "viewCounts", "content", "updateTime",
					"status" ]
		}),
		remoteSort : true
	});
	a.setDefaultSort("newsId", "desc");
	return a;
};
NoticeNewView.prototype.topbar = function() {
	var a = new Ext.Toolbar({
		id : "NoticeNewFootBar",
		height : 30,
		bodyStyle : "text-align:left",
		items : []
	});
	if (isGranted("_NoticeNewAdd")) {
		a.add(new Ext.Button({
			iconCls : "btn-add",
			text : "添加",
			handler : function() {
				new NoticeNewForm().show();
			}
		}));
	}
	if (isGranted("_NoticeNewDel")) {
		a.add(new Ext.Button({
			iconCls : "btn-del",
			text : "删除",
			handler : function() {
				var d = Ext.getCmp("NoticeNewGrid");
				var b = d.getSelectionModel().getSelections();
				if (b.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var e = Array();
				for ( var c = 0; c < b.length; c++) {
					e.push(b[c].data.newsId);
				}
				NoticeNewView.remove(e);
			}
		}));
	}
	return a;
};
NoticeNewView.remove = function(b) {
	var a = Ext.getCmp("NoticeNewGrid");
	Ext.Msg.confirm("信息确认",
			'删除通知,则通知下的<font color="red">评论</font>也删除,您确认要删除该记录吗？',
			function(c) {
				if (c == "yes") {
					Ext.Ajax.request({
						url : __ctxPath + "/info/multiDelNoticeNew.do",
						params : {
							ids : b
						},
						method : "post",
						success : function() {
							Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
							a.getStore().reload({
								params : {
									start : 0,
									limit : 25
								}
							});
						}
					});
				}
			});
};
NoticeNewView.edit = function(a) {
	new NoticeNewForm({
		newsId : a
	}).show();
};