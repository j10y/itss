//url后参数编码
function eunicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "@" + s.charCodeAt(i) + ";";
	}
	return rs;
}
PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	layout : 'border',
	modifyby : function() {
		var record = Ext.getCmp('typeGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('typeGrid').getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行！");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行！");
			return;
		}
		var dataId = record.get("id"); 
		var typeName=eunicode(record.get("name")); 
		window.location = webContext+"/user/event/eventTypeManager/newEventType.jsp?dataId="+dataId+"&typeName="+typeName;

	},
	    getSearchForm : function() {
			this.panel = new Ext.form.FormPanel({
				region : "north",
				layout : 'table',
				width : 'auto',
				height : 60,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:2px'
				},
				layoutConfig : {
					columns : 2
				},
				title : "查询列表",
				items : [{html: "事件类型名称:",cls: 'common-text',style:'width:150;text-align:right'}
			    	,{id : 'typeName',
				     xtype : 'textfield',
			       	fieldLabel : '事件类型名称',
			       	width : '200'
				}]
			});
			return this.panel;
		},
		
	remove : function() {
		var record = Ext.getCmp('typeGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('typeGrid').getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要删除的行！");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('提示', '最少选择一条信息进行删除！');
			return;
		}
		var id = new Array();
		var da = new DataAction();
		var firm  =Ext.Msg.confirm('提示', '您确定要进行删除操作吗？', function(button) {
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){	
					id[i] = records[i].get("id");
				Ext.Ajax.request({
						url : webContext
								+ "/eventAction_removeEventType.action",
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("提示","数据删除失败！");
	                       }else{  
							  var store=Ext.getCmp("typeGrid").getStore();
					          var params={
					          			 start : 0, 
					                     typeName : Ext.getCmp("typeName").getRawValue()
					                     };
//					          Ext.getCmp("pagetoolBar").formValue=param;
					          store.on('beforeload', function(a) {   
										      Ext.apply(a.baseParams,params);   
								});
					          store.load({
					              params : params
					          });
		                    }
	                   },scope:this
						
					});
				}
			}
		}, this);

	  },
	items : this.items,
	initComponent : function() {
		this.fp = this.getSearchForm();
		this.store = new Ext.data.JsonStore({
			url : webContext + '/eventAction_findAllEventTypeByName.action',
			fields : ['id', 'name'],
			root : 'data',
			totalProperty :'rowCount',
			id : 'id'
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel(
		  [sm,
		  	{header : "自动编号",
			dataIndex : "id",
			width : 70   
		     },
			{header : "事件类型",
			dataIndex : "name",
			width : 150   
		}]);
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			id : "typeGrid",
			store : this.store,
			cm : this.cm,
			sm:sm,
			tbar : [{
				text : "新建",
				scope : this,
				iconCls : "add",
				handler : function() {
					window.location = webContext
							+ "/user/event/eventTypeManager/newEventType.jsp";
				}
			},'-',{
				text : "查询",
				scope : this,
				iconCls : "search",
				handler : function(){
					var typeName = Ext.getCmp('typeName').getValue();
					this.store.removeAll();
					var param = {
						start : 0,
						typeName : typeName
					};
					this.store.load({
						params : param
					});
				}
			},'-',{
				text : "删除",
				scope : this,
				iconCls : "remove",
				handler : this.remove
			} ,   new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >【双击后查看详细信息】</font>")],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modifyby, this);
		var item=new Array();
		item.push(this.fp);
		item.push(this.grid);
		this.items = item;
		var params = {
			 start : 0,
			 typeName: ""
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
		this.store.removeAll();
		this.store.load({
			params : params
		});
		PagePanel.superclass.initComponent.call(this);
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
