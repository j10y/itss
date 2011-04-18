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
//			 autoScroll : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 6
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
							+ '/extjs/comboDataAction?clazz=com.digitalchina.itil.knowledge.entity.KnowFileType',
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
					//width : 200,
					width : 220,//2010-04-16 modified by huzh for bug(分页工具栏“最后一页”标记显示不出来)
					hiddenName : 'createUser',
					displayField : 'userName',
					valueField : 'id',
					resizable : true,
					emptyText :'请从下拉列表中选择...',
					triggerAction : 'all',
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.UserInfo',
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
	 remove : function() {
		var record = Ext.getCmp('KnowFileListGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('KnowFileListGrid').getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行！");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('提示', '最少选择一条信息进行删除！');
			return;
		}
		var id = new Array();
		var da = new DataAction();
		var firm  =Ext.Msg.confirm('提示', '您确定要进行删除操作吗？', function(button) {
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){	
					id[i] = records[i].get("KnowFile$id");
				Ext.Ajax.request({
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz=com.digitalchina.itil.knowledge.entity.KnowFile',
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("提示","数据删除失败！");
	                       }else{  
		                       	var params = Ext.getCmp("searchForm").getForm().getValues(false);
								var store = Ext.getCmp("KnowFileListGrid").getStore();
								params.start = 0;
								params.status = 4;
//								Ext.getCmp("pagetoolbar").formValue = param;
								store.on('beforeload', function(a) {   
								      Ext.apply(a.baseParams,params);   
								});
								store.load({
									params : params
								});
		                    }
	                   },scope:this
						
					});
				}
			}
		}, this);
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
		var data = da.getKnowledgeFormPanelElementsForEdit("KnowLedgeFile_pagepanel", knowFileId);
		for(var i=0;i<data.length;i++){
			if(data[i].name=='KnowFile$number'){
			 	data[i].readOnly=true;
			}
		}
		var dataform = this.split(data);
		var knowForm = new Ext.form.FormPanel({
			title: '文件基本信息',
			id : 'knowledgeSkip',
			layout : 'table',
			width : 630,  //2010-04-20 modified by huzh 
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
		var panelItem = new Array();
	    panelItem.push(knowForm);
	    var oldKnowFile=Ext.getCmp("KnowFile$oldKnowFile").getValue();
		if(oldKnowFile!=""){
		  var oldpanel={  
                  title: '原文件基本信息',
	        	  height : 340,
	              autoLoad : {
					url : webContext + "/tabFrame.jsp?url=" + webContext
							+ "/user/knowledge/knowledgeChangeManager/oldknowFile.jsp?dataId="+oldKnowFile,
					text : "页面正在加载中......",
					method : 'post',
					scope : this
			      }
             };
         panelItem.push(oldpanel);
		}
	    var infoTab = new Ext.TabPanel({	
			enableTabScroll : true,
			deferredRender : false,
			activeTab:0,
		    frame : true,
			plain : false,
			border : true,
			width : 630,
			height :329,
		    bodyBorder : true,
			items : panelItem
		});
		var windowSkip = new Ext.Window({
			title : '查看详细信息',
			width : 646,  
			height : 400,
			modal : true,
			maximizable : true,
			items : infoTab,
			bodyStyle : 'padding:2px',  
			buttons : [{
			text : '暂存变更',
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
//					var knowFileParam = Ext.encode(Ext.getCmp("knowledgeSkip").form.getValues(false));
					var knowFileParam = Ext.encode(getFormParam('knowledgeSkip'));	
					Ext.Ajax.request({
						url : webContext+ '/knowledgeAction_saveKnowFileEntityDraft.action',
						params : {
							knowFileParam : knowFileParam
						},
						method : 'post',
						success : function() {
							 Ext.MessageBox.alert("提示", "保存数据成功！",function(){
								windowSkip.close();
							 }); 
							var params = Ext.getCmp("searchForm").getForm().getValues(false);
							var store = Ext.getCmp("KnowFileListGrid").getStore();
							params.start = 0;
							params.status = 4;
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
							Ext.MessageBox.alert("提示", "保存变更数据失败！");
						}
					});
				}else{
					Ext.getCmp("savelistbutton").enable();
					Ext.getCmp("submitknowbutton").enable();
					Ext.getCmp("closebutton").enable();
					 Ext.MessageBox.alert("提示", "红色波浪线部分为必填项！"); 
				}
			},
			scope : this
			}, {
				text : '提交变更',
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
//						var knowFileParam = Ext.encode(Ext
//								.getCmp("knowledgeSkip").form.getValues(false));
						var knowFileParam = Ext.encode(getFormParam('knowledgeSkip'));	
						Ext.Ajax.request({
							url : webContext
									+ '/knowledgeAction_saveKnowFileEntityDraft.action',
							params : {
								knowFileId : knowFileId,
								knowFileParam : knowFileParam
							},
							method : 'post',
							success : function(response) {
								var r =Ext.decode(response.responseText);
                               if(r.success==false){
	                             	Ext.MessageBox.alert("提示", "该文件已变更过或正处于变更申请中，您不能再次对其提交变更申请！");
									var params = Ext.getCmp("searchForm").getForm().getValues(false);
									var store = Ext.getCmp("KnowFileListGrid").getStore();
									params.start = 0;
									params.status = 4;
//									Ext.getCmp("pagetoolbar").formValue = param;
									store.on('beforeload', function(a) {   
									      Ext.apply(a.baseParams,params);   
									});
									store.load({
										params : params
									});
                               }else{
								var dataId = eval('('
										+ response.responseText + ')').dataId;
								//---------------------------------------------
								//提交流程（合同、解决方案、文件）
								var dataType = 7;//数据类型Id
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
														+ "', applyType: 'kproject',applyTypeName: '知识变更审批',customer:''}",
												defname : 'KnowFileChangeProcess1276415998668'
											},
											success:function(response){
												var meg = Ext.decode(response.responseText);
												if (meg.Exception != undefined) {
													Ext.Msg.alert("提示",meg.Exception);
												}else{
													Ext.Msg.alert("提示","提交变更成功！",function(){
														windowSkip.close();
													});
													var params = Ext.getCmp("searchForm").getForm().getValues(false);
													var store = Ext.getCmp("KnowFileListGrid").getStore();
													params.start = 0;
													params.status = 4;
//													Ext.getCmp("pagetoolbar").formValue = param;
													store.on('beforeload', function(a) {   
													      Ext.apply(a.baseParams,params);   
													});
													store.load({
														params : params
													});
												
												}
											}
									});
                               }
							},
							failure : function(response, options) {
								Ext.getCmp("savelistbutton").enable();
								Ext.getCmp("submitknowbutton").enable();
								Ext.getCmp("closebutton").enable();
								Ext.MessageBox.alert("提示", "保存变更数据失败！");
							}
						});
					}else{
					 Ext.getCmp("savelistbutton").enable();
					Ext.getCmp("submitknowbutton").enable();
					Ext.getCmp("closebutton").enable();
					 Ext.MessageBox.alert("提示", "红色波浪线部分为必填项！"); 
				}
				},
				scope : this
			},{
				text : '关闭',
				id : 'closebutton',
				handler : function() {
						windowSkip.close();	
				},
				scope : this
			}]
		});
		windowSkip.show();
	},
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
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//通栏  
					if ((i - hid + longitems) % 2 == 1) {//在右侧栏，前一栏贯通                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//多占一栏
						longitems++;
					}
					data[i].colspan = 3;//本栏贯通                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//正常长度，半栏 
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
	items : this.items,
	initComponent : function() {
		var da = new DataAction();
		var DataHead = da
				.getListPanelElementsForHead("KnowLedgeFileQuery_pagepanel");
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
						 if(records[i].get("KnowFile$status")==4){
							records[i].set("KnowFile$status","变更草稿");
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
			var params = Ext.getCmp("searchForm").getForm().getValues(false);
			var store = Ext.getCmp("KnowFileListGrid").getStore();
			params.start = 0;
			params.status = 4;
//			pageBar.formValue = param;
			store.on('beforeload', function(a) {   
		    	  Ext.apply(a.baseParams,params);   
			});
			store.load({
				params : params
			});
		}, this.grid = new Ext.grid.GridPanel({
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
			},'-',{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				text : '修改',
				handler : this.show,
				scope : this,
				iconCls : 'edit'
			},'-',{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				text : '删除',
				handler : this.remove,
				scope : this,
				iconCls : 'remove'
			},'-',{
				xtype : 'button',
				style : 'margin:2px 0px 2px 5px',
				handler : function() {
					Ext.getCmp("searchForm").getForm().reset();
				},
				text : '清除',
				iconCls : 'reset'
			},new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >【双击后查看详细信息】</font>")],
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
			'status' : 4
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