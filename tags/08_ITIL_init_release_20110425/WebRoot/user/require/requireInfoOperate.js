OperatePanel = Ext.extend(Ext.Panel, {
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 'auto',
	frame : true,
	// title : this.description,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	save : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('operatePanel').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}

		//remove by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		if(this.pagePanel=="panel_ERP_NormalNeed1_Input"||this.pagePanel=="panel_ERP_NormalNeed2_Input"){
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
		}
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var formParam = this.formPanel.form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = "";
		if (this.processType == 0) {
			curUrl = webContext
					+ '/requireAction_saveApplyDraft.action?pagePanel='
					+ this.pagePanel + '&serviceItemId=' + curscid;
		} else if (this.processType == 1) {
			curUrl = webContext
					+ '/requireAction_saveModifyDraft.action?pagePanel='
					+ this.pagePanel + '&serviceItemId=' + curscid;
		} else if (this.processType == 2) {
			curUrl = webContext
					+ '/requireAction_saveCancelDraft.action?pagePanel='
					+ this.pagePanel + '&serviceItemId=' + curscid;
		}
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
	submit : function() {
		if(!Ext.getCmp('operatePanel').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		if(this.pagePanel=="panel_ERP_NormalNeed1_Input"||this.pagePanel=="panel_ERP_NormalNeed2_Input"){
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
		}
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		
		var curscid = this.serviceItemId;
		var des = this.description;
		var curDataId = this.dataId;
		var curClass = this.reqClass;
		var curModel = this.model;
		var curProcessName = this.processName;
		var reqName = this.reqName;
		Ext.Ajax.request({
			url : webContext + '/requireWorkflow_applyAndAssign.action',
			params : {
				dataId : curDataId,
				model : curModel,
				bzparam : "{dataId :'" + curDataId + "',applyId : '"
						+ curDataId
						+ "',erpxzFlag : '270"
						+ "',applyType: 'rproject',applyTypeName: '" + des
						+ "',customer:'',reqClass:'" + curClass + "'"
						+ ",serviceItemId:'" + curscid + "'}",
				defname : curProcessName
			},
			success : function(response, options) {
				
				var meg = Ext.decode(response.responseText);
				if (meg.id != undefined) {
					Ext.Msg.alert("提示", "申请提交成功", function() {//modify by zhangzy in 2009 11 21
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
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// 保存并提交
	saveAndSubmit : function() {
		if(!Ext.getCmp('operatePanel').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		
		if(this.pagePanel=="panel_ERP_NormalNeed1_Input"||this.pagePanel=="panel_ERP_NormalNeed2_Input"){
			var scope1 = Ext.getCmp('ERP_NormalNeed$scopes$1').getValue();
			var scope2 = Ext.getCmp('ERP_NormalNeed$scopes$2').getValue();
			var scope3 = Ext.getCmp('ERP_NormalNeed$scopes$3').getValue();
			var scope4 = Ext.getCmp('ERP_NormalNeed$scopes$4').getValue();
			var scope5 = Ext.getCmp('ERP_NormalNeed$scopes$5').getValue();
			var scope6 = Ext.getCmp('ERP_NormalNeed$scopes$6').getValue();
			var scope7 = Ext.getCmp('ERP_NormalNeed$scopes$7').getValue();
			var scope8 = Ext.getCmp('ERP_NormalNeed$scopes$8').getValue();
//			var level = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
//			var reason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
			if(scope1==false&&scope2==false&&scope3==false&&scope4==false&&
				scope5==false&&scope6==false&&scope7==false&&scope8==false){
				Ext.MessageBox.alert("提示","请选择使用范围！");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
				return;
			}
			
//			if((level=="1"||level=="2")&&(reason=="")){
//				Ext.MessageBox.alert("提示","加急需求必须填写加急理由！");
//				Ext.getCmp('saveButton').enable();
//				Ext.getCmp('workFlowButton').enable();
//				return;
//			}
		}
//		if(this.pagePanel=="panel_SpecialRequire_Input"){
//			var level = Ext.getCmp('SpecialRequire$requireLevelCombo').getValue();
//			var reason = Ext.getCmp('SpecialRequire$reason').getValue();
//			if((level=="1"||level=="2")&&(reason=="")){
//				Ext.MessageBox.alert("提示","加急需求必须填写加急理由！");
//				Ext.getCmp('saveButton').enable();
//				Ext.getCmp('workFlowButton').enable();
//				return;
//			}
//		}
		
		var curscid = this.serviceItemId;
		var des = this.description;
		var curClass = this.reqClass;
		var curModel = this.model;
		var curProcessName = this.processName;
		var formParam = this.formPanel.form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = "";
		if (this.processType == 0) {
			curUrl = webContext
					+ '/requireAction_saveApplyDraft.action?pagePanel='
					+ this.pagePanel + '&serviceItemId=' + curscid;
		} else if (this.processType == 1) {
			curUrl = webContext
					+ '/requireAction_saveModifyDraft.action?pagePanel='
					+ this.pagePanel + '&serviceItemId=' + curscid;
		} else if (this.processType == 2) {
			curUrl = webContext
					+ '/requireAction_saveCancelDraft.action?pagePanel='
					+ this.pagePanel + '&serviceItemId=' + curscid;
		}
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
						bzparam : "{dataId :'" + curDataId + "',applyId : '" + curDataId
								+ "',applyType: 'rproject',applyTypeName: '"
								+ des + "',customer:'',reqClass:'" + curClass
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',serviceItemId:'" + curscid
								+ "',workflowHistory:'com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis',reqName:'" + reqName + "'}",
						defname : curProcessName
					},
					success : function(response, options) {
						Ext.Msg.alert("提示", "申请提交成功", function() {//modify by zhangzy in 2009 11 21
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						});
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示", "申请提交失败");
						Ext.getCmp('saveButton').enable();
						Ext.getCmp('workFlowButton').enable();
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
	getformPanel : function() {
		var da = new DataAction();
		var data = null;
		if (this.processType != 1) {
			data = da.getRequireFormPanelElementsForEdit(this.pagePanel,
					this.dataId);
		} else {
			data = da.getModifyRequireFormPanelElementsForEdit(this.pagePanel,
					this.dataId);
		}
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr.indexOf('$oldApplyCombo')) {
				data[i].listeners = {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						var val = queryEvent.combo.getValue();
						if (queryEvent.query == '') {
							param = '';
						}
						this.store.removeAll();
						this.store.load({
							params : {
								status : 2,
								start : 0
							}
						});
						return false;
					}
				}
			}
			if (idStr.indexOf('$applyNum') > 0|| idStr.indexOf('$applyUserCombo') > 0) {
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].value == '') {
					data[i].emptyText = '自动生成';
					data[i].disabled = true;
				}
			}
			for(var i = 0; i<data.length;i++){
					if(data[i].id=='ERP_NormalNeed$oldSystemLink'){
						data[i].fieldLabel='加权账期';
						data[i].readOnly  = true;
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

		var infodata = da.split(data);
		curbuttons = new Array();
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
		if(this.readOnly == "1"){
			curbuttons = new Array();
			curbuttons.push(new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>这是您已经提交的申请，不能再进行任何操作！</font>'));
		}
		this.formPanel = new Ext.form.FormPanel({
			id : 'operatePanel',
			layout : 'table',
			height : 'auto',
			width : 768,
			title : this.description,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : infodata,
			tbar : curbuttons
			//buttons : curbuttons
		});
		return this.formPanel;
	},

	items : this.items,

	initComponent : function() {
		this.id = this.dataId;
		this.cls = this.reqClass;
		this.proId = this.processId;
		var formPanel = this.getformPanel();
		if (this.status != 0) {
			var histroyForm = new HistroyForm({
				reqId : this.id,
				reqClass : this.cls,
				processId : this.proId
			});

			this.tab = new Ext.TabPanel({
				xtype : 'tabpanel',
				activeTab : 0,
				title : '事件详细信息',
				enableTabScroll : true,
				// minTabWidth:100,
				// resizeTabs : true,
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
				items : [formPanel, histroyForm]
			});

			this.items = [this.tab];
		} else {
			this.items = [formPanel];
		}
		OperatePanel.superclass.initComponent.call(this);
	}
})