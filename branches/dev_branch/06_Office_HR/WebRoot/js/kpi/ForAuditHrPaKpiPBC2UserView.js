ForAuditHrPaKpiPBC2UserView = Ext.extend(Ext.Panel, {
	constructor : function(a) {
		if(a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		ForAuditHrPaKpiPBC2UserView.superclass.constructor.call(this, {
			id : "ForAuditHrPaKpiPBC2UserView",
			title : "待审核个人PBC",
			region : "center",
			autoScroll : true,
			layout : "border",
			items : [
				this.searchPanel,
				this.gridPanel
			]
		});
	},
	searchPanel : null,
	gridPanel : null,
	store : null,
	initComponents : function() {
		var a = __ctxPath + "/system/listDepartment.do?opt=appUser";
		var departments = new TreeSelector("depName", a, "所属部门", "depId");
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
			items : [
				{
					text : "查询条件：姓名"
				}, {
					fieldLabel : "姓名",
					name : "fullname",
					xtype : "textfield"
				}, {
					text : "所属部门"
				},
				departments,
				{
					xtype : "button",
					text : "查询",
					handler : this.search.createCallback(this)
				}, {
					name : "depId",
					id : "depId",
					xtype : "hidden"
				}
			]
		});
		this.store = new Ext.data.JsonStore({
			url : __ctxPath + "/kpi/listForAuditHrPaKpiPBC2User.do",
			totalProperty : "totalCounts",
			id : "id",
			root : "result",
			remoteSort : true,
			fields : [
				{
					name : "id",
					type : "int"
				},
				"fullname",
				"pbcName",
				"totalScore",
				"content"
			]
		});
		this.store.setDefaultSort("id", "desc");
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		var d = new Ext.ux.grid.RowExpander({
			tpl : new Ext.Template('<div style="padding:5px 5px 5px 62px;">{content}</div>')
		});
		var a = new Ext.grid.ColumnModel({
			columns : [
				new Ext.grid.RowNumberer(),
				d,
				{
					header : "id",
					dataIndex : "id",
					hidden : true
				}, {
					header : "姓名",
					dataIndex : "fullname"
				}, {
					header : "个人考核模板名称",
					dataIndex : "pbcName"
				}, {
					header : "总分",
					dataIndex : "totalScore"
				}
			],
			defaults : {
				sortable : true,
				menuDisable : false,
				width : 100
			}
		});
		this.gridPanel = new Ext.grid.GridPanel({
			id : "ForAuditHrPaKpiPBC2UserGrid",
			region : "center",
			autoWidth : true,
			autoHeight : true,
			stripeRows : true,
			closeable : true,
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : a,
			plugins : d,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : "当前显示{0}至{1}，共{2}条记录",
				emptyMsg : "当前没有记录"
			})
		});
	},
	search : function(a) {
		if(a.searchPanel.getForm().isValid()) {
			a.searchPanel.getForm().submit({
				waitMsg : "正在提交查询……",
				url : __ctxPath + "/kpi/listForAuditHrPaKpiPBC2User.do",
				success : function(c, d) {
					var e = Ext.util.JSON.decode(d.response.responseText);
					a.gridPanel.getStore().loadData(e);
				}
			});
		}
	}
});