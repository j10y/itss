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
	saveAndSubmit : function() {
		var tkId = this.taskId;
        var isNeedBasicEn=Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
		var engineerId = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').getValue();
//add by zhangzy for BASIS����ʦѡ����֤ in 2009 11 24 start		
		var engineerName = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').getRawValue();
		if(engineerName=="��"){
			Ext.Msg.alert("��ʾ","��ѡ���Ƿ���ҪBASIS����ʦ��������!");
			return;
		}	
		if(isNeedBasicEn==""){
			Ext.Msg.alert("��ʾ","��ѡ���Ƿ���ҪBASIS����ʦ��������!");
			return;
		}else if(engineerId==""&&isNeedBasicEn=="1"){
			Ext.Msg.alert("��ʾ","��ѡ��BASIS����ʦ��������!");
			return;
		}else if(engineerId==null&&isNeedBasicEn=="1"){
			Ext.Msg.alert("��ʾ","��ѡ��BASIS����ʦ��������!");
			return;
		}else if(engineerId==''&&isNeedBasicEn=="1"){
			Ext.Msg.alert("��ʾ","��ѡ��BASIS����ʦ��������!");
			return;				
		}else if(engineerName == ""&&isNeedBasicEn=="1"){
			Ext.Msg.alert("��ʾ","��ѡ��BASIS����ʦ��������!");
			return;
//add by zhangzy for BASIS����ʦѡ����֤ in 2009 11 24 end
		}else {
			var formParam = Ext.getCmp('panel_ErpEngineerFeedback_Input').form.getValues(false);
			var vp = null;
			if (formParam != null) {
				vp = Ext.apply(formParam, vp, {});
			}			
			var curdataId = this.dataId;
			Ext.Ajax.request({
				url : webContext
						+ '/SRAction_saveErpEngineerFeedback.action?'
						+ '&reqId=' + curdataId,
				params : vp,
				success : function(response, options) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					var tempId = responseArray.id;
					Ext.getCmp('itil_sci_ErpEngineerFeedback$id').setValue(tempId);
					var engineerId = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').getValue();
					if(engineerId!=""){
						Ext.Ajax.request({
							url : webContext
									+ '/extjs/workflow?method=getData&taskId='
									+ tkId + '&users=operateByBASISEngineer:'
									+ engineerId,
							method : 'post',
							success : function(response, options) {
								window.parent.auditContentWin.specialAudit();
							},
							failure : function(response, options) {
							}
						});
					}else{
						window.parent.auditContentWin.specialAudit();
					}
				},
				failure : function(response, options) {
					Ext.MessageBox.alert("����ʧ��");
				}
			}, this);
		}
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

	getFormpanel_ERP_NormalNeed1_m_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed1_m_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_m_operateByEngineer",
					"panel_ERP_NormalNeed1_m_Input", this.dataId);// ����Ҫ��ʱ���
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed1_m_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_m_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed1_m_Input',
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
			this.formpanel_ERP_NormalNeed1_m_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed1_m_Input',
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
		return this.formpanel_ERP_NormalNeed1_m_Input;
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
			data = da.getPanelElementsForEdit("nr_operateByEngineer",
					"panel_ErpEngineerFeedback_Input", this.dataId);// ����Ҫ��ʱ���
			for (var i = 0; i < data.length; i++) {
				// alert(data[i].id);
				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisFlagCombo') {

					data[i].on('select', function(combo, record, index) {
						// alert("hello");
						var s = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						// alert(s);
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
			data = da.getPanelElementsForEdit("nr_m_operateByEngineer",
					"panel_ErpEngineerFeedback_Input", this.dataId);// ����Ҫ��ʱ���
			for (var i = 0; i < data.length; i++) {
				// alert(data[i].id);
				if (data[i].id == 'itil_sci_ErpEngineerFeedback$basisFlagCombo') {

					data[i].on('select', function(combo, record, index) {
						// alert("hello");
						var s = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						// alert(s);
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
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
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
		this.model = "nr_m_operateByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nr_m_operateByEngineer",
				this);
		if (this.mybuttons != "") {
			this.buttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				},
				items : this.mybuttons
			};
		} else {
			this.buttons = {
				layout : 'table',
				height : 'auto',
				width : 800,
				style : 'margin:4px 6px 4px 300px',
				align : 'center',
				defaults : {
					bodyStyle : 'padding:4px'
				}
			};
		}

		this.getFormpanel_ERP_NormalNeed1_m_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_m_Input);
		this.formname.push("panel_ERP_NormalNeed1_m_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_m_Input);
		this.getFormpanel_ErpEngineerFeedback_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		this.formname.push("panel_ErpEngineerFeedback_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		var tempErpId = Ext.getCmp('itil_sci_ErpEngineerFeedback$id').getValue();
		PageTemplates.superclass.initComponent.call(this);
	}
})