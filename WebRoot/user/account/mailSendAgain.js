PagePanel = Ext.extend(Ext.Panel, {
	closable : true,

	height:800,
	layout : 'border',
	getSearchForm : function() {
		var applyUser = new Ext.form.TextField({
			name : "applyUser",
			fieldLabel : "������",
			id : "applyUser",
			width : 150
		});
		var delegateApplyUser = new Ext.form.TextField({
			name : "delegateApplyUser",
			fieldLabel : "��������",
			id : "delegateApplyUser",
			width : 150
		});
		var applyName = new Ext.form.TextField({
			name : "applyName",
			fieldLabel : "������",
			id : "applyName",
			width : 150
		});

		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 60,
			frame : true,
			title:'��ѯ����',
			//collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			items : [{
				html : "������:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyName, {
				html : "������:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyUser, {
				html : "��������:",
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
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
			text : '��ѯ',
			iconCls : 'search'
		});
		var resetButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			handler :function(){
				Ext.getCmp("searchForm").getForm().reset();
			},
			text : '����',
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
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�����ʼ�������!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('��ʾ', '����ѡ��Ҫ�����ʼ�������!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('��ʾ', 'ÿ��ֻ��Ϊһ�����뷢���ʼ�!');
					return;
				}
			
				var accountId=record.get("id");
				var status=record.get("status");
				if(status=='������'){
			      	Ext.Msg.show({
					   title:'��ʾ',
					   msg: '�Ƿ�ȷ�ϸ��ýڵ������˷����ʼ���',
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
												Ext.Msg.alert("��ʾ",r.mes);
										}
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "�����ʼ�ʧ��!");
									}
								}, this);
						   }
					   },
					    icon: Ext.MessageBox.INFO
					   });
						      
				}else{
					  
					      	Ext.Msg.show({
							   title:'��ʾ',
							   msg: '�Ƿ�ȷ�ϸ������˻�������˷����ʼ���',
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
														Ext.Msg.alert("��ʾ",r.mes);
												}
											},
											failure : function(response, options) {
												Ext.MessageBox.alert("��ʾ", "�����ʼ�ʧ��!");
											}
										}, this);
								   }
							   },
							    icon: Ext.MessageBox.INFO
							   });
						      
				}
			
			},
			text : '�����ʼ�',
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
				header : '�Զ����',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				hidden : true
			}, {
				header : '������',
				dataIndex : 'name',
				align : 'center',
				sortable : true,
				hidden : false
			}, {
				header : '������',
				dataIndex : 'applyUser',
				align : 'center',
				sortable : false,
				hidden : false
			}, {
				header : '��������',
				dataIndex : 'deleUser',
				align : 'center',
				sortable : false,
				hidden : false
			}, {
				header : '��������',
				dataIndex : 'applyDate',
				align : 'center',
				sortable : false,
				hidden : false
			},{
				header : '��ǰ״̬',
				dataIndex : 'status',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '��������',
				dataIndex : 'definame',
				align : 'center',
				sortable : false,
				hidden : false
			},
			{
				header : '����ҳ��',
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