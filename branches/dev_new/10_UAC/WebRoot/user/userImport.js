//TODO: 国际化
Ext.apply(Ext.form.VTypes,
{
  password: function(val, field)
  {
        if (field.initialPassField)
        {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
  },
  passwordText: '两次输入的密码不一致！',

  chinese:function(val,field)
  {
        var reg = /^[\u4e00-\u9fa5]+$/i;
        if(!reg.test(val))
        {
            return false;
        }
        return true;
  },
  chineseText:'请输入中文',

  age:function(val,field)
  {
        try
        {
            if(parseInt(val) >= 18 && parseInt(val) <= 100)
                return true;
            return false;
        }
        catch(err)
        {
            return false;
        }
  },
  ageText:'年龄输入有误',

  alphanum:function(val,field)
  {
        try
        {
            if(!/\W/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  alphanumText:'请输入英文字母或是数字,其它字符是不允许的.',

  url:function(val,field)
  {
        try
        {
            if(/^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  urlText:'请输入有效的URL地址.',

  max:function(val,field)
  {
        try
        {
            if(parseFloat(val) <= parseFloat(field.max))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  maxText:'超过最大值',

  min:function(val,field)
  {
        try
        {
            if(parseFloat(val) >= parseFloat(field.min))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  minText:'小于最小值',

  datecn:function(val,field)
  {
        try
        {
            var regex = /^(\d{4})-(\d{2})-(\d{2})$/;
            if(!regex.test(val)) return false;
            var d = new Date(val.replace(regex, '$1/$2/$3'));
            return (parseInt(RegExp.$2, 10) == (1+d.getMonth())) && (parseInt(RegExp.$3, 10) == d.getDate())&&(parseInt(RegExp.$1, 10) == d.getFullYear());
        }
        catch(e)
        {
            return false;
        }
  },
  datecnText:'请使用这样的日期格式: yyyy-mm-dd. 例如:2008-06-20.',

  integer:function(val,field)
  {
        try
        {
            if(/^[-+]?[\d]+$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  integerText:'请输入正确的整数',

  minlength:function(val,field)
  {
        try
        {
            if(val.length >= parseInt(field.minlen))
                return true;
            return false
        }
        catch(e)
        {
            return false;
        }
  },
  minlengthText:'长度过小',

  maxlength:function(val,field)
  {
     try
     {
        if(val.length <= parseInt(field.maxlen))
            return true;
        return false;
     }
     catch(e)
     {
        return false;
     }
  },
  maxlengthText:'长度过大',

  ip:function(val,field)
  {
        try
        {
            if((/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(val)))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  ipText:'请输入正确的IP地址',

  phone:function(val,field)
  {
        try
        {
            if(/^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  phoneText:'请输入正确的电话号码,如:0920-29392929',

  mobilephone:function(val,field)
  {
        try
        {
            if(/(^0?[1][35][0-9]{9}$)/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  mobilephoneText:'请输入正确的手机号码',

  alpha:function(val,field)
  {
        try
        {
            if( /^[a-zA-Z]+$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  alphaText:'请输入英文字母',
    
  port:function(val,field)
  {
        try
        {
            if( /^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-5]{2}[0-3][0-5])$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  portText:'请输入正确的端口号',

  generalName:function(val,field)
  {
        try
        {
            if( /^[a-zA-Z0-9_]{1,}$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  generalNameText:'请输入字母数字或下划线',

  excelfile:function(val, filed)
  {
      try
        {
            if( /^[\w\\]*.xls[x]?|[\w\/]*.xls[x]?$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  excelfileText:"请上传Excel文档",
  
  jpegfile:function(val, filed)
  {
      try
        {
            if( /^[\w\\]*.jpg?|[\w\/]*.jpg?|[\w\\]*.jpeg?|[\w\/]*.jpeg?|[\w\\]*.JPG?|[\w\/]*.JPG?|[\w\\]*.JPEG?|[\w\/]*.JPEG?$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  jpegfileText:"请上传jpeg格式图片！"


});

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