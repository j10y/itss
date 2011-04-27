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

	getFormpanel_ERP_NormalNeed1_m_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_m_end",
					"panel_ERP_NormalNeed1_m_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed1_m_Input");
		}
		biddata = da.splitForReadOnly(data);
		this.formpanel_ERP_NormalNeed1_m_Input = new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed1_m_Input',
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
			title : "ERP常规服务申请",
			items : biddata
		});
		return this.formpanel_ERP_NormalNeed1_m_Input;
	},
	getFormpanel_ErpEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_m_end",
					"panel_ErpEngineerFeedback_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ErpEngineerFeedback_Input");
		}
		biddata = da.splitForReadOnly(data);

		this.formpanel_ErpEngineerFeedback_Input = new Ext.form.FormPanel({
			id : 'panel_ErpEngineerFeedback_Input',
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
			title : "ERP工程师反馈信息",
			items : biddata
		});
		return this.formpanel_ErpEngineerFeedback_Input;
	},
	getFormpanel_BASISEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_m_end",
					"panel_BASISEngineerFeedback_Input", this.dataId);// 这是要随时变得
		} else {
			data = da
					.getPanelElementsForAdd("panel_BASISEngineerFeedback_Input");
		}
		biddata = da.splitForReadOnly(data);
		this.formpanel_BASISEngineerFeedback_Input = new Ext.form.FormPanel({
			id : 'panel_BASISEngineerFeedback_Input',
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
			title : "BASIS工程师反馈",
			items : biddata
		});
		return this.formpanel_BASISEngineerFeedback_Input;
	},
	getFormpanel_EBEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_m_end",
					"panel_EBEngineerFeedback_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_EBEngineerFeedback_Input");
		}
		biddata = da.splitForReadOnly(data);
		this.formpanel_EBEngineerFeedback_Input = new Ext.form.FormPanel({
			id : 'panel_EBEngineerFeedback_Input',
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
			title : "EB工程师反馈",
			items : biddata
		});

		return this.formpanel_EBEngineerFeedback_Input;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.ERP_NormalNeed"
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
		this.model = "nr_m_end";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nr_m_end", this);
		//add by lee for add back button in 20091104 begin
		var backButton = new Ext.Button({
			text : '返回',
			//pressed : true,
			iconCls : 'back',
			scope : this,
			handler : function() {
				window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
			}
		});
		if(this.serviceItemId!=""){
			this.mybuttons.push(backButton);
		}
		//add by lee for add back button in 20091104 end
		if (this.mybuttons != "") {
			this.buttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				items : this.mybuttons
			};
		} else {
			this.buttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				}
			};
		}

		this.getFormpanel_ERP_NormalNeed1_m_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_m_Input);
		this.formname.push("panel_ERP_NormalNeed1_m_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_m_Input);
		this.getFormpanel_ErpEngineerFeedback_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		this.formname.push("panel_ErpEngineerFeedback_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_Input);
		this.getFormpanel_BASISEngineerFeedback_Input();
		this.pa.push(this.formpanel_BASISEngineerFeedback_Input);
		this.formname.push("panel_BASISEngineerFeedback_Input");
		temp.push(this.formpanel_BASISEngineerFeedback_Input);
		this.getFormpanel_EBEngineerFeedback_Input();
		this.pa.push(this.formpanel_EBEngineerFeedback_Input);
		this.formname.push("panel_EBEngineerFeedback_Input");
		temp.push(this.formpanel_EBEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})