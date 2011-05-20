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
	gotoBack : function(){
	window.location = webContext
						+ "/user/train/train_surveyTypeList.jsp";
	},
	save : function(){
		var info = Ext.getCmp('page_trainSurveyType').getForm().getValues();
		//alert(Ext.encode(info));
		if(info.TrainSurveyType$name==""){//||info.TrainSurveyType$systemMainTable==""){
			Ext.Msg.alert("提示","名称为空，还不能保存!");
			return;
		}
		var vp = null;
		if(info!=null){
			vp = Ext.apply(info,vp,{});
		}
		Ext.Ajax.request({
		url : webContext + '/trainPlan_saveSurveyType.action',
			method : "POST",
			params : vp,
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				Ext.MessageBox.alert("提示","保存成功",function(){
				window.location = webContext
						+ "/user/train/train_surveyTypeList.jsp";
					
				});
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("提示","保存失败");
			},
			scope : this
		})
	},
	getTabpanel : function(tab,tabTitle){
		this.tabPanel = new Ext.TabPanel({           
			xtype : 'tabpanel',
			activeTab : 0,
            enableTabScroll:true, 
            //minTabWidth:100,
            //resizeTabs:true,
            title:tabTitle,
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
			title:panelTitle,
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
	
	
 getFormpage_trainSurveyType: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("page_trainSurveyType",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("page_train_surveyType_form", "page_trainSurveyType", this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("page_trainSurveyType");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpage_trainSurveyType= new Ext.form.FormPanel({
			id : 'page_trainSurveyType',
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
			title : "问卷类型",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpage_trainSurveyType= new Ext.form.FormPanel({
			id : 'page_trainSurveyType',
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
			title : "问卷类型",
			items : biddata
			});
		}
		return this.formpage_trainSurveyType;
	},
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
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
		this.model="page_train_surveyType_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_train_surveyType_form",this);
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
		
		       this.getFormpage_trainSurveyType();
		       this.pa.push(this.formpage_trainSurveyType);
		       this.formname.push("page_trainSurveyType");
		       temp.push(this.formpage_trainSurveyType);
          items = temp;
		items.push(this.allbuttons);
		   this.items = items;
		   this.on("save",this.save,this);
		   this.on("gotoBack",this.gotoBack,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})