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
	saveForGrid : function(){

		var reqId = this.dataId;
	   //var reqCls = this.reqClass;
					//var removedIds= this.remIds;
					var store = Ext.getCmp('panel_SRTestReport').store;
					var product = "";
						store.each(function(record) {
							if(record.dirty){
								product += Ext.encode(record.data)+",";
								//alert(product);
						}
						})

						product = unicode(product);
						//alert(product);
						Ext.Ajax.request({
									url : webContext + '/SRAction_saveTestReport.action',
									params : {
										product : product,
										reqId : reqId
										
									},
									success : function(response, options) {
										Ext.MessageBox.alert("提示", "保存成功", function() {
											store.reload();
											store.removeAll();
											//removedIds="";
											});
					
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("保存失败");
									}
								
							
						})
	},
	removePlan : function(){
				//this.remIds= this.removedIds;
				var record = Ext.getCmp('panel_SRTestReport').getSelectionModel().getSelected();
				var records = Ext.getCmp('panel_SRTestReport').getSelectionModel().getSelections();
				var store = Ext.getCmp('panel_SRTestReport').store;
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要删除的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
				} else {
					Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(btn) {
						if (btn == 'yes') {
							var remIds = "";
							if (records) {
								for (var i = 0; i < records.length; i++) {
									this.store.remove(records[i]);
									remIds += records[i].get("SRTestReport$id")+ ",";
								}
									Ext.Ajax.request({
											url : webContext + '/SRAction_removeTestReport.action',
											params : {
												removedIds : remIds
											},
											success : function(response, options) {
												Ext.MessageBox.alert("提示", "删除成功", function() {
													store.reload();
													store.removeAll();
													});
											},
											failure : function(response, options) {
												Ext.MessageBox.alert("删除失败");
											}
								})
							}
						}
					}, this)
				}
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

	getFormpanel_SpecialRequire4_m_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire4_m_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_m_executeByEngineer",
					"panel_SpecialRequire4_m_Input", this.dataId);// 这是要随时变得
					for (i = 0; i < data.length; i++) {
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].id = data[i].id + 8;// 改变Combobox的id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
				data[i].readOnly = true;
			}
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire4_m_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SpecialRequire4_m_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire4_m_Input',
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
				title : "小型配置或开发需求",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SpecialRequire4_m_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire4_m_Input',
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
				title : "小型配置或开发需求",
				items : biddata
			});
		}
		return this.formpanel_SpecialRequire4_m_Input;
	},
	getFormpanel_SRServiceProviderInfo_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRServiceProviderInfo_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_m_executeByEngineer",
					"panel_SRServiceProviderInfo_Input", this.dataId);// 这是要随时变得
					for (i = 0; i < data.length; i++) {
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].id = data[i].id + 8;// 改变Combobox的id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
				data[i].readOnly = true;
			}
			biddata = da.split(data);
		} else {
			data = da
					.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRServiceProviderInfo_Input = new Ext.form.FormPanel({
				id : 'panel_SRServiceProviderInfo_Input',
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
				title : "业务类及交付团队",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRServiceProviderInfo_Input = new Ext.form.FormPanel({
				id : 'panel_SRServiceProviderInfo_Input',
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
				title : "业务类及交付团队",
				items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},
	getFormpanel_SRAnalyse2 : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse2",
				this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_m_executeByEngineer",
					"panel_SRAnalyse2", this.dataId);// 这是要随时变得
					for (i = 0; i < data.length; i++) {
				if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
					data[i].id = data[i].id + 8;// 改变Combobox的id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				if (data[i].xtype == "panel") {
				var dd = Ext.encode(data[i]).replace(/display:/g,"display:none").replace("\"disabled\":false","\"disabled\":true");
				data[i] = Ext.decode(dd);
			}
				data[i].readOnly = true;
			}
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRAnalyse2");
			biddata = da.split(data);
		}
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRAnalyse2 = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse2',
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
				title : "项目需求分析",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRAnalyse2 = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse2',
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
				title : "项目需求分析",
				items : biddata
			});
		}
		return this.formpanel_SRAnalyse2;
	},
	getGridpanel_SRTestReport : function() {
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",
				this);
