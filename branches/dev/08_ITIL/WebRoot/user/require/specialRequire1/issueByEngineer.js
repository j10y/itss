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
// add by musicbear for 红色必填项验证 in 2009 11 6 start		
		if (!Ext.getCmp('NoticeForm').form.isValid()) {
			Ext.MessageBox.alert("提示", "页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
			return false;
		}
// add by musicbear for 红色必填项验证 in 2009 11 6 end	
		
		var tempId = Ext.getCmp('SRTransmisEngineer$id').getValue();
		var tempNeed = Ext.getCmp('SRTransmisEngineer$needOrNotCombo').getValue();
		var tempEngineer = Ext.getCmp('SRTransmisEngineer$userInfoCombo').getValue();
		var reqId = this.dataId;
		var tkId = this.taskId;

		if (tempNeed == "") {
			Ext.MessageBox.alert("提示", "请选择是否传输！");
		} else if (tempNeed == "1" && tempEngineer == "") {
			Ext.MessageBox.alert("提示", "请选择传输工程师！");
		} else {
			Ext.Ajax.request({
				url : webContext + '/SRAction_saveTEengineerForReq.action?',
				params : {
					id : tempId,
					reqId : reqId,
					need : tempNeed,
					userId : tempEngineer
				},

				success : function(response, options) {
					var responseArray = Ext.util.JSON
							.decode(response.responseText);
					var panId = responseArray.id;
					Ext.getCmp('SRTransmisEngineer$id').setValue(panId);

					if (tempNeed == "1") {
						Ext.Ajax.request({
							url : webContext
									+ '/extjs/workflow?method=getData&taskId='
									+ tkId + '&users=transmit:' + tempEngineer,
							method : 'post',
							success : function(response, options) {
								window.parent.auditContentWin.specialAudit();
							},
							failure : function(response, options) {

							}
						})
					} else {
//modify by musicbear for 解决工作流成图中此节点无法拒绝，前台页面提交时却有“拒绝”选项 的bug  in 2009 11 6 
						window.parent.auditContentWin.specialAudit();
					}

				},
				failure : function(response, options) {
					Ext.MessageBox.alert("保存失败");
				}
			}, this);
		}
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

	getFormpanel_SpecialRequire3_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire3_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_confirmByClientManager",
					"panel_SpecialRequire3_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3_Input");

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
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
				title : "用户申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
				title : "用户申请",
				items : biddata
			});
		}
		return this.formpanel_SpecialRequire3_Input;
	},
	getFormserviceItemBasePanel : function() {
		var sra = new SRAction();
		var siId = sra.getReqServiceItemId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"serviceItemBasePanel", this);
		if (siId != '0') {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					siId);// 这是要随时变得
		} else {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					"");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//改变Combobox的id
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
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
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
				title : "服务项表单基础面板",
				items : biddata
					//,
					//buttons : this.getFormButtons
			});
		} else {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
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
				title : "服务项表单基础面板",
				items : biddata
			});
		}
		return this.formserviceItemBasePanel;
	},

	getFormpanel_SRServiceProviderInfo_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRServiceProviderInfo_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SRServiceProviderInfo_Input", this.dataId);// 这是要随时变得
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
				title : "技术总监审批面板",
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
				title : "技术总监审批面板",
				items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},

	getFormpanel_SRprojectContracts : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRprojectContracts", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr_issueByEngineer",
					"panel_SRprojectContracts", this.dataId);// 这是要随时变得
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRprojectContracts");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRprojectContracts = new Ext.form.FormPanel({
				id : 'panel_SRprojectContracts',
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
				title : "个性化需求合同实体(N)",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRprojectContracts = new Ext.form.FormPanel({
				id : 'panel_SRprojectContracts',
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
				title : "个性化需求合同实体(N)",
				items : biddata
			});
		}
		return this.formpanel_SRprojectContracts;
	},
	getFormpanel_SRAnalyse : function() {
		var sra = new SRAction();
		var praId = sra.getProjectRequireAnalyseId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse",
				this);
		if (praId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse",
					praId);// 这是要随时变得
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", "");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//改变Combobox的id
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
	getFormpanel_SRProjectPlan : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRProjectPlan", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr_issueByEngineer",
					"panel_SRProjectPlan", this.dataId);// 这是要随时变得
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRProjectPlan");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "个性化需求项目计划实体(N)",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "个性化需求项目计划实体(N)",
				items : biddata
			});
		}
		return this.formpanel_SRProjectPlan;
	},

	getFormpanel_SRTransmisEngineer : function() {
		var da = new DataAction();
		var data = null;
		var sra = new SRAction();
		var pteId = sra.getProjectTransmissionEngineerId(this.dataId);
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRTransmisEngineer", this);
		if (pteId != '0') {
			data = da.getSingleFormPanelElementsForEdit(
					"panel_SRTransmisEngineer", pteId);// 这是要随时变得
			//biddata = da.split(data);
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRTransmisEngineer", "");
			for (i = 0; i < data.length; i++) {
			if (data[i].id == 'SRTransmisEngineer$needOrNotCombo') {
				data[i].value = '0';
			}
		}
		}

		biddata = da.split(data);

		if (this.getFormButtons.length != 0) {
			this.formpanel_SRTransmisEngineer = new Ext.form.FormPanel({
				id : 'panel_SRTransmisEngineer',
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
				title : "<font color=red>个性化需求传输工程师</font>",
				items : biddata

			});
		} else {
			this.formpanel_SRTransmisEngineer = new Ext.form.FormPanel({
				id : 'panel_SRTransmisEngineer',
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
				title : "<font color=red>个性化需求传输工程师</font>",
				items : biddata
			});
		}
		return this.formpanel_SRTransmisEngineer;
	},

	getGridSRTestReportList : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		 var buttonUtil = new ButtonUtil();
		  var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",this);
		//add by lee for add attachmentfile in 20090806 begin
		var curDataId = this.dataId;
		var fileButton =  new Ext.Button({
			text : '测试附件',
			pressed : true,
			iconCls : 'file',
			handler : function() {
				Ext.Ajax.request({
					url : webContext + '/SRAction_saveTestReportFile.action',
					params : {
						dataId : curDataId
					},
					success : function(response, options) {
						var responseArray = Ext.util.JSON.decode(response.responseText);
						var attachmentFlag = responseArray.file;
						var ud = new UpDownLoadFile();
						ud.getUpDownLoadFileSu(attachmentFlag, '8016',
							'com.digitalchina.info.appframework.metadata.entity.SystemFile');
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("获取附件失败");
					}
				})
			}
		});
		getGridButtons.push(fileButton);
