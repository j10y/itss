
TasksGrid = function(actorId, resource){
	//读取xml文件中规定的显示列名
	var header = com.faceye.ui.util.ResourceUtil.readXmlHeader(resource,'header','title');
    var columns = [  
    	 {
        	header: header[0], //和xml文件中的名称依次对应
	        width: 50, 
	        sortable: true,
	        dataIndex: 'dataId',//和store中fields的同名元素对应
	        scope:this,
	        hidden:true
        },{
	        header: header[1], 
	        width: 100, 
	        sortable: true, 
	        dataIndex: 'applyNum'
        },
        {	
	        header: header[2], 
	        width: 120, 
	        sortable: true, 
	        dataIndex: 'applyName'
        },
        {	
	        header: header[3], 
	        width: 120, 
	        sortable: true, 
	        dataIndex: 'applyUser'
        },
        {	
	        header: header[4], 
	        width: 120, 
	        sortable: true, 
	        dataIndex: 'applyDate'
        },
        {	
        	header: header[5], 
	        width: 120, 
	        sortable: true,
	        dataIndex: 'taskName'
        },
        {	
        	header: header[6], 
	        width: 100, 
	        sortable: true,
	        dataIndex: 'taskId',
	        hidden:true
        }
    ];
    TasksGrid.superclass.constructor.call(this, {  	
         store: new Ext.data.JsonStore({
			//获取数据的请求路径
	       	url: webContext+'/configModifyWorkflow_tasks.action?actorId='+actorId,
	       	root:"data",//主数据在响应回写时对应的json对象的属性名称
	  		remoteSort:false,  
	  		timeout:300000,
	  		//和响应回写的json对象中的属性data数组中的每个元素中的每个属性对应
  			fields:['dataId','applyNum','applyName','applyUser','applyDate','taskName','taskId','defdesc','pageUrl'],
  			autoLoad:true
  		}),
  		id:'porlet-ciModifyTasks',
        columns: columns,
        autoScroll:true,
        height:170,
        width:'auto',
        listeners:{
        	rowdblclick:function(){
        		var record = this.getSelectionModel().getSelected();
		    	var applyId = record.get("dataId");	
	        	var applyType = record.get('applyType');
	        	var taskName = record.get('taskName');
	        	var taskId = record.get('taskId');
	        	var pageUrl='/servlet/getPageModel?taskId='+taskId;
	        	//审批窗口
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
		portlet.on("resize",function(c,w,h){
			tasks.setSize(w - 4,180);
		});
		return portlet;
	}
};
function reload() {
	var porletContent = Ext.getCmp('porlet-ciModifyTasks');
	porletContent.getStore().reload();
}