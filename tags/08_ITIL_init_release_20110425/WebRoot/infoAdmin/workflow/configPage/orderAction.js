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
	//��ѯ����ȷ�ϵ�����Ŀ clazz=ViewConfirmOrderTable
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
	
	
	// ��ѯ�ĵ�ȷ�ϵ�����Ŀ
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

	// ��ѯ��������Store
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
	// ��ѯ�ĵ�����store
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

	
	// �õ�actionConfigUnit������
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
	
	// �õ�PageModelConfigUnit������
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
	
	// �õ�RuleConfigUnit������
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
	
	//�õ���ɫ�����б�����
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
	
	//�õ�pageModel�����б�����
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
	
	//����actionConfigUnit���������õ���Ϣ
	saveActionConfigUnitRecord : function(info,processId,nodeId) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=saveActionConfigUnitRecord&';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('��ʾ','����ɹ�',function(){
					window.location =  'actionConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId;
				});
			}
			,function(){
				Ext.alert('��ʾ','����ʧ��');
			}
		);
		
		return data;
	},
	
	//ɾ��actionConfigUnit�����е�ĳЩ��¼
	deleteActionConfigUnitRecord : function(removeIds) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=removeActionConfigUnitRecord&&removeIds='
				+ removeIds;
		var data = this.ajaxGetData(url);
		return data;
	},
	
	//����PageModelConfigUnit���������õ���Ϣ
    savePageModelConfigUnitRecord : function(info,processId,nodeId,desc,processName) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=savePageModelConfigUnitRecord';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('��ʾ','����ɹ�',function(){
					window.location =  'pageModelConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
			,function(){
				Ext.alert('��ʾ','����ʧ��');
			}
		);
		
		return data;
	},
	  
	//����PageModelConfigUnit���������õ���Ϣ
    saveRuleConfigUnitRecord : function(info,processId,nodeId,desc) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=saveRuleConfigUnit';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('��ʾ','����ɹ�',function(){
					window.location =  'ruleConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
			,function(){
				Ext.alert('��ʾ','����ʧ��');
			}
		);
		
		return data;
	},
	
	//����PageModelConfigUnit���������õ���Ϣ
    saveSubProcessConfigUnitRecord : function(info,processId,nodeId,desc) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=saveSubProcessConfigUnit';
		var param = {info:unicode(info),processId:processId,nodeId:nodeId};
		var data = this.ajaxGetDataExt(url,param
			, function(response, options) {
			var result = Ext.decode(response.responseText);
			if (result.success) {
				Ext.Msg.alert('��ʾ', '����ɹ�', function() {
					window.location =  'subProcessConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			} else {
				Ext.Msg.alert('��ʾ', '����ʧ��,��Ϊ���������ƴ���!', function() {
					window.location =  'subProcessConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
		}, function(response, options) {
			var result = Ext.decode(response.responseText);
			if (!result.success) {
				Ext.Msg.alert('��ʾ', '����ʧ��', function() {
					window.location =  'subProcessConfigUnit.jsp?virtualDefinitionInfoId='+processId+"&nodeId="+nodeId+"&desc="+desc;
				});
			}
				
			}
		);
		
		return data;
	},
	//ɾ��PageModelConfigUnit�����е�ĳЩ��¼
	deletePageModelConfigUnitRecord : function(removeIds) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=removePageModelConfigUnitRecord&removeIds='
				+ removeIds;
		var data = this.ajaxGetData(url);
		return data;
	}
	,
	//ɾ��RuleConfigUnit�����е�ĳЩ��¼
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