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
	saveAndSubmit : function(){
		var dataId = this.dataId;
		var formParam = Ext.getCmp('panel_SRUserFeedback_input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveUserFeedback.action?reqId='+ dataId,
			params : vp,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var tempId = responseArray.id;
				Ext.getCmp('itil_SRUserFeedback$id').setValue(tempId);
				window.parent.auditContentWin.specialAudit();
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	saveForGrid : function() {

		var reqId = this.dataId;
		//var reqCls = this.reqClass;
		//var removedIds= this.remIds;
		var store = Ext.getCmp('panel_SRTestReport').store;
		var product = "";
		store.each(function(record) {
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
				//alert(product);
			}
		})

		product = unicode(product);
		//alert(product);
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveTestReport.action',
			params : {
				product : product,
				reqId : reqId
			},
			success : function(response, options) {
				Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {
					store.reload();
					store.removeAll();
						//removedIds="";
					});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}

		})
	},
	removePlan : function() {
		//this.remIds= this.removedIds;
		var record = Ext.getCmp('panel_SRTestReport').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('panel_SRTestReport').getSelectionModel()
				.getSelections();
		var store = Ext.getCmp('panel_SRTestReport').store;
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
		} else {
			Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
				if (btn == 'yes') {
					var remIds = "";
					if (records) {
						for (var i = 0; i < records.length; i++) {
							this.store.remove(records[i]);
							remIds += records[i].get("SRTestReport$id") + ",";
							//alert(this.remIds);
						}
						Ext.Ajax.request({
							url : webContext
									+ '/SRAction_removeTestReport.action',
							params : {
								removedIds : remIds
							},
							success : function(response, options) {
								Ext.MessageBox.alert("��ʾ", "ɾ���ɹ�", function() {
//remove by musicbear for ɾ��һ��δ��������ʱ��ɾ������δ��������� in 2009 11 7 
//									store.reload();
//									store.removeAll();
								});

							},
							failure : function(response, options) {
								Ext.MessageBox.alert("ɾ��ʧ��");
							}
						})
					}
				}
			}, this)
		}
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

	getFormpanel_SpecialRequire3_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire3_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_confirmByClientManager",
					"panel_SpecialRequire3_Input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3_Input");

		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;// �ı�Combobox��id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
			data[i].readOnly = true;
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
				title : "�û�����",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
				title : "�û�����",
				items : biddata
			});
		}
		return this.formpanel_SpecialRequire3_Input;
	},
	getFormserviceItemBasePanel : function() {
		var sra = new SRAction();
		var siId = sra.getReqServiceItemId(this.dataId);
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"serviceItemBasePanel", this);
		if (siId != '0') {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					siId);// ����Ҫ��ʱ���
		} else {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					"");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//�ı�Combobox��id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
			data[i].readOnly = true;
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
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
				title : "��������������",
				items : biddata
					//,
					//buttons : this.getFormButtons
			});
		} else {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
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
				title : "��������������",
				items : biddata
			});
		}
		return this.formserviceItemBasePanel;
	},

	getFormpanel_SRServiceProviderInfo_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRServiceProviderInfo_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SRServiceProviderInfo_Input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;// �ı�Combobox��id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
			data[i].readOnly = true;
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRServiceProviderInfo_Input = new Ext.form.FormPanel({
				id : 'panel_SRServiceProviderInfo_Input',
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
				title : "�����ܼ��������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRServiceProviderInfo_Input = new Ext.form.FormPanel({
				id : 'panel_SRServiceProviderInfo_Input',
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
				title : "�����ܼ��������",
				items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},
	getFormpanel_SRTestReport : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",
				this);
//add by lee for add attachmentfile in 20090806 begin
		var curDataId = this.dataId;
		var fileButton =  new Ext.Button({
			text : '���Ը���',
			pressed : true,
			iconCls : 'export',
			handler : function() {
				Ext.Ajax.request({
					url : webContext + '/SRAction_saveTestReportFile.action',
					params : {
						dataId : curDataId
					},
					success : function(response, options) {
						var responseArray = Ext.util.JSON.decode(response.responseText);
						var attachmentFlag = responseArray.file;
						var ud = new UpDownLoadFile();
						ud.getUpDownLoadFileSu(attachmentFlag, '8016',
							'com.digitalchina.info.appframework.metadata.entity.SystemFile');
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ȡ����ʧ��");
					}
				})
			}
		});
//		getGridButtons.push(fileButton);
//		if(this.readOnly==1){
//			getGridButtons = new Array();
//			getGridButtons.push(fileButton);
//		}
//add by lee for add attachmentfile in 20090806 end
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// ��ÿһ����������
			var columnItem = "";
			//alert("aa"+editor.id);
			if (editor.xtype == 'datefield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else if (editor.xtype == 'textfield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					//width:300,
					editor : new Ext.form.TextField({
						fieldLabel : '���Խ��',
						xtype : 'textfield',
						id : 'flm_ProjectTestReport$description',
						name : 'flm_ProjectTestReport$description',
						style : '',
						width : 500,
						value : '',
						readOnly : false,
						allowBlank : true,
						validator : '',
						vtype : ''
					})

				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					width : 300,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
			if (editor.id == 'flm_ProjectTestReport$description') {
				width : 500
			}
		}
		
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRTestReport", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.formpanel_SRTestReport = new Ext.grid.EditorGridPanel({
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "��Ŀ�����б�",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			height : 600,
			width : 600,// this.width - 15,
			frame : true,
			tbar : [getGridButtons],
			bbar:[this.testFilePanel]
		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ this.dataId + '\"' + '}';// ����Ҫ��ʱ���
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.formpanel_SRTestReport;
	},
