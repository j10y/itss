<%@page contentType="text/html;charset=gbk" %>
<%@page import="java.util.*,com.digitalchina.itil.account.entity.PersonFormalAccount" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<head>
<style type="text/css" media="screen">}
td{text-align:center;font:smaller Verdana,"Andale Mono",Courier,"Courier New",monospace}
th{font:smaller Verdana,"����",Courier,"Courier New",monospace;font-weight:bold;}
</style>

<title>�ʺ��嵥</title>
<center><h3><font color="#4B0082">�ʺ���Ϣ</font></h3></center>
</head>
<body>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;" bgcolor="#c0c0c0">
<tr>
<td>
<table width="600" border="0" cellpadding="1" cellspacing="1" align="center" >
  <tr bgcolor="#EDEBEB">
<td  colspan="4" style="text-align:left"><img src="../../images/accountinfo.jpg"/></td>
</tr>
  <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>����:</font></strong></td>
    <td width=35% bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.realName}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>Itcode:</font></strong></td>
    <td bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.itcode}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>Ա�����:</font></strong></td>
    <td width=35% bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.employeeCode}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>�ɱ����ĺ�:</font></strong></td>
    <td bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.costCenterCode}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>��������:</font></strong></td>
    <td width=35% bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.department}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>�ʼ��ȼ�������:</font></strong></td>
    <td bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.sameMailDept.name}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>�����ص�:</font></strong></td>
    <td width=35% bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.workSpace.name}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>�ʼ�������:</font></strong></td>
    <td bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.mailServer.name}</font></td>
  </tr>
   <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong><font size=2>�û�����:</font></strong></td>
    <td width=35% bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.userType.name}</font></td><td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong><font size=2>�����ӷ�Χ:</font></strong></td>
    <td bgcolor="#ffffff" style="text-align:left"><font size=2>${userInfo.personnelScope.name}</font></td>
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
<td colspan="4" style="text-align:left"><img src="../../images/accountinfodetail.jpg"/></td>
</tr>
<th bgcolor="#E4E4E4">���</th><th bgcolor="#E4E4E4">�ʺ�����</th><th bgcolor="#E4E4E4">����Ҫ��</th><th bgcolor="#E4E4E4">��ע˵��</th>

 <!--  modify by liuying at 20100115 for �ҵ��ʺ��嵥�����ֻ���Ϣ��ʾ start -->
   <c:choose>
   <c:when test="${mobileTelephone!= null}">
 <tr bgcolor="#F0F0FA">
      <td align="center">1</td>
     <td align="center">${mobileTelephone.accountType.name}</td>
     <td></td>
     <td>${mobileTelephone.rightsDesc}</td>
</tr>
   <c:forEach items="${myAccounts}" var="account" varStatus="loopStatus">
 <c:set var="color" value="#ffffff" />
  <c:if test="${loopStatus.count % 2 == 0}">
    <c:set var="color" value="#F0F0FA" />
  </c:if>
     <c:if test="${account.accountType.accountType == 'WWWAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td>���:${account.wwwAccountValue.type}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'MailAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td>�����С:${account.mailValue.volume}</td>
     <td>${account.rightsDesc}</td>
     </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'DomainAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'MSNAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'VPNAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td>
     ���ƿ���:${account.cardNumber}<br>
     ��������:${account.endDate}
     </td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'ERPAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td>ERP�ʺ����ƣ�${account.erpUserName}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'PushMailAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'BIAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td>�Ƿ��漰н�꣺${account.referSalary}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'E-LogisticsAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'E-BridgeAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td>�ֻ��ţ�${account.telephone}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
       <c:if test="${account.accountType.accountType == 'Telephone'}">
       <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td>��������:${account.telephoneNumber }<br>
     VOIP��̯:${account.voip }
     </td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
       <c:if test="${account.accountType.accountType == 'LmsAccount'}">
       <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
     <c:if test="${account.accountType.accountType == 'TravelerAccount'}">
       <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
   
 

