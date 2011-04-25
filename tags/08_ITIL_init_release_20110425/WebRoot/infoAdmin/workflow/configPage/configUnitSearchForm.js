﻿
//订单申请Panel
configUnitSearchForm = Ext.extend(Ext.form.FormPanel,{
	layout: 'table', 
	height: 40,  
	width:'auto',
	frame:true,	  
	items:this.items,
	initComponent : function(){ 
		this.items = [			
			{html: "&nbsp;&nbsp;配置单元名称:",cls: 'common-text',style:'width:100;text-align:center'},	
			{
				id:'shipName',
	         	xtype: 'textfield',
	         	name: 'name',
	         	width: 150
	        }
	     ];
	   configUnitSearchForm.superclass.initComponent.call(this);
	}
})

	


