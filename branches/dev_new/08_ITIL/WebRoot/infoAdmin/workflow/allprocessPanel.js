// ���������б�   
ListProcessPanel = Ext.extend(Ext.Panel,{
	id : "ListProcessPanel",
	closable : true,
	height : 'auto',
	width : 'auto',
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'fit',
	items : this.items,
	reset : function() {
		this.fp.form.reset();
	},

	listAll : function() {
		this.store.removeAll();
		this.store.load();
	},

	view : function(grid) {
		var record = this.grid.getSelectionModel()
				.getSelected();
		var records = this.grid.getSelectionModel()
				.getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ���õ�������!");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ��!");
			return;
		}
		var id = record.get("id");

		window.location = webContext
				+ "/infoAdmin/workflow/configPage/taskInfo.jsp?virtualDefinitionInfoId="
				+ id;
	},

	// ��ʼ��
	initComponent : function() {
		ListProcessPanel.superclass.initComponent.call(this);
		var da = new DataAction();

		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore(
				{
					url : webContext
							+ '/workflow/listprocess.do?methodCall=findAllActiveProcessInstance',
					root : "data",
					fields : [ "id", "virtualDefinitionDesc",
							"definitionName",
							"defVersion",
							"start"],
					totalProperty : 'rowCount'
				});
		this.cm = new Ext.grid.ColumnModel([ sm, {
			header : "���� ",
			dataIndex : "id",
			width : 150,
			renderer : function(value){
				//return "<a href='" + webContext + "/workflow/history.do?procid="+value+"&methodCall=list'>" + "HISTORY" + "</a>";
				return "<a href='#' onclick='getProcessHistory(" + value + ");' >" + "�鿴��ʷ" + "</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='#' onclick='endProcess(" + value + ");' >" + "��������" + "</a>";
			}
		}, {
			header : "����ʵ��ID",
			dataIndex : "id",
			width : 150
		}, {
			header : "������������",
			dataIndex : "virtualDefinitionDesc",
			width : 300
		}, {
			header : "��ʵ���̶���",
			dataIndex : "definitionName",
			width : 150
		}, {
			header : "���̶���汾",
			dataIndex : "defVersion",
			width : 300
		}, {
			header : "��ʼʱ��",
			dataIndex : "start",
			width : 200
		}]);
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;

		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;

		this.grid = new Ext.grid.GridPanel(
				{
					id : 'mainGrid',
					title : '���̼���б�',
					layout : 'table',
					store : this.store,
					cm : this.cm,
					sm : sm,
					trackMouseOver : false,
					loadMask : true,
					clicksToEdit : 2,
					autoScroll : true,
					height : 490,
					width : 1000,
					frame : true,
					//bbar : this.pageBar,
					tbar : [
							

					]

				});
		this.grid.on("rowdblclick", this.view, this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {
			Ext.apply(a.baseParams, param);
		});
		this.store.removeAll();
		this.store.load({
			params : param

		});
		this.add(this.grid);
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for ( var i = 0, l = grid.store.getCount(); i < l; i++) {
			w = Math
					.max(
							w,
							grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}

});
