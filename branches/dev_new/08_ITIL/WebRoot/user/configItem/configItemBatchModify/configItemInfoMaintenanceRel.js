ConfigItemInfo = Ext.extend(Ext.TabPanel, {
	id : "configItem",
	frame : true,
	activeTab : 0,
	autoScroll : true,
	layoutOnTabChange:true,
	deferredRender : false,
	forceLayout:true,
	buttonAlign:'center',
	buttons:[
		  	{
			text:'保存变更计划并生成必要关系',
			iconCls:'save',
			handler:function(){
				var modifyId=Ext.getCmp('configItem').modifyId;
				var dataId=Ext.getCmp('configItem').dataId;
				var createAllNecessaryRel=Ext.getCmp('configItem').createAllNecessaryRel;
				var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
				var itemCode=Ext.getCmp('ConfigItem$cisn').getValue();
				if(!Ext.getCmp('planPanel').form.isValid()){
					Ext.MessageBox.alert("提示",'您填写的信息不完整或不正确.');
					return;
				}
				if(Ext.getCmp('CIBatchModifyPlan$startDate').getValue()>Ext.getCmp('CIBatchModifyPlan$endDate').getValue()){
					Ext.MessageBox.alert("提示",'变更计划的开始时间不能大于结束时间.');
					return;
				}
				var savePlanButton=this;
				savePlanButton.disable();
				var planPanel=Ext.encode(getFormParam("planPanel"));
				var savePlanAndCreateNecessaryRel=function(createAll){
					Ext.Ajax.request({
						url:webContext+"/configItemAction_savePlanAndCreateNecessaryRel.action",
						params:{
							planPanel:planPanel,
							configItemId:dataId,
							modifyId:modifyId,
							createAllNecessaryRel:createAll
						},
						success : function(response, options) {
							Ext.Msg.alert("提示","保存成功,生成关系已进入变更配置单！",function(){
								savePlanButton.enable();
								window.parent.Ext.getCmp('modifyGrid').getStore().reload();
								window.parent.Ext.getCmp('modifyRelGrid').getStore().reload();
								var modifyTab=window.parent.Ext.getCmp('modifyTab');
								modifyTab.remove(modifyTab.getActiveTab());							
							});
						},
						failure : function(response, options) {
							Ext.Msg.alert("提示","保存失败！",function(){
								savePlanButton.enable();
							});
						}
					});
				}
				if(createAllNecessaryRel==""){
					Ext.Ajax.request({
						url:webContext+"/configItemAction_findHasNotExistOptionalRel.action",
						params:{
							modifyId:modifyId,
							cid:dataId,
							cisn:itemCode,
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
												savePlanAndCreateNecessaryRel(true);
											}
										},
										{
											text:'否',
											handler:function(){
												Ext.getCmp('alertWin').close();
												savePlanAndCreateNecessaryRel(false);
											}
										}
										]
									}).show();
							}else{
								savePlanAndCreateNecessaryRel(false);
							}
						},
						failure : function(response, options) {
							savePlanAndCreateNecessaryRel(false);
						}
					})
				}else{
					savePlanAndCreateNecessaryRel(createAllNecessaryRel)
				}
			}
		  }
		],
 getBasicPanel: function(configItemId) {
 	var getExtendPanel=this.getExtendPanel;
 	var getPlanPanel=this.getPlanPanel;
	 var da = new DataAction();
	 if(configItemId!="")
	 	var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "configItemBasicPanel", configItemId);
	 else
		var data = da.getPanelElementsForAdd("configItemBasicPanel");
		
		for(var i=0;i<data.length;i++){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			data[i].allowBlank=true;
		 }
	 var biddata = da.split(data);
	 var basicPanel= new Ext.form.FormPanel({
		id : 'basicPanel',
		layout : 'table',
		frame : true,
		autoScroll : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		title : "基础信息",
		items : biddata
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
	  		data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			data[i].allowBlank=true;
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
	getExtendPanel:function(configItemId,configItemTypeId){
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
			  	data[i].readOnly=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
				data[i].allowBlank=true;
				 if(data[i].id=="Server$iloRemoteManagePassword"&&!displayServerPassword){
					data[i].inputType="password"; 
					data[i].readOnly=true; 
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
		var dataId=this.dataId;
		var pid=this.pid;
		var createAllNecessaryRel=this.createAllNecessaryRel;
	    var temp = new Array();
        temp.push(this.getBasicPanel(dataId));
        temp.push(this.getFinancePanelPanel(dataId));
        if(dataId!=""){
        	var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
        	temp.push(this.getExtendPanel(dataId,configItemTypeId));
        	temp.push(this.getPlanPanel(pid));
        }
		this.items = temp;
		ConfigItemInfo.superclass.initComponent.call(this);
		var cisn=Ext.getCmp("ConfigItem$cisn").getValue();
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