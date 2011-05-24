<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="/includefiles.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Untitled Document</title>
<style type="text/css">
<!--
@import url("DCIT.css");
body {
	background-image: url(images/bg.png);
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.style1 {font-size: 12px; font-style: normal; font-family: Arial, Helvetica, sans-serif; text-decoration: none; line-height: 14px; color: #666666;}
.style2 {font-size: x-small}
-->
</style>
</head>

<body>
<table width="940" border="0" align="center" cellpadding="2" cellspacing="0">
  <tr>
    <td height="20"><div align="right" class="whitetxt">DCone首页 | 流程中心 | 管理ITpassword</div></td>
  </tr>
</table>
<table width="940" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="105"><img src="images/banner.png" width="940" height="105" /></td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td valign="top"><table width="173" border="0" cellpadding="5" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
          <td valign="middle" bgcolor="#FFFFFF"><img src="images/ITservice01.gif" alt="" width="173" height="29" /></td>
        </tr>
        <tr>
          <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="7"><img src="images/arrow_red_down.gif" width="5" height="12" /></td>
                    <td height="23" class="bannertxtbold">服务目录</td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" bgcolor="#999999"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                    <td height="23" class="bannertxt">服务目录</td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="7"><img src="images/arrow_red_down.gif" alt="" width="5" height="12" /></td>
                    <td height="23" class="redtxtbold">IT服务申请</td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" bgcolor="#999999"><img src="images/1x1_pix.gif" alt="" width="1" height="1" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">IT服务管理系统</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                </table></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="7"><img src="images/arrow_red_down.gif" alt="" width="5" height="12" /></td>
                    <td height="23" class="bannertxtbold">最新应用（更多）</td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" bgcolor="#999999"><img src="images/1x1_pix.gif" alt="" width="1" height="1" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">IT服务管理系统</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">绩效考核</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">权限管理系统<br /></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">ITSS系统</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">大客户管理系统</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">内部员工管理<br /></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">收发管理</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">                      常用快速通道</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/arrow_red_down.gif" alt="" width="5" height="12" /></td>
                      <td height="23" class="bannertxtbold">IT试用服务</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" bgcolor="#999999"><img src="images/1x1_pix.gif" alt="" width="1" height="1" /></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">移动邮件<br /></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7">&nbsp;</td>
                      <td height="23" class="bannertxt">                      团队协作空间</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/arrow_red_down.gif" alt="" width="5" height="12" /></td>
                      <td height="23" class="bannertxtbold">我的服务信息</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" bgcolor="#999999"><img src="images/1x1_pix.gif" alt="" width="1" height="1" /></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">帐户信息</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7">&nbsp;</td>
                      <td height="23" class="bannertxt">                      WWW费用查询</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">                        通讯费用查询</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">                        我提过的问题</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                      <td width="7"><img src="images/1x1_pix.gif" alt="" width="8" height="15" /></td>
                      <td height="23" class="bannertxt">                      我提过的需求</td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table width="165" border="0" cellpadding="0" cellspacing="0">
          <tr>
                  <td class="style1"><img src="images/1x1_pix.gif" width="38" height="20" /></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td valign="top" background="images/kropli_linia_top.gif" bgcolor="#FFFFFF"><img src="images/1x1_pix.gif" width="1" height="2" /></td>
    <td align="center" valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="5">
        <tr>
          <td><img src="images/ad.jpg" alt="" width="417" height="117" /></td>
        </tr>

      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="4">
        <tr>
          <td class="bannertxt">提高效率Notes升级即将开始提高效率Notes升级即将开始提高效率Notes.....</td>
        </tr>
      </table>
      <table width="417" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#CCCCCC" class="bannertxt"><img src="images/1x1_pix.gif" alt="" width="1" height="1" /></td>
        </tr>
      </table>
      <table width="417" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="images/1x1_pix.gif" width="50" height="8" /></td>
        </tr>
        <tr>
          <td><img src="images/ITservicesupport.png" width="417" height="29" /></td>
        </tr>
      </table>
      <table width="417" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="bannertxt"><img src="images/1x1_pix.gif" alt="" width="20" height="15" /></td>
        </tr>
      </table>
      <table width="417" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
        <tr>
          <td valign="top" bgcolor="#F4F4F4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="97" height="25" class="redtxtbold"><div align="center">IT小贴</div></td>
              <td width="246" bgcolor="#CCCCCC" class="bannertxtbold"><img src="images/1x1_pix.gif" width="10" height="10" />操作手册及常见问题</td>
            </tr>
            <tr>
              <td colspan="2" valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr>
                  <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                  <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
                  <td height="28" class="bannertxt"><span class="bannertxt">25934rtytrrt70主要性能 标称主频 1.66GHz标配内存</span><span class="bluetxt"> ( 2010-05-12)</span></td>
                </tr>
                <tr>
                  <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                </tr>
              </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
                    <td height="28" class="bannertxt">Oracle数据库系统、WebSphere、WebLogic中间件系<span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
                    <td height="28" class="bannertxt">Oracle数据库系统、WebSphere、WebLogic中间件系<span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
                    <td height="28" class="bannertxt">如何保证这些系统软件健康稳定运行已成为CIO面临的重<span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
                    <td height="28" class="bannertxt">如何保证这些系统软件健康稳定运行已成为CIO面临的重<span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8" rowspan="2"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                    <td width="10"><img src="images/sidebar_li_br.gif" alt="" width="6" height="6" /></td>
                    <td height="28" class="bannertxt">Oracle数据库系统、WebSphere、WebLogic中间件系<span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                  <tr>
                    <td><div align="right" class="bannertxt">更多</div></td>
                    <td width="14">&nbsp;</td>
                  </tr>
                </table>
                </td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table width="417" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="images/1x1_pix.gif" width="50" height="15" /></td>
        </tr>
        <tr>
          <td height="13"><img src="images/ITpeixun.png" width="417" height="27" /></td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td width="8"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
              <td width="1"><img src="images/entry_before.gif" width="13" height="9" /></td>
              <td height="28" align="left" class="bannertxt">公司颁发了新的证书  ghdsghadsdakdlflk  dsfsadadafad   <span class="bluetxt">( 2010-)</span></td>
            </tr>
            <tr>
              <td height="1" colspan="3" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
              </tr>
          </table>
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td width="8"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
                <td width="1"><img src="images/entry_before.gif" width="13" height="9" /></td>
                <td height="28" align="left" class="bannertxt">技术部门获得最佳研究奖   <span class="bluetxt">( 2010-05-12)</span></td>
              </tr>
              <tr>
                <td height="1" colspan="3" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="417" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="bannertxt"><img src="images/1x1_pix.gif" alt="" width="20" height="15" /></td>
        </tr>
      </table>
      <table width="401" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="images/t1.png" width="417" height="31" /></td>
        </tr>
      </table>
      <table width="401" border="0" cellpadding="0" cellspacing="1" bgcolor="#e4e4e4">
        <tr>
          <td width="178" valign="top" bgcolor="#FFFFFF"><img src="images/7888.png" width="178" height="26" /></td>
          <td width="174" valign="top" bgcolor="#FFFFFF"><img src="images/online.png" width="95" height="26" /></td>
        </tr>
        <tr>
          <td valign="top" bgcolor="#FFFFFF"><img src="images/tel.png" width="178" height="89" /></td>
          <td align="center" valign="top" bgcolor="#FFFFFF"><table width="85%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50" rowspan="2" align="center"><img src="images/P1.png" width="19" height="20" /></td>
              <td><p align="left" class="bannertxt">客户端在线支持<br />
              </p>                </td>
            </tr>
            <tr>
              <td><div align="left"><span class="bannertxt">OA应用在线支持</span></div></td>
            </tr>
            
          </table>
            <table width="85%" height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="50" height="19" align="center"><img src="images/C1.png" width="21" height="19" /></td>
                <td><p align="left" class="bannertxt">网上问答</p></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="417" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="bannertxt"><img src="images/1x1_pix.gif" alt="" width="20" height="15" /></td>
        </tr>
      </table>
      <table width="401" border="0" cellpadding="3" cellspacing="0">
        <tr>
          <td width="178" valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td width="8"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
              <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
              <td height="23" class="bannertxt"><div align="left">IT专员清单</div></td>
            </tr>

          </table></td>
          <td width="174" valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td width="8"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
              <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
              <td height="23" class="bannertxt">最新病毒代码及工具</td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td width="8"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
              <td width="10"><img src="images/sidebar_li_br.gif" width="6" height="6" /></td>
              <td height="23" class="bannertxt">常见办公软件安装</td>
            </tr>
          </table></td>
          <td align="center" valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td width="8"><img src="images/1x1_pix.gif" width="8" height="15" /></td>
              <td height="23" class="redtxt">最新更新时间：2010年-9月  下载</td>
              </tr>
          </table></td>
        </tr>
      </table></td>
    <td valign="top" background="images/kropli_linia_top.gif"><img src="images/1x1_pix.gif" alt="" width="1" height="2" /></td>
    <td valign="top"><table width="173" border="0" cellpadding="5" cellspacing="1" bgcolor="#FFFFFF">
      <tr>
        <td valign="middle" bgcolor="#FFFFFF"><img src="images/ITservicemanage.png" alt="" width="225" height="34" /></td>
      </tr>
      <tr>
        <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="images/ITgonggao.png" width="225" height="29" /></td>
            </tr>
            <tr>
              <td valign="top">
              	<table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                    <td height="26"><span class="bluetxt"><span class="bannertxt">无线千兆网络技术规范</span></span><span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
              </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                    <td height="26"><span class="bluetxt"><span class="bannertxt">无线千兆网络技术规范</span></span><span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                    <td height="26"><span class="bluetxt"><span class="bannertxt">无线千兆网络技术规范</span></span><span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                    <td height="26"><span class="bluetxt"><span class="bannertxt">无线千兆网络技术规范</span></span><span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                    <td height="26"><span class="bluetxt"><span class="bannertxt">无线千兆网络技术规范</span></span><span class="bluetxt"> ( 2010-05-12)</span></td>
                  </tr>
                  <tr>
                    <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                <table width="165" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="style1"><img src="images/1x1_pix.gif" alt="" width="38" height="20" /></td>
                  </tr>
                </table></td>
            </tr>
          </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="images/ITmanger.png" width="225" height="29" /></td>
              </tr>
              <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                    <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                    <td height="26"><p class="bannertxt">IT管理制度</p>
                      </td>
                  </tr>
                  <tr>
                    <td colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                      <td height="26"><p class="bannertxt">IT管理工作体系</p>
                      </td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                      <td height="26"><p class="bannertxt">IT服务报告</p></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="165" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td class="style1"><img src="images/1x1_pix.gif" alt="" width="38" height="20" /></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="images/ITsupport.png" width="225" height="30" /></td>
              </tr>
              <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                      <td height="26"><p><span class="bannertxt">公司颁发了新的证书</span><span class="bluetxt"> ( 2010-05-12)</span></p>
                      </td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                      <td height="26"><p><span class="bannertxt">技术部门获得最佳研究奖</span><span class="bluetxt"> ( 2010-05-12)</span></p>
                      </td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                      <td height="26"><p><span class="bannertxt">多点触摸的最新文章</span><span class="bluetxt"> ( 2010-05-12)</span></p>
                      </td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="8"><img src="images/sidebar_li_cat.gif" width="4" height="5" /></td>
                      <td height="26"><p><span class="bannertxt">多点触摸的最新文章</span><span class="bluetxt"> ( 2010-05-12)</span></p></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="2" background="images/dotted_grey.gif"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
          <table width="165" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="style1"><img src="images/1x1_pix.gif" width="38" height="15" /></td>
              </tr>
          </table>
          <img src="images/more.png" width="227" height="70" /></td>
      </tr>
    </table>
    <p class="bluetxt">&nbsp;</p></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#CCCCCC"><img src="images/1x1_pix.gif" width="1" height="1" /></td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="4">
  <tr>
    <td width="112" align="right"><img src="images/DC_logo.gif" width="112" height="34" /></td>
    <td width="1" bgcolor="#CCCCCC"><img src="images/1x1_pix.gif" width="1" height="2" /></td>
    <td width="468"><p class="Leftlinktxt">神州数码 版权所有 Copyright 2000-2010 All Rights Reserved<br />
    技术支持：7888  IT服务邮箱：<span class="redtxt">IT@DC  IT</span> 投诉&amp;建议邮箱：<span class="redtxt">IT-Manage@DC</span>   <span class="redtxt">关于我们</span></p></td>
  </tr>
</table>
<div align="center"></div>
</body>
</html>
