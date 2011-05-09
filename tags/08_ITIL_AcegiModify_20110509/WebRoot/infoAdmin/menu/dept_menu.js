PagePanel = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'border',
	width : 900,
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},

	forLooK : function() {
		var record = this.grid.getSelectionModel().getSelected();
		Ext.getCmp('deptMenuId').setValue(record.get('id'));
		Ext.getCmp('deptMenuName').setValue(record.get('name'));
		Ext.getCmp('deptMenuDeptCombo').setValue(record.get('deptCode'));
		Ext.getCmp('deptMenuDeptCombo').setRawValue(record.get('dept'));
		Ext.getCmp('deptMenuTree').getRootNode().reload();
	},
	remove : function(){
		var record = this.grid.getSelectionModel().getSelected();
		var id = record.get('id');
		var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {				
				Ext.Ajax.request({
						url : webContext + '/menu_removeDeptMenu.action',
						params : {id:id},
	        		success:function(response){	
	        			Ext.Msg.alert("提示","删除数据成功");
	        			window.location = webContext+ "/infoAdmin/menu/dept_menu.jsp";
	        		},
	        		failure:function(response){
	        			Ext.Msg.alert("错误信息","删除数据失败");
	        		}
				});
			}
		}, this);
    },
	getDeptMenuGrid:function(){
		this.storeList = new Ext.data.JsonStore({
			autoLoad : true,
			url : webContext + '/menu_listDeptMenus.action',
			fields : ['id', 'name', 'deptCode','dept'],
			root : 'data',
			id : 'id'
		});
		this.cm = new Ext.grid.ColumnModel([
			{header : "ID",dataIndex : "id",sortable : true,hidden:true},
			{header : "菜单名称",dataIndex : "name",width : 125,sortable : true},
			{header : "部门",dataIndex : "dept",width : 125,sortable : true}
			]);

		this.grid = new Ext.grid.GridPanel({
			store : this.storeList,
			id : "deptMenuGird",
			cm : this.cm,
			colspan : 2,
			autoScroll : true,
			loadMask : true,
			width : 265,
			region:'west',
			tbar : [{
						pressed : true,
						text : "新增",
						iconCls : 'add',
						scope : this,
						handler : function(){
							Ext.getCmp('deptMenuInfo').form.reset();
							Ext.getCmp('deptMenuTree').getRootNode().reload();
						}
					},{
		        		pressed : true,
						text : "删除",
						iconCls : 'remove',
						scope : this,
						handler :this.remove
		    }]
		});
		this.grid.on("rowdblclick", this.forLooK, this);
		return this.grid;
	},                  
    getDeptMenuInfoPanel : function(){
  		this.deptMenuInfo= new Ext.form.FormPanel({
		    id:'deptMenuInfo',
		    width: 600,
		    layout:'table',
		    layoutConfig: {
		       columns : 4
		    },
		    items: [
		    	{html : '菜单名称:　',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},
		    	new Ext.form.TextField({
					id : 'deptMenuName',
					name : 'deptMenuName',
					width : 200,
					allowBlank : false
		        }),
		        {html : '隶属部门:　',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.ComboBox({
					hiddenName : 'deptMenuDept',
					id : 'deptMenuDeptCombo',
					width : 200,
					fieldLabel : '隶属部门',
					displayField : 'departName',
					valueField : 'departCode',
					emptyText : '请选择...',
					allowBlank : false,
					triggerAction : 'all', 
					typeAhead : true,
					store : new Ext.data.JsonStore({
							url : webContext
									+ '/menu_findDepartmentComboList.action',
							fields : ['departCode', 'departName'],
							totalProperty : 'rowCount',
							root : 'data',
							id : 'departCode'
					}),
						pageSize : 10,
						listeners : {
							'beforequery' : function(queryEvent) {
									var param = queryEvent.combo.getRawValue();
									this.store.baseParams={departName : param};
									this.store.load();							
								return false;
							}
						}
		        }),	        
		    	new Ext.form.Hidden({		    	
		    		id:"deptMenuId",
		    		name:"deptMenuId",
		    		hidden:true
		    	}) 
		    ],
		    buttons: [{
		        text: '保存',
		        handler : function(){
		        		var form = Ext.getCmp('deptMenuInfo').getForm();
		        		var id = Ext.getCmp("deptMenuId").getValue();
		        		var deptMenuName = Ext.getCmp("deptMenuName").getValue();
		        		var deptMenuDeptCombo = Ext.getCmp("deptMenuDeptCombo").getValue();
						if(form.isValid){
	               			form.submit({
	               		 		clientValidation: true,
				        		url:webContext+'/menu_saveDeptMenu.action',
				        		params:{
				        			id:id,
				        			deptMenuName:deptMenuName,
				        			deptMenuDept:deptMenuDeptCombo
				        		},
				        		success:function(response){
				        			var responseArray =Ext.util.JSON.decode(response.responseText);	
				        			Ext.getCmp("deptMenuId").setValue(responseArray.id);
				        			Ext.getCmp("solutionGrid").getStore().reload();
				        			Ext.Msg.alert("提示","保存数据成功");
				        		},
				        		failure:function(response){
				        			Ext.Msg.alert("错误信息","保存数据失败");
				        		}
		        		});
		        	}
		        },
				scope:this
		    }]
		});
		return this.deptMenuInfo;
    },
    getDeptMenuTreePanel : function(){
  		this.deptMenuTree= new Ext.tree.TreePanel({
					id: 'deptMenuTree',
					width : 600,
					height: 500,
					useArrows:true,
			        autoScroll:true,
			        animate:true,
			        enableDD:false,
			        containerScroll: true,
			        rootVisible: false,
			        frame: true,
					loader : new Ext.tree.TreeLoader({
								url : webContext + '/menu_loadDeptMenu.action',
								listeners : {
									'beforeload' : function(treeloader, node) {
										var deptMenuId = Ext.getCmp('deptMenuId').getValue();
											treeloader.baseParams = {
												deptMenuId : deptMenuId,
												id : node.id
											};
										}
								}
							}),
					root : new Ext.tree.AsyncTreeNode({
								id : '0',
								text : "模板菜单",
								expanded : true
							}),
					listeners : {
						'checkchange':function(node, checked){
							var deptMenuId = Ext.getCmp("deptMenuId").getValue();
							var nodeId = node.id;
							if(checked == true){
								if(deptMenuId==""){
									alert("请先保存部门菜单信息");
								}else{
									Ext.Ajax.request({
											url : webContext + '/menu_addDeptMenuItem.action',
											params : {
												deptMenuId : deptMenuId,
												templateMenuItemId : nodeId
											},
						        		success:function(response){	
						        		},
						        		failure:function(response){
						        			Ext.Msg.alert("错误信息","删除数据失败");
						        		}
									});
								}
							}else{
								Ext.Ajax.request({
										url : webContext + '/menu_removeDeptMenuItem.action',
										params : {
											deptMenuId : deptMenuId,
											templateMenuItemId : nodeId
										},
					        		success:function(response){	
					        		},
					        		failure:function(response){
					        			Ext.Msg.alert("错误信息","删除数据失败");
					        		}
								});
							}
						},
						'click' : function(node, event) {
							if (node.isLeaf()) {
								// 为叶子节点时，点击不进入链接
								event.stopEvent();
							}
						}
					}
				});

		return this.deptMenuTree;
    },
	items : this.items,
	initComponent : function() {
		var knowledgePanel=new Ext.Panel({
			layout:'table',
			autoScroll : true,
			collapsible : false,
			height : 'auto',
			rowspan: 2,
			width :600,
			frame: true,
			layoutConfig:{
				columns:1
			},
			region:'center',
			items:[this.getDeptMenuInfoPanel(),this.getDeptMenuTreePanel()]
		});
		var items = new Array();
		items.push(this.getDeptMenuGrid());
		items.push(knowledgePanel);
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})

