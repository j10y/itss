ServiceCatalogueHistroyForm = Ext.extend(Ext.Panel, {
	id : "ServiceCatalogueHistroyForm",
	layout : 'table',
	height : 900,
	title:'审批状态查询',
	align : 'center',
	foredit : true,
	width : 780,
	clazz : "com.digitalchina.itil.service.entity.ServiceCatalogueAuditHis",
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
	getProcessId:function(dataId){
		var clazz="com.digitalchina.itil.service.entity.ServiceCatalogueAuditHis";
		var pid="";
		var conn=Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post",webContext+'/extjs/dataAction?method=query&clazz='+clazz+"&methodCall=query"+"&serviceCatalogue="+dataId+"&isAsc=false"+"&start="+1,false);
			conn.send(null);
			if(conn.status=='200'){
				var result=Ext.decode(conn.responseText);
				pid=result.data[0].processId;
			}
		return pid;	
	},
	goBackUrl : function() {
	},
	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			handler : function() {
				history.back();
			},
			align : 'center',
			text : '返回',
			iconCls:'back'
		}]
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

		this.mybuttons = this.getButtons();
		var da = new DataAction();
		// 数据组装
		var biddata = "";
		//
		this.picPanel = new Ext.Panel({
			title : '审批流程图',
			layout : 'fit',
			height : 700,
			width : 780

		});
		//alert(this.dataId);
		this.hisGrid = this.getGrid(this.dataId,
				"com.digitalchina.itil.service.entity.ServiceCatalogueAuditHis",
				this.picPanel);

		var hisPanel = new Ext.Panel({
			title : '审批历史',
			width : 780,
			height : 200,
			layout : 'fit',
			frame:true,
			autoScroll:true,
			border: true,
			items : this.hisGrid

		});

		// 按钮
		var buttons = {
			layout : 'table',
			height : 'auto',
			width : 'auto',
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : this.mybuttons
		}

		this.items = [hisPanel, this.picPanel];

		//

		ServiceCatalogueHistroyForm.superclass.initComponent.call(this);
		// this.picPanel.html = 'http://www.google.cn';
		// this.picPanel.doLayout();

	},

	testfun : function() {

		var sto = this.hisGrid.getStore();

		var record = sto.getAt(0);
		if (record == undefined) {
			this.picPanel.add(new Ext.Panel({
				html : '没有可显示的审批图',
				laytou : 'fit',
				frame : true,
				align : 'center'
			}));
			this.picPanel.doLayout();
			return;
		}
		var pid = record.get('processId');
		//alert(pid);
		var url = webContext + '/extjs/workflow?method=next&procid=' + pid;
		var da = new DataAction();
		var data = da.ajaxGetData(url);
		 //alert(data);

		var dataClass = [{
			name : 'serviceCatalogue',
			mapping : 'serviceCatalogue'
		},{
			name : 'processId',
			mapping : 'processId'
		},  {
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
		var gridRecord = Ext.data.Record.create(dataClass);
		if (data[0]!=undefined) {
			var dataRecord = new gridRecord({
				processId : data[0].processId,
				nodeName : data[0].nodeName,
				approver : data[0].actorId,
				resultFlag : '【等待处理】',
				serviceCatalogue : record.get('serviceCatalogue')
			});
			
//			alert(record.get('serviceCatalogue').name);
		sto.add([dataRecord]);		
		var url = webContext + "/workflow/history.do?pid=" + pid
				+ "&methodCall=view";
				
		this.picPanel.add(new Ext.Panel({
			html : '<iframe id="myframebidhis" frameborder="no" myframebidhis="ifr"width="100%" height="500" scrolling="auto" src='
					+ url + '></iframe>'
		}));
		this.picPanel.doLayout();
		}
	},
	getGrid : function(kplanId, clazz, picPanel) {
		var processId=this.getProcessId(kplanId);
		var pPanel = picPanel;
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
			if (propertyName == 'id') {
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
		param.processId=processId;
		param.start = 1;
		param.serviceCatalogue = kplanId;
		param.isAsc=false;
		this.store.removeAll();
		this.store.on('load', this.testfun, this);

		this.store.load({
			params : param
		});

		return this.grid;

	}

})