</c:forEach>
   </c:when>
   <c:otherwise>
   <c:forEach items="${myAccounts}" var="account" varStatus="loopStatus">
 <c:set var="color" value="#ffffff" />
  <c:if test="${loopStatus.count % 2 == 0}">
    <c:set var="color" value="#F0F0FA" />
  </c:if>
     <c:if test="${account.accountType.accountType == 'WWWAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>���:${account.wwwAccountValue.type}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'MailAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>�����С:${account.mailValue.volume}</td>
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
     <td>
     ���ƿ���:${account.cardNumber}<br>
     ��������:${account.endDate}
     </td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
      <c:if test="${account.accountType.accountType == 'ERPAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>ERP�ʺ����ƣ�${account.erpUserName}</td>
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
     <td>�Ƿ��漰н�꣺${account.referSalary}</td>
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
     <td>�ֻ��ţ�${account.telephone}</td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
       <c:if test="${account.accountType.accountType == 'Telephone'}">
       <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${account.accountType.name}</td>
     <td>��������:${account.telephoneNumber }<br>
     VOIP��̯:${account.voip }
     </td>
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
     <c:if test="${account.accountType.accountType == 'TravelerAccount'}">
       <tr bgcolor="${color}">
     <td align="center">${loopStatus.count+1}</td>
     <td align="center">${account.accountType.name}</td>
     <td></td>
     <td>${account.rightsDesc}</td>
      </tr>
     </c:if>
   
 

</c:forEach>
   </c:otherwise>
   </c:choose>
   
 <!--  modify by liuying at 20100115 for �ҵ��ʺ��嵥�����ֻ���Ϣ��ʾ end -->


</table>
</td>
</tr>
</table>
<br>

 <c:if test="${accountNumber>0}">
     <center>
     <table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;" bgcolor="#c0c0c0">
	<tr bgcolor="#EDEBEB">
	<td colspan="5" style="text-align:left"><img src="../../images/spaccountinfo.jpg"/></td>
	</tr>
	<tr>
	<td>
     <table cellpadding="1" cellspacing="1" width="600">
     <th bgcolor="#E4E4E4">���</th><th bgcolor="#E4E4E4">�ʺ�����</th><th bgcolor="#E4E4E4">�ʺ�����</th><th bgcolor="#E4E4E4">�ʺ�������</th><th bgcolor="#E4E4E4">����Ҫ��</th><th bgcolor="#E4E4E4">��ע˵��</th>
     <c:forEach items="${specialAccounts}" var="specialAccount" varStatus="loopStatus">
 	<c:set var="color" value="#ffffff" />
  	<c:if test="${loopStatus.count % 2 == 0}">
    <c:set var="color" value="#F0F0FA" />
  	</c:if>
     <c:if test="${specialAccount.accountType.accountType == 'TempWWWAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
     <td align="center">${specialAccount.accountName}</td>
     <td align="center">${specialAccount.accountNowUser.userName}</td>
     <td>�����С:${specialAccount.wwwAccountValue.type}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
     <c:if test="${specialAccount.accountType.accountType == 'DeptMail'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
      <td align="center">${specialAccount.accountName}</td>
     <td align="center">${specialAccount.accountNowUser.userName}</td>
     <td>�����С:${specialAccount.mailValue.volume}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
      <c:if test="${specialAccount.accountType.accountType == 'TempMailAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
      <td align="center">${specialAccount.accountName}</td>
     <td align="center">${specialAccount.accountNowUser.userName}</td>
     <td>�����С:${specialAccount.mailValue.volume}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
   
      <c:if test="${specialAccount.accountType.accountType == 'TempVPNAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
      <td align="center">${specialAccount.accountName}</td>
     <td align="center">${specialAccount.accountNowUser.userName}</td>
     <td>���ƿ���:${specialAccount.cardNumber}<br>
     ��������:${specialAccount.endDate}
     </td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
      <c:if test="${specialAccount.accountType.accountType == 'SpecailERPAccount'}">
      <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
      <td align="center">${specialAccount.accountName}</td>
     <td align="center">${specialAccount.accountNowUser.userName}</td>
     <td>ERP�ʺ����ƣ�${specialAccount.erpUserName}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
  
     <c:if test="${specialAccount.accountType.accountType == 'TempEBAccount'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
      <td align="center">${specialAccount.accountName}</td>
     <td align="center">${specialAccount.accountNowUser.userName}</td>
     <td>�ֻ��ţ�${specialAccount.telephone}</td>
     <td>${specialAccount.rightsDesc}</td>
     </tr>
     </c:if>
      <c:if test="${specialAccount.accountType.accountType == 'Win7'}">
     <tr bgcolor="${color}">
     <td align="center">${loopStatus.count}</td>
     <td align="center">${specialAccount.accountType.name}</td>
      <td align="center">${specialAccount.accountName}</td>
     <td align="center">${specialAccount.accountNowUser.userName}</td>
     <td>Ӳ�����кţ�${specialAccount.hardwareId}</td>
     <td>${specialAccount.remarkDesc}</td>
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



</body>
</HTML>