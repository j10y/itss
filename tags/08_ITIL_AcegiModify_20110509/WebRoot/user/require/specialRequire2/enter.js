PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	title : '小型配置或开发需求',
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
		if(!Ext.getCmp('panel_SpecialRequire2_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept=Ext.getCmp('SpecialRequirement$flatCombo').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("提示","请填写需求名称！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(dept==""||dept==null){
			Ext.MessageBox.alert("提示","请选择申请部门！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var formParam = Ext.getCmp('panel_SpecialRequire2_Input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action?pagePanel=panel_SpecialRequire2_Input'
				+ '&serviceItemId=' + curscid;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext
						+ "/requireAction_toOperatePanel.action?id=" + curId
						+ "&serviceItemId=" + curscid + "&processType=0";
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
		if(!Ext.getCmp('panel_SpecialRequire2_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept=Ext.getCmp('SpecialRequirement$flatCombo').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("提示","请填写需求名称！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(dept==""||dept==null){
			Ext.MessageBox.alert("提示","请选择申请部门！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var formParam = Ext.getCmp('panel_SpecialRequire2_Input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action?pagePanel=panel_SpecialRequire2_Input'
				+ '&serviceItemId=' + curscid;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curDataId = responseArray.id;
				var reqName = responseArray.reqName;
				var reqCode = responseArray.applyNum;
				var reqDate = responseArray.applyDate;
				Ext.Ajax.request({
					url : webContext + '/requireWorkflow_applyAndAssign.action',
					params : {
						dataId : curDataId,
						model : curModel,
						bzparam : "{dataId :'" + curDataId 
								+ "',applyId : '"+ curDataId
								+ "',serviceItemId : '"+ curscid
								+ "',reqName : '"+ reqName
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.zsgj.itil.service.entity.ServiceItemApplyAuditHis',applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {
						//Ext.Msg.alert("提示", "启动工作流成功", function() {//remove by lee for 用户要求去掉提示
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						//});
					},
					failure : function(response, options) {
						Ext.Msg.alert("提示", "启动工作流失败", function() {
							window.location = webContext
									+ "/infoAdmin/serviceItemProcessAction.do?methodCall=toApplyPage&serviceItemId="
									+ curscid + "&processType=0" + "&dataId=" + curDataId;
						});
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);

	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},

 getFormpanel_SpecialRequire2_Input: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SpecialRequire2_Input",this);
			data = da.getRequireFormPanelElementsForEdit("panel_SpecialRequire2_Input",this.dataId);
			for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr.indexOf('$applyNum') > 0
					|| idStr.indexOf('$requireId') > 0 
					|| idStr.indexOf('$applyUser') > 0) {// add by musicbear for申请人只读限制 in 2009 11 17 
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].value == '') {
					data[i].emptyText = '自动生成';
					// data[i].readOnly = true;
					data[i].disabled = true;
				}
			}
		}
		if (this.status == 1 || this.status == 2) {
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
		if(this.status==0){
			curbuttons.push(saveButton);
			curbuttons.push(submitButton);
		}
		
		curbuttons.push(backButton);
		this.formpanel_SpecialRequire2_Input= new Ext.form.FormPanel({
			id : 'panel_SpecialRequire2_Input',
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
			title : "小型配置或开发需求",
			items : biddata,
			tbar : curbuttons
		});
		return this.formpanel_SpecialRequire2_Input;
	},
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});
		
	     this.getFormpanel_SpecialRequire2_Input();
	     
		  this.tab = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 1,
			title : '事件详细信息',
			enableTabScroll : true,
			// minTabWidth:100,
			//resizeTabs : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : [this.formpanel_SpecialRequire2_Input, histroyForm]
		});

		if (this.status != 0) {
			this.items = [this.tab];
		} else {
			this.items = [this.formpanel_SpecialRequire2_Input];
		}
		   PageTemplates.superclass.initComponent.call(this);
	
	}
})