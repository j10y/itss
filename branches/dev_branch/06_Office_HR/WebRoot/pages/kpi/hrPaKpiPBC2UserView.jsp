<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String basePath=request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
		<title>个人PBC结果</title>
		<style type="text/css">
			#pbcTable tr td{
				border-left:1px solid #000000;
				border-top:1px solid #000000;
				height:30px;
				line-height:30px;
				padding:0 5px 0 5px;
				font-size:14px;
				text-align:center;
				
				border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;
			}
			.boldLabel{
				font-weight:bold;
			}
		</style>
	</head>

	<body>
		<div style="text-align:center;height:30px;line-height:30px;font-size:20px;">
			个人PBC结果表
		</div>
		<div style="padding:5px 5px 5px 62px;">
			<table id="pbcTable" style="width:850px;margin:0 auto;border:1px solid #000000;background:#ffffff" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="9" style="background-color:#CCFFCC;font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">个人PBC结果表</td>
				</tr>
				<tr>
					<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;" colspan="2">姓名</td>
					<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${fullname}</td>
					<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">岗位名称</td>
					<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${position}</td>
					<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">岗位层级</td>
					<td colspan="3" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">${bandGrade}</td>
				</tr>
				<tr>
					<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;" colspan="2">所属部门</td>
					<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${depName}</td>
					<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">直接上级</td>
					<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${lineManager}</td>
					<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">填表日期</td>
					<td colspan="3" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">
						<fmt:formatDate type="date" value="${createDate}"/>
					</td>
				</tr>
				<c:if test="${fn:length(busGoalList) > 0}">
					<tr>
						<td colspan="9" style="background-color:#CCFFCC;font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">业务目标 (Business Goal)</td>
					</tr>
					<tr>
						<td colspan="9" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;border-right:1px solid #000000;font-size:12px;text-align:left;">本人的业务目标必须链接公司的战略发展思路以及本部门的目标，同时保证与公司所倡导的核心价值观保持一致，占年度PBC的比重为60%。</td>
					</tr>
					<tr>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">编号</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">考核指标</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">指标定义</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;" colspan="2">评分标准</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">实际完成情况</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">得分</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">权重</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">加权分数</td>
					</tr>
					<c:forEach items="${busGoalList}" var="busGoal" varStatus="status">
						<tr>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${status.index + 1}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${busGoal.paName}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;text-align:center;text-align:left;line-height:18px;">${busGoal.paDesc}</td>
							<td colspan="2" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">
								<c:forEach items="${busGoal.busPisMap}" var="entry">
									${entry.key}：${entry.value}<br/>
								</c:forEach>
							</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">${busGoal.remark}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${busGoal.result}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${busGoal.weight}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">
								<fmt:formatNumber value="${busGoal.result * busGoal.weight}" pattern="#.#" type="number"/>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:right;">业务目标加权成绩</td>
						<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">
							<fmt:formatNumber value="${busResult}" pattern="#.#" type="number"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${fn:length(pmGoalList) > 0}">
					<tr>
						<td colspan="9" style="background-color:#CCFFCC;font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">员工管理目标 (People Management Goal)</td>
					</tr>
					<tr>
						<td colspan="9"  style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;border-right:1px solid #000000;font-size:12px;text-align:left;">设定4个支持组织绩效提高的目标，应包括员工管理、下属发展和团队建设等内容，占年度PBC的比重为30%</td>
					</tr>
					<tr>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">编号</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">目标</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">关键衡量指标</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;" colspan="2">评分标准</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">直属领导评价</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">得分</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">权重</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">加权分数</td>
					</tr>
					<c:forEach items="${pmGoalList}" var="pmGoal" varStatus="status">
						<tr>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${status.index + 1}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:20px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${pmGoal.paName}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">${pmGoal.paDesc}</td>
							<td colspan="2" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">
								<c:forEach items="${pmGoal.pmPisMap}" var="entry">
									${entry.key}：${entry.value}<br/>
								</c:forEach>
							</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">${pmGoal.remark}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${pmGoal.result}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${pmGoal.weight}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">
								<fmt:formatNumber value="${pmGoal.result * pmGoal.weight}" pattern="#.#" type="number"/>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8" style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:right;">员工管理目标加权成绩</td>
						<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">
							<fmt:formatNumber value="${pmResult}" pattern="#.#" type="number"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${fn:length(idGoalList) > 0}">
					<tr>
						<td colspan="9" style="background-color:#CCFFCC;font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">个人发展目标 (Individual Development Goal)</td>
					</tr>
					<tr>
						<td colspan="9" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;border-right:1px solid #000000;font-size:12px;text-align:left;">根据人岗匹配考量的5个纬度，设定1~2个支持业务目标的需要提高的个人能力，占据年度PBC的10%</td>
					</tr>
					<tr>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">编号</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;" colspan="2">个人发展目标</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;" colspan="2">评分标准</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">直线经理评价</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">得分</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">权重</td>
						<td style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">加权分数</td>
					</tr>
					<c:forEach items="${idGoalList}" var="idGoal" varStatus="status">
						<tr>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${status.index + 1}</td>
							<td colspan="2" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">${idGoal.paName}：<br/>${idGoal.paDesc}</td>
							<td colspan="2" style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">
								<c:forEach items="${idGoal.idPisMap}" var="entry">
									${entry.key}：${entry.value}<br/>
								</c:forEach>
							</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;padding:0 5px 0 5px;font-size:14px;text-align:left;line-height:18px;">${idGoal.remark}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${idGoal.result}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;">${idGoal.weight}</td>
							<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">
								<fmt:formatNumber value="${idGoal.result * idGoal.weight}" pattern="#.#" type="number"/>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8"  style="font-weight:bold;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:right;">个人发展目标加权成绩</td>
						<td style="border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;">
							<fmt:formatNumber value="${idResult}" pattern="#.#" type="number"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="9" style="background-color:#CCFFCC;border-left:1px solid #000000;border-top:1px solid #000000;height:30px;line-height:30px;padding:0 5px 0 5px;font-size:14px;text-align:center;border-right:1px solid #000000;border-bottom:1px solid #000000;">&nbsp;</td>
				</tr>
			</table>
		</div>
	</body>
</html>
