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
	
	
 getFormflm_requireForm_panel: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			   this.getFormButtons=new Array();
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_t3_custmgr_contract", "flm_requireForm_panel", this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_requireForm_panel");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formflm_requireForm_panel= new Ext.form.FormPanel({
			id : 'flm_requireForm_panel',
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
			title : "需求信息",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formflm_requireForm_panel= new Ext.form.FormPanel({
			id : 'flm_requireForm_panel',
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
			title : "需求信息",
			items : biddata
			});
		}
		return this.formflm_requireForm_panel;
	},
getGridComconfigItemListPanel:function(){
		var da = new DataAction();
		var obj = da.getListPanelElementsForHead("configItemListPanel");
			var getGridButtons=new Array();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var hide = headItem.isHidden;
			var isHidde = false;
			if (hide == 'true') {
				isHidde= true;
			}
			// 给每一行增加修饰
			var columnItem = "";
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidde,
					align : alignStyle
				}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("configItemListPanel",obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.gridComconfigItemListPanel= new Ext.grid.EditorGridPanel({
			id : 'configItemListPanel',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title :"配置项列表",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
			if (this.dataId != '0') {
				
					var pcnameValue = "";
					var fcnameObj = Ext.getCmp("");
					if(fcnameObj!=undefined){
					   pcnameValue = Ext.getCmp("").getValue();
					}
					var str = '{' + '\"' + "" + '\"' + ':' + '\"'
							+ pcnameValue + '\"' + '}';// 这是要随时变得
							
					if(this.model=="flm_requireFormPage"|| "configItemListPanel"=="configItemListPanel"){ //
						var str = '{\"configItemRequireId\":\"' + this.dataId + '\"}';
					}
					
					var param = eval('(' + str + ')');
					param.methodCall = 'query';
					// alert(str);
					this.store.load({
						params : param
					});
				}
		return this.gridComconfigItemListPanel;
	},
getGridflm_projectPlan_grid:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("flm_projectPlan_grid");
			var getGridButtons=new Array();
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// 给每一行增加修饰
			var columnItem = "";
			if (editor.xtype == 'combo') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
					}
				}else if(editor.xtype=='datefield'){
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
                    renderer : renderer,                              
					editor : editor
				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("flm_projectPlan_grid",obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.gridflm_projectPlan_grid= new Ext.grid.EditorGridPanel({
			id : 'flm_projectPlan_grid',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title :"项目计划",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
			if (this.dataId != '0') {
					var pcnameValue = "";
					var fcnameObj = Ext.getCmp("flm_Requirement$id");
					if(fcnameObj!=undefined){
					   pcnameValue = Ext.getCmp("flm_Requirement$id").getValue();
					}
					var str = '{' + '\"' + "requirement" + '\"' + ':' + '\"'
							+ pcnameValue + '\"' + '}';// 这是要随时变得
					var param = eval('(' + str + ')');
					param.methodCall = 'query';
					// alert(str);
					this.store.load({
						params : param
					});
				}
		return this.gridflm_projectPlan_grid;
	},
 getFormflm_contract_panel: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("flm_contract_panel",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_t3_custmgr_contract", "flm_contract_panel", this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_contract_panel");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formflm_contract_panel= new Ext.form.FormPanel({
			id : 'flm_contract_panel',
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
			title : "合同信息",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formflm_contract_panel= new Ext.form.FormPanel({
			id : 'flm_contract_panel',
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
			title : "合同信息",
			items : biddata
			});
		}
		return this.formflm_contract_panel;
	},
 getFormflm_contractAnalysePanel: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("flm_contractAnalysePanel",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("flm_t3_custmgr_contract", "flm_contractAnalysePanel", this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("flm_contractAnalysePanel");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formflm_contractAnalysePanel= new Ext.form.FormPanel({
			id : 'flm_contractAnalysePanel',
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
			title : "合同分析报告",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formflm_contractAnalysePanel= new Ext.form.FormPanel({
			id : 'flm_contractAnalysePanel',
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
			title : "合同分析报告",
			items : biddata
			});
		}
		return this.formflm_contractAnalysePanel;
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
		var gridname = new Array();
		  this.gridname=gridname;
		this.model="flm_t3_custmgr_contract";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("flm_t3_custmgr_contract",this);
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
		
		       this.getFormflm_requireForm_panel();
		       this.pa.push(this.formflm_requireForm_panel);
		       this.formname.push("flm_requireForm_panel");
		       temp.push(this.formflm_requireForm_panel);
		       this.getGridComconfigItemListPanel();
		       this.gd.push(this.gridComconfigItemListPanel);
		       this.gridname.push("configItemListPanel");
		       temp.push(this.gridComconfigItemListPanel);
		       this.getGridflm_projectPlan_grid();
		       this.gd.push(this.gridflm_projectPlan_grid);
		       this.gridname.push("flm_projectPlan_grid");
		       temp.push(this.gridflm_projectPlan_grid);
		       this.getFormflm_contract_panel();
		       this.pa.push(this.formflm_contract_panel);
		       this.formname.push("flm_contract_panel");
		       temp.push(this.formflm_contract_panel);
		       this.getFormflm_contractAnalysePanel();
		       this.pa.push(this.formflm_contractAnalysePanel);
		       this.formname.push("flm_contractAnalysePanel");
		       temp.push(this.formflm_contractAnalysePanel);
          items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})