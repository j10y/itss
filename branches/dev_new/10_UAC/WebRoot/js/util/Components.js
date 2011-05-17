/**
 * ϵͳͨ�õĹ���,��ҳͷ,ҳβ
 */


/**
 * -------------------------------------------- ������Ŀչ��
 * --------------------------------------------
 */
/**
 * ����ͷ�������������
 */
var HeaderPanel = new Ext.Panel({
	id : 'header-panel',
	layout : 'fit',
	region : 'north',
	contentEl : 'default-header',
	border : false

});
com.faceye.ui.HeaderPanel = {
	init : function() {
		var header;
		if (!header) {
			header = new Ext.Panel({
				id : 'header-panel',
				layout : 'fit',
				region : 'north',
				contentEl : 'default-header',
				border : false
			});
		}
		return header;
	}
};
/**
 * ��Ȩ����
 */
var BottomLayout = new Ext.Panel({
	id : 'bottom-layout',
	layout : 'fit',
	margins : '5 5 5 5',
	// bodyStyle:'padding:5px 5px 5px 5px',
	region : 'south',
	// title:'bottom',
	height : 25,
	html : '<p align="center">2008 <a href="http://www.digitalchina.com">��������</a> All Rights Reserved</p>'
});
com.faceye.ui.BottomLayout = {
	init : function(context) {
		var bottom;
		if (!bottom) {
			bottom = new Ext.Panel({
				id : 'bottom-layout',
				layout : 'fit',
				//style : '{padding:2px 2px 2px 2px}',
				region : 'south',
				height : 25,
				html : '<p align="center">Copyright 2000-2008 ' + context +'All Rights Reserved</p>'
			});
		}
		return bottom;
	}
};
/**
 * log panel����
 */
com.faceye.ui.LogPanel = {
	init : function() {
		var panel;
		if (!panel) {
			panel = new Ext.Panel({
				id : 'default-log-panel',
				// margins:'2 0 2 0',
				style : '{padding:2px 2px 2px 2px}',
				height : 65,
				contentEl : 'default-log-pic'
			});
		}
		return panel;
	}
};

/**
 * ����ͨ�õĻ������
 */
com.faceye.ui.Container = {
	init : function() {
		var bottom = com.faceye.ui.BottomLayout.init();
		var header = com.faceye.ui.HeaderPanel.init();
		var log = com.faceye.ui.LogPanel.init();
		var panel = new Ext.Panel({
			id : 'default-center-body-container',
			// bodyStyle : 'padding:5px',
			// margins : '5 5 5 5',
			// layout:'fit',
			style : 'padding:2px 2px 2px 2px',
			header : false
		});
		var container = new Ext.Panel({
			id : 'default-container',
			border : false,
			// layout:'fit',
			items : [header, log, panel, bottom]
		});
		// container.render(Ext.getBody());
		// // viewport.render(Ext.getBody());
		return container;
	}
};

/**
 * ����һ���򵥵�ҳ������ ��Ҫȥ��log ֻ���¶���������β����Ȩ
 */
com.faceye.ui.SimpleContainer = {
	init : function() {
		var bottom = com.faceye.ui.BottomLayout.init();
		var header = com.faceye.ui.HeaderPanel.init();
		// var log = com.faceye.ui.LogPanel.init();
		var panel = new Ext.Panel({
			id : 'default-center-body-container',
			// bodyStyle : 'padding:5px',
			// margins : '5 5 5 5',
			layout : 'fit',
			style : 'padding:2px 2px 2px 2px',
			header : false
		});
		var container = new Ext.Panel({
			id : 'default-container',
			border : false,
			// layout:'fit',
			items : [header, panel, bottom]
		});
		// container.render(Ext.getBody());
		// // viewport.render(Ext.getBody());
		return container;
	}
};


/**
 * Ĭ�ϵ�Porlet ������
 */
com.faceye.portal.PortletTools = {
	getDefaultPorletTools: function(proletId, protalColumId, refershFunction){
		var refersh = com.faceye.portal.Refersh.init(refershFunction);
		var gear = com.faceye.portal.GearTools.GearTools(proletId, protalColumId, refershFunction);
		var close = com.faceye.portal.CloseTools;
		
		var defalutTools = [
			refersh,
			gear,
			close
		];
		return defalutTools;
	}
};

/**
 * ˢ��
 */
com.faceye.portal.Refersh = {
	init : function(refersh) {
		var ref = {
			id : 'refresh',
			handler : function() {
				refersh();
			}
		}
		
		return ref;
	}
};

/**
 * portlet ������ ˢ��ʱ���趨
 */
