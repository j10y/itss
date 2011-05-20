
// �����б�Panel����
CusApplayPanel = Ext.extend(Ext.Panel, {
	id : "CusApplayPanel",
	closable : true,
	viewConfig : {//����Ӧ���
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
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
		var id = record.get("id");//********************************�õ��޸��е�id
		
		var clazz = "com.zsgj.itil.actor.entity.Customer";
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz,id);
		var custdata = da.split(data);
		
		var envForm = new Ext.form.FormPanel({
			layout : 'table',
			width : "auto",
			height : 490,
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store=this.store;
		var win1 = new Ext.Window({
				title : '�޸��û���Ϣ',
				width : 800,
				height : 500,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',

				buttons : [{
					text : '����',
					handler : function() {
						var bsParam = envForm.form.getValues(false);
						da.saveData(clazz,bsParam,function(){
						
							store.reload();
							win1.close()
						});
					},scope : this
				},
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
	remove : function() {
		var clazz = "com.zsgj.itil.actor.entity.Customer";
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
		var da = new DataAction();	
		var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){	
					id[i] = records[i].data.id;
				Ext.Ajax.request({
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz='+clazz,
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);
	                       if(!r.success){
	                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){
	                       		});
	                       }
	                       this.store.reload();
	                   },scope:this
						
					});
				}
			}
		}, this);

	},
	// ����
	search : function() {
		var param = this.fp.form.getValues(false);
		param.methodCall = 'query';
        param.start = 1;           
		this.formValue = param;         
		this.pageBar.formValue = this.formValue;
		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	create : function() {
		var clazz = "com.zsgj.itil.actor.entity.Customer";
		var da = new DataAction();
		this.items = da.split(da.getElementsForAdd(clazz));
		var statusForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 775,
			height : 500,
			layoutConfig : {
				columns : 4
			},
			defaults : {
				bodyStyle : 'padding:15px '
			},
			frame : true,
			items : this.items

		});
		var store = this.store;
	    var win = new Ext.Window({
	    	title:"���ӿͻ���Ϣ",
				width : 700,
				height : 500,
				maximizable : true,
				modal : true,
				items : statusForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{
					text : '���� ',
					handler : function() {
						var bsParam = statusForm.form.getValues(false);
						da.saveData(clazz,bsParam,function(){ store.reload(); win.close()});
					},
					scope : this

				}, {
					text : '�ر�',
					handler : function() {
						win.hide();
					},
					scope : this
				}]

			});
		win.show();

	}, 

getSearchForm : function() {
        var da = new DataAction();
        var clazz = "com.zsgj.itil.actor.entity.Customer";
        data = da.getElementsForQuery(clazz);
         var biddata = da.split(data);
        this.panel = new Ext.form.FormPanel({
                              region:"north",
                              layout : 'table',
                              height : 'auto',
                              width : 'auto',
                              frame : true,
                             collapsible : true,
                              defaults : {
                                        bodyStyle : 'padding:4px'
                              },
                              layoutConfig : {
                                        columns : 6
                              },
                              title : "��ѯ�б�",
                              items : biddata
                    });    
        
         return  this.panel;         
},

 items:this.items,
	// ��ʼ��
	initComponent : function() {//�������涨��ı�����ȫ�ֱ���
		//this.removIds='';
		this.fp=this.getSearchForm();
		var da = new DataAction();
		var clazz = "com.zsgj.itil.actor.entity.Customer";
		var obj=da.getElementsForHead(clazz);//�õ����е��ֶ���
		var sm =new Ext.grid.CheckboxSelectionModel();
		var columns= new Array();
		var fields = new Array();
		columns[0]=sm;
		for(var i=0;i<obj.length;i++){
			var headItem = obj[i];
			var title = headItem.header;//
			var alignStyle ='left';
			var propertyName = headItem.dataIndex;//
			var isHidden=false;
			if(propertyName=="id"){
			 isHidden =true;
			}
		var columnItem={
			header:title,
			dataIndex:propertyName,
			sortable:true,
			hidden:isHidden,
			align:alignStyle
		}
		columns[i+1]=columnItem;
		fields[i]=propertyName;
		}
		this.storeMapping=fields;
		this.cm=new Ext.grid.ColumnModel(columns);
		this.store = da.getJsonStore(clazz);
		this.store.paramNames.sort="orderBy";
		this.store.paramNames.dir="orderType";
		this.cm.defaultSortable=true;
		
		var viewConfig = Ext.apply({//ʲô��˼��
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbarExt({
			pageSize :10, //sys_pageSize,//ʹ�õ���ϵͳĬ��ֵ
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({
            region:"center",               
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			anchor : '0 -35',
			tbar : ['   ', {
				text : '���',
				pressed : true,
				handler : this.create,
				scope : this,
				iconCls : 'add'
			}, '   ', {
				text : 'ɾ��',
				pressed : true,
				handler : this.remove,
				scope : this,
				iconCls : 'remove'
			}, '    &nbsp;|&nbsp;    ', {
				text : '��ѯ',
				pressed : true,
				handler : this.search,
				scope : this,
				iconCls : 'search'
			}, '   ', {
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
		// this.grid.on("rowdblclick",this.openDetail,this);
		// this.grid.on("rowdblclick", this.create, this);//
		this.grid.on("headerdblclick", this.fitWidth, this);//��(row)���һ�ʱ����
		this.grid.on("rowdblclick",this.modify,this);//˫���м���
	         var items=new Array();
             items.push(this.fp);
             items.push(this.grid);
            this.items=items;
          CusApplayPanel.superclass.initComponent.call(this);
		var param = {
			'mehtodCall' : 'query',
			'start' : 1
		};
		this.pageBar.formValue =param; 
		this.store.removeAll();//ɾ�����ݴ洢���е���������
		this.store.load({
			params : param

		});
		// this.search();

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
