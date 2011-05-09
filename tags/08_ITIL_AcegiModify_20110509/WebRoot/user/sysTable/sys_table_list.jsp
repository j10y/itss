<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>系统主表</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/> 
<META content="" name=Keywords>
<META content="" name=Description>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/ext-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>

<META content="MSHTML 6.00.2900.5583" name=GENERATOR></HEAD>

<script language="javascript">
function saveSort(){ 
  var xform = document.formDel;
  xform.methodCall.value="saveItemOrder";
  xform.submit();
}

</script>

<script language="javascript">
 function query(moduleId, pageNo){ 
   alert(moduleId);
   var xform = document.formSearch;
   xform.module.value = moduleId;
   xform.pageNo.value = pageNo;
   xform.methodCall.value="list";
   xform.submit();
 }
 
 function query(pageNo){ 
   var xform = document.formSearch;
   var moduleSelect = xform.module;
  
   var selectModuleId = moduleSelect.options[moduleSelect.selectedIndex].value;

   xform.pageNo.value = pageNo;
   xform.methodCall.value="list";
   xform.submit();
 }

function loadNewTables(){ 
    var serverPath = "${pageContext.request.contextPath}";

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/sysMainTable.do?methodCall=loadNewTables", 
       params:{
           mainTableId: 1
           
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         window.location.href = window.location.href;
         
         
       }
  });
}
</script>
  
  
<BODY >
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>系统主表管理：查询，添加、修改系统主表</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
      <TABLE cellSpacing=0 width="100%" border=0>
        <TBODY>
        <TR>

  <FORM name="formSearch" action="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do" method="post">
         
          <TD align="center"> 
        所属模块：
          <select name="module" style="width:100px;" class="textfield">
            <option value=""></option>
             <c:forEach var="item" items="${requestScope.modules}" varStatus="status">
              <option value="${item.id}" ${param.module eq item.id?' selected':''}>${item.name}</option>
            </c:forEach>
         </select>&nbsp;
         主表名称：<INPUT name="tableName" type="text" value="${param.tableName}">
         <INPUT name="pageNo" type="hidden" value="${param.pageNo}">
         <INPUT name="methodCall" type="hidden" value="list">
         <INPUT class="button" type="button" onclick="query(1);" value="检索" > 
         
         </TD>
        
          
  </FORM>
 
          <TD noWrap align=right>
            <A href="javascript:query(1);">全部主表</A>
          
           </TD>
          </TR>
          </TBODY>
          </TABLE>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
        
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>系统后台管理&nbsp;-&gt;&nbsp;
  ${not empty requestScope.module? requestScope.module.name : '全部'}主表
  </TD></TR></TBODY></TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="pageNo" value="${param.pageNo}">

  
  <TBODY>
  <TR>
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD noWrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>系统表名</FONT></STRONG>
    </TD>
    <TD noWrap width="20%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>表名中文</STRONG></FONT>
    </TD>
    <TD noWrap width="50%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>映射实体类类全名</STRONG></FONT>
    </TD>
    
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>主键字段</STRONG></FONT>
    </TD>
    <TD noWrap width="20%" bgColor=#8db5e9>
    <STRONG><FONT color=#ffffff><STRONG>所属模块</STRONG></FONT></STRONG>
    </TD>
   
   <TD  width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
    <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect> 
   </TD>
  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.page.data}" varStatus="status">   
 
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
    <TD  width="20">${item.id }</TD>
    <TD noWrap>
    <A onclick='changeAdminFlag("修改导航栏目")' 
      href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=toForm&id=${item.id}">
      ${item.tableName }
    </A> 
    </TD>
    
    <TD noWrap>${item.tableCnName }</TD>
    <TD width="70%">${item.className }</TD>
    
    <TD noWrap>${item.primaryKeyColumn.columnCnName }</TD>
    <TD noWrap>${item.module.name}&nbsp;</TD>
    
 
    <TD width=48 align="center">
    <A onclick='changeAdminFlag("修改导航栏目")' 
       href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=toForm&id=${item.id}">
      <FONT color="#330099">修改</FONT></A>
      <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px">
      </TD>
   
    </TR>
  
 </c:forEach> 
  
  
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=6 align="right">&nbsp;
    <INPUT class=button  onclick="loadNewTables();" type=button value=加载新主表 >&nbsp;
     <!--<INPUT class=button id=sort onclick="saveSort();" type=button value=保存排序 name=submitDelSelect>-->
    </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="ConfirmDel('删除主表将级联删除其所有字段和用户定制信息，您确认删除吗？');" 
    		type=button value="删除所选" name="submitDelSelect">
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
      <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
        <TBODY>
        <TR>
          <TD>共计：<FONT color=#ff6600>${requestScope.page.totalCount}</FONT>条记录&nbsp;页次：<FONT 
            color=#ff6600>${param.pageNo}</FONT></STRONG>/${requestScope.page.totalPageCount}&nbsp;每页：<FONT 
            color=#ff6600>${requestScope.page.pageSize}</FONT>条</TD>
          <TD align=right>&nbsp;
          <c:set var="actionPath" value="${pageContext.request.contextPath}/admin/itemManage.do?methodCall=${param.methodCall}" scope="page"/>
          <c:set var="pageno" value="${param.pageNo}" scope="page"/>
          <c:set var="pagenc" value="2" scope="page"/><!-- 每页显示的分页页码数量=pagenc*2+1 -->
          <c:set var="pagenmin" value="${pageno-pagenc}" scope="page"/>
          <c:set var="pagenmax" value="${pageno+pagenc}" scope="page"/>
          <c:set var="pagec" value="${requestScope.page.totalPageCount}" scope="page"/>
          
          <c:if test="${pagenmin<1}">
            <c:set var="pagenmin" value="1" scope="page"/>
          </c:if>
          <c:if test="${pageno>1}"><!-- 如果页码大于1则显示(第一页) -->
            <a href="javascript:query(1);"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>9</font></a>
          </c:if>
          <c:if test="${pagenmin>1}"><!-- 如果页码开始值大于1则显示(更前) -->
            <a href="javascript:query(${(pageno-(pagenc*2-1))});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>7</font></a>
          </c:if>
          <c:if test="${pagenmax>pagec}"> <!-- '如果页码结束值大于总页数,则=总页数 -->
            <c:set var="pagenmax" value="${pagec}" scope="page"/>
          </c:if>
		  <c:forEach var="index" begin="${pagenmin}" end="${pagenmax}"  varStatus="status">
		   <c:if test="${index eq pageno}" var="current">
		    <font color='#ff6600'>${index}</font>
		   </c:if>
		   <c:if test="${not current}">
		    [<a href="javascript:query(${index});">${index}</a>]
		   </c:if>
		  </c:forEach>
		  <c:if test="${pagenmax<pagec}"> <!-- '如果页码结束值小于总页数则显示(更后) -->
		   <a href="javascript:query(${pageno+(pagenc*2+1)});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>8</font></a>&nbsp;
		  </c:if>
		  <c:if test="${pageno<pagec}"> <!-- 如果页码小于总页数则显示(最后页)	 -->
		   <a href="javascript:query(${pagec});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>:</font></a>&nbsp;
		  </c:if>
		  
          &nbsp;跳到：第&nbsp;<INPUT class=textfield 
            onkeydown=if(event.keyCode==13)event.returnValue=false 
            style="WIDTH: 40px; HEIGHT: 18px" 
            onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='1';}" 
            value=1 name="skipPage">&nbsp;页
             <INPUT class=button style="WIDTH: 20px; HEIGHT: 18px" 
             onclick="javascript:getPage();" type=button value=GO name=submitSkip> 
          </TD></TR>
          </TBODY>
       </TABLE>
       <script>
         function getPage(){ 
           var pageNo = document.formDel.skipPage.value;
           query(pageNo);
         }
       </script>
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>
</BODY></HTML>
