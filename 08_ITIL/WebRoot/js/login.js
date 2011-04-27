var loginForm = {
	init : function() {
		var loginForm = function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			Ext.form.Field.prototype.msgTarget = 'side';

			var login = new Ext.form.FormPanel({
				labelWidth : 70,
				labelAlign : 'right',
				frame : true,
				renderTo : 'login',
				title : '�û���½',
				defaults : {
					width : 170
				},
				// bodyStyle : 'padding:10px 5px 0',
				defaultType : 'textfield',
				keys : [{
					key : [10, 13],
					fn : keypress
				}],
				monitorValid : true,
				items : [{
					fieldLabel : '�û��ʺ�',
					name : 'j_username',
					emptyText : '�������û��ʺ�',
					allowBlank : false
				}, {
					enableKeyEvents : true,
					fieldLabel : '�û�����',
					name : 'j_password',
					inputType : 'password',
					allowBlank : false
				}],

				buttons : [{
					text : '��¼',
					formBind : true,
					handler : submitForm
				}]
			});

			function submitForm() {

				var userName = login.form.findField('j_username').getValue();
				var passWd = login.form.findField("j_password").getValue();

				if (userName == "") {
					Ext.MessageBox.alert("\u63d0\u793a",
							"\u8bf7\u8f93\u5165\u7528\u6237\u540d\uff01",
							function() {
								login.form.findField('j_username').focus(true);
							});
					login.form.findField("j_username").markInvalid("�û�������Ϊ��!");
					return;
				}
				if (passWd == "") {
					Ext.MessageBox.alert("\u63d0\u793a",
							"\u8bf7\u8f93\u5165\u5bc6\u7801\uff01", function() {
								login.form.findField('j_password').focus(true);
							});
					login.form.findField("j_password").markInvalid("���벻��Ϊ��!");
					return;
				}
				login.getForm().submit({
					method : 'POST',
					waitTitle : 'ϵͳ��ʾ',
					waitMsg : '���ڵ�¼,���Ժ�...',
					url : serverPath + '/j_login.do',
					success : function(form, action) {
						var userName = form.findField('j_username').getValue();
						var demoData = new Cookie(document, userName, 240);
						demoData.loginName = userName;
						demoData.password = form.findField("j_password")
								.getValue();
						demoData.names = '';
						demoData.store();
						gotoIndex();
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('��ע��', action.result.errors.j_password);
						login.form.findField("j_password").setRawValue('');
					}
				});

			}

			function keypress() {
				submitForm();
			}
		}
		return loginForm;
	}
}
function gotoIndex() {
	document.body.innerHTML="";	
	Ext.DomHelper.insertHtml('afterBegin',document.body,"<div id=\"loading\" style=\"position:absolute;left: 45%;top: 40%;font-size:12px;\">"
					+ "<div class=\"loading-indicator1\">"
					+ "<img src=\"" + serverPath + "/images/blue-loading.gif\" width=\"32\" height=\"32\" style=\"margin-right:8px;float:left;vertical-align:top;\"/>"
					+ "��ӭʹ��IT����ϵͳ<br />"
					+ "<span id=\"loading-msg\">ת����...</span>" + "</div>"
					+ "</div>");
	window.location.href = serverPath + "/login.do?methodCall=logined";
}