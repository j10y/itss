PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	buttonAlign:'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},

	getTabpanel : function(tab, tabTitle) {
		// alert(this.tabNumber);
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab//,
			//bbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>������ɫ��ҳΪ������ֻ����Ϣ����ɫ��ҳΪ����д��Ϣ���֣�������д��Ϣ�󱣴��ύ!</font>')]
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
	getFormpanel_erpSR_it_m_input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_erpSR_it_m_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_m_confirmProjectPlan",
					"panel_erpSR_it_m_input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_erpSR_it_m_input");
		}
		biddata = da.splitForReadOnly(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_erpSR_it_m_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_it_m_input',
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
			this.formpanel_erpSR_it_m_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_it_m_input',
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
				items : biddata
			});
		}
		return this.formpanel_erpSR_it_m_input;
	},
	getFormpanel_srInfo_input1 : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_srInfo_input1",this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_m_confirmProjectPlan",
					"panel_srInfo_input1", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input1");
		}
		biddata = da.splitForReadOnly(data);
		if (this.getFormButtons.length != 0) {
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
				title : "��Ŀ�������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
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
				title : "��Ŀ�������",
				items : biddata
			});
		}
		return this.formpanel_srInfo_input1;
	},

	items : this.items,
	buttons:this.buttons,
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
		this.model = "esr_m_confirmProjectPlan";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("esr_m_confirmProjectPlan",this);
		this.buttons = new Array();
		if(this.readOnly!=1){
			this.buttons =this.mybuttons;
		}
		this.getFormpanel_erpSR_it_m_input();
		this.pa.push(this.formpanel_erpSR_it_m_input);
		this.formname.push("panel_erpSR_it_m_input");
		temp.push(this.formpanel_erpSR_it_m_input);
		
		this.formpanel_srInfo_input1 = this.getFormpanel_srInfo_input1();
		this.pa.push(this.formpanel_srInfo_input1);
		this.formname.push("panel_srInfo_input1");
		temp.push(this.formpanel_srInfo_input1);
		
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})