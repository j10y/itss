PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout:'fit',
	autoScroll : true,
	frame:true,
	buttonAlign:'center',
	
 getFormpagePanel_CIBatchModify: function() {
      var da = new DataAction();
	  var data = da.getSingleFormPanelElementsForEdit("pagePanel_CIBatchModify", this.dataId);
		for(var i=0;i<data.length;i++){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			if(data[i].xtype=="panel"){
				data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
            }
		}
			
	  var biddata  = da.split(data);
		this.formpagePanel_CIBatchModify= new Ext.form.FormPanel({
		id : 'pagePanel_CIBatchModify',
		layout : 'table',
		width:740,
		title:'�������',
		autoScroll : true,
		frame : true,
		defaults : {
			bodyStyle : 'padding:4px'
		},
		layoutConfig : {
			columns : 4
		},
		items : biddata
		});
		return this.formpagePanel_CIBatchModify;
	},
	
	getModifyConfigGrid:function(modifyId){
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'cid',dataIndex:'cid',hidden:true,sortable:true},
											{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
											{header:'oldId',dataIndex:'oldId',hidden:true,sortable:true},
											{header:'�������',dataIndex:'status',sortable:true},
											{header:'������',dataIndex:'result',sortable:true,renderer:function(value){
												if(value=='�ɹ�'){
													return '<font color=green>�ɹ�</font>';
												}else{
													return '<font color=red>δ�ɹ�</font>';
												}
											}},
											{header:'����������',dataIndex:'configItemType',sortable:true},
											{header:'����������',dataIndex:'configItemName',sortable:true},
											{header:'��������',dataIndex:'configItemCisn',sortable:true},
											{header:'ʵʩ����',dataIndex:'descn',sortable:true},
											{header:'ʵʩ����ʦ',dataIndex:'officer',sortable:true},
											{header:'��ʼ����',dataIndex:'startDate',sortable:true},
											{header:'��������',dataIndex:'endDate',sortable:true}
											]);
		this.storeChild=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getBatchModifyConfigItemList.action?modifyId='+modifyId,
				fields : ['cid', 'pid','oldId','configItemName','configItemCisn','configItemType','descn','officer','startDate','endDate','status','result'],
				totalProperty : 'rowCount',
				root : 'data'
				
		});
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,// ʹ�õ���ϵͳĬ��ֵ
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"modifyGrid",
			title:"�����������",
			store : this.storeChild,
			width:740,
			cm : cm,
			sm : sm,
			height:320,
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar,
			tbar:[
				{text:'����ɹ�',
				handler : function(){
					var records = Ext.getCmp('modifyGrid').getSelectionModel().getSelections();
					var pids=new Array();
					for(var i=0;i< records.length;i++){
						var pid=records[i].get('pid');
						pids.push(pid);
					}
					if(pids.length==0){
						Ext.Msg.alert("��ʾ","��ѡ�����ɹ��������");
						return;
					}
					pids=Ext.encode(pids);
					Ext.Ajax.request({
							url : webContext+ '/configItemAction_saveSuccessCIBatchModifyPlan.action',
							params : {
								pids : pids
							},
							success : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ɹ�!",function(){
									Ext.getCmp('modifyGrid').getStore().reload();
									Ext.getCmp('modifyRelGrid').getStore().reload();
								});
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ʧ��!");
							}
					}, this);
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},'-',{text:'���δ�ɹ�',
				handler : function(){
					var records = Ext.getCmp('modifyGrid').getSelectionModel().getSelections();
					var pids=new Array();
					for(var i=0;i< records.length;i++){
						var pid=records[i].get('pid');
						pids.push(pid);
					}
					if(pids.length==0){
						Ext.Msg.alert("��ʾ","��ѡ����δ�ɹ��������");
						return;
					}
					pids=Ext.encode(pids);
					Ext.Ajax.request({
							url : webContext+ '/configItemAction_saveUnSuccessCIBatchModifyPlan.action',
							params : {
								pids : pids
							},
							success : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ɹ�!",function(){
									Ext.getCmp('modifyGrid').getStore().reload();
									Ext.getCmp('modifyRelGrid').getStore().reload();
								});
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ʧ��!");
							}
					}, this);
				},
				scope : this,
				iconCls : 'remove',
				cls : "x-btn-text-icon"
				
			},'-',{xtype:'tbtext',text:'<span style=font-weight:bold >ȱʡ���б����Ϊ�ɹ�������и���δ�ɹ����뵥���޸�!</span>'}
			]
			});
		var param = {
			'start' : 0
		};
		this.storeChild.load({
			params : param
		});
		this.grid.on("rowdblclick",function(grid,rowIndex,eventObject){
			var record = grid.getSelectionModel().getSelected();
			var cid=record.get('cid');
			var pid=record.get('pid');
			var oldId=record.get('oldId');
			var status=record.get('status');
			if(Ext.getCmp(pid+"$")!=undefined){
				Ext.getCmp('modifyTab').remove(pid+"$");
			}
			if(status=='ά����Ҫ��ϵ')	{
				var title='ά����Ҫ��ϵ';
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoMaintenanceRelReadyOnly.jsp?dataId='+cid+
																									     "****modifyId="+modifyId+
																									     "****pid="+pid;
			}else{	
				var title=status+'������';
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoReadOnly.jsp?dataId='+cid+
																	     "****oldId="+oldId+
																	     "****modifyId="+modifyId+
																	     "****pid="+pid;
			}
			var panel=new Ext.Panel({
				id:pid+"$",
				title:title,
				closable:true,
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+url,
					text : "ҳ�����ڼ�����......",
					method : 'post',
					scope : this
				}
			})
			Ext.getCmp('modifyTab').add(panel);
			Ext.getCmp('modifyTab').doLayout();
			Ext.getCmp('modifyTab').activate(panel);	
		},this);
		return this.grid; 
	
	},
	getModifyRelGrid:function(modifyId){
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([sm,{header:'rid',dataIndex:'rid',hidden:true,sortable:true},
											{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
											{header:'oldRid',dataIndex:'oldRid',hidden:true,sortable:true},
											{header:'�������',dataIndex:'status',sortable:true},
											{header:'������',dataIndex:'result',sortable:true,renderer:function(value){
												if(value=='�ɹ�'){
													return '<font color=green>�ɹ�</font>';
												}else{
													return '<font color=red>δ�ɹ�</font>';
												}
											}},
											{header:'������',dataIndex:'parentType',sortable:true},
											{header:'������',dataIndex:'parentName',sortable:true},
											{header:'�����',dataIndex:'parentCode',sortable:true},
											{header:'������',dataIndex:'childType',sortable:true},
											{header:'������',dataIndex:'childName',sortable:true},
											{header:'�ӱ��',dataIndex:'childCode',sortable:true},
											{header:'ʵʩ����',dataIndex:'descn',sortable:true},
											{header:'ʵʩ����ʦ',dataIndex:'officer',sortable:true},
											{header:'��ʼ����',dataIndex:'startDate',sortable:true},
											{header:'��������',dataIndex:'endDate',sortable:true}
											]);
		this.storeChild=new Ext.data.JsonStore({
				url : webContext
						+'/configItemAction_getBatchModifyRelList.action?modifyId='+modifyId,
				fields : ['rid', 'pid','oldRid','parentType','parentName','parentCode','childType','childName','childCode','descn','officer','startDate','endDate','status','result'],
				totalProperty : 'rowCount',
				root : 'data'
		});
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,// ʹ�õ���ϵͳĬ��ֵ
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"modifyRelGrid",
			title:"����Ĺ�ϵ",
			store : this.storeChild,
			width:740,
			cm : cm,
			sm : sm,
			height:320,
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar,
						tbar:[
				{text:'����ɹ�',
				handler : function(){
					var records = Ext.getCmp('modifyRelGrid').getSelectionModel().getSelections();
					var pids=new Array();
					for(var i=0;i< records.length;i++){
						var pid=records[i].get('pid');
						pids.push(pid);
					}
					if(pids.length==0){
						Ext.Msg.alert("��ʾ","��ѡ�����ɹ��Ĺ�ϵ��");
						return;
					}
					pids=Ext.encode(pids);
					Ext.Ajax.request({
							url : webContext+ '/configItemAction_saveSuccessCIBatchModifyPlan.action',
							params : {
								pids : pids
							},
							success : function(response, options) {
								var message=response.responseText;
								if(message.trim()==''){
									Ext.MessageBox.alert("��ʾ","����ɹ�!",function(){
										Ext.getCmp('modifyRelGrid').getStore().reload();
									});
								}else{
									Ext.MessageBox.alert("��ʾ",message,function(){
									});
								}
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ʧ��!");
							}
					}, this);
				},
				scope : this,
				iconCls : 'add',
				cls : "x-btn-text-icon"
				
			},'-',{text:'���δ�ɹ�',
				handler : function(){
					var records = Ext.getCmp('modifyRelGrid').getSelectionModel().getSelections();
					var pids=new Array();
					for(var i=0;i< records.length;i++){
						var pid=records[i].get('pid');
						pids.push(pid);
					}
					if(pids.length==0){
						Ext.Msg.alert("��ʾ","��ѡ����δ�ɹ��Ĺ�ϵ��");
						return;
					}
					pids=Ext.encode(pids);
					Ext.Ajax.request({
							url : webContext+ '/configItemAction_saveUnSuccessCIBatchModifyPlan.action',
							params : {
								pids : pids
							},
							success : function(response, options) {
								var message=response.responseText;
								if(message.trim()==''){
									Ext.MessageBox.alert("��ʾ","����ɹ�!",function(){
										Ext.getCmp('modifyRelGrid').getStore().reload();
									});
								}else{
									Ext.MessageBox.alert("��ʾ",message,function(){
									});
								}
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("��ʾ","����ʧ��!");
							}
					}, this);
				},
				scope : this,
				iconCls : 'remove',
				cls : "x-btn-text-icon"
				},'-',{xtype:'tbtext',text:'<span style=font-weight:bold >ȱʡ���б����Ϊ�ɹ�������и���δ�ɹ����뵥���޸�!</span>'}
			
			]
			});
		var param = {
			'start' : 0
		};
		this.storeChild.load({
			params : param
		});
		this.grid.on("rowdblclick",function(grid,rowIndex,eventObject){
			var record = grid.getSelectionModel().getSelected();
			var rid=record.get('rid');
			var pid=record.get('pid');
			var oldRid=record.get('oldRid');
			var status=record.get('status');
			if(Ext.getCmp(pid+"$")!=undefined){
				Ext.getCmp('modifyTab').remove(pid+"$");
			}
			if(status=='ɾ��'){
				oldRid='';
			}
			var url=webContext+'/user/configItem/configItemBatchModify/configItemRelReadOnly.jsp?rid='+rid+
																     "****oldRid="+oldRid+
																     "****modifyId="+modifyId+
																     "****pid="+pid;			
			var panel=new Ext.Panel({
				id:pid+"$",
				title:status+'��ϵ',
				closable:true,
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+url,
					text : "ҳ�����ڼ�����......",
					method : 'post',
					scope : this
				}
			})
			Ext.getCmp('modifyTab').add(panel);
			Ext.getCmp('modifyTab').doLayout();
			Ext.getCmp('modifyTab').activate(panel);
		},this);
		return this.grid; 
	
	},
	getExtendPanel:function(type,typeId){
		var da = new DataAction();
		var data;
		var title="";
		if(type=='problem'){
			data=da.getSingleFormPanelElementsForEdit("problemDetail_pagepanel",typeId);
			title="������Ϣ";
		}else{
			data=da.getSingleFormPanelElementsForEdit("panel_SpecialRequireDevConfirm_Input",typeId);
			title="������Ϣ";
		}
		for(var i=0;i<data.length;i++){
			data[i].readOnly=true;
			data[i].hideTrigger=true;
			data[i].emptyText="";
			if(data[i].xtype=="panel"){
				data[i].items[0].disabled=true;
				data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
            }			
		}
		data = da.split(data);
		 var extendPanel= new Ext.form.FormPanel({
			autoScroll : true,
			layout : 'table',
			frame:true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : title,
			items:data
 		});
		return extendPanel;
	},
  items : this.items,
	initComponent : function() {
	var conn = Ext.lib.Ajax.getConnectionObject().conn;
	var url=webContext+"/configItemAction_getModifyTypeAndTypeId.action?modifyId="+this.dataId;
	conn.open("post", url, false);
	conn.send(null);
	if (conn.status == "200") {
		var responseText = conn.responseText;
		var result=Ext.decode(responseText);
		var type=result.type;
		var typeId=result.typeId;
	}
	
	var itemArray=new Array();
	if(type!=''){
		itemArray.push(this.getExtendPanel(type,typeId));
	}	
	itemArray.push(this.getFormpagePanel_CIBatchModify());
	var tabPanel=new Ext.TabPanel({
			id:'modifyTab',
			activeTab:0,
			enableTabScroll : true,
			layoutOnTabChange:true,
			width:740,
			deferredRender : false,
			forceLayout:true,
			items : itemArray
	});
	var buttons=new Array();
	tabPanel.add(this.getModifyConfigGrid(this.dataId));
	tabPanel.add(this.getModifyRelGrid(this.dataId));
	tabPanel.add(new HistroyForm({dataId:this.dataId}));
	tabPanel.doLayout();
	tabPanel.activate('modifyGrid');
    this.items = [tabPanel];
	PageTemplates.superclass.initComponent.call(this);
	}
})