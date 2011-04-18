PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	buttonAlign:'center',
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	saveAndSubmit : function() {
	    var reqId = this.dataId;
		if (!Ext.getCmp('panel_srInfo_input1').form.isValid()) {
			Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ������,����д����������.");
			return false;
		}
		var formParam = Ext.encode(getFormParam('panel_srInfo_input1'));
		var curdataId = this.dataId;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveSpecialRequirementInfo.action',
			params : {
				info : formParam,
				reqId : curdataId
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
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return tabPanel;

	},
	getPanel : function(appa,panelTitle) {
		var Panel = new Ext.Panel({
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
	
//���������	
 getFormpanel_erpSR_it_input : function() {
		var da = new DataAction();
		var data = null;
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_erpSR_it_input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_makeProjectPlan",
					"panel_erpSR_it_input", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_erpSR_it_input");
			
		}
		for (i = 0; i < data.length; i++) {
			if (data[i].id != 'SpecialRequirement$attachment') {
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
		}	
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_erpSR_it_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_it_input',
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
				title : "ERP�ǳ�������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_erpSR_it_input = new Ext.form.FormPanel({
				id : 'panel_erpSR_it_input',
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
				title : "ERP�ǳ�������",
				items : biddata
			});
		}
		return this.formpanel_erpSR_it_input;
	},
 getFormpanel_srInfo_input1 : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("panel_srInfo_input1",this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("esr_makeProjectPlan",
					"panel_srInfo_input1", this.dataId);// ����Ҫ��ʱ���
		} else {
			data = da.getPanelElementsForAdd("panel_srInfo_input1");
		}
		biddata = da.split(data);
		if (this.getFormButtons.length != 0) {
			this.formpanel_srInfo_input1 = new Ext.form.FormPanel({
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
			this.formpanel_srInfo_input1 = new Ext.form.FormPanel({
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
		return this.formpanel_srInfo_input1;
	},
// getFormserviceItemBasePanel : function() {
// 		var siId = "0";
// 		var url = webContext+'/SRAction_getReqServiceItemId.action?reqId='+this.dataId;
// 		var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		conn.open("post",url, false);
//		conn.send(null);
//		if (conn.status == "200") {
//			var curData = eval('('+conn.responseText+')');
//			siId = curData.id;
//		} else {
//			alert("��ʱ��������ȡ����");
//		}
//		 
//		var da = new DataAction();
//		var data = null;
//		// �ж������������޸�
//		var biddata = "";
//
//		var buttonUtil = new ButtonUtil();
//		this.getFormButtons = buttonUtil.getButtonForPanel("serviceItemBasePanel", this);
//		if (siId != '0') {
//			data = da.getSingleFormPanelElementsForEdit("serviceItemBasePanel",siId);// ����Ҫ��ʱ���
//		} else {
//			data = da.getPanelElementsForAdd("serviceItemBasePanel");
//		}
//		for (i = 0; i < data.length; i++) {
//			if (data[i].id == 'ServiceItem$serviceItemCode') {
//				data[i].readOnly = true;
//				data[i].emptyText = '�Զ�����';
//			}
//		}
//		biddata = da.split(data);
//		if (this.getFormButtons.length != 0) {
//			this.formserviceItemBasePanel = new Ext.form.FormPanel({
//				id : 'serviceItemBasePanel',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "��ط�����",
//				items : biddata,
//				buttons : this.getFormButtons
//			});
//		} else {
//			this.formserviceItemBasePanel = new Ext.form.FormPanel({
//				id : 'serviceItemBasePanel',
//				layout : 'table',
//				height : 'auto',
//				width : 800,
//				frame : true,
//				collapsible : true,
//				defaults : {
//					bodyStyle : 'padding:4px'
//				},
//				layoutConfig : {
//					columns : 4
//				},
//				title : "��ط�����",
//				items : biddata
//			});
//		}
//		return this.formserviceItemBasePanel;
//	},
//		addServiceItem : function() {
//		var dataId = this.dataId;
//		var record = Ext.getCmp('gridpanel').getSelectionModel().getSelected();
//		var records = Ext.getCmp('gridpanel').getSelectionModel().getSelections();
//		var store = this.storeservice;
//		if (!record) {
//			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ӵ���!");
//			return;
//		}
//		if (records.length > 1) {
//			Ext.MessageBox.alert('��ʾ', '���ѡ��һ����Ϣ���������!');
//		}
//		var newdataId = record.get('ServiceItem$id');
//		Ext.Ajax.request({
//			url : webContext + '/SRAction_saveServiceItemReShip.action?'
//					+ 'reqId=' + dataId + '&newdataId=' + newdataId,
//
//			success : function(response, options) {
//				Ext.MessageBox.alert("��ʾ", "��ӳɹ�", function() {
//					Ext.getCmp('winid').close();
//					window.location = webContext
//							+ "/user/require/specialRequireForDev/makeInfoByEngineer.jsp?"
//							+ "dataId=" + dataId + "&tabNumer="
//							+ 'serviceItemBasePanel';
//				});
//			},
//			failure : function(response, options) {
//				Ext.MessageBox.alert("���ʧ��");
//			}
//		}, this);
//	},
//	reset : function() {
//		Ext.getCmp('searchPanel').form.reset();
//	},
//	createSearch2 : function(btnName, link, imageUrl, scope) {
//		var param = Ext.getCmp('searchPanel').form.getValues(false);
//		param.methodCall = 'query';
//		param.start = 1;
//		this.formValue = param;
//		this.pageBarservice.formValue = this.formValue;
//		this.storeservice.removeAll();
//		this.storeservice.load({
//			params : param
//		});
//
//		return null;
//	},
//	saveServiceItem : function() {
//	    var reqId = this.dataId;
//		if (!Ext.getCmp('serviceItemBasePanel').form.isValid()) {
//			Ext.MessageBox.alert("��ʾ", "ҳ���д���ɫ�����ߵ�Ϊ������,����д����������.");
//			return false;
//		}		
//		var formParam = Ext.getCmp('serviceItemBasePanel').form.getValues(false);
//		var vp = null;
//		if (formParam != null) {
//			vp = Ext.apply(formParam, vp, {});
//		}
//		var curdataId = this.dataId;
//		Ext.Ajax.request({
//			url : webContext + '/SRAction_saveServiceItemForRequire.action?'
//					+ '&reqId=' + this.dataId,
//			params : vp,
//			success : function(response, options) {
//				Ext.MessageBox.alert("����ɹ�");
//				window.location = webContext
//						+ "/user/require/specialRequireForDev/makeInfoByEngineer.jsp?"
//						+ "dataId=" + curdataId + "&tabNumer="
//						+ 'serviceItemBasePanel';
//			},
//			failure : function(response, options) {
//				Ext.MessageBox.alert("����ʧ��");
//			}
//		}, this);
//	},
//	selectServiceItem : function() {
//		var createPanel2 = this.getGridPanel();
//		this.fp = this.getSearchForm();
//		var win1 = new Ext.Window({
//			id : 'winid',
//			title : '������',
//			height : 400,
//			width : 700,
//			constrain : false,
//			constrainHeader : true,
//			modal : true,
//			resizable : false,
//			draggable : true,
//			layout : 'border',
//			scope : this,
//			items : [this.fp, createPanel2],
//			buttons : [{
//				text : '�ر�',
//				handler : function() {
//					win1.close();
//				},
//				listeners : {
//					'beforeclose' : function(p) {
//						return true;
//					}
//				},
//				scope : this
//			}]
//		});
//		win1.show();
//	},
//	getSearchForm : function() {
//		var da = new DataAction();
//		var data = da.getPanelElementsForQuery("SerivceItemListGrid");
//		var biddata = da.split(data);
//		this.panel = new Ext.form.FormPanel({
//			id : 'searchPanel',
//			region : "north",
//			layout : 'table',
//			height : 'auto',
//			width : 300,
//			frame : true,
//			collapsible : true,
//			defaults : {
//				bodyStyle : 'padding:6px'
//			},
//			layoutConfig : {
//				columns : 4
//			},
//			items : biddata
//		});
//		return this.panel;
//	},
//	getGridPanel : function() {
//		var da = new DataAction();
//		this.fp = this.getSearchForm();
//		var obj = da.getListPanelElementsForHead("SerivceItemListGrid");
//		var sm = new Ext.grid.CheckboxSelectionModel();
//		var columns = new Array();
//		columns[0] = sm;
//		for (var i = 0; i < obj.length; i++) {
//			var headItem = obj[i];
//			var title = headItem.header;
//			var alignStyle = 'left';
//			var propertyName = headItem.dataIndex;
//			var renderer = headItem.renderer;
//			var hide = headItem.hidden;
//			var isHidden = false;
//			if (hide == 'true') {
//				isHidden = true;
//			}
//			var columnItem = "";
//			columnItem = {
//				header : title,
//				dataIndex : propertyName,
//				sortable : true,
//				hidden : isHidden,
//				align : alignStyle
//			}
//
//			columns[i + 1] = columnItem;
//		}
//		this.cm = new Ext.grid.ColumnModel(columns);
//		this.storeservice = da
//				.getPagePanelJsonStore("SerivceItemListGrid", obj);
//		this.storeservice.paramNames.sort = "orderBy";
//		this.storeservice.paramNames.dir = "orderType";
//		this.cm.defaultSortable = true;
//		var viewConfig = Ext.apply({
//			forceFit : true
//		}, this.gridViewConfig);
//		this.pageBarservice = new Ext.PagingToolbarExt({
//			pageSize : 10,
//			store : this.storeservice,
//			displayInfo : true,
//			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
//			emptyMsg : "����ʾ����"
//		});
//		this.formValue = '';
//		this.pageBarservice.formValue = this.formValue;
//		this.gridpanel = new Ext.grid.EditorGridPanel({
//			id : "gridpanel",
//			region : "center",
//			rowspan : 1,
//			store : this.storeservice,
//			width : 100,
//			frame : true,
//			cm : this.cm,
//			sm : sm,
//			trackMouseOver : false,
//			loadMask : true,
//			height : 320,
//			tbar : [{}, {
//				text : '�� ѯ',
//				pressed : true,
//				handler : this.createSearch2,
//				scope : this,
//				iconCls : 'search'
//			}, '|', {
//				text : '�� ��',
//				pressed : true,
//				handler : this.reset,
//				scope : this,
//				iconCls : 'reset'
//			}, '|', {
//				text : '�� ��',
//				pressed : true,
//				handler : this.addServiceItem,
//				scope : this,
//				iconCls : 'add'
//			}, '   '],
//			bbar : this.pageBarservice
//
//		});
//		var param = {
//			'mehtodCall' : 'query',
//			'start' : 1
//		};
//
//		this.pageBarservice.formValue = param;
//
//		this.storeservice.removeAll();
//		this.storeservice.load({
//			params : param
//		});
//
//		return this.gridpanel;
//
//	},
//	modifyGridInfo:function(){
//   
//   		var sm = new Ext.grid.CheckboxSelectionModel();// ��ѡ��
//		var cm = new Ext.grid.ColumnModel([sm,{header:'id',dataIndex:'id',hidden:true,sortable:true},
//											{header:'������',dataIndex:'modifyNo',sortable:true},
//											{header:'�������',dataIndex:'name',sortable:true},
//											{header:'�������',dataIndex:'descn',sortable:true},
//											{header:'���ԭ��',dataIndex:'reason',sortable:true},
//											{header:'����ύ��',dataIndex:'applyUser',sortable:true},
//											{header:'����ύ����',dataIndex:'applyDate',sortable:true}
//										
//											]);
//		this.storeChild=new Ext.data.JsonStore({
//				url : webContext
//						+'/ciRelationShip_getModifyInfoListByProblemId.action?objectId='+this.dataId+"&modifyType=specialRequire",//?&configItemId='+this.configItemId+"&itemFlag="+itemFlag,
//				fields : ['id', 'modifyNo','name','descn','reason','applyUser','applyDate'],
//				totalProperty : 'rowCount',
//				root : 'data',
//				id : 'id'
//				
//		});
//		this.storeChild.paramNames.sort = "orderBy";
//		this.storeChild.paramNames.dir = "orderType";
//		this.pageBar = new Ext.PagingToolbarExt({
//			pageSize :10,// ʹ�õ���ϵͳĬ��ֵ
//			store : this.storeChild,
//			displayInfo : true,
//			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
//			emptyMsg : "����ʾ����"
//		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
//		this.grid = new Ext.grid.GridPanel({
//			id:"modifyGrid",
//			title:"�����Ϣ   <font color=black style='font-weight:lighter'  face=����_GB2312>��˫���鿴�����Ϣ��</font>",
//			store : this.storeChild,
//			collapsible : true,
//			cm : cm,
//			sm : sm,
//			height:200,
//			width:800,
//			frame:true,
//			border:true,
//			trackMouseOver : false,
//			loadMask : true,
//			y : 40,
//			anchor : '0 -38',
//			bbar : this.pageBar			
//			});
//		var param = {
//			'start' : 1
//			};
//		this.pageBar.formValue = param;
//		this.storeChild.load({
//			params : param
//		});
//		return this.grid; 
//   
//   
//   },
//   //�鿴���������������Ϣ
//   lookModifyInfo:function(){
//   		var record = Ext.getCmp("modifyGrid").getSelectionModel().getSelected();
//	    var records = Ext.getCmp("modifyGrid").getSelectionModel().getSelections();
//        if (!record) {
//	         Ext.Msg.alert("��ʾ", "����ѡ��鿴�ļ�¼!");
//	         return;
//           }
//         if (records.length == 0) {
//	      Ext.MessageBox.alert('����', 'ֻ��ѡ��һ����Ϣ!');
//	      return;
//        }
//        var modifyId = record.get("id");
//        var url = webContext+ '/user/configNewModify/ciModifyFirstLook.jsp';
//
//		this.configItemModifyWindow = new Ext.Window({				
//			title : '�����Ϣ����',
//			modal : true,
//			height : 500,
//			width : 800,
//			resizable : true,
//			id : "modifyInfoWindow",
//			draggable : true,
//			buttons : [{
//				buttonAlign : 'center',
//				text : '�ر�',
//				pressed : true,
//				handler : function() {
//					Ext.getCmp("modifyInfoWindow").close();
//					// Ext.getCmp("tree").root.reload();
//				}
//			}],
//			autoLoad : {
//				// װ�������й���Ϣ���������й�,�˴��ɰ��������ƴ��ݹ�ȥ��auditInfo.jsp�����������ƾ����Ƿ���Ҫ�޸���������
//				url : webContext + "/tabFrame.jsp?url=" + url+"?dataId="+modifyId,// +"****currentItemId="+currentItemId+"****flag=child",
//				text : "ҳ�����ڼ�����......",
//				method : 'post',
//				scope : this
//			},
//			viewConfig : {
//				autoFill : true,
//				forceFit : true
//			},
//			layout : 'fit',
//			buttonAlign : 'center',
//			items : [{
//				html : "���ڼ���ҳ������......"
//			}]
//
//		});
//		this.configItemModifyWindow.show();	
//   },
	
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.SpecialRequirement"
		});		
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
		this.model = "esr_makeProjectPlan";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"esr_makeProjectPlan", this);
		this.buttons = new Array();
		if(this.readOnly!=1){
			this.buttons =this.mybuttons;
		}
		this.getFormpanel_erpSR_it_input();
		this.pa.push(this.formpanel_erpSR_it_input);
		this.formname.push("panel_erpSR_it_input");
		temp.push(this.formpanel_erpSR_it_input);
		this.formpanel_srInfo_input1 = this.getFormpanel_srInfo_input1();
		this.pa.push(this.formpanel_srInfo_input1);
		this.formname.push("panel_srInfo_input1");
		temp.push(this.formpanel_srInfo_input1);
		//this.getFormserviceItemBasePanel();
		//this.modifyGridInfo();
		//temp.push(this.getPanel([this.formserviceItemBasePanel,this.grid],"������/������"));
		//temp.push(this.formserviceItemBasePanel);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		this.items = items;
		//this.on("ciModify",this.ciModify,this);
		//this.on("saveServiceItem", this.saveServiceItem, this);
		//this.on("selectServiceItem", this.selectServiceItem, this);
		this.on("saveProjectRequireAnalyse", this.saveProjectRequireAnalyse,this);
		this.on("saveProjectPlan", this.saveProjectPlan, this);
		this.on("saveAndSubmit", this.saveAndSubmit, this);
		//Ext.getCmp("modifyGrid").on("rowdblclick",this.lookModifyInfo,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})