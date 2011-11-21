/**
 * 系统通用的功能,如页头,页尾
 */


/**
 * -------------------------------------------- 结束栏目展现
 * --------------------------------------------
 */
/**
 * 定义头部导航区域面板
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
 * 版权部分
 */
var BottomLayout = new Ext.Panel({
	id : 'bottom-layout',
	layout : 'fit',
	margins : '5 5 5 5',
	// bodyStyle:'padding:5px 5px 5px 5px',
	region : 'south',
	// title:'bottom',
	height : 25,
	html : '<p align="center">2008 <a href="http://www.digitalchina.com">神州数码</a> All Rights Reserved</p>'
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
 * log panel定义
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
 * 定义通用的基础框架
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
 * 定义一个简单的页面容器 主要去掉log 只留下顶部导航和尾部版权
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
 * 默认的Porlet 工具条
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
 * 刷新
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
 * portlet 工具条 刷新时间设定
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
		var refreshTimeA = [["1分钟", 1], ["2分钟", 2], ["5分钟", 5], ["10分钟", 10],
				["15分钟", 15], ["20分钟", 20], ["25分钟", 25], ["30分钟", 30],["45分钟", 45],["60分钟", 60]];

		var store = new Ext.data.SimpleStore({
			fields : ['name', 'value'],
			data : refreshTimeA
		});

		var timeComb = new Ext.form.ComboBox({
			id : 'refersh-setting',
			name : 'refersh-setting',
			fieldLabel : '刷新时间',
			displayField : 'name',
			valueField : 'value',
			store : store,
			mode : 'local',
			triggerAction : 'all',
			emptyText : '请选择刷新时间间隔...',
			selectOnFource : true
		});

		if (defaultRefersh == '') {
			timeComb.setValue(1);
		} else {
			timeComb.setValue(defaultRefersh);
		}

		var comitButton = new Ext.Button({
			id : 'commit-porlet-set',
			text : '提交',
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
						Ext.MessageBox.alert('提示', '刷新时间已经修改成功！',
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
			title : '刷新设置',
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
 * portlet 工具条 Close 按钮
 */
com.faceye.portal.CloseTools = {
	id : 'close',
	handler : function(e, target, panel) {
		Ext.Msg.confirm('删除自定义工具', '您确认要删除"' + panel.title + '"吗?', function(
				btn, text) {
			if (btn == 'yes') {
				var portletId = panel.id.split('_')[0];
				var portalId = panel.ownerCt.ownerCt.id;
				// 发送删除数据的请求
				Ext.Ajax.request({
					url : BP
							+ 'portalContainerAction.do?method=removeUserPortletSubscribe',
					failure : function() {
						Ext.Msg.alert('删除自定义工具条', panel.title + '删除失败！');
					},
					success : function() {
						panel.ownerCt.remove(panel, true);
						// Ext.Msg.alert('传统导航删除', '传统导航删除成功！');
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
 * 向导
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
			title = 'FaceYe小贴士';
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
			title = 'FaceYe小贴士';
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
