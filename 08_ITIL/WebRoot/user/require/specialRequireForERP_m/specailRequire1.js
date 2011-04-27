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
		Ext.Msg.alert("��ʾ","��������������");
		return;
		}
		if(!Ext.getCmp('panel_erpSR_enter_m_input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
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
				window.location = webContext//modify by zhangzy for ����ݸ����ת in 2009 11 21
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	// ���沢�ύ
	saveAndSubmit : function() {
		var name=Ext.getCmp('SpecialRequirement$name').getValue();
		if(name==""){
		Ext.Msg.alert("��ʾ","��������������");
		return;
		}
		if(!Ext.getCmp('panel_erpSR_enter_m_input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}		
		if(Ext.getCmp('SpecialRequirement$groupFinanceManagerCombo').getRawValue()=="��"){//add by zhangzy for ����ѡ���ޡ�ʱbug
			Ext.MessageBox.alert("��ʾ","��ѡ���Ų�����");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(Ext.getCmp('SpecialRequirement$homeFinanceManagerCombo').getRawValue()=="��"){//add by zhangzy for ����ѡ���ޡ�ʱbug
			Ext.MessageBox.alert("��ʾ","��ѡ�񱾲�������");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(Ext.getCmp('SpecialRequirement$confirmUserCombo').getRawValue()=="��"){//add by zhangzy for ����ѡ���ޡ�ʱbug
			Ext.MessageBox.alert("��ʾ","��ѡ��IT����ˣ�");
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
						Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {
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
				Ext.MessageBox.alert("�����ύʧ��");
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
		// �ж������������޸�
		var biddata = "";
		data = da.getModifyRequireFormPanelElementsForEdit("panel_erpSR_enter_m_input",this.dataId,this.oldId);
		
			for (i = 0; i < data.length; i++) {
				if (data[i].id=='SpecialRequirement$applyNum'|| data[i].id=='SpecialRequirement$applyUserCombo'){// add by musicbear for������ֻ������ in 2009 11 17 
					data[i].readOnly = true;
					data[i].hideTrigger = true;
						if (data[i].value == '') {
							data[i].emptyText = '�Զ�����';
							// data[i].readOnly = true;
							data[i].disabled = true;
						}
					}
				//add by lee for �任���������� in 20091203 begin
				if(data[i].id=='SpecialRequirement$confirmUserCombo'){
					data[i].fieldLabel = 'IT�����'
				}
				//add by lee for �任���������� in 20091203 end
			var idStr = data[i].id + "";				
			if (idStr=='SpecialRequirement$groupFinanceManagerCombo' 
				|| idStr=='SpecialRequirement$homeFinanceManagerCombo'
				|| idStr=='SpecialRequirement$confirmUserCombo') {
					data[i].readOnly = true;				// add by zhangzy for �ֶ���Ϊֻ�� in 2009 12 09
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
			text : '����Ϊ�ݸ�',
			id : 'tempSave',				// add by zhangzy for �����ύ��ťʱ ��ť��� in 2009 12 7
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
			title : "ERP�ǳ�������������",
			items : biddata,
			tbar : curbuttons
		});

	
		return this.formpanel_erpSR_enter_m_input;
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
		   //SBU����ѡ���Ǵ�����Ӧ������
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
							Ext.MessageBox.alert("��ȡSBU������ʧ��");
						}
					}, this);					
				});		
	}
})