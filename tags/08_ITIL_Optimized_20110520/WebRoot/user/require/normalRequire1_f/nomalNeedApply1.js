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
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 begin
		if(Ext.getCmp('ERP_NormalNeed$name').getValue()==""){
			Ext.MessageBox.alert("��ʾ","�������������ƣ�");	
			return false;
		}
		//add by lee for ����Ϊͳһ����֤��������̨���ñ����� in 20091103 end
		if(Ext.getCmp('ERP_NormalNeed$flatCombo').getRawValue()=="��"){//add by zhangzy for ����ѡ���ޡ�ʱbug
			Ext.MessageBox.alert("��ʾ","��ѡ������SBU/������");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return;
		}
		if(Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()==""||Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()=="����д���ڱ��������"){
			Ext.MessageBox.alert("��ʾ","����д��������");		
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
				Ext.Msg.alert("��ʾ","���ڱ����ܺͲ���100%���������޸ģ�");
				return;
			}
			
		}
//		if(Ext.getCmp('ERP_NormalNeed$weightAccount').getValue()==""){
//			Ext.MessageBox.alert("��ʾ","����д��Ȩ����");	
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
			Ext.MessageBox.alert("��ʾ","��ѡ��ʹ�÷�Χ��");
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
				Ext.MessageBox.alert("����ʧ��");
				Ext.getCmp('saveButton').enable();
				Ext.getCmp('workFlowButton').enable();
			}
		}, this);
	},
	// ���沢�ύ
	saveAndSubmit : function() {
		if(Ext.getCmp('ERP_NormalNeed$flatCombo').getValue()==""){//add by zhangzy for ����ѡ���ޡ�ʱbug
			Ext.MessageBox.alert("��ʾ","��ѡ������SBU/������");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return false;
		}
		if(Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()==""||Ext.getCmp('ERP_NormalNeed$otherInfo').getValue()=="����д���ڱ��������"){
			Ext.MessageBox.alert("��ʾ","����д��������");		
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
				Ext.Msg.alert("��ʾ","���ڱ����ܺͲ���100%���������޸ģ�");
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
			Ext.MessageBox.alert("��ʾ","��ѡ��ʹ�÷�Χ��");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return false;
		}
		var tempApplyName =  Ext.getCmp('ERP_NormalNeed$name').getValue();
		if(tempApplyName==""){
			Ext.MessageBox.alert("��ʾ","����д���⣡");
			Ext.getCmp('saveButton').enable();
			Ext.getCmp('workFlowButton').enable();
			return false;
		}
		var tempRequireLevel = Ext.getCmp('ERP_NormalNeed$requireLevelCombo').getValue();
		var tempReason = Ext.getCmp('ERP_NormalNeed$reason').getValue();
		if(tempRequireLevel==1&&tempReason==""){
			Ext.MessageBox.alert("��ʾ","�Ӽ����������д�Ӽ����ɣ�");
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
							Ext.Msg.alert("��ʾ", "�����ύ�ɹ�", function() {//modify by zhangzy for �û�Ҫ���������ɹ���ʾ  2009 11 23
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

	getFormpanel1 : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
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
					data[i].emptyText = '�Զ�����';
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
		if(this.serviceItemId=='143'){	
			var modifyData = new Array();
			for(var i = 0; i<data.length;i++){
				if(data[i].id=='ERP_NormalNeed$weightAccount'){
					data[i].readOnly  = true;
				}
				if(data[i].id=='ERP_NormalNeed$isOverseasSaleCombo'){
					data[i].fieldLabel="<font color='#FF0000 '>�Ƿ�������</font>";
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
//				fieldLabel:"����%",
//				width:200
//			});
		}
		biddata = da.split(modifyData);
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

		var normalFlagCombo = new Ext.form.ComboBox({
				id:'normalFlagCombo',
		        width:100,
				mode :"local",
				displayField :'name', 
				valueField : 'id',
				readOnly:true,
				triggerAction : "all",
				fieldLabel :"��������",
				value : '0',
				store:new Ext.data.SimpleStore({
						fields:['id','name'],
						data : [['1', '��'], ['0', '��']]
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
			title : '������Ϣ����',
			fieldLabel:'������Ϣ����', 
			id : 'percentAndDayCountPanel',
			width : 9999,
			layout :'table',
			buttonAlign : 'center',
			layoutConfig : {
						columns : 4
			},
			items : [
				{
					html : '<h3><font color=red>ʹ����ʾ:</font></h3><br> 1����������ҵ������ѡ��"�Ƿ�������"��ϵͳĬ��Ϊ���񡱡�<br>2��������Ǻ������ۣ�Ҫ������д������%���ֶ�<font color=red>������%ӦΪ�Ǹ�����С��ʱ����3Ϊ��Ч���֣�</font>��Ȼ����д���������ֶΣ����������������Ϣ����ť��ϵͳ�������Ȩ���ڡ������������ݡ������ֶθ�ֵ���������ֶβ����ֶ��༭����������������Ϣ����ť��������������Ϣ��<br>3������Ǻ������룬���������ݡ��ֶο����ֶ��༭��<br><br>',
					cls : 'common-text',
					colspan : 4,
					rowspan : 0,
					style : 'border:1px dotted #b0acac;width:455;text-align:left;margin:0px 0px 0px 0px'
				},
			{html : '����%:��',id :'percentText',cls : 'common-text',style : 'text-align:left;line-height:3'},		
			
			new Ext.form.NumberField({
					id:"percent",
					name:"percent",
					fieldLabel:"����%",
					width:150,
					decimalPrecision:3,
					minValue:0
				}),	{html : '��������������������:��', id :'dayCountText',cls : 'common-text',style : 'line-height:3'},
					new Ext.form.NumberField({
										id:"dayCount",
										name:"dayCount",
										fieldLabel:"����",
										width:150,
										//add by lee for ������֤ in 20100302 begin
										validator:function(value){
												var regu = /^[0-9]*[1-9][0-9]*$/;
												return regu.test(value);
												},
										validationEvent:'���������0����'
										//add by lee for ������֤ in 20100302 end
					})
			],
			 buttons: [{
       				id:"addMessage",
					text :"����������Ϣ",
					style : "width:150;text-align:right;",
					width:150,
					handler:function(){

						var percentVlaue =  Ext.getCmp("percent").getValue();
						var dayCountVlaue =  Ext.getCmp("dayCount").getValue();	
						var weightAccountValue = Ext.getCmp("ERP_NormalNeed$weightAccount").getValue();
						var tempweightAccountValue = null;
						if(percentVlaue==""){
								Ext.MessageBox.alert("��ʾ","����д����%");
								return;
						}
						if(dayCountVlaue==""){
							if(dayCountVlaue!='0'){
									Ext.MessageBox.alert("��ʾ","����д����");
									return;
							}		
						}
						var otherInfovalue = Ext.getCmp('ERP_NormalNeed$otherInfo').getValue();
						if(otherInfovalue == ""){
							otherInfovalue = otherInfovalue+percentVlaue+"%"+dayCountVlaue+"��";
						}else{
							otherInfovalue = otherInfovalue+","+percentVlaue+"%"+dayCountVlaue+"��";
						}
						Ext.getCmp('ERP_NormalNeed$otherInfo').setValue(otherInfovalue);
						
						 var str= new Array(); 
						  str=otherInfovalue.split(",");      
						    for (var i=0;i<str.length ;i++ )   
						    { 
						        var str2 = new Array();
						        str2 = str[i].split("%");
						        str2[1] = str2[1].replace("��","");
						        tempweightAccountValue =  (str2[0]*str2[1]*0.01); 									
						   }  
						   weightAccountValue = weightAccountValue*1 + tempweightAccountValue;
						 Ext.getCmp("ERP_NormalNeed$weightAccount").setValue(weightAccountValue);  						
					}
   			 },{
        			id:"deleteMessage",
					text :"���������Ϣ",
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
						        str2[1] = str2[1].replace("��","");
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
	 * clientvalidation ��ʼ��
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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
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
						Ext.getCmp('ERP_NormalNeed$otherInfo').setValue("����д���ڱ��������");
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
//modify by zhangzy for ���������ġ� �޷��� in 2009 11 25 end	 
//		if(this.dataId==""&&this.serviceItemId=='143'){//������������ʱ�ӱ���
//			Ext.getCmp('ERP_NormalNeed$otherInfo').setValue("����д���ڱ��������");
//		}
		PageTemplates.superclass.initComponent.call(this);
	}
})