PagePanel = Ext.extend(Ext.Panel, {
	closable : true,

	height:800,
	layout : 'border',
	getSearchForm : function() {
		var applyNum = new Ext.form.TextField({
			name : "deptname",
			fieldLabel : "所有者",
			id : "deptname",
			width : 150
		});
		/*var applytype = new Ext.form.TextField({
			name : "rbtype",
			fieldLabel : "红黑类型",
			id : "rbtype",
			emptyText:"请输入'红'或者'黑'",
			width : 150
		});*/
		
		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 40,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items : [{
				html : "用户ITCODE:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applyNum/*,{
				html : "红/黑类型:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:right'
			}, applytype*/
		
			]
		});

		return this.panel;
	},
	getAddform :function(){
	
		var addform= new Ext.form.FormPanel({
			id:"addForm",
			layout : 'table',
			title : '增加黑红名单信息',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
			items : [{
				html : "用户itcode:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "amanageritcode",
				fieldLabel : "加签人itcode",
				id : "amanageritcode",
				allowBlank : false,
				width : 200
			}),{
				html : "红/黑类型:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			},new Ext.form.ComboBox({
				xtype : 'combo',
				id : 'itil_ac_PersonFormalAccount$password',
				colspan : 0,
				rowspan : 0,
				name : 'itil_ac_PersonFormalAccount$password',
				width : 200,
			   // style : 'color:red',
			    displayField : 'text',
			    valueField : 'value',
				allowBlank : false,
				validator : '',
				fieldLabel : '备注说明',
				emptyText : '请选择...',
				mode: 'local',
				store:new Ext.data.SimpleStore({
			             fields: ['value', 'text'],
			             data : [['1','黑名单'],['0','红名单']]
			        })

			})
			],
			buttons : [{
				text : '保存',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('addForm').form.isValid()){
					Ext.MessageBox.alert("提示","所有信息为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var amanageritcode=Ext.getCmp('amanageritcode').getValue().trim();
					var amanagername=Ext.getCmp('itil_ac_PersonFormalAccount$password').getValue();
					
					if(amanageritcode==''||amanageritcode==null){
						Ext.MessageBox.alert("提示","用户itcode不能为空,谢谢您的合作!");
						return false;
					}
					if(amanagername==''||amanagername==null){
						Ext.MessageBox.alert("提示","红/黑类型不能为空,谢谢您的合作!");
						return false;
					}
					
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_addRAndBUserList.action',
						params : {
							manageritcode : amanageritcode,
							managername : amanagername
							
						},
						success : function(response, options) {
							var r = Ext.decode(response.responseText);
							if(r.noitcode==1){
								Ext.Msg.alert("提示","保存失败，系统中没有这个itcode！");
								Ext.getCmp("save").enable();
								Ext.getCmp("back").enable();
							}else if(r.noitcode==2){
									Ext.Msg.alert("提示","保存失败，这个用户已经在红黑名单中！");
									Ext.getCmp("save").enable();
									Ext.getCmp("back").enable();
							}else{
								Ext.Msg.alert("提示", "保存成功", function() {
									Ext.getCmp('grid').store.load();
									Ext.getCmp('addwin').close();
								});
							}
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "保存失败!");
							Ext.getCmp("save").enable();
			                Ext.getCmp("back").enable();
						}
					}, this);
				}
			},  {
				text : '取消',
			    id:'back',
				iconCls : 'back',
				handler : function() {
					Ext.getCmp('addwin').close();
				}
			}]
		});
		return addform;
	
	},
	
	items : this.items,
	initComponent : function(){
		var getModifyform=this.getModifyform;
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		this.fp = this.getSearchForm();

		this.store = new Ext.data.JsonStore({
				url : webContext + "/accountSystemAdminAction_listRAndBUserList.action",
				fields : ['id', 'countSignItcode', 'countSignName', 'department'],
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
				var addwin= new Ext.Window({ 
					id:'addwin',
	                width:340, 
	                modal : true,
	                height:160,   
	                items: [this.getAddform()]   
	           	});   
	           	addwin.show();
			},
			text : '添加',
			iconCls : 'add'
		});
		var removeButton = new Ext.Button({
			text:'修改为红名单',
			pressed:true,
			iconCls:'edit',
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要修改的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('提示', '请选择一条信息进行修改!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('提示', '每次只能修改一条记录!');
					return;
				}
			
				var mid=record.get("id");
								
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyRAndBUserList.action',
						params : {
							id:mid,
							manageritcode : '0'
						},
						success : function(response, options) {
						var r = Ext.decode(response.responseText);
							if(r.success){
									Ext.Msg.alert("提示", "修改成功", function() {
									Ext.getCmp('grid').store.load();
										
									});
							}else{
								Ext.Msg.alert("提示","保存失败！");
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "保存失败!");
						}
					}, this);
			}
		});
		var modifyToBlackButton = new Ext.Button({
			text:'修改为黑名单',
			pressed:true,
			iconCls:'edit',
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要修改的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('提示', '请选择一条信息进行修改!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('提示', '每次只能修改一条记录!');
					return;
				}
			
				var mid=record.get("id");
								
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyRAndBUserList.action',
						params : {
							id:mid,
							manageritcode : '1'
						},
						success : function(response, options) {
						var r = Ext.decode(response.responseText);
							if(r.success){
									Ext.Msg.alert("提示", "修改成功", function() {
									Ext.getCmp('grid').store.load();
										
									});
							}else{
								Ext.Msg.alert("提示","保存失败！");
							}
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "保存失败!");
						}
					}, this);
			}
		});
		var DelButton = new Ext.Button({
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			scope : this,
			handler :function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要删除的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('提示', '请选择一条信息进行删除!');
					return;
				}
				if(records.length > 1){
					Ext.MessageBox.alert('提示', '每次只能删除一条记录!');
					return;
				}
				var mid=record.get("id");
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_DeleteRAndBUserList.action',
						params : {
							id:mid
						},
						success : function(response, options) {
							
							var r = Ext.decode(response.responseText);
							if(r.success){
								if(r.noitcode){
								}else{
									Ext.Msg.alert("提示", "删除成功", function() {
										Ext.getCmp('grid').store.load();
									});
								}
							}else{
								Ext.Msg.alert("提示","删除失败！");
							}
							
						},
						failure : function(response, options) {
							Ext.MessageBox.alert("提示", "删除失败!");
							
						}
					}, this);
			},
			text : '删除',
			iconCls : 'remove'
		});
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		mybuttons.push(searchButton);
		mybuttons.push(resetButton);
		mybuttons.push(AddButton);
		mybuttons.push(removeButton);
		mybuttons.push(modifyToBlackButton);
		mybuttons.push(DelButton);
		
		this.grid = new Ext.grid.GridPanel({
			id:"grid",
			name:"grid",
			region : "center",
			store : this.store,
			columns : [sm, {
				header : '自动编号',
				dataIndex : 'id',
				align : 'left',
				sortable : true,
				width:150,
				hidden : true
			},  {
				header : '用户ITCODE',
				dataIndex : 'department',
				align : 'center',
				width:150,
				sortable : false,
				hidden : false
			}, {
				header : '红黑类型',
				dataIndex : 'countSignItcode',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '红黑类型',
				dataIndex : 'countSignName',
				align : 'center',
				sortable : false,
				width:150,
				hidden : true
			}
			],
			sm : sm,
			loadMask : true,
			tbar : mybuttons,
			bbar : pageBar
		});
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		var params={start:0};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
        this.store.load({
            params :params
        });
		PagePanel.superclass.initComponent.call(this);
	}
});