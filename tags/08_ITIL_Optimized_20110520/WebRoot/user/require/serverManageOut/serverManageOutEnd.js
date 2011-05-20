PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
            //minTabWidth:100,
            //resizeTabs:true,
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
	
	
 getFormpanel_ServerManageOut_Input: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_ServerManageOut_Input",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("serverManageOutend", "panel_ServerManageOut_Input", this.dataId);// 这是要随时变得
				biddata = da.splitForReadOnly(data);
			} else {
				data = da.getPanelElementsForAdd("panel_ServerManageOut_Input");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_ServerManageOut_Input= new Ext.form.FormPanel({
			id : 'panel_ServerManageOut_Input',
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
			title : "服务器迁出数据中心申请",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_ServerManageOut_Input= new Ext.form.FormPanel({
			id : 'panel_ServerManageOut_Input',
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
			title : "服务器迁出数据中心申请",
			items : biddata
			});
		}
		return this.formpanel_ServerManageOut_Input;
	},
		//获取配置项基础信息面板
	getFormconfigItemBasicPanel : function(id) {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (id == 0) {
			data = da.getPanelElementsForAdd('configItemBasicPanel');
		} else {
			data = da.getSingleFormPanelElementsForEdit("configItemBasicPanel",id);
		}
		biddata = da.splitForReadOnly(data);
		this.formconfigItemBasicPanel = new Ext.form.FormPanel({
			id : 'configItemBasicPanel',
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
			title : "配置项基础信息",
			items : biddata
		});
		return this.formconfigItemBasicPanel;
	},
	//获取服务项信息面板
	getFormpanel_ServerPanel : function(id) {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (id == 0) {
			data = da.getPanelElementsForAdd('panel_ServerPanel');
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_ServerPanel", id);
		}
		biddata = da.splitForReadOnly(data);
		this.formpanel_ServerPanel = new Ext.form.FormPanel({
			id : 'formpanel_ServerPanel',
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
			title : "服务器信息",
			items : biddata
		});
		return this.formpanel_ServerPanel;
	},
	getServerPanel : function(ciId, serverId) {
		this.getFormconfigItemBasicPanel(ciId);
		this.getFormpanel_ServerPanel(serverId);

		this.serverPanel = new Ext.Panel({
			id : 'serverPanel',
			height : 'auto',
			align : 'center',
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
			title : "服务器配置项信息",
			items : [this.formconfigItemBasicPanel, this.formpanel_ServerPanel]
		});
		return this.serverPanel;
	},
 getFormpanel_ServerManageOutInfoOne_input: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_ServerManageOutInfoOne_input",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("serverManageOutend", "panel_ServerManageOutInfoOne_input", this.dataId);// 这是要随时变得
				biddata = da.splitForReadOnly(data);
			} else {
				data = da.getPanelElementsForAdd("panel_ServerManageOutInfoOne_input");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_ServerManageOutInfoOne_input= new Ext.form.FormPanel({
			id : 'panel_ServerManageOutInfoOne_input',
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
			title : "迁出服务器管理员处理信息",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_ServerManageOutInfoOne_input= new Ext.form.FormPanel({
			id : 'panel_ServerManageOutInfoOne_input',
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
			title : "迁出服务器管理员处理信息",
			items : biddata
			});
		}
		return this.formpanel_ServerManageOutInfoOne_input;
	},
 getFormpanel_ServerManageOutInfoTwo_input: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_ServerManageOutInfoTwo_input",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("serverManageOutend", "panel_ServerManageOutInfoTwo_input", this.dataId);// 这是要随时变得
				biddata = da.splitForReadOnly(data);
			} else {
				data = da.getPanelElementsForAdd("panel_ServerManageOutInfoTwo_input");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_ServerManageOutInfoTwo_input= new Ext.form.FormPanel({
			id : 'panel_ServerManageOutInfoTwo_input',
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
			title : "迁出数据中心管理员处理信息",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_ServerManageOutInfoTwo_input= new Ext.form.FormPanel({
			id : 'panel_ServerManageOutInfoTwo_input',
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
			title : "迁出数据中心管理员处理信息",
			items : biddata
			});
		}
		return this.formpanel_ServerManageOutInfoTwo_input;
	},
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.ServerManage"
		});

	    var items = new Array();
	    var pa = new Array();
		  this.pa = pa;
		var gd = new Array();
		  this.gd = gd;
		var temp = new Array();
		  this.temp = temp;
		var formname = new Array();
		  this.formname=formname;
		var gridname = new Array();
		  this.gridname=gridname;
		this.model="serverManageOutend";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("serverManageOutend",this);
		//add by lee for add back button in 20091104 begin
		var backButton = new Ext.Button({
			text : '返回',
			//pressed : true,
			iconCls : 'back',
			scope : this,
			handler : function() {
				window.location = webContext
				+ "/requireAction_toRequireInfo.action?serviceItemId="
				+ this.serviceItemId;
			}
		});
		if(this.serviceItemId!=""){
			this.mybuttons.push(backButton);
		}
		//add by lee for add back button in 20091104 end
		if(this.mybuttons!=""){
			this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : this.mybuttons
			};
		}else{
			this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			}
			};
		}
		
		       this.getFormpanel_ServerManageOut_Input();
		       this.pa.push(this.formpanel_ServerManageOut_Input);
		       this.formname.push("panel_ServerManageOut_Input");
		       temp.push(this.formpanel_ServerManageOut_Input);
		       var sra = new SRAction();
		var data = sra.getServerManageCiData(this.dataId);
		var ciId = data.ciId;
		var serverId = data.serverId;
		this.getServerPanel(ciId, serverId);
		temp.push(this.serverPanel);
		       this.getFormpanel_ServerManageOutInfoOne_input();
		       this.pa.push(this.formpanel_ServerManageOutInfoOne_input);
		       this.formname.push("panel_ServerManageOutInfoOne_input");
		       temp.push(this.formpanel_ServerManageOutInfoOne_input);
		       this.getFormpanel_ServerManageOutInfoTwo_input();
		       this.pa.push(this.formpanel_ServerManageOutInfoTwo_input);
		       this.formname.push("panel_ServerManageOutInfoTwo_input");
		       temp.push(this.formpanel_ServerManageOutInfoTwo_input);
		       temp.push(histroyForm);
          items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})