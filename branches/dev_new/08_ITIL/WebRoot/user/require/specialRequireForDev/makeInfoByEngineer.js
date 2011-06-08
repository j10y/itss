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
	//�������������ͬʱ���渶��ƻ��б�
	saveAndSubmit : function() {
		if (!Ext.getCmp('panel_srInfo_input1').form.isValid()) {
			Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ������,����д����������.");
			return false;
		}			
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
		var formParam = Ext.encode(getFormParam('panel_srInfo_input1'));	

		var curdataId = this.dataId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveSpecialRequirementInfo.action',
			params : {
				info:formParam,
				reqId:curdataId
				},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var tempId = responseArray.id;
				Ext.getCmp('itil_req_SpecialRequirementInfo$id').setValue(tempId);
				window.parent.auditContentWin.specialAudit();
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	
	getTabpanel : function(tab,tabTitle){
		var tabPanel = new Ext.TabPanel({      
			activeTab : 0,
            enableTabScroll:true, 
            title:tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
            border : false, 
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			defaults : {
				autoHeight : true
			},
			items : tab
		});
		return tabPanel;

	},
	getPanel : function(appa,panelTitle) {
		var Panel = new Ext.Panel({
			id : Ext.id(),
			height : 'auto',
			align : 'center',
			title:panelTitle,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return Panel;

	}, 
	getServiceItemAndConfigItemPanel:function(){
		var backUrl=webContext+'/user/require/specialRequireForDev/makeInfoByEngineer.jsp?dataId='+this.dataId+'&readOnly='+this.readOnly;
		backUrl=escape(backUrl);
		var url=webContext+'/user/configItem/configItemBatchModify/configItemBatchModifyListForExterior.jsp?typeId='+this.dataId+"&type=specialRequirement&backUrl="+backUrl;
		var panel=new Ext.TabPanel({
			id:"serviceItemAndConfigItemPanel",
			deferredRender : false,
			title:"����&�������",
			activeTab:1,
			width:800,
			autoScroll : true,
            items:[
            	this.getFormserviceItemBasePanel(),
            	new Ext.Panel({
            		id:"configItemPanel",
            		title:"�����",
            		height:300,
            		frame:true,
        			html:"<IFRAME SRC='"+url+"' width='100%' height='100%' frameborder='0'></IFRAME>"
            	})
            ]
		})
		return panel;
	},
//���������	
 getFormpanel_SpecialRequireDevConfirm_Input : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_SpecialRequireDevConfirm_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_makeInfoByEngineer",
					"panel_SpecialRequireDevConfirm_Input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_SpecialRequireDevConfirm_Input");
			
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].id != 'SpecialRequirement$attachment') {
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			//add by awen for changge fieldLable on 2011-06-08 begin
			if (data[i].id=='SpecialRequirement$confirmUserCombo') {
				data[i].fieldLabel = '����������';
				data[i].readOnly = true;				
				data[i].hideTrigger = true;					
			}
			//add by awen for changge fieldLable on 2011-06-08 end
		}	
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			var formpanel_SpecialRequireDevConfirm_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequireDevConfirm_Input',
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
				title : "�����������������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			var formpanel_SpecialRequireDevConfirm_Input = new Ext.form.FormPanel({
				id : 'panel_SpecialRequireDevConfirm_Input',
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
				title : "�����������������",
				items : biddata
			});
		}
		return formpanel_SpecialRequireDevConfirm_Input;
	},
 getFormpanel_srInfo_input1 : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_srInfo_input1",this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_makeInfoByEngineer",
					"panel_srInfo_input1", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input1");
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			var formpanel_srInfo_input1 = new Ext.form.FormPanel({
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
				title : "��Ŀ�������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			var formpanel_srInfo_input1 = new Ext.form.FormPanel({
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
				title : "��Ŀ�������",
				items : biddata
			});
		}
		return formpanel_srInfo_input1;
	},
 getFormserviceItemBasePanel : function() {
 		var siId = "0";
 		var url = webContext+'/SRAction_getReqServiceItemId.action?reqId='+this.dataId;
 		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post",url, false);
		conn.send(null);
		if (conn.status == "200") {
			var curData = eval('('+conn.responseText+')');
			siId = curData.id;
		} else {
			alert("��ʱ��������ȡ����");
		}
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("serviceItemBasePanel", this);
		if (siId != '0') {
			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",siId);// ����Ҫ��ʱ���
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
		if (this.getFormButtons.length != 0) {
			var formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "�������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			var formserviceItemBasePanel = new Ext.form.FormPanel({
				id : 'serviceItemBasePanel',
				layout : 'table',
				height : 'auto',
				frame : true,
				width : 800,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "�������",
				items : biddata
			});
		}
		return formserviceItemBasePanel;
	},
		addServiceItem : function() {
		var dataId = this.dataId;
		var record = Ext.getCmp('gridpanel').getSelectionModel().getSelected();
		var records = Ext.getCmp('gridpanel').getSelectionModel().getSelections();
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
							+ "/user/require/specialRequireForDev/makeInfoByEngineer.jsp?"
							+ "dataId=" + dataId + "&tabNumer="
							+ 'serviceItemBasePanel';
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
		if (!Ext.getCmp('serviceItemBasePanel').form.isValid()) {
			Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ������,����д����������.");
			return false;
		}		
		var formParam =getFormParam('serviceItemBasePanel');
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
						+ "/user/require/specialRequireForDev/makeInfoByEngineer.jsp?"
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
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
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
		this.pageBarservice = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.storeservice,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
//		this.formValue = '';
//		this.pageBarservice.formValue = this.formValue;
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
			'start' : 0
		};

