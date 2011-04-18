ProblemManagerPanel = Ext.extend(Ext.Panel, {
	id : "problemManager",
//	title : '问题管理',
	layout : 'table',
	height : 'auto',
	width : 'auto',
	autoScroll : true,
	align : 'center',
	foredit : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	items : this.items,
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			width : 'auto',
			frame : true,
			height :60,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "查询列表",
			items : [{html: "问题名称：&nbsp;",cls: 'common-text',style:'width:150;text-align:right'}
			,{	id : 'searchField',
				xtype : 'textfield',
				fieldLabel : '问题名称',
				width : '200'
			}]
		});
		return this.panel;
	},
	problemDetail : function(){
		var record = this.problemGrid.getSelectionModel().getSelected();
		var problemId = record.get('id');
		var associatedEventId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("POST", webContext+'/eventAction_findEventByProblemId.action?problemId='+problemId,false);
		conn.send(null);	
		if(conn.status=='200'){
			var result = Ext.decode(conn.responseText);
			associatedEventId = result.eventId;
		}
		window.location =webContext+'/user/event/unsolvedProblems/problemDetail.jsp?pdataId='+problemId+'&EventId='+ associatedEventId;
	},
	// 创建问题
	create : function() {
		var da = new DataAction();
	    var data=da.getPanelElementsForAdd("problemForm_pagepanel");
		var dataform = da.split(data);
		var envForm = new Ext.form.FormPanel({
		    id:"evformaa",
			layout : 'table',
			width : 670,
			height : 250,
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:16px'
				},
				layout : 'table',
			frame : true,
			items : dataform

		});	
		var win = new Ext.Window({
				title : '添加问题',
				width : 700,
				height : 350,
				modal : true,
				//maximizable : true,
				autoScroll:true,
				items : [envForm],
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [ {
					text : '保存',
					//handler : submitBookInfo,	
					handler :function() {
						var panelparam;
					if (Ext.getCmp("evformaa").getForm().isValid()) {
//						var panelparam = Ext.encode(Ext.getCmp("evformaa").form.getValues(false));
					 	panelparam = Ext.encode(getFormParam('evformaa'));
					}
	                 Ext.Ajax.request({
	                      url :webContext + '/problemAction_saveProblem.action',
	                      params: { eid:this.dataId,
                                     panelparam:panelparam},
	                      method:'post', 
	                      success:function(){
						Ext.getCmp('problemgrid').store.reload();
						win.close()
						},
	                      failure:function(response,options){
	                      	Ext.MessageBox.alert("提示","保存数据失败！");
	                      }
	                  });
					},
					scope : this
				},{
					text : '关闭',
					handler : function() {
						win.hide();
					},
					scope : this
				}]

			});
		win.show();
		
     
	},

	initComponent : function() {
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns = [sm,{header : '问题编号', width: 150,dataIndex : 'problemCisn',sortable:true},
				{header : '问题名称', width: 220, dataIndex : 'summary',sortable:true},
				{header : '提交日期', width: 120,dataIndex : 'submitTime',sortable:true}
				];
		var cm = new Ext.grid.ColumnModel(columns);
		this.problemStore = new Ext.data.JsonStore({
			url : webContext + '/problemAction_findAllNotEndProblemsOFAllEndEvents.action',
			totalProperty : 'rowCount',
			root : 'data',
			fields : ['id', 'summary', 'submitTime','problemCisn']
		});	
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,
			store : this.problemStore,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		var params = {
			start : 0
		};
//		this.pageBar.formValue = param;
		this.problemStore.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
		this.problemStore.removeAll();
		this.problemStore.load({
			params : params
		});
		//end
		this.problemGrid = new Ext.grid.GridPanel({
			id : 'problemGridPanel',
			region : 'center',
			autoScroll : true,
			store : this.problemStore,
			cm : cm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : [
			{
				text : '查询',
				xtype : 'button',
				handler : function(){
					//alert("开始查询...");
					var pName = Ext.getCmp('searchField').getValue();
					this.problemStore.removeAll();
					var param = {
						start : 0,
						problemName : pName
					};
					this.problemStore.load({
						params : param
					});
				},
				scope : this,
				iconCls : 'search'
			},'-',
			{
				text : '清除',
				xtype : 'button',
				handler : function(){
					this.fp.form.reset();
				},
				scope : this,
				iconCls : 'reset'
			},{xtype: 'tbtext',text:"【<font style='font-weight:lighter' color=red >双击记录查看详细信息</font>】"}
			],
			bbar : this.pageBar
		});
		this.problemGrid.on("rowdblclick",this.problemDetail,this);
		this.fp = this.getSearchForm();
		this.items = [this.fp, this.problemGrid];
		ProblemManagerPanel.superclass.initComponent.call(this);
	}
});
