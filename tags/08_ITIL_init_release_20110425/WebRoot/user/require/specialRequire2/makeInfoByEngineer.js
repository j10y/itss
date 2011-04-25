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
		if(!Ext.getCmp('panel_SRAnalyse2').form.isValid()){
					Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д����������.");	
					return false;
					}
		var formParam = Ext.getCmp('panel_SRAnalyse2').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curdataId = this.dataId;
		Ext.Ajax.request({
			url : webContext
					+ '/SRAction_saveProjectRequireAnalyseForReq.action?'
					+ '&reqId=' + this.dataId,
			params : vp,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var tempId = responseArray.id;
				Ext.getCmp('SRAnalyse$id').setValue(tempId);
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

	getFormpanel_SpecialRequire4_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire4_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SpecialRequire4_Input", this.dataId);// ����Ҫ��ʱ���
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
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire4_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SpecialRequire4_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire4_Input',
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
				title : "С�����û򿪷�����",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SpecialRequire4_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire4_Input',
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
				title : "С�����û򿪷�����",
				items : biddata
			});
		}
		return this.formpanel_SpecialRequire4_Input;
	},
	getFormpanel_SRServiceProviderInfo_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRServiceProviderInfo_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SRServiceProviderInfo_Input", this.dataId);// ����Ҫ��ʱ���
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
		} else {
			data = da
					.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");
			biddata = da.split(data);
		}
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
				title : "ҵ���༰�����Ŷ�",
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
				title : "ҵ���༰�����Ŷ�",
				items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},
	getFormpanel_SRAnalyse2 : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse2",
				this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SRAnalyse2", this.dataId);// ����Ҫ��ʱ���
			
		} else {
			data = da.getPanelElementsForAdd("panel_SRAnalyse2");
			
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRAnalyse2 = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse2',
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
				title : "<font color=red>��Ŀ�������</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRAnalyse2 = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse2',
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
				title : "<font color=red>��Ŀ�������</font>",
				items : biddata
			});
		}
		return this.formpanel_SRAnalyse2;
	},
	// getFormpanel_SRProjectPlan: function() {
	//      var da = new DataAction();
	//		  var data = null;
	//			// �ж������������޸�
	//			var biddata = "";
	//			
	//			  var buttonUtil = new ButtonUtil();
	//			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRProjectPlan",this);
	//			if (this.dataId != '0') {
	//				data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer", "panel_SRProjectPlan", this.dataId);// ����Ҫ��ʱ���
	//				biddata = da.split(data);
	//			} else {
	//				data = da.getPanelElementsForAdd("panel_SRProjectPlan");
	//				biddata = da.split(data);
	//			}
	//			if(this.getFormButtons.length!=0){
	//		this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
	//			id : 'panel_SRProjectPlan',
	//			layout : 'table',
	//			height : 'auto',
	//			width : 800,
	//			frame : true,
	//			collapsible : true,
	//			defaults : {
	//				bodyStyle : 'padding:4px'
	//			},
	//			layoutConfig : {
	//				columns : 4
	//			},
	//			title : "���Ի�������Ŀ�ƻ�ʵ��(N)",
	//			items : biddata,
	//			buttons : this.getFormButtons
	//		});
	//		}else{
	//			this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
	//			id : 'panel_SRProjectPlan',
	//			layout : 'table',
	//			height : 'auto',
	//			width : 800,
	//			frame : true,
	//			collapsible : true,
	//			defaults : {
	//				bodyStyle : 'padding:4px'
	//			},
	//			layoutConfig : {
	//				columns : 4
	//			},
	//			title : "���Ի�������Ŀ�ƻ�ʵ��(N)",
	//			items : biddata
	//			});
	//		}
	//		return this.formpanel_SRProjectPlan;
	//	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
		
//		var sra = new SRAction();
//		var ppId = sra.getRootProjectPlanId(this.dataId);

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
		this.model = "sr2_makeInfoByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("sr2_makeInfoByEngineer",
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

		this.getFormpanel_SpecialRequire4_Input();
		this.pa.push(this.formpanel_SpecialRequire4_Input);
		this.formname.push("panel_SpecialRequire4_Input");
		temp.push(this.formpanel_SpecialRequire4_Input);
		this.getFormpanel_SRServiceProviderInfo_Input();
		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
		this.formname.push("panel_SRServiceProviderInfo_Input");
		temp.push(this.formpanel_SRServiceProviderInfo_Input);
		this.getFormpanel_SRAnalyse2();
		this.pa.push(this.formpanel_SRAnalyse2);
		this.formname.push("panel_SRAnalyse2");
		temp.push(this.formpanel_SRAnalyse2);
		//		       this.getFormpanel_SRProjectPlan();
		//		       this.pa.push(this.formpanel_SRProjectPlan);
		//		       this.formname.push("panel_SRProjectPlan");
		//		       temp.push(this.formpanel_SRProjectPlan);
//		this.projectPlanGrid = new projectPlanListPanel({
//			dataId : this.dataId,
//			rootId : ppId
//		});
//		temp.push(this.projectPlanGrid);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})