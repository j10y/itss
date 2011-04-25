<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>欢迎使用IT服务系统</title>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/extEngine/resources/css/ext-all.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/extEngine/adapter/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/extEngine/ext-all.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/extEngine/source/locale/ext-lang-zh_CN.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/extEngine/calendar.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/extEngine/resources/css/grid-examples.css" />
		<style type="text/css">
			.image1 {
				list-style-image: url('images/cal1.gif');
				margin-left: 15px;
			}

			.image2 {
				list-style-image: url('images/cal2.gif');
				margin-left: 15px;
			}
		</style>
		<script type="text/javascript">
		Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			var store = new Ext.data.Store({
				proxy : new Ext.data.ScriptTagProxy({
					url : 'http://extjs.com/forum/topics-browse-remote.php'
				}),
				reader : new Ext.data.JsonReader({
					root : 'topics',
					totalProperty : 'totalCount',
					id : 'threadid',
					fields : ['title', 'forumtitle', 'forumid', 'author', {
						name : 'replycount',
						type : 'int'
					}, {
						name : 'lastpost',
						mapping : 'lastpost',
						type : 'date',
						dateFormat : 'timestamp'
					}, 'lastposter', 'excerpt']
				}),
		
				remoteSort : true
			});
			store.setDefaultSort('lastpost', 'desc');
		
			function renderTopic(value, p, record) {
				return String
						.format(
								'<b><a href="http://extjs.com/forum/showthread.php?t={2}" target="_blank">{0}</a></b><a href="http://extjs.com/forum/forumdisplay.php?f={3}" target="_blank">{1} Forum</a>',
								value, record.data.forumtitle, record.id,
								record.data.forumid);
			}
			function renderLast(value, p, r) {
				return String.format('{0}<br/>by {1}', value
						.dateFormat('M j, Y, g:i a'), r.data['lastposter']);
			}
		
			var cm = new Ext.grid.ColumnModel([{
				id : 'topic',
				header : '主题',
				dataIndex : 'title',
				width : 420,
				renderer : renderTopic
			}, {
				header : 'Author',
				dataIndex : 'author',
				width : 100,
				hidden : true
			}, {
				id : 'last',
				header : "时间",
				dataIndex : 'lastpost',
				width : 150,
				renderer : renderLast
			}]);
		
			cm.defaultSortable = true;
		
			var grid = new Ext.grid.GridPanel({
				el : 'center',
				layout : 'fit',
				title : '',
				store : store,
				cm : cm,
				trackMouseOver : false,
				sm : new Ext.grid.RowSelectionModel({
					selectRow : Ext.emptyFn
				}),
				loadMask : true,
				viewConfig : {
					forceFit : true,
					enableRowBody : true,
					showPreview : true,
					getRowClass : function(record, rowIndex, p, store) {
						if (this.showPreview) {
							p.body = '<p>' + record.data.excerpt + '</p>';
							return 'x-grid3-row-expanded';
						}
						return 'x-grid3-row-collapsed';
					}
				},
				bbar : new Ext.PagingToolbar({
					pageSize : 25,
					store : store,
					displayInfo : true,
					displayMsg : '显示行数{0}-{1}of{2}',
					emptyMsg : "无可显示内容",
					items : ['-', {
						pressed : true,
						enableToggle : true,
						text : '显示预览',
						cls : 'x-btn-text-icon details',
						toggleHandler : toggleDetails
					}]
				})
			});
		
			grid.render();
			store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
		
			function toggleDetails(btn, pressed) {
				var view = grid.getView();
				view.showPreview = pressed;
				view.refresh();
			}
			var view = new Ext.Viewport({
				layout : 'border',
				renderTo : 'topic-grid',
				border : false,
				items : [{
					region : 'center',
					layout : 'fit',
					items : [grid]
				}]
			});
		});
		
		</script>
	</head>
	<body>

		<div id="center" style="width: 100%; height: 250px">
			</>
		</div>

	</body>
</html>