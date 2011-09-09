var com_tree;
function queryList(ischecked,treetype){
 //var ischecked=ischecked;
// var treetype=treetype;
 var url="${pageContext.request.contextPath}/tree_getTree.action";
 var params={ischecked:ischecked,treetype:treetype};
 $.post(
		url, //服务器要接受的url
		params, //传递的参数 
		function(json){ 
		var dataObj=eval("("+json+")");//
			root = dataObj;
			$("#tree-div").remove();
			$("#tt").append("<div id='tree-div' style='font-size:12;'></div>");
			com_tree = new TreePanel({
				renderTo:'tree-div',
				'root' : root
			});
			com_tree.render();
			//com_tree.expandAll();
			com_tree.on(function(node){
				if(ischecked==0){
					//var nodeids=node.attributes.id;
					//var nodeid=nodeids.split("_");
					optionClickTree(node);
				}				
			})
		});
 }
 //checkType:树的选择类型
//displayType:树的显示类型（节点？部门？用户？设备）
//displayName:树的错误提示名称（请选择**树）
//nodeId:树的根节点ID
//keys:选择之后，往原页面赋值的元素ID,数组类型
//filter:是否显示设备的状态
 function showTree(ischecked,treetype,treename,nodeid,keys,filter){
 $("#tree-div").attr('align','center');
$("#tree-div").html('<img src="Images/Common/Loading1.gif" >');
 var url="${pageContext.request.contextPath}/tree_showTree.action";
 var params={ischecked:ischecked,treetype:treetype,nodeid:nodeid};
 $.post(
		url, //服务器要接受的url
		params, //传递的参数 
		function(json){ 
		var dataObj=eval("("+json+")");//
			root = dataObj;
			$("#tree-div").remove();
			$("#tt").append("<div id='tree-div' style='font-size:12;'></div>");
			com_tree = new TreePanel({
				renderTo:'tree-div',
				'root' : root
			});
			com_tree.render();
			com_tree.on(function(node){
					var nodeId =node.attributes.id;
					var nodeIds = nodeId.split('_');
					if(nodeIds[0]=='pass'){
						return ;
					}
					var nodeValues=node.attributes.values;
					var nodeText = node.attributes.text;
					if(nodeValues!=undefined){
					nodeValues=nodeValues.split("_");
					if(keys!=null){
						if(nodeValues[0]==treetype){
							if(filter!=undefined){
								if(nodeText.indexOf('(')!=-1){
									alert('此'+treename+'正在使用,无法选择!');
								}else{
								setValues(keys,nodeValues);
								}
							}else{
								setValues(keys,nodeValues);
								}
						}else{
							alert('请选择正确的'+treename+'！');
						}
					}else{
						//如果点击的设备和树的类型相同，则执行回调函数
						if(nodeValues[0]==treetype){
						callBack(nodeValues);
//						node.onCheck();
						}
					}
				}				
			})
		});
 }
 //显示选中的树
 function showCheckTree(ischecked,treetype,treename,nodeid,checkIds){
 $("#tree-div").attr('align','center');
$("#tree-div").html('<img src="Images/Common/Loading1.gif" >');
 var url="${pageContext.request.contextPath}/tree_showTree.action";
 var params={ischecked:ischecked,treetype:treetype,nodeid:nodeid};
 $.post(
		url, //服务器要接受的url
		params, //传递的参数 
		function(json){ 
		var dataObj=eval("("+json+")");//
			root = dataObj;
			$("#tree-div").remove();
			$("#tt").append("<div id='tree-div' style='font-size:12;'></div>");
			com_tree = new TreePanel({
				renderTo:'tree-div',
				'root' : root
			});
			com_tree.render();
			var checks = checkIds.split(",");
			for(var i=0;i<checks.length;i++){
				var node =com_tree.getNodeById(checks[i]);
				if(node!=undefined){
					node.onCheck();
				}
			}
			com_tree.on(function(node,type){
				if(type!=undefined){
						var nodeValues=node.attributes.values;
							nodeValues=nodeValues.split("_");
						//如果点击的设备和树的类型相同，则执行回调函数
						if(nodeValues[0]==treetype){
							changeCheck(node,nodeValues);
						}else{
							var parentFlag = node.checked;
							var rules =	node.findMyChilds('icon','terminal');
							if(rules.length>0){
								changeChecks(rules,parentFlag);
							}else{
								alert('此节点下没有电报终端！');
								node.onCheck();
								return ;
							}
						}
				}		
			})
		});
 }
 //批量更改页面上选中的终端
 function changeChecks(nodes,parentFlag){
 	var flag = false ;
 	var addRules =' ';
 	var nodeValues ='';
 	var addStrs = new Array();
 	var a = 0;
 	for(var m =0;m<nodes.length;m++){
		if(nodes[m].checked==0){
			flag = true;
			addRules+=nodes[m].attributes.text+' ';
			addStrs[a] = nodes[m];
			a++;
		}
	}
	if(flag ==false){
		if(parentFlag==2){
			alert('没有有效的电报终端被选中');
			return ;
		}
		if(confirm('您确定要移除此节点下的所有电报终端吗？')){
			for(var m =0;m<nodes.length;m++){
				nodeValues=nodes[m].attributes.values;
				nodeValues=nodeValues.split("_");
				callBack(nodeValues,false);
			}
		}else{
 			node.onCheck();
 		}
	}else{
		if(confirm('您确定要增加('+addRules+')'+a+'个电报终端吗？')){
			for(var m =0;m<addStrs.length;m++){
				nodeValues=addStrs[m].attributes.values;
				nodeValues=nodeValues.split("_");
				callBack(nodeValues,true);
			}
		}else{
 			node.onCheck();
 		}
	}
 }
  //更改页面上选中的终端
 function changeCheck(node,nodeValues){
 	var tip ='';
 	var val ='';
 	if(node.checked==0){
 		tip='增加';
 		val = true;
 	}else{
 		tip='移除';
 		val =false ;
 	}
 	if(confirm('您确定'+tip+'吗？')){
 			callBack(nodeValues,val);
 		}else{
 			node.onCheck();
 		}
 }
  function removeCheck(nodeId){
 	var node =com_tree.getNodeById(nodeId);
 	if(node!=undefined){
			node.onCheck();
		}
 }
  function queryListByNodes(ischecked,treetype,treename,nodeid){
  //alert();
 var url="${pageContext.request.contextPath}/tree_showTreeByNodes.action";
 var params={ischecked:ischecked,treetype:treetype,nodeid:nodeid,treename:treename};
 $.post(
		url, //服务器要接受的url
		params, //传递的参数 
		function(json){ 
		var dataObj=eval("("+json+")");//
			root = dataObj;
			$("#tree-div").remove();
			$("#tt").append("<div id='tree-div' style='font-size:12;'></div>");
			com_tree = new TreePanel({
				renderTo:'tree-div',
				'root' : root
			});
			com_tree.render();
			com_tree.on(function(node){
				if(ischecked==0){
//					var nodeid=node.attributes.id;
//					var nodeids=nodeid.split("_");
//					var name=node.attributes.text;
					optionClickTree(node);
				}				
			})
		});
 }
 //当树加载成功后执行一个方法 
 function queryListBack(ischecked,treetype){
 //var ischecked=ischecked;
// var treetype=treetype;
 var url="${pageContext.request.contextPath}/tree_getTree.action";
 var params={ischecked:ischecked,treetype:treetype};
 $.post(
		url, //服务器要接受的url
		params, //传递的参数 
		function(json){ 
		var dataObj=eval("("+json+")");//
			root = dataObj;
			$("#tree-div").remove();
			$("#tt").append("<div id='tree-div' style='font-size:12;'></div>");
			com_tree = new TreePanel({
				renderTo:'tree-div',
				'root' : root
			});
			com_tree.render();
			//com_tree.expandAll();
			//alert(com_tree.getNodeById("node_1"));
			getNode(com_tree);
			com_tree.on(function(node){
				if(ischecked==0){
//					var nodeids=node.attributes.id;
//					var nodeid=nodeids.split("_");
					optionClickTree(node);
				}				
			})
		});
 }	 
 function getCheckedId(treetype){
 	var chickeds=com_tree.getChecked();
 	//alert(chickeds);
 	//var chickeda=chickeds.split(",");
 	var checknew="";
 	for(var i=0;i<chickeds.length;i++){
 		var c=chickeds[i].split("_");
 		if(c[0]==treetype){
 			checknew+=c[1]+",";
 		}
 	}
 	checknew=checknew.substring(0,checknew.length-1);
 	return checknew;
 }
 
