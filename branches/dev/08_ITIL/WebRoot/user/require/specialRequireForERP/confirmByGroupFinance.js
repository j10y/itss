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
		if(!Ext.getCmp('panel_erpSR_finance_input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		var firstId = Ext.getCmp('SpecialRequirement$financeTypeCombo').getValue();
		var secId = Ext.getCmp('SpecialRequirement$batchTypeCombo').getValue();
		if (firstId == "") {
			Ext.Msg.alert("提示", "请选择财务分类!");
			return;
		}
		if (firstId == '2' && secId == "") {
			Ext.Msg.alert("提示", "请选择批处理分类!");
			return;
		}
		var formParam = Ext.encode(getFormParam('panel_erpSR_finance_input'));
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
		var tabPanel = new Ext.TabPanel({
			xtype : Ext.id(),
			activeTab : 0,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return tabPanel;

	},
	getPanel : function(appa, panelTitle) {
		var Panel = new Ext.Panel({
			id : Ext.id(),
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
		return Panel;

	},

	getFormpanel_erpSR_finance_input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_erpSR_finance_input", this);
		
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_confirmByGroupFinance",
					"panel_erpSR_finance_input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_erpSR_finance_input");
		}
		for (var i = 0; i < data.length; i++) {
//			if(data[i].id!='SpecialRequirement$financeTypeCombo'&&data[i].id!='SpecialRequirement$batchTypeCombo'){
//				data[i].readOnly = true;
//				data[i].hideTrigger = true;
//				if (data[i].xtype == "panel") {
//					var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
//					data[i] = Ext.decode(dd);
//				}
//			}
			
		}
		biddata = da.split(data);
		
		if (this.getFormButtons.length != 0) {
			var formpanel_erpSR_finance_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_finance_input',
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
				title : "ERP非常规需求",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			var formpanel_erpSR_finance_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_finance_input',
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
				title : "ERP非常规需求",
				items : biddata
			});
		}
		return formpanel_erpSR_finance_input;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
		var items = new Array();

		var temp = new Array();
		this.temp = temp;
		this.model = "esr_confirmByGroupFinance";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"esr_confirmByGroupFinance", this);	
		this.buttons = new Array();
		if(this.readOnly!=1){
			this.buttons = this.mybuttons;
		}
		temp.push(this.getFormpanel_erpSR_finance_input());
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		this.items = items;
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
		
		Ext.getCmp('SpecialRequirement$financeTypeCombo').on('select',function(){
			var s = Ext.getCmp('SpecialRequirement$financeTypeCombo').getValue();
			if (s == '2') {
				Ext.getCmp('SpecialRequirement$batchTypeCombo').enable();
			} else {
				Ext.getCmp('SpecialRequirement$batchTypeCombo').clearValue();
				Ext.getCmp('SpecialRequirement$batchTypeCombo').disable();
			}
		},this);
		
	}
})