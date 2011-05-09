

NewsGrid = function(resource) {
	var header = com.faceye.ui.util.ResourceUtil.readXmlHeader(resource,
			'header', 'title');
	var columns = [{
		id : 'id',
		header : header[0],
		width : 50,
		sortable : true,
		dataIndex : 'id',
		scope : this,
		hidden : true
	}, {
		id : 'title',
		header : header[1],
		width : 120,
		sortable : true,
		dataIndex : 'title'
	}, {
		id : 'createDate',
		header : header[2],
		width : 150,
		sortable : true,
		dataIndex : 'createDate'
	}, {
		id : 'createUser',
		header : header[3],
		width : 120,
		sortable : true,
		dataIndex : 'createUser'
	}];
	NewsGrid.superclass.constructor.call(this, {
		id : 'porlet-news',
		store : new Ext.data.JsonStore({
			id : Ext.id(),
			url : webContext + '/sysManage/noticeAction.do?methodCall=listNews',
			root : "data",
			totalProperty : "rowCount",
			remoteSort : false,
			timeout : 300000,
			fields : ['id', 'title', 'createDate', 'createUser', 'content','creuser'],
			autoLoad : true
		}),
		columns : columns,
		autoScroll : true,
		height : 180,
		width : 'auto',
		listeners : {
			rowdblclick : function() {
				var record = this.getSelectionModel().getSelected();
				var _window = new NewsWindow(record);
				_window.show();
			}
		},
		scope : this
	});
}
Ext.extend(NewsGrid, Ext.grid.GridPanel);

NewsWindow = function(_record) {
	var _me = this;
	var item = new Array();
	var cuser = _record.get("createUser");
	var creuser = _record.get("creuser");
	item.push(
			{html :"<b><font color='red' size=4>"+_record.get("title")+"</font></b>"+"<br>"+_record.get("createDate")+creuser+cuser,
			style : 'text-align:center',cls: 'common-text',height : 45},
			{html : Ext.decode(_record.get("content"))});
	NewsWindow.superclass.constructor.call(this, {
		//title : '¹«¸æ',
		width : 500,
		height : 300,
		border : true,
		bodyBorder : true,
		autoScroll : true,
		resizable : true,
		plain : true,
		bodyStyle : "padding:5px;",
		buttonAlign : "right",
		maximizable : true,
		items : item
	});
}
Ext.extend(NewsWindow, Ext.Window);

com.faceye.portal.portlet.SinglePortlet = {
	
	init : function(id, name, resource,portalColum) {
		var porletId =  id + '_' + portalColum + "_"  + Ext.id();
		var news = new NewsGrid(resource);
		var me = this;
		var portlet = new Ext.ux.Portlet({
			id :porletId,
			title : name,
			tools : com.faceye.portal.PortletTools.getDefaultPorletTools(id,portalColum,reload)
		});
		portlet.add(news);
		portlet.on("resize",function(c,w,h){
			news.setSize(w - 4,180);
		});
		return portlet;
	}
};

function reload() {
	var porletContent = Ext.getCmp('porlet-news');
	porletContent.getStore().reload();
}
