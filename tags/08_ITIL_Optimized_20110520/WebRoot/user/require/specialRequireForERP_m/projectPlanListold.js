projectPlanListPanel = Ext.extend(Ext.Panel, {
	id : "projectPlanListPanel",
	title : '<font color=red>��Ŀ�ƻ�</font>',
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
					Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
					return;
				}
				var modifyPanel = this.getFormProjectPlanInfoPanel(record.get("itil_sci_ProjectPlan$id"));
				var win1 = new Ext.Window({
					title : '��Ŀ�ƻ���Ϣ',
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
						text:'����',
						scope:this,
						handler:function(){
							var formParam = Ext.getCmp('ProjectPlanInfoPanel').form.getValues(false);
							if(formParam.itil_sci_ProjectPlan$planName==""||formParam.itil_sci_ProjectPlan$parentPlan==""||formParam.itil_sci_ProjectPlan$progress==""){
								Ext.MessageBox.alert("��ʾ","ȱ�ٱ������ݣ�����д�����ٱ���");
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
									Ext.MessageBox.alert("����ɹ�");
									win1.close();
								},
								scope:this,
								failure : function(response, options) {
									Ext.MessageBox.alert("����ʧ��");
								}
							}, this);
							}
						},{
						text : '�ر�',
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
		// �ж������������޸�
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		if (curppId != '0') {
			data = da.getSingleFormPanelElementsForEdit("ProjectPlanInfoPanel", curppId);// ����Ҫ��ʱ���
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
					//data[i].fieldLabel="����Ŀ�ƻ�<font color='red'>(��ѡ��)";
					var nId = this.nId;
					data[i]=new Ext.form.ComboBox({ 
					xtype:'combo',
					hiddenName: 'itil_sci_ProjectPlan$parentPlan',
					id :'itil_sci_ProjectPlan$parentPlanCombo',
					width:'auto',
					style:'',
					fieldLabel:'����Ŀ�ƻ�<font color=red>(��ѡ��)',
					lazyRender: true,
					displayField: 'planName',
					valueField :'id',
					emptyText:'��ѡ��...',
					allowBlank:false,
					editable : false,
					typeAhead:false,//�Զ�ƥ��ʣ���ı�
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
			//title : "��Ŀ�ƻ������",
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
				header : title,//"��Ŀ�ƻ�(<font color=red>�����㿪,˫������</font>)",
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
			text:'�½�',
			pressed:true,
			iconCls: 'add',
			scope : this,
			handler:function(){
				var reqId = this.dataId;
				var reqClazz = this.reqClass;
				var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree111").getSelectionModel().getSelections();
				if(records.length>1){
					Ext.Msg.alert("��ʾ","ֻ��ѡ��һ��!");
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
					title : '�½���Ŀ�ƻ�',
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
						text:'����',
						handler:function(){
							var formParam = Ext.getCmp('ProjectPlanInfoPanel').form.getValues(false);
							//alert(Ext.encode(formParam));
							if(formParam.itil_sci_ProjectPlan$planName==""||formParam.itil_sci_ProjectPlan$parentPlan==""||formParam.itil_sci_ProjectPlan$progress==""){
								Ext.MessageBox.alert("��ʾ","ȱ�ٱ������ݣ�����д�����ٱ���");
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
									Ext.MessageBox.alert("����ɹ�");
									win1.close();
									
								},
								failure : function(response, options) {
									Ext.MessageBox.alert("����ʧ��");
								}
							}, this);
							}
						},{
						text : '�ر�',
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
			text:'ɾ��',
			pressed:true,
			iconCls: 'remove',
			scope : this,
			handler:function(){
				var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree111").getSelectionModel().getSelections();
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
					return;
				}
				if (records.length == 0) {
					Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
					return;
				}
				var id = new Array();
				var da = new DataAction();
				var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
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
										Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){

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
			text:'�޸�',
			pressed:true,
			iconCls: 'edit',
			scope : this,
			handler:function(){
				var reqId = this.dataId;
				var reqClazz = this.reqClass;
				var record = Ext.getCmp("tree111").getSelectionModel().getSelected();
				var records = Ext.getCmp("tree111").getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
					return;
				}
				var modifyPanel = this.getFormProjectPlanInfoPanel(record.get("itil_sci_ProjectPlan$id"));
				var win1 = new Ext.Window({
					title : '��Ŀ�ƻ���Ϣ',
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
						text:'����',
						scope:this,
						handler:function(){
							var formParam = Ext.getCmp('ProjectPlanInfoPanel').form.getValues(false);
							if(formParam.itil_sci_ProjectPlan$planName==""||formParam.itil_sci_ProjectPlan$parentPlan==""||formParam.itil_sci_ProjectPlan$progress==""){
								Ext.MessageBox.alert("��ʾ","ȱ�ٱ������ݣ�����д�����ٱ���");
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
									Ext.MessageBox.alert("����ɹ�");
									win1.close();
								},
								scope:this,
								failure : function(response, options) {
									Ext.MessageBox.alert("����ʧ��");
								}
							}, this);
							}
						},{
						text : '�ر�',
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
			text:'ˢ��',
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
			title : "��Ŀ�ƻ��б�",
			root_title : '��Ŀ�ƻ�',
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
			//			text : '�½��Ӽƻ�',
			//			handler : function(){
			//			}
			//		},{
			//			text : '�༭',
			//			handler : function(){
			//			}
			//		},{
			//			text : 'ɾ��',
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
	 * clientvalidation ��ʼ��
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