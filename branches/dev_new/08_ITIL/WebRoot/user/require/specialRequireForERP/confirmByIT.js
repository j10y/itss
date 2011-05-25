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
		var formParam = Ext.encode(getFormParam('panel_erpSR_it_input'));
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveSpecialRequire.action',
			params : {
				info : formParam
			},
			success : function(response, options) {
				var tempOfficerId = Ext.util.JSON.decode(response.responseText).serviceProviderInOfficer;//交付线负责人
				Ext.Ajax.request({
					url : webContext
							+ '/extjs/workflow?method=getData&taskId='
							+ taskId + '&users=selectEngineer:'
							+ tempOfficerId
							+ '$confirmByServiceProvider:'
							+ tempOfficerId,
					method : 'post',
					success : function(response, options) {
						window.parent.auditContentWin.specialAudit();
					},
					failure : function(response, options) {

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
			activeTab : this.tabNum,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
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
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_confirmByIT",
					"panel_erpSR_it_input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_erpSR_it_input");
		}
		Ext.getCmp('SpecialRequirement$bigTypeCombo').setValue('6');
		Ext.getCmp('SpecialRequirement$bigTypeCombo').initComponent();
		Ext.getCmp('SpecialRequirement$smallTypeCombo').setValue('18');
		Ext.getCmp('SpecialRequirement$smallTypeCombo').initComponent();
		Ext.getCmp('SpecialRequirement$deliveryTeamCombo').setValue('2');
		Ext.getCmp('SpecialRequirement$deliveryTeamCombo').initComponent();
		
//add by zhangzy for 用户要求显示 需求小类 字段 in 2009 12 4 start		
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(data[i].id!="SpecialRequirement$smallTypeCombo"  ){ 
				if(data[i].id =="SpecialRequirement$attachment"){
					data[i].readOnly = false;
					data[i].hideTrigger = false;
				}else{
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
			}else{
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
					}});
			}	
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);		
//		biddata = da.splitForReadOnly(data);
//add by zhangzy for 用户要求显示 需求小类 字段 in 2009 12 4 start
		
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
			title : "ERP个性化服务主表",
			items : biddata
		});
		return this.formpanel_erpSR_it_input;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var beginT = new Date();
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});
		this.spid = "";
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
		this.model = "esr_confirmByIT";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("esr_confirmByIT", this);
		this.buttons = new Array();
		if(this.readOnly!=1){
			this.buttons =this.mybuttons;
		}
		this.getFormpanel_erpSR_it_input();
		this.pa.push(this.formpanel_erpSR_it_input);
		this.formname.push("panel_erpSR_it_input");
		temp.push(this.formpanel_erpSR_it_input);

		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		this.on("saveAndSubmit", this.saveAndSubmit, this);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})