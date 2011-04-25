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
	save : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('panel_ServerManage_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end		
		//add by zhangzy for 必填字段可以选择“无” in 20091126 start
		var confirmUser = Ext.getCmp('ServerManage$confirmUserCombo').getRawValue();
		if(confirmUser==''||confirmUser==null||confirmUser==""||confirmUser=="无"){
			Ext.MessageBox.alert("提示","部门审批人是必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by zhangzy for 必填字段可以选择“无” in 20091126 end	
		if(Ext.getCmp('ServerManage$serverTypeCombo').getValue()==2&&Ext.getCmp('ServerManage$serverPort').getValue()==""){
			Ext.MessageBox.alert("提示","服务器类型为Internet应用服务器时,请填写服务器端口。谢谢您合作！");
		 	return false;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_ServerManage_Input',false));		


		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action';
		Ext.Ajax.request({
			url : curUrl,
			params:{
				pagePanel:'panel_ServerManage_Input',
				info:formParam,
				serviceItemId:curscid
			},

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext
						+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
						+ curscid;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// 保存并提交
	saveAndSubmit : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('panel_ServerManage_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		//add by zhangzy for 必填字段可以选择“无” in 20091126 start
		var confirmUser = Ext.getCmp('ServerManage$confirmUserCombo').getRawValue();
		if(confirmUser==''||confirmUser==null||confirmUser==""||confirmUser=="无"){
			Ext.MessageBox.alert("提示","部门审批人是必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by zhangzy for 必填字段可以选择“无” in 20091126 end
		if(Ext.getCmp('ServerManage$serverTypeCombo').getValue()==2&&Ext.getCmp('ServerManage$serverPort').getValue()==""){
			Ext.MessageBox.alert("提示","服务器类型为Internet应用服务器时,请填写服务器端口。谢谢您合作！");
		 	return false;
		}			
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curModel = this.model;
		var curProcessName = this.processName;
		//		var tempRequireLevel = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
		//		var tempReason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
		//		if(tempRequireLevel==1&&tempReason==""){
		//			Ext.MessageBox.alert("提示","加急申请必须填写加急理由！");
		//			Ext.getCmp('saveButton').enable();
		//			Ext.getCmp('workFlowButton').enable();
		//			return;
		//		}
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_ServerManage_Input',false));		


		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action';
		Ext.Ajax.request({
			url : curUrl,
			params:{
				pagePanel:'panel_ServerManage_Input',
				info:formParam,
				serviceItemId:curscid
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curDataId = responseArray.id;
				var reqName = responseArray.reqName;
				var reqCode = responseArray.applyNum;
				var reqDate = responseArray.applyDate;
				Ext.Ajax.request({
					url : webContext + '/requireWorkflow_applyForServerManage.action',
					params : {
						dataId : curDataId,
						model : curModel,
						bzparam : "{dataId :'"+ curDataId
								+ "',applyId : '"+ curDataId
								+ "',serviceItemId : '"+ curscid
								+ "',reqName : '"+ reqName
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis'"
								+ ",applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {

						var meg = Ext.decode(response.responseText);
						if (meg.id != undefined) {
							Ext.Msg.alert("提示", "申请提交成功", function() {//modify by zhangzy for 用户要求添加提示 in 2009 11 24								window.location = webContext
								window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
							});
						} else {
							Ext.Msg.alert("提示", "申请提交失败", function() {
								alert(meg.Exception);
							});
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示", "申请提交失败");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("申请提交失败");
			}
		}, this);
	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
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
		data = da.getRequireFormPanelElementsForEdit(
				"panel_ServerManage_Input", this.dataId);
				
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr.indexOf('$applyNum') > 0
					|| idStr.indexOf('$applyUser') > 0
					|| idStr.indexOf('$applyDate') > 0) {
				data[i].readOnly = true;
				data[i].cls = "x-form-field-wrap";
				if (data[i].value == '') {
					data[i].emptyText = '自动生成';
					// data[i].readOnly = true;
					data[i].disabled = true;
				}
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].hideTrigger = true;
				}
			}
		}
		if (this.status == 1 || this.status == 2) {//add by zhangzy for 在非申请页面，元素设为只读 in 2009 11 25
			for (i = 0; i < data.length; i++) {
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].id = data[i].id + 8;// 改变Combobox的id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				data[i].readOnly = true;
			}
		}
		biddata = da.split(data);		
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '保存为草稿',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.save
		});
		var submitButton = new Ext.Button({
			text : '保存并提交',
			id : 'workFlowButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : this.saveAndSubmit
		});
		var backButton = new Ext.Button({
			text : '返回',
			id : 'refresh',
			pressed : true,
			iconCls : 'back',
			scope : this,
			handler : this.back
		});
		if (this.status == 0) {
			curbuttons.push(saveButton);
			curbuttons.push(submitButton);
		}
		curbuttons.push(backButton);
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
			tbar : curbuttons
		});

		return this.formpanel_ServerManage_Input;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
//		var items = new Array();
//		var pa = new Array();
//		this.pa = pa;
//		var gd = new Array();
//		this.gd = gd;
//		var temp = new Array();
//		this.temp = temp;
//		var formname = new Array();
//		this.formname = formname;
//		var gridname = new Array();
//		this.gridname = gridname;
//		this.model = "serverManage1";
//		var buttonUtil = new ButtonUtil();
//		this.mybuttons = buttonUtil.getButtonForModel("serverManage1", this);
//		if (this.mybuttons != "") {
//			this.buttons = {
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				style : 'margin:4px 6px 4px 300px',
//				align : 'center',
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				items : this.mybuttons
//			};
//		} else {
//			this.buttons = {
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				style : 'margin:4px 6px 4px 300px',
//				align : 'center',
//				defaults : {
//					bodyStyle : 'padding:4px'
//				}
//			};
//		}

		this.getFormpanel_ServerManage_Input();
		this.items = [this.formpanel_ServerManage_Input];
//modify by zhangzy for “我审批的” 无法打开 in 2009 11 25 start
		if (this.status != 0) {
			var spId = this.serviceItemProcessId;
			var histroyForm = new HistroyForm({
				reqId : this.dataId,
				reqClass : "com.digitalchina.itil.require.entity.ServerManage"
			});
			this.tab = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 1,
			enableTabScroll : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			defaults : {
				autoHeight : true,
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : [this.formpanel_ServerManage_Input, histroyForm]
		});
			this.items = [this.tab];
			
		} else {
			this.items = [this.formpanel_ServerManage_Input];
		}
//modify by zhangzy for “我审批的” 无法打开 in 2009 11 25 end
		PageTemplates.superclass.initComponent.call(this);
	}
})