<%@page contentType="text/html;charset=gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<head>
<style type="text/css" media="screen">
.title{background:url(images/bg.gif) no-repeat 1px 2px;padding:1px;height:18px;line-height:18px;color:#fff;text-indent:20px;}
td{text-align:center;font:smaller Verdana,"Andale Mono",Courier,"Courier New",monospace;font-size:12px;}
th{font:smaller Verdana,"����",Courier,"Courier New",monospace;font-weight:bold;}
</style>

<title>�ʺ��嵥</title>
</head>
<body>
<h3 align="center">WWW���ø���</h3>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;" bgcolor="#c0c0c0">
<tr>
<td>
<table width="600" border="0" cellpadding="4" cellspacing="1" align="center" >
  <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong>��ǰ���:</strong></td>
    <td width="35%" bgcolor="#ffffff" style="text-align:left">${limit}Ԫ</td>
    </tr>
  <tr>
    <td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong>ʵ�ʷ���:</strong></td>
    <td bgcolor="#ffffff" style="text-align:left">${fee}Ԫ</td>
    </tr>
  <tr>
    <td style="text-align:right;width:20%" bgcolor="#F0F0FA"><strong>�¶����:</strong></td>
    <td bgcolor="#ffffff" style="text-align:left">${balance}Ԫ</td>
    </tr>
  <tr>
    <td style="text-align:right;width:15%" bgcolor="#F0F0FA"><strong>��������:</strong></td>
    <td bgcolor="#ffffff" style="text-align:left">${flux}M</td>
    </tr>
</table>
</td>
</tr>
</table>
<div align="center"><br>
  <input type="submit" name="button" id="button" value="�鿴��ϸ" onClick="window.open('${pageContext.request.contextPath}/accountAction_findWWWMonthDetail.action?yearAndMonth=${yearAndMonth}','_self')">
</div>
</body>
</HTML>