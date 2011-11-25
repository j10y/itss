var TreeSelector = function (g, f, h, e, c) {
	var d = {
		id : g,
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [[]]
		}),
		editable : false,
		mode : "local",
		fieldLabel : h,
		allowBlank : c == false ? false : true,
		triggerAction : "all",
		maxHeight : 200,
		tpl : "<tpl for='.'><div style='height:200px'><div id='" + g + "tree'></div></div></tpl>",
		selectedClass : "",
		onSelect : Ext.emptyFn
	};
	var loader = new Ext.tree.TreeLoader({
		url : f
	});
	loader.on('beforeload', function(treeloader, node) {
		treeloader.baseParams = {
			parentDN : node.id,
			method : 'tree'
		};
	});
	var b = new Ext.form.ComboBox(d);
	var a = new Ext.tree.TreePanel({
			id : g + "Tree",
			height : 200,
			autoScroll : true,
			split : true,
			loader : loader,
			root : new Ext.tree.AsyncTreeNode({
				id : "ou=orgnizations",
				expanded : true
			}),
			rootVisible : false
		});
	a.on("click", function (j) {
		var i = Ext.getCmp(e);
		if (j.id != null && j.id != "") {
			b.setValue(j.text);
			b.id = j.id;
			b.collapse();
			if (i != null) {
				i.setValue(j.id);
			}
		}
	});
	b.on("expand", function () {
		a.render(g + "tree");
	});
	return b;
};
