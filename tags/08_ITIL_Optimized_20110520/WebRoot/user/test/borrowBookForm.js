/**
 * 图书借阅查询form
 * 
 */

 function BorrowBookForm(){
	
	
	
	//出处
	this.publisher =  new Ext.form.TextField({
             xtype: 'textfield',
             name: 'bookInfo.publisher',
             width:150
	});
	
	var store1=new Ext.data.JsonStore({
		url:"/book/findbookType.action",
		fields:['name','code'],
		totalProperty:"tatal",
		root:"data",
		id:"name",
		pageSize : 5
		}
	);
	this.bookType
	this.form = new Ext.form.FormPanel({
		layout: 'table', 
		height: 15,  
		frame:true,
		autoWidth: true,
		layoutConfig: {columns: 6},	    		
		items:[
    		{html: "主表名称:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:right'},		        		
    		this.publisher,
    		{html: "所属模块:&nbsp;",cls: 'common-text',style:'width:100;height:20;text-align:right'},		        		
    		{
    		xtype:"combo",
    		fieldLable:'所属模块',
    		width:250,
    		displayField:'name',
    		valueField:'code',
    		pageSize : 5,
    		store:store1,
    		typeAhead:true,
    		triggerAction:'all',
    		name:"bookInfo.bookType.id",
    		emptyText:'请选择...',
    		selectOnFocus:true,
    		pageSize : 5
    		}
  	     ]
	});

	this.beforeSearch = function(){
		
		return true;
	}
}
	
var borrowForm = new BorrowBookForm();