/**
 * ------------------------------------------------------------
 * 创建一个通用的工具类，用于帮助其它javascripts的生成
 * -------------------------------------------------------------
 */
Ext.ns('com.faceye.ui');
Ext.ns('com.faceye.ui.util');
Ext.ns('com.faceye.components');
Ext.ns('com.faceye.components.rss');
Ext.ns('com.faceye.compoents.core.security');
Ext.ns('com.faceye.portal');
Ext.ns('com.faceye.portal.portlet');
Ext.ns('com.dc.ui');
// 定义全局路径
var BP = 'http://localhost:80/faceye/';

/**
 * ************************************************************ tree 工具类
 * 
 * *************************************************************
 */
com.faceye.ui.util.Tree = function(el, rootName, rootId, url) {

};
/**
 * 生成分页工具条
 */
com.faceye.ui.util.PaggingToolBar = function(pageSize, store) {
	return new com.faceye.ui.util.PagingToolBar({
		pageSize : pageSize,
		store : store,
		displayInfo : true,
		displayMsg : '当前显示的是 {0} - {1} of {2}',
		emptyMsg : '没有可以显示的结果集'
	});
};

com.faceye.ui.util.PagingToolBar = Ext.extend(Ext.PagingToolbar, {
	initComponent : function() {
		com.faceye.ui.util.PagingToolBar.superclass.initComponent.call(this);
		this.cursor = 0;
		this.bind(this.store);
	},
	onRender : function(ct, position) {
		Ext.PagingToolbar.superclass.onRender.call(this, ct, position);
		this.first = this.addButton({
			tooltip : this.firstText,
			iconCls : "x-tbar-page-first",
			disabled : true,
			handler : this.onClick.createDelegate(this, ["first"])
		});
		this.prev = this.addButton({
			tooltip : this.prevText,
			iconCls : "x-tbar-page-prev",
			disabled : true,
			handler : this.onClick.createDelegate(this, ["prev"])
		});
		// var count = this.store.getCount();
		// alert(count);
		// var pageData=getPageData();
		// alert(pageData.total);
		this.addSeparator();
		this.add(this.beforePageText);
		this.field = Ext.get(this.addDom({
			tag : "input",
			type : "text",
			size : "3",
			value : "1",
			cls : "x-tbar-page-number"
		}).el);
		this.field.on("keydown", this.onPagingKeydown, this);
		this.field.on("focus", function() {
			this.dom.select();
		});
		this.afterTextEl = this.addText(String.format(this.afterPageText, 1));
		this.field.setHeight(18);
		this.addSeparator();
		this.next = this.addButton({
			tooltip : this.nextText,
			iconCls : "x-tbar-page-next",
			disabled : true,
			handler : this.onClick.createDelegate(this, ["next"])
		});
		this.last = this.addButton({
			tooltip : this.lastText,
			iconCls : "x-tbar-page-last",
			disabled : true,
			handler : this.onClick.createDelegate(this, ["last"])
		});
		this.addSeparator();
		this.loading = this.addButton({
			tooltip : this.refreshText,
			iconCls : "x-tbar-loading",
			handler : this.onClick.createDelegate(this, ["refresh"])
		});

		if (this.displayInfo) {
			this.displayEl = Ext.fly(this.el.dom).createChild({
				cls : 'x-paging-info'
			});
		}
		if (this.dsLoaded) {
			this.onLoad.apply(this, this.dsLoaded);
		}
	},
	updateInfo : function() {
		if (this.displayEl) {
			var count = this.store.getCount();
			var msg = count == 0 ? this.emptyMsg : String.format(
					this.displayMsg, this.cursor + 1, this.cursor + count,
					this.store.getTotalCount());
			this.displayEl.update(msg);
		}
	}
});

/**
 * 处理porlet的中文问题，引入资源文件概念
 */
com.faceye.ui.util.ResourceUtil = {
	getOs : function() {
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		if (window.ActiveXObject)
			Sys.ie = ua.match(/msie ([\d.]+)/)[1]
		else if (document.getBoxObjectFor)
			Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1]
//		else if (window.MessageEvent && !document.getBoxObjectFor)
//			Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
		else if (window.opera)
			Sys.opera = ua.match(/opera.([\d.]+)/)[1]
		else if (window.openDatabase)
			Sys.safari = ua.match(/version\/([\d.]+)/)[1];

		// 以下进行测试
		if (Sys.ie)
			return 0;
		if (Sys.firefox)
			return 1;
		if (Sys.chrome)
			return 2;
		if (Sys.opera)
			return 3;
		if (Sys.safari)
			return 4;
	},
	
	readXmlHeader : function(docSrc, nodeKey, valueKey) {
		var xmlHttp;
		var list;
		if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject("microsoft.xmldom");
			xmlHttp.async = false;
			xmlHttp.load(docSrc);
			list = this.getXMLArray(xmlHttp, nodeKey);
		} else {
			xmlHttp = new XMLHttpRequest();
			xmlHttp.open("GET", docSrc, false);
			xmlHttp.send(null);

			list = this.getXMLArrayNotIENotFirefox(xmlHttp.responseXML, nodeKey);
		}

		var headers = new Array();
		for (var i = 0; i < list.length; i++) {
			var obj = list[i];
			headers[i] = this.getvalue(obj, valueKey);
		}

		return headers;
	},	
	getXMLArrayNotIENotFirefox : function(results, name) {
		// var results = xmlHttp.responseXML;
		// var sDiv = "";
		// var shop = "null";
		// var name = "";
		// var address = "";
		// var phone = "";
		// var fax = "";

		var hhitshop = results.getElementsByTagName(name);
		// for (var i = 0; i < hhitshop.length; i++) {
		// shop = hhitshop[i];
		// name = shop.getElementsByTagName("name")[0].firstChild.nodeValue;
		// phone = shop.getElementsByTagName("phone")[0].firstChild.nodeValue;
		// fax = shop.getElementsByTagName("fax")[0].firstChild.nodeValue;
		// address =
		// shop.getElementsByTagName("address")[0].firstChild.nodeValue;
		// sDiv += addDiv(name, photo, address, phone, fax);
		// }
		// document.getElementById("results").innerHTML = sDiv;

		return hhitshop;
	},
	getXMLArray : function(xmlDoc, name) {
		var keys = name.split('.');
		var node = xmlDoc.documentElement; // 得到根节点
		var rtn = new Array();
		var n = 0;

		for (var i = 0; i < keys.length; i++) {
			var childs = node.childNodes; // 得到子节点
			var key = keys[i];
			for (var k = 0; k < childs.length; k++) {
				var child = childs[k];
				if (child.nodeName == key) { // 判断子节点是否符合
					if (i == keys.length - 1) {
						rtn[n] = child;
						n++;
					} else {
						node = child;
						break;
					}
				}
			}
		}
		return rtn;
	},
	getvalue : function(node, name) {
		var keys = name.split('.');
		for (var i = 0; i < keys.length; i++) {
			var childs = node.childNodes; // 得到子节点
			var key = keys[i];
			for (var k = 0; k < childs.length; k++) {
				var child = childs[k];
				if (child.nodeName == key) { // 判断子节点是否符合
					if (child.childNodes.length == 1) {
						// 如果没有字节点,返回值
						if(this.getOs() == 0 || this.getOs() == 3){
							return child.text;
						}else{
							return child.childNodes[0].nodeValue;
						}
					} else {
						// 还有子节点,继续分析
						node = child;
						break;
					}
				}
			}
		}

		return "";
	}
};
