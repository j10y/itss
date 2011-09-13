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
		<title>员工晋升评估</title>
	</head>

	<body>
		<div style="text-align:center;height:50px;line-height:50px;">
			<font style="font-size:18px;">晋升评估表</font>
		</div>
		<table style="width:700px;margin:0 auto;border:1px solid #000000" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">姓名</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.promApply.applyUser.fullname}</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">部门/门店</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">${hrPromAssessment.promApply.depName}</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">入职日期</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">
					<fmt:formatDate value="${hrPromAssessment.promApply.applyUser.accessionTime}" type="date"/>
				</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">现职位</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
					${hrPromAssessment.promApply.nowPositionName}
				</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟担任职位</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.promApply.applyPositionName}</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟晋升时间</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
					<fmt:formatDate value="${hrPromAssessment.promApply.applyDate}" type="date"/>
				</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">工作年限</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.promApply.workYear}</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">本单位工作年限</td>
				<td colspan="2" style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">${hrPromAssessment.promApply.workHereYear}</td>
			</tr>
			<tr>
				<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作目标完成情况</td>
			</tr>
			<tr>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标一</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="reached1" name="hrPromAssessment.reached1" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
				</td>
			</tr>
			<tr>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标二</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="reached2" name="hrPromAssessment.reached2" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
				</td>
			</tr>
			<tr>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标三</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="reached3" name="hrPromAssessment.reached3" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作业绩</td>
			</tr>
			<tr>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">绩效结果</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="performResult" name="hrPromAssessment.performResult" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromAssessment.performResult}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作能力匹配</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">专业知识</td>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">沟通影响</td>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">问题解决能力</td>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">工作难度管理幅度</td>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">业务领域影响</td>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">评级结果</td>
			</tr>
			<tr>
				<td style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.proKnowledge}</td>
				<td style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.commEffect}</td>
				<td style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.solveAbility}</td>
				<td style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.difficultyManage}</td>
				<td style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.businessFieldEffect}</td>
				<td style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">${hrPromAssessment.ratingResult}</td>
			</tr>
			<tr>
				<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">正式任命时间</td>
				<td style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">
					<fmt:formatDate value="${hrPromAssessment.appointDate}" type="date"/>
				</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">岗位职级</td>
				<td style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">${hrPromAssessment.postRank}</td>
				<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">薪资等级</td>
				<td style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">${hrPromAssessment.salaryLevelName}</td>
			</tr>
			<tr>
				<td colspan="6" style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">晋升面谈</td>
			</tr>
			<tr>
				<td colspan="6" style="height:150px;padding:2px;border:1px solid #000000;">
					<textarea readonly="readonly" name="hrPromAssessment.promIntRecord" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromAssessment.promIntRecord}</textarea>
				</td>
			</tr>
		</table>
	</body>
</html>
