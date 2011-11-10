CheckEmpProfileHistForm = Ext.extend(Ext.Window, {
	displayPanel : null,
	constructor : function (a) {
		if (a == null) {
			a = {};
		}
		Ext.apply(this, a);
		this.initComponents();
		CheckEmpProfileHistForm.superclass.constructor.call(this, {
			id : "CheckEmpProfileHistFormWin",
			iconCls : "btn-empProfile-pass",
			layout : "form",
			items : [this.displayPanel],
			modal : true,
			autoHeight : true,
			shadow : false,
			y : 10,
			width : 820,
			maximizable : true,
			title : "档案历史信息",
			buttonAlign : "center",
			buttons : this.buttons
		});
	},
	initComponents : function () {
		this.displayPanel = new Ext.Panel({
			id : "CheckEmpProfileHistFormPanel",
			height : 430,
			autoScroll : true,
			border : false,
			autoLoad : {
				url : __ctxPath + "/pages/hrm/CheckEmpProfileHist.jsp?profileHistId=" + this.profileHistId
			}
		});
		this.buttons = [{
			text : "关闭",
			iconCls : "btn-cancel",
			handler : this.cancel.createCallback(this)
		}];
	},
	cancel : function (a) {
		a.close();
	}
});
