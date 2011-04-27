PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'column',
	dataId : this.dataId,
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("panel_trainAnswer"); // da.getElementsForQuery("com.zsgj.itil.train.entity.QuestOption");
		var biddata = da.split(data);
		this.panel = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 1200,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "问题选项列表",
			items : biddata
		});
		return this.panel;
	},
	createSearch2 : function(btnName, link, imageUrl, scope) {
		var params = this.panel.form.getValues(false);
		if (dataId == "" || dataId == null) {
			params.methodCall = 'query';
			params.start = 0;
		} else {
			//alert(param);
			params.methodCall = 'query';
			params.start = 0;
			params.quest = dataId;
//			this.formValue = param;
//			this.pageBar.formValue = this.formValue;
			this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
			this.store.removeAll();
			this.store.load({
				params : params
			});
		}

		
	},
	remove : function() {

		var store = this.store;
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		// alert(Ext.encode(record.data));
		var clazz = "com.zsgj.itil.train.entity.QuestOption";
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		var da = new DataAction();
		var ids = "";
		for (var i = 0; i < records.length; i++) {

			var scId = records[i].get("TrainQuestOption$id");
			Ext.Ajax.request({

				url : webContext + '/trainPlan_removeQuestOption.action',
				params : {
					dataId : scId,
					clazz : clazz
				},
				success : function(response, options) {
					Ext.MessageBox.alert("删除成功", "", function() {
						store.reload();
							// window.location=
							// webContext+"/user/train/train_instructorList.jsp";
						});

				},
				failure : function(response, options) {
					Ext.MessageBox.alert("删除失败");
				}
			}, this);
		}

	},
	create : function() {
		var info = Ext.getCmp('cataPanel').getForm().getValues();
		//alert(Ext.encode(info));
		if(dataId==""||dataId=='null'){
			alert("你还没有保存问题,不能新建问题选项!");
			return;
		}
		if(info.questType==3||info.questType==4){
			alert("非选择题没有问题选项");
					return;
				}
		var clazz = "com.zsgj.itil.train.entity.QuestOption";
		var da = new DataAction();
		var data = da.getElementsForAdd(clazz);
		
		 for (i = 0; i < data.length; i++) {
		// if (data[i].xtype == 'combo') {
		// data[i].id = data[i].id + 6;// 改变Combobox的id
		// }
		// data[i].listeners = {
		// beforequery : function() {
		// return false;
		// }
		// };
		 	//alert(data[i].name);
		 	//alert(data[i].hidden);
		 	if(data[i].name=='quest'){
				data[i].value=dataId;
				data[i].fieldLabel='';
				data[i].hidden=true;
			}
		 }

		var custdata = da.split(data);

		var envForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 350,
			height : 120,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store = this.store;
		var win1 = new Ext.Window({
			title : '问题',
			width : 400,
			height : 240,
			maximizable : true,
			items : envForm,
			closeAction : 'hide',
			bodyStyle : 'padding:4px',

			buttons : [{
				text : '保存',
				handler : function() {
					var bsParam = envForm.form.getValues(false);
					da.saveData(clazz, bsParam, function() {
						store.reload();
						win1.close()
					});
				},
				scope : this

			}, {
				text : '关闭',
				handler : function() {
					// location = 'ibmCust.jsp'
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
	popup : function() {
		//alert(dataId);
		var record = this.grid.getSelectionModel().getSelected();
		var id = record.get("TrainQuestOption$id");// ********************************得到修改行的id
		var clazz = "com.zsgj.itil.train.entity.QuestOption";
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz, id);
		for (i = 0; i < data.length; i++) {
			if (data[i].xtype == 'combo') {
				data[i].id = data[i].id + 6;// 改变Combobox的id
			}
			if(data[i].name=='quest'){
				data[i].value=dataId;
				data[i].fieldLabel='';
				data[i].hidden=true;
			
			}
			data[i].listeners = {
				beforequery : function() {
					return false;
				}
			};
		}

		var custdata = da.split(data);

		var envForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 400,
			height : 120,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store = this.store;
		var win1 = new Ext.Window({
			title : '问题',
			width : 400,
			height : 240,
			maximizable : true,
			items : envForm,
			closeAction : 'hide',
			bodyStyle : 'padding:4px',

			buttons : [{
				text : '保存',
				handler : function() {
					var bsParam = envForm.form.getValues(false);
					da.saveData(clazz, bsParam, function() {

						store.reload();
						win1.close()
					});
				},
				scope : this

			}, {
				text : '关闭',
				handler : function() {
					// location = 'ibmCust.jsp'
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
	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			text : '保存',
			scope : this,
			handler : this.save
		}, {
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			handler : function() {
				history.back();
			},
			text : '返回'
		}]

	},
	save : function() {
//		alert("oldDataId" + oldDataId);
//		alert("dataId" + dataId);
		var info = Ext.getCmp('cataPanel').getForm().getValues();
		var dataId = this.dataId;
		// alert(Ext.encode(info));
		if (info.questType == "" || info.questName == "") {
			alert("缺少必须数据，请填写完全");
			return;
		};
		Ext.Ajax.request({
			url : webContext + '/trainPlan_saveQuest.action',
			method : "POST",
			params : {
				dataId : dataId,
				id : info.id,
				questName : info.questName,
				survey : oldDataId,
				questType : info.questType,
				content : info.content
			},
			success : function(response, options) {
				//alert(info.questType);
				if(info.questType==3||info.questType==4){
					history.back();
				}
				//Ext.MessageBox.alert("保存成功");
				var responseArray = Ext.util.JSON.decode(response.responseText);
				//alert(Ext.encode(responseArray));
				var dataId = responseArray.dataId;
				//alert(oldDataId);
				window.location =  webContext+"/user/train/train_answerList.jsp?oldDataId="+oldDataId+"&&newDataId="+dataId;
				//window.location=webContext+"/user/train/train_answerList.jsp?newDataId="+dataId+"&&oldDataId="+oldDataId;
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			},
			scope : this
		});
	},
	getPanel : function() {
		//alert(dataId);
		var da = new DataAction();
		var clazz = "com.zsgj.itil.train.entity.Quest";
		this.button = this.getButtons();
		var data = null;
		if (dataId == "" || dataId == null) {
			data = da.getElementsForAdd(clazz);
		} else {
			data = da.getElementsForEdit(clazz, dataId);
		}
		var biddata = da.split(data);

		var cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			layout : 'table',
			height : 120,
			width : 1200,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:16px'
			},
			layoutConfig : {
				columns : 4
			},
			buttons : this.button,
			title : "试题",
			items : biddata
		});
		return cpanel;
	},
	items : this.items,
	initComponent : function() {
		var da = new DataAction();
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_train_answer_list",
				this);
		this.fp = this.getSearchForm();
		this.modelTableName = "TrainQuestOption";
		var obj = da.getListPanelElementsForHead("panel_trainAnswer");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;

			var isHiddenColumn = false;
			var modelTableId = this.modelTableName + "$id";
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("panel_trainAnswer", obj); // add
		// header
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 400,
			width : 1200,
			anchor : '0 -35',
			tbar : [this.mybuttons],
			bbar : this.pageBar
		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.popup, this);
		this.on("popup", this.popup, this);
		this.on("create", this.create, this);
		this.on("remove", this.remove, this);
		this.on("createSearch2", this.createSearch2, this);
		this.caPanel = this.getPanel();
		var items = new Array();
		items.push(this.caPanel);
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		if (dataId == "" || dataId == null) {
			var params = {
				'mehtodCall' : 'query',

				'start' : 0
			};

		} else {
			var params = {
				'mehtodCall' : 'query',
				'quest' : dataId,
				'start' : 0
			};
//			this.pageBar.formValue = param;
			this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});

			this.store.removeAll();
			this.store.load({
				params : params
			});
		}

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
