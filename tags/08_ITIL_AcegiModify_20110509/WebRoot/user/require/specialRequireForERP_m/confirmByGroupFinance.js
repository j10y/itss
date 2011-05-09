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
		if(!Ext.getCmp('panel_erpSR_finance_m_input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		var formParam = Ext.getCmp('panel_erpSR_finance_m_input').form.getValues(false);
		var firstId = Ext.getCmp('SpecialRequirement$financeTypeCombo').getValue();
		var secId = Ext.getCmp('SpecialRequirement$batchTypeCombo').getValue();
		if (firstId == "") {
			Ext.Msg.alert("��ʾ", "��ѡ��������!");
			return;
		}
		if (firstId == '2' && secId == "") {
			Ext.Msg.alert("��ʾ", "��ѡ�����������!");
			return;
		}
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveSpecialRequire.action',
			params : vp,
			
			success : function(response, options) {
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
			activeTab : 0,
			enableTabScroll : true,
			// minTabWidth:100,
			// resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab//,
			//bbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>������ɫ��ҳΪ������ֻ����Ϣ����ɫ��ҳΪ����д��Ϣ���֣�������д��Ϣ���ȱ�����Ϣ���ύ����!</font>')]

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

	getFormpanel_erpSR_finance_m_input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_erpSR_finance_m_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_m_confirmByGroupFinance",
					"panel_erpSR_finance_m_input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_erpSR_finance_m_input");
		}
		for (var i = 0; i < data.length; i++) {
			if(data[i].id!='SpecialRequirement$financeTypeCombo'&&data[i].id!='SpecialRequirement$batchTypeCombo'){
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].xtype == "panel") {
					var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
					data[i] = Ext.decode(dd);
				}
			}
			
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_erpSR_finance_m_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_finance_m_input',
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
				title : "ERP�ǳ�������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_erpSR_finance_m_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_finance_m_input',
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
				title : "ERP�ǳ���������",
				items : biddata
			});
		}
		return this.formpanel_erpSR_finance_m_input;
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
		this.model = "esr_m_confirmByGroupFinance";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"esr_m_confirmByGroupFinance", this);	
		this.buttons = new Array();
		
		if(this.readOnly!=1){
			this.buttons = this.mybuttons;
		}
		this.getFormpanel_erpSR_finance_m_input();
		temp.push(this.formpanel_erpSR_finance_m_input);
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