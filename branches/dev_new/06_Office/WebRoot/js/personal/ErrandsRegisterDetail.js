var ErrandsRegisterDetail = function(c) {
	var b = new Ext.Panel({
		autoHeight : true,
		autoWidth : true,
		border : false,
		autoLoad : {
			url : __ctxPath
					+ "/pages/personal/errandsRegisterDetail.jsp?dateId=" + c
		}
	});
	var a = new Ext.Window({
		title : "请假详细信息",
		iconCls : "menu-holiday",
		id : "ErrandsRegisterDetail",
		width : 460,
		height : 280,
		modal : true,
		autoScroll : true,
		layout : "form",
		buttonAlign : "center",
		items : [ b ],
		buttons : [ {
			text : "关闭",
			iconCls : "btn-cancel",
			handler : function() {
				a.close();
			}
		} ]
	});
	a.show();
};