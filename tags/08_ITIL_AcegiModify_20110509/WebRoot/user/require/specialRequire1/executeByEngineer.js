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
	saveForGrid : function() {

		var reqId = this.dataId;
		//var reqCls = this.reqClass;
		//var removedIds= this.remIds;
		var store = Ext.getCmp('panel_SRTestReport').store;
		var product = "";
		store.each(function(record) {
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
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
//add by musicbear for 添加提交按钮	in 2009 11 5 start	
	saveAndSubmit : function() {
		var reqId = this.dataId;		
		var store = Ext.getCmp('panel_SRTestReport').store;
		var product = "";
		store.each(function(record) {
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";			
			}
		})

		product = unicode(product);		
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveTestReport.action',
			params : {
				product : product,
				reqId : reqId

			},
			success : function(response, options) {
				Ext.MessageBox.alert("提示", "提交成功", function() { //modify by musicbear for 保存成功改为：提交成功
					store.reload();
					store.removeAll();
					window.parent.auditContentWin.specialAudit();
					});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("提交失败");
			}

		})
	},
//add by musicbear for 添加提交按钮	in 2009 11 5 end
	
	removePlan : function() {
		//this.remIds= this.removedIds;
		var record = Ext.getCmp('panel_SRTestReport').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('panel_SRTestReport').getSelectionModel()
				.getSelections();
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
							remIds += records[i].get("SRTestReport$id") + ",";
							//alert(this.remIds);
						}
						Ext.Ajax.request({
							url : webContext
									+ '/SRAction_removeTestReport.action',
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

	getFormpanel_SpecialRequire3_Input : function() {
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire3_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_confirmByClientManager",
					"panel_SpecialRequire3_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3_Input");

		}
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
		if (this.getFormButtons.length != 0) {
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
				title : "用户申请",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SpecialRequire3_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequire3_Input',
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
				title : "用户申请",
				items : biddata
			});
		}
		return this.formpanel_SpecialRequire3_Input;
	},
	getFormserviceItemBasePanel : function() {
		var sra = new SRAction();
		var siId = sra.getReqServiceItemId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("serviceItemBasePanel", this);

		if (siId != '0') {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",siId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("serviceItemBasePanel");
		}
		for (i = 0; i < data.length; i++) {
			data[i].readOnly = true;
			data[i].hideTrigger = true;
		}
		biddata = da.split(data);

		var item = new Array();
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
					columns : 4
				},
				title : "关联服务项",
				items : biddata
			});
		this.formserviceItemBasePanel2 = new Ext.Panel({
			id : 'serviceItemBasePanel',
			y : 200,
			anchor : '0 -200',
			layout : 'column',
			height : 1000,
			width : 1200,
			title : "关联服务项",
			items : [this.formserviceItemBasePanel]
			//items : [this.formserviceItemBasePanel,this.modifyGridInfo()]
		});
		return this.formserviceItemBasePanel2;
	},
	 modifyGridInfo:function(){
   
   		var sm = new Ext.grid.CheckboxSelectionModel();// 复选框
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											{header:'变更编号',dataIndex:'modifyNo',sortable:true},
											{header:'变更名称',dataIndex:'name',sortable:true},
											{header:'变更描述',dataIndex:'descn',sortable:true},
											{header:'变更原因',dataIndex:'reason',sortable:true},
											{header:'变更提交人',dataIndex:'applyUser',sortable:true},
											{header:'变更提交日期',dataIndex:'applyDate',sortable:true}
										
											]);
		this.storeChild=new Ext.data.JsonStore({
				url : webContext
						+'/ciRelationShip_getModifyInfoListByProblemId.action?objectId='+this.dataId+"&modifyType=specialRequire",//?&configItemId='+this.configItemId+"&itemFlag="+itemFlag,
				fields : ['id', 'modifyNo','name','descn','reason','applyUser','applyDate'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
				
		});
		this.storeChild.paramNames.sort = "orderBy";
		this.storeChild.paramNames.dir = "orderType";
		this.pageBar = new Ext.PagingToolbarExt({
			pageSize :10,// 使用的是系统默认值
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			id:"modifyGrid",
			title:"变更信息   <font color=black style='font-weight:lighter'  face=楷体_GB2312>【双击查看变更信息】</font>",
			store : this.storeChild,
			collapsible : true,
			cm : cm,
			sm : sm,
			height:200,
			width:800,
			frame:true,
			border:true,
			trackMouseOver : false,
			loadMask : true,
			y : 40,
			anchor : '0 -38',
			bbar : this.pageBar			
			});
		var param = {
			'start' : 1
			};
		this.pageBar.formValue = param;
		this.storeChild.load({
			params : param
		});
		return this.grid; 
   
   
   },
   
   lookModifyInfo:function(){
   		/*var record = Ext.getCmp("modifyGrid").getSelectionModel()
							.getSelected();
	    var records = Ext.getCmp("modifyGrid").getSelectionModel()
							.getSelections();
        if (!record) {
	         Ext.Msg.alert("提示", "请先选择查看的记录!");
	         return;
           }
         if (records.length == 0) {
	      Ext.MessageBox.alert('警告', '只能选择一条信息!');
	      return;
        }
        var modifyId = record.get("id");
        
        var url = webContext+ '/user/configNewModify/ciModifyFirstLook.jsp';

					this.configItemModifyWindow = new Ext.Window({
						//									
						title : '变更信息窗口',
						modal : true,
						height : 500,
						width : 800,
						resizable : true,
						id : "modifyInfoWindow",
						draggable : true,
						buttons : [{
							buttonAlign : 'center',
							text : '关闭',
							pressed : true,
							handler : function() {
								Ext.getCmp("modifyInfoWindow").close();
								// Ext.getCmp("tree").root.reload();
							}
						}],
						autoLoad : {
							// 装载审批有关信息，与流程有关,此处可把任务名称传递过去，auditInfo.jsp根据任务名称决定是否需要修改审批内容
							url : webContext + "/tabFrame.jsp?url=" + url+"?dataId="+modifyId,// +"****currentItemId="+currentItemId+"****flag=child",
							text : "页面正在加载中......",
							method : 'post',
							scope : this
						},
						viewConfig : {
							autoFill : true,
							forceFit : true
						},
						layout : 'fit',
						buttonAlign : 'center',
						items : [{
							html : "正在加载页面内容......"
						}]

					});
					this.configItemModifyWindow.show();	*/
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
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SRServiceProviderInfo_Input", this.dataId);// 这是要随时变得
		} else {
			data = da.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");
		}
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
				title : "技术总监审批面板",
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
				title : "技术总监审批面板",
				items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},
	getGridpanel_SRExpendPlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRExpendPlan_list");

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
		this.store = da.getPagePanelJsonStore("panel_SRExpendPlan_list", obj);

		this.cm.defaultSortable = true;
		// var viewConfig = Ext.apply({
		// forceFit : false
		// }, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRExpendPlan_list = new Ext.grid.EditorGridPanel({
			id : 'panel_SRExpendPlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "支出计划",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true

				// [getGridButtons]
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
		return this.gridpanel_SRExpendPlan_list;
	},

	getGridpanel_SRIncomePlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRIncomePlan_list");
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
		// var viewConfig = Ext.apply({
		// forceFit : true
		// }, this.gridViewConfig);
		this.formValue = '';
		this.gridpanel_SRIncomePlan_list = new Ext.grid.EditorGridPanel({
			id : 'panel_SRIncomePlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "收款计划",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true

		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ this.dataId + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.gridpanel_SRIncomePlan_list;
	},

	getFormpanel_SRAnalyse : function() {
		var sra = new SRAction();
		var praId = sra.getProjectRequireAnalyseId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse",
				this);
		if (praId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse",
					praId);// 这是要随时变得
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", "");
		}
		for (var i = 0; i < data.length; i++) {
			data[i].readOnly = true;
			data[i].hideTrigger = true;
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRAnalyse = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse',
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
		} else {
			this.formpanel_SRAnalyse = new Ext.form.FormPanel({
				id : 'panel_SRAnalyse',
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
		return this.formpanel_SRAnalyse;
	},

	getFormpanel_SRProjectPlan : function() {
		var sra = new SRAction();
		var ppId = sra.getProjectPlanId(this.dataId);
		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRProjectPlan", this);
		if (ppId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan",
					ppId);// 这是要随时变得
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan",
					"");
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "项目计划",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_SRProjectPlan = new Ext.form.FormPanel({
				id : 'panel_SRProjectPlan',
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
				title : "项目计划",
				items : biddata
			});
		}
		return this.formpanel_SRProjectPlan;
	},
	getFormpanel_SRTestReport : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRTestReport");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_SRTestReport",
				this);
