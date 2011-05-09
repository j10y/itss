/*
* 部门菜单模板查询表单
*/

function DeptMenuTemplateSearchForm(){
	
	this.templateName = new Ext.form.TextField({
             xtype: 'textfield',
	         name: 'templateName',
	         width:150
	});
	
	this.form = new Ext.form.FormPanel({
		layout: 'table', 
		height: 30,  
		autoWidth: true,
		frame: true,
		layoutConfig: {columns: 4},	    		
		items:[
			{html: "模板名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
			this.templateName
  	     ]
	});
	
	this.beforeSearch = function(){		
		return true;
	}
}
 var deptMenuTemplateSearchForm = new DeptMenuTemplateSearchForm();
