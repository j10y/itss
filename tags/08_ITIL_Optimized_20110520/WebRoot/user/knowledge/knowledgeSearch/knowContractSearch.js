PagePanel = Ext.extend(Ext.Panel, {
	id : "KnowContractSearch",
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getcontractSearchForm : function() {
		this.contractSearchPanel = new Ext.form.FormPanel({
			id : "contractSearchForm",
			region : "north",
			layout : 'table',
			height : 60,
			width : 'auto',
			frame : true,
//			autoScroll : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "��ѯ����",
			items : [{
				html : '��ͬ����:',
				cls : 'common-text',
				style : 'width:80;text-align:right'
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
				width : 200,
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
			}),{html : '״̬:',
			   cls : 'common-text',
			   style : 'width:60;text-align:right'
			},new Ext.form.ComboBox({
				name : "knowContractStatus",
				fieldLabel : "״̬",
				id : "knowContractStatus",
				width : 200,
				mode : 'local',
				defaultParam : '',
				hiddenName : "status",
				xtype : 'combo',
				displayField : 'name',
				valueField : 'id',
				triggerAction : 'all',
				typeAhead : true,
				forceSelection : true,
				emptyText :'��������б���ѡ��...',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'name'],
					data : [['1', '��ʽ'], ['3', '����']]
				})
			})]
		});
    	 Ext.getCmp("knowContractStatus").setValue("1");//2010-05-12 add by huzh for Ĭ��״̬Ϊ��ʽ
		return this.contractSearchPanel;
	},
	show : function() {
		var record = Ext.getCmp('KnowContractListGrid').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('KnowContractListGrid').getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�鿴���У�");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�鿴ʱֻ��ѡ��һ�У�");
			return;
		}
		var knowContractId = record.get('KnowContract$id');
		var da = new DataAction();
		var data = da.getKnowledgeFormPanelElementsForEdit(
				"KnowLedgeContract_pagepanel", knowContractId);
		//2010-04-20 modified by huzh for ҳ������ begin
		//var dataform = da.splitForReadOnly(data);
		var dataform = this.splitForReadOnly(data);
		//2010-04-20 modified by huzh for ҳ������ end
		var knowForm = new Ext.form.FormPanel({
			id : 'knowledgeSkip',
			layout : 'table',
			width : 640, //2010-04-20 modified by huzh 
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
			width : 660,  //2010-04-20 modified by huzh 
			height : 'auto',
			modal : true,
			//maximizable : true,
			items : [knowForm],
			bodyStyle : 'padding:4px',
			buttons : [{
				text : '�ر�',
				handler : function() {
					windowSkip.close();
				},
				scope : this
			}]
		});
		windowSkip.show();
	},
	 //2010-04-20 add by huzh for ҳ������ begin 
	splitForReadOnly : function(data) {
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
		for (i = 0; i < data.length; i++) {
			data[i].style = data[i].style == null ? "" : data[i].style;
			data[i].readOnly = true;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			if (data[i].xtype == 'combo' || data[i].xtype == 'datefield') {
				data[i].hideTrigger = true;
			}
			if (data[i].xtype == "panel") {
				data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
			}
			if (data[i].xtype == "fckeditor") {
				data[i] = {
					value : data[i].value,
					xtype : "htmleditor",
					colspan : 3,
					fieldLabel : data[i].fieldLabel,
					cls : 'common-text',
					width: data[i].width,
					height : 230
				};
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// ͨ��
					if ((i - hid + longitems) % 2 == 1) {// ���Ҳ�����ǰһ����ͨ
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// ��ռһ��
						longitems++;
					}
					data[i].colspan = 3;// ������ͨ
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					
					data[i].style += "width:" + throulen + "px;";
				} else {// �������ȣ�����
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
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
		var DataHead = da
				.getListPanelElementsForHead("KnowLedgeContractQuery_pagepanel");
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
			if(propertyName=="KnowContract$status"
			     ||propertyName=="KnowContract$createDate"
			       ||propertyName=="KnowContract$createUser"
			        ||propertyName=="KnowContract$oldKnowContract"){
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
			if(propertyName=="KnowContract$id"){
				columnItem.width=70;
			}
			if(propertyName=="KnowContract$name"){
				columnItem.width=200;
			}
			if(propertyName=="KnowContract$descn"){
				columnItem.width=230;
			}
			if(propertyName=="KnowContract$createDate"){
				columnItem.width=120;
			}
			if(propertyName=="KnowContract$createUser"){
				columnItem.width=100;
			}
			//2010-04-24 add by huzh for �е��� end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("KnowLedgeContractQuery_pagepanel",
				DataHead);
        this.store.on("load",function(store,records, opt) {
					for(var i=0;i<records.length;i++){
						var cDate=records[i].get("KnowContract$createDate")
						if(cDate!=""){
							records[i].set("KnowContract$createDate",cDate.substring(0,16));
						}
						 if(records[i].get("KnowContract$status")==1){
							records[i].set("KnowContract$status","��ʽ");
						}else if(records[i].get("KnowContract$status")==3){
							records[i].set("KnowContract$status","����");
			  			}
					}
		});
		this.fp = this.getcontractSearchForm();
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		var pageBar = new Ext.PagingToolbar({
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
			var params = Ext.getCmp("contractSearchForm").getForm().getValues(false);
			var store = Ext.getCmp("KnowContractListGrid").getStore();
			params.start = 0;
			//param.status = 1;
//			pageBar.formValue = param;
			store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
			store.load({
				params : params
			});
		}, this.grid = new Ext.grid.GridPanel({
			id : "KnowContractListGrid",
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
			}, '-',{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				handler : function() {
					Ext.getCmp("contractSearchForm").getForm().reset();
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