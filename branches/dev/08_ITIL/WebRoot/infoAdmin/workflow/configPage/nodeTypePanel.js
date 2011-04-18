WorkflowListPanel = Ext.extend(Ext.Panel, {
	id : "WorkflowListPanel",	
	closable : true,
	viewConfig : {//����Ӧ���
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	// �޸�
	matching:function(){
		var record = this.grid.getSelectionModel().getSelected();
	    var records = this.grid.getSelectionModel().getSelections();
		if(!record){
				Ext.Msg.alert("��ʾ","����ѡ��Ҫƥ�����!");
				return;
			}
		if(records.length>1){
				Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
				return;
			}
		var id = record.get("id");
		this.matchingForm = new NodeTypeAndConfigUnitPanel({nodeId:id});		
		this.matchingWin = new Ext.Window({
		 	title:'���õ�Ԫ�޸���Ϣ����',
		 	modal: true,
		 	height:400,
	        width:930,
	        resizable:false,
	        constrain:false,
	        constrainHeader:false,
	        autoScroll:true,
	        viewConfig: {
		        autoFill: true,
		      	forceFit: true
		    },
		  	layout: 'absolute',
	        buttonAlign:'center',
	        buttons:[	        	
	        	{
	        	id : 'saveCI',
	        	xtype:'button',
	        	pressed : true,
	        	handler : this.matchingForm.saveRelation,
	        	text : ' ����',
	        	scope : this			
				},{xtype:'button',
	        	handler:this.matchingForm.returned,
	        	text:'�ر�',
	        	scope:this
	        	}],
        	items:[this.matchingForm]
		});
		this.matchingWin.show();
					
		
	},

	reset : function() {
		this.searchForm.form.reset();
	}, 
	// ��ʼ��
	initComponent : function() {//�������涨��ı�����ȫ�ֱ���
		
	

		this.csm = new Ext.grid.CheckboxSelectionModel();
		
		this.store = new Ext.data.JsonStore({ 				
				url: webContext+ '/nodeType_showAllNodeType.action',
				fields: ['id','name','desc','pattern'],
			    root:'data',
			    totalProperty : 'rowCount',
				sortInfo: {field: "id", direction: "ASC"}
		}); 
		this.cm = new Ext.grid.ColumnModel([   
		   	this.csm,	    
		    {header: "�ڵ���������", width: 100, sortable: true, dataIndex: 'name'}, 
		    {header: "�ڵ���������", width: 100, sortable: true, dataIndex: 'desc'},
		    {header: "�ڵ����ͱ�ʶ", width: 100, sortable: true, dataIndex: 'pattern'},
		    {id:'id', header: "id", width: 100, sortable: true, dataIndex: 'id',hidden:true}	
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
		
		this.nodeType = new Ext.form.TextField({
			name : 'nodeType',
			fieldLabel : '��ѯ�ڵ���������',
			width:200
		});				
		this.grid = new Ext.grid.GridPanel({ 
			
				id :'grid',
		        store: this.store,
		        cm: this.cm,
		        sm: this.csm,
		        trackMouseOver:false,    
		        loadMask: true,
		        height: 'auto',
		        width: 615,
		        y : 0,
				anchor : '0 -40',
		        frame:true,
		        ddGroup: "tgDD",
				enableDragDrop: true,    
		        autoScroll: true,
		        autoEncode : true,		        
		        viewConfig : {
					autoFill : true,
					forceFit : true
		        },
		        tbar : [{
				text : ' �ڵ����ͱ������õ�Ԫ',
				pressed : true,
				handler : this.matching,
				scope : this,
				iconCls :'reset'
				}],
				bbar : this.pageBar
		}); 

		var param = {
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.load({
			params : param
		});
		
		this.items = [this.grid];	
		this.grid.on("rowdblclick",this.matching,this);//˫���м���
		WorkflowListPanel.superclass.initComponent.call(this);	
	}

});