PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
//	title :this.description,
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
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_tempRequire').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("��ʾ","����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_tempRequire',false));
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				pagePanel��:��'panel_tempRequire',
				info : formParam,
				serviceItemId : curscid
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext//modify by zhangzy for ����ݸ����ת in 2009 11 21
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// ���沢�ύ
	saveAndSubmit : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_tempRequire').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();		
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("��ʾ","����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curModel = this.model;
		var curProcessName = this.processName;
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_tempRequire',false));
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				panel��:��'panel_tempRequire',
				pagePanel��:��'panel_tempRequire',
				info : formParam,
				serviceItemId : curscid
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
								+ "',applyId : '"+ curDataId
								+ "',serviceItemId : '"+ curscid
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.zsgj.itil.service.entity.ServiceItemApplyAuditHis',applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {
						Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {//modify by zhangzy for ��������ύ��ʾ  in 2009 11 21
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						});
					},
					failure : function(response, options) {
						Ext.Msg.alert("��ʾ", "�����ύʧ��", function() {
							window.location = webContext
									+ "/infoAdmin/serviceItemProcessAction.do?methodCall=toApplyPage&serviceItemId="
									+ curscid + "&processType=0" + "&dataId=" + curDataId;
						});
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
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

 getFormpanel_typicalRequire_Input : function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_tempRequire",this);
			if (this.dataId == '0') {
				data = da.getRequireFormPanelElementsForEdit("panel_tempRequire","");
			}else{
				data = da.getRequireFormPanelElementsForEdit("panel_tempRequire",this.dataId);
			}
			for (i = 0; i < data.length; i++) {				
			var idStr = data[i].id + "";
			if (idStr.indexOf('$applyNum') > 0
					|| idStr.indexOf('$requireId') > 0 
					|| idStr.indexOf('$applyUser') > 0) {// add by musicbear for������ֻ������ in 2009 11 17 
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].value == '') {
					data[i].emptyText = '�Զ�����';
					// data[i].readOnly = true;
					data[i].disabled = true;
				}
			}
			if (idStr.indexOf('$applyUser')> 0) {
					data[i].emptyText = '������ITCODE���в�ѯ';
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
		if (this.status == 1 || this.status == 2) {
			for (i = 0; i < data.length; i++) {
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].id = data[i].id + 8;// �ı�Combobox��id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				data[i].readOnly = true;
			}
		}
		biddata = da.split(data);
			var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '����Ϊ�ݸ�',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.save
		});
		var submitButton = new Ext.Button({
			text : '���沢�ύ',
			id : 'workFlowButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : this.saveAndSubmit
		});
		var backButton = new Ext.Button({
			text : '����',
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
		this.formpanel_typicalRequire_Input = new Ext.form.FormPanel({
			id : 'panel_tempRequire',
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
			title :this.description,
			items : biddata,
			tbar : curbuttons
		});
		return this.formpanel_typicalRequire_Input;
	},
  items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
	    this.getFormpanel_typicalRequire_Input();    
//modify by zhangzy for ���������ġ� �޷��� in 2009 11 21 start		 
		if (this.status != 0) {
			var spId = this.serviceItemProcessId;
			var histroyForm = new HistroyForm({
				reqId : this.dataId,
				reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
			});
			this.tab = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 1,
			enableTabScroll : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			defaults : {
				autoHeight : true,
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : [this.formpanel_typicalRequire_Input, histroyForm]
		});
			this.items = [this.tab];
			
		} else {
			this.items = [this.formpanel_typicalRequire_Input];
		}
//modify by zhangzy for ���������ġ� �޷��� in 2009 11 21 end
//remove by lee for �û�����ȥ�� in 20091203 begin		
//add by lee for ��ʾ������ǩ�� in 20091113 begin
//		Ext.getCmp('SpecialRequirement$flatCombo').on('select',function(){
//			var flatId = Ext.getCmp('SpecialRequirement$flatCombo').getValue();
//			Ext.Ajax.request({
//					url : webContext + '/SRAction_getCadreBizAudit.action?flatId=' +flatId,
//					success : function(response, options) {
//						var responseArray = Ext.util.JSON.decode(response.responseText);
//						var auditUser = responseArray.auditUser;
//						Ext.Msg.alert("������ǩ��", auditUser);
//					},
//					failure : function(response, options) {
//						Ext.Msg.alert("��ʾ", "��ȡ������ǩ��ʧ��");
//					}
//				}, this);
//		});
//add by lee for ��ʾ������ǩ�� in 20091113 begin
//remove by lee for �û�����ȥ�� in 20091203 end
		   PageTemplates.superclass.initComponent.call(this);
	
	}
})