//	//�û�ȷ��--�û�����
//	getFeedback : function(dataId, taskId, surveyId) {
//		return new Ext.Panel({
//			title : "<font color=red>�û�����</font>",
//			id : "feedback_success",
//			width : 'auto',
//			heigth : 'auto',
//			frame : true,
//			autoScroll : true,
//			autoLoad : {
//				url : webContext + '/SRAction_findQuestions.action?surveyId='
//						+ surveyId + '&dataId=' + dataId + "&taskId=" + taskId,
//				nocache : true,
//				scripts : true,
//				text : "ҳ�����ڼ�����......",
//				method : 'post',
//				scope : this
//			},
//			viewConfig : {
//				autoFill : true,
//				forceFit : true
//			},
//			layout : 'fit'
//		});
//	},
	//add by lee for modify userFeedback in 20090806 begin 
	getFormpanel_SRUserFeedback_input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRUserFeedback_input",this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_testByClient",
					"panel_SRUserFeedback_input", this.dataId);// ����Ҫ��ʱ���	
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRUserFeedback_input");
			for (i = 0; i < data.length; i++) {
				if (data[i].id == 'itil_SRUserFeedback$completeCombo' || data[i].id == 'itil_SRUserFeedback$contentCombo') {
					data[i].value = 1;
				}
			}
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRUserFeedback_input = new Ext.form.FormPanel({
				id : 'panel_SRUserFeedback_input',
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
				title : "<font color=red>�û�����</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRUserFeedback_input = new Ext.form.FormPanel({
				id : 'panel_SRUserFeedback_input',
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
				title : "<font color=red>�û�����</font>",
				items : biddata
			});
		}
		return this.formpanel_SRUserFeedback_input;
	},
