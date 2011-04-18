PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	title : this.description,
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
		if(!Ext.getCmp('panel_ERP_NormalNeed1_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		if(Ext.getCmp('ERP_NormalNeed$flatCombo').getRawValue()=="无"){//add by zhangzy for 申请选择“无”时bug
			Ext.MessageBox.alert("提示","请选择所属SBU/本部！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		
		var scope1 = Ext.getCmp('ERP_NormalNeed$scopes$1').getValue();
		var scope2 = Ext.getCmp('ERP_NormalNeed$scopes$2').getValue();
		var scope3 = Ext.getCmp('ERP_NormalNeed$scopes$3').getValue();
		var scope4 = Ext.getCmp('ERP_NormalNeed$scopes$4').getValue();
		var scope5 = Ext.getCmp('ERP_NormalNeed$scopes$5').getValue();
		var scope6 = Ext.getCmp('ERP_NormalNeed$scopes$6').getValue();
		var scope7 = Ext.getCmp('ERP_NormalNeed$scopes$7').getValue();
		var scope8 = Ext.getCmp('ERP_NormalNeed$scopes$8').getValue();
		if(scope1==false&&scope2==false&&scope3==false&&scope4==false&&
			scope5==false&&scope6==false&&scope7==false&&scope8==false){
			Ext.MessageBox.alert("提示","请选择使用范围！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var formParam = Ext.encode(getFormParam('panel_ERP_NormalNeed1_Input'));
		var curscid = this.serviceItemId;
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				info:formParam,
				pagePanel:'panel_ERP_NormalNeed1_Input',
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
		if(!Ext.getCmp('panel_ERP_NormalNeed1_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		if(Ext.getCmp('ERP_NormalNeed$flatCombo').getRawValue()=="无"){//add by zhangzy for 申请选择“无”时bug
			Ext.MessageBox.alert("提示","请选择所属SBU/本部！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curModel = this.model;
		var curProcessName = this.processName;
		var scope1 = Ext.getCmp('ERP_NormalNeed$scopes$1').getValue();
		var scope2 = Ext.getCmp('ERP_NormalNeed$scopes$2').getValue();
		var scope3 = Ext.getCmp('ERP_NormalNeed$scopes$3').getValue();
		var scope4 = Ext.getCmp('ERP_NormalNeed$scopes$4').getValue();
		var scope5 = Ext.getCmp('ERP_NormalNeed$scopes$5').getValue();
		var scope6 = Ext.getCmp('ERP_NormalNeed$scopes$6').getValue();
		var scope7 = Ext.getCmp('ERP_NormalNeed$scopes$7').getValue();
		var scope8 = Ext.getCmp('ERP_NormalNeed$scopes$8').getValue();
		if(scope1==false&&scope2==false&&scope3==false&&scope4==false&&
			scope5==false&&scope6==false&&scope7==false&&scope8==false){
			Ext.MessageBox.alert("提示","请选择使用范围！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var tempApplyName =  Ext.getCmp('ERP_NormalNeed$name').getValue();
		if(tempApplyName==""){
			Ext.MessageBox.alert("提示","请填写标题！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var tempRequireLevel = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
		var tempReason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
		if(tempRequireLevel==1&&tempReason==""){
			Ext.MessageBox.alert("提示","加急申请必须填写加急理由！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var formParam = Ext.encode(getFormParam('panel_ERP_NormalNeed1_Input'));
		var curscid = this.serviceItemId;
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				info:formParam,
				pagePanel:'panel_ERP_NormalNeed1_Input',
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
						bzparam : "{dataId :'" + curDataId + "',applyId : '"
								+ curDataId + "',serviceItemId : '" + curscid
								+ "',erpxzFlag : '270"
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis'"
								+ ",applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {
				
						var meg = Ext.decode(response.responseText);
						if (meg.id != undefined) {
							Ext.Msg.alert("提示", "申请提交成功", function() {//modify by zhangzy for 用户要求添加申请成功提示  2009 11 23
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

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		data = da.getRequireFormPanelElementsForEdit(
				"panel_ERP_NormalNeed1_Input", this.dataId);
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if(idStr.indexOf('$requirementLevelCombo') > 0 && data[i].value==''){
				data[i].value='4';
			}
			if(idStr=='ERP_NormalNeed$flatCombo'){
					data[i].store = new Ext.data.JsonStore({
					url:webContext+"/extjs/comboDataAction?clazz=com.digitalchina.itil.require.entity.RequireApplyDefaultAudit&orderBy=sortNum&deleteFlag=0&enable=1",
					fields:['id','departmentName'],
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}

			if (idStr.indexOf('$applyNum') > 0|| idStr.indexOf('$applyUser') > 0
					//|| idStr.indexOf('$applyDate') > 0
			) {
				data[i].readOnly = true;
				data[i].cls = "x-form-field-wrap";
				//data[i].disabled = true;
				if (data[i].value == '') {
					data[i].emptyText = '自动生成';
					// data[i].readOnly = true;
					data[i].disabled = true;
				}
				if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
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
		//var descStr = this.description;
		this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed1_Input',
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
			title : this.description,
			items : biddata,
			tbar:curbuttons 	
			//buttons : curbuttons
		});

		return this.formpanel_ERP_NormalNeed1_Input;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		this.getFormpanel_ERP_NormalNeed1_Input();
//		if (this.status != 0) {
//			var histroyForm = new HistroyForm({
//				reqId : this.dataId,
//				reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
//			});
//			this.tab = new Ext.TabPanel({
//			xtype : 'tabpanel',
//			activeTab : 1,
//			enableTabScroll : true,
//			// minTabWidth:100,
//			// resizeTabs : true,
//			deferredRender : false,
//			frame : true,
//			plain : true,
//			border : false,
//			// tabPosition:'bottom',
//			baseCls : 'x-plain',// 是否设置和背景色同步
//			width : 900,
//			// bodyBorder : true,
//			defaults : {
//				autoHeight : true,
//				autoHeight : true,
//				bodyStyle : 'padding:2px'
//			},
//			items : [this.formpanel_ERP_NormalNeed1_Input, histroyForm]
//			});
//			this.items = [this.tab];
//		} else {
//			this.items = [this.formpanel_ERP_NormalNeed1_Input];
//		}

		 var items = new Array();		
		 this.model = "nr_nomalNeedApply1";
//add by zhangzy for 改变“申请内容”组件高度 in 2009 11 25 start		
		 if(Ext.getCmp("ERP_NormalNeed$otherInfo").getValue()!=null){
		 	Ext.getCmp("ERP_NormalNeed$otherInfo").height = 80;
		 }
//add by zhangzy for 改变“申请内容”组件高度 in 2009 11 25 end
		 
//modify by zhangzy for “我审批的” 无法打开 in 2009 11 25 start
		if (this.status != 0) {
			var spId = this.serviceItemProcessId;
			var histroyForm = new HistroyForm({
				reqId : this.dataId,
				reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
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
			items : [this.formpanel_ERP_NormalNeed1_Input, histroyForm]
		});
			this.items = [this.tab];
			
		} else {
			this.items = [this.formpanel_ERP_NormalNeed1_Input];
		}
//modify by zhangzy for “我审批的” 无法打开 in 2009 11 25 end	 
		if(this.dataId==""&&this.serviceItemId=='143'){//付款条款申请时加必填
			Ext.getCmp('ERP_NormalNeed$otherInfo').setValue("请填写账期比例（必填）");
		}
		PageTemplates.superclass.initComponent.call(this);
	}
})