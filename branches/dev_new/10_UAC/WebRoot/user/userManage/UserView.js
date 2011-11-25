UserView = Ext.extend(Ext.Panel, {
	treePanel : null,
	searchPanel : null,
	gridPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		UserView.superclass.constructor.call(this, {
			id : "UserView",
			layout : "column",
			items : [
				{
					columnWidth : 0.2,
					items : [
						this.treePanel
					]
				}, {
					columnWidth : 0.8,
					items : [
						new Ext.Panel({
							width : "100%",
							border : false,
							items : [
								this.searchPanel,
								this.gridPanel
							]
						})
					]
				}
			],
			title : "人员架构管理",
			renderTo : "mainDiv"
		});
	},
	initComponents : function() {
		//左侧部门列表
		var loader = new Ext.tree.TreeLoader({
			url : webContext + '/menu/loadTree?methodCall=childLevel'
		});
		loader.on('beforeload', function(treeloader, node) {
			treeloader.baseParams = {
				parentDN : node.id,
				method : 'tree'
			};
		});
		this.treePanel = new Ext.tree.TreePanel({
			id : "userTreePanel",
			title : "部门信息显示",
			collapsible : true,
			split : true,
			height : 800,
			loader : loader,
			border : false,
			/*
			tbar : new Ext.Toolbar({
				items : [
					{
						xtype : "button",
						text : "刷新",
						handler : function() {
							this.treePanel.root.reload();
						}
					}, {
						xtype : "button",
						text : "展开",
						handler : function() {
							this.treePanel.expandAll();
						}
					}, {
						xtype : "button",
						text : "收起",
						handler : function() {
							this.treePanel.collapseAll();
						}
					}
				]
			}),
			*/
			root : new Ext.tree.AsyncTreeNode({
				id : "ou=orgnizations",
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				"click" : UserView.clickNode
			}
		});
		//右侧人员列表
		this.searchPanel = new Ext.FormPanel({
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
			items : [
				{
					text : "查询条件："
				}, {
					fieldLabel : "账号/姓名",
					id : "uidName",
					name : "uidName",
					xtype : "textfield",
					emptyText : "账号/姓名"
				}, {
					text : "最大结果数"
				}, {
					fieldLabel : "最大结果数",
					name : "maxSize",
					xtype : "textfield",
					value : "20",
					allowBlank : false
				}, {
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}
			]
		});
		var k = new Ext.data.Store({
			url : webContext + "/user?methodCall=getList",
			reader : new Ext.data.JsonReader({
				root : "result",
				fields : [
					"dn",
					"uid",
					"displayName",
					"title",
					"deptName",
					"userType",
					"status"
				]
			}),
			remoteSort : true
		});
		k.load();
		var d = new Ext.grid.CheckboxSelectionModel();
		var l = new Ext.grid.ColumnModel({
			columns : [
				d,
				new Ext.grid.RowNumberer(),
				{
					header : "dn",
					dataIndex : "dn",
					hidden : true
				}, {
					header : "账号",
					dataIndex : "uid",
					width : 60
				}, {
					header : "姓名",
					dataIndex : "displayName",
					width : 60
				}, {
					header : "所属部门",
					dataIndex : "deptName",
					width : 60
				}, {
					header : "职位",
					dataIndex : "title",
					width : 60
				}, {
					header : "状态",
					dataIndex : "status",
					width : 60,
					renderer : function(c) {
						if(c == "0" || c == "") {
							return "<font color='green'>正常</font>";
						}
						if(c == "1") {
							return "<font color='red'>锁定</font>";
						}
						if(c == "2") {
							return "<font color='red'>未启用</font>";
						}
						if(c == "3") {
							return "<font color='red'>注销</font>";
						}
					}
				}, {
					header : "管理",
					dataIndex : "dn",
					sortable : false,
					width : 60,
					renderer : function(r, q, o, u, p) {
						var t = o.data.uid;
						var s = "";
						s += '<a href="#" title="删除" onclick="UserView.del(\'' + o.data.dn + '\')">删除</a>';
						s += '&nbsp;<a href="#" title="修改" onclick="UserView.modify(\'' + t + '\',\'' + o.data.dn + '\')">修改</button>';
						return s;
					}
				}
			],
			defaults : {
				sortable : true,
				menuDisable : true,
				width : 100
			}
		});
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : "text-align:left",
			items : []
		});
		this.topbar.add(new Ext.Button({
			text : "新建",
			handler : this.create
		}));
		this.gridPanel = new Ext.grid.GridPanel({
			id : "UserList",
			autoHeight : true,
			border : false,
			store : k,
			tbar : this.topbar,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : l,
			sm : d,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : webContext + "/user?methodCall=getList",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	},
	create : function() {
		if(isSuperAdmin != "true") {
			Ext.MessageBox.show({
				title : "操作信息",
				msg : "您不在超级管理员组，没有该权限！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
			return ;
		}
		new UserForm({
			isModify : false,
			userRDN : ""
		}).show();
	}
});
UserView.clickNode = function(b) {
	if(b != null) {
		var c = Ext.getCmp("UserList");
		var a = c.getStore();
		a.url = webContext + "/user";
		a.baseParams = {
			methodCall : "getList",
			deptRDN : b.id
		};
		a.params = {
			methodCall : "getList",
			deptRDN : b.id
		};
		a.reload({
			params : {
				methodCall : "getList",
				deptRDN : b.id
			}
		});
	}
};
UserView.del = function(u) {
	if(isSuperAdmin != "true") {
		Ext.MessageBox.show({
			title : "操作信息",
			msg : "您不在超级管理员组，没有该权限！",
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
		return ;
	}
	Ext.Msg.confirm("系统提示", "确定要删除该员工吗？", function(btn) {
		if(btn == "yes") {
			Ext.Ajax.request({
				url : webContext + "/user?methodCall=delete",
				params : {
					userRDN : u
				},
				success : function(d) {
					var e = Ext.util.JSON.decode(d.responseText);
					Ext.getCmp("UserList").getStore().reload();
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "删除成功！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.INFO
					});
				},
				failure : function() {
					Ext.MessageBox.show({
						title : "操作信息",
						msg : "删除失败，请联系管理员！",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
			});
		}
		return ;
	}, this);
};
UserView.modify = function(u, ur) {
	if(isSuperAdmin != "true") {
		Ext.MessageBox.show({
			title : "操作信息",
			msg : "您不在超级管理员组，没有创该限！",
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
		return ;
	}
	new UserForm({
		uid : u,
		isModify : true,
		userRDN : ur
	}).show();
};