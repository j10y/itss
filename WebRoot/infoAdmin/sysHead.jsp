<%@ page contentType="text/html; charset=GBK" %>
<table name="Trans" id="Trans" width="98%" height="24" border="0" cellpadding="0" cellspacing="0" style="BORDER-Top: #6298E1 1px solid;font-family:����;font-size:12px;color:#000000 ;">
  <tr>
    <td width="50" nowrap ></td>
    <td width="200" nowrap>����Ա��${userInfo.realName }</td>
    <td width="50" align="right" nowrap>λ�ã�</td>
    <td align="left" nowrap id="DateTime">
      	<script language="javascript">
			var week; 
			if(new Date().getDay()==0){week="������";}
			if(new Date().getDay()==1){week="����һ";}
			if(new Date().getDay()==2)          {week="���ڶ�" ;}
			if(new Date().getDay()==3)          {week="������";}
			if(new Date().getDay()==4)          {week="������";}
			if(new Date().getDay()==5)          {week="������";}
			if(new Date().getDay()==6)          {week="������";}
			if(navigator.userAgent.toLowerCase().indexOf("msie") >= 0){
				document.write(new Date().getYear()+"��"+(new Date().getMonth()+1)+"��"+new Date().getDate()+"�� "+week);
			}else{
				document.write((new Date().getYear() + 1900)+"��"+(new Date().getMonth()+1)+"��"+new Date().getDate()+"�� "+week);
			}
		</script>					
    </td>
    <td width="90" align="right" nowrap >
    	&nbsp;<a onclick="window.parent.location='${pageContext.request.contextPath}/login.jsp'" style="cursor:auto;"><font color="blue">[���µ�¼]</font></a> 
    </td>
    <td width="90" align="right" nowrap style="cursor:head;">
    	<a onclick="window.parent.location='${pageContext.request.contextPath}/j_spring_security_logout'"  ><font color="blue">[�˳���¼]</font></a> 
    </td>
  </tr>
</table>