//�û���ϸ��Ϣ��
function UserDetailForm(){
	
	this.definitonName =  new Ext.form.TextField({
			 id:'userDefinitonName',
             xtype: 'textfield',
             name: 'definitonName',
             readOnly : true,
             width:150
	});	
	this.nodeName =  new Ext.form.TextField({
			id:'userNodeName',
			xtype: 'textfield',
            name: 'nodeName',
            readOnly : true,
            width:150
	});		
	this.nodeDesc =  new Ext.form.TextField({
			id:'userNodeDesc',
			xtype: 'textfield',
            name: 'nodeDesc',
            readOnly : true,
            width:150
	});		
	var codes = [['0','���������'], ['1','��һ������'], ['2','�Զ�������']];
	var stores = new Ext.data.SimpleStore({
		fields : ['id','type'],
		data : codes
	});
	this.type= new Ext.form.ComboBox({
		       id:"userRoleType",
			   name : 'typeName',
				store : stores,
				triggerAction : 'all',
				displayField : 'type',
				valueField : 'id',
				value : '��һ������',
				mode : 'local',
				width : 200
	});
	this.form = new Ext.form.FormPanel({ 
		id:'userDetailForm',
	    buttonAlign:'center',  
	    labelAlign:'right',    
	    labelWidth:60,
	    height:"auto",
	    frame:true,   
	    reader: new Ext.data.JsonReader({//��������þ��൱�ڰѺ�̨���͵�typeӳ�䵽��Ӧ�Ŀؼ�type�����ˣ�
					//��ȡ��֮���Ұ����ݷŵ�record���У���store�ļ�����һ����
			    		root: 'list',
		                successProperty: '@success'
			    },[{
		              name: 'definitonName',//�Ƕ�Ӧ��record�е�hiddenName
		              mapping: 'definitonName'  //��Ӧ���Ƕ�ȡ֮���record
		            },{
		              name: 'nodeName',
		              mapping: 'nodeName'
		            },{
		              name: 'nodeDesc',
		              mapping: 'nodeDesc'
		            }
		]),
	    items:[
	    {
    		layout: 'table', 
    		height: 50,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
    			{html: "��������:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
    			this.definitonName,
        		{html: "�ڵ�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	        		
        		this.nodeName,
        		{html: "�ڵ�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.nodeDesc
        	]
	    },
	    {
    		layout: 'table', 
    		height: 50,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		 {html: "��ɫ���ͣ�&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        			this.type
            ]}               
	    ]
	});	
	this.form.load({//��������form��������
			 url: webContext+'/configUnitRole_queryProNameAndnodeName.action?nodeId='+nodeId+'&virtualDefinitionInfoId='+virtualDefinitionInfoId,
			 timeout: 3000			 
	});
	
}
//
function getTopbar() {//λ��������grid����
		return ['   ', {
			text : '���',
			pressed : true,
			handler : new ConfigUnitRoleListPanel().checkRol,
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
		},'<font color=red>����ʾ������һ��ѡ��һ����ɫ����Ϊһ����ɫֻ��Ӧһ����¼��</font>'];
}


////�����û�,��ɫ�б�Grid
// function RoleListGrid(){
// 	var sm = new Ext.grid.CheckboxSelectionModel({
//            singleSelect: false
//    });
//	var columns = [
//		{
//            header:'id',
//            hidden : true,           
//            dataIndex:'id',
//            sortable: true,
//            editor: new Ext.form.TextField()
//        },
//	  	{
//            header:'��ɫ����',          
//            dataIndex:'name',
//            sortable: true,
//            editor: new Ext.form.TextField()
//        },{
//            header:'����',          
//            dataIndex:'descn',
//            sortable: true,
//            editor: new Ext.form.TextField()
//        },sm
//	];	
//	var roleCM = new Ext.grid.ColumnModel(columns);
//	var roleStore = new Ext.data.JsonStore({
//		url:webContext+'/configUnitRole_findRolesByDept.action?virtualDefinitonId='+virtualDefinitionInfoId,
//        root: 'data',
//        fields: ['name', 'descn', 'id']        
//    	});    	
//    this.topbar = getTopbar();	    
//	this.grid =  new Ext.grid.GridPanel({
//			id:'roleGrid',
//	  		store: roleStore,
//	  		cm: roleCM,  
//	  		sm: sm,
//	  		trackMouseOver:false,    
//	        loadMask: true,	        
//	        autoScroll:true,
//	        y:65,
//	        viewConfig : {
//					autoFill : true,
//					forceFit : true
//		        },
//	        tbar : this.topbar,
//    		anchor: '0 -0'
//	});		 
//	this.grid.store.removeAll();
//	this.grid.store.reload();
// }
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
			//title:'<font color=red>�����Ҫ�޸Ľ�ɫ������ɾ�����ڵĽ�ɫ�������½�ɫ����Ϊһ����ɫ��Ӧһ����¼</font>',
	  		store: roleStore,
	  		cm: roleCM,  
	  		sm: sm,
	  		trackMouseOver:false,    
	        loadMask: true,
	        height:'auto',
	        viewConfig:{
	        	autoScroll:true
	        },
	        
	        y:80,
	        tbar : this.topbar,
    		anchor: '0 -0'
	});		
	this.grid.store.removeAll();
 }
 
 ///////////////////////////////////////////////////////// 
