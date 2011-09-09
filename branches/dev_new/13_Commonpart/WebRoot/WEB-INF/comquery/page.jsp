<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>sql_jquery</title>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/icon.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/PageGrid/pagegrid.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/TreePanel/TreePanel.css">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/common.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/TreePanel.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/commontree.js"></script>
		<script
			src="${pageContext.request.contextPath}/JS/DatePicker/DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/commonFormFunction.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery.blockUI.js"></script>	
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/util.js"></script>	

		<script type="text/javascript">
		function queryList(key,value,columnid,ischecked){
		 var url="${pageContext.request.contextPath}/comquery_getTreeData.action";
		 var params={columnid:columnid};
		 $.post(
				url, //服务器要接受的url
				params, //传递的参数 
				function(json){ 
				var dataObj=eval("("+json+")");//
					root = dataObj;
					$("#tree-div").remove();
					$("#tt").append("<div id='tree-div'></div>");
					tree = new TreePanel({
						renderTo:'tree-div',
						'root' : root
					});
					tree.render();
					if(ischecked=='1'){
							var nodekey=$("#"+key).val();
							if(nodekey!=null&&nodekey.length>0){
								var nodekeys=nodekey.split(",");
								var nodevalue=$("#"+value).val();
								var nodevalues=nodevalue.split(",");
								for(var j=0;j<nodekeys.length;j++){
									tree.getNodeById(nodekeys[j]+"_"+nodevalues[j]).onCheckByTreeBack();//.setCheck("1");
								}
							}
							$("#treesubmit").click(function () {
								var chickeds=tree.getChecked();
							 	var checkid="";
							 	var checkval="";
							 	for(var i=0;i<chickeds.length;i++){
							 		var c=chickeds[i].split("_");
							 		if(c[1]!=undefined){
							 			checkid+=c[0]+",";
							 			checkval+=c[1]+",";
							 		}	
							 	}
							 	checkid=checkid.substring(0,checkid.length-1);
							 	checkval=checkval.substring(0,checkval.length-1);
							 	document.getElementById(key).value=checkid;
								document.getElementById(value).value=checkval;
								$('#treewindows').window('close');
							});
							document.getElementById("treesubmit").disabled="";
						}else{
							document.getElementById("treesubmit").disabled="disabled";
							tree.on(function(node){
								if(node.attributes.id!="-1"){
									document.getElementById(key).value=node.attributes.id;
									document.getElementById(value).value=node.attributes.text;
									$('#treewindows').window("close");
								}
							})
						}
				 	$('#treewindows').window('open');
				});
		 }
		 function getTitleList(){
		 var url="${pageContext.request.contextPath}/comquery_getTitleList.action";
		 var tableName=$("#tableName").val();
		 var params={tableName:tableName};
		 var modifyrule=$("#modifyrule").val();
		 $.post(
				url, //服务器要接受的url
				params, //传递的参数 
				function(json){ 
					var obj=eval("("+json+")");
					var titles="";
					for(var i=0;i<obj.length;i++){
						if(obj[i]['columnType']=="checked"){
							titles+="{field:'"+obj[i]['columnName']+"',title:'选择框',checkbox:true},";
						}else if(obj[i]['columnType']=="optioned"){
							if(modifyrule=="true"){
								titles+="{field:'"+obj[i]['columnName']+"',title:'"+obj[i]['columnCName']+"',width:"+obj[i]['columnWidth']+",sortable:true,formatter:function(value,rec){ var v=\"<a href=\\\"javascript:void(0)\\\" onclick=\\\"addObject('\"+value+\"')\\\">修改</a>\"; return v;}},";
							}else
								titles+="{field:'"+obj[i]['columnName']+"',title:'"+obj[i]['columnCName']+"',width:"+obj[i]['columnWidth']+",sortable:true,formatter:function(value,rec){ var v=\"<a href=\\\"javascript:void(0)\\\" onclick=\\\"viewObject('\"+value+\"')\\\">查看</a>\"; return v;}},";
						}else
							titles+="{field:'"+obj[i]['columnName']+"',title:'"+obj[i]['columnCName']+"',width:"+obj[i]['columnWidth']+",sortable:true},";
					}
					if(titles.length>0){
					titles=titles.substring(0,titles.length-1);
					}
					getpagegrid(titles);
				}
				);
		 }
		function getpagegrid(titles){
			$('#pagegrid').html("");
			var pageSize=$("#pageSize").val();
			var height=pageSize*27+20;
			var params=eval("({"+this.getformParms('queryform')+"})")
			var obtitle=eval("(["+titles+"])");
			//获取按钮权限
			var insertrule=$("#insertrule").val();
			var deleterule=$("#deleterule").val();
			var exportrule=$("#exportrule").val();
			if(insertrule=="true"){
				//insertrule=false;
				$("#buadd").linkbutton({disabled:false});
			}else{
				//insertrule=true;
				$("#buadd").linkbutton({disabled:true});
			}
			if(deleterule=="true"){
				//deleterule=false;
				$("#budelete").linkbutton({disabled:false});
			}else{
				//deleterule=true;
				$("#budelete").linkbutton({disabled:true});
			}
			if(exportrule=="true"){
				//exportrule=false;
				$("#buexport").linkbutton({disabled:false});
			}else{
				//exportrule=true;
				$("#buexport").linkbutton({disabled:true});
			}
			$('#pagegrid').datagrid({
					width:'auto',
					height:'auto',
					nowrap: true,
					striped: false,
					pageSize:pageSize,
					pageNumber:1,
					url:'comquery_toPage.action',
					remoteSort:true,
					queryParams:params,
					columns:[
						obtitle
					],
					pagination:true,
					rownumbers:true,
					/**
					toolbar:[
					{
						id:'btnadd',
						text:'添加',
						iconCls:'icon-add',
						disabled:insertrule,
						handler:function(){
							addObject('');
						}
					},'-',{
						id:'btndelete',
						text:'删除',
						iconCls:'icon-remove',
						disabled:deleterule,
						handler:function(){
							$('#btndelete').linkbutton('disable');
							var ids=[];
							var rows=$("#pagegrid").datagrid('getSelections');
							if(rows.length<1){
								//alert("删除选择不能为空");
								$.messager.alert('提示','删除选择不能为空!','info');
								$('#btndelete').linkbutton('enable');
							return false;
							}
							for(var i=0;i<rows.length;i++){
							ids.push(rows[i].id);
							}
							$.messager.confirm('提示', '确定需要删除吗?', function(r){
								if (r){
									//alert(ids.join(','));
									deleteColumnData(ids.join(','))
								}else{
									$('#btndelete').linkbutton('enable');
								}
							});
						}
					},'-',{
						id:'btnexport',
						text:'导出',
						iconCls:'icon-redo',
						disabled:exportrule,
						handler:function(){
							exportdate();
						}
					}
					],
					*/
					onHeaderContextMenu: function(e, field){
						e.preventDefault();
						if (!$('#tmenu').length){
							createColumnMenu();
						}
						$('#tmenu').menu('show', {
							left:e.pageX,
							top:e.pageY
						});
					}
			});
		}
		function exportdate(){
	  		var url="${pageContext.request.contextPath}/comquery_exportPageTableByAjax.action";
	 		var params=eval("({"+this.getformParms('queryform')+"})")
	 		waiting();
		 	$.get(
				url, //服务器要接受的url
				params, //传递的参数 
				function(json){
				var obj=eval("("+json+")");
				closeWait();
				if(obj['flag']=="true"){
					$("#exporturl").html('<center><a href="'+obj['url']+'">点击下载->'+obj['desc']+'</a></center>');
					$("#windowsexport").window('open');
				}else{
					$("#exporturl").html('<center>'+obj['desc']+'</center>');
					$("#windowsexport").window('open');
				}
			});
	 	}
	 	function deleteDate(){
	 		$("#budelete").linkbutton({disabled:true});
	 		var ids=[];
			var rows=$("#pagegrid").datagrid('getSelections');
			if(rows.length<1){
				$.messager.alert('提示','删除选择不能为空!','info');
				$("#budelete").linkbutton({disabled:false});
			}else{
				for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
				}
				$.messager.confirm('提示', '确定需要删除吗?', function(r){
					if (r){
						//alert(ids.join(','));
						deleteColumnData(ids.join(','));
						$("#budelete").linkbutton({disabled:false});
					}else{
					$("#budelete").linkbutton({disabled:false});
					}
				});
			}
	 	}
		function deleteColumnData(ids){
			var url="${pageContext.request.contextPath}/comquery_removeRealTable.action";
		 	var tableName=$("#tableName").val();
		 	var params={tableName:tableName,ids:ids};
		 	$.post(
				url, //服务器要接受的url
				params, //传递的参数 
				function(json){ 
					if(json=="true"){
						$.messager.alert('提示','删除成功!','info',function(){
							getTitleList();
						});
					}else{
						$.messager.alert('提示','删除失败!','info');
					}
				}
			);
		}
		function createColumnMenu(){
			var tmenu = $('<div id="tmenu" style="width:100px;"></div>').appendTo('body');
			var fields = $('#pagegrid').datagrid('getColumnFields');
			for(var i=0; i<fields.length; i++){
				$('<div iconCls="icon-ok"/>').html(fields[i]).appendTo(tmenu);
			}
			tmenu.menu({
				onClick: function(item){
					if (item.iconCls=='icon-ok'){
						$('#pagegrid').datagrid('hideColumn', item.text);
						tmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					} else {
						$('#pagegrid').datagrid('showColumn', item.text);
						tmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
					}
				}
			});
		}
		function addObject(value){
		 	var tableName=$("#tableName").val();
		 	var deleterule=$("#deleterule").val();
		 	var url="${pageContext.request.contextPath}/comquery_tableDetail.action?tableName="+tableName+"&realtableid="+value+"&deleterule="+deleterule;
		 	document.getElementById("dataTable").src=url;
		 	$('#tabledetailwindows').window('open');
	 	}
	 	function viewObject(value){
		 	var tableName=$("#tableName").val();
		 	var url="${pageContext.request.contextPath}/comquery_tableDetail.action?tableName="+tableName+"&realtableid="+value+"&isView=true";
		 	document.getElementById("dataTable").src=url;
		 	$('#tabledetailwindows').window('open');
	 	}
	 	function closeAddObjectWind(){
	 		$('#tabledetailwindows').window('close');
	 	}
	 	function getchart(id){
	 	var par=this.getformPaemsj('queryform');
	 	var url="${pageContext.request.contextPath}/jfchart_toStatisticsPicture.action?id="+id+"&"+par;
	 		document.getElementById("chartTable").src=url;
	 		$('#chartwindows').window('open');
	 	}
	</script>
	</head>
	<body onload="getTitleList();">
		<form id="queryform" name="queryform">
			<c:forEach var="item" items="${rulelist}">
				<c:if test="${item['operatename'] eq '增加'}">
				<input type="hidden" id="insertrule"name="insertrule" value="true"ispost="true">
				</c:if>
				<c:if test="${item['operatename'] eq '删除'}">
				<input type="hidden" id="deleterule"name="deleterule" value="true"ispost="true">
				</c:if>
				<c:if test="${item['operatename'] eq '修改'}">
				<input type="hidden" id="modifyrule"name="modifyrule" value="true"ispost="true">
				</c:if>
				<c:if test="${item['operatename'] eq '导出'}">
				<input type="hidden" id="exportrule"name="exportrule" value="true"ispost="true">
				</c:if>
			</c:forEach>
			<input type="hidden" id="tableName"name="tableName" value="${tableName }"
				ispost="true">
			<input type="hidden" id="keyColumnName"name="keyColumnName" value="${maintable.keyColumnName }"
				ispost="true">	
			<input type="hidden" id="pageSize"name="pageSize" value="${pageSize }"
				ispost="true">	
			<!-- 
  <div id="queryWindow"  class="easyui-window" closed="true" modal="true" title="<div align='left' style='font-size:14px;'>查询</div>" style="width:650px;height:400px;display:none">
	</div>   -->
			<c:set var="num" value="0"></c:set>
			<div id="querypanel" class="easyui-panel"
				title="<div align='center'>${maintable.tableCName }</div>" collapsible="true" minimizable="true" maximizable=true
				closable="false" style="width: auto; height: auto;">
				<table width="100%" class='MzTreeViewRow'>
					<c:forEach var="list" items="${queryList}" varStatus="status">
						<c:if test="${list.type eq 6}">
							<input type="hidden" name="${list.name }" value="${list.value}"
								ispost="true" />
						</c:if>
						<c:if test="${num%2 eq 0 }">
							<tr>
						</c:if>

						<c:if test="${list.type eq 1 or list.type eq 9}">
							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>${list.cname}</B>
							</td>
							<td class='MzTreeViewCell1'>
								<input style="height: 15px; width: 160px; font-size: 12px;"
									type="text" name="${list.name }" value="${list.value}"
									ispost="true" isclear="true" />
							</td>
						</c:if>

						<c:if test="${list.type eq 2}">
							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>${list.cname}</B>
							</td>
							<td class='MzTreeViewCell1'>
								<select name="${list.name }" id="${list.name }"
									style="height: 20px; width: 160px; font-size: 12px;"
									ispost="true" isclear="true">
									<option value="">
										请选择
									</option>
									<c:forEach var="map" items="${list.lists}">
										<option value="${map.id }"
											<c:if test="${map.id eq list.value}">selected</c:if>>
											${map.name }
										</option>
									</c:forEach>
								</select>
							</td>
						</c:if>

						<c:if
							test="${list.type eq 5 and list.mainTableColumn.dataType ne 3}">
							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>${list.cname}</B>
							</td>
							<td class='MzTreeViewCell1'>
								<input style="height: 15px; width: 160px; font-size: 12px;"
									class="Wdate" type="text" runat="server" name="${list.name }"
									value="${fn:substring(list.value,0,19) }" size="18"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									ispost="true" isclear="true">
							</td>
						</c:if>
						<c:if
							test="${list.type eq 5 and list.mainTableColumn.dataType eq 3}">
							<c:if test="${num%2 eq 1}">
								<td colspan="2" class='MzTreeViewCell1'></td>
								<c:set var="num" value="0"></c:set>
								</tr>
							</c:if>

							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>${list.cname}</B>
							</td>
							<td class='MzTreeViewCell1'>
								<input style="height: 15px; width: 160px; font-size: 12px;"
									class="Wdate" type="text" runat="server"
									name="${list.name }start"
									value="${fn:substring(list.startdate,0,19) }" size="18"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									ispost="true" isclear="true">
							</td>

							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>到</B>
							</td>
							<td class='MzTreeViewCell1'>
								<input style="height: 15px; width: 160px; font-size: 12px;"
									class="Wdate" type="text" runat="server"
									name="${list.name }end"
									value="${fn:substring(list.enddate,0,19) }" size="18"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									ispost="true" isclear="true">
							</td>
						</c:if>
						<c:if
							test="${list.type eq 10 and list.mainTableColumn.dataType ne 3}">
							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>${list.cname}</B>
							</td>
							<td class='MzTreeViewCell1'>
								<input style="height: 15px; width: 160px; font-size: 12px;"
									class="Wdate" type="text" runat="server" name="${list.name }"
									value="${fn:substring(list.value,0,19) }" size="18"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									ispost="true" isclear="true">
							</td>
						</c:if>
						<c:if
							test="${list.type eq 10 and list.mainTableColumn.dataType eq 3}">
							<c:if test="${num%2 eq 1}">
								<td colspan="2" class='MzTreeViewCell1'></td>
								<c:set var="num" value="0"></c:set>
								</tr>
							</c:if>

							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>${list.cname}</B>
							</td>
							<td class='MzTreeViewCell1'>
								<input style="height: 15px; width: 160px; font-size: 12px;"
									class="Wdate" type="text" runat="server"
									name="${list.name }start"
									value="${fn:substring(list.startdate,0,10)}" size="18"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
									ispost="true" isclear="true">
							</td>

							<c:set var="num" value="${num+1}"></c:set>
							<td noWrap class='MzTreeViewCell2' width="16%">
								<B>到${list.cname}</B>
							</td>
							<td class='MzTreeViewCell1'>
								<input style="height: 15px; width: 160px; font-size: 12px;"
									class="Wdate" type="text" runat="server"
									name="${list.name }end"
									value="${fn:substring(list.enddate,0,10) }" size="18"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
									ispost="true" isclear="true">
							</td>
						</c:if>
						<c:if test="${list.type eq 8}">
							<c:set var="num" value="${num+1}"></c:set>
							<td class='MzTreeViewCell2' width="16%" noWrap>
								<B>${list.cname}</B>
							</td>
							<td noWrap class='MzTreeViewCell1'>
								<input id="${list.name }" type="hidden" name="${list.name }"
									value="${list.lists[0].id}" ispost="true" isclear="true" />
								<input id="${list.name }_sn"
									style="height: 15px; width: 160px; font-size: 12px;" class="s"
									type="text" runat="server" name="${list.name }_sn"
									value="${list.lists[0].name}" size="18"
									onclick="queryList('${list.name }','${list.name }_sn','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }');"
									valueType="${valueType}" mustInput="${mustInput}" ispost="true"
									isclear="true" readonly />
								<!-- <input style="height:18px;width:40px;font-size: 12px;"type="button" name="query" value="查询" onclick="queryList('${list.name }','${list.name }_sn','${list.mainTableColumn.columnid }');" style="filter:Alpha(opacity=40);color:blue">   -->
							</td>
						</c:if>
						
						<c:if test="${num%2 eq 0 }">
							<c:set var="num" value="0"></c:set>
							</tr>
						</c:if>
					</c:forEach>
					<c:if test="${num%2 eq 1}">
						<td colspan="2" class='MzTreeViewCell1'>
							 <a href="javascript:getTitleList()" class="easyui-linkbutton" plain="false" icon="icon-search">查询</a>
							 <a href="javascript:clearForm('queryform')" class="easyui-linkbutton" plain="false" icon="icon-cancel">清空</a>
						</td>
						</tr>
					</c:if>
					<c:if test="${num%2 eq 0}">
						<td colspan="4" class='MzTreeViewCell1'>
							 <a href="javascript:getTitleList()" class="easyui-linkbutton" plain="false" icon="icon-search">查询</a>
							 <a href="javascript:clearForm('queryform')" class="easyui-linkbutton" plain="false" icon="icon-cancel">清空</a>
						</td>
						</tr>
					</c:if>
				</table>
			</div>
			<div style="background:#EFEFEF;padding:5px;width:auto;">
				<a href="javascript:addObject('')" id="buadd" class="easyui-linkbutton" iconCls="icon-add" plain="true" >添加</a>
				<a href="javascript:deleteDate()" id="budelete" class="easyui-linkbutton" iconCls="icon-remove"plain="true">删除</a>
				<a href="javascript:exportdate()" id="buexport" class="easyui-linkbutton" iconCls="icon-redo" plain="true" >导出</a>
				<a href="javascript:void(0)" id="mbchart" class="easyui-menubutton" menu="#mmchart" iconCls="icon-edit">报表</a>
			</div>
				<div id="pagegrid">
				</div>
				<div id="mmchart" style="width:150px;">
				<c:forEach var="item" items="${chartlist}">
				<div iconCls="icon-help"><a href="javascript:getchart('${item['id']}')" >${item['tjPictureName']}</a></div>
				</c:forEach>
				</div>
		</form>
		<div id="treewindows" class="easyui-window" closed="true" modal="true"
			title="<div align='left' style='font-size:14px'>数据列表</div>"
			style="width: 400px; height: 300px;">
			<div align="left">
				<input type="button" id="treesubmit" name="treesubmit" value="确定"
					disabled="disabled">
			</div>
			<div id='tt' align="left">
				<div id='tree-div'></div>
			</div>
		</div>
		<div id="tabledetailwindows" class="easyui-window" closed="true" modal="true" title="<div align='center'>${maintable.tableCName }</div>" style="width:530px;height:400px;">
  	 		<iframe id="dataTable"  frameborder="0" src="" style="width:100%;height:95%;"></iframe>
 		</div>
 		<div id="windowsexport" class="easyui-window" closed="true" modal="true" maximizable="true" minimizable="false" title="<div align='left'>导出文件</div>" style="width:200px;height:100px;">
		  	<table height="100%" width="100%">
			  	<tr>
				  	<td id="exporturl">
				  	</td>
			  	</tr>
		  	</table>
		</div>
		<div id="chartwindows" class="easyui-window" closed="true" modal="true" title="<div align='center'>${maintable.tableCName }</div>" style="width:530px;height:400px;">
  	 		<iframe id="chartTable"  frameborder="0" src="" style="width:100%;height:95%;"></iframe>
 		</div>
	</body>
</html>