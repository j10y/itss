PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
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
	gotoBack : function(){
		var reqId = this.ccId;
		//var reqCls = this.reqCls;
		var dataId = this.dataId;
			window.location = webContext+"/user/require/specialRequire2/executeByEngineer.jsp?dataId="+dataId+'&tabNumer='+'projectRenderListPanel';
//		history.back();
	},
	newReport:function(){
				var reqId = this.ccId;
				var stored = this.store;
				//alert(stored);
				var createPanel2 = this.getWorkReportPanel('0');
				var win1 = new Ext.Window({
					title : '�½���Ŀ����',
					height:400,
					width:500,
					constrain:false,
					constrainHeader:true,
					modal:true,
					resizable:false,
					draggable:true,
					layout:'fit',
					scope :this,
					items : [createPanel2],
					buttons : [{
						text:'����',
						handler:function(){
							//add by lee for ���ӹ��� 
							if(!Ext.getCmp('requirementWorkReport').form.isValid()){
								
								//add by musicbear for ���ӹ��� ʵ�ʻ���ʱ���ʽ
								if(!validate_integer(Ext.getCmp('itil_sci_ProjectWorkReport$spendHours').getValue())){
									Ext.MessageBox.alert("��ʾ","ʵ�ʻ���ʱ���ʽΪ������,��λΪСʱ����������д��");	
									return false;
								}
								//add by musicbear for ���ӹ��� ʵ�ʻ���ʱ���ʽ							
								Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
								return false;
							}
							//add by lee for ���ӹ��� 							
					
							var formParam = Ext.getCmp('requirementWorkReport').form.getValues(false);
							//alert(Ext.encode(formParam));
							if(formParam.flm_ProjectWorkReport$projectPlan==""){
								Ext.MessageBox.alert("��ʾ","ȱ�ٱ������ݣ�����д�����ٱ���");
								return;
							}
							var vp = null;
							if(formParam!=null){
								vp = Ext.apply(formParam,vp,{});
							}
							Ext.Ajax.request({
								url : webContext + '/SRAction_saveProjectWorkReport.action?'
								+'reqId='+reqId,
								params : vp,
					
								success : function(response, options) {
									Ext.MessageBox.alert("����ɹ�");
									//var stored = Ext.getCmp('tree111').store;
									//alert(Ext.encode(options));
									 var responseArray = Ext.util.JSON.decode(response.responseText);
									 var name = responseArray.name;
									Ext.getCmp('SRProjectPlan$progressCombo').setValue(name)
									stored.reload();
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
							stored.reload();
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
	modifyReport : function(){
				var reqId = this.ccId;
//				alert(reqId);
				var stored = this.store;
				//alert(stored);
				var record = Ext.getCmp("flm_projectRenderList").getSelectionModel().getSelected();
				var records = Ext.getCmp("flm_projectRenderList").getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
					return;
				}
				if(records.length>1){
					Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
					return;
				}
				var modifyPanel = this.getWorkReportPanel(record.get("itil_sci_ProjectWorkReport$id"));
				//var modifyPanel = this.getWorkReportPanel('0');
				var win1 = new Ext.Window({
					title : '�޸���Ŀ����',
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
						handler:function(){
							//add by lee for ���ӹ��� 
							if(!Ext.getCmp('requirementWorkReport').form.isValid()){
								
								//add by musicbear for ���ӹ��� ʵ�ʻ���ʱ���ʽ
								if(!validate_integer(Ext.getCmp('itil_sci_ProjectWorkReport$spendHours').getValue())){
									Ext.MessageBox.alert("��ʾ","ʵ�ʻ���ʱ���ʽΪ������,��λΪСʱ����������д��");	
									return false;
								}
								//add by musicbear for ���ӹ��� ʵ�ʻ���ʱ���ʽ
								Ext.MessageBox.alert("��ʾ","ҳ���д���ɫ�����ߵ�Ϊ������,����д������лл��������");	
								return false;
							}
							//add by lee for ���ӹ��� 
							var formParam = Ext.getCmp('requirementWorkReport').form.getValues(false);
							//alert(Ext.encode(formParam));
							if(formParam.flm_ProjectWorkReport$projectPlan==""){
								Ext.MessageBox.alert("��ʾ","ȱ�ٱ������ݣ�����д�����ٱ���");
								return;
							}
							var vp = null;
							if(formParam!=null){
								vp = Ext.apply(formParam,vp,{});
							}
							Ext.Ajax.request({
								url : webContext + '/SRAction_saveProjectWorkReport.action?'
								+'reqId='+reqId,
								params : vp,
					
								success : function(response, options) {
									Ext.MessageBox.alert("����ɹ�");
									//var stored = Ext.getCmp('tree111').store;
									//alert(Ext.encode(options));
									 var responseArray = Ext.util.JSON.decode(response.responseText);
									 var name = responseArray.name;
									Ext.getCmp('SRflm_ProjectPlan$progressCombo').setValue(name)
									stored.reload();
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
							stored.reload();
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
	removeReport : function(){
				var stored = this.store;
				//alert(stored);
				var record = Ext.getCmp("flm_projectRenderList").getSelectionModel().getSelected();
				var records = Ext.getCmp("flm_projectRenderList").getSelectionModel().getSelections();
				if(!record){
					Ext.Msg.alert("��ʾ","����ѡ��Ҫɾ������!");
					return;
				}
//				if(records.length>1){
//					Ext.Msg.alert("��ʾ","ɾ��ʱֻ��ɾ��һ��!");
//					return;
//				}
				var id = new Array();
				var da = new DataAction();
				var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
					if (button == 'yes') {
						for(var i=0;i<records.length;i++){
							id[i] = records[i].data.itil_sci_ProjectWorkReport$id;
							Ext.Ajax.request({
								url : webContext+ '/SRAction_removeProjectWorkReport.action',
								params : {id:id[i]},
								timeout:100000,
								success:function(response){
									var r =Ext.decode(response.responseText);
									if(!r.success){
										Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){

										});
									}
									stored.reload();
								},scope:this

							});
							stored.reload();
						}
					}
				}, this);
				
				
	},
	getWorkReportPanel: function(reportId) {
		var dataId=this.ccId;
		//alert(dataId);
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("requirementWorkReport",this);
			  if(reportId!='0'){
			  	data = da.getSingleFormPanelElementsForEdit("requirementWorkReport", reportId);
			  }else{
			  	data = da.getSingleFormPanelElementsForEdit("requirementWorkReport", "");// ����Ҫ��ʱ���
			  	for(var i=0;i<data.length;i++){
			  		//alert(data[i].id);
			  		if(data[i].id=='itil_sci_ProjectWorkReport$projectPlanCombo'){
			  			data[i].readOnly=true;
			  			data[i].hideTrigger=true ;
			  		}
			  	}
			  }			
				biddata = da.split(data);
			if(this.getFormButtons.length!=0){
					this.requirementWorkReport= new Ext.form.FormPanel({
						id : 'requirementWorkReport',
						layout : 'table',
						height : 'auto',
						width : 400,
						frame : true,
						collapsible : true,
						defaults : {
							bodyStyle : 'padding:4px'
						},
						layoutConfig : {
							columns : 2
						},
						//title : "��Ŀ���������",
						items : biddata
					});
						}else{
							this.requirementWorkReport= new Ext.form.FormPanel({
							id : 'requirementWorkReport',
							layout : 'table',
							height : 'auto',
							width : 400,
							frame : true,
							collapsible : true,
							defaults : {
								bodyStyle : 'padding:4px'
							},
							layoutConfig : {
								columns : 2
							},
							//title : "��Ŀ���������",
							items : biddata
							});
						}
		return this.requirementWorkReport;
	},
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
            //minTabWidth:100,
            //resizeTabs:true,
            title:tabTitle,
			deferredRender : false,
			frame : true,
			plain : true,
            border : false, 
            //tabPosition:'bottom',
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 900,
			//bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa,panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title:panelTitle,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	}, 
	
	
 getFormpanel_SRProjectPlan: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRProjectPlan",this);
			  //alert(this.dataId);
			if (this.ccId != '0') {
				
				data = da.getPanelElementsForEdit("flm_project_renderList", "panel_SRProjectPlan", this.ccId);// ����Ҫ��ʱ���
						for(var i=0;i<data.length;i++){
//							if(data[i].id=='flm_ProjectPlan$planName'){
//								alert(this.dataId);
//								alert(data[i].value);
//								
//							}
								if(data[i].xtype=='textfield'){
									//alert(data[i].id+"dataId != '0'");
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
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("panel_SRProjectPlan");
						for(var i=0;i<data.length;i++){
								if(data[i].xtype=='textfield'){
									//alert(data[i].id);
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
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
			id : 'panel_SRProjectPlan',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "��Ŀ�ƻ������",
			items : biddata
		});
		}else{
			this.formpanel_SRProjectPlan= new Ext.form.FormPanel({
			id : 'panel_SRProjectPlan',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "��Ŀ�ƻ������",
			items : biddata
			});
		}
		return this.formpanel_SRProjectPlan;
	},
	getGridComflm_projectRenderList:function(){
		
		var da = new DataAction();
		var obj = da.getListPanelElementsForHead("flm_projectRenderList");
		var buttonUtil = new ButtonUtil();
		var getGridButtons = buttonUtil.getButtonForPanel("flm_projectRenderList",this);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var hide = headItem.isHidden;
			var isHidde = false;
			if (hide == 'true') {
				isHidde= true;
			}
			// ��ÿһ����������
			var columnItem = "";
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidde,
					align : alignStyle
				}
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.store = da.getPagePanelJsonStore("flm_projectRenderList",obj);
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.formValue = '';
		this.gridComflm_projectRenderList= new Ext.grid.EditorGridPanel({
			id : 'flm_projectRenderList',
			store : this.store,
			cm : this.cm,
			sm : sm,
			title :"��Ŀ�����б�",
			trackMouseOver : false,
			loadMask : true,
			clicksToEdit : 2,
			collapsible : true,
			autoScroll : true,
			loadMask : true,
			autoHeight : true,
			width : 800,// this.width - 15,
			frame : true,
			tbar : [getGridButtons]
		});
			if (this.ccId != '0') {
				
					var pcnameValue = "";
					var fcnameObj = Ext.getCmp("itil_sci_ProjectWorkReport$id");
					if(fcnameObj!=undefined){
					   pcnameValue = Ext.getCmp("itil_sci_ProjectWorkReport$id").getValue();
					}
					//alert("this.dataId="+this.dataId);
					var str = '{' + '\"' + "projectPlan" + '\"' + ':' + '\"'
							+ this.ccId + '\"' + '}';// ����Ҫ��ʱ���
							
					if(this.model=="flm_requireFormPage"|| "flm_projectRenderList"=="configItemListPanel"){ //
						var str = '{\"configItemRequireId\":\"' + this.ccId + '\"}';
					}
					
					var param = eval('(' + str + ')');
					param.methodCall = 'query';
//					alert(Ext.encode(param));
//					alert(str);
					this.store.load({
						params : param
					});
				}
		return this.gridComflm_projectRenderList;
	},
  items : this.items,
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
	    var items = new Array();
	    var pa = new Array();
		  this.pa = pa;
		var gd = new Array();
		  this.gd = gd;
		var temp = new Array();
		  this.temp = temp;
		var formname = new Array();
		  this.formname=formname;
		var gridname = new Array();
		  this.gridname=gridname;
		this.model="flm_project_renderList";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("flm_project_renderList",this);
		if(this.mybuttons!=""){
			this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : this.mybuttons
			};
		}else{
			this.buttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			}
			};
		}
		
		       this. getFormpanel_SRProjectPlan();
		       this.pa.push(this.formpanel_SRProjectPlan);
		       this.formname.push("panel_SRProjectPlan");
		       temp.push(this.formpanel_SRProjectPlan);
		       
		       this.getGridComflm_projectRenderList();
		       this.gd.push(this.gridComflm_projectRenderList);
		       this.gridname.push("flm_projectRenderList");
		       temp.push(this.gridComflm_projectRenderList);
          items = temp;
		if(this.readOnly!=1){
			items.push(this.buttons);
		}
		   this.items = items;
		   this.gridComflm_projectRenderList.on("rowdblclick", this.modifyReport, this);
		   this.on("newReport",this.newReport,this);
		   this.on("modifyReport",this.modifyReport,this);
		   this.on("removeReport",this.removeReport,this);
		   this.on("gotoBack",this.gotoBack,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})