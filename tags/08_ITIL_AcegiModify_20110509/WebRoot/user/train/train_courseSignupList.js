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
		var data = da.getPanelElementsForQuery("panel_trainCourseSignup"); //da.getElementsForQuery("com.zsgj.itil.train.entity.TrainCourseSignup");
		var biddata = da.split(data);
		var trainCourse = new Ext.form.ComboBox({
					xtype : "combo",
					id : "tc",
					fieldLabel : "报名人员:",
					valueField : "trainCourse",
					displayField : "name",
					listWidth : 220,
					maxHeight : 220,
					emptyText: '请选择',
					mode : 'remote',
					forceSelection : true,
					hiddenName : "trainCourse",
					editable : true,
					triggerAction : 'all', 
					lazyRender: true,
		            typeAhead: false,
		            autoScroll:true,
					allowBlank : true,
					name : "name",
					selectOnFocus: true,
					width : 220,
					// *******************************************************************
					store : new Ext.data.JsonStore({// displayField
						id : Ext.id(),
						url : webContext+'/trainPlan_findTrainCourse.action?displayField=name',
						fields: ['trainCourse','name'],
						totalProperty:'rowCount',
						root:'data',
						//id:'signupUser',			
						sortInfo: {field: "name", direction: "ASC"},
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['name'] == undefined) {
									opt.params['name'] = Ext.getCmp("tc").defaultParam;
								}
							}
						}
					}),
					// *******************************************************************
					pageSize:10,
					listeners:{
						blur:function(combo){//当其失去焦点的时候
							if(combo.getRawValue()==''){
								combo.reset();
							}
						},
						beforequery : function(queryEvent){
							var param = unicode(queryEvent.query);
							this.defaultParam=param;
							this.store.load({
								params:{name:param,start:0}
							});
							return true;
						}
					}					
				});
				
		var signup = new Ext.form.ComboBox({
					xtype : "combo",
					id : "signup",
					fieldLabel : "报名人员:",
					valueField : "signupUser",
					displayField : "name",
					listWidth : 220,
					maxHeight : 220,
					emptyText: '请选择',
					mode : 'remote',
					forceSelection : true,
					hiddenName : "signupUser",
					editable : true,
					triggerAction : 'all', 
					lazyRender: true,
		            typeAhead: false,
		            autoScroll:true,
					allowBlank : true,
					name : "name",
					selectOnFocus: true,
					width : 220,
					// *******************************************************************
					store : new Ext.data.JsonStore({// displayField
						id : Ext.id(),
						url : webContext+'/trainPlan_findAllUser.action?displayField=name',
						fields: ['signupUser','name'],
						totalProperty:'rowCount',
						root:'data',
						//id:'signupUser',			
						sortInfo: {field: "name", direction: "ASC"},
						listeners : {
							beforeload : function(store, opt) {
								if (opt.params['name'] == undefined) {
									opt.params['name'] = Ext.getCmp("signup").defaultParam;
								}
							}
						}
					}),
					// *******************************************************************
					pageSize:10,
					listeners:{
						blur:function(combo){//当其失去焦点的时候
							if(combo.getRawValue()==''){
								combo.reset();
							}
						},
						beforequery : function(queryEvent){
							var param = unicode(queryEvent.query);
							this.defaultParam=param;
							this.store.load({
								params:{name:param,start:0}
							});
							return true;
						}
					}					
				});
				
				
				
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
			title : "查询列表",
			items : [{html: "&nbsp;&nbsp;培训课程:" ,cls: 'common-text', style:'width:80;height:20;text-align:left;margin:5 0 5 0;'},
					trainCourse,
					{html: "&nbsp;&nbsp;报名人员:" ,cls: 'common-text', style:'width:80;height:20;text-align:left;margin:5 0 5 0;'},
					signup]
		});
		return this.panel;
	},
	createSearch2 : function(btnName, link, imageUrl, scope) {
				var params = this.panel.form.getValues(false);
					params.methodCall = 'query';
					params.start = 0;
					params.deleteFlag = 0;

//				this.formValue = param;
//				this.pageBar.formValue = this.formValue;
					this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
				this.store.removeAll();
				this.store.load({
					params : params
				});

		return false;
	},
	removeTrain : function(){
		var store = this.store;
			    var record = this.grid.getSelectionModel().getSelected();
				var records = this.grid.getSelectionModel().getSelections();
				var clazz = "com.zsgj.itil.train.entity.TrainCourseSignup";		
					if(!record){
						Ext.Msg.alert("提示","请先选择要删除的行!");
						return;
					}
				var firm  =Ext.Msg.confirm('提示', '您确定要进行删除操作吗?', function(button) {
			if (button == 'yes') {	
				for(var i=0;i<records.length;i++){
				
					var scId = records[i].get("TrainCourseSignup$id");
							Ext.Ajax.request({
					
									url : webContext+'/trainPlan_remove.action',
									params : {
										dataId : scId,
										model: this.model,
										clazz : clazz
									},
									success : function(response, options) {
										Ext.MessageBox.alert("删除成功","",function(){
											store.reload();
											//window.location= webContext+"/user/train/train_instructorList.jsp";
										});
										
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("删除失败");
									}
							}, this);
				}
					}
		}, this);
		
	},
	items : this.items,
	initComponent : function(){
		var da = new DataAction();
        var buttonUtil=new ButtonUtil();
        this.mybuttons=buttonUtil.getButtonForModel("page_train_courseSignup_list",this);
		this.fp = this.getSearchForm();
		 this.modelTableName="TrainCourseSignup";
		var obj = da.getListPanelElementsForHead("panel_trainCourseSignup");
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
			if(propertyName=='TrainCourseSignup$deleteFlag'){
				isHiddenColumn = true;
			}
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
			if(propertyName!='TrainCourseSignup$deleteFlag'){
				columns[columns.length] = columnItem;
			}
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPanelJsonStore("panel_trainCourseSignup",obj); //add header 
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
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
//		this.formValue = '';
//		this.pageBar.formValue = this.formValue;
		
		this.grid = new Ext.grid.GridPanel({
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
		this.grid.on("rowdblclick", this.modify, this);
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		this.on("delete",this.removeTrain,this);
		this.on("createSearch2",this.createSearch2,this);
		PagePanel.superclass.initComponent.call(this);
		var params = {
			'mehtodCall' : 'query',
			'deleteFlag':0,
			'start' : 0
		};
		
//		this.pageBar.formValue = param;
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
					});
		
		this.store.removeAll();
		this.store.load({
			params : params
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





