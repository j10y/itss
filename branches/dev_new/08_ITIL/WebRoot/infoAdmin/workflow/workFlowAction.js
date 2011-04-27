WorkFlowAction = Ext.extend(Ext.util.Observable, {

	getElementsForAdd : function(clazz) {
		var url = webContext+ '/workflow/preassign.do?methodCall=forAdd';
		var data = this.ajaxGetData(url);
		return data;
	},

	// 获得编辑时的元数据
	getElementsForEdit : function(clazz, id) {
		var url = webContext
				+ '/workflow/preassign.do?methodCall=forSave&id=' + id;
		var data = this.ajaxGetData(url);
		return data;
	},
	
	ajaxGetData : function(url) {
		var da=new DataAction();
		return da.ajaxGetData(url);
	}
});