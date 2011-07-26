Ext.namespace("Ext.ux");

Ext.ux.ToastWindowMgr = {
    positions: [] 
};

Ext.ux.ToastWindow = Ext.extend(Ext.Window, {
    initComponent: function(){
          Ext.apply(this, {
              iconCls: this.iconCls || 'information',
            width: 250,
            height: 150,
            autoScroll: true,
            autoDestroy: true,
            plain: false,
            shadow:false
          });
        this.task = new Ext.util.DelayedTask(this.hide, this);
        Ext.ux.ToastWindow.superclass.initComponent.call(this);
    },
    setMessage: function(msg){
        this.body.update(msg);
    },
    setTitle: function(title, iconCls){
        Ext.ux.ToastWindow.superclass.setTitle.call(this, title, iconCls||this.iconCls);
    },
    onRender:function(ct, position) {
        Ext.ux.ToastWindow.superclass.onRender.call(this, ct, position);
    },
    onDestroy: function(){
        Ext.ux.ToastWindowMgr.positions.remove(this.pos);
        Ext.ux.ToastWindow.superclass.onDestroy.call(this);
    },
    afterShow: function(){
        Ext.ux.ToastWindow.superclass.afterShow.call(this);
        this.on('move', function(){
               Ext.ux.ToastWindowMgr.positions.remove(this.pos);
            this.task.cancel();}
        , this);
        this.task.delay(4000);
    },
    animShow: function(){
        this.pos = 0;
        while(Ext.ux.ToastWindowMgr.positions.indexOf(this.pos)>-1)
            this.pos++;
        Ext.ux.ToastWindowMgr.positions.push(this.pos);
        this.setSize(250,150);
        this.el.alignTo(document, "br-br", [ -20, -20-((this.getSize().height+10)*this.pos) ]);
        this.el.slideIn('b', {
            duration: 2,
            callback: this.afterShow,
            scope: this
        });    
    },
    animHide: function(){
           Ext.ux.ToastWindowMgr.positions.remove(this.pos);
        this.el.ghost("b", {
            duration: 2,
            remove: true,
         scope: this,
         callback: this.destroy
        });    
    }
}); 


/*Ext.onReady(function(){
 new Ext.ux.ToastWindow({
  title: '提示窗口',
  html: '测试信息',
  iconCls: 'error'
}).show(document);
})*/

Ext.ux.Toast = function() {
	var b;
	function a(c, d) {
		return [
				'<div class="msg">',
				'<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
				'<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>',
				c,
				"</h3>",
				d,
				"</div></div></div>",
				'<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
				"</div>" ].join("");
	}
	return {
		msg : function(f, e) {
//			if (!b) {
//				b = Ext.DomHelper.insertFirst(document.body, {
//					id : "msg-div",
//					style : "position:absolute;z-index:10000"
//				}, true);
//			}
//			var d = String.format.apply(String, Array.prototype.slice.call(
//					arguments, 1));
//			var c = Ext.DomHelper.append(b, {
//				html : a(f, d)
//			}, true);
//			b.alignTo(document, "b-b");
//			c.shift("t").pause(2.5).ghost("t", {
//				remove : true
//			});
			var a = new Ext.ux.ToastWindow({
				  title: f,
				  html: e,
				  iconCls: 'info'
				}).show(document);
			
		}
	};
}();