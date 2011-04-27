WorkFlowAction = Ext.extend(Ext.util.Observable, {

	getElementsForAdd : function(clazz) {
		var url = webContext+ '/workflow/preassign.do?methodCall=forAdd';
		var data = this.ajaxGetData(url);
		return data;
	},

	// ��ñ༭ʱ��Ԫ����
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