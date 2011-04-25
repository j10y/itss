HistroyForm = Ext.extend(Ext.Panel, {
	id : "HistroyForm",
	layout : 'table',
	height : 900,
	align : 'center',
	foredit : true,
	width : 'auto',
	border:true,
	clazz : "com.digitalchina.itil.event.entity.EventAuditHis",
	frame : true,
	layoutConfig : {
		columns : 1
	},
	items : this.items,
	getFirstSearchForm : function(eventId) {
		var da = new DataAction();
		var data=da.getPanelElementsForEdit("page_event_suppGroupEngineer","panel_event_suppGroupEngineer",eventId);
		for(var  i=0;i<data.length;i++){
			if(data[i].name=="Event$appendix"){
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
		    height : 340,	
			width : 767,
			frame : true,
			border:true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px' 
			},
			layoutConfig : {
				columns : 4
			},
			items : biddata
			});
		return this.panel;

	},
	getFormUserForLookSulotion_pagepanel: function(knowledgeId) {
			var da = new DataAction();
			data =da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
			var konwledgecontext = "";
	        for(i = 0; i < data.length; i++){
	        	if(data[i].id=="Knowledge$resolvent"){
	        		konwledgecontext = data[i].value;
	        	}
	        }
			this.formUserForLookSulotion_pagepanel= new Ext.form.FormPanel({
				id : 'UserForLookSulotion_pagepanel',
				title : '解决方案',
				maximizable : true,
				autoScroll : true,
				width : 700,
				height :400,
				frame : true, 
				modal : true,
				items : [{html : konwledgecontext}]
			});
		return this.formUserForLookSulotion_pagepanel;
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
			html : '<iframe id="myframebidhis" frameborder="no" myframebidhis="ifr"width="100%" height="540" scrolling="auto" src='
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
			if (propertyName == 'id' ||propertyName == 'comment'|| propertyName == 'processName'|| propertyName == 'nodeId'|| propertyName == 'alterFlag') {
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
			width  :800
		});

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

	},

	initComponent : function() {
		var tabItems=new Array();
		this.eventPanel = this.getFirstSearchForm(this.dataId);
		tabItems.push(this.eventPanel);
		if(this.knowledgeId!=''&&this.knowledgeId!=null){
			this.eventSulotionPanel = this.getFormUserForLookSulotion_pagepanel(this.knowledgeId);
			tabItems.push(this.eventSulotionPanel)
		}
		var da = new DataAction();
		var biddata = "";
		this.picPanel = new Ext.Panel({
			title : '审批流程图',
			layout : 'fit',
			height : 550,
			frame :true,
			width : 840

		});
		this.hisGrid = this.getGrid(this.dataId,"com.digitalchina.itil.event.entity.EventAuditHis",
						this.picPanel);
		var hisPanel = new Ext.Panel({
			title : "审批历史【<font style='font-weight:lighter' color=red>双击审批历史条目可查看审批意见</font>】",
			width : 840,
			height : 200,
			frame : true,
			layout : 'fit',
			border: true,
			items : this.hisGrid

		});
		var history={
				xtype : 'panel',
				id : "first1",
				title : '审批状态',
				frame : true,
				border : true,
				height:"auto",
				width : 830,
				items : [hisPanel, this.picPanel]
			};
		tabItems.push(history);
	    this.tab  = new Ext.TabPanel({	
            title:'事件详细信息',
			enableTabScroll : true,
			deferredRender : false,
			activeTab:0,
		    frame : true,
			plain : false,
			border : true,
			width : 850,
			height :"auto",
		    bodyBorder : true,
		    items : tabItems
		});
		this.items = [this.tab];
		HistroyForm.superclass.initComponent.call(this);
	}
	
})
