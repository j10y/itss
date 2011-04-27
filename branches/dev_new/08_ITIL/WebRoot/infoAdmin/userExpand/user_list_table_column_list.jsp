<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">  
<title>用户主表属性与字段设置</title>
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

function saveSystemMainTable(){ 
    var serverPath = "${pageContext.request.contextPath}";

    var id = Ext.getDom("id").value;
    var tableName = Ext.getDom("tableName").value;
    var tableCnName = Ext.getDom("tableCnName").value;
    var className = Ext.getDom("className").value;
    var primaryKeyColumn = Ext.getDom("primaryKeyColumn");
    var module = Ext.getDom("module");
    var pkselectid = primaryKeyColumn.selectedIndex;
    var pkId=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].value;
    var pkName=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].text;
    var moduleId = module.options[module.selectedIndex].value;

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/userMainTable.do?methodCall=save", 
       params:{
           id: id, 
           tableName: tableName,
           tableCnName: unicode(tableCnName),
           className: className,
           primaryKeyColumn: pkId,
           module: moduleId
           
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         Ext.getDom("tableName").value=smt.data.tableName;
         Ext.getDom("tableCnName").value=smt.data.tableCnName;
         Ext.getDom("className").value=smt.data.className
         Ext.getDom("primaryKeyColumn").selectedIndex=pkselectid;
         
         alert("用户主表属性保存成功!");
         
       }
  });
}

function loadNewColumn(mainTableId){ 
    var serverPath = "${pageContext.request.contextPath}";

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/userMainTableColumn.do?methodCall=loadNewColumns", 
       params:{
           mainTableId: mainTableId
           
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         window.location.href = window.location.href;
         
         
       }
  });
}

