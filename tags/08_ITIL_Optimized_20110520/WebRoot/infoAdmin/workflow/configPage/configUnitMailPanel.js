ConfigUnitNodeMailPanel = Ext.extend(Ext.Panel, {
	id : "configUnitNodeMailPanel",
	//title : '邮件模板信息',
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
		
		//邮件内容信息fieldset中内容
		this.mailSubject = new Ext.form.TextField({
			id : 'subject',
			name : 'mailSubject',
			fieldLabel : '邮件标题',	
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
			html : "邮件主题:",
			cls : 'common-text',
			style : 'text-align:left'
		},this.mailSubject);
		item.push({
			html : "邮件内容:",
			cls : 'common-text',
			style : 'text-align:left'
		}, this.mailContent);

		
		//add by guangsa for sendMailModel in 20090824 begin
		/******************************************************************************************/
		//整个页面的store
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
		//邮件抄送人信息内容
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
			reader: new Ext.data.JsonReader({//这个的作用就相当于把后台发送的type映射到相应的控件type当中了，
										//读取到之后并且把数据放到record当中，和store的加载是一样的
				    		root: 'list',
			                successProperty: '@success'
				    },[{
			              name: 'mailSubject',//是对应的record中的hiddenName
			              mapping: 'mailSubject'  //对应的是读取之后的record
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
				text : '保存',
				scope : this,
				handler : function(){
							var product = '';
							var subject = Ext.getCmp("subject").getValue();
							
							//var content = Ext.getCmp("content").getValue();
							//add by guangsa for FCKEditor in 20090824 begin
							var oEditor = FCKeditorAPI.GetInstance("content");//FCKeditor初始化以后才能用FCKeditorAPI
							var content = oEditor.EditorDocument.body.innerHTML;
//							if(!reg.test(content)){
//								Ext.Msg.alert('提示', '您输入的邮件地址不合法！');
//								return;
//							}
//							if(content=="<P>&nbsp;</P>"){
//								content = "";
//								alert(1);
//							}
							//add by guangsa for FCKEditor in 20090824 end
		/***************************************数据校验***********************************************************/
//						if(!this.mailPanel.form.isValid()){
//							Ext.Msg.alert('提示','带红色线的项必须正确填写');
//							return ;
//						}							
						//把所有记录拼装成一个串传到后台						
						subject = unicode(subject);
						content = unicode(content);	
		/***************************************数据校验***********************************************************/
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
										Ext.MessageBox.alert("提示", "保存成功", function() {											
//											ccStore.reload();
											Ext.getCmp('editBtn').enable();
											Ext.getCmp('configUnitNodeMailPanel').disableEdit();
											});
					
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("保存失败");
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
				text : '编辑'		
				}
			]				  
		});	
		/************************************用来加载form表单的数据********************************************************************/
		this.items = [this.mailPanel];		
		this.loadInfo();
		ConfigUnitNodeMailPanel.superclass.initComponent.call(this);
	}
});
