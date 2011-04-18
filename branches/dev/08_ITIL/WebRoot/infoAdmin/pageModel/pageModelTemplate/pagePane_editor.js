/**
 * 编辑pagePanel信息的form
 * 
 */

 function ModifyMessageForm(){
	var display = new Ext.form.ComboBox({ 
		store : new Ext.data.JsonStore({
			url: webContext+'/pageModel/pagePanelManage.do?methodCall=findModuleListByEntity',
			fields: ['id','display'],
		    root:'data',
			sortInfo: {field: "id", direction: "ASC"}
			}),
			valueField : "id",
			displayField : "display",
            emptyText:"是否可见",
			mode: 'remote',
			forceSelection : true,
			hiddenName : "id",
			editable : false,
			triggerAction : 'all', 
			lazyRender: true,
            typeAhead: true,
			allowBlank : true,
			name : "display",
			selectOnFocus: true,
			width: 200
	});

	this.form = new Ext.form.FormPanel({
		layout: 'table', 
		height: '30',  
		autoWidth: true,
		frame: true,
		layoutConfig: {columns: 2},	    		
		items:[
			{html: "可见:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},	
			display
//    		{html: "部门:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:left;margin:5 0 5 0;'},		        		
//    		this.department,this.id  		
  	     ]
	});	
	this.beforeSearch = function(){		
		return true;
	}
}

