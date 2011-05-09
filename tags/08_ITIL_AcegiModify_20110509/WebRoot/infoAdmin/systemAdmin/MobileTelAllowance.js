PagePanel = Ext.extend(Ext.Panel, {
	closable : true,

	height:800,
	layout : 'border',
	getSearchForm : function() {
		var applyNum = new Ext.form.TextField({
			name : "deptname",
			fieldLabel : "岗位编号",
			id : "deptname",
			width : 150
		});
		
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
				html : "岗位编号:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:center'
			}, applyNum
		
			]
		});

		return this.panel;
	},
	getAddform :function(){
	
		var addform= new Ext.form.FormPanel({
			id:"addForm",
			layout : 'table',
			title : '增加手机补贴标准信息',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
			items : [{
				html : "岗位编号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "adeptname",
				fieldLabel : "岗位编号",
				id : "adeptname",
				allowBlank : false,
				width : 200
			}),{
				html : "岗位名称:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "amanageritcode",
				fieldLabel : "岗位名称",
				id : "amanageritcode",
				width : 200
			}),{
				html : "补贴标准:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "amanagername",
				fieldLabel : "补贴标准",
				id : "amanagername",
				allowBlank : false,
				width : 200
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
					var adeptname=Ext.getCmp('adeptname').getValue().trim();
					var amanageritcode=Ext.getCmp('amanageritcode').getValue().trim();
					var amanagername=Ext.getCmp('amanagername').getValue().trim();
					
					if(adeptname==''||adeptname==null){
						Ext.MessageBox.alert("提示","岗位编号不能为空,谢谢您的合作!");
						return false;
					}
					if(amanagername==''||amanagername==null){
						Ext.MessageBox.alert("提示","补贴标准不能为空,谢谢您的合作!");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_addMobileTelAllowance.action',
						params : {
							deptname : adeptname,
							manageritcode : amanageritcode,
							managername : amanagername
							
						},
						success : function(response, options) {
							var r = Ext.decode(response.responseText);
							
								Ext.Msg.alert("提示", "保存成功", function() {
									Ext.getCmp('grid').store.load();
									Ext.getCmp('addwin').close();
								});
							
							
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
	getModifyform :function(mid,department,countSignItcode,countSignName){
		var modifyform= new Ext.form.FormPanel({
			id:"modifyForm",
			layout : 'table',
			title : '修改手机补贴标准信息',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
			items : [{
				html : "岗位编号:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mdeptname",
				fieldLabel : "岗位编号",
				id : "mdeptname",
				readOnly:true,
				allowBlank : false,
				value:department,
				width : 200
			}),{
				html : "岗位名称:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mmanageritcode",
				fieldLabel : "岗位名称",
				id : "mmanageritcode",
				value:countSignItcode,
				allowBlank : true,
				width : 200
			}),{
				html : "补贴标准:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mmanagername",
				fieldLabel : "补贴标准",
				id : "mmanagername",
				allowBlank : false,
				value:countSignName,
				width : 200
			}),new Ext.form.Hidden({
					xtype : 'hidden',
					id : 'mid',
					name : 'mspid',
					value : mid,
					fieldLabel : 'isTemp'
				})
			],
			buttons : [{
				text : '保存修改',
				iconCls : 'save',
				id:'save',
				handler : function() {
					if(!Ext.getCmp('modifyForm').form.isValid()){
					Ext.MessageBox.alert("提示","所有信息为必填项,请填写完整。谢谢您合作！");	
					return false;
					}
					var amanageritcode=Ext.getCmp('mmanageritcode').getValue().trim();
					var amanagername=Ext.getCmp('mmanagername').getValue().trim();
					var mid=Ext.getCmp('mid').getValue();
					
					if(amanagername==''||amanagername==null){
						Ext.MessageBox.alert("提示","补贴标准不能为空,谢谢您的合作!");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyMobileTelAllowance.action',
						params : {
							id:mid,
							manageritcode : amanageritcode,
							managername : amanagername
						},
						success : function(response, options) {
							
							var r = Ext.decode(response.responseText);
							if(r.success){
									Ext.Msg.alert("提示", "保存成功", function() {
										Ext.getCmp('grid').store.load();
										Ext.getCmp('modifywin').close();
									});
								
							}else{
								Ext.Msg.alert("提示","保存失败！");
									Ext.getCmp("save").enable();
									Ext.getCmp("back").enable();
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
					Ext.getCmp('modifywin').close();
				}
			}]
		});
		
		return modifyform;
	},
	
	items : this.items,
	initComponent : function(){
		var getModifyform=this.getModifyform;
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		this.fp = this.getSearchForm();

		this.store = new Ext.data.JsonStore({
				url : webContext + "/accountSystemAdminAction_listMobileTelAllowance.action",
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
	                height:180,   
	                items: [this.getAddform()]   
	           	});   
	           	addwin.show();
			},
			text : '添加',
			iconCls : 'add'
		});
		var removeButton = new Ext.Button({
			text:'修改',
			pressed:true,
			style : 'margin:2px 0px 2px 5px',
			iconCls:'edit',
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
				var department=record.get("department");
				var countSignItcode=record.get("countSignItcode");
				var countSignName=record.get("countSignName");
				
				var win= new Ext.Window({ 
					modal : true,
					id:'modifywin',
	                width:340,   
	                height:180,   
	                items: [getModifyform(mid,department,countSignItcode,countSignName)]   
	           	});   
	           	win.show();

			}
		});
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		mybuttons.push(searchButton);
		mybuttons.push(resetButton);
		mybuttons.push(AddButton);
		mybuttons.push(removeButton);
		
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
				header : '岗位编号',
				dataIndex : 'department',
				align : 'center',
				width:150,
				sortable : false,
				hidden : false
			}, {
				header : '岗位名称',
				dataIndex : 'countSignItcode',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '补贴标准',
				dataIndex : 'countSignName',
				align : 'center',
				sortable : true,
				width:150,
				hidden : false
			}
			],
			sm : sm,
			loadMask : true,
			tbar : mybuttons,
			bbar : pageBar,
			listeners :{'rowdblclick':function(gd,num){
				var mid=gd.store.getAt(num).get("id");
				var department=gd.store.getAt(num).get("department");
				var countSignItcode=gd.store.getAt(num).get("countSignItcode");
				var countSignName=gd.store.getAt(num).get("countSignName");
			
				var win= new Ext.Window({ 
					modal : true,
					id:'modifywin',
	                width:340,   
	                height:180,   
	                items: [getModifyform(mid,department,countSignItcode,countSignName)]   
	           	});   
				
	           	win.show();
			}} 
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