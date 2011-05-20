// ģ�����
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
		//alert("================"+"����������"+"======================");
		//alert("=="+this.dataId+"==");
//		alert("=="+this.modifyDataId+"==22");
		alert("this.dataId=="+this.dataId+"==");
		alert("oldModifyDataId=="+oldModifyDataId+"==");

		Ext.Ajax.request({
			url :webContext+'/extjs/workflow?method=apply',
			params : {
				dataId : this.dataId,
				model: this.model,
				bzparam : "{dataId :'"+ this.dataId+"',oldDataId:'"+oldModifyDataId+"',applyId : '"+this.dataId+"',applyType: 'cproject',applyTypeName: '����Ŀ¼�����������',customer:''}",						
				defname : "serviceCatalogueManager1239183450187"
			},
			success : function(response, options) {
				
					
				Ext.Msg.alert("��ʾ","�����������ɹ�",function(){
					
					window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
				});				
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("��ʾ","����������ʧ��");
			}
		}, this);
		},
	getButtons : function() {
		return [
			{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '��ʼ���',
			scope : this,
			pressed:true,
			iconCls : 'edit',
			handler : function(){
				  	window.location= webContext+"/sciRelationShip_getRootRelationShipDataAlter.action?rootCataId="+this.dataId;
				//��ת����һ��ҳ�棬���ں�̨����ݸ�
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
			text : '����'
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
			text : '����'
		}]

	},
	save : function() {
		var info = Ext.getCmp('cataPanel').getForm().getValues();
		if (info.sp == "" || info.name == "" || info.descn == ""
				|| info.beginDate == "" || info.endDate == "") {
			alert("ȱ�ٱ������ݣ�����д��ȫ");
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
				status : 4//info.ServiceCatalogue$status����ݸ�״̬4
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;//�µķ���Ŀ¼id
				//var oldSerCataId = info.ServiceCatalogue$id;
				alert("oldModifyDataId"+oldModifyDataId);
				this.newDataId = curId;
				window.location = webContext
						+ "/sciRelationShip_getRootRelationShipData.action?rootCataId="
						+ curId+"&oldSerCataId="+oldModifyDataId+"&type=modify";
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
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
			title : "����Ŀ¼",
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
			title : "����Ŀ¼ <font color=red>���Ѿ����ڱ�������еķ���Ŀ¼��</font>",
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
			title : "����Ŀ¼",
			items : biddata
		});
		
		}
		return this.cpanel;
	},
	// ��ú�ͬ�����
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
				return 'û�н�����Ŀ¼idΪ��' + modifyDataId + '��ŵ��ķ����ͬ����';
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
			title : "�����ͬ",
			items : biddata,
			buttons : [
//				{
//				xtype : 'button',
//				style : 'margin:4px 4px 4px 0',
//				text : '��������ͬ',
//				scope : this,
//				iconCls : 'save',
//				handler : this.saveConPanelbysu
//			},{
//				xtype : 'button',
//				style : 'margin:4px 4px 4px 0',
//				text : '�ύ',
//				scope : this,
//				iconCls : 'submit',
//				handler : this.worksubmit
//			},
				{
				xtype : 'button',
				style : 'margin:4px 4px 4px 0',
				text : '����',
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
	// ��÷���������
	getGridPanel : function() {
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_serviceItemSLA");
		// ǰ��ĸ�ѡ��
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
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
			// ��ÿһ����������
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.gridpanel = new Ext.grid.GridPanel({
			id : "serviceSlA",
			rowspan : 1,
			store : this.storeservice,
			width : 800,
			frame : true,
			title : 'SLA��Ϣ',
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
			tbar:[new Ext.Toolbar.TextItem('<font color=red>˫����Ԫ��ɱ༭��ʱ�䵥λΪСʱ</font>')],
			buttonAlign:'center',
			buttons: [					
					{	pressed : true,
						text : '����',
						scope : this,
						iconCls : 'back',
						handler : function(){
							window.location = webContext+'/user/serviceCataModify/serviceCatalogueModifyList.jsp';
						}
					}
//						'    |  ',
//					{
//						text : '�� ��',
//						pressed : true,
//						handler : this.saveServiceSlA,
//						scope : this,
//						iconCls : 'save'
//					},'   |  ',
//					{	text : '�ύ',
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
	// �����ͬ���
	saveConPanelbysu : function() {
		var panelparam = "";
		if (this.conPanel.getForm().isValid()) {
			panelparam = Ext.encode(this.conPanel.form.getValues(false));
			// panelparam='{['+panelparam+']}';
		} else {
			Ext.MessageBox.alert("<font color=red>��ע��</font>",
					"<font color=red>�����ߵ��ı�Ϊ������,�����޷�����</font>");
			return;
		}
		Ext.Ajax.request({
			url : webContext
					+ '/sciRelationShip_saveServiceCatalogueContract.action',
			params : {
				info : panelparam
			},
			success : function(response, options) {
				Ext.MessageBox.alert("������ʾ", "����ɹ�");
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
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
				Ext.MessageBox.alert("������ʾ", "����ɹ�");
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);

	},
	getBtns:function(){
		this.mybuttons = [{
					xtype : 'button',
					pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '��ѯ',
					scope : this,
					iconCls:'search',
					handler:function(){
					}	
				  },{
				  	xtype : 'button',
				  	pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '�鿴',
					scope : this,
					iconCls:'look',
					handler:function(){
					} 	
				  },{
					xtype : 'button',
					pressed:true,
					style : 'margin:4px 10px 4px 0',
					text : '����',
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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
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
				title : '����Ŀ¼���',
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
				title : '����Ŀ¼���',
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
		// ��tab�¼�����
		this.tabPanel.on('beforetabchange', function(obj, newTab, oldTab) {
			if (modifyDataId == "" && newTab.id != "first1") {
				Ext.MessageBox.alert("������ʾ", "���ȱ������Ŀ¼");
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
					return 'û�н�����Ŀ¼idΪ��' + modifyDataId + '��ŵ��ķ����ͬ����';
				}

			}

		});

	}

});