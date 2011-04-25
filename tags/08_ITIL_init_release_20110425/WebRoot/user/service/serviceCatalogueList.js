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
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("page_ServiceCatalogue"); //da.getElementsForQuery("com.digitalchina.itil.service.entity.ServiceCatalogue");
		//var url = webContext+'/extjs/pageView?method=query&panelname='+panelname;      
       // var data = da.ajaxGetData(url);   
		
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
	modifyInPage:function(){
		var record = this.grid.getSelectionModel().getSelected();
	    var records = this.grid.getSelectionModel().getSelections();
		if(!record){
				Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
				return;
			}
		if(records.length>1){
				Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
				return;
			}
		var id = record.get("ServiceCatalogue$id");//********************************�õ��޸��е�id
		window.location=webContext+"/sciRelationShip_getRootRelationShipData.action?rootCataId="+id;
	},
    cataLogueView:function(){
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
        var dataId = record.get("ServiceCatalogue$id");
        window.location= webContext+"/user/service/sciRelationShipList.jsp?dataId="+dataId;  
    },
       search : function() {
                    var param = this.fp.form.getValues(false);
                  //  alert(Ext.encode(param)); //**** query params ***
                    param.methodCall = 'query';
                     param.status = 1;  
                    param.start = 1;  
                    param.rootFlag=1;
                    this.formValue = param;         
                    this.pageBar.formValue = this.formValue;
                    this.store.removeAll();
                    this.store.load({
                              params : param
                    });
          },
          
	removeServiceCatelogue : function(){
			  
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
				var scId = record.get("ServiceCatalogue$id");
				
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
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
        var buttonUtil=new ButtonUtil();
        this.mybuttons=buttonUtil.getButtonForModel("page_ServiceCatalogue",this);
		this.fp = this.getSearchForm();
		 this.modelTableName="ServiceCatalogue";
		 this.model="page_ServiceCatalogue";//add by dashi
		var obj = da.getListPanelElementsForHead("page_ServiceCatalogue");
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
        var fieldsGrid = new Array();
          for(i=0;i<obj.length;i++){
                    fieldsGrid[i] = obj[i].dataIndex;
          }
          this.store = new Ext.data.JsonStore({ 
                    id: Ext.id(),
                    url : webContext+'/extjs/pageData?method=pageQuery&panelname='+"page_ServiceCatalogue", //�ĳ�pageQuery����
                    root:"data",
                    fields:fieldsGrid,
                              totalProperty:"rowCount",
                              remoteSort:false,             
                              timeout:8000
                    });    
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbarExt({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			id : 'serviceCataGrid',
			region : "center",
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			tbar : [this.mybuttons],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.cataLogueView, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		this.on("modifyInPage",this.modifyInPage,this);
		this.on("delete",this.removeServiceCatelogue,this);
        this.on("select",this.search,this);            
		PagePanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
            'rootFlag' :1,                
			'start' : 1,
			'status': 1
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





