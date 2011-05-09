PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	title : 'С�����û򿪷�����',
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
		if(!Ext.getCmp('panel_SpecialRequire2_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept=Ext.getCmp('SpecialRequirement$flatCombo').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("��ʾ","����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(dept==""||dept==null){
			Ext.MessageBox.alert("��ʾ","��ѡ�����벿�ţ�");
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
				Ext.MessageBox.alert("����ʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// ���沢�ύ
	saveAndSubmit : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_SpecialRequire2_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept=Ext.getCmp('SpecialRequirement$flatCombo').getValue();
		if(name==""||name==null){
			Ext.MessageBox.alert("��ʾ","����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(dept==""||dept==null){
			Ext.MessageBox.alert("��ʾ","��ѡ�����벿�ţ�");
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
						//Ext.Msg.alert("��ʾ", "�����������ɹ�", function() {//remove by lee for �û�Ҫ��ȥ����ʾ
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						//});
					},
					failure : function(response, options) {
						Ext.Msg.alert("��ʾ", "����������ʧ��", function() {
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

 getFormpanel_SpecialRequire2_Input: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SpecialRequire2_Input",this);
			data = da.getRequireFormPanelElementsForEdit("panel_SpecialRequire2_Input",this.dataId);
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
			title : "С�����û򿪷�����",
			items : biddata,
			tbar : curbuttons
		});
		return this.formpanel_SpecialRequire2_Input;
	},
  items : this.items,
	/*
	 * clientvalidation ��ʼ��
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
			title : '�¼���ϸ��Ϣ',
			enableTabScroll : true,
			// minTabWidth:100,
			//resizeTabs : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
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