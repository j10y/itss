Ext.ns("Ext.ux.grid");if("function"!==typeof RegExp.escape){RegExp.escape=function(a){if("string"!==typeof a){return a;}return a.replace(/([.*+?\^=!:${}()|\[\]\/\\])/g,"\\$1");};}Ext.ux.grid.RowActions=function(a){Ext.apply(this,a);this.addEvents("beforeaction","action","beforegroupaction","groupaction");Ext.ux.grid.RowActions.superclass.constructor.call(this);};Ext.extend(Ext.ux.grid.RowActions,Ext.util.Observable,{actionEvent:"click",autoWidth:true,dataIndex:"",header:"",keepSelection:false,menuDisabled:true,sortable:false,tplGroup:'<tpl for="actions">'+'<div class="ux-grow-action-item<tpl if="\'right\'===align"> ux-action-right</tpl> '+'{cls}" style="{style}" qtip="{qtip}">{text}</div>'+"</tpl>",tplRow:'<div class="ux-row-action">'+'<tpl for="actions">'+'<div class="ux-row-action-item {cls} <tpl if="text">'+'ux-row-action-text</tpl>" style="{hide}{style}" qtip="{qtip}">'+'<tpl if="text"><span qtip="{qtip}">{text}</span></tpl></div>'+"</tpl>"+"</div>",hideMode:"visiblity",widthIntercept:4,widthSlope:21,init:function(c){this.grid=c;if(!this.tpl){this.tpl=this.processActions(this.actions);}if(this.autoWidth){this.width=this.widthSlope*this.actions.length+this.widthIntercept;this.fixed=true;}var b=c.getView();var a={scope:this};a[this.actionEvent]=this.onClick;c.afterRender=c.afterRender.createSequence(function(){b.mainBody.on(a);c.on("destroy",this.purgeListeners,this);},this);if(!this.renderer){this.renderer=function(h,d,e,i,g,f){d.css+=(d.css?" ":"")+"ux-row-action-cell";return this.tpl.apply(this.getData(h,d,e,i,g,f));}.createDelegate(this);}if(b.groupTextTpl&&this.groupActions){b.interceptMouse=b.interceptMouse.createInterceptor(function(d){if(d.getTarget(".ux-grow-action-item")){return false;}});b.groupTextTpl='<div class="ux-grow-action-text">'+b.groupTextTpl+"</div>"+this.processActions(this.groupActions,this.tplGroup).apply();}if(true===this.keepSelection){c.processEvent=c.processEvent.createInterceptor(function(d,f){if("mousedown"===d){return !this.getAction(f);}},this);}},getData:function(e,a,b,f,d,c){return b.data||{};},processActions:function(d,c){var a=[];Ext.each(d,function(e,f){if(e.iconCls&&"function"===typeof(e.callback||e.cb)){this.callbacks=this.callbacks||{};this.callbacks[e.iconCls]=e.callback||e.cb;}var g={cls:e.iconIndex?"{"+e.iconIndex+"}":(e.iconCls?e.iconCls:""),qtip:e.qtipIndex?"{"+e.qtipIndex+"}":(e.tooltip||e.qtip?e.tooltip||e.qtip:""),text:e.textIndex?"{"+e.textIndex+"}":(e.text?e.text:""),hide:e.hideIndex?'<tpl if="'+e.hideIndex+'">'+("display"===this.hideMode?"display:none":"visibility:hidden")+";</tpl>":(e.hide?("display"===this.hideMode?"display:none":"visibility:hidden;"):""),align:e.align||"right",style:e.style?e.style:""};a.push(g);},this);var b=new Ext.XTemplate(c||this.tplRow);return new Ext.XTemplate(b.apply({actions:a}));},getAction:function(c){var b=false;var a=c.getTarget(".ux-row-action-item");if(a){b=a.className.replace(/ux-row-action-item /,"");if(b){b=b.replace(/ ux-row-action-text/,"");b=b.trim();}}return b;},onClick:function(g,h){var i=this.grid.getView();var l=g.getTarget(".x-grid3-row");var a=i.findCellIndex(h.parentNode.parentNode);var c=this.getAction(g);if(false!==l&&false!==a&&false!==c){var f=this.grid.store.getAt(l.rowIndex);if(this.callbacks&&"function"===typeof this.callbacks[c]){this.callbacks[c](this.grid,f,c,l.rowIndex,a);}if(true!==this.eventsSuspended&&false===this.fireEvent("beforeaction",this.grid,f,c,l.rowIndex,a)){return;}else{if(true!==this.eventsSuspended){this.fireEvent("action",this.grid,f,c,l.rowIndex,a);}}}t=g.getTarget(".ux-grow-action-item");if(t){var j=i.findGroup(h);var d=j?j.id.replace(/ext-gen[0-9]+-gp-/,""):null;var b;if(d){var k=new RegExp(RegExp.escape(d));b=this.grid.store.queryBy(function(e){return e._groupId.match(k);});b=b?b.items:[];}c=t.className.replace(/ux-grow-action-item (ux-action-right )*/,"");if("function"===typeof this.callbacks[c]){this.callbacks[c](this.grid,b,c,d);}if(true!==this.eventsSuspended&&false===this.fireEvent("beforegroupaction",this.grid,b,c,d)){return false;}this.fireEvent("groupaction",this.grid,b,c,d);}}});Ext.reg("rowactions",Ext.ux.grid.RowActions);