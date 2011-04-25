PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	closable : true,
	layout : 'border',
		split : function(data) {
		var labellen = 75;
		var itemlen = 120;
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
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 BEGIN
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			//add by lee for 为下拉列表增加可拖拽属性以修改不能看全信息的BUG in 20091112 END
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//通栏  
					// alert("data");
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
	getSearchForm : function(searchConfig) {
		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			frame : true,
			keys:{
			    key:Ext.EventObject.ENTER,
			    fn: function(){
			    	searchConfig();
			    },
			    scope: this
			},
			height:60,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "查询条件",
			items : [
			{
			    html: "类型:" ,
				cls: 'common-text', 
				style:'width:50;height:20;text-align:right;'
			},
			new Ext.form.ComboBox({
				store : new Ext.data.JsonStore({
					url: webContext+'/configItemAction_findItemTypeByItem.action',
					fields: ['id','name'],
				    root:'data',
					sortInfo: {field: "id", direction: "ASC"}
				}),
				listeners:{
					beforequery  :function(queryEvent){
						queryEvent.combo.store.baseParams={
							item:"ci"
						};
						queryEvent.combo.store.reload();
						return false;
					}
				}, 
				id:'itemTypeCombo',
				hiddenName: "configItemType",
				valueField : "id",
				displayField : "name",
				resizable:true,
				width:150,
				editable : false,
				maxHeight:200,
				triggerAction : 'all'
				
			}),{
				html : "名称:",
				cls : 'common-text',
				style:'width:50;height:20;text-align:right;'
			},
			new Ext.form.TextField({
				id:"name",
				name:"name",
				width:150
			}),
			{
				html : "编号:",
				cls : 'common-text',
				style:'width:50;height:20;text-align:right;'
			},
			new Ext.form.TextField({
				id:"cisn",
				name:"cisn",
				width:150
			})
			]
		});

		return this.panel;
	},
	seeInfo:function(){
		 var record = this.grid.getSelectionModel().getSelected();
		 var records = this.grid.getSelectionModel().getSelections();
		 if (!record) {
			Ext.Msg.alert("提示", "请选择要查看的配置项!");
			return;
		 }
		if (records.length > 1) {
			Ext.Msg.alert("提示", "查看时只能选择一条记录!");
			return;
		}
		var id = record.get("ConfigItem$id");//主id
		window.location= webContext+'/user/configItem/configItemSearch/configItemInfo.jsp?dataId='+id;
	},
	items : this.items,
	initComponent : function(){
		this.searchConfig=function(){
			var param = getFormParam("searchForm");
			var store=Ext.getCmp("listGrid").getStore();
	        param.start =0;  
	        param.status=1;
	        store.baseParams=param;
	        store.load();
		}
		var da = new DataAction();
		var obj = da.getListPanelElementsForHead("configItemListPanel");
		this.fp = this.getSearchForm(this.searchConfig);
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
            var modelTableId = this.modelTableName+"$id";         
            var isHidden = headItem.isHidden;
            if(isHidden=='true'){
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
		this.store = da.getPanelJsonStore("configItemListPanel",obj);

		var pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"listGrid",
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
					handler :this.searchConfig,
					text : '查询',
					iconCls : 'search'
				},{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :function(){
						Ext.getCmp("searchForm").getForm().reset();
					},
					text : '重置',
					iconCls : 'reset'
		},{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					text : '查看',
					scope : this,
					handler : this.seeInfo,
					iconCls : 'look'
				} ],
			bbar : pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.seeInfo, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
		var param = {
			 start : 0,
			'status':1
		};
		this.store.baseParams=param;
		this.store.load();
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