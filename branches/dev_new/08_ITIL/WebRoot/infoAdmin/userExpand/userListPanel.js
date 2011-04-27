
// 用户自定义主表
userListPanel = Ext.extend(Ext.Panel, {
	id : "userListPanel",
	title:"用户自定义主表",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	create : function() {
		var clazz = this.cName;		
		var da = new DataAction();
		this.items = da.split(da.getElementsForAdd(clazz));		
		var statusForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 390,
			height : 200,
			layoutConfig : {
				columns : 2	
			},
			defaults : {
				bodyStyle : 'padding:15px '
			},
			frame : true,
			items : this.items

		});	
		var store = this.store;
	    var win = new Ext.Window({
				title : '添加用户自定义主表',
				width : 400,
				height : 200,
				maximizable : true,
				modal : true,
				items : statusForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//点击按钮时，首先从面板中得到所有数据，在提交给相应Action中的方法
					text : '保存',					
					handler : function() {
						//alert(clazz);
						var bsParam = statusForm.form.getValues(false);//该值是一个key和value的对象
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
	//修改
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
		var clazz = this.cName;		
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz,id);
		var custdata = da.split(data);		
		var statusForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 390,
			height : 200,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store = this.store;
		var win1 = new Ext.Window({
				title : '修改配置项状态信息',
				width : 400,
				height : 200,
				maximizable : true,
				items : statusForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',

				buttons : [{
					text : '保存',
					handler : function() {
						var bsParam = statusForm.form.getValues(false);
						da.saveData(clazz,bsParam,function(){ store.reload(); win1.close()});
					},
					scope : this

				}, {
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
	//删除
	remove : function() {
		var clazz = this.cName;
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
					id[i]= records[i].data.id;
					Ext.Ajax.request({
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz='+clazz,
						params : {
							//removeIds : removeIds
							id : id[i]
						},
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
	// 初始化
	initComponent : function() {//在这里面定义的变量是全局变量
		this.cName = className;
		this.removIds='';		
		var da = new DataAction();
		var clazz = this.cName;
		var obj=da.getElementsForHead(clazz);//得到所有的字段名
		if(obj==""){
			alert("登录超时，请重新登录");
			history.go(-1);
		}
		
		var sm =new Ext.grid.CheckboxSelectionModel();
		var columns= new Array();
		var fields = new Array();
		columns[0]=sm;
		for(var i=0;i<obj.length;i++){
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle ='left';
			var propertyName = headItem.dataIndex;
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
		userListPanel.superclass.initComponent.call(this);
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.grid = new Ext.grid.GridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
//			y : 40,
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
			},'     ',{
				text : ' 修改',
				pressed : true,
				handler : this.modify,
				scope : this,
				iconCls : 'edit'
			}],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);//行(row)被右击时触发
		this.grid.on("rowdblclick",this.modify,this);//双击行监听		
		this.add(this.grid);//大面板中加了两个小面板，一个放查询条件，一个放查询出的数据列表
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
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
	}

});
