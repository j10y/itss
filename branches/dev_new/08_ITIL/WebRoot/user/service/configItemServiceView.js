PageTemplates = Ext.extend(Ext.TabPanel, {

	activeTab : 0,
	enableTabScroll : true,
	deferredRender : false,
	frame : true,
	plain : true,
	border : false,
	baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
	width : 900,
	defaults : {
		autoHeight : true,
		bodyStyle : 'padding:2px'
	},
	/***************************************������Ϣ���******************************/
	getBasePanel : function(){
		var da = new DataAction();
		var clazz = "com.zsgj.itil.service.entity.ServiceItem";
		var data = da.getElementsForEdit(clazz,dataId);
		for(i=0;i<data.length;i++){
			if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
				data[i].id=data[i].id+8;//�ı�Combobox��id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			data[i].readOnly = true;
		}
		var biddata = da.split(data);
		this.basePanel = new Ext.form.FormPanel({
			id : 'base',
			title : '���������Ϣ',
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
			items : biddata
		});
		return this.basePanel;
	},
	/***************************************������Ϣ���******************************/
	
	getInfoPanel : function() {
		var da = new DataAction();
		var data = da.getElementForIncorporeity(dataId);
//		alert("data:"+data);
		for(i=0;i<data.length;i++){
			if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
				data[i].id=data[i].id+8;//�ı�Combobox��id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			data[i].readOnly = true;
		}
		var biddata = da.split(data);
		this.infoPanel = new Ext.Panel({
			id : 'info',
			title : '������Ϣ',
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
			items : biddata
		});
		return this.infoPanel;
	},

	items : this.items,

	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var basePanel = this.getBasePanel();
		var infoPanel = this.getInfoPanel();
		this.items=[basePanel,infoPanel];
		this.on("pageEvent", this.pageMethod, this);
    	PageTemplates.superclass.initComponent.call(this);  
	},
	pageMethod : function(){
		alert("��ǰdataId:"+dataId);
	}
})
