<%@ page contentType="text/html; charset=GBK" %>
<table name="Trans" id="Trans" width="98%" height="24" border="0" cellpadding="0" cellspacing="0" style="BORDER-Top: #6298E1 1px solid;font-family:宋体;font-size:12px;color:#000000 ;">
  <tr>
    <td width="50" nowrap ></td>
    <td width="200" nowrap>管理员：${userInfo.realName }</td>
    <td width="50" align="right" nowrap>位置：</td>
    <td align="left" nowrap id="DateTime">
      	<script language="javascript">
			var week; 
			if(new Date().getDay()==0){week="星期日";}
			if(new Date().getDay()==1){week="星期一";}
			if(new Date().getDay()==2)          {week="星期二" ;}
			if(new Date().getDay()==3)          {week="星期三";}
			if(new Date().getDay()==4)          {week="星期四";}
			if(new Date().getDay()==5)          {week="星期五";}
			if(new Date().getDay()==6)          {week="星期六";}
			if(navigator.userAgent.toLowerCase().indexOf("msie") >= 0){
				document.write(new Date().getYear()+"年"+(new Date().getMonth()+1)+"月"+new Date().getDate()+"日 "+week);
			}else{
				document.write((new Date().getYear() + 1900)+"年"+(new Date().getMonth()+1)+"月"+new Date().getDate()+"日 "+week);
			}
		</script>					
    </td>
    <td width="90" align="right" nowrap >
    	&nbsp;<a onclick="window.parent.location='${pageContext.request.contextPath}/login.jsp'" style="cursor:auto;"><font color="blue">[重新登录]</font></a> 
    </td>
    <td width="90" align="right" nowrap style="cursor:head;">
    	<a onclick="window.parent.location='${pageContext.request.contextPath}/j_spring_security_logout'"  ><font color="blue">[退出登录]</font></a> 
    </td>
  </tr>
</table>