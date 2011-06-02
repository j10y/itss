var webContext = "${pageContext.request.contextPath}";
var userImpPanel = new Ext.form.FormPanel({
	id : 'userImpPanel',
	title : '用户信息导入',
	frame:true,
	height:250,
	labelAlign:"right",
	labelWidth:130,
	layout:"form",
	fileUpload:true,
	enctype:'multipart/form-data',
	margins:"3 0 0 0",
	items : [
		{
			allowBlank:false,
			anchor:"50%",
			fieldLabel:"请选择",
			labelStyle:"width:130",
			maxLength:64,
			name:"userList",
			inputType:'file',
			vtype:"excelfile",
			xtype:"textfield"
		},{
			html:"<br><br><br><br><font color='red'>注意："
				+"<br>1,点击左下角【请下载导入模板】，下载导入模板，按照要求填信息"
				+"<br>2,填写完用户信息后选择要导入的文件，点击【导入】即可"
				+"</font>"
		}],
	buttons : [{
	xtype : 'button',
	text : '导入',
	handler: function(){
		if(userImpPanel.getForm().isValid()){
			userImpPanel.getForm().submit({						
				url:webContext + '/user?methodCall=import',						
				method:'POST',
				waitTitle : '请稍候',
				waitMsg :'正在提交表单数据，请稍候……',
				success : function(form, action){
					Ext.MessageBox.hide();
					Ext.Msg.alert('提示',"导入成功！\n" + action.result.msg);	
				},
				failure : function(form, action){
					Ext.MessageBox.hide();
					switch (action.failureType) {
			            case Ext.form.Action.CLIENT_INVALID:
			                Ext.Msg.alert('错误', '请检查输入项！');
			                break;
			            case Ext.form.Action.CONNECT_FAILURE:
			                Ext.Msg.alert('错误', '连接失败！');
			                break;
			            case Ext.form.Action.SERVER_INVALID:
			               Ext.Msg.alert('错误', action.result.msg);
			       }
				}
			});
		}else{
			Ext.Msg.alert("提示", "请检查选择的文件格式是否正确！");
		}
	}}
	],
	buttonAlign : 'center',
	bbar:{
		xtype:"toolbar",
		items:[
			{
				html:"<a href='user/userlist.xlsx'>请下载导入模板</a>",
				xtype:"label",
				id:'btnUserSelect'
			}
		]
	}
});