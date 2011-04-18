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
		title:'变更申请',
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
		if(Ext.getCmp("CIBatchModify$status").getValue()!=1&&Ext.getCmp("CIBatchModify$status").getValue()!=3){
			var cm = new Ext.grid.ColumnModel([sm,{header:'cid',dataIndex:'cid',hidden:true,sortable:true},
												{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
												{header:'oldId',dataIndex:'oldId',hidden:true,sortable:true},
												{header:'变更类型',dataIndex:'status',sortable:true},
												{header:'配置项类型',dataIndex:'configItemType',sortable:true},
												{header:'配置项名称',dataIndex:'configItemName',sortable:true},
												{header:'配置项编号',dataIndex:'configItemCisn',sortable:true},
												{header:'实施内容',dataIndex:'descn',sortable:true},
												{header:'实施工程师',dataIndex:'officer',sortable:true},
												{header:'开始日期',dataIndex:'startDate',sortable:true},
												{header:'结束日期',dataIndex:'endDate',sortable:true}
												]);
			this.storeChild=new Ext.data.JsonStore({
					url : webContext
							+'/configItemAction_getBatchModifyConfigItemList.action?modifyId='+modifyId,
					fields : ['cid', 'pid','oldId','configItemName','configItemCisn','configItemType','descn','officer','startDate','endDate','status',"result"],
					totalProperty : 'rowCount',
					root : 'data'
					
			});
		}else{
			var cm = new Ext.grid.ColumnModel([sm,{header:'cid',dataIndex:'cid',hidden:true,sortable:true},
												{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
												{header:'oldId',dataIndex:'oldId',hidden:true,sortable:true},
												{header:'变更类型',dataIndex:'status',sortable:true},
												{header:'变更结果',dataIndex:'result',sortable:true},
												{header:'配置项类型',dataIndex:'configItemType',sortable:true},
												{header:'配置项名称',dataIndex:'configItemName',sortable:true},
												{header:'配置项编号',dataIndex:'configItemCisn',sortable:true},
												{header:'实施内容',dataIndex:'descn',sortable:true},
												{header:'实施工程师',dataIndex:'officer',sortable:true},
												{header:'开始日期',dataIndex:'startDate',sortable:true},
												{header:'结束日期',dataIndex:'endDate',sortable:true}
												]);
			this.storeChild=new Ext.data.JsonStore({
					url : webContext
							+'/configItemAction_getBatchModifyConfigItemList.action?modifyId='+modifyId,
					fields : ['cid', 'pid','oldId','configItemName','configItemCisn','configItemType','descn','officer','startDate','endDate','status','result'],
					totalProperty : 'rowCount',
					root : 'data'
					
			});
		}
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,// 使用的是系统默认值
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"modifyGrid",
			title:"变更的配置项",
			store : this.storeChild,
			width:740,
			cm : cm,
			sm : sm,
			height:320,
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar
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
			if(status=='维护必要关系')	{
				var title='维护必要关系';
				var url=webContext+'/user/configItem/configItemBatchModify/configItemInfoMaintenanceRelReadyOnly.jsp?dataId='+cid+
																									     "****modifyId="+modifyId+
																									     "****pid="+pid;
			}else{	
				var title=status+'配置项';
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
					text : "页面正在加载中......",
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
		if(Ext.getCmp("CIBatchModify$status").getValue()!=1&&Ext.getCmp("CIBatchModify$status").getValue()!=3){
			var cm = new Ext.grid.ColumnModel([sm,{header:'rid',dataIndex:'rid',hidden:true,sortable:true},
												{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
												{header:'oldRid',dataIndex:'oldRid',hidden:true,sortable:true},
												{header:'变更类型',dataIndex:'status',sortable:true},
												{header:'父类型',dataIndex:'parentType',sortable:true},
												{header:'父名称',dataIndex:'parentName',sortable:true},
												{header:'父编号',dataIndex:'parentCode',sortable:true},
												{header:'子类型',dataIndex:'childType',sortable:true},
												{header:'子名称',dataIndex:'childName',sortable:true},
												{header:'子编号',dataIndex:'childCode',sortable:true},
												{header:'实施内容',dataIndex:'descn',sortable:true},
												{header:'实施工程师',dataIndex:'officer',sortable:true},
												{header:'开始日期',dataIndex:'startDate',sortable:true},
												{header:'结束日期',dataIndex:'endDate',sortable:true}
												]);
			this.storeChild=new Ext.data.JsonStore({
					url : webContext
							+'/configItemAction_getBatchModifyRelList.action?modifyId='+modifyId,
					fields : ['rid', 'pid','oldRid','parentType','parentName','parentCode','childType','childName','childCode','descn','officer','startDate','endDate','status',"result"],
					totalProperty : 'rowCount',
					root : 'data'
			});
		}else{
			var cm = new Ext.grid.ColumnModel([sm,{header:'rid',dataIndex:'rid',hidden:true,sortable:true},
											{header:'pid',dataIndex:'pid',hidden:true,sortable:true},
											{header:'oldRid',dataIndex:'oldRid',hidden:true,sortable:true},
											{header:'变更类型',dataIndex:'status',sortable:true},
											{header:'变更结果',dataIndex:'result',sortable:true},
											{header:'父类型',dataIndex:'parentType',sortable:true},
											{header:'父名称',dataIndex:'parentName',sortable:true},
											{header:'父编号',dataIndex:'parentCode',sortable:true},
											{header:'子类型',dataIndex:'childType',sortable:true},
											{header:'子名称',dataIndex:'childName',sortable:true},
											{header:'子编号',dataIndex:'childCode',sortable:true},
											{header:'实施内容',dataIndex:'descn',sortable:true},
											{header:'实施工程师',dataIndex:'officer',sortable:true},
											{header:'开始日期',dataIndex:'startDate',sortable:true},
											{header:'结束日期',dataIndex:'endDate',sortable:true}
											]);
			this.storeChild=new Ext.data.JsonStore({
					url : webContext
							+'/configItemAction_getBatchModifyRelList.action?modifyId='+modifyId,
					fields : ['rid', 'pid','oldRid','parentType','parentName','parentCode','childType','childName','childCode','descn','officer','startDate','endDate','status',"result"],
					totalProperty : 'rowCount',
					root : 'data'
			});
		}
		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,// 使用的是系统默认值
			store : this.storeChild,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		this.grid = new Ext.grid.GridPanel({
			id:"modifyRelGrid",
			title:"变更的关系",
			store : this.storeChild,
			width:740,
			cm : cm,
			sm : sm,
			height:320,
			frame:true,
			loadMask : true,
			autoScroll:true,
			bbar : this.pageBar
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
			if(status=='删除'){
				oldRid='';
			}
			var url=webContext+'/user/configItem/configItemBatchModify/configItemRelReadOnly.jsp?rid='+rid+
																     "****oldRid="+oldRid+
																     "****modifyId="+modifyId+
																     "****pid="+pid;			
			var panel=new Ext.Panel({
				id:pid+"$",
				title:status+'关系',
				closable:true,
				autoLoad : {
					url : webContext + "/tabFrame.jsp?url="+url,
					text : "页面正在加载中......",
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
			title="问题信息";
		}else{
			data=da.getSingleFormPanelElementsForEdit("panel_SpecialRequireDevConfirm_Input",typeId);
			title="需求信息";
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
			frame:true,
			layout : 'table',
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
		var itemArray=new Array();
		if(this.type!=''){
			itemArray.push(this.getExtendPanel(this.type,this.typeId));
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
		tabPanel.activate('pagePanel_CIBatchModify');
	    this.items = [tabPanel];
		PageTemplates.superclass.initComponent.call(this);
	}
})