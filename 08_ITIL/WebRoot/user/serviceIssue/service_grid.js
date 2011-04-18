/*
 * Դ�������
 */
PageModelGridPanel = Ext.extend(Ext.Panel, {
	title: '������',
	width: 490,
	columnWidth:.5,
	animate: true,
	containerScroll: true,
    height: 500,
	smtId:"",
	store: "", 
	
	// ���ҷ���
	searchConfigItem : function() {
		var param = this.searchForm.form.getValues(true);		
		/*ȫ��unicode����*/
		var searchName = unicode(this.searchForm.form.findField('searchName').getValue());
		var searchType = unicode(this.searchForm.form.findField('searchType').getValue());
		var param = {
					searchName : searchName,
					searchType : searchType,
					start: 0
					};
		/*����ҳ����ʱ�������Ĳ�����ʽ*/
	    this.formValue = param;
		this.pageBar.formValue = this.formValue;
		var formParam = Ext.encode(param);
		this.store.baseParams=param;
		this.store.load();
	},
	show : function(){
		var record = this.grid.getSelectionModel().getSelected();
		var id = record.get("id");
		var win1 = new Ext.Window({
			title : '�鿴��������Ϣ',
			height:500,
			width:800,
			resizable:false,
			draggable:true,
			autoLoad:{
			url:webContext+"/tabFrame.jsp?url="+webContext+"/user/serviceIssue/configItemServiceView.jsp?dataId="+id,
			text:"ҳ�������......",
			method:'post',
			scripts:true,
			scope:this
				},
			viewConfig:{
				autoFill:true,
				forceFit:true
				},
			layout:'fit',
			buttons : [
				{
				text : '�ر�',
				handler : function() {
				win1.close();
				},
				listeners: {
					'beforeclose':  function(p) {
					return true;
					}
				},
				scope : this
				}]
				});
			win1.show();
	},
	reset : function() {
		this.searchForm.form.reset();
	}, 

	initComponent: function(){
		
		var csm = new Ext.grid.CheckboxSelectionModel();
		var rootCataId = dataId;
		if(modifyDataId!=null&&modifyDataId!=""){
			rootCataId = modifyDataId;
		}
		this.store = new Ext.data.JsonStore({ 				
				url: webContext+'/sciRelationShip_listCI.action',
				fields: ['id','name',,'serviceType'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.cm = new Ext.grid.ColumnModel([   
		   		    
		    {header: "����������", width: 100, sortable: true, dataIndex: 'name'},
		    {id:'id', header: "id", width: 100, sortable: true, dataIndex: 'id',hidden:true},
		    {header: "��������", width: 50, sortable: true, dataIndex: 'serviceType'}	
		]); 
		
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		
		// ������ҳToolBar
		this.pageBar = new Ext.PagingToolbar({
			
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
				emptyMsg : '����ʾ����'
		});
		
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		
		this.searchName = new Ext.form.TextField({
			name : 'searchName',
			fieldLabel : '����������'
			
		});
		this.searchType = new Ext.form.ComboBox({
			id :'searchType',
			hiddenName: 'name',
			width:198,
			style:'',
			lazyRender: true,
			fieldLabel:'����������',
			displayField: 'name',
			valueField :'id',
			emptyText:'��ѡ��...',
			allowBlank:true,
			typeAhead:true,
			minChars :50,
			triggerAction:'all',
			name:'searchType',
			pageSize:10,
			listWidth:198,
			mode:'remote',
			store:new Ext.data.JsonStore({
			url:webContext+'/extjs/comboDataAction?clazz=com.digitalchina.itil.service.entity.ServiceItemType',
				fields: ['id', 'name'],
				totalProperty:'rowCount',
				root:'data',
				id:'id'
			})
		});
		
		this.searchForm = new Ext.form.FormPanel({
			id : "search",
			layout : 'table',
			height : 40,
			width : 500,
			labelWidth : 100,		
			y : 40,
			anchor : '0 -40',
			frame : true,	
			layoutConfig : {columns: 4},
			items : [
				{html: "����������:&nbsp;" ,cls: 'common-text', style:'width:80;height:20;text-align:left;margin:5 0 5 0;'},
				this.searchName,
				{html: "����������:&nbsp;" ,cls: 'common-text', style:'width:80;height:20;text-align:right;margin:5 0 5 0;'},
				this.searchType
				]
		});
		
		
		
		this.grid = new Ext.grid.GridPanel({
			
				id :'grid',
		        store: this.store,
		        cm: this.cm,
		        sm: csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 430,
		        width: 490,
		        y : 40,
				anchor : '0 -40',
		        frame:true,
		        ddGroup: "tgDD",
				enableDragDrop: true,    
		        autoScroll: true,
		        autoEncode : true,
		        clickToEdit: 1,
		        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
		        tbar : ['   ', {
				text : ' ��ѯ',
				pressed : true,
				handler : this.searchConfigItem,
				scope : this,
				iconCls : 'search',
				cls : "x-btn-text-icon"
				}, '&nbsp;|&nbsp;',{
				text : ' ����',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
				}],
				bbar : this.pageBar
		}); 
		this.grid.on("rowdblclick",this.show,this);//˫���м���
		var param = {
			'start' : 0
		};
		this.pageBar.formValue = param;
		this.store.baseParams=param;
		this.store.load();
		
		this.items = [this.searchForm,this.grid];		
		PageModelGridPanel.superclass.initComponent.call(this);	
	}

});