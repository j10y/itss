HistroyForm = Ext.extend(Ext.Panel, {
	id : "HistroyForm",
	layout : 'table',
	//title:"�¼�����״̬",
	height : 800,
	align : 'center',
	foredit : true,
	width : 'auto',
	border:true,
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
	getButtons : function() {
		return [{
			xtype : 'button',
			style : 'margin:4px 10px 4px 0',
			handler : function() {
				history.back();
			},
			align : 'center',
			text : '����'
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
				if (data[i].width == "9999") {// ͨ��
					if (i % 2 == 1) {// ���Ҳ�����ǰһ����ͨ
						longData[2 * (i - hid) - 1].colspan = 3;
					}
					data[i].colspan = 3;// ������ͨ
					data[i].width = throulen;
					data[i].style += "width:" + throulen + "px;";
				} else {// �������ȣ�����
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
		// ������װ
		var biddata = "";
		//
		this.picPanel = new Ext.Panel({
			title : '��������ͼ',
			layout : 'fit',
			height : 570,
			frame :true,
			width : 748

		});
		this.hisGrid = this.getGrid(this.dataId,
				"com.digitalchina.itil.event.entity.EventAuditHis",
				this.picPanel);
		var hisPanel = new Ext.Panel({
			title : "������ʷ��<font style='font-weight:lighter' color=red>˫��������ʷ��Ŀ�ɲ鿴�������</font>��",
			width : 748,
			height : 200,
			frame : true,
			layout : 'fit',
			border: true,
			items : this.hisGrid

		});

		// ��ť
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

		HistroyForm.superclass.initComponent.call(this);

	},

	testfun : function() {
	
		var sto = this.hisGrid.getStore();
		var record = sto.getAt(0);
		if (record == undefined) {
			this.picPanel.add(new Ext.Panel({
				html : 'û�п���ʾ������ͼ',
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
		//alert(data[0]);
		var gridRecord = Ext.data.Record.create(dataClass);
		if (data[0]!=undefined) {
			var dataRecord = new gridRecord({
				processId : data[0].processId,
				nodeName : data[0].nodeName,
				approver : data[0].actorId,
				resultFlag : '���ȴ�����',
				event : record.get('event')
			});
		sto.add([dataRecord]);		
		var url = webContext + "/workflow/history.do?pid=" + pid
				+ "&methodCall=view";
				
		this.picPanel.add(new Ext.Panel({
			html : '<iframe id="myframebidhis" frameborder="no" myframebidhis="ifr"width="100%" height="570" scrolling="auto" src='
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
			if (propertyName == 'id' ||propertyName == 'event' || propertyName == 'comment' || propertyName == 'processName'
			|| propertyName == 'nodeId'|| propertyName == 'alterFlag') {
				isHidden = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
			//2010-04-24 add by huzh for �е��� begin
			if(propertyName=="approver"){
				columnItem.width=200;
			}
			if(propertyName=="comment"){
				columnItem.width=160;
			}
			if(propertyName=="resultFlag"){
				columnItem.width=100;
			}
			if(propertyName=="approverDate"){
				columnItem.width=160;
			}
			if(propertyName=="processId"){
				columnItem.width=90;
			}
			if(propertyName=="nodeName"){
				columnItem.width=160;
			}
			
			//2010-04-24 add by huzh for �е��� end
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
			height : 700,
			width  :740
			//tbar : [new Ext.Toolbar.TextItem('<font color=red>��ʾ��</font><font color=blue>˫��������ʷ��Ŀ�ɲ鿴�������</font>')]

		});

		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("celldblclick",function(g,row){
			if(g.getStore().getAt(row).get('comment')==undefined){
			  }else{
				Ext.Msg.show({
				   title:'�������',
				   msg:'�����ˣ�'+g.getStore().getAt(row).get('approver')+'<br>'+'������ƣ�'+g.getStore().getAt(row).get('nodeName')+'<br>'+'��������� '+g.getStore().getAt(row).get('comment'),
				   buttons: Ext.Msg.OK,
				   animEl: 'elId',
				   icon: Ext.MessageBox.INFO
				});
			}
		},this);

		var param = {
			'start' : 1
		};
		param.methodCall = 'query';
		param.start = 1;
		param.event = kplanId;
		param.isAsc=true;
		this.store.removeAll();
		this.store.on('load', this.testfun, this);

		this.store.load({
			params : param
		});

		return this.grid;

	}

})
