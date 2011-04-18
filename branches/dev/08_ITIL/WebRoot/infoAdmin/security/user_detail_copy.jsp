<%@page contentType="text/html; charset=gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<HTML>
<HEAD><TITLE>授权</TITLE>
<%@include file="/includefiles.jsp"%>
<META http-equiv=Content-Type content="text/html; charset=gbk">
	<SCRIPT language="javascript">
	
	function doSelected(temp,temp1){ 
		var desSelect=eval('document.userDetailForm.'+temp)
		var srcSelect=eval('document.userDetailForm.'+temp1)
		
		for(idx=0;idx<desSelect.options.length;idx++){ //src select
		  if(desSelect.options[idx].selected==true){
		    var str="a";
		    for(id=0;id<srcSelect.options.length;id++){ //dsc select
		       if(srcSelect.options[id].value==desSelect.options[idx].value){
		       	  str="b";
		       }
		    } 
		    if(str=="a"){
		      srcSelect.options[srcSelect.options.length]=new Option(desSelect.options[idx].text,desSelect.options[idx].value,0,0);
		      //srcSelect.options[srcSelect.options.length].selected=true;
		      desSelect.options[idx]=null;
		    }
		  }//end if
		}//for
	}
	function allMultiColumnsSelect() 
	{
	  var List = document.userDetailForm.userSelectCorporations;
	  if (List.length && List.options[0].value == 'temp') return;
	  for (i=0;i<List.length;i++)
	  {
	     List.options[i].selected = true;
	  }
	}
</script>	
</head>

<body>
	
 <form action="${pageContext.request.contextPath}/admin/userRoleManage.do"  method="post">
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		          <tr bgcolor="#EFEFEF">
                   	<td  align=absMiddle>
                   	<INPUT name="methodCall" type="hidden" value="saveUsers">
                   	<INPUT name="id" type="hidden" value="${user.id}">
                   	<INPUT name="departCode" type="hidden" value="${user.departCode}">
                   	<INPUT name="itcode" type="hidden" value="${user.itcode}">
                   	<INPUT name="employeeCode" type="hidden" value="${user.employeeCode}">
                   	<INPUT name="mobilePhone" type="hidden" value="${user.mobilePhone}">
                   	<INPUT name="telephone" type="hidden" value="${user.telephone}">
                   	用户名：<INPUT name="userName" size=20 value="${user.userName}"> 
                      
                   
                    </td>
                    <td  align=absMiddle>
                   		用户姓名：<INPUT name="realName" size=20 value="${user.realName}"> 
                    </td>
                    
                    <td align=absMiddle colspan="3">
                    	ITCODE：<INPUT name="itcode" size=20 value="${user.itcode}"> 
                    </td>
                  </tr>
                  <tr bgcolor="#EFEFEF">
                     <td align=absMiddle>密&nbsp;码：<INPUT name="password" size=20 value="${user.password}"></td>
                     <td align=absMiddle>电子邮件：<INPUT name="email" size=20 value="${user.email}"></td> 
                     <td align=absMiddle>是否可用：
                      <INPUT name="enableTemp" type="checkbox"  
                       <c:if test="${user.enabled eq 1}">checked</c:if>
                       <c:if test="${user.enabled eq 0}"> </c:if>
                             onclick="switchEnabled(this);" >
                      <INPUT name="enabled" id="user_enabled" type="hidden" value="<c:if test="${user.enabled eq 1}">1</c:if><c:if test="${empty user.enabled or user.enabled eq 0}">0</c:if>" >
                     </td>
                     <td align=absMiddle colspan="2">
                     
                     <INPUT name="specialUser" type="hidden" value="${user.specialUser}">
                      
                    </td>
                  </tr>
                  
                </table>
  	
							
				<table width=100% border=0 align=center cellpadding="0" cellspacing=1>
                  <tbody>
                    <tr bgcolor="#3a95c2"><!-- #ffcc99 -->
                     <td   width="20%"  height="25"  valign="middle"><font color="#FFFFFF">角色名称</font></td>
                      <td   width="20%"  height="25"  valign="middle"><font color="#FFFFFF">描述</font></td>
            			<td width="200" height="25"  valign="middle">
                      <font color="#FFFFFF">&nbsp;选择</font> </td>
                    </tr> 
                

				<c:forEach  var="item" items="${roles}" varStatus="status">
                    <tr  bgcolor="<c:if test="${status.index%2==0}" var="t">#dfe4e8</c:if><c:if test="${!t}">#FFFFFF</c:if>"><!-- #FEFAFA -->
	                      <td width="20%" valign="middle">${item.name}</td>
	                      <td nowrap valign="middle">${item.descn}</td>
	                      <td nowrap height="25"  valign="middle">&nbsp;
	                         <input name="au_check" type="checkbox" <c:if test="${item.checked}">checked</c:if> onclick="func(this,${item.id});">
	                         <input id="role_check_id${item.id}"  name="role_check_name" type="hidden" value="<c:if test="${item.checked}">${item.id}|true</c:if><c:if test="${not item.checked}">${item.id}|false</c:if>">
	                      </td>
                    </tr>
                  
				</c:forEach>
         

                  </tbody>
                </table>
           
       <hr>
       <INPUT type="submit" value=" 保存用户授权 ">&nbsp;
       
       <INPUT type="button" value="返回角色列表 " 
      onclick="window.location.href='${pageContext.request.contextPath}/admin//userRoleManage.do?methodCall=listUsers';">
     
       
<script language="javascript"> 
 
  function switchEnabled(obj){
  	var valueObj = document.getElementById("user_enabled");
  	valueObj.value = obj.checked?"1":"0";
  	
  }
  
  function func(obj,itemId){ 
     //alert("is au_check:" + obj.checked);
     var valueObj = document.getElementById("role_check_id"+itemId);
     valueObj.value = ""+itemId+"|"+(obj.checked?true:false);
     //alert(valueObj.value);
  }
</script>		
</form>

</body>
</html>
