
//�û���ѯ��
function UserSearchForm(){
	
	this.realName = new Ext.form.TextField({
             xtype: 'textfield',
	         name: 'realName',
	         width:150
	});
	
	this.userName =  new Ext.form.TextField({
             xtype: 'textfield',
             name: 'userName',
             width:150
	});
	
	this.form = new Ext.form.FormPanel({
		layout: 'table', 
		height: 30,  
		autoWidth: true,
		frame:true,
		layoutConfig: {columns: 4},	    		
		items:[
			{html: "�û�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
			this.realName,
    		{html: "�û���:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
    		this.userName
  	     ]
	});
	
	this.beforeSearch = function(){
		
		return true;
	}
}
 var userSearchForm = new UserSearchForm();

///////////////////////////////////////////////////////// 
function UserRolForm(){
//modify by oucy 
//    	this.department = new Ext.form.ComboBox({
//			store : new Ext.data.JsonStore({
//				url: webContext+'/sysManage/userRoleAction.do?methodCall=findAllDept',
//				fields: ['id','name'],
//			    root:'data',
//				sortInfo: {field: "id", direction: "ASC"}
//			}),
//			valueField : "id",
//			displayField : "name",
//			fieldLabel: '��������',
//            emptyText: '��ѡ����������',
//			mode : 'remote',
//			forceSelection : true,
//			hiddenName : "department",
//			editable : true,
//			triggerAction : 'all', 
//			lazyRender: false,
//            typeAhead: true,
//			allowBlank : true,
//			name : "name",
//			minChars: 1,
//			selectOnFocus: true,
//			hideTrigger: false,
//			width: 360,
//			listeners: {
//				select: function(combo, record, index){
//					var id = record.get('id');
//					//alert(id)
//					///////////////////////////////////////////////
//					var conn = Ext.lib.Ajax.getConnectionObject().conn;
//				    conn.open("POST", webContext+'/sysManage/userRoleAction.do?methodCall=findRoleByDept&deptCode='+id, false);
//				    conn.send(null);
//				    var result = Ext.util.JSON.decode(conn.responseText);
//				    //result = "{success:true,rowCount:3,roles:[{'name':'user','descn':'user','id':'2'}, {'name':'һ���û�','descn':'ֻ���Բ鿴ϵͳ��Ϣ������ά����Ϣ','id':'3'},{'name':'�û�����Ա ','descn':'�û�����Ա','id':'4'}]}"
//				    //alert(result.roles.length)
//					for (var i = 0; i < result.roles.length; i++) {
//						var id = result.roles[i].id;
//						var name = result.roles[i].name;
//						var descn = result.roles[i].descn;
//						var data = [{
//							id : 'id',
//							mapping : 'id'
//						},{
//							name : 'name',
//							mapping : 'name'
//						}, {
//							name : 'descn',
//							mapping : 'descn'
//						}]
//						var gridRecord = Ext.data.Record.create(data);
//						var dataRecord = new gridRecord({
//							id : id,
//							name : name,
//							descn : descn
//						});
//					roleGrid.store.add([dataRecord]);
//					}
//					/////////////////////////////////////////////
//					
//			    }
//			}
//	});

	this.department = new Ext.form.ComboBox({
		xtype:'combo',
		id : 'departmentforSelect',
		defaultParam : '',
		allowBlank:false,
		hiddenName: 'department',
		fieldLabel:'����',
		displayField: 'departName',
		valueField : 'id',
		emptyText : '��ѡ����������',
		triggerAction:'all',
		name:'departmentforSelect',
		width: 390,
		validator:function(value){
			if(this.store.getCount()==0&&this.getRawValue()!=''){
				return false;
			}
			if(this.store.getCount()>0){
				var valid = false;
				this.store.each(function(r){ 
					var rowValue=r.data.departName; 
					if(rowValue==value){
						valid=true;
					}
				});
				if(!valid){
					return false;
				}
			}
			return true;
		},
		store:new Ext.data.JsonStore({
			url:webContext+'/sysManage/userRoleAction.do?methodCall=findDeptForCombo',
			fields:['id','departName'],
			totalProperty:'rowCount',
			root:'data',
			id:'id',
			listeners:{
				beforeload:function(store,opt){
					if(opt.params['departName']==undefined){
						opt.params['departName']=Ext.getCmp('departmentforSelect').unicode(Ext.getCmp('departmentforSelect').defaultParam);
					}
				}
			}
		}),
		pageSize:10,
		listeners:{
			'blur':function(combo){
				if(combo.getRawValue()==''){
					combo.reset();
				}
			},
			'beforequery' : function(queryEvent){
				var param = queryEvent.query;
				this.defaultParam=param;
				param = this.unicode(param);
				this.store.load({
					params:{departName:param,start:0},
					callback:function(r, options, success){
						Ext.getCmp('departmentforSelect').validate();
					}
				});
				return true;
			},
			select: function(combo, record, index){
					var id = record.get('id');
					//alert(id)
					///////////////////////////////////////////////
					var conn = Ext.lib.Ajax.getConnectionObject().conn;
				    conn.open("POST", webContext+'/sysManage/userRoleAction.do?methodCall=findRoleByDept&deptCode='+id, false);
				    conn.send(null);
				    var result = Ext.util.JSON.decode(conn.responseText);
				    //result = "{success:true,rowCount:3,roles:[{'name':'user','descn':'user','id':'2'}, {'name':'һ���û�','descn':'ֻ���Բ鿴ϵͳ��Ϣ������ά����Ϣ','id':'3'},{'name':'�û�����Ա ','descn':'�û�����Ա','id':'4'}]}"
				    //alert(result.roles.length)
					for (var i = 0; i < result.roles.length; i++) {
						var id = result.roles[i].id;
						var name = result.roles[i].name;
						var descn = result.roles[i].descn;
						var data = [{
							id : 'id',
							mapping : 'id'
						},{
							name : 'name',
							mapping : 'name'
						}, {
							name : 'descn',
							mapping : 'descn'
						}]
						var gridRecord = Ext.data.Record.create(data);
						var dataRecord = new gridRecord({
							id : id,
							name : name,
							descn : descn
						});
					roleGrid.store.add([dataRecord]);
					}
					/////////////////////////////////////////////
					
			    }
		},
		unicode:function(s) {
		    var len = s.length;
		    var rs = "";
		    for (var i = 0; i < len; i++) {
		        var k = s.substring(i, i + 1);
		        rs += "&#" + s.charCodeAt(i) + ";";
		    }
		    return rs;
		}
	});

	this.form = new Ext.form.FormPanel({ 
	    buttonAlign:'center',  
	    labelAlign:'right',    
	    labelWidth:60,
	    height:50,
	    frame:true,    
	    items:[
            {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "�������ţ�&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.department
            ]}
	    ]
	});
}
///////////////////////////////////////////////////////// 
 
//�û���ϸ��Ϣ��
function UserDetailForm(){
	
	this.realName = new Ext.form.TextField({
             xtype: 'textfield',
	         name: 'realName',
	         id:'realName',
	         width:150
	});
	
	this.userName =  new Ext.form.TextField({
             xtype: 'textfield',
             name: 'userName',
             id:'userName',
             width:150
	});
	
	this.password =  new Ext.form.TextField({
			xtype: 'textfield',
            name: 'password',
            width:150
	});
	
	this.email =  new Ext.form.TextField({
            name: 'email',
	        width:150
	});
	
	this.telephone =  new Ext.form.TextField({
             xtype: 'textfield',
             fieldLabel: '�绰',
             name: 'telephone',
             width:150
	});
	
	this.mobilePhone =  new Ext.form.TextField({
             xtype: 'textfield',
             name: 'mobilePhone',
             width:150
	});
	
	this.enabled =  new Ext.form.Checkbox({
             name: 'enabled',
             anchor: '50%',
             align:'center',
             checked:true,
             width:100
	});
		
	this.specialUser =  new Ext.form.Checkbox({
		     name: 'specialUser',
             anchor: '50%',
             align:'center',
             checked:false,
             width:100            
	});
//modify by oucy	
//    this.department = new Ext.form.ComboBox({
//			store : new Ext.data.JsonStore({
//				url: webContext+'/sysManage/userRoleAction.do?methodCall=findAllDept',
//				fields: ['id','name'],
//			    root:'data',
//				sortInfo: {field: "id", direction: "ASC"}
//			}),
//			valueField : "id",
//			displayField : "name",
//            emptyText: '��ѡ����������',
//			mode : 'remote',
//			forceSelection : true,
//			hiddenName : "id",
//			editable : false,
//			triggerAction : 'all', 
//			lazyRender: true,
//            typeAhead: true,
//			allowBlank : true,
//			name : "department",
//			selectOnFocus: true,
//			width: 390
//	});
	this.department = new Ext.form.ComboBox({
		xtype:'combo',
		id : 'departmentforSave',
		defaultParam : '',
		allowBlank:false,
		hiddenName: 'department',
		fieldLabel:'����',
		displayField: 'departName',
		valueField : 'id',
		emptyText : '��ѡ����������',
		triggerAction:'all',
		name:'departmentforSave',
		width: 390,
		validator:function(value){
			if(this.store.getCount()==0&&this.getRawValue()!=''){
				return false;
			}
			if(this.store.getCount()>0){
				var valid = false;
				this.store.each(function(r){ 
					var rowValue=r.data.departName; 
					if(rowValue==value){
						valid=true;
					}
				});
				if(!valid){
					return false;
				}
			}
			return true;
		},
		store:new Ext.data.JsonStore({
			url:webContext+'/sysManage/userRoleAction.do?methodCall=findDeptForCombo',
			fields:['id','departName'],
			totalProperty:'rowCount',
			root:'data',
			id:'id',
			listeners:{
				beforeload:function(store,opt){
					if(opt.params['departName']==undefined){
						opt.params['departName']=Ext.getCmp('departmentforSave').unicode(Ext.getCmp('departmentforSave').defaultParam);
					}
				}
			}
		}),
		pageSize:10,
		listeners:{
			'blur':function(combo){
				if(combo.getRawValue()==''){
					combo.reset();
				}
			},
			'beforequery' : function(queryEvent){
				var param = queryEvent.query;
				this.defaultParam=param;
				param = this.unicode(param);
				this.store.load({
					params:{departName:param,start:0},
					callback:function(r, options, success){
						Ext.getCmp('departmentforSave').validate();
					}
				});
				return true;
			}
		},
		unicode:function(s) {
		    var len = s.length;
		    var rs = "";
		    for (var i = 0; i < len; i++) {
		        var k = s.substring(i, i + 1);
		        rs += "&#" + s.charCodeAt(i) + ";";
		    }
		    return rs;
		}
	});
	
	this.enabled.on("check",function(field){

		var value = field.getValue();
	    if(value == false){
			this.enabledValue.setValue(0);
	    	return;
		}else if(value == true){
			this.enabledValue.setValue(1);
			return;
		}
	},this);
    
	this.enabledValue = new Ext.form.Hidden({
		 id:'enabledHid',
		 name:'enabled',
		 value:1
	});
	
	this.specialUser.on("check",function(field){

		var value = field.getValue();
	    if(value == false){
			this.specialUserValue.setValue(0);
	    	return;
		}else if(value == true){
			this.specialUserValue.setValue(1);
			return;
		}
	},this);
    
	this.specialUserValue = new Ext.form.Hidden({
		 id:'specialUser',
		 name:'specialUser',
		 value:0
	});
	
	this.form = new Ext.form.FormPanel({ 
	    buttonAlign:'center',  
	    labelAlign:'right',    
	    labelWidth:60,
	    height:"auto",
	    frame:true,    
	    items:[
	    {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
    			{html: "�û�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
    			this.realName,
        		{html: "�û���:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.userName
        	]}, 
            {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.password,
        		{html: "�����ʼ�:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.email
        		]
            },
           {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "�绰:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.telephone,
        		{html: "�ֻ�:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.mobilePhone
            ]},
            {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "�������ţ�&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.department
            ]}, 
          {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "�Ƿ����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.enabled,
        		{html: "�Ƿ������û�:&nbsp;",cls: 'common-text',style:'width:140;text-align:right'},
        		this.specialUser,this.enabledValue,this.specialUserValue
            ]}
	    ]
	});
	

	//��У��
	this.realName.on("blur",function(field){
		var value = field.getValue();
		if(value.length==0){
				field.markInvalid("�û���������Ϊ��");			
				showVailed('�û���������Ϊ��');
		}
	});
	
	this.userName.on("blur",function(field){
		var value = field.getValue();
		if(value.length==0){
				field.markInvalid("�û�������Ϊ��");			
				showVailed('�û�������Ϊ��');
		}else if(value.match(/[^0-9A-Za-z]/g)){
			field.markInvalid("�û���������������");		
		}
	});
	
	this.password.on("blur",function(field){
		var value = field.getValue();
		if(value.length==0){
				field.markInvalid("���벻��Ϊ��");			
				showVailed('���벻��Ϊ��');
		}
	});
	
	this.email.on("blur",function(field){
		var value = field.getValue();
		var len = value.length;
		if(len==0){
			field.markInvalid("�����ʼ�����Ϊ��,��ʽ�磺user@domain.com");			
			showVailed('�����ʼ�����Ϊ��');
		}
		if(len!=0){
			 var index1 = value.indexOf('@');
			 var index2 = value.indexOf('.');
			 if(index1==0 || index1==-1 || index2==-1 || index1>index2 || index2-index1==1 || index2==len-1){
			 	field.markInvalid("������������ǵ����ʼ���ַ,��ʽ��:user@domain.com");			
				showVailed('Email����������ǵ����ʼ���ַ,��ʽ��:user@domain.com');
			 }	
		}	
	});
	
	
	this.beforeSave = function(){
		var realName = this.realName.getValue();
		var userName = this.userName.getValue();
		var password = this.password.getValue();
		var email = this.email.getValue();
		var department = this.department.getValue();
//		alert("���ű��deptCode ��"+department);
		if(realName==""){
			Ext.MessageBox.alert("��ʾ","�û���������Ϊ��");
			return false;
		}
		if(userName==""){
			Ext.MessageBox.alert("��ʾ","�û�������Ϊ��");
			return false;
		}
		if(password==""){
			Ext.MessageBox.alert("��ʾ","���벻��Ϊ��");
			return false;
		}
		if(email==""){
			Ext.MessageBox.alert("��ʾ","�����ʼ�����Ϊ��");
			return false;
		}
		if(department==""){
			Ext.MessageBox.alert("��ʾ","�������ű���ѡ��");
			return false;
		}
		if(userName!="" && userName.match(/[^0-9A-Za-z]/g)){
			Ext.MessageBox.alert("��ʾ","�û���������������");
			return false;
		}
		return true;
	}
}

