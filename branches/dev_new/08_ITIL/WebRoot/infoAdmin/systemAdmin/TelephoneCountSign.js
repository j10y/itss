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
		
		this.panel = new Ext.form.FormPanel({
			id:"searchForm",
			region : "north", 
			layout : 'table',
			height : 230,
			frame : true,
			
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 1
			},
			items : [
			 {
			xtype : 'fieldset',
		    //title : '注意事项',
			layout : 'table',
		    width : '100%',
			autoHeight : true,
		    defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			items:[{
				html : '<h3><font color=red>座机和手机加签人变更说明:</font></h3><br> 1、页面显示的部门名称是座机和手机申请流程中字段“隶属部门”下拉列表中显示的部门信息。<br>2、每个部门必须对应一个加签人。<br>3、在修改加签人信息之前提交的流程，流转到加签人处理环节时会找到修改之前的加签人去审批。修改加签人只对修改以后提交的申请有效，且修改加签人不会把已经流转到原加签人处审批的任务转移到新加签人处。<br>4、删除记录后在手机和座机申请的“隶属部门”下拉列表中将不再出现此删除的记录。',
				cls : 'common-text',
				colspan : 4,
				rowspan : 0
			}]},
			{
			xtype : 'fieldset',
		    title : '查询条件',
			layout : 'table',
		    anchor : '100%',
			autoHeight : true,
		    /*defaults : {
				bodyStyle : 'padding:4px'
			},*/
			layoutConfig : {
				columns : 4
			},
			items:[{
				html : "部门名称:",
				cls : 'common-text',
				width : 150,
				style : 'width:150;text-align:center'
			}, applyNum]}
			
		
			]
		});

		return this.panel;
	},
	getAddform :function(){
	
		var addform= new Ext.form.FormPanel({
			id:"addForm",
			layout : 'table',
			title : '增加部门加签人信息',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
			items : [{
				html : "部门名称:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "adeptname",
				fieldLabel : "部门名称",
				id : "adeptname",
				allowBlank : false,
				width : 200
			}),{
				html : "加签人itcode:",
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
				html : "加签人姓名:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "amanagername",
				fieldLabel : "加签人姓名",
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
						Ext.MessageBox.alert("提示","部门名称不能为空,谢谢您的合作!");
						return false;
					}
					if(amanageritcode==''||amanageritcode==null){
						Ext.MessageBox.alert("提示","加签人itcode不能为空,谢谢您的合作!");
						return false;
					}
					if(amanagername==''||amanagername==null){
						Ext.MessageBox.alert("提示","加签人姓名不能为空,谢谢您的合作!");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_addTelephoneCountSign.action',
						params : {
							deptname : adeptname,
							manageritcode : amanageritcode,
							managername : amanagername
							
						},
						success : function(response, options) {
							var r = Ext.decode(response.responseText);
							if(r.noitcode){
								Ext.Msg.alert("提示","保存失败，系统中没有这个itcode！");
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
	getModifyform :function(mid,department,countSignItcode,countSignName){
		var modifyform= new Ext.form.FormPanel({
			id:"modifyForm",
			layout : 'table',
			title : '修改部门加签人信息',
			frame : true,
			collapsible : false,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			
			items : [{
				html : "部门名称:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mdeptname",
				fieldLabel : "部门名称",
				id : "mdeptname",
				allowBlank : false,
				value:department,
				width : 200
			}),{
				html : "加签人itcode:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mmanageritcode",
				fieldLabel : "加签人itcode",
				id : "mmanageritcode",
				value:countSignItcode,
				allowBlank : false,
				width : 200
			}),{
				html : "加签人姓名:",
				cls : 'common-text',
				width : 100,
				style : 'width:150;text-align:right'
			}, new Ext.form.TextField({
				name : "mmanagername",
				fieldLabel : "加签人姓名",
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
					var adeptname=Ext.getCmp('mdeptname').getValue().trim();
					var amanageritcode=Ext.getCmp('mmanageritcode').getValue().trim();
					var amanagername=Ext.getCmp('mmanagername').getValue().trim();
					var mid=Ext.getCmp('mid').getValue();
					if(adeptname==''||adeptname==null){
						Ext.MessageBox.alert("提示","部门名称不能为空,谢谢您的合作!");
						return false;
					}
					if(amanageritcode==''||amanageritcode==null){
						Ext.MessageBox.alert("提示","加签人itcode不能为空,谢谢您的合作!");
						return false;
					}
					if(amanagername==''||amanagername==null){
						Ext.MessageBox.alert("提示","加签人姓名不能为空,谢谢您的合作!");
						return false;
					}
					Ext.getCmp("save").disable();
					Ext.getCmp("back").disable();
					Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyTelephoneCountSign.action',
						params : {
							id:mid,
							deptname : adeptname,
							manageritcode : amanageritcode,
							managername : amanagername
						},
						success : function(response, options) {
							
							var r = Ext.decode(response.responseText);
							if(r.success){
								if(r.noitcode){
									Ext.Msg.alert("提示","保存失败，系统中没有这个itcode！");
									Ext.getCmp("save").enable();
									Ext.getCmp("back").enable();
								}else{
									Ext.Msg.alert("提示", "保存成功", function() {
										Ext.getCmp('grid').store.load();
										Ext.getCmp('modifywin').close();
									});
								}
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
				url : webContext + "/accountSystemAdminAction_listTelephoneCountSign.action",
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
				var department=record.get("department");
				Ext.Ajax.request({
						url : webContext
								+ '/accountSystemAdminAction_modifyTelephoneCountSign.action',
						params : {
							id:mid,
							deptname : department,
							manageritcode : 'admin',
							managername : 'ITSS系统管理员'
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
				header : '部门名称',
				dataIndex : 'department',
				align : 'center',
				width:150,
				sortable : false,
				hidden : false
			}, {
				header : '加签人ITCODE',
				dataIndex : 'countSignItcode',
				align : 'center',
				sortable : false,
				width:150,
				hidden : false
			}, {
				header : '加签人姓名',
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
		//pageBar.formValue=params;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});

        this.store.load({
            params :params
        });
		PagePanel.superclass.initComponent.call(this);
	}
});