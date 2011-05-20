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
			width : 830,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			//width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	}, 
	
 getFormflm_contractAnalysePanel: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_contractAnalyseReportPage", "flm_contractAnalysePanel", this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_contractAnalysePanel");
				biddata = da.split(data);
			}
		this.formflm_contractAnalysePanel= new Ext.form.FormPanel({
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
			title : "合同分析报告基本信息",
			items : biddata
		});
		return this.formflm_contractAnalysePanel;
	},
 getFormflm_contractAnalyseExtPanel: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_contractAnalyseReportPage", "flm_contractAnalyseExtPanel", this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_contractAnalyseExtPanel");
				biddata = da.split(data);
			}
		this.formflm_contractAnalyseExtPanel= new Ext.form.FormPanel({
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
			title : "合同分析报告可扩展信息",
			items : biddata
		});
		return this.formflm_contractAnalyseExtPanel;
	},
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
	    var items = new Array();
	    var pa = new Array();
		  this.pa = pa;
		var gd = new Array();
		  this.gd = gd;
		var temp = new Array();
		  this.temp = temp;
		var formname = new Array();
		  this.formname=formname;
		var girdname = new Array();
		  this.girdname=girdname;
		this.model="flm_contractAnalyseReportPage";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("flm_contractAnalyseReportPage",this);
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
		       this.getFormflm_contractAnalysePanel();
		       this.pa.push(this.formflm_contractAnalysePanel);
		       this.formname.push("flm_contractAnalysePanel");
		       temp.push(this.formflm_contractAnalysePanel);
		       this.getFormflm_contractAnalyseExtPanel();
		       this.pa.push(this.formflm_contractAnalyseExtPanel);
		       this.formname.push("flm_contractAnalyseExtPanel");
		       temp.push(this.formflm_contractAnalyseExtPanel);
          items = temp;
		items.push(this.buttons);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})