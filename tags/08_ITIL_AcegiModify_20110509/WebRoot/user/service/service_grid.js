/*
 * 源数据面板
 */
PageModelGridPanel = Ext.extend(Ext.Panel, {
	title: '服务项',
	width: 550,
	columnWidth:.5,
	animate: true,
	containerScroll: true,
    height: 500,
	smtId:"",
	store: "", 
	
	// 查找方法
	searchConfigItem : function() {
		
		var param = this.searchForm.form.getValues(true);		
		/*全用unicode编码*/
		var searchName = unicode(this.searchForm.form.findField('searchName').getValue());
		var searchType = unicode(this.searchForm.form.findField('searchType').getValue());
		var param = {
					searchName : searchName,
					searchType : searchType,
					start: 1
					};
		/*给分页发的时候这样的参数格式*/
	    this.formValue = param;
		this.pageBar.formValue = this.formValue;
		var formParam = Ext.encode(param);
		this.store.removeAll();
		this.store.load({
			params : param
		})
	},
	show : function(){
		var record = this.grid.getSelectionModel().getSelected();
		var id = record.get("id");
		var win1 = new Ext.Window({
			title : '查看服务项信息',
			height:500,
			width:800,
			resizable:false,
			draggable:true,
			autoLoad:{
			url:webContext+"/tabFrame.jsp?url="+webContext+"/user/service/configItemServiceView.jsp?dataId="+id,
			text:"页面加载中......",
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
				text : '关闭',
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
				fields: ['id','name','serviceType'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.cm = new Ext.grid.ColumnModel([   
		   		    
		    {header: "服务项名称", width:50, sortable: true, dataIndex: 'name'},
		    {id:'id', header: "id", width: 50, sortable: true, dataIndex: 'id',hidden:true},
		    {header: "服务类型", width: 50, sortable: true, dataIndex: 'serviceType'}	
		]); 
		
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);
		
		// 创建分页ToolBar
		this.pageBar = new Ext.PagingToolbarExt({
			
				pageSize : 10,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前显示 {0}-{1}条记录 /共{2}条记录',
				emptyMsg : '无显示数据'
		});
		
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		
		this.searchName = new Ext.form.TextField({
			name : 'searchName',
			fieldLabel : '服务项名称'
			
		});
		this.searchType = new Ext.form.ComboBox({
			id :'searchType',
			hiddenName: 'name',
			width:'null',
			style:'',
			lazyRender: true,
			fieldLabel:'服务项类型',
			displayField: 'name',
			valueField :'id',
			emptyText:'请选择...',
			allowBlank:true,
			typeAhead:true,
			minChars :50,
			triggerAction:'all',
			name:'searchType',
			store:new Ext.data.JsonStore({
			url:webContext+'/extjs/comboDataAction?clazz=com.zsgj.itil.service.entity.ServiceItemType',
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
			width : 615,
			labelWidth : 100,		
			y : 40,
			anchor : '0 -40',
			frame : true,	
			layoutConfig : {columns: 4},
			items : [
				{html: "服务项名称:&nbsp;" ,cls: 'common-text', style:'width:80;height:20;text-align:left;margin:5 0 5 0;'},
				this.searchName	,
				{html: "服务项类型:&nbsp;" ,cls: 'common-text', style:'width:80;height:20;text-align:left;margin:5 0 5 0;'},
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
		        width: 615,
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
				text : ' 查询',
				pressed : true,
				handler : this.searchConfigItem,
				scope : this,
				iconCls : 'search',
				cls : "x-btn-text-icon"
				}, '&nbsp;|&nbsp;',{
				text : ' 重置',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
				}],
				bbar : this.pageBar
		}); 
		this.grid.on("rowdblclick",this.show,this);//双击行监听
		var param = {
			'start' : 1
		};
		this.pageBar.formValue = param;
		this.store.load({
			params : param
		});
		
		this.items = [this.searchForm,this.grid];		
		PageModelGridPanel.superclass.initComponent.call(this);	
	}

});