projectPlanListPanel = Ext.extend(Ext.Panel, {
	id : "projectPlanListPanel",
	title : '项目计划',
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
		var id = record.get("SRflm_ProjectPlan$id");
		alert(id);
	},
	getFormProjectPlanInfoPanel: function(curppId) {
		var da = new DataAction();
		var data = null;
		var curId = this.dataId;
		//var curClass = this.reqClass;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		if (curppId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan", curppId);// 这是要随时变得
			for(i=0;i<data.length;i++){
			if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
				data[i].id=data[i].id+8;//改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			data[i].readOnly = true;
			}
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan","");
			for(var i=0;i<data.length;i++){
				//alert(data[i].id);
				if(data[i].id=='itil_lst_SRProjectPlan$parentPlanCombo'){
					//alert("aaa");
					data[i].fieldLabel="父项目计划(<font color='red'>必选项)";
					data[i].listeners = {
						'beforequery' : function(queryEvent){
						var param = queryEvent.combo.getRawValue();
						var val = queryEvent.combo.getValue();
						if(queryEvent.query==''){
						param='';
						}
						this.store.removeAll();
						this.store.load({
						params:{
						planName:param,
						specialRequire : curId,
						start:0}
						});
						return false;
						}
					}
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
	modifyTreeGrid : function(){
	
				var reqId = this.dataId;
				//var reqClazz = this.reqClass;
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
				var modifyPanel = this.getFormProjectPlanInfoPanel(record.get("SRProjectPlan$id"));
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
	getTreePanel : function(rootPlanId) {
		var da = new DataAction();
		var sra = new SRAction();
		var data = da.getGridTreeHead("SRflm_projectPlanList");
		var record = Ext.data.Record.create(data);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0]=sm;
     	var obj = da.getListPanelElementsForHead("SRflm_projectPlanList");
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
			if(propertyName=='SRProjectPlan$planName'){
				var columnItem = {
				id : 'SRProjectPlan$id',
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
		this.store = sra.getTreeGridJsonStore('SRProjectPlan$id',record,rootPlanId);
		var showButton=new Ext.Button({
			text:'查看',
			pressed:true,
			iconCls: 'search',
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
				var modifyPanel = this.getFormProjectPlanInfoPanel(record.get("SRProjectPlan$id"));
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
		this.treePanel = new Ext.ux.maximgb.treegrid.GridPanel({
			store : this.store,
			id : "tree111",
			width : 900,
			height : 500,
			frame : true,
			master_column_id : 'SRProjectPlan$id',
			cm : this.cm,
			sm : sm,               
			stripeRows : true,
			autoExpandColumn : 'SRProjectPlan$id',
			title : "项目计划列表",
			root_title : '项目计划',
			tbar : [showButton]
		});
		this.treePanel.on("dblclick", this.modifyTreeGrid, this);
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