
// 订单列表Panel基类
CusApplayPanel = Ext.extend(Ext.Panel, {
	id : "CusApplayPanel",
	closable : true,
	viewConfig : {//自适应填充
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
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
		var id = record.get("id");//********************************得到修改行的id
		
		var clazz = "com.zsgj.itil.actor.entity.Customer";
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz,id);
		var custdata = da.split(data);
		
		var envForm = new Ext.form.FormPanel({
			layout : 'table',
			width : "auto",
			height : 490,
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store=this.store;
		var win1 = new Ext.Window({
				title : '修改用户信息',
				width : 800,
				height : 500,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',

				buttons : [{
					text : '保存',
					handler : function() {
						var bsParam = envForm.form.getValues(false);
						da.saveData(clazz,bsParam,function(){
						
							store.reload();
							win1.close()
						});
					},scope : this
				},
					{
					text : '关闭',
					handler : function() {
							win1.close();	
					},
					listeners: {
						'beforeclose':  function(p) {
							return true;
						}
					},
					scope : this
				}]
			});
		win1.show();
	},
	remove : function() {
		var clazz = "com.zsgj.itil.actor.entity.Customer";
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
					id[i] = records[i].data.id;
				Ext.Ajax.request({
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz='+clazz,
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

	},
	// 搜索
	search : function() {
		var param = this.fp.form.getValues(false);
		param.methodCall = 'query';
        param.start = 1;           
		this.formValue = param;         
		this.pageBar.formValue = this.formValue;
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	create : function() {
		var clazz = "com.zsgj.itil.actor.entity.Customer";
		var da = new DataAction();
		this.items = da.split(da.getElementsForAdd(clazz));
		var statusForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 775,
			height : 500,
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:15px '
			},
			frame : true,
			items : this.items

		});
		var store = this.store;
	    var win = new Ext.Window({
	    	title:"增加客户信息",
				width : 700,
				height : 500,
				maximizable : true,
				modal : true,
				items : statusForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{
					text : '保存 ',
					handler : function() {
						var bsParam = statusForm.form.getValues(false);
						da.saveData(clazz,bsParam,function(){ store.reload(); win.close()});
					},
					scope : this

				}, {
					text : '关闭',
					handler : function() {
						win.hide();
					},
					scope : this
				}]

			});
		win.show();

	}, 

getSearchForm : function() {
        var da = new DataAction();
        var clazz = "com.zsgj.itil.actor.entity.Customer";
        data = da.getElementsForQuery(clazz);
         var biddata = da.split(data);
        this.panel = new Ext.form.FormPanel({
                              region:"north",
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
        
         return  this.panel;         
},

 items:this.items,
	// 初始化
	initComponent : function() {//在这里面定义的变量是全局变量
		//this.removIds='';
		this.fp=this.getSearchForm();
		var da = new DataAction();
		var clazz = "com.zsgj.itil.actor.entity.Customer";
		var obj=da.getElementsForHead(clazz);//得到所有的字段名
		var sm =new Ext.grid.CheckboxSelectionModel();
		var columns= new Array();
		var fields = new Array();
		columns[0]=sm;
		for(var i=0;i<obj.length;i++){
			var headItem = obj[i];
			var title = headItem.header;//
			var alignStyle ='left';
			var propertyName = headItem.dataIndex;//
			var isHidden=false;
			if(propertyName=="id"){
			 isHidden =true;
			}
		var columnItem={
			header:title,
			dataIndex:propertyName,
			sortable:true,
			hidden:isHidden,
			align:alignStyle
		}
		columns[i+1]=columnItem;
		fields[i]=propertyName;
		}
		this.storeMapping=fields;
		this.cm=new Ext.grid.ColumnModel(columns);
		this.store = da.getJsonStore(clazz);
		this.store.paramNames.sort="orderBy";
		this.store.paramNames.dir="orderType";
		this.cm.defaultSortable=true;
		
		var viewConfig = Ext.apply({//什么意思？
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbarExt({
			pageSize :10, //sys_pageSize,//使用的是系统默认值
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
            region:"center",               
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			anchor : '0 -35',
			tbar : ['   ', {
				text : '添加',
				pressed : true,
				handler : this.create,
				scope : this,
				iconCls : 'add'
			}, '   ', {
				text : '删除',
				pressed : true,
				handler : this.remove,
				scope : this,
				iconCls : 'remove'
			}, '    &nbsp;|&nbsp;    ', {
				text : '查询',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '   ', {
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
		// this.grid.on("rowdblclick",this.openDetail,this);
		// this.grid.on("rowdblclick", this.create, this);//
		this.grid.on("headerdblclick", this.fitWidth, this);//行(row)被右击时触发
		this.grid.on("rowdblclick",this.modify,this);//双击行监听
	         var items=new Array();
             items.push(this.fp);
             items.push(this.grid);
            this.items=items;
          CusApplayPanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 1
		};
		this.pageBar.formValue =param; 
		this.store.removeAll();//删除数据存储器中的所有数据
		this.store.load({
			params : param

		});
		// this.search();

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
