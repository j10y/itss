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

	//保存并提交方法
	saveAndSubmit : function() {
		var formParam = Ext.encode(getFormParam('panel_SRprojectContracts'));
		var curDataId = this.dataId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveRequirementContractForReq.action',
			params : {
				info:formParam,
				reqId:curDataId
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

	getFormpanel_SpecialRequireDevConfirm_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit(
					"devsr_confirmShareByUserManager",
					"panel_SpecialRequireDevConfirm_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequireDevConfirm_Input");
		}
		biddata = da.splitForReadOnly(data);
		var formpanel_SpecialRequireDevConfirm_Input = new Ext.form.FormPanel({
			id : 'panel_SpecialRequireDevConfirm_Input',
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
			title : "开发类需求审批面板",
			items : biddata
		});
		return formpanel_SpecialRequireDevConfirm_Input;
	},
	getFormpanel_srInfo_input1 : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit(
					"devsr_confirmShareByUserManager", "panel_srInfo_input1",
					this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input1");
			
		}
		biddata = da.splitForReadOnly(data);
		var formpanel_srInfo_input1 = new Ext.form.FormPanel({
			id : 'panel_srInfo_input1',
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
			title : "需求分析",
			items : biddata
		});
		return formpanel_srInfo_input1;
	},
	getFormpanel_SRprojectContracts : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
//
//		var buttonUtil = new ButtonUtil();
//		this.getFormButtons = buttonUtil.getButtonForPanel(
//				"panel_SRprojectContracts", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit(
					"devsr_confirmShareByUserManager",
					"panel_SRprojectContracts", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SRprojectContracts");
		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(data[i].id!='SRprojectContracts$shareCostCenter'){
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if(i==8){
				modifyData.push(this.getGridpanel_SRIncomePlan_list());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);
		var formpanel_SRprojectContracts = new Ext.form.FormPanel({
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
			title : "需求合同",
			items : biddata
		});
		return formpanel_SRprojectContracts;
	},
	getGridpanel_SRIncomePlan_list : function() {
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
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_SRIncomePlan_list = new Ext.grid.GridPanel({
			fieldLabel : '收款计划',
			id : 'panel_SRIncomePlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			clicksToEdit : 2,
			autoScroll : true,
			loadMask : true,
			height : 200,
			width : 9999,
			frame : true
		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			this.store.load({
				params : param
			});
		}
		return gridpanel_SRIncomePlan_list;
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
		this.model = "devsr_confirmShareByUserManager";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"devsr_confirmShareByUserManager", this);
		this.buttons = new Array();
		if (this.readOnly != "1") {
			this.buttons = this.mybuttons;
		}

		temp.push(this.getFormpanel_SpecialRequireDevConfirm_Input());
		temp.push(this.getFormpanel_srInfo_input1());
		temp.push(this.getFormpanel_SRprojectContracts());
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})