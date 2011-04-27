 
// 新建角色表单
function RoleDetailForm() {

	this.name = new Ext.form.TextField({
		fieldLabel : '角色名称',
		name : 'name',
		width : 360
	});
	this.descn = new Ext.form.TextArea({
		fieldLabel : '描述',
		name : 'descn',
		width : 360
	});

	this.dataView = new Ext.form.ComboBox({
		xtype : 'combo',
		mode : 'local',
		fieldLabel : '数据查看范围',
		name : 'dataView',
		hiddenName : 'dataView',
		store : new Ext.data.SimpleStore({
			fields : ["id", "name"],
			data : [[0, '查看自己'], [1, '查看全部']]
		}),
		emptyText : '请选择...',
		valueField : "id",
		displayField : "name",
		forceSelection : true,
		editable : false,
		triggerAction : 'all',
		width : 360
	});
	// //////////////////////////////////////////////////////////
//modify by oucy
//	this.department = new Ext.form.ComboBox({
//		store : new Ext.data.JsonStore({
//			url : webContext
//					+ '/sysManage/userRoleAction.do?methodCall=findAllDept',
//			fields : ['id', 'name'],
//			root : 'data',
//			sortInfo : {
//				field : "id",
//				direction : "ASC"
//			}
//		}),
//		fieldLabel : '隶属部门',
//		valueField : "id",
//		displayField : "name",
//		emptyText : '请选择隶属部门',
//		mode : 'remote',
//		forceSelection : true,
//		hiddenName : "department",
//		editable : false,
//		triggerAction : 'all',
//		lazyRender : true,
//		typeAhead : true,
//		allowBlank : true,
//		name : "deptMenuTemplate",
//		selectOnFocus : true,
//		width : 360,
//		listeners : {
//			select : function(combo, record, index) {
//				var mt = Ext.getCmp('sys_role_menutpl');
//
//				mt.store.reload({
//					url : webContext
//							+ '/sysManage/roleAction.do?methodCall=findDeptTemplateByDept',
//					params : {
//						department : combo.value
//					}
//				});
//			}
//		}
//	});
	this.department = new Ext.form.ComboBox({
		store:new Ext.data.JsonStore({
			url:webContext+'/sysManage/userRoleAction.do?methodCall=findDeptForCombo',
			fields:['id','departName'],
			totalProperty:'rowCount',
			root:'data',
			id:'id',
			listeners:{
				beforeload:function(store,opt){
					if(opt.params['departName']==undefined){
						opt.params['departName']=Ext.getCmp('deptMenuTemplate').unicode(Ext.getCmp('deptMenuTemplate').defaultParam);
					}
				}
			}
		}),
		id : 'deptMenuTemplate',
		fieldLabel : '隶属部门',
		valueField : "id",
		defaultParam : '',
		displayField : "departName",
		emptyText : '请选择隶属部门',
		mode : 'remote',
		hiddenName : "department",
		triggerAction : 'all',
		lazyRender : true,
		allowBlank:false,
		name : "deptMenuTemplate",
		width : 360,
		pageSize:10,
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
		unicode:function(s) {
		    var len = s.length;
		    var rs = "";
		    for (var i = 0; i < len; i++) {
		        var k = s.substring(i, i + 1);
		        rs += "&#" + s.charCodeAt(i) + ";";
		    }
		    return rs;
		},
		listeners : {
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
						Ext.getCmp('deptMenuTemplate').validate();
					}
				});
				return true;
			},
			select : function(combo, record, index) {
				var mt = Ext.getCmp('sys_role_menutpl');

				mt.store.reload({
					url : webContext
							+ '/sysManage/roleAction.do?methodCall=findDeptTemplateByDept',
					params : {
						department : combo.value
					}
				});
			}
		}
	});
	// //////////////////////////////////////////////////////////

	this.menuTemplate = new Ext.form.ComboBox({
		id : 'sys_role_menutpl',
		fieldLabel : '可用菜单模板',
		valueField : "id",
		displayField : "templateName",
		emptyText : '请选择可用菜单模板',
		mode : 'local',
		forceSelection : true,
		hiddenName : "deptmt",
		editable : false,
		triggerAction : 'all',
		lazyRender : false,
		typeAhead : true,
		allowBlank : true,
		name : "deptMT",
		selectOnFocus : true,
		width : 360,
		store : new Ext.data.JsonStore({
			url : webContext
					+ '/sysManage/roleAction.do?methodCall=findDeptTemplateByDept',
			fields : ['id', 'templateName'],
			root : 'data',
			sortInfo : {
				field : "id",
				direction : "ASC"
			}
		})

	});

	var fromdata = [];
	var da = new DataAction();
	var url = webContext
			+ "/workflow/preassign.do?methodCall=getAllWorkflowRole";
	fromdata = da.ajaxGetData(url);

	this.workFlowRoles = {
		xtype : "itemselector",
		name : "workFlowRoles",
		dataFields : ["code", "desc"],
		fromData : fromdata,
		toData : [],
		msWidth : 170,
		style : 'margin:0;',
		autoScroll : true,
		msHeight : 100,
		valueField : "code",
		displayField : "desc",
		// imagePath:"ext-ux/multiselect",
		fieldLabel : '流程角色',
		toLegend : "已选角色",
		fromLegend : "可选角色"
	}

	this.form = new Ext.form.FormPanel({
		buttonAlign : 'center',
		labelAlign : 'right',
		labelWidth : 80,
		height : 431,
		frame : true,
		items : [this.name, this.descn, this.department, this.menuTemplate,
				this.dataView, this.workFlowRoles]
	});

	this.name.on("blur", function(field) {
		var value = field.getValue();
		if (value.length == 0) {
			field.markInvalid("角色名称不能为空");
			showVailed('角色名称不能为空');
		}
	});
}