//add by lee for add attachmentfile in 20090806 begin
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
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
			//alert("aa"+editor.id);
			if (editor.xtype == 'datefield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else if (editor.xtype == 'textfield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					//width:300,
					editor : new Ext.form.TextField({
						fieldLabel : '测试结果',
						xtype : 'textfield',
						id : 'flm_ProjectTestReport$description',
						name : 'flm_ProjectTestReport$description',
						style : '',
						width : 500,
						value : '',
						readOnly : false,
						allowBlank : true,
						validator : '',
						vtype : ''
					})

				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					width : 300,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
			if (editor.id == 'flm_ProjectTestReport$description') {
				width : 500
			}
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRTestReport", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.gridSRTestReportList = new Ext.grid.GridPanel({
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "项目测试列表",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			height : 600,
			width : 600,// this.width - 15,
			frame : true//,
			//tbar : [getGridButtons]

		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ this.dataId + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.gridSRTestReportList;
	},
//add by lee for modify userFeedback in 20090806 begin 
	getFormpanel_SRUserFeedback_input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRUserFeedback_input",this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_issueByEngineer",
					"panel_SRUserFeedback_input", this.dataId);// 这是要随时变得	
			for (i = 0; i < data.length; i++) {
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRUserFeedback_input");
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
//add by lee for modify userFeedback in 20090806 end
	getGridpanel_SRExpendPlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRExpendPlan_list");
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
		this.store = da.getPagePanelJsonStore("panel_SRExpendPlan_list", obj);
		this.cm.defaultSortable = true;
		// var viewConfig = Ext.apply({
		// forceFit : false
		// }, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRExpendPlan_list = new Ext.grid.EditorGridPanel({
			id : 'panel_SRExpendPlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "个性化需求支出计划",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true

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

		return this.gridpanel_SRExpendPlan_list;
	},
	getGridpanel_SRIncomePlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRIncomePlan_list");
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
		this.store = da.getPagePanelJsonStore("panel_SRIncomePlan_list", obj);
		this.cm.defaultSortable = true;
		// var viewConfig = Ext.apply({
		// forceFit : true
		// }, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRIncomePlan_list = new Ext.grid.EditorGridPanel({
			id : 'panel_SRIncomePlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "个性化需求收款计划",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true

		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ this.dataId + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.gridpanel_SRIncomePlan_list;
	},
	
	//add by lee for add noticeForm in 20091102 begin
	noticeSubmit : function() {
			if(!Ext.getCmp('NoticeForm').form.isValid()){
				Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
				return false;
			}
			Ext.getCmp('noticeSubmit').disable();//按钮灰质
			var content = Ext.getCmp('NewNotice$content').getValue();
			content = Ext.encode(content);
			var info = Ext.getCmp('NoticeForm').getForm().getValues();
			var dataId = Ext.getCmp('NewNotice$id').getValue();
			var curReqId = this.dataId;
			Ext.Ajax.request({
				url : webContext + '/SRAction_saveNoticeForReq.action',//保存公告内容同时保存公告与需求关系
				method : "POST",
				params : {
					auditflag : 0,
					dataId : dataId,
					id : info.id,
					title : info.NewNotice$title,
					remark : info.NewNotice$remark,
					beginDate : info.NewNotice$beginDate,
					endDate : info.NewNotice$endDate,
					grade : info.NewNotice$grade,
					noticeType : info.NewNotice$newNoticeType,
					customer : info.NewNotice$customer,
					customerType : info.NewNotice$customerType,
					serviceProvider : info.NewNotice$serviceProvider,
					serviceProviderType : info.NewNotice$serviceProviderType,
					content : content,
					reqId : curReqId
				},
				success : function(response, options) {
					var noticeId = eval('('+response.responseText+')').newNoticeId
					Ext.getCmp('NewNotice$id').setValue(noticeId);
					var newNoticeName = eval('('+response.responseText+')').newNoticeName
					var newNoticeType = eval('('+response.responseText+')').newNoticeType
					Ext.Ajax.request({
						url : webContext + '/noticeManagerWorkflow_apply.action',
						params : {
							dataId : noticeId,
							model : this.model,
							bzparam : "{dataId :'"
									+ noticeId
									+ "',applyId : '"
									+ noticeId
									+ "',newNoticeName : '"
									+ newNoticeName
									+ "',newNoticeType : '"
									+ newNoticeType
									+ "',applyType: 'nproject',applyTypeName: '公告审批',customer:''}",
							defname : "NoticeManager1249905823812"
						},
						success : function(response, options) {
							var meg = Ext.decode(response.responseText);
							if (meg.id != undefined) {
								Ext.Msg.alert("提示", "已经成功提交，并发送给审批人", function() {
										Ext.getCmp('noticeSubmit').enable();//按钮生效
								});
							} else {
								Ext.Msg.alert("提示", "启动工作流失败", function() {
									Ext.getCmp('noticeSubmit').enable();//按钮生效
									alert(meg.Exception);
								});
							}
						},
						failure : function(response, options) {
							Ext.getCmp('noticeSubmit').enable();//按钮生效
							Ext.MessageBox.alert("提示", "启动工作流失败");
						}
					}, this);
				},
				failure : function(response, options) {
					Ext.getCmp('noticeSubmit').enable();//按钮生效
					Ext.MessageBox.alert("保存失败");
				},
				scope : this
			});
		},
		noticeSave : function() {
			if(!Ext.getCmp('NoticeForm').form.isValid()){
				Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
				return false;
			}
			var content = Ext.getCmp('NewNotice$content').getValue();
			content = Ext.encode(content);
			var info = Ext.getCmp('NoticeForm').getForm().getValues();
			var dataId = Ext.getCmp('NewNotice$id').getValue();
			var curReqId = this.dataId;
			Ext.Ajax.request({
				url : webContext + '/SRAction_saveNoticeForReq.action',//保存公告内容同时保存公告与需求关系
				method : "POST",
				params : {
					auditflag : 0,
					dataId : dataId,
					id : info.id,
					title : info.NewNotice$title,
					remark : info.NewNotice$remark,
					beginDate : info.NewNotice$beginDate,
					endDate : info.NewNotice$endDate,
					grade : info.NewNotice$grade,
					noticeType : info.NewNotice$newNoticeType,
					customer : info.NewNotice$customer,
					customerType : info.NewNotice$customerType,
					serviceProvider : info.NewNotice$serviceProvider,
					serviceProviderType : info.NewNotice$serviceProviderType,
					content : content,
					reqId : curReqId
				},
				success : function(response, options) {
					var noticeId = eval('('+response.responseText+')').newNoticeId
					Ext.getCmp('NewNotice$id').setValue(noticeId);
					Ext.MessageBox.alert("保存成功");
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("保存失败");
				},
				scope : this
			});
		},	
		getNoticeForm : function() {
		var da = new DataAction();
		var sra = new SRAction();
		var data = null;
		var pnId = sra.getProjectNoticeId(this.dataId);
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		if (pnId != '0') {
			data = da.getSingleFormPanelElementsForEdit("page_notice", pnId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("page_notice");
		}
		biddata = da.split(data);
		for(var i=0;i<biddata.length;i++ ){
			if(biddata[i].fieldLabel=='公告标题') {
				biddata[i].allowBlank = false;
				biddata[i].blankText = '此处为必填项';
			}
			if(biddata[i].fieldLabel=='公告类型') {
				biddata[i].allowBlank = false;
				biddata[i].emptyText = '';
			}
		}
		var noticeSubmit = new Ext.Button(		
		{
			text : '公告提交',
			id : 'noticeSubmit',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : this.noticeSubmit
		});
		var noticeSave = new Ext.Button({
			text : '公告暂存',
			id : 'noticeSave',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.noticeSave
		});
			this.noticeForm = new Ext.form.FormPanel({
				id : 'NoticeForm',
				layout : 'table',
				height : 'auto',
				width : 820,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "<font color=red>公告</font>",
				items : biddata,
				tbar:[noticeSave,noticeSubmit,
//add by musicbear for 在页面注释公告审批和提交审批的区别 in 2009 11 12 start 
				new Ext.Toolbar.TextItem("<font color=red>公告暂存、公告提交功能按钮独立于流程审批提交，公告提交后会自动发到公告审批人进行审批</font>"), '-',noticeSubmit
//add by musicbear for 在页面注释公告审批和提交审批的区别 in 2009 11 12 end				
				]
				
			});

		return this.noticeForm;
	},
	//add by lee for add noticeForm in 20091102 end
	
	
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var sra = new SRAction();
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
		this.removedIds = "";
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
		this.model = "nsr1_issueByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nsr1_issueByEngineer", this);
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

		this.getFormpanel_SpecialRequire3_Input();
		this.pa.push(this.formpanel_SpecialRequire3_Input);
		this.formname.push("panel_SpecialRequire3_Input");
		temp.push(this.formpanel_SpecialRequire3_Input);
