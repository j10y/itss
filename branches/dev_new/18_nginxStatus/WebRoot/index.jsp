<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<script type="text/javascript" src="<s:url value="/js/swfobject.js"/>"></script>
		<script type="text/javascript" src="<s:url value="/js/jquery-1.6.1.min.js"/>"></script>
		<script type="text/javascript">
			/**
			swfobject.embedSWF("<s:url value="/open-flash-chart.swf"/>", "my_chart", "1024", "500", "9.0.0", 
			"expressInstall.swf",
			{"data-file":"<s:url value="/json-chart/nginxSecondChart.html"/>"});
			*/
			swfobject.embedSWF("<s:url value="/open-flash-chart.swf"/>", "my_chart", "1024", "500", "9.0.0", "expressInstall.swf",
			{"data-file":"<s:url value="/json-chart/nginxSecondChart.html"/>"},{wmode:"transparent"});
			
		</script>
		
		<script type="text/javascript">
		
	        function ofc_ready() {
	            setInterval('load_1()',1000);
	        }
	        function open_flash_chart_data() {
	            $.ajax({
	                   type: "POST",
	                   url: "<%=request.getContextPath()%>/>/json-chart/nginxSecondChart.html",
	                   success: function(msg){
	                     return JSON.stringify(msg);
	                   },
	                   async: false
	                }); 
	            
	        }
	         
	        function findSWF(movieName) {
	          if (navigator.appName.indexOf("Microsoft")!= -1) {
	            return window[movieName];
	            //return document[movieName];
	          } else {
	            return document[movieName];
	          }
	        }
	        
	        function load_1() {
	          tmp = findSWF("my_chart");
	          $.ajax({
	               type: "POST",
	               url: "<%=request.getContextPath()%>/json-chart/nginxSecondChart.html",
	               success: function(msg){
	                 x = tmp.load( JSON.stringify(msg) );
	                 //$("#my_chart").load(JSON.stringify(msg));
	               },
	               async: false
	                   
	            }); 
	        }
		        
		</script>
		
	</head>
	<body>
		<center>
		<div id="my_chart"></div>
		</center>
	</body>
</html>