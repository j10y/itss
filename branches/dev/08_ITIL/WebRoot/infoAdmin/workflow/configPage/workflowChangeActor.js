ChangeActorPanel = Ext.extend(Ext.Panel, {
	id : "changeActorPanel",
//	title : '问题管理',
	layout : 'table',
	height : 'auto',
	width : 'auto',
//	autoScroll : true,
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
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "用户任务列表",
			items : [{html: "用户ITCode:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'}
			,{	id : 'searchField',
				xtype : 'textfield',
				fieldLabel : '用户ITCode',
				width : '200'
			}]
		});
		return this.panel;
	},
//替换审批人窗口
	create : function() {
		var record = this.taskGrid.getSelectionModel().getSelected();
		var taskId = record.get('id');
	
      var envForm = new Ext.form.FormPanel({
    	    id:"evformaa",
		    region : "north",
			layout : 'table',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
           items : [{html: "新审批人ITCode:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'}
			,{	id : 'newActor',
				xtype : 'textfield',
				allowBlank : false,
				fieldLabel : '用户ITCode',
				width : '200'
			}]

		});	
		var win = new Ext.Window({
				title : '更换审批人',
				width : 400,
				height : 50,
				modal : true,
				maximizable : true,
				autoScroll:true,
				items : [envForm],
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [ {
					text : '保存',
					//handler : submitBookInfo,	
					handler :function() {
					if (!Ext.getCmp("evformaa").getForm().isValid()) {
					Ext.MessageBox.alert("提示",
								"窗口中带红色波浪线的为必填项,请填写完整。谢谢您合作！");
						return false;
					}
					var newActor=Ext.getCmp("newActor").getValue();
				
	                 Ext.Ajax.request({
	                      url :webContext + '/workflow/reassign.do?methodCall=reassignByActor',
	                      params: { 
	                      id:taskId,
                          actor:newActor
                         },
	                    method:'post', 
	                    success:function(){
	                    	
						Ext.getCmp('taskGridPanel').store.reload();
						win.close()
						},
	                      failure:function(response,options){
	                      	Ext.MessageBox.alert("提示信息：","保存数据失败");
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
	//删除问题
	remove : function() {
		var record = this.taskGrid.getSelectionModel().getSelected();
		var records = this.taskGrid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
			return;
		}
		var id = new Array();
		var da = new DataAction();
		var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {

				for(var i=0;i<records.length;i++){	
					id[i] =records[i].get("id");
					Ext.Ajax.request({
						url :webContext + '/problemAction_delete.action',
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       	var r =Ext.decode(response.responseText);
	                       	if(!r.success){
	                       		Ext.Msg.alert("提示信息","数据删除失败",function(){
	                       				
	                       		});
	                       	}   
	                   	},
	                   	scope:this
					});
				}
				this.taskStore.reload(); 
			}
		}, this);

	},
	initComponent : function() {
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns = [sm,
				{header : '自动编号', dataIndex : 'id', hidden : true,sortable:true},
				{header : '流程名称', dataIndex : 'processName',sortable:true},
				{header : '任务名称', dataIndex : 'taskName',sortable:true},
				{header : '审批人', dataIndex : 'auditPerson',sortable:true},
				{header : '操作', dataIndex : 'operation',sortable:true,renderer:function(value,record){
				return "<a href='javaScript:create()'>更换审批人</a>"
				}}
				];
		var cm = new Ext.grid.ColumnModel(columns);
		this.taskStore = new Ext.data.JsonStore({
			id:'taskStore',
			url: webContext+ '/workflow/listTask.do?methodCall=listTaskByActor',
			autoLoad : true,
			totalProperty : 'rowCount',
			root : 'data',
			fields : ['id', 'processName', 'taskName','auditPerson','operation'],
			sortInfo: {field: "id", direction: "ASC"}
		});	
		this.pageBar = new Ext.PagingToolbarExt({
			pageSize :10,
			store : this.taskStore,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		//begin分页页数start参数传递问题解决
		var param = {
			start : 1
		};
		this.pageBar.formValue = param;
		this.taskStore.removeAll();
		this.taskStore.load({
			params : param
		});
		//end
		this.taskGrid = new Ext.grid.GridPanel({
			id : 'taskGridPanel',
			region : 'center',
			height : 200,
			store : this.taskStore,
			cm : cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			scope:this,
			tbar : [
			{
				text : '查询',
				pressed : true,
				handler : function(){
					var actor = Ext.getCmp('searchField').getValue();
					this.taskStore.removeAll();
					var param = {
						start : 1,
						actor : actor
					};
					this.taskStore.load({
						params : param
					});
				},
				scope : this,
				iconCls : 'search'
			},
			{
				text : '重置',
				pressed : true,
				handler : function(){
					this.fp.form.reset();
				},
				scope : this,
				iconCls : 'reset'
			}
			],
			bbar : this.pageBar
		});
		//双击事件
		this.taskGrid.on("rowdblclick",this.create,this);
		this.fp = this.getSearchForm();
		this.items = [this.fp, this.taskGrid];
		ChangeActorPanel.superclass.initComponent.call(this);
	}
});
