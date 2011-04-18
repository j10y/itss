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
		var tkId = this.taskId;
		var formParam = "";
		var gridParam = "";
		var param = '{';
		if (this.pa.length != "0") {
			for (var i = 0; i < this.pa.length; i++) {
				var fP = Ext.encode(this.pa[i].form.getValues(false));
				formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
			}
			param += formParam;
		}
		if (this.gd.length != "0") {
			for (var k = 0; k < this.gd.length; k++) {
				var gParam = "";
				var gP = this.gd[k].getStore().getRange(0,
						this.gd[k].getStore().getCount());
				for (i = 0; i < gP.length; i++) {
					gParam += Ext.encode(gP[i].data) + ",";
				}
				gParam = gParam.slice(0, gParam.length - 1);
				gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
						+ '],';
			}
			param += gridParam;
		}
		param = param.slice(0, param.length - 1) + '}';
		// alert(param); //************************************��ʾ��屣�������************************************
		Ext.Ajax.request({
			url : webContext + '/extjs/pageData?method=savePanel',
			params : {
				info : param,
				panel : 'panel_ServerManageInfoTwo_input',
				model : 'serverManageInfo2'
			},
			success : function(response, options) {
				window.parent.auditContentWin.specialAudit();
			},
			failure : function(response, options) {
				Ext.Msg.alert("��ʾ", "����ʧ��!");
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
			data = da.getPanelElementsForEdit("serverManageInfo2",
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
			data = da.getPanelElementsForEdit("serverManageInfo2",
					"panel_ServerManageInfoOne_input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_ServerManageInfoOne_input");
		}
		biddata = da.splitForReadOnly(data);
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
			data = da.getPanelElementsForEdit("serverManageInfo2",
					"panel_ServerManageInfoTwo_input", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ServerManageInfoTwo_input");
			biddata = da.split(data);
		}
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
				title : "<font color=red>�������Ĺ���Ա������Ϣ</font>",
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
				title : "<font color=red>�������Ĺ���Ա������Ϣ</font>",
				items : biddata
			});
		}
		return this.formpanel_ServerManageInfoTwo_input;
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
		this.model = "serverManageInfo2";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil
				.getButtonForModel("serverManageInfo2", this);
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
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("saveAndSubmit",this.saveAndSubmit,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})