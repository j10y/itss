HistroyForm = Ext.extend(Ext.Panel, {
	id : "HistroyForm",
	layout : 'table',
	//height : 900,
	align : 'center',
	width:800,
	foredit : true,
	width : 'auto',
	autoScroll : true,
	//border:true,
	clazz : "com.digitalchina.itil.event.entity.EventAuditHis",
	frame : true,
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
	goBackUrl : function() {
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
	//事件基本信息
	getFirstSearchForm : function() {
		var da = new DataAction();
		var data=da.getPanelElementsForEdit("page_event_suppGroupEngineer","panel_event_suppGroupEngineer",this.dataId);
		for(var  i=0;i<data.length;i++){
			
			if(data[i].name=="Event$submitDate"){
				data[i].value=data[i].value.substring(0,19);
			    }
			     if(data[i].xtype=="panel"){
			    	 data[i].items[0].disabled=true;
					data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
          		}
				data[i].readOnly=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
			}
		var biddata = da.split(data);
			
		this.panel = new Ext.form.FormPanel({  
			id : 'eventDetails',
			layout : 'table',
			autoScroll:true,
			title:"事件基本信息",
		    height : 387,	
			width : 700,
			frame : true,
			border:true,
//			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			items : biddata
			});
		return this.panel;

	},
	initComponent : function() {
		this.eventPanel = this.getFirstSearchForm();
		var da = new DataAction();
		var biddata = "";
		this.picPanel = new Ext.Panel({
			title : '审批流程图',
			layout : 'fit',
			height : 550,
			frame :true,
			width : 760

		});
		this.hisGrid = this.getGrid(this.dataId,"com.digitalchina.itil.event.entity.EventAuditHis",this.picPanel);
		var hisPanel = new Ext.Panel({
			title : "审批历史【<font style='font-weight:lighter' color=red>双击审批历史条目可查看审批意见</font>】",
			width : 7560,
			height : 200,
			frame : true,
			layout : 'fit',
			border: true,
			items : this.hisGrid

		});
	this.tab  = new Ext.TabPanel({	
            title:'事件详细信息',
			enableTabScroll : true,
			deferredRender : false,
			activeTab:0,
		    frame : true,
			plain : false,
			border : true,
			width : 750,
			height :"auto",
		    bodyBorder : true,
			items : [this.eventPanel,
					{
				xtype : 'panel',
				id : "first1",
				title : '审批状态',
				frame : true,
				border : true,
				height: "auto",
				width : 800,
				items : [hisPanel, this.picPanel]
			
			}]
		});
		this.items = [this.tab];
		HistroyForm.superclass.initComponent.call(this);

	},

	testfun : function() {
	
		var sto = this.hisGrid.getStore();
		var record = sto.getAt(sto.getTotalCount()-1);
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
		var url = webContext + '/extjs/workflow?method=next&procid=' + pid;
		var da = new DataAction();
		var data = da.ajaxGetData(url);
		var dataClass = [{
			name : 'event',
			mapping : 'event'
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
				event : record.get('event')
			});
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
			if (propertyName == 'id' || propertyName == 'comment' || propertyName == 'processName'
			|| propertyName == 'processId'|| propertyName == 'nodeId'|| propertyName == 'alterFlag') {
				isHidden = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
			if(propertyName=="approver"){
				columnItem.width=200;
			}
			if(propertyName=="resultFlag"){
				columnItem.width=100;
			}
			if(propertyName=="approverDate"){
				columnItem.width=160;
			}
			if(propertyName=="nodeName"){
				columnItem.width=160;
			}
			
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
			autoScroll:true,
			trackMouseOver : false,
			loadMask : true,
			border: true,
			height : 700,
			width  :800

		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("celldblclick",function(g,row){
			if(g.getStore().getAt(row).get('comment')==undefined){
			  }else{
				Ext.Msg.show({
				   title:'审批意见',
				   msg:'审批人：'+g.getStore().getAt(row).get('approver')+'<br>'+'结点名称：'+g.getStore().getAt(row).get('nodeName')+'<br>'+'审批意见： '+g.getStore().getAt(row).get('comment'),
				   buttons: Ext.Msg.OK,
				   animEl: 'elId',
				   icon: Ext.MessageBox.INFO
				});
			}
		},this);

		var param = {
			'start' : 0
		};
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'event': kplanId,
			'isAsc':true
		};
//		param.methodCall = 'query';
//		param.start = 0;
//		param.event = kplanId;
//		param.isAsc=true;
		this.store.removeAll();
		this.store.on('load', this.testfun, this);

		this.store.load({
			params : param
		});

		return this.grid;

	}

})
