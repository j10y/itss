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
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="my.css">
	<script>
	function unicode(s) {
		var len = s.length;
		var rs = "";
		for (var i = 0; i < len; i++) {
			var k = s.substring(i, i + 1);
			rs += "&#" + s.charCodeAt(i) + ";";
		}
		return rs;
	}
		function result(){
				var mainMark = 0;
				var message = "您未完全填写满意度问卷，请检查！";
				var xform = document.form2;
				var vTmp, vTmp2, vTmp3, vTmp4;	
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
							alert(message);
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
							alert(message);
							return false;
						}
					}
		//			if(xform[i].name == 'quest_id3'){//textfield
		//				var mark = 0;
		//				vTmp3 = xform[i].value;
		//				vTmp3 = vTmp3 + '_text';
		//				var textfield = document.form2(vTmp3).value;
		//				if(textfield.length == 0){
		//					text.innerText = "请填写完毕，谢谢!";
		//					document.getElementById("message").innerHTML= message;
		//					return false;
		//				}
		//			}	
		//			if(xform[i].name == 'quest_id4'){//textarea
		//				vTmp4 = xform[i].value;
		//				var eid = vTmp4+"_text"; 
		//				var text = document.getElementById(eid);
		//				if(text.innerText.length == 0){
		//					text.innerText = "请填写完毕，谢谢!";
		//					document.getElementById("message").innerHTML= message;
		//					return false;
		//				}
		//			}
					if(xform[i].name.indexOf("_textArea")!=-1){
						xform[i].value = unicode(xform[i].value);
					}
				}
				
				xform.action="${pageContext.request.contextPath}/eventAction_saveQuestResult.action";
				xform.submit();
		}
		function reset(){
			document.form2.reset();
		}
	</script>
  </head>
  <body bgcolor="#808080" background="${pageContext.request.contextPath}/user/js/images/globe2.gif">
  <table border="0px" style="line-height:30px;">
  <tr>
  <td width="400" align="center"  valign="top" background="${pageContext.request.contextPath}/user/js/images/globe2.gif">
    </td>
	<td width="650" valign="top" >
    <h3 align='center'>${survey.surveyName }</h3>
    <br>
    <form   name="form2" action=""  method=post style="font-size:12px">
    <input type="hidden" name="objId" value="${eventId}">
    <input type="hidden" name="surveyId" value="${surveyId}">
    <input type="hidden" name="taskId" value="${taskId}">
  	<table align="center" border="0px" width="480">    
  	<%  
  		int i = 1; 
	%>
    <c:forEach var="quest" items="${questOptions}" >
    		<c:if test="${quest.key.questType.id=='1'}">
		    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		    <input type="hidden" name="quest_id" value="${quest.key.id}">   
				<c:forEach var="quest1" items="${quest.value}" >
				    &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="${quest.key.id}_radio" value="${quest1.id}"> ${quest1.answerNo}.${quest1.content}
		    	</c:forEach><br>	 
	    	<%  i++; %>
	    	</c:if>
    	 <c:if test="${quest.key.questType.id=='2'}">
		    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		    
		     <input type="hidden" name="quest_id2" value="${quest.key.id}">   
				<c:forEach var="quest1" items="${quest.value}"  varStatus="status">
				    &nbsp;&nbsp;<input type="checkbox" name="${quest.key.id}_checkbox" value="${quest1.id}" > ${quest1.answerNo}.${quest1.content}
		    	<c:if test="${status.count%3==0}"></c:if>
		    	</c:forEach>
	    		 <br><br>
	    	<%  i++; %>
    	</c:if>
    	<c:if test="${quest.key.questType.id=='3'}">
		     <input type="hidden" name="quest_id3" value="${quest.key.id}">  
		     <c:set var="id" value="${quest.key.id}"></c:set> 
			    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}<input type="text" style="border:none;width:150px;border-bottom:1px solid #000" name="${quest.key.id}_text" value="${sessionScope.listresult['id'].value}"></tr>   
			    <br>	 
		    	<%  i++; %>
		    	</c:if>
    	<c:if test="${quest.key.questType.id=='4'}">
		    <input type="hidden" name="quest_id4" value="${quest.key.id}">   
			    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr><br>
				 <tr>&nbsp;&nbsp;&nbsp;&nbsp;
				 <textarea cols="60" rows="5" id="${quest.key.id}_textArea" name="${quest.key.id}_textArea"></textarea>
		    	<%  i++; %>
    	</c:if>
    </c:forEach>
    <br>
    <center>
    	<div id="message"></div>
		<input type="button" name="Submit" value="提交满意度" onclick="result()">	
	    <input type="button" name="Submit2" value="重置" onclick="reset()">	
	</center>  
    </table>
   	</form>
   	</td>
	<td width="250" align="center" >
    </td>
    </tr>
    </table>
  </body>
</html>
