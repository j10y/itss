PageTemplates = Ext.extend(Ext.TabPanel, {

	activeTab : 0,
	enableTabScroll : true,
	deferredRender : false,
	frame : true,
	plain : true,
	border : false,
	baseCls : 'x-plain',// 是否设置和背景色同步
	width : 900,
	defaults : {
		autoHeight : true,
		bodyStyle : 'padding:2px'
	},
	/***************************************基础信息面板******************************/
	getBasePanel : function(){
		var da = new DataAction();
		var clazz = "com.zsgj.itil.service.entity.ServiceItem";
		var data = da.getElementsForEdit(clazz,dataId);
		for(i=0;i<data.length;i++){
			if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
				data[i].id=data[i].id+8;//改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			data[i].readOnly = true;
		}
		var biddata = da.split(data);
		this.basePanel = new Ext.form.FormPanel({
			id : 'base',
			title : '服务基础信息',
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
	/***************************************其他信息面板******************************/
	
	getInfoPanel : function() {
		var da = new DataAction();
		var data = da.getElementForIncorporeity(dataId);
//		alert("data:"+data);
		for(i=0;i<data.length;i++){
			if(data[i].xtype=='combo'||data[i].xtype=='datefield'){
				data[i].id=data[i].id+8;//改变Combobox的id
				data[i].readOnly = true;
				data[i].hideTrigger = true;
			}
			data[i].readOnly = true;
		}
		var biddata = da.split(data);
		this.infoPanel = new Ext.Panel({
			id : 'info',
			title : '其他信息',
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
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var basePanel = this.getBasePanel();
		var infoPanel = this.getInfoPanel();
		this.items=[basePanel,infoPanel];
		this.on("pageEvent", this.pageMethod, this);
    	PageTemplates.superclass.initComponent.call(this);  
	},
	pageMethod : function(){
		alert("当前dataId:"+dataId);
	}
})
