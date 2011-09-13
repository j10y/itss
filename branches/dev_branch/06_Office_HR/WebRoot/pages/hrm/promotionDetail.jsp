<%@ page pageEncoding="UTF-8"%>
<%
	String basePath=request.getContextPath();
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.xpsoft.core.util.AppUtil"%>
<%@page import="com.xpsoft.oa.model.hrm.HrPromApply"%>
<%@page import="com.xpsoft.oa.service.hrm.HrPromApplyService"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>员工晋升申请</title>
		<%
			String hrPromApplyId = request.getParameter("hrPromApplyId");
			HrPromApplyService hrPromApplyService = (HrPromApplyService)AppUtil.getBean("hrPromApplyService");
			HrPromApply hrPromApply=new HrPromApply();
			if(StringUtils.isNotEmpty(hrPromApplyId)&&hrPromApplyId.indexOf("$")==-1){
				hrPromApply = hrPromApplyService.load(new Long(hrPromApplyId));
			}
			request.setAttribute("hrPromApply",hrPromApply);
		%>
	</head>

	<body>
		<div style="text-align:center;height:50px;line-height:50px;">
			<font style="font-size:18px;">晋升申请表</font>
		</div>
		<table style="width:700px;margin:0 auto;border:1px solid #000000" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">姓名</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromApply.applyUser.fullname}</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">部门/门店</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">${hrPromApply.depName}</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">入职日期</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">
					<fmt:formatDate value="${hrPromApply.accessionTime}" type="date"/>
				</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">现职位</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">${hrPromApply.nowPositionName}</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟担任职位</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromApply.applyPositionName}</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟晋升时间</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
					<fmt:formatDate value="${hrPromApply.applyDate}" type="date"/>
				</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">工作年限</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromApply.workYear}</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">本单位工作年限</td>
				<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">${hrPromApply.workHereYear}</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟晋升原因</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="applyReason" name="hrPromApply.applyReason" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.applyReason}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作目标设定</td>
			</tr>
			<tr>
				<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标一</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="target1" name="hrPromApply.target1" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target1}</textarea>
				</td>
			</tr>
			<tr>
				<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标二</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="target2" name="hrPromApply.target2" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target2}</textarea>
				</td>
			</tr>
			<tr>
				<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标三</td>
				<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="target3" name="hrPromApply.target3" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target3}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">员工面谈记录</td>
			</tr>
			<tr>
				<td colspan="4" style="height:150px;padding:2px;border:1px solid #000000;">
					<textarea readonly="readonly" id="intRecord" name="hrPromApply.intRecord" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.intRecord}</textarea>
				</td>
			</tr>
		</table>
	</body>
</html>
