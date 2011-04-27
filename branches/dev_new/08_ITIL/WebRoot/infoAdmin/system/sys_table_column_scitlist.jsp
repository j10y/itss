<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>系统主表属性与字段设置</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">

function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
}
function openOptionWindow(url,title){
		window.open(url,title,
					"menubar=no,scrollbars=yes,resizable=yes,height=400,width=350,status=no,toolbar=no,location=no,fullscreen=no,left=5,top=5",
					true);
	}


//判断输入的字符串是中文（可包含其它字符）
function isChinese(s){
 // 正则表达式对象
 var re = new RegExp("[\\u4e00-\\u9fa5]", "");
 // 验证是否刚好匹配
 var yesorno = re.test(s);
 if(yesorno){
        return true;
 }
 else{
        return false;
 }
}

//判断输入的字符串是否是中文（只能使中文不能包含其它字符）
function isOnlyChinese(s){
 // 正则表达式对象
 var re = new RegExp("^[\\u4e00-\\u9fa5]+$", "");
 // 验证是否刚好匹配
 var yesorno = re.test(s);
 if(yesorno){
  return true;
 }
 else{
  return false;
 }
}


function checkForm(){
  var xform = document.editForm;
  if(xform.name.value==""){
  	 alert("服务项数据类型名称必须填写");
  	 xform.name.focus();
  	 return false;
  }
}

function addNewColumn(){ 
	var xform = document.formDel;
   xform.methodCall.value="add";
   xform.submit();
}
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>服务项数据类型管理：新增、修改字段</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    <!-- 
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableSetting.do?methodCall=list&smtId=${smt.id}&settingType=1">系统可见字段设置</a>
    &nbsp;
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableQuery.do?methodCall=list&smtId=${smt.id}">系统主表查询设置</a>
    &nbsp;
	 <a href="${pageContext.request.contextPath}/infoAdmin/sysTableRole.do?methodCall=list&smtId=${smt.id}">系统角色可见字段设置</a> 
    -->
    </TD>
   
 </TR>
</TBODY>
</TABLE>
<BR>

系统后台管理 -&gt; 服务项类型
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/infoAdmin/ServiceItemTypeAction.do?methodCall=save" 
 	   method=post onsubmit="return checkForm();">
 
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">服务项类型名称</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="id"  id="id"   type="hidden" value="${serviceItemType.id}">
    <input name="name"  id="name"   type="text" value="${serviceItemType.name}">
   </td>
   
   <td width="10%" align="center" bgcolor="#EFF3FF">发布状态</td>
   <td width="40%" bgcolor="#EFF3FF">
  		 <select name="deployFlag" style="width:100px;" class="textfield">
            <option value="0"
            <c:if test="${serviceItemType.deployFlag==0}">selected</c:if>
            >否</option>
            <option value="1"
            <c:if test="${serviceItemType.deployFlag==1}">selected</c:if>
            >是</option>
         </select>
  </td>
  
  </tr>
  <!-- 
  <tr>
   <td width="10%" align="center" bgcolor="#EFF3FF">是否是个性化</td>
  <td width="40%" bgcolor="#EFF3FF">
  		 <select name="specialFlag" style="width:100px;" class="textfield">
            <option value="0"
            <c:if test="${serviceItemType.specialFlag==0}">selected</c:if>
            >否</option>
            <option value="1"
            <c:if test="${serviceItemType.specialFlag==1}">selected</c:if>
            >是</option>
         </select>
  </td>
  <td width="10%" align="center" bgcolor="#EFF3FF"></td>
  <td width="40%" align="center" bgcolor="#EFF3FF"></td>
  </tr> -->
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF"></td>
    <td width="40%" bgcolor="#EFF3FF">
    <input class=button type="submit" name="Submit" value="保存服务项类型">
   </td>
   <td width="10%" align="center" bgcolor="#EFF3FF">是否特殊</td>
   <td width="40%" bgcolor="#EFF3FF">
  		 <select name="specialFlag" style="width:100px;" class="textfield">
            <option value="0"
            <c:if test="${serviceItemType.specialFlag==0}">selected</c:if>
            >否</option>
            <option value="1"
            <c:if test="${serviceItemType.specialFlag==1}">selected</c:if>
            >是</option>
         </select>
  </td>
   <td width="10%" align="center" bgcolor="#EFF3FF"></td>
   <td width="40%" bgcolor="#EFF3FF">
   </td>
  </tr>
  <tr>
    <td colspan="3"  bgcolor="#EFF3FF">
	</td>
    <td  align="right" bgcolor="#EFF3FF">
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<BR>

  系统后台管理 -&gt; 服务项数据类型 -&gt; 服务项数据 
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/SCIColumnAction.do" method="post">
    <input type="hidden" name="methodCall" value="remove">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="serviceItemType" value="${serviceItemType.id}">
    <TBODY>
      <TR>
        <TD  width="20" bgColor=#8db5e9> <FONT color=#ffffff><STRONG>ID</STRONG></FONT> </TD>		
        <TD  noWrap bgColor=#8db5e9 > <STRONG><FONT color=#ffffff>字段</FONT></STRONG> </TD>
        <TD noWrap  bgColor=#8db5e9> <FONT color=#ffffff><STRONG>字段中文名称</STRONG></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>表单类型</strong></FONT></TD>
        
        <TD noWrap  bgColor=#8db5e9><FONT color=#ffffff><strong>关联表</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>关联表显示字段</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>数据验证类型</strong></FONT></TD>
		 <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>排序</strong></FONT></TD>
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>操作</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect>
        </TD>
      </TR>
      <c:forEach  var="item" items="${requestScope.sCIColumns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        <A onclick='changeAdminFlag("修改导航栏目")' 
          href="${pageContext.request.contextPath}/infoAdmin/SCIColumnAction.do?methodCall=add&id=${item.id}&serviceItemType=${serviceItemType.id}">
        ${item.columnName }&nbsp; 
        </A>
        </TD>
        <TD noWrap>${item.columnCnName }</TD>
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
		<TD noWrap> 
		<input name="order${item.id}" type="text" class="textfield" id="order" style="WIDTH:20px;" value="${item.order}"></TD>
        <TD width=48 align="center"> 
		<A onclick='changeAdminFlag("修改导航栏目")' 
           href="${pageContext.request.contextPath}/infoAdmin/SCIColumnAction.do?methodCall=add&id=${item.id}&serviceItemType=${serviceItemType.id}"> <FONT color="#330099">修改</FONT>
		</A><INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
        </TD>
      </TR>
      </c:forEach>
      <TR>        
        <TD noWrap bgColor=#ebf2f9 colSpan=8 align="right">&nbsp;
         <INPUT class=button  onclick="addNewColumn();" type=button value="增加字段" >&nbsp;
       <!--  <INPUT class=button id=sort onclick="saveSort();" type=button value=保存排序 name=submitDelSelect>
         -->
        </TD>
        <TD colSpan=2 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('您确认删除吗？');" 
    		type=button value="删除所选" name="submitDelSelect">
        </TD>
      </TR>
      
   </TBODY>
  </form>
</TABLE>
<p>&nbsp;</p>
</body>
</html>
