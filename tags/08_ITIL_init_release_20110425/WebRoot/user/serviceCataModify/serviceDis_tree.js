PagteModelDisTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '����Ŀ¼��ϵ',
	animate: true,
	autoScroll: true,
	rootVisible: true,
	containerScroll: true,
	//columnWidth:.5,
	lines: true,
	height: 500,
	enableDD: true,
	ddGroup: "tgDD",
	removeFlag: false,
	initComponent: function() {
			
				
				this.error = '0';
				this.kernel = ''; //�жϹ�ϵ�а������Ƿ���Ŀ¼���Ƿ�����
				this.root = new Ext.tree.AsyncTreeNode({
					text: rootText,
					draggable: false,
					id: rootId
				}),

				//���ݴӱ��õ����ݵ�id ��ҳ��ģ���id �����غ�̨����
				this.loader = new Ext.tree.TreeLoader({
					dataUrl:webContext+'/sciRelationShip_loadSCIRelationShip.action'
				});
				
				this.loader.on('beforeload', function(treeloader, node) {

					treeloader.baseParams = {
						id : node.id,
						rootId : rootId
					};

				}, this);

				PagteModelDisTreePanel.superclass.initComponent.call(this);
			}
		});