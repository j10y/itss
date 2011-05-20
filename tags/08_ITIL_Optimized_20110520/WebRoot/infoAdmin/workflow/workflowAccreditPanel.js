 
// 授权
AccreditPanel = Ext.extend(Ext.Panel, {
	id : Ext.id(),
	closable : true,
	userId : '',
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',

	reset : function() {
		this.fp.form.reset();
	},
	// 搜索
	search : function() {
		var param = this.fp.form.getValues(false);		
		param.actorId = this.userId;
		param.methodCall = 'query';
		this.formValue = param;
		this.pageBar.formValue = this.formValue;
		param.start = 1;
		this.store.removeAll();
		// alert(Ext.encode(param));
		this.store.load({
			params : param
		});
		this.render();
	},

	getTopbar : function() {
		return ['   '
		,{
			text : '新增授权',
			pressed : true,
			handler : this.toAdd,
			scope : this,
			iconCls : 'add'
		},'     ',{
			text : '修改授权',
			pressed : true,
			handler : this.modify,
			scope : this,
			iconCls : 'edit'
		},'     ',{
			text : '删除授权',
			pressed : true,
			handler : this.remove,
			scope : this,
			iconCls : 'delete'
		}, '    &nbsp;|&nbsp;    ', {
			text : '查询',
			pressed : true,
			handler : this.search,
			scope : this,
			iconCls : 'search'
		}, '    ', {
			text : ' 重置',
			pressed : true,
			handler : this.reset,
			scope : this,
			iconCls : 'reset'
		}];
	},
	remove : function() {
		var records = this.grid.getSelectionModel().getSelections();
		if (!records||records.length==0) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		Ext.MessageBox.confirm("提示信息:","确实要删除授权信息吗？",function(btn){
			if (btn == 'yes'){ 
				var ids = "";
				for(i=0;i<records.length;i++){
					ids += records[i].get("id")+",";
				}
				Ext.Ajax.request({
					url : webContext + '/workflow/preassign.do?methodCall=remove',
					params : {
					    ids : ids
					},
					success : function(response, options) {						
						this.grid.getStore().reload();
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("提示信息：","删除失败");
					},
					scope:this
				});	
			} 				
		},this);			
	},
	toAdd : function() {
		var da = new DataAction();
		var workFlowAction=new WorkFlowAction();
		var proxyMap = null;
		
		var result = workFlowAction.getElementsForAdd();
		var defs = result.defs;
		
		var defMap = new Object();
		var defArray = new Array();
		
		//alert(Ext.encode(result));
		//构造级联下拉框
		var defData = [];
		for(defName in defs){
			var nodes = defs[defName];
			var node = nodes[0];
			var defDesc = node['definitionDesc'];
			defArray[defArray.length]=nodes;
			defData[defData.length]=[defName,defDesc];
		}
		if(defData.length==0){
			Ext.MessageBox.alert("提示信息：","您目前没有尚未受权的任务。");
			return;
		}
		
		var defStore = new Ext.data.SimpleStore({
			fields: ['id', 'name'],
			data:defData
 		})
 		
		var nodeStore = new Ext.data.SimpleStore({
			fields: ['id', 'name'],
			data:[]
 		})
		
 		var nodeComp = new Ext.form.ComboBox({
			name:'taskName',
			hiddenName: 'taskName',
			typeAhead: true,
			forceSelection: true,
			emptyText:'请选择...',
			valueField :'id',
			displayField: 'name',
			triggerAction:'all',
			store:nodeStore,
			mode:'local',
			fieldLabel:'任务名称',
			id:'workRoleComponentId'
		});
		
		var defComp = new Ext.form.ComboBox({
			xtype:'combo',
			style:'',
			//name:'definitionName',
			hiddenName: 'definitionName',
			typeAhead: true,
			forceSelection: true,
			emptyText:'请选择...',
			valueField :'id',
			displayField: 'name',
			triggerAction:'all',
			store: defStore,
			mode: 'local',
			fieldLabel:'流程定义',
			id:'definitionComponentId'
		});
		defComp.on("select",function(combo,record,index){
			var nodes = defArray[index];
			//alert(Ext.encode(nodes));
			var nodeData = [];
			for(i=0;i<nodes.length;i++){
				var node = nodes[i];
				nodeData[nodeData.length]=[node.nodeName,node.nodeName];
			}
			//流程描述设值
			//alert(Ext.getCmp("definitionDescId").getValue);
			Ext.getCmp("definitionDescId").setValue(record.get("name"));
			//alert(Ext.getCmp("definitionDescId").getValue);
			
			nodeComp.clearValue(); 
			
			nodeComp.store.proxy = new Ext.data.MemoryProxy(nodeData);
			var reader = new Ext.data.ArrayReader(
				[
					{name: 'id', mapping: 0},         
					{name: 'name', mapping: 1}    
				]
			);
			nodeComp.store.load(null,reader); 
		});
		
		//联动构造完毕
		//替换相应字段		
		var data = eval(result.json);
		//alert(result.json);
		for(i=0;i<data.length;i++){//替换
			if(data[i].name=='definitionDesc'){//流程描述
				data[i] = defComp;
			}
			if(data[i].name=='definitionName'){//流程描述
				//alert("definitionName");
				data[i] = new Ext.form.Hidden({
					xtype:'hidden',
					name:'definitionDesc',
					id:'definitionDescId',
					value:"definitionDesc",
					style:''
				});
			}
			else if(data[i].name=='taskName'){//节点名称
				//alert(data[i].name);
				data[i] = nodeComp;
			}
		}
		//以下如常
		for(i=0;i<data.length;i++){	
			var itemName = data[i].name;			
			if(itemName=="proxyName"){
				data.remove(data[i]);
			}
		}
		for(i=0;i<data.length;i++){	
			var itemName = data[i].name;			
			if(itemName=="actorName"){
				data.remove(data[i]);
			}
		}
		
		var biddata = da.split(data);
		//加入代理人的对选框
		var da = new DataAction();
	    var url = webContext+'/workflow/preassign.do?methodCall=workmates';
	    var fromdata = da.ajaxGetData(url);

	    var todata = [];//da.ajaxGetData(url);

		var item = {
			colspan : 2,
			xtype:"itemselector",
			name:"proxySelect",
			//fieldLabel:"双栏选框",
			dataFields:["code", "desc"],
			fromData: fromdata,
			toData:todata,
			msWidth:180,
			style:"margin: 0,23,10,13",
			autoScroll:true,
			msHeight:120,
			//width:450,
			frame:true,
			valueField:"code",
			displayField:"desc",
			//imagePath:"ext-ux/multiselect",
			//switchToFrom:true,
			fieldLabel:"代理执行人",
			toLegend:"已选人",
			fromLegend:"可选人"
		};
		title = {html:"选择代理审批人：",colspan : 2,style:'font-size:9pt;text-align:center'};
		if(biddata.length%2>0){
			var last = biddata[biddata.length-1];
			biddata[biddata.length-1] = title;
			biddata[biddata.length] = item;
			biddata[biddata.length] = last;
			
		}
		else{
			biddata[biddata.length] = title;
			biddata[biddata.length] = item;
		}
		
		this.panel = new Ext.form.FormPanel({
			layout : 'table',
		    height : 'auto',
		    width : 450,
			frame : true,
			autoScroll:true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
		   // title : "FormPanel",
			items : biddata
		})

		var modifyWin = new Ext.Window({
			title : '流程授权管理',
			modal : true,
			height : 420,
			width : 450,
			resizable : true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'fit',
			buttons : [{
				xtype : 'button',
				handler : function() {

					// 表单数据
					var bsParam = this.panel.form.getValues(false);
					var formParam = Ext.encode(bsParam);
					formParam = unicode(formParam);
					if (this.panel.getForm().isValid()) {
						// ***************************************
						Ext.Ajax.request({
							url : webContext + '/workflow/preassign.do?methodCall=saveProxy',
							params : {
							    preAssignId : id,
								info : formParam
							},

							success : function(response, options) {
								Ext.MessageBox.alert("提示信息：",
										"修改成功，所做修改仅对于新启动的流程有效。", function(btn) {
											if (btn == 'ok') {
												modifyWin.hide();
												location.reload();
											}

										});
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("保存失败");
							}
						});
					} else {
						Ext.MessageBox.alert("填写不规范");
					}

				},
				text : '保存',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					modifyWin.close()
				},
				text : '关闭',
				scope : this
			}],
			items : this.panel
		});
		modifyWin.show();
	},
	
	modify : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("提示", "请先选择要修改的行!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("提示", "修改时只能选择一行!");
			return;
		}
		var id = record.get("id");
		var da = new DataAction();
		var workFlowAction=new WorkFlowAction();
		var proxyMap = null;
		this.clazz = "com.zsgj.info.framework.workflow.entity.TaskPreAssign";
		var data = null;
		//FIXME 修改为按本部门成员显示审批人
		if (id != "") {
			data = workFlowAction.getElementsForEdit(this.clazz, id);
		} else {
			data = workFlowAction.getElementsForAdd(this.clazz);
		}
		//过滤字段，流程名称和任务名称设置只读属性
		for(i=0;i<data.length;i++){	
			var itemName = data[i].name;			
			if(itemName!=null&&(itemName=="definitionDesc"||itemName=="taskName")){
				data[i].readOnly = true;
			}
		}
		//???
		for(i=0;i<data.length;i++){	
			var itemName = data[i].name;			
			if(itemName=="proxyName"){
				data.remove(data[i]);
			}
		}
		for(i=0;i<data.length;i++){	
			var itemName = data[i].name;			
			if(itemName=="actorName"){
				data.remove(data[i]);
			}
		}
		
		var biddata = da.split(data);
		//加入代理人的对选框
		var da = new DataAction();
	    var url = webContext+'/workflow/preassign.do?methodCall=workmates';
	    var fromdata = da.ajaxGetData(url);
	    url = webContext+'/workflow/preassign.do?methodCall=proxies&id='+id;
	    var todata = da.ajaxGetData(url);
	    for(i=0;i<fromdata.length;i++){
	    	for(j=0;j<todata.length;j++){
		    	if(fromdata[i][0]==todata[j][0]){
		    		//alert("aa"+fromdata[i][0]);
		    		fromdata.remove(fromdata[i]);
		    	}
	    	}
	    }
		var item = {
			colspan : 2,
			xtype:"itemselector",
			name:"proxySelect",
			//fieldLabel:"双栏选框",
			dataFields:["code", "desc"],
			fromData: fromdata,
			toData:todata,
			msWidth:180,
			style:"margin: 0,23,10,13",
			autoScroll:true,
			msHeight:150,
			//width:450,
			frame:true,
			valueField:"code",
			displayField:"desc",
			//imagePath:"ext-ux/multiselect",
			//switchToFrom:true,
			fieldLabel:"代理执行人",
			toLegend:"已选人",
			fromLegend:"可选人"
		};
		title = {html:"选择代理审批人：",colspan : 2,style:'font-size:9pt;text-align:center'};
		if(biddata.length%2>0){
			var last = biddata[biddata.length-1];
			biddata[biddata.length-1] = title;
			biddata[biddata.length] = item;
			biddata[biddata.length] = last;
			
		}
		else{
			biddata[biddata.length] = title;
			biddata[biddata.length] = item;
		}
		
		this.panel = new Ext.form.FormPanel({
			layout : 'table',
		    height : 420,
		    width : 450,
			frame : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
		   // title : "FormPanel",
			items : biddata
		})

		var modifyWin = new Ext.Window({
			title : '流程授权管理',
			modal : true,
			height : 420,
			width : 450,
			resizable : true,
			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'fit',
			buttons : [{
				xtype : 'button',
				handler : function() {

					// 表单数据
					var bsParam = this.panel.form.getValues(false);
					var formParam = Ext.encode(bsParam);
					formParam = unicode(formParam);
					if (this.panel.getForm().isValid()) {
						// ***************************************
						Ext.Ajax.request({
							url : webContext + '/workflow/preassign.do?methodCall=saveProxy',
							params : {
							    preAssignId : id,
								info : formParam
							},

							success : function(response, options) {
								Ext.MessageBox.alert("提示信息：",
										"修改成功，所做修改仅对于新启动的流程有效。", function(btn) {
											if (btn == 'ok') {
												modifyWin.hide();
												location.reload();
											}

										});
							},
							failure : function(response, options) {
								Ext.MessageBox.alert("保存失败");
							}
						});
					} else {
						Ext.MessageBox.alert("填写不规范");
					}

				},
				text : '保存',
				scope : this
			}, {
				xtype : 'button',
				handler : function() {
					modifyWin.hide()
				},
				text : '关闭',
				scope : this
			}],
			items : this.panel
		});
		modifyWin.show();
	},