//		this.getFormserviceItemBasePanel();
//		this.pa.push(this.formserviceItemBasePanel);
//		this.formname.push("serviceItemBasePanel");
//		temp.push(this.formserviceItemBasePanel);
//		this.getFormpanel_SRServiceProviderInfo_Input();
//		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
//		this.formname.push("panel_SRServiceProviderInfo_Input");
//		temp.push(this.formpanel_SRServiceProviderInfo_Input);

		this.getFormpanel_SRAnalyse();
		this.pa.push(this.formpanel_SRAnalyse);
		this.formname.push("panel_SRAnalyse");
		temp.push(this.formpanel_SRAnalyse);

		this.gridflm_projectPlanList = new projectPlanListPanel({
			dataId : this.dataId,
			rootId : ppId
		});
		temp.push(this.gridflm_projectPlanList);
//		this.getGridpanel_SRExpendPlan_list();
//		this.gd.push(this.gridpanel_SRExpendPlan_list);
//		this.gridname.push("panel_SRExpendPlan_list");
//		temp.push(this.gridpanel_SRExpendPlan_list);
//		this.getGridpanel_SRIncomePlan_list();
//		this.gd.push(this.gridpanel_SRIncomePlan_list);
//		this.gridname.push("panel_SRIncomePlan_list");
//		temp.push(this.gridpanel_SRIncomePlan_list);
		//		       
		this.getGridSRTestReportList();
		this.gd.push(this.gridSRTestReportList);
		this.gridname.push("panel_SRTestReport");
		temp.push(this.gridSRTestReportList);