// 角色修改表单
function RoleDetailEditForm(roleId, department, deptmt) {

	this.name = new Ext.form.TextField({
		fieldLabel : '角色名称',
		name : 'name',
		width : 360
	});
	this.descn = new Ext.form.TextArea({
		fieldLabel : '描述',
		name : 'descn',
		width : 360
	});
	this.id = new Ext.form.Hidden({
		xtype : 'hidden',
		name : 'id',
		value : roleId
	});

	this.dataView = new Ext.form.ComboBox({
		xtype : 'combo',
		mode : 'local',
		fieldLabel : '数据查看范围',
		name : 'dataView',
		hiddenName : 'dataView',
		store : new Ext.data.SimpleStore({
			fields : ["id", "name"],
			data : [[0, '查看自己'], [1, '查看全部']]
		}),
		emptyText : '请选择...',
		valueField : "id",
		displayField : "name",
		triggerAction : 'all',
		editable : false,
		width : 360
	});
//modify by oucy
//	this.department = new Ext.form.ComboBox({
//		store : new Ext.data.JsonStore({
//			url : webContext
//					+ '/sysManage/userRoleAction.do?methodCall=findAllDept',
//			fields : ['id', 'name'],
//			root : 'data',
//			sortInfo : {
//				field : "id",
//				direction : "ASC"
//			}
//		}),
//		valueField : "id",
//		displayField : "name",
//		fieldLabel : '隶属部门',
//		emptyText : department,
//		mode : 'remote',
//		forceSelection : true,
//		hiddenName : "department",
//		editable : true,
//		triggerAction : 'all',
//		lazyRender : false,
//		typeAhead : true,
//		allowBlank : true,
//		name : "name",
//		minChars : 1,
//		selectOnFocus : true,
//		hideTrigger : false,
//		width : 360,
//		listeners : {
//			select : function(combo, record, index) {
//				var id = record.get('id');
//				var mt = Ext.getCmp('sys_role_menutpl');
//
//				mt.store.reload({
//					url : webContext
//							+ '/sysManage/roleAction.do?methodCall=findDeptTemplateByDept',
//					params : {
//						department : combo.value
//					}
//				});
//			}
//		}
//	});
	var departmentCombo = new Ext.form.ComboBox({
		store:new Ext.data.JsonStore({
			url:webContext+'/sysManage/userRoleAction.do?methodCall=findDeptForCombo',
			fields:['id','departName'],
			totalProperty:'rowCount',
			root:'data',
			id:'id',
			listeners:{
				beforeload:function(store,opt){
					if(opt.params['departName']==undefined){
						opt.params['departName']=departmentCombo.unicode(departmentCombo.defaultParam);
					}
				}
			}
		}),
		pageSize:10,
		valueField : "id",
		displayField : "departName",
		fieldLabel : '隶属部门',
		emptyText : '请选择部门',
		mode : 'remote',
		forceSelection : true,
		hiddenName : "department",
		editable : true,
		triggerAction : 'all',
		lazyRender : false,
		allowBlank : false,
		name : "name",
		minChars : 1,
		selectOnFocus : true,
		hideTrigger : false,
		width : 360,
		listeners : {
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
						departmentCombo.validate();
					}
				});
				return true;
			},
			select : function(combo, record, index) {
				var id = record.get('id');
				var mt = Ext.getCmp('sys_role_menutpl');

				mt.store.reload({
					url : webContext
							+ '/sysManage/roleAction.do?methodCall=findDeptTemplateByDept',
					params : {
						department : combo.value
					}
				});
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
			departName = this.unicode(department);
			this.store.load({
				params:{isLoad:'true',departName:departName,start:0},
				callback:function(r, options, success){
					var depId = r[0].data.id;
					departmentCombo.setValue(depId);
				}
			});
		}
	});
	this.department = departmentCombo;	

	this.menuTemplate = new Ext.form.ComboBox({
		id : 'sys_role_menutpl',
		fieldLabel : '可用菜单模板',
		valueField : "id",
		displayField : "templateName",
		//emptyText : deptmt,
		mode : 'local',
		forceSelection : true,
		hiddenName : "deptmt",
		editable : false,
		triggerAction : 'all',
		lazyRender : true,
		typeAhead : true,
		allowBlank : true,
		name : "deptmt",
		selectOnFocus : true,
		width : 360,
		store : new Ext.data.JsonStore({
			url : webContext
					+ '/sysManage/roleAction.do?methodCall=findDeptTemplateByRoleID&roleId='
					+ (roleId),
			fields : ['id', 'templateName'],
			root : 'data',
			sortInfo : {
				field : "id",
				direction : "ASC"
			}
		})

	});

	var da = new DataAction();
	var url = webContext
			+ "/workflow/preassign.do?methodCall=getWorkflowRoleByRole&id="
			+ roleId;
	var data = da.ajaxGetData(url);
	var fromdata = data.rest;
	var todata = data.selected;

	this.workFlowRoles = {
		xtype : "itemselector",
		name : "workFlowRoles",
		dataFields : ["code", "desc"],
		fromData : fromdata,
		toData : todata,
		msWidth : 170,
		style : 'margin:0;',
		autoScroll : true,
		msHeight : 100,
		valueField : "code",
		displayField : "desc",
		// imagePath:"ext-ux/multiselect",
		fieldLabel : '流程角色',
		toLegend : "已选角色",
		fromLegend : "可选角色"
	}

	this.form = new Ext.form.FormPanel({
		buttonAlign : 'center',
		labelAlign : 'right',
		labelWidth : 80,
		height : 431,
		frame : true,
		reader : new Ext.data.JsonReader({
			root : 'role',
			successProperty : '@success'
		}, [{
			name : 'name',
			mapping : 'name'
		}, {
			name : 'dataView',
			mapping : 'dataView'
		}, {
			name : 'department',
			mapping : 'department'
		}, {
			name : 'deptmt',
			mapping : 'deptmt'
		}, {
			name : 'descn',
			mapping : 'descn'
		}, {
			name : 'id',
			mapping : 'id'
		}]),
		items : [this.name, this.descn, this.department, this.menuTemplate,
				this.dataView, this.workFlowRoles, this.id]
	});

	var mt = Ext.getCmp('sys_role_menutpl');
	mt.store.reload({
		url : webContext
				+ '/sysManage/roleAction.do?methodCall=findDeptTemplateByRoleID&roleId='
					+ (roleId)
	});

	this.form.load({
		url : webContext + '/sysManage/roleAction.do?methodCall=findRole&id='
				+ roleId,
		timeout : 3000000
//modify by oucy 增加了回调方法
		,success:function(){
			departmentCombo.initLoad();
		}
	});

	this.name.on("blur", function(field) {
		var value = field.getValue();
		if (value.length == 0) {
			field.markInvalid("角色名称不能为空");
			showVailed('角色名称不能为空');
		}
	});
}

