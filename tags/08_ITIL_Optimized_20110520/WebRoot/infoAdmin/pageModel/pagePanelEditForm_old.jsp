<%@ page pageEncoding="gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>PagePanel表单</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">
   //模板Id
   var tmpId = '${detail.id}';
</script>
<script type="text/javascript">
function findTableByModule(){ 
	   var serverPath = "${pageContext.request.contextPath}";
	   var module = Ext.getDom("module");
	   var modulestr = module.options[module.selectedIndex].value;
	   var systemMainTable = Ext.getDom("systemMainTable");
	   Ext.Ajax.request({
	      url: serverPath+"/pageModel/pagePanelManage.do?methodCall=findTableByModule", 
	      params:{
	          module: modulestr
	      },
	      method:'POST',
	      success:function(response){
	        var data = response.responseText;
	        var result = Ext.decode(data);
	        var obj = result.data;
	        initSelect(obj, systemMainTable);
	      }//end func
	  });
}

function initSelect(cobj, fselect){
  var j=1;
  for(var i=fselect.options.length-1;i>=0;--i){ 
	fselect.options[i]=null;  
  }
  for(var i=0 ;i< cobj.length ;i++){
	fselect.options[j] = new Option(cobj[i].tableCnName,cobj[i].id);
	j = j +1;
  }
}

