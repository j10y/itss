<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>



<html>
  <head>
    <title>流程列表</title>
    <%@include file="/includefiles.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">

  </head>
  
  <body>
 	<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>流程监控</STRONG></FONT></TD></TR>
   <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
      <TABLE cellSpacing=0 width="100%" border=0>
        <TBODY>
        <TR>
        <td>
        </td>
        </TR>
        </TBODY>
      </TABLE>
    </TD>
  </TR>
  </TBODY>
  </TABLE>  
  <br>
  <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>选择流程实例</STRONG></FONT>
    </TD>
  </TR>
  
 <c:forEach  var="process" items="${processes}" varStatus="status">   
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
   <TD >
   definitionName: ${process.definitionName }|Version: ${process.defVersion }|Id: ${process.id }|start: 
    <fmt:formatDate value="${process.start}" pattern="MM月dd日 HH时mm分ss秒"/>
    | <a href="javascript: end(${process.id })">end</a>&nbsp;
    <a href="javascript: history(${process.id })">history</a>
    <br><br>
   </TD>
  </TR>
 </c:forEach> 

 <c:if test="${length==0}">
     <tr onmouseover="this.style.backgroundColor = '#FFFFFF'"
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
     <td >
    当前没有流程实例可操作.<br/><br/>
    </td>
     </tr>
 </c:if>
  </TBODY>
 </TABLE>
  </body>
  
  <Script language="javaScript">
  
  function audit(id){
    var url = "${pageContext.request.contextPath}/wftask?id="+id;
  	window.open(url); 
  }
  
  function end(id){
  //	if(window.confirm("确定要终止第"+id+"号流程吗？此操作不可恢复！")){
	    var url = "${pageContext.request.contextPath}/workflow/endprocess.do?id="+id;
	    url += "&methodCall=end";
	  	location = url;
  //	}
  }
  
  function history(id){
    var url = "${pageContext.request.contextPath}/workflow/history.do?procid="+id;
    url += "&methodCall=list";
  	location = url;
  }
  </Script>
</html>