// 增加角色,权限列表
function RightListGrid() {
	var conn = Ext.lib.Ajax.getConnectionObject().conn;
	conn.open("POST", webContext
			+ '/sysManage/authorizAction.do?methodCall=listAuthorizations',
			false);
	conn.send(null);
	var data = Ext.util.JSON.decode(conn.responseText);
	// fields中的id为authId
	var store = new Ext.data.JsonStore({
		root : 'authorizations',
		data : data,
		fields : ['moduleName', 'name', 'id'],
		autoLoad : true
	});

	var sm = new Ext.grid.CheckboxSelectionModel();
	var columns = [
	//{
	//	header : '模块',
	//	width : 205,
	//	dataIndex : 'moduleName'
	//}, 
	{
		header : '权限名称',
		width : 205,
		dataIndex : 'name'
	}, {
		dataIndex : "id",
		name : 'authId',
		hidden : true,
		width : 60
	}, sm];
	var cm = new Ext.grid.ColumnModel(columns);
	this.grid = new Ext.grid.GridPanel({
		store : store,
		cm : cm,
		sm : sm,
		trackMouseOver : false,
		loadMask : true,
		height : 'auto',
		autoScroll : true,
		y : 280,
		anchor : '0 -280'
	});
          return this.grid;
}

// 修改角色,权限列表
function EditRightListGrid(roleId) {

	var conn = Ext.lib.Ajax.getConnectionObject().conn;
	conn.open("POST", webContext
			+ '/sysManage/roleAction.do?methodCall=findAuthsByRoleId&id='
			+ roleId, false);
	conn.send(null);
	var data = Ext.util.JSON.decode(conn.responseText);

	this.store = new Ext.data.JsonStore({
		root : 'auths',
		data : data,
		fields : ['name', 'checked', 'id'], //'moduleName', 
		autoLoad : true
	});

	this.sm = new Ext.grid.SmartCheckboxSelectionModel({
		dataIndex : 'checked'
	})

	var columns = [
	//{
	///	header : '模块',
	//	width : 200,
	//	dataIndex : 'moduleName'
	//}, 
	{
		header : '权限名称',
		width : 400,
		dataIndex : 'name'
	}, {
		hidden : true,
		dataIndex : "id",
		name : 'authId',
		width : 10
	}, this.sm];
	this.cm = new Ext.grid.ColumnModel(columns);
	this.grid = new Ext.grid.GridPanel({
		store : this.store,
		cm : this.cm,
		sm : this.sm,
		trackMouseOver : false,
		loadMask : true,
		height : 'auto',
		autoScroll : true,
		y : 290,
		anchor : '0 -300'
	});
}
