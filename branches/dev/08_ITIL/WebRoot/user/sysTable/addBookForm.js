
	var tableName = new Ext.form.TextField({
		name : "systemMainTable.tableName",
		fieldLabel : "*ϵͳ������",
		
		allowBlank : false,
		blankText : "��������Ϊ��",
		selectOnFocus : true,
		width : 250
	});

	var tableCnName = new Ext.form.TextField({
		name : "systemMainTable.tableCnName",
		fieldLabel : "*������",
		allowBlank : false,
		blankText : "����������Ϊ��",
		selectOnFocus : true,
		width : 250
	});

	var className = new Ext.form.TextField({
		name : "systemMainTable.className",
		fieldLabel : "*����ӳ��ʵ����",
		allowBlank : false,
		blankText : "����ӳ��ʵ���಻��Ϊ��",
		selectOnFocus : true,
		width : 250
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
	
	var addForm = new Ext.form.FormPanel({
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
		  	{html: "ϵͳ������:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},	
			tableName,
    		{html: "������:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		tableCnName,
    		//{html: "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>���ݿ��б������ƣ�������������ƣ�</font>",cls: 'common-text',style:'width:150;text-align:right'},	
    		//{html: "<font>���ݿ��б������ƣ�������������ƣ�</font>",cls: 'common-text',style:'width:150;text-align:right'},	
    		{html: "����ӳ��ʵ����:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		className,
    		{html: "�����ֶ�:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		{
    		xtype:"combo",
    		fieldLable:'�����ֶ�',
    		width:250,
    		displayField:'name',
    		valueField:'code',
    		pageSize : 5,
    		store:store1,
    		typeAhead:true,
    		triggerAction:'all',
    		name:"bookInfo.bookType.id",
    		emptyText:'��ѡ��...',
    		selectOnFocus:true,
    		pageSize : 5
    		},
    		{html: "����ģ��:&nbsp;",cls: 'common-text',style:'width:150;text-align:right'},		        		
    		{
    		xtype:"combo",
    		fieldLable:'����ģ��',
    		width:250,
    		displayField:'name',
    		valueField:'code',
    		pageSize : 5,
    		store:store1,
    		typeAhead:true,
    		triggerAction:'all',
    		name:"bookInfo.bookType.id",
    		emptyText:'��ѡ��...',
    		selectOnFocus:true,
    		pageSize : 5
    		},
    		
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
		             	text: '�������������޸�',
		             	handler:submitBookInfo
		            }
				]
			}           
  	     ]
	});
	function submitBookInfo(){
            addForm.form.submit({
                waitTitle:"���Ե�",
                waitMsg:"�����ύ��������,���Ե�...",
                url:"test/TableSave.action",
                method:"POST",
                success:function(action,form){
                    alert("�ύ�ɹ�");
                    location.href = 'configItem.jsp';
                    
                },
                failure:function(action,form){
                    alert("�ύʧ��");
                }
            });
        }     
	