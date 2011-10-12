<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>考评项目预览</title>
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
				font-size : 18px;
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
	</head>

	<body>
		<table id="headTable" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="padding-left:20px">
					名称：${spPaKpipbc.pbcName}
				</td>
				<td>
					频度：${spPaKpipbc.frequency.name}
				</td>
			</tr>
			<tr>
				<td style="padding-left:20px">
					部门：${spPaKpipbc.belongDept.depName}
				</td>
				<td>
					岗位：${spPaKpipbc.belongPost.jobName}
				</td>
			</tr>
		</table>
		<br/>
		<table id="contentTable" cellpadding="0" cellspacing="0">
			<tr style="height:40px">
				<td colspan="7" style="font-size:20px;padding-left:10px">考核指标列表</td>
			</tr>
			<c:forEach items="${kpiItemList}" var="kpiItem" varStatus="status">
				<tr>
					<td style="width:40px">&nbsp;</td>
					<td colspan="6">
						${status.index + 1}.&nbsp;&nbsp;${kpiItem.pi.paName}
						<c:choose>
							<c:when test="${kpiItem.pi.mode.id == 12}">
								<font style="font-size:12px;color:#888;margin-left:20px">（定性考核指标）</font>
							</c:when>
							<c:when test="${kpiItem.pi.mode.id == 13}">
								<font style="font-size:12px;color:#888;margin-left:20px">（定量考核指标）</font>
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td style="width:40px">&nbsp;</td>
					<td>
						<input type="radio"/>1分
					</td>
					<td>
						<input type="radio"/>2分
					</td>
					<td>
						<input type="radio"/>3分
					</td>
					<td>
						<input type="radio"/>4分
					</td>
					<td>
						<input type="radio"/>5分
					</td>
				</tr>
				<tr>
					<td colspan="7">&nbsp;</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