function UserRolForm(){ //�ڶ����б�ҳ���form��
	var clazz = "com.zsgj.info.framework.security.entity.Department";
	//�������ŵĿɲ�ѯ��comboBox
    var department = new Ext.form.ComboBox({			
			valueField : "id",
			displayField : "departName",
            emptyText: '��ѡ����������',
			mode : 'remote',
			forceSelection : true,
			hiddenName : "department",
			editable : true,
			triggerAction : 'all', 
			lazyRender: true,
            typeAhead: false,
			allowBlank : true,
			name : "department",
			selectOnFocus: true,
			width: 300,
			//*******************************************************************
			store:new Ext.data.JsonStore({//displayField
				url:webContext+'/configUnitRole_queryCobom.action?displayField=departName&clazz='+clazz,
				fields: ['id','departName'],
				totalProperty:'rowCount',
				root:'data',
				id:'id',
				sortInfo: {field: "id", direction: "ASC"},
				listeners:{
					beforeload:function(store,opt){
						if(opt.params['departName']== undefined){
							opt.params['departName']=department.defaultParam;
						}
					}
				}
			}),
			//*******************************************************************
			validator:function(value){
				if(this.store.getCount()==0&&this.getRawValue()!='')
					{return false;}
				if(this.store.getCount()>0){
					var valid = false;
					this.store.each(function(r){
						var rowValue=r.data.departName;
						if(rowValue==value){valid=true;} 
					});
					if(!valid){return false;}
				}
				return true;
			},				
			//*******************************************************************
			pageSize:10,
			listeners:{
				blur:function(combo){//����ʧȥ�����ʱ��
					if(combo.getRawValue()==''){
						combo.reset();
					}
				},
				beforequery : function(queryEvent){
					var param = queryEvent.query;
					this.defaultParam=param;
					this.store.load({
						params:{departName:param,start:0},
						callback:function(r, options, success){
							department.validate();}
					});
					return true;
				},
				select: function(combo, record, index){
					var id = record.get('id');
					///////////////////////////////////////////////
					var conn = Ext.lib.Ajax.getConnectionObject().conn;
					conn.open("POST", webContext+'/configUnitRole_findRoleByDept.action?deptCode='+id, false);
				    conn.send(null);
				    var result = Ext.util.JSON.decode(conn.responseText);	
					for (var i = 0; i < result.data.length; i++) {
						var id = result.data[i].id;
						var name = result.data[i].name;
						var descn = result.data[i].descn;
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
					Ext.getCmp("sroleGrid").store.add([dataRecord]);
					}					
			    }
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
        		department
            ]}
	    ]
	});
}
///////////////////////////////////////////////////////// 
//
 function RoleGrid(){//�ڶ����б�ҳ���grid��
 	var sm = new Ext.grid.CheckboxSelectionModel({
            singleSelect: false
    });
	var columns = [
		{
            header:'id',
            hidden : true,           
            dataIndex:'id',
            sortable: true,
            editor: new Ext.form.TextField()
        },
	  	{
            header:'��ɫ����',           
            dataIndex:'name',
            sortable: true,
            editor: new Ext.form.TextField()
        },{
            header:'����',           
            dataIndex:'descn',
            sortable: true,
            editor: new Ext.form.TextField()
        },sm
	];
	this.topbar = getTopbar();			
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
	this.grid =  new Ext.grid.GridPanel({
			id:'sroleGrid',
	  		store: roleStore,
	  		//title:'<font color=red> ����һ��ѡ��һ����ɫ����Ϊһ����ɫֻ��Ӧһ����¼</font>',
	  		cm: roleCM,  
	  		sm: sm,
	  		trackMouseOver:false,    
	        loadMask: true,
	        height:'auto',
	        autoScroll:true,
	        y:40,
	        anchor: '-7 -60',
	        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
	        tbar : this.topbar
    		
	});		
	this.grid.store.removeAll();
	
 }
 //***************************************************************************
 //�û���Ϣ�޸ı�
 function UserDetailEditForm(userId,department){
 	//alert(id+"=="+department);
 	this.definitonName =  new Ext.form.TextField({
 			 id:'definitonName',
             xtype: 'textfield',
             name: 'definitonName',
             readOnly : true,
             width:150
	});
	
	this.nodeName =  new Ext.form.TextField({
			id:'nodeName',
			xtype: 'textfield',
            name: 'nodeName',
            readOnly : true,
            width:150
	});		
	
	this.nodeDesc =  new Ext.form.TextField({
			id:'nodeDesc',
			xtype: 'textfield',
            name: 'nodeDesc',
            readOnly : true,
            width:150
	});		
	var codes = [['0','���������'], ['1','��һ������'],['2','�Զ�������']];
	var stores = new Ext.data.SimpleStore({
		fields : ['id','type'],
		data : codes
	});
	this.type= new Ext.form.ComboBox({
		       id:"roleType",
				name : 'typeName',
				store : stores,
				triggerAction : 'all',
				displayField : 'type',
				valueField :'id',
				mode : 'local',
				width : 200
	});	

	this.id = new Ext.form.Hidden({xtype:'hidden',name:'id'});
	
 	this.form = new Ext.form.FormPanel({
 		id:'modifyForm',
	    buttonAlign:'center',  
	    labelAlign:'right',    
	    labelWidth:60,
	    autoWidth: true,
	    height: "auto",
	    frame:true, 
	    reader: new Ext.data.JsonReader({
	    		root: 'list',
                successProperty: '@success'
	    },[{
              name: 'definitonName',
              mapping: 'definitonName'  
            },{
              name: 'nodeName',
              mapping: 'nodeName'
            },{
              name: 'nodeDesc',
              mapping: 'nodeDesc'
           },{
              name: 'typeName',
              mapping: 'typeName'
           }
        ]),
	     items:[
	    {
    		layout: 'table', 
    		height: 50,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
    			{html: "��������:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
    			this.definitonName,
        		{html: "�ڵ�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.nodeName,
        		{html: "�ڵ�����:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.nodeDesc
        	]
        },
	    {
    		layout: 'table', 
    		height: 50,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		 {html: "��ɫ���ͣ�&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        			this.type
            ]
       }            	
	    ]
	});
	this.form.load({//��������form��������
			 url: webContext+'/configUnitRole_queryProNameAndnodeName.action?nodeId='+nodeId+'&virtualDefinitionInfoId='+virtualDefinitionInfoId+'&tableId='+userId,
			 timeout: 3000			 
	});
 }
 function getTopbarEdit() {
		return ['   ', {
			text : '���',
			pressed : true,
			handler : new ConfigUnitRoleListPanel().checkRolEdit,
			scope : this,
			iconCls : 'add'
		}, '  || ', {
			text : ' ɾ��',
			pressed : true,
				handler : function() {
					var record = Ext.getCmp("roleEditGrid").getSelectionModel().getSelected();
					var records = Ext.getCmp("roleEditGrid").getSelectionModel().getSelections();
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
										Ext.getCmp("roleEditGrid").store.remove(records[i]);
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
 //�޸��û�,��ɫ�б�Grid
 function RoleListEditGrid(userId){
 	
 	//������н�ɫ�б�
	
	var conn = Ext.lib.Ajax.getConnectionObject().conn;
    conn.open("POST", webContext+'/configUnitRole_modifyRoleMessage.action?id='+userId, false);
    conn.send(null);
    var data = Ext.util.JSON.decode(conn.responseText);
	
	this.store = new Ext.data.JsonStore({
        root: 'data',
        data: data,
        fields: ['name', 'descn', 'id'],
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
	        y:100,
	        tbar : this.topbar,
    		anchor: '0 -0'
	});	
 }