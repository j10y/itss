﻿
//订单申请查询Panel
ServiceSearchForm = Ext.extend(Ext.form.FormPanel,{
	layout: 'table', 
	height: 40,  
	width:'auto',
	frame:true,
	layoutConfig: {columns: 4},	  
	items: this.items,
	
	getMyItems: function(){
//		var clazz = "com.digitalchina.ibmb2b.order.entity.OrderApplyBid";
//		var da = new DataAction();
//		return da.split(da.getElementsForHead(clazz));
		return [
			{html: "&nbsp;订单编号:",cls: 'common-text',style:'width:90;text-align:center'},	
			{
             	xtype: 'textfield',
             	name: 'fileName',
             	width: 200
            },
            {html: "&nbsp;&nbsp;&nbsp;&nbsp;最终客户名称:",cls: 'common-text',style:'width:120;text-align:center'},	
			{
             	xtype: 'textfield',
             	name: 'fileName',
             	width:200
            }
  	     ];
	},

	initComponent : function(){ 
		this.items = this.getMyItems();	
		
        ServiceSearchForm.superclass.initComponent.call(this);
    }
})

	


