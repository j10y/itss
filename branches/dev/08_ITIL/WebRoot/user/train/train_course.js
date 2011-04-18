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
	save : function(){
		var info = Ext.getCmp('panel_trainCourse').form.getValues(false);
		var cId = info.name;
		var dataId = this.dataId;
		var vp = null;
		if(info!=null){
			vp = Ext.apply(info,vp,{});
		}
			Ext.Ajax.request({
			url : webContext + '/trainPlan_saveCourseAndSurvey.action?cId='+cId+'&dataId='+dataId,
			method : "POST",
			params :vp,
			success : function(response, options) {
//				var responseArray = Ext.util.JSON.decode(response.responseText);
//				var dataId = responseArray.id;
				Ext.MessageBox.alert("提示","保存成功",function(){
					window.location = webContext
						+ "/user/train/train_courseList.jsp";
				});
			
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("提示","保存失败");
			},
			scope : this
		});
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
	
	
 getFormpanel_trainCourse: function() {
 		//*************
 	var dataId=this.dataId;
		var item = new Array();
		var stored = new Ext.data.JsonStore({
			url : webContext + '/trainPlan_findSurveyByCourse.action',
			fields : ['code', 'name'],
			root : "data"
		});
		var newCombo = new Ext.form.ComboBox({
			xtype : "combo",
			name : "combobox",
			id : "comboxid",
			fieldLabel : "<font color=green>关联问卷</font>",
			displayField : 'name',
			valueField : "code",
			hiddenName : "name",
			code : "code",
			mode : 'local',
			store : stored,
			editable : false,
			typeAhead : true,
			triggerAction : 'all',
            width:200,
			emptyText : '请选择...',
			selectOnFocus : true,
			pageSize:10,
			listeners : {
				'beforequery' : function(queryEvent){
					var param = queryEvent.combo.getRawValue();
					if(queryEvent.query==''){
					param='';
					}stored.load({
					params:{planName:param,dataId : dataId,start:0}});
					return false;
				}
			}
		});
//		item.push({
//			html : "该课程反馈问卷:&nbsp;",
//			cls : 'common-text',
//			style : 'width:130;text-align:right'
//		},newCombo);
		//********************
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_trainCourse",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("page_train_course_form", "panel_trainCourse", this.dataId);// 这是要随时变得
				for(var i=0;i<data.length;i++){
					if(data[i].name=='TrainCourse$deleteFlag'){
					data[i].value=0;
					}
				}
               data.push(newCombo);                      
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("panel_trainCourse");
				for(var i=0;i<data.length;i++){
					//alert(data[i].name);
					if(data[i].name=='TrainCourse$deleteFlag'){
					data[i].value=0;
					}
				}
                  data.push(newCombo);                                    
				biddata = da.split(data);
			}
			for(var i =0;i<biddata.length;i++){
				if(biddata[i].name=="TrainCourse$instructor"){
							biddata[i].store.baseParams.deleteFlag=0;
					
				}
			}
            item = biddata;       
			if(this.getFormButtons.length!=0){
				this.formpanel_trainCourse= new Ext.form.FormPanel({
					id : 'panel_trainCourse',
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
					title : "课程",
					items : item,
					buttons : this.getFormButtons
				});
				}else{
					this.formpanel_trainCourse= new Ext.form.FormPanel({
					id : 'panel_trainCourse',
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
					title : "课程",
					items : item
					});
				}
		
		return this.formpanel_trainCourse;
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
		this.model="page_train_course_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_train_course_form",this);
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
		
		       this.getFormpanel_trainCourse();
		       this.pa.push(this.formpanel_trainCourse);
		       this.formname.push("panel_trainCourse");
		       temp.push(this.formpanel_trainCourse);
          items = temp;
		items.push(this.allbuttons);
		   this.items = items;
		   this.on("save",this.save,this);
		PageTemplates.superclass.initComponent.call(this);
	}
})