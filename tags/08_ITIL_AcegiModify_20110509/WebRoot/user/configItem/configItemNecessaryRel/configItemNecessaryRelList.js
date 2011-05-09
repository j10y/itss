PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	showConfigItemNecessaryRelWin:function(url){
		new Ext.Window({
			id:"configItemNecessaryRelWin",
			title:'�������Ҫ��ϵ',
			width:580,
			height:380,
			maximizable :true,
			modal:true,
			autoLoad : {
			url : webContext + "/tabFrame.jsp?url="+url,
			text : "ҳ�����ڼ�����......",
			method : 'post',
			scope : this
			}
		}).show();
	},
	split : function(data) {
		var labellen = 100;
		var itemlen = 120;
		var throulen = 530;
		if (Ext.isIE) {
			throulen = 530;
		} else {
			throulen = 540;
		}
		if (data == null || data.length == 0) {
			return null;
		}
		var hid = 0;
		var hidd = new Array();
		var longData = new Array();
		var longitems = 0;
		for (i = 0; i < data.length; i++) {

			data[i].style = data[i].style == null ? "" : data[i].style;
			if (data[i].xtype == "textarea") {
				data[i].style += "'margin:5 0 5 0;'";
			}
			if (data[i].xtype == "hidden") {
				hidd[hid] = data[i];
				hid++;
				continue;
			}
			if (data[i].xtype == "combo") {
				data[i].resizable = true;
			}
			if (data[i].width == null || data[i].width == 'null'
					|| data[i].width == "") {
				data[i].style += "width:" + itemlen + "px";
				data[i].width = itemlen;
			} else {
				if (data[i].width == "9999") {//ͨ��  
					if ((i - hid + longitems) % 2 == 1) {//���Ҳ�����ǰһ����ͨ                                             
						longData[2 * (i - hid) - 1].colspan = 3;
					} else {//��ռһ��
						longitems++;
					}
					data[i].colspan = 3;//������ͨ                                            
					data[i].width = throulen;
					if (data[i].xtype == "textarea") {
						data[i].height = 150;
					}
					data[i].style += "width:" + throulen + "px;";
				} else {//�������ȣ����� 
					data[i].style += "width:" + itemlen + "px";
					data[i].width = itemlen;
				}
			}
			longData[2 * (i - hid)] = {
				html : data[i].fieldLabel + ":",
				cls : 'common-text',
				style : 'width:' + labellen + ';text-align:right'
			};
			longData[2 * (i - hid) + 1] = data[i];
		}
		for (i = 0; i < hidd.length; i++) {
			longData[longData.length] = hidd[i];
		}
		return longData;
	},
	getSearchForm : function() {
		this.panel = new Ext.form.FormPanel({
			id:'searchForm',
			region : "north",
			layout : 'table',
			height : 'auto',
			width : 'auto',
			frame : true,
			keys:{
			    key:Ext.EventObject.ENTER,
			    fn: function(){
		    		var params=getFormParam('searchForm');
				    params.start=0;
					Ext.getCmp("gridPanel").store.baseParams=params;
					Ext.getCmp("gridPanel").store.load();
			    },
			    scope: this
			},
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
			layoutConfig : {
				columns : 6
			},
			title : "��ѯ�б�",
			items : [
							{
			    html: "����������:" ,
				cls: 'common-text', 
				style:'width:80;height:20;text-align:right;'
			},
			new Ext.form.ComboBox({
				store : new Ext.data.JsonStore({
					url: webContext+'/configItemAction_findItemTypeByItem.action',
					fields: ['id','name'],
				    root:'data',
					sortInfo: {field: "id", direction: "ASC"}
				}),
				listeners:{
					beforequery  :function(queryEvent){
						queryEvent.combo.store.baseParams={
							item:"ci"
						};
						queryEvent.combo.store.reload();
						return false;
					}
				}, 
				id:'configItemTypeCombo',
				hiddenName: "configItemType",
				valueField : "id",
				displayField : "name",
				resizable:true,
				mode : 'remote',
				width:150,
				maxHeight:200,
				editable : false,
				triggerAction : 'all', 
				allowBlank : true
			}),	{
			    html: "��������������:" ,
				cls: 'common-text', 
				style:'width:110;height:20;text-align:right;'
			},
			new Ext.form.ComboBox({
				store : new Ext.data.JsonStore({
					url: webContext+'/configItemAction_findItemTypeByItem.action',
					fields: ['id','name'],
				    root:'data',
					sortInfo: {field: "id", direction: "ASC"}
				}),
				listeners:{
					beforequery  :function(queryEvent){
						queryEvent.combo.store.baseParams={
							item:"ci"
						};
						queryEvent.combo.store.reload();
						return false;
					}
				}, 
				id:'otherConfigItemTypeCombo',
				hiddenName: "otherConfigItemType",
				valueField : "id",
				displayField : "name",
				resizable:true,
				mode : 'remote',
				width:150,
				maxHeight:200,
				editable : false,
				triggerAction : 'all', 
				allowBlank : true
			}),{
			    html: "��ϵ����:" ,
				cls: 'common-text', 
				style:'width:70;height:20;text-align:right;'
			}, new Ext.form.ComboBox({
					id :"parentOrChildTypeCombo",
					mode : "local",
					hiddenName : "parentOrChildType",
					store :new Ext.data.SimpleStore({
						fields:['id','name'],
						data:[[1,"��-->��"],[2,"��-->��"]]
					}),
					valueField : "id",
					displayField : "name",
					width:150,
					editable : false,
					triggerAction : 'all'
				})
			]
		});
		return this.panel;
	},
	items : this.items,
	initComponent : function(){
		var showConfigItemNecessaryRelWin=this.showConfigItemNecessaryRelWin;
		var da = new DataAction();
  		this.fp = this.getSearchForm();
		var obj = da.getListPanelElementsForHead("panel_ConfigItemNecessaryRelList");
		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns = new Array();
		var fields = new Array();
		columns[0] = sm;
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';
			var propertyName = headItem.dataIndex;
			
			var isHiddenColumn = false;
            var isHidden = headItem.isHidden;
            if(isHidden=='true'){
            	isHiddenColumn = true;
            }
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHiddenColumn,
				align : alignStyle
			}
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}
		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);
		var store = da.getPanelJsonStore("panel_ConfigItemNecessaryRel",obj);
		 store.addListener("load",function(store, records, options){
    		for(var i=0;i<records.length;i++){
    			switch(records[i].get('ConfigItemNecessaryRel$parentOrChildType')){
    				case '��':records[i].set('ConfigItemNecessaryRel$parentOrChildType',"��-->��");records[i].commit();break;
    				case '��':records[i].set('ConfigItemNecessaryRel$parentOrChildType','��-->��');records[i].commit();break;
    			}
    		}
            });
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		var pageBar = new Ext.PagingToolbar({
			id:'pageBar',
			pageSize : 10,
			store : store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����",
			plugins :new Ext.ux.ProgressBarPager()
		});
		
		this.grid = new Ext.grid.GridPanel({
			id:'gridPanel',
			region : "center",
			store : store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			bbar : pageBar,
			tbar:[{
					xtype : 'button',
					handler :function(){
					    var params=getFormParam('searchForm');
					    params.start=0;
					    store.baseParams=params;
						store.load();
					},
					text : '��ѯ',
					iconCls : 'search'
			},"-",{
					xtype : 'button',
					handler :function(){
					    Ext.getCmp('searchForm').form.reset();
					},
					text : '����',
					iconCls : 'reset'
			},
			"-",{
					xtype : 'button',
					handler :function(){
						var records=Ext.getCmp('gridPanel').getSelectionModel().getSelections();
						if(records.length==0){
							Ext.Msg.alert("��ʾ:","��ѡ���¼��");
							return;
						}
						if(records.length>1){
							Ext.Msg.alert("��ʾ:","һ��ֻ��ѡ��һ����¼��");
							return;
						}
						var id=records[0].get("ConfigItemNecessaryRel$id");
						var url=webContext+"/user/configItem/configItemNecessaryRel/configItemNecessaryRelReadOnly.jsp?dataId="+id;
						showConfigItemNecessaryRelWin(url);
					},
					text : '�鿴',
					iconCls :'look'
			},"-",{
					xtype : 'button',
					handler :function(){
						var url=webContext+"/user/configItem/configItemNecessaryRel/configItemNecessaryRel.jsp";
						showConfigItemNecessaryRelWin(url);
					},
					text : '�½�',
					iconCls : 'add'
			},"-",{
					xtype : 'button',
					handler :function(){
						var records=Ext.getCmp('gridPanel').getSelectionModel().getSelections();
						if(records.length==0){
							Ext.Msg.alert("��ʾ:","��ѡ���¼��");
							return;
						}
						if(records.length>1){
							Ext.Msg.alert("��ʾ:","һ��ֻ��ѡ��һ����¼��");
							return;
						}
						var id=records[0].get("ConfigItemNecessaryRel$id");
						var url=webContext+"/user/configItem/configItemNecessaryRel/configItemNecessaryRel.jsp?dataId="+id;
						showConfigItemNecessaryRelWin(url);
					},
					text : '�޸�',
					iconCls :'edit'
			},"-",{
					xtype : 'button',
					handler :function(){
						var records=Ext.getCmp('gridPanel').getSelectionModel().getSelections();
						if(records.length==0){
							Ext.Msg.alert("��ʾ","��ѡ��Ҫɾ���ļ�¼!");
							return;
						}
						var idArray=new Array();
						for(var i=0;i<records.length;i++){
							idArray.push(records[i].get("ConfigItemNecessaryRel$id"));
						}
						idArray=Ext.encode(idArray);
						Ext.MessageBox.confirm("��ʾ","ȷ��ɾ����",function(buttontext){
							if(buttontext=='yes'){
								Ext.Ajax.request({
									url : webContext +'/configItemAction_removeConfigItemNecessaryRel.action',
									params:{
										idArray:idArray
									},
									success : function(response, options) {
										Ext.MessageBox.alert("��ʾ��Ϣ��", "ɾ���ɹ���",function(){
											Ext.getCmp('gridPanel').getStore().reload();
										});
									},
									failure : function() {
										Ext.MessageBox.alert("��ʾ��Ϣ��", "ɾ��ʧ�ܣ�");
									}
								});
							}
						});						
					},
					text:'ɾ��',
					iconCls:'delete'
			}
			]
		});
		this.grid.on('rowdblclick',function(grid,rowIndex,eventObject){
			var record = grid.getSelectionModel().getSelected();
			var id=record.get("ConfigItemNecessaryRel$id");
			var url=webContext+"/user/configItem/configItemNecessaryRel/configItemNecessaryRelReadOnly.jsp?dataId="+id;
			showConfigItemNecessaryRelWin(url);
		});
		var items = new Array();
		items.push(this.fp);
		items.push(this.grid);
		this.items = items;
		
		PagePanel.superclass.initComponent.call(this);
		var param = {
			'start' : 0
		};
		store.load({
			params : param
		});
	}
});