//�û���Ϣ�޸ı�
 function UserDetailEditForm(userId,department){
 	
 	this.realName = new Ext.form.TextField({
             xtype: 'textfield',
	         name: 'realName',
	         width:150
	});
	
	this.userName =  new Ext.form.TextField({
             xtype: 'textfield',
             name: 'userName',
             width:150
	});
	
	this.password =  new Ext.form.TextField({
			xtype: 'textfield',
            name: 'password',
            width:150
	});
	
	this.email =  new Ext.form.TextField({
            xtype: 'textfield',
            name: 'email',
	        width:150
	});
	
	this.telephone =  new Ext.form.TextField({
             xtype: 'textfield',
             fieldLabel: '�绰',
             name: 'telephone',
             width:150
	});
	
	this.mobilePhone =  new Ext.form.TextField({
             xtype: 'textfield',
             name: 'mobilePhone',
             width:150
	});
//modify by oucy	
//	this.department = new Ext.form.ComboBox({
//			store : new Ext.data.JsonStore({
//				url: webContext+'/sysManage/userRoleAction.do?methodCall=findAllDept',
//				fields: ['id','name'],
//			    root:'data',
//				sortInfo: {field: "id", direction: "ASC"}
//			}),
//			valueField : "id",
//			displayField : "name",
//            emptyText: department,
//			mode : 'remote',
//			forceSelection : true,
//			hiddenName : "deptId",
//			editable : true,
//			triggerAction : 'all', 
//			lazyRender: false,
//            typeAhead: true,
//			allowBlank : true,
//			name : "name",
//			minChars: 1,
//			selectOnFocus: true,
//			hideTrigger: false,
//			width: 390,
//			listeners: {
//				select: function(combo, record, index){
//					var id = record.get('id');
//			    }
//			}
//	});
	this.department = new Ext.form.ComboBox({
		xtype:'combo',
		id : 'departmentforSave',
		defaultParam : '',
		allowBlank:false,
		hiddenName: 'department',
		fieldLabel:'����',
		displayField: 'departName',
		valueField : 'id',
		emptyText : '��ѡ����������',
		triggerAction:'all',
		name:'departmentforSave',
		width: 390,
		validator:function(value){
			if(this.store.getCount()==0&&this.getRawValue()!=''){
				return false;
			}
			if(this.store.getCount()>0){
				var valid = false;
				this.store.each(function(r){ 
					var rowValue=r.data.departName; 
					if(rowValue==value){
						valid=true;
					}
				});
				if(!valid){
					return false;
				}
			}
			return true;
		},
		store:new Ext.data.JsonStore({
			url:webContext+'/sysManage/userRoleAction.do?methodCall=findDeptForCombo',
			fields:['id','departName'],
			totalProperty:'rowCount',
			root:'data',
			id:'id',
			listeners:{
				beforeload:function(store,opt){
					if(opt.params['departName']==undefined){
						opt.params['departName']=Ext.getCmp('departmentforSave').unicode(Ext.getCmp('departmentforSave').defaultParam);
					}
				}
			}
		}),
		pageSize:10,
		listeners:{
			'blur':function(combo){
				if(combo.getRawValue()==''){
					combo.reset();
				}
			},
			'beforequery' : function(queryEvent){
				var param = queryEvent.query;this.defaultParam=param;
				param = this.unicode(param);
				this.store.load({
					params:{departName:param,start:0},
					callback:function(r, options, success){
						Ext.getCmp('departmentforSave').validate();
					}
				});
				return true;
			}
		},
		unicode:function(s) {
		    var len = s.length;
		    var rs = "";
		    for (var i = 0; i < len; i++) {
		        var k = s.substring(i, i + 1);
		        rs += "&#" + s.charCodeAt(i) + ";";
		    }
		    return rs;
		},
		initLoad : function() {
			var departName = this.getRawValue();
			var combo = this;
			departName = this.unicode(departName);
			this.store.load({
				params:{isLoad:'true',departName:departName,start:0},
				callback:function(r, options, success){
					var depId = r[0].data.id;
					combo.setValue(depId);
				}
			});
		}
	});
	
	this.enabled =  new Ext.form.Checkbox({
             name: 'enabled',
             anchor: '50%',
             align:'center',
             width:100
	});
		
	this.specialUser =  new Ext.form.Checkbox({
             name: 'specialUser',
             anchor: '50%',
             align:'center',
             width:100
	});
	this.id = new Ext.form.Hidden({xtype:'hidden',name:'id',value:userId});
	
 	this.form = new Ext.form.FormPanel({ 
	    buttonAlign:'center',  
	    labelAlign:'right',    
	    labelWidth:60,
	    autoWidth: true,
	    height: "auto",
	    frame:true, 
	    reader: new Ext.data.JsonReader({
	    		root: 'user',
                successProperty: '@success'
	    },[{
              name: 'realName',
              mapping: 'realName'  
            },{
              name: 'userName',
              mapping: 'userName'
            },{
              name: 'password',
              mapping: 'password'
            },{
              name: 'email',
              mapping: 'email'
            },{
              name: 'telephone',
              mapping: 'telephone'
            },{
              name: 'mobilePhone',
              mapping: 'mobilePhone'
            },
            {
              name: 'enabled',
              mapping: 'enabled'
            },{
              name: 'specialUser',
              mapping: 'specialUser'
           },
           {
              name: 'id',
              mapping: 'id'
           },{
           	  name : 'departmentforSave',
           	  mapping:'name'
           }
        ]),
	     items:[
	    {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
    			{html: "�û�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
    			this.realName,
        		{html: "�û���:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.userName
        	]}, 
            {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.password,
        		{html: "�����ʼ�:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.email
        		]
            },
           {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "�绰:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.telephone,
        		{html: "�ֻ�:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.mobilePhone
            ]},
            {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "��������:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.department
            ]},
          {
    		layout: 'table', 
    		height: 30,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		{html: "�Ƿ����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		this.enabled,
        		{html: "�Ƿ������û�:&nbsp;",cls: 'common-text',style:'width:140;text-align:right'},
        		this.specialUser,this.id
            ]}
	    ]
	});
	this.form.load({
        url: webContext+'/sysManage/userRoleAction.do?methodCall=findUser&id='+userId,
        timeout: 30
//modify by oucy �����˻ص�����
		,success: function(action,form){
        	var combo = form.form.findField('departmentforSave');
        	combo.initLoad();
        }
    });

    //��У��
	this.realName.on("blur",function(field){
		var value = field.getValue();
		if(value.length==0){
				field.markInvalid("�û���������Ϊ��");			
				showVailed('�û���������Ϊ��');
		}
	});
	
	this.userName.on("blur",function(field){
		var value = field.getValue();
		if(value.length==0){
				field.markInvalid("�û�������Ϊ��");			
				showVailed('�û�������Ϊ��');
		}
	});
	
	this.password.on("blur",function(field){
		var value = field.getValue();
		if(value.length==0){
				field.markInvalid("���벻��Ϊ��");			
				showVailed('���벻��Ϊ��');
		}
	});
	
	this.email.on("blur",function(field){
		var value = field.getValue();
		var len = value.length;
		if(len==0){
			field.markInvalid("�����ʼ�����Ϊ��,��ʽ�磺user@domain.com");			
			showVailed('�����ʼ�����Ϊ��');
		}
		if(len!=0){
			 var index1 = value.indexOf('@');
			 var index2 = value.indexOf('.');
			 if(index1==0 || index1==-1 || index2==-1 || index1>index2 || index2-index1==1 || index2==len-1){
			 	field.markInvalid("������������ǵ����ʼ���ַ,��ʽ��:user@domain.com");			
				showVailed('Email����������ǵ����ʼ���ַ,��ʽ��:user@domain.com');
			 }		
		}
	});
 }
 //
