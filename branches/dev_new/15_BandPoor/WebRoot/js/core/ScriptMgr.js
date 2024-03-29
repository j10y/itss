ScriptLoader = function() {
	this.timeout = 10;
	this.scripts = [];
	this.disableCaching = false;
	this.loadMask = null;
};
ScriptLoader.prototype = {
	showMask : function() {
		if (!this.loadMask) {
			this.loadMask = new Ext.LoadMask(Ext.getBody());
			this.loadMask.show();
		}
	},
	hideMask : function() {
		if (this.loadMask) {
			this.loadMask.hide();
			this.loadMask = null;
		}
	},
	processSuccess : function(response) {
		this.scripts[response.argument.url] = true;
		window.execScript ? window.execScript(response.responseText) : window
				.eval(response.responseText);
		if (typeof response.argument.callback == "function") {
			response.argument.callback.call(response.argument.scope);
		}
	},
	processFailure : function(a) {
		this.hideMask();
		Ext.MessageBox.show({
			title : "应用程序出错",
			msg : "Js脚本库加载出错，服务器可能停止，请联系管理员。",
			closable : false,
			icon : Ext.MessageBox.ERROR,
			minWidth : 200
		});
		setTimeout(function() {
			Ext.MessageBox.hide();
		}, 3000);
	},
	load : function(b, d) {
		var a, c;
		if (typeof b == "object") {
			a = b;
			b = a.url;
			d = d || a.callback;
			c = a.scope;
			if (typeof a.timeout != "undefined") {
				this.timeout = a.timeout;
			}
			if (typeof a.disableCaching != "undefined") {
				this.disableCaching = a.disableCaching;
			}
		}
		if (this.scripts[b]) {
			if (typeof d == "function") {
				d.call(c || window);
			}
			return null;
		}
		this.showMask();
		Ext.Ajax.request({
			url : b,
			success : this.processSuccess,
			failure : this.processFailure,
			scope : this,
			timeout : (this.timeout * 1000),
			disableCaching : this.disableCaching,
			argument : {
				"url" : b,
				"scope" : c || window,
				"callback" : d,
				"options" : a
			}
		});
	}
};
ScriptLoaderMgr = function() {
	this.loader = new ScriptLoader();
	this.load = function(b) {
		if (!Ext.isArray(b.scripts)) {
			b.scripts = [ b.scripts ];
		}
		b.lfiles = 0;
		for ( var a = 0; a < b.scripts.length; a++) {
			b.url = b.scripts[a];
			b.scope = this;
			this.loader.load(b, function() {
				b.lfiles++;
				if (b.lfiles == b.scripts.length) {
					if (b.callback != null) {
						this.loader.hideMask();
						b.callback.call(this);
					}
				}
			});
		}
	};
};
ScriptMgr = new ScriptLoaderMgr();