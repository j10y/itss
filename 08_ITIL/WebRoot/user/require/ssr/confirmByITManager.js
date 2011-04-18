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
	addServiceItem : function(){
				var dataId = this.dataId;
				var reqClass = this.reqClass;
				var record = Ext.getCmp('gridpanel').getSelectionModel().getSelected();
				var records = Ext.getCmp('gridpanel').getSelectionModel().getSelections();
				var store = this.storeservice;
				if (!record) {
					Ext.Msg.alert("��ʾ", "����ѡ��Ҫ��ӵ���!");
					return;
				}
				if (records.length >1) {
					Ext.MessageBox.alert('��ʾ', '���ѡ��һ����Ϣ���������!');
				}
				var newdataId = record.get('ServiceItem$id');
				Ext.Ajax.request({
					url : webContext + '/specialRequireAction_saveServiceItemReShip.action?'
					+'reqId='+dataId+'&reqClass='+reqClass+'&newdataId='+newdataId,
		
					success : function(response, options) {
						Ext.MessageBox.alert("��ʾ","��ӳɹ�",function(){
						Ext.getCmp('winid').close();
						window.location = webContext+"/user/require/specialRequire/comfirmByITManager.jsp?"
								+"dataId="+dataId+"&reqClass="+reqClass+"&tabNumer="+'serviceItemBasePanel';});
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("���ʧ��");
					}
				},this);
	},
	reset : function() {
		Ext.getCmp('searchPanel').form.reset();
	}, 
	createSearch2 : function(btnName, link, imageUrl, scope) {
				var param = Ext.getCmp('searchPanel').form.getValues(false);
				param.methodCall = 'query';
				param.start = 1;
				this.formValue = param;
				this.pageBarservice.formValue = this.formValue;
				this.storeservice.removeAll();
				this.storeservice.load({
					params : param
				});

		return null;
	},
	saveServiceItem : function(){
		var formParam = Ext.getCmp('serviceItemBasePanel3').form.getValues(false);
		var vp = null;
		if(formParam!=null){
			vp = Ext.apply(formParam,vp,{});
		}
		var curdataId = this.dataId;
		var curreqClass = this.reqClass;
		Ext.Ajax.request({
			url : webContext + '/specialRequireAction_saveServiceItemForRequire.action?'
			+'reqClass='+this.reqClass
			+'&reqId='+this.dataId,
			params : vp,

			success : function(response, options) {
				Ext.MessageBox.alert("����ɹ�");
				window.location = webContext+"/user/require/specialRequire/comfirmByITManager.jsp?"
								+"dataId="+curdataId+"&reqClass="+curreqClass+"&tabNumer="+'serviceItemBasePanel';
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
			}
		}, this);
	},
	/////////////////////////////������ѡ�񴰿ڹ���START///////////////////////
	selectServiceItem : function(){
		var createPanel2 = this.getGridPanel();
		this.fp = this.getSearchForm();
			var win1 = new Ext.Window({
					id : 'winid',
					title : '������',
					height:400,
					width:700,
					constrain:false,
					constrainHeader:true,
					modal:true,
					resizable:false,
					draggable:true,
					layout:'border',
					scope :this,
					items : [this.fp,createPanel2],
					buttons : [{
						text : '�ر�',
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
	getSearchForm : function() {
		var da = new DataAction();
		var data = da.getPanelElementsForQuery("SerivceItemListGrid");
		var biddata = da.split(data);
//		alert(biddata.length);
		this.panel = new Ext.form.FormPanel({
			id : 'searchPanel',
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 300,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
//			title : "��ѯ�б�",
			items : biddata
		});
		return this.panel;
	},
	getGridPanel : function() {
		var da = new DataAction();
		this.fp = this.getSearchForm();
		var obj = da.getListPanelElementsForHead("SerivceItemListGrid");
		// ǰ��ĸ�ѡ��
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		columns[0] = sm;
		// ѭ�������е��У�Ȼ��������������
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			var renderer = headItem.renderer;
			var hide = headItem.hidden;
			var isHidden = false;
			if (hide == 'true') {
				isHidden = true;
			}
			// ��ÿһ����������
			var columnItem = "";
				columnItem = {
					header : title,
					dataIndex : propertyName,
					sortable : true,
					hidden : isHidden,
					align : alignStyle
				}
			
			columns[i + 1] = columnItem;
		}
		this.cm = new Ext.grid.ColumnModel(columns);
		this.storeservice = da.getPagePanelJsonStore("SerivceItemListGrid",
				obj);
		this.storeservice.paramNames.sort = "orderBy";
		this.storeservice.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		this.pageBarservice = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.storeservice,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
//		this.formValue = '';
//		this.pageBarservice.formValue = this.formValue;
		this.gridpanel = new Ext.grid.EditorGridPanel({
			id : "gridpanel",
			region : "center",
			rowspan : 1,
			store : this.storeservice,
			width : 100,
			frame : true,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			height : 320,
			tbar : [
					{},
					{
					text : '�� ѯ',
						pressed : true,
						handler : this.createSearch2,
						scope : this,
						iconCls : 'search'
					},'|',
					{
						text : '�� ��',
						pressed : true,
						handler : this.reset,
						scope : this,
						iconCls : 'reset'
					},'|',
					{	text : '�� ��',
						pressed : true,
						handler : this.addServiceItem,
						scope : this,
						iconCls : 'add'},
					'   '],
			bbar : this.pageBarservice
			
		});
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};

//		this.pageBarservice.formValue = param;
		this.storeservice.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});


		this.storeservice.removeAll();
		this.storeservice.load({
			params : param
		});

		return this.gridpanel;

	},
	///////////////////////////////////////////ѡ�������ѡ�񴰿ڹ���END/////////////////////////////////
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
	
	
 getFormpanel_SpecialRequire_Input: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			var buttonUtil = new ButtonUtil();
			this.getFormButtons = buttonUtil.getButtonForPanel("panel_SpecialRequire_Input",this);
			data = da.getPanelElementsForEdit("ssr_confirmByITManager", "panel_SpecialRequire_Input", this.dataId);// ����Ҫ��ʱ���
			for(i=0;i<data.length;i++){
				if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
					data[i].id=data[i].id+8;//�ı�Combobox��id
					data[i].readOnly = true;
					data[i].hideTrigger = true;
				}
				data[i].readOnly = true;
			}
			biddata = da.split(data);
			if(this.getFormButtons.length!=0){
		this.formpanel_SpecialRequire_Input= new Ext.form.FormPanel({
			id : 'panel_SpecialRequire_Input',
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
			title : "������Ի���������",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_SpecialRequire_Input= new Ext.form.FormPanel({
			id : 'panel_SpecialRequire_Input',
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
			title : "������Ի���������",
			items : biddata
			});
		}
		return this.formpanel_SpecialRequire_Input;
	},
 getFormpanel_SRServiceProvider: function() {
	var da = new DataAction();
	var data = null;
	var biddata = "";
	var sra = new SRAction();
	var spId = sra.getReqServiceProviderId(this.dataId);
	var buttonUtil = new ButtonUtil();
	this.getFormButtons = buttonUtil.getButtonForPanel("panel_SRServiceProvider",this);
	if (spId != '0') {
			data = da.getSingleFormPanelElementsForEdit("requirementServiceProviderInfo", spId);// ����Ҫ��ʱ���
					var pid="";
			for(var i=0;i<data.length;i++){
				if(data[i].id=='itil_sci_RequirementServiceProvider$bigTypeCombo'){
				data[i].on('select' , function(combo,record,index){
						pid = record.get("id");
						Ext.getCmp('itil_sci_RequirementServiceProvider$smallTypeCombo').store.load({params:{pid:pid}});
					}
					)
				}
				if(data[i].id=='itil_sci_RequirementServiceProvider$smallTypeCombo'){
						data[i].on('beforequery' , function(queryEvent){
							if(pid==""){
								Ext.Msg.alert("��ʾ","����ѡ��ҵ�����!");
								return false;
							}
						var param = queryEvent.combo.getRawValue();
						var val = queryEvent.combo.getValue();
						if(queryEvent.query==''){
						param='';
						}
						this.store.removeAll();
						this.store.load({
						params:{
						name:param,
						start:0}
						});
						return false;
						}
					)
				}
			}
			biddata = da.split(data);
		} else {
			data = da.getPanelElementsForAdd("requirementServiceProviderInfo");
				var pid="";
			for(var i=0;i<data.length;i++){
				if(data[i].id=='itil_sci_RequirementServiceProvider$bigTypeCombo'){
				data[i].listeners={
					'select' : function(combo,record,index){
						pid = record.get("id");
						Ext.getCmp('itil_sci_RequirementServiceProvider$smallTypeCombo').store.load({params:{pid:pid}});
					}
					}
				}
				if(data[i].id=='itil_sci_RequirementServiceProvider$smallTypeCombo'){
						data[i].listeners = {
						'beforequery' : function(queryEvent){
							if(pid==""){
								Ext.Msg.alert("��ʾ","����ѡ��ҵ�����!");
								return false;
							}
						var param = queryEvent.combo.getRawValue();
						var val = queryEvent.combo.getValue();
						if(queryEvent.query==''){
						param='';
						}
						this.store.removeAll();
						this.store.load({
						params:{
						name:param,
						type:pid,
						start:0}
						});
						return false;
						}
					}
				}
			}
			biddata = da.split(data);
		}
		if(this.getFormButtons.length!=0){
		this.formpanel_SRServiceProvider= new Ext.form.FormPanel({
			id : 'panel_SRServiceProvider',
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
			title : "���Ի����󽻸��Ŷ�����",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_SRServiceProvider= new Ext.form.FormPanel({
			id : 'panel_SRServiceProvider',
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
			title : "���Ի����󽻸��Ŷ�����",
			items : biddata
			});
		}
		return this.formpanel_SRServiceProvider;
	},
 getFormserviceItemBasePanel: function() {
      var da = new DataAction();
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("serviceItemBasePanel",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("ssr_confirmByITManager", "serviceItemBasePanel", this.dataId);// ����Ҫ��ʱ���
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("serviceItemBasePanel");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formserviceItemBasePanel= new Ext.form.FormPanel({
			id : 'serviceItemBasePanel',
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
			title : "��������������",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formserviceItemBasePanel= new Ext.form.FormPanel({
			id : 'serviceItemBasePanel',
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
			title : "��������������",
			items : biddata
			});
		}
		return this.formserviceItemBasePanel;
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
		this.model="ssr_confirmByITManager";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("ssr_confirmByITManager",this);
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
		
		       this.getFormpanel_SpecialRequire_Input();
		       this.pa.push(this.formpanel_SpecialRequire_Input);
		       this.formname.push("panel_SpecialRequire_Input");
		       temp.push(this.formpanel_SpecialRequire_Input);
		       this.getFormpanel_SRServiceProvider();
		       this.pa.push(this.formpanel_SRServiceProvider);
		       this.formname.push("panel_SRServiceProvider");
		       temp.push(this.formpanel_SRServiceProvider);
		       this.getFormserviceItemBasePanel();
		       this.pa.push(this.formserviceItemBasePanel);
		       this.formname.push("serviceItemBasePanel");
		       temp.push(this.formserviceItemBasePanel);
          items.push(this.getTabpanel(temp));
		items.push(this.buttons);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})