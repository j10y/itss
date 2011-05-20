ConfigUnitListPanel = Ext.extend(Ext.Panel, {
	id : "configUnitListPanel",	
	closable : true,
	viewConfig : {//����Ӧ���
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	reset : function() {
		this.fp.form.reset();
	},
	// �޸�
	modify:function(){
		var record = this.grid.getSelectionModel().getSelected();
	    var records = this.grid.getSelectionModel().getSelections();
		if(!record){
				Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
				return;
			}
		if(records.length>1){
				Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
				return;
			}
		var id = record.get("id");
		this.mofifyForm = new ConfigUnitModifyRecordPanel({configUnit:id});		
		this.mofifyWin = new Ext.Window({
		 	title:'���õ�Ԫ�޸���Ϣ����',
		 	modal: true,
		 	height:300,
	        width:600,
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
	        	handler : this.mofifyForm.saveConfigItem,
	        	text : ' ����',
	        	scope : this			
				},{xtype:'button',
	        	handler:this.mofifyForm.returned,
	        	text:'�ر�',
	        	scope:this
	        	}],
        	items:[this.mofifyForm]
		});
		this.mofifyWin.show();
					
		
	},
	remove : function() {
		var record = this.grid.getSelectionModel().getSelected();
		var records = this.grid.getSelectionModel().getSelections();
		
		if (!record) {
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		if (records.length == 0) {
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
			return;
		}
		var id = new Array();
		for(var i=0;i<records.length;i++){			
			id[i] = records[i].data.id;			
		}

		var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
			if (button == 'yes') {
				Ext.Ajax.request({
						url : webContext
								+ '/configUnit_deleteConfigUnit.action',
						params : {							
							removeIds : id
						},	
						timeout:1000000,
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){	                       			
	                       			this.store.reload();
	                       		});
	                       }
	                       else{
                                Ext.Msg.alert("��ʾ��Ϣ","����ɾ���ɹ�!",function(){                                 	
                                	this.store.reload();
                                },this);
	                       }	                      
	                       
	                   },scope:this							
					});
			}
		}, this);

	},	
	create : function() {	
		
		this.searchForm = new ConfigUnitAddRecordPanel;		
		this.detailWin = new Ext.Window({
		 	title:'���õ�Ԫ¼����Ϣ',
		 	modal: true,
		 	height:300,
	        width:600,
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
	        	handler : this.searchForm.saveConfigItem,
	        	text : ' ����',
	        	scope : this			
				},{xtype:'button',
	        	handler:this.searchForm.returned,
	        	text:'�ر�',
	        	scope:this
	        	}],
        	items:[this.searchForm]
		});
		this.detailWin.show();
	},
	// ��ʼ��
	initComponent : function() {//�������涨��ı�����ȫ�ֱ���

		this.removeIds='';
		this.configItemName = '';		
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.store = new Ext.data.JsonStore({
			url : webContext+ '/configUnit_getConfigUnit.action',
			root : "data",
			fields : ["id", "configUnitName", "configUnitDesc", "configUnitLink"]
		});		
		var obj=[{
			header : "���õ�Ԫ����",
			dataIndex : "configUnitName"
		}, {
			header : "���õ�Ԫ����",
			dataIndex : "configUnitDesc"
		},
		{
			header : "���õ�Ԫ����",
			dataIndex : "configUnitLink"
		}];
		var columns = new Array();
		var fields = new Array();

		columns[0] = sm;
		
		for (var i = 0; i < obj.length; i++) {
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle = 'left';

			var propertyName = headItem.dataIndex;

			var isHidden = false;
			if (propertyName == 'id') {
				isHidden = true;
			}
			var columnItem = {
				header : title,
				dataIndex : propertyName,
				sortable : true,
				hidden : isHidden,
				align : alignStyle
			};
			columns[i + 1] = columnItem;
			fields[i] = propertyName;
		}

		this.storeMapping = fields;
		this.cm = new Ext.grid.ColumnModel(columns);

		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		this.cm.defaultSortable = true;//����Ĭ��ֵ	

		ConfigUnitListPanel.superclass.initComponent.call(this);//�ø����ȳ�ʼ��
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,//ʹ�õ���ϵͳĬ��ֵ
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 0,
			anchor : '0 -0',
			tbar : ['   ', {
				text : '����',
				pressed : true,
				handler : this.create,
				scope : this,
				iconCls : 'add'
			},'   ', {
				text : ' ����',
				pressed : true,
				handler : this.reset,
				scope : this,
				iconCls : 'reset'
			},'     ',{
				text : ' �޸�',
				pressed : true,
				handler : this.modify,
				scope : this,
				iconCls : 'edit'
			}],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);
		this.grid.on("rowdblclick",this.modify,this);//˫���м���
		//this.add(this.fp);
		this.add(this.grid);//������м�������С��壬һ���Ų�ѯ������һ���Ų�ѯ��������
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.removeAll();//ɾ�����ݴ洢���е���������
		this.store.load({
			params : param

		});

	},
fitWidth : function(grid, columnIndex, e) {
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	},
	initData : function() {
		var param = {
			'methodCall' : 'list',
			'start' : 0
		};
		this.store.on('beforeload', function(a) {   
					      Ext.apply(a.baseParams,param);   
					});
		this.store.load({
			params : param
		});
	}
});
