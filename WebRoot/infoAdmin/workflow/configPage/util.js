 var webContext = "";
 var curpath = window.location.pathname;
 var index = curpath.indexOf("/",1);
 	webContext = curpath.substr(0,index);

WorkFlowAction = Ext.extend(Ext.util.Observable,{
	
       // ����������������,������
	ajaxGetData : function(url) {
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null); 
		if (conn.status == "200") {
			//alert(conn.responseText);
			var responseText = conn.responseText;
//			if(responseText!=null&&responseText.indexOf("<!DOCTYPE ")>=0){
//				Ext.MessageBox.alert("��ʾ��Ϣ��","�������������ص����ݲ���ȷ��\n<br>'"+url+"'");
//				return;
//			}
			//alert(responseText);
			//responseText = clearReturn(responseText);
			var data = eval("(" + responseText + ")");
			return data;
		} else {
			return 'no result';
		}
	},
	
	//grid�첽���ò�ѯ����,
    getJsonStore:function(clazz){ //alert("getJsonStore");
          var url = webContext+'/extjs/metaData?method=head&clazz='+clazz;
        var data = this.ajaxGetData(url); 
        var fields = new Array();
          for(i=0;i<data.length;i++){
                    fields[i] = data[i].dataIndex;
          }
          var store = new Ext.data.JsonStore({ 
                              id: Ext.id(),
                    url: webContext+'/extjs/dataAction?method=query&clazz='+clazz,
                    root:"data",
                    fields:fields,
                    totalProperty:"rowCount",
                    remoteSort:false,             
                    timeout:8000
                              //sortInfo:{field:'id',direction:'ASC'}
                    });
                    return store;
    },
     getAllProcessJson:function(){
          var url =  webContext+"/workflow/processconfig.do?methodCall=listAllProcess";
          var data = this.ajaxGetData(url);
          return data;
     },
     getNodeJson:function(processName){
          var url =  webContext+"/workflow/processconfig.do?methodCall=listAllNode&processName="+processName;
          var data = this.ajaxGetData(url);
          return data;
     },
     getProcessJson:function(processName,description){
          var url =  webContext+'/workflow/processconfig.do?methodCall=getProcess&processName='+processName+'&description='+description;
         var data = this.ajaxGetData(url);
          return data;
     }
     
   
})