function getTopbar() {
		return ['   ', {
			text : '���',
			pressed : true,
			handler : new UserManagePanel().checkRol,
			scope : this,
			iconCls : 'add'
		}, '   ', {
			text : ' ɾ��',
			pressed : true,
				handler : function() {
					var record = roleListGrid.getSelectionModel().getSelected();
					var records = roleListGrid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
					} else {
						Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(
								btn) {
							if (btn == 'yes') {
								if (records) {
									for (var i = 0; i < records.length; i++) {
										roleListGrid.store.remove(records[i]);
									}
								}
							}
						}, this)
					}

				},
			scope : this,
			iconCls : 'reset'
		}];
}

 //
function getTopbarEdit() {
		return ['   ', {
			text : '���',
			pressed : true,
			handler : new UserManagePanel().checkRolEdit,
			scope : this,
			iconCls : 'add'
		}, '   ', {
			text : ' ɾ��',
			pressed : true,
				handler : function() {
					var record = roleEditGrid.getSelectionModel().getSelected();
					var records = roleEditGrid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
					} else {
						Ext.MessageBox.confirm('��ʾ��', '��ȷ��Ҫ���иò�����', function(
								btn) {
							if (btn == 'yes') {
								if (records) {
									for (var i = 0; i < records.length; i++) {
										roleEditGrid.store.remove(records[i]);
										roleEditGrid.editRemovedIds += records[i].get("id")+ ",";
									}
								}
							}
						}, this)
					}

				},
			scope : this,
			iconCls : 'reset'
		}];
}

