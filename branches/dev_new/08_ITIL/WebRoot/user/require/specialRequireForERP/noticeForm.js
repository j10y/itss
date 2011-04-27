NoticeForm = Ext.extend(Ext.Panel, {
	id : "NoticeForm",
	title: "<font color=red>��Ŀ��������</font>",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width : 1050,
	frame : true,
	//autoScroll : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1
	},
	
	workSubmit:function(){
		var dataId = Ext.getCmp('NewNotice$id').getValue();
//		alert(Ext.getCmp('NewNotice$id').getValue());
		if(dataId==""){
			Ext.MessageBox.alert("��ʾ","���ȱ���ݸ�");
			return;
		}
		var url = webContext + '/noticeaction_findNoticeFlag.action?dataId='+dataId;
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn.open("post", url, false);
		conn.send(null);
		var noticeFlag = eval('('+conn.responseText+')'); 
		if(noticeFlag!=0){
			Ext.Msg.alert("��ʾ","�벻Ҫ�ظ��ύ!");
			return;
		}
		
//		alert("================"+"����������asdas"+"======================");
		Ext.Ajax.request({
			url :webContext+'/noticeManagerWorkflow_apply.action', 
			params : {
				dataId : this.dataId,
				model: this.model,
				bzparam : "{dataId :'"+ dataId+"',applyId : '"+dataId+"',applyType: 'nproject',applyTypeName: '��������',customer:''}",						
				defname : "noticeManager1237967838187"
			},
			success : function(response, options) {
				Ext.Msg.alert("��ʾ","�����������ɹ�");				
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("��ʾ","����������ʧ��");
			}
		}, this);
	},
	save : function() {
		var oEditor = FCKeditorAPI.GetInstance("content");//FCKeditor��ʼ���Ժ������FCKeditorAPI
		oEditor.GetXHTML();
		Ext.get('content').dom.value=oEditor;
		var info = Ext.getCmp('page_notice').getForm().getValues();
		var dataId =this.dataId;
		if (info.NewNotice$title == "" || info.NewNotice$beginDate == "" || info.NewNotice$endDate == "" ) {
			Ext.MessageBox.alert("��ʾ","ȱ�ٱ������ݣ�����д��ȫ");
			return;
		};
		var curReqId = this.reqId;
		var curReqClass = this.reqClass;
		Ext.Ajax.request({
			url : webContext + '/SRAction_saveNoticeForReq.action',
			method : "POST",
			params : {
				auditflag : 0,
				//dataId : dataId,
				dataId : info.NewNotice$id,
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
				content : Ext.encode(oEditor.GetData()),
				reqId : curReqId
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				dataId = responseArray.newDataId;
				Ext.getCmp('NewNotice$id').setValue(dataId);
				Ext.MessageBox.alert("����ɹ�");
				
//				history.back();
				// window.location=webContext+"/user/train/train_questList.jsp";
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("����ʧ��");
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
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
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
		this.Panel = new Ext.form.FormPanel({
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
	
	getPanelArea : function() {
		var dataId = this.dataId;
		var contents = "";
			var contentIntro = new Ext.form.TextArea({
				xtype : "textarea",
				height : 200,
				name : "content",
				width : 800,
				id : "content",
				listeners : {
					"render" : function(f) {
						var fckEditor = new FCKeditor("content");
						if (dataId != 0) {
							Ext.Ajax.request({
								url : webContext + '/noticeaction_find.action',
								method : "POST",
								params : {
									dataId : dataId
								},
								success : function(response, options) {
									contents = Ext.decode(response.responseText);
									Ext.get('content').dom.value = contents;// IEˢ��ʱ��������
																			// �����ʹ
									fckEditor.GetData = contents;
									var oEditor = FCKeditorAPI
											.GetInstance("content");// IE��ʹ
																	// ���FCKeditorAPIδ����
									oEditor.SetHTML(contents);
								}
							})
						}
						Ext.get('content').dom.value = contents;
						fckEditor.GetData = contents;
						fckEditor.Height = 230;
						fckEditor.Width = 1100;
						fckEditor.BasePath = webContext + "/FCKeditor/";
						fckEditor.ToolbarSet = "Default";
						fckEditor.ReplaceTextarea();
					}
				}
			});
			var item = new Array();
			item.push({
			// html : "��������:",
			cls : 'common-text',
			style : 'text-align:right'
		},contentIntro);
	
			this.cpanel = new Ext.form.FormPanel({
					id : 'cataPanel',
					title : '��������',
					layout : 'table',
					region : 'north',
					height : 280,
					width : 800,
					frame : true,
					collapsible : true,
					defaults : {
						bodyStyle : 'padding:16px'
					},
					layoutConfig : {
						columns : 8
					},
					items : item
				});
				
		return this.cpanel;
	
	
	},
 getFormpage_notice: function() {
      var da = new DataAction();
       var sra = new SRAction();
		 var pnId = sra.getProjectNoticeId(this.reqId);
		  var data = null;
			// �ж������������޸�
			var biddata = "";
			
			  var buttonUtil = new ButtonUtil();
			  this.getFormButtons = buttonUtil.getButtonForPanel("page_notice",this);
//			  alert(this.getFormButtons.length+"this.getFormButtons");
//			  alert(pnId);
			if (pnId!= '0') {
				data = da.getSingleFormPanelElementsForEdit("page_notice", pnId);// ����Ҫ��ʱ���
				
				biddata = da.split(data);
			} else {
				//alert("�½�");
				data = da.getSingleFormPanelElementsForEdit("page_notice","");
				biddata = da.split(data);
			}
		//	alert(this.getFormButtons.length);
			if(this.getFormButtons.length!=0){
		this.formpage_notice= new Ext.form.FormPanel({
			id : 'page_notice',
			layout : 'table',
			height : 'auto',
			width : 900,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "����",
			items : biddata,
			buttons : this.getFormButtons
		});
		}else{
			this.formpage_notice= new Ext.form.FormPanel({
			id : 'page_notice',
			layout : 'table',
			height : '600',
			width : 900,
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 4
			},
			title : "����",
			items :biddata
			});
		}

		return this.formpage_notice;
	},
	
  items : this.items,
  
	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		//alert(this.idd+"idd");
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
		this.model="page_notice_form";
		var buttonUtil = new ButtonUtil();
		this.mybuttons = buttonUtil.getButtonForModel("page_notice_form",this);
		for(var i =0;i<this.mybuttons.length;i++){
//			alert(this.mybuttons[i].text);
			if(this.mybuttons[i].text=='����'){
				this.mybuttons[i].hidden=true;
			}
//			if(this.mybuttons[i].text=='�ύ'){
//				this.mybuttons[i].iconCls="";
//			}
		}
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
		
		       this.getFormpage_notice();
//		       this.pa.push(this.formpage_notice);
//		       this.formname.push("page_notice");
		       temp.push(this.formpage_notice);
		       this.getPanelArea();
		       temp.push(this.cpanel);
		       items = temp;
			   items.push(this.buttons);
		   	   this.items = items;
		this.on("save", this.save, this);
		this.on("workSubmit", this.workSubmit, this);
		NoticeForm.superclass.initComponent.call(this);
	}
})