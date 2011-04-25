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

	saveAndSubmit : function() {
		var formParam = Ext.getCmp('panel_SpecialRequire3_m_Input').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		Ext.Ajax.request({
			url : webContext+ '/SRAction_saveSpecialRequire.action',
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				window.parent.auditContentWin.specialAudit();
				},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	addServiceItem : function() {
		var dataId = this.dataId;
		var taskId = this.taskId;
		// var reqClass = this.reqClass;
		var record = Ext.getCmp('gridpanel').getSelectionModel().getSelected();
		var records = Ext.getCmp('gridpanel').getSelectionModel()
				.getSelections();
		var store = this.storeservice;
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要添加的行!");
			return;
		}
		if (records.length > 1) {
			Ext.MessageBox.alert('提示', '最多选择一条信息，进行添加!');
		}
		var newdataId = record.get('ServiceItem$id');
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveServiceItemReShip.action?'
					+ 'reqId=' + dataId + '&newdataId=' + newdataId,

			success : function(response, options) {
				Ext.MessageBox.alert("提示", "添加成功", function() {
					Ext.getCmp('winid').close();
					window.location = webContext
							+ "/user/require/specialRequire1/comfirmByITManager.jsp?"
							+ "dataId=" + dataId 
							+ "&tabNumer="+ 'serviceItemBasePanel'
							+ "taskId="+ taskId;
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("添加失败");
			}
		}, this);
	},
	reset : function() {
		Ext.getCmp('searchPanel').form.reset();
	},
	createSearch2 : function(btnName, link, imageUrl, scope) {
		var param = Ext.getCmp('searchPanel').form.getValues(false);
		param.methodCall = 'query';
		param.start = 1;
		this.formValue = param;
		this.pageBarservice.formValue = this.formValue;
		this.storeservice.removeAll();
		this.storeservice.load({
			params : param
		});

		return null;
	},
	saveServiceItem : function() {
		var formParam = Ext.getCmp('serviceItemBasePanel3').form
				.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curdataId = this.dataId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveServiceItemForRequire.action?'
					+ '&reqId=' + this.dataId,
			params : vp,

			success : function(response, options) {
				Ext.MessageBox.alert("保存成功");
				window.location = webContext
						+ "/user/require/specialRequire1/comfirmByITManager.jsp?"
						+ "dataId=" + curdataId + "&tabNumer="
						+ 'serviceItemBasePanel';
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);
	},
	selectServiceItem : function() {
		var createPanel2 = this.getGridPanel();
		this.fp = this.getSearchForm();
		var win1 = new Ext.Window({
			id : 'winid',
			title : '服务项',
			height : 400,
			width : 700,
			constrain : false,
			constrainHeader : true,
			modal : true,
			resizable : false,
			draggable : true,
			layout : 'border',
			scope : this,
			items : [this.fp, createPanel2],
			buttons : [{
				text : '关闭',
				handler : function() {
					win1.close();
				},
				listeners : {
					'beforeclose' : function(p) {
						return true;
					}
				},
				scope : this
			}]
		});
		win1.show();
	},
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("SerivceItemListGrid");
		var biddata = da.split(data);
		this.panel = new Ext.form.FormPanel({
			id : 'searchPanel',
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 300,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			items : biddata
		});
		return this.panel;
	},
	getGridPanel : function() {
		var da = new DataAction();
		this.fp = this.getSearchForm();
		var obj = da.getListPanelElementsForHead("SerivceItemListGrid");
		// 前面的复选框
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// 循环出所有的行，然后增加属性修饰
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// 给每一行增加修饰
			var columnItem = "";
			columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			}

			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.storeservice = da
				.getPagePanelJsonStore("SerivceItemListGrid", obj);
		this.storeservice.paramNames.sort = "orderBy";
		this.storeservice.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBarservice = new Ext.PagingToolbarExt({
			pageSize : 10,
			store : this.storeservice,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBarservice.formValue = this.formValue;
		this.gridpanel = new Ext.grid.EditorGridPanel({
			id : "gridpanel",
			region : "center",
			rowspan : 1,
			store : this.storeservice,
			width : 100,
			frame : true,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			height : 320,
			tbar : [{}, {
				text : '查 询',
				pressed : true,
				handler : this.createSearch2,
				scope : this,
				iconCls : 'search'
			}, '|', {
				text : '重 置',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			}, '|', {
				text : '添 加',
				pressed : true,
				handler : this.addServiceItem,
				scope : this,
				iconCls : 'add'
			}, '   '],
			bbar : this.pageBarservice

		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 1
		};

		this.pageBarservice.formValue = param;

		this.storeservice.removeAll();
		this.storeservice.load({
			params : param
		});

		return this.gridpanel;

	},
	getTabpanel : function(tab, tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			enableTabScroll : true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : true,
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab,
			bbar : [new Ext.Toolbar.TextItem('<font color=red>提示：</font><font color=blue>以上蓝色分页为已审批只读信息，红色分页为需填写信息部分，请在填写信息后先保存信息再提交申请!</font>')]
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

	

	getFormpanel_SpecialRequire3_m_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire3_m_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_m_comfirmByITManager",
					"panel_SpecialRequire3_m_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3_m_Input");

		}
		for (i = 0; i < data.length; i++) {
			if (data[i].id == "SpecialRequirement$requireId") {
				data[i].readOnly = true;
				
			}
		}
		
		biddata = da.split(data);
		
		if (this.getFormButtons.length != 0) {
			this.formpanel_SpecialRequire3_m_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_m_Input',
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
				title : "申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SpecialRequire3_m_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_m_Input',
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
				title : "申请",//"解决方案设计开发服务",
				items : biddata
			});
		}
		return this.formpanel_SpecialRequire3_m_Input;
	},
	getFormserviceItemBasePanel : function() {
		var sra = new SRAction();
		var siId = sra.getReqServiceItemId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"serviceItemBasePanel", this);

		if (siId != '0') {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					siId);// 这是要随时变得

		} else {
			data = da.getPanelElementsForAdd("serviceItemBasePanel");

		}

		for (i = 0; i < data.length; i++) {
			if (data[i].id == 'ServiceItem$serviceItemCode') {
				data[i].readOnly = true;
				data[i].emptyText = '自动生成';
			}
			
		}
		
		biddata = da.split(data);
		
		var item = new Array();
		// item=biddata;
		// item.push(this.caPanel);

		if (this.getFormButtons.length != 0) {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel3',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'

				},
				style : 'padding:10px 0 0px 0px',
				layoutConfig : {
					columns : 4
				},
				title : "<font color=red>服务项表单基础面板</font>",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel3',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px',
					autoHeight : true
				},
				style : 'padding:10px 0 0px 0px',
				layoutConfig : {
					columns :4
				},
				title : "<font color=red>服务项表单基础面板</font>",
				items : biddata
			});
		}
		this.formserviceItemBasePanel2 = new Ext.Panel({
			id : 'serviceItemBasePanel',
			y : 200,
			anchor : '0 -200',
			layout : 'column',
			height : 510,
			width : 1200,
			title : "<font color=red>服务项表单基础面板</font>",
			items : [this.formserviceItemBasePanel]
		});
		this.formserviceItemBasePanel.on("saveServiceItem",
				this.saveServiceItem, this);
		return this.formserviceItemBasePanel2;
	},

	

	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
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
		this.model = "nsr1_m_comfirmByITManager";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nsr1_m_comfirmByITManager",
				this);
		if (this.mybuttons != "") {
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
		} else {
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

		this.getFormpanel_SpecialRequire3_m_Input();
		this.pa.push(this.formpanel_SpecialRequire3_m_Input);
		this.formname.push("panel_SpecialRequire3_m_Input");
		temp.push(this.formpanel_SpecialRequire3_m_Input);
//add by lee for remove serviceItem operate in 20090727 begin
//		this.getFormserviceItemBasePanel();
//		this.pa.push(this.formserviceItemBasePanel2);
//		this.formname.push("serviceItemBasePanel");
//		temp.push(this.formserviceItemBasePanel2);
//add by lee for remove serviceItem operate in 20090727 end
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.on("saveServiceItem", this.saveServiceItem, this);
		this.on("selectServiceItem", this.selectServiceItem, this)
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})