com.faceye.portal.GearTools = {
	GearTools : function(id, portalColum, porletRefershFunction) {

		var gear = {
			id : 'gear',
			handler : function(e, target, panel) {
				var stra = panel.id.split('_');
				var id = stra[0];
				var portalColum = stra[1];
				com.faceye.portal.GearTools.init(porletRefershFunction, id,
						portalColum);
			}
		};
		Ext.Ajax.request({
			url : BP + 'portalContainerAction.do?method=getUserPorletRefersh',
			method : 'POST',
			params : {
				porletID : id,
				porlatColumnID : portalColum
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var value = responseArray.rows[0].refresh;

				var defaultRefersh = (value == "null" || value == null)
						? 1
						: value;
				var reload = porletUtil.getPorletReload(id);
				if (reload == null) {
					reload = new porletUtil.porletReload(id,
							porletRefershFunction, defaultRefersh);
				}

			},
			failure : function(response, options) {
				var reload = porletUtil.getPorletReload(id);
				if (reload == null) {
					reload = new porletUtil.porletReload(id,
							porletRefershFunction, 1);
				}
			}
		});
		return gear;
	},
	init : function(porletRefershFunction, porletID, porlatColumnID) {
		var obj = this;
		Ext.Ajax.request({
			url : BP + 'portalContainerAction.do?method=getUserPorletRefersh',
			method : 'POST',
			params : {
				porletID : porletID,
				porlatColumnID : porlatColumnID
			},
			success : function(response, options) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var value = responseArray.rows[0].refresh;

				var defaultRefersh = (value == "null" || value == null)
						? 1
						: value;

				obj.getRefershSet(porletRefershFunction, porletID,
						porlatColumnID, defaultRefersh);
			},
			failure : function(response, options) {
				obj.getRefershSet(porletRefershFunction, porletID,
						porlatColumnID, 1);
			}
		});
	},
	getRefershSet : function(porletRefershFunction, porletID, porlatColumnID,
			defaultRefersh) {

		// alert("getRefershSet():" + porletRefershFunction + "," + porletID +
		// "," + porlatColumnID + "," + defaultRefersh);
		var refreshTimeA = [["1����", 1], ["2����", 2], ["5����", 5], ["10����", 10],
				["15����", 15], ["20����", 20], ["25����", 25], ["30����", 30],["45����", 45],["60����", 60]];

		var store = new Ext.data.SimpleStore({
			fields : ['name', 'value'],
			data : refreshTimeA
		});

		var timeComb = new Ext.form.ComboBox({
			id : 'refersh-setting',
			name : 'refersh-setting',
			fieldLabel : 'ˢ��ʱ��',
			displayField : 'name',
			valueField : 'value',
			store : store,
			mode : 'local',
			triggerAction : 'all',
			emptyText : '��ѡ��ˢ��ʱ����...',
			selectOnFource : true
		});

		if (defaultRefersh == '') {
			timeComb.setValue(1);
		} else {
			timeComb.setValue(defaultRefersh);
		}

		var comitButton = new Ext.Button({
			id : 'commit-porlet-set',
			text : '�ύ',
			scope : this,
			formBind : true,
			type : 'submit',
			handler : function(btn) {
				var porletSetForm = Ext.getCmp('porlet-set-form');
				var refershValue = porletSetForm.form
						.findField("refersh-setting").getValue();

				Ext.Ajax.request({
					url : BP
							+ 'portalContainerAction.do?method=saveUserPorletRefersh',
					method : 'POST',
					params : {
						porletID : porletID,
						porlatColumnID : porlatColumnID,
						newValue : refershValue
					},
					success : function() {
						Ext.MessageBox.alert('��ʾ', 'ˢ��ʱ���Ѿ��޸ĳɹ���',
								Ext.MessageBox.INFO);
						var win = Ext.getCmp('porlet-set-form-window');
						if (win) {
							win.destroy();
						}

						var reload = porletUtil.getPorletReload(porletID);
						if (reload == null) {
							reload = new porletUtil.porletReload(porletID,
									porletRefershFunction, refershValue);
						} else {
							reload.reloadTime = refershValue;
							porletUtil.setPorletReload(porletID, reload);
						}
					}
				});
			}
		});
		var portletSet = new Ext.FormPanel({
			layout : 'form',
			id : 'porlet-set-form',
			labelWidth : 'auto',
			url : '#',
			frame : false,
			hideBorders : false,
			border : false,
			plain : false,
			labelAlign : 'right',
			monitorValid : true,
			items : [timeComb],
			buttons : [comitButton]
		});

		var setWindow = new Ext.Window({
			id : 'porlet-set-form-window',
			title : 'ˢ������',
			width : 300,
			buttonAlign : 'center',
			autoHeight : true,
			expandOnShow : true,
			// plain : true,
			modal : true,
			// border:false,
			autoScroll : false,
			shadow : true,
			items : [portletSet]
		});

		setWindow.show();
	}
};

