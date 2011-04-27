projectRenderListPanel = Ext.extend(Ext.Panel, {
	id : "projectRenderListPanel",
	title : "<font color=red>项目报工</font>",
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
		//alert(id);
	},
	selectAll : function(){
		
		var dataId = this.dataId;
		var url = webContext + '/SRAction_findAllProjectPlan.action?dataId='+dataId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		var data = "";
		if (conn.status == "200") {
			var responseText = conn.responseText;
			if (responseText == '' || responseText == null) {
				data = "";

			} else {
				responseText = clearReturn(responseText);
				data = eval("(" + responseText + ")");
				if(data.name=='100%'){
					var records = Ext.getCmp('tree2').store.getRange(0,Ext.getCmp('tree2').store.getTotalCount());
					for(var i=0;i<records.length;i++){
						records[i].set('SRProjectPlan$progress',"100%");
					
					}
					//Ext.getCmp('tree2').getCmp('flm_ProjectPlan$progress').setValue("99%");
		 		}
			}
		}
		 
	},
	getFormpanel_SRProjectPlan: function(curppId) {
		var da = new DataAction();
		var data = null;
		var curId = this.dataId;
		//alert(curId);
		//alert(curClass);
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		if (curppId != '0') {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan", curppId);// 这是要随时变得
			for(var i=0;i<data.length;i++){
					if(data[i].xtype=='textfield'){
						data[i].readOnly=true;
					}
					if(data[i].xtype=='datefield'){
						data[i].readOnly=true;
						data[i].hideTrigger=true ;
					}
					if(data[i].xtype=='combo'){
						data[i].readOnly=true;
						data[i].hideTrigger=true ;
					}
			}
		} else {
			data = da.getSingleFormPanelElementsForEdit("panel_SRProjectPlan","");
			for(var i=0;i<data.length;i++){
				//alert(data[i].id);
				if(data[i].id=='SRProjectPlan$parentPlanCombo'){
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
						requirementId : curId,
						requirementClass : curClass,
						start:0}
						});
						return false;
						}
					}
				}
			}
		}
		biddata = da.split(data);
		this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
			id : 'panel_SRProjectPlan',
			layout : 'table',
			height : 'auto',
			width : 600,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			//title : "项目计划表单面板",
			items : biddata
		});
	   return this.formpanel_SRProjectPlan;
	},
	operate : function(){
//		alert("aa");
				var reqId = this.dataId;
				//var reqClazz = this.reqClass;
				var record = Ext.getCmp("tree2").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree2").getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("提示","请先选择要报工的行!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("提示","报工时只能选择一行!");
					return;
				}
				var ccId=record.get("SRProjectPlan$id");
				var url = webContext + '/SRAction_findProjectPlanForReq.action?dataId='+ccId;
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", url, false);
				 conn.send(null);
				 var data = eval('('+conn.responseText+')');
				 if(data.name=='100%'){
				 	Ext.Msg.alert("提示","该项目计划已完工！");
				 	return;
				 }
				//alert(ccId);
				//alert(this.dataId);
				var dataId = this.dataId;
				window.location= webContext+"/user/require/specialRequire1/renderList.jsp?ccId="+ccId+"&dataId="+dataId;
	},
	
	//+++++++++++张喜+保存报工小时数+++++++++++++
	
	save : function() {// 搜索
		var hoursAmount = Ext.getCmp('userGroup').getValue();
		var temp=this.dataId
			Ext.Ajax.request({
				url:webContext + '/requireAction_saveAnnounceWorkHours.action',
				method:'post',
				params : {
					hoursAmount:hoursAmount,
					specialRequirementId:temp
				},
				success:function(response, action){
					var ms=Ext.decode(response.responseText);
					if(ms.success){
						Ext.Msg.alert("提示","保存报工成功！");
					}else{
						Ext.Msg.alert("提示","保存报工失败！");
					}
				},
				failure:function(form, action){
				}
			},this);
	},
	
//------------------------------	
	
	
	
	
	getTreePanel : function(rootPlanId) {},

	
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		
		
		var da = new DataAction();
		var sra = new SRAction();
		var rootPlanId = this.rootId;
		
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
		
		var modify2Button=new Ext.Button({
			text:'报工',
			pressed:true,
			iconCls: 'edit',
			scope : this,
			handler:function(){
				//alert(webContext+"/user/require/individuation/renderList.jsp");
//				window.location= webContext+"/user/require/individuation/renderList.jsp?dataId="+curId;
				var reqId = this.dataId;
				var record = Ext.getCmp("tree2").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree2").getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("提示","请先选择要报工的行!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("提示","报工时只能选择一行!");
					return;
				}
				var ccId=record.get("SRProjectPlan$id");
				
					var url = webContext + '/SRAction_findProjectPlanForReq.action?dataId='+ccId;
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", url, false);
				 conn.send(null);
				 
				 var data = eval('('+conn.responseText+')');
				 if(data.name=='100%'){
				 	Ext.Msg.alert("提示","该项目计划已完工！");
				 	return;
				 }
				//alert(ccId);
				//alert(this.dataId);
				//var reqClass=this.reqClass;
				var dataId = this.dataId;
				//alert(this.reqClass);
				window.location= webContext+"/user/require/specialRequire1/renderList.jsp?ccId="+ccId+"&dataId="+dataId;

		    	}
			});
		var userGroup=new Ext.form.TextField({
			id : 'userGroup',
			emptyText : '[报工小时数]',
			width : 160
		});
		this.treePanel = new Ext.ux.maximgb.treegrid.GridPanel({
			store : this.store,
			id : "tree2",
			width : 900,
			height : 500,
			frame : true,
			master_column_id : 'SRProjectPlan$id',
			cm : this.cm,
			sm : sm,               
			stripeRows : true,
			autoExpandColumn : 'SRProjectPlan$id',
			title : "项目报工列表",
			root_title : '项目报工',
			tbar : [modify2Button,
			//++++++++++张喜 增加保存报工++++++++++
					userGroup, '&nbsp;|&nbsp;', {
						text : '保存',
						pressed : true,
						handler : this.save,
						scope : this,
						iconCls : 'search'
					}, '&nbsp;|&nbsp;', {
						text : '重置',
						pressed : true,
						handler : function() {
							Ext.getCmp("userGroup").reset();
						},
						scope : this,
						iconCls : 'reset'
					}
			//--------------------		
			]
		});
        // this.store.on('load', function(){Ext.getCmp('tree2').expandAllNodes();}); 
         this.treePanel.on("dblclick", this.operate, this);
 		//++++++++++++++++++张喜++++++++
        var dataId=this.dataId;
		var url = webContext + '/requireAction_findAllHours.action?dataId='+dataId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		var data = eval("(" + conn.responseText + ")");
		//alert(data.allHoursAmount);
		Ext.getCmp('userGroup').setValue(data.allHoursAmount);
		
		//------------------------------
   			
		
		var items = new Array();
		items.push(this.treePanel);
		this.items = items;
		
		
		
		
		projectRenderListPanel.superclass.initComponent.call(this);
	}
})