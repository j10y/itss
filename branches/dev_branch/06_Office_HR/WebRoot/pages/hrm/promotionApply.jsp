<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>员工晋升申请</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script type="text/javascript">
			Ext.onReady(function() {
				var applyUserCombo = new Ext.form.ComboBox({
					mode : "remote",
					allowBlank : false,
					hiddenName : "hrPromApply.applyUser.userId",
					valueField : "userId",
					displayField : "fullname",
					renderTo : "applyUser",
					triggerAction : "all",
					store : new Ext.data.JsonStore({
						url : "${pageContext.request.contextPath}/hrm/sameDeptUserHrPromApply.do",
						fields : ["userId", "fullname", "depName", "accessionTime", "position", "workYear", "workHereYear"],
						root : "result",
						remoteSort : true
					}),
					listeners : {
						select : function(l, h, k) {
							document.getElementById("department").innerHTML = h.data.depName;
							document.getElementById("accessionTime").innerHTML = h.data.accessionTime;
							document.getElementById("nowPosition").innerHTML = h.data.position;
							if(h.data.workYear == 0) {
								document.getElementById("workYear").innerHTML = "不满1年";
							} else {
								document.getElementById("workYear").innerHTML = h.data.workYear + "年";
							}
							if(h.data.workHereYear == 0) {
								document.getElementById("workHereYear").innerHTML = "不满1年";
							} else {
								document.getElementById("workHereYear").innerHTML = h.data.workHereYear + "年";
							}
						},
						beforequery : function(queryEvent) {
							var store = queryEvent.combo.store;
							store.baseParams = {
								"fullname" : queryEvent.query
							};
							store.load({
								params : {
									start : 0,
									limit : 25
								}
							});
							return false;
						}
					}
				});
				var applyPositionCombo = new Ext.form.ComboBox({
					mode : "remote",
					hiddenName : "hrPromApply.applyPosition.jobId",
					allowBlank : false,
					valueField : "jobId",
					displayField : "jobName",
					renderTo : "applyPosition",
					triggerAction : "all",
					store : new Ext.data.JsonStore({
						url : "${pageContext.request.contextPath}/hrm/applyPositionHrPromApply.do",
						fields : ["jobId", "jobName"],
						root : "result",
						remoteSort : true
					})
				});
				var applyDate = new Ext.form.DateField({
					name : "hrPromApply.applyDate",
					format : "Y-m-d",
					editable : false,
					renderTo : "applyDate"
				});
				if("${hrPromApply.id}" != 0) {
					applyUserCombo.setValue("${hrPromApply.applyUser.userId}");
					applyUserCombo.setRawValue("${hrPromApply.applyUser.fullname}");
					applyPositionCombo.setValue("${hrPromApply.applyPosition.jobId}");
					applyPositionCombo.setRawValue("${hrPromApply.applyPosition.jobName}");
					applyDate.setRawValue("${hrPromApply.applyDate}");
					
					document.getElementById("department").innerHTML = "${depName}";
					document.getElementById("accessionTime").innerHTML = "${accessionTime}";
					document.getElementById("nowPosition").innerHTML = "${position}";
					if("${workYear}" == 0) {
						document.getElementById("workYear").innerHTML = "不满1年";
					} else {
						document.getElementById("workYear").innerHTML = "${workYear}年";
					}
					if("${workHereYear}" == 0) {
						document.getElementById("workHereYear").innerHTML = "不满1年";
					} else {
						document.getElementById("workHereYear").innerHTML = "${workHereYear}年";
					}
				}
			});
		</script>
	</head>

	<body>
		<div style="text-align:center;height:50px;line-height:50px;">
			<font style="font-size:18px;">晋升申请表</font>
		</div>
		<form action="${pageContext.request.contextPath}/hrm/saveHrPromApply.do" method="post">
			<input type="hidden" name="hrPromApply.id" value="${hrPromApply.id}"/>
			<table width="700" align="center" border="1" cellpadding="0" cellspacing="0">
				<tr>
					<td width="90" align="right">姓名</td>
					<td width="250"><div id="applyUser"></div></td>
					<td width="120" align="right">部门/门店</td>
					<td><div id="department"></div></td>
				</tr>
				<tr>
					<td align="right">入职日期</td>
					<td><div id="accessionTime"></div></td>
					<td align="right">现职位</td>
					<td><div id="nowPosition"></div></td>
				</tr>
				<tr>
					<td align="right">拟担任职位</td>
					<td><div id="applyPosition"></div></td>
					<td align="right">拟晋升时间</td>
					<td><div id="applyDate"></div></td>
				</tr>
				<tr>
					<td align="right">工作年限</td>
					<td><div id="workYear"></div></td>
					<td align="right">本单位工作年限</td>
					<td><div id="workHereYear"></div></td>
				</tr>
				<tr>
					<td align="right">拟晋升原因</td>
					<td colspan="3" style="height:60px;padding:1px;">
						<textarea name="hrPromApply.applyReason" style="width:100%;height:100%;border:none;">${hrPromApply.applyReason}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">工作目标设定</td>
				</tr>
				<tr>
					<td align="right">目标一</td>
					<td colspan="3" style="height:60px;padding:1px;">
						<textarea name="hrPromApply.target1" style="width:100%;height:100%;border:none;">${hrPromApply.target1}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right">目标二</td>
					<td colspan="3" style="height:60px;padding:1px;">
						<textarea name="hrPromApply.target2" style="width:100%;height:100%;border:none;">${hrPromApply.target2}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right">目标三</td>
					<td colspan="3" style="height:60px;padding:1px;">
						<textarea name="hrPromApply.target3" style="width:100%;height:100%;border:none;">${hrPromApply.target3}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">员工面谈记录</td>
				</tr>
				<tr>
					<td colspan="4" style="height:150px;padding:1px;">
						<textarea name="hrPromApply.intRecord" style="width:100%;height:100%;border:none;">${hrPromApply.intRecord}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						<input type="reset" value="取消"/>&nbsp;&nbsp;
						<input type="submit" value="保存"/>&nbsp;&nbsp;
						<input type="button" value="提交审核"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
