// 模板组件
PageModelComponentPanel = Ext.extend(Ext.Panel, {
	id : "PageModelComponentPanel",
	title : "PageModel",
	header : false,
	closable : true,
	frame : true,
	layout : 'column',
	autoScroll : true,
	border : false,
	height : 900,
	width : 'auto',
	layoutConfig : {
		columns : 1
	},
	worksubmit : function(){
		Ext.getCmp("saveBtn").setDisabled(true);
		Ext.getCmp("submitBtn").setDisabled(true);
		var info = Ext.getCmp('cataPanel').getForm().getValues();
		if (info.ServiceCatalogue$name == "" || info.ServiceCatalogue$customerType == "" || info.ServiceCatalogue$customer == ""
				|| info.ServiceCatalogue$beginDate == "" || info.ServiceCatalogue$endDate == "") {
			 Ext.Msg.alert("提示","缺少必须数据，请填写完全",function(){
			 		Ext.getCmp("saveBtn").setDisabled(false);
			 });
			return;
		};
		var d1 = new Date(info.ServiceCatalogue$beginDate.replace(/-/g, "/")); 
		var d2 = new Date(info.ServiceCatalogue$endDate.replace(/-/g, "/")); 
		  if (Date.parse(d1) - Date.parse(d2) == 0) { 
		    	 Ext.Msg.alert("提示","日期不能相等!",function(){
		    	 	Ext.getCmp("saveBtn").setDisabled(false);
		    	 }); 
		    	return; 
		  }  
		  if (Date.parse(d1) - Date.parse(d2) > 0) { 
		    Ext.Msg.alert("提示","截止有效期不能小于起始有效期!",function(){
		    	Ext.getCmp("saveBtn").setDisabled(false);
		    });
		    return; 
		  } 
		Ext.Ajax.request({
			url : webContext + '/sciRelationShip_saveRootSCIRelationShip.action',
			method : "POST",
			params : {
				id : info.ServiceCatalogue$id,
				sp : info.ServiceCatalogue$sp,
				customer : info.ServiceCatalogue$customer,
				customerType : info.ServiceCatalogue$customerType,
				name : info.ServiceCatalogue$name,
				descn : info.ServiceCatalogue$descn,
				validDate : info.ServiceCatalogue$validDate,
				beginDate : info.ServiceCatalogue$beginDate,
				endDate : info.ServiceCatalogue$endDate,
				oldCatalogueId:info.ServiceCatalogue$oldCatalogueId,
				flag:'modify',
				status : 4//info.ServiceCatalogue$status变更草稿状态4
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;//新的服务目录id
				Ext.Ajax.request({
					url :webContext+'/extjs/workflow?method=apply',
					params : {
						dataId : curId,
						model: this.model,
						bzparam : "{dataId :'"+ curId
							+ "',applyNum:'-----" 
							+ "',applyName:'" + Ext.getCmp('ServiceCatalogue$name').getValue()
							+"',oldDataId:'"+oldModifyDataId+"',applyId : '"+curId+"',applyType: 'cproject',applyTypeName: '服务目录变更审批流程',customer:'',alterFlag:'变更','workflowHistory':'com.zsgj.itil.service.entity.ServiceCatalogueAuditHis'}",						
						defname : "serviceCataManager1305882470217"
					},
					success : function(response, options) {
						var meg = Ext.decode(response.responseText);
							if (meg.id != undefined) {
								Ext.Msg.alert("提示", "启动工作流成功", function() {
									window.location = webContext
											+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
								});
							} else {
								Ext.Msg.alert("提示", "启动工作流失败", function() {
									alert(meg.Exception);
								});
							}		
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示","启动工作流失败");
					}
				}, this);
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			},
			scope : this
		});
		
		
		},
	getButtons : function() {
		return [
			{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '保存变更草稿',
			pressed:true,
			id:'saveBtn',
			scope : this,
			iconCls : 'save',
			handler : this.save
		}, {
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '删除变更草稿',
			pressed:true,
			scope : this,
			iconCls : 'delete',
			handler : this.removeAltersketch
		},{
			xtype : 'button',
			pressed:true,
			id : 'submitBtn',
			style : 'margin:4px 10px 4px 0',
			text : '提交',
			scope : this,
			iconCls:'submit',
			handler:function(){
				Ext.getCmp("PageModelComponentPanel").worksubmit();
			}
		  },
			{
			xtype : 'button',
			iconCls : 'back',
			pressed:true,
			style : 'margin:4px 10px 4px 0',
			handler : function() {
				window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
				//history.back();
			},
			text : '返回'
		}]

	},
	removeAltersketch:function(){
		
		Ext.Msg.confirm('提示', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {
					Ext.Ajax.request({
					url : webContext
							+ '/sciRelationShip_removeServiceCataSketch.action',//删除服务目录草稿
					method : "POST",
					params:{
						sketchId:dataId
					},
					success : function(response, options) {
						Ext.MessageBox.alert("提示","删除草稿成功",function(){
							window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
							
						});
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示","删除草稿失败");
					},
					scope : this
				});
			}
		});
	
	},
	save : function() {
			Ext.getCmp("saveBtn").setDisabled(true);
				var info = Ext.getCmp('cataPanel').getForm().getValues();
				if (info.ServiceCatalogue$name == "" || info.ServiceCatalogue$customerType == "" || info.ServiceCatalogue$customer == ""
						|| info.ServiceCatalogue$beginDate == "" || info.ServiceCatalogue$endDate == "") {
					 Ext.Msg.alert("提示","缺少必须数据，请填写完全",function(){
					 		Ext.getCmp("saveBtn").setDisabled(false);
					 });
					return;
				};
		//		alert(Ext.encode(info));
				var d1 = new Date(info.ServiceCatalogue$beginDate.replace(/-/g, "/")); 
				var d2 = new Date(info.ServiceCatalogue$endDate.replace(/-/g, "/")); 
				  if (Date.parse(d1) - Date.parse(d2) == 0) { 
				    	 Ext.Msg.alert("提示","日期不能相等!",function(){
				    	 	Ext.getCmp("saveBtn").setDisabled(false);
				    	 }); 
				    	return; 
				  }  
				  if (Date.parse(d1) - Date.parse(d2) > 0) { 
				    Ext.Msg.alert("提示","截止有效期不能小于起始有效期!",function(){
				    	Ext.getCmp("saveBtn").setDisabled(false);
				    });
				    return; 
				  } 
		//alert("主---"+info.ServiceCatalogue$id);
		Ext.Ajax.request({
			url : webContext
					+ '/sciRelationShip_saveRootSCIRelationShip.action',
			method : "POST",
			params : {
				id : info.ServiceCatalogue$id,
				//oldId:oldModifyDataId,
				sp : info.ServiceCatalogue$sp,
				customer : info.ServiceCatalogue$customer,
				customerType : info.ServiceCatalogue$customerType,
				name : info.ServiceCatalogue$name,
				descn : info.ServiceCatalogue$descn,
				validDate : info.ServiceCatalogue$validDate,
				beginDate : info.ServiceCatalogue$beginDate,
				endDate : info.ServiceCatalogue$endDate,
				oldCatalogueId:info.ServiceCatalogue$oldCatalogueId,
				flag:'modify',
				status : 4//info.ServiceCatalogue$status变更草稿状态4
			},
			success : function(response, options) {
				//Ext.getCmp("saveBtn").disable();
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;//新的服务目录id
				//var oldSerCataId = info.ServiceCatalogue$id;
				//alert("oldModifyDataId"+oldModifyDataId);
				this.newDataId = curId;
				Ext.MessageBox.alert("提示","保存成功",function(){
					window.location = webContext
						+ "/sciRelationShip_getRootRelationShipData.action?rootCataId="
						+ curId+"&oldSerCataId="+oldModifyDataId+"&type=modify";
				});
				
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			},
			scope : this
		});
	},
	getPanel : function() {
		var da = new DataAction();
		var clazz = "com.zsgj.itil.service.entity.ServiceCatalogue";
//		this.button = this.getButtons();
		var data = null;
		if (modifyDataId != "") {
			data = da.getPanelElementsForEdit("serviceCataInfoModel",
					"serviceCataInfoPanel", modifyDataId);
		} else {
			data = da.getPanelElementsForAdd("serviceCataInfoPanel");
		}
		var biddata = da.split(data);
//		if(alterFlag=="alter"){
//			this.button.push({
//			xtype : 'button',
//			style : 'margin:4px 10px 4px 0',
//			text : '删除变更草稿',
//			scope : this,
//			iconCls : 'delete',
//			handler : this.deletesketch
//		});
//		}
		this.cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 215,
			width : 985,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 4
			},
			//buttons : this.button,
			title : "服务目录  <font color=red >【变更草稿】</font>",
			items : biddata
		});
