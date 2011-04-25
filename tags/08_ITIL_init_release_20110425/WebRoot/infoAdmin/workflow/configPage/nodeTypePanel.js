WorkflowListPanel = Ext.extend(Ext.Panel, {
	id : "WorkflowListPanel",	
	closable : true,
	viewConfig : {//自适应填充
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	// 修改
	matching:function(){
		var record = this.grid.getSelectionModel().getSelected();
	    var records = this.grid.getSelectionModel().getSelections();
		if(!record){
				Ext.Msg.alert("提示","请先选择要匹配的行!");
				return;
			}
		if(records.length>1){
				Ext.Msg.alert("提示","修改时只能选择一行!");
				return;
			}
		var id = record.get("id");
		this.matchingForm = new NodeTypeAndConfigUnitPanel({nodeId:id});		
		this.matchingWin = new Ext.Window({
		 	title:'配置单元修改信息窗口',
		 	modal: true,
		 	height:400,
	        width:930,
	        resizable:false,
	        constrain:false,
	        constrainHeader:false,
	        autoScroll:true,
	        viewConfig: {
		        autoFill: true,
		      	forceFit: true
		    },
		  	layout: 'absolute',
	        buttonAlign:'center',
	        buttons:[	        	
	        	{
	        	id : 'saveCI',
	        	xtype:'button',
	        	pressed : true,
	        	handler : this.matchingForm.saveRelation,
	        	text : ' 保存',
	        	scope : this			
				},{xtype:'button',
	        	handler:this.matchingForm.returned,
	        	text:'关闭',
	        	scope:this
	        	}],
        	items:[this.matchingForm]
		});
		this.matchingWin.show();
					
		
	},

	reset : function() {
		this.searchForm.form.reset();
	}, 
	// 初始化
	initComponent : function() {//在这里面定义的变量是全局变量
		
	

		this.csm = new Ext.grid.CheckboxSelectionModel();
		
		this.store = new Ext.data.JsonStore({ 				
				url: webContext+ '/nodeType_showAllNodeType.action',
				fields: ['id','name','desc','pattern'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.cm = new Ext.grid.ColumnModel([   
		   	this.csm,	    
		    {header: "节点类型名称", width: 100, sortable: true, dataIndex: 'name'}, 
		    {header: "节点类型描述", width: 100, sortable: true, dataIndex: 'desc'},
		    {header: "节点类型标识", width: 100, sortable: true, dataIndex: 'pattern'},
		    {id:'id', header: "id", width: 100, sortable: true, dataIndex: 'id',hidden:true}	
		]); 

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		
		// 创建分页ToolBar
		this.pageBar = new Ext.PagingToolbar({			
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : '无显示数据'
		});
		
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		
		this.nodeType = new Ext.form.TextField({
			name : 'nodeType',
			fieldLabel : '查询节点类型名称',
			width:200
		});				
		this.grid = new Ext.grid.GridPanel({ 
			
				id :'grid',
		        store: this.store,
		        cm: this.cm,
		        sm: this.csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 'auto',
		        width: 615,
		        y : 0,
				anchor : '0 -40',
		        frame:true,
		        ddGroup: "tgDD",
				enableDragDrop: true,    
		        autoScroll: true,
		        autoEncode : true,		        
		        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
		        tbar : [{
				text : ' 节点类型比配配置单元',
				pressed : true,
				handler : this.matching,
				scope : this,
				iconCls :'reset'
				}],
				bbar : this.pageBar
		}); 

		var param = {
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.load({
			params : param
		});
		
		this.items = [this.grid];	
		this.grid.on("rowdblclick",this.matching,this);//双击行监听
		WorkflowListPanel.superclass.initComponent.call(this);	
	}

});