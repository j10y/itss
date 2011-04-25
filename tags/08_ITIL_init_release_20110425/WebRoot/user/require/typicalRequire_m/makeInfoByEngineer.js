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
		if(!Ext.getCmp('panel_SRAnalyse').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整必填项.");	
			return false;
		}
		//add by musicbear for 系统开始结束时间、测试开始结束时间 有效性验证 in 2009 11 19 start
		var startDate=Ext.getCmp('SRAnalyse$startDate').getValue().format('Y-m-d');
		var endtDate=Ext.getCmp('SRAnalyse$finishDate').getValue().format('Y-m-d');
		var testBeginDate=Ext.getCmp('SRAnalyse$testBeginDate').getValue().format('Y-m-d');
		var testEndDate=Ext.getCmp('SRAnalyse$testEndDate').getValue().format('Y-m-d');
		if (startDate > endtDate) {
			Ext.MessageBox.alert("提示", "系统开始结束时间填写不正确，请认真填写."); 
			return false;
		 }
		 if (testBeginDate > testEndDate) {
			Ext.MessageBox.alert("提示", "测试开始结束时间填写不正确，请认真填写.");
			return false;
		 }
		 //add by musicbear for 系统开始结束时间、测试开始结束时间 有效性验证 in 2009 11 19 end
		var formParam = Ext.getCmp('panel_SRAnalyse').form.getValues(false);
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

	getFormpanel_typicalRequire_m_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_typicalRequire_m_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("tr_m_makeInfoByEngineer","panel_typicalRequire_m_Input", this.dataId);// 这是要随时变得
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("panel_typicalRequire_m_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_typicalRequire_m_Input = new Ext.form.FormPanel({
				id : 'panel_typicalRequire_m_Input',
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
				title : "需求申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_typicalRequire_m_Input = new Ext.form.FormPanel({
				id : 'panel_typicalRequire_m_Input',
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
				title : "需求申请",
				items : biddata
			});
		}
		return this.formpanel_typicalRequire_m_Input;
	},
	getFormpanel_SRAnalyse : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse",
				this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("tr_m_makeInfoByEngineer","panel_SRAnalyse", this.dataId);// 这是要随时变得
//remove by musicbear for 项目需求分析panel中的字段在“变更”流程中也能修改 in 2009 11 19 start
//			for (i = 0; i < data.length; i++) {
//				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
//					data[i].id = data[i].id + 8;// 改变Combobox的id
//					data[i].readOnly = true;
//					data[i].hideTrigger = true;
//				}
//				data[i].readOnly = true;
//			}
//remove by musicbear for 项目需求分析panel中的字段在“变更”流程中也能修改 in 2009 11 19 start
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRAnalyse");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRAnalyse = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse',
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
				title : "<font color=red>项目需求分析</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRAnalyse = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse',
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
				title : "<font color=red>项目需求分析</font>",
				items : biddata
			});
		}
		return this.formpanel_SRAnalyse;
	},
	// getFormpanel_SRProjectPlan: function() {
	//      var da = new DataAction();
	//		  var data = null;
	//			// 判断是新增还是修改
	//			var biddata = "";
	//			
	//			  var buttonUtil = new ButtonUtil();
	//			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRProjectPlan",this);
	//			if (this.dataId != '0') {
	//				data = da.getPanelElementsForEdit("tr_m_makeInfoByEngineer", "panel_SRProjectPlan", this.dataId);// 这是要随时变得
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
	//			title : "个性化需求项目计划实体(N)",
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
	//			title : "个性化需求项目计划实体(N)",
	//			items : biddata
	//			});
	//		}
	//		return this.formpanel_SRProjectPlan;
	//	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});

		var sra = new SRAction();
		var ppId = sra.getRootProjectPlanId(this.dataId);

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
		this.model = "tr_m_makeInfoByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("tr_m_makeInfoByEngineer",
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

		this.getFormpanel_typicalRequire_m_Input();
		this.pa.push(this.formpanel_typicalRequire_m_Input);
		this.formname.push("panel_typicalRequire_m_Input");
		temp.push(this.formpanel_typicalRequire_m_Input);
		this.getFormpanel_SRAnalyse();
		this.pa.push(this.formpanel_SRAnalyse);
		this.formname.push("panel_SRAnalyse");
		temp.push(this.formpanel_SRAnalyse);
		//this.getFormpanel_SRProjectPlan();
		//this.pa.push(this.formpanel_SRProjectPlan);
		//this.formname.push("panel_SRProjectPlan");
		//temp.push(this.formpanel_SRProjectPlan);
		this.projectPlanGrid = new projectPlanListPanel({
			dataId : this.dataId,
			rootId : ppId
		});
		temp.push(this.projectPlanGrid);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if (this.readOnly != 1) {
			items.push(this.buttons);
		}
		this.items = items;
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})