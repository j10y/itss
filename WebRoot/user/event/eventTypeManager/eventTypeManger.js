//url���������
function eunicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "@" + s.charCodeAt(i) + ";";
	}
	return rs;
}
PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	layout : 'border',
	modifyby : function() {
		var record = Ext.getCmp('typeGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('typeGrid').getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫ�޸ĵ��У�");
			return;
		}
		if (records.length > 1) {
			Ext.Msg.alert("��ʾ", "�޸�ʱֻ��ѡ��һ�У�");
			return;
		}
		var dataId = record.get("id"); 
		var typeName=eunicode(record.get("name")); 
		window.location = webContext+"/user/event/eventTypeManager/newEventType.jsp?dataId="+dataId+"&typeName="+typeName;

	},
	    getSearchForm : function() {
			this.panel = new Ext.form.FormPanel({
				region : "north",
				layout : 'table',
				width : 'auto',
				height : 60,
				frame : true,
				collapsible : true,
				defaults : {
					bodyStyle : 'padding:2px'
				},
				layoutConfig : {
					columns : 2
				},
				title : "��ѯ�б�",
				items : [{html: "�¼���������:",cls: 'common-text',style:'width:150;text-align:right'}
			    	,{id : 'typeName',
				     xtype : 'textfield',
			       	fieldLabel : '�¼���������',
			       	width : '200'
				}]
			});
			return this.panel;
		},
		
	remove : function() {
		var record = Ext.getCmp('typeGrid').getSelectionModel().getSelected();
		var records = Ext.getCmp('typeGrid').getSelectionModel().getSelections();
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ�����У�");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('��ʾ', '����ѡ��һ����Ϣ����ɾ����');
			return;
		}
		var id = new Array();
		var da = new DataAction();
		var firm  =Ext.Msg.confirm('��ʾ', '��ȷ��Ҫ����ɾ��������', function(button) {
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){	
					id[i] = records[i].get("id");
				Ext.Ajax.request({
						url : webContext
								+ "/eventAction_removeEventType.action",
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("��ʾ","����ɾ��ʧ�ܣ�");
	                       }else{  
							  var store=Ext.getCmp("typeGrid").getStore();
					          var params={
					          			 start : 0, 
					                     typeName : Ext.getCmp("typeName").getRawValue()
					                     };
//					          Ext.getCmp("pagetoolBar").formValue=param;
					          store.on('beforeload', function(a) {   
										      Ext.apply(a.baseParams,params);   
								});
					          store.load({
					              params : params
					          });
		                    }
	                   },scope:this
						
					});
				}
			}
		}, this);

	  },
	items : this.items,
	initComponent : function() {
		this.fp = this.getSearchForm();
		this.store = new Ext.data.JsonStore({
			url : webContext + '/eventAction_findAllEventTypeByName.action',
			fields : ['id', 'name'],
			root : 'data',
			totalProperty :'rowCount',
			id : 'id'
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel(
		  [sm,
		  	{header : "�Զ����",
			dataIndex : "id",
			width : 70   
		     },
			{header : "�¼�����",
			dataIndex : "name",
			width : 150   
		}]);
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
		this.grid = new Ext.grid.GridPanel({
			region : "center",
			id : "typeGrid",
			store : this.store,
			cm : this.cm,
			sm:sm,
			tbar : [{
				text : "�½�",
				scope : this,
				iconCls : "add",
				handler : function() {
					window.location = webContext
							+ "/user/event/eventTypeManager/newEventType.jsp";
				}
			},'-',{
				text : "��ѯ",
				scope : this,
				iconCls : "search",
				handler : function(){
					var typeName = Ext.getCmp('typeName').getValue();
					this.store.removeAll();
					var param = {
						start : 0,
						typeName : typeName
					};
					this.store.load({
						params : param
					});
				}
			},'-',{
				text : "ɾ��",
				scope : this,
				iconCls : "remove",
				handler : this.remove
			} ,   new Ext.Toolbar.TextItem("<font color=red style='font-weight:lighter' >��˫����鿴��ϸ��Ϣ��</font>")],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick", this.modifyby, this);
		var item=new Array();
		item.push(this.fp);
		item.push(this.grid);
		this.items = item;
		var params = {
			 start : 0,
			 typeName: ""
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,params);   
			});
		this.store.removeAll();
		this.store.load({
			params : params
		});
		PagePanel.superclass.initComponent.call(this);
	},
	fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});
