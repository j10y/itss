ProblemManagerPanel = Ext.extend(Ext.Panel, {
	id : "problemManager",
//	title : '�������',
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
			title : "��ѯ�б�",
			items : [{html: "�������ƣ�&nbsp;",cls: 'common-text',style:'width:150;text-align:right'}
			,{	id : 'searchField',
				xtype : 'textfield',
				fieldLabel : '��������',
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
	// ��������
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
				title : '�������',
				width : 700,
				height : 350,
				modal : true,
				//maximizable : true,
				autoScroll:true,
				items : [envForm],
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [ {
					text : '����',
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
	                      	Ext.MessageBox.alert("��ʾ","��������ʧ�ܣ�");
	                      }
	                  });
					},
					scope : this
				},{
					text : '�ر�',
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
		columns = [sm,{header : '������', width: 150,dataIndex : 'problemCisn',sortable:true},
				{header : '��������', width: 220, dataIndex : 'summary',sortable:true},
				{header : '�ύ����', width: 120,dataIndex : 'submitTime',sortable:true}
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
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
				text : '��ѯ',
				xtype : 'button',
				handler : function(){
					//alert("��ʼ��ѯ...");
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
				text : '���',
				xtype : 'button',
				handler : function(){
					this.fp.form.reset();
				},
				scope : this,
				iconCls : 'reset'
			},{xtype: 'tbtext',text:"��<font style='font-weight:lighter' color=red >˫����¼�鿴��ϸ��Ϣ</font>��"}
			],
			bbar : this.pageBar
		});
		this.problemGrid.on("rowdblclick",this.problemDetail,this);
		this.fp = this.getSearchForm();
		this.items = [this.fp, this.problemGrid];
		ProblemManagerPanel.superclass.initComponent.call(this);
	}
});