//		temp.push(this.getFeedback(this.dataId, this.taskId, this.surveyId));
		this.getFormpanel_SRUserFeedback_input();
		temp.push(this.formpanel_SRUserFeedback_input);
				
		this.getFormpanel_SRTransmisEngineer();
		this.pa.push(this.formpanel_SRTransmisEngineer);
		this.formname.push("panel_SRTransmisEngineer");
		temp.push(this.formpanel_SRTransmisEngineer);

		//modify by lee for 修改公告部分BUG直接将公告面板加入 in 20091102 begin
//		this.noticeForm = new NoticeForm({
//			reqId : this.dataId,
//			dataId : pnId
//		});
		this.getNoticeForm();
		this.pa.push(this.noticeForm);
		this.formname.push("NoticeForm");
		temp.push(this.noticeForm);
		//modify by lee for 修改公告部分BUG直接将公告面板加入 in 20091102 end
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("removePlan", this.removePlan, this);
		this.on("saveForGrid", this.saveForGrid, this);
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		Ext.getCmp('SRTransmisEngineer$needOrNotCombo').on('select',
				function(combo, record, index) {
					var needOrNot = Ext.getCmp('SRTransmisEngineer$needOrNotCombo').getValue();

					if (needOrNot == '0') {
						Ext.getCmp('SRTransmisEngineer$userInfoCombo').clearValue();
						Ext.getCmp('SRTransmisEngineer$userInfoCombo').disable();
					} else {
						Ext.getCmp('SRTransmisEngineer$userInfoCombo').clearValue();
						Ext.getCmp('SRTransmisEngineer$userInfoCombo').enable();
					}
				});
		PageTemplates.superclass.initComponent.call(this);

	}

})