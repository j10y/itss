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
			<font style="font-size:18px;">晋升评估表</font>
		</div>
		<table style="width:700px;margin:0 auto;border:1px solid #000000" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">姓名</td>
				<td colspan="2" class="field">${hrPromAssessment.promApply.applyUser.fullname}&nbsp;</td>
				<td class="label">部门/门店</td>
				<td colspan="2" class="field" style="border-right:1px solid #000000;">${hrPromAssessment.promApply.depName}&nbsp;</td>
			</tr>
			<tr>
				<td class="label">入职日期</td>
				<td colspan="2" class="field">
					<fmt:formatDate value="${hrPromAssessment.promApply.applyUser.accessionTime}" type="date"/>&nbsp;
				</td>
				<td class="label">现职位</td>
				<td colspan="2" class="field" style="border-right:1px solid #000000;">
					${hrPromAssessment.promApply.nowPositionName}&nbsp;
				</td>
			</tr>
			<tr>
				<td class="label">拟担任职位</td>
				<td colspan="2" class="field">${hrPromAssessment.promApply.applyPositionName}&nbsp;</td>
				<td class="label">拟晋升时间</td>
				<td colspan="2" class="field" style="border-right:1px solid #000000;">
					<fmt:formatDate value="${hrPromAssessment.promApply.applyDate}" type="date"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="label">工作年限</td>
				<td colspan="2" class="field">${hrPromAssessment.promApply.workYear}&nbsp;</td>
				<td class="label">本单位工作年限</td>
				<td colspan="2" class="field" style="border-right:1px solid #000000;">${hrPromAssessment.promApply.workHereYear}&nbsp;</td>
			</tr>
			<tr>
				<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作目标完成情况</td>
			</tr>
			<tr>
				<td class="label">目标一</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="reached1" name="hrPromAssessment.reached1" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
				</td>
			</tr>
			<tr>
				<td class="label">目标二</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="reached2" name="hrPromAssessment.reached2" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
				</td>
			</tr>
			<tr>
				<td class="label">目标三</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="reached3" name="hrPromAssessment.reached3" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作业绩</td>
			</tr>
			<tr>
				<td class="label">绩效结果</td>
				<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
					<textarea readonly="readonly" id="performResult" name="hrPromAssessment.performResult" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.performResult}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作能力匹配</td>
			</tr>
			<tr>
				<td class="label">专业知识</td>
				<td class="label">沟通影响</td>
				<td class="label">问题解决能力</td>
				<td class="label">工作难度管理幅度</td>
				<td class="label">业务领域影响</td>
				<td class="label" style="border-right:1px solid #000000;">评级结果</td>
			</tr>
			<tr>
				<td class="field">${hrPromAssessment.proKnowledge}&nbsp;</td>
				<td class="field">${hrPromAssessment.commEffect}&nbsp;</td>
				<td class="field">${hrPromAssessment.solveAbility}&nbsp;</td>
				<td class="field">${hrPromAssessment.difficultyManage}&nbsp;</td>
				<td class="field">${hrPromAssessment.businessFieldEffect}&nbsp;</td>
				<td class="field" style="border-right:1px solid #000000;">${hrPromAssessment.ratingResult}&nbsp;</td>
			</tr>
			<tr>
				<td class="label">正式任命时间</td>
				<td class="field">
					<fmt:formatDate value="${hrPromAssessment.appointDate}" type="date"/>&nbsp;
				</td>
				<td class="label">岗位职级</td>
				<td class="field">${hrPromAssessment.postRank}&nbsp;</td>
				<td class="label">薪资等级</td>
				<td class="field" style="border-right:1px solid #000000;">${hrPromAssessment.salaryLevelName}&nbsp;</td>
			</tr>
			<tr>
				<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">晋升面谈</td>
			</tr>
			<tr>
				<td colspan="6" style="height:150px;padding:2px;border:1px solid #000000;">
					<textarea readonly="readonly" name="hrPromAssessment.promIntRecord" style="width:100%;*width:690px;height:100%;*height:140px;border:none;overflow-y:hidden;">${hrPromAssessment.promIntRecord}</textarea>
				</td>
			</tr>
		</table>
	</body>
</html>
