/*
 * Ext JS Library 2.0.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */


TasksGrid = function(actorId, resource){
	var header = com.faceye.ui.util.ResourceUtil.readXmlHeader(resource,'header','title');
    var columns = [  
    	 {	
    	    id:'applyId',
        	header: header[0], 
	        width: 50, 
	        sortable: true,
	        dataIndex: 'applyId',
	        scope:this,
	        hidden:true
//	        renderer:  function showUrl(value,metadata,record){
//	        	var applyId = value;//�����ţ����ҵ������
//	        	var applyType = record.get('applyType');//�������
//	        	var taskId = record.get('taskId');//������
//	        	var taskName = record.get('taskName');//�������ƣ���Ϊ��˱���	 
//	        	var param = "'"+taskId+"','"+taskName+"','"+applyType+"','"+applyId+"'";
//          		return "<a href='#' onclick=javascript:showAuditWindow("+param+")>"+applyId+"</a>";
//    		}
        },
        {	id:'applyTypeName',
	        header: header[1], 
	        width: 120, 
	        sortable: true, 
//	        renderer:  function showUrl(value,metadata,record){
//	        	var applyId = value;//�����ţ����ҵ������
//	        	var applyType = record.get('applyType');//�������
//	        	var taskId = record.get('taskId');//������
//	        	var taskName = record.get('taskName');//�������ƣ���Ϊ��˱���	 
//	        	var param = "'"+taskId+"','"+taskName+"','"+applyType+"','"+applyId+"'";
//          		return "<a href='#' onclick=javascript:showAuditWindow("+param+")>"+value+"</a>";
//    		},
	        dataIndex: 'applyTypeName'
        },
        {	header: header[2], 
	        width: 120, 
	        sortable: true,
	        dataIndex: 'taskName'
        },
        {	header: header[3], 
	        width: 100, 
	        sortable: true,
	        //hidden:true,
	        dataIndex: 'taskId'
        },
         {	header: header[5], 
	        width: 100, 
	        sortable: true,
	        //hidden:true,
	        dataIndex: 'defdesc'
        },
        {	header: header[6], 
	        width: 100, 
	        sortable: true,
	        hidden:true,
	        dataIndex: 'pageUrl'
        }
    ];

    TasksGrid.superclass.constructor.call(this, {  	
         store: new Ext.data.JsonStore({
			id:Ext.id(),
	       	url: webContext+'/extjs/workflow?method=tasks&actorId='+actorId,
	       	//��ʾ����������Ϊ���������⣬�ο�����url
	       	//url: webContext+'/ibmb2b/order/test.jsp',
	       	root:"data",
	       	totalProperty:"rowCount",
	  		remoteSort:false,  
	  		timeout:300000,
  			fields:['applyId','applyTypeName','customer','taskName','taskId','defdesc','pageUrl'],
  			autoLoad:true
  		}),
  		id:'porlet-taskGridInMainPanel',
        columns: columns,
        autoScroll:true,
        height:170,
        width:520,
        listeners:{
        	rowdblclick:function(){
        		//alert("werwer");
        		var record = this.getSelectionModel().getSelected();
		    	var applyId = record.get("applyId");		    	
		   		//var applyType = record.get("applyType");
        		//var applyId = value;//�����ţ����ҵ������
	        	var applyType = record.get('applyType');//�������
	        	var tmpTaskName = record.get('taskName');//�ֵֹ����⣬Ҫ��һ�ж����
	        	var taskName = record.get('taskName');//�������ƣ���Ϊ��˱���	
	        	var taskId = record.get('taskId');//������
	        //	var pageUrl = record.get("pageUrl");//ҳ��url
	        	//alert(taskId)
	        	
	        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
	        	//	        	alert(taskId+taskName);
	        	var param = ""+taskId+","+taskName+","+applyType+","+applyId+","+pageUrl;
//	        	alert(param);
          		showAuditWindow(taskId,taskName,applyType,applyId,pageUrl);
        	}
        },
        scope:this
    });
}
Ext.extend(TasksGrid, Ext.grid.GridPanel);

com.faceye.portal.portlet.SinglePortlet = {
	init : function(id, name, resource, portalColum) {
		var porletId =  id + '_' + portalColum;
		var tasks = new TasksGrid(userID, resource);
		var portlet = new Ext.ux.Portlet({
			id : porletId + '_' + Ext.id(),
			title : name,
			autoScroll: true,
			height: 210,
			tools : com.faceye.portal.PortletTools.getDefaultPorletTools(id,portalColum,reload)
		});
		portlet.add(tasks);
			//add by likang for  ֻ��һ��������  2009-8-29 begin
		portlet.on("resize",function(c,w,h){
			tasks.setSize(w - 4,180);
		});
		//add by likang for ֻ��һ�������� 2009-8-29 end
		return portlet;
	}
};
function reload() {
	var porletContent = Ext.getCmp('porlet-taskGridInMainPanel');
	porletContent.getStore().reload();
}