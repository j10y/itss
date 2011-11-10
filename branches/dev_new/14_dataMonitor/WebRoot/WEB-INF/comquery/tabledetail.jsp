<%@ page contentType="text/html; charset=utf8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详细信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
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
		<script
			src="${pageContext.request.contextPath}/JS/DatePicker/DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/commonFormFunction.js"></script>
		<script type="text/javascript">
		var GlobalUtil={"weburl":"${pageContext.request.contextPath}"};
	function returnTableDetail(){
	  window.location.href="${pageContext.request.contextPath}/admin/maintablelist.do?methodCall=queryPageTable&tableName=${maintable.tableName }";
	} 
	 function queryListSel(key,value,columnid,ischecked,allTreeStatus){
	 var url="${pageContext.request.contextPath}/comquery_getTreeData.action";
	 var params={columnid:columnid};
	 var tree;
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
							if(allTreeStatus=='1'){
								tree.getNodeById(nodekeys[j]+"_"+nodevalues[j]).setCheck("1");//.onCheckByTreeBack();
							}else{
								tree.getNodeById(nodekeys[j]+"_"+nodevalues[j]).onCheckByTreeBack();//.setCheck("1");
							}	
						}
					}
					//com_tree.getNodeById(nodeid).setCheck("1");
					$("#treesubmit").click(function () {
						var chickeds=tree.getChecked();
						if(allTreeStatus=='1'){
							chickeds=tree.getAllChecked();
						}
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
						$('#detailwindows').window('close');
					});
					document.getElementById("treesubmit").disabled="";
				}else{
					document.getElementById("treesubmit").disabled="disabled";
					tree.on(function(node){
						if(node.attributes.id!="-1"){
							if(node.attributes.checktype!=node.attributes.ischecktype){
								alert("请选择正确的类型!");
								return false;
							}
							document.getElementById(key).value=node.attributes.id;
							document.getElementById(value).value=node.attributes.text;
							$('#detailwindows').window('close');
						}
					})
				}
			 	$('#detailwindows').window('open');
			});
		
	 }
	 function addObject(){
	 	if(checkForm('queryform')==false){
	 	}else{
	 	var url="${pageContext.request.contextPath}/comquery_addRealTable.action";
	 	var params=eval("({"+this.getformParms('queryform')+"})")
		 $.post(
				url, //服务器要接受的url
				params, //传递的参数 
				function(json){
				if(json=="true"){
					$.messager.alert('提示','保存成功!','info',function(){
						parent.closeAddObjectWind();
						parent.getTitleList();
					});
				}else{
					$.messager.alert('提示','保存失败!','info');
				}  
			});
	 	}
	 }
	 function deleteObject(){
	 	$.messager.confirm('提示', '确定需要删除吗?', function(r){
			if (r){
				var url="${pageContext.request.contextPath}/comquery_removeRealTable.action";
			 	var tableName=$("#tableName").val();
			 	var ids=$("#realtableid").val();
			 	var params={tableName:tableName,ids:ids};
			 	$.post(
					url, //服务器要接受的url
					params, //传递的参数 
					function(json){ 
						if(json=="true"){
							$.messager.alert('提示','删除成功!','info',function(){
								parent.closeAddObjectWind();
								parent.getTitleList();
							});
						}else{
							$.messager.alert('提示','删除失败!','info');
						}
					}
				);
			}else{
				
			}
		});
	 }
	</script>
	</head>

	<body topMargin='0' leftMargin='0' style="">
		<div>
			<form name="queryform" action="" method="post">
				<div align="center">
					<input type="hidden" id="maintableid" name="maintableid"
						value="${maintable.tableid }" ispost="true">
					<input type="hidden" id="realtableid" name="realtableid"
						value="${realTableMap[maintable.keyColumnName]}" ispost="true">
					<input type="hidden" id="keyColumnName" name="keyColumnName"
						value="${maintable.keyColumnName}" ispost="true">
					<input type="hidden" id="tableName" name="tableName"
						value="${maintable.tableName }" ispost="true">
					<input type="hidden" id="tableRealName"name="tableRealName" value="${maintable.tableRealName }" ispost="true">	
					<input id="userid" name="userid" type="hidden" value="${session.userid}"
						ispost="true" />
					<c:set var="num" value="0"></c:set>
					<c:if test="${realTableMap[maintable.keyColumnName] ne null}">
						<c:set var="isupdate" value="1"></c:set>
					</c:if>
					<c:if
						test="${realTableMap[maintable.keyColumnName] eq null or realTableMap[maintable.keyColumnName] eq ''}">
						<c:set var="isupdate" value="0"></c:set>
					</c:if>
					<table class='MzTreeViewRow' style="font-size: 12px;" width="100%">
						<c:forEach var="list" items="${detailList}" varStatus="status">
							<c:set var="valueType" value="true" scope="page" />
							<c:if test="${list.mainTableColumn.isMust eq 1 }">
								<c:set var="mustInput" value="true" scope="page" />
							</c:if>
							<c:if test="${list.mainTableColumn.isMust ne 1 }">
								<c:set var="mustInput" value="false" scope="page" />
							</c:if>
							<c:if test="${list.type eq 6}">
								<input type="hidden" name="${list.name }"
									value="${realTableMap[list.name]}" ispost="true" />
							</c:if>
							<c:if test="${num%2 eq 0 }">
								<tr>
							</c:if>
							<c:if test="${list.type eq 1}">
								<c:set var="num" value="${num+1}"></c:set>
								<td noWrap class='MzTreeViewCell2'>
									<B>${list.cname}</B>
								</td>
								<td class='MzTreeViewCell1' noWrap>
									<input style="height: 20px; width: 160px; font-size: 12px;"
										type="text" id="${list.name }" name="${list.name }"
										value="${realTableMap[list.name]}"
										valueType="${list.mainTableColumn.validDataType}"
										isunique="${list.mainTableColumn.isUnique}"
										mustInput="${mustInput}" onblur="validData('${list.name }');"
										ispost="true"
										<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">disabled="disabled"</c:if> />
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
							</c:if>
							<c:if test="${list.type eq 11}">
								<c:if test="${num%2 eq 1}">
									<c:set var="num" value="${num+1}"></c:set>
									<td colspan="2" class='MzTreeViewCell2'></td>
									</tr>
									<tr>
								</c:if>
								<c:set var="num" value="${num+2}"></c:set>
								<td noWrap class='MzTreeViewCell2'>
									<B>${list.cname}</B>
								</td>
								<td class='MzTreeViewCell1' noWrap>
									<input style="height: 20px; width: 160px; font-size: 12px;"
										type="password" id="${list.name }" name="${list.name }"
										value="${realTableMap[list.name]}"
										valueType="${list.mainTableColumn.validDataType}"
										valueSePass="true" mustInput="${mustInput}"
										onblur="validData('${list.name }');" ispost="true" />
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
								<td noWrap class='MzTreeViewCell2'>
									<B>确认${list.cname}</B>
								</td>
								<td class='MzTreeViewCell1' noWrap>
									<input style="height: 20px; width: 160px; font-size: 12px;"
										type="password" id="${list.name }_se" name="${list.name }_se"
										value="${realTableMap[list.name]}"
										valueType="${list.mainTableColumn.validDataType}"
										mustInput="${mustInput}" onblur="validData('${list.name }');"
										ispost="true" />
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
							</c:if>
							<c:if test="${list.type eq 2}">
								<c:set var="num" value="${num+1}"></c:set>
								<td noWrap class='MzTreeViewCell2'>
									<B>${list.cname}</B>
								</td>
								<td class='MzTreeViewCell1' noWrap>
									<select name="${list.name }" id="${list.name }"
										style="height: 20px; width: 160px; font-size: 12px;"
										valueType="${valueType}" mustInput="${mustInput}"
										ispost="true"
										isunique="${list.mainTableColumn.isUnique }"
										onchange="validData('${list.name }');"
										<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">disabled="disabled"</c:if>>
										<option value="">
											请选择
										</option>
										<c:forEach var="map" items="${list.lists}">
											<option value="${map.id }"
												<c:if test="${map.id eq realTableMap[list.name]}">selected</c:if>>
												${map.name }
											</option>
										</c:forEach>
									</select>
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
							</c:if>
							<c:if test="${list.type eq 5}">
								<c:set var="num" value="${num+1}"></c:set>
								<td class='MzTreeViewCell2' noWrap>
									<B>${list.cname}</B>
								</td>
								<td noWrap class='MzTreeViewCell1'>
									<input style="height: 20px; width: 160px; font-size: 12px;"
										class="Wdate" type="text" runat="server" name="${list.name }"
										value="${fn:substring(realTableMap[list.name],0,19)}"
										size="18"
										onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										valueType="${valueType}" mustInput="${mustInput}"
										ispost="true"
										<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">disabled="disabled"</c:if> />
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
							</c:if>
							<c:if test="${list.type eq 10}">
								<c:set var="num" value="${num+1}"></c:set>
								<td class='MzTreeViewCell2' noWrap>
									<B>${list.cname}</B>
								</td>
								<td noWrap class='MzTreeViewCell1'>
									<input style="height: 20px; width: 160px; font-size: 12px;"
										class="Wdate" type="text" runat="server" name="${list.name }"
										value="${fn:substring(realTableMap[list.name],0,10)}"
										size="18"
										onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
										valueType="${valueType}" mustInput="${mustInput}"
										ispost="true"
										<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">disabled="disabled"</c:if> />
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
							</c:if>
							<c:if test="${list.type eq 8}">
								<c:set var="num" value="${num+1}"></c:set>
								<td class='MzTreeViewCell2' noWrap>
									<B>${list.cname}</B>
								</td>
								<td noWrap class='MzTreeViewCell1'>
									<input id="${list.name }" type="hidden" name="${list.name }"
										value="${list.lists[0].id}" ispost="true" />
									<input id="${list.name }_sn"
										style="height: 20px; width: 160px; font-size: 12px;" class="s"
										type="text" runat="server" name="${list.name }_sn"
										value="${list.lists[0].name}" size="18"
										onclick="queryListSel('${list.name }','${list.name }_sn','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }','${list.mainTableColumn.allTreeStatus }');"
										valueType="${valueType}" mustInput="${mustInput}"
										ispost="true" readonly
										<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">disabled="disabled"</c:if> />
									<!--  <input style="height:18px;width:40px;font-size: 12px;"type="button" name="query" value="查询" onclick="queryList('${list.name }','${list.name }_sn','${list.mainTableColumn.columnid }');" style="filter:Alpha(opacity=40);color:blue">  -->
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
							</c:if>
							<c:if test="${list.type eq 9}">
								<c:if test="${num%2 eq 1}">
									<c:set var="num" value="${num+1}"></c:set>
									<td colspan="2" class='MzTreeViewCell2'></td>
									</tr>
									<tr>
								</c:if>
								<c:set var="num" value="${num+2}"></c:set>
								<td class='MzTreeViewCell2'>
									<B>${list.cname}</B>
								</td>
								<td noWrap class='MzTreeViewCell1' colspan="3">
									<c:set var="tablekey"
										value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
									<textarea name="${list.name }" rows="${tablekey[1] }"
										cols="55" ispost="true" valueType="${valueType}"
										mustInput="${mustInput}"
										<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">disabled="disabled"</c:if>>${realTableMap[list.name]}</textarea>
									<c:if test="${list.mainTableColumn.isMust eq 1 }">
										<font color="#ff0000">*</font>
									</c:if>
								</td>
							</c:if>
						</c:forEach>
						<c:if test="${num%2 eq 1}">
							<td colspan="2" class='MzTreeViewCell2'>
							</td>
							</tr>
						</c:if>
							<tr>
							<td colspan="4" class='MzTreeViewCell1'>
							<a href="javascript:addObject()" class="easyui-linkbutton" icon="icon-remove">保存</a>
							<c:if test="${deleterule eq 'true'}">
							<a href="javascript:deleteObject()" class="easyui-linkbutton"  icon="icon-remove">删除</a>
							</c:if>
							</td>
							</tr>
					</table>
			</form>
		</div>
		<div id="detailwindows" class="easyui-window" closed="true"
			modal="true" title="数据列表" style="width: 400px; height: 300px;">
			<input type="button" id="treesubmit" name="treesubmit" value="确定"
				disabled="disabled">
			<div id='tt'>
				<div id='tree-div'></div>
			</div>
		</div>
	</body>
</html>