//
 function RoleGrid(){
 	var sm = new Ext.grid.CheckboxSelectionModel({
            singleSelect: false
    });
	var columns = [
		{
            header:'id',
            hidden : true,
            width:235,
            dataIndex:'id',
            sortable: true,
            editor: new Ext.form.TextField()
        },
	  	{
            header:'��ɫ����',
            width:235,
            dataIndex:'name',
            sortable: true,
            editor: new Ext.form.TextField()
        },{
            header:'����',
            width:230,
            dataIndex:'descn',
            sortable: true,
            editor: new Ext.form.TextField()
        },sm
	];
			
	var roleCM = new Ext.grid.ColumnModel(columns);
//	var conn = Ext.lib.Ajax.getConnectionObject().conn;
//    conn.open("POST", webContext+'/sysManage/userRoleAction.do?methodCall=findRoleByDept&deptCode=50031476', false);
//    conn.send(null);
//    var data = Ext.util.JSON.decode(conn.responseText);
    
	var roleStore = new Ext.data.JsonStore({
        root: 'roles',
        data: {success:true,rowCount:0,roles:[]},
        fields: ['name', 'descn', 'id'],
        autoLoad: true
    	});
	this.grid =  new Ext.grid.GridPanel({
			id:'roleGrid',
	  		store: roleStore,
	  		cm: roleCM,  
	  		sm: sm,
	  		trackMouseOver:false,    
	        loadMask: true,
	        height:'auto',
	        autoScroll:true,
	        y:50,
    		anchor: '0 -50'
	});			
 }

