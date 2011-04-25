PagePanel = Ext.extend(Ext.Panel, {
	id : "KnowledgeSearch",
	closable : true,
	autoScroll : true,
	viewConfig : {
		autoFill : true,
		forceFit : true 
	},
	layout : 'border',
	getknowSearchForm : function() {
		var serviceItemBySu = new Ext.form.ComboBox({
			name : "serviceItemBySu",
			id : 'serviceItemBySu',
			fieldLabel : "服务项",
			width : 200,
			hiddenName : 'serviceItem',
			displayField : 'name',
			valueField : 'id',
			lazyRender : true,
			emptyText :'请从下拉列表中选择...',
			minChars : 50,
			resizable : true,
			triggerAction : 'all',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext
						+ '/eventAction_findServiceItem.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					Ext.getCmp('problemTypebySu').clearValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={"name" : param,official:1};
					this.store.load();
					return false;
				},
				"select" :function(){
				 Ext.getCmp('problemTypebySu').clearValue();
				}
				
			}

		});
		var problemTypebySu = new Ext.form.ComboBox({
			name : "problemTypebySu",
			id : 'problemTypebySu',
			fieldLabel : "问题类型",
			width : 200,
			hiddenName : 'knowProblemType',
			displayField : 'name',
			valueField : 'id',
			emptyText :'请从下拉列表中选择...',
			lazyRender : true,
			minChars : 50,
			resizable : true,
			triggerAction : 'all',
			selectOnFocus : true,
			store : new Ext.data.JsonStore({
				url : webContext  + '/knowledgeAction_findKnowProblemType.action',
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			pageSize : 10,
			listeners : {
				'beforequery' : function(queryEvent) {
					var discValue = Ext.getCmp('serviceItemBySu').getValue();
					var param = queryEvent.combo.getRawValue();
					var val = queryEvent.combo.getValue();
					if (queryEvent.query == '') {
						param = '';
					}
					this.store.baseParams={
						"name" : param,
						serviceItem : discValue,
						deleteFlag :0//过滤已删除的问题类型
						};
					this.store.load();
					return false;
				}
			}
		});
		var summary = new Ext.form.TextField({
			name : "summary",
			fieldLabel : "具体问题",
			id : 'summary',
			width : 200
		});
		var knowStatus = new Ext.form.ComboBox({
			name : "knowledgeStatus",
			fieldLabel : "状态",
			id : "knowledgeStatus",
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
		});
		
		this.panel = new Ext.form.FormPanel({
			id:"knowSearchForm",
			region : "north", 
			layout : 'table',
			height : 85,
			width : 'auto',
			frame : true,
//			autoScroll : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "查询条件",
			items : [ {
				html : "服务项：",
				cls : 'common-text',
				width : 90,
				style : 'width:200;text-align:right'
			}, serviceItemBySu, {
				html : "问题类型：",
				cls : 'common-text',
				width : 90,
				style : 'width:200;text-align:right'
			}, problemTypebySu, {
				html : "具体问题：",
				cls : 'common-text',
				width : 90,
				style : 'width:200;text-align:right'
			}, summary,{
				html : "状态：",
				cls : 'common-text',
				width : 90,
				style : 'width:200;text-align:right'
			}, knowStatus]
		});
        Ext.getCmp("knowledgeStatus").setValue("1");//2010-05-12 add by huzh for 默认状态为正式
		return this.panel;
	},
	show:function(){ 
            var record =  Ext.getCmp('konwledgeGrid').getSelectionModel().getSelected();
			var records =  Ext.getCmp('konwledgeGrid').getSelectionModel().getSelections();
			if(!record){
				Ext.Msg.alert("提示","请先选择要查看的行！");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("提示","查看时只能选择一行！");
				return;
			}
			var knowledgeId=record.get('Knowledge$id');	
	        var da = new DataAction();
	        var data=da.getKnowledgeFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
	        var konwledgecontext = "";
	        for(i = 0; i < data.length; i++){
	        	if(data[i].id=="Knowledge$resolvent"){
	        		konwledgecontext = data[i].value;
	        	}
	        }
		var windowSkip = new Ext.Window({
			title : '查看详细信息',
			maximizable : true,
			autoScroll : true,
			width : 600,
			height :400,
			modal : true,
			items : [{html : konwledgecontext}],
			buttons : [
				{
				text : '关闭',
				handler : function() {
						windowSkip.close();	
				},
				scope : this
			}]
		  });
        windowSkip.show();
      },
      splitForReadOnly : function(data) {
		var labellen = 95;
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
					value : data[i].value,
					xtype : "htmleditor",
					colspan : 3,
					fieldLabel : data[i].fieldLabel,
					cls : 'common-text',
					width: data[i].width,
					height : 230
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
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
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
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
		var DataHead=da.getListPanelElementsForHead("KnowLedgeSolutionList_pagepanel");
		this.modelTableName="Knowledge";  
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
            if(isHidden=='true'){
                    isHiddenColumn = true;  
            }  
            if(propertyName=="Knowledge$status"
                ||propertyName=="Knowledge$knowledgeCisn"
                  ||propertyName=="Knowledge$createUser"
                    ||propertyName=="Knowledge$createDate"
                     ||propertyName=="Knowledge$oldKnowledge"){
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
			if(propertyName=="Knowledge$createUser"){
				columnItem.width=100;
			}
			if(propertyName=="Knowledge$createDate"){
				columnItem.width=120;
			}
			if(propertyName=="Knowledge$summary"){
				columnItem.width=200;
			}
			if(propertyName=="Knowledge$id"){
				columnItem.width=70;
			}
			if(propertyName=="Knowledge$knowProblemType"){
				columnItem.width=180;
			}
			if(propertyName=="Knowledge$serviceItem"){
				columnItem.width=180;
			}
			//2010-04-24 add by huzh for 列调整 end
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store= da.getPanelJsonStore("KnowLedgeSolutionList_pagepanel", DataHead);
		this.store.on("load",function(store,records, opt) {
					for(var i=0;i<records.length;i++){
						var cDate=records[i].get("Knowledge$createDate")
						if(cDate!=""){
							records[i].set("Knowledge$createDate",cDate.substring(0,16));
						}
						if(records[i].get("Knowledge$status")==1){
							records[i].set("Knowledge$status","正式");
						}else if(records[i].get("Knowledge$status")==3){
							records[i].set("Knowledge$status","过期");
			  			}
					}
		});
		this.fp = this.getknowSearchForm();
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
		this.searchConfig=function(){
			if((Ext.getCmp('serviceItemBySu').getRawValue() != '' 
		              && Ext.getCmp('serviceItemBySu').getValue() == '')
		                ||(Ext.getCmp('problemTypebySu').getRawValue() != '' 
		                  && Ext.getCmp('problemTypebySu').getValue() == '')){
			    Ext.Msg.alert("提示","请从下拉列表中选择正确的服务项或问题类型！");
			    return;
		    }
			if(Ext.getCmp('serviceItemBySu').getRawValue()==''){
				Ext.getCmp('serviceItemBySu').clearValue();
			}
			if(Ext.getCmp('problemTypebySu').getRawValue()==''){
				Ext.getCmp('problemTypebySu').clearValue();
			}
			var params = Ext.getCmp("knowSearchForm").getForm().getValues(false);
			var store=Ext.getCmp("konwledgeGrid").getStore();
	        params.start = 0;  
//	        pageBar.formValue=param;
	        store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
	        store.load({
	            params : params
	        });
	},
		this.konwledgeGrid = new Ext.grid.GridPanel({
			id:"konwledgeGrid",
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
				},'-',{
					xtype : 'button',
					style : 'margin:2px 0px 2px 5px',
					handler :function(){
						Ext.getCmp("knowSearchForm").getForm().reset();
					},
					text : '清除',
					iconCls : 'reset'
			},new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >【双击后查看详细信息】</font>")],
			bbar : pageBar
		});
		
		this.konwledgeGrid.on("headerdblclick", this.fitWidth, this);
		this.konwledgeGrid.on("rowdblclick", this.show, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.konwledgeGrid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var params = {
			 start : 0,
			'status':1
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