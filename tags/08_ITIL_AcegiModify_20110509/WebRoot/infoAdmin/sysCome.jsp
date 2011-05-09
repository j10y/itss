<%@ page contentType="text/html; charset=GBK" %>

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>欢迎进入系统后台</TITLE>
<%@include file="/includefiles.jsp"%>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK" />
<META NAME="copyright" CONTENT="Copyright 2004-2008" />
<META NAME="Author" CONTENT="xform" />
<META NAME="Keywords" CONTENT="" />
<META NAME="Description" CONTENT="" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">

</HEAD>
<BODY>
<div align="center">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="392" rowspan="2"><img src="${pageContext.request.contextPath}/images/adminmain01.gif" width="392" height="126"></td>
    <td height="114" valign="top" background="${pageContext.request.contextPath}/images/adminmain0line2.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
        <td>[<font color=#ffffff><b>${userInfo.realName }</b></font>]您好,欢迎您进入后台管理系统！</td>
      </tr>
      <tr>
        <td height="8"><img src="${pageContext.request.contextPath}/images/adminmain0line.gif" width="283" height="1" /></td>
      </tr>
      <tr>
        <td>
		<div id="peinfo1"><font color=ffffff><!-- 在这里您可以利用系统提供的强大的管理功能，便捷的后台管理功能，栏目无限级分类，IP控制网站功能，广告设置专题、文件管理专栏等功能有效地管理网站。您可以随时使用左边的<font color="#FF0000">关闭左栏</font>功能关闭或打开左边的管理导航，以扩展操作界面。 --></font></div>
         </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="9" valign="bottom" background="${pageContext.request.contextPath}/images/adminmain03.gif"><img src="${pageContext.request.contextPath}/images/adminmain02.gif" width="23" height="12"></td>
  </tr>
</table>
<br>
<!-- 
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <tr>
    <td height="24" colspan="2"><font color="#FFFFFF"><img src="${pageContext.request.contextPath}/images/Explain.gif" width="18" height="18" border="0" align="absmiddle">&nbsp;<strong>服务器信息</strong></font></td>
  </tr>
  <tr>
    <td width="50%" height="24" bgcolor="#EBF2F9">服务器操作系统：</td>
    <td width="50%" height="24" bgcolor="#EBF2F9">网站信息服务软件和版本：</td>
  </tr>
  <tr>
    <td width="50%" height="24" bgcolor="#EBF2F9">脚本解释引擎：</td>
    <td width="50%" height="24" bgcolor="#EBF2F9">脚本超时时间：秒</td>
  </tr>
  <tr>
    <td height="24" bgcolor="#EBF2F9">CDONTS组件支持：
    </td>
    <td height="24" bgcolor="#EBF2F9">Jmail邮箱组件支持：
       </td>
  </tr>
  <tr>
    <td height="24" bgcolor="#EBF2F9">返回服务器处理请求的端口：</td>
    <td height="24" bgcolor="#EBF2F9">协议的名称和版本：</td>
  </tr>
  <tr>
    <td height="24" bgcolor="#EBF2F9">服务器 CPU 数量：</td>
    <td height="24" bgcolor="#EBF2F9">FSO文本文件读写：
        </td>
  </tr>
  <tr>
    <td height="24" bgcolor="#EBF2F9">客户端操作系统：
       </td>
    <td height="24" bgcolor="#EBF2F9">站点物理路径：</td>
  </tr>
  <tr>
    <td width="50%" height="24" bgcolor="#EBF2F9">域名IP：http://&nbsp;/&nbsp;</td>
    <td width="50%" height="24" bgcolor="#EBF2F9">虚拟路径：</td>
  </tr>
  <tr>
    <td height="24" colspan="2" bgcolor="#D7E4F7">客户端浏览器要求： IE5.5或以上，并关闭所有弹窗的阻拦程序；服务器建议采用：Windows 2000或Windows 2003 Server。</td>
  </tr>
</table> -->
<br>
<br>
<table width="100%" border="0" align=center cellpadding="2" cellspacing="1" bgcolor="#6298E1" class="border">
  <tr align="center">
    <td height=25 class="topbg"><span class="Glow"><span class="STYLE5">Copyright 2006-2010 &copy; zsgj All Rights Reserved</span>.</span>  </tr>
</table>
</div>
</BODY>
</HTML>