testKernel = function(result){
		if(result=='REPEAT'){
			alert("同级有重复");//Ext.MessageBox.
			//Ext.getCmp("tree").kernel = 'RING';
			return ;
		}else if(result=='RINGORREPEAT'){
			alert("同级有重复或有环");
			return ;
		}else if(result=='REPEATC'){
			alert("同级有重复配置项");
			//Ext.getCmp("tree").kernel = 'REPEATC';
			return ;
		}else if(result=='REPEATS'){
			alert("同级有重复服务项");
			//Ext.getCmp("tree").kernel = 'REPEATS';
			return ;
		}else if (result=='RINGORREPEATC'){
			//Ext.getCmp("tree").kernel = 'RINGORREPEATC';
			alert("同级有重复配置项或有环");
			return ;
		}else if (result=='RINGORREPEATS'){
			//Ext.getCmp("tree").kernel = 'RINGORREPEATS';
			alert("同级有重复服务项或有环");
			return ;
		}
	}

AddExistConfigPanel = Ext.extend(Ext.Panel, {
	id : "AddExistConfigPanel",
	//title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true 
	},
	layout : 'border',
	
	ddFunction : function(root,node, refNode, selections,dItemFlag) { 
		
    for(var i = 0; i < selections.length; i++) {
    	//alert(selections.length+"selections.length");
		//alert(i);
    	var record = selections[i];//这个record表示表格选中的数据        
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),             
            id: record.get('id'),   
            leaf: record.get('leaf')   
        }), refNode);      
         //模板
       
         var id = "";//应该是关系的id
 		 //模板行项目父节点ID,
    	 var parentId = node.id; //此配置项的父配置项的id   
    	 //alert(node.id);
         //表格配置项id
 		 var configItemId = record.get('ConfigItem$id'); //配置项的id	**********要知道往树上拖的是配置项还是服务项
 		 //alert(configItemId);
         //表格数据名称
         var configItemName = record.get('ConfigItem$name');//配置项的名字        
         //页面模板id
         var itemId = this.shipPicId;//配置项关系名的id，不是关系id 
         //alert(itemId);
         var order = node.indexOf(node.lastChild);	//当前的父节点下的子节点的排号	
        // alert(dItemFlag);
     //同步保存到数据库   
     DWREngine.setAsync(false);
     //ConfigItemRelationManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order);
     ConfigItemRelationNewManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order,dItemFlag,testKernel);
    DWREngine.setAsync(false);
     //父节点加载数据
     //node.reload();
     //Ext.getCmp("tree").root.reload();   
     // DWREngine.setAsync(true);    
    }   
   Ext.getCmp("tree").root.reload();
    return true;
},

	goBackByPage:function(){
			window.location = webContext+'/user/require/specialRequire/makeInfoByEngineer.jsp';
		//history.back();
	},
	addConfigByPage:function(){
			//var record = this.grid.getSelectionModel().getSelected();
			var records = this.grid.getSelectionModel().getSelections();
					if(records.length<1){
						Ext.Msg.alert("提示","请先选择要添加的配置项!");
						return;
					}
//					alert(this.rt+"root");
//					alert(this.nd.parentNode+"fu");
//					alert(this.nd.nextSibling+"xiong");
		var flag=this.ddFunction(this.rt,this.nd, null, records,"配置项管理");
		if(flag==true){
			Ext.Msg.alert("提示","添加成功!",function(){
				Ext.getCmp("newConfig").close();
				
			});
		}else{
			Ext.Msg.alert("提示","添加失败!");
		}
					
	},
	searchByPage:function(){
		 var param = this.panel.form.getValues(false);
            param.methodCall = 'query';
            param.start = 1;  
            
            param.status=1;
            this.formValue = param;         
            this.pageBar.formValue = this.formValue;
            this.store.removeAll();
            this.store.load({
                      params : param
            });
	},
	getSearchForm : function() {
		var da = new DataAction();
		data = da.getElementsForQuery(this.clazz);
		//alert("getSearchForm data:"+data);
		var biddata = da.split(data);
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
			layoutConfig : {
				columns : 6
			},
			title : "查询列表",
			items : biddata
		});

		return this.panel;
	},
	//remove configItem
	removeConfigItem : function(){
			    var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
					if(!record){
						Ext.Msg.alert("提示","请先选择要查看的行!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("提示","查看时只能选择一行!");
						return;
					}
				var scId = record.get("ConfigItem$id");
				alert(scId);
				return;
				Ext.Ajax.request({
					
					url : webContext+'/serviceCatalogue_remove.action',
					//url : webContext+'/extjs/pageData?method=remove',
					params : {
						dataId : scId,
						model: this.model
					},
					success : function(response, options) {
						window.location= webContext+"/user/service/serviceCatalogueList.jsp";
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("删除失败");
					}
				}, this);
	},
	//end
	items : this.items,
	initComponent : function(){
		this.rt = this.root;
		this.nd = this.node;
		this.shipPicId = this.picId;
		var da = new DataAction();
		var json = da.getPageModel("configItemAddListPage", "","");
        this.modelTableName = json.pageModel[0].modelTableName;
        this.model = json.pageModel[0].name;
        //var buttonStrs=json.pageModelBtns;
        //alert("buttonstrs:"+buttonStrs);
        var buttonUtil=new ButtonUtil();
        this.mybuttons=buttonUtil.getButtonForModel(this.model,this);
        //alert(this.mybuttons.length);
        this.panelname = json.panels[0].panelname;      
		this.clazz = json.panels[0].clazz;
		this.fp = this.getSearchForm();
		var obj = da.getListPanelElementsForHead(this.panelname);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
            var modelTableId = this.modelTableName+"$id";         
            var isHidden = headItem.isHidden;
            if(isHidden=='true'){
            	isHiddenColumn = true;
            }
			//if (propertyName == modelTableId) {//"id" by peixf
			//	isHidden = true;
				//alert(propertyName+":"+isHidden);
			//}
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
		this.store = da.getPanelJsonStore(this.panelname,obj);//add by peixf
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
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
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		//alert(this.mybuttons);
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 295,
			anchor : '0 -35',
			tbar : [this.mybuttons],
			bbar : this.pageBar
		});
		
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modify, this);
		this.on("searchByPage",this.searchByPage,this);
		this.on("addConfigByPage",this.addConfigByPage,this);
		this.on("goBackByPage",this.goBackByPage,this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		AddExistConfigPanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'status':1
			
		};
		
//		this.pageBar.formValue = param;
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