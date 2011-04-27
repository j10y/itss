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
	saveAndSubmit : function() {
		if (!Ext.getCmp('panel_SpecialRequire3Confirm_Input').form.isValid()) {
			Ext.MessageBox.alert("提示", "页面中带红色波浪线的为必填项,请填写完整必填项.");
			return false;
		}

		var formParam = Ext.encode(getFormParam('panel_SpecialRequire3Confirm_Input',false));
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveSpecialRequire.action',
			params : {
				info : formParam
			},					
			success : function(response, options) {
					window.parent.auditContentWin.specialAudit();
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
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

//	getFormpanel_specialRequire3_start : function() {
//		var da = new DataAction();
//		var data = null;
//		// 判断是新增还是修改
//		var biddata = "";
//
//		var buttonUtil = new ButtonUtil();
//		this.getFormButtons = buttonUtil.getButtonForPanel(
//				"panel_specialRequire3_start", this);
//		if (this.dataId != '0') {
//			data = da.getPanelElementsForEdit("sr3_shareConfirm",
//					"panel_specialRequire3_start", this.dataId);// 这是要随时变得
//		} else {
//			data = da.getPanelElementsForAdd("panel_specialRequire3_start");
//		}
//		biddata = da.splitForReadOnly(data);		
//		if (this.getFormButtons.length != 0) {
//			this.formpanel_specialRequire3_start = new Ext.form.FormPanel({
//				id : 'panel_specialRequire3_start',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "售前支持申请面板",
//				items : biddata,
//				buttons : this.getFormButtons
//			});
//		} else {
//			this.formpanel_specialRequire3_start = new Ext.form.FormPanel({
//				id : 'panel_specialRequire3_start',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "售前支持申请面板",
//				items : biddata
//			});
//		}
//		return this.formpanel_specialRequire3_start;
//	},
//	getFormpanel_SREngineerAndShare : function() {
//		var da = new DataAction();
//		var data = null;
//		// 判断是新增还是修改
//		var biddata = "";
//
//		var buttonUtil = new ButtonUtil();
//		this.getFormButtons = buttonUtil.getButtonForPanel(
//				"panel_SREngineerAndShare", this);
//		if (this.dataId != '0') {
//			data = da.getPanelElementsForEdit("sr3_shareConfirm",
//					"panel_SREngineerAndShare", this.dataId);// 这是要随时变得
//
//		} else {
//			data = da.getPanelElementsForAdd("panel_SREngineerAndShare");
//
//		}
//		biddata = da.splitForReadOnly(data);		
//		if (this.getFormButtons.length != 0) {
//			this.formpanel_SREngineerAndShare = new Ext.form.FormPanel({
//				id : 'panel_SREngineerAndShare',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "售前支持选择工程师分摊金额信息",
//				items : biddata,
//				buttons : this.getFormButtons
//			});
//		} else {
//			this.formpanel_SREngineerAndShare = new Ext.form.FormPanel({
//				id : 'panel_SREngineerAndShare',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "售前支持选择工程师分摊金额信息",
//				items : biddata
//			});
//		}
//		return this.formpanel_SREngineerAndShare;
//	},
//	getFormpanel_srInfo_input5 : function() {
//		var da = new DataAction();
//		var data = null;
//		// 判断是新增还是修改
//		var biddata = "";
//
//		var buttonUtil = new ButtonUtil();
//		this.getFormButtons = buttonUtil.getButtonForPanel(
//				"panel_srInfo_input5", this);
//		if (this.dataId != '0') {
//			data = da.getPanelElementsForEdit("sr3_executeByEngineer",
//					"panel_srInfo_input5", this.dataId);// 这是要随时变得			
//		} else {
//			data = da.getPanelElementsForAdd("panel_srInfo_input5");			
//		}
//		biddata = da.splitForReadOnly(data);
//		if (this.getFormButtons.length != 0) {
//			this.formpanel_srInfo_input5 = new Ext.form.FormPanel({
//				id : 'panel_srInfo_input5',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "售前支持交付经理实施信息",
//				items : biddata,
//				buttons : this.getFormButtons
//			});
//		} else {
//			this.formpanel_srInfo_input5 = new Ext.form.FormPanel({
//				id : 'panel_srInfo_input5',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "售前支持交付经理实施信息",
//				items : biddata
//			});
//		}
//		return this.formpanel_srInfo_input5;
//	},
// addy by zhangzy for 售前申请改为一个实体 in 2009 12 9 start	
	getFormpanel_SpecialRequire3Confirm_Input : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr3_shareConfirm",
					"panel_SpecialRequire3Confirm_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3Confirm_Input");
		}
		for(var i = 0; i < data.length; i++){
			var idStr = data[i].id + "";
			if (idStr=='SpecialRequirement$appManagerCombo') {
					data[i].fieldLabel = '实施顾问';
			}			
			if (idStr=='SpecialRequirement$includeAndExpend') {
					data[i].fieldLabel = '顾问填写实施报告';
			}
			if (idStr=='SpecialRequirement$otherInfo') {
					data[i].fieldLabel = '费用分摊成本中心';
			}				
		}		
		biddata = da.splitForReadOnly(data);
		var formpanel_SpecialRequire3Confirm_Input = new Ext.form.FormPanel({
			id : 'panel_SpecialRequire3Confirm_Input',
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
			title : "售前支持需求审批面板",
			items : biddata
		});
		return formpanel_SpecialRequire3Confirm_Input;
	},	
	items : this.items,
	buttons : this.buttons,
// addy by zhangzy for 售前申请改为一个实体 in 2009 12 9 end	
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {		
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});		
		var items = new Array();
		var temp = new Array();
		this.model = "sr3_shareConfirm";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("sr3_shareConfirm",this);
		if(this.readOnly!=1){
			this.buttons = this.mybuttons;
		}	
		temp.push(this.getFormpanel_SpecialRequire3Confirm_Input());
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})