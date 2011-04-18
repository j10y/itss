/*
 * 
 * ɾ����Ʒ��
 */
function delProduct(elem) {
	Ext.MessageBox.confirm("ȷ����Ϣ", "ȷ��Ҫɾ���ò�Ʒ��ô�����ܻ�ɾ����Ӧ�Ĳ�Ʒ��Ϣ", function(tes) {
		if (tes == "yes") {
			var projectValue = document.getElementById('product').innerHTML;
			var projectTextId = document.getElementById('productId').innerText;
			var elemText = elem.innerText;
			var valueArry = projectValue.split("<BR>");
			var flag;
			for (var i = 0; i < valueArry.length; i++) {
				if (valueArry[i].indexOf(elemText) > 0) {
					flag = i;
				}
			}
			var projectText = "";
			for (var i = 0; i < valueArry.length; i++) {
				if (i != flag) {
					if (projectText == "") {
						projectText = projectText + valueArry[i];
					} else {
						projectText = projectText + "<BR>" + valueArry[i];
					}
				}
			}
			document.getElementById('product').innerHTML = projectText;
			var idArry = projectTextId.split("@");
			projectTextId = "";
			for (var i = 0; i < idArry.length; i++) {
				if (i != flag) {
					if (projectTextId == "") {
						projectTextId = projectTextId + idArry[i];
					} else {
						projectTextId = projectTextId + "@" + idArry[i];
					}
				}
			}
			document.getElementById('productId').innerText = projectTextId;
		}
	}, this);

}

ApplyPanel = Ext.extend(Ext.Panel, {
	id : "applyPanel",
	height : 'auto',
	width : 'auto',
	layout : 'fit',
	frame : true,
	items : this.items,

	/*
	 * �����formpanel
	 */
	
		
	
	formSubmit : function() {

	},
	getMainForm : function() {
		this.planCombo = new Ext.form.ComboBox({
			xtype : 'combo',
			//hiddenName : 'planCombo',
			id : 'planCombo',
			width : 200,
			style : '',
			fieldLabel : '����',
			//lazyRender : true,
			displayField : 'name',
			valueField : 'id',
			emptyText : '��ѡ��...',
			allowBlank : true,
			typeAhead : true,
			name : 'planCombo',
			triggerAction : 'all',
			minChars : 50,
			queryDelay : 700,
			store : new Ext.data.JsonStore({
				url : webContext+ '/updatePlanAction_getSRExpandPlanComms.action?dataId='+ this.requireId,
				fields : ['id', 'name'],
				totalProperty : 'rowCount',
				root : 'data',
				id : 'id'
			}),
			listeners : {
				'beforequery' : function(queryEvent) {
					//alert(this.requireId);
				}
			}
		});
		
		var businessAccount = new Ext.form.Hidden({
				xtype : 'hidden',
				id : 'businessAccountId',
				fieldLabel : 'ID'
			})
		
		var searchForm = new Ext.form.FormPanel({
			title : '��/��������',
			id : "formPanel",
			collapsible : true,
			frame : true,
			autoScroll : true,
			layout : "table",
			defaults : {
				height : 30
			},
			layoutConfig : {
				columns : 1
			},
			items : [{
				xtype : 'fieldset',
				title : '����������Ŀ',
				height : 150,
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults : {
					bodyStyle : 'padding:8px 0px 8px 0px'
				},
				items : [
				{
					html : "����ƻ�:&nbsp;",
					cls : 'common-text',
					style : 'width:150;text-align:right'
				}, {
					xtype : "panel",
					colspan : 3,
					layout : "table",
					defaults : {
						bodyStyle : 'padding:10px 400px 10px 0px'

					},
					items : [
					businessAccount,
					this.planCombo,
					{
						xtype : "button",
						iconCls : 'add',
						text : "����",
						handler : function() {
							var varTMPId = Ext.getCmp("planCombo").getValue();
							if (varTMPId == "") {
								Ext.getCmp("planCombo").setValue("");
								Ext.Msg.alert("��ʾ", "��ѡ������");
								return;
							}
							var varTMP = Ext.getCmp("planCombo").getRawValue();
							var var1 = document.getElementById('productId').innerText;
							var idArry = var1.split("@");
							for (var i = 0; i < idArry.length; i++) {
								idArr = idArry[i].split(":");
								if (idArr[0] == varTMPId) {
									Ext.Msg.alert("��ʾ", "�Ѿ���ӣ������ظ����");
									return;
								}
							}
							showWin();

						}
					}]
				}, {
					html : "��ѡ�񸶿���Ŀ:&nbsp;",
					colspan : 1,
					cls : 'common-text',
					style : 'width:150;text-align:right',
					id : "ccc"
				}, {
					id : "product",
					style : 'width:550',
					colspan : 3
				}, {
					id : "productId",
					hidden : true,
					style : 'width:550',
					colspan : 3
				}]
			}, {
				xtype : "fieldset",
				title : "��������",
				layout : 'table',
				defaults : {
					bodyStyle : 'padding:10px 40px 10px 40px'
				},
				items : [{
					xtype : 'textarea',
					id : "applyDescn",
					fieldLabel : '��Ŀ����',
					height : 550,
					width : 550
				}]
			}],
			buttons : [
			{
				text : "�ݴ�",
				pressed : true,
				scope : this,
				iconCls : 'save',
				handler : this.formSave
			},
			{
				text : "�ύ����",
				pressed : true,
				iconCls : 'submit',
				handler : this.formSubmit
			}, {
				text : "�� ��",
				pressed : true,
				iconCls : 'back',
				handler : function() {

					history.go(-1);
				}
			}],
			buttonsalign : 'left'
		});
		return searchForm;
	},
	formSave : function(){
		var info = document.getElementById('productId').innerText;
		var descn = Ext.getCmp('applyDescn').getValue();
		var dataId = Ext.getCmp('businessAccountId').getValue();
		var curRequireId = this.requireId;
		alert(curRequireId);
		Ext.Ajax.request({
			url : webContext+ '/updatePlanAction_saveBusinessAccount.action',
			params : {
				planInfo : info,
				descn : descn,
				requireId : curRequireId,
				dataId :dataId
			},
			success : function(response, options) {
				
				Ext.Msg.alert("��ʾ", "����ɹ�");
			},
			failure : function(response, options) {
				Ext.MessageBox.alert("��ʾ", "����ʧ��");
			}
		}, this);
			
	},
	initComponent : function() {
		this.searchForm = this.getMainForm();
		this.items = [this.searchForm];
		ApplyPanel.superclass.initComponent.call(this);
	}
});

