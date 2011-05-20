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
			text:'保存配置项信息',
			iconCls:'save',
			handler:function(){
				if(Ext.getCmp("ConfigItem$configItemTypeCombo").getValue()==""){
						Ext.Msg.alert("提示","请选择配置项类型！",function(){
						});
						return;
				}
				if(!Ext.getCmp('basicPanel').form.isValid()||
				   !Ext.getCmp('financePanel').form.isValid()||
				   !Ext.getCmp('extendPanel').form.isValid()||
				   !Ext.getCmp('planPanel').form.isValid()){
					Ext.MessageBox.alert("提示",'您填写的信息不完整或不正确.');
					return;
				}
				if(Ext.getCmp('CIBatchModifyPlan$startDate').getValue()>Ext.getCmp('CIBatchModifyPlan$endDate').getValue()){
					Ext.MessageBox.alert("提示",'变更计划的开始时间不能大于结束时间.');
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
								Ext.MessageBox.alert("提示","您已存在针对配置项"+cisn+"的变更草稿！",function(){
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
												Ext.Msg.alert("提示","保存配置项信息成功！",function(){
													window.parent.Ext.getCmp('modifyGrid').getStore().reload();
													window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
													var modifyTab=window.parent.Ext.getCmp('modifyTab');
													modifyTab.remove(modifyTab.getActiveTab());
												});
										},
										failure : function(response, options) {
											Ext.Msg.alert("提示","保存配置项信息失败！",function(){
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
													title:"<font color=red>是否生成可选关系？</font>",
													width:350,
													height:200,
													closable :false,
													maximizable:true,
													autoScroll :true,
													modal:true,
													buttonAlign:'center',
													html :result.message+"<p align='center'><font color=red>是否生成可选关系？</font></p>",
													buttons:[{
														text:'是',
														handler:function(){
															Ext.getCmp('alertWin').close();
															saveOrUpdateConfigItemAndPlan(true);
														}
													},
													{
														text:'否',
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
							Ext.MessageBox.alert("提示","系统异常!",function(){
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
								Ext.Msg.alert("提示",result.error,function(){
									saveCIButton.enable();
								});
								return;
							}
							if(result.alert!=undefined){
								new Ext.Window({
									id:"alertWin",
									title:"<font color=red >继续？</font>",
									width:350,
									height:200,
									closable :false,
									maximizable:true,
									autoScroll :true,
									modal:true,
									buttonAlign:'center',
									html :"配置项状态为\""+configItemStatusRaw+"\",变更成功之后以下关系将被删除："+result.alert+"<font color=red>是否继续？</font>",
									buttons:[{
										text:'是',
										handler:function(){
											Ext.getCmp('alertWin').close();
											saveCI();
										}
									},
									{
										text:'否',
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
						Ext.Msg.alert("提示",'系统异常！',function(){
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
			case "73":specification="平台名称+地点名称";typeName="办公地点";break;//办公地点  
			case "74":specification="地点+楼层+“布线工程”+年份";typeName="布线工程";break;//布线工程  
			case "75":specification="起点+“-”+终点+带宽+“-”+类型+“专线”";typeName="专线";break;//专线
			case "76":specification="地点+楼层+机房名称";typeName="机房";break;//机房
			case "77":specification="计算机名称+“-”+IP地址+“-”+机柜编号+“-”+位置";typeName="服务器";break;//服务器
			case "78":specification="地点-楼层-型号-路由器编号";typeName="路由器";break;//路由器
			case "79":specification="地点-楼层-型号-交换机编号";typeName="交换机";break;//交换机
			case "80":specification="品牌+“-”+型号";typeName="存储服务器";break;//存储服务器
			case "81":specification="地点+楼层+机房名称+空调品牌+“-”+型号";typeName="空调";break;//空调
			case "82":specification="地点+品牌+UPS+“-”+编号";typeName="不间断电源";break;//不间断电源
			case "83":specification="地点+发电机+“-”+编号（两位数字）";typeName="发电机";break;//发电机
			case "84":specification="地点+品牌+型号+“-”+PBX";typeName="程控交换机";break;//程控交换机
			case "86":specification="数据库名称+“-”+版本";typeName="数据库";break;//数据库
			case "87":specification="中间件名称+“-”+版本";typeName="中间件";break;//中间件
			case "88":specification="应用软件名称+“-”+版本";typeName="应用软件";break;//应用软件
			case "89":specification="配件名称+“-”+型号+“-”+属性描述";typeName="接口卡_配件";break;//接口卡_配件
			case "98":specification="地点+楼层+机房名称+设备名称+“-”+编号（两位数字）";typeName="消防设备";break;//消防设备
			case "100":specification="办公地点+楼层+“机柜”+“-”+机柜编号";typeName="通用机柜";break;//通用机柜
			case "105":specification="交付团队名称";typeName="交付团队";break;//交付团队
			case "106":specification="视讯会议多点控制器名称";typeName="视讯会议多点控制器";break;//视讯会议多点控制器
			case "107":specification="地点+楼层+会议室名称+视讯会议终端名称";typeName="视讯会议终端";break;//视讯会议终端
			case "221":specification="品牌+“-”+型号";typeName="带库";break;//带库
			case "222":specification="应用名简要描述_IP地址_申请人";typeName="虚拟服务器";break;//虚拟服务器
			case "223":specification="虚拟机组名称";typeName="虚拟机组";break;//虚拟机组
			case "224":specification="办公地点+楼层+“-”+设备编号";typeName="安全防护设备";break;//安全防护设备
			case "228":specification="工程师中文名称+Itcode";typeName="服务工程师";break;//服务工程师
			case "266":specification="工程师中文名称+Itcode";typeName="应用管理员";break;//应用管理员
			case "267":specification="服务商名称";typeName="服务商";break;//服务商
			case "271":specification="对方名称缩写+业务描述缩写";typeName="B2B接口实例";break;//B2B接口实例
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
			data[i].emptyText="此项为系统自动生成";
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
				data[i].value=1;//内部客户
				data[i].initComponent();
			}
			if(data[i].id=='ConfigItem$customerCombo'){
				data[i].value=74;//上品折扣 
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
				data[i].value=29;//生产环境
				data[i].initComponent();
			}
			if(data[i].id=='ConfigItem$tenancyFlagCombo'){
				data[i].value=0;//否(租赁设备)
			}
			if(data[i].id=='ConfigItem$configItemStatusCombo'){
				data[i].value=7;//使用中
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
		title : "基础信息",
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
			title : "财务信息",
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
					data[i].emptyText="请填写物业负责人";
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
					data[i].emptyText='虚拟机选"否",物理服务器选"是"!';
				}
				if(data[i].id=="Server$brand"){
					data[i].emptyText='虚拟机填"无"!';
				}
				if(data[i].id=="Server$model"){
					data[i].emptyText='虚拟机填"无"!';
				}
				if(data[i].id=="Server$powerConsumption"){
					data[i].emptyText='虚拟机填"无"!';
				}
				if(data[i].id=="Server$sn"){
					data[i].emptyText='虚拟机填"无"!';
				}
				if(data[i].id=="Server$cpuType"){
					data[i].emptyText='虚拟机填"无"!';
				}
				if(data[i].id=="Server$diskRaidTypeCombo"){
					data[i].emptyText='虚拟机选"无"!';
				}
				if(data[i].id=="Server$iloRemoteManageIP"){
					data[i].emptyText='没有填"无"!';
				}
				if(data[i].id=="Server$iloRemoteManagePassword"){
					data[i].emptyText='没有填"无"!';
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
			Ext.MessageBox.alert("提示","获取扩展信息失败");
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
			title : "变更计划",
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
								text:'查看配置项关系',
								iconCls:'look'					
							},function(){
			
									new Ext.Window({
										id:"relWin",
										title:"配置项关系",
										width:350,
										height:200,
										closable :false,
										maximizable:true,
										autoScroll :true,
										modal:true,
										buttonAlign:'center',
										html :"此配置项变更成功之后,以下关系将被删除："+result.rels,
										buttons:[
										{
											text:'关闭',
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
				text:'查看原配置项',
				iconCls:'look'					
			},function(){
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoForLook.jsp';
					var win=new Ext.Window({
						title:'原配置项信息',
						width:700,
						frame:true,
						maximizable : true,
						autoScroll : true,
						height:350,
						modal : true,
						autoLoad : {
							url : webContext + "/tabFrame.jsp?url="+url+"?dataId="+ oldId,
							text : "页面正在加载中......",
							method : 'post',
							scope : this
						},
						buttonAlign:"center",
						buttons:[{
							text:'关闭',
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
						text:'查看必要关系',
						iconCls:'look'					
					},function(){
							var store = new Ext.data.JsonStore({ 				
								url: webContext+'/configItemAction_findCINecessaryRel.action?itemCode='+cisn+"&TypeId="+configItemTypeId+"&modifyId="+modifyId,
								fields: ['configItemType','otherConfigItemType','parentOrChildType',"isOptional","isExist"],
							    root:'data'
							}); 
							var cm = new Ext.grid.ColumnModel([
								    {header: "配置项类型",  sortable: true, dataIndex: 'configItemType'}, 
								    {header: "关联配置项类型", sortable: true, dataIndex: 'otherConfigItemType'},
								    {header: "关系种类", sortable: true, dataIndex: 'parentOrChildType'},
								     {header: "是否可选", sortable: true, dataIndex: 'isOptional'},
								    {header: "是否已存在",  sortable: true, dataIndex: 'isExist'}	
							]); 
							var grid = new Ext.grid.GridPanel({
									title:'所有必要关系',
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
								    {header: "父类型",  sortable: true, dataIndex: 'parentType'}, 
								    {header: "父编号", sortable: true, dataIndex: 'parentCode'},
								    {header: "子类型", sortable: true, dataIndex: 'childType'},
								    {header: "子编号",  sortable: true, dataIndex: 'childCode'}	
							]); 
							var maintenanceGrid = new Ext.grid.GridPanel({
									title:'此配置项变更生成的必要关系',
							        store: maintenanceStore,
							        cm: maintenanceCm,
							        width:530,
									height:190,
							        autoScroll : true,
							        frame:true,
							        loadMask: true
							}); 			
							var necessaryRelWin=new Ext.Window({
								title:"必要关系查看",
								width:561,
								height:380,
								maximizable:true,
								autoScroll :true,
								modal:true,
								buttonAlign:'center',
								items:[grid,maintenanceGrid],
								buttons:[{
									text:'生成必要关系',
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
													Ext.Msg.alert("提示","生成成功,生成关系已进入变更配置单！",function(){
														necessaryRelWin.close();
														window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
													});
												},
												failure:function(response){
													Ext.Msg.alert("提示","系统异常！");
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
															title:"<font color=red>是否生成可选关系？</font>",
															width:350,
															height:200,
															closable :false,
															maximizable:true,
															autoScroll :true,
															modal:true,
															buttonAlign:'center',
															html :result.message+"<p align='center'><font color=red>是否生成可选关系？</font></p>",
															buttons:[{
																text:'是',
																handler:function(){
																	Ext.getCmp('alertWin').close();
																	createNecessaryRel(true);
																}
															},
															{
																text:'否',
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
									text:'关闭',
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
										if(r[i].get("isExist")=='不存在'){
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