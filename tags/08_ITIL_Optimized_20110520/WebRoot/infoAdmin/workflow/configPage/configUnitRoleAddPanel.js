//用户详细信息表单
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
	var codes = [['0','需多人审批'], ['1','仅一人审批'], ['2','自定义审批']];
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
				value : '仅一人审批',
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
	    reader: new Ext.data.JsonReader({//这个的作用就相当于把后台发送的type映射到相应的控件type当中了，
					//读取到之后并且把数据放到record当中，和store的加载是一样的
			    		root: 'list',
		                successProperty: '@success'
			    },[{
		              name: 'definitonName',//是对应的record中的hiddenName
		              mapping: 'definitonName'  //对应的是读取之后的record
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
    			{html: "流程名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
    			this.definitonName,
        		{html: "节点名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	        		
        		this.nodeName,
        		{html: "节点描述:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.nodeDesc
        	]
	    },
	    {
    		layout: 'table', 
    		height: 50,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		 {html: "角色类型：&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        			this.type
            ]}               
	    ]
	});	
	this.form.load({//用来加载form表单的数据
			 url: webContext+'/configUnitRole_queryProNameAndnodeName.action?nodeId='+nodeId+'&virtualDefinitionInfoId='+virtualDefinitionInfoId,
			 timeout: 3000			 
	});
	
}
//
function getTopbar() {//位置在下面grid当中
		return ['   ', {
			text : '添加',
			pressed : true,
			handler : new ConfigUnitRoleListPanel().checkRol,
			scope : this,
			iconCls : 'add'
		}, '   ', {
			text : ' 删除',
			pressed : true,
				handler : function() {
					var record = roleListGrid.getSelectionModel().getSelected();
					var records = roleListGrid.getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("提示", "请先选择要删除的行!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					} else {
						Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(
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
		},'<font color=red>【提示：请您一次选择一个角色，因为一个角色只对应一条记录】</font>'];
}


////增加用户,角色列表Grid
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
//            header:'角色名称',          
//            dataIndex:'name',
//            sortable: true,
//            editor: new Ext.form.TextField()
//        },{
//            header:'描述',          
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
//增加用户,角色列表Grid
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
            header:'角色名称',
            width:235,
            dataIndex:'name',
            sortable: true,
            editor: new Ext.form.TextField()
        },{
            header:'描述',
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
			//title:'<font color=red>如果您要修改角色，请先删除存在的角色再增加新角色，因为一个角色对应一条记录</font>',
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
function UserRolForm(){ //第二个列表页面的form表单
	var clazz = "com.zsgj.info.framework.security.entity.Department";
	//隶属部门的可查询的comboBox
    var department = new Ext.form.ComboBox({			
			valueField : "id",
			displayField : "departName",
            emptyText: '请选择隶属部门',
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
				blur:function(combo){//当其失去焦点的时候
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
        		{html: "隶属部门：&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        		department
            ]}
	    ]
	});
}
///////////////////////////////////////////////////////// 
//
 function RoleGrid(){//第二个列表页面的grid表单
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
            header:'角色名称',           
            dataIndex:'name',
            sortable: true,
            editor: new Ext.form.TextField()
        },{
            header:'描述',           
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
	  		//title:'<font color=red> 请您一次选择一个角色，因为一个角色只对应一条记录</font>',
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
 //用户信息修改表单
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
	var codes = [['0','需多人审批'], ['1','仅一人审批'],['2','自定义审批']];
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
    			{html: "流程名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},	
    			this.definitonName,
        		{html: "节点名称:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.nodeName,
        		{html: "节点描述:&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},		        		
        		this.nodeDesc
        	]
        },
	    {
    		layout: 'table', 
    		height: 50,  
    		width:'2000',
    		layoutConfig: {columns: 4},	    		
    		items:[
        		 {html: "角色类型：&nbsp;",cls: 'common-text',style:'width:90;text-align:right'},
        			this.type
            ]
       }            	
	    ]
	});
	this.form.load({//用来加载form表单的数据
			 url: webContext+'/configUnitRole_queryProNameAndnodeName.action?nodeId='+nodeId+'&virtualDefinitionInfoId='+virtualDefinitionInfoId+'&tableId='+userId,
			 timeout: 3000			 
	});
 }
 function getTopbarEdit() {
		return ['   ', {
			text : '添加',
			pressed : true,
			handler : new ConfigUnitRoleListPanel().checkRolEdit,
			scope : this,
			iconCls : 'add'
		}, '  || ', {
			text : ' 删除',
			pressed : true,
				handler : function() {
					var record = Ext.getCmp("roleEditGrid").getSelectionModel().getSelected();
					var records = Ext.getCmp("roleEditGrid").getSelectionModel().getSelections();
					if (!record) {
						Ext.Msg.alert("提示", "请先选择要删除的行!");
						return;
					}
					if (records.length == 0) {
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!');
					} else {
						Ext.MessageBox.confirm('提示框', '您确定要进行该操作？', function(
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
 //修改用户,角色列表Grid
 function RoleListEditGrid(userId){
 	
 	//获得所有角色列表
	
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
            header:'角色名称',
            width:230,
            dataIndex:'name'
        },{
            header:'描述',
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