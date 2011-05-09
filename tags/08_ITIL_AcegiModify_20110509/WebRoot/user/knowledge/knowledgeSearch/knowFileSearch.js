PagePanel = Ext.extend(Ext.Panel, {
	id : "KnowFileSearch",
	closable : true,
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
			height : 85,
			width : 'auto',
			frame : true,
//			autoScroll : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "查询条件",
			items : [{
				html : '文件类型:',
				cls : 'common-text',
				style : 'width:80;text-align:right'
			}, new Ext.form.ComboBox({
				id : 'knowFileTypeCombo',
				fieldLabel : "文件类型",
				width : 200,
				hiddenName : 'knowFileType',
				displayField : 'name',
				valueField : 'id',
				resizable : true,
				emptyText :'请从下拉列表中选择...',
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
				html : '文件名称:',
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
				html : '创建人:',
				cls : 'common-text',
				style : 'width:80;text-align:right'
			}, new Ext.form.ComboBox({
				id : 'createUserCombo',
				fieldLabel : "创建人",
				width : 200,
				hiddenName : 'createUser',
				displayField : 'userName',
				valueField : 'id',
				resizable : true,
				emptyText :'请从下拉列表中选择...',
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
			}),{html : '状态:',
			   cls : 'common-text',
			   style : 'width:80;text-align:right'
			},new Ext.form.ComboBox({
				name : "knowFileStatus",
				fieldLabel : "状态",
				id : "knowFileStatus",
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
				emptyText :'请从下拉列表中选择...',
				store : new Ext.data.SimpleStore({
					fields : ['id', 'name'],
					data : [['1', '正式'], ['3', '过期']]
				})
			})]
		});
		Ext.getCmp("knowFileStatus").setValue("1");//2010-05-12 add by huzh for 默认状态为正式
		return this.panel;
	},
	show : function() {
		var record = Ext.getCmp('KnowFileListGrid').getSelectionModel()
				.getSelected();
		var records = Ext.getCmp('KnowFileListGrid').getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要查看的行！");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "查看时只能选择一行！");
			return;
		}
		var knowFileId = record.get('KnowFile$id');
		var da = new DataAction();
		var data = da.getKnowledgeFormPanelElementsForEdit(
				"KnowLedgeFile_pagepanel", knowFileId);
        //2010-04-20 modified by huzh for 页面修整 begin 
		//var dataform = da.splitForReadOnly(data);
				var descn="";
			for(var i=0;i<data.length;i++){
				if(data[i].id=="KnowFile$number"){
					data.remove(data[i]);
				};
				if(data[i].id=="KnowFile$name"){
					data.remove(data[i]);
				};
				if(data[i].id=="KnowFile$knowFileTypeCombo"){
					data.remove(data[i]);
				};
				if(data[i].id=="KnowFile$descn"){
					descn=data[i].value;
					data.remove(data[i]);
				};
			}
		var dataform = this.splitForReadOnly(data);
		//2010-04-20 modified by huzh for 页面修整 end 
		var knowForm = new Ext.form.FormPanel({
			id : 'knowledgeSkip',
			layout : 'table',
			width : 'auto', //2010-04-20 modified by huzh
			height : 'auto',
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:6px'
			},
			frame : true,
			items : dataform
		});
		var windowSkip = new Ext.Window({
			title : '查看文件描述及相关附件',
			width : 660, //2010-04-20 modified by huzh
			height : 400,
			modal : true,
			maximizable : true,
			autoScroll : true,
			items : [{html:descn},knowForm],
			bodyStyle : 'padding:1px',
			buttons : [{
				text : '关闭',
				handler : function() {
					windowSkip.close();
				},
				scope : this
			}]
		});
		windowSkip.show();
	},
	 //2010-04-20 add by huzh for 页面修整 begin 
	splitForReadOnly : function(data) {
		var labellen = 70;
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
					html : data[i].value,
					frame : true,
//					xtype : "htmleditor",
//					colspan : 3,
					fieldLabel : data[i].fieldLabel,
					cls : 'common-text',
					width: 'auto',
					height : 'auto'
				};
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// 通栏
					if ((i - hid + longitems) % 2 == 1) {// 在右侧栏，前一栏贯通
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {// 多占一栏
						longitems++;
					}
					data[i].colspan = 3;// 本栏贯通
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					
					data[i].style += "width:" + throulen + "px;";
				} else {// 正常长度，半栏
					data[i].style += "width:'auto'" ;
					data[i].width = 'auto';
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
	//2010-04-20 add by huzh for 页面修整 end 
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
			       ||propertyName=="KnowFile$createUser"
			        ||propertyName=="KnowFile$oldKnowFile"){
				isHiddenColumn = false;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			//2010-04-24 add by huzh for 列调整 begin
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
			if(propertyName=="KnowFile$knowFileType"){
				columnItem.width=140;
			}
			//2010-04-24 add by huzh for 列调整 end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("KnowLedgeFileQuery_pagepanel",
				DataHead);
        this.store.on("load",function(store,records, opt) {
				for(var i=0;i<records.length;i++){
					var cDate=records[i].get("KnowFile$createDate")
					if(cDate!=""){
						records[i].set("KnowFile$createDate",cDate.substring(0,16));
					}
					 if(records[i].get("KnowFile$status")==1){
						records[i].set("KnowFile$status","正式");
					}else if(records[i].get("KnowFile$status")==3){
						records[i].set("KnowFile$status","过期");
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
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.searchConfig = function() {
			if(Ext.getCmp('createUserCombo').getRawValue()!=''&&Ext.getCmp('createUserCombo').getValue()==''){
			    Ext.Msg.alert("提示","请从下拉列表中选择正确的创建人！");
			    return false;
			}
			if(Ext.getCmp('createUserCombo').getRawValue()==''){
				Ext.getCmp('createUserCombo').clearValue();
			}
			if(Ext.getCmp('knowFileTypeCombo').getRawValue()==''){
				Ext.getCmp('knowFileTypeCombo').clearValue();
			}
			var params = Ext.getCmp("searchForm").getForm().getValues(false);
			var store = Ext.getCmp("KnowFileListGrid").getStore();
			params.start = 0;
			//param.status = 1;
			store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
//			pageBar.formValue = param;
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
				text : '查询',
				iconCls : 'search'
			}, '-',{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				handler : function() {
					Ext.getCmp("searchForm").getForm().reset();
				},
				text : '清除',
				iconCls : 'reset'
			},new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >【双击后查看详细信息】</font>")
			],
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