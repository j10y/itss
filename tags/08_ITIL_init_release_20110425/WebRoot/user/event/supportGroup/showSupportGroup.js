PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1 
	},
	getFormnewsupportgroup_pagepanel : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId !="") {
			data = da.getPanelElementsForEdit("newsupportgroup_pagemodel",
					"newsupportgroup_pagepanel", this.dataId);// 这是要随时变得
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("newsupportgroup_pagepanel");
			biddata = da.splitForReadOnly(data);
		}
		this.formnewsupportgroup_pagepanel = new Ext.form.FormPanel({
				id : 'newsupportgroup_pagepanel',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "支持组基本信息",
				items : biddata
			});
		return this.formnewsupportgroup_pagepanel;
	},
	getGridComSupportGroupEngineer_pagepanel : function() {
		this.store= new Ext.data.JsonStore({
			url : webContext
					+ '/supportGroupAction_findEngineersBySupportGroupId.action',
			root : "data",
			fields : ['id', 'userInfo']
		});

		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm=new Ext.grid.ColumnModel([this.sm,{
				header : '工程师名称',
				dataIndex : 'userInfo',
				width:400,
				sortable: true
			}]);
		this.gridComSupportGroupEngineer_pagepanel = new Ext.grid.EditorGridPanel({
			id : 'SupportGroupEngineer_pagepanel',
			store : this.store,
			cm : this.cm,
			sm : this.sm,
			title : "支持组工程师",
			trackMouseOver : false,
			loadMask : true,
			width : 800,
			height:250,
			frame : true
		});
		if (this.dataId !="") {
			this.store.load({
				params : {
					supportGroupId : this.dataId
				}
			});
		}
		return this.gridComSupportGroupEngineer_pagepanel;
	},
	
	getServiceItemsGridPanel : function() {
		this.store= new Ext.data.JsonStore({
			url : webContext+ '/supportGroupAction_findServiceItemBySupportGroup.action',
			root : "data",
			fields : ['id', 'name']
		});

		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm=new Ext.grid.ColumnModel([this.sm,{
				header : '服务项名称',
				dataIndex : 'name',
				width:400,
				sortable: true
			}]);
		this.serviceItemsGridPanel = new Ext.grid.EditorGridPanel({
			id : 'serviceItemsGridPanel',
			store : this.store,
			cm : this.cm,
			sm : this.sm,
			title : "支持组所选服务项",
			trackMouseOver : false,
			loadMask : true,
			width : 800,
			height:250,
			frame : true
		});
		if (this.dataId !="") {
			this.store.load({
				params : {
					supportGroupId : this.dataId,
					official : 1
				}
			});
		}
		return this.serviceItemsGridPanel;
	},
	items : this.items,
	initComponent : function() {
		var items = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var temp = new Array();
		this.temp = temp;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
		this.model = "newsupportgroup_pagemodel";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = {
			layout : 'table',
			height : 'auto',
			width : 700,
			style : 'margin:4px 6px 4px 350px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : [{
				id:"backButton",
				xtype : 'button',
				iconCls : "back",
				style : 'margin:4px 10px 4px 0',
				handler : function() {
					history.back();
				},
				text : '返回'
			}]
		};
		this.getFormnewsupportgroup_pagepanel();
		this.pa.push(this.formnewsupportgroup_pagepanel);
		this.formname.push("newsupportgroup_pagepanel");
		temp.push(this.formnewsupportgroup_pagepanel);
		this.getGridComSupportGroupEngineer_pagepanel();
		this.gd.push(this.gridComSupportGroupEngineer_pagepanel);
		this.gridname.push("SupportGroupEngineer_pagepanel");
		temp.push(this.gridComSupportGroupEngineer_pagepanel);
		var siGridPanel = this.getServiceItemsGridPanel();
		temp.push(siGridPanel);
		items = temp;
		items.push(this.mybuttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})