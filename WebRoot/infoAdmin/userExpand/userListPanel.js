
// �û��Զ�������
userListPanel = Ext.extend(Ext.Panel, {
	id : "userListPanel",
	title:"�û��Զ�������",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	create : function() {
		var clazz = this.cName;		
		var da = new DataAction();
		this.items = da.split(da.getElementsForAdd(clazz));		
		var statusForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 390,
			height : 200,
			layoutConfig : {
				columns : 2	
			},
			defaults : {
				bodyStyle : 'padding:15px '
			},
			frame : true,
			items : this.items

		});	
		var store = this.store;
	    var win = new Ext.Window({
				title : '����û��Զ�������',
				width : 400,
				height : 200,
				maximizable : true,
				modal : true,
				items : statusForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//�����ťʱ�����ȴ�����еõ��������ݣ����ύ����ӦAction�еķ���
					text : '����',					
					handler : function() {
						//alert(clazz);
						var bsParam = statusForm.form.getValues(false);//��ֵ��һ��key��value�Ķ���
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
	//�޸�
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
		var clazz = this.cName;		
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz,id);
		var custdata = da.split(data);		
		var statusForm = new Ext.form.FormPanel({
			layout : 'table',
			width : 390,
			height : 200,
			layoutConfig : {
				columns : 2
			},
			defaults : {
				bodyStyle : 'padding:15px'
			},
			frame : true,
			items : custdata

		});
		var store = this.store;
		var win1 = new Ext.Window({
				title : '�޸�������״̬��Ϣ',
				width : 400,
				height : 200,
				maximizable : true,
				items : statusForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',

				buttons : [{
					text : '����',
					handler : function() {
						var bsParam = statusForm.form.getValues(false);
						da.saveData(clazz,bsParam,function(){ store.reload(); win1.close()});
					},
					scope : this

				}, {
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
	//ɾ��
	remove : function() {
		var clazz = this.cName;
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
					id[i]= records[i].data.id;
					Ext.Ajax.request({
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz='+clazz,
						params : {
							//removeIds : removeIds
							id : id[i]
						},
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
	// ��ʼ��
	initComponent : function() {//�������涨��ı�����ȫ�ֱ���
		this.cName = className;
		this.removIds='';		
		var da = new DataAction();
		var clazz = this.cName;
		var obj=da.getElementsForHead(clazz);//�õ����е��ֶ���
		if(obj==""){
			alert("��¼��ʱ�������µ�¼");
			history.go(-1);
		}
		
		var sm =new Ext.grid.CheckboxSelectionModel();
		var columns= new Array();
		var fields = new Array();
		columns[0]=sm;
		for(var i=0;i<obj.length;i++){
			var headItem = obj[i];
			var title = headItem.header;
			var alignStyle ='left';
			var propertyName = headItem.dataIndex;
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
		userListPanel.superclass.initComponent.call(this);
		var viewConfig = Ext.apply({
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({
			pageSize :10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.grid = new Ext.grid.GridPanel({
			store : this.store,
			cm : this.cm,
			sm : sm,
			trackMouseOver : false,
			loadMask : true,
//			y : 40,
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
			},'     ',{
				text : ' �޸�',
				pressed : true,
				handler : this.modify,
				scope : this,
				iconCls : 'edit'
			}],
			bbar : this.pageBar
		});
		this.grid.on("headerdblclick", this.fitWidth, this);//��(row)���һ�ʱ����
		this.grid.on("rowdblclick",this.modify,this);//˫���м���		
		this.add(this.grid);//������м�������С��壬һ���Ų�ѯ������һ���Ų�ѯ���������б�
		var param = {
			'mehtodCall' : 'query',
			'start' : 0
		};
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
	}

});
