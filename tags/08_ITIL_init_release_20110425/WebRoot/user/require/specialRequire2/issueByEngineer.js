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
	saveAndSubmit:function(){
		
		var tempId = Ext.getCmp('SRTransmisEngineer$id').getValue();
		var tempNeed = Ext.getCmp('SRTransmisEngineer$needOrNotCombo').getValue();
		var tempEngineer = Ext.getCmp('SRTransmisEngineer$userInfoCombo').getValue();
		var reqId = this.dataId;
		var tkId = this.taskId;
		
		if(tempNeed==""){
			Ext.MessageBox.alert("��ʾ", "��ѡ���Ƿ��䣡");
		}else if(tempNeed=="1"&&tempEngineer==""){
			Ext.MessageBox.alert("��ʾ", "��ѡ���乤��ʦ��");
		}else{
			Ext.Ajax.request({
			url : webContext + '/SRAction_saveTEengineerForReq.action?',
			params : {
				id:tempId,
				reqId:reqId,
				need:tempNeed,
				userId:tempEngineer
			},

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var panId = responseArray.id;
				Ext.getCmp('SRTransmisEngineer$id').setValue(panId);

				if (tempNeed == "1" ) {
					Ext.Ajax.request({
						url : webContext+ '/extjs/workflow?method=getData&taskId='
								+ tkId + '&users=transmitByEngineer:' + tempEngineer,
						method : 'post',
						success : function(response, options) {
							window.parent.auditContentWin.specialAudit();
						},
						failure : function(response, options) {

						}
					})
				}else{
					window.parent.auditContentWin.specialAudit();//modify by zhangzy for �ܾ����̳��� in 2009 11 16
				}
				
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
		}
	},
//	getFeedback : function(dataId, taskId, surveyId){
//  		return new Ext.Panel({
//  			title : "�û��������",
//  			id:"feedback_success",
//  			width : 'auto',
//  			heigth : 'auto',
//  			frame : true,
//  			autoScroll : true,
//  			autoLoad : {
//  				url: webContext + '/SRAction_findQuestionsResult.action?surveyId=' + surveyId + '&dataId=' + dataId + "&taskId=" + taskId,
//  				nocache : true, 
//  				scripts :true,
//  				text : "ҳ�����ڼ�����......",
//				method : 'post',
//				scope : this
//  			},
//  			viewConfig : {
//				autoFill : true,
//				forceFit : true
//			},
//			layout : 'fit'
//  		});
//	},
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
            //minTabWidth:100,
            //resizeTabs:true,
            title:tabTitle,
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
	getPanel : function(appa,panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title:panelTitle,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	}, 
	
	
 getFormpanel_SpecialRequire4_Input: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SpecialRequire4_Input",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("sr2_issueByEngineer", "panel_SpecialRequire4_Input", this.dataId);// ����Ҫ��ʱ���
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
			} else {
				data = da.getPanelElementsForAdd("panel_SpecialRequire4_Input");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_SpecialRequire4_Input= new Ext.form.FormPanel({
			id : 'panel_SpecialRequire4_Input',
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
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_SpecialRequire4_Input= new Ext.form.FormPanel({
			id : 'panel_SpecialRequire4_Input',
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
			items : biddata
			});
		}
		return this.formpanel_SpecialRequire4_Input;
	},
 getFormpanel_SRServiceProviderInfo_Input: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRServiceProviderInfo_Input",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("sr2_issueByEngineer", "panel_SRServiceProviderInfo_Input", this.dataId);// ����Ҫ��ʱ���
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
			} else {
				data = da.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_SRServiceProviderInfo_Input= new Ext.form.FormPanel({
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
			title : "ҵ���༰�����Ŷ�",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_SRServiceProviderInfo_Input= new Ext.form.FormPanel({
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
			title : "ҵ���༰�����Ŷ�",
			items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},
 getFormpanel_SRAnalyse2: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse2",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("sr2_issueByEngineer", "panel_SRAnalyse2", this.dataId);// ����Ҫ��ʱ���
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
			} else {
				data = da.getPanelElementsForAdd("panel_SRAnalyse2");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_SRAnalyse2= new Ext.form.FormPanel({
			id : 'panel_SRAnalyse2',
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
			title : "��Ŀ�������",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_SRAnalyse2= new Ext.form.FormPanel({
			id : 'panel_SRAnalyse2',
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
			title : "��Ŀ�������",
			items : biddata
			});
		}
		return this.formpanel_SRAnalyse2;
	},
// getFormpanel_SRProjectPlan: function() {
//      var da = new DataAction();
//		  var data = null;
//			// �ж������������޸�
//			var biddata = "";
//			
//			  var buttonUtil = new ButtonUtil();
//			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRProjectPlan",this);
//			if (this.dataId != '0') {
//				data = da.getPanelElementsForEdit("sr2_issueByEngineer", "panel_SRProjectPlan", this.dataId);// ����Ҫ��ʱ���
//				biddata = da.split(data);
//			} else {
//				data = da.getPanelElementsForAdd("panel_SRProjectPlan");
//				biddata = da.split(data);
//			}
//			if(this.getFormButtons.length!=0){
//		this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
//			id : 'panel_SRProjectPlan',
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			frame : true,
//			collapsible : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "���Ի�������Ŀ�ƻ�ʵ��(N)",
//			items : biddata,
//			buttons : this.getFormButtons
//		});
//		}else{
//			this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
//			id : 'panel_SRProjectPlan',
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			frame : true,
//			collapsible : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "���Ի�������Ŀ�ƻ�ʵ��(N)",
//			items : biddata
//			});
//		}
//		return this.formpanel_SRProjectPlan;
//	},
getGridpanel_SRTestReport:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
		  var buttonUtil = new ButtonUtil();
		  var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",this);
		//add by lee for add attachmentfile in 20090806 begin
		var curDataId = this.dataId;
		var fileButton =  new Ext.Button({
			text : '���Ը���',
			pressed : true,
			iconCls : 'file',
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
		getGridButtons.push(fileButton);
//add by lee for add attachmentfile in 20090806 begin
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
					editor : editor
					}
				}else if(editor.xtype=='datefield'){
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
                    renderer : renderer,                              
					editor : editor
				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRTestReport",obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRTestReport= new Ext.grid.EditorGridPanel({
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title :"�û����Ա���",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
			if (this.dataId != '0') {
					var pcnameValue = "";
					var fcnameObj = Ext.getCmp("SpecialRequirement$id");
					if(fcnameObj!=undefined){
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
		return this.gridpanel_SRTestReport;
	},
//add by lee for modify userFeedback in 20090806 begin 
	getFormpanel_SRUserFeedback_input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRUserFeedback_input",this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_testByClient",
					"panel_SRUserFeedback_input", this.dataId);// ����Ҫ��ʱ���	
			for (i = 0; i < data.length; i++) {
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRUserFeedback_input");
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
 getFormpanel_SRTransmisEngineer: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRTransmisEngineer",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("sr2_issueByEngineer", "panel_SRTransmisEngineer", this.dataId);// ����Ҫ��ʱ���
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("panel_SRTransmisEngineer");
				biddata = da.split(data);
			}
//			if(this.getFormButtons.length!=0){
//		this.formpanel_SRTransmisEngineer= new Ext.form.FormPanel({
//			id : 'panel_SRTransmisEngineer',
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			frame : true,
//			collapsible : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "<font color=red>���乤��ʦ</font>",
//			items : biddata,
//			buttons : this.getFormButtons
//		});
//		}else{
			this.formpanel_SRTransmisEngineer= new Ext.form.FormPanel({
			id : 'panel_SRTransmisEngineer',
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
			title : "<font color=red>���乤��ʦ</font>",
			items : biddata
			});
//		}
		return this.formpanel_SRTransmisEngineer;
	},
  items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
//	   var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		var url = webContext+'/SRAction_findSpecialRequireSurvey.action';
//		conn.open("get", url, false);
//		conn.send(null);
//		if (conn.status == "200") {
//			var responseText = conn.responseText;
//			var data = Ext.decode(responseText);
//			this.surveyId = data.surveyId;
//		} 
		this.removedIds="";
		var sra = new SRAction();
		var ppId = sra.getRootProjectPlanId(this.dataId);
		var pnId = sra.getProjectNoticeId(this.dataId);
		
	    var items = new Array();
	    var pa = new Array();
		  this.pa = pa;
		var gd = new Array();
		  this.gd = gd;
		var temp = new Array();
		  this.temp = temp;
		var formname = new Array();
		  this.formname=formname;
		var gridname = new Array();
		  this.gridname=gridname;
		this.model="sr2_issueByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("sr2_issueByEngineer",this);
		if(this.mybuttons!=""){
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
		}else{
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
		
		       this.getFormpanel_SpecialRequire4_Input();
		       this.pa.push(this.formpanel_SpecialRequire4_Input);
		       this.formname.push("panel_SpecialRequire4_Input");
		       temp.push(this.formpanel_SpecialRequire4_Input);
		       this.getFormpanel_SRServiceProviderInfo_Input();
		       this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
		       this.formname.push("panel_SRServiceProviderInfo_Input");
		       temp.push(this.formpanel_SRServiceProviderInfo_Input);
		       this.getFormpanel_SRAnalyse2();
		       this.pa.push(this.formpanel_SRAnalyse2);
		       this.formname.push("panel_SRAnalyse2");
		       temp.push(this.formpanel_SRAnalyse2);
//		       this.getFormpanel_SRProjectPlan();
//		       this.pa.push(this.formpanel_SRProjectPlan);
//		       this.formname.push("panel_SRProjectPlan");
//		       temp.push(this.formpanel_SRProjectPlan);
		       
//		       this.gridflm_projectPlanList = new projectPlanListPanel({dataId:this.dataId,rootId:ppId});
//		       temp.push(this.gridflm_projectPlanList);
		       
		       this.getGridpanel_SRTestReport();
		       this.gd.push(this.gridpanel_SRTestReport);
		       this.gridname.push("panel_SRTestReport");
		       temp.push(this.gridpanel_SRTestReport);
		       
		       this.getFormpanel_SRUserFeedback_input();
				temp.push(this.formpanel_SRUserFeedback_input);
//		       temp.push(this.getFeedback(this.dataId, this.taskId, this.surveyId));
		       
		       this.getFormpanel_SRTransmisEngineer();
		       this.pa.push(this.formpanel_SRTransmisEngineer);
		       this.formname.push("panel_SRTransmisEngineer");
		       temp.push(this.formpanel_SRTransmisEngineer);
		       temp.push(histroyForm);
          items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		   this.items = items;
		   this.on('saveAndSubmit', this.saveAndSubmit, this);
		   Ext.getCmp('SRTransmisEngineer$needOrNotCombo').on('select',function(combo, record, index) {
								var needOrNot = Ext.getCmp('SRTransmisEngineer$needOrNotCombo').getValue();

								if (needOrNot == '0') {
									Ext.getCmp('SRTransmisEngineer$userInfoCombo').clearValue();
									Ext.getCmp('SRTransmisEngineer$userInfoCombo').disable();
								}else{
									Ext.getCmp('SRTransmisEngineer$userInfoCombo').clearValue();
									Ext.getCmp('SRTransmisEngineer$userInfoCombo').enable();
								}
							}
						);
		PageTemplates.superclass.initComponent.call(this);
				//add by lee for add default value for choose transmisEngineer in 20090819
		var tempTEngineer = Ext.getCmp('SRTransmisEngineer$id').getValue();
		if(tempTEngineer==''){
			Ext.getCmp('SRTransmisEngineer$needOrNotCombo').setValue(0);
		}
		//add by lee for add default value for choose transmisEngineer in 20090819
	}
})