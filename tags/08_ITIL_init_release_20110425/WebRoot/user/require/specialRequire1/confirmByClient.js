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

	getFormpanel_SpecialRequire3_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire3_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_confirmByClientManager",
					"panel_SpecialRequire3_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3_Input");

		}
		biddata = da.splitForReadOnly(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
		return this.formpanel_SpecialRequire3_Input;
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
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					"");
		}
		biddata = da.splitForReadOnly(data);
		if (this.getFormButtons.length != 0) {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
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
				title : "服务项表单基础面板",
				items : biddata
					//,
					//buttons : this.getFormButtons
			});
		} else {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
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
				title : "服务项表单基础面板",
				items : biddata
			});
		}
		return this.formserviceItemBasePanel;
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
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SRServiceProviderInfo_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");
		}
		biddata = da.splitForReadOnly(data);
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
	getGridpanel_SRIncomePlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRIncomePlan_list");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel(
				"panel_SRIncomePlan_list", this);

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// 给每一行增加修饰
			var columnItem = "";
			if (editor.xtype == 'combo') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else if (editor.xtype == 'datefield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRIncomePlan_list", obj);
		this.cm.defaultSortable = true;
		// var viewConfig = Ext.apply({
		// forceFit : true
		// }, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRIncomePlan_list = new Ext.grid.GridPanel({
			id : 'panel_SRIncomePlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "个性化需求收款计划",
			trackMouseOver : false,
			loadMask : true,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true
		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ this.dataId + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.gridpanel_SRIncomePlan_list;
	},

	getFormpanel_SRAnalyse : function() {
		var sra = new SRAction();
		var praId = sra.getProjectRequireAnalyseId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse",
				this);
		if (praId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse",
					praId);// 这是要随时变得
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", "");
		}
		biddata = da.splitForReadOnly(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRAnalyse = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse',
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
				title : "项目需求分析",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRAnalyse = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse',
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
				title : "项目需求分析",
				items : biddata
			});
		}
		return this.formpanel_SRAnalyse;
	},

	getFormpanel_SRProjectPlan : function() {
		var sra = new SRAction();
		var ppId = sra.getProjectPlanId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRProjectPlan", this);
		if (ppId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan",ppId);// 这是要随时变得
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan","");
		}
		biddata = da.splitForReadOnly(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "个性化需求项目计划",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "个性化需求项目计划",
				items : biddata
			});
		}
		return this.formpanel_SRProjectPlan;
	},

	getFormpanel_SRprojectContracts : function() {
		var sra = new SRAction();
		var rcId = sra.getRequirementContractId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRprojectContracts", this);
		if (rcId != '0') {
			data = da.getSingleFormPanelElementsForEdit(
					"panel_SRprojectContracts", rcId);// 这是要随时变得
		} else {
			data = da.getSingleFormPanelElementsForEdit(
					"panel_SRprojectContracts", "");
		}
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr.indexOf('$contractCode') > 0
					|| idStr.indexOf('$contractCode') > 0) {
				data[i].readOnly = true;
				if (data[i].value == '') {
					data[i].emptyText = '自动生成';
					// data[i].readOnly = true;
					data[i].disabled = true;
				}
			}
			data[i].readOnly = true;
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRprojectContracts = new Ext.form.FormPanel({
				id : 'panel_SRprojectContracts',
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
				title : "个性化需求合同",
				items : biddata

			});
		} else {
			this.formpanel_SRprojectContracts = new Ext.form.FormPanel({
				id : 'panel_SRprojectContracts',
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
				title : "个性化需求合同",
				items : biddata
			});
		}
		return this.formpanel_SRprojectContracts;
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
		var sra = new SRAction();
		var ppId = sra.getRootProjectPlanId(this.dataId);
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
		this.model = "nsr1_confirmByClient";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nsr1_confirmByClient",
				this);
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

		this.getFormpanel_SpecialRequire3_Input();
		this.pa.push(this.formpanel_SpecialRequire3_Input);
		this.formname.push("panel_SpecialRequire3_Input");
		temp.push(this.formpanel_SpecialRequire3_Input);

		this.getFormpanel_SRServiceProviderInfo_Input();
		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
		this.formname.push("panel_SRServiceProviderInfo_Input");
		temp.push(this.formpanel_SRServiceProviderInfo_Input);
		this.getFormpanel_SRAnalyse();
		this.pa.push(this.formpanel_SRAnalyse);
		this.formname.push("panel_SRAnalyse");
		temp.push(this.formpanel_SRAnalyse);
//		this.projectPlanGrid = new projectPlanListPanel({
//			dataId : this.dataId,
//			rootId : ppId
//		});
//		this.pa.push(this.projectPlanGrid);
//		this.formname.push("projectPlanListPanel");
//		temp.push(this.projectPlanGrid);
		this.getFormserviceItemBasePanel();
		this.pa.push(this.formserviceItemBasePanel);
		this.formname.push("serviceItemBasePanel");
		temp.push(this.formserviceItemBasePanel);
		this.getFormpanel_SRprojectContracts();
		this.pa.push(this.formpanel_SRprojectContracts);
		this.formname.push("panel_SRprojectContracts");
		temp.push(this.formpanel_SRprojectContracts);
		this.getGridpanel_SRIncomePlan_list();
		this.gd.push(this.gridpanel_SRIncomePlan_list);
		this.gridname.push("panel_SRIncomePlan_list");
		temp.push(this.gridpanel_SRIncomePlan_list);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})