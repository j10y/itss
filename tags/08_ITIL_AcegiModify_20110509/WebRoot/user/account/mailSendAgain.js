PagePanel = Ext.extend(Ext.Panel, {
	closable : true,

	height:800,
	layout : 'border',
	getSearchForm : function() {
		var applyUser = new Ext.form.TextField({
			name : "applyUser",
			fieldLabel : "申请人",
			id : "applyUser",
			width : 150
		});
		var delegateApplyUser = new Ext.form.TextField({
			name : "delegateApplyUser",
			fieldLabel : "代申请人",
			id : "delegateApplyUser",
			width : 150
		});
		var applyName = new Ext.form.TextField({
			name : "applyName",
			fieldLabel : "申请编号",
			id : "applyName",
			width : 150
		});

		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 60,
			frame : true,
			title:'查询条件',
			//collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			items : [{
				html : "申请编号:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyName, {
				html : "申请人:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyUser, {
				html : "代申请人:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, delegateApplyUser
		
			]
		});

		return this.panel;
	},
	
	
	items : this.items,
	initComponent : function(){
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		this.fp = this.getSearchForm();

		this.store = new Ext.data.JsonStore({
				url : webContext + "/accountMailAction_listAllAccountApply.action",
				fields : ['id', 'name', 'applyUser', 'deleUser',
						'applyDate','status','definame','endpage'],
				root : 'data',
				totalProperty : 'rowCount',
				sortInfo : {
					field : "id",
					direction : "DESC"
				},
				baseParams : {
				}
			});
		var pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		
		
		var searchConfig=function(){
			var param = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("grid").getStore();
	        param.start = 0;  
	        store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
	        store.load({
	            params : param
	        });
		};
		var searchButton = new Ext.Button({
			style : 'margin:2px 0px 2px 5px',
			handler :searchConfig,
			pressed:true,
			text : '查询',
			iconCls : 'search'
		});
		var resetButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			handler :function(){
				Ext.getCmp("searchForm").getForm().reset();
			},
			text : '重置',
			iconCls : 'reset'
		});
		var AddButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler :function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要发送邮件的申请!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('提示', '请先选择要发送邮件的申请!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('提示', '每次只能为一个申请发送邮件!');
					return;
				}
			
				var accountId=record.get("id");
				var status=record.get("status");
				if(status=='审批中'){
			      	Ext.Msg.show({
					   title:'提示',
					   msg: '是否确认给该节点审批人发送邮件？',
					   buttons: Ext.Msg.OKCANCEL,
					   fn:function(btn){
						   if(btn=='ok'){
								Ext.Ajax.request({
									url : webContext
											+ '/accountMailAction_sendAuditMailToUser.action',
									params : {
										user:'',
										dataId : accountId
									},
									success : function(response, options) {
										var r = Ext.decode(response.responseText);
										if(r.success){
												Ext.Msg.alert("提示",r.mes);
										}
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("提示", "发送邮件失败!");
									}
								}, this);
						   }
					   },
					    icon: Ext.MessageBox.INFO
					   });
						      
				}else{
					  
					      	Ext.Msg.show({
							   title:'提示',
							   msg: '是否确认给申请人或代申请人发送邮件？',
							   buttons: Ext.Msg.OKCANCEL,
							   fn:function(btn){
								   if(btn=='ok'){
										Ext.Ajax.request({
											url : webContext
													+ '/accountMailAction_sendEndMailToUser.action',
											params : {
												user:'',
												dataId : accountId
											},
											success : function(response, options) {
												var r = Ext.decode(response.responseText);
												if(r.success){
														Ext.Msg.alert("提示",r.mes);
												}
											},
											failure : function(response, options) {
												Ext.MessageBox.alert("提示", "发送邮件失败!");
											}
										}, this);
								   }
							   },
							    icon: Ext.MessageBox.INFO
							   });
						      
				}
			
			},
			text : '发送邮件',
			iconCls : 'forward'
		});
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		mybuttons.push(searchButton);
		mybuttons.push(resetButton);
		mybuttons.push(AddButton);
		this.grid = new Ext.grid.EditorGridPanel({
			id:"grid",
			name:"grid",
			region : "center",
			store : this.store,
			viewConfig : {
				forceFit : true
			},
			columns : [sm, {
				header : '自动编号',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				hidden : true
			}, {
				header : '申请编号',
				dataIndex : 'name',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '申请人',
				dataIndex : 'applyUser',
				align : 'center',
				sortable : false,
				hidden : false
			}, {
				header : '代申请人',
				dataIndex : 'deleUser',
				align : 'center',
				sortable : false,
				hidden : false
			}, {
				header : '申请日期',
				dataIndex : 'applyDate',
				align : 'center',
				sortable : false,
				hidden : false
			},{
				header : '当前状态',
				dataIndex : 'status',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '申请类型',
				dataIndex : 'definame',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '结束页面',
				dataIndex : 'endpage',
				align : 'center',
				sortable : false,
				hidden : true
			}
			],
			sm : sm,
			loadMask : true,
			tbar : mybuttons,
			bbar : pageBar,
			listeners :{'rowdblclick':function(gd,num){
				var id=gd.store.getAt(num).get("id");
				var page=gd.store.getAt(num).get("endpage");
				var url='/user/account/report/forword.jsp?&dataId='+id+'&url='+page;
				window.open(url,"","");
			
			}} 
		});
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		var params={start:0};
		this.store.baseParams=params;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});

        this.store.load({
            params :params
        });
		PagePanel.superclass.initComponent.call(this);
	}
});