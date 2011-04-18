PagteModelDisTreePanel = Ext.extend(Ext.tree.TreePanel, {
	id:'tree',
	title: '服务目录关系',
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
				this.kernel = ''; //判断关系中包含的是服务目录还是服务项
				this.root = new Ext.tree.AsyncTreeNode({
					text: rootText,
					draggable: false,
					id: rootId
				}),

				//根据从表格得到数据的id 和页面模板的id 来加载后台数据
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