function findColumnsByTable(){
	var xform = document.getElementById("editAll");
	xform.methodCall.value="findColumnByTable";
	xform.submit();
}
function savePagePanel(){
	var xform = document.getElementById("editAll");
	xform.methodCall.value="saveAll";
	xform.submit();
}
function delColumn(){
	var xform = document.getElementById("editAll");
	xform.methodCall.value="removeColumn";
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
      border=0>&nbsp;<STRONG>PagePanel管理</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
     <a href="${pageContext.request.contextPath}/pageModel/pageModelManage.do?methodCall=list&pageNo=1">PagePanel列表</a>
    </TD>
 </TR>
</TBODY>
</TABLE>
<BR>

系统后台管理 -&gt; PagePanel
<table width="100%" border="0" bgcolor="#6298E1">
<form name="editAll" id="editAll" action="${pageContext.request.contextPath}/pageModel/pagePanelManage.do" method=post>
<input type="hidden" name="pageNo" value="${param.pageNo}">
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">Panel标题</td>
    <td width="36%" bgcolor="#EFF3FF">
    <input name="title"  id="title"   type="text" value="${empty detail.title? (pp.title): (detail.title)}">
    <input name="methodCall"  id="methodCall"   type="hidden" value="save">
    
    <input name="id"  id="id"   type="hidden" value="${empty detail.id? (pp.id): (detail.id)}">
    </td>
    <td width="11%" bgcolor="#EFF3FF">页面表现形式</td>
    <td width="41%" colspan="3"  bgcolor="#EFF3FF">
      <select name="settingType" id="settingType" class="textfield">
      <option value="1"></option>
      <c:forEach var="item" items="${requestScope.settingTypes}" varStatus="status">
      <c:if test="${not empty item.name}">
          <option value="${item.id}" ${pp.settingType eq item.id?' selected':''}>${item.name}</option>
      </c:if>
     </c:forEach>
    </select>  
   </td>
  </tr>
  
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">Ext组件类型</td>
    <td width="36%" bgcolor="#EFF3FF">
    <input name="xtype"  id="xtype"   type="text" value="${empty detail.xtype? (pp.xtype): (detail.xtype)}">
    <input name="methodCall"  id="methodCall"   type="hidden" value="save">
    
    <input name="id"  id="id"   type="hidden" value="${empty detail.id? (pp.id): (detail.id)}">
    </td>
    <td width="11%" bgcolor="#EFF3FF">分组类型</td>
    <td width="41%" colspan="3"  bgcolor="#EFF3FF">
      <select name="groupFlag" id="groupFlag" class="textfield">
      <option value="1"></option>
      <c:forEach var="item" items="${requestScope.groupFlags}" varStatus="status">
      <c:if test="${not empty item.name}">
          <option value="${item.id}" ${pp.groupFlag eq item.id?' selected':''}>${item.name}</option>
      </c:if>
     </c:forEach>
    </select>  
   </td>
  </tr>
  
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">所属的模块</td>
    <td width="36%" bgcolor="#EFF3FF">
     <select name="module" id="module" class="textfield" onchange="findTableByModule();">
      <option value=""></option>
      <c:forEach var="item" items="${requestScope.modules}" varStatus="status">
      <option value="${item.id}" ${pp.module.id eq item.id?' selected':''}>${item.name}</option>
      </c:forEach>
    </select>
    </td>
    <td width="12%" bgcolor="#EFF3FF" >所属的系统主表</td>
    <td width="36%" bgcolor="#EFF3FF">
     <select name="systemMainTable" id="systemMainTable"  class="textfield" >
      <option value=""></option>
      <c:forEach var="item" items="${requestScope.systemMainTables}" varStatus="status">
      <option value="${item.id}" ${pp.systemMainTable.id eq item.id?' selected':''}>${item.tableCnName}</option>
     </c:forEach>
    </select>
   </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="6" bgcolor="#EFF3FF">
   <INPUT class=button onclick="savePagePanel();" type=button value="保存PagePanel">
    </td>
  </tr>
  <tr><td></td></tr>
</table>  
<BR>

系统后台管理&nbsp;-&gt;&nbsp;PagePanel管理&nbsp;-&gt;&nbsp;PagePanelColumn:

<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <!-- <FORM name="formDel" action="${pageContext.request.contextPath}/pageModel/pagePanelManage.do" method="post">
  <input type="hidden" name="methodCall" value="removeColumn">
  <input type="hidden" name="pageNo" value="${param.pageNo}">
  <input name="ppId" type="hidden" value="${pp.id}">  -->
  <TBODY>
  <TR>
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>字段名称</STRONG></FONT>
    </TD>
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>所属主表称</STRONG></FONT>
    </TD>
    <td nowrap bgColor=#8db5e9>
    <font color="#FFFFFF"><STRONG>页面宽度</STRONG></font>（%/px）
    </td>
    <TD noWrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>是否显示</FONT></STRONG>
    </TD>
    <TD noWrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>必须显示</FONT></STRONG>
    </TD>
    
    <TD noWrap  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>排序</STRONG></FONT>
    </TD>
    <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>操作</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect>
    </TD>

  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.pagePanelColumns}" varStatus="status">   
 
  <TR id="tr${item.id}" style="CURSOR: hand" 
       bgColor=#ebf2f9>
    <TD noWrap>${item.mainTableColumn.columnCnName}</TD>
    <TD noWrap>${item.systemMainTable.tableCnName}(${item.systemMainTable.id})</TD>
    <TD noWrap>
    <input id="order${item.id}" name="lengthForPage${item.mainTableColumn.id}" 
    		type="text" style="height:18px;width:70px" value="${item.length}">
     </TD>
    <TD noWrap>
     <input name="isDisplay" type="checkbox" 
     	<c:if test="${item.isDisplay eq 1}">checked</c:if> onclick="func(this,${item.id});">
     <input id="column_checked${item.id}"  name="isDisplay${item.mainTableColumn.id}" type="hidden" style="width:35px"
                        value="<c:choose><c:when test="${item.isDisplay eq 1}">${item.id}1</c:when><c:otherwise>${item.id}0</c:otherwise></c:choose>">
       <!-- <span><font color="red">修改成功</font></span> -->
         			
    </TD>
    <TD>
	 <select name="isMustInput${item.mainTableColumn.id}"  class="textfield">
        <option value="0" ${empty item.isMustInput or item.isMustInput eq 0?' selected':''}>否</option>
        <option value="1" ${item.isMustInput eq 1?' selected':''}>是</option>
      </select> 
	</TD> 
    <TD noWrap><input id="order${item.mainTableColumn.id}" name="order${item.mainTableColumn.id}" type="text" style="height:18px;width:20px" value="${item.order}">&nbsp;</TD>
     <TD width=48 align="center"> 
		<INPUT name="id" type="checkbox" value="${empty item.id?item.mainTableColumn.id : item.id}" style="WIDTH:13px;HEIGHT:13px">
    </TD>
    </TR>
 </c:forEach> 
  <TR style="CURSOR: hand"  bgColor=#ebf2f9>
	<TD style="width:90px;" align="left">
   		
    </TD>
    <TD noWrap colspan="5">
   		<INPUT class=button  onclick="findColumnsByTable();" type=button value="加载主字段" >
    </TD>

    <TD style="width:90px;" align="left">
    	<INPUT class=button  onclick="delColumn();" 
    		type=button value="删除所选" name="submitDelSelect">
      </TD>
   
    </TR></TBODY></TABLE></TD></TR><!-- </FORM> --></TBODY></TABLE>
</form>
</table>
<p>&nbsp;</p>
</body>
</html>
