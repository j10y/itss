ConfigUnitNodeMailPanel = Ext.extend(Ext.Panel, {
	id : "configUnitNodeMailPanel",
	//title : '�ʼ�ģ����Ϣ',
	layout : 'table',
	height : 'auto',
	width : 'auto',
	autoScroll : true,
	align : 'center',
	foredit : true,
	frame : true,
	defaults : {
		bodyStyle : 'padding:4px',
		overflow : 'auto'
	},
	layoutConfig : {
		columns : 1
	},
	disableEdit : function(){			
		this.mailSubject.disable();
		this.mailContent.disable();
		Ext.getCmp('addBtn').disable();
		Ext.getCmp('removeBtn').disable();
	},
	loadInfo : function(){
		Ext.Ajax.request({
			url: webContext+ '/configUnit_showAllMessage.action?virId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
			success : function(response){
				var jsonObj = Ext.decode(response.responseText);
				if(jsonObj.success){
					var subject = jsonObj.data[0].subject;
					var content = jsonObj.data[0].content;
					Ext.getCmp("subject").setValue(subject);
					content = runicode(content);
					var oEditor = FCKeditorAPI.GetInstance('content');
					oEditor.SetHTML(content);
				}
			}
		})
	},
	enableEdit : function(){		
		Ext.getCmp("subject").enable();
		Ext.getCmp("content").enable();
		Ext.getCmp('saveBtn').setDisabled(false);
		Ext.getCmp('editBtn').setDisabled(true);
	},		
	initComponent : function() {	
		
		//�ʼ�������Ϣfieldset������
		this.mailSubject = new Ext.form.TextField({
			id : 'subject',
			name : 'mailSubject',
			fieldLabel : '�ʼ�����',	
			allowBlank:false,
			width: 300,
			readOnly :false
		});
		//add by guangsa for sendMailModel in 20090824 begin
		var contents = "";
		this.mailContent = new Ext.form.TextArea({
			xtype : "textarea",
			height : 350,
			name : "content",
			width : 780,
			id : "content",
			listeners : {
				"render" : function(f) {
					var fckEditor = new FCKeditor("content");
					Ext.get('content').dom.value = contents;
					fckEditor.GetData = contents;
					fckEditor.Height = 350;
					fckEditor.Width = 780;
					fckEditor.BasePath = webContext + "/FCKeditor/";
					fckEditor.ToolbarSet = "Default";
					fckEditor.ReplaceTextarea();
				}
			}
		});
		var item = new Array();
		item.push({
			html : "�ʼ�����:",
			cls : 'common-text',
			style : 'text-align:left'
		},this.mailSubject);
		item.push({
			html : "�ʼ�����:",
			cls : 'common-text',
			style : 'text-align:left'
		}, this.mailContent);

		
		//add by guangsa for sendMailModel in 20090824 begin
		/******************************************************************************************/
		//����ҳ���store
		var ccStore= new Ext.data.JsonStore({ 	
				id: 'CcStore',
				url: webContext+ '/configUnit_showAllMessage.action?virId='+virtualDefinitionInfoId+"&nodeId="+nodeId,
				fields: ['id','name','mail'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.store = ccStore;
		/******************************************************************************************/
		//�ʼ���������Ϣ����
		this.mailPanel = new Ext.form.FormPanel({
			id : 'mailPanel',
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			autoScroll : true,
			defaults : {
				bodyStyle : 'padding:4px',
				overflow : 'auto'
			},
			layoutConfig : {columns : 1},
			reader: new Ext.data.JsonReader({//��������þ��൱�ڰѺ�̨���͵�typeӳ�䵽��Ӧ�Ŀؼ�type�����ˣ�
										//��ȡ��֮���Ұ����ݷŵ�record���У���store�ļ�����һ����
				    		root: 'list',
			                successProperty: '@success'
				    },[{
			              name: 'mailSubject',//�Ƕ�Ӧ��record�е�hiddenName
			              mapping: 'mailSubject'  //��Ӧ���Ƕ�ȡ֮���record
			            },{
			              name: 'mailContent',
			              mapping: 'mailContent'
			            }
			]),
			items:item,
			buttons:[{
				id : 'saveBtn',
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				text : '����',
				scope : this,
				handler : function(){
							var product = '';
							var subject = Ext.getCmp("subject").getValue();
							
							//var content = Ext.getCmp("content").getValue();
							//add by guangsa for FCKEditor in 20090824 begin
							var oEditor = FCKeditorAPI.GetInstance("content");//FCKeditor��ʼ���Ժ������FCKeditorAPI
							var content = oEditor.EditorDocument.body.innerHTML;
//							if(!reg.test(content)){
//								Ext.Msg.alert('��ʾ', '��������ʼ���ַ���Ϸ���');
//								return;
//							}
//							if(content=="<P>&nbsp;</P>"){
//								content = "";
//								alert(1);
//							}
							//add by guangsa for FCKEditor in 20090824 end
		/***************************************����У��***********************************************************/
//						if(!this.mailPanel.form.isValid()){
//							Ext.Msg.alert('��ʾ','����ɫ�ߵ��������ȷ��д');
//							return ;
//						}							
						//�����м�¼ƴװ��һ����������̨						
						subject = unicode(subject);
						content = unicode(content);	
		/***************************************����У��***********************************************************/
						Ext.getCmp('saveBtn').setDisabled(true);
						Ext.Ajax.request({
									url: webContext+ '/configUnit_saveNodeMailMessage.action',
									params : {							        	
										virtualDefinitionInfoId : virtualDefinitionInfoId,
										nodeId : nodeId,
										subject : subject,
										content : content
									},
									success : function(response, options) {
										Ext.MessageBox.alert("��ʾ", "����ɹ�", function() {											
//											ccStore.reload();
											Ext.getCmp('editBtn').enable();
											Ext.getCmp('configUnitNodeMailPanel').disableEdit();
											});
					
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("����ʧ��");
										Ext.getCmp('saveBtn').enable();
									}						
						})	
					}
				}, {
				id : 'editBtn',
				xtype : 'button',
				style : 'margin:4px 10px 4px 0',
				disabled:true,
				handler : function(){
					Ext.getCmp('configUnitNodeMailPanel').enableEdit();
				},
				text : '�༭'		
				}
			]				  
		});	
		/************************************��������form��������********************************************************************/
		this.items = [this.mailPanel];		
		this.loadInfo();
		ConfigUnitNodeMailPanel.superclass.initComponent.call(this);
	}
});
