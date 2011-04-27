projectPlanListPanel = Ext.extend(Ext.Panel, {
	id : "projectPlanListPanel",
	title : '<font color=red>项目计划</font>',
	layout : 'table',
	height : 500,
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	create : function(){
		var record = this.treePanel.getSelectionModel().getSelected();
		var id = record.get("itil_sci_ProjectPlan$id");
		alert(id);
	},
	modifyTreeGrid : function(){
//				alert("modifyTreeGrid");
				var reqId = this.dataId;
				var reqClazz = this.reqClass;
				var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree111").getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("提示","请先选择要修改的行!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("提示","修改时只能选择一行!");
					return;
				}
				var modifyPanel = this.getFormProjectPlanInfoPanel(record.get("itil_sci_ProjectPlan$id"));
				var win1 = new Ext.Window({
					title : '项目计划信息',
					height:400,
					width:500,
					constrain:false,
					constrainHeader:true,
					modal:true,
					resizable:false,
					draggable:true,
					layout:'fit',
					scope :this,
					items : [modifyPanel],
					buttons : [{
						text:'保存',
						scope:this,
						handler:function(){
							var formParam = Ext.getCmp('ProjectPlanInfoPanel').form.getValues(false);
							if(formParam.itil_sci_ProjectPlan$planName==""||formParam.itil_sci_ProjectPlan$parentPlan==""||formParam.itil_sci_ProjectPlan$progress==""){
								Ext.MessageBox.alert("提示","缺少必须数据，请填写完整再保存");
								return;
							}
							var vp = null;
							if(formParam!=null){
								vp = Ext.apply(formParam,vp,{});
							}
							Ext.Ajax.request({
								url : webContext + '/specialRequireAction_saveProjectPlanForReq.action?'
								+'reqClass='+reqClazz
								+'&reqId='+reqId,
								params : vp,
					
								success : function(response, options) {
									Ext.MessageBox.alert("保存成功");
									win1.close();
								},
								scope:this,
								failure : function(response, options) {
									Ext.MessageBox.alert("保存失败");
								}
							}, this);
							}
						},{
						text : '关闭',
						scope:this,
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
	getFormProjectPlanInfoPanel: function(curppId) {
		var da = new DataAction();
		var data = null;
		var curId = this.dataId;
		//alert(curId);
		var curClass = this.reqClass;
		//alert(curClass);
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		if (curppId != '0') {
			data = da.getSingleFormPanelElementsForEdit("ProjectPlanInfoPanel", curppId);// 这是要随时变得
			var stored = this.store;
			for(var i=0;i<data.length;i++){
				if(data[i].id=='itil_sci_ProjectPlan$parentPlanCombo'){
					data[i].on("beforequery",function(queryEvent){
						var param = queryEvent.combo.getRawValue();
						var val = queryEvent.combo.getValue();
						if(queryEvent.query==''){
						param='';
						}
						this.store.removeAll();
						this.store.load({
						params:{
						planName:param,
						requirementId : curId,
						requirementClass : curClass,
						start:0}
						});
						return false;
						});
				}
			}	
		} else {
			data = da.getSingleFormPanelElementsForEdit("ProjectPlanInfoPanel","");
			for(var i=0;i<data.length;i++){
				//alert(data[i].id);
				if(data[i].id=='itil_sci_ProjectPlan$parentPlanCombo'){
					//data[i].fieldLabel="父项目计划<font color='red'>(必选项)";
					var nId = this.nId;
					data[i]=new Ext.form.ComboBox({ 
					xtype:'combo',
					hiddenName: 'itil_sci_ProjectPlan$parentPlan',
					id :'itil_sci_ProjectPlan$parentPlanCombo',
					width:'auto',
					style:'',
					fieldLabel:'父项目计划<font color=red>(必选项)',
					lazyRender: true,
					displayField: 'planName',
					valueField :'id',
					emptyText:'请选择...',
					allowBlank:false,
					editable : false,
					typeAhead:false,//自动匹配剩余文本
					name:'itil_sci_ProjectPlan$parentPlan',
					triggerAction:'all',
					minChars :50,
					queryDelay : 700,
					store:new Ext.data.JsonStore({ 
					url:webContext+'/extjs/comboDataAction?clazz=com.zsgj.itil.project.entity.ProjectPlan',
					fields:['id','planName'],
					totalProperty:'rowCount',
					root:'data',
					id:'id'
					}),
					pageSize:10,
					listeners:{'beforequery' : function(queryEvent){
					var param = queryEvent.combo.getRawValue();
					if(queryEvent.query==''){
					param='';
					}this.store.load({
					params:{planName:param,requirementId : curId,
						requirementClass : curClass,start:0}});
					return false;
					}},
					initComponent : function() {
						this.store.load({
						params:{requirementId : curId,
						requirementClass : curClass,start:0},
							callback:function(r, options, success){
//								alert(nId);
								Ext.getCmp('itil_sci_ProjectPlan$parentPlanCombo').setValue(nId);
							}
						});
					}
					})
//					data[i].listeners = {
//						'beforequery' : function(queryEvent){
//						var param = queryEvent.combo.getRawValue();
//						var val = queryEvent.combo.getValue();
//						if(queryEvent.query==''){
//						param='';
//						}
//						this.store.removeAll();
//						this.store.load({
//						params:{
//						planName:param,
//						requirementId : curId,
//						requirementClass : curClass,
//						start:0}
//						});
//						return false;
//						}
//					}
				}
			}
		}
		biddata = da.split(data);
		this.formProjectPlanInfoPanel= new Ext.form.FormPanel({
			id : 'ProjectPlanInfoPanel',
			layout : 'table',
			height : 'auto',
			width : 300,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 2
			},
			//title : "项目计划表单面板",
			items : biddata
		});
		return this.formProjectPlanInfoPanel;
	},
	
	getTreePanel : function(rootPlanId) {
		var da = new DataAction();
		var sra = new SpecialRequireAction();
		var data = da.getGridTreeHead("flm_projectPlanList");
		var record = Ext.data.Record.create(data);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0]=sm;
     	var obj = da.getListPanelElementsForHead("flm_projectPlanList");
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var isHiddenColumn = false;
			var isHidden = headItem.isHidden;
			if (isHidden == 'true') {
				isHiddenColumn = true;
			}
			if(propertyName=='itil_sci_ProjectPlan$planName'){
				var columnItem = {
				id : 'itil_sci_ProjectPlan$id',
				header : title,//"项目计划(<font color=red>单击点开,双击操作</font>)",
				dataIndex : propertyName,//'flm_ProjectPlan$planName',
				sortable : true,
				align : 'left'
				}
			}else{
				var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
				}
			}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = sra.getTreeGridJsonStore('itil_sci_ProjectPlan$id',record,rootPlanId);
		var createButton=new Ext.Button({
			text:'新建',
			pressed:true,
			iconCls: 'add',
			scope : this,
			handler:function(){
				var reqId = this.dataId;
				var reqClazz = this.reqClass;
				var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree111").getSelectionModel().getSelections();
				if(records.length>1){
					Ext.Msg.alert("提示","只能选择一行!");
					return;
				}
				var newId ="";
				if (!record) {
					newId ="";
				}else{
					newId = record.get("itil_sci_ProjectPlan$id");
				}
//				alert(newId);
				this.nId = newId;
				var createPanel = this.getFormProjectPlanInfoPanel('0');
				var win1 = new Ext.Window({
					title : '新建项目计划',
					height:400,
					width:500,
					constrain:false,
					constrainHeader:true,
					modal:true,
					resizable:false,
					draggable:true,
					layout:'fit',
					scope :this,
					items : [createPanel],
					buttons : [{
						text:'保存',
						handler:function(){
							var formParam = Ext.getCmp('ProjectPlanInfoPanel').form.getValues(false);
							//alert(Ext.encode(formParam));
							if(formParam.itil_sci_ProjectPlan$planName==""||formParam.itil_sci_ProjectPlan$parentPlan==""||formParam.itil_sci_ProjectPlan$progress==""){
								Ext.MessageBox.alert("提示","缺少必须数据，请填写完整再保存");
								return;
							}
							var vp = null;
							if(formParam!=null){
								vp = Ext.apply(formParam,vp,{});
							}
							Ext.Ajax.request({
								url : webContext + '/specialRequireAction_saveProjectPlanForReq.action?'
								+'reqClass='+reqClazz
								+'&reqId='+reqId,
								params : vp,
					
								success : function(response, options) {
									Ext.MessageBox.alert("保存成功");
									win1.close();
									
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("保存失败");
								}
							}, this);
							}
						},{
						text : '关闭',
						handler : function() {
							//stored.reload();
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
		    	}
			});
		var removeButton=new Ext.Button({
			text:'删除',
			pressed:true,
			iconCls: 'remove',
			scope : this,
			handler:function(){
				var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree111").getSelectionModel().getSelections();
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
							id[i] = records[i].data.itil_sci_ProjectPlan$id;
							Ext.Ajax.request({
								url : webContext+ '/specialRequireAction_removeProjectPlan.action',
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
		    	}
			});
		var modifyButton=new Ext.Button({
			text:'修改',
			pressed:true,
			iconCls: 'edit',
			scope : this,
			handler:function(){
				var reqId = this.dataId;
				var reqClazz = this.reqClass;
				var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree111").getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("提示","请先选择要修改的行!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("提示","修改时只能选择一行!");
					return;
				}
				var modifyPanel = this.getFormProjectPlanInfoPanel(record.get("itil_sci_ProjectPlan$id"));
				var win1 = new Ext.Window({
					title : '项目计划信息',
					height:400,
					width:500,
					constrain:false,
					constrainHeader:true,
					modal:true,
					resizable:false,
					draggable:true,
					layout:'fit',
					scope :this,
					items : [modifyPanel],
					buttons : [{
						text:'保存',
						scope:this,
						handler:function(){
							var formParam = Ext.getCmp('ProjectPlanInfoPanel').form.getValues(false);
							if(formParam.itil_sci_ProjectPlan$planName==""||formParam.itil_sci_ProjectPlan$parentPlan==""||formParam.itil_sci_ProjectPlan$progress==""){
								Ext.MessageBox.alert("提示","缺少必须数据，请填写完整再保存");
								return;
							}
							var vp = null;
							if(formParam!=null){
								vp = Ext.apply(formParam,vp,{});
							}
							Ext.Ajax.request({
								url : webContext + '/specialRequireAction_saveProjectPlanForReq.action?'
								+'reqClass='+reqClazz
								+'&reqId='+reqId,
								params : vp,
					
								success : function(response, options) {
									Ext.MessageBox.alert("保存成功");
									win1.close();
								},
								scope:this,
								failure : function(response, options) {
									Ext.MessageBox.alert("保存失败");
								}
							}, this);
							}
						},{
						text : '关闭',
						scope:this,
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
		    	}
			});
		var refreshButton=new Ext.Button({
			text:'刷新',
			pressed:true,
			iconCls: 'refresh',
			scope : this,
			handler:function(){
				window.location = webContext + '/user/require/specialRequireForERP/makeProjectPlan.jsp?dataId='+this.dataId+'&reqClass='+this.reqClass+'&tabNumber='+'projectPlanListPanel';
				}
			});
		this.treePanel = new Ext.ux.maximgb.treegrid.GridPanel({
			store : this.store,
			id : "tree111",
			width : 900,
			height : 500,
			frame : true,
			master_column_id : 'itil_sci_ProjectPlan$id',
			cm : this.cm,
			sm : sm,               
			stripeRows : true,
			autoExpandColumn : 'itil_sci_ProjectPlan$id',
			title : "项目计划列表",
			root_title : '项目计划',
			tbar : [createButton,'|',modifyButton,'|',removeButton,'|',refreshButton]
		});
         //this.store.on('load', function(){Ext.getCmp('tree111').expandAllNodes();}); 
         this.treePanel.on("dblclick", this.modifyTreeGrid, this);
         //this.treePanel.on("contextmenu", function(){}, this);
         //this.treePanel.on("rowcontextmenu",function(grid,rowIndex,e){
			//Ext.getCmp("tree111").getSelectionModel().selectRow(rowIndex);
			//var menu;
			//var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
			//var id = record.get("flm_ProjectPlan$id");
			//if(!menu){
			//		menu = new Ext.menu.Menu([
			//		{
			//			text : '新建子计划',
			//			handler : function(){
			//			}
			//		},{
			//			text : '编辑',
			//			handler : function(){
			//			}
			//		},{
			//			text : '删除',
			//			handler : function(){
			//				
			//				}
			//			}]);
			//			menu.showAt(e.getPoint());
			//		}
			//	});
   			
		return this.treePanel;
	},

	items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var items = new Array();
		var rootPlanId = this.rootId;
		this.getTreePanel(rootPlanId);
		items.push(this.treePanel);
		this.items = items;
		projectPlanListPanel.superclass.initComponent.call(this);
	}
})