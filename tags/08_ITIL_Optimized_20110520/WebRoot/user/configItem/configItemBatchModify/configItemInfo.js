ConfigItemInfo = Ext.extend(Ext.TabPanel, {
	id : "configItem",
	frame : true,
	activeTab : 0,
	autoScroll : true,
	layoutOnTabChange:true,
	deferredRender : false,
	buttonAlign:'center',
	forceLayout:true,
	buttons:[
		  	{
			text:'������������Ϣ',
			iconCls:'save',
			handler:function(){
				if(Ext.getCmp("ConfigItem$configItemTypeCombo").getValue()==""){
						Ext.Msg.alert("��ʾ","��ѡ�����������ͣ�",function(){
						});
						return;
				}
				if(!Ext.getCmp('basicPanel').form.isValid()||
				   !Ext.getCmp('financePanel').form.isValid()||
				   !Ext.getCmp('extendPanel').form.isValid()||
				   !Ext.getCmp('planPanel').form.isValid()){
					Ext.MessageBox.alert("��ʾ",'����д����Ϣ����������ȷ.');
					return;
				}
				if(Ext.getCmp('CIBatchModifyPlan$startDate').getValue()>Ext.getCmp('CIBatchModifyPlan$endDate').getValue()){
					Ext.MessageBox.alert("��ʾ",'����ƻ��Ŀ�ʼʱ�䲻�ܴ��ڽ���ʱ��.');
					return;
				}
				var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
				var cisn=Ext.getCmp("ConfigItem$cisn").getValue().trim();
				var configItemStatus=Ext.getCmp("ConfigItem$configItemStatusCombo").getValue();
				var configItemStatusRaw=Ext.getCmp("ConfigItem$configItemStatusCombo").getRawValue().trim();
				var modifyId=Ext.getCmp('configItem').modifyId;
				var dataId=Ext.getCmp('configItem').dataId;
				var oldId=Ext.getCmp('configItem').oldId;
				var saveCIButton=this;
				var saveCI=function(){
					Ext.Ajax.request({
						url : webContext+ '/configItemAction_modifyConfigItemValidate.action',
						params : {
								cid:dataId,
								cisn : cisn,
								modifyId:modifyId
						},
						success : function(response, options) {
							var result=Ext.decode(response.responseText);
							if(result.exist){
								Ext.MessageBox.alert("��ʾ","���Ѵ������������"+cisn+"�ı���ݸ壡",function(){
									saveCIButton.enable();
								});
								return;
							}else{
								var basicPanel=getFormParam("basicPanel");
								basicPanel['ConfigItem$status']=0;
								basicPanel=Ext.encode(basicPanel);
								var financePanel=Ext.encode(getFormParam("financePanel"));
								var extendPanel=Ext.encode(getFormParam("extendPanel"));
								var planPanel=Ext.encode(getFormParam("planPanel"));
								var saveOrUpdateConfigItemAndPlan=function(createAllNecessaryRel){
									Ext.Ajax.request({
										url:webContext+"/configItemAction_saveOrUpdateConfigItemAndPlan.action",
										params:{
											basicPanel:basicPanel,
											financePanel:financePanel,
											extendPanel:extendPanel,
											planPanel:planPanel,
											modifyId:modifyId,
											oldId:oldId,
											createAllNecessaryRel:createAllNecessaryRel
										},
										success : function(response, options) {
												saveCIButton.enable();
												var result=Ext.decode(response.responseText);
												Ext.Msg.alert("��ʾ","������������Ϣ�ɹ���",function(){
													window.parent.Ext.getCmp('modifyGrid').getStore().reload();
													window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
													var modifyTab=window.parent.Ext.getCmp('modifyTab');
													modifyTab.remove(modifyTab.getActiveTab());
												});
										},
										failure : function(response, options) {
											Ext.Msg.alert("��ʾ","������������Ϣʧ�ܣ�",function(){
												saveCIButton.enable();
											});
										}
									});
								}
								Ext.Ajax.request({
									url:webContext+"/configItemAction_findHasNotExistOptionalRel.action",
									params:{
										modifyId:modifyId,
										configItemStatus:configItemStatus,
										cid:dataId,
										cisn:cisn,
										configItemTypeId:configItemTypeId
									},
									success : function(response, options) {
										if(response.responseText!=""){
											var result=Ext.decode(response.responseText);
												new Ext.Window({
													id:"alertWin",
													title:"<font color=red>�Ƿ����ɿ�ѡ��ϵ��</font>",
													width:350,
													height:200,
													closable :false,
													maximizable:true,
													autoScroll :true,
													modal:true,
													buttonAlign:'center',
													html :result.message+"<p align='center'><font color=red>�Ƿ����ɿ�ѡ��ϵ��</font></p>",
													buttons:[{
														text:'��',
														handler:function(){
															Ext.getCmp('alertWin').close();
															saveOrUpdateConfigItemAndPlan(true);
														}
													},
													{
														text:'��',
														handler:function(){
															Ext.getCmp('alertWin').close();
															saveOrUpdateConfigItemAndPlan(false);
														}
													}
													]
												}).show();
										}else{
											saveOrUpdateConfigItemAndPlan(false);
										}
									},
									failure : function(response, options) {
										saveOrUpdateConfigItemAndPlan(false);
									}
								})
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ","ϵͳ�쳣!",function(){
								saveCIButton.enable();
							});
						}
					})
				}
				saveCIButton.disable();
				if(cisn!=""&&configItemStatus!=""){
					var conn = Ext.lib.Ajax.getConnectionObject().conn;
					var url=webContext+"/configItemAction_saveOrphanCIAlert.action?cisn="+cisn+"&modifyId="+modifyId+"&configItemStatus="+configItemStatus;
					conn.open("post", url, false);
					conn.send(null);
					if (conn.status == "200") {
						var responseText = conn.responseText;
						if(responseText!=''){
							var result=Ext.decode(responseText);
							if(result.error!=undefined){
								Ext.Msg.alert("��ʾ",result.error,function(){
									saveCIButton.enable();
								});
								return;
							}
							if(result.alert!=undefined){
								new Ext.Window({
									id:"alertWin",
									title:"<font color=red >������</font>",
									width:350,
									height:200,
									closable :false,
									maximizable:true,
									autoScroll :true,
									modal:true,
									buttonAlign:'center',
									html :"������״̬Ϊ\""+configItemStatusRaw+"\",����ɹ�֮�����¹�ϵ����ɾ����"+result.alert+"<font color=red>�Ƿ������</font>",
									buttons:[{
										text:'��',
										handler:function(){
											Ext.getCmp('alertWin').close();
											saveCI();
										}
									},
									{
										text:'��',
										handler:function(){
											saveCIButton.enable();
											Ext.getCmp('alertWin').close();
										}
									}
									]
								}).show();
							}
						}else{
							saveCI();
						}
					}else{
						Ext.Msg.alert("��ʾ",'ϵͳ�쳣��',function(){
							saveCIButton.enable();
						});
					}
				}else{
					saveCI();
				}
			}
		  }
		],
 getBasicPanel: function(configItemId,oldId) {
 	var getSpecification=function(configItemTypeId){
	  if(configItemTypeId.trim()!=""){
	 	var specification="";
	 	var typeName="";
	 	switch (configItemTypeId){
			case "73":specification="ƽ̨����+�ص�����";typeName="�칫�ص�";break;//�칫�ص�  
			case "74":specification="�ص�+¥��+�����߹��̡�+���";typeName="���߹���";break;//���߹���  
			case "75":specification="���+��-��+�յ�+����+��-��+����+��ר�ߡ�";typeName="ר��";break;//ר��
			case "76":specification="�ص�+¥��+��������";typeName="����";break;//����
			case "77":specification="���������+��-��+IP��ַ+��-��+������+��-��+λ��";typeName="������";break;//������
			case "78":specification="�ص�-¥��-�ͺ�-·�������";typeName="·����";break;//·����
			case "79":specification="�ص�-¥��-�ͺ�-���������";typeName="������";break;//������
			case "80":specification="Ʒ��+��-��+�ͺ�";typeName="�洢������";break;//�洢������
			case "81":specification="�ص�+¥��+��������+�յ�Ʒ��+��-��+�ͺ�";typeName="�յ�";break;//�յ�
			case "82":specification="�ص�+Ʒ��+UPS+��-��+���";typeName="����ϵ�Դ";break;//����ϵ�Դ
			case "83":specification="�ص�+�����+��-��+��ţ���λ���֣�";typeName="�����";break;//�����
			case "84":specification="�ص�+Ʒ��+�ͺ�+��-��+PBX";typeName="�̿ؽ�����";break;//�̿ؽ�����
			case "86":specification="���ݿ�����+��-��+�汾";typeName="���ݿ�";break;//���ݿ�
			case "87":specification="�м������+��-��+�汾";typeName="�м��";break;//�м��
			case "88":specification="Ӧ���������+��-��+�汾";typeName="Ӧ�����";break;//Ӧ�����
			case "89":specification="�������+��-��+�ͺ�+��-��+��������";typeName="�ӿڿ�_���";break;//�ӿڿ�_���
			case "98":specification="�ص�+¥��+��������+�豸����+��-��+��ţ���λ���֣�";typeName="�����豸";break;//�����豸
			case "100":specification="�칫�ص�+¥��+������+��-��+������";typeName="ͨ�û���";break;//ͨ�û���
			case "105":specification="�����Ŷ�����";typeName="�����Ŷ�";break;//�����Ŷ�
			case "106":specification="��Ѷ���������������";typeName="��Ѷ�����������";break;//��Ѷ�����������
			case "107":specification="�ص�+¥��+����������+��Ѷ�����ն�����";typeName="��Ѷ�����ն�";break;//��Ѷ�����ն�
			case "221":specification="Ʒ��+��-��+�ͺ�";typeName="����";break;//����
			case "222":specification="Ӧ������Ҫ����_IP��ַ_������";typeName="���������";break;//���������
			case "223":specification="�����������";typeName="�������";break;//�������
			case "224":specification="�칫�ص�+¥��+��-��+�豸���";typeName="��ȫ�����豸";break;//��ȫ�����豸
			case "228":specification="����ʦ��������+Itcode";typeName="���񹤳�ʦ";break;//���񹤳�ʦ
			case "266":specification="����ʦ��������+Itcode";typeName="Ӧ�ù���Ա";break;//Ӧ�ù���Ա
			case "267":specification="����������";typeName="������";break;//������
			case "271":specification="�Է�������д+ҵ��������д";typeName="B2B�ӿ�ʵ��";break;//B2B�ӿ�ʵ��
		}
		if(Ext.getCmp("specificationTip")!=undefined){
			Ext.getCmp("specificationTip").destroy();
		}
		if(specification!=""){
			new Ext.ToolTip({
				id:"specificationTip",
		 		target:"ConfigItem$name",
		 		html:specification,
		 		dismissDelay:0,
		 		trackMouse:true
		 	})
		}
	  }
 	}
 	var getExtendPanel=this.getExtendPanel;
 	var getPlanPanel=this.getPlanPanel;
	 var da = new DataAction();
	 if(configItemId!="")
	 	var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "configItemBasicPanel", configItemId);
	 else
		var data = da.getPanelElementsForAdd("configItemBasicPanel");
	var configItemTypeId="";	
	for(var i=0;i<data.length;i++){
		if(data[i].id=='ConfigItem$configItemTypeCombo'){
			if(data[i].value!=undefined){
				configItemTypeId=data[i].value;
			}
			data[i].store.baseParams={
				deployFlag:1
			}
		}
		if(data[i].id=="ConfigItem$configItemStatusCombo"){
			data[i].maxHeight=100;
		}
		if(data[i].id=="ConfigItem$name"){
			data[i].blankText="";
			data[i].invalidText="";
		}
		if(data[i].id=='ConfigItem$cisn'){
			data[i].readOnly=true;
			data[i].emptyText="����Ϊϵͳ�Զ�����";
		}
		if(data[i].id=='ConfigItem$configItemTypeCombo'&&configItemId!=""){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
		}
		if(this.dataId==''&&configItemId!=''&&data[i].id=='ConfigItem$id'){
			data[i].value="";
		}
		if(configItemId==''){
			if(data[i].id=='ConfigItem$customerTypeCombo'){
				data[i].value=1;//�ڲ��ͻ�
				data[i].initComponent();
			}
			if(data[i].id=='ConfigItem$customerCombo'){
				data[i].value=74;//��Ʒ�ۿ� 
				data[i].initComponent();
			}
			if(data[i].id=='ConfigItem$useDate'){
				data[i].value=new Date();
			}
			if(data[i].id=='ConfigItem$buyDate'){
				data[i].value=new Date();
			}
			if(data[i].id=='ConfigItem$preStopDate'){
				var currentDate=new Date();
				currentDate.setYear(currentDate.getYear()+5);
				data[i].value=currentDate;
			}
			if(data[i].id=='ConfigItem$environmentCombo'){
				data[i].value=29;//��������
				data[i].initComponent();
			}
			if(data[i].id=='ConfigItem$tenancyFlagCombo'){
				data[i].value=0;//��(�����豸)
			}
			if(data[i].id=='ConfigItem$configItemStatusCombo'){
				data[i].value=7;//ʹ����
				data[i].initComponent();
			}
			if(data[i].id=='ConfigItem$principalCombo'){
				data[i].value=window.parent.Ext.getCmp("CIBatchModify$applyUser").getValue();
				data[i].initComponent();
			}
		}
	 }
	 Ext.getCmp("ConfigItem$name").on("render",function(component){
		getSpecification(configItemTypeId);
	 })
	 var biddata = da.split(data);
	 var basicPanel= new Ext.form.FormPanel({
		id : 'basicPanel',
		layout : 'table',
		autoScroll : true,
		frame:true,
		title : "������Ϣ",
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		items : biddata
	 });
	Ext.getCmp("ConfigItem$configItemTypeCombo").addListener('select',function(combo,record,index){
		var configItemTypeId=record.get('id');
		getSpecification(configItemTypeId);
		var configItemTab=Ext.getCmp("configItem");
		if(Ext.getCmp("extendPanel")!=undefined)
			configItemTab.remove("extendPanel");
		var extendPanel =getExtendPanel(configItemId,configItemTypeId,oldId);
		configItemTab.insert(2,extendPanel);
		if(Ext.getCmp("planPanel")==undefined){
			configItemTab.add(getPlanPanel(""));
		}
		configItemTab.doLayout();
	});
	 return basicPanel;
	},
 getFinancePanelPanel: function(configItemId) {
      var da = new DataAction();
      if(configItemId!=""){
	 	 var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "financeEditPanel", configItemId);
      }else{
	  	 var data = da.getPanelElementsForAdd("financeEditPanel");
	  }
	  for(var i=0;i<data.length;i++){
	  	if(configItemId==''){
			if(data[i].id=='ConfigItemFinanceInfo$assetFlagCombo'){
				data[i].value=0;
			}
			if(data[i].id=='ConfigItemFinanceInfo$maFlagCombo'){
				data[i].value=0;
			}
	  	}
	  	if(this.dataId==''&&configItemId!=''&&data[i].id=='ConfigItemFinanceInfo$configItem'){
			data[i].value="";
		}
	  	if(this.dataId==''&&configItemId!=''&&data[i].id=='ConfigItemFinanceInfo$id'){
			data[i].value="";
		}
		if(data[i].id=="ConfigItemFinanceInfo$levelFlag"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel,
				decimalPrecision:0
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$maFee"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$buyFee"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$saleFee"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$depressPeriod"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel,
				decimalPrecision:0
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$depressedPeriod"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel,
				decimalPrecision:0
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$monthDepressFee"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$fee"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
		if(data[i].id=="ConfigItemFinanceInfo$feeRemain"){
			data[i]=new Ext.form.NumberField({
				id:data[i].id,
				name:data[i].name,
				value:data[i].value,
				height:data[i].height,
				width:data[i].width,
				allowBlank:data[i].allowBlank,
				fieldLabel:data[i].fieldLabel
			});
		}
	  }
	  var biddata = da.split(data);
		var financePanel= new Ext.form.FormPanel({
			id : 'financePanel',
			frame : true,
			layout : 'table',
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "������Ϣ",
			items : biddata
		});
		return financePanel;
	},
	getExtendPanel:function(configItemId,configItemTypeId,oldId){
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url=webContext+"/configItemAction_getPagePanelName.action?configItemTypeId="+configItemTypeId+"&configItemId="+configItemId;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var da = new DataAction();
			var result=Ext.decode(responseText);
			this.isOrphan=result.isOrphan;
			var displayServerPassword=result.displayServerPassword;
			var data;
			if(result.extendId!=undefined){
				data=da.getSingleFormPanelElementsForEdit(result.panelName,result.extendId);
			}else{
				data=da.getPanelElementsForAdd(result.panelName);
			}
			 for(var i=0;i<data.length;i++){
			  	if(this.dataId==''&&configItemId!=''&&data[i].id.split("$")[1]=='id'){
					data[i].value="";
				}
				if(data[i].id=="Server$iloRemoteManagePassword"&&oldId!=''&&!displayServerPassword){
					data[i].inputType="password"; 
					data[i].readOnly=true; 
				}
				if(data[i].id=="OfficeLocation$principalName"){
					data[i].emptyText="����д��ҵ������";
				}
				/*if(data[i].id=='SpecialLine$startLocationCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.zsgj.itil.config.extci.entity.OfficeLocation"+"&fuzzyQuery=locationName",
						fields:['id','locationName'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
				if(data[i].id=='SpecialLine$endLocationCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.zsgj.itil.config.extci.entity.OfficeLocation"+"&fuzzyQuery=locationName",
						fields:['id','locationName'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
				if(data[i].id=='DataCenter$officeLocationCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.zsgj.itil.config.extci.entity.OfficeLocation"+"&fuzzyQuery=locationName",
						fields:['id','locationName'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
				if(data[i].id=='ServiceEngineerOut$serviceProviderOutCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.zsgj.itil.actor.entity.ServiceProviderOut"+"&fuzzyQuery=name",
						fields:['id','name'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
				if(data[i].id=='ServiceEngineerIn$serviceProviderInCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.zsgj.itil.actor.entity.ServiceProviderIn"+"&fuzzyQuery=name",
						fields:['id','name'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}*/
/*				if(data[i].id=="Server$isPhysicalServerCombo"){
					data[i].value=1;
				}*/
				if(data[i].id=="ServiceProvider$isInCombo"){
					data[i].value=0;
				}
				if(data[i].id=="DataCenter$floor"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="DataCenter$roomSize"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel, 
						decimalPrecision:0
					});
				}
				if(data[i].id=="Application$supportUserMax"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Application$supportUserStd"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Gateway$powerConsumption"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Generator$power"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="LanProject$phonePortNumber"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="LanProject$dataPortNumber"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="LanProject$lanAPNumber"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Server$powerNum"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Server$cpuClockSpeed"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel
					});
				}
				if(data[i].id=="Server$cpuCores"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Server$diskSize"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel
					});
				}
				if(data[i].id=="Server$diskNumber"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Server$isPhysicalServerCombo"){
					data[i].emptyText='�����ѡ"��",���������ѡ"��"!';
				}
				if(data[i].id=="Server$brand"){
					data[i].emptyText='�������"��"!';
				}
				if(data[i].id=="Server$model"){
					data[i].emptyText='�������"��"!';
				}
				if(data[i].id=="Server$powerConsumption"){
					data[i].emptyText='�������"��"!';
				}
				if(data[i].id=="Server$sn"){
					data[i].emptyText='�������"��"!';
				}
				if(data[i].id=="Server$cpuType"){
					data[i].emptyText='�������"��"!';
				}
				if(data[i].id=="Server$diskRaidTypeCombo"){
					data[i].emptyText='�����ѡ"��"!';
				}
				if(data[i].id=="Server$iloRemoteManageIP"){
					data[i].emptyText='û����"��"!';
				}
				if(data[i].id=="Server$iloRemoteManagePassword"){
					data[i].emptyText='û����"��"!';
				}
				if(data[i].id=="Switch$portNumber"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="UPS$batteryQuantity"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="VirtualMachine$licenseQuantity"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="VirtualMachine$ramCapacity"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="StorageServer$standardHigh"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="TapeServer$standardHigh"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Server$standardHigh"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Switch$standardHigh"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
				if(data[i].id=="Router$standardHigh"){
					data[i]=new Ext.form.NumberField({
						id:data[i].id,
						name:data[i].name,
						value:data[i].value,
						height:data[i].height,
						width:data[i].width,
						allowBlank:data[i].allowBlank,
						fieldLabel:data[i].fieldLabel,
						decimalPrecision:0
					});
				}
			 }
			data = da.split(data);
			 var extendPanel= new Ext.form.FormPanel({
				id : 'extendPanel',
				layout : 'table',
				frame : true,
				autoScroll : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : result.panelTitle,
				items:data
	 		});
			return extendPanel;
		} else {
			Ext.MessageBox.alert("��ʾ","��ȡ��չ��Ϣʧ��");
		}
	},
	getPlanPanel:function(pid){
		var da = new DataAction();
		if(pid!=''){
	 		var data = da.getSingleFormPanelElementsForEdit("pagepanel_CIBatchModifyPlan_form",pid);
		}else{
			var data = da.getPanelElementsForAdd("pagepanel_CIBatchModifyPlan_form");
		}
		for(var i=0;i<data.length;i++){
			if(data[i].id=='CIBatchModifyPlan$startDate'){
				data[i]=new Ext.form.DateField({
					id:data[i].id,
					name:data[i].name,
					hideTrigger:true,
					format:"Y-m-d H:i:s",
					width:data[i].width,
					allowBlank:data[i].allowBlank,
					fieldLabel:data[i].fieldLabel,
					value:data[i].value
				})
			}
			if(data[i].id=='CIBatchModifyPlan$endDate'){
				data[i]=new Ext.form.DateField({
					id:data[i].id,
					name:data[i].name,
					hideTrigger:true,
					format:"Y-m-d H:i:s",
					width:data[i].width,
					allowBlank:data[i].allowBlank,
					fieldLabel:data[i].fieldLabel,
					value:data[i].value
				})
			}
			if(pid==''){
				if(data[i].id=='CIBatchModifyPlan$officerCombo'){
					data[i].value=window.parent.Ext.getCmp("CIBatchModify$applyUser").getValue();
					data[i].initComponent();
				}
				if(data[i].id=='CIBatchModifyPlan$startDate'){
					var currentDate=new Date();
					currentDate.setDate(currentDate.getDate()+1);
					currentDate.setHours(20);
					currentDate.setMinutes(0);
					currentDate.setSeconds(0);
					data[i].value=currentDate;
				}
				if(data[i].id=='CIBatchModifyPlan$endDate'){
					var currentDate=new Date();
					currentDate.setDate(currentDate.getDate()+1);
					currentDate.setHours(20);
					currentDate.setMinutes(0);
					currentDate.setSeconds(0);					
					data[i].value=currentDate;
				}
			}
	 	}
	  	var biddata = da.split(data);
		var planPanel= new Ext.form.FormPanel({
			id : 'planPanel',
			layout : 'table',
			autoScroll : true,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "����ƻ�",
			items : biddata
		});
		return planPanel;
	},
    items : this.items,
	initComponent : function() {
		var modifyId=this.modifyId;
		var oldId=this.oldId;
		var dataId=this.dataId;
		var pid=this.pid;
		var configItemId=dataId;
		if(oldId!=''&&dataId==''){
			configItemId=oldId;
		}
	    var temp = new Array();
        temp.push(this.getBasicPanel(configItemId,oldId));
        temp.push(this.getFinancePanelPanel(configItemId));
        if(configItemId!=""){
        	var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
        	temp.push(this.getExtendPanel(configItemId,configItemTypeId,oldId));
        	temp.push(this.getPlanPanel(pid));
        }
		this.items = temp;
		ConfigItemInfo.superclass.initComponent.call(this);
		var cisn=Ext.getCmp("ConfigItem$cisn").getValue();
		if(oldId!=''){
			if(this.isOrphan){
					var conn = Ext.lib.Ajax.getConnectionObject().conn;
					var url=webContext+"/configItemAction_findCIRel.action?oldId="+oldId;
					conn.open("post", url, false);
					conn.send(null);
					if (conn.status == "200") {
						var result = Ext.decode(conn.responseText);
						if(result.rels!=""){
							Ext.getCmp('configItem').addButton({
								text:'�鿴�������ϵ',
								iconCls:'look'					
							},function(){
			
									new Ext.Window({
										id:"relWin",
										title:"�������ϵ",
										width:350,
										height:200,
										closable :false,
										maximizable:true,
										autoScroll :true,
										modal:true,
										buttonAlign:'center',
										html :"�����������ɹ�֮��,���¹�ϵ����ɾ����"+result.rels,
										buttons:[
										{
											text:'�ر�',
											handler:function(){
												Ext.getCmp('relWin').close();
											}
										}
										]
									}).show();
							});
						}
					}
			}
			Ext.getCmp('configItem').addButton({
				text:'�鿴ԭ������',
				iconCls:'look'					
			},function(){
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoForLook.jsp';
					var win=new Ext.Window({
						title:'ԭ��������Ϣ',
						width:700,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:350,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?dataId="+ oldId,
							text : "ҳ�����ڼ�����......",
							method : 'post',
							scope : this
						},
						buttonAlign:"center",
						buttons:[{
							text:'�ر�',
							handler:function(){
								win.close();
							}
						}
						]
					});
					win.show();
			});
		}
		if(dataId!=""&&pid!=''&&!this.isOrphan&&cisn.trim().length!=0&&configItemTypeId.trim().length!=0){
				Ext.getCmp('configItem').addButton({
						text:'�鿴��Ҫ��ϵ',
						iconCls:'look'					
					},function(){
							var store = new Ext.data.JsonStore({ 				
								url: webContext+'/configItemAction_findCINecessaryRel.action?itemCode='+cisn+"&TypeId="+configItemTypeId+"&modifyId="+modifyId,
								fields: ['configItemType','otherConfigItemType','parentOrChildType',"isOptional","isExist"],
							    root:'data'
							}); 
							var cm = new Ext.grid.ColumnModel([
								    {header: "����������",  sortable: true, dataIndex: 'configItemType'}, 
								    {header: "��������������", sortable: true, dataIndex: 'otherConfigItemType'},
								    {header: "��ϵ����", sortable: true, dataIndex: 'parentOrChildType'},
								     {header: "�Ƿ��ѡ", sortable: true, dataIndex: 'isOptional'},
								    {header: "�Ƿ��Ѵ���",  sortable: true, dataIndex: 'isExist'}	
							]); 
							var grid = new Ext.grid.GridPanel({
									title:'���б�Ҫ��ϵ',
							        store: store,
							        cm: cm,
							        width:530,
									height:190,
							        autoScroll : true,
							        frame:true,
							        loadMask: true
							}); 			
							var maintenanceStore = new Ext.data.JsonStore({ 				
								url: webContext+'/configItemAction_findMaintenanceRel.action?configItemId='+dataId+"&modifyId="+modifyId,
								fields: ['parentType','parentCode','childType',"childCode"],
							    root:'data'
							}); 
							var maintenanceCm = new Ext.grid.ColumnModel([
								    {header: "������",  sortable: true, dataIndex: 'parentType'}, 
								    {header: "�����", sortable: true, dataIndex: 'parentCode'},
								    {header: "������", sortable: true, dataIndex: 'childType'},
								    {header: "�ӱ��",  sortable: true, dataIndex: 'childCode'}	
							]); 
							var maintenanceGrid = new Ext.grid.GridPanel({
									title:'�������������ɵı�Ҫ��ϵ',
							        store: maintenanceStore,
							        cm: maintenanceCm,
							        width:530,
									height:190,
							        autoScroll : true,
							        frame:true,
							        loadMask: true
							}); 			
							var necessaryRelWin=new Ext.Window({
								title:"��Ҫ��ϵ�鿴",
								width:561,
								height:380,
								maximizable:true,
								autoScroll :true,
								modal:true,
								buttonAlign:'center',
								items:[grid,maintenanceGrid],
								buttons:[{
									text:'���ɱ�Ҫ��ϵ',
									id:'maintenance',
									disabled:true,
									handler:function(){
										var createNecessaryRel=function(createAll){
											Ext.Ajax.request({
												url:webContext+"/configItemAction_createNecessaryRel.action",
												params:{
													modifyId:modifyId,
													dataId:dataId,
													pid:pid,
													createAllNecessaryRel:createAll
												},
												success:function(response){
													Ext.Msg.alert("��ʾ","���ɳɹ�,���ɹ�ϵ�ѽ��������õ���",function(){
														necessaryRelWin.close();
														window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
													});
												},
												failure:function(response){
													Ext.Msg.alert("��ʾ","ϵͳ�쳣��");
												}
											})
										}
										Ext.Ajax.request({
											url:webContext+"/configItemAction_findHasNotExistOptionalRel.action",
											params:{
												modifyId:modifyId,
												cid:dataId,
												cisn:cisn,
												configItemTypeId:configItemTypeId
											},
											success : function(response, options) {
												if(response.responseText!=""){
													var result=Ext.decode(response.responseText);
														new Ext.Window({
															id:"alertWin",
															title:"<font color=red>�Ƿ����ɿ�ѡ��ϵ��</font>",
															width:350,
															height:200,
															closable :false,
															maximizable:true,
															autoScroll :true,
															modal:true,
															buttonAlign:'center',
															html :result.message+"<p align='center'><font color=red>�Ƿ����ɿ�ѡ��ϵ��</font></p>",
															buttons:[{
																text:'��',
																handler:function(){
																	Ext.getCmp('alertWin').close();
																	createNecessaryRel(true);
																}
															},
															{
																text:'��',
																handler:function(){
																	Ext.getCmp('alertWin').close();
																	createNecessaryRel(false);
																}
															}
															]
														}).show();
												}else{
													createNecessaryRel(false);
												}
											},
											failure : function(response, options) {
												createNecessaryRel(false);
											}
										})
									}
								},
								{
									text:'�ر�',
									handler:function(){
										necessaryRelWin.close();
									}
								}
								]
							});
							necessaryRelWin.show();
							store.load({
								callback:function(r){
									for(var i=0;i<r.length;i++){
										if(r[i].get("isExist")=='������'){
											Ext.getCmp("maintenance").enable();
											break;
										}
									}
								}
							});
							maintenanceStore.reload();
					});
			}
	}
})