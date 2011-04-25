PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	width:800,
	align : 'center',
	foredit : true,
	width : 'auto',
	frame : true,
	autoScroll : true,
	getEventForm: function(eventId) {
		var da = new DataAction();
		var data=da.getPanelElementsForEdit("page_event_suppGroupEngineer","panel_event_suppGroupEngineer",eventId);
		for(var  i=0;i<data.length;i++){
			if(data[i].name=="Event$submitDate"){
				data[i].value=data[i].value.substring(0,19);
			    }
			     if(data[i].xtype=="panel"){
			        data[i].items[0].disabled=true;
					data[i].items[1].html=data[i].items[1].html.replace(/display:/g, "display:none");
          		}
				data[i].readOnly=true;
				data[i].hideTrigger=true;
				data[i].emptyText="";
			}
		var biddata = da.split(data);
		this.eventPanel = new Ext.form.FormPanel({  
			id : 'eventPanel',
			layout : 'table',
			autoScroll:true,
			title:"事件基本信息",
		    height : 350,	
			width : 700,
			frame : true,
			border:true,
//			collapsible : true,
			defaults : {
				bodyStyle : 'padding:6px'
			},
			layoutConfig : {
				columns : 4
			},
			items : biddata
			});
		return this.eventPanel;
	},
	getKnowledgeForm : function(knowledgeId) {
		        var da = new DataAction();
		        var data=da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
		        var konwledgecontext = "";
		        for(i = 0; i < data.length; i++){
		        	if(data[i].id=="Knowledge$resolvent"){
		        		konwledgecontext = data[i].value;
		        	}
		        }
		        this.knowFormPanel = new Ext.form.FormPanel({
		            id:'knowledgeDetails',
			        layout : 'fit',
			        autoScroll:true,
					title:"解决方案详细信息",
				    height : 400,	
					width : 700,
					frame : true, 
			        items : [{html : konwledgecontext}]
		           });
	     	return this.knowFormPanel;

	},
	 items : this.items,
	 initComponent : function() {
	   this.eventPanel=this.getEventForm(this.eventId);
	   this.KnowledgePanel=this.getKnowledgeForm(this.knowledgeId);
	   this.tab  = new Ext.TabPanel({	
			enableTabScroll : true,
			deferredRender : false,
			activeTab:0,
		    frame : true,
			plain : false,
			border : true,
			width : 750,
			height :"auto",
		    bodyBorder : true,
			items : [this.eventPanel,this.KnowledgePanel]
		});
		this.items = [this.tab];
		PageTemplates.superclass.initComponent.call(this);
	 }
})