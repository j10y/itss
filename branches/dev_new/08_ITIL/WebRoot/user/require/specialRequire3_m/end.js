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

	getFormpanel_specialRequire3_m_start : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr3_m_end",
					"panel_specialRequire3_m_start", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_specialRequire3_m_start");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//�ı�Combobox��id
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
		this.formpanel_specialRequire3_m_start = new Ext.form.FormPanel({
			id : 'panel_specialRequire3_m_start',
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
			title : "��ǰ֧���������",
			items : biddata
		});
		return this.formpanel_specialRequire3_m_start;
	},
	getFormpanel_SREngineerAndShare : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr3_m_end",
					"panel_SREngineerAndShare", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SREngineerAndShare");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//�ı�Combobox��id
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

		this.formpanel_SREngineerAndShare = new Ext.form.FormPanel({
			id : 'panel_SREngineerAndShare',
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
			title : "��ǰ֧��ѡ�񹤳�ʦ��̯�����Ϣ",
			items : biddata
		});

		return this.formpanel_SREngineerAndShare;
	},
	getFormpanel_SREngineerOperateInfo : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SREngineerOperateInfo", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr3_m_end",
					"panel_SREngineerOperateInfo", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SREngineerOperateInfo");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//�ı�Combobox��id
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

		this.formpanel_SREngineerOperateInfo = new Ext.form.FormPanel({
			id : 'panel_SREngineerOperateInfo',
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
			title : "��ǰ֧�ֽ�������ʵʩ��Ϣ",
			items : biddata
		});

		return this.formpanel_SREngineerOperateInfo;
	},
	items : this.items,
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
		this.model = "sr3_m_end";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("sr3_m_end", this);
		//add by lee for add back button in 20091104 begin
		var backButton = new Ext.Button({
			text : '����',
			//pressed : true,
			iconCls : 'back',
			scope : this,
			handler : function() {
				window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
			}
		});
		if(this.serviceItemId!=""){
			this.mybuttons.push(backButton);
		}
		//add by lee for add back button in 20091104 end
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

		this.getFormpanel_specialRequire3_m_start();
		this.pa.push(this.formpanel_specialRequire3_m_start);
		this.formname.push("panel_specialRequire3_m_start");
		temp.push(this.formpanel_specialRequire3_m_start);
		this.getFormpanel_SREngineerAndShare();
		this.pa.push(this.formpanel_SREngineerAndShare);
		this.formname.push("panel_SREngineerAndShare");
		temp.push(this.formpanel_SREngineerAndShare);
		this.getFormpanel_SREngineerOperateInfo();
		this.pa.push(this.formpanel_SREngineerOperateInfo);
		this.formname.push("panel_SREngineerOperateInfo");
		temp.push(this.formpanel_SREngineerOperateInfo);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})