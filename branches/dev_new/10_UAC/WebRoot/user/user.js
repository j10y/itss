//TODO: 国际化
var userPanel = new Ext.form.FormPanel({
	id : 'userPanel',
	title : '个人基本信息',
	frame:true,
	//height:250,
	labelAlign:"right",
	labelWidth:130,
	layout:"form",
	//margins:"3 0 0 0",
	fileUpload :true,
	enctype:'multipart/form-data',
	items : [
		{
			//allowBlank:false,
			anchor:"50%",
			labelStyle:"width:130",
			maxLength:64,
			readOnly : true,
			name:"dn",
			xtype:"textfield",
			lableHidden:true,
			hidden:true
		},		
		{
			id:'userUid',
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"账号",
			labelStyle:"width:130",
			maxLength:64,
			name:"uid",
			xtype:"textfield"
		},
		{
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"密码",
			labelStyle:"width:130",
			maxLength:64,
			name:"password",
			//value:'000000',
			inputType:'password',
			xtype:"textfield"
		},
		{
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"姓名",
			labelStyle:"width:130",
			maxLength:255,
			name:"cn",
			xtype:"textfield"
		},{
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"人员类型",
			labelStyle:"width:130",
			maxLength:255,
			name:"userType",
			displayField:'value',
			hiddenName:'userType',
			valueField:'id',
			store : new Ext.data.SimpleStore({
				fields:['id','value'],
				data:[[1, '员工'],[2, '客户'],[3, '供应商'],[4,'特殊用户']]
			}),
			mode:'local',
			xtype:"combo"
		},		
		{
			anchor:"50%",
			fieldLabel:"部门",
			labelStyle:"width:130",
			maxLength:255,
			id:'departmentNumber1',
			name:"departmentNumber",
			xtype:"combo",
			typeAhead : true,
			defaultParam : "",
			triggerAction : "all",
			displayField : 'deptName',
			valueField : "deptNo",
			hiddenName : 'departmentNumber',
			store : new Ext.data.JsonStore({
				url : webContext + '/dept?methodCall=searchDepts',
				fields : ['deptNo', 'deptName'],
				totalProperty : 'rowCount',
				root : 'data',
				listeners : {
					beforeload : function(store, opt) {
						var param = Ext.getCmp('departmentNumber1').defaultParam;
						if (opt.params['param'] == undefined) {
							opt.params['param'] = param;
						}
					}
				}
			}),
			listeners : {
				'beforequery' : function(queryEvent) {
					var param = queryEvent.query;
					this.defaultParam = param;
					Ext.getCmp("departmentNumber1").store.load({
								params : {
									param : param,
									start : 0
								}
							});
					return true;
				},
				"blur" : function(combo){
					var nowVal = combo.getRawValue();
					var nowId = combo.getValue();
					if(nowVal==""){
						combo.setValue("")
					}else if(nowId==""){
						combo.setValue("");
						Ext.Msg.alert("提示信息","不能直接输入，请查询后选择！");
					}
				}
			}
		},
		/*{
			//allowBlank:false,
			anchor:"50%",
			fieldLabel:"部门编号",
			labelStyle:"width:130",
			maxLength:255,
			name:"departmentNumber",
			xtype:"textfield"
		},*/
		{
			//allowBlank:false,
			anchor:"50%",
			fieldLabel:"职位",
			labelStyle:"width:130",
			maxLength:255,
			name:"title",
			xtype:"textfield"
		},
		{
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"邮箱",
			labelStyle:"width:130",
			maxLength:255,
			name:"mail",
			xtype:"textfield"
		},
		{
			//allowBlank:false,
			anchor:"50%",
			fieldLabel:"座机号码",
			labelStyle:"width:130",
			maxLength:255,
			name:"telephoneNumber",
			xtype:"textfield"
		},
		{
			//allowBlank:false,
			anchor:"50%",
			fieldLabel:"手机号码",
			labelStyle:"width:130",
			maxLength:255,
			name:"mobile",
			xtype:"textfield"
		},
		{
			//allowBlank:false,
			anchor:"50%",
			fieldLabel:"传真",
			labelStyle:"width:130",
			maxLength:255,
			name:"facsimileTelephoneNumber",
			xtype:"textfield"
		},
		{
			//allowBlank:false,
			anchor:"50%",
			fieldLabel:"姓",
			labelStyle:"width:130",
			maxLength:255,
			name:"sn",
			xtype:"textfield",
			hidden:true,
			hideLabel:true
		},
		{
			anchor:"50%",
			fieldLabel:"请选择肖像",
			labelStyle:"width:130",
			maxLength:64,
			name:"photo",
			vtype:'jpegfile',
			inputType:'file',
			xtype:"textfield"
		},
		{
			anchor:"50%",
			//fieldLabel:"账号备份",
			labelStyle:"width:130",
			maxLength:64,
			name:"uidCopy",
			id:"uidCopy",
			xtype:"textfield",
			hidden:true
		},{
			id:"help",
			html:"<br><font color='red'>注意："
				+"<br>1,添加新用户操作，填写完毕用户信息（账号必须修改），点击【添加新用户】即可"
				+"<br>2,修改用户操作，请首先选择要修改的用户（账号不可以修改，即使修改系统会自动重置），点击【修改】即可"
				+"<br>3,删除用户操作，请首先选择要删除的用户，点击【删除】即可"
				+"</font>"
		}],
		buttons:[{
					text:"添加新用户",
					xtype:"button",
					id:'btnUserAdd',
					handler : function(){
						if(Ext.getCmp("userPanel").form.isValid()){
							var formValues = Ext.getCmp("userPanel").form.getValues();
							var dn = formValues.dn;
							
							userPanel.form.submit({
				                method: 'POST',
				                url: webContext + '/user?methodCall=add'
				                	+ '&dn=' + formValues.dn
				                	+ '&uid=' + formValues.uid
				                	+ '&password=' + formValues.password
				                	+ '&cn=' + formValues.cn
				                	+ '&sn=' + formValues.uid + '_' + formValues.cn
				                	+ '&departmentNumber=' + formValues.departmentNumber
				                	+ '&title=' + formValues.title
				                	+ '&mail=' + formValues.mail
				                	+ '&telephoneNumber=' + formValues.telephoneNumber
				                	+ '&mobile=' + formValues.mobile
				                	+ '&facsimileTelephoneNumber=' + formValues.facsimileTelephoneNumber
				                	+ '&userType=' + formValues.userType,
				                success: function(form, action) {
									Ext.Msg.alert("提示", "添加成功！");
				                },
				                failure : function(form, action) {
									Ext.Msg.alert("提示", "添加失败！原因:\n" + action.result.msg);
				                }
				            });
				            
							/*Ext.Ajax.request({  
						        url: webContext + '/user?methodCall=add', 
						        params : {
						        	dn : formValues.dn,
						        	uid : formValues.uid,
						        	password : formValues.password,
						        	cn : formValues.cn,
						        	sn : formValues.uid + '_' + formValues.cn,
						        	departmentNumber : formValues.departmentNumber,
						        	title : formValues.title,
						        	mail : formValues.mail,
						        	telephoneNumber : formValues.telephoneNumber,
						        	mobile : formValues.mobile,
						        	facsimileTelephoneNumber : formValues.facsimileTelephoneNumber,
						        	userType : formValues.userType
						        },
						        async: false,
						        method: 'POST',
						        success: function(response, opts) {	
						        	obj = Ext.util.JSON.decode(response.responseText);
						        	if(obj.success){				         
										Ext.Msg.alert("提示", "添加成功！");
									}else{
										Ext.Msg.alert("提示", "添加失败！原因:\n" + obj.msg);
									}			         	
						        },
						        failure: function(response, opts) {
						         	Ext.Msg.alert("提示", "添加失败！");
						        }   
						    });*/
						}else{
							Ext.Msg.alert("提示", "请检查您的输入！");
						}
					}
				},
				{
					text:"修改",
					xtype:"button",
					id:'btnUserModify',
					handler : function(){
						if(Ext.getCmp("userPanel").form.isValid()){
							var formValues = Ext.getCmp("userPanel").form.getValues();
							userPanel.form.submit({
				                method: 'POST',
				                url: webContext + '/user?methodCall=modify'
				                	/*+ '&dn=' + formValues.dn
				                	+ '&uid=' + formValues.uid
				                	+ '&password=' + formValues.password
				                	+ '&cn=' + formValues.cn
				                	+ '&sn=' + formValues.uid + '_' + formValues.cn
				                	+ '&departmentNumber=' + formValues.departmentNumber
				                	+ '&title=' + formValues.title
				                	+ '&mail=' + formValues.mail
				                	+ '&telephoneNumber=' + formValues.telephoneNumber
				                	+ '&mobile=' + formValues.mobile
				                	+ '&facsimileTelephoneNumber=' + formValues.facsimileTelephoneNumber
				                	+ '&userType=' + formValues.userType*/,
				                /*params : {
						        	dn : formValues.dn,
						        	uid : formValues.uidCopy,
						        	password : formValues.password,
						        	cn : formValues.cn,
						        	sn : formValues.uid + '_' + formValues.cn,
						        	departmentNumber : formValues.departmentNumber,
						        	title : formValues.title,
						        	mail : formValues.mail,
						        	telephoneNumber : formValues.telephoneNumber,
						        	mobile : formValues.mobile,
						        	facsimileTelephoneNumber : formValues.facsimileTelephoneNumber,
						        	userType : formValues.userType
						        },*/
				                success: function(form, action) {
									//Ext.Msg.alert("提示", "修改成功！");
					        		if(servletPath==="/user/userModify.jsp"){
					        			Ext.Msg.confirm("确认", "修改成功,是否关闭？", function(btn, text){
					        				if(btn=="yes"){
					        					window.close();
					        					self.close();
					        				}
					        			});
					        		}else{
										Ext.Msg.alert("提示", "修改成功！");
										//修改成功后，用户类型可能改变，那么uid也改变了，需要重新load
										//初始化数据
									    Ext.Ajax.request({  
									        url: webContext + '/user?methodCall=getDetailByUid', 
									        params : {
									        	uid : formValues.uid
									        },
									        async: false,
									        method: 'POST',
									        success: function(response, opts) {	
									        	obj = Ext.util.JSON.decode(response.responseText);
									        	if(obj.success){
									        		Ext.getCmp("userPanel").form.setValues(obj);
									        		Ext.getCmp("uidCopy").setValue(obj.uid);
									        		
									        		Ext.getCmp("departmentNumber1").setValue(obj.deptNo);
									        		Ext.getCmp("departmentNumber1").setRawValue(obj.deptName);
												}			         	
									        },
									        failure: function(response, opts) {
									         	Ext.Msg.alert("提示", "DN为：" + dn + "的用户信息获取失败！");
									        }   
									    });
										
					        		}
				                },
				                failure : function(form, action) {
									Ext.Msg.alert("提示", "修改失败！");
				                }
				            });
							
							return ;
							
							Ext.Ajax.request({  
						        url: webContext + '/user?methodCall=modify', 
						        method: 'POST', 
						        isUpload:true,
						        params : {
						        	dn : formValues.dn,
						        	uid : formValues.uidCopy,
						        	password : formValues.password,
						        	cn : formValues.cn,
						        	sn : formValues.uid + '_' + formValues.cn,
						        	departmentNumber : formValues.departmentNumber,
						        	title : formValues.title,
						        	mail : formValues.mail,
						        	telephoneNumber : formValues.telephoneNumber,
						        	mobile : formValues.mobile,
						        	facsimileTelephoneNumber : formValues.facsimileTelephoneNumber,
						        	userType : formValues.userType,
						        	photo: formValues.photo
						        }, 
						        async: false,  
						        success: function(response, opts) {	
						        	obj = Ext.util.JSON.decode(response.responseText);
						        	if(obj.success){
										Ext.Msg.alert("提示", "修改成功！");
									}			         	
						        },
						        failure: function(response, opts) {
						         	Ext.Msg.alert("提示", "修改失败！");
						        }   
						    });
						}else{
							Ext.Msg.alert("提示", "请检查您的输入！");
						}
					}
				},
				{
					text:"删除",
					xtype:"button",
					id:'btnUserDel',
					handler : function(){
						var formValues = Ext.getCmp("userPanel").form.getValues();
						if(formValues.uid=='admin'){
							Ext.Msg.alert("提示","超级管理员admin不可以删除！");
							return;
						}
						if(!Ext.getCmp("userPanel").form.isValid()){
							Ext.Msg.alert("提示","请选择用户信息！");
							return;
						}
						var dn = formValues.dn;
						Ext.MessageBox.confirm("确认", "您确认要删除？",function(bool){
	                        if(bool=='yes'){
	                       		Ext.Ajax.request({  
							        url: webContext + '/user?methodCall=delete&dn=' + dn,   
							        async: false, 
							        params : {
								        	dn : formValues.dn,
								        	uid : formValues.uid,
								        	password : formValues.password,
								        	cn : formValues.cn,
								        	sn : formValues.sn,
								        	departmentNumber : formValues.departmentNumber,
								        	title : formValues.title,
								        	mail : formValues.mail,
								        	telephoneNumber : formValues.telephoneNumber,
								        	mobile : formValues.mobile,
								        	facsimileTelephoneNumber : formValues.facsimileTelephoneNumber,
								        	userType : formValues.userType
								        }, 
							        success: function(response, opts) {	
							        	obj = Ext.util.JSON.decode(response.responseText);
							        	if(obj.success){				         	
											Ext.getCmp("userPanel").form.reset();
											Ext.Msg.alert("提示", "删除成功！");
										}			         	
							        },
							        failure: function(response, opts) {
							         	Ext.Msg.alert("提示", "删除失败！");
							        }   
							    });     
	                        }
	                    });
					}
				}],
		buttonAlign:'center',
		bbar:{
			xtype:"toolbar",
			items:[
				{
					text:"请从左侧导航【人员架构】选择用户",
					xtype:"label",
					id:'btnUserSelect'
				},
				"->"//,
				
			]
		}
});