//�����û�,��ɫ�б�Grid
 function RoleListGrid(){
 	var sm = new Ext.grid.CheckboxSelectionModel({
            singleSelect: false
    });
	var columns = [
		{
            header:'id',
            hidden : true,
            width:235,
            dataIndex:'id',
            sortable: true,
            editor: new Ext.form.TextField()
        },
	  	{
            header:'��ɫ����',
            width:235,
            dataIndex:'name',
            sortable: true,
            editor: new Ext.form.TextField()
        },{
            header:'����',
            width:230,
            dataIndex:'descn',
            sortable: true,
            editor: new Ext.form.TextField()
        },sm
	];
			
	var roleCM = new Ext.grid.ColumnModel(columns);
	var conn = Ext.lib.Ajax.getConnectionObject().conn;
    conn.open("POST", webContext+'/sysManage/userRoleAction.do?methodCall=findRoles', false);
    conn.send(null);
    var data = Ext.util.JSON.decode(conn.responseText);
    
	var roleStore = new Ext.data.JsonStore({
        root: 'roles',
        data: data,
        fields: ['name', 'descn', 'id'],
        autoLoad: true
    	});
    this.topbar = getTopbar();	
	this.grid =  new Ext.grid.GridPanel({
			id:'roleGrid',
	  		store: roleStore,
	  		cm: roleCM,  
	  		sm: sm,
	  		trackMouseOver:false,    
	        loadMask: true,
	        height:'auto',
	        autoScroll:true,
	        y:150,
	        tbar : this.topbar,
    		anchor: '0 -150'
	});		
	this.grid.store.removeAll();
 }

