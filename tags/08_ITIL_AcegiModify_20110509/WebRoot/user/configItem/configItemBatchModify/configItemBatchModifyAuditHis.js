HistroyForm = Ext.extend(Ext.Panel, {
	id : "HistroyForm",
	layout : 'table',
	autoScroll:true,
	frame : true,
	title:'审批历史',
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	getProcessId:function(dataId,clazz){
		var conn=Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post",webContext+'/extjs/dataAction?method=query&clazz='+clazz+"&modify="+dataId,false);
			conn.send(null);
			if(conn.status=='200'){
				var result=Ext.decode(conn.responseText);
				var processId=result.data[0].processId;
			}
		return processId;
	},
	items : this.items,
	initComponent : function() {
		var clazz="com.zsgj.itil.config.entity.CIBatchModifyAuditHis";
		var processId=this.getProcessId(this.dataId,clazz);
		var hisGrid = this.getGrid(this.dataId,clazz,processId);
		var picPanel = this.getPicPanel(processId);
		var temp=new Array();
		var da=new DataAction();
		var url = webContext + '/extjs/workflow?method=next&procid=' + processId;
		var data = da.ajaxGetData(url);
		if(data!=''){
			temp.push({
				html:"<font size=2><B>当前处理环节:</B>"+data[0].nodeName+'.<br/><B>当前处理人:</B>'+data[0].actorId+'.</font>'
			});
		}
		temp.push(hisGrid);
		temp.push(picPanel);
		this.items =temp;
		HistroyForm.superclass.initComponent.call(this);
	},
	getPicPanel : function(processId) {
		var url = webContext + "/workflow/history.do?pid=" + processId
				+ "&methodCall=view";
		this.picPanel = new Ext.Panel({
			title : '审批流程图',
			height :600,
			autoScroll:false,
			frame:true,
			width : 735,
			html : '<iframe id="myframebidhis" frameborder="no" myframebidhis="ifr"width="100%" height="100%" scrolling="no" src='
					+ url + '></iframe>'
		});
		this.picPanel.doLayout();
		return this.picPanel
	},
	getGrid : function(id, clazz,processId) {
		var da=new DataAction();
		this.cm = new Ext.grid.ColumnModel([{
			header : "id",
			dataIndex : "id",
			align:'center',
			hidden :true
		},{
			header : "变更名称",
			dataIndex : "modify"
		}, {
			header : "节点名称",
			dataIndex : "nodeName"
		}, {
			header : "审批人",
			dataIndex : "approver"
		}, {
			header : "审批结果",
			dataIndex : "resultFlag"
		},
		 {
			header : "审批日期",
			dataIndex : "approverDate"
		}]);
		this.store = da.getJsonStore(clazz);
		var pageBar = new Ext.PagingToolbar({
			pageSize:10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据",
			plugins :new Ext.ux.ProgressBarPager()
		});
		var hisGrid = new Ext.grid.GridPanel({
			title : "审批历史<font color='red'>【双击查看详细信息】</font>",
			width : 735,
			height : 250,
			frame:true,
			store : this.store,
			cm :this.cm,
			bbar:pageBar
		});
		hisGrid.on('dblclick',function(eventObject){
			var record=hisGrid.getSelectionModel().getSelected();
			var win=new Ext.Window({
				title:'详细信息',
				width:400,
				height:230,
				buttonAlign:'center',
				buttons:[
				{text:'关闭',
				 handler:function(){
				 	win.close();
				 }
				}
				],
				items:[
				new Ext.Panel({
					layout:'table',
					frame:true,
					defaults : {
						bodyStyle : 'padding:4px'
					},
					layoutConfig:{
						columns:4
					},
					items:[
					{html: "节点名称:",cls: 'common-text',style:'width:60;text-align:left;'},	
					new Ext.form.Field({
						width:125,
						readOnly:true,
						value:record.get('nodeName')
					}),
					{html: "审批人:",cls: 'common-text',style:'width:60;text-align:left;'},	
					new Ext.form.Field({
						width:125,
						readOnly:true,
						value:record.get('approver')
					}),
					{html: "审批结果:",cls: 'common-text',style:'width:60;text-align:left;'},	
					new Ext.form.Field({
						width:125,
						readOnly:true,
						value:record.get('resultFlag')
					}),
					{html: "审批日期:",cls: 'common-text',style:'width:60;text-align:left;'},	
					new Ext.form.Field({
						width:125,
						readOnly:true,
						value:record.get('approverDate')
					}
					),
					{html: "审批意见:",cls: 'common-text',style:'width:60;text-align:left;'},	
					{
						xtype:'textarea',
						width:308,
						readOnly:true,
						value:record.get('comment'),
						height:100,
						colspan:3
					}
					]
				})
				]
			});
			win.show();
		});
		var param = {
			start : 0,
			modify:id,
			isAsc:true
		};
		this.store.baseParams=param;
		this.store.load();
		return hisGrid;

	}

})
