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
	
	//保存并提交方法
	saveAndSubmit : function() {
		if(!Ext.getCmp('panel_SpecialRequireDevConfirm_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		//remove  by awen  on 2011-06-05 begin
//		if(Ext.getCmp('SpecialRequirement$deliveryTeamCombo').getValue()==""){
//			Ext.MessageBox.alert("提示","您必须选择交付团队！");	
//			return false;
//		}
		//remove  by awen  on 2011-06-05 end
		var formParam = Ext.encode(getFormParam('panel_SpecialRequireDevConfirm_Input'));
		var taskId = this.taskId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveSpecialRequire.action',
			params : {info : formParam},
			success : function(response, options) {
				var tempOfficerId = Ext.util.JSON.decode(response.responseText).serviceProviderInOfficer;//交付线负责人
				var tempEngineerId = Ext.util.JSON.decode(response.responseText).mainEngineer;//交付经理
				var tempServiceManageId = Ext.util.JSON.decode(response.responseText).serviceManager;//服务总监
				var tempUrl = "";
				if (tempEngineerId != "0") {
					tempUrl = webContext
							+ '/extjs/workflow?method=getData&taskId=' + taskId
							+ '&users=makeInfoByEngineer:' + tempEngineerId
							+ '$executeByEngineer:' + tempEngineerId
							+ '$issueByEngineer:' + tempEngineerId
							+ '$selectEngineer:' + tempOfficerId;
							if(tempServiceManageId != ""){
								tempUrl += '$confirmByServiceManager:' + tempServiceManageId;
							}		
				} else {				
					tempUrl = webContext
							+ '/extjs/workflow?method=getData&taskId=' + taskId
							+ '&users=selectEngineer:' + tempOfficerId;
							if(tempServiceManageId != ""){
								tempUrl += '$confirmByServiceManager:' + tempServiceManageId;
							}		
				}
				//add by awen for remove dynamic assign auditPerson on 2011-06-05 begin
				tempUrl = webContext
				+ '/extjs/workflow?method=getData&taskId=' + taskId;
				//add by awen for remove dynamic assign auditPerson on 2011-06-05 end
				Ext.Ajax.request({
					url : tempUrl,
					method : 'post',
					success : function(response, options) {
						window.parent.auditContentWin.specialAudit();
					},
					failure : function(response, options) {
						alert("指派节点审批人失败！");
					}
				})
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
            title:tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
            border : false, 
            //tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
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
	
	
 getFormpanel_SpecialRequireDevConfirm_Input: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SpecialRequireDevConfirm_Input",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("devsr_confirmByTechnicManager", "panel_SpecialRequireDevConfirm_Input", this.dataId);// 这是要随时变得
			} else {
				data = da.getPanelElementsForAdd("panel_SpecialRequireDevConfirm_Input");
			}
			for (i = 0; i < data.length; i++) {
			var idStr = data[i].id;
			if(idStr=='SpecialRequirement$applyNum'|| idStr=='SpecialRequirement$applyUserCombo'
				||idStr=='SpecialRequirement$applyDate'|| idStr=='SpecialRequirement$tel'
				||idStr=='SpecialRequirement$mobilePhone'|| idStr=='SpecialRequirement$finishDate'
				||idStr=='SpecialRequirement$flatCombo'|| idStr=='SpecialRequirement$confirmUserCombo'){
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if(idStr=='SpecialRequirement$appConfigItemCombo'){
				data[i].store = new Ext.data.JsonStore({
					url:webContext+'/SRAction_getAppConfigItemComboData.action?appTypeId=16',
					fields:['id','name'],
					listeners:{
						beforeload : function(store, opt){
							if(opt.params['SpecialRequirement$appConfigItemCombo'] == undefined){
								opt.params['name'] =Ext.getCmp('SpecialRequirement$appConfigItemCombo').defaultParam;
							}
						}
					},
					totalProperty:'rowCount',
					root:'data',
					id:'id'
				});
				data[i].initComponent();
			}
			if (data[i].id == 'SpecialRequirement$bigTypeCombo') {
				data[i].on('select', function(combo, record, index) {
					Ext.getCmp('SpecialRequirement$smallTypeCombo').clearValue();
				})
			}
			if (data[i].id == 'SpecialRequirement$smallTypeCombo') {
				data[i] = new Ext.form.ComboBox({
				xtype:'combo',
				hiddenName: 'SpecialRequirement$smallType',
				id :'SpecialRequirement$smallTypeCombo',
				width:200,
				fieldLabel:'需求小类',
				lazyRender: true,
				displayField: 'name',
				valueField :'id',
				emptyText:'请选择...',
				allowBlank:true,
				typeAhead:true,
				name:'SpecialRequirement$smallType',
				triggerAction:'all',minChars :50,
				queryDelay : 700,
				store:new Ext.data.JsonStore({
					url:webContext+'/extjs/comboDataAction?clazz=com.zsgj.itil.config.extlist.entity.RequirementSmallType',
					fields:['id','name'],
					totalProperty:'rowCount',
					root:'data',
					id:'id',
					listeners : {
						beforeload : function(store, opt) {
							if (Ext.getCmp('SpecialRequirement$bigTypeCombo').getValue() != "") {
								opt.params['type'] = Ext.getCmp('SpecialRequirement$bigTypeCombo').getValue();
							}
						}
					}
				}),
				pageSize:10,
				listeners:{
					'beforequery' : function(queryEvent){
						var pid = Ext.getCmp('SpecialRequirement$bigTypeCombo').getValue();
						if(pid==''){
							alert('请先选择需求大类');
							return false;
						}
						var param = queryEvent.combo.getRawValue();
						this.store.load({params:{name:param,type:pid,start:0}});return true;}
					},
				initComponent : function() {
					this.store.load({params:{id:Ext.getCmp('SpecialRequirement$smallTypeCombo').getValue(),start:0},
					callback:function(r, options, success){
						Ext.getCmp('SpecialRequirement$smallTypeCombo').setValue(Ext.getCmp('SpecialRequirement$smallTypeCombo').getValue());
						}});
					}})
				}
			if (data[i].id == 'SpecialRequirement$deliveryTeamCombo') {
						data[i] = new Ext.form.ComboBox({
							xtype:"combo",
							hiddenName: "SpecialRequirement$deliveryTeam",
							id :"SpecialRequirement$deliveryTeamCombo",
							width:"null",
							fieldLabel:"交付团队",
							lazyRender: true,
							displayField: "name",
							valueField :"id",
							forceSelection: true,
							emptyText:"请选择...",
							allowBlank:true,
							name:"SpecialRequirement$deliveryTeam",
							value:data[i].value,
							triggerAction:"all",
							minChars :50,queryDelay : 700,
							store:new Ext.data.JsonStore({
								url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.zsgj.itil.config.extci.entity.DeliveryTeam",
								fields:["id","name"],totalProperty:"rowCount",root:"data"}),
							pageSize:10,
							listeners:{"beforequery" : function(queryEvent){
								var pid = Ext.getCmp('SpecialRequirement$deliveryTeamCombo').getValue();
								var store=queryEvent.combo.store;
								store.baseParams={
									"name":queryEvent.query,
									fuzzyQuery:"name"
								};
								store.load({params:{start:0}});
								return false;}},
							initComponent : function() {this.store.load({params:{id:Ext.getCmp("SpecialRequirement$deliveryTeamCombo").getValue(),start:0},callback:function(r, options, success){Ext.getCmp("SpecialRequirement$deliveryTeamCombo").setValue(Ext.getCmp("SpecialRequirement$deliveryTeamCombo").getValue());}});}});
				
				data[i].on('select', function(combo, record, index) {
					Ext.getCmp('SpecialRequirement$mainEngineerCombo').clearValue();
					Ext.getCmp('SpecialRequirement$assistanEngineerCombo').clearValue();
				})
			}else if(data[i].id == 'SpecialRequirement$mainEngineerCombo'){
				data[i] = new Ext.form.ComboBox({
							xtype:"combo",
							hiddenName: "SpecialRequirement$mainEngineer",
							id :"SpecialRequirement$mainEngineerCombo",
							width:"null",
							fieldLabel:"交付经理",
							lazyRender: true,
							displayField: "userInfo",
							valueField :"id",
							forceSelection: true,
							emptyText:"请选择...",
							allowBlank:true,
							name:"SpecialRequirement$mainEngineer",
							value:data[i].value,
							triggerAction:"all",
							minChars :50,queryDelay : 700,
							store:new Ext.data.JsonStore({
								url:webContext+"/configItemAction_findServiceEngineer.action",
								fields:["id","userInfo"],totalProperty:"rowCount",root:"data",id:"id"}),
							pageSize:10,
							listeners:{"beforequery" : function(queryEvent){
								var pid = Ext.getCmp('SpecialRequirement$deliveryTeamCombo').getValue();
								if (pid == "") {
									Ext.Msg.alert("提示", "请先选择交付团队!");
									return false;
								}
								var store=queryEvent.combo.store;
								store.baseParams={
									"userInfo.userName":queryEvent.query,
									"deliveryTeamId":pid,									
									fuzzyQuery:"userInfo.userName"
								};
								store.load({params:{start:0}});
								return false;}},
							initComponent : function() {
									var strMainEngineer = Ext.getCmp("SpecialRequirement$mainEngineerCombo").getValue();
									if(strMainEngineer !=''&&strMainEngineer != null){
										this.store.load({
											params:{
													id:strMainEngineer,
													start:0
											},
											callback:function(r, options, success){
													Ext.getCmp("SpecialRequirement$mainEngineerCombo").setValue(strMainEngineer);
											}
											});
									}	
							}
						});
			}else if(data[i].id == 'SpecialRequirement$assistanEngineerCombo'){
				data[i].id == 'SpecialRequirement$assistanEngineerCombo';
				data[i] = new Ext.form.ComboBox({
							xtype:"combo",
							hiddenName: "SpecialRequirement$assistanEngineer",
							id :"SpecialRequirement$assistanEngineerCombo",
							width:"null",style:"",
							fieldLabel:"辅助工程师",
							lazyRender: true,
							displayField: "userInfo",
							valueField :"id",
							forceSelection: true,
							emptyText:"请选择...",
							allowBlank:true,
							name:"SpecialRequirement$assistanEngineer",
							value:data[i].value,
							triggerAction:"all",
							minChars :50,
							queryDelay : 700,
							store:new Ext.data.JsonStore({
								url:webContext+"/configItemAction_findServiceEngineer.action",
								fields:["id","userInfo"],totalProperty:"rowCount",root:"data",id:"id"}),
							pageSize:10,
							listeners:{"beforequery" : function(queryEvent){
								var pid = Ext.getCmp('SpecialRequirement$deliveryTeamCombo').getValue();
								if (pid == "") {
									Ext.Msg.alert("提示", "请先选择交付团队!");
									return false;
								}
								var store=queryEvent.combo.store;
								store.baseParams={
									"userInfo.userName":queryEvent.query,
									"deliveryTeamId":pid,									
									fuzzyQuery:"userInfo.userName"
								};
								store.load({params:{start:0}});
								return false;}},
							initComponent : function() {
									var strMainEngineer = Ext.getCmp("SpecialRequirement$assistanEngineerCombo").getValue();
									if(strMainEngineer !=''&&strMainEngineer != null){
										this.store.load({
											params:{
													id:strMainEngineer,
													start:0
											},
											callback:function(r, options, success){
													Ext.getCmp("SpecialRequirement$assistanEngineerCombo").setValue(strMainEngineer);
											}
											});
									}	
							}
			});
			}
		}
		biddata = da.split(data);
		if(this.getFormButtons.length!=0){
		this.formpanel_SpecialRequireDevConfirm_Input= new Ext.form.FormPanel({
			id : 'panel_SpecialRequireDevConfirm_Input',
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
			title : "开发类需求审批面板",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_SpecialRequireDevConfirm_Input= new Ext.form.FormPanel({
			id : 'panel_SpecialRequireDevConfirm_Input',
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
			title : "开发类需求审批面板",
			items : biddata
			});
		}
		return this.formpanel_SpecialRequireDevConfirm_Input;
	},
  items : this.items,
  buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
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
		this.model = "devsr_confirmByTechnicManager";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"devsr_confirmByTechnicManager", this);
		this.buttons = new Array();
		if (this.readOnly != "1") {
			this.buttons = this.mybuttons;
		}

		this.getFormpanel_SpecialRequireDevConfirm_Input();
		this.pa.push(this.formpanel_SpecialRequireDevConfirm_Input);
		this.formname.push("panel_SpecialRequireDevConfirm_Input");
		temp.push(this.formpanel_SpecialRequireDevConfirm_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		this.items = items;
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})