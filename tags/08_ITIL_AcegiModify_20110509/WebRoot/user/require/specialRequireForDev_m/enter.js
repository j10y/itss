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
		if (!Ext.getCmp('panel_SpecialRequireDev_m_Input').form.isValid()) {
			Ext.MessageBox.alert("提示", "页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
			return false;
		}
		var deptName=Ext.getCmp('SpecialRequirement$flatCombo').getRawValue();	
		var ItName=Ext.getCmp('SpecialRequirement$confirmUserCombo').getRawValue();			
		if(deptName=="无"){
			Ext.MessageBox.alert("提示","请选择所属SBU/本部！");
			return;
		}
		if(ItName=="无"){
			Ext.MessageBox.alert("提示","请选择IT审批人！");
			return;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept = Ext.getCmp('SpecialRequirement$confirmUserCombo').getValue();
		if (name == "" || name == null) {
			Ext.MessageBox.alert("提示", "请填写需求名称！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if (dept == "" || dept == null) {
			Ext.MessageBox.alert("提示", "请选择部门审批人！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_SpecialRequireDev_m_Input',false));		

		Ext.Ajax.request({
			url:webContext + '/requireAction_saveApplyDraft.action',
			params:{
				pagePanel:'panel_SpecialRequireDev_m_Input',
				info:formParam,
				serviceItemId:curscid
			},

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
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
		if (!Ext.getCmp('panel_SpecialRequireDev_m_Input').form.isValid()) {
			Ext.MessageBox.alert("提示", "页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
			return false;
		}
		var deptName=Ext.getCmp('SpecialRequirement$flatCombo').getRawValue();	
		var ItName=Ext.getCmp('SpecialRequirement$confirmUserCombo').getRawValue();			
		if(deptName=="无"){
			Ext.MessageBox.alert("提示","请选择所属SBU/本部！");
			return;
		}
		if(ItName=="无"){
			Ext.MessageBox.alert("提示","请选择IT审批人！");
			return;
		}					
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept = Ext.getCmp('SpecialRequirement$confirmUserCombo').getValue();
		if (name == "" || name == null) {
			Ext.MessageBox.alert("提示", "请填写需求名称！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if (dept == "" || dept == null) {
			Ext.MessageBox.alert("提示", "请选择部门审批人！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var formParam = Ext.encode(getFormParam('panel_SpecialRequireDev_m_Input',false));		

		Ext.Ajax.request({
			url:webContext + '/requireAction_saveApplyDraft.action',
			params:{
				pagePanel:'panel_SpecialRequireDev_m_Input',
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
					url : webContext + '/requireWorkflow_applyAndAssign.action',
					params : {
						dataId : curDataId,
						model : curModel,
						bzparam : "{dataId :'" + curDataId
								+ "',applyId : '" + curDataId
								+ "',serviceItemId : '" + curscid
								+ "',reqName : '" + reqName
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.zsgj.itil.service.entity.ServiceItemApplyAuditHis',applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {
						Ext.Msg.alert("提示", "提交申请成功", function() {
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						});
					},
					failure : function(response, options) {
						Ext.Msg.alert("提示", "启动工作流失败", function() {
							window.location = webContext
									+ "/infoAdmin/serviceItemProcessAction.do?methodCall=toApplyPage&serviceItemId="
									+ curscid + "&processType=1" + "&dataId=" + curDataId;
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

	getFormpanel_SpecialRequireDev_m_Input : function() {
		var curSi = this.serviceItemId;
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		data = da.getModifyRequireFormPanelElementsForEdit( "panel_SpecialRequireDev_m_Input", this.dataId,this.oldId);
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr=='SpecialRequirement$applyNum'|| idStr=='SpecialRequirement$applyUserCombo') {
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].value == '') {
					data[i].emptyText = '自动生成';
					data[i].disabled = true;
				}
			}
			if(idStr=='SpecialRequirement$appConfigItemCombo'){
				data[i].store = new Ext.data.JsonStore({
					url:webContext+'/SRAction_getAppConfigItemComboData.action?appTypeId=16',//应用软件类配置项
					fields:['id','name'],
					listeners:{
						beforeload : function(store, opt){
							if(opt.params['SpecialRequirement$appConfigItemCombo'] == undefined){
								opt.params['name'] =Ext.getCmp('SpecialRequirement$appConfigItemCombo').defaultParam;
							}
						}
					},
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}
			if(idStr=='SpecialRequirement$flatCombo'){
					data[i].store = new Ext.data.JsonStore({
					url:webContext+"/extjs/comboDataAction?clazz=com.zsgj.itil.require.entity.RequireApplyDefaultAudit&orderBy=sortNum&deleteFlag=0&enable=1",
					fields:['id','departmentName'],
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}			
			if (idStr=='SpecialRequirement$confirmUserCombo') {
					data[i].fieldLabel = 'IT审批人';
					data[i].readOnly = true;				// add by zhangzy for 字段设为只读 in 2009 12 09
					data[i].hideTrigger = true;					
			}
			if(idStr=='SpecialRequirement$oldApplyCombo'){
					data[i].store = new Ext.data.JsonStore({
						url:webContext+'/extjs/comboDataAction?clazz=com.zsgj.itil.require.entity.SpecialRequirement',
						fields:['id','name'],
						listeners:{
							beforeload : function(store, opt){
								opt.params['serviceItem'] =curSi;
								opt.params['status'] =1;
								if(opt.params['SpecialRequirement$oldApply'] == undefined){
									opt.params['name'] =Ext.getCmp('SpecialRequirement$oldApplyCombo').defaultParam;
								}
							}
						},
						totalProperty:'rowCount',
						root:'data',
						id:'id'
					});
					data[i].initComponent();
				}
		}
		if (this.status == 1 || this.status == 2) {
			biddata = da.splitForReadOnly(data);
		}else{
			biddata = da.split(data);
		}
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
		this.formpanel_SpecialRequireDev_m_Input = new Ext.form.FormPanel({
			id : 'panel_SpecialRequireDev_m_Input',
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
			title : "开发类IT需求",
			items : biddata,
			tbar : curbuttons
		});
		return this.formpanel_SpecialRequireDev_m_Input;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		this.getFormpanel_SpecialRequireDev_m_Input();
		this.items = [this.formpanel_SpecialRequireDev_m_Input];
		PageTemplates.superclass.initComponent.call(this);
//所属SBU/本部 选择后 连带选择“IT审批人” start		
		Ext.getCmp('SpecialRequirement$flatCombo').on('select',function(){	
					var flatId = Ext.getCmp('SpecialRequirement$flatCombo').getValue();				
					var curUrl = webContext
							+ '/requireAction_selectConfirmUserByFlat.action'
							+ '?flatId=' + flatId;					
					Ext.Ajax.request({
						url : curUrl,			
						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							var clientItManagerId = responseArray.clientItManagerId;
							if(clientItManagerId!='0'){
								Ext.getCmp('SpecialRequirement$confirmUserCombo').setValue(clientItManagerId);
								Ext.getCmp('SpecialRequirement$confirmUserCombo').initComponent();
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("获取IT审批人失败");
						}
					}, this);					
				});		
//所属SBU/本部 选择后 连带选择“IT审批人” end	
//应用系统 选择后 连带选择“应用管理员” start		
		Ext.getCmp('SpecialRequirement$appConfigItemCombo').on('select',function(){	
					var appConfigItemId = Ext.getCmp('SpecialRequirement$appConfigItemCombo').getValue();				
					var curUrl = webContext
							+ '/requireAction_selectAppManagerByAppConfigItem.action'
							+ '?appConfigItemId=' + appConfigItemId;	
					Ext.Ajax.request({
						url : curUrl,			
						success : function(response, options) {
							var responseArray = Ext.util.JSON.decode(response.responseText);
							var appManagerId = responseArray.appManagerId;
							if(appManagerId != '0' && appManagerId != ""){
								Ext.getCmp('SpecialRequirement$appManagerCombo').setValue(appManagerId);
								Ext.getCmp('SpecialRequirement$appManagerCombo').initComponent();
							}
						}, 
						failure : function(response, options) {
							Ext.MessageBox.alert("获取应用管理员失败");
						}
					}, this);					
				});		
//应用系统 选择后 连带选择“应用管理员” end
		var curDataId = this.dataId;
		var spId = this.serviceItemProcessId;
		Ext.getCmp('SpecialRequirement$oldApplyCombo').addListener('collapse',
			function(box) {
				var newv = box.getValue();
				if (newv != ""&&curDataId=="") {
					window.location = webContext + "/requireAction_toProcessEnterPage.action?serviceItemProcessId="+spId+"&oldId="+newv;
				}
		});

	}
})