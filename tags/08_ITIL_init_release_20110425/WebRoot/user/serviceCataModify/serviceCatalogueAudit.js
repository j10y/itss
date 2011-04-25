// ģ�����
PageModelAuditPanel = Ext.extend(Ext.Panel, {
	id : "mb",
	title : "PageModel",
	header : false,
	closable : true,
	frame : true,
	layout : 'column',
	autoScroll : true,
	border : false,
	height : 1200,
	width : 1200,
	layoutConfig : {
		columns : 1
	},
	getConfirmButtons : function() {
		return [
			{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : 'ȷ��',
			scope : this,
			handler : function(){
				audit();
			}
		}
			]

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
		var clazz = "com.digitalchina.itil.service.entity.ServiceCatalogue";
		this.button = this.getButtons();
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
		var cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 180,
			width : 1190,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 6
			},
			buttons : this.button,
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
			height : 'auto',
			width : 100,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "����Ŀ¼��ͬ",
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
			width : 100,
			frame : true,
			title : '��������Ϣ��',
			cm : this.cm,
			trackMouseOver : false,
			loadMask : true,
			height : 320,
			bbar : this.pageBarservice
			
		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'serviceCatalogue' : this.dataId
		};

		this.pageBarservice.formValue = param;

		this.storeservice.baseParams = param;
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
	initComponent : function() {
		modifyDataId =this.dataId;
		this.tree = new PagteModelTreePanel({rootId:rootId,rootText:rootText});
		this.tree.expandAll();
		//this.grid = new PageModelGridPanel();
		this.panel = new Ext.Panel({
			y : 200,
			anchor : '0 -200',
			layout : 'column',
			height : 510,
			width : 1200,
			items : [this.tree]
		});
		this.caPanel = this.getPanel();
		this.getConPanel = this.getConPanel();
		this.getGrid = this.getGridPanel();
		var item = new Array();
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
			width : 1400,
			// bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : [{
				xtype : 'panel',
				id : "first1",
				title : '����Ŀ¼���',
				width : 1200,
				items : [this.caPanel, this.panel]
			}, this.getConPanel, this.getGrid]
		});
		// item.push(this.caPanel);
		// item.push(this.panel);
		// this.items=item;
		this.bb = this.getConfirmButtons();
	 	this.wwwbuttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : this.bb
			};
		this.items = [this.tabPanel,this.wwwbuttons];
		PageModelAuditPanel.superclass.initComponent.call(this);
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
					serviceSla.store.baseParams = param;
					serviceSla.store.load();

				} else {
					return 'û�н�����Ŀ¼idΪ��' + modifyDataId + '��ŵ��ķ����ͬ����';
				}

			}

		});

	}

});