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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ux/Toast.js"></script>
		<script type="text/javascript">
			var __ctxPath = "${pageContext.request.contextPath}";
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/selector/UserSelector.js"></script>		
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
		<script type="text/javascript">
			function check() {
				if(document.getElementById("age").value == "" || isNaN(document.getElementById("age").value)) {
					alert("请正确填写年龄！");
					return false;
				}
				if(document.getElementById("proSummary").value == "") {
					alert("请正确填写员工试用期（实习）总结！");
					return false;
				}
				return true;
			}
			function onSend(){
				var result = check();
				if(!result){
					return;
				}
				//选人逻辑，至少选择一个才能执行下面的逻辑
				var signUserIds="";
				UserSelector.getView(function(id, name) {
					if(id == "") {
						Ext.MessageBox.show({
							title : "操作信息",
							msg : "请选择审批人",
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
						return ;
					}
					Ext.Ajax.request({
						url : "${pageContext.request.contextPath}/hrm/saveHrPostApply.do",
						params : {
								"hrPostApply.id" : "${hrPostApply.id}",
								"hrPostApply.age" : document.getElementById("age").value,
								"hrPostApply.proSummary" : document.getElementById("proSummary").value,
								"hrPostApply.publishStatus" : 5, //提交申请
								"isSubmit" : "true" //提交申请
						},
						method : "post",
						success : function(h, j) {
							var data = Ext.decode(h.responseText);
							Ext.Ajax.request({							
								url : "${pageContext.request.contextPath}/flow/saveProcessActivity.do",
								waitMsg : "正在提交流程表单信息...",
								scope : this,
								params : {
									defId : 18,
									startFlow : true,
									"hrPostApply.id" : data.applyId,
									"hrPostApply.deptId" : data.deptId,
									"hrPostApply.deptName" : data.deptName,
									"hrPostApply.postId" : data.postId,
									"hrPostApply.postName" : data.postName,
									"hrPostApply.accessionTime" : data.accessionTime,
									"hrPostApply.proSummary" : data.proSummary,
									"hrPostApply.publishStatus" : 5, //待直线经理审核
									"isSubmit" : "true", //提交申请
									flowAssignId : id
									//flowAssignId：可以选择多人，只要有一个人审批通过即可通过
									//signUserIds:可以选择多人，必须所有人审批通过才能通过
								},
								success : function(
										i,
										j) {
									Ext.ux.Toast.msg("操作信息", "成功保存信息！");
									window.location = "${pageContext.request.contextPath}/pages/hrm/applyResult.jsp?flag=2";
									return;
									Ext.getCmp("HrPostApplyForm").close();
									var k = Ext.getCmp("ProcessRunGrid");
									if (k != null) {
										k.getStore().reload();
									}
								}
																			
							});
						}
					});
				}, true).show();
			}
		</script>
	</head>

	<body>
		<div style="text-align:center;height:30px;line-height:30px;">
			<font style="font-size:22px;font-weight:bold;">北京市上品商业发展有限责任公司</font><br/>
		</div>
		<div style="text-align:center;height:30px;line-height:30px;">
			<font style="font-size:22px;font-weight:bold;">员工转正申请表</font>
		</div>
		<form id="applyForm" onsubmit="return check();" action="${pageContext.request.contextPath}/hrm/saveHrPostApply.do" method="post">
			<input type="hidden" name="hrPostApply.id" value="${hrPostApply.id}"/>
			<input type="hidden" id="publishStatus" name="hrPostApply.publishStatus" value="0"/>
			<table style="width:700px;margin:0 auto;border:1px solid #000000;background:#ffffff" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="6" class="label" style="border-right:1px solid #000000;">员工基本信息表</td>
				</tr>
				<tr>
					<td class="field">姓名</td>
					<td class="field">${hrPostApply.applyUser.fullname}</td>
					<td class="field">性别</td>
					<td class="field">${hrPostApply.gender}</td>
					<td class="field">年龄</td>
					<td class="field" style="border-right:1px solid #000000;padding:2px;">
						<input type="text" id="age" name="hrPostApply.age" value="${hrPostApply.age}" style="width:100%;height:100%;border:none;line-height:40px;text-align:center;"/>
					</td>
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
						<textarea id="proSummary" name="hrPostApply.proSummary" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPostApply.proSummary}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="6" style="height:40px;border:1px solid #000000;">
						<input type="reset" value="取消"/>&nbsp;&nbsp;
						<input type="submit" value="保存"/>&nbsp;&nbsp;
						<input type="button" value="提交审核" onclick="onSend();"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
