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

	saveAndSubmit : function() {
		var needId = this.dataId;
		var tempisWM = Ext.getCmp('ERP_NormalNeed$isWMCombo').getValue();
		var tempWMInfo = Ext.getCmp('ERP_NormalNeed$includeAndExpend')
				.getValue();
		if (tempisWM == "") {
			Ext.MessageBox.alert("提示", "请选择是否WM！");
			return;
		}
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveWM.action?',
			params : {
				dataId : needId,
				isWM : tempisWM,
				wmInfo : tempWMInfo
			},
			success : function(response, options) {
				window.parent.auditContentWin.specialAudit();//modify by zhangzy for去掉拒绝单选钮 in 2009 11 26
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
			title : tabTitle,
			enableTabScroll : true,
			// minTabWidth:100,
			// resizeTabs : true,
			deferredRender : false,
			frame : true,
			// plain : true,
			autoScroll : true,
			border : true,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			// width : 800,
			// bodyBorder : true,
			// defaults : {
			// bodyStyle : 'padding:2px',
			// autoFill:true
			// },
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

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed3_m_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit(
					"nr_m_confirmByOperationManager2",
					"panel_ERP_NormalNeed3_m_Input", this.dataId);// 这是要随时变得
			for (i = 0; i < data.length; i++) {
				if ((data[i].id != 'ERP_NormalNeed$includeAndExpend')
						&& (data[i].id != 'ERP_NormalNeed$isWMCombo')) {
					if ((data[i].xtype == 'combo' || data[i].xtype == 'datefield')) {
						data[i].id = data[i].id + 8;// 改变Combobox的id
						data[i].disabled = true;
						data[i].hideTrigger = true;
					}
					if (data[i].xtype == "panel") {
						var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
						data[i] = Ext.decode(dd);
					}
					data[i].disabled = true;
					// data[i].readOnly = true;
				} else if (data[i].id == 'ERP_NormalNeed$isWMCombo') {
					data[i].allowBlank = false;
					data[i].on('select', function(combo, record, index) {
						var s = Ext.getCmp('ERP_NormalNeed$isWMCombo')
								.getValue();
						if (s == '1') {
							Ext.getCmp('ERP_NormalNeed$includeAndExpend')
									.enable();
						} else {
							Ext.getCmp('ERP_NormalNeed$includeAndExpend')
									.reset();
							Ext.getCmp('ERP_NormalNeed$includeAndExpend')
									.disable();
						}
					})
				} else if (data[i].id == 'ERP_NormalNeed$includeAndExpend') {
					data[i].disabled = true;
				}
			}

			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed3_m_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed3_m_Input',
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
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed3_m_Input',
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
		}
		return this.formpanel_ERP_NormalNeed1_Input;
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
		this.model = "nr_m_confirmByOperationManager2";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"nr_m_confirmByOperationManager2", this);
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

		this.getFormpanel_ERP_NormalNeed1_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		this.formname.push("panel_ERP_NormalNeed3_m_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if (this.readOnly != 1) {
			items.push(this.buttons);
		}
		this.items = items;
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})