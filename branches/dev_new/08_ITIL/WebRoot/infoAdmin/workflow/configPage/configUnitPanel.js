ConfigUnitListPanel = Ext.extend(Ext.Panel, {
	id : "configUnitListPanel",	
	closable : true,
	viewConfig : {//自适应填充
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	reset : function() {
		this.fp.form.reset();
	},
	// 修改
	modify:function(){
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
		var id = record.get("id");
		this.mofifyForm = new ConfigUnitModifyRecordPanel({configUnit:id});		
		this.mofifyWin = new Ext.Window({
		 	title:'配置单元修改信息窗口',
		 	modal: true,
		 	height:300,
	        width:600,
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
	        	handler : this.mofifyForm.saveConfigItem,
	        	text : ' 保存',
	        	scope : this			
				},{xtype:'button',
	        	handler:this.mofifyForm.returned,
	        	text:'关闭',
	        	scope:this
	        	}],
        	items:[this.mofifyForm]
		});
		this.mofifyWin.show();
					
		
	},
	remove : function() {
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
		for(var i=0;i<records.length;i++){			
			id[i] = records[i].data.id;			
		}

		var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {
				Ext.Ajax.request({
						url : webContext
								+ '/configUnit_deleteConfigUnit.action',
						params : {							
							removeIds : id
						},	
						timeout:1000000,
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("提示信息","数据删除失败",function(){	                       			
	                       			this.store.reload();
	                       		});
	                       }
	                       else{
                                Ext.Msg.alert("提示信息","数据删除成功!",function(){                                 	
                                	this.store.reload();
                                },this);
	                       }	                      
	                       
	                   },scope:this							
					});
			}
		}, this);

	},	
	create : function() {	
		
		this.searchForm = new ConfigUnitAddRecordPanel;		
		this.detailWin = new Ext.Window({
		 	title:'配置单元录入信息',
		 	modal: true,
		 	height:300,
	        width:600,
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
	        	handler : this.searchForm.saveConfigItem,
	        	text : ' 保存',
	        	scope : this			
				},{xtype:'button',
	        	handler:this.searchForm.returned,
	        	text:'关闭',
	        	scope:this
	        	}],
        	items:[this.searchForm]
		});
		this.detailWin.show();
	},
	// 初始化
	initComponent : function() {//在这里面定义的变量是全局变量

		this.removeIds='';
		this.configItemName = '';		
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext+ '/configUnit_getConfigUnit.action',
			root : "data",
			fields : ["id", "configUnitName", "configUnitDesc", "configUnitLink"]
		});		
		var obj=[{
			header : "配置单元名称",
			dataIndex : "configUnitName"
		}, {
			header : "配置单元描述",
			dataIndex : "configUnitDesc"
		},
		{
			header : "配置单元链接",
			dataIndex : "configUnitLink"
		}];
		var columns = new Array();
		var fields = new Array();

		columns[0] = sm;
		
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;

			var isHidden = false;
			if (propertyName == 'id') {
				isHidden = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}

		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);

		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;//排序默认值	

		ConfigUnitListPanel.superclass.initComponent.call(this);//让父类先初始化
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,//使用的是系统默认值
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 0,
			anchor : '0 -0',
			tbar : ['   ', {
				text : '新增',
				pressed : true,
				handler : this.create,
				scope : this,
				iconCls : 'add'
			},'   ', {
				text : ' 重置',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			},'     ',{
				text : ' 修改',
				pressed : true,
				handler : this.modify,
				scope : this,
				iconCls : 'edit'
			}],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick",this.modify,this);//双击行监听
		//this.add(this.fp);
		this.add(this.grid);//大面板中加了两个小面板，一个放查询条件，一个放查询出的数据
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();//删除数据存储器中的所有数据
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
	},
	initData : function() {
		var param = {
			'methodCall' : 'list',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.load({
			params : param
		});
	}
});
