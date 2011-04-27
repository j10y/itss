﻿﻿﻿
/*
 * 部门菜单模板列表面板（查询,修改）
 * 
 */
	DeptMenuTemplatePanel = Ext.extend(Ext.Panel,{
		id: "deptMenuTemplatePanel",
		title: "部门菜单模板查询",
		closable: true,
		viewConfig: {
	        autoFill: true,
	      	forceFit: true
	    },
	  	layout: 'absolute',
	  	
	  	reset: function(){
	  		this.searchForm.form.reset();
	  	},
	  	
	    search: function(){     	
	    	if(!deptMenuTemplateSearchForm.beforeSearch()){
	    		return;
	    	}
	    	var param = this.searchForm.form.getValues();
	    	this.formValue = param;
	    	this.pageBar.formValue = this.formValue;
	    	param.start=1;
	    	this.store.removeAll();
	    	this.store.load({params:param});
	    },
	    
	    //初始化菜单
	    initMenu: function(){
	    	
	    },
	    
	    modifyMenuTemplate: function(){
	    	
	    	var record = this.grid.getSelectionModel().getSelected();
    		var records = this.grid.getSelectionModel().getSelections();
			if(!record){
				Ext.Msg.alert("提示","请先选择要修改的行!");
				return;
			}
			if(records.length>1){
				Ext.Msg.alert("提示","修改时只能选择一行!");
				return;
			}
	    	var dmtId = record.get("id");
	    	var smtId = record.get("smtId");
//	    	alert("部门菜单模板ID："+dmtId);
//	    	alert("部门菜单模板所属系统模板的ID："+smtId);
	    	window.open(webContext+"/servlet/deptMenuModify?smtId="+smtId+"&dmtId="+dmtId,"部门菜单","height=500,width=400,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,fullscreen=0 top=0 left=250");
	    	
	    },
	    
		initComponent : function(){
		   //查询表单
		   if(deptMenuTemplateSearchForm!=undefined){
				this.searchForm = deptMenuTemplateSearchForm.form;
		   }	
		   var csm = new Ext.grid.CheckboxSelectionModel();
		   this.storeMapping = ['templateName', 'systemMenuTemplate', 'dept', 'adminFlag', 'smtId', 'id']; 
		   this.cm = new Ext.grid.ColumnModel([csm,{
	                    header: "部门菜单模板名称",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "templateName",
	                    width: 200,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "所属系统菜单模板",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "systemMenuTemplate",
	                    width: 200,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "隶属部门",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "dept",
	                    width: 200,
	                    editor: new Ext.form.TextField()
                	}, {
	                    header: "是否是部门管理员标志",
	                    sortable: true,
	                    hideable: false,
	                    dataIndex: "adminFlag",
	                    width: 200,
	                    editor: new Ext.form.TextField()
                	}, {
                		header: "smtId",
                		sortable: true,
			            hidden: true,
			            dataIndex: "smtId"
		     	   },  {
                		header: "id",
                		sortable: true,
			            hidden: true,
			            dataIndex: "id"
		     	   }
		     ]);
	       DeptMenuTemplatePanel.superclass.initComponent.call(this);
	       //数据
		   this.store = new Ext.data.JsonStore({
				id: "id",
		       	url: webContext+'/servlet/deptMenuTemplateSearch',
		       	root: "data",
		  		totalProperty: "rowCount",
		  		remoteSort: false,  
		  		timeout: 3000000,
	  			fields: this.storeMapping
	  		});
	  		
	      	this.store.paramNames.sort = "orderBy";
		 	this.store.paramNames.dir = "orderType";	  
	      	this.cm.defaultSortable = true;
	      	
	        var viewConfig=Ext.apply({forceFit:true},this.gridViewConfig);  
	        //分页工具栏
	        
	        this.pageBar = new Ext.PagingToolbarExt({
	            pageSize: 10,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '当前显示 {0}-{1}条记录 /共{2}条记录',
	            emptyMsg: "无显示数据"
	        });
	        this.formValue = '';
	        this.pageBar.formValue = this.formValue;
	        
	   		//工具栏
	   		this.grid=new Ext.grid.GridPanel({
		        store: this.store,
		        cm: this.cm,
		        sm: csm,
		        trackMouseOver: false,    
		        loadMask: true,
		        y:30,
		        anchor: '0 -30',
		        clicksToEdit:1,
		        tbar : ['   ',
		       		 {    
		                text: ' 查询',  
		                pressed: true,           
		                handler: this.search,
		                scope: this,
		                iconCls: 'search',
		                cls: "x-btn-text-icon"
		            },'   ',		            
		            {    
		                text: ' 重置',  
		                pressed: true,          
		                handler: this.reset,
		                scope: this,
		                iconCls: 'reset',
		                cls: "x-btn-text-icon"
		            },'&nbsp;|&nbsp;',
		            {    
		                text: '修改模板',  
		                pressed: true, 
		                id: 'modifyTemplate',
		                handler: this.modifyMenuTemplate,
		                scope: this,
		                iconCls: 'edit',
		                cls: "x-btn-text-icon"
		            }
		        ],
		        
		        bbar: this.pageBar
	   		});
	   		this.grid.on("headerdblclick",this.fitWidth,this);  

	   		this.searchForm.height="200";
	   		this.searchForm.width="1000";
	   		
	   		this.add(this.searchForm);	   		
	   		this.add(this.grid);	   		
	   		
		},
		
		initData:function(){
			var param = {'start':1};
	   		this.pageBar.formValue = param;
	   		this.store.removeAll();
	   		this.store.load({params:param});
		},
		
		fitWidth:function(grid, columnIndex, e){  
			var c = columnIndex;
			var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
			for (var i = 0, l = grid.store.getCount(); i < l; i++) {
				w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
			}
			grid.colModel.setColumnWidth(c, w);	
	    }
});