//add by lee for add attachmentfile in 20090806 begin
		var curDataId = this.dataId;
		var fileButton =  new Ext.Button({
			text : '测试附件',
			pressed : true,
			iconCls : 'export',
			handler : function() {
				Ext.Ajax.request({
					url : webContext + '/SRAction_saveTestReportFile.action',
					params : {
						dataId : curDataId
					},
					success : function(response, options) {
						var responseArray = Ext.util.JSON.decode(response.responseText);
						var attachmentFlag = responseArray.file;
						var ud = new UpDownLoadFile();
						ud.getUpDownLoadFileSu(attachmentFlag, '8016',
							'com.digitalchina.info.appframework.metadata.entity.SystemFile');
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("获取附件失败");
					}
				})
			}
		});
		getGridButtons.push(fileButton);
		if(this.readOnly==1){
			getGridButtons = new Array();
			getGridButtons.push(fileButton);
		}
//add by lee for add attachmentfile in 20090806 begin
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
		this.gridpanel_SRTestReport = new Ext.grid.EditorGridPanel({
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "<font color=red>用户测试报告</font>",
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
		return this.gridpanel_SRTestReport;
	},
	// getFormpanel_SRProjectPlan: function() {
	//      var da = new DataAction();
	//		  var data = null;
	//			// 判断是新增还是修改
	//			var biddata = "";
	//			
	//			  var buttonUtil = new ButtonUtil();
	//			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRProjectPlan",this);
	//			if (this.dataId != '0') {
	//				data = da.getPanelElementsForEdit("sr2_m_executeByEngineer", "panel_SRProjectPlan", this.dataId);// 这是要随时变得
	//				biddata = da.split(data);
	//			} else {
	//				data = da.getPanelElementsForAdd("panel_SRProjectPlan");
	//				biddata = da.split(data);
	//			}
	//			if(this.getFormButtons.length!=0){
	//		this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
	//			id : 'panel_SRProjectPlan',
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
	//			title : "个性化需求项目计划实体(N)",
	//			items : biddata,
	//			buttons : this.getFormButtons
	//		});
	//		}else{
	//			this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
	//			id : 'panel_SRProjectPlan',
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
	//			title : "个性化需求项目计划实体(N)",
	//			items : biddata
	//			});
	//		}
	//		return this.formpanel_SRProjectPlan;
	//	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {

		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});
		var sra = new SRAction();
		var ppId = sra.getRootProjectPlanId(this.dataId);

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
		this.model = "sr2_m_executeByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("sr2_m_executeByEngineer",
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

		this.getFormpanel_SpecialRequire4_m_Input();
		this.pa.push(this.formpanel_SpecialRequire4_m_Input);
		this.formname.push("panel_SpecialRequire4_m_Input");
		temp.push(this.formpanel_SpecialRequire4_m_Input);
		this.getFormpanel_SRServiceProviderInfo_Input();
		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
		this.formname.push("panel_SRServiceProviderInfo_Input");
		temp.push(this.formpanel_SRServiceProviderInfo_Input);
		this.getFormpanel_SRAnalyse2();
		this.pa.push(this.formpanel_SRAnalyse2);
		this.formname.push("panel_SRAnalyse2");
		temp.push(this.formpanel_SRAnalyse2);
		//		       this.getFormpanel_SRProjectPlan();
		//		       this.pa.push(this.formpanel_SRProjectPlan);
		//		       this.formname.push("panel_SRProjectPlan");
		//		       temp.push(this.formpanel_SRProjectPlan);
		this.projectRenderGrid = new projectRenderListPanel({
			dataId : this.dataId,
			rootId : ppId
		});
		this.projectRenderGrid.selectAll();

		this.pa.push(this.projectRenderGrid);
		this.formname.push("projectRenderListPanel");
		temp.push(this.projectRenderGrid);
		this.getGridpanel_SRTestReport();
		this.gd.push(this.gridpanel_SRTestReport);
		this.gridname.push("panel_SRTestReport");
		temp.push(this.gridpanel_SRTestReport);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("removePlan",this.removePlan,this);
		this.on("saveForGrid",this.saveForGrid,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})