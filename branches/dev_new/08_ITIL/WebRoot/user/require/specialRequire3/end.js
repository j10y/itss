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
// addy by zhangzy for 售前申请改为一个实体 in 2009 12 9 start	
	getFormpanel_SpecialRequire3Confirm_Input : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr3_executeByEngineer",
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
	//add by musicbear for add back button in 20091119 begin
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
		this.mybuttons = new Array();
		this.mybuttons.push(backButton);
	//add by musicbear for add back button in 20091119 end	
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
		temp.push(this.getFormpanel_SpecialRequire3Confirm_Input());		
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		items.push(this.buttons);
		PageTemplates.superclass.initComponent.call(this);
	}
})