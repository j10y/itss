﻿
//订单申请查询Panel
AccreditSearchForm = Ext.extend(Ext.form.FormPanel,{
	layout: 'table', 
	height: 80,  
	width:'auto',
	frame:true,
	layoutConfig: {columns: 4},	 
		defaults: {
        bodyStyle:'padding:8px'
    },
    
	items: this.items,

		
	initComponent : function(){ 		
		var clazz = "com.zsgj.info.framework.workflow.entity.TaskPreAssign";
		var da = new DataAction();
		this.items = da.split(da.getElementsForQuery(clazz));		
        AccreditSearchForm.superclass.initComponent.call(this);
    }
})


	