//add by lee for modify userFeedback in 20090806 end
	getFormpanel_SRProjectPlan : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRProjectPlan", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr_testByClient",
					"panel_SRProjectPlan", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRProjectPlan");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "���Ի�������Ŀ�ƻ�ʵ��(N)",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "���Ի�������Ŀ�ƻ�ʵ��(N)",
				items : biddata
			});
		}
		return this.formpanel_SRProjectPlan;
	},

	getGridpanel_SRExpendPlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRExpendPlan_list");
		//var buttonUtil = new ButtonUtil();
		//var getGridButtons = buttonUtil.getButtonForPanel(
		//		"panel_SRExpendPlan_list", this);

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// ��ÿһ����������
			var columnItem = "";
			if (editor.xtype == 'combo') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					readOnly : true,
					editor : editor
				}
			} else if (editor.xtype == 'datefield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					readOnly : true,
					editor : editor
				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					readOnly : true,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRExpendPlan_list", obj);

		this.cm.defaultSortable = true;
		// var viewConfig = Ext.apply({
		// forceFit : false
		// }, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRExpendPlan_list = new Ext.grid.EditorGridPanel({
			id : 'panel_SRExpendPlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "���Ի�����֧���ƻ�",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			readOnly : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true

		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';// ����Ҫ��ʱ���
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.gridpanel_SRExpendPlan_list;
	},
	getTestFilePanel : function(){
		var curDataId = this.dataId;
		this.testFilePanel = new Ext.Panel({
			layout:'table',
			width:600,
			layoutConfig:{columns:4},
			items:[
			{fieldLabel:'����',
				xtype:'button',
				text:'<font color=red>�ϴ����Ա���</font>',
				width:600,
				scope:this,
				//disabled:true, 
				handler:function(){
					Ext.Ajax.request({
					url : webContext + '/SRAction_saveTestReportFile.action',
					params : {
						dataId : curDataId
					},
					success : function(response, options) {
						var responseArray = Ext.util.JSON.decode(response.responseText);
						var attachmentFlag = responseArray.file;
						var ud = new UpDownLoadFile();
						ud.getUpDownLoadFileSu(attachmentFlag, '8016',
							'com.digitalchina.info.appframework.metadata.entity.SystemFile','TestReportFile');
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ȡ����ʧ��");
					}
				})
				}
			},
			{id:'TestReportFile',width:500,border : true,html:'',cls : 'common-text',style : 'width:500;text-align:left'}
			]
		});
			
		return this.testFilePanel;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var sra = new SRAction();
//		this.surveyId = sra.getSpecialRequireSurveyId();
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
//		Ext.Ajax.request({
//			url : webContext + '/SRAction_findSpecialRequireSurvey.action',
//			success : function(response, options) {
//				var data = Ext.util.JSON.decode(response.responseText);
//				this.surveyId = data.surveyId;
//			},
//			failure : function(response, options) {
//				Ext.MessageBox.alert('��ʾ','��ȡ�ʾ�ʧ��');
//			}
//		}, this);
//		
//		var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		var url = webContext + '/SRAction_findSpecialRequireSurvey.action';
//		conn.open("get", url, false);
//		conn.send(null);
//		if (conn.status == "200") {
//			var responseText = conn.responseText;
//			var data = Ext.decode(responseText);
//			this.surveyId = data.surveyId;
//		}
//		this.removedIds = "";
		
		var ppId = sra.getRootProjectPlanId(this.dataId);
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
		this.model = "nsr1_testByClient";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil
				.getButtonForModel("nsr1_testByClient", this);
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
        this.getTestFilePanel();        
		this.getFormpanel_SpecialRequire3_Input();
		this.pa.push(this.formpanel_SpecialRequire3_Input);
		this.formname.push("panel_SpecialRequire3_Input");
		temp.push(this.formpanel_SpecialRequire3_Input);
//		this.getFormserviceItemBasePanel();
//		this.pa.push(this.formserviceItemBasePanel);
//		this.formname.push("serviceItemBasePanel");
//		temp.push(this.formserviceItemBasePanel);
		this.getFormpanel_SRServiceProviderInfo_Input();
		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
		this.formname.push("panel_SRServiceProviderInfo_Input");
		temp.push(this.formpanel_SRServiceProviderInfo_Input);
		this.gridflm_projectPlanList = new projectPlanListPanel({
			dataId : this.dataId,
			reqClass : 'com.digitalchina.itil.require.entity.SpecialRequirement',
			rootId : ppId
		});
		temp.push(this.gridflm_projectPlanList);
		this.getFormserviceItemBasePanel();
		this.pa.push(this.formserviceItemBasePanel);
		this.formname.push("serviceItemBasePanel");
		temp.push(this.formserviceItemBasePanel);
//		this.getGridpanel_SRExpendPlan_list();
//		this.gd.push(this.gridpanel_SRExpendPlan_list);
//		this.gridname.push("panel_SRExpendPlan_list");
//		temp.push(this.gridpanel_SRExpendPlan_list);

		this.getFormpanel_SRTestReport();
		this.pa.push(this.formpanel_SRTestReport);
		this.formname.push("panel_SRTestReport");
		temp.push(this.formpanel_SRTestReport);
//		temp.push(this.getFeedback(this.dataId, this.taskId, this.surveyId));
		this.getFormpanel_SRUserFeedback_input();
		temp.push(this.formpanel_SRUserFeedback_input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("removePlan", this.removePlan, this);
		this.on("saveForGrid", this.saveForGrid, this);
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
		var tempId = Ext.getCmp('itil_SRUserFeedback$id').getValue();
		if(tempId==""){
			Ext.getCmp('itil_SRUserFeedback$completeCombo').setValue(1);
			Ext.getCmp('itil_SRUserFeedback$contentCombo').setValue(1);
		}
//add by musicbear for �ϸ��ڵ��ϴ��ĸ����ڴ˽ڵ���ʾ in 2009 11 10 start		
		var da=new DataAction();
		var url=webContext+'/SRAction_getTestReportFileList.action?columnid=8016&dataId='+this.dataId;
		var value=da.ajaxGetData(url);
		Ext.getCmp("TestReportFile").html=value.file;
//add by musicbear for �ϸ��ڵ��ϴ��ĸ����ڴ˽ڵ���ʾ in 2009 11 10 end		
	}
//	,
//	workflowForSubmit : function() {
//		
//		Ext.Ajax.request({
//			url : webContext + '/SRAction_findIsUserFeedbackOrNot.action?',
//			params : {
//				dataId:this.dataId,
//				surveyId:this.surveyId
//			},
//			success : function(response, options) {
//				window.parent.auditContentWin.audit();
//			},
//			failure : function(response, options) {
//				Ext.MessageBox.confirm('��ȷ��', 'δ��ɷ�������,�Ƿ������', function(button,text) {
//					if (button == 'yes') {
//						window.parent.auditContentWin.audit();
//					}
//				});
//			}
//		}, this);
//		
//		var isExist;
//		var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		var url = webContext+ '/SRAction_findIsUserFeedbackOrNot.action?dataId='
//				+ this.dataId + '&surveyId=' + this.surveyId;
//		conn.open("GET", url, false);
//		conn.send(null);
//		if (conn.status == "200") {
//			var responseText = conn.responseText;
//			var data = Ext.decode(responseText);
//			isExist = data.success;
//		}
//		//alert(isExist);
//		if (!isExist) {//δ��д�����ʾ�
//			Ext.MessageBox.confirm('��ȷ��', 'δ��ɷ�������,�Ƿ������', function(button,text) {
//				if (button == 'yes') {
//					window.parent.auditContentWin.audit();
//				}
//			});
//		} else {
//			window.parent.auditContentWin.audit();
//		}
//	}

})