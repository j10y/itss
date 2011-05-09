ProcessAction = Ext.extend(Ext.util.Observable, {

	// ��ò�ѯ��Ԫ����
	getElementsForQuery : function(clazz) {
		var url = webContext + '/cmccb2b/order/commonAction.do?method=query&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		// alert(Ext.encode(data));
		return data;
	},
	
	
	
	getJsonStore : function(clazz) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/workflow/update.do?methodCall=getAllVirtualProcess&&clazz='+clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	//�õ����������б�����
	getProcessStore : function() {
		
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/update.do?methodCall=getProcessDefinitionStore' ,
			root : "data",
			fields : ['id','name','description'],
			remoteSort : false,
			timeout : 8000
			
				// sortInfo:{field:'id',direction:'ASC'}
		});
		
		return store;
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
	
	saveVirtualProcess : function(virtualProcessDesc,department,processType,id) {
		var url = webContext
				+ '/workflow/processconfig.do?methodCall=uploadRuleFile';
		var param = {virtualProcessDesc:unicode(virtualProcessDesc),department:department,processType:processType,id:id};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('��ʾ','����ɹ�',function(){
					window.location =  'listProcess.jsp';
				});
			}
			,function(){
				Ext.alert('��ʾ','����ʧ��');
			}
		);
		
		return data;
	},
	//Modified by Kanglei  add email temlpate (content)  2011/5/9
	saveUpdateVirtualProcess : function(virtualProcessDesc,department,processType,virtualDefinitionInfoId,realDefinitionDesc,content) {
		var url = webContext
				+ '/workflow/update.do?methodCall=saveUpdateVirtualProcess';
		var param = {virtualProcessDesc:unicode(virtualProcessDesc),department:department,processType:processType,virtualDefinitionInfoId:virtualDefinitionInfoId,content:unicode(content)};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('��ʾ','����ɹ�',function(){
//					var conn = Ext.lib.Ajax.getConnectionObject().conn;
//					Ext.Ajax.request({
//						url:webContext+ '/workflow/update.do?methodCall=getSearchVirtualDefinitionInfo',
//						virtualProcessDesc:unicode(virtualProcessDesc)
//					});
				});
			}
			,function(){
				Ext.alert('��ʾ','����ʧ��');
			}
		);
		
		return data;
	},
	//�õ����̽�ɫ�����б�����
	getWorkFlowRoleStore : function(processId,nodeId) {
		
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/update.do?methodCall=getWorkFlowRole',
			root : "data",
			fields : ['id','name'],
			remoteSort : false,
			timeout : 8000
			
				// sortInfo:{field:'id',direction:'ASC'}
		});
		
		return store;
	},
	// �õ�VirtualNodeInfo������
	getVirtualNodeInfoStore : function(clazz,virtualDefinitionInfoId) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext
					+'/workflow/update.do?methodCall=getVirtualNodeInfo&virtualDefinitionInfoId='+virtualDefinitionInfoId+"&clazz="+clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	//����VirtualNodeInfo���������õ���Ϣ
    saveVirtualNodeInfo : function(info,virtualDefinitionInfoId) {
		var url = webContext
				+ '/workflow/update.do?methodCall=saveVirtualNodeInfo';
		var param = {info:unicode(info)};
		var data = this.ajaxGetDataExt(url,param
			,function(){
				Ext.Msg.alert('��ʾ','����ɹ�',function(){
					window.location =  'taskInfo.jsp?virtualDefinitionInfoId='+virtualDefinitionInfoId;
				});
			}
			,function(){
				Ext.alert('��ʾ','����ʧ��');
			}
		);
		
		return data;
	},
	//ɾ��VirtualDefinitionInfo�����е�ĳЩ��¼
	deleteVirtualDefinitionInfo : function(removeIds) {
		var url = webContext
				+ '/workflow/update.do?methodCall=deleteVirtualDefinitionInfo&removeIds='
				+ removeIds;
		var data = this.ajaxGetData(url);
		return data;
	},
	
	
	
	
	
	
	
	
	
	//����ʱ����ñ�����ݣ��õ����Ը��µĶ���
	getJsonStore2 : function(clazz) {
		var url = webContext + '/extjs/metaData?method=head&clazz=' + clazz;
		var data = this.ajaxGetData(url);
		var fields = new Array();
		for (i = 0; i < data.length; i++) {
			fields[i] = data[i].dataIndex;
		}
		var store = new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/cmccb2b/order/UpdateOrder.do?method=getAllOrders&&clazz='+clazz,
			root : "data",
			fields : fields,
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 8000
				// sortInfo:{field:'id',direction:'ASC'}
		});
		return store;
	},
	
	// ����������������,������
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		// alert(conn.responseText)
		if (conn.status == "200") {
			var responseText = conn.responseText;
		//	responseText = clearReturn(responseText);
			var data = eval("(" + responseText + ")");
			return data;
		} else {
			return 'no result';
		}
	}
});