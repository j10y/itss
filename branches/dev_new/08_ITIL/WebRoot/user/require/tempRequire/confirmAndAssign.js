

PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	buttonAlign : 'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			//minTabWidth:100,
			//resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			//tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			//bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa, panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title : panelTitle,
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	},

	getFormpanel_tempRequire : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_tempRequire", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("page_tempRequireAudit","panel_tempRequire", this.dataId);// 这是要随时变得
			for (i = 0; i < data.length; i++) {
				var idStr = data[i].id + "";
				if (idStr.indexOf('$confirmUserCombo') > 0) {
					delete data[i].fieldLabel;
					data[i].fieldLabel = '部门审批人';
					
				}
			}
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("panel_tempRequire");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_tempRequire = new Ext.form.FormPanel({
				id : 'panel_tempRequire',
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
				title : "需求申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_tempRequire = new Ext.form.FormPanel({
				id : 'panel_tempRequire',
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
				title : "需求申请",
				items : biddata//,
//				buttonAlign:'center',
//				buttons : [{
//					text : '同意',
//					iconCls : 'submit',
//					handler : function() {
//				               //添加用户
//				               var tempUrl = webContext
//								+ '/extjs/workflow?method=getData&taskId=' + taskId
//								+ '&users=process:' + currentUserId;
//				               Ext.Ajax.request({
//									url : tempUrl,
//									method : 'post',
//									success : function(response, options) {
//										window.parent.auditContentWin.specialAudit();
//									},
//									failure : function(response, options) {
//										alert("指派节点审批人失败！");
//									}
//								})
//				               
//							}
//				} ,
//				{                  	
//					text : '拒绝',
//					iconCls : 'back',
//					handler : function() {
//						window.parent.auditContentWin.specialNoAudit();
//				  }
//				}]
			});
		}
		return this.formpanel_tempRequire;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});

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
		this.model = "page_tempRequireAudit";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_tempRequireAudit", this);
		//this.buttons = this.mybuttons;
		this.buttons=[{
			text : '同意',
			iconCls : 'submit',
			handler : function() {
		               //添加用户
		               var tempUrl = webContext
						+ '/extjs/workflow?method=getData&taskId=' + taskId
						+ '&users=process:' + currentUserId;
		               Ext.Ajax.request({
							url : tempUrl,
							method : 'post',
							success : function(response, options) {
								window.parent.auditContentWin.specialAudit();
							},
							failure : function(response, options) {
								alert("指派节点审批人失败！");
							}
						})
		               
					}
		} ,
		{                  	
			text : '拒绝',
			iconCls : 'back',
			handler : function() {
				window.parent.auditContentWin.specialNoAudit();
		  }
		}];
	
		this.getFormpanel_tempRequire();
		this.pa.push(this.formpanel_tempRequire);
		this.formname.push("panel_tempRequire");
		temp.push(this.formpanel_tempRequire);

		temp.push(histroyForm);

		items.push(this.getTabpanel(temp));
		if (this.readOnly != 1) {
			items.push(this.buttons);
		}
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})