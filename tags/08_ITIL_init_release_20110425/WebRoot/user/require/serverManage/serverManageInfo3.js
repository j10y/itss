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
	//����������
	initServerConfigItem : function() {
		var dataId = this.dataId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_initServerConfigItem.action?dataId=' + dataId,
			success : function(response, options) {
				window.location = webContext
						+ "/user/require/serverManage/serverManageInfo3.jsp?dataId="
						+ dataId;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ�ܣ�");
			}
		}, this);
	},
	saveServerCI : function() {
		var dataId = this.dataId;
		var ciParam = Ext.getCmp('configItemBasicPanel').form.getValues(false);
		var serverParam = Ext.getCmp('formpanel_ServerPanel').form.getValues(false);
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveServerConfigItem.action?',
			params : {
				dataId : dataId,
				configItemInfo : ciParam,
				serverInfo : serverParam
			},
			success : function(response, options) {
				window.location = webContext+ "/user/require/serverManage/serverManageInfo3.jsp?dataId="+ dataId;
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

	getFormpanel_ServerManage_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ServerManage_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("serverManageInfo3",
					"panel_ServerManage_Input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_ServerManage_Input");
		}
		biddata = da.splitForReadOnly(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_ServerManage_Input = new Ext.form.FormPanel({
				id : 'panel_ServerManage_Input',
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
				title : "��������פ����������������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ServerManage_Input = new Ext.form.FormPanel({
				id : 'panel_ServerManage_Input',
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
				title : "��������פ����������������",
				items : biddata
			});
		}
		return this.formpanel_ServerManage_Input;
	},
	getFormpanel_ServerManageInfoOne_input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ServerManageInfoOne_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("serverManageInfo3",
					"panel_ServerManageInfoOne_input", this.dataId);// ����Ҫ��ʱ���

		} else {
			data = da.getPanelElementsForAdd("panel_ServerManageInfoOne_input");

		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;// �ı�Combobox��id
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
			this.formpanel_ServerManageInfoOne_input = new Ext.form.FormPanel({
				id : 'panel_ServerManageInfoOne_input',
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
				title : "����������Ա������Ϣ",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ServerManageInfoOne_input = new Ext.form.FormPanel({
				id : 'panel_ServerManageInfoOne_input',
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
				title : "����������Ա������Ϣ",
				items : biddata
			});
		}
		return this.formpanel_ServerManageInfoOne_input;
	},
	getFormpanel_ServerManageInfoTwo_input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ServerManageInfoTwo_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("serverManageInfo3",
					"panel_ServerManageInfoTwo_input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_ServerManageInfoTwo_input");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;// �ı�Combobox��id
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
			this.formpanel_ServerManageInfoTwo_input = new Ext.form.FormPanel({
				id : 'panel_ServerManageInfoTwo_input',
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
				title : "�������Ĺ���Ա������Ϣ",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ServerManageInfoTwo_input = new Ext.form.FormPanel({
				id : 'panel_ServerManageInfoTwo_input',
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
				title : "�������Ĺ���Ա������Ϣ",
				items : biddata
			});
		}
		return this.formpanel_ServerManageInfoTwo_input;
	},
	//��ȡ�����������Ϣ���
	getFormconfigItemBasicPanel : function(id) {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		data = da.getSingleFormPanelElementsForEdit("configItemBasicPanel", id);
		biddata = da.splitForReadOnly(data);
		this.formconfigItemBasicPanel = new Ext.form.FormPanel({
			id : 'configItemBasicPanel',
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
			title : "�����������Ϣ",
			items : biddata
		});
		return this.formconfigItemBasicPanel;
	},
	//��ȡ��������Ϣ���
	getFormpanel_ServerPanel : function(id) {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		data = da.getSingleFormPanelElementsForEdit("panel_ServerPanel", id);
		biddata = da.splitForReadOnly(data);
		this.formpanel_ServerPanel = new Ext.form.FormPanel({
			id : 'formpanel_ServerPanel',
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
			title : "��������Ϣ",
			items : biddata
		});
		return this.formpanel_ServerPanel;
	},
	getServerPanel : function(ciId, serverId) {
		this.getFormconfigItemBasicPanel(ciId);
		this.getFormpanel_ServerPanel(serverId);

		this.serverPanel = new Ext.Panel({
			id : 'serverPanel',
			height : 'auto',
			align : 'center',
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
			title : "��������������Ϣ",
			items : [this.formconfigItemBasicPanel, this.formpanel_ServerPanel]
		});
		return this.serverPanel;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.ServerManage"
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
		this.model = "serverManageInfo3";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil
				.getButtonForModel("serverManageInfo3", this);
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
		this.getFormpanel_ServerManage_Input();
		this.pa.push(this.formpanel_ServerManage_Input);
		this.formname.push("panel_ServerManage_Input");
		temp.push(this.formpanel_ServerManage_Input);
		this.getFormpanel_ServerManageInfoOne_input();		
		this.pa.push(this.formpanel_ServerManageInfoOne_input);
		this.formname.push("panel_ServerManageInfoOne_input");
		temp.push(this.formpanel_ServerManageInfoOne_input);
		this.getFormpanel_ServerManageInfoTwo_input();		
		this.pa.push(this.formpanel_ServerManageInfoTwo_input);
		this.formname.push("panel_ServerManageInfoTwo_input");
		temp.push(this.formpanel_ServerManageInfoTwo_input);
		var ciId = "";
		var serverId = "";
		var url = webContext + '/SRAction_findServerManageCiData.action?dataId='+this.dataId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post",url, false);
		conn.send(null);
		if (conn.status == "200") {
			var data = eval('('+conn.responseText+')');
			ciId = data.ciId;
			serverId = data.serverId;
		} else {
			return 'no result';
		}
		
		if (ciId != 0) {
			this.getServerPanel(ciId, serverId);
			temp.push(this.serverPanel);
		}
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("initServerConfigItem", this.initServerConfigItem, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})