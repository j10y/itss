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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>		
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Toast.js"></script>
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
			.field2 {
				width:120px;
				text-align:center;
				border-left:1px solid #000000;
				border-top:1px solid #000000;
			}
		</style>
		<script type="text/javascript">
			Ext.onReady(function() {
				var proKnowledgeCombo = new Ext.form.ComboBox({
					id : "proKnowledgeCombo",
					mode : "local",
					width:120,
					allowBlank : false,
					name : "hrPostAssessment.proKnowledge",
					renderTo : "proKnowledge",
					editable : false,
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
					id : "commEffectCombo",
					mode : "local",
					width:120,
					allowBlank : false,
					name : "hrPostAssessment.commEffect",
					renderTo : "commEffect",
					editable : false,
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
					id : "solveAbilityCombo",
					mode : "local",
					width:120,
					allowBlank : false,
					name : "hrPostAssessment.solveAbility",
					renderTo : "solveAbility",
					editable : false,
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
					id : "difficultyManageCombo",
					mode : "local",
					width:120,
					allowBlank : false,
					name : "hrPostAssessment.difficultyManage",
					renderTo : "difficultyManage",
					editable : false,
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
					id : "businessFieldEffectCombo",
					mode : "local",
					width:120,
					allowBlank : false,
					name : "hrPostAssessment.businessFieldEffect",
					renderTo : "businessFieldEffect",
					editable : false,
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
					id : "ratingResultCombo",
					mode : "local",
					width:120,
					allowBlank : false,
					name : "hrPostAssessment.ratingResult",
					renderTo : "ratingResult",
					editable : false,
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
				if("${hrPostAssessment.id}" != 0) {
					proKnowledgeCombo.setValue("${hrPostAssessment.proKnowledge}");
					commEffectCombo.setValue("${hrPostAssessment.commEffect}");
					solveAbilityCombo.setValue("${hrPostAssessment.solveAbility}");
					difficultyManageCombo.setValue("${hrPostAssessment.difficultyManage}");
					businessFieldEffectCombo.setValue("${hrPostAssessment.businessFieldEffect}");
					ratingResultCombo.setValue("${hrPostAssessment.ratingResult}");
				}
			});
			function check() {
				if(!Ext.getCmp("proKnowledgeCombo").isValid()) {
					return false;
				}
				if(!Ext.getCmp("commEffectCombo").isValid()) {
					return false;
				}
				if(!Ext.getCmp("solveAbilityCombo").isValid()) {
					return false;
				}
				if(!Ext.getCmp("difficultyManageCombo").isValid()) {
					return false;
				}
				if(!Ext.getCmp("businessFieldEffectCombo").isValid()) {
					return false;
				}
				if(!Ext.getCmp("ratingResultCombo").isValid()) {
					return false;
				}
				if(document.getElementById("proPerformance").value == "") {
					alert("请正确填写员工试用（实习）期间工作表现评价！");
					return false;
				}
				return true;
			}
		</script>
	</head>

	<body>
		<div style="text-align:center;height:30px;line-height:30px;">
			<font style="font-size:22px;font-weight:bold;">北京市上品商业发展有限责任公司</font><br/>
		</div>
		<div style="text-align:center;height:30px;line-height:30px;">
			<font style="font-size:22px;font-weight:bold;">员工试用（实习）评估表</font>
		</div>
		<form id="applyForm" onsubmit="return check();" action="${pageContext.request.contextPath}/hrm/saveHrPostAssessment.do" method="post">
			<input type="hidden" name="hrPostAssessment.id" value="${hrPostAssessment.id}"/>
			<table style="width:700px;margin:0 auto;border:1px solid #000000;background:#ffffff" cellpadding="0" cellspacing="0">
				<tr>
					<td class="field">姓名</td>
					<td class="field">${hrPostAssessment.postApply.applyUser.fullname}</td>
					<td class="field">部门/门店</td>
					<td class="field">${hrPostAssessment.postApply.gender}</td>
					<td class="field">职务</td>
					<td class="field" style="border-right:1px solid #000000;padding:2px;">${hrPostAssessment.postApply.postName}</td>
				</tr>
				<tr>
					<td class="field">实际报到日期</td>
					<td class="field"><fmt:formatDate value="${hrPostAssessment.actualReportDate}" type="date"/></td>
					<td class="field">拟定转正日期</td>
					<td class="field"><fmt:formatDate value="${hrPostAssessment.applyPostDate}" type="date"/></td>
					<td class="field">实际转正日期</td>
					<td class="field" style="border-right:1px solid #000000;"><fmt:formatDate value="${hrPostAssessment.actualPostDate}" type="date"/></td>
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
					<td class="field2"><div id="proKnowledge"></div></td>
					<td class="field2"><div id="commEffect"></div></td>
					<td class="field2"><div id="solveAbility"></div></td>
					<td class="field2"><div id="difficultyManage"></div></td>
					<td class="field2"><div id="businessFieldEffect"></div></td>
					<td class="field2" style="border-right:1px solid #000000;"><div id="ratingResult"></div></td>
				</tr>
				<tr>
					<td class="field">被评估者标准岗位名称</td>
					<td class="field" colspan="2">${hrPostAssessment.standardPostName}</td>
					<td class="field">被评估者岗位层级</td>
					<td class="field" colspan="2" style="border-right:1px solid #000000;padding:2px;">
						Band&nbsp;<font style="text-decoration:underline">${hrPostAssessment.postBand}</font>&nbsp;&nbsp;
						档&nbsp;<font style="text-decoration:underline">${hrPostAssessment.postGrade}</font>
					</td>
				</tr>
				<tr>
					<td class="field">转正前薪酬级别</td>
					<td class="field" colspan="2">
						<font style="text-decoration:underline">${hrPostAssessment.oldSalaryLevelName}</font>，
						<font style="text-decoration:underline">${hrPostAssessment.oldSalary}</font>元/月
					</td>
					<td class="field">转正后薪酬（由人力资源部填写）</td>
					<td class="field" colspan="2" style="border-right:1px solid #000000;padding:2px;text-align:left">
						月度工资为<font style="text-decoration:underline">${hrPostAssessment.newFixedSalary + hrPostAssessment.newFloatSalary}</font>元，其中<br/>
						固定部分为<font style="text-decoration:underline">${hrPostAssessment.newFixedSalary}</font>元，<br/>
						浮动部分为<font style="text-decoration:underline">${hrPostAssessment.newFloatSalary}</font>元，<br/>
						年终奖金基数为<font style="text-decoration:underline">${hrPostAssessment.yearEndBonusCoefficient}</font>元，<br/>
						年度总酬为<font style="text-decoration:underline">${hrPostAssessment.totalYearSalary}</font>元。
					</td>
				</tr>
				<tr>
					<td colspan="6" class="label" style="border-right:1px solid #000000;">员工试用（实习）期间工作表现评价</td>
				</tr>
				<tr>
					<td colspan="6" style="height:300px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea id="proPerformance" name="hrPostAssessment.proPerformance" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.proPerformance}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3" class="label">本部门/门店意见</td>
					<td colspan="3" class="label" style="border-right:1px solid #000000;">主管部门意见（无主管部门可不填写）</td>
				</tr>
				<tr>
					<td colspan="3" class="field" style="height:150px;padding:2px">
						<textarea readonly="readonly" id="postOpinion" name="hrPostAssessment.postOpinion" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.postOpinion}</textarea>
					</td>
					<td colspan="3" class="field" style="height:150px;padding:2px;border-right:1px solid #000000;">
						<textarea readonly="readonly" id="deptOpinion" name="hrPostAssessment.deptOpinion" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.deptOpinion}</textarea>
					</td>
				</tr>
				<tr>
					<td class="field">人力资源部意见</td>
					<td class="field" colspan="2"  style="height:70px;padding:2px">
						<textarea readonly="readonly" id="hrOpinion" name="hrPostAssessment.hrOpinion" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.hrOpinion}</textarea>
					</td>
					<td class="field">总经理意见</td>
					<td class="field" colspan="2" style="height:70px;padding:2px;border-right:1px solid #000000;padding:2px;">
						<textarea readonly="readonly" id="bossOpinion" name="hrPostAssessment.bossOpinion" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostAssessment.bossOpinion}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="6" style="height:40px;border:1px solid #000000;">
						<!-- <input type="reset" value="取消"/>&nbsp;&nbsp; -->
						<input type="submit" value="保存"/>&nbsp;&nbsp;
						<!-- <input type="button" value="提交审核" onclick="onSend();"/> -->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
