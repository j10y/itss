﻿
//订单申请查询Panel
WorkflowRoleSearchForm = Ext.extend(Ext.form.FormPanel,{
	layout: 'table', 
	height: 80,  
	width:'auto',
	frame:true,
	layoutConfig: {columns: 4},	 
		defaults: {
        bodyStyle:'padding:8px'
    },
    
	items: this.items,

	getMyItems: function(){
		var clazz = "com.digitalchina.info.framework.workflow.entity.WorkflowRole";
		var da = new DataAction();
		return da.split(da.getElementsForQuery(clazz));
	},
	
	
	initComponent : function(){ 
		this.items = this.getMyItems();			
        WorkflowRoleSearchForm.superclass.initComponent.call(this);
    }
})

	