//		this.pageBarservice.formValue = param;
		this.storeservice.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});


		this.storeservice.removeAll();
		this.storeservice.load({
			params : param
		});

		return this.gridpanel;

	},
   /**
    * ��װ��������壬����������������������
    */
   getFormserviceItemPanel : function() {
   	
   },
	getGridpanel_SRExpendPlan_list : function() {
		var curReqId = this.dataId;
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
				editor.readOnly=false;//2010-09-16 add by huzh for bug
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
				var record = Ext.getCmp('panel_SRExpendPlan_list').getSelectionModel().getSelected();
				var records = Ext.getCmp('panel_SRExpendPlan_list').getSelectionModel().getSelections();
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
									Ext.getCmp('panel_SRExpendPlan_list').store.remove(records[i]);
									var id = records[i].get("SRExpendPlan$id");
									Ext.Ajax.request({
										url : webContext+ '/extjs/pageData?method=removeGridColumn',
										params : {
											panel : 'panel_SRExpendPlan_list',
											id : id
										},
										timeout : 100000,
										success : function(response) {
											var r = Ext.decode(response.responseText);
											if (!r.success) {
												Ext.Msg.alert("��ʾ��Ϣ", "����ɾ��ʧ��",
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
		var saveExpendPlanButton = new Ext.Button({
			text : '�ݴ�',
			pressed : true,
			iconCls : 'save',
			handler : function() {
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
						reqId : curReqId
					},
					success : function(response, options) {
						Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {
							store.reload();
							});
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("����ʧ��");
					}
				})
			}
		});
		getGridButtons.push(addExpendPlanButton);
		getGridButtons.push(removeExpendPlanButton);
		getGridButtons.push(saveExpendPlanButton);
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("panel_SRExpendPlan_list", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_SRExpendPlan_list = new Ext.grid.EditorGridPanel({
			id : 'panel_SRExpendPlan_list',
			store : this.store,
			cm : this.cm,
			sm : sm,
			//title : "���Ի�����֧���ƻ�",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			height : 400,
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
					+ pcnameValue + '\"' + '}';// ����Ҫ��ʱ���
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			this.store.load({
				params : param
			});
		}
		return gridpanel_SRExpendPlan_list;
	},
	//����ƻ�����
	getFormpanel_SRPaymentPlanFile_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("devsr_makeInfoByEngineer",
					"panel_SRPaymentPlanFile_Input", this.dataId);// ����Ҫ��ʱ���
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("panel_SRPaymentPlanFile_Input");
			biddata = da.split(data);
		}
			var formpanel_SRPaymentPlanFile_Input = new Ext.form.FormPanel({
				id : 'panel_SRPaymentPlanFile_Input',
				layout : 'table',
				height : 50,
				width : 800,
				frame : true,
//				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				//title : "���󸶿�ƻ�����",
				items : biddata
			});
		return formpanel_SRPaymentPlanFile_Input;
	},
  items : this.items,
  buttons : this.buttons,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.SpecialRequirement"
		});
		var items = new Array();
		var temp = new Array();
		this.model = "devsr_makeInfoByEngineer";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("devsr_makeInfoByEngineer", this);
		this.buttons = new Array();
		if (this.readOnly != "1") {
			this.buttons = this.mybuttons;
		}
		temp.push(this.getFormpanel_SpecialRequireDevConfirm_Input());
		temp.push(this.getFormpanel_srInfo_input1());
		temp.push(this.getServiceItemAndConfigItemPanel());
		temp.push(this.getPanel([this.getGridpanel_SRExpendPlan_list(),this.getFormpanel_SRPaymentPlanFile_Input()],"����ƻ�"));
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));
		//items.push(this.buttons);
		
		this.items = items;
		this.on("saveServiceItem", this.saveServiceItem, this);
		this.on("selectServiceItem", this.selectServiceItem, this);
		this.on("saveProjectRequireAnalyse", this.saveProjectRequireAnalyse,this);
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		PageTemplates.superclass.initComponent.call(this);
	}
})