﻿
//订单申请查询Panel
ApplySearchForm = Ext.extend(Ext.form.FormPanel,{
	layout: 'table', 
	height: 40,  
	width:'auto',
	frame:true,
	layoutConfig: {columns: 4},	  
	items: this.items,//items在initComponent()中进行初始化
	
	getMyItems: function(){

		return [
			{html: "&nbsp;订单编号:",cls: 'common-text',style:'width:90;text-align:center'},//这里是一个对象	
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
        ApplySearchForm.superclass.initComponent.call(this);
    }
})

	


