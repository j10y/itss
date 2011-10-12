PagePanel = Ext.extend(Ext.Panel, {
	id : "KnowFileManager",
	closable : true,
	frame : true,
	 autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
			id : "searchForm",
			region : "north",
			layout : 'table',
			height : 60,
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "��ѯ����",
			items : [{
				html : '�ļ�����:',
				cls : 'common-text',
				style : 'width:70;text-align:right'
			}, new Ext.form.ComboBox({
				id : 'knowFileTypeCombo',
				fieldLabel : "�ļ�����",
				width : 200,
				hiddenName : 'knowFileType',
				displayField : 'name',
				valueField : 'id',
				resizable : true,
				emptyText :'��������б���ѡ��...',
				triggerAction : 'all',
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.itil.knowledge.entity.KnowFileType',
					fields : ['id', 'name'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['knowFileType'] == undefined) {
								opt.params['name'] = Ext
										.getCmp('knowFileTypeCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}

						this.store.load({
							params : {
								name : param,
								start : 0
							}
						});
						return true;
					}
				}
			}),{
				html : '�ļ�����:',
				cls : 'common-text',
				style : 'width:70;text-align:right'
			}, new Ext.form.TextField({
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'name',
				name : 'name',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			}), {
				html : '������:',
				cls : 'common-text',
				style : 'width:70;text-align:right'
			}, new Ext.form.ComboBox({
				id : 'createUserCombo',
				fieldLabel : "������",
				//width : 200,
				width : 220,//2010-04-16 modified by huzh for bug(��ҳ�����������һҳ�������ʾ������)
				hiddenName : 'createUser',
				displayField : 'userName',
				valueField : 'id',
				resizable : true,
				emptyText :'��������б���ѡ��...',
				triggerAction : 'all',
				store : new Ext.data.JsonStore({
					url : webContext
							+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
					fields : ['id', 'userName'],
					listeners : {
						beforeload : function(store, opt) {
							if (opt.params['createUser'] == undefined) {
								opt.params['userName'] = Ext
										.getCmp('createUserCombo').defaultParam;
							}
						}
					},
					totalProperty : 'rowCount',
					root : 'data',
					id : 'id'
				}),
				pageSize : 10,
				listeners : {
					'beforequery' : function(queryEvent) {
						var param = queryEvent.combo.getRawValue();
						this.defaultParam = param;
						if (queryEvent.query == '') {
							param = '';
						}

						this.store.load({
							params : {
								userName : param,
								start : 0
							}
						});
						return true;
					}
				}
			})]
		});
		return this.panel;
	},
	show : function() {
		var record = Ext.getCmp('KnowFileListGrid').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('KnowFileListGrid').getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�鿴���У�");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�鿴ʱֻ��ѡ��һ�У�");
			return;
		}
		var knowFileId = record.get('KnowFile$id');
		var da = new DataAction();
		var data = da.getKnowledgeFormPanelElementsForEdit("KnowLedgeFile_pagepanel", knowFileId);
		for(var i=0;i<data.length;i++){
			if(data[i].name=='KnowFile$number'){
			 	data[i].readOnly=true;
			}
		}
		var dataform = this.split(data);
		var knowForm = new Ext.form.FormPanel({
			id : 'knowledgeSkip',
			layout : 'table',
			width : 630,  
			height : 'auto',
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:6px'
			},
			frame : true,
			items : dataform
		});
		var windowSkip = new Ext.Window({
			title : '�鿴��ϸ��Ϣ',
			width : 648,  
			height : 'auto',
			modal : true,
			//maximizable : true,
			items : [knowForm],
			bodyStyle : 'padding:2px', 
			buttons : [{
			text : '�ݴ���',
			id : 'savelistbutton',
			handler : function() {
				var fileType=Ext.getCmp('KnowFile$knowFileTypeCombo');
					var name=Ext.getCmp('KnowFile$name');
					if(fileType.getValue().trim()==""){
						fileType.clearValue();
					}
					if(name.getValue().trim()==""){
						name.setValue('');
					}
				Ext.getCmp("savelistbutton").disable();
				Ext.getCmp("submitknowbutton").disable();
				Ext.getCmp("closebutton").disable();
				if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
//					var panelTypeParam = Ext.encode(Ext.getCmp("knowledgeSkip").form.getValues(false));
					var panelTypeParam = Ext.encode(getFormParam('knowledgeSkip'));
					Ext.Ajax.request({
						url : webContext+ '/knowledgeAction_saveKnowFileChangDraft.action',
						params : {
							panelTypeParam : panelTypeParam
						},
						method : 'post',
						success : function() {
							Ext.MessageBox.alert("��ʾ", "���������ݳɹ���",function(){
								windowSkip.close();
							});
							var params = Ext.getCmp("searchForm").getForm().getValues(false);
							var store = Ext.getCmp("KnowFileListGrid").getStore();
							params.start = 0;
							params.status = 1;
//							Ext.getCmp("pagetoolbar").formValue = param;
							store.on('beforeload', function(a) {   
							      Ext.apply(a.baseParams,params);   
							});
							store.load({
								params : params
							});
						},
						failure : function(response, options) {
							Ext.getCmp("savelistbutton").enable();
							Ext.getCmp("submitknowbutton").enable();
							Ext.getCmp("closebutton").enable();
							Ext.MessageBox.alert("��ʾ", "����������ʧ�ܣ�");
						}
					});
				}else{
					 Ext.getCmp("savelistbutton").enable();
					Ext.getCmp("submitknowbutton").enable();
					Ext.getCmp("closebutton").enable();
					 Ext.MessageBox.alert("��ʾ", "��ɫ�����߲���Ϊ�����"); 
				}
			},
			scope : this
			}, {
				text : '�ύ���',
				id : 'submitknowbutton',
				handler : function() {
					var fileType=Ext.getCmp('KnowFile$knowFileTypeCombo');
					var name=Ext.getCmp('KnowFile$name');
					if(fileType.getValue().trim()==""){
						fileType.clearValue();
					}
					if(name.getValue().trim()==""){
						name.setValue('');
					}
					Ext.getCmp("savelistbutton").disable();
					Ext.getCmp("submitknowbutton").disable();
					Ext.getCmp("closebutton").disable();
					if (Ext.getCmp("knowledgeSkip").getForm().isValid()) {
//						var panelTypeParam = Ext.encode(Ext
//								.getCmp("knowledgeSkip").form.getValues(false));
						var panelTypeParam = Ext.encode(getFormParam('knowledgeSkip'));		
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowFileChangDraft.action',
							params : {
								knowFileId : knowFileId ,
								panelTypeParam : panelTypeParam
							},
							method : 'post',
							success : function(response) {
								var r =Ext.decode(response.responseText);
                               if(r.success==false){
                             	 Ext.MessageBox.alert("��ʾ", "���ļ��ѱ�����������ڱ�������У��������ٴζ����ύ������룡��",function(){
								}); 
                               }else{
								var dataId = eval('('
										+ response.responseText + ')').dataId;
								//---------------------------------------------
								//�ύ���̣���ͬ������������ļ���
								var dataType = 7;//��������Id
								Ext.Ajax.request({
									url : webContext + '/configWorkflow_findProcessByPram.action',
									params : {
										modleType : 'Kno_File',//
										processStatusType : '1'//
									},
									success : function(response, options) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										var vpid = responseArray.vpid;
										if(vpid!=""&&vpid!=undefined&&vpid.length>0){
											Ext.Ajax.request({
												url : webContext
														+ '/knowledgeWorkflow_apply.action',
												params : {
													dataId : dataId,
													model : this.model,
													bzparam : "{dataId :'"
															+ dataId
															+ "',dataType : '"
															+ dataType
															+ "',applyId : '"
															+ dataId
															+ "', applyType: 'kproject',applyTypeName: '֪ʶ��������',customer:''}",
													defname : vpid
												},
												success:function(response){
													var meg = Ext.decode(response.responseText);
													if (meg.Exception != undefined) {
														Ext.Msg.alert("��ʾ",meg.Exception);
													}else{
														Ext.Msg.alert("��ʾ","�ύ�������ɹ���",function(){
															windowSkip.close();
														});
														var param = Ext.getCmp("searchForm").getForm().getValues(false);
														var store = Ext.getCmp("KnowFileListGrid").getStore();
														param.start = 1;
														param.status = 1;
														Ext.getCmp("pagetoolbar").formValue = param;
														store.load({
															params : param
														});
													}
												}
										});
										}else{
											Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
										}
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("δ�ҵ���Ӧ�����̣���鿴�Ƿ�����!");
									}
								}, this);
                               }
							},
							failure : function(response, options) {
								Ext.getCmp("savelistbutton").enable();
								Ext.getCmp("submitknowbutton").enable();
								Ext.getCmp("closebutton").enable();
								Ext.MessageBox.alert("��ʾ", "����������ʧ�ܣ�");
							}
						});
					}
				},
				scope : this
			}, 
					{
					text : '�ر�',
					id :'closebutton',
					handler : function() {
							windowSkip.close();	
					},
					scope : this
				}]
		});
		windowSkip.show();
	},
	//2010-04-20 add by huzh for ҳ������ begin
	split : function(data) {
		var labellen = 90;
		var itemlen = 200;
		var throulen = 500;
		if (Ext.isIE) {
			throulen = 500;
		} else {
			throulen = 510;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		//alert(this.dataId);         
		for (i = 0; i < data.length; i++) {

			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			//add by lee for Ϊ�����б����ӿ���ק�������޸Ĳ��ܿ�ȫ��Ϣ��BUG in 20091112 BEGIN
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			//add by lee for Ϊ�����б����ӿ���ק�������޸Ĳ��ܿ�ȫ��Ϣ��BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//ͨ��  
					// alert("data");
					if ((i - hid + longitems) % 2 == 1) {//���Ҳ�����ǰһ����ͨ                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//��ռһ��
						longitems++;
					}
					data[i].colspan = 3;//������ͨ                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//�������ȣ����� 
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			//alert(data[i].width+data[i].name);
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
	//2010-04-20 add by huzh for ҳ������ end
	items : this.items,
	initComponent : function() {
		var da = new DataAction();
		var DataHead = da.getListPanelElementsForHead("KnowLedgeFileQuery_pagepanel");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < DataHead.length; i++) {
			var headItem = DataHead[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			if(propertyName=="KnowFile$status"
			   ||propertyName=="KnowFile$createDate"
			     ||propertyName=="KnowFile$createUser"){
				isHiddenColumn = false;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			//2010-04-24 add by huzh for �е��� begin
			if(propertyName=="KnowFile$id"){
				columnItem.width=70;
			}
			if(propertyName=="KnowFile$name"){
				columnItem.width=200;
			}
			if(propertyName=="KnowFile$descn"){
				columnItem.width=230;
			}
			if(propertyName=="KnowFile$createDate"){
				columnItem.width=120;
			}
			if(propertyName=="KnowFile$createUser"){
				columnItem.width=100;
			}
			//2010-04-24 add by huzh for �е��� end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
//		this.store = da.getPanelJsonStore("KnowLedgeFileList_pagepanel",DataHead);
		this.store = da.getPanelJsonStore("KnowLedgeFileQuery_pagepanel",DataHead);
        this.store.on("load",function(store,records, opt) {
					for(var i=0;i<records.length;i++){
						var cDate=records[i].get("KnowFile$createDate")
						if(cDate!=""){
							records[i].set("KnowFile$createDate",cDate.substring(0,16));
						}
						if(records[i].get("KnowFile$status")==1){
							records[i].set("KnowFile$status","��ʽ");
						}
					}
		});
		this.fp = this.getSearchForm();
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		var pageBar = new Ext.PagingToolbar({
			id : "pagetoolbar",
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.searchConfig = function() {
			if(Ext.getCmp('createUserCombo').getRawValue()!=''&&Ext.getCmp('createUserCombo').getValue()==''){
			    Ext.Msg.alert("��ʾ","��������б���ѡ����ȷ�Ĵ����ˣ�");
			    return false;
			}
			if(Ext.getCmp('createUserCombo').getRawValue()==''){
				Ext.getCmp('createUserCombo').clearValue();
			}
			var params = Ext.getCmp("searchForm").getForm().getValues(false);
			var store = Ext.getCmp("KnowFileListGrid").getStore();
			params.start = 0;
			params.status = 1;
//			pageBar.formValue = param;
			store.on('beforeload', function(a) {   
			      Ext.apply(a.baseParams,params);   
			});
			store.load({
				params : params
			});
		};
		this.grid = new Ext.grid.GridPanel({
			id : "KnowFileListGrid",
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : [{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				handler : this.searchConfig,
				text : '��ѯ',
				iconCls : 'search'
			},'-',{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				text : '���',
				handler : this.show,
				scope : this,
				iconCls : 'edit'
			},'-',{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				handler : function() {
					Ext.getCmp("searchForm").getForm().reset();
				},
				text : '���',
				iconCls : 'reset'
			},new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >��˫����鿴��ϸ��Ϣ��</font>")],
			bbar : pageBar
		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.show, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;

		PagePanel.superclass.initComponent.call(this);
		var params = {
			start : 0,
			'status' : 1
		};
//		pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
		      Ext.apply(a.baseParams,params);   
		});
		this.store.removeAll();
		this.store.load({
			params : params
		});
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});