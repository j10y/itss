/**
 * portlet subscribe 用户订阅portlet
 */
com.faceye.portal.UserSubscribePortlet = {
	init : function(portalId) {
		var panel = this.buildPanelBody();
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : BP
						+ 'portletAction.do?method=getPortletsByUserIdForSubscribe'
			}),
			reader : new Ext.data.JsonReader({
				root : 'root',
				totalProperty : 'total',
				id : 'id',
				fields : ['id', 'name', 'url', 'imageSrc', 'source', 'init',
						'flag']
			})
		});
		// function renderTopic(value, p, r) {
		// var source = '<table id="user-subscribe-porltet-table'
		// + r.data['id'] + '"><tr><td>' + r.data['name'];
		// source += '</td></tr>';
		// source += '<tr><td><img src="/faceye' + r.data['imageSrc']
		// + '"></img></td></tr>';
		// source += '<tr><td><input type="button" id="user-subscribe'
		// + r.data['id'] + '" value="添加" onclick="portletSubscrie(\''
		// + r.data['id'] + '\',\'' + portalId + '\')"'
		// + '></input></td></tr>'
		// source += '</table>';
		// return source;
		// }
		// var action = new Ext.ux.grid.RowAction({
		// iconCls : 'add-user-portlet',
		// qtip : '添加'
		// });
		// var cm = new Ext.grid.ColumnModel([{
		// id : 'id',
		// header : 'ID',
		// dataIndex : 'id',
		// hidden : true
		// }, {
		// // header : "名称",
		// dataIndex : 'name',
		// renderer : renderTopic
		// }, action]);

		var tpl = new Ext.XTemplate(
				'<div id="portlet-subscribe-view-template-div"><table id="portlet-subscribe-view-template-table"><tr>'
						+ '<tpl for=".">'
						+ '<td>'
						+ '<table>'
						+ '<tr>'
						+ '<td><img src="{imageSrc}" title="{name}"/></td>'
						+ '</tr>'
				
						+ '<tr>'
						+ '<td>'
						+ '<tpl if="this.isSubscribed(flag)==false">'
						+ '<div id="user-subscribe-info{id}">'
						+ '<input type="button"  id="user-subscribe{id}"  value="添加{name}" onclick="portletSubscrie(\'{id}\',\''
						+ portalId
						+ '\')"></input>'
						+ '</div>'
						+ '</tpl>'
						+ '<tpl if="this.isSubscribed(flag)==true">'
						+ '<div id="user-subscribe-info{id}">'
						+ '<font color="red">已订阅{name}</font><br/>'
						+ '<input type="button"  id="user-unSubscribe{id}"  value="取消订阅" onclick="unPortletSubscribe(\'{id}\',\''
						+ portalId
						+ '\')"></input>'
						+ '</div>'
						+ '</tpl>'
						+ '</td>'
						+ '</tr>'
						+ '</table>'
						+ '</td>'
						+ '<tpl if="this.isEnter(xindex)==true">'
						+ '</tr>'
						+ '<tr>' + '</tpl>' + '</tpl></table></div>', {
					isEnter : function(index) {
						if (index % 3 === 0) {
							return true;
						} else {
							return false;
						}
					},
					isSubscribed : function(flag) {
						if (flag == 'true') {
							return true;
						} else {
							return false;
						}
					}
				});
		store.load({
			params : {
				start : 0,
				limit : 15,
				portalId : portalId
			}
		});
		var dataView = new Ext.DataView({
			store : store,
			tpl : tpl,
			loadingText : '正在加载...',
			style : 'overflow:auto',
			itemSelector : 'div-column',
			multiSelect : true,
			autoHeight : true
		});

		// cm.defaultSortable = true;
		//
		// var grid = new Ext.grid.GridPanel({
		// autoHeight : true,
		// border : false,
		// bodyStyle : 'width:100%',
		// loadMask : true,
		// header : false,
		// stripeRows : true,
		// // trackMouseOver : true,
		// // layoutConfig : {
		// // autoWidth : true
		// // },
		// store : store,
		// cm : cm,
		// // columns:[action],
		// plugins : [action],
		// trackMouseOver : false,
		// loadMask : true,
		// viewConfig : {
		// forceFit : true,
		// enableRowBody : true,
		// showPreview : true
		// },
		// bbar : com.faceye.ui.util.PaggingToolBar(15, store)
		// });
		// action.on('action', function(grid, record) {
		// alert(1);
		// });
		panel.add(dataView);
		var viewport = this.buildViewport(panel);
		viewport.render(Ext.getBody());
	},
	/**
	 * 创建面板
	 */
	buildViewport : function(panel) {
		var viewport = Ext.getCmp('user-subscribe-portlet-container');
		//var bottom = com.faceye.ui.BottomLayout.init();
		//var header = com.faceye.ui.HeaderPanel.init();
		//var log = com.faceye.ui.LogPanel.init();
		if (!viewport) {
			var viewport = new Ext.Viewport({
				id : 'user-subscribe-portlet-container',
				border : true,
				items : [panel]
			});
		}
		return viewport;
	},
	/**
	 * 创建容纳portlet信息的 panel
	 */
	buildPanelBody : function() {
		var panel = Ext.getCmp('portlet-subscrine-info-panel');
		if (!panel) {
			panel = new Ext.Panel({
				id : 'portlet-subscrine-info-panel',
				title : '个人桌面工具订阅',
				layout : 'fit'
			});
		}
		return panel;
	}
};
/**
 * 订阅portlet
 */
function portletSubscrie(portletId, portalId) {
	Ext.Ajax.request({
		url : BP + 'portalContainerAction.do?method=portletSubscribe',
		params : {
			portletId : portletId,
			portalId : portalId
		},
		success : function(response, options) {
			var dom = Ext.get('user-subscribe-info' + portletId);
			var buttonParnet = dom.parent();
			dom.remove();
			buttonParnet.createChild({
				tag : 'div',
				id : 'user-subscribe-info' + portletId,
				html : '<font color="red">已添加</font>'
			});
		}
	});
};

/**
 * 取消portlet订阅
 */
function unPortletSubscribe(portletId, portalId) {
	Ext.Ajax.request({
		url : BP + 'portalContainerAction.do?method=removeUserPortletSubscribe',
		params : {
			portletId : portletId,
			portalId : portalId
		},
		success : function(response, options) {
			var dom = Ext.get('user-subscribe-info' + portletId);
			var buttonParnet = dom.parent();
			dom.remove();
			buttonParnet.createChild({
				tag : 'div',
				id : 'user-subscribe-info' + portletId,
				html : '<font color="red">已取消</font>'
			});
		}
	});
}