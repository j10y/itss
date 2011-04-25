<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>My JSP 'columnsList.jsp' starting page</title>
    <%@include file="/includefiles.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

  </head>
  <body>
 <TABLE width="100%" border=0 align=center cellPadding=0 cellSpacing=0>
  <TBODY>
    <TR>
      <TD bgcolor="#CCCCCC">
      
                
                <table width=100% border=0 align=center cellpadding="2" cellspacing=1>
                  <tbody>
                    <tr bgcolor="#6298E1"><!-- #ffcc99 -->
                     
                      <td  nowrap height="25"  valign="middle"><font color="#FFFFFF">编号</font></td>
                      <td nowrap height="25" valign="middle"><font color="#FFFFFF">选项名称</font></td>
                      
                      
                      <td nowrap height="25" align="center" valign="middle">
                      <font color="#FFFFFF">修改</font> </td>
                      <td nowrap height="25" align="center" valign="middle">
                      <font color="#FFFFFF">删除</font> </td>
                    </tr>
                    <c:forEach var="item" items="${requestScope.extOptionDatas}">
                    <tr bgcolor="#EFF3FF"><!-- #ffcc99 -->
                     
                      <td  nowrap height="25"  valign="middle"><font color="#000000">${item.id }</font></td>
                      <td nowrap height="25" valign="middle"><font color="#000000">${item.extOptionValue }</font></td>
                      <td nowrap height="25" align="center" valign="middle">
                      <font color="#000000"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=modifyOptionById&id=${item.id }&extColumnId=${extendTableId }">修改</a></font> </td>
                      <td nowrap height="25" align="center" valign="middle">
                      <font color="#000000"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=removeOptionById&id=${item.id }&extColumnId=${extendTableId }">删除</a></font> </td>
                    </tr> 
                    </c:forEach> 
                    <form action="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=saveSelectOption" method="post">
                    <tr bgcolor="#EFF3FF">
                        <td nowrap  height="25" valign="middle"> 
                    <input name="extColumnId" type="hidden" style="width:100px;" value="${extendTableId }" >    
                     <c:if test="${not empty requestScope.extOption and requestScope.extOption!=null}">
                     <input name="id" type="hidden" style="width:100px;" value="${requestScope.extOption.id}" >
                     ${requestScope.extOption.id}
                     </c:if>
                      </td>
                      <td nowrap  height="25" valign="middle"> 
                       <input name="extOptionValue" style="width:90%;" value="${requestScope.extOption.extOptionValue}" >        
                      </td>
                     
                      <td height="25" align="center" colspan="2" valign="middle">
                      
                      <input type="submit" name="Submit" value="保存">
                      <!--  
                      <a href="${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=saveSelectOption">取消</a>
                      --></td>
                      </tr>
                      </form>                 
                  </tbody>
                </table></TD>
            </TR>
          </TBODY>
      </TABLE></TD>
    </TR>
  </TBODY>
</TABLE>
<!-- 
<hr style="color:#3a95c2;height:1px"> 
<a href="/b2b/system/columnsDef.do?methodCall=toAddSelectOption&extSelectColumnNum=1">添加记录</a><br> -->
</body>
</html>