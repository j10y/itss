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
	getOldApply : function(){
		var curSi = this.serviceItemId;
		var oldApply = new Ext.form.ComboBox({
			xtype:'combo',
			hiddenName: 'SpecialRequirement$oldApply',
			id :'SpecialRequirement$oldApplyCombo',
			width:'null',
			style:'',
			fieldLabel:'变更前申请',
			lazyRender: true,
			displayField: 'name',
			valueField :'id',
			emptyText:'请选择...',
			allowBlank:false,
			typeAhead:true,
			name:'SpecialRequirement$oldApply',
			triggerAction:'all',
			minChars :50,
			queryDelay : 700,
			store:new Ext.data.JsonStore({
				url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.require.entity.SpecialRequirement&serviceItem='+curSi+"&status=1",
				fields:['id','name'],
				listeners:{
					beforeload : function(store, opt){
						if(opt.params['SpecialRequirement$oldApply'] == undefined){
							opt.params['name'] =Ext.getCmp('SpecialRequirement$oldApplyCombo').defaultParam;
						}
					}
				},
				totalProperty:'rowCount',
				root:'data',
				id:'id'
			}),
			pageSize:10,
			listeners:{
				'beforequery' : function(queryEvent){
					var param = queryEvent.combo.getRawValue();
					this.defaultParam = param;
					if(queryEvent.query==''){
						param='';
					}
					this.store.load({
						params:{
							name:param,
							start:0
						}
					});
					return true;
				}
			},
			initComponent : function() {
				this.store.load({
					params:{
						id:Ext.getCmp('SpecialRequirement$oldApplyCombo').getValue(),
						start:0
					},
				callback:function(r, options, success){
					Ext.getCmp('SpecialRequirement$oldApplyCombo').setValue(Ext.getCmp('SpecialRequirement$oldApplyCombo').getValue());
				}
			});
		}});
		return oldApply;
	},
	save : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('panel_SpecialRequire3_m_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var curStatus = this.status;
		var curProcessType = this.processType;
		var name=Ext.getCmp('SpecialRequirement$name').getValue()
		if(name==""){
		Ext.Msg.alert("提示","请输入需求名称");
		return;
		}
		var curscid = this.serviceItemId;
		var formParam = Ext.getCmp('panel_SpecialRequire3_m_Input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveModifyDraft.action?' 
				+ 'pagePanel=panel_SpecialRequire3_m_Input'
				+ '&serviceItemId=' + curscid
				+ '&processType=' + curProcessType
				+ '&status=' + curStatus;
		Ext.Ajax.request({
			url : curUrl,
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext
						+ "/infoAdmin/serviceItemProcessAction.do?methodCall=toApplyPage&serviceItemId="
						+ curscid + "&processType=0" + "&dataId=" + curId;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	// 保存并提交
	saveAndSubmit : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(!Ext.getCmp('panel_SpecialRequire3_m_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var curStatus = this.status;
		var curProcessType = this.processType;
		var name=Ext.getCmp('SpecialRequirement$name').getValue()
		if(name==""){
		Ext.Msg.alert("提示","请输入需求名称");
		return;
		}
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var formParam = Ext.getCmp('panel_SpecialRequire3_m_Input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curUrl = webContext
				+ '/requireAction_saveModifyDraft.action?' 
				+ 'pagePanel=panel_SpecialRequire3_m_Input'
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
								+ "',workflowHistory:'com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis',applyType: 'rproject'}",
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
			}
		}, this);
	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},
	
 getFormpanel_SpecialRequire3_m_Input: function() {
 	var pName = this.processName;
 	
 	  var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		data = da.getModifyRequireFormPanelElementsForEdit("panel_SpecialRequire3_m_Input",this.dataId,this.oldId);
		
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
				if(idStr=='SpecialRequirement$oldApplyCombo'){
					data[i]=this.getOldApply();
				}
			}
			biddata = da.split(data);
		
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '保存为草稿',
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
		this.formpanel_SpecialRequire3_m_Input = new Ext.form.FormPanel({
			id : 'panel_SpecialRequire3_m_Input',
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
			title : "个性化需求申请",
			items : biddata,
			buttons : curbuttons
		});

	
		return this.formpanel_SpecialRequire3_m_Input;
	},
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var spId = this.serviceItemProcessId;
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
		
	     this.getFormpanel_SpecialRequire3_m_Input();
	     
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
			items : [this.formpanel_SpecialRequire3_m_Input, histroyForm]
		});

		if (this.status != 0) {
			this.items = [this.tab];
		} else {
			this.items = [this.formpanel_SpecialRequire3_m_Input];
		}
		   PageTemplates.superclass.initComponent.call(this);
		var curDataId = this.dataId;
		Ext.getCmp('SpecialRequirement$oldApplyCombo').addListener('collapse',
				function(box) {
					var newv = box.getValue();
					if (newv != ""&&curDataId=="") {
						window.location = webContext + "/requireAction_toProcessEnterPage.action?serviceItemProcessId="+spId+"&oldId="+newv;
					}
				});
	}
})