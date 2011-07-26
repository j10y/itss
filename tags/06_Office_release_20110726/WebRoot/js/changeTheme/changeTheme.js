var ChangeTheme = new Ext.form.ComboBox({
	id : "ChangeTheme",
	xtype : "combo",
	mode : "local",
	editable : false,
	value : "切换皮肤",
	width : 100,
	triggerAction : "all",
	store : [
			[ "ext-all", "缺省浅蓝" ],
			[ "ext-all-css04", "银白主题" ],
			[ "ext-all-css05", "绿色主题" ],
			[ "ext-all-css03", "粉红主题" ],
			[ "xtheme-tp", "灰色主题" ],
			[ "xtheme-default2", "紫色主题" ],
			[ "xtheme-black", "黑色主题" ],
			[ "xtheme-slate", "VISTA主题" ],
			[ "xtheme-chocolate", "巧克力色主题" ],
			[ "xtheme-access", "Access风格" ] ],
	listeners : {
		scope : this,
		"select" : function(d, b, c) {
			if (d.value != "") {
				var a = new Date();
				a
						.setDate(a
								.getDate() + 300);
				setCookie("theme",
						d.value, a,
						__ctxPath);
				Ext.util.CSS
						.swapStyleSheet(
								"theme",
								__ctxPath
										+ "/ext3/resources/css/"
										+ d.value
										+ ".css");
			}
		}
	}
});