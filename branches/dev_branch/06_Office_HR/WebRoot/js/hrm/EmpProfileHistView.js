Ext.ns("EmpProfileView");
EmpProfileHistView = Ext.extend(Ext.Panel, {
			constructor : function (a) {
				if (a == null) {
					a = {};
				}
				Ext.apply(this, a);
				this.initComponents();
				EmpProfileHistView.superclass.constructor.call(this, {
						id : "EmpProfileHistView",
						title : "档案历史查询",
						iconCls : "menu-profile",
						region : "center",
						layout : "border",
						items : [this.searchPanel, this.gridPanel]
					});
			},
			typeId : null,
			searchPanel : null,
			gridPanel : null,
			store : null,
			topbar : null,
			initComponents : function () {
				this.searchPanel = new Ext.FormPanel({
							region : "north",
							height : 40,
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
							items : [{
									text : "查询条件：档案编号："
								}, {
									name : "Q_profileNo_S_LK",
									width : 80,
									xtype : "textfield"
								}, {
									text : "员工姓名："
								}, {
									name : "Q_fullname_S_LK",
									width : 80,
									xtype : "textfield"
								}, {
									text : "身份证号："
								}, {
									width : 80,
									name : "Q_idCard_S_LK",
									xtype : "textfield"
								}, {
									xtype : "button",
									text : "查询",
									iconCls : "search",
									handler : this.search.createCallback(this)
								}
							]
						});
				this.store = new Ext.data.JsonStore({
							url : __ctxPath + "/hrm/listEmpProfileHist.do",
							root : "result",
							totalProperty : "totalCounts",
							remoteSort : true,
							fields : [{
									name : "profileHistId",
									type : "int"
								},
								"profileNo",
								"fullname",
								"designation",
								"creator",
								"createtime",
								"modifiedDate",
								"memo",
								"depName",
								{
									name : "modifiedUser.fullname",
									mapping : "modifiedUser.fullname"
								}
							]
						});
				this.store.setDefaultSort("modifiedDate", "desc");
				this.store.load({
						params : {
							start : 0,
							limit : 25
						}
					});
				var b = new Ext.grid.CheckboxSelectionModel();
				var a = new Ext.grid.ColumnModel({
							columns : [b, new Ext.grid.RowNumberer(), {
									header : "profileHistId",
									dataIndex : "profileHistId",
									hidden : true
								}, {
									header : "档案编号",
									dataIndex : "profileNo"
								}, {
									header : "员工姓名",
									dataIndex : "fullname"
								}, {
									header : "建档人",
									dataIndex : "creator"
								}, {
									header : "建档时间",
									dataIndex : "createtime",
									renderer : function (c) {
										return c.substring(0, 10);
									}
								}, {
									header : "最后修改人",
									dataIndex : "modifiedUser.fullname"
								}, {
									header : "最后修改时间",
									dataIndex : "modifiedDate",
									renderer : function (c) {
										return c.substring(0, 10);
									}
								}, {
									header : "部门或公司",
									dataIndex : "depName"
								}, {
									header : "管理",
									dataIndex : "profileId",
									width : 100,
									sortable : false,
									renderer : function (g, f, d, j, e) {
										var i = d.data.profileHistId;
										var c = d.data.approvalStatus;
										var h = "";
										h += '<button title="查看" value=" " class="btn-readdocument" onclick="EmpProfileHistView.look(' + i + ')">&nbsp;&nbsp;</button>';
										return h;
									}
								}
							],
							defaults : {
								sortable : true,
								menuDisabled : false,
								width : 100
							}
						});
				this.topbar = new Ext.Toolbar({
							height : 30,
							bodyStyle : "text-align:left",
							items : []
						});
				this.gridPanel = new Ext.grid.GridPanel({
							id : "EmpProfileGrid",
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
							bbar : new Ext.PagingToolbar({
									pageSize : 25,
									store : this.store,
									displayInfo : true,
									displayMsg : "当前页记录索引{0}-{1}， 共{2}条记录",
									emptyMsg : "当前没有记录"
								})
						});
				this.gridPanel.addListener("rowdblclick", function (d, c, f) {
						d.getSelectionModel().each(function (e) {
							var g = e.data.profileHistId;
							EmpProfileHistView.look(g);
						});
					});
			},
			search : function (a) {
				if (a.searchPanel.getForm().isValid()) {
					a.searchPanel.getForm().submit({
							waitMsg : "正在提交查询",
							url : __ctxPath + "/hrm/listEmpProfileHist.do",
							success : function (c, d) {
								var b = Ext.util.JSON.decode(d.response.responseText);
								a.gridPanel.getStore().loadData(b);
							}
						});
				}
			}
		});
EmpProfileHistView.look = function (b) {
	new CheckEmpProfileHistForm({
		profileHistId : b
	}).show();
};
 