<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'view.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="my.css">
	<script>
		var surveyId = ${surveyId}; 
		function result(){
				var mainMark = 0;
				var message = "<font color='red'>请检查是否填写完毕,谢谢！</font>";
				var xform = document.form2;
				var vTmp, vTmp2, vTmp3, vTmp4;	
				 //下面这些主要是为了验证用的
				for(var i=0; i<xform.length; i++){
						if(xform[i].name == 'quest_id'){//radio
						var mark = 0;
						vTmp = xform[i].value;
						vTmp = vTmp + '_radio';
						var radios = document.getElementsByName(vTmp);
						for(var j=0; j< radios.length; j++){
							if(radios[j].checked){//本题目选中一项
								mark = 1;
								break;//一道题目选择一个
							}
						}
						if(mark==0){
							document.getElementById("message").innerHTML= message;
							return false;
						}
					}
					
					if(xform[i].name == 'quest_id2'){//checkbox
						var mark = 0;
						vTmp2 = xform[i].value;
						vTmp2 = vTmp2 + '_checkbox';
						var chechboxes = document.getElementsByName(vTmp2);
						for(var j=0; j< chechboxes.length; j++){
							if(chechboxes[j].checked){//本题目选中一项
								mark = 1;
								break;
							}
						}
						if(mark==0){
							document.getElementById("message").innerHTML= message;
							return false;
						}
					}
					if(xform[i].name == 'quest_id3'){//textfield
						var mark = 0;
						vTmp3 = xform[i].value;
						vTmp3 = vTmp3 + '_text';
						var textfield = document.form2(vTmp3).value;
						if(textfield.length == 0){
							text.innerText = "请填写完毕，谢谢6666!";
							document.getElementById("message").innerHTML= message;
							return false;
						}
					}	
					if(xform[i].name == 'quest_id4'){//textarea
						vTmp4 = xform[i].value;
						var eid = vTmp4+"_textArea"; 
						var text = document.getElementById(eid);
						if(text.innerText.length == 0){
							text.innerText = "请填写完毕，谢谢!";
							document.getElementById("message").innerHTML= message;
							return false;
						}
					}
	
				}
				xform.action="${pageContext.request.contextPath}/trainPlan_saveQuestResult.action";
				xform.submit();
		}
		function reset(){
			document.form2.reset();
		}
		function goback(){
			window.location="${pageContext.request.contextPath}/user/train/train_questList.jsp?dataId="+surveyId;
		}
	</script>

  </head>
  
  <body bgcolor="#808080" >
  <table>
  <tr >
  <td width="300" align="center" style="background-color: #808080;" valign="top" >
    </td>
	<td width="659" valign="top" style="background-color: #CCCCFF;" >
    <h2 align='center'>${survey.surveyName }</h2>
    <form   name="form2" action=""  method=post>
     <input type="hidden" name="courseId" value="${courseId}">
    <table align="left" width="660" border="0">
    <%   int i=1; %>
    <c:forEach var="quest" items="${questOptions}" >
    		<c:if test="${quest.key.questType.id=='1'}">
		    <tr>&nbsp;&nbsp;<%=i%>.  ${quest.key.questName}</tr>
		    <input type="hidden" name="quest_id" value="${quest.key.id}">   
				<c:forEach var="quest1" items="${quest.value}" >
				    <tr> &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="${quest.key.id}_radio" value="${quest1.id}"> ${quest1.answerNo}.${quest1.content}</tr>
		    	</c:forEach><br>	 
	    	<%  i++; %>
	    	</c:if>
    	 <c:if test="${quest.key.questType.id=='2'}">
		    <tr>&nbsp;&nbsp;<%=i%>.  ${quest.key.questName}</tr>
		    
		     <input type="hidden" name="quest_id2" value="${quest.key.id}">   
				<c:forEach var="quest2" items="${quest.value}"  varStatus="status">
				    &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="${quest.key.id}_checkbox" value="${quest2.id}" > ${quest2.answerNo}.${quest2.content}
		    	<c:if test="${status.count%3==0}"><br></c:if>
		    	</c:forEach>
	    		 <br><br>
	    	<%  i++; %>
    	</c:if>
    	<c:if test="${quest.key.questType.id=='3'}">
		     <input type="hidden" name="quest_id3" value="${quest.key.id}">  
		     <c:set var="id" value="${quest.key.id}"></c:set> 
			    <tr>&nbsp;&nbsp;<%=i%>.  ${quest.key.questName}<input type="text" style="border:none;width:150px;border-bottom:1px solid #000" name="${quest.key.id}_text" value="${sessionScope.listresult['id'].value}"></tr>   
			    <br>	 
		    	<%  i++; %>
		    	</c:if>
    	<c:if test="${quest.key.questType.id=='4'}">
		    <input type="hidden" name="quest_id4" value="${quest.key.id}">   
			    <tr>&nbsp;&nbsp;<%=i%>.  ${quest.key.questName}</tr><br>
				 <tr>&nbsp;&nbsp;&nbsp;&nbsp;
				 <textarea cols="60" rows="5" id="${quest.key.id}_textArea" name="${quest.key.id}_textArea"></textarea>
		    	<%  i++; %>
    	</c:if>
    </c:forEach>
    <br>
    <center>
    	<div id="message"></div>
		<input type="button" name="Submit" value="完成" onclick="result()">	     
	    <input type="button" name="Submit2" value="重置" onclick="reset()">	
	    <input type="button" name="Submit3" value="返回" onclick="goback()">	
	</center> 
    </table>
    </form>
         
     
     </td>
	 <td width="250" align="center" style="background-color: #808080" >
    </td>
    </tr>
    </table>
    
  </body>
</html>
