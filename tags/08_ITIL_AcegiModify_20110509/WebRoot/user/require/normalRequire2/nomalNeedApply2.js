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
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_ERP_NormalNeed2_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
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
		if (scope1 == false && scope2 == false && scope3 == false
				&& scope4 == false && scope5 == false && scope6 == false
				&& scope7 == false && scope8 == false) {
			Ext.MessageBox.alert("��ʾ", "��ѡ��ʹ�÷�Χ��");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var formParam = Ext.encode(getFormParam('panel_ERP_NormalNeed2_Input'));
		var curscid = this.serviceItemId;
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				info:formParam,
				pagePanel:'panel_ERP_NormalNeed2_Input',
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
				Ext.MessageBox.alert("����ʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// ���沢�ύ
	saveAndSubmit : function() {
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_ERP_NormalNeed2_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
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
		if (scope1 == false && scope2 == false && scope3 == false
				&& scope4 == false && scope5 == false && scope6 == false
				&& scope7 == false && scope8 == false) {
			Ext.MessageBox.alert("��ʾ", "��ѡ��ʹ�÷�Χ��");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}

		var tempRequireLevel = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
		var tempReason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
		if(tempRequireLevel==1&&tempReason==""){
			Ext.MessageBox.alert("��ʾ","�Ӽ����������д�Ӽ����ɣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var formParam = Ext.encode(getFormParam('panel_ERP_NormalNeed2_Input'));
		var curscid = this.serviceItemId;
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				info:formParam,
				pagePanel:'panel_ERP_NormalNeed2_Input',
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
								+ "',workflowHistory:'com.zsgj.itil.service.entity.ServiceItemApplyAuditHis'"
								+ ",applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {

						var meg = Ext.decode(response.responseText);
						if (meg.id != undefined) {
							Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {//modify by zhangzy for �û�Ҫ�������ʾ
								window.location = webContext+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="+ curscid;
							});
						} else {
							Ext.Msg.alert("��ʾ", "�����ύʧ��", function() {
								alert(meg.Exception);
							});
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ", "�����ύʧ��");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("�����ύʧ��");
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

	getFormpanel_ERP_NormalNeed2_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		data = da.getRequireFormPanelElementsForEdit(
				"panel_ERP_NormalNeed2_Input", this.dataId);
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr.indexOf('$requirementLevelCombo') > 0
					&& data[i].value == '') {
				data[i].value = '4';
			}
			if(idStr=='ERP_NormalNeed$flatCombo'){
					data[i].store = new Ext.data.JsonStore({
					url:webContext+"/extjs/comboDataAction?clazz=com.zsgj.itil.require.entity.RequireApplyDefaultAudit&orderBy=sortNum&deleteFlag=0&enable=1",
					fields:['id','departmentName'],
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}
			//add by lee for ƽ̨��ת������Ĭ��ֵ in 20091116 begin
			if (idStr=='ERP_NormalNeed$isStoreCombo'&& data[i].value == '') {
				data[i].value = '1';
			}
			//add by lee for ƽ̨��ת������Ĭ��ֵ in 20091116 end
			if (idStr.indexOf('$applyNum') > 0
					|| idStr.indexOf('$applyUser') > 0
					//|| idStr.indexOf('$applyDate') > 0
			) {
				data[i].readOnly = true;
				if (data[i].value == '') {
					data[i].emptyText = '�Զ�����';
					// data[i].readOnly = true;
					data[i].disabled = true;
				}
			}
		}
//		if (this.status != 0) {
//			biddata = da.splitForReadOnly(data);
//		}
		if (this.status == 1 || this.status == 2) { //add by zhangzy for ������ҳ���������Ϊֻ�� in 2009 11 25
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
		if (this.status == 0) {
			curbuttons.push(saveButton);
			curbuttons.push(submitButton);
		}
		curbuttons.push(backButton);
		this.formpanel_ERP_NormalNeed2_Input = new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed2_Input',
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
			tbar : curbuttons
				// buttons : curbuttons
		});

		return this.formpanel_ERP_NormalNeed2_Input;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {

		this.getFormpanel_ERP_NormalNeed2_Input();

		if (this.status != 0) {
			var histroyForm = new HistroyForm({
				reqId : this.dataId,
				reqClass : "com.zsgj.itil.require.entity.ERP_NormalNeed"
			});
			this.tab = new Ext.TabPanel({
				xtype : 'tabpanel',
				activeTab : 1,
				enableTabScroll : true,
				// minTabWidth:100,
				// resizeTabs : true,
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
				items : [this.formpanel_ERP_NormalNeed2_Input, histroyForm]
			});
			this.items = [this.tab];
		} else {
			this.items = [this.formpanel_ERP_NormalNeed2_Input];
		}
		//add by lee for ѡ��ƽ̨��ת��ʱ������ʾ in 20091116 begin
		Ext.getCmp('ERP_NormalNeed$isStoreCombo').on('select',function(){
			var isStore = Ext.getCmp('ERP_NormalNeed$isStoreCombo').getValue();
			if(isStore==0){
				Ext.Msg.alert("��ʾ", "���ѡ�񡰷񡱣��Ժ��޷�������ƽ̨�ⷿ�������ص�ת����������");
			}
		});
		//add by lee for Ϊ��������/��������������ӱ����� in 20091207 begin
		if(this.dataId==""&&this.serviceItemId=='167'){
			Ext.getCmp('ERP_NormalNeed$otherInfo').setValue("����д������Ϣ�����");
		}
		
		//add by lee for Ϊ��������/��������������ӱ����� in 20091207 end
			//add by lee for ѡ��ƽ̨��ת��ʱ������ʾ in 20091116 end
		PageTemplates.superclass.initComponent.call(this);
	}
})