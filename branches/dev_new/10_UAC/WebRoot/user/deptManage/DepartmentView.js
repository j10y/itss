DepartmentView = Ext.extend(Ext.Panel, {
	treePanel : null,
	gridPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		DepartmentView.superclass.constructor.call(this, {
			id : "DepartmentView",
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
						this.gridPanel
					]
				}
			],
			title : "组织架构管理",
			renderTo : "mainDiv"
		});
	},
	initComponents : function() {
		var e;
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
			id : "deptTreePanel",
			title : "部门信息显示",
			collapsible : true,
			split : true,
			height : 800,
			border : false,
			loader : loader,
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
				"click" : DepartmentView.clickNode
			}
		});
		this.treePanel.on("contextmenu", b, this.treePanel);
		var h = new Ext.menu.Menu({
			id : "DepartmentTreeMenu",
			items : []
		});
		h.add({
			text : "新建子部门",
			iconCls : "btn-add",
			scope : this,
			handler : create
		});
		h.add({
			text : "修改部门信息",
			iconCls : "btn-edit",
			scope : this,
			handler : modify
		});
		h.add({
			text : "删除部门",
			iconCls : "btn-delete",
			scope : this,
			handler : del
		});
		function b(n, o) {
			e = new Ext.tree.TreeNode({
				id : n.id,
				text : n.text
			});
			h.showAt(o.getXY());
		}
		function create() {
			if(isSuperAdmin != "true") {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "您不在超级管理员组，没有该权限！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
			var n = e.id;
			new DepartmentForm({
				isModify:false,
				parentRDN : n,
				parentDeptName : e.text
			}).show();
		}
		function modify() {
			if(isSuperAdmin != "true") {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "您不在超级管理员组，没有该权限！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
			var n = e.id;
			new DepartmentForm({
				deptRDN : n,
				isModify : true
			}).show();
		}
		function del() {
			if(isSuperAdmin != "true") {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : "您不在超级管理员组，没有该权限！",
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
				return ;
			}
			Ext.Msg.confirm("系统提示", "确定要删除该部门吗？", function(btn) {
				if(btn == "yes") {
					var n = e.id;
					Ext.Ajax.request({
						url : __ctxPath + "/dept?methodCall=delete",
						params : {
							deptRDN : n
						},
						success : function(d) {
							var e = Ext.util.JSON.decode(d.responseText);
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
		}
		//右侧人员列表
		var k = new Ext.data.Store({
			url : webContext + "/user?methodCall=getList&queryType=all",
			reader : new Ext.data.JsonReader({
				root : "result",
				id : "dn",
				fields : [
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
		var l = new Ext.grid.ColumnModel({
			columns : [
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
				}
			],
			defaults : {
				sortable : true,
				menuDisable : true,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "deptUserList",
			autoHeight : true,
			border : false,
			title : "员工基本信息",
			store : k,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : l,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
	}
});
DepartmentView.clickNode = function(b) {
	if(b != null) {
		var c = Ext.getCmp("deptUserList");
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