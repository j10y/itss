Ext.ns("App");
App.LoginWin = function(b) {
	this.isCodeEnabled = b;
	var a = new Ext.form.FormPanel({
		id : "LoginFormPanel",
		bodyStyle : "padding-top:6px",
		defaultType : "textfield",
		columnWidth : 0.75,
		labelAlign : "right",
		labelWidth : 55,
		labelPad : 0,
		border : false,
		layout : "form",
		defaults : {
			allowBlank : false,
			anchor : "90%,120%",
			selectOnFocus : true
		},
		items : [ {
			name : "username",
			fieldLabel : "账      号",
			cls : "text-user",
			blankText : "账号不能为空"
		}, {
			name : "password",
			fieldLabel : "密      码",
			blankText : "密码不能为空",
			cls : "text-lock",
			inputType : "password"
		}, {
			name : "checkCode",
			id : "checkCode",
			fieldLabel : "验证码",
			hidden : true,
			cls : "text-code",
			blankText : "验证码不能为空"
		}, {
			xtype : "container",
			id : "codeContainer",
			layout : "table",
			defaultType : "textfield",
			hideLabel : false,
			layoutConfig : {
				columns : 3
			}
		}, {
			xtype : "checkbox",
			name : "_spring_security_remember_me",
			boxLabel : "让系统记住我 "
		} ]
	});
	if (this.isCodeEnabled != "undefined" && this.isCodeEnabled != ""
			&& this.isCodeEnabled != "1") {
		a.remove(Ext.getCmp("checkCode"));
	} else {
		Ext.getCmp("checkCode").show();
		var d = [
				{
					width : 55,
					xtype : "label",
					text : "　　　　"
				},
				{
					width : 150,
					id : "loginCode",
					xtype : "panel",
					border : false,
					html : '<img border="0" height="30" width="150" src="'
							+ __ctxPath + '/CaptchaImg"/>'
				}, {
					width : 55,
					xtype : "panel",
					border : false,
					bodyStyle : "font-size:12px;padding-left:5px",
					html : '<a href="javascript:refeshCode()">换一张</a>'
				} ];
		var f = Ext.getCmp("codeContainer");
		f.add(d);
		f.doLayout();
	}
	var e = function() {
		if (a.form.isValid()) {
			a.form.submit({
				waitTitle : "请稍候",
				waitMsg : "正在登录......",
				url : __ctxPath + "/login.do",
				success : function(g, h) {
					handleLoginResult(h.result);
				},
				failure : function(g, h) {
					handleLoginResult(h.result);
					g.findField("password").setRawValue("");
					g.findField("username").focus(true);
				}
			});
		}
	};
	var c = new Ext.Window({
		id : "LoginWin",
		title : "用户登录",
		iconCls : "login-icon",
		bodyStyle : "background-color: white",
		border : true,
		closable : false,
		resizable : false,
		renderTo : 'loginArea',
		buttonAlign : "center",
		height : 220,
		width : 350,
		layout : {
			type : "vbox",
			align : "stretch"
		},
		keys : {
			key : Ext.EventObject.ENTER,
			fn : e,
			scope : this
		},
		items : [
				{
					xtype : "panel",
					border : false,
					layout : "column",
					items : [a]
				} ],
		buttons : [ {
			text : "登录",
			iconCls : "btn-login",
			handler : e.createDelegate(this)
		}, {
			text : "重置",
			iconCls : "btn-login-reset",
			handler : function() {
				a.getForm().reset();
			}
		} ]
	});
	return c;
};
function handleLoginResult(a) {
	if (a.success) {
		Ext.getCmp("LoginWin").hide();
		var b = new Ext.ProgressBar({
			text : "正在登录..."
		});
		b.show();
		window.location.href = __ctxPath + "/index.jsp";
	} else {
		Ext.MessageBox.show({
			title : "操作信息",
			msg : a.msg,
			buttons : Ext.MessageBox.OK,
			icon : Ext.MessageBox.ERROR
		});
	}
}
function refeshCode() {
	var a = Ext.getCmp("loginCode");
	a.body.update('<img border="0" height="30" width="150" src="' + __ctxPath
			+ "/CaptchaImg?rand=" + Math.random() + '"/>');
}