function showWin() {
	var window = new Ext.Window({
		id : 'tempDistributor',
		title : '���������',
		width : 350,
		height : 100,
		modal : "local",
		layout : 'table',
		defaults : {
			bodyStyle : 'padding:8px 0px 8px 0px',
			overflow : 'auto',
			baseCls : "x-plain"
		},
		layoutConfig : {
			columns : 4
		},
		buttonAlign : 'center',
		buttons : [{
			id : 'saveButton',
			text : '����',
			pressed : true,
			handler : function() {
				var money = Ext.getCmp("money").getValue();
				if (money == "") {
					Ext.Msg.alert("��ʾ", "����Ϊ��");
					return;
				}
				var varTMPId = Ext.getCmp("planCombo").getValue();
				var varTMP = Ext.getCmp("planCombo").getRawValue();
				var var1 = document.getElementById('product').innerText;
				if (var1 == "") {
					document.getElementById('product').innerHTML = '<a href="javascript:;" onclick="delProduct(this)" >'
							+ varTMP + ":" + money + '</a>';
					document.getElementById('productId').innerText = varTMPId
							+ ":" + money;
				} else {
					var var2 = document.getElementById('product').innerHTML;
					document.getElementById('product').innerHTML = var2
							+ '<br><a href="javascript:;" onclick="delProduct(this)" >'
							+ varTMP + ":" + money + '</a>';
					var2 = document.getElementById('productId').innerText;
					document.getElementById('productId').innerText = var2 + "@"
							+ varTMPId + ":" + money;
				}
				Ext.getCmp("planCombo").setValue("");
				window.close();
			}
		}, {
			text : '�ر�',
			pressed : true,
			handler : function() {
				window.close();
			}
		}],
		items : [{
			html : "ʵ�ʷ���:&nbsp",
			cls : 'common-text',
			style : 'width:100;text-align:right'
		}, {
			id : 'money',
			xtype : 'numberfield',
			//				allowBlank : false,
			blankText : '��ѡ��Ϊ������',
			width : 200
		}]
	});
	window.show();
}