//add by lee for add attachmentfile in 20090806 begin 添加附件
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
							'com.zsgj.info.appframework.metadata.entity.SystemFile');
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("获取附件失败");
					}
				})
			}
		});
//		getGridButtons.push(fileButton);
//add by lee for add attachmentfile in 20090806 begin
		if(this.readOnly==1){
			getGridButtons = new Array();
			getGridButtons.push(fileButton);
		}
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
			//alert("aa"+editor.id);
			if (editor.xtype == 'datefield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					renderer : renderer,
					editor : editor
				}
			} else if (editor.xtype == 'textfield') {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					//width:300,
					editor : new Ext.form.TextField({
						fieldLabel : '测试结果',
						xtype : 'textfield',
						id : 'flm_ProjectTestReport$description',
						name : 'flm_ProjectTestReport$description',
						style : '',
						width : 500,
						value : '',
						readOnly : false,
						allowBlank : true,
						validator : '',
						vtype : ''
					})

				}
			} else {
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle,
					width : 300,
					editor : editor
				}
			}
			columns[i + 1] = columnItem;
			if (editor.id == 'flm_ProjectTestReport$description') {
				width : 500
			}
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRTestReport", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.formpanel_SRTestReport = new Ext.grid.EditorGridPanel({
			id : 'panel_SRTestReport',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title : "<font color=red>项目测试列表</font>",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			height : 600,
			width : 600,// this.width - 15,
			frame : true,
			tbar : [getGridButtons],
			bbar:[this.testFilePanel]
		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ this.dataId + '\"' + '}';// 这是要随时变得
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
			
