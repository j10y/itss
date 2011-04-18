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
	saveAndSubmit : function() {
		if(!Ext.getCmp('panel_EBEngineerFeedback_Input').form.isValid()){
			Ext.MessageBox.alert("提示","页面中带红色波浪线的为必填项,请填写完整。谢谢您合作！");	
			return false;
		}
		var formParam = Ext.encode(getFormParam('panel_EBEngineerFeedback_Input'));
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveEbEngineerFeedback.action',
			params : {
				pagePanel:'panel_EBEngineerFeedback_Input',
				info:formParam,
				reqId:this.dataId
			},
			success : function(response, options) {
				var feedbackId = Ext.util.JSON.decode(response.responseText).id;
				Ext.getCmp('itil_sci_EBEngineerFeedback$id').setValue(feedbackId);
				window.parent.auditContentWin.specialAudit();
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : this.tabId,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			bodyBorder : true,
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

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed5_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_EBEngineerFeedback2_k",
					"panel_ERP_NormalNeed5_Input", this.dataId);// 这是要随时变得
			biddata = da.splitForReadOnly(data);
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed5_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
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
				title : "ERP常规服务申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
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
				title : "ERP常规服务申请",
				items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed1_Input;
	},
	getFormpanel_ErpEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ErpEngineerFeedback_f_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_EBEngineerFeedback2_k",
					"panel_ErpEngineerFeedback_f_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_ErpEngineerFeedback_f_Input");
		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(i==3){
				modifyData.push(this.getpanel_requireFactoryInfo_list_1());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);			
		if (this.getFormButtons.length != 0) {
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
					// ,
					// buttons : this.getFormButtons
			});
		} else {
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
		}
		return this.formpanel_ErpEngineerFeedback_Input;
	},

	// remove by lee for BASIS工程师处理环节被用户去除 in 20091202 begin
	// getFormpanel_BASISEngineerFeedback_Input: function() {
	// var da = new DataAction();
	// var data = null;
	// // 判断是新增还是修改
	// var biddata = "";
	//			
	// var buttonUtil = new ButtonUtil();
	// this.getFormButtons =
	// buttonUtil.getButtonForPanel("panel_BASISEngineerFeedback_Input",this);
	// if (this.dataId != '0') {
	// data = da.getPanelElementsForEdit("nr_EBEngineerFeedback2_k",
	// "panel_BASISEngineerFeedback_Input", this.dataId);// 这是要随时变得
	// biddata = da.splitForReadOnly(data);
	// } else {
	// data = da.getPanelElementsForAdd("panel_BASISEngineerFeedback_Input");
	// biddata = da.split(data);
	// }
	// if(this.getFormButtons.length!=0){
	// this.formpanel_BASISEngineerFeedback_Input= new Ext.form.FormPanel({
	// id : 'panel_BASISEngineerFeedback_Input',
	// layout : 'table',
	// height : 'auto',
	// width : 800,
	// frame : true,
	// collapsible : true,
	// defaults : {
	// bodyStyle : 'padding:4px'
	// },
	// layoutConfig : {
	// columns : 4
	// },
	// title : "BASIS工程师反馈",
	// items : biddata//,
	// //buttons : this.getFormButtons
	// });
	// }else{
	// this.formpanel_BASISEngineerFeedback_Input= new Ext.form.FormPanel({
	// id : 'panel_BASISEngineerFeedback_Input',
	// layout : 'table',
	// height : 'auto',
	// width : 800,
	// frame : true,
	// collapsible : true,
	// defaults : {
	// bodyStyle : 'padding:4px'
	// },
	// layoutConfig : {
	// columns : 4
	// },
	// title : "BASIS工程师反馈",
	// items : biddata
	// });
	// }
	// return this.formpanel_BASISEngineerFeedback_Input;
	// },
	// remove by lee for BASIS工程师处理环节被用户去除 in 20091202 end
	getFormpanel_EBEngineerFeedback_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_EBEngineerFeedback_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_EBEngineerFeedback2_k",
					"panel_EBEngineerFeedback_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_EBEngineerFeedback_Input");
		}
		biddata = da.split(data);			
		if (this.getFormButtons.length != 0) {
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
				title : "<font color=red>EB工程师反馈</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
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
				title : "<font color=red>EB工程师反馈</font>",
				items : biddata
			});
		}
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
		this.model = "nr_EBEngineerFeedback2_k";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nr_EBEngineerFeedback2_k",
				this);
		this.buttons = new Array();
		if (this.readOnly != 1) {
			this.buttons = this.mybuttons;
		}

		this.getFormpanel_ERP_NormalNeed1_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		this.formname.push("panel_ERP_NormalNeed5_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_Input);
		this.getFormpanel_ErpEngineerFeedback_Input();
		this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		this.formname.push("panel_ErpEngineerFeedback_f_Input");
		temp.push(this.formpanel_ErpEngineerFeedback_Input);
		// remove by lee for BASIS工程师处理环节被用户去除 in 20091202 begin
		// this.getFormpanel_BASISEngineerFeedback_Input();
		// this.pa.push(this.formpanel_BASISEngineerFeedback_Input);
		// this.formname.push("panel_BASISEngineerFeedback_Input");
		// temp.push(this.formpanel_BASISEngineerFeedback_Input);
		// remove by lee for BASIS工程师处理环节被用户去除 in 20091202 end
		this.getFormpanel_EBEngineerFeedback_Input();
		this.pa.push(this.formpanel_EBEngineerFeedback_Input);
		this.formname.push("panel_EBEngineerFeedback_Input");
		temp.push(this.formpanel_EBEngineerFeedback_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		this.items = items;
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		// remove by lee for 除掉默认同意 in 20090806 begin
		// var tempErpId =
		// Ext.getCmp('itil_sci_EBEngineerFeedback$id').getValue();
		// if(tempErpId==''){
		// Ext.getCmp('itil_sci_EBEngineerFeedback$feekback').setValue('同意');
		// Ext.getCmp('itil_sci_EBEngineerFeedback$otherInfo').setValue('同意');
		// }
		// remove by lee for 除掉默认同意 in 20090806 end
		PageTemplates.superclass.initComponent.call(this);
	}
})