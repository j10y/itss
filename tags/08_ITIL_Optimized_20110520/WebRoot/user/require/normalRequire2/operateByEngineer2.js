PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	buttonAlign : 'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	//�ݴ�
	saveForTemp : function() {
		if(!Ext.getCmp('panel_ErpEngineerFeedback_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		Ext.Ajax.request({
			url : webContext+ '/SRAction_reAssignAudit.action?taskId='+this.taskId,
			success : function(response, options) {
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("���°󶨱��ڵ�������ʧ��");
			}
		}, this);
		Ext.Ajax.request({
			url : webContext+ '/requireAction_saveLockUser.action?taskId='+this.taskId,
			success : function(response, options) {
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("������Ϣ","�����ڵ����ʧ��");
			}
		}, this);
		var formParam = Ext.encode(getFormParam('panel_ErpEngineerFeedback_Input'));			
		var reqId = this.dataId;
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveErpEngineerFeedback.action',
			params : {
				info:formParam,
				reqId:reqId
			},
			success : function(response, options) {
				var feedbackId = Ext.util.JSON.decode(response.responseText).id;
				Ext.getCmp('itil_sci_ErpEngineerFeedback$id').setValue(feedbackId);
				Ext.MessageBox.alert("�ݴ�ɹ�");
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	//���沢�ύ
	saveAndSubmit : function() {
		if(!Ext.getCmp('panel_ErpEngineerFeedback_Input').form.isValid()){
			Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
			return false;
		}
		var formParam = Ext.encode(getFormParam('panel_ErpEngineerFeedback_Input'));
		var reqId = this.dataId;
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveErpEngineerFeedback.action',
			params : {
				info:formParam,
				reqId:reqId
			},
			success : function(response, options) {
				var feedbackId = Ext.util.JSON.decode(response.responseText).id;
				Ext.getCmp('itil_sci_ErpEngineerFeedback$id').setValue(feedbackId);
				window.parent.auditContentWin.specialAudit();
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
				
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			// minTabWidth:100,
			// resizeTabs:true,
			title : tabTitle,
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

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed3_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_operateByEngineer2",
					"panel_ERP_NormalNeed3_Input", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed3_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed3_Input',
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
				title : "ERP�����������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed3_Input',
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
				title : "ERP�����������",
				items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed1_Input;
	},
	getFormpanel_ErpEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ErpEngineerFeedback_Input", this);

		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_operateByEngineer2",
					"panel_ErpEngineerFeedback_Input", this.dataId);// ����Ҫ��ʱ���
			for (var i = 0; i < data.length; i++) {
				// alert(data[i].id);
				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisFlagCombo') {

					data[i].on('select', function(combo, record, index) {
						var s = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						if (s == '1') {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').enable();
						} else {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').clearValue();
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').disable();
						}
					})
				}

				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisEngineerCombo') {
					data[i].disabled = true;
				}
			}

			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForEdit("nr_operateByEngineer2",
					"panel_ErpEngineerFeedback_Input", this.dataId);// ����Ҫ��ʱ���
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisFlagCombo') {
					data[i].on('select', function(combo, record, index) {
						var s = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						if (s == '1') {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').enable();
						} else {
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').clearValue();
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').disable();
						}
					})
				}

				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisEngineerCombo') {
					data[i].disabled = true;
				}
			}

			biddata = da.split(data);

		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_ErpEngineerFeedback_Input = new Ext.form.FormPanel({
				id : 'panel_ErpEngineerFeedback_Input',
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
				title : "<font color=red>Erp����ʦ����</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ErpEngineerFeedback_Input = new Ext.form.FormPanel({
				id : 'panel_ErpEngineerFeedback_Input',
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
				title : "<font color=red>Erp����ʦ����</font>",
				items : biddata
			});
		}
		return this.formpanel_ErpEngineerFeedback_Input;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var isLockUser = true;
		var  sra = new SRAction();
		isLockUser = sra.getConfirmLockUser(this.taskId);			
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.ERP_NormalNeed"
		});
		var items = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var temp = new Array();
		this.temp = temp;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
		this.model = "nr_operateByEngineer2";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nr_operateByEngineer2",
				this);
		this.buttons = new Array();
		if (this.readOnly != 1) {
			this.buttons = this.mybuttons;
		}
		if(!isLockUser){
			for(var i=0;i<this.buttons.length;i++){
				this.buttons[i].disabled=true;
			}								
		}		
		this.getFormpanel_ERP_NormalNeed1_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		this.formname.push("panel_ERP_NormalNeed3_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_Input);
		this.getFormpanel_ErpEngineerFeedback_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		this.formname.push("panel_ErpEngineerFeedback_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		this.items = items;
		this.on("saveAndAssign", this.saveAndAssign, this);
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		this.on('saveForTemp', this.saveForTemp, this);
		// remove by lee for ����Ĭ��ͬ�� in 20090806 begin
		// var tempErpId =
		// Ext.getCmp('itil_sci_ErpEngineerFeedback$id').getValue();
		// if(tempErpId==''){
		// Ext.getCmp('itil_sci_ErpEngineerFeedback$feekback').setValue('ͬ��');
		// Ext.getCmp('itil_sci_ErpEngineerFeedback$otherInfo').setValue('ͬ��');
		// }
		// remove by lee for ����Ĭ��ͬ�� in 20090806 end
		PageTemplates.superclass.initComponent.call(this);
	}
})