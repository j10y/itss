HistroyForm = Ext.extend(Ext.Panel, {
	id : "HistroyForm",
	layout : 'table',
	height : 'auto',
	title:'审批历史状态',
	align : 'center',
	foredit : true,
	width : 800,
	clazz : "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis",
	frame : true,

	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
	items : this.items,

	split : function(data) {
		var labellen = 135;
		var itemlen = 200;
		var throulen = 550;
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
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

			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width += "'" + itemlen + "'";
			} else {
				if (data[i].width == "9999") {// 通栏
					if (i % 2 == 1) {// 在右侧栏，前一栏贯通
						longData[2 * (i - hid) - 1].colspan = 3;
					}
					data[i].colspan = 3;// 本栏贯通
					data[i].width = throulen;
					data[i].style += "width:" + throulen + "px;";
				} else {// 正常长度，半栏
					data[i].style += "width:" + data[i].width + "px;";
				}
			}
			longData[2 * (i - hid)] = {
				html : data[i].feildLabel + ":",
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

	initComponent : function() {
		this.reqIdHis= this.reqId;
		
		this.reqClassHis = this.reqClass;

		var da = new DataAction();
		// 数据组装
		var biddata = "";

		this.hisGrid = this.getGrid(this.dataId,
				"com.zsgj.itil.service.entity.ServiceItemApplyAuditHis");
				
		
		var hisPanel = new Ext.Panel({
			title : '审批历史',
			width : 800,
			height : 300,
			layout : 'fit',
			border: true,
			items : this.hisGrid

		});
		this.items = [hisPanel];//, this.picPanel];
		HistroyForm.superclass.initComponent.call(this);

	},
	
	testfun : function() {

		var sto = this.hisGrid.getStore();
		var dataClass = [{
			name : 'serviceItem',
			mapping : 'serviceItem'
		}, {
			name : 'processId',
			mapping : 'processId'
		}, {
			name : 'comment',
			mapping : 'comment'
		}, {
			name : 'resultFlag',
			mapping : 'resultFlag'
		},{
			name : 'nodeName',
			mapping : 'nodeName'
		}, {
			name : 'approver',
			mapping : 'approver'
		}, {
			name : 'approverDate',
			mapping : 'approverDate'
		}]
	},
	
	getGrid : function(kplanId, clazz) {
		//var pPanel = picPanel;
		var da = new DataAction();
		var obj = da.getElementsForHead(clazz);

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();

		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;

			var isHidden = false;
			if (propertyName == 'id'||propertyName == 'definitionName'||propertyName == 'processId') {
				isHidden = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}

		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);

		this.store = da.getJsonStore(clazz);

		this.cm.defaultSortable = true;

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.formValue = '';

		this.grid = new Ext.grid.GridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			frame:true,
//			bodyScroll:{
//				autoScroll:true
//			},
			trackMouseOver : false,
			loadMask : true,
			border: true,
			height : 700

		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		var param = {
			'start' : 1
		};
		param.methodCall = 'query';
		param.start = 1;
		//param.processId=this.processId;
		param.pageSize = 999;
		param.requirementId = this.reqIdHis;
		param.requirementClass = this.reqClassHis;
		param.isAsc=true;
		this.store.removeAll();
		this.store.on('load', this.testfun, this);

		this.store.load({
			params : param
		});

		return this.grid;

	}

})
