Ext.tree.TriStateNodeUI=Ext.extend(Ext.tree.TreeNodeUI,{onCheckChange:function(){Ext.tree.TriStateNodeUI.superclass.onCheckChange.apply(this,arguments);var a=this.node;while((a=a.parentNode)&&a.getUI().updateParent&&a.getUI().checkbox&&!a.getUI().isUpdating){a.getUI().updateParent();}},toggleCheck:function(){var a=Ext.tree.TriStateNodeUI.superclass.toggleCheck.apply(this,arguments);this.updateChild(a);return a;},renderElements:function(e,c,d,b){Ext.tree.TriStateNodeUI.superclass.renderElements.apply(this,arguments);this.updateChild(this.node.attributes.checked);},updateParent:function(){var a;this.node.eachChild(function(b){if(a===undefined){a=b.attributes.checked;}else{if(a!==b.attributes.checked){a=this.grayedValue;return false;}}},this);this.toggleCheck(a);},updateChild:function(a){if(typeof a=="boolean"){this.isUpdating=true;this.node.eachChild(function(b){b.getUI().toggleCheck(a);},this);delete this.isUpdating;}}});Ext.tree.AsynchTriStateNodeUI=Ext.extend(Ext.tree.TriStateNodeUI,{updateChild:function(a){if(this.checkbox){if(a===true){Ext.fly(this.ctNode).replaceClass("x-tree-branch-unchecked","x-tree-branch-checked");}else{if(a===false){Ext.fly(this.ctNode).replaceClass("x-tree-branch-checked","x-tree-branch-unchecked");}else{Ext.fly(this.ctNode).removeClass(["x-tree-branch-checked","x-tree-branch-unchecked"]);}}}},getChecked:function(){var a=this.node.parentNode?this.node.parentNode.ui.getChecked():this.grayedValue;return typeof a=="boolean"?a:Ext.tree.TriStateNodeUI.superclass.getChecked.call(this);}});