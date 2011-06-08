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
		var taskId = this.taskId;
		if (!Ext.getCmp('panel_srInfo_input4').form.isValid()) {
			Ext.MessageBox.alert("提示", "页面中带红色波浪线的为必填项,请填写完整必填项.");
			return false;
		}
		var formParam = Ext.encode(getFormParam('panel_srInfo_input4'));
		var curDataId = this.dataId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveSpecialRequirementInfo.action',
			params : {
				info:formParam,
				reqId:curDataId
			},
			success : function(response, options) {
//remove by zhangzy for‘用户要求去掉动态指派传输工程师操作，改为后台预指派（也就是在后台工作流配置角色） start				
//				var tEngineer = Ext.getCmp('itil_req_SpecialRequirementInfo$transmisEngineerCombo').getValue();
//				Ext.Ajax.request({
//					url : webContext
//							+ '/extjs/workflow?method=getData&taskId='
//							+ taskId + '&users=transmitByEngineer:'+ tEngineer,
//					method : 'post',
//					success : function(response, options) {
//						window.parent.auditContentWin.specialAudit();
//					},
//					failure : function(response, options) {
//						Ext.MessageBox.alert("指派传输工程师失败！");
//					}
//				})
//remove by zhangzy for‘用户要求去掉动态指派传输工程师操作，改为后台预指派（也就是在后台工作流配置角色） end

				window.parent.auditContentWin.specialAudit();	
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	
	getTabpanel : function(tab, tabTitle) {
		var tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return tabPanel;

	},
	getPanel : function(appa, panelTitle) {
		var Panel = new Ext.Panel({
			id : Ext.id(),
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
		return Panel;

	},

	getFormpanel_SpecialRequireDevConfirm_Input : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_issueByEngineer",
					"panel_SpecialRequireDevConfirm_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequireDevConfirm_Input");
		}
		for(var i=0; i<data.length; i++){			
			//add by awen for changge fieldLable on 2011-06-08 begin
			if (data[i].id=='SpecialRequirement$confirmUserCombo') {
				data[i].fieldLabel = '部门审批人';
				data[i].readOnly = true;				
				data[i].hideTrigger = true;					
			}
			//add by awen for changge fieldLable on 2011-06-08 end
		}
		biddata = da.splitForReadOnly(data);
		var formpanel_SpecialRequireDevConfirm_Input = new Ext.form.FormPanel({
			id : 'panel_SpecialRequireDevConfirm_Input',
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
			title : "开发类需求审批面板",
			items : biddata
		});
		return formpanel_SpecialRequireDevConfirm_Input;
	},
	getFormpanel_srInfo_input4 : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_issueByEngineer",
					"panel_srInfo_input4", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input4");
		}
		
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			
			if(data[i].id!="itil_req_SpecialRequirementInfo$transmisEngineerCombo"&&
			data[i].id!="itil_req_SpecialRequirementInfo$isTransmisCombo"&&
			data[i].id!="itil_req_SpecialRequirementInfo$manHour"){
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].xtype == "panel") {
				/*	var dd = Ext.encode(data[i]).replace(/display:/g,
							"display:none").replace("\"disabled\":false",
							"\"disabled\":true");
					data[i] = Ext.decode(dd);*/
				}
			}
			if(data[i].id=="itil_req_SpecialRequirementInfo$transContent"){
				data[i].setWidth(9999);	
				data[i].readOnly = false;
			}				
			if(i==9){
				modifyData.push(this.getGridpanel_SRTestReport());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);
		//biddata = da.splitForReadOnly(data);
		var formpanel_srInfo_input4 = new Ext.form.FormPanel({
			id : 'panel_srInfo_input4',
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
			title : "需求分析",
			items : biddata
		});
		return formpanel_srInfo_input4;
	},
	getGridpanel_SRTestReport : function() {
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",
				this);

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
		var gridpanel_SRTestReport = new Ext.grid.GridPanel({
			fieldLabel : '测试信息',
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			collapsible : true,
			autoScroll : true,
			height : 120,
			width : 9999,// this.width - 15,
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
		return gridpanel_SRTestReport;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});
		
		var items = new Array();
		var temp = new Array();
		this.model = "devsr_issueByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("devsr_issueByEngineer",this);
		this.buttons = new Array();
		if (this.readOnly != "1") {
			this.buttons = this.mybuttons;
		}
		temp.push(this.getFormpanel_SpecialRequireDevConfirm_Input());
		temp.push(this.getFormpanel_srInfo_input4());
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	//是否传输选择“否”时，改变传输工程师只读属性			
//		Ext.getCmp('itil_req_SpecialRequirementInfo$isTransmisCombo').on('select',function(){
//			var isTrans = Ext.getCmp('itil_req_SpecialRequirementInfo$isTransmisCombo').getValue();
//			var transmis = Ext.getCmp('itil_req_SpecialRequirementInfo$transmisEngineerCombo');	
//				if(isTrans == 0){
//					transmis.disable();
//				}else if(isTrans == 1){
//					transmis.enable();
//				}
//		});	
	//是否传输选择“否”时，改变传输工程师只读属性		
	}
})