function getRoleOperate(){
 //var ischecked=ischecked;
// var treetype=treetype;
 var url="${pageContext.request.contextPath}/systemManage_getRoleTree.action";
 var params={};
 $.post(
		url, //服务器要接受的url
		params, //传递的参数 
		function(json){ 
		var dataObj=eval("("+json+")");//
			root = dataObj;
			$("#tree-div").remove();
			$("#tt").append("<div id='tree-div' style='font-size:12;'></div>");
			com_tree = new TreePanel({
				renderTo:'tree-div',
				'root' : root
			});
			com_tree.render();
			//com_tree.expandAll();
			getUserGroupNode(com_tree);
		});
 }
 function getCheckedRoleId(){
 	var chickeds=com_tree.getAllChecked();
 	var checknew="";
 	for(var i=0;i<chickeds.length;i++){
 		if(chickeds[i]!="-1"){
 			checknew+=chickeds[i]+"_";
 		}
 	}
 	checknew=checknew.substring(0,checknew.length-1);
 	return checknew;
 }
 
 function addTreeNode(parNodeId,node){
 //var newNode = new TreeNode({text:text});
 	var pNode =com_tree.getNodeById('node_'+parNodeId);
 	if(pNode!=null){
 		pNode.appendChild(node);
 	}else{
 		alert("没有父节点!");
 	} 	
 }
 function insertBeforeTreeNode(parNodeId,node,refNodeId){
 //var newNode = new TreeNode({text:text});
 	var pNode =com_tree.getNodeById('node_'+parNodeId);
 	var refNode =com_tree.getNodeById('node_'+refNodeId);
 	//alert(pNode);
 	//alert(refNode);
 	if(pNode!=null){
 		pNode.insertBefore(node,refNode);
 	}else{
 		alert("没有父节点!");
 	} 	
 }
