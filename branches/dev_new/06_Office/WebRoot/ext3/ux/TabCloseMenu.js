
/*
 * Ext JS Library 3.1.1
 * Copyright(c) 2006-2010 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ux.TabCloseMenu = function () {
	var a, c, b;
	this.init = function (e) {
		a = e;
		a.on("contextmenu", d);
	};
	function d(h, g, i) {
		if (!c) {
			c = new Ext.menu.Menu({items:[{id:a.id + "-close", text:"关闭标签", handler:function () {
				a.remove(b);
			}}, {id:a.id + "-close-others", text:"关闭其他标签", handler:function () {
				a.items.each(function (e) {
					if (e.closable && e != b) {
						a.remove(e);
					}
				});
			}},{
                id: a.id + '-close-all',
                text: '关闭全部标签',
                handler : function(){
                    a.items.each(function(e){
                        if(e.closable){
                            a.remove(e);
                        }
                    });
                }
            }]});
		}
		b = g;
		var f = c.items;
		f.get(a.id + "-close").setDisabled(!g.closable);
		var j = true;
		a.items.each(function () {
			if (this != g && this.closable) {
				j = false;
				return false;
			}
		});
		f.get(a.id + "-close-others").setDisabled(j);
		i.stopEvent();
		c.showAt(i.getPoint());
	}
};
Ext.preg("tabclosemenu", Ext.ux.TabCloseMenu);

