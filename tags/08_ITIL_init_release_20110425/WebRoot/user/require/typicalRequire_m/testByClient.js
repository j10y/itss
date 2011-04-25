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
	saveForGrid : function() {
		var reqId = this.dataId;
		//var reqCls = this.reqClass;
		var removedIds = this.remIds;
		var store = Ext.getCmp('panel_SRTestReport').store;
		var product = "";
		store.each(function(record) {
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
				//alert(product);
			}
		})
		product = unicode(product);
		//alert(product);
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveTestReport.action',
			params : {
				product : product,
				reqId : reqId

			},
			success : function(response, options) {
				Ext.MessageBox.alert("提示", "保存成功", function() {
					store.reload();
					store.removeAll();
					removedIds = "";
				});

			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		})
	},
	removePlan : function() {
		this.remIds = this.removedIds;
		var record = Ext.getCmp('panel_SRTestReport').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('panel_SRTestReport').getSelectionModel()
				.getSelections();
		var store = Ext.getCmp('panel_SRTestReport').store;
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
		} else {
			Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(btn) {
				if (btn == 'yes') {
					if (records) {
						for (var i = 0; i < records.length; i++) {
							this.store.remove(records[i]);
							this.remIds += records[i].get("SRTestReport$id")
									+ ",";
							//alert(this.remIds);
						}
						Ext.Ajax.request({
							url : webContext
									+ '/SRAction_removeTestReport.action',
							params : {
								removedIds : this.remIds
							},
							success : function(response, options) {
								Ext.MessageBox.alert("提示", "删除成功", function() {
//modify by zhangzy for 当用户添加数据未保存且要删除其中一条数据时，会产生删除所有记录的bug in 2009 11 23									
//									store.reload();
//									store.removeAll();
								});

							},
							failure : function(response, options) {
								Ext.MessageBox.alert("删除失败");
							}
						})
					}
				}
			}, this)
		}
	},
	getFeedback : function(dataId, taskId, surveyId) {
		return new Ext.Panel({
			title : "<font color=red>用户反馈</font>",
			id : "feedback_success",
			width : 'auto',
			heigth : 'auto',
			frame : true,
			autoScroll : true,
			autoLoad : {
				url : webContext + '/SRAction_findQuestions.action?surveyId='
						+ surveyId + '&dataId=' + dataId + "&taskId=" + taskId,
				nocache : true,
				scripts : true,
				text : "页面正在加载中......",
				method : 'post',
				scope : this
			},
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'fit'
		});
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
			data = da.getPanelElementsForEdit("tr_m_testByClient","panel_typicalRequire_m_Input", this.dataId);// 这是要随时变得
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
			data = da.getPanelElementsForEdit("tr_m_testByClient",
					"panel_SRAnalyse", this.dataId);// 这是要随时变得
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
				title : "项目需求分析",
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
				title : "项目需求分析",
				items : biddata
			});
		}
		return this.formpanel_SRAnalyse;
	},

	getGridpanel_SRTestReport : function() {
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",this);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
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
			// 给每一行增加修饰
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
		this.store = da.getPagePanelJsonStore("panel_SRTestReport", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRTestReport = new Ext.grid.EditorGridPanel({
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "<font color=red>用户测试报告</font>",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.gridpanel_SRTestReport;
	},
//add by zhangzy for modify userFeedback in 20091124 begin 
	getFormpanel_SRUserFeedback_input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRUserFeedback_input",this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("tr_testByClient",
					"panel_SRUserFeedback_input", this.dataId);// 这是要随时变得	
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRUserFeedback_input");
			for (i = 0; i < data.length; i++) {
				if (data[i].id == 'itil_SRUserFeedback$completeCombo' || data[i].id == 'itil_SRUserFeedback$contentCombo') {
					data[i].value = 1;
				}
			}
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRUserFeedback_input = new Ext.form.FormPanel({
				id : 'panel_SRUserFeedback_input',
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
				title : "<font color=red>用户评价</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRUserFeedback_input = new Ext.form.FormPanel({
				id : 'panel_SRUserFeedback_input',
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
				title : "<font color=red>用户评价</font>",
				items : biddata
			});
		}
		return this.formpanel_SRUserFeedback_input;
	},
//add by zhangzy for modify userFeedback in 20091124 end
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url = webContext + '/SRAction_findSpecialRequireSurvey.action';
		conn.open("get", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			this.surveyId = data.surveyId;
		}
		this.removedIds = "";
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
		this.model = "tr_m_testByClient";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("tr_m_testByClient", this);
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

		this.gridflm_projectPlanList = new projectPlanListPanel({
			dataId : this.dataId,
			reqClass : this.reqClass,
			rootId : ppId
		});
		temp.push(this.gridflm_projectPlanList);

		this.getGridpanel_SRTestReport();
		this.gd.push(this.gridpanel_SRTestReport);
		this.gridname.push("panel_SRTestReport");
		temp.push(this.gridpanel_SRTestReport);

//		temp.push(this.getFeedback(this.dataId, this.taskId, this.surveyId));
		this.getFormpanel_SRUserFeedback_input();
		temp.push(this.formpanel_SRUserFeedback_input);		
		temp.push(histroyForm);

		items.push(this.getTabpanel(temp));
		if (this.readOnly != 1) {
			items.push(this.buttons);
		}
		this.items = items;
		this.on("removePlan",this.removePlan,this);
		this.on("saveForGrid",this.saveForGrid,this);
		this.on("saveForTestGrid",this.saveForGrid,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})