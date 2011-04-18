PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 600,
	align : 'center',
	foredit : true,
	width : 800,
	frame : true,
	autoScroll : true,
	items : this.items,
	buttonAlign: 'center',
	buttons : [{
			text : "返回",
			xtype : 'button',
			scope : 'back',
			iconCls : 'back',
//			style : 'margin:4px 6px 4px 300px',
			handler : function() {
				window.location.href = webContext + "/user/notice/noticeQuery.jsp";
			}

		}],
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var da = new DataAction();
		var data = null;
		data = da.getSingleFormPanelElementsForEdit("page_noticeQueryForm", this.dataId);// 这是要随时变得
		var content = "";
		for(i = 0; i < data.length; i++){
        	if(data[i].id=="NewNotice$content"){
        		content = data[i].value;
        	}
        }
		this.items = [{html:content}],
		PageTemplates.superclass.initComponent.call(this);
	}
})