BaAction = Ext.extend(Ext.util.Observable,{
	//获取个性化需求关联服务项ID
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
	

	// 串行申请服务端数据,不对外
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		
		if (conn.status == "200") {
			var responseText = conn.responseText;
			if(responseText!=null&&responseText.indexOf("<!DOCTYPE ")>=0){
				Ext.MessageBox.alert("提示信息：","下列请求所返回的数据不正确：\n<br>'"+url+"'");
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
			//注意只有这种格式才能够真正的解析json
			var data = eval('('+conn.responseText+')');
			return data;
		} else {
			return 'no result';
		}
	}
});
