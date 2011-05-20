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
	saveAndSubmit : function(){
		var tkId = this.taskId;
		var isNeedBasicEn = Ext
				.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo')
				.getValue();
		var engineerId = Ext
				.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo')
				.getValue();
		var engineerName = Ext	//add by zhangzy for BASIS 工程师必填不能选“无” in 2009 11 30
				.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo')
				.getRawValue();
		if(engineerName=="无"){
			Ext.Msg.alert("提示","请选择是否需要BASIS工程师处理并保存!");
			return;
		}	
		if(isNeedBasicEn==""){
			Ext.Msg.alert("提示","请选择是否需要BASIS工程师处理并保存!");
			return;
		}else if(engineerId==""&&isNeedBasicEn=="1"){
			Ext.Msg.alert("提示","请选择BASIS工程师处理并保存!");
			return;
		}else if(engineerId==null&&isNeedBasicEn=="1"){
			Ext.Msg.alert("提示","请选择BASIS工程师处理并保存!");
			return;
		}else if(engineerId==''&&isNeedBasicEn=="1"){
			Ext.Msg.alert("提示","请选择BASIS工程师处理并保存!");
			return;		
		}else if(engineerName == ""&&isNeedBasicEn=="1"){
			Ext.Msg.alert("提示","请选择BASIS工程师处理并保存!");
			return;
//add by zhangzy for BASIS工程师选择验证 in 2009 11 24 end				
		}else{
		var formParam = "";
				var gridParam = "";
				var param = '{'; 
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
								+ '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
			 // alert(param); //************************************显示面板保存的数据************************************
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : param,
						panel : 'panel_ErpEngineerFeedback_Input',
						model : 'nr_m_operateByEngineer2'
					},
					success : function(response, options) {
						Ext.Ajax.request({
							url: webContext + '/extjs/workflow?method=getData&taskId=' + tkId 
							+ '&users=operateByBASISEngineer:' + engineerId,
							method:'post', 
							success:function(response,options){
								window.parent.auditContentWin.specialAudit();
								},
							failure:function(response,options){					
								}
							});
					},
					failure : function(response, options) {
						Ext.Msg.alert("提示","保存失败!");
					}
				}, this);
		}
	},
	saveAndAssign : function(){
		var tkId = this.taskId;
         var isNeedBasicEn=Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
		 if(isNeedBasicEn==""){
			Ext.Msg.alert("提示","请选择是否需要BASIS工程师处理并保存!");
			return;
			}
		var engineerId = Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').getValue();
		if(engineerId==""&&isNeedBasicEn=="1"){
			Ext.Msg.alert("提示","请选择BASIS工程师处理并保存!");
			return;
			}
		var formParam = "";
				var gridParam = "";
				var param = '{'; 
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						formParam += '\"' + this.formname[i] + '\"' + ':[' + fP + '],';
					}
					param += formParam;
				}
				if (this.gd.length != "0") {
					for (var k = 0; k < this.gd.length; k++) {
						var gParam = "";
						var gP = this.gd[k].getStore().getRange(0,
								this.gd[k].getStore().getCount());
						for (i = 0; i < gP.length; i++) {
							gParam += Ext.encode(gP[i].data) + ",";
						}
						gParam = gParam.slice(0, gParam.length - 1);
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam
								+ '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
			 // alert(param); //************************************显示面板保存的数据************************************
				Ext.Ajax.request({
					url : webContext + '/extjs/pageData?method=savePanel',
					params : {
						info : param,
						panel : 'panel_ErpEngineerFeedback_Input',
						model : 'nr_EBEngineerFeedback2'
					},
					success : function(response, options) {
						Ext.Msg.alert("提示","保存成功!");
						
						Ext.Ajax.request({
							url: webContext + '/extjs/workflow?method=getData&taskId=' + tkId 
							+ '&users=operateByBASISEngineer:' + engineerId,
							method:'post', 
							success:function(response,options){
								},
							failure:function(response,options){					
								}
							});
					},
					failure : function(response, options) {
						Ext.Msg.alert("提示","保存失败!");
					}
				}, this);
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
	
	
 getFormpanel_ERP_NormalNeed1_Input: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_ERP_NormalNeed3_m_Input",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("nr_m_operateByEngineer2", "panel_ERP_NormalNeed3_m_Input", this.dataId);// 这是要随时变得
				biddata = da.splitForReadOnly(data);
			} else {
				data = da.getPanelElementsForAdd("panel_ERP_NormalNeed3_m_Input");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_ERP_NormalNeed1_Input= new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed3_m_Input',
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
			title : "ERP常规服务申请",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_ERP_NormalNeed1_Input= new Ext.form.FormPanel({
			id : 'panel_ERP_NormalNeed3_m_Input',
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
			title : "ERP常规服务申请",
			items : biddata
			});
		}
		return this.formpanel_ERP_NormalNeed1_Input;
	},
 getFormpanel_ErpEngineerFeedback_Input: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("panel_ErpEngineerFeedback_Input",this);
			 
			  if (this.dataId != '0') {
			  	 data = da.getPanelElementsForEdit("nr_m_operateByEngineer2", "panel_ErpEngineerFeedback_Input", this.dataId);// 这是要随时变得
		       for(var i=0;i<data.length;i++){
				 	  	//alert(data[i].id);
				   if(data[i].id=='itil_sci_ErpEngineerFeedback$basisFlagCombo'){
				 	
					data[i].on('select' , function(combo,record,index){
						//alert("hello");
						var s=Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						//alert(s);
						if(s=='1'){
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').enable();
						}else{
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').clearValue();
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').disable();
						}
						})
					}
			
				if(data[i].id=='itil_sci_ErpEngineerFeedback$basisEngineerCombo'){
					data[i].disabled=true;
				 }
			   }
				
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForEdit("nr_m_operateByEngineer2", "panel_ErpEngineerFeedback_Input", this.dataId);// 这是要随时变得
				 for(var i=0;i<data.length;i++){
				 	  	//alert(data[i].id);
				   if(data[i].id=='itil_sci_ErpEngineerFeedback$basisFlagCombo'){
				 	
					data[i].on('select' , function(combo,record,index){
						//alert("hello");
						var s=Ext.getCmp('itil_sci_ErpEngineerFeedback$basisFlagCombo').getValue();
						//alert(s);
						if(s=='1'){
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').enable();
						}else{
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').clearValue();
							Ext.getCmp('itil_sci_ErpEngineerFeedback$basisEngineerCombo').disable();
						}
						})
					}
			
				if(data[i].id=='itil_sci_ErpEngineerFeedback$basisEngineerCombo'){
					data[i].disabled=true;
				 }
				 if(data[i].id=='itil_sci_ErpEngineerFeedback$feekback'
				 	||data[i].id=='itil_sci_ErpEngineerFeedback$otherInfo'){
				 		alert('aaa');
				 	data[i].value='同意';
				 }
			   	}
				
				biddata = da.split(data);
				
			
			}
			if(this.getFormButtons.length!=0){
		this.formpanel_ErpEngineerFeedback_Input= new Ext.form.FormPanel({
			id : 'panel_ErpEngineerFeedback_Input',
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
			title : "<font color=red>Erp工程师反馈</font>",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpanel_ErpEngineerFeedback_Input= new Ext.form.FormPanel({
			id : 'panel_ErpEngineerFeedback_Input',
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
			title : "<font color=red>Erp工程师反馈</font>",
			items : biddata
			});
		}
		return this.formpanel_ErpEngineerFeedback_Input;
	},
  items : this.items,
	/*
	 * clientvalidation 初始化
	 */
	initComponent : function() {
		var histroyForm = new HistroyForm({
			reqId : this.dataId,
			reqClass : "com.zsgj.itil.require.entity.ERP_NormalNeed"
		});
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
		this.model="nr_m_operateByEngineer2";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("nr_m_operateByEngineer2",this);
		if(this.mybuttons!=""){
			this.buttons = {
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
			this.buttons = {
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
		
		       this.getFormpanel_ERP_NormalNeed1_Input();
		       this.pa.push(this.formpanel_ERP_NormalNeed1_Input);
		       this.formname.push("panel_ERP_NormalNeed3_m_Input");
		       temp.push(this.formpanel_ERP_NormalNeed1_Input);
		       this.getFormpanel_ErpEngineerFeedback_Input();
		       this.pa.push(this.formpanel_ErpEngineerFeedback_Input);
		       this.formname.push("panel_ErpEngineerFeedback_Input");
		       temp.push(this.formpanel_ErpEngineerFeedback_Input);
		       temp.push(histroyForm);
               items.push(this.getTabpanel(temp));
		       if(this.readOnly!=1){
			items.push(this.buttons);
		}
		       this.items = items;
//		       this.on("saveAndAssign",this.saveAndAssign,this);
		       this.on('saveAndSubmit',this.saveAndSubmit,this);
		       var tempErpId = Ext.getCmp('itil_sci_ErpEngineerFeedback$id').getValue();
//		       if(tempErpId==''){
//		       		Ext.getCmp('itil_sci_ErpEngineerFeedback$feekback').setValue('同意');
//		       		Ext.getCmp('itil_sci_ErpEngineerFeedback$otherInfo').setValue('同意');
//		       }
		       PageTemplates.superclass.initComponent.call(this);
	},
	workflowForGroup : function(){
		var id = Ext.getCmp('itil_sci_RequirementEngineer$id').getValue();
		//alert(id);
	    if(id==""){
		    Ext.MessageBox.alert("提示","请选择是否需要BASIS工程师处理并保存!");
			return;
		}
//		alert(Ext.encode(formParam));
//		return;
		window.parent.auditContentWin.audit();
	}
})