PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	border:true,
	style :"margin 0px",//2010-04-30 modified by huzh for 页面优化
	autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	items : this.items,
	//用户确认--事件详情
	getEventForm: function(eventId) {
		var tempPanel = new Ext.Panel({
        	height:80,
        	width:9999,
        	fieldLabel:"说明",
        	style : 'margin:0px 0px 0px 0px',
        	frame:true,
        	border:true,
        	autoScroll : true,
        	html:"<font size=2 face = '楷体_GB2312'>1、在“解决方案”中，您可以查看到关于您提交问题的解决方法或说明。 <br>  " +
        		 "2、为了帮助我们提升IT服务质量，请您点击该页面上的“满意度评价”对服务过程和IT服务本身进行评价。评价可以多次进行，以最后的评价为准。</font>"
        });
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
		data.push(tempPanel);
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
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 4
			},
			items : biddata
			});
		return this.eventPanel;
	},
	
	//用户确认--解决方案
 	getKnowForm: function(knowledgeId) {
   		var da = new DataAction();
		var	data =da.getSingleFormPanelElementsForEdit("KnowLedgeSolution_pagepanel",knowledgeId);
			var konwledgecontext = "";
	        for(i = 0; i < data.length; i++){
	        	if(data[i].id=="Knowledge$resolvent"){
	        		konwledgecontext = data[i].value;
	        	}
	        }
			this.knowFormPanel= new Ext.form.FormPanel({
				id : 'UserForLookSulotion_pagepanel',
				title : '解决方案',
				maximizable : true,
				autoScroll : true,
				width : 800,
				height:400,
				frame : true,
				items : [{html : konwledgecontext,autoScroll : true,height:370,width:700}]
			});
			return this.knowFormPanel;
//		}
		
	},
	//用户确认--用户反馈
  	getFeedback : function(eventId,surveyId){
		return new Ext.Panel({
  			title : '<font color="red">满意度评价</font>',
  			id:"feedback_success",
  			width : 'auto',
  			heigth : 'auto',
  			frame : true,
  			autoScroll : true,
  			autoLoad : {
  				url: webContext + '/eventAction_findQuestions.action?surveyId=' + surveyId + '&eventId=' + eventId,
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
		var items = new Array();
		var temp = new Array();
		this.temp = temp;
		this.eventPanel=this.getEventForm(this.dataId);
		this.knowFormPanel=this.getKnowForm(this.knowledgeId);
		this.getFeedbackPanel=this.getFeedback(this.dataId,this.surveyId);
		temp.push(this.eventPanel);
		temp.push(this.knowFormPanel);
		temp.push(this.getFeedback(this.dataId,this.surveyId));
        this.tabPanel = new Ext.TabPanel({           
				xtype : 'tabpanel',
				activeTab : 0,
	            enableTabScroll:true, 
				deferredRender : false,
				frame : true,
				plain : true,
	            border : true, 
				baseCls : 'x-plain',// 是否设置和背景色同步
				width : 750,
				height : "auto", 
				defaults : {
					autoHeight : true,
					bodyStyle : 'padding:2px'
				},
				items :this.temp,
	  			buttonAlign : 'center',
	  			scope : this
			});
        items.push(this.tabPanel);
		this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})