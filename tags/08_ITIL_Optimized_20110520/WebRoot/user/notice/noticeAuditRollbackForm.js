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
	noticeConfirm : function(){
		audit();
		
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
	
	save : function() {
		var oEditor = FCKeditorAPI.GetInstance("content");//FCKeditor初始化以后才能用FCKeditorAPI
		oEditor.GetXHTML();
		Ext.get('content').dom.value=oEditor;
		//alert(oEditor.GetXHTML());
		//alert(oEditor.EditorDocument.body.innerText);
		//alert(Ext.encode(oEditor.GetData()));
		var info = Ext.getCmp('page_notice').getForm().getValues();
		var dataId = this.dataId;
		if (info.NewNotice$title == "" || info.NewNotice$beginDate == "" || info.NewNotice$endDate == "" ) {
			Ext.MessageBox.alert("提示","缺少必须数据，请填写完全");
			return;
		};
		Ext.Ajax.request({
			url : webContext + '/noticeaction_save.action',
			method : "POST",
			params : {
				auditflag : 0,
				dataId : dataId,
				id : info.id,
				title : info.NewNotice$title,
				remark : info.NewNotice$remark,
				beginDate : info.NewNotice$beginDate,
				endDate : info.NewNotice$endDate,
				grade : info.NewNotice$grade,
				noticeType : info.NewNotice$newNoticeType,
				customer : info.NewNotice$customer,
				customerType : info.NewNotice$customerType,
				serviceProvider : info.NewNotice$serviceProvider,
				serviceProviderType : info.NewNotice$serviceProviderType,
				content : Ext.encode(oEditor.GetData())
				
			},
			success : function(response, options) {
				Ext.MessageBox.alert("保存成功");
				//history.back();
				// window.location=webContext+"/user/train/train_questList.jsp";
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("保存失败");
			},
			scope : this
		});
	},
	
	
	getPanelArea : function(){
//		var dataId = this.dataId;
//		var item = new Array();
//		var url = webContext + '/noticeaction_find.action?dataId='+dataId;
//		var conn = Ext.lib.Ajax.getConnectionObject().conn;
//		conn.open("post", url, false);
//		 conn.send(null);
//		 var data = eval('('+conn.responseText+')'); 
//		item.push({html : Ext.util.Format.htmlDecode(data),cls : 'common-text',style : 'text-align:left'});
//	
		
		
		
				var dataId = this.dataId;
		var contents = "";
		//alert(dataId);
			var contentIntro =  new Ext.form.TextArea({
		        xtype : "textarea", 
				height : 350, 
				name : "content", 
				width : 780, 
				id : "content", 
				listeners : { "render" : function(f) { 
				  			var fckEditor = new FCKeditor("content"); 
//				  			var oEditor = FCKeditorAPI.GetInstance("content");
//        					oEditor.GetXHTML(true);
				  			//fckEditor.UpdateLinkedField();  
				  			//alert(FCKeditorAPI.GetInstance('content').GetXHTML( true ));  
				  			// var editorInstance=FCKeditorAPI.GetInstance('content');  
				  			//alert(Ext.get('content').dom.value);  
				  			if(dataId!=0){
				  				Ext.Ajax.request({
				  				url : webContext + '/noticeaction_find.action',
								method : "POST",
				  					params : {
										dataId : dataId
										},
								success : function(response, options) {
									//alert(FCKeditorAPI+"---2");
//											alert(response.responseText);
//											var responseArray = Ext.util.JSON.decode(response.responseText);
//											alert(responseArray);
											//var content = responseArray.content;
											//alert(content);
											contents = Ext.decode(response.responseText);
											//alert(aa);
											Ext.get('content').dom.value=contents;//IE刷新时带不出来 火狐好使
											fckEditor.GetData= contents;
											var oEditor = FCKeditorAPI.GetInstance("content");//IE好使 火狐FCKeditorAPI未定义
											oEditor.SetHTML( contents);
											}
				  				})}
				  			//Ext.get('content').dom.value=content;//写个Ajax请求 把值set进去
				  			//Ext.get('content').dom.value=fckEditor.Value;
				  			Ext.get('content').dom.value=contents;
				  			fckEditor.GetData= contents;
				  			fckEditor.Height = 350; 
				  			fckEditor.Width = 780; 
				  			fckEditor.BasePath = webContext+"/FCKeditor/" ; 
				  			//fckEditor.Config['CustomConfigurationsPath'] = webContext+"/FCKeditor/fckconfig.js" ;
				  			fckEditor.ToolbarSet = "Default" ; 
				  			fckEditor.ReplaceTextarea(); 
//				  			function FCKeditor_OnComplete( instance ) {  
//				  				alert("nnnnnn");
//								     editorInstance=instance;  
//								 };  
  						} 
				}
			});
			var item = new Array();
			item.push({
			//html : "公告内容:",
			cls : 'common-text',
			style : 'text-align:left' 
		},contentIntro);
		
		
		
		
	var cpanel = new Ext.form.FormPanel({
			id : 'cataPanel',
			title : '公告内容',
			layout : 'table',
			region : 'north',
			height : 'auto',
			width :820,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:2px'
			},
			layoutConfig : {
				columns : 8
			},
			items : item
		});
		return cpanel;
	},
	alertAdd : function(){		
		addMarkUser();
	},
	
	
 getFormpage_notice: function() {
      var da = new DataAction();
		  var data = null;
			// 判断是新增还是修改
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("page_notice",this);
			if (this.dataId != '0') {
				data = da.getPanelElementsForEdit("page_notice_form_audit_rollback", "page_notice", this.dataId);// 这是要随时变得
				biddata = da.split(data);
			} else {
				data = da.getPanelElementsForAdd("page_notice");
				biddata = da.split(data);
			}
			if(this.getFormButtons.length!=0){
		this.formpage_notice= new Ext.form.FormPanel({
			id : 'page_notice',
			layout : 'table',
			height : 'auto',
			width : 820,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "公告",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpage_notice= new Ext.form.FormPanel({
			id : 'page_notice',
			layout : 'table',
			height : 'auto',
			width : 820,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "公告",
			items : biddata
			});
		}
		return this.formpage_notice;
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
		this.model="page_notice_form_audit_rollback";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_notice_form_audit_rollback",this);
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
		
		       this.getFormpage_notice();
		       this.pa.push(this.formpage_notice);
		       this.formname.push("page_notice");
		       temp.push(this.formpage_notice);
		        temp.push(this.getPanelArea());
          items = temp;
		items.push(this.allbuttons);
		   this.items = items;
		   
		   
		   this.on("noticeConfirm", this.noticeConfirm, this);
		   this.on("saveForNoticeModel", this.save, this);
		   //add by lee for checkout date in 20100409 begin
		Ext.getCmp("NewNotice$beginDate").on("change",function(obj, newvalue, oldvalue){
			if(Ext.getCmp("NewNotice$endDate").getValue()!=""&&Ext.getCmp("NewNotice$endDate").getValue()<newvalue){
			Ext.MessageBox.alert("提示","起始时间不能大于结束时间");
			Ext.getCmp("NewNotice$beginDate").reset();
			}},this);
		Ext.getCmp("NewNotice$endDate").on("change",function(obj, newvalue, oldvalue){
			if(Ext.getCmp("NewNotice$beginDate").getValue()!=""&&Ext.getCmp("NewNotice$beginDate").getValue()>newvalue){
			Ext.MessageBox.alert("提示","起始时间不能大于结束时间");
			Ext.getCmp("NewNotice$endDate").reset();
			}},this);
		//add by lee for checkout date in 20100409 end
		PageTemplates.superclass.initComponent.call(this);
	}
})