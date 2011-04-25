PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	align : 'center',
	foredit : true,
	frame : true,
	buttonAlign : 'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},

	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			width : 800,
			//title : tabTitle,
			enableTabScroll : true,
			// minTabWidth:100,
			//resizeTabs : true,
			deferredRender : false,
			frame : true,
			//plain : true,
			autoScroll : true,
			border : true,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
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

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed5_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_confirmByFinancialManager2_k",
					"panel_ERP_NormalNeed5_Input", this.dataId);// 这是要随时变得
			//modify by lee for 附件可编辑 in 20091221 begin
			//biddata = da.splitForReadOnly(data);
			for (i = 0; i < data.length; i++) {
				if (data[i].xtype != "panel") {
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
			}
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed5_Input");
		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(i==9){
				modifyData.push(this.getpanel_requireFactoryInfo_list_1());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);			
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed5_Input',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "ERP常规服务申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed5_Input',
				layout : 'table',
				height :800,
				width : 500,
				frame : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "ERP常规服务申请",
				items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed1_Input;
	},
	getpanel_requireFactoryInfo_list_1:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_requireFactoryInfo_list_1");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_requireFactoryInfo_list_1",
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
		this.store = da.getPagePanelJsonStore("panel_requireFactoryInfo_list_1", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_requireFactoryInfo_list_1 = new Ext.grid.EditorGridPanel({
			fieldLabel : '工厂明细',
			id : 'panel_requireFactoryInfo_list_1',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			height : 220,
			width : 9999,// this.width - 15,
			frame : true,
	        viewConfig: {   
                  forceFit:true   
            },   
           bbar: new Ext.PagingToolbar({   
           	    id:'pagId',
                pageSize: 10,   
                store: this.store,   
               displayInfo: true
           }) 			
		});
		if (this.dataId != '') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("ERP_NormalNeed$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("ERP_NormalNeed$id").getValue();
			}
			var str = '{' + '\"' + "requireData" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			param.start = 0;
			this.store.baseParams=param;
			this.store.load();
		}	
	return 	gridpanel_requireFactoryInfo_list_1;
		
	}, 	
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {

		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
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
		this.model = "nr_confirmByFinancialManager2_k";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"nr_confirmByFinancialManager2_k", this);
		this.buttons = new Array();
		if (this.readOnly != 1) {
			this.buttons = this.mybuttons;
		}

		this.getFormpanel_ERP_NormalNeed1_Input();
		//this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		//this.formname.push("panel_ERP_NormalNeed2_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})