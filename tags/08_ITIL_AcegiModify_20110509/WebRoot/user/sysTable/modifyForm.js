    var tid = new Ext.form.Hidden({xtype:'hidden',name:'systemMainTable.id'});
	var tableName = new Ext.form.TextField({
		name : "systemMainTable.tableName",
		fieldLabel : "*系统主表名",
		allowBlank : false,
		blankText : "表名不能为空",
		selectOnFocus : true,
		width : 250
	});

	var tableCnName = new Ext.form.TextField({
		name : "systemMainTable.tableCnName",
		fieldLabel : "*中文名",
		allowBlank : false,
		blankText : "中文名不能为空",
		selectOnFocus : true,
		width : 250
	});

	var className = new Ext.form.TextField({
		name : "systemMainTable.className",
		fieldLabel : "*主表映射实体类",
		allowBlank : false,
		blankText : "主表映射实体类不能为空",
		selectOnFocus : true,
		width : 250
	});
	 var primaryKeyColumn =  new Ext.form.TextField({
             name: 'systemMainTable.primaryKeyColumn',
             fieldLabel : "主键字段",
             selectOnFocus : true,
             width:250
    });
     var module =  new Ext.form.TextField({
             name: 'systemMainTable.module',
             fieldLabel : "所属模块",
             selectOnFocus : true,
             width:250
    });
    
   
    
	var store1=new Ext.data.JsonStore({
		url:"/book/findbookType.action",
		fields:['name','code'],
		totalProperty:"tatal",
		root:"data",
		ids:"name",
		pageSize : 5
		}
	);
	
	var modifyForm = new Ext.form.FormPanel({
		layout: 'table', 
		height: 'auto',  
		width:'1200',
		frame:true,
		autoScroll:true,
		defaults: {
	        bodyStyle:'padding:8px'
	        
	    },
		layoutConfig: {columns: 4},	    		
		items:[
		  	{html: "系统主表名:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},	
			tableName,
    		{html: "中文名:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		tableCnName,
//    		{html: "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>数据库中表的名称，而不是类的名称！</font>",cls: 'common-text',style:'width:150;text-align:right'},	
//    		{html: "<font>数据库中表的名称，而不是类的名称！</font>",cls: 'common-text',style:'width:150;text-align:right'},	
    		{html: "主表映射实体类:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		className,
    		{html: "主键字段:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		primaryKeyColumn,
    		{html: "所属模块:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		module,
//    		{html: "主键字段:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
//    		{
//    		xtype:"combo",
//    		fieldLable:'主键字段',
//    		width:250,
//    		displayField:'primaryKeyColumn',
//    		valueField:'code',
//    		pageSize : 5,
//    		store:store1,
//    		typeAhead:true,
//    		triggerAction:'all',
//    		name:"primaryKeyColumn",
//    		emptyText:'请选择...',
//    		selectOnFocus:true,
//    		pageSize : 5
//    		},
//    		{html: "所属模块:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
//    		{
//    		xtype:"combo",
//    		fieldLable:'所属模块',
//    		width:250,
//    		displayField:'name',
//    		valueField:'code',
//    		pageSize : 5,
//    		store:store1,
//    		typeAhead:true,
//    		triggerAction:'all',
//    		name:"module",
//    		emptyText:'请选择...',
//    		selectOnFocus:true,
//    		pageSize : 5
//    		},
//    		
    		{
            	layout: 'table', 
				height: 'auto',  
				width:'auto',
				style: 'margin:4px 6px 4px 300px',
				colspan: 4,
				align: 'center',
				defaults: {
			        bodyStyle:'padding:8px'
			    },
				layoutConfig: {columns: 3},
				items:[
					{
		             	xtype: 'button',             	
		             	style: 'margin:4px 10px 4px 0',		             	
		             	text: '保存主表属性修改',
		             	handler:submitBookInfo
		            }
				]
			}           
  	     ]
	});
	function submitBookInfo(){
            addForm.form.submit({
                waitTitle:"请稍等",
                waitMsg:"正在提交表单数据,请稍等...",
                 url:"test/TableSave.action",
                method:"POST",
                success:function(action,form){
                    alert("提交成功");
                    location.href = 'cust.jsp';
                    
                },
                failure:function(action,form){
                    alert("提交失败");
                }
            });
        }
      
//        alert("11111"+id);
        
	modifyForm.load({
        url:'test/TableModify.action?id='+id,
        timeout: 3000000
        
    });