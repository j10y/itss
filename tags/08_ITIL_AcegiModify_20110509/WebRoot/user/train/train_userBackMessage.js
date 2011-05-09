PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	splitOwn : function(data) {
		var labellen = 135;
		var itemlen = 200;
		var throulen = 530;
		if (Ext.isIE) {
			throulen = 530;
		} else {
			throulen = 540;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		// alert(this.dataId);
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

			// alert(data[i].width+data[i].name);
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {// 通栏
					// alert("data");
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
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			// alert(data[i].width+data[i].name);
			if (data[i].fieldLabel != "报名人员") {
				longData[2 * (i - hid)] = {
					html : data[i].fieldLabel + ":",
					cls : 'common-text',
					style : 'width:' + labellen + ';text-align:right'
				};
				longData[2 * (i - hid) + 1] = data[i];
			}
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("panel_trainUserBackMessageForm"); // da.getElementsForQuery("com.zsgj.itil.train.entity.TrainCourseSignup");
		var biddata = this.splitOwn(data);
		var trainCourse = new Ext.form.ComboBox({
					xtype : "combo",
					id : "tc",
					fieldLabel : "报名人员:",
					valueField : "trainCourse",
					displayField : "name",
					listWidth : 220,
					maxHeight : 220,
					emptyText: '请选择',
					mode : 'remote',
					forceSelection : true,
					hiddenName : "trainCourse",
					editable : true,
					triggerAction : 'all', 
					lazyRender: true,
		            typeAhead: false,
		            autoScroll:true,
					allowBlank : true,
					name : "name",
					selectOnFocus: true,
					width : 220,
					// *******************************************************************
					store : new Ext.data.JsonStore({// displayField
						id : Ext.id(),
						url : webContext+'/trainPlan_findTrainCourse.action?displayField=name',
						fields: ['trainCourse','name'],
						totalProperty:'rowCount',
						root:'data',
						//id:'signupUser',			
						sortInfo: {field: "name", direction: "ASC"},
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['name'] == undefined) {
									opt.params['name'] = Ext.getCmp("tc").defaultParam;
								}
							}
						}
					}),
					// *******************************************************************
					pageSize:10,
					listeners:{
						blur:function(combo){//当其失去焦点的时候
							if(combo.getRawValue()==''){
								combo.reset();
							}
						},
						beforequery : function(queryEvent){
							var param = unicode(queryEvent.query);
							this.defaultParam=param;
							this.store.load({
								params:{name:param,start:0}
							});
							return true;
						}
					}					
				});
				
				
		this.panel = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
            id:"panel",
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "查询列表",
			items : [{html: "&nbsp;&nbsp;培训课程:" ,cls: 'common-text', style:'width:78;height:20;text-align:left;margin:5 0 5 0;'},
						trainCourse]
		});
		return this.panel;
	},
	createSearch2 : function(btnName, link, imageUrl, scope) {
		var params = this.panel.form.getValues(false);
		params.methodCall = 'query';
		params.start = 0;
		params.deleteFlag = 0;

//		this.formValue = param;
//		this.pageBar.formValue = this.formValue;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
		this.store.removeAll();
		this.store.load({
			params : params
		});

		return false;
	},
	// 双击进去之后获得相应课程的反馈问卷
	addBackMesssage : function() {
		var record = this.grid.getSelectionModel().getSelected();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择某一行课程!");
			return;
		}
		window.location = webContext + "/trainPlan_findQuest.action?dataId="
				+ record.get('id');
	},
	items : this.items,
	initComponent : function() {
		var da = new DataAction();
		this.fp = this.getSearchForm();
		this.modelTableName = "TrainCourseSignup";
		var obj = da.getListPanelElementsForHead("panel_trainUserBackMessage");
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
			if (propertyName == 'TrainCourseSignup$deleteFlag') {
				isHiddenColumn = true;
			}
			var modelTableId = this.modelTableName + "$id";
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			var propertyNames = propertyName.split('$');
			propertyName = propertyNames[1];
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			if (propertyName != 'deleteFlag') {
				columns[columns.length] = columnItem;
			}
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		var field = new Array();
		for (i = 0; i < obj.length; i++) {
			field[i] = obj[i].dataIndex.split('$')[1];
		}
		this.store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/trainPlan_findCourseByuser.action',
			root : "data",
			fields : field,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
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
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
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
				text : '查询',
				pressed : true,
				handler : this.createSearch2,
				scope : this,
				iconCls : 'search'
			}, '&nbsp;|&nbsp;', {
				text : '重置',
				pressed : true,
				handler : function() {
					Ext.getCmp("panel").form.reset();
				},
				scope : this,
				iconCls : 'reset'
			},'-',new Ext.Toolbar.TextItem("<font color=red>以下是你所参与的课程，双击即可填写反馈问卷</font>")],
			bbar : this.pageBar
		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.addBackMesssage, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		var params = {
			'mehtodCall' : 'query',
			'deleteFlag' : 0,
			'start' : 0
		};

//		this.pageBar.formValue = param;
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
