ReqAccPanel = Ext.extend(Ext.Panel, {
	id : "ReqAccPanel",
	closable : true,
	frame : true,
	autoScroll:true,
	height : 'auto',
	viewConfig : {
		autoFill : true,
		forceFit : true
	},

	layoutConfig : {
		columns : 1
	},
	initComponent : function() {
		//this.exportExcel=this.getExportExcel();
		url= webContext
				+ '/infoAdmin/jfreeChartAction.do?method=findRequire';
		var AccPal=new Ext.Panel({
		    //height:500,
			html : '<iframe id="myframebidhis" frameborder="no" myframebidhis="ifr"width="100%" height="350" scrolling="auto" src='
					+url+ '></iframe>'
		});	
		this.add(AccPal);
		
		
	}
});
