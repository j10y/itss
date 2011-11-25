PostView = Ext.extend(Ext.Panel, {
	gridPanel : null,
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		PostView.superclass.constructor.call(this, {
			id : "PostView",
			layout : "column",
			items : this.gridPanel,
			title : "职务管理",
			renderTo : "mainDiv"
		});
	},
	initComponents : function() {
		var k = new Ext.data.Store({
			url : webContext + "/duty?methodCall=getList",
			reader : new Ext.data.JsonReader({
				root : "result",
				fields : [
					"dn",
					"cn",
					"title"
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
					header : "cn",
					dataIndex : "cn",
					width : 60
				}, {
					header : "名称",
					dataIndex : "title",
					width : 60
				}, {
					header : "管理",
					dataIndex : "dn",
					sortable : false,
					width : 60,
					renderer : function(r, q, o, u, p) {
						var s = "";
						s += '<a href="#" title="删除" onclick="PostView.del(\'' + o.data.dn + '\')">删除</a>';
						s += '&nbsp;<a href="#" title="修改" onclick="PostView.modify(\'' + o.data.dn + '\')">修改</button>';
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
			id : "PostPanel",
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
		new PostForm({
			isModify : false
		}).show();
	}
});
PostView.del = function(u) {
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
				url : webContext + "/duty?methodCall=delete",
				params : {
					dutyRDN : u
				},
				success : function(d) {
					var e = Ext.util.JSON.decode(d.responseText);
					Ext.getCmp("PostPanel").getStore().reload();
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
PostView.modify = function(u) {
	if(isSuperAdmin != "true") {
		Ext.MessageBox.show({
			title : "操作信息",
			msg : "您不在超级管理员组，没有该权限！",
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
		return ;
	}
	new PostForm({
		isModify : true,
		dutyRDN : u
	}).show();
};