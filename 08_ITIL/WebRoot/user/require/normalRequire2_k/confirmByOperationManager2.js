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

	saveAndSubmit : function() {
		var needId = this.dataId;
		var store = Ext.getCmp('panel_requireFactoryInfo_list_2').store;
		var product = "";
		var dataRecords = new Array();
		var i =0
		store.each(function(record) {			
			dataRecords[i++] = record;		
			if (record.dirty) {
				product += Ext.encode(record.data) + ",";
			}
	
		})
		function isChn(str){
		      var reg = /^[u4E00-u9FA5]+$/;
		      if(!reg.test(str)){
		       return false;
		      }
		      return true;
		}		
		for(var i=0;i < dataRecords.length;i++){
			var strFactoryId = dataRecords[i].get("itil_req_RequireFactoryInfo$factoryId");
			var strStockAddressId = dataRecords[i].get("itil_req_RequireFactoryInfo$stockAddressId");
			if(strFactoryId==""||strFactoryId==null){
				Ext.MessageBox.alert("��ʾ","�������Ϊ������,����д������лл��������");	
				return false;
			}else if(strFactoryId.length!=4){
				Ext.MessageBox.alert("��ʾ","������ų���Ϊ4���ַ�,���޸ġ�лл��������");	
				return false;
			}
			else if(!isChn(strFactoryId)){
				Ext.MessageBox.alert("��ʾ","������Ų�����������,���޸ġ�лл��������");	
				return false;
			}
			if(strStockAddressId==""||strStockAddressId==null){
				Ext.MessageBox.alert("��ʾ","���ر��Ϊ������,����д������лл��������");	
				return false;
			}else if(strStockAddressId.length!=4){
				Ext.MessageBox.alert("��ʾ","���ر�ų���Ϊ4���ַ�,���޸ġ�лл��������");	
				return false;
			}
			else if(!isChn(strStockAddressId)){
				Ext.MessageBox.alert("��ʾ","���ر�Ų�����������,���޸ġ�лл��������");	
				return false;
			}				
				if(dataRecords[i].get("itil_req_RequireFactoryInfo$wareHouseId").length>3){
					Ext.MessageBox.alert("��ʾ","�ֿ��/��ϲֿⳤ��Ϊ3���ַ�,���޸ġ�лл��������");	
					return false;
				}
		}	
		Ext.Ajax.request({
			url : webContext + '/requireAction_saveGridInfo.action?dataId='+this.dataId,
			params : {
					product : product
			},
			success : function(response, options) {
				window.parent.auditContentWin.audit();
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
		
	},
	removePlan : function() {
		var record = Ext.getCmp('panel_requireFactoryInfo_list_2').getSelectionModel().getSelected();
		var records = Ext.getCmp('panel_requireFactoryInfo_list_2').getSelectionModel().getSelections();
		var store = Ext.getCmp('panel_requireFactoryInfo_list_2').store;
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
		} else {
			Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(btn) {
				if (btn == 'yes') {
					var remIds = "";
					if (records) {
						for (var i = 0; i < records.length; i++) {
							this.store.remove(records[i]);
							remIds += records[i].get("itil_req_RequireFactoryInfo$id") + ",";
						}
						Ext.Ajax.request({
							url : webContext
									+ '/SRAction_removeRequireFactoryInfo.action',
							params : {
								removedIds : remIds
							},
							success : function(response, options) {
								Ext.MessageBox.alert("��ʾ", "ɾ���ɹ�", function() {
									store.reload();
									store.removeAll();
								});

							},
							failure : function(response, options) {
								Ext.MessageBox.alert("ɾ��ʧ��");
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
			title : tabTitle,
			enableTabScroll : true,
			// minTabWidth:100,
			// resizeTabs : true,
			deferredRender : false,
			frame : true,
			// plain : true,
			autoScroll : true,
			border : true,
			// tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			// width : 800,
			// bodyBorder : true,
			// defaults : {
			// bodyStyle : 'padding:2px',
			// autoFill:true
			// },
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

	getFormpanel_ERP_NormalNeed1_Input : function() {
		var da = new DataAction();
		var data = null;
		// �ж������������޸�
		var biddata = "";

		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(
				"panel_ERP_NormalNeed5_Input", this);
		if (this.dataId != '0') {
			data = da.getPanelElementsForEdit("nr_confirmByOperationManager2_k",
					"panel_ERP_NormalNeed5_Input", this.dataId);// ����Ҫ��ʱ���
			for (i = 0; i < data.length; i++) {
				if ((data[i].id != 'ERP_NormalNeed$includeAndExpend')
						&& (data[i].id != 'ERP_NormalNeed$isWMCombo')) {
					if ((data[i].xtype == 'combo' || data[i].xtype == 'datefield')) {
						data[i].id = data[i].id + 8;// �ı�Combobox��id
						data[i].disabled = true;
						data[i].hideTrigger = true;
					}
//remove by lee for �����ɱ༭ in 20091221 begin
//					if (data[i].xtype == "panel") {
//						var dd = Ext.encode(data[i]).replace(/display:/g,
//								"display:none").replace("\"disabled\":false",
//								"\"disabled\":true");
//						data[i] = Ext.decode(dd);
//					}
//remove by lee for �����ɱ༭ in 20091221 end
					data[i].disabled = true;
					// data[i].readOnly = true;
				} else if (data[i].id == 'ERP_NormalNeed$isWMCombo') {
					data[i].allowBlank = false;
					data[i].on('select', function(combo, record, index) {
						var s = Ext.getCmp('ERP_NormalNeed$isWMCombo').getValue();
						if (s == '1') {
							Ext.getCmp('ERP_NormalNeed$includeAndExpend').enable();
						} else {
							Ext.getCmp('ERP_NormalNeed$includeAndExpend').reset();
							Ext.getCmp('ERP_NormalNeed$includeAndExpend').disable();
						}
					})
				} else if (data[i].id == 'ERP_NormalNeed$includeAndExpend') {
					data[i].disabled = true;
					// data[i].allowBlank = false;
				}
			}
		} else {
			data = da.getPanelElementsForAdd("panel_ERP_NormalNeed5_Input");
		}
		var modifyData = new Array();
		for (var i = 0; i < data.length; i++) {
			if(i==9){
				modifyData.push(this.getpanel_requireFactoryInfo_list_1());
			}
			modifyData.push(data[i]);
		}
		biddata = da.split(modifyData);			
		if (this.getFormButtons.length != 0) {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed5_Input',
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
				title : "ERP�����������",
				items : biddata,
				buttons : this.getFormButtons
			});
		} else {
			this.formpanel_ERP_NormalNeed1_Input = new Ext.form.FormPanel({
				id : 'panel_ERP_NormalNeed5_Input',
				layout : 'table',
				height : 800,
				width : 500,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "ERP�����������",
				items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed1_Input;
	},
	getpanel_requireFactoryInfo_list_1:function(){
		var da = new DataAction();
		var obj = da.getPanelElementsForHead("panel_requireFactoryInfo_list_2");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("panel_requireFactoryInfo_list_2",
				this);
		var getGridButtons = buttonUtil.getButtonForPanel("panel_requireFactoryInfo_list_3",
				this);
		var modifyButtons = new Array();
			for(var i = 0;i<getGridButtons.length;i++){
//				if (getGridButtons[i].id=='ext-comp-1019') {
//					modifyButtons.push(getGridButtons[i]);
//				}	
			}					
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
		this.store = da.getPagePanelJsonStore("panel_requireFactoryInfo_list_2", obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		var gridpanel_requireFactoryInfo_list_1 = new Ext.grid.EditorGridPanel({
			fieldLabel : '������ϸ',
			id : 'panel_requireFactoryInfo_list_2',
			title : '<br>������Ĭ�ϲ���WM�������������Ҫ��WM���������������ڿ��غ���д�ֿ�š�<br>',
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
//			collapsible : true,
			autoScroll : true,
			height : 220,
			width : 9999,// this.width - 15,
			frame : true,
			tbar : [modifyButtons],         
			viewConfig: {   
                  forceFit:true   
            },   
           bbar: new Ext.PagingToolbar({   
           	    id:'pagId',
                pageSize: 10,   
                store: this.store,   
               displayInfo: true
           }) 			
		});
		if (this.dataId != '') {
			var pcnameValue = "";
			var fcnameObj = Ext.getCmp("ERP_NormalNeed$id");
			if (fcnameObj != undefined) {
				pcnameValue = Ext.getCmp("ERP_NormalNeed$id").getValue();
			}
			var str = '{' + '\"' + "requireData" + '\"' + ':' + '\"'
					+ pcnameValue + '\"' + '}';
			var param = eval('(' + str + ')');
			param.methodCall = 'query';
			param.start = 0;
			this.store.baseParams=param;
			this.store.load();
		}	
	return 	gridpanel_requireFactoryInfo_list_1;
		
	}, 	
	items : this.items,
	buttons : this.buttons,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {

		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.digitalchina.itil.require.entity.ERP_NormalNeed"
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
		this.model = "nr_confirmByOperationManager2_k";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel(
				"nr_confirmByOperationManager2_k", this);
		this.buttons = new Array();
		if (this.readOnly != 1) {
			this.buttons = this.mybuttons;
		}

		this.getFormpanel_ERP_NormalNeed1_Input();
		this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		this.formname.push("panel_ERP_NormalNeed5_Input");
		temp.push(this.formpanel_ERP_NormalNeed1_Input);
		temp.push(histroyForm);
		items.push(this.getTabpanel(temp));

		this.items = items;
		this.on('saveAndSubmit', this.saveAndSubmit, this);
		this.on("removePlan", this.removePlan, this);				
		PageTemplates.superclass.initComponent.call(this);
	}
})