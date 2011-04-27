PagePanel = Ext.extend(Ext.Panel, {
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
    getUserMenuInfoPanel : function(){
  		this.usertMenuInfo= new Ext.form.FormPanel({
		    id:'usertMenuInfo',
		    width: 600,
		    layout:'table',
		    layoutConfig: {
		       columns : 4
		    },
		    items: [
		    	{html : '用户:　',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},
		    	new Ext.form.ComboBox({
					xtype : 'combo',
					id : 'userInfo',
					width : 200,
					lazyRender : true,
					displayField : 'userName',
					valueField : 'id',
					emptyText : '请输入ITCODE进行选择...',
					name : 'userInfo',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
					store : new Ext.data.JsonStore({
						url : webContext
								+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.UserInfo',
						fields : ['id', 'userName'],
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['userInfo'] == undefined) {
									opt.params['userName'] = Ext
											.getCmp('userInfo').defaultParam;
								}
							}
						},
						totalProperty : 'rowCount',
						root : 'data',
						id : 'id'
					}),
					pageSize : 10,
					listeners : {
						'beforequery' : function(queryEvent) {if(this.readOnly==true){return false}
							var param = queryEvent.combo.getRawValue();
							this.defaultParam = param;
							if (queryEvent.query == '') {
								param = '';
							}
							this.store.load({
								params : {
									userName : param,
									start : 0
								}
							});
							return true;
						},
						'select':function(combo, record, index){
							Ext.getCmp('usertMenuTree').getRootNode().reload();
						}
					}
				})
		    ]
		});
		return this.usertMenuInfo;
    },
    getUserMenuTreePanel : function(){
  		this.usertMenuTree= new Ext.tree.TreePanel({
					id: 'usertMenuTree',
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
								url : webContext + '/menu_loadUserExtraMenu.action',
								listeners : {
									'beforeload' : function(treeloader, node) {
										var userId = Ext.getCmp('userInfo').getValue();
											treeloader.baseParams = {
												userId : userId,
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
							var userId = Ext.getCmp("userInfo").getValue();
							var nodeId = node.id;
							if(checked == true){
								if(userId==""){
									alert("请选择用户!");
								}else{
									Ext.Ajax.request({
											url : webContext + '/menu_addUserExtraMenuItem.action',
											params : {
												userId : userId,
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
										url : webContext + '/menu_removeUserExtraMenuItem.action',
										params : {
											userId : userId,
											templateMenuItemId : nodeId
										},
					        		success:function(response){	
					        		},
					        		failure:function(response){
					        			Ext.Msg.alert("错误信息","删除数据失败");
					        		}
								});
							}
						}
					}
				});

		return this.usertMenuTree;
    },
	items : this.items,
	initComponent : function() {
		var items = new Array();
		items.push(this.getUserMenuInfoPanel());
		items.push(this.getUserMenuTreePanel());
		this.items = items;
		PagePanel.superclass.initComponent.call(this);
	}
})

