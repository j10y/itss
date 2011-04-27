// 模板组件
PageModelDisComponentPanel = Ext.extend(Ext.Panel, {
	id : "mb",
	title : "PageModel",
	header : false,
	closable : true,
	frame : true,
	layout : 'column',
	autoScroll : true,
	border : false,
	height : 600,
	//viewCofig:{autoFill:true},
	width : 'auto',
	layoutConfig : {
		columns : 1
	},
	worksubmit : function(){
		//alert("================"+"启动工作流"+"======================");
		//alert("=="+this.dataId+"==");
//		alert("=="+this.modifyDataId+"==22");
		alert("this.dataId=="+this.dataId+"==");
		alert("oldModifyDataId=="+oldModifyDataId+"==");

		Ext.Ajax.request({
			url :webContext+'/extjs/workflow?method=apply',
			params : {
				dataId : this.dataId,
				model: this.model,
				bzparam : "{dataId :'"+ this.dataId+"',oldDataId:'"+oldModifyDataId+"',applyId : '"+this.dataId+"',applyType: 'cproject',applyTypeName: '服务目录变更审批流程',customer:''}",						
				defname : "serviceCatalogueManager1239183450187"
			},
			success : function(response, options) {
				
					
				Ext.Msg.alert("提示","启动工作流成功",function(){
					
					window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
				});				
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("提示","启动工作流失败");
			}
		}, this);
		},
	getButtons : function() {
		return [
			{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '开始变更',
			scope : this,
			pressed:true,
			iconCls : 'edit',
			handler : function(){
				  	window.location= webContext+"/sciRelationShip_getRootRelationShipDataAlter.action?rootCataId="+this.dataId;
				//跳转到另一个页面，且在后台保存草稿
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
	getExistButtons : function() {
		return [
			
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
	save : function() {
		var info = Ext.getCmp('cataPanel').getForm().getValues();
		if (info.sp == "" || info.name == "" || info.descn == ""
				|| info.beginDate == "" || info.endDate == "") {
			alert("缺少必须数据，请填写完全");
			return;
		};
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
				status : 4//info.ServiceCatalogue$status变更草稿状态4
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;//新的服务目录id
				//var oldSerCataId = info.ServiceCatalogue$id;
				alert("oldModifyDataId"+oldModifyDataId);
				this.newDataId = curId;
				window.location = webContext
						+ "/sciRelationShip_getRootRelationShipData.action?rootCataId="
						+ curId+"&oldSerCataId="+oldModifyDataId+"&type=modify";
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
		//this.button = this.getButtons();
		//this.existBtn = this.getExistButtons();
		var data = null;
		if (modifyDataId != "") {
			data = da.getPanelElementsForEdit("serviceCataInfoModel",
					"serviceCataInfoPanel", modifyDataId);
		} else {
			data = da.getPanelElementsForAdd("serviceCataInfoPanel");
		}
		for(var i=0;i<data.length;i++){
			//alert(data[i].name);
				data[i].readOnly=true;
				//biddata[i].disabled=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
		}
		var biddata = da.split(data);
		this.cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 215,
			width : 785,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 4
			},
			//buttons : this.button,
			title : "服务目录",
			items : biddata
		});
		if(existFlag=='exist'){
			this.cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 215,
			width : 785,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 4
			},
			//buttons : this.existBtn,
			title : "服务目录 <font color=red>【已经存在变更审批中的服务目录】</font>",
			items : biddata
		});
			
			
		
		}else{
			this.cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 215,
			width : 785,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 4
			},
			//buttons : this.button,
			title : "服务目录",
			items : biddata
		});
		
		}
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
		for(var i=0;i<data.length;i++){
			//alert(data[i].name);
				data[i].readOnly=true;
				//biddata[i].disabled=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
		}
		var biddata = da.split(data);
		this.conPanel = new Ext.form.FormPanel({
			id : "contract",
			layout : 'table',
			rowspan : 1,
			height : 500,
			width :800,
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
			buttons : [
//				{
//				xtype : 'button',
//				style : 'margin:4px 4px 4px 0',
//				text : '保存服务合同',
//				scope : this,
//				iconCls : 'save',
//				handler : this.saveConPanelbysu
//			},{
//				xtype : 'button',
//				style : 'margin:4px 4px 4px 0',
//				text : '提交',
//				scope : this,
//				iconCls : 'submit',
//				handler : this.worksubmit
//			},
				{
				xtype : 'button',
				style : 'margin:4px 4px 4px 0',
				text : '返回',
				pressed:true,
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
		this.gridpanel = new Ext.grid.GridPanel({
			id : "serviceSlA",
			rowspan : 1,
			store : this.storeservice,
			width : 800,
			frame : true,
			title : 'SLA信息',
			cm : this.cm,
			// sm : sm,
			trackMouseOver : false,
			loadMask : true,
			// y : 140,
			height : 300,
			// anchor : '0 -35',
			defaults : {
				autoHeight : true,
				forceFit : true,
				bodyStyle : 'padding:2px'
			},
			tbar:[new Ext.Toolbar.TextItem('<font color=red>双击单元格可编辑，时间单位为小时</font>')],
			buttonAlign:'center',
			buttons: [					
					{	pressed : true,
						text : '返回',
						scope : this,
						iconCls : 'back',
						handler : function(){
							window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
						}
					}
//						'    |  ',
//					{
//						text : '保 存',
//						pressed : true,
//						handler : this.saveServiceSlA,
//						scope : this,
//						iconCls : 'save'
//					},'   |  ',
//					{	text : '提交',
//						pressed : true,
//						handler : this.worksubmit,
//						scope : this,
//						iconCls : 'submit'},
					],
			bbar : this.pageBarservice
			
		});
		var param = {
			'start' : 0,
			'serviceCatalogue':this.dataId
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
			url : webContext + '/sciRelationShip_saveServiceItemSLA.action',
			params : {
				info : gParam
			},
			success : function(response, options) {
				Ext.MessageBox.alert("保存提示", "保存成功");
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			}
		}, this);

	},
	getBtns:function(){
		this.mybuttons = [{
					xtype : 'button',
					pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '查询',
					scope : this,
					iconCls:'search',
					handler:function(){
					}	
				  },{
				  	xtype : 'button',
				  	pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '查看',
					scope : this,
					iconCls:'look',
					handler:function(){
					} 	
				  },{
					xtype : 'button',
					pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '重置',
					scope : this,
					iconCls:'reset',
					handler:function(){
					}
				}]
			return this.mybuttons;
	},
	items : this.items,
	
	initComponent : function() {
		this.button = this.getButtons();
		this.existBtn = this.getExistButtons();
		this.btns = this.getBtns();
		var treeGrid = new PagePanel({dataId:dataId});
		this.tree = new PagteModelDisTreePanel();
		this.tree.expandAll();
		this.panel = new Ext.Panel({
			y : 200,
			anchor : '0 -200',
			layout : 'column',
			frame : true,
			height : 'auto',
			width : 'auto',
			items : [treeGrid]
		});
		this.caPanel = this.getPanel();
		this.getConPanel = this.getConPanel();
		this.getGrid = this.getGridPanel();
		var item = new Array();
		if(existFlag=='exist'){
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
			// title:tabTitle,
			enableTabScroll : true,
			// minTabWidth:100,
			// resizeTabs : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 800,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				forceFit : true,
				bodyStyle : 'padding:2px'
			},
//			buttons:this.btns,
//			buttonAlign : 'center',
			items : [{
				xtype : 'panel',
				id : "first1",
				title : '服务目录面板',
				frame : true,
				width : 800,
//				height:1500,
				buttons:this.existBtn,
				buttonAlign:'center',
				viewCofig:{autoFill:true},
				items : [this.caPanel, treeGrid]
			}, this.getConPanel, this.getGrid]
			
		});
		}else{
		this.tabPanel = new Ext.TabPanel({
			activeTab : 0,
			frame : true,
			plain : true,
			border : false,
			width : 800,
			items : [{
				xtype : 'panel',
				id : "first1",
				title : '服务目录面板',
				frame : true,
				width : 800,
//				height:1500,
				buttons:this.button,
				buttonAlign:'center',
				viewCofig:{autoFill:true},
				items : [this.caPanel, treeGrid]
			}, this.getConPanel, this.getGrid]
			
		});
		}
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
		PageModelDisComponentPanel.superclass.initComponent.call(this);
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
						+ modifyDataId;
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", url, false);
				conn.send(null);
				if (conn.status == "200") {
					// alert(conn.responseText);
					var responseText = conn.responseText;
					var serviceSla = Ext.getCmp('serviceSlA');
					var param = {
						'mehtodCall' : 'query',
						'serviceCatalogue' : modifyDataId,
						'start' : 0
					};
					// alert(Ext.encode(param));
					this.formValue = param;
//					serviceSla.bbar.formValue = this.formValue;
					serviceSla.store.baseParams=param;
					serviceSla.store.load();
				} else {
					return '没有将服务目录id为：' + modifyDataId + '存放到的服务合同表中';
				}

			}

		});

	}

});