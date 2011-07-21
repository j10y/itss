/*
 * Ext JS Library 3.1.1
 * Copyright(c) 2006-2010 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.namespace("Ext.ux.menu");Ext.ux.menu.ListMenu=Ext.extend(Ext.menu.Menu,{labelField:"text",loadingText:"Loading...",loadOnShow:true,single:false,constructor:function(b){this.selected=[];this.addEvents("checkchange");Ext.ux.menu.ListMenu.superclass.constructor.call(this,b=b||{});if(!b.store&&b.options){var c=[];for(var d=0,a=b.options.length;d<a;d++){var e=b.options[d];switch(Ext.type(e)){case"array":c.push(e);break;case"object":c.push([e.id,e[this.labelField]]);break;case"string":c.push([e,e]);break;}}this.store=new Ext.data.Store({reader:new Ext.data.ArrayReader({id:0},["id",this.labelField]),data:c,listeners:{"load":this.onLoad,scope:this}});this.loaded=true;}else{this.add({text:this.loadingText,iconCls:"loading-indicator"});this.store.on("load",this.onLoad,this);}},destroy:function(){if(this.store){this.store.destroy();}Ext.ux.menu.ListMenu.superclass.destroy.call(this);},show:function(){var a=null;return function(){if(arguments.length===0){Ext.ux.menu.ListMenu.superclass.show.apply(this,a);}else{a=arguments;if(this.loadOnShow&&!this.loaded){this.store.load();}Ext.ux.menu.ListMenu.superclass.show.apply(this,arguments);}};}(),onLoad:function(c,b){var g=this.isVisible();this.hide(false);this.removeAll(true);var e=this.single?Ext.id():null;for(var d=0,a=b.length;d<a;d++){var f=new Ext.menu.CheckItem({text:b[d].get(this.labelField),group:e,checked:this.selected.indexOf(b[d].id)>-1,hideOnClick:false});f.itemId=b[d].id;f.on("checkchange",this.checkChange,this);this.add(f);}this.loaded=true;if(g){this.show();}this.fireEvent("load",this,b);},getSelected:function(){return this.selected;},setSelected:function(a){a=this.selected=[].concat(a);if(this.loaded){this.items.each(function(d){d.setChecked(false,true);for(var c=0,b=a.length;c<b;c++){if(d.itemId==a[c]){d.setChecked(true,true);}}},this);}},checkChange:function(b,a){var c=[];this.items.each(function(d){if(d.checked){c.push(d.itemId);}},this);this.selected=c;this.fireEvent("checkchange",b,a);}});