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
	getFormpanel_erpSR_it_input: function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_remark", "panel_erpSR_it_input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_erpSR_it_input");
		}
		biddata = da.splitForReadOnly(data);
		var formpanel_erpSR_it_input= new Ext.form.FormPanel({
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
			title : "ERP非常规需求",
			items : biddata
		});
		return formpanel_erpSR_it_input;
	},
	getFormpanel_srInfo_input3 : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_remark",
					"panel_srInfo_input3", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input3");
		}
		var modifyData = new Array();
		
		for (var i = 0; i < data.length; i++) {				
			if(i==9){
				modifyData.push(this.getGridpanel_SRTestReport());
			}
			modifyData.push(data[i]);
		}
		biddata = da.splitForReadOnly(modifyData);		
//		biddata = da.splitForReadOnly(data);
		var formpanel_srInfo_input3 = new Ext.form.FormPanel({
			id : 'panel_srInfo_input3',
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
			title : "需求分析",
			items : biddata
		});
		return formpanel_srInfo_input3;
	},
	getGridpanel_SRTestReport : function() {
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",
				this);

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
			} else if (editor.xtype == 'datefield') {
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
		this.store = da.getPagePanelJsonStore("panel_SRTestReport", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_SRTestReport = new Ext.grid.EditorGridPanel({
			fieldLabel : '测试信息',
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			//title : "用户测试报告",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
//			loadMask : true,
			height : 120,
			width : 9999,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return gridpanel_SRTestReport;
	},	
  items : this.items,
  buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
		var items = new Array();
		var temp = new Array();
		this.model = "esr_remark";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("esr_remark", this);
		this.buttons = new Array();
		if(this.readOnly!=1){
			this.buttons =this.mybuttons;
		}
		
		temp.push(this.getFormpanel_erpSR_it_input());
		temp.push(this.getFormpanel_srInfo_input3());	//add by zhangzy for 用户要求添加工程师填写的信息 in 2009 12 7 	
		
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})
