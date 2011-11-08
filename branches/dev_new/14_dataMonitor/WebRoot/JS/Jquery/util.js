
//关闭父页面打开的div
function closeParentDiv(){
	if(confirm('您确定要关闭吗？')){
		window.parent.closediv();
	}
}

//等待效果
function waiting(){
	//当保存的时候出现遮罩
   	$.blockUI({message: '请稍后...' , 
						  overlayCSS:{
						  		backgroundColor:'#eee'
						  },
						  css: {
						  		border:'none',
						  		padding:'15px', 
						  		backgroundColor:'#000', 
						  		'-webkit-border-radius':'10px',
						  		'-moz-border-radius':'10px',
						  		opacity:.5,
						  		color: '#fff'}
						 });
}
//关闭等待效果
function closeWait(){
	$.unblockUI();
}
//比较两个时间
 function isStartEndDate(startDate,endDate){   
     if(startDate.length>0&&endDate.length>0){   
      var startDateTemp = startDate.split(" ");   
      var endDateTemp = endDate.split(" ");  
      var arrStartDate = startDateTemp[0].split("-");   
      var arrEndDate = endDateTemp[0].split("-");
      var arrStartTime = startDateTemp[1].split(":");   
      var arrEndTime = endDateTemp[1].split(":");  
      var allStartDate = new Date(arrStartDate[0],arrStartDate[1],arrStartDate[2],arrStartTime[0],arrStartTime[1],arrStartTime[2]);   
      var allEndDate = new Date(arrEndDate[0],arrEndDate[1],arrEndDate[2],arrEndTime[0],arrEndTime[1],arrEndTime[2]);   
         
      if(allStartDate.getTime()>allEndDate.getTime()){   
       return false;   
      }   
     }   
     return true;   
    } 