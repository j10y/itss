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
				var bandName = "${bandName}";
				var proKnowledgeCombo = new Ext.form.ComboBox({
					id : "proKnowledgeCombo",
					mode : "local",
					width:120,
					allowBlank : false,
					name : "hrPromAssessment.proKnowledge",
					renderTo : "proKnowledge",
					triggerAction : "all",
					editable : false,
					store : [
						[bandName + "-", bandName + "-"],
						[bandName, bandName],
						[bandName + "+", bandName + "+"]
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
					name : "hrPromAssessment.commEffect",
					renderTo : "commEffect",
					triggerAction : "all",
					editable : false,
					store : [
						[bandName + "-", bandName + "-"],
						[bandName, bandName],
						[bandName + "+", bandName + "+"]
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
					name : "hrPromAssessment.solveAbility",
					renderTo : "solveAbility",
					triggerAction : "all",
					editable : false,
					store : [
						[bandName + "-", bandName + "-"],
						[bandName, bandName],
						[bandName + "+", bandName + "+"]
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
					name : "hrPromAssessment.difficultyManage",
					renderTo : "difficultyManage",
					triggerAction : "all",
					editable : false,
					store : [
						[bandName + "-", bandName + "-"],
						[bandName, bandName],
						[bandName + "+", bandName + "+"]
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
					name : "hrPromAssessment.businessFieldEffect",
					renderTo : "businessFieldEffect",
					triggerAction : "all",
					editable : false,
					store : [
						[bandName + "-", bandName + "-"],
						[bandName, bandName],
						[bandName + "+", bandName + "+"]
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
					name : "hrPromAssessment.ratingResult",
					renderTo : "ratingResult",
					triggerAction : "all",
					editable : false,
					store : [
						[bandName + "-", bandName + "-"],
						[bandName, bandName],
						[bandName + "+", bandName + "+"]
					],
					listeners : {
						select : function(l, h, k) {
							
						}
					}
				});
				var postRankCombo = new Ext.form.ComboBox({
					mode : "local",
					disable : true,
					//allowBlank : false,
					width:120,
					name : "hrPromAssessment.postRank",
					renderTo : "postRank",
					triggerAction : "all",
					readOnly : true,
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
					mode : "local",
					width:120,
					renderTo : "salaryLevelName",
					disable : true,
					//allowBlank : false,
					//editable : false,
					valueField : "standardName",
					displayField : "standardName",
					triggerAction : "all",
					readOnly : true,
					store : new Ext.data.JsonStore({
						remoteSort : true,
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
							"status"
						]
					}),
					listeners : {
						focus : function() {
						return;
							Ext.Ajax.request({
								url : "${pageContext.request.contextPath}/hrm/comboSalaryJobSalaryRelation.do",
								method : "post",
								params : {
									'Q_job.jobId_L_EQ' : document.getElementById("nowPositionId").value,
									'Q_deleteFlag_N_EQ' : 0
								},
								success : function(f) {
									var e = Ext.util.JSON.decode(f.responseText);
									Ext.getCmp("salaryLevelNameCombo").getStore().loadData(e);
								}
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
					width:120,
					disable : true,
					//editable : false,
					readOnly : true,
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
					appointDate.setRawValue("${hrPromAssessment.appointDate}".split(" ")[0]);
					salaryLevelNameCombo.setValue("${hrPromAssessment.salaryLevelName}");
					salaryLevelNameCombo.setRawValue("${hrPromAssessment.salaryLevelName}");
					document.getElementById("salaryLevelId").value="${hrPromAssessment.salaryLevelId}";
				}
			});
			function check() {
				if(document.getElementById("reached1").value == "") {
					alert("请填写目标一的完成情况！");
					return false;
				}
				if(document.getElementById("reached2").value == "") {
					alert("请填写目标二的完成情况！");
					return false;
				}
				if(document.getElementById("reached3").value == "") {
					alert("请填写目标三的完成情况！");
					return false;
				}
				if(document.getElementById("performResult").value == "") {
					alert("请填写工作业绩信息！");
					return false;
				}
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
				document.getElementById('submitButton').disabled='disabled';
				return true;
			}
		</script>
	</head>

	<body>
		<div style="text-align:center;height:50px;line-height:50px;">
			<font style="font-size:18px;">晋升评估表</font>
		</div>
		<form onsubmit="return check();" action="${pageContext.request.contextPath}/hrm/saveHrPromAssessment.do" method="post">
			<input type="hidden" name="hrPromAssessment.id" value="${hrPromAssessment.id}"/>
			<input type="hidden" name="hrPromAssessment.promApply.id" value="${hrPromAssessment.promApply.id}"/>
			<input type="hidden" name="hrPromAssessment.createPerson.userId" value="${hrPromAssessment.createPerson.userId}"/>
			<input type="hidden" name="hrPromAssessment.createDate" value="${hrPromAssessment.createDate}"/>
			<input type="hidden" name="hrPromAssessment.publishStatus" value="${hrPromAssessment.publishStatus}"/>
			<table style="width:720px;margin:0 auto;border:1px solid #000000;background:#ffffff" cellpadding="0" cellspacing="0">
				<tr>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">姓名</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;" colspan="2">${hrPromAssessment.promApply.applyUser.fullname}&nbsp;</td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">部门/门店</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;" colspan="2">${hrPromAssessment.promApply.depName}&nbsp;</td>
				</tr>
				<tr>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">入职日期</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;" colspan="2">
						<fmt:formatDate value="${hrPromAssessment.promApply.applyUser.accessionTime}" type="date"/>&nbsp;
					</td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">现职位</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;" colspan="2">
						<input type="hidden" id="nowPositionId" value="${hrPromAssessment.promApply.nowPositionId}"/>
						${hrPromAssessment.promApply.nowPositionName}&nbsp;
					</td>
				</tr>
				<tr>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟担任职位</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;" colspan="2">${hrPromAssessment.promApply.applyPositionName}&nbsp;</td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟晋升时间</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;" colspan="2">
						<fmt:formatDate value="${hrPromAssessment.promApply.applyDate}" type="date"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">工作年限</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;" colspan="2" colspan="2">${hrPromAssessment.promApply.workYear}&nbsp;</td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">本单位工作年限</td>
					<td style="width:240px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;" colspan="2">${hrPromAssessment.promApply.workHereYear}&nbsp;</td>
				</tr>
				<tr>
					<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作目标完成情况</td>
				</tr>
				<tr>
					<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标一<font style="color:red;">*</font></td>
					<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea id="reached1" name="hrPromAssessment.reached1" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
					</td>
				</tr>
				<tr>
					<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标二<font style="color:red;">*</font></td>
					<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea id="reached2" name="hrPromAssessment.reached2" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
					</td>
				</tr>
				<tr>
					<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标三<font style="color:red;">*</font></td>
					<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea id="reached3" name="hrPromAssessment.reached3" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.reached1}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作业绩</td>
				</tr>
				<tr>
					<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">绩效结果<font style="color:red;">*</font></td>
					<td colspan="5" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea id="performResult" name="hrPromAssessment.performResult" style="width:100%;*width:590px;height:100%;*height:50px;border:none;overflow-y:hidden;">${hrPromAssessment.performResult}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作能力匹配</td>
				</tr>
				<tr>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">专业知识<font style="color:red;">*</font></td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">沟通影响<font style="color:red;">*</font></td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">问题解决能力<font style="color:red;">*</font></td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">工作难度管理幅度<font style="color:red;">*</font></td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">业务领域影响<font style="color:red;">*</font></td>
					<td style="width:120px;height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">评级结果<font style="color:red;">*</font></td>
				</tr>
				<tr>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;"><div id="proKnowledge"></div></td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;"><div id="commEffect"></div></td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;"><div id="solveAbility"></div></td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;"><div id="difficultyManage"></div></td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;"><div id="businessFieldEffect"></div></td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;"><div id="ratingResult"></div></td>
				</tr>
				<tr>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">正式任命时间</td>
					<td style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">
						<div id="appointDate"></div>
					</td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">岗位职级</td>
					<td style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;"><div id="postRank"></div></td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">薪资等级</td>
					<td style="text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
						<input type="hidden" id="salaryLevelId" name="hrPromAssessment.salaryLevelId" value="${hrPromAssessment.salaryLevelId}"/>
						<div id="salaryLevelName"></div>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">晋升面谈</td>
				</tr>
				<tr>
					<td colspan="6" style="height:150px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea disabled="disabled" name="hrPromAssessment.promIntRecord" style="width:100%;*width:690px;height:100%;*height:140px;border:none;overflow-y:hidden;">${hrPromAssessment.promIntRecord}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="6" style="height:40px;border:1px solid #000000;">
						<!-- 
						<input type="reset" value="取消"/>&nbsp;&nbsp;
						 -->
						<input id="submitButton" type="submit" value="保存"/>&nbsp;&nbsp;
						<!-- 
						<input type="button" value="提交审核"/>
						 -->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
