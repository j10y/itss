var str= new Array(); 
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
	// 暂存
	saveForTemp : function() {
		if(!Ext.getCmp('panel_ErpEngineerFeedback_f_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		if(Ext.getCmp("itil_sci_ErpEngineerFeedback$otherInfo").getValue()==""){
			Ext.Msg.alert("提示","请填写‘ERP工程师反馈’内容，此信息是必填项");
			return false;
		}	
		Ext.Ajax.request({
			url : webContext+ '/SRAction_reAssignAudit.action?taskId='+this.taskId,

			success : function(response, options) {
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("错误信息","重新绑定本节点审批人失败");
			}
		}, this);
		Ext.Ajax.request({
			url : webContext+ '/requireAction_saveLockUser.action?taskId='+this.taskId,

			success : function(response, options) {
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("错误信息","审批节点加锁失败");
			}
		}, this);
		var formParam = Ext.encode(getFormParam('panel_ErpEngineerFeedback_f_Input'));
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
				Ext.MessageBox.alert("暂存成功");
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	saveAndSubmit : function() {
		if(!Ext.getCmp('panel_ErpEngineerFeedback_f_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		if(Ext.getCmp("itil_sci_ErpEngineerFeedback$otherInfo").getValue()==""){
			Ext.Msg.alert("提示","请填写‘ERP工程师反馈’内容，此信息是必填项");
			return false;
		}
		var formParam = Ext.encode(getFormParam('panel_ErpEngineerFeedback_f_Input'));
		var reqId = this.dataId;
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveErpEngineerFeedback.action?reqId='+reqId,
			params : {info:formParam},
			success : function(response, options) {
				var feedbackId = Ext.util.JSON.decode(response.responseText).id;
				Ext.getCmp('itil_sci_ErpEngineerFeedback$id').setValue(feedbackId);
				window.parent.auditContentWin.specialAudit();
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
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
			baseCls : 'x-plain',// 是否设置和背景色同步
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

	getFormpanel_ERP_NormalNeed4_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed4_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nrf_operateByEngineer",
					"panel_ERP_NormalNeed4_Input", this.dataId);// 这是要随时变得
					
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed4_Input");
			for(var i = 0; i<data.length;i++){
					if(data[i].id=='ERP_NormalNeed$oldSystemLink'){
						data[i].fieldLabel='加权账期';
						data[i].readOnly  = true;
					}
			}				
			biddata = da.split(data);
		}
	
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed4_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed4_Input',
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
				title : "ERP常规服务申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed4_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed4_Input',
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
				title : "ERP常规服务申请",
				items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed4_Input;
	},
	getFormpanel_ErpEngineerFeedback_f_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ErpEngineerFeedback_f_Input", this);
		var modifyData = new Array();
		var otherInfovalue = Ext.getCmp('ERP_NormalNeed$otherInfo').getValue();
		 str=otherInfovalue.split("%"); 			
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nrf_operateByEngineer",
					"panel_ErpEngineerFeedback_f_Input", this.dataId);// 这是要随时变得
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
				if(i==3){
					if(!(Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue()=='1' || str[0]=='100')){
						modifyData.push(this.getPercentAndDayCountPanel());
					}
				}	
				if(Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue()=='1' || str[0]=='100'){
					if(data[i].id == 'itil_sci_ErpEngineerFeedback$transferRequestNumber'){
						continue;
					}
				}
				modifyData.push(data[i]);
			}

			biddata = da.split(modifyData);
		} else {
			data = da.getPanelElementsForEdit("nrf_operateByEngineer",
					"panel_ErpEngineerFeedback_f_Input", this.dataId);// 这是要随时变得
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
			this.formpanel_ErpEngineerFeedback_f_Input = new Ext.form.FormPanel({
				id : 'panel_ErpEngineerFeedback_f_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,	
				hideLabel : true,
				hidden : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "<font color=red>Erp工程师反馈</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ErpEngineerFeedback_f_Input = new Ext.form.FormPanel({
				id : 'panel_ErpEngineerFeedback_f_Input',
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
				title : "<font color=red>Erp工程师反馈</font>",
				items : biddata
			});
		}
		return this.formpanel_ErpEngineerFeedback_f_Input;
	},
	getPercentAndDayCountPanel : function(){
	var percentAndDayCountPanel = new Ext.form.FieldSet({
			title : '写入ERP',
			fieldLabel:'写入ERP', 
			id : 'percentAndDayCountPanel',
			width : 9999,
			layout :'table',
			buttonAlign : 'center',
			hideLabel : true,
			layoutConfig : {
						columns : 4
			},
			items : [
				{
					html : '<h3><font color=red>使用提示:</font></h3><br>1、如果不是"海外销售"类型的申请，请先输入“传输请求号”，点击"写入ERP"按钮，系统会自动生成"ERP账期编号",并对"反馈"字段赋值。<br>2、如果是"海外销售"类型的申请，"写入ERP"按钮无法使用。<br>3、"反馈"字段可以手动编辑修改。<br><br>',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0,
					style : 'border:1px dotted #b0acac;width:455;text-align:left;margin:0px 0px 0px 0px'
				}
			],
			 buttons: [{
       				id:"writeErp",
					text :"写入ERP",
					style : "width:150;text-align:right;",
					width:150,
					handler:function(){	
						var normalFlag = Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue();
						var transferRequestNumber = Ext.getCmp('itil_sci_ErpEngineerFeedback$transferRequestNumber').getValue();
							if(transferRequestNumber == null || transferRequestNumber == "" ){
								Ext.Msg.alert("提示信息","请先输入“传输请求号”");
								return;
							}							
						if(normalFlag == '1'){
							Ext.Msg.alert("提示信息","海外销售类型申请无法使用“写入ERP”按钮，请手动编辑“反馈”字段即可");
							return;
						}else{
							Ext.Ajax.request({
								url : webContext+ '/requireAction_getErpAccountId.action',
								params : {
									dataId:this.dataId,
									weightAccount:Ext.getCmp('ERP_NormalNeed$weightAccount').getValue(),
									otherInfo:Ext.getCmp('ERP_NormalNeed$otherInfo').getValue(),
									transferRequestNumber:transferRequestNumber
									
								},
								success : function(response, options) {
									var responseArray =Ext.util.JSON.decode(response.responseText);	
									
 									var text = "";
									for(var i=0;i<responseArray.length;i++){
										if(i==0){
											text +="<p style='font-size: 14px;'>SUBRC:"+responseArray[i].SUBRC+"</p>";
											var curValue = Ext.getCmp('itil_sci_ErpEngineerFeedback$otherInfo').getValue();
											Ext.getCmp('itil_sci_ErpEngineerFeedback$otherInfo').setValue(curValue+responseArray[i].erpAccountId);											
										}else{
											text +="<p style='font-size: 14px;'> ARBGB:"+responseArray[i].ARBGB+", MSGNR:"+responseArray[i].MSGNR+", TEXT:"+responseArray[i].TEXT+"。</p>";
										}
									}	
									var resultWindow = new Ext.Window({
										title : '写入ERP反馈信息',
										width : 500,
										height : 250,
										autoScroll : true,
										html : text
									});
									resultWindow.show();									
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("错误信息","获取ERP账期编号失败");
								}
							}, this);
						}
					}
   			 }]

		});
		return 	percentAndDayCountPanel;
	},
	items : this.items,
	buttons : {id:'pageButtons' ,item:this.buttons},
	/*
	 * clientvalidation 初始化
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
		this.model = "nrf_operateByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nrf_operateByEngineer",
				this);		
		this.mybuttons.disabled=true;		
		this.buttons = new Array();
		if (this.readOnly != 1) {
			this.buttons = this.mybuttons;
		}
		if(!isLockUser){
			for(var i=0;i<this.buttons.length;i++){
				this.buttons[i].disabled=true;
			}								
		}		
		this.getFormpanel_ERP_NormalNeed4_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed4_Input);
		this.formname.push("panel_ERP_NormalNeed4_Input");
		temp.push(this.formpanel_ERP_NormalNeed4_Input);
		this.getFormpanel_ErpEngineerFeedback_f_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_f_Input);
		this.formname.push("panel_ErpEngineerFeedback_f_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_f_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		this.on("saveAndAssign", this.saveAndAssign, this);
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		this.on('saveForTemp', this.saveForTemp, this);
		var tempErpId = Ext.getCmp('itil_sci_ErpEngineerFeedback$id')
				.getValue();
		PageTemplates.superclass.initComponent.call(this);	
	},
	workflowForGroup : function() {
		var id = Ext.getCmp('itil_sci_RequirementEngineer$id').getValue();
		if (id == "") {
			Ext.MessageBox.alert("提示", "请选择是否需要BASIS工程师处理并保存!");
			return;
		}
		window.parent.auditContentWin.audit();
		
	}
	
})