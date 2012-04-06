<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
		<TITLE></TITLE>	
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
		<script src="${pageContext.request.contextPath}/FusionChartsFree/js/FusionChartsW.js" type="text/javascript"></script>
		<script type="text/javascript">
	     $(function(){
	      setTimeout(function(){window.location.reload();}, 300000);
	     })
	     function getWebInfoById(id){
	     	var url="${pageContext.request.contextPath}/monitor_getWebStatusTimer.action";
			var params={webid:id};
			$.post(
			url, //服务器要接受的url
			params, //传递的参数 
			function(json){
				var dataObj=eval("("+json+")");//
				var total=dataObj['total'];	
				var xmlData="";
				for(var i=0;i<total;i++){
				if(dataObj['data'][i]['status']==-1){
					$("#des_"+id).html("<font color='#ff0000'>"+dataObj['data'][i]['descsp']+"</font>");
				}else{
					$("#des_"+id).html(dataObj['data'][i]['descsp']);
				}
				//alert(dataObj['data'][i]['status']);
				xmlData+="<Chart bgColor='AEC0CA,FFFFFF' fillAngle='45' upperLimit='2' lowerLimit='-2' majorTMNumber='10' majorTMHeight='8' showGaugeBorder='0' gaugeOuterRadius='70' gaugeOriginX='105' gaugeOriginY='156' gaugeInnerRadius='2' formatNumberScale='1' numberPrefix='' displayValueDistance='30' decimalPrecision='2' tickMarkDecimalPrecision='1' pivotRadius='9' showPivotBorder='1' pivotBorderColor='000000' pivotBorderThickness='5' pivotFillMix='FFFFFF,000000'>";
				xmlData+="<colorRange>";
				xmlData+="<color minValue='-2' maxValue='0' code='E48739'/>"; 
				xmlData+="<color minValue='0' maxValue='-2' code='399E38'/>";
				xmlData+="</colorRange>";
				xmlData+="<dials>";
				xmlData+="<dial value='"+dataObj['data'][i]['status']+"' borderAlpha='0' bgColor='000000' baseWidth='7' topWidth='1' radius='65'/>";
				xmlData+="</dials>";
				xmlData+="<annotations>";
				xmlData+="<annotationGroup xPos='105' yPos='157.5'>";
				xmlData+="<annotation type='circle' xPos='0' yPos='2.5' radius='75' startAngle='0' endAngle='180' fillPattern='linear' fillAsGradient='1' fillColor='dddddd,666666' fillAlpha='100,100'  fillRatio='50,50' fillDegree='0' showBorder='1' borderColor='444444' borderThickness='2'/>";
				xmlData+="<annotation type='circle' xPos='0' yPos='0' radius='73' startAngle='0' endAngle='180' fillPattern='linear' fillAsGradient='1' fillColor='666666,ffffff' fillAlpha='100,100'  fillRatio='50,50' fillDegree='0' />";
				xmlData+="</annotationGroup>";
				xmlData+="</annotations>";
				xmlData+="</Chart>";
				}
				var myChart = new FusionCharts("${pageContext.request.contextPath}/FusionChartsFree/swf/AngularGauge.swf", "myChartId", "200", "180", "0", "0");
					myChart.setDataXML(xmlData);//.setDataURL("Data/Angular2.xml");
					//alert(xmlData);
					myChart.render("web_"+id);
				
			})
	     }
		</script>
	</HEAD>
<body style="width:960px">
<c:set var="num" value="0"></c:set>
<table border=1>
<c:forEach var="webinfo" items="${weblist}" >
<c:if test="${num==0}">
<tr>
</c:if>
<c:if test="${num%5==0}">
</tr>
<tr>
</c:if>
<td>
<div style="width:200px;height:15px;font-size:12px" align="center">
${webinfo.webName}
</div>
<div id="des_${webinfo.id}"style="width:200px;height:25px;font-size:10px" align="center">
</div>
<div id="web_${webinfo.id}" style="width:200px;height:180px">
<script type="text/javascript">getWebInfoById(${webinfo.id})</script>
</div></td>
<c:set var="num" value="${num+1}"></c:set>
</c:forEach>
</tr>
</table>
</body>	
</HTML>
