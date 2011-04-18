PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	reset:function(){ //重置方法
		this.fp.form.reset();
	},
	search : function() {//查询方法
		var tempApp = Ext.getCmp('itCode').getValue();
		var param = {
					itCode : tempApp
		};
		/*给分页发的时候这样的参数格式*/
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});

		this.store.removeAll();
		this.store.load({
			params : param
		})
	},
	create : function() {
		var clazz = "com.digitalchina.itil.account.entity.SystemAppAdmin";//指定实体类型
		var da = new DataAction();//具体是用来做什么的？
		this.items = da.split(da.getElementsForAdd(clazz));//从实体类中读取对应的items信息，用于界面显示
		var envForm = new Ext.form.FormPanel({//增加审批人 界面的Form
			layout : 'table',
			width : 390,
			height : 150,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px '
			},
			frame : true,
			items : this.items

		});
		var store = this.store;//获取store值
	     var win = new Ext.Window({//增加审批人的 窗口
				title : '系统及应用负责人管理',
				width : 400,
				height : 200,
				modal : true,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//点击按钮时，首先从面板中得到所有数据，在提交给相应Action中的方法
					text : '保存',
					//id:'postButton',
					handler : function() {//为保存按钮增加监听器
						var bsParam = envForm.form.getValues(false);//该值是一个key和value的对象
						da.saveData(clazz,bsParam,function(){//不用指定url？通过clazz就可以？
						store.reload();
							win.close()
						});
					},
					scope : this //指定目标作用域

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
		var clazz = "com.digitalchina.itil.account.entity.SystemAppAdmin";//指定实体类型
		var record = this.grid.getSelectionModel().getSelected();//获取被选中的数据
		var records = this.grid.getSelectionModel().getSelections();//获取被选中数据集合
		if (!record) {//判断是否未选择
			Ext.Msg.alert("提示", "请先选择要删除的行!");
			return;
		}
		if (records.length == 0) {//判断是否未选择
			Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
			return;
		}
		var id = new Array();
		var da = new DataAction();//没有使用
//		for(var i=0;i<records.length;i++){			
//			id[i] = records[i].data.id;
//		//da.removeData(clazz,{'id':id[i]});
//		
//		}
		//this.store.reload();

		// var param="";
		var firm  =Ext.Msg.confirm('提示框', '您确定要进行删除操作吗?', function(button) {//删除提示
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){//遍历选择项集合
				id[i] = records[i].get('id');
				Ext.Ajax.request({//用dataAction的方法完成删除
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz='+clazz,
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);//获取返回文本信息
	                       if(!r.success){//删除失败
	                       		Ext.Msg.alert("提示信息","数据删除失败",function(){
	                       				//this.store.reload();
	                       			//location = 'ibmRelPerson.jsp'
	                       			//firm.close();
	                       		});
	                       }
	                       else{//删除成功
                                Ext.Msg.alert("提示信息","成功删除数据!",function(){
                                	
                                   // location = 'ibmRelPerson.jsp'
                                    //this.store.reload()
                                },this);
	                       }
	                       this.store.reload();//重新读取数据
                          // location = 'ibmCust.jsp'  
	                   },scope:this
						
						
						
						
						
					});
				}
			}
		}, this);

	},
	
	//修改
	modify:function(){
		var record = this.grid.getSelectionModel().getSelected();    //获取选中的选项
	    var records = this.grid.getSelectionModel().getSelections(); //获取被选中的集合
		if(!record){//判断是否未选任何选项
				Ext.Msg.alert("提示","请先选择要修改的行!");
				return;
			}
		if(records.length>1){//判断是否选项数量大于1
				Ext.Msg.alert("提示","修改时只能选择一行!");
				return;
			}
		var id = record.get("id");//********************************得到修改行的id
	
		
		var clazz = "com.digitalchina.itil.account.entity.SystemAppAdmin";//指定实体类型
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz,id);//这个方法具体怎么写的
		var custdata = da.split(data);//得到数据类型定义？包含数据么？
		
		var envForm = new Ext.form.FormPanel({//指定修改界面FORM
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
		var win1 = new Ext.Window({//修改窗口定制
				title : '系统及应用负责人',
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
						da.saveData(clazz,bsParam,function(){//调用DataAction实例来保存
						
							store.reload();//重新加载修改后的数据
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
	getSearchForm : function() {//初始化查询界面
		this.panel = new Ext.form.FormPanel({
			id : 'searchPanel',
			region : "north",
			layout : 'column',
			height : 'auto',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
		   title : "查询列表",
		   items :[{
					columnWidth : .5,
					layout : 'form',
					border : false,
					items : [
				new Ext.form.TextField({
				fieldLabel : '负责人ITCode',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'itCode',
				name : 'itCode',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			    })
					]
				}]
		});
		return this.panel;//返回查询面板
	},
	items : this.items,
	initComponent : function() {//初始化面板
		 this.fp = this.getSearchForm();//获取查询面板
		var sm = new Ext.grid.CheckboxSelectionModel();
		 //this.cm = new Ext.grid.ColumnModel(columns);
		this.store = new Ext.data.JsonStore({//设置下方列表的格式
			url : webContext + "/accountAction_listSystemAppAdmin.action",
			fields: ['id','itCode','realName'],
			root:'data',
			totalProperty : 'rowCount',
			sortInfo: {field: "id", direction: "ASC"}
		});
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		// this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({//视图设置
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({//分页功能条
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({//设定grid
			region : "center",
			store : this.store,
			columns : [sm, {header : '编号',dataIndex : 'id',align : 'left',sortable : true,hidden : true}, 
						{header : '负责人itCode',dataIndex : 'itCode',align : 'left',sortable : true,hidden : false}, 
						{header : '负责人真实姓名',dataIndex : 'realName',align : 'left',sortable : true,hidden : false}], 
			sm : sm,//在每行的前面加入一个checkbox
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			bbar : this.pageBar,//添加分页栏
			tbar : ['   ', { //设置上方工具栏
				text : '新增',
				pressed : true,
				handler : this.create, //指定回调函数
				scope : this,
				iconCls : 'add'
			}, '   ', {
				text : '删除',
				pressed : true,
				handler : this.remove,//指定回调函数
				scope : this,
				iconCls : 'remove'
			}, '    &nbsp;|&nbsp;    ', {
				text : '查询',
				pressed : true,
				handler : this.search,//指定回调函数
				scope : this,
				iconCls : 'search'
			}, '   ', {
				text : ' 重置',
				pressed : true,
				handler : this.reset,//指定回调函数
				scope : this,
				iconCls : 'reset'
			},'     ',{
				text : ' 修改',
				pressed : true,
				handler : this.modify,//指定回调函数
				scope : this,
				iconCls : 'edit'
			}]
			
		});

		this.grid.on("headerdblclick", this.fitWidth, this);//添加监听器
		this.grid.on("rowdblclick", this.modify, this);//添加监听器
		var items = new Array();
		items.push(this.fp);	
		items.push(this.grid);
		this.items = items;//把组建填入items中

		PagePanel.superclass.initComponent.call(this);//父节点初始化当前页面
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'status':1
		};

		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});


		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	fitWidth : function(grid, columnIndex, e) {//设置grid中的单元格大小
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});
