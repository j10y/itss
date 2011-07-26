/*
 * Ext JS Library 3.1.1
 * Copyright(c) 2006-2010 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns("Ext.ux");Ext.ux.TabScrollerMenu=Ext.extend(Object,{pageSize:10,maxText:15,menuPrefixText:"Items",constructor:function(a){a=a||{};Ext.apply(this,a);},init:function(b){Ext.apply(b,this.parentOverrides);b.tabScrollerMenu=this;var a=this;b.on({render:{scope:b,single:true,fn:function(){var c=b.createScrollers.createSequence(a.createPanelsMenu,this);b.createScrollers=c;}}});},createPanelsMenu:function(){var c=this.stripWrap.dom.offsetHeight;var b=this.header.dom.firstChild;Ext.fly(b).applyStyles({right:"18px"});var a=Ext.get(this.strip.dom.parentNode);a.applyStyles({"margin-right":"36px"});var d=this.header.insertFirst({cls:"x-tab-tabmenu-right"});d.setHeight(c);d.addClassOnOver("x-tab-tabmenu-over");d.on("click",this.showTabsMenu,this);this.scrollLeft.show=this.scrollLeft.show.createSequence(function(){d.show();});this.scrollLeft.hide=this.scrollLeft.hide.createSequence(function(){d.hide();});},getPageSize:function(){return this.pageSize;},setPageSize:function(a){this.pageSize=a;},getMaxText:function(){return this.maxText;},setMaxText:function(a){this.maxText=a;},getMenuPrefixText:function(){return this.menuPrefixText;},setMenuPrefixText:function(a){this.menuPrefixText=a;},parentOverrides:{showTabsMenu:function(c){if(this.tabsMenu){this.tabsMenu.destroy();this.un("destroy",this.tabsMenu.destroy,this.tabsMenu);this.tabsMenu=null;}this.tabsMenu=new Ext.menu.Menu();this.on("destroy",this.tabsMenu.destroy,this.tabsMenu);this.generateTabMenuItems();var b=Ext.get(c.getTarget());var a=b.getXY();a[1]+=24;this.tabsMenu.showAt(a);},generateTabMenuItems:function(){var a=this.getActiveTab();var l=this.items.getCount();var g=this.tabScrollerMenu.getPageSize();if(l>g){var d=Math.floor(l/g);var j=l%g;for(var e=0;e<d;e++){var f=(e+1)*g;var b=[];for(var h=0;h<g;h++){index=h+f-g;var k=this.items.get(index);b.push(this.autoGenMenuItem(k));}this.tabsMenu.add({text:this.tabScrollerMenu.getMenuPrefixText()+" "+(f-g+1)+" - "+f,menu:b});}if(j>0){var c=d*g;b=[];for(var e=c;e<l;e++){var k=this.items.get(e);b.push(this.autoGenMenuItem(k));}this.tabsMenu.add({text:this.tabScrollerMenu.menuPrefixText+" "+(c+1)+" - "+(c+b.length),menu:b});}}else{this.items.each(function(i){if(i.id!=a.id&&!i.hidden){b.push(this.autoGenMenuItem(i));}},this);}},autoGenMenuItem:function(b){var a=this.tabScrollerMenu.getMaxText();var c=Ext.util.Format.ellipsis(b.title,a);return{text:c,handler:this.showTabFromMenu,scope:this,disabled:b.disabled,tabToShow:b,iconCls:b.iconCls};},showTabFromMenu:function(a){this.setActiveTab(a.tabToShow);}}});Ext.reg("tabscrollermenu",Ext.ux.TabScrollerMenu);