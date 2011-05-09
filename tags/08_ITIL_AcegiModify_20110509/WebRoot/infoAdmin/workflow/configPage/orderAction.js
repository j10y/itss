OrderAction = Ext.extend(Ext.util.Observable, {
	
	getJsonStore : function(clazz) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
			if (data[i].dataIndex == 'lineNumber') {
				fields[i] = {
					name : data[i].dataIndex,
					type : 'int'
				}
			}
			
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+ '/workflow/processconfig.do?methodCall=getDefinitionInfos&&clazz='
					+ clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},

	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			responseText = clearReturn(responseText);
			var data = eval("(" + responseText + ")");
			return data;
		} else {
			return 'no result';
		}
	},
	ajaxGetDataExt : function(url,param,successFn,failureFn) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		Ext.Ajax.request({
			url:url,
			params:param,
			success:successFn,
			failure:failureFn
		});
	},
	//查询订单确认的行项目 clazz=ViewConfirmOrderTable
	getConfirmItemStore:function(clazz){
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
			if (data[i].dataIndex == 'lineNumber') {
				fields[i] = {
					name : data[i].dataIndex,
					type : 'int'
				}
			}
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+ '/cmccb2b/order/CreateOrder.do?methodCall=query3A4RItem&clazz=' + clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000

		});
		return store;
	},
	
	
	// 查询改单确认的行项目
	getModifyItemStore : function(clazz) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+ '/cmccb2b/order/ModifyOrder.do?methodCall=query3A8CItem',
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000

		});
		return store;

	},

	// 查询创建订单Store
	getCreateStore : function(clazz) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+ '/cmccb2b/order/CreateOrder.do?methodCall=query3A4R',
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000

		});
		return store;

	},
	// 查询改单订单store
	getModifyStore : function(clazz) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+ '/cmccb2b/order/ModifyOrder.do?methodCall=query3A8R',
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000

		});
		return store;

	},

	
	// 得到actionConfigUnit的数据
	getUpdateStore : function(clazz, processId,nodeId) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/processconfig.do?methodCall=getActionConfigUnit&processId='
				    + processId + '&nodeId=' + nodeId,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	// 得到PageModelConfigUnit的数据
	getPageModelConfigUnitStore : function(clazz, processId,nodeId) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/processconfig.do?methodCall=getPageModelConfigUnit&processId='
				    + processId + '&nodeId=' + nodeId+"&clazz="+clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	// 得到RuleConfigUnit的数据
	getRuleConfigUnitStore : function(clazz, processId,nodeId,processName) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/processconfig.do?methodCall=getRuleConfigUnit&processId='
				    + processId + '&nodeId=' + nodeId+"&clazz="+clazz+"&processName="+processName,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	//得到角色下拉列表数据
	getRoleStore : function(processId,nodeId) {
		
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/processconfig.do?methodCall=getSystemRole&processId='
				    + processId+'&nodeId='+nodeId ,
			root : "data",
			fields : ['id','name'],
			remoteSort : false,
			timeout : 8000
			
				// sortInfo:{field:'id',direction:'ASC'}
		});
		
		return store;
	},
	
	//得到pageModel下拉列表数据
	getPageModelStore : function(processId,nodeId) {
		
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/processconfig.do?methodCall=getAllPageModel&processId='
				    + processId+'&nodeId='+nodeId ,
			root : "data",
			fields : ['id','name'],
			remoteSort : false,
			timeout : 8000
			
				// sortInfo:{field:'id',direction:'ASC'}
		});
		
		return store;
	},

	getData : function(clazz, dataId) {
		var url = webContext
				+ '/cmccb2b/order/UpdateOrder.do?method=getInfo&&dataId='
				+ dataId + '&&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		// alert(Ext.encode(data));
		return data
	},
	
	//保存actionConfigUnit界面中配置的信息
	saveActionConfigUnitRecord : function(info,processId,nodeId) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=saveActionConfigUnitRecord&';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('提示','保存成功',function(){
					window.location =  'actionConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId;
				});
			}
			,function(){
				Ext.alert('提示','保存失败');
			}
		);
		
		return data;
	},
	
	//删除actionConfigUnit界面中的某些记录
	deleteActionConfigUnitRecord : function(removeIds) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=removeActionConfigUnitRecord&&removeIds='
				+ removeIds;
		var data = this.ajaxGetData(url);
		return data;
	},
	
	//保存PageModelConfigUnit界面中配置的信息
    savePageModelConfigUnitRecord : function(info,processId,nodeId,desc,processName) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=savePageModelConfigUnitRecord';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('提示','保存成功',function(){
					window.location =  'pageModelConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
			,function(){
				Ext.alert('提示','保存失败');
			}
		);
		
		return data;
	},
	  
	//保存PageModelConfigUnit界面中配置的信息
    saveRuleConfigUnitRecord : function(info,processId,nodeId,desc) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=saveRuleConfigUnit';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('提示','保存成功',function(){
					window.location =  'ruleConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
			,function(){
				Ext.alert('提示','保存失败');
			}
		);
		
		return data;
	},
	
	//保存PageModelConfigUnit界面中配置的信息
    saveSubProcessConfigUnitRecord : function(info,processId,nodeId,desc) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=saveSubProcessConfigUnit';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			, function(response, options) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				Ext.Msg.alert('提示', '保存成功', function() {
					window.location =  'subProcessConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			} else {
				Ext.Msg.alert('提示', '保存失败,因为子流程名称错误!', function() {
					window.location =  'subProcessConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
		}, function(response, options) {
			var result = Ext.decode(response.responseText);
			if (!result.success) {
				Ext.Msg.alert('提示', '保存失败', function() {
					window.location =  'subProcessConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
				
			}
		);
		
		return data;
	},
	//删除PageModelConfigUnit界面中的某些记录
	deletePageModelConfigUnitRecord : function(removeIds) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=removePageModelConfigUnitRecord&removeIds='
				+ removeIds;
		var data = this.ajaxGetData(url);
		return data;
	}
	,
	//删除RuleConfigUnit界面中的某些记录
	deleteRuleConfigUnitRecord : function(removeIds) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=removeRuleConfigUnitRecord&removeIds='
				+ removeIds;
		var data = this.ajaxGetData(url);
		return data;
	},
    getRuleData : function( processId,nodeId) {
		var url = webContext + '/workflow/processconfig.do?methodCall=getRuleInfo&virtualDefinitionInfoId='
				+ processId +"&nodeId="+nodeId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			//responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			
			return data
		} else {
			return 'no result';
		}	
		// alert(Ext.encode(data));
	},
	getSubProcessData : function( processId,nodeId) {
		var url = webContext + '/workflow/processconfig.do?methodCall=getSubProcessInfo&virtualDefinitionInfoId='
				+ processId +"&nodeId="+nodeId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("get", url, false);
		conn.send(null);
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
			//responseText = clearReturn(responseText);
			var data = Ext.decode(responseText);
			return data
		} else {
			return 'no result';
		}	
		// alert(Ext.encode(data));
	},
	
	getSubProcessStore : function(processId) {
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext+'/workflow/processconfig.do?methodCall=getSubProcessStore&virtualDefinitionInfoId='+processId,
			root : "data",
			fields : ['id','subProcessName'],
			remoteSort : false,
			timeout : 8000
		});
		return store;
	}
,
	getNodeStore : function(taskId) {
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/assginAndAddMark.do?methodCall=getNodeStore&taskId='+taskId,
			root : "data",
			fields : ['id','name','desc'],
			remoteSort : false,
			timeout : 8000
		});
		
		return store;
	},
	
	getUserStore : function() {
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext+'/workflow/assginAndAddMark.do?methodCall=getUserStore',
			root : "data",
			fields : ['id','name'],
			remoteSort : false,
			timeout : 8000
		});
		return store;
	}

});