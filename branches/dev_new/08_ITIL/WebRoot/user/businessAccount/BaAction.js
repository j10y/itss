BaAction = Ext.extend(Ext.util.Observable,{
	//��ȡ���Ի��������������ID
	getReqId:function(dataId){
		var url = webContext+'/updatePlanAction_getReqId.action?dataId='+dataId;
		var data = this.ajaxGetPage(url);
		var siId = data.id;
		return siId;
	},
	getDescn:function(dataId){
		var url = webContext+'/updatePlanAction_getDescn.action?dataId='+dataId;
		var data = this.ajaxGetPage(url);
		var siId = data.descn;
		return siId;
	},
	getIncomeBa:function(dataId){
		var url = webContext+'/businessAccountAction_getIncomeBusinessAccount.action?dataId='+dataId;
		var data = this.ajaxGetPage(url);
		return data;
	},
	getPaymentBa:function(dataId){
		var url = webContext+'/businessAccountAction_getExplandBusinessAccount.action?dataId='+dataId;
		var data = this.ajaxGetPage(url);
		return data;
	},
	

	// ����������������,������
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		
		if (conn.status == "200") {
			var responseText = conn.responseText;
			if(responseText!=null&&responseText.indexOf("<!DOCTYPE ")>=0){
				Ext.MessageBox.alert("��ʾ��Ϣ��","�������������ص����ݲ���ȷ��\n<br>'"+url+"'");
				return;
			}
			if(responseText==""){
			return "";
			}
			responseText = clearReturn(responseText);
			var data = eval("(" + responseText + ")");
			return data;
		} else {
			return 'no result';
		}
	},

	ajaxGetPage:function(url){
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post",url, false);
		conn.send(null);
		if (conn.status == "200") {
			//alert(conn.responseText);
			//ע��ֻ�����ָ�ʽ���ܹ������Ľ���json
			var data = eval('('+conn.responseText+')');
			return data;
		} else {
			return 'no result';
		}
	}
});
