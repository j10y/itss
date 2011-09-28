<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@page import="com.xpsoft.oa.service.hrm.HrPostApplyService"%>
<%@page import="com.xpsoft.core.util.AppUtil"%>
<%@page import="com.xpsoft.oa.model.hrm.HrPostApply"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
	String basePath=request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>员工转正申请</title>
		<%
			String hrPostApplyId = request.getParameter("hrPostApplyId");
			HrPostApplyService hrPostApplyService = (HrPostApplyService)AppUtil.getBean("hrPostApplyService");
			HrPostApply hrPostApply=new HrPostApply();
			if(StringUtils.isNotEmpty(hrPostApplyId)&&hrPostApplyId.indexOf("$")==-1){
				hrPostApply = hrPostApplyService.load(new Long(hrPostApplyId));
			}
			request.setAttribute("hrPostApply",hrPostApply);
		%>
		<style type="text/css">
			.field {
				height:40px;
				width:120px;
				text-align:center;
				border-left:1px solid #000000;
				border-top:1px solid #000000;
				
				height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;
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
				<td colspan="6" style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">员工基本信息表</td>
			</tr>
			<tr>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">姓名</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPostApply.applyUser.fullname}&nbsp;</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">性别</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPostApply.gender}&nbsp;</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">年龄</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;padding:2px;">${hrPostApply.age}&nbsp;</td>
			</tr>
			<tr>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">部门/门店</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPostApply.deptName}&nbsp;</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">职务</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPostApply.postName}&nbsp;</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">入职日期</td>
				<td style="height:40px;width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
					<fmt:formatDate value="${hrPostApply.accessionTime}" type="date"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="6" style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">员工试用期（实习）总结</td>
			</tr>
			<tr>
				<td colspan="6" style="height:600px;padding:2px;border:1px solid #000000;">
					<textarea readonly="readonly" id="proSummary" name="hrPostApply.proSummary" style="width:100%;*width:690px;height:100%;*height:590px;border:none;overflow-y:hidden;">${hrPostApply.proSummary}</textarea>
				</td>
			</tr>
		</table>
	</body>
</html>
