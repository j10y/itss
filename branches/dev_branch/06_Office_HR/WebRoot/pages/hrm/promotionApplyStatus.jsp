<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%
	String basePath=request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>员工晋升申请</title>
		<style type="text/css">
			.label {
				width:120px;
				height:40px;
				text-align:center;
				border-left:1px solid #000000;
				border-top:1px solid #000000;
				background:#ddd;
			}
			.field {
				height:40px;
				text-align:center;
				border-left:1px solid #000000;
				border-top:1px solid #000000;
			}
		</style>
	</head>

	<body>
		<div style="text-align:center;height:50px;line-height:50px;">
			<font style="font-size:18px;">晋升申请表</font>
		</div>
		<table style="width:700px;margin:0 auto;border:1px solid #000000" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">姓名</td>
				<td class="field" style="width:230px;">${hrPromApply.applyUser.fullname}</td>
				<td class="label">部门/门店</td>
				<td class="field" style="border-right:1px solid #000000;">${hrPromApply.depName}</td>
			</tr>
			<tr>
				<td class="label">入职日期</td>
				<td class="field">
					<fmt:formatDate value="${hrPromApply.accessionTime}" type="date"/>
				</td>
				<td class="label">现职位</td>
				<td class="field" style="border-right:1px solid #000000;">${hrPromApply.nowPositionName}</td>
			</tr>
			<tr>
				<td class="label">拟担任职位</td>
				<td class="field">${hrPromApply.applyPositionName}</td>
				<td class="label">拟晋升时间</td>
				<td class="field" style="border-right:1px solid #000000;">
					<fmt:formatDate value="${hrPromApply.applyDate}" type="date"/>
				</td>
			</tr>
			<tr>
				<td class="label">工作年限</td>
				<td class="field">${hrPromApply.workYear}</td>
				<td class="label">本单位工作年限</td>
				<td class="field" style="border-right:1px solid #000000;">${hrPromApply.workHereYear}</td>
			</tr>
			<tr>
				<td class="label">拟晋升原因</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="applyReason" name="hrPromApply.applyReason" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.applyReason}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="label" style="border-right:1px solid #000000;">工作目标设定</td>
			</tr>
			<tr>
				<td class="label">目标一</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="target1" name="hrPromApply.target1" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target1}</textarea>
				</td>
			</tr>
			<tr>
				<td class="label">目标二</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="target2" name="hrPromApply.target2" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target2}</textarea>
				</td>
			</tr>
			<tr>
				<td class="label">目标三</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="target3" name="hrPromApply.target3" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target3}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="label" style="border-right:1px solid #000000;">员工面谈记录</td>
			</tr>
			<tr>
				<td colspan="4" style="height:150px;padding:2px;border:1px solid #000000;">
					<textarea readonly="readonly" id="intRecord" name="hrPromApply.intRecord" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.intRecord}</textarea>
				</td>
			</tr>
		</table>
	</body>
</html>
