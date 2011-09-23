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
		<title>员工转正申请</title>
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
				width:120px;
				text-align:center;
				border-left:1px solid #000000;
				border-top:1px solid #000000;
			}
		</style>
	</head>

	<body>
		<div style="text-align:center;height:30px;line-height:30px;">
			<font style="font-size:22px;font-weight:bold;">北京市上品商业发展有限责任公司</font><br/>
		</div>
		<div style="text-align:center;height:30px;line-height:30px;">
			<font style="font-size:22px;font-weight:bold;">员工转正申请表</font>
		</div>
		<table style="width:700px;margin:0 auto;border:1px solid #000000" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="6" class="label" style="border-right:1px solid #000000;">员工基本信息表</td>
			</tr>
			<tr>
				<td class="field">姓名</td>
				<td class="field">${hrPostApply.applyUser.fullname}</td>
				<td class="field">性别</td>
				<td class="field">${hrPostApply.gender}</td>
				<td class="field">年龄</td>
				<td class="field" style="border-right:1px solid #000000;padding:2px;">${hrPostApply.age}</td>
			</tr>
			<tr>
				<td class="field">部门/门店</td>
				<td class="field">${hrPostApply.deptName}</td>
				<td class="field">职务</td>
				<td class="field">${hrPostApply.postName}</td>
				<td class="field">入职日期</td>
				<td class="field" style="border-right:1px solid #000000;">
					<fmt:formatDate value="${hrPostApply.accessionTime}" type="date"/>
				</td>
			</tr>
			<tr>
				<td colspan="6" class="label" style="border-right:1px solid #000000;">员工试用期（实习）总结</td>
			</tr>
			<tr>
				<td colspan="6" style="height:600px;padding:2px;border:1px solid #000000;">
					<textarea readonly="readonly" id="proSummary" name="hrPostApply.proSummary" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostApply.proSummary}</textarea>
				</td>
			</tr>
		</table>
	</body>
</html>
