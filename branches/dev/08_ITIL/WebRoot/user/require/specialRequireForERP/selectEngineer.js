PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	buttonAlign:'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	saveAndSubmit : function() {
		var taskId = this.taskId;
		if(!Ext.getCmp('panel_erpSR_it_input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		if(!Ext.getCmp('SpecialRequirement$mainEngineerCombo').getValue()){
			Ext.MessageBox.alert("提示","请选择交付经理。谢谢您合作！");	
			return false;
		}
		var formParam = Ext.encode(getFormParam('panel_erpSR_it_input'));
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveSpecialRequire.action',
			params : {
				info : formParam
			},
			success : function(response, options) {
				var tempMainEngineerId = Ext.util.JSON.decode(response.responseText).mainEngineer;//交付经理
				Ext.Ajax.request({
					url : webContext
							+ '/extjs/workflow?method=getData&taskId='
							+ taskId + '&users=makeProjectPlan:'
							+ tempMainEngineerId + '$engineerApplyTest:'
							+ tempMainEngineerId + '$issueByEngineer:'
							+ tempMainEngineerId,
					method : 'post',
					success : function(response, options) {
						window.parent.auditContentWin.specialAudit();
					},
					failure : function(response, options) {
						Ext.getCmp('submitButton').enabled();
					}
				})
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
	getFormpanel_erpSR_it_input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_erpSR_it_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_selectEngineer",
					"panel_erpSR_it_input", this.dataId);// 这是要随时变得
			// biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_erpSR_it_input");
			// biddata = da.split(data);
		}
		for (i = 0; i < data.length; i++) {
			if(data[i].id!='SpecialRequirement$mainEngineerCombo'
				&&data[i].id!='SpecialRequirement$assistanEngineerCombo'
				&&data[i].id!='SpecialRequirement$isNewFactoryCombo'
				&&data[i].id!='SpecialRequirement$smallTypeCombo'){
				data[i].readOnly = true;
				data[i].hideTrigger = true;
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
					url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.config.extlist.entity.RequirementSmallType',
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
					}});
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
								url:webContext+"/configItemAction_findConfigItemExtendInfo.action?clazz=com.digitalchina.itil.config.extci.entity.DeliveryTeam",
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
		if (this.getFormButtons.length != 0) {
			this.formpanel_erpSR_it_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_it_input',
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
				title : "ERP非常规服务",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_erpSR_it_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_it_input',
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
				title : "ERP非常规服务",
				items : biddata
			});
		}
		return this.formpanel_erpSR_it_input;
	},

	items : this.items,
	buttons:this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
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
		this.model = "esr_selectEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("esr_selectEngineer",this);
		this.buttons = new Array();
		if(this.readOnly!=1){
			this.buttons =this.mybuttons;
		}
		this.getFormpanel_erpSR_it_input();
		this.pa.push(this.formpanel_erpSR_it_input);
		this.formname.push("panel_erpSR_it_input");
		temp.push(this.formpanel_erpSR_it_input);

		this.on("saveAndSubmit", this.saveAndSubmit, this);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})
