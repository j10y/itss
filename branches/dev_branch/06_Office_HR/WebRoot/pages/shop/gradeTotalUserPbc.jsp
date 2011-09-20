<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人PBC模板打分</title>
		<style type="text/css">
			#headTable{
				width : 500px;
				margin : 0 auto;
				background : #eee;
			}
			#headTable tr{
				height : 40px;
			}
			#headTable tr td{
				padding : 0;
				font-size : 22px;
			}
			#contentTable{
				width : 700px;
				margin : 0 auto;
				border : 0;
			}
			#contentTable tr{
				height : 25px;
			}
			#contentTable tr td{
				padding : 0;
			}
		</style>
		<script type="text/javascript">
			window.onload = function() {
				if(${forAudit == 'true'}) {
					if(confirm("计算结果总分为：" + ${totalScore} + "\r\n确定要提交审核吗？")) {
						var auditForm = document.getElementById("auditForm");
						auditForm.submit();
					} else {
						return ;
					}
				}
			}
			function check(a) {
				var pbcForm = document.getElementById("pbcForm");
				pbcForm.submit();
				a.disabled="disabled";
			}
		</script>
	</head>

	<body>
		<div style="text-align:center;height:80px;line-height:80px;font-size:30px;background:#eee;">
			PBC定性考核计算总分
		</div>
		<br/>
		<form id="auditForm" action="${pageContext.request.contextPath}/shop/submitToAuditSpPaKpiPBC2User.do" method="post">
			<input type="hidden" name="pbcId" value="${pbcId}"/>
		</form>
		<form id="auditForm" action="${pageContext.request.contextPath}/shop/gridTotalScoreSpPaAuthorizepbc.do" method="post">
			<input type="hidden" name="pbcId" value="${pbcId}"/>
			<table id="contentTable" cellpadding="0" cellspacing="0">
				<tr style="height:40px">
					<td colspan="7" style="font-size:20px;padding-left:10px">考核指标列表</td>
				</tr>
				<c:forEach items="${itemMap}" var="item" varStatus="status">
					<tr>
						<td style="width:40px">&nbsp;</td>
						<td colspan="6">
							${status.index + 1}.&nbsp;&nbsp;${item.key['paName']}&nbsp;&nbsp;&nbsp;&nbsp;
							得分：<fmt:formatNumber value="${item.key['result']}" pattern="#.###" type="number"/>
						</td>
					</tr>
					<tr>
						<td colspan="7">&nbsp;</td>
					</tr>
				</c:forEach>
				<tr style="text-align:center">
					<td colspan="7">
						<input type="reset" value="取消"/>&nbsp;&nbsp;
						<input id="saveSubmit" type="submit" value="计算总分"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
