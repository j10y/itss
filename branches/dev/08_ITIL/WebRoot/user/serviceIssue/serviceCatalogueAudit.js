// ģ�����
PageModelAuditPanel = Ext.extend(Ext.Panel, {
	id : "mb",
	title : "����Ŀ¼��Ϣ",
	header : false,
	buttonAlign : 'center',
	frame : true,
	layout : 'column',
	//autoScroll : true,
	border : false,
	height : 600,
	width : 'auto',
	layoutConfig : {
		columns : 1
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
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.id;
				window.location = webContext
						+ "/sciRelationShip_getRootRelationShipData.action?rootCataId="
						+ curId;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			},
			scope : this
		});
	},
	getPanel : function() {
		var da = new DataAction();
		var data = null;
		if (this.dataId != "") {
			data = da.getPanelElementsForEdit("serviceCataInfoModel",
					"serviceCataInfoPanel", this.dataId);
		} else {
			data = da.getPanelElementsForAdd("serviceCataInfoPanel");
		}
		var biddata = da.splitForReadOnly(data);
		var cpanel = new Ext.form.FormPanel({
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
			title : "����Ŀ¼",
			items : biddata
		});
		return cpanel;
	},
	// ��ú�ͬ�����
	getConPanel : function() {
		var da = new DataAction();
		var data = "";
		var clazz5 = "com.digitalchina.itil.service.entity.ServiceCatalogueContract";
		if (this.dataId != "") {
			var url = webContext
					+ "/sciRelationShip_getServiceCatalogueContractId.action?serviceCataId="
					+ this.dataId;
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", url, false);
			conn.send(null);
			if (conn.status == "200") {
				var responseText = conn.responseText;
				data = da.getElementsForEdit(clazz5, responseText);

			} else {
				return 'û�н�����Ŀ¼idΪ��' + this.dataId + '��ŵ��ķ����ͬ����';
			}

		} else {
			data = da.getElementsForAdd(clazz5);
		}
		var biddata = da.splitForReadOnly(data);
		this.conPanel = new Ext.form.FormPanel({
			id : "contract",
			layout : 'table',
			rowspan : 1,
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
			title : "�����ͬ",
			items : biddata
			
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
		this.gridpanel = new Ext.grid.GridPanel({
			id : "serviceSlA",
			rowspan : 1,
			store : this.storeservice,
			width : 800,
			frame : true,
			title : '��������Ϣ��',
			cm : this.cm,
			trackMouseOver : false,
			loadMask : true,
			height : 'auto',
			defaults : {
				autoHeight : true,
				forceFit : true,
				bodyStyle : 'padding:2px'
			},
			tbar:[new Ext.Toolbar.TextItem('<font color=red>��ʾ��ʱ�䵥λΪСʱ</font>')],
			bbar : this.pageBarservice
			
		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'serviceCatalogue' : this.dataId
		};

		this.pageBarservice.formValue = param;
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
	items : this.items,
	buttons : this.buttons,
	initComponent : function() {
		var histroyForm = new ServiceCatalogueHistroyForm({dataId:this.dataId});
		var treeGrid = new PagePanel({dataId:this.dataId});
		this.caPanel = this.getPanel();
		this.getConPanel = this.getConPanel();
		this.getGrid = this.getGridPanel();
		var item = new Array();
		this.tabPanel = new Ext.TabPanel({
			activeTab : 0,
			deferredRender : false,
			frame : true,
			width : 800,
			height :500,
			items : [{
				xtype : 'panel',
				id : "first1",
				title : '����Ŀ¼���',
				frame:true,
				items : [this.caPanel, treeGrid]
			}, this.getConPanel, this.getGrid,histroyForm]
		});
		this.items = [this.tabPanel];
		this.buttons=[{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '����',
			scope : this,
			handler : function(){
				audit();
			}
		}]
		PageModelAuditPanel.superclass.initComponent.call(this);
	}

});