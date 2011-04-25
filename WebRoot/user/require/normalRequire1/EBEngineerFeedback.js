var reqId = "";
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
		if(!Ext.getCmp('panel_EBEngineerFeedback_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		var formParam = Ext.encode(getFormParam('panel_EBEngineerFeedback_Input'));
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveEbEngineerFeedback.action',
			params : {
				info:formParam,
				reqId:reqId
			},
			success : function(response, options) {
				var feedbackId = Ext.util.JSON.decode(response.responseText).id;
				Ext.getCmp('itil_sci_EBEngineerFeedback$id').setValue(feedbackId);
				window.parent.auditContentWin.specialAudit();
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : this.tabId,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			bodyBorder : true,
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

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed1_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_EBEngineerFeedback",
					"panel_ERP_NormalNeed1_Input", this.dataId);// ����Ҫ��ʱ���
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed1_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed1_Input',
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
				title : "ERP�����������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed1_Input',
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
				title : "ERP�����������",
				items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed1_Input;
	},
	getFormpanel_ErpEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ErpEngineerFeedback_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_EBEngineerFeedback",
					"panel_ErpEngineerFeedback_Input", this.dataId);// ����Ҫ��ʱ���
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ErpEngineerFeedback_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
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
				title : "ERP����ʦ������Ϣ",
				items : biddata
					//,
					//buttons : this.getFormButtons
			});
		} else {
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
				title : "ERP����ʦ������Ϣ",
				items : biddata
			});
		}
		return this.formpanel_ErpEngineerFeedback_Input;
	},
	
//remove by lee for BASIS����ʦ�����ڱ��û�ȥ�� in 20091202 begin
//	getFormpanel_BASISEngineerFeedback_Input : function() {
//		var da = new DataAction();
//		var data = null;
//		// �ж������������޸�
//		var biddata = "";
//
//		var buttonUtil = new ButtonUtil();
//		this.getFormButtons = buttonUtil.getButtonForPanel(
//				"panel_BASISEngineerFeedback_Input", this);
//		if (this.dataId != '0') {
//			data = da.getPanelElementsForEdit("nr_EBEngineerFeedback",
//					"panel_BASISEngineerFeedback_Input", this.dataId);// ����Ҫ��ʱ���
//			biddata = da.splitForReadOnly(data);
//		} else {
//			data = da.getPanelElementsForAdd("panel_BASISEngineerFeedback_Input");
//			biddata = da.split(data);
//		}
//		if (this.getFormButtons.length != 0) {
//			this.formpanel_BASISEngineerFeedback_Input = new Ext.form.FormPanel({
//				id : 'panel_BASISEngineerFeedback_Input',
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
//				title : "BASIS����ʦ����",
//				items : biddata
//					//,
//					//buttons : this.getFormButtons
//			});
//		} else {
//			this.formpanel_BASISEngineerFeedback_Input = new Ext.form.FormPanel({
//				id : 'panel_BASISEngineerFeedback_Input',
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
//				title : "BASIS����ʦ����",
//				items : biddata
//			});
//		}
//		return this.formpanel_BASISEngineerFeedback_Input;
//	},
//remove by lee for BASIS����ʦ�����ڱ��û�ȥ�� in 20091202 end
	
	getFormpanel_EBEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_EBEngineerFeedback_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_EBEngineerFeedback",
					"panel_EBEngineerFeedback_Input", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_EBEngineerFeedback_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
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
				title : "<font color=red>EB����ʦ����</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
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
				title : "<font color=red>EB����ʦ����</font>",
				items : biddata
			});
		}
		return this.formpanel_EBEngineerFeedback_Input;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		reqId = this.dataId;
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
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
		this.model = "nr_EBEngineerFeedback";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nr_EBEngineerFeedback",
				this);
		this.buttons = new Array();
		if (this.readOnly != 1) {
			this.buttons = this.mybuttons;
		}

		this.getFormpanel_ERP_NormalNeed1_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		this.formname.push("panel_ERP_NormalNeed1_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_Input);
		this.getFormpanel_ErpEngineerFeedback_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		this.formname.push("panel_ErpEngineerFeedback_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_Input);
//remove by lee for BASIS����ʦ�����ڱ��û�ȥ�� in 20091202 begin
//		this.getFormpanel_BASISEngineerFeedback_Input();
//		this.pa.push(this.formpanel_BASISEngineerFeedback_Input);
//		this.formname.push("panel_BASISEngineerFeedback_Input");
//		temp.push(this.formpanel_BASISEngineerFeedback_Input);
//remove by lee for BASIS����ʦ�����ڱ��û�ȥ�� in 20091202 end
		this.getFormpanel_EBEngineerFeedback_Input();
		this.pa.push(this.formpanel_EBEngineerFeedback_Input);
		this.formname.push("panel_EBEngineerFeedback_Input");
		temp.push(this.formpanel_EBEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		this.items = items;
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		//remove by lee for ����Ĭ��ͬ�� in 20090806 begin			   
		//		   var tempErpId = Ext.getCmp('itil_sci_EBEngineerFeedback$id').getValue();
		//		       if(tempErpId==''){
		//		       		Ext.getCmp('itil_sci_EBEngineerFeedback$feekback').setValue('ͬ��');
		//		       		Ext.getCmp('itil_sci_EBEngineerFeedback$otherInfo').setValue('ͬ��');
		//		       }
		//remove by lee for ����Ĭ��ͬ�� in 20090806 end			   
		PageTemplates.superclass.initComponent.call(this);
	}
})