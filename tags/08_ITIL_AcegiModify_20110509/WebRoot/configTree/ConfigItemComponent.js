//ģ�����
ConfigItemComponentPanel = Ext.extend(Ext.Panel, {
	id : "tc",
	title : "������",
	closable : true,
	viewConfig : {
		autoFill : true,
		forceFit : true
	},
	layout : 'absolute',
	autoScroll : true,
	height : 1000,
	initComponent : function() {

		ConfigItemComponentPanel.superclass.initComponent.call(this);

		this.tree = new TreeDataPanel();
		this.tree.expandAll();
		alert(123);
		this.grid = new ConfigSourceDataPanel();
				
		this.panel = new Ext.Panel({
			y : 0,
			anchor : '0 -0',
			height : 800,
			layout:'column',
			items : [this.tree, this.grid],
			tbar : ['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
					'Դ������ѡ������������',
					new Ext.form.ComboBox({
						store : new Ext.data.JsonStore({
							url: webContext+'/BoxData.action',
							fields: ['id','name'],
						    root:'data',
							sortInfo: {field: "id", direction: "ASC"}
						}),
						valueField : "id",
						displayField : "name",
		                emptyText: '��ѡ����������',
						mode : 'remote',
						forceSelection : true,
						hiddenName : "id",
						editable : false,
						triggerAction : 'all', 
						lazyRender: true,
			            typeAhead: true,
						allowBlank : true,
						name : "name",
						selectOnFocus: true,
						listeners: {
							select: function(combo, record, index){
								//��ϵͳ����id������̨,�ں�̨����ϵͳ����������ģ�壬ͬʱ��ϵͳ����ID�ŵ� request�з���ҳ��
//								������ǵ������������б�ѡ���ʱ�򣬴�����Ӧ��ʱ��
								//id: ϵͳ����Id
								//var id = record.get('id');						
								this.grid.store.load();
						    },
							scope: this
						}
					})
		   ]
		});
        
		this.add(this.panel);
	}

});