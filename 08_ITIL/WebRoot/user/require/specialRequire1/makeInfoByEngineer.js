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
	 //add by musicbear for �ύʱͬʱ������Ի�����ƻ� in 2009 11 19 start	
		    var reqId = this.dataId;
			var store = Ext.getCmp('panel_SRExpendPlan_list').store;
				var info = "";
				store.each(function(record) {
					if (record.dirty) {
						info += Ext.encode(record.data) + ",";
					}
				});
				info = unicode(info);
				Ext.Ajax.request({
					url : webContext + '/SRAction_saveExpendPlan.action',
					params : {
						info : info,
						reqId : reqId
					},
					success : function(response, options) {
						store.reload();
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}

				},this);	
		 //add by musicbear for �ύʱͬʱ������Ի�����ƻ� in 2009 11 19 end
		if (!Ext.getCmp('panel_SRAnalyse').form.isValid()) {
			Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ������,����д����������.");
			return false;
		}		
		var startDate=Ext.getCmp('SRAnalyse$startDate').getValue().format('Y-m-d');
		var endtDate=Ext.getCmp('SRAnalyse$finishDate').getValue().format('Y-m-d');
		var testBeginDate=Ext.getCmp('SRAnalyse$testBeginDate').getValue().format('Y-m-d');
		var testEndDate=Ext.getCmp('SRAnalyse$testEndDate').getValue().format('Y-m-d');
		
		if (startDate > endtDate) {
			Ext.MessageBox.alert("��ʾ", "ϵͳ��ʼ����ʱ����д����ȷ����������д."); 
			return false;
		 }
		 if (testBeginDate > testEndDate) {
			Ext.MessageBox.alert("��ʾ", "���Կ�ʼ����ʱ����д����ȷ����������д.");
			return false;
		 }

		var formParam = Ext.getCmp('panel_SRAnalyse').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curdataId = this.dataId;
		Ext.Ajax.request({
			url : webContext
					+ '/SRAction_saveProjectRequireAnalyseForReq.action?'
					+ '&reqId=' + this.dataId,
			params : vp,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var tempId = responseArray.id;
				Ext.getCmp('SRAnalyse$id').setValue(tempId);
				window.parent.auditContentWin.specialAudit();
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},

	saveProjectRequireAnalyse : function() {
		var formParam = Ext.getCmp('panel_SRAnalyse').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		var curdataId = this.dataId;
		Ext.Ajax.request({
			url : webContext
					+ '/SRAction_saveProjectRequireAnalyseForReq.action?'
					+ '&reqId=' + this.dataId,
			params : vp,
			success : function(response, options) {
				Ext.MessageBox.alert("����ɹ�");
				window.location = webContext
						+ "/user/require/specialRequire/makeInfoByEngineer.jsp?"
						+ "dataId=" + curdataId;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	saveProjectPlan : function() {
		var formParam = Ext.getCmp('panel_SRProjectPlan').form.getValues(false);
		var vp = null;
		if (formParam != null) {
			vp = Ext.apply(formParam, vp, {});
		}
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveProjectPlanForReq.action?'
					+ '&reqId=' + this.dataId,
			params : vp,

			success : function(response, options) {
				Ext.MessageBox.alert("����ɹ�");
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var curId = responseArray.requirementServiceItemId;
				window.location = webContext
						+ "/user/require/specialRequire/makeInfoByEngineer.jsp?"
						+ "dataId=" + this.dataId;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
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
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ӵ���!");
			return;
		}
		if (records.length > 1) {
			Ext.MessageBox.alert('��ʾ', '���ѡ��һ����Ϣ���������!');
		}
		var newdataId = record.get('ServiceItem$id');
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveServiceItemReShip.action?'
					+ 'reqId=' + dataId + '&newdataId=' + newdataId,

			success : function(response, options) {
				Ext.MessageBox.alert("��ʾ", "��ӳɹ�", function() {
					Ext.getCmp('winid').close();
					window.location = webContext
							+ "/user/require/specialRequire1/makeInfoByEngineer.jsp?"
							+ "dataId=" + dataId + "&tabNumer="
							+ 'serviceItemBasePanel' + "taskId=" + taskId;
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("���ʧ��");
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
				Ext.MessageBox.alert("����ɹ�");
				window.location = webContext
						+ "/user/require/specialRequire1/makeInfoByEngineer.jsp?"
						+ "dataId=" + curdataId + "&tabNumer="
						+ 'serviceItemBasePanel';
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	selectServiceItem : function() {
		var createPanel2 = this.getGridPanel();
		this.fp = this.getSearchForm();
		var win1 = new Ext.Window({
			id : 'winid',
			title : '������',
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
				text : '�ر�',
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
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// ��ÿһ����������
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
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
				text : '�� ѯ',
				pressed : true,
				handler : this.createSearch2,
				scope : this,
				iconCls : 'search'
			}, '|', {
				text : '�� ��',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			}, '|', {
				text : '�� ��',
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
			//minTabWidth:100,
			//resizeTabs:true,
			title : tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			//tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
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
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequire3_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nsr1_confirmByClientManager",
					"panel_SpecialRequire3_Input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequire3_Input");

		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;// �ı�Combobox��id
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
				title : "�û�����",
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
				title : "�û�����",
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
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"serviceItemBasePanel", this);

		if (siId != '0') {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",
					siId);// ����Ҫ��ʱ���

		} else {
			data = da.getPanelElementsForAdd("serviceItemBasePanel");

		}
		for (i = 0; i < data.length; i++) {
			if (data[i].id == 'ServiceItem$serviceItemCode') {
				data[i].readOnly = true;
				data[i].emptyText = '�Զ�����';
			}

		}

		biddata = da.split(data);

		var item = new Array();
		// item=biddata;
		// item.push(this.caPanel);

		if (this.getFormButtons.length != 0&&this.readOnly!=1) {
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
				title : "<font color=red>����������</font>",
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
					columns : 4
				},
				title : "<font color=red>����������</font>",
				items : biddata
			});
		}
		this.formserviceItemBasePanel2 = new Ext.Panel({
			id : 'serviceItemBasePanel',
			y : 200,
			anchor : '0 -200',
			layout : 'column',
			height : 1000,
			width : 1200,
			title : "<font color=red>����������</font>",
			items : [this.formserviceItemBasePanel]
			//items : [this.formserviceItemBasePanel,this.modifyGridInfo()]
		});
//		this.formserviceItemBasePanel.on("saveServiceItem",this.saveServiceItem, this);
		return this.formserviceItemBasePanel2;
	},
	 modifyGridInfo:function(){
   
   		var sm = new Ext.grid.CheckboxSelectionModel();// ��ѡ��
		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
											{header:'������',dataIndex:'modifyNo',sortable:true},
											{header:'�������',dataIndex:'name',sortable:true},
											{header:'�������',dataIndex:'descn',sortable:true},
											{header:'���ԭ��',dataIndex:'reason',sortable:true},
											{header:'����ύ��',dataIndex:'applyUser',sortable:true},
											{header:'����ύ����',dataIndex:'applyDate',sortable:true}
										
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
			pageSize :10,// ʹ�õ���ϵͳĬ��ֵ
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			id:"modifyGrid",
			title:"�����Ϣ   <font color=black style='font-weight:lighter'  face=����_GB2312>��˫���鿴�����Ϣ��</font>",
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
   		var record = Ext.getCmp("modifyGrid").getSelectionModel()
							.getSelected();
	    var records = Ext.getCmp("modifyGrid").getSelectionModel()
							.getSelections();
        if (!record) {
	         Ext.Msg.alert("��ʾ", "����ѡ��鿴�ļ�¼!");
	         return;
           }
         if (records.length == 0) {
	      Ext.MessageBox.alert('����', 'ֻ��ѡ��һ����Ϣ!');
	      return;
        }
        var modifyId = record.get("id");
        
        
        
        var url = webContext+ '/user/configNewModify/ciModifyFirstLook.jsp';

					this.configItemModifyWindow = new Ext.Window({
						//									
						title : '�����Ϣ����',
						modal : true,
						height : 500,
						width : 800,
						resizable : true,
						id : "modifyInfoWindow",
						draggable : true,
						buttons : [{
							buttonAlign : 'center',
							text : '�ر�',
							pressed : true,
							handler : function() {
								Ext.getCmp("modifyInfoWindow").close();
								// Ext.getCmp("tree").root.reload();
							}
						}],
						autoLoad : {
							// װ�������й���Ϣ���������й�,�˴��ɰ��������ƴ��ݹ�ȥ��auditInfo.jsp�����������ƾ����Ƿ���Ҫ�޸���������
							url : webContext + "/tabFrame.jsp?url=" + url+"?dataId="+modifyId,// +"****currentItemId="+currentItemId+"****flag=child",
							text : "ҳ�����ڼ�����......",
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
							html : "���ڼ���ҳ������......"
						}]

					});
					this.configItemModifyWindow.show();	
        
        
        
   },
	getFormpanel_SRServiceProviderInfo_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SRServiceProviderInfo_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("sr2_makeInfoByEngineer",
					"panel_SRServiceProviderInfo_Input", this.dataId);// ����Ҫ��ʱ���

		} else {
			data = da
					.getPanelElementsForAdd("panel_SRServiceProviderInfo_Input");

		}
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].id = data[i].id + 8;//�ı�Combobox��id
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
				title : "�����ܼ��������",
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
				title : "�����ܼ��������",
				items : biddata
			});
		}
		return this.formpanel_SRServiceProviderInfo_Input;
	},
	getFormpanel_SRAnalyse : function() {
		var sra = new SRAction();
		var praId = sra.getProjectRequireAnalyseId(this.dataId);
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRAnalyse",
				this);
		if (praId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse",
					praId);// ����Ҫ��ʱ���
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRAnalyse", "");
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
				title : "<font color=red>��Ŀ�������</font>",
				items : biddata,
				buttons : this.getFormButtons
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
				title : "<font color=red>��Ŀ�������</font>",
				items : biddata
			});
		}
		return this.formpanel_SRAnalyse;
	},
