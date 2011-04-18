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
 getFormpanel_tableCodeBuilder: function() {
		this.formpanel_tableCodeBuilder= new Ext.form.FormPanel({
		    title: '������������',
		    id:'IdGeneratorForm',
		    width: 800,
		    layout:'table',
		    layoutConfig: {
		       columns : 4
		    },
		    fileUpload : true,
		    items: [
		    	{html : '��������:��',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.ComboBox({
					hiddenName : 'systemMainTableIdBuilder.SystemMainTable',
					id : 'systemMainTableIdBuilder.SystemMainTableCombo',
					width : 200,
					style : '',
					fieldLabel : 'ϵͳ����',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'tableCnName',
					valueField : 'id',
					emptyText : '��ѡ��...',
					allowBlank : false,
					
					name : 'systemMainTableIdBuilder.SystemMainTable',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,
			        store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.digitalchina.info.appframework.metadata.entity.SystemMainTable',
							fields : ['id', 'tableCnName'],
							listeners : {
								beforeload : function(store, opt) {
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
					})
		        }),
		        {html : '��������:��',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.ComboBox({
					hiddenName : 'systemMainTableIdBuilder.department',
					id : 'systemMainTableIdBuilder.departmentCombo',
					width : 200,
					style : '',
					fieldLabel : '��������',
					colspan : 0,
					rowspan : 0,
					lazyRender : true,
					displayField : 'departName',
					valueField : 'departCode',
					emptyText : '��ѡ��...',
					allowBlank : false,
					
					name : 'systemMainTableIdBuilder.department',
					triggerAction : 'all',
					minChars : 50,
					queryDelay : 700,	        
					store : new Ext.data.JsonStore({
							url : webContext
									+ '/extjs/comboDataAction?clazz=com.digitalchina.info.framework.security.entity.Department',
							fields : ['departCode', 'departName'],
							listeners : {
								load : function(store, opt) {
								}
							},
							totalProperty : 'rowCount',
							root : 'data',
							id : 'id'
					})
		        }),{html : 'ǰ׺:��',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.TextField({
		        id:'systemMainTableIdBuilder.prefix',
		        name:'systemMainTableIdBuilder.prefix',
		        width:200
		        }),
		        {html : '����:��',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.TextField({
		        id:'systemMainTableIdBuilder.length',
		        name:'systemMainTableIdBuilder.length',
		        width:200
		        }),
		         {html : '�Ƿ񷢲���־:��',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.ComboBox({
		        
		        hiddenName:'systemMainTableIdBuilder.deployFlag', 	
		        id:'systemMainTableIdBuilder.deployFlagCombo',
		        name:'systemMainTableIdBuilder.deployFlag',
		        width:200,
				mode :"local",
				displayField :'deployFlag', 
				valueField : 'id',
				readOnly:true,
				triggerAction : "all",
				store:new Ext.data.SimpleStore({
						fields:['id','deployFlag'],
						data : [['1', '��'], ['0', '��']]
					})
		        }),
		        {html : '���±��ֵ:��',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},new Ext.form.TextField({
		        id:'systemMainTableIdBuilder.latestValue',
		        name:'systemMainTableIdBuilder.latestValue',
		        width:200
		        }),
		        {html : '�����ļ�:��',cls : 'common-text',style : 'width:100;text-align:right;line-height:3'},
		        new Ext.form.Field({
		        id:'systemMainTableIdBuilder.ruleFileName',
		        name:'systemMainTableIdBuilder.ruleFileName',
		        width:200,
		        height : 25,
		        inputType : 'file'
		        }),		        
		    	new Ext.form.TextField({		    	
		    		id:"systemMainTableIdBuilder.id",
		    		name:"systemMainTableIdBuilder.id",
		    		hidden:true
		    	}) 
		    ],
		    buttons: [{
		        text: '����',
		        handler : function(){
		        		var form = Ext.getCmp('IdGeneratorForm').getForm();
		        		if(form.isValid){
	               		 form.submit({
	               		 		clientValidation: true,
				        		url:webContext+'/IdGeneratorAction_save.action',
				        		success:function(response){
				        			var responseArray =Ext.util.JSON.decode(response.responseText);	
				        			Ext.Msg.alert("��ʾ","�������ݳɹ�");
				        		},
				        		failure:function(response){
				        			Ext.Msg.alert("������Ϣ","��������ʧ��");
				        		}
		        		});
		        		}
		        },
				scope:this
		    },{
		        text: '����',
		        handler:function(){
		        	Ext.getCmp('IdGeneratorForm').getForm().reset();
		        }
		    },{
		        text: '����',
		        handler:function(){
		        	window.location.href=webContext+"/infoAdmin/idgen/tableIdGenList.jsp";
		        }
		    },{
		        text: 'ɾ��',
		        handler:function(){
					var id = new Array();
					var da = new DataAction();
					var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
						if (button == 'yes') {
							var id = Ext.getCmp("systemMainTableIdBuilder.id").getValue();						
							Ext.Ajax.request({
									url : webContext
											+ '/IdGeneratorAction_delete.action',
									params : {id:id},
				        		success:function(response){
				        			var responseArray =Ext.util.JSON.decode(response.responseText);	
				        			Ext.Msg.alert("��ʾ","ɾ�����ݳɹ�");
				        			window.location.href=webContext+"/infoAdmin/idgen/tableIdGenList.jsp";
				        		},
				        		failure:function(response){
				        			Ext.Msg.alert("������Ϣ","ɾ������ʧ��");
				        		}
									
								});
							
						}
					}, this);
		        }
		    }]
		});
		return this.formpanel_tableCodeBuilder;
	},
  items : this.items,
	/*
	 * clientvalidation ��ʼ��
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
		this.model="page_tableCodeBuilderForm";
		if(this.isUpdate==1){
			Ext.Ajax.request({ 
				url	: webContext + '/IdGeneratorAction_preUpdate.action?recordId='+this.recordId,		
				success : function(response){
					var responseArray = Ext.util.JSON.decode(response.responseText);
					var id = responseArray.id;
					Ext.getCmp("systemMainTableIdBuilder.id").setValue(id);
					var SystemMainTableCombo = Ext.getCmp("systemMainTableIdBuilder.SystemMainTableCombo");
					SystemMainTableCombo.setValue(responseArray.systemMainTableId);
					SystemMainTableCombo.setRawValue(responseArray.systemMainTableName);
					var departmentCombo = Ext.getCmp("systemMainTableIdBuilder.departmentCombo");
					departmentCombo.setValue(responseArray.departmentId);
					departmentCombo.setRawValue(responseArray.departmentName);
					var deployFlagCombo = Ext.getCmp("systemMainTableIdBuilder.deployFlagCombo");
					deployFlagCombo.setValue(responseArray.deployFlag);
					if(responseArray.deployFlag=='1'){
						deployFlagCombo.setRawValue("��");
					}else{
						deployFlagCombo.setRawValue("��");
					}
					Ext.getCmp("systemMainTableIdBuilder.prefix").setValue(responseArray.prefix);
					Ext.getCmp("systemMainTableIdBuilder.length").setValue(responseArray.length);
					Ext.getCmp("systemMainTableIdBuilder.latestValue").setValue(responseArray.latestValue);
					Ext.getCmp("systemMainTableIdBuilder.ruleFileName").setValue(responseArray.ruleFileName);				
				},
				failure : function(response){
					Ext.Msg.alert("������Ϣ","������Ϣ����");
				}			
			});
		}
		       this.getFormpanel_tableCodeBuilder();
		       this.pa.push(this.formpanel_tableCodeBuilder);
		       this.formname.push("panel_tableCodeBuilder");
		       temp.push(this.formpanel_tableCodeBuilder);
		       items = temp;
		   this.items = items;
		PageTemplates.superclass.initComponent.call(this);
	}
})