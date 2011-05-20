var panel1 = "" ;
var normalFlag = 1;

PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	title : this.description,
	width : 1050,
	frame : true,
	autoScroll : true,
	buttonAlign : 'center',
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	save : function() {
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 begin
		if(Ext.getCmp('ERP_NormalNeed$name').getValue()==""){
			Ext.MessageBox.alert("提示","请输入申请名称！");	
			return false;
		}
		//add by lee for 更换为统一的验证规则结果后台配置必填项 in 20091103 end
		if(Ext.getCmp('ERP_NormalNeed$flatCombo').getRawValue()=="无"){//add by zhangzy for 申请选择“无”时bug
			Ext.MessageBox.alert("提示","请选择所属SBU/本部！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()==""||Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()=="请填写账期比例（必填）"){
			Ext.MessageBox.alert("提示","请填写申请内容");		
			return ;
		}else if(Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue()=='0'){			
			var otherInfovalue = Ext.getCmp("ERP_NormalNeed$otherInfo").getValue();
			var weightAccountValue = null;
			var tempweightAccountValue = null;	
			
			var str= new Array(); 
			str=otherInfovalue.split(",");      
			for (var i=0;i<str.length ;i++ )   
			{ 
				tempweightAccountValue = null;	
				var str2 = new Array();
				str2 = str[i].split("%");
				tempweightAccountValue =  str2[0];
				weightAccountValue = weightAccountValue*1+ tempweightAccountValue*1;
			}
			if(weightAccountValue !='100'){
				Ext.Msg.alert("提示","账期比例总和不是100%，请重新修改！");
				return;
			}
			
		}
//		if(Ext.getCmp('ERP_NormalNeed$weightAccount').getValue()==""){
//			Ext.MessageBox.alert("提示","请填写加权账期");	
//			return ;
//		}	
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
		if(scope1==false&&scope2==false&&scope3==false&&scope4==false&&
			scope5==false&&scope6==false&&scope7==false&&scope8==false){
			Ext.MessageBox.alert("提示","请选择使用范围！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}

		var formParam = Ext.encode(getFormParam(panel1));
		var curscid = this.serviceItemId;
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				info:formParam,
				pagePanel:panel1,
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
				Ext.MessageBox.alert("保存失败");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// 保存并提交
	saveAndSubmit : function() {
		if(Ext.getCmp('ERP_NormalNeed$flatCombo').getValue()==""){//add by zhangzy for 申请选择“无”时bug
			Ext.MessageBox.alert("提示","请选择所属SBU/本部！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return false;
		}
		if(Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()==""||Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()=="请填写账期比例（必填）"){
			Ext.MessageBox.alert("提示","请填写申请内容");		
			return false;
		}else if(Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue()=='0'){			
			var otherInfovalue = Ext.getCmp("ERP_NormalNeed$otherInfo").getValue();
			var weightAccountValue = null;
			var tempweightAccountValue = null;	
			
			var str= new Array(); 
			str=otherInfovalue.split(",");      
			for (var i=0;i<str.length ;i++ )   
			{ 
				tempweightAccountValue = null;	
				var str2 = new Array();
				str2 = str[i].split("%");
				tempweightAccountValue =  str2[0];
				weightAccountValue = weightAccountValue*1+ tempweightAccountValue*1;
			}
			if(weightAccountValue !='100'){
				Ext.Msg.alert("提示","账期比例总和不是100%，请重新修改！");
				return false;
			}
			
		}
		Ext.getCmp('saveButton').disable();
		Ext.getCmp('workFlowButton').disable();
		//var curscid = this.serviceItemId;
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
		if(scope1==false&&scope2==false&&scope3==false&&scope4==false&&
			scope5==false&&scope6==false&&scope7==false&&scope8==false){
			Ext.MessageBox.alert("提示","请选择使用范围！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return false;
		}
		var tempApplyName =  Ext.getCmp('ERP_NormalNeed$name').getValue();
		if(tempApplyName==""){
			Ext.MessageBox.alert("提示","请填写标题！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return false;
		}
		var tempRequireLevel = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
		var tempReason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
		if(tempRequireLevel==1&&tempReason==""){
			Ext.MessageBox.alert("提示","加急申请必须填写加急理由！");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return false;
		}
		var formParam = Ext.encode(getFormParam(panel1));
		var curscid = this.serviceItemId;
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveApplyDraft.action',
			params : {
				info:formParam,
				pagePanel:panel1,
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
							Ext.Msg.alert("提示", "申请提交成功", function() {//modify by zhangzy for 用户要求添加申请成功提示  2009 11 23
								window.location = webContext
									+ "/requireSIAction_toRequireInfoByServiceItemId.action?id="
									+ curscid;
							});
						} else {
							Ext.Msg.alert("提示", "申请提交失败", function() {
								alert(meg.Exception);
							});
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示", "申请提交失败");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("申请提交失败");
			}
		}, this);
	},
	back : function() {
		window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
	},

	getFormpanel1 : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		data = da.getRequireFormPanelElementsForEdit(
				panel1, this.dataId);
		for (i = 0; i < data.length; i++) {
			var idStr = data[i].id + "";
			if(idStr.indexOf('$requirementLevelCombo') > 0 && data[i].value==''){
				data[i].value='4';
			}

			if (idStr.indexOf('$applyNum') > 0|| idStr.indexOf('$applyUser') > 0
			) {
				data[i].readOnly = true;
				data[i].cls = "x-form-field-wrap";
				if (data[i].value == '') {
					data[i].emptyText = '自动生成';
					data[i].disabled = true;
				}
				if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
					data[i].hideTrigger = true;
				}
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
			
		}
		if (this.status == 1 || this.status == 2) {//add by zhangzy for 在非申请页面，元素设为只读 in 2009 11 25
			for (i = 0; i < data.length; i++) {
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].id = data[i].id + 8;// 改变Combobox的id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				data[i].readOnly = true;
			}
		}
		if(this.serviceItemId=='143'){	
			var modifyData = new Array();
			for(var i = 0; i<data.length;i++){
				if(data[i].id=='ERP_NormalNeed$weightAccount'){
					data[i].readOnly  = true;
				}
				if(data[i].id=='ERP_NormalNeed$isOverseasSaleCombo'){
					data[i].fieldLabel="<font color='#FF0000 '>是否海外销售</font>";
					if(data[i].value=='0'){						
						data[i].value='0';
					}else if(data[i].value=='1'){
						data[i].value='1';
					}else if(data[i].value==''){
						data[i].value='0';
					}
				}
				if(data[i].id=='ERP_NormalNeed$otherInfo'){
					data[i].readOnly  = true;
				}
				
				if(i==12){
					modifyData.push(this.getPercentAndDayCountPanel());
				}
				
				modifyData.push(data[i]);
			}
//			data[data.length] = new Ext.form.TextField({
//				id:"percent",
//				name:"percent",
//				fieldLabel:"比例%",
//				width:200
//			});
		}
		biddata = da.split(modifyData);
		var curbuttons = new Array();
		var saveButton = new Ext.Button({
			text : '保存为草稿',
			id : 'saveButton',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : this.save
		});
		var submitButton = new Ext.Button({
			text : '保存并提交',
			id : 'workFlowButton',
			pressed : true,
			iconCls : 'submit',
			scope : this,
			handler : this.saveAndSubmit
		});
		var backButton = new Ext.Button({
			text : '返回',
			id : 'refresh',
			pressed : true,
			iconCls : 'back',
			scope : this,
			handler : this.back
		});

		var normalFlagCombo = new Ext.form.ComboBox({
				id:'normalFlagCombo',
		        width:100,
				mode :"local",
				displayField :'name', 
				valueField : 'id',
				readOnly:true,
				triggerAction : "all",
				fieldLabel :"海外销售",
				value : '0',
				store:new Ext.data.SimpleStore({
						fields:['id','name'],
						data : [['1', '是'], ['0', '否']]
					})
		});
		if (this.status == 0) {
			curbuttons.push(saveButton);
			curbuttons.push(submitButton);
		}
		curbuttons.push(backButton);		
		this.formpanel1 = new Ext.form.FormPanel({
			id : panel1,
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
			tbar:curbuttons //,	
		});
		return this.formpanel1;
	},
	getPercentAndDayCountPanel : function(){
	var percentAndDayCountPanel = new Ext.form.FieldSet({
			title : '账期信息生成',
			fieldLabel:'账期信息生成', 
			id : 'percentAndDayCountPanel',
			width : 9999,
			layout :'table',
			buttonAlign : 'center',
			layoutConfig : {
						columns : 4
			},
			items : [
				{
					html : '<h3><font color=red>使用提示:</font></h3><br> 1、按照您的业务类型选择"是否海外销售"，系统默认为“否”。<br>2、如果不是海外销售，要首先填写“比例%”字段<font color=red>（比例%应为非负数，小数时保留3为有效数字）</font>，然后填写“天数”字段，点击“增加账期信息”按钮，系统会给“加权账期”、“申请内容”两个字段赋值，这两个字段不可手动编辑；点击“清除账期信息”按钮可逐个清除账期信息。<br>3、如果是海外申请，“申请内容”字段可以手动编辑。<br><br>',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0,
					style : 'border:1px dotted #b0acac;width:455;text-align:left;margin:0px 0px 0px 0px'
				},
			{html : '比例%:　',id :'percentText',cls : 'common-text',style : 'text-align:left;line-height:3'},		
			
			new Ext.form.NumberField({
					id:"percent",
					name:"percent",
					fieldLabel:"比例%",
					width:150,
					decimalPrecision:3,
					minValue:0
				}),	{html : '　　　　　　　　天数:　', id :'dayCountText',cls : 'common-text',style : 'line-height:3'},
					new Ext.form.NumberField({
										id:"dayCount",
										name:"dayCount",
										fieldLabel:"天数",
										width:150,
										//add by lee for 增加验证 in 20100302 begin
										validator:function(value){
												var regu = /^[0-9]*[1-9][0-9]*$/;
												return regu.test(value);
												},
										validationEvent:'请输入大于0数字'
										//add by lee for 增加验证 in 20100302 end
					})
			],
			 buttons: [{
       				id:"addMessage",
					text :"增加账期信息",
					style : "width:150;text-align:right;",
					width:150,
					handler:function(){

						var percentVlaue =  Ext.getCmp("percent").getValue();
						var dayCountVlaue =  Ext.getCmp("dayCount").getValue();	
						var weightAccountValue = Ext.getCmp("ERP_NormalNeed$weightAccount").getValue();
						var tempweightAccountValue = null;
						if(percentVlaue==""){
								Ext.MessageBox.alert("提示","请填写比例%");
								return;
						}
						if(dayCountVlaue==""){
							if(dayCountVlaue!='0'){
									Ext.MessageBox.alert("提示","请填写天数");
									return;
							}		
						}
						var otherInfovalue = Ext.getCmp('ERP_NormalNeed$otherInfo').getValue();
						if(otherInfovalue == ""){
							otherInfovalue = otherInfovalue+percentVlaue+"%"+dayCountVlaue+"天";
						}else{
							otherInfovalue = otherInfovalue+","+percentVlaue+"%"+dayCountVlaue+"天";
						}
						Ext.getCmp('ERP_NormalNeed$otherInfo').setValue(otherInfovalue);
						
						 var str= new Array(); 
						  str=otherInfovalue.split(",");      
						    for (var i=0;i<str.length ;i++ )   
						    { 
						        var str2 = new Array();
						        str2 = str[i].split("%");
						        str2[1] = str2[1].replace("天","");
						        tempweightAccountValue =  (str2[0]*str2[1]*0.01); 									
						   }  
						   weightAccountValue = weightAccountValue*1 + tempweightAccountValue;
						 Ext.getCmp("ERP_NormalNeed$weightAccount").setValue(weightAccountValue);  						
					}
   			 },{
        			id:"deleteMessage",
					text :"清除账期信息",
					width:150,
					handler:function(){
						var otherInfovalue = Ext.getCmp("ERP_NormalNeed$otherInfo").getValue();

						otherInfovalue =otherInfovalue.substring(0, otherInfovalue.lastIndexOf(","));
						Ext.getCmp("ERP_NormalNeed$otherInfo").setValue(otherInfovalue);
						if(otherInfovalue == null || otherInfovalue ==""){
							Ext.getCmp("ERP_NormalNeed$weightAccount").setValue("");
							return;
						}						
						var weightAccountValue = null;
						var tempweightAccountValue = null;				
						
						 var str= new Array(); 
						  str=otherInfovalue.split(",");      
						    for (var i=0;i<str.length ;i++ )   
						    { 
						    	tempweightAccountValue = null;	
						        var str2 = new Array();
						        str2 = str[i].split("%");
						        str2[1] = str2[1].replace("天","");
						        tempweightAccountValue =  (str2[0]*str2[1]*0.01); 	
						       	weightAccountValue = weightAccountValue*1+ tempweightAccountValue;						        
						   } 
						 Ext.getCmp("ERP_NormalNeed$weightAccount").setValue(weightAccountValue);  	
						 Ext.getCmp("percent").setValue("");
						 Ext.getCmp("dayCount").setValue("");
					}
    }]

		});
		return 	percentAndDayCountPanel;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		if(this.serviceItemId=='143'){
			panel1 = "panel_ERP_NormalNeed4_Input";
		}		
		this.getFormpanel1();

		 var items = new Array();		
		 this.model = "nrf_nomalNeedApply1";	
		 if(Ext.getCmp("ERP_NormalNeed$otherInfo").getValue()!=null){
		 	Ext.getCmp("ERP_NormalNeed$otherInfo").height = 80;
		 }
			var spId = this.serviceItemProcessId;
			var tempId = this.dataId;
			if(tempId==""){
				tempId=0;
			}
			if(this.status != 0){
			var histroyForm = new HistroyForm({
				reqId : tempId,
				reqClass : "com.zsgj.itil.require.entity.ERP_NormalNeed"
			});
			this.tab = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			defaults : {
				autoHeight : true,
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : [this.formpanel1, histroyForm]
		});
			this.items = [this.tab];
		
		} else {
			this.items = [this.formpanel1];
		}
		Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').on('select',function(){	
					var normalFlag = Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue();
					if(normalFlag == '1'){ 
						Ext.getCmp('ERP_NormalNeed$otherInfo').setValue("请填写账期比例（必填）");
						Ext.getCmp('ERP_NormalNeed$weightAccount').setValue("");
						Ext.getCmp('ERP_NormalNeed$otherInfo').getEl().dom.readOnly=false;
						Ext.getCmp('percentAndDayCountPanel').hide();
						Ext.getCmp('percentAndDayCountPanel').el.parent().parent().hide();
					}
				
		});	
		if( Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue()=='1'){
						Ext.getCmp('ERP_NormalNeed$otherInfo').readOnly=false;
						Ext.getCmp('percentAndDayCountPanel').hide();
							
		}		
		Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').on('select',function(){	
					var normalFlag = Ext.getCmp('ERP_NormalNeed$isOverseasSaleCombo').getValue();
					if(normalFlag == '0'){
						Ext.getCmp('ERP_NormalNeed$otherInfo').setValue("");
						Ext.getCmp('ERP_NormalNeed$weightAccount').setValue("");
						Ext.getCmp('ERP_NormalNeed$otherInfo').getEl().dom.readOnly=true;
						Ext.getCmp('ERP_NormalNeed$weightAccount').getEl().dom.readOnly=true;
						Ext.getCmp('percentAndDayCountPanel').show();
						Ext.getCmp('percentAndDayCountPanel').el.parent().parent().show();						
//						Ext.getCmp('addMessage').show();
//						Ext.getCmp('deleteMessage').show();	
//						Ext.getCmp("percent").show();
//						Ext.getCmp("dayCount").show();
//						Ext.getCmp("percentText").show();
//						Ext.getCmp("dayCountText").show();
					}
				
		});			
//modify by zhangzy for “我审批的” 无法打开 in 2009 11 25 end	 
//		if(this.dataId==""&&this.serviceItemId=='143'){//付款条款申请时加必填
//			Ext.getCmp('ERP_NormalNeed$otherInfo').setValue("请填写账期比例（必填）");
//		}
		PageTemplates.superclass.initComponent.call(this);
	}
})