function addNewColumn(mainTableId){ 
    document.location.href="${pageContext.request.contextPath}/infoAdmin/userListTableColumn.do?methodCall=toForm&tableId="+mainTableId+"&id=";
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


function saveNewTable(){
  var xform = document.editForm;
 
  if(xform.tableName.value==""){
  	 alert("自定义主表英文名必须填写");
  	 xform.tableName.focus();
  	 return false;
  }else if(xform.tableCnName.value==""){
  	 alert("自定义主表中文名必须填写");
  	 xform.tableCnName.focus();
  	 return false;
  }

  if(isChinese(xform.tableName.value)){
     alert("自定义主表英文名不可以包含中文");
  	 xform.tableName.focus();
  	 return false;
  }
  
  if(!isChinese(xform.tableCnName.value)){
     alert("自定义主表中文名填写中文");
  	 xform.tableCnName.focus();
  	 return false;
  }
  
  xform.submit();

 
}

function deployNewTable(){ 
  var result = confirm("确认发布吗？注意发布成功后，如再新增字段，只能在下次服务器重启后生效！");
  if(result){
   var xform = document.getElementById("editForm");
   xform.methodCall.value="deployTable";
   xform.submit();
  }
 
}
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>用户自定义主表管理</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    <a href="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do?methodCall=toAdd" 
    	target="mainFrame" >[增加用户自定义主表]</a>
    </TD>
   
 </TR>
</TBODY>
</TABLE>
<BR>

用户后台管理 -&gt; 自定义主表
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do" 
 	   method=post>
 
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">自定义主表英文名</td>
    <td width="38%" bgcolor="#EFF3FF">
    <input name="methodCall"  id="id"  type="hidden" value="saveTable">
    <input name="id"  id="id"   type="hidden" value="${smt.id}">
    <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${smt}">
      <input name="tableName" id="tableName"  type="text" class="textfield" style="WIDTH:250px;" value="${smt.tableName}">
    
    <br>
    </td>
    <td  width="12%" bgcolor="#EFF3FF">自定义主表中文名</td>
    <td width="38%" bgcolor="#EFF3FF">
     <input name="tableCnName"  id="tableCnName" type="text" class="textfield" style="WIDTH:250px;" value="${smt.tableCnName}">     
     <input name="primaryKeyColumn" id="primaryKeyColumn" type="hidden" class="textfield" value="${smt.primaryKeyColumn.id}">               
    <br>
    </td>
  </tr>

  <tr>  
    <td width="10%" align="center" bgcolor="#EFF3FF">自定义主表映射实体类</td>
    <td width="40%" bgcolor="#EFF3FF">
     <input name="className" id="className" type="hidden" class="textfield" style="WIDTH:400px;" value="${empty smt?'com.zsgj.itil.config.extlist.entity':smt.className}">      
   ${empty smt?'com.zsgj.itil.config.extlist.entity':smt.className}
   </td>
    <td width="10%" bgcolor="#EFF3FF">所属模块</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="module" id="module" style="width:100px;" class="textfield">
       <c:forEach var="item" items="${requestScope.modules}" varStatus="status">
         <option value="${item.id}" ${smt.module.id eq item.id? ' selected':'' }>${item.name}</option>
        </c:forEach>
      </select> 
    </td>
  </tr>
 
 <tr>  
    <td align="center" bgcolor="#EFF3FF">状态</td>
    <td  bgcolor="#EFF3FF">
   		${empty smt.deployFlag or smt.deployFlag eq 0? '未发布' : '已发布'}
    </td>
    
    <td align="left" bgcolor="#EFF3FF"></td>
    <td  bgcolor="#EFF3FF">
   		<input name="userExtFlag"  id="userExtFlag"   type="hidden" value="${smt.userExtFlag}">
   		<input name="deployFlag"  id="deployFlag"   type="hidden" value="${smt.deployFlag}">
   		<input name="templateFlag"  id="templateFlag"   type="hidden" value="${smt.deployFlag}">
   		
    </td>
  </tr>
      
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="3" bgcolor="#EFF3FF">
   		<input class=button type="button"  onclick="saveNewTable();" value="保存自定义主表">&nbsp;
    	<input class=button type="button" onclick="deployNewTable();" value="发布自定义主表">
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<BR>

  用户后台管理 -&gt; 用户自定义表字段
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/userListTableColumn.do" method="post">
    <input type="hidden" name="methodCall" value="remove">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="smtId" value="${smt.id}">
    <TBODY>
      <TR>
        <TD  width="20" bgColor=#8db5e9> <FONT color=#ffffff><STRONG>ID</STRONG></FONT> </TD>
		<TD width="100"  bgColor=#8db5e9><FONT color=#ffffff><strong>字段名称</strong></FONT> </TD>
		 <TD width="100"  bgColor=#8db5e9> <FONT color=#ffffff><STRONG>字段中文名称</STRONG></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>字段类型</strong></FONT> </TD>
       
       
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>表单组件类型</strong></FONT></TD>
        
        <TD noWrap  bgColor=#8db5e9><FONT color=#ffffff><strong>关联表</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>关联表显示字段</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>数据验证类型</strong></FONT></TD>
		 
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>操作</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect>
        </TD>
      </TR>
      <c:forEach  var="item" items="${requestScope.mainColumns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        <A onclick='changeAdminFlag("修改导航栏目")' 
          href="${pageContext.request.contextPath}/infoAdmin/userListTableColumn.do?methodCall=toForm&tableId=${smt.id}&id=${item.id}">
        ${item.columnName }&nbsp; 
        </A>
        </TD>
         <TD noWrap>${item.columnCnName }</TD>
        <TD noWrap>${item.propertyType.sqlColumnType}(${item.propertyType.propertyTypeName })&nbsp; </TD>
      
       
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
		
        <TD width=48 align="center"> 
		<A onclick='changeAdminFlag("修改导航栏目")' 
           href="${pageContext.request.contextPath}/infoAdmin/userListTableColumn.do?methodCall=toForm&tableId=${smt.id}&id=${item.id}"> <FONT color="#330099">修改</FONT>
		</A><INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
        </TD>
      </TR>
      </c:forEach>
      <TR> 
        
        <TD noWrap bgColor=#ebf2f9 colSpan=7 align="right">&nbsp;
       </TD>
         <TD colSpan=1 noWrap bgColor=#ebf2f9 align="center">
       <INPUT class=button  onclick="addNewColumn(${smt.id});" type=button value="增加主字段" >&nbsp;  
        </TD>
        <TD colSpan=1 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('删除字段将级联删除其用户可见字段和查询字段，您确认删除吗？');" 
    		type=button value="删除所选" name="submitDelSelect">
        </TD>
      </TR>
      
   </TBODY>
  </form>
  
</TABLE>
<br>
</body>
</html>
