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

	//保存并提交方法
	saveAndSubmit : function() {
		var curDataId = this.dataId;
		var formParam = Ext.getCmp('panel_SRprojectContracts').form
				.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		Ext.Ajax.request({
			url : webContext
					+ '/SRAction_saveRequirementContractForReq.action?'
					+ '&reqId=' + this.dataId,
			params : vp,

			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				this.spid = responseArray.contractId;
				var spCode = responseArray.contractCode;
				Ext.getCmp('SRprojectContracts$id').setValue(this.spid);
				Ext.getCmp('SRprojectContracts$contractCode').setValue(spCode);
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

	getFormpanel_SpecialRequireDevConfirm_m_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequireDevConfirm_m_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_m_confirmByITS",
					"panel_SpecialRequireDevConfirm_m_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequireDevConfirm_m_Input");
		}
		biddata = da.splitForReadOnly(data);
			this.formpanel_SpecialRequireDevConfirm_m_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequireDevConfirm_m_Input',
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
				title : "开发类需求审批面板",
				items : biddata
			});
		return this.formpanel_SpecialRequireDevConfirm_m_Input;
	},
	getFormpanel_srInfo_input1 : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_srInfo_input1",
				this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_m_confirmByITS",
					"panel_srInfo_input1", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input1");
		}
		biddata = da.splitForReadOnly(data);
			this.formpanel_srInfo_input1 = new Ext.form.FormPanel({
				id : 'panel_srInfo_input1',
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
		return this.formpanel_srInfo_input1;
	},
	
		getFormpanel_SRprojectContracts : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_m_confirmByITS",
					"panel_SRprojectContracts", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SRprojectContracts");
		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(data[i].id=='SRprojectContracts$contractCode'){
				data[i].readOnly = true;
				data[i].emptyText = '自动生成';
			}
			if(i==8){
				modifyData.push(this.getGridpanel_SRIncomePlan_list());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);
		var formpanel_SRprojectContracts = new Ext.form.FormPanel({
			id : 'panel_SRprojectContracts',
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
			title : "需求合同",
			items : biddata
		});
		return formpanel_SRprojectContracts;
	},
	
	getGridpanel_SRIncomePlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRIncomePlan_list");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel(
				"panel_SRIncomePlan_list", this);

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
		this.store = da.getPagePanelJsonStore("panel_SRIncomePlan_list", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRIncomePlan_list", obj);
		this.cm.defaultSortable = true;
//		var viewConfig = Ext.apply({
//			forceFit : true
//		}, this.gridViewConfig);
		var addIncomePlanButton = new Ext.Button({
			text : '添加',
			pressed : true,
			iconCls : 'add',
			handler : function() {
				var store = Ext.getCmp('panel_SRIncomePlan_list').store;
				if (store.recordType) {
					var rec = new store.recordType({
						newRecord : true
					});
					rec.fields.each(function(f) {
						rec.data[f.name] = f.defaultValue || null;
					});
					rec.commit();
					store.add(rec);
					return rec;
				}
				return false;
			}
		});
		var removeIncomePlanButton = new Ext.Button({
			text : '删除',
			pressed : true,
			iconCls : 'remove',
			handler : function() {
				var record = Ext.getCmp('panel_SRIncomePlan_list')
						.getSelectionModel().getSelected();
				var records = Ext.getCmp('panel_SRIncomePlan_list')
						.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要删除的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
				} else {
					Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(btn) {
						if (btn == 'yes') {
							if (records) {
								for (var i = 0; i < records.length; i++) {
									Ext.getCmp('panel_SRIncomePlan_list').store
											.remove(records[i]);
									var id = records[i].get("SRIncomePlan$id");
									Ext.Ajax.request({
										url : webContext
												+ '/extjs/pageData?method=removeGridColumn',
										params : {
											panel : 'panel_SRIncomePlan_list',
											id : id
										},
										timeout : 100000,
										success : function(response) {
											var r = Ext
													.decode(response.responseText);
											if (!r.success) {
												Ext.Msg.alert("提示信息", "数据删除失败",
														function() {

														});
											}
										},
										scope : this

									});
								}
							}
						}
					}, this)
				}
			}
		});
		var saveIncomePlanButton = new Ext.Button({
			text : '保存',
			pressed : true,
			iconCls : 'save',
			handler : function() {
				var store = Ext.getCmp('panel_SRIncomePlan_list').store;
				var info = "";
				store.each(function(record) {
					if (record.dirty) {
						info += Ext.encode(record.data) + ",";
					}
				})
				info = unicode(info);
				Ext.Ajax.request({
					url : webContext + '/SRAction_saveIncomePlan.action',
					params : {
						info : info,
						reqId : reqId
					},
					success : function(response, options) {
						Ext.MessageBox.alert("提示", "保存成功", function() {
							store.reload();
							});

					},
					failure : function(response, options) {
						Ext.MessageBox.alert("保存失败");
					}

				})
			}
		});
		
		var gridpanel_SRIncomePlan_list = new Ext.grid.EditorGridPanel({
			fieldLabel : '收款计划',
			id : 'panel_SRIncomePlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			//title : "个性化需求收款计划列表",
			trackMouseOver : false,
//			loadMask : true,
			clicksToEdit : 2,
//			collapsible : true,
			autoScroll : true,
			loadMask : true,
			height : 200,
			width : 9999,// this.width - 15,
			frame : true,
			tbar : [addIncomePlanButton, removeIncomePlanButton,
					saveIncomePlanButton]
			//[getGridButtons]
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
		return gridpanel_SRIncomePlan_list;
	},
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});
		
		var items = new Array();
		var temp = new Array();
		this.model = "devsr_m_confirmByITS";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("devsr_m_confirmByITS",this);
		this.buttons = this.mybuttons;

		temp.push(this.getFormpanel_SpecialRequireDevConfirm_m_Input());
		temp.push(this.getFormpanel_srInfo_input1());
		temp.push(this.getFormpanel_SRprojectContracts());
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		//items.push(this.buttons);
		this.items = items;
		
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})