//	afterEdit : function(obj) {
//		var record = obj.record;
//		var comboActor = record.get('actor');
//		var comboProxy = record.get('proxy');
//
//		// alert(comboActor);
//		// alert(comboProxy);
//
//	},
	// 初始化
	initComponent : function() {
		this.fp = new AccreditSearchForm();

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();

		var da = new DataAction();
		var clazz = "com.zsgj.info.framework.workflow.entity.TaskPreAssign";
		var obj = da.getElementsForHead(clazz)

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
			var editor = '';
			var renderer = '';
			if (headItem.renderer) {
				renderer = headItem.renderer;
			}
			if (propertyName == 'proxyId') {
				// editor = new Ext.form.TextField();
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				//editor : headItem.editor,
				renderer : renderer,
				align : alignStyle
			};
			columns[i + 1] = columnItem;
			// fields[i] = propertyName;
		}

		this.store = da.getJsonStore(clazz);
		// this.storeMapping = fields;

		this.cm = new Ext.grid.ColumnModel(columns);

		this.cm.defaultSortable = true;
		AccreditPanel.superclass.initComponent.call(this);
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbarExt({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
			emptyMsg : "无显示数据"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.topbar = this.getTopbar();
		this.grid = new Ext.grid.EditorGridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			clicksToEdit : 2,
			loadMask : true,
			y : 40,
			anchor : '0 -35',
			tbar : this.topbar,
			bbar : this.pageBar
		});

		this.grid.on("headerdblclick", this.fitWidth, this);
//		this.grid.on('afteredit', this.afterEdit, this);
		this.grid.on("rowdblclick", this.modify, this);
		// alert("asdasd");
		this.add(this.fp);
		this.add(this.grid);
		this.initData();
		// this.store.load({params:{actorId:this.userId}});
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
	initData : function() {
		var param = {
			'methodCall' : 'list',
			'start' : 1,
			'actorId': this.userId
		};
		//alert(this.userId);
		this.pageBar.formValue = param;
		// alert(Ext.encode(param));
		this.store.load({
			params : param
		});
	}
});
