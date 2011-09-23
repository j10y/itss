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
		<title>员工试用期评估</title>
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
			<font style="font-size:22px;font-weight:bold;">员工试用（实习）评估表</font>
		</div>
		<form id="applyForm" onsubmit="return check();" action="${pageContext.request.contextPath}/hrm/saveHrPostApply.do" method="post">
			<input type="hidden" name="hrPostApply.id" value="${hrPostApply.id}"/>
			<input type="hidden" name="hrPostApply.publishStatus" value="0"/>
			<table style="width:700px;margin:0 auto;border:1px solid #000000;background:#ffffff" cellpadding="0" cellspacing="0">
				<tr>
					<td class="field">姓名</td>
					<td class="field">${hrPostAssessment.postApply.applyUser.fullname}</td>
					<td class="field">部门/门店</td>
					<td class="field">${hrPostAssessment.postApply.deptName}</td>
					<td class="field">职务</td>
					<td class="field" style="border-right:1px solid #000000;padding:2px;">${hrPostAssessment.postApply.postName}</td>
				</tr>
				<tr>
					<td class="field">实际报到日期</td>
					<td class="field">
						<fmt:formatDate value="${hrPostAssessment.actualReportDate}" type="date"/>
					</td>
					<td class="field">拟定转正日期</td>
					<td class="field">
						<fmt:formatDate value="${hrPostAssessment.applyPostDate}" type="date"/>
					</td>
					<td class="field">实际转正日期</td>
					<td class="field" style="border-right:1px solid #000000;">
						<fmt:formatDate value="${hrPostAssessment.actualPostDate}" type="date"/>
					</td>
				</tr>
				<tr>
					<td class="field">专业知识</td>
					<td class="field">沟通影响</td>
					<td class="field">问题解决能力</td>
					<td class="field">工作难度管理幅度</td>
					<td class="field">业务领域影响</td>
					<td class="field" style="border-right:1px solid #000000;padding:2px;">评级结果</td>
				</tr>
				<tr>
					<td class="field">${hrPostAssessment.proKnowledge}</td>
					<td class="field">${hrPostAssessment.commEffect}</td>
					<td class="field">${hrPostAssessment.solveAbility}</td>
					<td class="field">${hrPostAssessment.difficultyManage}</td>
					<td class="field">${hrPostAssessment.businessFieldEffect}</td>
					<td class="field" style="border-right:1px solid #000000;padding:2px;">${hrPostAssessment.ratingResult}</td>
				</tr>
				<tr>
					<td class="field">被评估者标准岗位名称</td>
					<td class="field" colspan="2">${hrPostAssessment.standardPostName}</td>
					<td class="field">被评估者岗位层级</td>
					<td class="field" colspan="2" style="border-right:1px solid #000000;padding:2px;">${hrPostAssessment.postRank}</td>
				</tr>
				<tr>
					<td class="field">转正前薪酬级别</td>
					<td class="field" colspan="2">${hrPostAssessment.oldSalaryLevelName}</td>
					<td class="field">转正后薪酬（由人力资源部填写）</td>
					<td class="field" colspan="2" style="border-right:1px solid #000000;padding:2px;text-align:left">
						月度工资为${hrPostAssessment.newFixedSalary + hrPostAssessment.newFloadSalary}元，其中<br/>
						固定部分为${hrPostAssessment.newFixedSalary}元，<br/>
						浮动部分为${hrPostAssessment.newFloadSalary}元，<br/>
						年终奖金基数为${hrPostAssessment.yearEndBonusCoefficient}元，<br/>
						年度总酬为${hrPostAssessment.totalYearSalary}元。
					</td>
				</tr>
				<tr>
					<td colspan="6" class="label" style="border-right:1px solid #000000;">员工试用（实习）期间工作表现评价</td>
				</tr>
				<tr>
					<td colspan="6" style="height:300px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea readonly="readonly" id="proSummary" name="hrPostApply.proSummary" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.proPerformance}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3" class="label">本部门/门店意见</td>
					<td colspan="3" class="label" style="border-right:1px solid #000000;">主管部门意见（无主管部门可不填写）</td>
				</tr>
				<tr>
					<td colspan="3" class="field" style="height:150px;padding:2px">
						<textarea readonly="readonly" id="proSummary" name="hrPostApply.proSummary" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.postOpinion}</textarea>
					</td>
					<td colspan="3" class="field" style="height:150px;padding:2px;border-right:1px solid #000000;">
						<textarea readonly="readonly" id="proSummary" name="hrPostApply.proSummary" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.deptOpinion}</textarea>
					</td>
				</tr>
				<tr>
					<td class="field" style="border-bottom:1px solid #000000;">人力资源部意见</td>
					<td class="field" colspan="2"  style="height:70px;padding:2px;border-bottom:1px solid #000000;">
						<textarea readonly="readonly" id="proSummary" name="hrPostApply.proSummary" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.hrOpinion}</textarea>
					</td>
					<td class="field" style="border-bottom:1px solid #000000;">总经理意见</td>
					<td class="field" colspan="2" style="height:70px;padding:2px;border-right:1px solid #000000;border-bottom:1px solid #000000;">
						<textarea readonly="readonly" id="proSummary" name="hrPostApply.proSummary" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.bossOpinion}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