//�޸��û�,��ɫ�б�Grid
 function RoleListEditGrid(userId){
 	
 	//������н�ɫ�б�
	
	var conn = Ext.lib.Ajax.getConnectionObject().conn;
    conn.open("POST", webContext+'/sysManage/userRoleAction.do?methodCall=findRolesByUserId&id='+userId, false);
    conn.send(null);
    var data = Ext.util.JSON.decode(conn.responseText);
    
	this.store = new Ext.data.JsonStore({
        root: 'roles',
        data: data,
        fields: ['name', 'descn', 'checked','id'],
        autoLoad: true
    });
    
 	this.sm = new Ext.grid.SmartCheckboxSelectionModel({
            dataIndex:'checked',
            width:30,
            align:'center'
    });
	var columns = [
		{
            header:'id',
            hidden : true,
            width:235,
            dataIndex:'id',
            sortable: true,
            editor: new Ext.form.TextField()
        },
	  	{
            header:'��ɫ����',
            width:230,
            dataIndex:'name'
        },{
            header:'����',
            width:230,
            dataIndex:'descn'
        },{
	        hidden: true,
	        dataIndex: "id",
	        name:'roleId',
	        width:60
	    },this.sm
	];
    this.cm = new Ext.grid.ColumnModel(columns);
    this.topbar = getTopbarEdit();	
	this.grid =  new Ext.grid.GridPanel({
			id:'roleEditGrid',
	  		store: this.store,
	  		cm: this.cm,  
	  		sm: this.sm,
	  		trackMouseOver:false,    
	        loadMask: true,
	        height:'auto',
	        autoScroll:true,
	        y:150,
	        tbar : this.topbar,
    		anchor: '0 -150'
	});	
 }
