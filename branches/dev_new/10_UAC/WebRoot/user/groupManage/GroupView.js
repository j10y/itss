GroupView = Ext.extend(Ext.Panel, {
	gridPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		GroupView.superclass.constructor.call(this, {
			id : "GroupView",
			layout : "column",
			items : this.gridPanel,
			title : "组管理",
			renderTo : "mainDiv"
		});
	},
	initComponents : function() {
		var k = new Ext.data.Store({
			url : webContext + "/group?methodCall=getList",
			reader : new Ext.data.JsonReader({
				root : "result",
				fields : [
					"rdn",
					"cn",
					"displayName"
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
					header : "rdn",
					dataIndex : "rdn",
					hidden : true
				}, {
					header : "cn",
					dataIndex : "cn"
				}, {
					header : "名称",
					dataIndex : "displayName"
				}, {
					header : "管理",
					dataIndex : "dn",
					sortable : false,
					width : 60,
					renderer : function(r, q, o, u, p) {
						var t = o.data.rdn;
						var s = "";
						s += '<a href="#" title="删除" onclick="GroupView.del(\'' + o.data.rdn + '\')">删除</a>';
						s += '&nbsp;<a href="#" title="修改" onclick="GroupView.modify(\'' + t + '\')">修改</button>';
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
			id : "GroupPanel",
			autoHeight : true,
			border : false,
			store : k,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			tbar : this.topbar,
			cm : l,
			sm : d,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		});
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
		new GroupForm({
			isModify : false
		}).show();
	}
});
GroupView.del = function(u) {
	if(isSuperAdmin != "true") {
		Ext.MessageBox.show({
			title : "操作信息",
			msg : "您不在超级管理员组，没有该权限！",
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
		return ;
	}
	Ext.Msg.confirm("系统提示", "确定要删除该组吗？", function(btn) {
		if(btn == "yes") {
			Ext.Ajax.request({
				url : webContext + "/group?methodCall=delete",
				params : {
					groupRDN : u
				},
				success : function(d) {
					var e = Ext.util.JSON.decode(d.responseText);
					Ext.getCmp("GroupPanel").getStore().reload();
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
GroupView.modify = function(u) {
	if(isSuperAdmin != "true") {
		Ext.MessageBox.show({
			title : "操作信息",
			msg : "您不在超级管理员组，没有该权限！",
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
		return ;
	}
	new GroupForm({
		isModify : true,
		groupRDN : u
	}).show();
};