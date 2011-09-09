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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>		
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Toast.js"></script>
		<script type="text/javascript">
			Ext.onReady(function() {
				var proKnowledgeCombo = new Ext.form.ComboBox({
					mode : "local",
					width:100,
					allowBlank : false,
					name : "hrPromAssessment.proKnowledge",
					renderTo : "proKnowledge",
					triggerAction : "all",
					store : [
						["1-", "1-"],
						["1", "1"],
						["1+", "1+"],
						["2-", "2-"],
						["2", "2"],
						["2+", "2+"],
						["3-", "3-"],
						["3", "3"],
						["3+", "3+"],
						["4-", "4-"],
						["4", "4"],
						["4+", "4+"],
						["5-", "5-"],
						["5", "5"],
						["5+", "5+"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var commEffectCombo = new Ext.form.ComboBox({
					mode : "local",
					width:100,
					allowBlank : false,
					name : "hrPromAssessment.commEffect",
					renderTo : "commEffect",
					triggerAction : "all",
					store : [
						["1-", "1-"],
						["1", "1"],
						["1+", "1+"],
						["2-", "2-"],
						["2", "2"],
						["2+", "2+"],
						["3-", "3-"],
						["3", "3"],
						["3+", "3+"],
						["4-", "4-"],
						["4", "4"],
						["4+", "4+"],
						["5-", "5-"],
						["5", "5"],
						["5+", "5+"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var solveAbilityCombo = new Ext.form.ComboBox({
					mode : "local",
					width:100,
					allowBlank : false,
					name : "hrPromAssessment.solveAbility",
					renderTo : "solveAbility",
					triggerAction : "all",
					store : [
						["1-", "1-"],
						["1", "1"],
						["1+", "1+"],
						["2-", "2-"],
						["2", "2"],
						["2+", "2+"],
						["3-", "3-"],
						["3", "3"],
						["3+", "3+"],
						["4-", "4-"],
						["4", "4"],
						["4+", "4+"],
						["5-", "5-"],
						["5", "5"],
						["5+", "5+"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var difficultyManageCombo = new Ext.form.ComboBox({
					mode : "local",
					allowBlank : false,
					name : "hrPromAssessment.difficultyManage",
					renderTo : "difficultyManage",
					triggerAction : "all",
					store : [
						["1-", "1-"],
						["1", "1"],
						["1+", "1+"],
						["2-", "2-"],
						["2", "2"],
						["2+", "2+"],
						["3-", "3-"],
						["3", "3"],
						["3+", "3+"],
						["4-", "4-"],
						["4", "4"],
						["4+", "4+"],
						["5-", "5-"],
						["5", "5"],
						["5+", "5+"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var businessFieldEffectCombo = new Ext.form.ComboBox({
					mode : "local",
					width:100,
					allowBlank : false,
					name : "hrPromAssessment.businessFieldEffect",
					renderTo : "businessFieldEffect",
					triggerAction : "all",
					store : [
						["1-", "1-"],
						["1", "1"],
						["1+", "1+"],
						["2-", "2-"],
						["2", "2"],
						["2+", "2+"],
						["3-", "3-"],
						["3", "3"],
						["3+", "3+"],
						["4-", "4-"],
						["4", "4"],
						["4+", "4+"],
						["5-", "5-"],
						["5", "5"],
						["5+", "5+"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var ratingResultCombo = new Ext.form.ComboBox({
					mode : "local",
					allowBlank : false,
					name : "hrPromAssessment.ratingResult",
					renderTo : "ratingResult",
					triggerAction : "all",
					store : [
						["1-", "1-"],
						["1", "1"],
						["1+", "1+"],
						["2-", "2-"],
						["2", "2"],
						["2+", "2+"],
						["3-", "3-"],
						["3", "3"],
						["3+", "3+"],
						["4-", "4-"],
						["4", "4"],
						["4+", "4+"],
						["5-", "5-"],
						["5", "5"],
						["5+", "5+"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var postRankCombo = new Ext.form.ComboBox({
					mode : "local",
					allowBlank : false,
					name : "hrPromAssessment.postRank",
					renderTo : "postRank",
					triggerAction : "all",
					store : [
						["1级","1级"],
						["2级","2级"],
						["3级","3级"],
						["4级","4级"],
						["5级","5级"],
						["6级","6级"],
						["7级","7级"],
						["8级","8级"],
						["9级","9级"],
						["10级","10级"],
						["11级","11级"],
						["12级","12级"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var salaryLevelNameCombo = new Ext.form.ComboBox({
					fieldLabel : "薪酬标准单名称",
					id : "salaryLevelNameCombo",
					name : "hrPromAssessment.salaryLevelName",
					mode : "remote",
					renderTo : "salaryLevelName",
					allowBlank : false,
					editable : false,
					valueField : "standardName",
					displayField : "standardName",
					triggerAction : "all",
					store : new Ext.data.JsonStore(
							{
								url : "${pageContext.request.contextPath}/hrm/comboSalaryJobSalaryRelation.do",
								fields : [
										{
											name : "standardId",
											type : "int"
										},
										"standardNo",
										"standardName",
										"totalMoney",
										"setdownTime",
										"perCoefficient",
										"status" ]
							}),
					listeners : {
						focus : function() {
							Ext.getCmp("salaryLevelNameCombo").getStore().reload({
								'Q_job.jobId_L_EQ' : document.getElementById("nowPositionId"),
								'Q_deleteFlag_N_EQ' : 0
							});
						},
						select : function(e, c, d) {
							document.getElementById("salaryLevelId").value = c.data.standardId;
						}
					}
				});
				var appointDate = new Ext.form.DateField({
					name : "hrPromApply.appointDate",
					format : "Y-m-d",
					editable : false,
					renderTo : "appointDate"
				});
				if("${hrPromAssessment.id}" != 0) {
					proKnowledgeCombo.setValue("${hrPromAssessment.proKnowledge}");
					commEffectCombo.setValue("${hrPromAssessment.commEffect}");
					solveAbilityCombo.setValue("${hrPromAssessment.solveAbility}");
					difficultyManageCombo.setValue("${hrPromAssessment.difficultyManage}");
					businessFieldEffectCombo.setValue("${hrPromAssessment.businessFieldEffect}");
					ratingResultCombo.setValue("${hrPromAssessment.ratingResult}");
					postRankCombo.setValue("${hrPromAssessment.postRank}");
					appointDate.setRawValue("${hrPromAssessment.appointDate}");
					salaryLevelNameCombo.setValue("${hrPromAssessment.salaryLevelName}");
					salaryLevelNameCombo.setRawValue("${hrPromAssessment.salaryLevelName}");
					document.getElementById("salaryLevelId").value="${hrPromAssessment.salaryLevelId}";
				}
			});
		</script>
	</head>

	<body>
		<div style="text-align:center;height:50px;line-height:50px;">
			<font style="font-size:18px;">晋升评估表</font>
		</div>
		<form action="${pageContext.request.contextPath}/hrm/saveHrPromAssessment.do" method="post">
			<input type="hidden" name="hrPromAssessment.id" value="${hrPromApply.id}"/>
			<table width="700" align="center" border="1" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">姓名</td>
					<td colspan="2">${hrPromAssessment.promApply.applyUser.fullname}</td>
					<td align="right">部门/门店</td>
					<td colspan="2">${hrPromAssessment.promApply.depName}</div></td>
				</tr>
				<tr>
					<td align="right">入职日期</td>
					<td colspan="2">${hrPromAssessment.promApply.applyUser.accessionTime}</div></td>
					<td align="right">现职位</td>
					<td colspan="2">
						<input type="hidden" id="nowPositionId" readonly="readonly" value="${hrPromAssessment.promApply.nowPositionId}"/>
						${hrPromAssessment.promApply.nowPositionName}
					</td>
				</tr>
				<tr>
					<td align="right">拟担任职位</td>
					<td colspan="2">${hrPromAssessment.promApply.applyPositionName}</td>
					<td align="right">拟晋升时间</td>
					<td colspan="2">${hrPromAssessment.promApply.applyDate}</td>
				</tr>
				<tr>
					<td align="right">工作年限</td>
					<td colspan="2">${hrPromAssessment.promApply.workYear}</td>
					<td align="right">本单位工作年限</td>
					<td colspan="2">${hrPromAssessment.promApply.workHereYear}</td>
				</tr>
				<tr>
					<td colspan="6" align="center">工作目标完成情况</td>
				</tr>
				<tr>
					<td align="right">目标一</td>
					<td colspan="5" style="height:60px;padding:1px;">
						<textarea name="hrPromAssessment.reached1" readonly="readonly" style="width:100%;height:100%;border:none;">${hrPromAssessment.reached1}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right">目标二</td>
					<td colspan="5" style="height:60px;padding:1px;">
						<textarea name="hrPromAssessment.reached2" readonly="readonly" style="width:100%;height:100%;border:none;">${hrPromAssessment.reached1}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right">目标三</td>
					<td colspan="5" style="height:60px;padding:1px;">
						<textarea name="hrPromAssessment.reached3" readonly="readonly" style="width:100%;height:100%;border:none;">${hrPromAssessment.reached1}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center">工作业绩</td>
				</tr>
				<tr>
					<td align="right">绩效结果</td>
					<td colspan="5" style="height:60px;padding:1px;">
						<textarea name="hrPromAssessment.performResult" readonly="readonly" style="width:100%;height:100%;border:none;">${hrPromAssessment.performResult}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center">工作能力匹配</td>
				</tr>
				<tr>
					<td align="center">专业知识</td>
					<td align="center">沟通影响</td>
					<td align="center">问题解决能力</td>
					<td align="center">工作难度管理幅度</td>
					<td align="center">业务领域影响</td>
					<td align="center">评级结果</td>
				</tr>
				<tr>
					<td>
						<input value="${hrPromAssessment.proKnowledge}" readonly="readonly" style="width:100px;border:none;"/>
					</td>
					<td>
						<input value="${hrPromAssessment.commEffect}" readonly="readonly" style="width:100px;border:none;"/>
					</td>
					<td>
						<input value="${hrPromAssessment.solveAbility}" readonly="readonly" style="width:100px;border:none;"/>
					</td>
					<td>
						<input value="${hrPromAssessment.difficultyManage}" readonly="readonly" style="border:none;"/>
					</td>
					<td>
						<input value="${hrPromAssessment.businessFieldEffect}" readonly="readonly" style="width:100px;border:none;"/>
					</td>
					<td>
						<input value="${hrPromAssessment.ratingResult}" readonly="readonly" style="width:100px;border:none;"/>
					</td>
				</tr>
				<tr>
					<td align="right">正式任命时间</td>
					<td>
						<div id="appointDate"></div>
					</td>
					<td align="right">岗位职级</td>
					<td><div id="postRank"></div></td>
					<td align="right">薪资等级</td>
					<td>
						<input type="hidden" id="salaryLevelId" readonly="readonly" name="hrPromAssessment.salaryLevelId" value="${hrPromAssessment.salaryLevelId}"/>
						<div id="salaryLevelName"></div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center">晋升面谈</td>
				</tr>
				<tr>
					<td colspan="6" style="height:150px;padding:1px;">
						<textarea name="hrPromAssessment.promIntRecord" readonly="readonly" style="width:100%;height:100%;border:none;">${hrPromAssessment.promIntRecord}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="6">
						&nbsp;&nbsp;&nbsp;
						<!-- 
						<input type="reset" value="取消"/>&nbsp;&nbsp;
						<input type="submit" value="保存"/>&nbsp;&nbsp;
						<input type="button" value="提交审核"/>
						 -->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
