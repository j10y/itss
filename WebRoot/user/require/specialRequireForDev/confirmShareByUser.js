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
	
	//���沢�ύ����
	saveAndSubmit : function() {
		if(!Ext.getCmp('panel_SRprojectContracts').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		var taskId = this.taskId;
		var formParam = Ext.encode(getFormParam('panel_SRprojectContracts'));
		var curDataId = this.dataId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveRequirementContractForReq.action',
			params : {
				info:formParam,
				reqId:curDataId
			},
			success : function(response, options) {
				var costCenterManager = Ext.getCmp('SRprojectContracts$costCenterManagerCombo').getValue();//�ɱ����ĸ�����
				Ext.Ajax.request({
					url :  webContext
							+ '/extjs/workflow?method=getData&taskId=' + taskId
							+ '&users=confirmShareByUserManager:' + costCenterManager,
					method : 'post',
					success : function(response, options) {
						window.parent.auditContentWin.specialAudit();
					},
					failure : function(response, options) {
						alert("ָ�ɽڵ�������ʧ�ܣ�");
					}
				})
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
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

	getFormpanel_SpecialRequireDevConfirm_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequireDevConfirm_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_confirmShareByUser",
					"panel_SpecialRequireDevConfirm_Input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequireDevConfirm_Input");
		}
		biddata = da.splitForReadOnly(data);
			this.formpanel_SpecialRequireDevConfirm_Input = new Ext.form.FormPanel({
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
				title : "�����������������",
				items : biddata
			});
		return this.formpanel_SpecialRequireDevConfirm_Input;
	},
	getFormpanel_srInfo_input1 : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_srInfo_input1",
				this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_confirmShareByUser",
					"panel_srInfo_input1", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input1");
		}
		biddata = da.splitForReadOnly(data);
			this.formpanel_srInfo_input1 = new Ext.form.FormPanel({
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
				title : "�������",
				items : biddata
			});
		return this.formpanel_srInfo_input1;
	},
	getFormpanel_SRprojectContracts : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRprojectContracts", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_confirmShareByUser",
					"panel_SRprojectContracts", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SRprojectContracts");
		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(data[i].id!='SRprojectContracts$shareCostCenter'&&data[i].id!='SRprojectContracts$costCenterManagerCombo'){
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if(i==8){
				modifyData.push(this.getGridpanel_SRIncomePlan_list());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);
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
				title : "�����ͬ",
				items : biddata
			});
		return this.formpanel_SRprojectContracts;
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
		// ѭ�������е��У�Ȼ��������������
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
			// ��ÿһ����������
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
			fieldLabel : '�տ�ƻ�',
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
					+ pcnameValue + '\"' + '}';// ����Ҫ��ʱ���
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
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});
		
		var items = new Array();
		var temp = new Array();
		this.model = "devsr_confirmShareByUser";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("devsr_confirmShareByUser", this);
		this.buttons = new Array();
		if (this.readOnly != "1") {
			this.buttons = this.mybuttons;
		}
		temp.push(this.getFormpanel_SpecialRequireDevConfirm_Input());
		temp.push(this.getFormpanel_srInfo_input1());
		temp.push(this.getFormpanel_SRprojectContracts());
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		this.items = items;
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})