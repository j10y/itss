
SearchForm = Ext.extend(Ext.form.FormPanel,{
	layout: 'table', 
	height: 'auto',  
	width:'auto',
	frame:true,
	layoutConfig: {columns: 4},	  
	items: this.items,
	
	getMyItems: function(clazz){
		var da = new DataAction();
		return da.split(da.getElementsForHead(clazz));
	},

	initComponent : function(){ 
		this.items = this.getMyItems();			
        SearchForm.superclass.initComponent.call(this);
    }
})