var porletUtil = new Object();
porletUtil.proletReloadArr = new Array();
porletUtil.maxTimeValue = 61;
porletUtil.setpTimeValue = 0;
porletUtil.getPorletReload = function(id) {
	for (var i in porletUtil.proletReloadArr) {
		var tp = porletUtil.proletReloadArr[i]
		if (tp) {
			if (tp.id == id) {
				return tp;
			}
		}
	}

};

porletUtil.setPorletReload = function(id, obj) {
	this.proletReloadArr[id] = obj;
};
porletUtil.reload = function() {
	for (var i in porletUtil.proletReloadArr) {
		var tp = porletUtil.proletReloadArr[i];
		if (tp) {
			if (porletUtil.setpTimeValue % tp.reloadTime == 0) {
				//alert(tp.id );
				tp.reloadFunction();
				continue;
			}
		}
	}
	if (porletUtil.setpTimeValue < porletUtil.maxTimeValue) {
		porletUtil.setpTimeValue++;
	} else {
		porletUtil.setpTimeValue = 0;
	}
	
	setTimeout(porletUtil.reload, (60 * 1000));
};

porletUtil.porletReload = function(id, functions, time) {
	this.id = id;
	this.reloadFunction = functions;
	this.reloadTime = time;

	porletUtil.proletReloadArr[id] = this;
};

/**
 * portlet ������ Close ��ť
 */
com.faceye.portal.CloseTools = {
	id : 'close',
	handler : function(e, target, panel) {
		Ext.Msg.confirm('ɾ���Զ��幤��', '��ȷ��Ҫɾ��"' + panel.title + '"��?', function(
				btn, text) {
			if (btn == 'yes') {
				var portletId = panel.id.split('_')[0];
				var portalId = panel.ownerCt.ownerCt.id;
				// ����ɾ�����ݵ�����
				Ext.Ajax.request({
					url : BP
							+ 'portalContainerAction.do?method=removeUserPortletSubscribe',
					failure : function() {
						Ext.Msg.alert('ɾ���Զ��幤����', panel.title + 'ɾ��ʧ�ܣ�');
					},
					success : function() {
						panel.ownerCt.remove(panel, true);
						// Ext.Msg.alert('��ͳ����ɾ��', '��ͳ����ɾ���ɹ���');
					},
					params : {
						portletId : portletId,
						portalId : portalId
					}
				});
			} else {
				return;
			}
		});
	}
}

/**
 * ��
 */
com.faceye.Cicerone = {
	msg : function(title, format) {
		function createBox(t, s) {
			return [
					'<div class="msg">',
					'<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
					'<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>',
					t,
					'</h3>',
					s,
					'</div></div></div>',
					'<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
					'</div>'].join('');
		}
		if (!title) {
			title = 'FaceYeС��ʿ';
		}
		var msgCt;
		if (!msgCt) {
			msgCt = Ext.DomHelper.insertFirst(document.body, {
				id : 'msg-div'
			}, true);
		}
		msgCt.alignTo(document, 't-t', [588, 299]);
		var s = String.format.apply(String, Array.prototype.slice.call(
				arguments, 1));
		var m = Ext.DomHelper.append(msgCt, {
			html : createBox(title, s)
		}, true);
		m.slideIn('t').pause(3).ghost("t", {
			remove : true
		});
	}
};

com.faceye.SingleCicerone = {
	msg : function(title, format) {
		
		function createBox(t, s) {
			return [
					'<div class="msg">',
					'<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
					'<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>',
					t,
					'</h3>',
					s,
					'</div></div></div>',
					'<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
					'</div>'].join('');
		}
		if (!title) {
			title = 'FaceYeС��ʿ';
		}
		var msgCt;
		if (!msgCt) {
			msgCt = Ext.DomHelper.insertFirst(document.body, {
				id : 'msg-div'
			}, true);
		}
		msgCt.alignTo(document, 't-tr');
		var s = String.format.apply(String, Array.prototype.slice.call(
				arguments, 1));
		var m = Ext.DomHelper.append(msgCt, {
			html : createBox(title, s)
		}, true);
		m.slideIn('t').pause(2).ghost("t", {
			remove : true
		});
	}
};
