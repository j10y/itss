PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll : true, 
            //minTabWidth:100,
            //resizeTabs:true,
            title : tabTitle,
			deferredRender : false, 
			frame : true,
			plain : true,
            border : false, 
            //tabPosition:'bottom',
			baseCls : 'x-plain',// 是否设置和背景色同步
			width : 900,
			//bodyBorder : true,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa,panelTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
			title : panelTitle,
			border : false,
            defaults : {
                 bodyStyle : 'padding:5px'
            },
			width :'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	}, 
	
	
 getFormUserEventMessage_pagepanel: function() {
 		var da = new DataAction();
		var data = null;
		// 判断是新增还是修改
		var biddata = "";
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel("UserEventMessage_pagepanel",this);
		data = da.getPanelElementsForLook("page_event_suppGroupLeader", "panel_event_suppGroupLeader", this.dataId);// 这是要随时变得
		biddata = da.split(data);
		if(this.getFormButtons.length != 0){
		this.formUserEventMessage_pagepanel = new Ext.form.FormPanel({
			id : 'UserEventMessage_pagepanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "事件详细信息",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formUserEventMessage_pagepanel = new Ext.form.FormPanel({
			id : 'UserEventMessage_pagepanel',
			layout : 'table',
			height : 'auto',
			width : 800,
			frame : true,
			border : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "事件详细信息",
			items : biddata
			});
		}
		return this.formUserEventMessage_pagepanel;
	},
	
		//用户确认--解决方案
 	getFormUserForLookSulotion_pagepanel : function(eventId) {
   		var da = new DataAction();
	  	var data = null;
		var biddata = "";
		var knowledgeId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url = webContext+'/knowledgeAction_findKnowledgeByEventId.action?eventId='+eventId;
		conn.open("post", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			knowledgeId = data.knowledgeId;
		} 
		if(knowledgeId == 'noknowledge') {
			this.formUserForLookSulotion_pagepanel= new Ext.form.FormPanel({
				id : 'UserForLookSulotion_pagepanel',
				layout : 'table',
				height : 'auto',
				width : 800,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:4px'
				},
				layoutConfig : {
					columns : 4
				},
				title : "解决方案",
				items : [{html : '<font color="red">没有解决方案！</font>'}]//,
				//buttons : this.getFormButtons
			});
			return this.formUserForLookSulotion_pagepanel;
		}else{
			data =da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
			var konwledgecontext = "";
	        for(i = 0; i < data.length; i++){
	        	if(data[i].id=="Knowledge$resolvent"){
	        		konwledgecontext = data[i].value;
	        	}
	        }
			this.formUserForLookSulotion_pagepanel= new Ext.form.FormPanel({
				id : 'UserForLookSulotion_pagepanel',
				title : '解决方案',
				maximizable : true,
				autoScroll : true,
				width : 800,
				height:400,
				frame : true,
				items : [{html : konwledgecontext,autoScroll : true,height:370,width:700}]
			});
			return this.formUserForLookSulotion_pagepanel;
		}
	},
	//用户确认--用户反馈
  	getFeedback : function(eventId, taskId, surveyId){

		return new Ext.Panel({
  			title : '满意度评价',
  			id:"feedback_success",
  			width : 'auto',
  			heigth : 'auto',
  			frame : true,
  			autoScroll : true,
  			autoLoad : {
  				url: webContext + '/eventAction_findQuestions.action?surveyId=' + surveyId + '&eventId=' + eventId ,
  				nocache : true, 
  				scripts :true,
  				text : "页面正在加载中......",
				method : 'post',
				scope : this
  			},
  			viewConfig : {
				autoFill : true,
				forceFit : true
			},
			layout : 'fit'
  		});
	},
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		//查询surveyId
  		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		var url = webContext+'/eventAction_findEventSurvey.action';
		conn.open("get", url, false);
		conn.send(null);
		if (conn.status == "200") {
			var responseText = conn.responseText;
			var data = Ext.decode(responseText);
			this.surveyId = data.surveyId;
		} 
		var isSatisty = this.isExist;
	    var items = new Array();
	    var pa = new Array();
		  this.pa = pa;
		var gd = new Array();
		  this.gd = gd;
		var temp = new Array();
		  this.temp = temp;
		var formname = new Array();
		  this.formname=formname;
		var gridname = new Array();
		  this.gridname=gridname;
		this.model="CC_pageModel";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("CC_pageModel",this);
		if(this.mybuttons!=""){
			this.allbuttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			},
			items : this.mybuttons
			};
		}else{
			this.allbuttons = {
			layout : 'table',
			height : 'auto',
			width : 800,
			style : 'margin:4px 6px 4px 300px',
			align : 'center',
			defaults : {
				bodyStyle : 'padding:4px'
			}
			};
		}
		
		       this.getFormUserEventMessage_pagepanel();
		       this.pa.push(this.formUserEventMessage_pagepanel);
		       this.formname.push("UserEventMessage_pagepanel");
		       temp.push(this.formUserEventMessage_pagepanel);
		       this.getFormUserForLookSulotion_pagepanel(this.dataId);
				this.pa.push(this.formUserForLookSulotion_pagepanel);
				this.formname.push("UserForLookSulotion_pagepanel");
				temp.push(this.formUserForLookSulotion_pagepanel);
				if(isSatisty!='s'){//s代表存在满意度调查
					temp.push(this.getFeedback(this.dataId, null, this.surveyId));
				}
				
          items.push(this.getTabpanel(temp));
		items.push(this.allbuttons);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})