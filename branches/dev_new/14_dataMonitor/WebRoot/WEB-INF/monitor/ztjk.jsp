<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
		<TITLE></TITLE>	
		<script src="${pageContext.request.contextPath}/FusionChartsFree/js/FusionCharts.js" type="text/javascript"></script>
		<script
			src="${pageContext.request.contextPath}/JS/DatePicker/DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		 var myChart = new FusionCharts("${pageContext.request.contextPath}/FusionChartsFree/swf/ScrollLine2D.swf", "alarm_chart", "100%", "257","0","0");
	     var xmlData ="${parm}";	     
	     function bodyLoad() {
	            myChart.setDataXML(xmlData);
	            myChart.render("alarm_chart");
	        }
		</script>
	</HEAD>
<body  onload="bodyLoad()">
<form action="${pageContext.request.contextPath}/monitor_toGetAllMonitor.action" method="post">
<table>
<tr style="font:12px">
<td>开始时间</td>
<td><input style="height: 20px; width: 160px; font-size: 12px;"class="Wdate" type="text" runat="server" name="startdate"value="${startdate}"
										size="18"onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
</td>
<td>结束时间</td>
<td><input style="height: 20px; width: 160px; font-size: 12px;"class="Wdate" type="text" runat="server" name="enddate"value="${enddate}"
										size="18"onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
</td>
<td> <input type="submit" name="tijiao" value="查询"></td>
</tr>
</table>
  
<table width="100%">
<tr>
<td colspan="4"><div align="center"><font size="3"><strong>总体数据库连通性监控</strong></font></div>
</td>
</tr>
</table>
 <div id="alarm_chart" ></div>
 </form> 
</body>	
</HTML>
