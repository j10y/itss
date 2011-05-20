 var webContext = "";
 var curpath = window.location.pathname;
 var index = curpath.indexOf("/",1);
 	webContext = curpath.substr(0,index);

ServiceAction = Ext.extend(Ext.util.Observable,{
     //获取pageModel数据
     getRootRelationShipInfo:function(dataId){
          var url = webContext+'/sciRelationShip_getRootRelationShipInfo.action?rootCataId='+dataId;
          var data = this.ajaxGetpage(url);
          return data;
     },

        // 串行申请服务端数据,不对外
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			//alert(conn.responseText);
			var responseText = conn.responseText;
			if(responseText!=null&&responseText.indexOf("<!DOCTYPE ")>=0){
				Ext.MessageBox.alert("提示信息：","下列请求所返回的数据不正确：\n<br>'"+url+"'");
				return;
			}
			//alert(responseText);
			responseText = clearReturn(responseText);
			var data = eval("(" + responseText + ")");
			return data;
		} else {
			return 'no result';
		}
	},
     
    ajaxGetpage:function(url){
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