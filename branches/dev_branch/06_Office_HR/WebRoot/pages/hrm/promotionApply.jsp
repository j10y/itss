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
		<title>员工晋升申请</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>		
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Toast.js"></script>
		<script type="text/javascript">
			__ctxPath = "${pageContext.request.contextPath}";
		</script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/UserSelector.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>/js/selector/UserSubSelector.js">
		</script>
		<script type="text/javascript">
			Ext.onReady(function() {
				var applyUserCombo = new Ext.form.ComboBox({
					id : "applyUserCombo",
					mode : "remote",
					hiddenName : "hrPromApply.applyUser.userId",
					valueField : "userId",
					width : 229,
					displayField : "fullname",
					renderTo : "applyUser",
					triggerAction : "all",
					allowBlank : false,
					blankText : "姓名不能为空！",
					store : new Ext.data.JsonStore({
						url : "${pageContext.request.contextPath}/hrm/sameDeptUserHrPromApply.do",
						fields : ["userId", "fullname", "depId", "depName", "accessionTime", "nowPositionId", "nowPositionName", "workYear", "workHereYear"],
						root : "result",
						remoteSort : true
					}),
					listeners : {
						select : function(l, h, k) {
							document.getElementById("depId").value = h.data.depId;
							document.getElementById("depName").value = h.data.depName;
							document.getElementById("accessionTime").value = h.data.accessionTime;
							document.getElementById("nowPositionId").value = h.data.nowPositionId;
							document.getElementById("nowPositionName").value = h.data.nowPositionName;
							document.getElementById("accessionTime").value = h.data.accessionTime;
							if(h.data.workYear == 0) {
								document.getElementById("workYear").value = "不满1年";
							} else {
								document.getElementById("workYear").value = h.data.workYear + "年";
							}
							if(h.data.workHereYear == 0) {
								document.getElementById("workHereYear").value = "不满1年";
							} else {
								document.getElementById("workHereYear").value = h.data.workHereYear + "年";
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
				var applyPositionNameCombo = new Ext.form.ComboBox({
					id : "applyPositionNameCombo",
					mode : "remote",
					hiddenName : "hrPromApply.applyPositionName",
					allowBlank : false,
					width : 229,
					valueField : "jobName",
					displayField : "jobName",
					renderTo : "applyPositionName",
					triggerAction : "all",
					allowBlank : false,
					blankText : "拟申请职位不能为空！",
					store : new Ext.data.JsonStore({
						url : "${pageContext.request.contextPath}/hrm/applyPositionHrPromApply.do",
						fields : ["jobId", "jobName"],
						root : "result",
						remoteSort : true
					}),
					listeners : {
						select : function(l, h, k) {
							document.getElementById("applyPositionId").value = h.data.jobId;
						}
					}
				});
				var applyDate = new Ext.form.DateField({
					name : "hrPromApply.applyDate",
					id : "hrPromApply.applyDate",
					format : "Y-m-d",
					editable : false,
					width : 230,
					allowBlank : false,
					blankText : "拟申请时间不能为空！",
					renderTo : "applyDate"
				});
				if("${hrPromApply.id}" != 0) {
					applyUserCombo.setValue("${hrPromApply.applyUser.userId}");
					applyUserCombo.setRawValue("${hrPromApply.applyUser.fullname}");
					applyPositionNameCombo.setValue("${hrPromApply.applyPositionName}");
					applyPositionNameCombo.setRawValue("${hrPromApply.applyPositionName}");
					applyDate.setRawValue("${hrPromApply.applyDate}".split(" ")[0]);
					document.getElementById("accessionTime").value = "${hrPromApply.accessionTime}".split(" ")[0];
				}
			});
			function check() {
				if(!Ext.getCmp("applyUserCombo").isValid()) {
					return false;
				}
				if(!Ext.getCmp("applyPositionNameCombo").isValid()) {
					return false;
				}
				if(!Ext.getCmp("hrPromApply.applyDate").isValid()) {
					return false;
				}
				return true;
			}
			function onSend(){
				var result = check();
				if(!result){
					return;
				}
				
				UserSelector.getView(
					function(id, name) {
						if(id!=""){
							Ext.Ajax.request({
								url : "${pageContext.request.contextPath}/hrm/saveHrPromApply.do",
								params : {
										"hrPromApply.id" : "${hrPromApply.id}",
										"hrPromApply.depId" : document.getElementById("depId").value,
										"hrPromApply.depName" : document.getElementById("depName").value,
										"hrPromApply.accessionTime" : document.getElementById("accessionTime").value,
										"hrPromApply.applyUser.userId" : document.getElementById("hrPromApply.applyUser.userId").value,
										"hrPromApply.nowPositionId" : document.getElementById("nowPositionId").value,
										"hrPromApply.nowPositionName" : document.getElementById("nowPositionName").value,
										"hrPromApply.workYear" : document.getElementById("workYear").value,
										"hrPromApply.workHereYear" : document.getElementById("workHereYear").value,
										"hrPromApply.applyReason" : document.getElementById("applyReason").value,
										"hrPromApply.target1" : document.getElementById("target1").value,
										"hrPromApply.target2" : document.getElementById("target2").value,
										"hrPromApply.target3" : document.getElementById("target3").value,
										"hrPromApply.intRecord" : document.getElementById("intRecord").value,
										"hrPromApply.applyPositionId" : document.getElementById("applyPositionId").value,
										"hrPromApply.applyPositionName" : document.getElementById("hrPromApply.applyPositionName").value,
										"hrPromApply.applyDate" : document.getElementById("hrPromApply.applyDate").value,
										"hrPromApply.publishStatus" : 5, //上报审批
										"isSubmit" : "true" //上报审批
								},
								method : "post",
								success : function(h, j) {
									var data = Ext.decode(h.responseText);
									document.getElementById("hrPromApply.id").value= data.applyId;
									Ext.Ajax.request({							
										url : "${pageContext.request.contextPath}/flow/saveProcessActivity.do",
										waitMsg : "正在提交流程表单信息...",
										scope : this,
										params : {
											defId : 16,
											startFlow : true,
											"hrPromApply.id" : document.getElementById("hrPromApply.id").value,
											"hrPromApply.depId" : document.getElementById("depId").value,
											"hrPromApply.depName" : document.getElementById("depName").value,
											"hrPromApply.accessionTime" : document.getElementById("accessionTime").value,
											"hrPromApply.applyUser.userId" : document.getElementById("hrPromApply.applyUser.userId").value,
											"hrPromApply.nowPositionId" : document.getElementById("nowPositionId").value,
											"hrPromApply.nowPositionName" : document.getElementById("nowPositionName").value,
											"hrPromApply.workYear" : document.getElementById("workYear").value,
											"hrPromApply.workHereYear" : document.getElementById("workHereYear").value,
											"hrPromApply.applyReason" : document.getElementById("applyReason").value,
											"hrPromApply.target1" : document.getElementById("target1").value,
											"hrPromApply.target2" : document.getElementById("target2").value,
											"hrPromApply.target3" : document.getElementById("target3").value,
											"hrPromApply.intRecord" : document.getElementById("intRecord").value,
											"hrPromApply.applyPositionId" : document.getElementById("applyPositionId").value,
											"hrPromApply.applyPositionName" : document.getElementById("hrPromApply.applyPositionName").value,
											"hrPromApply.applyDate" : document.getElementById("hrPromApply.applyDate").value,
											"hrPromApply.publishStatus" : 5, //上报审批
											"isSubmit" : "true", //上报审批
											//flowAssignId : k.deptUserId
											flowAssignId : id
										},
										success : function(
												i,
												j) {
											Ext.ux.Toast.msg("操作信息", "成功保存信息！");
											window.location = "${pageContext.request.contextPath}/pages/hrm/applyResult.jsp?flag=2";
											return;
											Ext.getCmp("HrPromApplyForm").close();
											var k = Ext.getCmp("ProcessRunGrid");
											if (k != null) {
												k.getStore().reload();
											}
										}
																					
									});
									return ;
									Ext.Ajax.request({
										url : "${pageContext.request.contextPath}/archive/getByCurrentUserArchRecUser.do",
										success : function(h, j) {
											var k = Ext.util.JSON.decode(h.responseText).data;
											Ext.Ajax.request({							
												url : "${pageContext.request.contextPath}/flow/saveProcessActivity.do",
												waitMsg : "正在提交流程表单信息...",
												scope : this,
												params : {
													defId : 16,
													startFlow : true,
													"hrPromApply.id" : document.getElementById("hrPromApply.id").value,
													"hrPromApply.depId" : document.getElementById("depId").value,
													"hrPromApply.depName" : document.getElementById("depName").value,
													"hrPromApply.accessionTime" : document.getElementById("accessionTime").value,
													"hrPromApply.applyUser.userId" : document.getElementById("hrPromApply.applyUser.userId").value,
													"hrPromApply.nowPositionId" : document.getElementById("nowPositionId").value,
													"hrPromApply.nowPositionName" : document.getElementById("nowPositionName").value,
													"hrPromApply.workYear" : document.getElementById("workYear").value,
													"hrPromApply.workHereYear" : document.getElementById("workHereYear").value,
													"hrPromApply.applyReason" : document.getElementById("applyReason").value,
													"hrPromApply.target1" : document.getElementById("target1").value,
													"hrPromApply.target2" : document.getElementById("target2").value,
													"hrPromApply.target3" : document.getElementById("target3").value,
													"hrPromApply.intRecord" : document.getElementById("intRecord").value,
													"hrPromApply.applyPositionId" : document.getElementById("applyPositionId").value,
													"hrPromApply.applyPositionName" : document.getElementById("hrPromApply.applyPositionName").value,
													"hrPromApply.applyDate" : document.getElementById("hrPromApply.applyDate").value,
													"hrPromApply.publishStatus" : 5, //上报审批
													"isSubmit" : "true", //上报审批
													//flowAssignId : k.deptUserId
													flowAssignId : id
												},
												success : function(
														i,
														j) {
													Ext.ux.Toast.msg("操作信息", "成功保存信息！");
													window.location = "${pageContext.request.contextPath}/pages/hrm/applyResult.jsp?flag=2";
													return;
													Ext.getCmp("HrPromApplyForm").close();
													var k = Ext.getCmp("ProcessRunGrid");
													if (k != null) {
														k.getStore().reload();
													}
												}
																							
											});
										}
									});
								}
							});
							
						}else{
							Ext.ux.Toast.msg('操作信息','请选择上级领导！');
						}
				}).show();
				
				
				
				/*$.post("${pageContext.request.contextPath}/hrm/saveHrPromApply.do",
						{
							"hrPromApply.id" : "${hrPromApply.id}",
							"hrPromApply.depId" : document.getElementById("depId").value,
							"hrPromApply.depName" : document.getElementById("depName").value,
							"hrPromApply.accessionTime" : document.getElementById("accessionTime").innerHTML,
							"hrPromApply.applyUser.userId" : document.getElementById("hrPromApply.applyUser.userId").value,
							"hrPromApply.nowPositionId" : document.getElementById("nowPositionId").value,
							"hrPromApply.nowPositionName" : document.getElementById("nowPositionName").value,
							"hrPromApply.workYear" : document.getElementById("workYear").value,
							"hrPromApply.workHereYear" : document.getElementById("workHereYear").value,
							"hrPromApply.applyReason" : document.getElementById("applyReason").value,
							"hrPromApply.target1" : document.getElementById("target1").value,
							"hrPromApply.target2" : document.getElementById("target2").value,
							"hrPromApply.target3" : document.getElementById("target3").value,
							"hrPromApply.intRecord" : document.getElementById("intRecord").value
						},
						function(data){
    						alert("Data Loaded: " + data);
   						},
   						"json");*/
			}
		</script>
	</head>

	<body>
		<div style="text-align:center;height:50px;line-height:50px;">
			<font style="font-size:18px;">晋升申请表</font>
		</div>
		<form id="applyForm" onsubmit="return check();" action="${pageContext.request.contextPath}/hrm/saveHrPromApply.do" method="post">
			<input type="hidden" id="hrPromApply.id" name="hrPromApply.id" value="${hrPromApply.id}"/>
			<input type="hidden" name="flag" value="0"/>
			<table style="width:700px;margin:0 auto;border:1px solid #000000;background:#ffffff" cellpadding="0" cellspacing="0">
				<tr>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">姓名</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;"><div id="applyUser"></div></td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">部门/门店</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
						<input type="hidden" id="depId" name="hrPromApply.depId" value="${hrPromApply.depId}"/>
						<input type="text" id="depName" name="hrPromApply.depName" value="${hrPromApply.depName}" readonly="readonly" style="border:none;width:100%;height:100%"/>
					</td>
				</tr>
				<tr>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">入职日期</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">
						<input type="text" id="accessionTime" name="hrPromApply.accessionTime" value="${hrPromApply.accessionTime}" readonly="readonly" style="border:none;width:100%;height:100%"/>
					</td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">现职位</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
						<input type="hidden" id="nowPositionId" name="hrPromApply.nowPositionId" value="${hrPromApply.nowPositionId}"/>
						<input type="text" id="nowPositionName" name="hrPromApply.nowPositionName" value="${hrPromApply.nowPositionName}" readonly="readonly" style="border:none;width:100%;height:100%"/>
					</td>
				</tr>
				<tr>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟担任职位</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">
						<input type="hidden" id="applyPositionId" name="hrPromApply.applyPositionId" value="${hrPromApply.applyPositionId}"/>
						<div id="applyPositionName"></div>
					</td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟晋升时间</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;"><div id="applyDate"></div></td>
				</tr>
				<tr>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">工作年限</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;">
						<input type="text" id="workYear" name="hrPromApply.workYear" value="${hrPromApply.workYear}" style="border:none;width:100%;height:100%"/>
					</td>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">本单位工作年限</td>
					<td style="width:230px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;border-right:1px solid #000000;">
						<input type="text" id="workHereYear" name="hrPromApply.workHereYear" value="${hrPromApply.workHereYear}" style="border:none;width:100%;height:100%"/>
					</td>
				</tr>
				<tr>
					<td style="width:120px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">拟晋升原因</td>
					<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea id="applyReason" name="hrPromApply.applyReason" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.applyReason}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">工作目标设定</td>
				</tr>
				<tr>
					<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标一</td>
					<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea readonly="readonly" id="target1" name="hrPromApply.target1" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target1}</textarea>
					</td>
				</tr>
				<tr>
					<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标二</td>
					<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea readonly="readonly" id="target2" name="hrPromApply.target2" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target2}</textarea>
					</td>
				</tr>
				<tr>
					<td style="width:120px;height:60px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;">目标三</td>
					<td colspan="3" style="height:60px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea readonly="readonly" id="target3" name="hrPromApply.target3" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.target3}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="height:40px;text-align:center;border-left:1px solid #000000;border-top:1px solid #000000;background:#ddd;border-right:1px solid #000000;">员工面谈记录</td>
				</tr>
				<tr>
					<td colspan="4" style="height:150px;padding:2px;border:1px solid #000000;border-bottom:none;">
						<textarea readonly="readonly" id="intRecord" name="hrPromApply.intRecord" style="width:100%;height:100%;border:none;overflow-y:hidden;">${hrPromApply.intRecord}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4" style="height:40px;border:1px solid #000000;">
						<input type="reset" value="取消"/>&nbsp;&nbsp;
						<input type="submit" value="保存"/>&nbsp;&nbsp;
						<input type="button" value="提交上级领导审核" onclick="onSend();"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
