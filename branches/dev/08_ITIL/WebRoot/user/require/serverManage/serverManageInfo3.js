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
	//生成配置项
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
				Ext.MessageBox.alert("生成失败！");
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

	getFormpanel_ServerManage_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ServerManage_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("serverManageInfo3",
					"panel_ServerManage_Input", this.dataId);// 这是要随时变得
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
				title : "服务器入驻数据中心申请主表",
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
				title : "服务器入驻数据中心申请主表",
				items : biddata
			});
		}
		return this.formpanel_ServerManage_Input;
	},
	getFormpanel_ServerManageInfoOne_input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ServerManageInfoOne_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("serverManageInfo3",
					"panel_ServerManageInfoOne_input", this.dataId);// 这是要随时变得

		} else {
			data = da.getPanelElementsForAdd("panel_ServerManageInfoOne_input");

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
				title : "服务器管理员处理信息",
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
				title : "服务器管理员处理信息",
				items : biddata
			});
		}
		return this.formpanel_ServerManageInfoOne_input;
	},
	getFormpanel_ServerManageInfoTwo_input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ServerManageInfoTwo_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("serverManageInfo3",
					"panel_ServerManageInfoTwo_input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ServerManageInfoTwo_input");
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
				title : "数据中心管理员处理信息",
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
				title : "数据中心管理员处理信息",
				items : biddata
			});
		}
		return this.formpanel_ServerManageInfoTwo_input;
	},
	//获取配置项基础信息面板
	getFormconfigItemBasicPanel : function(id) {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
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
			title : "配置项基础信息",
			items : biddata
		});
		return this.formconfigItemBasicPanel;
	},
	//获取服务项信息面板
	getFormpanel_ServerPanel : function(id) {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
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
			title : "服务器信息",
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
			title : "服务器配置项信息",
			items : [this.formconfigItemBasicPanel, this.formpanel_ServerPanel]
		});
		return this.serverPanel;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
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