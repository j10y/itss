testKernel = function(result){
		if(result=='REPEAT'){
			alert("ͬ�����ظ�");//Ext.MessageBox.
			//Ext.getCmp("tree").kernel = 'RING';
			return ;
		}else if(result=='RINGORREPEAT'){
			alert("ͬ�����ظ����л�");
			return ;
		}else if(result=='REPEATC'){
			alert("ͬ�����ظ�������");
			//Ext.getCmp("tree").kernel = 'REPEATC';
			return ;
		}else if(result=='REPEATS'){
			alert("ͬ�����ظ�������");
			//Ext.getCmp("tree").kernel = 'REPEATS';
			return ;
		}else if (result=='RINGORREPEATC'){
			//Ext.getCmp("tree").kernel = 'RINGORREPEATC';
			alert("ͬ�����ظ���������л�");
			return ;
		}else if (result=='RINGORREPEATS'){
			//Ext.getCmp("tree").kernel = 'RINGORREPEATS';
			alert("ͬ�����ظ���������л�");
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
    	var record = selections[i];//���record��ʾ���ѡ�е�����        
        node.insertBefore(new Ext.tree.TreeNode({   
            text: record.get('name'),             
            id: record.get('id'),   
            leaf: record.get('leaf')   
        }), refNode);      
         //ģ��
       
         var id = "";//Ӧ���ǹ�ϵ��id
 		 //ģ������Ŀ���ڵ�ID,
    	 var parentId = node.id; //��������ĸ��������id   
    	 //alert(node.id);
         //���������id
 		 var configItemId = record.get('ConfigItem$id'); //�������id	**********Ҫ֪���������ϵ���������Ƿ�����
 		 //alert(configItemId);
         //�����������
         var configItemName = record.get('ConfigItem$name');//�����������        
         //ҳ��ģ��id
         var itemId = this.shipPicId;//�������ϵ����id�����ǹ�ϵid 
         //alert(itemId);
         var order = node.indexOf(node.lastChild);	//��ǰ�ĸ��ڵ��µ��ӽڵ���ź�	
        // alert(dItemFlag);
     //ͬ�����浽���ݿ�   
     DWREngine.setAsync(false);
     //ConfigItemRelationManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order);
     ConfigItemRelationNewManager.ajaxSaveConfigItem(id, parentId, configItemName, configItemId, itemId,order,dItemFlag,testKernel);
    DWREngine.setAsync(false);
     //���ڵ��������
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
						Ext.Msg.alert("��ʾ","����ѡ��Ҫ��ӵ�������!");
						return;
					}
//					alert(this.rt+"root");
//					alert(this.nd.parentNode+"fu");
//					alert(this.nd.nextSibling+"xiong");
		var flag=this.ddFunction(this.rt,this.nd, null, records,"���������");
		if(flag==true){
			Ext.Msg.alert("��ʾ","��ӳɹ�!",function(){
				Ext.getCmp("newConfig").close();
				
			});
		}else{
			Ext.Msg.alert("��ʾ","���ʧ��!");
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
			title : "��ѯ�б�",
			items : biddata
		});

		return this.panel;
	},
	//remove configItem
	removeConfigItem : function(){
			    var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
					if(!record){
						Ext.Msg.alert("��ʾ","����ѡ��Ҫ�鿴����!");
						return;
					}
					if(records.length>1){
						Ext.Msg.alert("��ʾ","�鿴ʱֻ��ѡ��һ��!");
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
						Ext.MessageBox.alert("ɾ��ʧ��");
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
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
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