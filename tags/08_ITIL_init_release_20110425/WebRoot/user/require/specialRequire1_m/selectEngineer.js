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
		var dataId = this.dataId;
		var tkId = this.taskId;
		var formParam = Ext.getCmp('panel_SRServiceProviderInfo_Input').form
				.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		Ext.Ajax.request({
			url : webContext
					+ '/SRAction_saveSRServiceProviderInfo.action?reqId='
					+ dataId,
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var tempId = responseArray.id;
				var tempEngineerId = responseArray.engineerId;
				var tempOfficerId = responseArray.officerId;
				var tempUrl = "";
				if (tempEngineerId != "0") {
					tempUrl = webContext
							+ '/extjs/workflow?method=getData&taskId=' + tkId
							+ '&users=makeInfoByEngineer:' + tempEngineerId
							+ '$executeByEngineer:' + tempEngineerId
							+ '$issueByEngineer:' + tempEngineerId;
				} else {
					tempUrl = webContext
							+ '/extjs/workflow?method=getData&taskId=' + tkId
							+ '&users=selectEngineer:' + tempOfficerId;
				}
				Ext.getCmp('itil_SRServiceProviderInfo$id').setValue(tempId);
				Ext.Ajax.request({
					url : tempUrl,
					method : 'post',
					success : function(response, options) {
						window.parent.auditContentWin.audit();
					},
					failure : function(response, options) {
						Ext.getCmp('submitButton').enabled();
					}
				})
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

	getFormpanel_SpecialRequire3_m_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire3_m_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_m_selectEngineer",
					"panel_SpecialRequire3_m_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3_m_Input");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;// 改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
			data[i].readOnly = true;
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SpecialRequire3_m_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_m_Input',
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
				title : "用户申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SpecialRequire3_m_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_m_Input',
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
				title : "用户申请",
				items : biddata
			});
		}
		return this.formpanel_SpecialRequire3_m_Input;
	},

	getFormpanel_SRServiceProviderInfo_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRServiceProviderInfo_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_confirmByTechnicManager",
					"panel_SRServiceProviderInfo_Input", this.dataId);// 这是要随时变得

		} else {
			data = da.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");
			//biddata = da.split(data);
		}
		for (var i = 0; i < data.length; i++) {
			if (data[i].id == 'itil_SRServiceProviderInfo$bigTypeCombo' 
				|| data[i].id == 'itil_SRServiceProviderInfo$smallTypeCombo'
				|| data[i].id == 'itil_SRServiceProviderInfo$serviceProviderInCombo') {
					data[i].id = data[i].id + 8;// 改变Combobox的id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
			if (data[i].id == 'itil_SRServiceProviderInfo$mainEngineerCombo') {
				data[i].on('beforequery', function(queryEvent) {
					var pid = Ext
							.getCmp('itil_SRServiceProviderInfo$serviceProviderInCombo')
							.getValue();
					if (pid == "") {
						Ext.Msg.alert("提示", "请先选择交付团队!");
						return false;
					}
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.removeAll();
					this.store.load({
						params : {
							serviceProviderIn : pid,
							start : 0
						}
					});
					return false;
				})
			}
			if (data[i].id == 'itil_SRServiceProviderInfo$assistanEngineerCombo') {
				data[i].on('beforequery', function(queryEvent) {
					var pid = Ext
							.getCmp('itil_SRServiceProviderInfo$serviceProviderInCombo')
							.getValue();
					if (pid == "") {
						Ext.Msg.alert("提示", "请先选择交付团队!");
						return false;
					}
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.removeAll();
					this.store.load({
						params : {
							serviceProviderIn : pid,
							start : 0
						}
					});
					return false;
				})
			}
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRServiceProviderInfo_Input = new Ext.form.FormPanel({
				id : 'panel_SRServiceProviderInfo_Input',
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
				title : "技术总监审批面板",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRServiceProviderInfo_Input = new Ext.form.FormPanel({
				id : 'panel_SRServiceProviderInfo_Input',
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
				title : "技术总监审批面板",
				items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},
	getFormserviceItemBasePanel : function() {
		var sra = new SRAction();
		var siId = sra.getReqServiceItemId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"serviceItemBasePanel", this);

		if (siId != '0') {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					siId);// 这是要随时变得

		} else {
			data = da.getPanelElementsForAdd("serviceItemBasePanel");

		}

		for (i = 0; i < data.length; i++) {
			if (data[i].id == 'ServiceItem$serviceItemCode') {
				data[i].readOnly = true;
				data[i].emptyText = '自动生成';
			}

		}
		biddata = da.split(data);
		var item = new Array();
		// item=biddata;
		// item.push(this.caPanel);

		if (this.getFormButtons.length != 0) {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel3',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'

				},
				style : 'padding:10px 0 0px 0px',
				layoutConfig : {
					columns : 4
				},
				title : "<font color=red>服务项表单基础面板</font>",
				items : biddata

			});
		} else {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel3',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px',
					autoHeight : true
				},
				style : 'padding:10px 0 0px 0px',
				layoutConfig : {
					columns : 4
				},
				title : "服务项表单基础面板",
				items : biddata
			});
		}
		this.formserviceItemBasePanel2 = new Ext.Panel({
			id : 'serviceItemBasePanel',
			y : 200,
			anchor : '0 -200',
			layout : 'column',
			height : 510,
			width : 1200,
			title : "服务项表单基础面板",
			items : [this.formserviceItemBasePanel]
		});
		this.formserviceItemBasePanel.on("saveServiceItem",
				this.saveServiceItem, this);
		return this.formserviceItemBasePanel2;
	},

	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
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
		this.model = "nsr1_m_selectEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nsr1_m_selectEngineer",this);
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
		this.getFormpanel_SpecialRequire3_m_Input();
		this.pa.push(this.formpanel_SpecialRequire3_m_Input);
		this.formname.push("panel_SpecialRequire3_m_Input");
		temp.push(this.formpanel_SpecialRequire3_m_Input);

//		this.getFormserviceItemBasePanel();
//		this.pa.push(this.formserviceItemBasePanel2);
//		this.formname.push("serviceItemBasePanel");
//		temp.push(this.formserviceItemBasePanel2);
		this.getFormpanel_SRServiceProviderInfo_Input();
		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
		this.formname.push("panel_SRServiceProviderInfo_Input");
		temp.push(this.formpanel_SRServiceProviderInfo_Input);
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