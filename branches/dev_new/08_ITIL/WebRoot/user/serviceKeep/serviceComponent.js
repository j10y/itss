// ģ�����
PageModelComponentPanel = Ext.extend(Ext.Panel, {
	id : "mb",
	title : "PageModel",
	header : false,
	closable : true,
	frame : true,
	layout : 'column',
	autoScroll : true,
	border : false,
	height : 920,
	width : 'auto',
	layoutConfig : {
		columns : 1
	},
	isNotNegative:function(str)
	{
		if(str == ""){
			return false;
		}
		if(str*1-0>=0){
			return false;
		}
		return true;
	},
	afterEdit:function(obj){	
		var r = obj.record;
		var provideTime = r.get("ServiceItemSLA$provideTime");
		if(this.isNotNegative(provideTime)){
			alert("�����ṩʱ�䲻�����븺����");
			r.set("ServiceItemSLA$provideTime","");
		}
		var problemHandleTime = r.get("ServiceItemSLA$problemHandleTime");	
		if(this.isNotNegative(problemHandleTime)){
			alert("������ʱ�䲻�����븺����");
			r.set("ServiceItemSLA$problemHandleTime","");
		}
	},
	worksubmit : function(){
		alert("================"+"����������"+"======================");
		Ext.Ajax.request({
			url : webContext + '/configWorkflow_findProcessByPram.action',
			params : {
				modleType : 'SCIC',//
				processStatusType : '0'//
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON
						.decode(response.responseText);
				var vpid = responseArray.vpid;
				if(vpid!=""&&vpid!=undefined&&vpid.length>0){
					Ext.Ajax.request({
						url :webContext+'/extjs/workflow?method=apply',
						params : {
							dataId : this.dataId,
							model: this.model,
							bzparam : "{dataId :'"+ this.dataId+"',modifyDataId:'"+this.dataId+"',rootId:'"+this.rootId+"',rootText:'"+this.rootText+"',applyId : '"+this.dataId+"',applyType: 'cproject',applyTypeName: '����Ŀ¼����',customer:''}",						
							defname : vpid
						},
						success : function(response, options) {
							
								
							Ext.Msg.alert("��ʾ","�����������ɹ�",function(){
								
								window.location = webContext+'/user/serviceIssue/serviceCatalogueIssueList.jsp';
							});				
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("��ʾ","����������ʧ��");
						}
					}, this);
				}else{
					Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
				}
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
			}
		}, this);
		},
	getButtons : function() {
		return [
			{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '����',
			id:'saveBtn',
			scope : this,
			handler : this.save,
			iconCls : 'save'
		}, 
			{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			handler : function() {
				window.location = webContext+'/user/serviceKeep/serviceCatalogueKeepList.jsp';
			},
			text : '����',
			iconCls : 'back'
		}]

	},

	save : function() {
		Ext.getCmp("saveBtn").setDisabled(true);
		var info = Ext.getCmp('cataPanel').getForm().getValues();
		if (info.ServiceCatalogue$name == "" || info.ServiceCatalogue$customerType == "" || info.ServiceCatalogue$customer == ""
				|| info.ServiceCatalogue$beginDate == "" || info.ServiceCatalogue$endDate == "") {
			 Ext.Msg.alert("��ʾ","ȱ�ٱ������ݣ�����д��ȫ",function(){
			 		Ext.getCmp("saveBtn").setDisabled(false);
			 });
			return;
		};
//		alert(Ext.encode(info));
		var d1 = new Date(info.ServiceCatalogue$beginDate.replace(/-/g, "/")); 
		var d2 = new Date(info.ServiceCatalogue$endDate.replace(/-/g, "/")); 
		  if (Date.parse(d1) - Date.parse(d2) == 0) { 
		    	 Ext.Msg.alert("��ʾ","���ڲ������!",function(){
		    	 	Ext.getCmp("saveBtn").setDisabled(false);
		    	 }); 
		    	return; 
		  }  
		  if (Date.parse(d1) - Date.parse(d2) > 0) { 
		    Ext.Msg.alert("��ʾ","��ֹ��Ч�ڲ���С����ʼ��Ч��!",function(){
		    	Ext.getCmp("saveBtn").setDisabled(false);
		    });
		    return; 
		  } 
		Ext.Ajax.request({
			url : webContext
					+ '/sciRelationShip_saveRootSCIRelationShip.action',
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
				status : info.ServiceCatalogue$status
			},
			success : function(response, options) {
				//Ext.getCmp("saveBtn").disable();
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				Ext.MessageBox.alert("��ʾ","����ɹ�",function(){
					window.location = webContext
						+ "/sciRelationShip_getRootRelationShipData.action?rootCataId="
						+ curId+"&type=back";
				});
				
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
				 Ext.getCmp("saveBtn").setDisabled(false);
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
		//for(var i=0;i<data.length;i++){
			//alert(data[i].name);
			//alert(data[i].getValue());
				
		//}
		var biddata = da.split(data);
		var cpanel = new Ext.form.FormPanel({
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
			title : "����Ŀ¼ <font color=red><��ʾ:���ȱ���,��ά������Ŀ¼��ϵ></font>",
			items : biddata
		});
		return cpanel;
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
		for (i = 0; i < data.length; i++) {
			if(data[i].id=='contractCode'){
				data[i].readOnly = true;
				data[i].emptyText = '�Զ�����';
			}
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
			title : "�����ͬ",
			items : biddata,
			buttons : [{
				xtype : 'button',
				style : 'margin:4px 4px 4px 0',
				text : '��������ͬ',
				scope : this,
				handler : this.saveConPanelbysu,
				iconCls : 'save'
			},
				{
				xtype : 'button',
				style : 'margin:4px 4px 4px 0',
				text : '����',
				scope : this,
				iconCls : 'back',
				handler : function(){
					window.location=webContext+'/user/serviceKeep/serviceCatalogueKeepList.jsp';
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBarservice.formValue = this.formValue;
		this.gridpanel = new Ext.grid.EditorGridPanel({
			id : "serviceSlA",
			rowspan : 1,
			store : this.storeservice,
			width : 1000,
			frame : true,
			title : 'SLA��Ϣ',
			cm : this.cm,
			// sm : sm,
			trackMouseOver : false,
			loadMask : true,
			// y : 140,
			height : 'auto',
			defaults : {
				autoHeight : true,
				forceFit : true,
				bodyStyle : 'padding:2px'
			},
			// anchor : '0 -35',
			tbar:[new Ext.Toolbar.TextItem('<font color=red>��ʾ��˫����Ԫ��ɱ༭��ʱ�䵥λΪСʱ</font>')],
			buttonAlign:'center',
			buttons : [
					
					{
						text : '�� ��',
						pressed : true,
						handler : this.saveServiceSlA,
						scope : this,
						iconCls : 'save'
					},
					{	text : '����',
						pressed : true,
						scope : this,
						iconCls : 'back',
						handler : function(){
							window.location=webContext+'/user/serviceKeep/serviceCatalogueKeepList.jsp';
						}}
					
					],
			bbar : this.pageBarservice
			
		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'serviceCatalogue':this.dataId
		};

		this.pageBarservice.formValue = param;
		this.gridpanel.on("afteredit",this.afterEdit,this);
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
		Ext.Ajax.request({
			url : webContext + '/sciRelationShip_saveNewServiceItemSLA.action',
			params : {
				info : gParam
				//model:"panel_serviceItemSLA"
			},
			success : function(response, options) {
				Ext.MessageBox.alert("������ʾ", "����ɹ�",function(){
					Ext.getCmp("serviceSlA").getStore().reload();
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);

	},
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
				title : '����Ŀ¼���',
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
					serviceSla.bbar.formValue = this.formValue;
					serviceSla.store.baseParams=param;
					serviceSla.store.load();

				} else {
					return 'û�н�����Ŀ¼idΪ��' + modifyDataId + '��ŵ��ķ����ͬ����';
				}

			}

		});

	}

});