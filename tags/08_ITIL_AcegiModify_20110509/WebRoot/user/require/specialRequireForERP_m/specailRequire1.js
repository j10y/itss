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
		var name=Ext.getCmp('SpecialRequirement$name').getValue();
		if(name==""){
		Ext.Msg.alert("提示","请输入需求名称");
		return;
		}
		if(!Ext.getCmp('panel_erpSR_enter_m_input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}		

		var curscid = this.serviceItemId;
		var formParam = Ext.getCmp('panel_erpSR_enter_m_input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action?pagePanel=panel_erpSR_enter_m_input'
				+ '&serviceItemId=' + curscid;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext//modify by zhangzy for 保存草稿后跳转 in 2009 11 21
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	// 保存并提交
	saveAndSubmit : function() {
		var name=Ext.getCmp('SpecialRequirement$name').getValue();
		if(name==""){
		Ext.Msg.alert("提示","请输入需求名称");
		return;
		}
		if(!Ext.getCmp('panel_erpSR_enter_m_input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}		
		if(Ext.getCmp('SpecialRequirement$groupFinanceManagerCombo').getRawValue()=="无"){//add by zhangzy for 申请选择“无”时bug
			Ext.MessageBox.alert("提示","请选择集团财务经理！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(Ext.getCmp('SpecialRequirement$homeFinanceManagerCombo').getRawValue()=="无"){//add by zhangzy for 申请选择“无”时bug
			Ext.MessageBox.alert("提示","请选择本部财务经理！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(Ext.getCmp('SpecialRequirement$confirmUserCombo').getRawValue()=="无"){//add by zhangzy for 申请选择“无”时bug
			Ext.MessageBox.alert("提示","请选择IT审核人！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		Ext.getCmp('workFlowButton').disable();
		Ext.getCmp('tempSave').disable();
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var curStatus = this.status;
		var curProcessType = this.processType;
		var name=Ext.getCmp('SpecialRequirement$name').getValue()
		var formParam = Ext.getCmp('panel_erpSR_enter_m_input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveModifyDraft.action?' 
				+ 'pagePanel=panel_erpSR_enter_m_input'
				+ '&serviceItemId=' + curscid
				+ '&processType=' + curProcessType
				+ '&status=' + curStatus;
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
						Ext.Msg.alert("提示", "申请提交成功", function() {
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						});
					},
					failure : function(response, options) {
						Ext.Msg.alert("提示", "申请提交失败", function() {
							window.location = webContext
									+ "/infoAdmin/serviceItemProcessAction.do?methodCall=toApplyPage&serviceItemId="
									+ curscid + "&processType=0" + "&dataId=" + curDataId;
						});
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
	
 getFormpanel_erpSR_enter_m_input: function() {
 	var pName = this.processName;
 	
 	  var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		data = da.getModifyRequireFormPanelElementsForEdit("panel_erpSR_enter_m_input",this.dataId,this.oldId);
		
			for (i = 0; i < data.length; i++) {
				if (data[i].id=='SpecialRequirement$applyNum'|| data[i].id=='SpecialRequirement$applyUserCombo'){// add by musicbear for申请人只读限制 in 2009 11 17 
					data[i].readOnly = true;
					data[i].hideTrigger = true;
						if (data[i].value == '') {
							data[i].emptyText = '自动生成';
							// data[i].readOnly = true;
							data[i].disabled = true;
						}
					}
				//add by lee for 变换审批人名称 in 20091203 begin
				if(data[i].id=='SpecialRequirement$confirmUserCombo'){
					data[i].fieldLabel = 'IT审核人'
				}
				//add by lee for 变换审批人名称 in 20091203 end
			var idStr = data[i].id + "";				
			if (idStr=='SpecialRequirement$groupFinanceManagerCombo' 
				|| idStr=='SpecialRequirement$homeFinanceManagerCombo'
				|| idStr=='SpecialRequirement$confirmUserCombo') {
					data[i].readOnly = true;				// add by zhangzy for 字段设为只读 in 2009 12 09
					data[i].hideTrigger = true;					
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
			}
			biddata = da.split(data);
		
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '保存为草稿',
			id : 'tempSave',				// add by zhangzy for 单击提交按钮时 按钮变灰 in 2009 12 7
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
		this.formpanel_erpSR_enter_m_input = new Ext.form.FormPanel({
			id : 'panel_erpSR_enter_m_input',
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
			title : "ERP非常规变更需求申请",
			items : biddata,
			tbar : curbuttons
		});

	
		return this.formpanel_erpSR_enter_m_input;
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
			items : [this.getFormpanel_erpSR_enter_m_input(), histroyForm]
		});

		if (this.status != 0) {
			this.items = [this.tab];
		} else {
			this.items = [this.getFormpanel_erpSR_enter_m_input()];
		}
		PageTemplates.superclass.initComponent.call(this);
		var spId = this.serviceItemProcessId;	
		var curDataId = this.dataId;		
		Ext.getCmp('SpecialRequirement$oldApplyCombo').addListener('collapse',
				function(box) {
					var newv = box.getValue();
					if (newv != ""&&curDataId=="") {
						window.location = webContext + "/requireAction_toProcessEnterPage.action?serviceItemProcessId="+spId+"&oldId="+newv;
					}
		});		   
		   //SBU本部选择是带出相应审批人
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
							var cadreFinanceAuditId = responseArray.cadreFinanceAuditId;
							var groupFinanceAuditId = responseArray.groupFinanceAuditId;
							Ext.getCmp('SpecialRequirement$groupFinanceManagerCombo').setValue(groupFinanceAuditId);
							Ext.getCmp('SpecialRequirement$groupFinanceManagerCombo').initComponent();
							Ext.getCmp('SpecialRequirement$homeFinanceManagerCombo').setValue(cadreFinanceAuditId);
							Ext.getCmp('SpecialRequirement$homeFinanceManagerCombo').initComponent();
							Ext.getCmp('SpecialRequirement$confirmUserCombo').setValue(clientItManagerId);
							Ext.getCmp('SpecialRequirement$confirmUserCombo').initComponent();
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("获取SBU审批人失败");
						}
					}, this);					
				});		
	}
})