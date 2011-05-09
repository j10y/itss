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

	getFormpanel_ERP_NormalNeed4_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nrf_end",
					"panel_ERP_NormalNeed4_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed4_Input");
		}
		
		biddata = da.splitForReadOnly(data);
		this.formpanel_ERP_NormalNeed4_Input = new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed4_Input',
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
		return this.formpanel_ERP_NormalNeed4_Input;
	},
	getFormpanel_ErpEngineerFeedback_f_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var modifyData = new Array();		
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nrf_end",
					"panel_ErpEngineerFeedback_f_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ErpEngineerFeedback_f_Input");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
		var otherInfovalue = Ext.getCmp('ERP_NormalNeed$otherInfo').getValue();
		var str=otherInfovalue.split("%");
			if(Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue()=='1' || str[0]=='100'){
				if(data[i].id == 'itil_sci_ErpEngineerFeedback$transferRequestNumber'){
					continue;
				}
			}			
			data[i].readOnly = true;
			modifyData.push(data[i]);
		}
		biddata = da.splitForReadOnly(modifyData);

		this.formpanel_ErpEngineerFeedback_f_Input = new Ext.form.FormPanel({
			id : 'panel_ErpEngineerFeedback_f_Input',
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
		return this.formpanel_ErpEngineerFeedback_f_Input;
	},
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 begin
//	getFormpanel_BASISEngineerFeedback_Input : function() {
//		var da = new DataAction();
//		var data = null;
//		// 判断是新增还是修改
//		var biddata = "";
//		if (this.dataId != '0') {
//			data = da.getPanelElementsForEdit("nrf_end",
//					"panel_BASISEngineerFeedback_Input", this.dataId);// 这是要随时变得
//		} else {
//			data = da
//					.getPanelElementsForAdd("panel_BASISEngineerFeedback_Input");
//		}
//		for (i = 0; i < data.length; i++) {
//			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
//				data[i].id = data[i].id + 8;//改变Combobox的id
//				data[i].readOnly = true;
//				data[i].hideTrigger = true;
//			}
//			data[i].readOnly = true;
//		}
//		biddata = da.split(data);
//		this.formpanel_BASISEngineerFeedback_Input = new Ext.form.FormPanel({
//			id : 'panel_BASISEngineerFeedback_Input',
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			frame : true,
//			collapsible : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "BASIS工程师反馈",
//			items : biddata
//		});
//		return this.formpanel_BASISEngineerFeedback_Input;
//	},
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 end
	getFormpanel_EBEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nrf_end",
					"panel_EBEngineerFeedback_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_EBEngineerFeedback_Input");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			data[i].readOnly = true;
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
		if(this.serviceItemId!=""){
				this.buttons=[ new Ext.Button({
					text : '返回',
					//pressed : true,
					iconCls : 'back',
					scope : this,					
					handler : function() {
						window.location = webContext
						+ "/requireAction_toRequireInfo.action?serviceItemId="
						+ this.serviceItemId;
					}
				})]
		}

		this.getFormpanel_ERP_NormalNeed4_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed4_Input);
		this.formname.push("panel_ERP_NormalNeed4_Input");
		temp.push(this.formpanel_ERP_NormalNeed4_Input);
		this.getFormpanel_ErpEngineerFeedback_f_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_f_Input);
		this.formname.push("panel_ErpEngineerFeedback_f_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_f_Input);
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 begin
//		this.getFormpanel_BASISEngineerFeedback_Input();
//		this.pa.push(this.formpanel_BASISEngineerFeedback_Input);
//		this.formname.push("panel_BASISEngineerFeedback_Input");
//		temp.push(this.formpanel_BASISEngineerFeedback_Input);
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 end
		this.getFormpanel_EBEngineerFeedback_Input();
		this.pa.push(this.formpanel_EBEngineerFeedback_Input);
		this.formname.push("panel_EBEngineerFeedback_Input");
		temp.push(this.formpanel_EBEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
//		items.push(this.buttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})