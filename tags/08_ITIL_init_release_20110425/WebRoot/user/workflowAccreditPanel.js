﻿
//授权
AccreditPanel = Ext.extend(Ext.Panel,{
	id:Ext.id(), 
	closable: true,
	userId:'',
	viewConfig: {
        autoFill: true,
      	forceFit: true
    },
  	layout: 'absolute',
  	
	reset:function(){
  		this.fp.form.reset();
  	},
	//搜索
    search:function(){  
    	var param = this.fp.form.getValues(false);
    	param.actorId = this.userId;
    	param.methodCall='query';
    	this.formValue = param;
    	this.pageBar.formValue = this.formValue;
    	param.start=1;
    	this.store.removeAll();
    	//alert(Ext.encode(param));
    	this.store.load({params:param});
    	this.render();
    },

	save : function(){ 
		var clazz = "com.digitalchina.info.framework.workflow.entity.TaskPreAssign";
		var records = this.grid.getStore().getModifiedRecords();
		var da = new DataAction();
		for(i=0;i<records.length;i++){
			var data = {};
			var record = Ext.apply(data, records[i].data,{});
			var proxyBegin = Ext.encode(data.proxyBegin);
			var p = proxyBegin.indexOf("T");
			if(p>1){
				proxyBegin = proxyBegin.substring(1,p);
				data.proxyBegin = proxyBegin;
			}
			else{
				data.proxyBegin = "";
			}
			
			
			var proxyEnd = Ext.encode(data.proxyEnd);
			var p = proxyEnd.indexOf("T");
			if(p>1){
				proxyEnd = proxyEnd.substring(1,p);
				data.proxyEnd = proxyEnd;
			}
			else{
				data.proxyEnd = "";
			}	
					
			//alert(Ext.encode(data));

			da.saveData(clazz,data,Ext.emptyFn);
		}
		if(records.length>0){
			Ext.MessageBox.alert("提示信息：","修改成功，所做修改仅对于新启动的流程有效。");
		}
		else{
			Ext.MessageBox.alert("提示信息：","没有数据被修改。");
		}
//		window.location = webContext+"/order/orderItem/orderApplyInfo.jsp";
	},
	
	getTopbar: function(){
		return ['   ',
	        	{    
	                text: '保存',  
					pressed: true,           
	                handler: this.save,
	                scope:this,
	                iconCls:'save'
	            },'    &nbsp;|&nbsp;    ',
	        	{    
	                text: '查询',  
					pressed: true,           
	                handler: this.search,
	                scope:this,
	                iconCls:'search'
	            },'    ',
            	{    
	                text: ' 重置',  
	                pressed: true,           
	                handler: this.reset,
	                scope:this,
	                iconCls:'reset'
	            }
	        ];
	},

	//初始化
    initComponent : function(){ 
    	this.fp = new AccreditSearchForm();
    	   	
        var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();

   		var da = new DataAction();
   		var clazz = "com.digitalchina.info.framework.workflow.entity.TaskPreAssign";
   		var obj = da.getElementsForHead(clazz)
   		
   		var columns = new Array();
		var fields = new Array();
   		columns[0] = sm;
	    for (var i = 0; i < obj.length; i++) {    	
	        var headItem = obj[i];
	        var title  = headItem.header;
	       	var alignStyle = 'left';

	        var propertyName = headItem.dataIndex; 
	       
	        var isHidden = false;
	        if(propertyName=='id'){
	        	isHidden = true;
	        }
	        var editor = '';
	        var renderer = '';
	        if(propertyName=='proxyBegin'||propertyName=='proxyEnd'){
	        	editor = new Ext.form.DateField({format:'Y-m-d'});
	        	renderer = Ext.util.Format.dateRenderer('Y-m-d');     
	        }
	        if(propertyName=='proxyId'){
	        	editor = new Ext.form.TextField();
	        }
	        var columnItem = {
	            header: title,
	            dataIndex: propertyName,
	            sortable: true,
	            hidden: isHidden,
	            editor: editor,
	            renderer: renderer,
	            align: alignStyle
	        };
	        columns[i+1] = columnItem;
	        fields[i] = propertyName;
	    }
       	this.store = da.getJsonStore(clazz);
	  	this.storeMapping = fields; 

        this.cm= new Ext.grid.ColumnModel(columns);
        
      

      	this.cm.defaultSortable=true;   	  	
        AccreditPanel.superclass.initComponent.call(this);
        var viewConfig=Ext.apply({forceFit:true},this.gridViewConfig);  
        
        this.pageBar = new Ext.PagingToolbarExt({
            pageSize: 10,
            store: this.store,
            displayInfo: true,
            displayMsg: '当前显示 {0}-{1}条记录 /共{2}条记录',
            emptyMsg: "无显示数据"
        });
        this.formValue = '';
        this.pageBar.formValue = this.formValue;
        this.topbar = this.getTopbar();
        this.grid=new Ext.grid.EditorGridPanel({
	        store: this.store,
	        cm: this.cm,
	        sm:sm,
	        trackMouseOver:false, 
	        clicksToEdit : 2,
	        loadMask: true,
	        y:40,
	        anchor: '0 -35',
	        tbar: this.topbar,
	        bbar: this.pageBar
   		});   		   		

   		this.grid.on("headerdblclick",this.fitWidth,this);  
   		
   		this.add(this.fp);
   		this.add(this.grid);
   		this.initData();
   		//this.store.load({params:{actorId:this.userId}});		
    },
    fitWidth:function(grid, columnIndex, e){  
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
    },
    initData:function(){
    	var param = {'methodCall':'list','start':1};
    	param.actorId = this.userId;
   		this.pageBar.formValue = param; 
   		//alert(Ext.encode(param));		
   		this.store.load({params:param});
    }
});
