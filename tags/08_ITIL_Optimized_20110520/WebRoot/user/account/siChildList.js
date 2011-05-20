PagePanel = Ext.extend(Ext.Panel, {
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true 
	},
	layout : 'border',

	show:function(){ 
	 	var record =  Ext.getCmp('grid').getSelectionModel().getSelected();
		var records =  Ext.getCmp('grid').getSelectionModel().getSelections();
		if(!record){
			Ext.Msg.alert("提示","请先选择要查看的行!");
			return;
		}
		if(records.length>1){
			Ext.Msg.alert("提示","查看时只能选择一行!");
			return;
		}
		var id = record.get(this.tableName+"$id");
		var status = record.get(this.tableName+"$status");
		var processType = record.get(this.tableName+"$processType");
		var serviceItemId = this.rootId;

		if(id==''){
		Ext.MessageBox.alert("友情提示","请双击具体的申请流程,谢谢您的合作!");
		return false;
		}
		if(status==2){
			window.location = webContext + "/requireAction_toProcessEndPage.action?dataId="+id+"&serviceItemId="+serviceItemId+"&processType="+processType;
		}else{
			window.location = webContext +"/requireAction_toOperatePageAccount.action?serviceItemId="+serviceItemId+"&processType="+processType+"&dataId="+id;
		}
      },
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
		var DataHead=da.getListPanelElementsForHead(this.gridName);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < DataHead.length; i++) {
			var headItem = DataHead[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;         
            var isHidden = headItem.isHidden;
            if(isHidden=='true'){
                    isHiddenColumn = true;  
            }   
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store= da.getPanelJsonStoreForUser(this.gridName, DataHead, 'createUser');
		//this.fp = this.getSearchForm();
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		var pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		var serviceItemId = this.rootId;
		this.searchConfig=function(){
			var param = Ext.getCmp("searchForm").getForm().getValues(false);
			var store=Ext.getCmp("grid").getStore();
	        param.start = 0;  
	        param.status=0;
	        param.serviceItem=serviceItemId;
	        //pageBar.formValue=param;
	        store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});

	        store.load({
	            params : param
	        });
		};

		var removeButton = new Ext.Button({
			text:'删除草稿',
			pressed:true,
			iconCls:'remove',
			scope : this,
			handler:function(){
				var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("提示", "请先选择要删除的行!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('提示', '最少选择一条信息，进行删除!');
					return;
				}
				for(var i=0;i<records.length;i++){
					var status = records[i].get(this.tableName+"$status");
					var tempId = records[i].get(this.tableName+"$id");
					if(tempId==""){
						Ext.MessageBox.alert('警告', '您选择的不是数据信息，不能进行非数据操作！');
						return;
					}
					if(status=='1'||status=='2'||status==1||status==2||status=='3'||status==3){
						Ext.MessageBox.alert('警告', '不能删除非草稿类数据!');
						return;
					}
				}
				var idName = this.tableName+"$id";
				var id = new Array();
				var da = new DataAction();
				var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
					if (button == 'yes') {
						for(var i=0;i<records.length;i++){
							id[i] = records[i].get(idName);
							this.store.remove(records[i]);
							Ext.Ajax.request({
								url : webContext+ '/requireAction_removeDraft.action?serviceItemId='+this.rootId,
								params : {id:id[i]},
								timeout:100000,
								success:function(response){
									var r =Ext.decode(response.responseText);
									if(!r.success){
										Ext.Msg.alert("提示信息","数据删除失败",function(){

										});
									}
									this.store.reload();
								},scope:this

							});
						}
					}
				}, this);
			}
		});
		var mybuttons = new Array();
		var buttonUtil = new ButtonUtil();
		var pButtons = buttonUtil.getButtonForProcess(this.rootId,this);
		mybuttons.push(pButtons);
		mybuttons.push(removeButton);
		this.grid = new Ext.grid.GridPanel({
			id:"grid",
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : mybuttons,
			bbar : pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.show, this);
		var items = new Array();
	//	items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		var prompt= new Ext.Toolbar.TextItem('&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<font color=red><==  提示：点击按钮进入申请页面</font>')
		this.grid.getTopToolbar().add(prompt);

		PagePanel.superclass.initComponent.call(this);
		var param = {
			 start : 0,
			 status:0,
			 serviceItem:this.rootId
		};
		pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
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