//add by muiscbear for ��Ӹ��Ի�����ƻ� in 2009 11 6 start	
	getGridpanel_SRExpendPlan_list : function() {
		var reqId = this.dataId;
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_SRExpendPlan_list");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel(
				"panel_SRExpendPlan_list", this);

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
		var addExpendPlanButton = new Ext.Button({
			text : '���',
			pressed : true,
			iconCls : 'add',
			handler : function() {
				var store = Ext.getCmp('panel_SRExpendPlan_list').store;
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
		var removeExpendPlanButton = new Ext.Button({
			text : 'ɾ��',
			pressed : true,
			iconCls : 'remove',
			handler : function() {
				var record = Ext.getCmp('panel_SRExpendPlan_list')
						.getSelectionModel().getSelected();
				var records = Ext.getCmp('panel_SRExpendPlan_list')
						.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
				} else {
					Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
						if (btn == 'yes') {
							if (records) {
								for (var i = 0; i < records.length; i++) {
									Ext.getCmp('panel_SRExpendPlan_list').store
											.remove(records[i]);
									var id = records[i].get("SRExpendPlan$id");
									Ext.Ajax.request({
										url : webContext
												+ '/extjs/pageData?method=removeGridColumn',
										params : {
											panel : 'panel_SRExpendPlan_list',
											id : id
										},
										timeout : 100000,
										success : function(response) {
											var r = Ext
													.decode(response.responseText);
											if (!r.success) {
												Ext.Msg.alert("��ʾ��Ϣ", "����ɾ��ʧ��",
														function() {

														});
											}
//remove by musicbear for ɾ��һ��δ��������ʱ��ɾ������δ���������											
//											Ext
//													.getCmp('panel_SRExpendPlan_list').store
//													.reload();
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
		var saveExpendPlanButton = new Ext.Button({
			text : '�ݴ�',
			pressed : true,
			iconCls : 'save',
			handler : function() {
				// var reqId = this.dataId;
				var store = Ext.getCmp('panel_SRExpendPlan_list').store;
				var info = "";
				store.each(function(record) {
					if (record.dirty) {
						info += Ext.encode(record.data) + ",";
					}
				});
				info = unicode(info);
				Ext.Ajax.request({
					url : webContext + '/SRAction_saveExpendPlan.action',
					params : {
						info : info,
						reqId : reqId
					},
					success : function(response, options) {
						Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {
							store.reload();
								// store.removeAll();
								// removedIds = "";
							});

					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}

				})
			}
		});
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
			title : "<font color=red>���Ի�����֧���ƻ�</font>",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [addExpendPlanButton, removeExpendPlanButton,
					saveExpendPlanButton]
				// [getGridButtons]
					,
			bbar:[this.testFilePanel]
		});
		if (this.dataId != '0') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("SpecialRequirement$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("SpecialRequirement$id").getValue();
			}
			var str = '{' + '\"' + "specialRequire" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';// ����Ҫ��ʱ���
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			// alert(str);
			this.store.load({
				params : param
			});
		}
		return this.gridpanel_SRExpendPlan_list;
	},
//add by muiscbear for ��Ӹ��Ի�����ƻ� in 2009 11 6 end	
	
	ciModify:function(){//�����������
			window.location = webContext
							+ '/user/configNewModify/configNewModify.jsp?modifyObjectId='+this.dataId
							+ "&backUrl=/user/require/specialRequire1/makeInfoByEngineer.jsp?dataId="
							+"&modifyType=specialRequire";
	},
	getTestFilePanel : function(){
		var curDataId = this.dataId;
		this.testFilePanel = new Ext.Panel({
			layout:'table',
			width:800,
			layoutConfig:{columns:4},
			items:[
			{fieldLabel:'����',
				xtype:'button',
				text:'<font color=red>�ϴ�����</font>',
				width:800,
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
							'com.zsgj.info.appframework.metadata.entity.SystemFile','DeptMailattachment');
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ȡ����ʧ��");
					}
					})
				}
			},
			{id:'DeptMailattachment',width:500,border : false,html:'',cls : 'common-text',style : 'width:500;text-align:left;'}]
		});
		
		return this.testFilePanel;
	},
	items : this.items,
	/*
	 * clientvalidation ��ʼ��
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
		this.model = "nsr1_makeInfoByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"nsr1_makeInfoByEngineer", this);
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
		var serviceId = sra.getReqServiceItemId(this.dataId);
		this.getFormpanel_SpecialRequire3_Input();
		this.pa.push(this.formpanel_SpecialRequire3_Input);
		this.formname.push("panel_SpecialRequire3_Input");
		temp.push(this.formpanel_SpecialRequire3_Input);

		this.getFormpanel_SRServiceProviderInfo_Input();
		this.pa.push(this.formpanel_SRServiceProviderInfo_Input);		
		this.formname.push("panel_SRServiceProviderInfo_Input");
		temp.push(this.formpanel_SRServiceProviderInfo_Input);
		
//add by muiscbear for ��Ӹ��Ի�����ƻ� in 2009 11 6 start		
		this.getGridpanel_SRExpendPlan_list();
		this.pa.push(this.gridpanel_SRExpendPlan_list);
		this.formname.push("panel_SRExpendPlan_list");
		temp.push(this.gridpanel_SRExpendPlan_list);
//add by muiscbear for ��Ӹ��Ի�����ƻ� in 2009 11 6 end
		
		this.getFormpanel_SRAnalyse();
		this.pa.push(this.formpanel_SRAnalyse);
		this.formname.push("panel_SRAnalyse");
		temp.push(this.formpanel_SRAnalyse);
		//		if (serviceId != '0') {
		
		//		}
//remove by lee for ȥ����Ŀ�ƻ��ͱ��� in 20090901
//		this.projectPlanGrid = new projectPlanListPanel({
//			dataId : this.dataId,
//			rootId : ppId
//		});
//		this.pa.push(this.projectPlanGrid);
//		this.formname.push("projectPlanListPanel");
//		temp.push(this.projectPlanGrid);
//remove by lee for ȥ����Ŀ�ƻ��ͱ��� in 20090901
		this.getFormserviceItemBasePanel();
		this.pa.push(this.formserviceItemBasePanel2);
		this.formname.push("serviceItemBasePanel");
		temp.push(this.formserviceItemBasePanel2);
//		if (serviceId != '0') {
//			this.serviceORconfigShipTree = new PagteModelTreePanel({
//				dataId : this.dataId,
//				rootId : ppId
//			});
//			this.pa.push(this.serviceORconfigShipTree);
//			this.formname.push("tree");
//			temp.push(this.serviceORconfigShipTree);
//		}
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		this.items = items;
		this.on("ciModify",this.ciModify,this);
		this.on("saveServiceItem", this.saveServiceItem, this);
		this.on("selectServiceItem", this.selectServiceItem, this);
		this.on("saveProjectRequireAnalyse", this.saveProjectRequireAnalyse,this);
		this.on("saveProjectPlan", this.saveProjectPlan, this);
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		Ext.getCmp("modifyGrid").on("rowdblclick",this.lookModifyInfo,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})