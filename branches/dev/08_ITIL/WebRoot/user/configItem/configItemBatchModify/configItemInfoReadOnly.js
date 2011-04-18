ConfigItemInfo = Ext.extend(Ext.TabPanel, {
	id : "configItem",
	frame : true,
	activeTab : 0,
	autoScroll : true,
	layoutOnTabChange:true,
	deferredRender : false,
	forceLayout:true,
	buttonAlign:'center',
	
 getBasicPanel: function(configItemId) {
 	var getSpecification=function(configItemTypeId){
	  if(configItemTypeId.trim()!=""){
	 	var specification="";
	 	var typeName="";
	 	switch (configItemTypeId){
			case "73":specification="平台名称+地点名称";typeName="办公地点";break;//办公地点  
			case "74":specification="地点+楼层+“布线工程”+年份";typeName="布线工程";break;//布线工程  
			case "75":specification="起点+“-”+终点+带宽+“-”+类型+“专线”";typeName="专线";break;//专线
			case "76":specification="地点+楼层+机房名称";typeName="机房";break;//机房
			case "77":specification="计算机名称+“-”+IP地址+“-”+机柜编号+“-”+位置";typeName="物理服务器";break;//物理服务器
			case "78":specification="地点-型号-楼层-路由器编号";typeName="路由器";break;//路由器
			case "79":specification="地点-型号-楼层-交换机编号";typeName="交换机";break;//交换机
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
			case "105":specification="服务商名称";typeName="内部服务商";break;//内部服务商
			case "106":specification="视讯会议多点控制器名称";typeName="视讯会议多点控制器";break;//视讯会议多点控制器
			case "107":specification="地点+楼层+会议室名称+视讯会议终端名称";typeName="视讯会议终端";break;//视讯会议终端
			case "218":specification="服务商名称";typeName="外部服务商";break;//外部服务商
			case "220":specification="工程师中文名称+Itcode";typeName="外部服务工程师";break;//外部服务工程师
			case "221":specification="品牌+“-”+型号";typeName="带库";break;//带库
			case "222":specification="应用名简要描述_IP地址_申请人";typeName="虚拟服务器";break;//虚拟服务器
			case "223":specification="虚拟机组名称";typeName="虚拟机组";break;//虚拟机组
			case "224":specification="办公地点+楼层+“-”+设备编号";typeName="安全防护设备";break;//安全防护设备
			case "228":specification="工程师中文名称+Itcode";typeName="内部服务工程师";break;//内部服务工程师
			case "266":specification="工程师中文名称+Itcode";typeName="应用管理员";break;//应用管理员
		}
		if(Ext.getCmp("specificationTip")!=undefined){
			Ext.getCmp("specificationTip").destroy();
		}
		new Ext.ToolTip({
			id:"specificationTip",
	 		target:"ConfigItem$name",
	 		html:specification,
	 		dismissDelay:0,
	 		trackMouse:true
	 	})
	  }
 	} 	
 	var getExtendPanel=this.getExtendPanel;
	var da = new DataAction();
	var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "configItemBasicPanel", configItemId);
	var configItemTypeId="";
	for(var i=0;i<data.length;i++){
		data[i].readOnly=true;
		data[i].hideTrigger=true;
		data[i].emptyText="";
		data[i].allowBlank=true;
		if(data[i].id=='ConfigItem$configItemTypeCombo'){
			if(data[i].value!=undefined){
				configItemTypeId=data[i].value;
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
		frame : true,
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
	  var data = da.getPanelElementsForEdit("configItemFinanceNewModifyMix", "financeEditPanel", configItemId);
	  for(var i=0;i<data.length;i++){
		 data[i].readOnly=true;
		 data[i].hideTrigger=true;
		 data[i].emptyText="";
		 data[i].allowBlank=true;
	  }
	  var biddata = da.split(data);
	  var financePanel= new Ext.form.FormPanel({
			id : 'financePanel',
			layout : 'table',
			frame : true,
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
			var data=da.getSingleFormPanelElementsForEdit(result.panelName,result.extendId);
			for(var i=0;i<data.length;i++){
				 data[i].readOnly=true;
				 data[i].hideTrigger=true;
				 data[i].emptyText="";
				 data[i].allowBlank=true;
				 if(data[i].id=="Server$iloRemoteManagePassword"&&!displayServerPassword){
					data[i].inputType="password"; 
					data[i].readOnly=true; 
				}
				if(data[i].id=='SpecialLine$startLocationCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.digitalchina.itil.config.extci.entity.OfficeLocation",
						fields:['id','locationName'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
				if(data[i].id=='SpecialLine$endLocationCombo'){
					data[i].store=new Ext.data.JsonStore({
						url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.digitalchina.itil.config.extci.entity.OfficeLocation",
						fields:['id','locationName'],
						totalProperty:'rowCount',
						root:'data'});
					data[i].initComponent();
				}
			}
			data = da.split(data);
			 var extendPanel= new Ext.form.FormPanel({
				id : 'extendPanel',
				frame : true,
				layout : 'table',
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
	 	var data = da.getSingleFormPanelElementsForEdit("pagepanel_CIBatchModifyPlan_form",pid);
	    for(var i=0;i<data.length;i++){
			 data[i].readOnly=true;
			 data[i].hideTrigger=true;
			 data[i].emptyText="";
			if(data[i].id=='CIBatchModifyPlan$startDate'){
				data[i]=new Ext.form.DateField({
					id:data[i].id,
					name:data[i].name,
					hideTrigger:true,
					readOnly:true,
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
					readOnly:true,
					format:"Y-m-d H:i:s",
					width:data[i].width,
					allowBlank:data[i].allowBlank,
					fieldLabel:data[i].fieldLabel,
					value:data[i].value
				})
			}
		}
	  	var biddata = da.split(data);
		var planPanel= new Ext.form.FormPanel({
			id : 'planPanel',
			layout : 'table',
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
		var oldId=this.oldId;
		var title=this.title;
	    var temp = new Array();
        temp.push(this.getBasicPanel(dataId));
        temp.push(this.getFinancePanelPanel(dataId));
    	var configItemTypeId=Ext.getCmp("ConfigItem$configItemTypeCombo").getValue();
    	temp.push(this.getExtendPanel(dataId,configItemTypeId));
    	temp.push(this.getPlanPanel(pid));
		this.items = temp;
		ConfigItemInfo.superclass.initComponent.call(this);
		var cisn=Ext.getCmp("ConfigItem$cisn").getValue();
		if(oldId!=''){
			if(this.isOrphan){
					var cisn=Ext.getCmp("ConfigItem$cisn").getValue();
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
										height:300,
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
		if(dataId!=""&&!this.isOrphan&&cisn.trim().length!=0&&configItemTypeId.trim().length!=0){
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
								buttons:[
								{
									text:'关闭',
									handler:function(){
										necessaryRelWin.close();
									}
								}
								]
							});
							necessaryRelWin.show();
							store.reload();
							maintenanceStore.reload();
					});
			}		
	}
})