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
		if(!Ext.getCmp('panel_ServerManage_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end		
		//add by zhangzy for �����ֶο���ѡ���ޡ� in 20091126 start
		var confirmUser = Ext.getCmp('ServerManage$confirmUserCombo').getRawValue();
		if(confirmUser==''||confirmUser==null||confirmUser==""||confirmUser=="��"){
			Ext.MessageBox.alert("��ʾ","�����������Ǳ�����,����д������лл��������");	
			return false;
		}
		//add by zhangzy for �����ֶο���ѡ���ޡ� in 20091126 end	
		if(Ext.getCmp('ServerManage$serverTypeCombo').getValue()==2&&Ext.getCmp('ServerManage$serverPort').getValue()==""){
			Ext.MessageBox.alert("��ʾ","����������ΪInternetӦ�÷�����ʱ,����д�������˿ڡ�лл��������");
		 	return false;
		}		
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_ServerManage_Input',false));		


		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action';
		Ext.Ajax.request({
			url : curUrl,
			params:{
				pagePanel:'panel_ServerManage_Input',
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
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(!Ext.getCmp('panel_ServerManage_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		//add by zhangzy for �����ֶο���ѡ���ޡ� in 20091126 start
		var confirmUser = Ext.getCmp('ServerManage$confirmUserCombo').getRawValue();
		if(confirmUser==''||confirmUser==null||confirmUser==""||confirmUser=="��"){
			Ext.MessageBox.alert("��ʾ","�����������Ǳ�����,����д������лл��������");	
			return false;
		}
		//add by zhangzy for �����ֶο���ѡ���ޡ� in 20091126 end
		if(Ext.getCmp('ServerManage$serverTypeCombo').getValue()==2&&Ext.getCmp('ServerManage$serverPort').getValue()==""){
			Ext.MessageBox.alert("��ʾ","����������ΪInternetӦ�÷�����ʱ,����д�������˿ڡ�лл��������");
		 	return false;
		}			
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		var curModel = this.model;
		var curProcessName = this.processName;
		//		var tempRequireLevel = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
		//		var tempReason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
		//		if(tempRequireLevel==1&&tempReason==""){
		//			Ext.MessageBox.alert("��ʾ","�Ӽ����������д�Ӽ����ɣ�");
		//			Ext.getCmp('saveButton').enable();
		//			Ext.getCmp('workFlowButton').enable();
		//			return;
		//		}
		var curscid = this.serviceItemId;
		var formParam = Ext.encode(getFormParam('panel_ServerManage_Input',false));		


		var curUrl = webContext
				+ '/requireAction_saveApplyDraft.action';
		Ext.Ajax.request({
			url : curUrl,
			params:{
				pagePanel:'panel_ServerManage_Input',
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
					url : webContext + '/requireWorkflow_applyForServerManage.action',
					params : {
						dataId : curDataId,
						model : curModel,
						bzparam : "{dataId :'"+ curDataId
								+ "',applyId : '"+ curDataId
								+ "',serviceItemId : '"+ curscid
								+ "',reqName : '"+ reqName
								+ "',reqCode : '" + reqCode
								+ "',reqDate : '" + reqDate
								+ "',workflowHistory:'com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis'"
								+ ",applyType: 'rproject'}",
						defname : curProcessName
					},
					success : function(response, options) {

						var meg = Ext.decode(response.responseText);
						if (meg.id != undefined) {
							Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {//modify by zhangzy for �û�Ҫ�������ʾ in 2009 11 24								window.location = webContext
								window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
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
			}
		}, this);
	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			//minTabWidth:100,
			//resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			//tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			//bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa, panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title : panelTitle,
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	},

	getFormpanel_ServerManage_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		data = da.getRequireFormPanelElementsForEdit(
				"panel_ServerManage_Input", this.dataId);
				
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if (idStr.indexOf('$applyNum') > 0
					|| idStr.indexOf('$applyUser') > 0
					|| idStr.indexOf('$applyDate') > 0) {
				data[i].readOnly = true;
				data[i].cls = "x-form-field-wrap";
				if (data[i].value == '') {
					data[i].emptyText = '�Զ�����';
					// data[i].readOnly = true;
					data[i].disabled = true;
				}
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].hideTrigger = true;
				}
			}
		}
		if (this.status == 1 || this.status == 2) {//add by zhangzy for �ڷ�����ҳ�棬Ԫ����Ϊֻ�� in 2009 11 25
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
		this.formpanel_ServerManage_Input = new Ext.form.FormPanel({
			id : 'panel_ServerManage_Input',
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
			title : "��������פ����������������",
			items : biddata,
			tbar : curbuttons
		});

		return this.formpanel_ServerManage_Input;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
//		var items = new Array();
//		var pa = new Array();
//		this.pa = pa;
//		var gd = new Array();
//		this.gd = gd;
//		var temp = new Array();
//		this.temp = temp;
//		var formname = new Array();
//		this.formname = formname;
//		var gridname = new Array();
//		this.gridname = gridname;
//		this.model = "serverManage1";
//		var buttonUtil = new ButtonUtil();
//		this.mybuttons = buttonUtil.getButtonForModel("serverManage1", this);
//		if (this.mybuttons != "") {
//			this.buttons = {
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				style : 'margin:4px 6px 4px 300px',
//				align : 'center',
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				items : this.mybuttons
//			};
//		} else {
//			this.buttons = {
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				style : 'margin:4px 6px 4px 300px',
//				align : 'center',
//				defaults : {
//					bodyStyle : 'padding:4px'
//				}
//			};
//		}

		this.getFormpanel_ServerManage_Input();
		this.items = [this.formpanel_ServerManage_Input];
//modify by zhangzy for ���������ġ� �޷��� in 2009 11 25 start
		if (this.status != 0) {
			var spId = this.serviceItemProcessId;
			var histroyForm = new HistroyForm({
				reqId : this.dataId,
				reqClass : "com.digitalchina.itil.require.entity.ServerManage"
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
			items : [this.formpanel_ServerManage_Input, histroyForm]
		});
			this.items = [this.tab];
			
		} else {
			this.items = [this.formpanel_ServerManage_Input];
		}
//modify by zhangzy for ���������ġ� �޷��� in 2009 11 25 end
		PageTemplates.superclass.initComponent.call(this);
	}
})