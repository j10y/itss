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
	resetBtn:function(){
		
		Ext.getCmp("panel_trainInstructor").form.reset();
	},
	
 getFormpanel_trainInstructor: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_trainInstructor",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("page_train_instructor_form", "panel_trainInstructor", this.dataId);// 这是要随时变得
				for(var i=0;i<data.length;i++){
					if(data[i].name=='TrainInstructor$deleteFlag'){
					data[i].value=0;
					}
					
				}
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("panel_trainInstructor");
				
					for(var i=0;i<data.length;i++){
					if(data[i].name=='TrainInstructor$deleteFlag'){
					data[i].value=0;
					}
					
				}
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_trainInstructor= new Ext.form.FormPanel({
			id : 'panel_trainInstructor',
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
			title : "讲师",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_trainInstructor= new Ext.form.FormPanel({
			id : 'panel_trainInstructor',
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
			title : "讲师",
			items : biddata
			});
		}
		return this.formpanel_trainInstructor;
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
		this.model="page_train_instructor_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_train_instructor_form",this);
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
				this.on("resetBtn",this.resetBtn,this);
		       this.getFormpanel_trainInstructor();
		       this.pa.push(this.formpanel_trainInstructor);
		       this.formname.push("panel_trainInstructor");
		       temp.push(this.formpanel_trainInstructor);
          items = temp;
		items.push(this.allbuttons);
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})