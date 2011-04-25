PageTemplates = Ext.extend(Ext.Panel, {
	id : "PageTemplates",
	layout : 'table',
	height : 'auto',
	align : 'center',
	foredit : true,
	width :780,
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px'
	},
	layoutConfig : {
		columns : 1 
	},
	getForm : function(biddata, faNo) { 
		for(var i=0;i<biddata.length;i++){
			if(biddata[i].name=="ConfigItem$status"){
				Ext.getCmp("ConfigItem$status").setValue(1);
			}
			if(biddata[i].name=="ConfigItem$configItemType"){
				biddata[i].readOnly=true;
				biddata[i].hideTrigger=true;
			}
			if(biddata[i].fieldLabel=="��������"){
				biddata[i].readOnly=true;
				biddata[i].emptyText="�˱���Զ�����";
			}
				
			if(biddata[i].name=="ConfigItem$customerType"){
				var temp = biddata[i];
				Ext.Ajax.request({
					url :webContext + '/ci_findComboMessage.action',
					method : 'post',
					params : {
						clazz : 'com.digitalchina.itil.actor.entity.CustomerType',
						propertyName : 'name',
						propertyValue : unicode('������')
					},
					success : function(response, options) {
						var responseText = response.responseText;
						data = Ext.decode(responseText);
						Ext.getCmp("ConfigItem$customerTypeCombo").setValue(data.id);
						Ext.getCmp("ConfigItem$customerTypeCombo").setRawValue('������');
					}
				})
			}
			if(biddata[i].name=="ConfigItem$customer"){
				Ext.Ajax.request({
					url :webContext + '/ci_findComboMessage.action',
					method : 'post',
					params : {
						clazz : 'com.digitalchina.itil.actor.entity.CustomerIn',
						propertyName : 'customerName',
						propertyValue : unicode('��������')
					},
					success : function(response, options) {
						var responseText = response.responseText;
						data = Ext.decode(responseText);
						Ext.getCmp("ConfigItem$customerCombo").setValue(data.id);
						Ext.getCmp("ConfigItem$customerCombo").setRawValue('��������');
					}
				})
			}
				
			if(biddata[i].name=="ConfigItem$owner"){
				Ext.Ajax.request({
					url :webContext + '/ci_findComboMessage.action',
					method : 'post',
					params : {
						clazz : 'com.digitalchina.itil.actor.entity.CustomerIn',
						propertyName : 'customerName',
						propertyValue : unicode('��������')
					},
					success : function(response, options) {
						var responseText = response.responseText;
						data = Ext.decode(responseText);
						Ext.getCmp("ConfigItem$ownerCombo").setValue(data.id);
						Ext.getCmp("ConfigItem$ownerCombo").setRawValue('��������');
					}
				})
			}
				
			if(biddata[i].name=="ConfigItem$principal"){//Ĭ��Ϊ��ǰ������
				var data;
				var url = webContext + '/ci_findCurrentUser.action';
				Ext.Ajax.request({
					url :url,
					success : function(response, options) {
						var responseText = response.responseText;
						data = Ext.decode(responseText);
						Ext.getCmp("ConfigItem$principalCombo").setValue(data.id); 
						Ext.getCmp("ConfigItem$principalCombo").setRawValue(data.realName); 
					},
					failure : function(response, options) {
					}
				}, this);
				biddata[i].disable = true;
				biddata[i].emptyText="";
			}
			
			if(biddata[i].name=="ConfigItem$configItemStatus"){//������״̬
				biddata[i].disable = true;
				biddata[i].colspan=3;
				biddata[i].emptyText="";
			}
			
			if(biddata[i].name=="ConfigItem$descn"){//����
				biddata[i].disable = true;
				biddata[i].width=533;
				biddata[i].colspan=3;
				biddata[i].emptyText="";
			}
			
			if(biddata[i].name=="ConfigItem$useDate"){//Ĭ��Ϊ����
				biddata[i].disable = true;
				biddata[i].value = new Date();
				biddata[i].emptyText="";
			}
			if(biddata[i].name=="ConfigItemFinanceInfo$assetFlag"){//Ĭ��Ϊ����
				biddata[i].disable = true;
				Ext.getCmp("ConfigItemFinanceInfo$assetFlagCombo").setValue(0);
				biddata[i].colspan=3;
				biddata[i].emptyText="";
			}
			
			if(biddata[i].name=="ConfigItemFinanceInfo$maFlag"){//Ĭ��Ϊ����
				biddata[i].disable = true;
				Ext.getCmp("ConfigItemFinanceInfo$maFlagCombo").setValue(0);
				biddata[i].emptyText="";
			}
		}
		var buttonUtil = new ButtonUtil();
		this.getFormButtons = buttonUtil.getButtonForPanel(faNo,this);
		if(this.getFormButtons.length!=0){
			if(this.formTitle=="�����������Ϣ"&&(this.flag==""||this.flag==null)){
				this.formPanel = new Ext.form.FormPanel({
					id : faNo,
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
					title : this.formTitle,
					items : biddata,
					buttons : this.getFormButtons,
					tbar:[new Ext.Toolbar.TextItem('<font color=red>��������ʾ������ѡ�����������͡�</font>')]
				});
			}else if(this.formTitle!="�����������Ϣ"&&this.formTitle!="������Ϣ"){
				this.formPanel = new Ext.form.FormPanel({
					id : faNo,
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
					title : "���Ի���Ϣ",
					items : biddata,
					buttons : this.getFormButtons
			});
		}else{
				this.formPanel = new Ext.form.FormPanel({
					id : faNo,
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
					title : this.formTitle,
					items : biddata,
					buttons : this.getFormButtons
			});
		}
		
		}else{
			if(this.formTitle=="�����������Ϣ"&&(this.flag==""||this.flag==null)){
				this.formPanel = new Ext.form.FormPanel({
					id : faNo,
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
					title : this.formTitle,
					items : biddata,
					tbar:[new Ext.Toolbar.TextItem('<font color=red>��������ʾ������ѡ�����������͡�</font>')]
				});
			}else  if(this.formTitle!="�����������Ϣ"&&this.formTitle!="������Ϣ"){
				this.formPanel = new Ext.form.FormPanel({
					id : faNo,
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
					title : "���Ի���Ϣ",
					items : biddata,
					buttons : this.getFormButtons
			});
		}else{
				this.formPanel = new Ext.form.FormPanel({
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
					title : this.formTitle,
					items : biddata
				});
			}
		}
		return this.formPanel;
	},
	getTabpanel : function(tab,tabTitle) {
		this.tabPanel = new Ext.TabPanel({
			xtype : 'tabpanel',
			activeTab : 0,
            title:tabTitle,
			enableTabScroll : true,
			deferredRender : false,
			frame : true,
			plain : true,
			border : false,
			baseCls : 'x-plain',// �Ƿ����úͱ���ɫͬ��
			width : 800,
			defaults : {
				autoHeight : true,
				bodyStyle : 'padding:2px'
			},
			items : tab
		});
		return this.tabPanel;

	},
	getPanel : function(appa,panTitle) {
		this.Panel = new Ext.Panel({
			id : "Pages",
			height : 'auto',
			align : 'center',
            title:panTitle,
			border : false,
			defaults : {
				bodyStyle : 'padding:5px'
			},
			width : 'auto',
			frame : true,
			autoScroll : true,
			layoutConfig : {
				columns : 1
			},
			items : appa
		});
		return this.Panel;

	},
	getPanelTemplate : function(fcname,pcname) {//*************************
		var da = new DataAction();
		if (this.panelObj.xtype == "form") {
			this.formname.push(this.panelObj.panelname);
			this.formTitle = this.panelObj.title;
			var formClazz = this.panelObj.clazz;
			var foNa = this.panelObj.panelname;
			var divfloat = this.panelObj.divFloat;
			var panelId = this.panelObj.id;
			var data = null;
			// �ж������������޸�
			var biddata = "";
			if (this.dataId != '0') {
				data = da.getPanelConfigItemElementsForEdit(this.model, foNa, this.dataId);// ����Ҫ��ʱ���
				biddata = da.split(data);
			} else {
				data = da.getPanelConfigItemElementsForAdd(foNa);
				biddata = da.split(data);
			}
			this.pa[this.p] = this.getForm(biddata,foNa);
			this.itemsArray.push(this.pa[this.p]);
			this.p++;

		}
	},
	getChildpanelTemplate : function(layout, pagePanelchild,groupTitle) {
		var itm = new Array();
		var pnm;
		var second;
		for (var r = 0; r < pagePanelchild.length; r++) {
			if (pagePanelchild[r].groupFlag == '1') {
				var lay = pagePanelchild[r].xtype;
                var childGrTitle = pagePanelchild[r].title;                       
				pagePanelchild = pagePanelchild[r].childPagePanels;
				pnm = this.getChildpanelTemplate(lay, pagePanelchild,childGrTitle);
				itm.push(pnm);
				if (layout == "panel") {
					second = this.getPanel(itm,groupTitle);
				} else {
					second = this.getTabpanel(itm,groupTitle);
				}
			} else {
				this.panelObj = pagePanelchild[r]; //*********************8888888 ne aaddds
				var fcname= this.panelObj.fcolumnName;//customer
				var pcname= this.panelObj.pcolumnName;//CustomerIn$id
				this.itemsArray = itm;
				this.getPanelTemplate(fcname,pcname);
				if (layout == "tabpanel") {    
					second = this.getTabpanel(this.itemsArray,groupTitle);
				} else {
					second = this.getPanel(this.itemsArray,groupTitle);
				}
			}
		}
		return second;

	},

	items : this.items,

	/*
	 * clientvalidation ��ʼ��
	 */
	initComponent : function() {
		var da = new DataAction();
		if(this.dataId=="0"){
			var json = da.getPageModelAddForConfigItem("configItemFinanceMix",266);
		}else{
			var json = da.getPageModelForConfigItem("configItemFinanceMix",this.dataId);//getPageModel
		}
		this.model = json.pageModel[0].name;
		var saveButton = new Ext.Button({
			text : '����������滻',
			pressed : true,
			iconCls : 'save',
			scope : this,
			handler : function() {
				var curAccountDataId = this.accountDataId;//����ID
				var formParam = "";
				var gridParam = "";
				var param = '{';
				if (this.pa.length != "0") {
					for (var i = 0; i < this.pa.length; i++) {
						var fP = Ext.encode(this.pa[i].form.getValues(false));
						if (!this.pa[i].form.isValid()) {
							Ext.Msg.alert('��ʾ', '����ɫ�����ߵ��������ȷ��д!');
							return;
						}
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
						gridParam += '\"' + this.gridname[k] + '\"' + ':[' + gParam + '],';
					}
					param += gridParam;
				}
				param = param.slice(0, param.length - 1) + '}';
				Ext.Ajax.request({
					url : webContext + '/ci_saveConfigItem.action?',
					params : {
						info : param,
						model : this.model
					},
					success : function(response, options) {
						Ext.MessageBox.alert("��ʾ","����ɹ�",function(){
							var newUserId = Ext.getCmp('itil_ci_AppAdministrator$userInfoCombo').getValue();
							Ext.Ajax.request({
								url : webContext+ '/accountAction_initNewAppAdministrator.action',
								params : {
									dataId : curAccountDataId,
									newUserId : newUserId
								},
								success : function(response,options) {
									Ext.Msg.alert("��ʾ","ѡ�����滻������ɹ���");
									window.location = webContext + "/user/account/employeeQuit/EmployeeDimissionBM.jsp?dataId="+curAccountDataId;
								},
								failure : function(response,options) {
									Ext.Msg.alert("��ʾ","ѡ�����滻������ʧ�ܣ�");
								}
							}, this);
						});
					},
					failure : function(response, options) {
						Ext.MessageBox.alert("��ʾ","����ʧ��");
					}
				}, this);
			}
		});
		var backButton = new Ext.Button({
			text : '����',
			pressed : true,
			iconCls : 'back',
			scope : this,
			handler : function() {
				window.location = webContext + "/user/account/employeeQuit/EmployeeDimissionBM.jsp?dataId="+this.accountDataId;
			}
		});
		this.mybuttons = new Array();
		this.mybuttons.push(saveButton);
		this.mybuttons.push(backButton);
		var pagePanel = json.panels;
		// ������Ҫ�������������г�ʼ��
		var items = new Array();
		var temp = new Array();
		var pa = new Array();
		this.pa = pa;
		var gd = new Array();
		this.gd = gd;
		var formname = new Array();
		this.formname = formname;
		var gridname = new Array();
		this.gridname = gridname;
		this.my = "0";
		this.p="0";
        this.n="0";        
		var child = '';
		for (var k = 0; k < pagePanel.length; k++) {
			if (pagePanel[k].groupFlag == '1') {
				var laye = pagePanel[k].xtype;
                var groupTitle=pagePanel[k].title ;
				pagePanelchild = pagePanel[k].childPagePanels;
				child = this.getChildpanelTemplate(laye, pagePanelchild,groupTitle);
				temp.push(child);
			} else {
				this.panelObj = pagePanel[k];
				var fcname= this.panelObj.fcolumnName;//customer
				var pcname= this.panelObj.pcolumnName;//CustomerIn$id
				this.itemsArray = temp;
				this.getPanelTemplate(fcname, pcname); //1�·��賿�޸�bypeixf                       
			}

		}
		// ����Ĵ�����Ϊ���ñ���ʱ����������õ�
		// ��ť
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
		// �����������������������ӵ����������
		if (json.pageModel[0].pagePathType == "tabpanel") {
			items.push(this.getTabpanel(temp));
		} else {
			items = temp;
		}
		items.push(this.buttons);
		this.items = items;
		this.on("pageEvent", this.pageMethod, this); 
		this.on("goBackByPage", this.goBackByPage, this); 
		Ext.getCmp('ConfigItem$configItemTypeCombo').store.load({
			params:{
				id:266,
				start:0 
			},
			callback:function(r, options, success){
				Ext.getCmp('ConfigItem$configItemTypeCombo').setValue(266);
			}
		});
    	PageTemplates.superclass.initComponent.call(this);  
	},
	goBackByPage:function(){
		window.location = webContext+'/user/config/configItem.jsp';
	}
})