//						+
//								'?clazzName=com.zsgj.itil.config.extlist.entity.NotesIDFile&dataId='
//						+ this.dataId+"&columnName=attachment&columnid=7887&hiddenId=NotesIDFile010attachment";
//					var value=da.ajaxGetData(url);
//					document.getElementById("NotesIDFile010attachment").innerHTML=value.file;
//				  Ext.getCmp("AccountApplyMainTable$applyUserCombo")
//							           .initComponent();
//							             var idFile= Ext.getCmp("NotesIDFile$fileNameCombo").getValue();
//							            if(idFile!=null&&idFile!=''){
//							           	Ext.getCmp("idFile").expand(true);
//							            Ext.getDom('file').checked=true;
//							           }
			
			
		}
		return this.formpanel_SRTestReport;
	},
	getTestFilePanel : function(){
		var curDataId = this.dataId;
		this.testFilePanel = new Ext.Panel({
			layout:'table',
			width:600,
			layoutConfig:{columns:4},
			items:[
			{fieldLabel:'附件',
				xtype:'button',
				text:'<font color=red>上传测试报告</font>',
				width:600,
				scope:this,
				handler:function(){
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
							'com.zsgj.info.appframework.metadata.entity.SystemFile','TestReportFile');
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("获取附件失败");
					}
				})
				}
			},
			{id:"TestReportFile",width:600,border : true,html:'',cls : 'common-text',style : 'width:600;text-align:left'}
			]
		});
		return this.testFilePanel;
	},
	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
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
		this.model = "nsr1_executeByEngineer";
		var buttonUtil = new ButtonUtil();
//		this.mybuttons = buttonUtil.getButtonForModel("nsr1_executeByEngineer",this);
				this.mybuttons = buttonUtil.getButtonForModel(
				"nsr1_executeByEngineer", this);	
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
		this.getTestFilePanel();		
		this.getFormpanel_SpecialRequire3_Input();
		this.pa.push(this.formpanel_SpecialRequire3_Input);
		this.formname.push("panel_SpecialRequire3_Input");
		temp.push(this.formpanel_SpecialRequire3_Input);

		this.getFormpanel_SRServiceProviderInfo_Input();
		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);
		this.formname.push("panel_SRServiceProviderInfo_Input");
		temp.push(this.formpanel_SRServiceProviderInfo_Input);

		this.getFormpanel_SRAnalyse();
		this.pa.push(this.formpanel_SRAnalyse);
		this.formname.push("panel_SRAnalyse");
		temp.push(this.formpanel_SRAnalyse);
		
		this.getFormserviceItemBasePanel();
		this.pa.push(this.formserviceItemBasePanel2);
		this.formname.push("serviceItemBasePanel");
		temp.push(this.formserviceItemBasePanel2);
//		this.getGridpanel_SRExpendPlan_list();
//		this.gd.push(this.gridpanel_SRExpendPlan_list);
//		this.gridname.push("panel_SRExpendPlan_list");
//		temp.push(this.gridpanel_SRExpendPlan_list);
//		this.getGridpanel_SRIncomePlan_list();
//		this.gd.push(this.gridpanel_SRIncomePlan_list);
//		this.gridname.push("panel_SRIncomePlan_list");
//		temp.push(this.gridpanel_SRIncomePlan_list);

//		this.projectRenderGrid = new projectRenderListPanel({
//			dataId : this.dataId,
//			rootId : ppId
//		});
//		this.projectRenderGrid.selectAll();
//
//		this.pa.push(this.projectRenderGrid);
//		this.formname.push("projectRenderListPanel");
//		temp.push(this.projectRenderGrid);
		this.getFormpanel_SRTestReport();
		this.pa.push(this.formpanel_SRTestReport);
		this.formname.push("panel_SRTestReport");
		temp.push(this.formpanel_SRTestReport);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("removePlan", this.removePlan, this);
		this.on("saveForGrid", this.saveForGrid, this);
		this.on("saveAndSubmit",this.saveAndSubmit,this);
		PageTemplates.superclass.initComponent.call(this);
//add by musicbear for 上传的附件在此节点显示 in 2009 11 10 start		
		var da=new DataAction();
		var url=webContext+'/SRAction_getTestReportFileList.action?columnid=8016&dataId='+this.dataId;
		var value=da.ajaxGetData(url);
		Ext.getCmp("TestReportFile").html=value.file;
//add by musicbear for 上传的附件在此节点显示 in 2009 11 10 end
	}
})