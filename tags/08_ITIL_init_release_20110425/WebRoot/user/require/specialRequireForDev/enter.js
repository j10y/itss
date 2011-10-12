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
	
//	save : function(){
//		getFormParam("panel_SpecialRequireDev_Input");
//	},

	save : function() {
		if (!Ext.getCmp('panel_SpecialRequireDev_Input').form.isValid()) {
			Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
			return false;
		}
		var deptName=Ext.getCmp('SpecialRequirement$flatCombo').getRawValue();	
		var ItName=Ext.getCmp('SpecialRequirement$confirmUserCombo').getRawValue();			
		if(deptName=="��"){
			Ext.MessageBox.alert("��ʾ","��ѡ������SBU/������");
			return;
		}
		if(ItName=="��"){
			Ext.MessageBox.alert("��ʾ","��ѡ��IT�����ˣ�");
			return;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept = Ext.getCmp('SpecialRequirement$confirmUserCombo').getValue();
		if (name == "" || name == null) {
			Ext.MessageBox.alert("��ʾ", "����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if (dept == "" || dept == null) {
			Ext.MessageBox.alert("��ʾ", "��ѡ���������ˣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_SpecialRequireDev_Input',false));
		Ext.Ajax.request({
			url:webContext + '/requireAction_saveApplyDraft.action',
			params:{
				pagePanel:'panel_SpecialRequireDev_Input',
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
				Ext.MessageBox.alert("����ʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},

	// ���沢�ύ
	saveAndSubmit : function() {
		if (!Ext.getCmp('panel_SpecialRequireDev_Input').form.isValid()) {
			Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");
			return false;
		}
		var deptName=Ext.getCmp('SpecialRequirement$flatCombo').getRawValue();	
		var ItName=Ext.getCmp('SpecialRequirement$confirmUserCombo').getRawValue();			
		if(deptName=="��"){
			Ext.MessageBox.alert("��ʾ","��ѡ������SBU/������");
			return;
		}
		if(ItName=="��"){
			Ext.MessageBox.alert("��ʾ","��ѡ��IT�����ˣ�");
			return;
		}					
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var name = Ext.getCmp('SpecialRequirement$name').getValue();
		var dept = Ext.getCmp('SpecialRequirement$confirmUserCombo').getValue();
		if (name == "" || name == null) {
			Ext.MessageBox.alert("��ʾ", "����д�������ƣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if (dept == "" || dept == null) {
			Ext.MessageBox.alert("��ʾ", "��ѡ���������ˣ�");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		var curscid = this.serviceItemId;
		var curModel = this.model;
		var curProcessName = this.processName;
		var formParam = Ext.encode(getFormParam('panel_SpecialRequireDev_Input',false));

		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action?pagePanel=panel_SpecialRequireDev_Input'
				+ '&serviceItemId=' + curscid;
		Ext.Ajax.request({
			url : curUrl,
			params : {
				info : formParam
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curDataId = responseArray.id;
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
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis',applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {
						Ext.Msg.alert("��ʾ", "�ύ����ɹ�", function() {
							window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
						});
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

	getFormpanel_SpecialRequireDev_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		data = da.getRequireFormPanelElementsForEdit( "panel_SpecialRequireDev_Input", this.dataId);
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr=='SpecialRequirement$applyNum'|| idStr=='SpecialRequirement$applyUserCombo') {
				data[i].readOnly = true;
				data[i].hideTrigger = true;
				if (data[i].value == '') {
					data[i].emptyText = '�Զ�����';
					data[i].disabled = true;
				}
			}
			if(idStr=='SpecialRequirement$appConfigItemCombo'){
				data[i].store = new Ext.data.JsonStore({
					url:webContext+'/SRAction_getAppConfigItemComboData.action?appTypeId=16',
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
					url:webContext+"/extjs/comboDataAction?clazz=com.digitalchina.itil.require.entity.RequireApplyDefaultAudit&orderBy=sortNum&deleteFlag=0&enable=1",
					fields:['id','departmentName'],
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}
			if (idStr=='SpecialRequirement$confirmUserCombo') {
					data[i].fieldLabel = 'IT������';
					data[i].readOnly = true;				// add by zhangzy for �ֶ���Ϊֻ�� in 2009 12 09
					data[i].hideTrigger = true;					
			}
		}
		if (this.status == 1 || this.status == 2) {
			biddata = da.splitForReadOnly(data);
		}else{
			biddata = da.split(data);
		}
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
		this.formpanel_SpecialRequireDev_Input = new Ext.form.FormPanel({
			id : 'panel_SpecialRequireDev_Input',
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
			title : "������IT����",
			items : biddata,
			tbar : curbuttons
		});
		return this.formpanel_SpecialRequireDev_Input;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		this.getFormpanel_SpecialRequireDev_Input();
		if (this.dataId != "") {
			var histroyForm = new HistroyForm({
				reqId : this.dataId,
				reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
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
				items : [this.formpanel_SpecialRequireDev_Input, histroyForm]
			});
			this.items = [this.tab];
		} else {
			this.items = [this.formpanel_SpecialRequireDev_Input];
		}
		PageTemplates.superclass.initComponent.call(this);
//����SBU/���� ѡ��� ����ѡ��IT�����ˡ� start		
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
							Ext.MessageBox.alert("��ȡIT������ʧ��");
						}
					}, this);					
				});		
//����SBU/���� ѡ��� ����ѡ��IT�����ˡ� end
//Ӧ��ϵͳ ѡ��� ����ѡ��Ӧ�ù���Ա�� start		
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
							Ext.MessageBox.alert("��ȡӦ�ù���Աʧ��");
						}
					}, this);					
				});		
//Ӧ��ϵͳ ѡ��� ����ѡ��Ӧ�ù���Ա�� end					

	}
})