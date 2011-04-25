
//配置状态Panel列表
ServiceListPanel = Ext.extend(Ext.Panel, {
	id : "ServiceListPanel",
	 //title:"配置项状态维护",
	closable : true,
	viewConfig : {//自适应填充
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	reset:function(){
		this.fp.form.reset();
	},
	search : function() {
		var param = this.fp.form.getValues(false);
		param.methodCall = 'query';
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	goBackUrl:function(){
		
		location="servicePortfolio.jsp";
	},
	create : function() {
		var clazz = "com.digitalchina.itil.service.entity.ServicePortfolio";
		var da = new DataAction();
		this.items = da.split(da.getElementsForAdd(clazz));
		var envForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 390,
			height : 350,
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
				title : '新增配置项分类',
				width : 400,
				height : 400,
				modal : true,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//点击按钮时，首先从面板中得到所有数据，在提交给相应Action中的方法
					text : '保存',
					//id:'postButton',
					handler : function() {
						var bsParam = envForm.form.getValues(false);//该值是一个key和value的对象
						da.saveData(clazz,bsParam,function(){
						
							store.reload();
							win.close()
						});
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
	
	//删除
	remove : function() {
		var clazz = "com.digitalchina.itil.service.entity.ServicePortfolio";
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
//		for(var i=0;i<records.length;i++){			
//			id[i] = records[i].data.id;
//		//da.removeData(clazz,{'id':id[i]});
//		
//		}
		//this.store.reload();

		// var param="";
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
	                       				//this.store.reload();
	                       			//location = 'ibmRelPerson.jsp'
	                       			//firm.close();
	                       		});
	                       }
	                       else{
                                Ext.Msg.alert("提示信息","成功删除数据!",function(){
                                	
                                   // location = 'ibmRelPerson.jsp'
                                    //this.store.reload();
                                },this);
	                       }
	                       this.store.reload();
                          // location = 'ibmCust.jsp'  
	                   },scope:this
						
						
						
						
						
					});
				}
			}
		}, this);

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
		
		var clazz = "com.digitalchina.itil.service.entity.ServicePortfolio";
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz,id);
		var custdata = da.split(data);
		
		var envForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 390,
			height : 350,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store=this.store;
		var win1 = new Ext.Window({
				title : '修改配置项环境信息',
				width : 400,
				height : 400,
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
							
//						
					},
					scope : this

				}, {
					text : '关闭',
					handler : function() {
						//location = 'ibmCust.jsp'
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
	getSearchForm : function() {
		return new ServiceSearchForm({//他是一个panel
			height : 250,
			layoutConfig : {
				columns : 4
			},
			getMyItems : function() {//方法重写
				var clazz = "com.digitalchina.itil.service.entity.ServicePortfolio";
				var da = new DataAction();
				var data = da.getElementsForQuery(clazz);
				return da.split(data);
			}
		});
	},
	// 初始化
	initComponent : function() {//在这里面定义的变量是全局变量
		//this.removIds='';
		this.fp=this.getSearchForm();
		var da = new DataAction();
		var clazz = "com.digitalchina.itil.service.entity.ServicePortfolio";
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
		ServiceListPanel.superclass.initComponent.call(this);
		var viewConfig = Ext.apply({//什么意思？
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize :10, //sys_pageSize,//使用的是系统默认值
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
			y : 40,
			anchor : '0 -35',
			tbar : ['   ', {
				text : '新增',
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
		this.add(this.fp);
		this.add(this.grid);//大面板中加了两个小面板，一个放查询条件，一个放查询出的数据列表
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		this.pageBar.formValue =param; 
		this.store.baseParams = param;
		this.store.load();
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
//	initData : function() {
//		var param = {
//			'methodCall' : 'query',
//			'start' : 1
//		};
//		this.formValue = param;
//		this.pageBar.formValue = this.formValue;
//		
//		this.store.load({
//			params : param
//		});
//	}
});
