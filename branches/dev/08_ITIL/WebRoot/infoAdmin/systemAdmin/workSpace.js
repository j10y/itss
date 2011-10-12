PagePanel = Ext.extend(Ext.Panel, {
	id : "PagePanel",
	// title:this.pageModeltitle,
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'border',
	reset:function(){ //���÷���
		this.fp.form.reset();
	},
	search : function() {//��ѯ����
		var tempApp = Ext.getCmp('name').getValue();
		var param = {
					name : tempApp,
					'start' : 1
		};
		/*����ҳ����ʱ�������Ĳ�����ʽ*/
	    this.formValue = param;
		this.pageBar.formValue = this.formValue;
		var formParam = Ext.encode(param);
		this.store.removeAll();
		this.store.load({
			params : param
		})
	},
	create : function() {
		var clazz = "com.digitalchina.info.framework.security.entity.WorkSpace";//ָ��ʵ������
		var da = new DataAction();//������������ʲô�ģ�
		this.items = da.split(da.getElementsForAdd(clazz));//��ʵ�����ж�ȡ��Ӧ��items��Ϣ�����ڽ�����ʾ
		var envForm = new Ext.form.FormPanel({//���������� �����Form
			layout : 'table',
			width : 400,
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
		var store = this.store;//��ȡstoreֵ
	     var win = new Ext.Window({//���������˵� ����
				title : '�����ص�-�ʼ�����������',
				width : 400,
				height : 200,
				modal : true,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',
				buttons : [{//�����ťʱ�����ȴ�����еõ��������ݣ����ύ����ӦAction�еķ���
					text : '����',
					//id:'postButton',
					handler : function() {//Ϊ���水ť���Ӽ�����
						var bsParam = envForm.form.getValues(false);//��ֵ��һ��key��value�Ķ���
						da.saveData(clazz,bsParam,function(){//����ָ��url��ͨ��clazz�Ϳ��ԣ�
						store.reload();
							win.close()
						});
					},
					scope : this //ָ��Ŀ��������

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
	//ɾ��
	remove : function() {
		var clazz = "com.digitalchina.info.framework.security.entity.WorkSpace";//ָ��ʵ������
		var record = this.grid.getSelectionModel().getSelected();//��ȡ��ѡ�е�����
		var records = this.grid.getSelectionModel().getSelections();//��ȡ��ѡ�����ݼ���
		if (!record) {//�ж��Ƿ�δѡ��
			Ext.Msg.alert("��ʾ", "����ѡ��Ҫɾ������!");
			return;
		}
		if (records.length == 0) {//�ж��Ƿ�δѡ��
			Ext.MessageBox.alert('����', '����ѡ��һ����Ϣ������ɾ��!');
			return;
		}
		var id = new Array();
		var da = new DataAction();//û��ʹ��
		var firm  =Ext.Msg.confirm('��ʾ��', '��ȷ��Ҫ����ɾ��������?', function(button) {//ɾ����ʾ
			if (button == 'yes') {
				for(var i=0;i<records.length;i++){//����ѡ�����
				id[i] = records[i].get('id');
				Ext.Ajax.request({//��dataAction�ķ������ɾ��
						url : webContext
								+ '/extjs/dataAction?method=remove&clazz='+clazz,
						params : {id:id[i]},
						timeout:100000, 
						success:function(response){
	                       var r =Ext.decode(response.responseText);//��ȡ�����ı���Ϣ
	                       if(!r.success){//ɾ��ʧ��
	                       		Ext.Msg.alert("��ʾ��Ϣ","����ɾ��ʧ��",function(){
	                       				//this.store.reload();
	                       			//location = 'ibmRelPerson.jsp'
	                       			//firm.close();
	                       		});
	                       }
	                       else{//ɾ���ɹ�
                                Ext.Msg.alert("��ʾ��Ϣ","�ɹ�ɾ������!",function(){
                                	
                                   // location = 'ibmRelPerson.jsp'
                                    //this.store.reload()
                                },this);
	                       }
	                       this.store.reload();//���¶�ȡ����
                          // location = 'ibmCust.jsp'  
	                   },scope:this
						
						
						
						
						
					});
				}
			}
		}, this);

	},
	
	//�޸�
	modify:function(){
		var record = this.grid.getSelectionModel().getSelected();    //��ȡѡ�е�ѡ��
	    var records = this.grid.getSelectionModel().getSelections(); //��ȡ��ѡ�еļ���
		if(!record){//�ж��Ƿ�δѡ�κ�ѡ��
				Ext.Msg.alert("��ʾ","����ѡ��Ҫ�޸ĵ���!");
				return;
			}
		if(records.length>1){//�ж��Ƿ�ѡ����������1
				Ext.Msg.alert("��ʾ","�޸�ʱֻ��ѡ��һ��!");
				return;
			}
		var id = record.get("id");//********************************�õ��޸��е�id
	
		
		var clazz = "com.digitalchina.info.framework.security.entity.WorkSpace";//ָ��ʵ������
		var da = new DataAction();
		var data = da.getElementsForEdit(clazz,id);//�������������ôд��
		var custdata = da.split(data);//�õ��������Ͷ��壿��������ô��
		
		var envForm = new Ext.form.FormPanel({//ָ���޸Ľ���FORM
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
		var store=this.store;
		var win1 = new Ext.Window({//�޸Ĵ��ڶ���
				title : '�����ص�',
				width : 400,
				height : 200,
				maximizable : true,
				items : envForm,
				closeAction : 'hide',
				bodyStyle : 'padding:4px',

				buttons : [{
					text : '����',
					handler : function() {
						var bsParam = envForm.form.getValues(false);
						da.saveData(clazz,bsParam,function(){//����DataActionʵ��������
						
							store.reload();//���¼����޸ĺ������
							win1.close()
						});
							
//						
					},
					scope : this

				}, {
					text : '�ر�',
					handler : function() {
						//location = 'ibmCust.jsp'
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
	getSearchForm : function() {//��ʼ����ѯ����
		this.panel = new Ext.form.FormPanel({
			id : 'searchPanel',
			region : "north",
			layout : 'column',
			height : 'auto',
			width : 'auto',
			frame : true,
			collapsible : true,
			defaults : {
				bodyStyle : 'padding:4px'
			},
		   title : "��ѯ�б�",
		   items :[{
				columnWidth : .5,
				layout : 'form',
				border : false,
				items : [
				new Ext.form.TextField({
				fieldLabel : '�����ص�',
				xtype : 'textfield',
				colspan : 0,
				rowspan : 0,
				id : 'name',
				name : 'name',
				style : '',
				width : 200,
				value : '',
				allowBlank : true,
				validator : '',
				vtype : ''
			    })
					]
				}]
		});
		return this.panel;//���ز�ѯ���
	},
	items : this.items,
	initComponent : function() {//��ʼ�����
		this.fp = this.getSearchForm();//��ȡ��ѯ���
		var sm = new Ext.grid.CheckboxSelectionModel();
		 //this.cm = new Ext.grid.ColumnModel(columns);
		this.store = new Ext.data.JsonStore({//�����·��б��ĸ�ʽ
			url : webContext + "/accountSystemAdminAction_listWorkSpace.action",
			fields: ['id','name','mailServer'],
			root:'data',
			totalProperty : 'rowCount',
			sortInfo: {field: "id", direction: "ASC"}
		});
		this.store.paramNames.sort = "orderBy";
		this.store.paramNames.dir = "orderType";
		// this.cm.defaultSortable = true;
		var viewConfig = Ext.apply({//��ͼ����
			forceFit : true
		}, this.gridViewConfig);

		this.pageBar = new Ext.PagingToolbar({//��ҳ������
			pageSize : 10,
			store : this.store,
			displayInfo : true,
			displayMsg : '��ǰ��ʾ {0}-{1}����¼ /��{2}����¼',
			emptyMsg : "����ʾ����"
		});
		this.formValue = '';
		this.pageBar.formValue = this.formValue;
		this.grid = new Ext.grid.GridPanel({//�趨grid
			region : "center",
			store : this.store,
			columns : [sm, {header : '���',dataIndex : 'id',align : 'left',sortable : true,hidden : true}, 
						{header : '�����ص�',dataIndex : 'name',align : 'left',sortable : true,hidden : false,width:200},
						{header : '�ʼ�������',dataIndex : 'mailServer',align : 'left',sortable : true,hidden : false,width:250}
						
						], 
			sm : sm,//��ÿ�е�ǰ�����һ��checkbox
			trackMouseOver : false,
			loadMask : true,
			y : 140,
			height : 200,
			anchor : '0 -35',
			bbar : this.pageBar,//���ӷ�ҳ��
			tbar : ['   ', { //�����Ϸ�������
				text : '����',
				pressed : true,
				handler : this.create, //ָ���ص�����
				scope : this,
				iconCls : 'add'
			}, '   ', {
				text : 'ɾ��',
				pressed : true,
				handler : this.remove,//ָ���ص�����
				scope : this,
				iconCls : 'remove'
			}, '    &nbsp;|&nbsp;    ', {
				text : '��ѯ',
				pressed : true,
				handler : this.search,//ָ���ص�����
				scope : this,
				iconCls : 'search'
			}, '   ', {
				text : ' ����',
				pressed : true,
				handler : this.reset,//ָ���ص�����
				scope : this,
				iconCls : 'reset'
			},'     ',{
				text : ' �޸�',
				pressed : true,
				handler : this.modify,//ָ���ص�����
				scope : this,
				iconCls : 'edit'
			}]
			
		});

		this.grid.on("headerdblclick", this.fitWidth, this);//���Ӽ�����
		this.grid.on("rowdblclick", this.modify, this);//���Ӽ�����
		var items = new Array();
		items.push(this.fp);	
		items.push(this.grid);
		this.items = items;//���齨����items��

		PagePanel.superclass.initComponent.call(this);//���ڵ��ʼ����ǰҳ��
		var param = {
			'mehtodCall' : 'query',
			'start' : 0,
			'status':1
		};

		this.pageBar.formValue = param;

		this.store.removeAll();
		this.store.load({
			params : param
		});
	},
	fitWidth : function(grid, columnIndex, e) {//����grid�еĵ�Ԫ���С
		var c = columnIndex;
		var w = grid.view.getHeaderCell(c).firstChild.scrollWidth;
		for (var i = 0, i = grid.store.getCount(); i < l; i++) {
			w = Math.max(w, grid.view.getCell(i, c).firstChild.scrollWidth);
		}
		grid.colModel.setColumnWidth(c, w);
	}
});