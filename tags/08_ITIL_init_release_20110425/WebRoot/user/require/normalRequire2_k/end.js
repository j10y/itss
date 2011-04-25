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
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			//minTabWidth:100,
			//resizeTabs:true,
			title : tabTitle,
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

	getFormpanel_ERP_NormalNeed3_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_end2_k",
					"panel_ERP_NormalNeed5_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed5_Input");
		}
		biddata = da.splitForReadOnly(data);
		this.formpanel_ERP_NormalNeed3_Input = new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed5_Input',
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
			title : "ERP常规申请",
			items : biddata
		});
		return this.formpanel_ERP_NormalNeed3_Input;
	},
	getFormpanel_ErpEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_end2_k",
					"panel_ErpEngineerFeedback_f_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ErpEngineerFeedback_f_Input");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
//				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
//				data[i] = Ext.decode(dd);
			}
			data[i].readOnly = true;
		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(i==3){
				modifyData.push(this.getpanel_requireFactoryInfo_list_1());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);	
		this.formpanel_ErpEngineerFeedback_Input = new Ext.form.FormPanel({
			id : 'panel_ErpEngineerFeedback_f_Input',
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
			title : "ERP工程师反馈信息",
			items : biddata
		});
		return this.formpanel_ErpEngineerFeedback_Input;
	},
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 begin
//	getFormpanel_BASISEngineerFeedback_Input : function() {
//		var da = new DataAction();
//		var data = null;
//		// 判断是新增还是修改
//		var biddata = "";
//		if (this.dataId != '0') {
//			data = da.getPanelElementsForEdit("nr_end2_k",
//					"panel_BASISEngineerFeedback_Input", this.dataId);// 这是要随时变得
//		} else {
//			data = da
//					.getPanelElementsForAdd("panel_BASISEngineerFeedback_Input");
//		}
//		for (i = 0; i < data.length; i++) {
//			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
//				data[i].id = data[i].id + 8;//改变Combobox的id
//				data[i].readOnly = true;
//				data[i].hideTrigger = true;
//			}
//			if (data[i].xtype == "panel") {
//				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
//				data[i] = Ext.decode(dd);
//			}
//			data[i].readOnly = true;
//		}
//		biddata = da.split(data);
//		this.formpanel_BASISEngineerFeedback_Input = new Ext.form.FormPanel({
//			id : 'panel_BASISEngineerFeedback_Input',
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			frame : true,
//			collapsible : true,
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			title : "BASIS工程师反馈",
//			items : biddata
//		});
//		return this.formpanel_BASISEngineerFeedback_Input;
//	},
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 end
	getFormpanel_EBEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_end2_k",
					"panel_EBEngineerFeedback_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_EBEngineerFeedback_Input");
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
//				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
//				data[i] = Ext.decode(dd);
			}
			data[i].readOnly = true;
		}
		biddata = da.split(data);
		this.formpanel_EBEngineerFeedback_Input = new Ext.form.FormPanel({
			id : 'panel_EBEngineerFeedback_Input',
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
			title : "EB工程师反馈",
			items : biddata
		});
		return this.formpanel_EBEngineerFeedback_Input;
	},
	getpanel_requireFactoryInfo_list_1:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_requireFactoryInfo_list_3");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_requireFactoryInfo_list_3",
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
		this.store = da.getPagePanelJsonStore("panel_requireFactoryInfo_list_3", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_requireFactoryInfo_list_1 = new Ext.grid.EditorGridPanel({
			fieldLabel : '工厂明细',
			id : 'panel_requireFactoryInfo_list_3',
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
		if(this.serviceItemId!=""){
				this.buttons=[ new Ext.Button({
					text : '返回',
					//pressed : true,
					iconCls : 'back',
					scope : this,					
					handler : function() {
						window.location = webContext
						+ "/requireAction_toRequireInfo.action?serviceItemId="
						+ this.serviceItemId;
					}
				})]
		}

		this.getFormpanel_ERP_NormalNeed3_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed3_Input);
		this.formname.push("panel_ERP_NormalNeed5_Input");
		temp.push(this.formpanel_ERP_NormalNeed3_Input);
		this.getFormpanel_ErpEngineerFeedback_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		this.formname.push("panel_ErpEngineerFeedback_f_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_Input);
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 begin
//		this.getFormpanel_BASISEngineerFeedback_Input();
//		this.pa.push(this.formpanel_BASISEngineerFeedback_Input);
//		this.formname.push("panel_BASISEngineerFeedback_Input");
//		temp.push(this.formpanel_BASISEngineerFeedback_Input);
//remove by lee for 用户让去掉BASIS工程师处理 in 20091204 end
		this.getFormpanel_EBEngineerFeedback_Input();
		this.pa.push(this.formpanel_EBEngineerFeedback_Input);
		this.formname.push("panel_EBEngineerFeedback_Input");
		temp.push(this.formpanel_EBEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
//		items.push(this.buttons);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})