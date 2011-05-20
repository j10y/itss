PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	//title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			title : "查询列表",
			layoutConfig : {
				columns :6
			},
			items : [
		    	{html : '所属主表:　',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.ComboBox({
					hiddenName : 'systemMainTableIdBuilder.SystemMainTable',
					id : 'systemMainTableIdBuilder.SystemMainTableCombo',
					width : 200,
					style : '',
					fieldLabel : '系统主表',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'tableCnName',
					valueField : 'id',
					emptyText : '请选择...',
					allowBlank : false,
					
					name : 'systemMainTableIdBuilder.SystemMainTable',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
			        store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.info.appframework.metadata.entity.SystemMainTable',
							fields : ['id', 'tableCnName'],
							listeners : {
								load : function(store, opt) {
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
					})
		        }),
		        {html : '隶属部门:　',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.ComboBox({
					hiddenName : 'systemMainTableIdBuilder.department',
					id : 'systemMainTableIdBuilder.departmentCombo',
					width : 200,
					style : '',
					fieldLabel : '隶属部门',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'departName',
					valueField : 'departCode',
					emptyText : '请选择...',
					allowBlank : false,
					
					name : 'systemMainTableIdBuilder.department',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,	        
					store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.zsgj.info.framework.security.entity.Department',
							fields : ['departCode', 'departName'],
							listeners : {
								load : function(store, opt) {
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
					})
		        })]
		});
		return this.panel;
	},  
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
        var buttonUtil=new ButtonUtil();
        this.mybuttons=buttonUtil.getButtonForModel("page_tableCodeBuilder",this);
		this.fp = this.getSearchForm();
		 this.modelTableName="tSystemMainTableIdBuilder";
		var obj = da.getListPanelElementsForHead("panel_tableCodeBuilder");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		var param = {
			pageSize : 10,
			start : 1,
			orderBy : "id",
			isAsc : 1,
			systemMainTable : "",
			department : ""
			
		};		

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		// 创建表格数据
		this.store = new Ext.data.JsonStore({
			url : webContext + '/IdGeneratorAction_query.action',
			fields : ['id','systemMainTable','department', 'prefix', 'length', 'ruleFileName',
					'deployFlag', 'latestValue'],
			totalProperty : "rowCount",
			root : "data",
			id : 'idGeneratorStore'//,
//			listeners :{
//  				load :function(){
//  					var porletContent = Ext.getCmp('66666');
//        				var pageData = porletContent.getStore().getRange();
//        				alert(pageData.length);
//        				for(var i = 0;i<pageData.length;i++){
//        						alert(pageData[i].get("id"));
//	       						alert(pageData[i].get("tableName"));
//        				}
//  				
//  				}
//			}	
		});				
		this.pageBar = new Ext.PagingToolbarExt({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			id:"displayPanel",
			region : "center",
			store : this.store,
			sm : sm,
			columns : [sm, {
				id : 'id',
				header : "编号",
				width : 50,
				sortable : true,
				dataIndex : 'id',
				hidden : true
			}, {
				header : "所属主表",
				width : 100,
				sortable : true,
				dataIndex : 'systemMainTable'
			}, {
				header : "隶属部门",
				width : 150,
				sortable : true,
				dataIndex : 'department'
			}, {
				header : "前缀",
				width : 100,
				sortable : true,
				dataIndex : 'prefix'
			}, {
				header : "长度",
				width : 100,
				sortable : true,
				dataIndex : 'length'
			}, {
				header : "规则文件",
				width : 100,
				sortable : true,
				dataIndex : 'ruleFileName'
			}, {
				header : "是否发布标志",
				width : 100,
				sortable : true,
				dataIndex : 'deployFlag'
			}, {
				header : "最新编号值",
				width : 100,
				sortable : true,
				dataIndex : 'latestValue'
			}],			
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',

			tbar:[{
					text:"新增",
					handler:function(){
						window.location.href=webContext
								+"/infoAdmin/idgen/tableIdGenForm.jsp";
					},
					scope:this
					},"-",{
					text:"查询",
					handler:function(){
						Ext.Ajax.request({
							url : webContext + '/IdGeneratorAction_query.action',
							params : {
									pageSize : 12,
									start : 1,
									orderBy : "id",
									isAsc : 1,
									systemMainTable : Ext.getCmp("systemMainTableIdBuilder.SystemMainTableCombo").getValue(),
									department : Ext.getCmp("systemMainTableIdBuilder.departmentCombo").getValue()
							},
							success : function(response){
								var responseArray =Ext.util.JSON.decode(response.responseText); 
								var idGeneratorStore =Ext.getCmp("displayPanel").getStore();
								idGeneratorStore.removeAll();
								if(responseArray.data!=''){
									idGeneratorStore.load({
										data:responseArray.data
									});
								}
							},
							failure : function(response){
								Ext.MessageBox.alert("错误信息","查询时出错");
							}
						})
					},
					scope:this						
					},"-",{
					text:"修改",
					handler:function(){
						var record = this.grid.getSelectionModel().getSelected();
						var records = this.grid.getSelectionModel().getSelections();
						if(!record){
							Ext.Msg.alert("提示","请选择要修改的信息");
							return;
						}
						if(record.length == 0){
							Ext.Msg.alert("提示","至少选择一条进行修改！");
						}
						if (records.length > 1) {
							Ext.Msg.alert('提示', '修改不可以选择多条！');
							return;
						}
						window.location.href=webContext
								+"/infoAdmin/idgen/tableIdGenForm.jsp?recordId="+record.get("id")+"&isUpdate=1";
					},
					scope:this						
					},"-",{
					text:"重置",
					handler:function(){
							alert("重置");
					},
					scope:this						
					},"-",{
					text:"删除",
					handler:function(){
						var record = this.grid.getSelectionModel().getSelected();
						var records = this.grid.getSelectionModel().getSelections();
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
								id[i] = records[i].get('id');
								Ext.Ajax.request({
										url : webContext
												+ '/IdGeneratorAction_delete.action',
										params : {id:id[i]},
										timeout:100000, 
								        success:function(response){
								        		var responseArray =Ext.util.JSON.decode(response.responseText);	
								        		Ext.Msg.alert("提示","删除数据成功");
								        		window.location.href=webContext+"/infoAdmin/idgen/tableIdGenList.jsp";
								        },
								        failure:function(response){
								        		Ext.Msg.alert("错误信息","删除数据失败");
								        }						
									});
								}
							}
						}, this);
					},
					scope:this						
					},"-",{
					text:"导出",
					handler:function(){
							alert("导出");
					},
					scope:this						
					}],
			bbar : this.pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modify, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 1
		};
		
		this.pageBar.formValue = param;
		
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});