//		if(alterFlag=="alter"){
//			this.cpanel.title = "服务目录   <font color=red >【变更草稿】</font>";
//			
//		}
		return this.cpanel;
	},
	// 获得合同的面板
	getConPanel : function() {
		var da = new DataAction();
		var data = "";
		var clazz5 = "com.zsgj.itil.service.entity.ServiceCatalogueContract";
		if (modifyDataId != "") {
			var url = webContext
					+ "/sciRelationShip_getServiceCatalogueContractId.action?serviceCataId="
					+ modifyDataId;
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", url, false);
			conn.send(null);
			if (conn.status == "200") {
				// alert(conn.responseText);
				var responseText = conn.responseText;
				data = da.getElementsForEdit(clazz5, responseText);

			} else {
				return '没有将服务目录id为：' + modifyDataId + '存放到的服务合同表中';
			}

		} else {
			data = da.getElementsForAdd(clazz5);
		}
		var biddata = da.split(data);
		this.conPanel = new Ext.form.FormPanel({
			id : "contract",
			layout : 'table',
			rowspan : 1,
			height : 'auto',
			width : 1000,
			frame : true,
			collapsible : true,
			defaults : {
				autoHeight : true,
				forceFit : true,
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "服务合同",
			items : biddata,
			buttonAlign:'center',
			buttons : [{
				xtype : 'button',
				style : 'margin:4px 4px 4px 0',
				text : '保存服务合同',
				scope : this,
				iconCls : 'save',
				handler : this.saveConPanelbysu
			},
			{
					xtype : 'button',
					pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '提交',
					scope : this,
					iconCls:'submit',
					handler:function(){
						Ext.getCmp("PageModelComponentPanel").worksubmit();
					}
				  },
				{
				xtype : 'button',
				style : 'margin:4px 4px 4px 0',
				text : '返回',
				scope : this,
				iconCls : 'back',
				handler : function(){
					window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
				}
			}]
		});
		return this.conPanel;
	},
	// 获得服务项的面板
	getGridPanel : function() {
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_serviceItemSLA");
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
			var editor = headItem.editor;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
//			if(propertyName=="ServiceItemSLA$serviceItemName"){
//				
//				
//			}
			if (hide == 'true') {
				isHidden = true;
			}
			// 给每一行增加修饰
			var columnItem = "";
                //alert(propertyName);
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
		for(var i=0;i<columns.length;i++){
			if(columns[i].dataIndex=='ServiceItemSLA$serviceCatalogue'||columns[i].dataIndex=='ServiceItemSLA$serviceItem'){
				columns[i].hidden=true;
			}
		
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.cm.setEditable(2,false);
		this.storeservice = da.getPagePanelJsonStore("panel_serviceItemSLA",
				obj);
		this.storeservice.paramNames.sort = "orderBy";
		this.storeservice.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBarservice = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.storeservice,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.gridpanel = new Ext.grid.EditorGridPanel({
			id : "serviceSlA",
			store : this.storeservice,
			width : 600,
			height : 300,
			frame : true,
			title : 'SLA信息',
			cm : this.cm,
			trackMouseOver : false,
			loadMask : true,
			tbar:[new Ext.Toolbar.TextItem('<font color=red><提示: 双击单元格可编辑，时间单位为小时></font>')],
			buttonAlign:'center',
			buttons : [
					
					{
						text : '保存',
						style : 'margin:4px 10px 4px 0',
						xtype : 'button',
						handler : this.saveServiceSlA,
						scope : this,
						iconCls : 'save'
					},
					{
					xtype : 'button',
					pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '提交',
					scope : this,
					iconCls:'submit',
					handler:function(){
						Ext.getCmp("PageModelComponentPanel").worksubmit();
					}
				  },						{
						xtype : 'button',
						style : 'margin:4px 10px 4px 0',
						text : '返回',
						scope : this,
						iconCls : 'back',
						handler : function(){
							window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
						}
					}
					],
			bbar : this.pageBarservice
			
		});
		var param = {
			'start' : 0,
			'serviceCatalogue':dataId
		};

		this.storeservice.baseParams=param;
		this.storeservice.load();		

		return this.gridpanel;

	},
	// 保存合同面板
	saveConPanelbysu : function() {
		var panelparam = "";
		if (this.conPanel.getForm().isValid()) {
			panelparam = Ext.encode(this.conPanel.form.getValues(false));
			// panelparam='{['+panelparam+']}';
		} else {
			Ext.MessageBox.alert("<font color=red>请注意</font>",
					"<font color=red>带红线的文本为必填项,否则无法保存</font>");
			return;
		}
		Ext.Ajax.request({
			url : webContext
					+ '/sciRelationShip_saveServiceCatalogueContract.action',
			params : {
				info : panelparam
			},
			success : function(response, options) {
				Ext.MessageBox.alert("保存提示", "保存成功");
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);

	},
	saveServiceSlA : function() {
		var gP = this.gridpanel.getStore().getRange(0,
				this.gridpanel.getStore().getCount());
		var gParam = "";
		for (i = 0; i < gP.length; i++) {
			gParam += Ext.encode(gP[i].data) + ";";
		}

		gParam = gParam.slice(0, gParam.length - 1);
		//alert(gParam);
		Ext.Ajax.request({
			url : webContext + '/sciRelationShip_saveNewServiceItemSLA.action',
			params : {
				info : gParam,
				serviceCataId:modifyDataId
			},
			success : function(response, options) {
				Ext.MessageBox.alert("保存提示", "保存成功",function(){
						Ext.getCmp("serviceSlA").getStore().reload();
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);

	},
//	buttons:[{
//					xtype : 'button',
//					pressed:true,
//					style : 'margin:4px 10px 4px 0',
//					text : '提交',
//					scope : this,
//					iconCls:'submit',
//					handler:function(){
//						Ext.getCmp("PageModelComponentPanel").worksubmit();
//					}
//				  }],
	//buttonAlign : 'center',
	items : this.items,
	
	initComponent : function() {
		this.button = this.getButtons();
		this.tree = new PagteModelTreePanel();
		this.tree.expandAll();
		this.grid = new PageModelGridPanel();
		this.panel = new Ext.Panel({
			y : 200,
			anchor : '0 -200',
			layout : 'column',
			frame : true,
			height : 'auto',
			width : 1000,
			items : [this.tree,this.grid]
		});
		this.caPanel = this.getPanel();
		this.getConPanel = this.getConPanel();
		this.getGrid = this.getGridPanel();
		var item = new Array();
		this.tabPanel = new Ext.TabPanel({
			activeTab : 0,
			deferredRender : false,
			frame : true,
			width : 1000,
			items : [{
				xtype : 'panel',
				id : "first1",
				title : '服务目录面板',
				width : 1000,
				frame : true,
				buttons:this.button,
				buttonAlign:'center',
				items : [this.caPanel, this.panel]
			}, this.getConPanel, this.getGrid]
			
		});
		// item.push(this.caPanel);
		// item.push(this.panel);
		// this.items=item;
//	 	this.wwwbuttons = {
//			layout : 'table',
//			height : 'auto',
//			width : 800,
//			style : 'margin:4px 6px 4px 300px',
//			align : 'center',
//			defaults : {
//				bodyStyle : 'padding:4px'
//			},
//			items : this.submitbb
//			};
		this.items = [this.tabPanel];
		PageModelComponentPanel.superclass.initComponent.call(this);
//		this.save();
//		this.saveConPanelbysu();
//		this.saveServiceSlA();
		// 做tab事件监听
		this.tabPanel.on('beforetabchange', function(obj, newTab, oldTab) {
			if (modifyDataId == "" && newTab.id != "first1") {
				Ext.MessageBox.alert("保存提示", "请先保存服务目录");
				return false;
			}
			if (newTab.id == "serviceSlA") {

				var url = webContext
						+ "/sciRelationShip_saveServiceItemSLAbaseData.action?serviceCatalogueId="
						+ modifyDataId;//modifyDataId
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", url, false);
				conn.send(null);
				if (conn.status == "200") {
					var serviceSla = Ext.getCmp('serviceSlA');
					var param = {
						'serviceCatalogue' : modifyDataId,
						'start' : 0
					};
					serviceSla.store.baseParams=param;
					serviceSla.store.load();							

				} else {
					return '没有将服务目录id为：' + modifyDataId + '存放到的服务合同表中';
				}

			}

		});

	}

});