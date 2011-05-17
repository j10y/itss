
var deptPanel = new Ext.form.FormPanel({
	id : 'deptPanel',
	title : '部门基本信息',
	frame:true,
	height:250,
	labelAlign:"right",
	labelWidth:130,
	layout:"form",
	margins:"3 0 0 0",
	items : [
		{
			allowBlank:false,
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
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"部门编号",
			labelStyle:"width:130",
			maxLength:64,
			readOnly : true,
			name:"deptNo",
			xtype:"textfield"
		},
		{
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"部门名称",
			labelStyle:"width:130",
			maxLength:255,
			name:"deptDesc",
			xtype:"textarea"
		},{
			html:'<br><br><br><br><font color="red">注意：'
				 +'<br>1,添加子部门操作，请首先选择左侧父部门，然后修改部门名称，点击【添加子部门】即可'				 				
				 +'<br>2,修改操作，请首先选择要修改的部门，然后修改部门名称，点击【修改】即可'
				 +'<br>2,删除操作，请首先选择要删除的部门，点击【删除】即可</font>'
		}],
		buttons:[{
					text:"添加子部门",
					xtype:"button",
					handler : function(){
						if(Ext.getCmp("deptPanel").form.isValid()){
							var formValues = Ext.getCmp("deptPanel").form.getValues();
							var dn = formValues.dn;
							var name = formValues.deptDesc;
							Ext.Ajax.request({  
						        url: webContext + '/dept?methodCall=add&parentNo=' + dn,   
						        async: false,
						        params:{
						        	deptName : name
						        },
						        method: 'POST',
						        success: function(response, opts) {	
						        	obj = Ext.util.JSON.decode(response.responseText);
						        	if(obj.success){				         
										Ext.Msg.alert("提示", "添加成功！");
									}			         	
						        },
						        failure: function(response, opts) {
						         	Ext.Msg.alert("提示", "添加失败！");
						        }   
						    });
						}else{
							Ext.Msg.alert("提示", "请检查您的输入！");
						}
					}
				},
				{
					text:"修改",
					xtype:"button",
					handler : function(){
						if(Ext.getCmp("deptPanel").form.isValid()){
							var formValues = Ext.getCmp("deptPanel").form.getValues();
							var dn = formValues.dn;
							var name = formValues.deptDesc;
							Ext.Ajax.request({  
						        url: webContext + '/dept?methodCall=modify&deptNo=' + dn, 
						        method: 'POST',  
						        async: false,  
						        params:{
						        	deptName : name
						        },
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
					handler : function(){
						var formValues = Ext.getCmp("deptPanel").form.getValues();
						if(!Ext.getCmp("userPanel").form.isValid()){
							Ext.Msg.alert("提示","请选择部门信息！");
							return;
						}
						var dn = formValues.dn;
						Ext.MessageBox.confirm("确认", "您确认要删除？",function(bool){
	                        if(bool=='yes'){
								Ext.Ajax.request({  
							        url: webContext + '/dept?methodCall=delete&deptNo=' + dn,   
							        async: false,  
							        success: function(response, opts) {	
							        	obj = Ext.util.JSON.decode(response.responseText);
							        	if(obj.success){				         	
											Ext.getCmp("deptPanel").form.reset();
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
					text:"请从左侧导航【组织架构】选择部门",
					xtype:"label"
				},
				"->"//,
			]
		}
});