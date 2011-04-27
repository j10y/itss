<%@page contentType="text/html;charset=gbk" %>
<%@page import="java.util.*,com.zsgj.itil.account.entity.PersonFormalAccount" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<head>
<title>我的帐号清单</title>
<center><h3><font color="#4B0082">我拥有的帐号</font></h3></center>
</head>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;" bgcolor="#c0c0c0">
<tr>
<td>
<table width="600" border="0" cellpadding="1" cellspacing="1" align="center" cellspacing="0">
  <tr bgcolor="#EDEBEB">
<td  colspan="4"><img src="../../images/accountinfo.jpg"/></td>
</tr>
  <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>姓名:</font></strong></td>
    <td width=35% bgcolor="#ffffff"><font size=2>${userInfo.realName}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>Itcode:</font></strong></td>
    <td bgcolor="#ffffff"><font size=2>${userInfo.itcode}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>员工编号:</font></strong></td>
    <td width=35% bgcolor="#ffffff"><font size=2>${userInfo.employeeCode}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>成本中心号:</font></strong></td>
    <td bgcolor="#ffffff"><font size=2>${userInfo.costCenterCode}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>部门名称:</font></strong></td>
    <td width=35% bgcolor="#ffffff"><font size=2>${userInfo.department}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>邮件等价各部门:</font></strong></td>
    <td bgcolor="#ffffff"><font size=2>${userInfo.sameMailDept.name}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>工作地点:</font></strong></td>
    <td width=35% bgcolor="#ffffff"><font size=2></font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>邮件服务器:</font></strong></td>
    <td bgcolor="#ffffff"><font size=2>${userInfo.mailServer.name}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>用户类型:</font></strong></td>
    <td width=35% bgcolor="#ffffff"><font size=2>${userInfo.userType.name}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>人事子范围:</font></strong></td>
    <td bgcolor="#ffffff"><font size=2>${userInfo.personnelScope.name}</font></td>
  </tr>
</table>
</td>
</tr>
</table>
<br>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;" bgcolor="#c0c0c0">
<tr>
<td>
<table align="center" border="0" cellpadding="1" cellspacing="1" width="600">
<tr bgcolor="#EDEBEB">
<td colspan="4"><img src="../../images/accountinfodetail.jpg"/></td>
</tr>
<th bgcolor="#E4E4E4">序号</th><th bgcolor="#E4E4E4">帐号类型</th><th bgcolor="#E4E4E4">特殊要求</th><th bgcolor="#E4E4E4">备注说明</th>
<c:forEach items="${myAccounts}" var="account" varStatus="loopStatus">
 <c:set var="color" value="#ffffff" />
  <c:if test="${loopStatus.count % 2 == 0}">
    <c:set var="color" value="#F0F0FA" />
  </c:if>
     <c:if test="${account.accountType.accountType == 'WWWAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>额度:${account.wwwAccountValue.type}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'MailAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>邮箱大小:${account.mailValue.volume}</td>
     <td>${account.rightsDesc}</td>
     </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'DomainAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'MSNAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'VPNAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>帐号名：${account.accountName}<br>
     令牌卡号:${account.cardNumber}<br>
     到期日期:${account.endDate}
     </td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'ERPAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>ERP帐号名称：${account.erpUserName}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'PushMailAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'BIAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>是否涉及薪酬：${account.referSalary}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'E-LogisticsAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'E-BridgeAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>手机号：${account.telephone}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
       <c:if test="${account.accountType.accountType == 'Telephone'}">
       <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>${account.telephoneNumber }</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
       <c:if test="${account.accountType.accountType == 'LmsAccount'}">
       <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
   
 

</c:forEach>

</table>
</td>
</tr>
</table>
 <c:if test="${accountNumber>0}">
     <center>
     <table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;" bgcolor="#c0c0c0">
	<tr bgcolor="#EDEBEB">
	<td colspan="5"><img src="../../images/spaccountinfo.jpg"/></td>
	</tr>
	<tr>
	<td>
     <table cellpadding="1" cellspacing="1" width="600">
     <th bgcolor="#E4E4E4">序号</th><th bgcolor="#E4E4E4">帐号类型</th><th bgcolor="#E4E4E4">帐号所有者</th><th bgcolor="#E4E4E4">特殊要求</th><th bgcolor="#E4E4E4">备注说明</th>
     <c:forEach items="${specialAccounts}" var="specialAccount" varStatus="loopStatus">
 	<c:set var="color" value="#ffffff" />
  	<c:if test="${loopStatus.count % 2 == 0}">
    <c:set var="color" value="#F0F0FA" />
  	</c:if>
     <c:if test="${specialAccount.accountType.accountType == 'TempWWWAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
     <td align="center">${specialAccount.accountNowUser.itcode}</td>
     <td>邮箱大小:${specialAccount.wwwAccountValue.type}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
     <c:if test="${specialAccount.accountType.accountType == 'DeptMail'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
     <td align="center">${specialAccount.accountNowUser.itcode}</td>
     <td>邮箱大小:${specialAccount.mailValue.volume}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
      <c:if test="${specialAccount.accountType.accountType == 'TempMailAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
     <td align="center">${specialAccount.accountNowUser.itcode}</td>
     <td>邮箱大小:${specialAccount.mailValue.volume}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
   
      <c:if test="${specialAccount.accountType.accountType == 'TempVPNAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
     <td align="center">${specialAccount.accountNowUser.itcode}</td>
     <td>令牌卡号:${specialAccount.cardNumber}<br>
     到期日期:${specialAccount.endDate}
     </td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
      <c:if test="${specialAccount.accountType.accountType == 'SpecailERPAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
     <td align="center">${specialAccount.accountNowUser.itcode}</td>
     <td>ERP帐号名称：${specialAccount.erpUserName}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
  
     <c:if test="${specialAccount.accountType.accountType == 'TempEBAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
     <td align="center">${specialAccount.accountNowUser.itcode}</td>
     <td>手机号：${specialAccount.telephone}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
     </c:forEach>
     </table>
     </td>
     </tr>
     </table>
     </center>
 </c:if>
 <br>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;">
<tr>
<td>
<font color="red">
提示：点击此链接，可进入帐号申请页面申请您所需要的帐号！<a href="">帐号申请</a></font>
</td>
</tr>
</table>
</HTML>