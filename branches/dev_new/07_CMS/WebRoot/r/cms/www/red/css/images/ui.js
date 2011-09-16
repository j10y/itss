$(function(){
	$(".persearch").hover(function(){
		$(this).css("backgroundPosition","-400px -34px")
	},function(){
		$(this).css("backgroundPosition","-400px -58px")
		})
	$(".gsnewst").hover(function(){
		$(this).css({"background-position":"-183px -469px"});
		$(".mcnewst").css({"background-position":"-276px -469px"})
		$("#moregsnews").show();
		$("#moremcnews").hide();
		$("#gsnews").show();
		$("#mcnews").hide();
		})
	$(".mcnewst").hover(function(){
		$(this).css({"background-position":"-276px -500px"});
		$(".gsnewst").css({"background-position":"-183px -500px"})
		$("#moregsnews").hide();
		$("#moremcnews").show();
		$("#gsnews").hide();
		$("#mcnews").show();
		})
	$(".xsrwt1").hover(function(){
		$(this).css({"background-position":"-183px -645px"});
		$(".xsrwt2").css({"background-position":"-302px -645px"})
		$("#morexsrws1").show();
		$("#morexsrws2").hide();
		$("#xsrws1").show();
		$("#xsrws2").hide();
		})
	$(".xsrwt2").hover(function(){
		$(this).css({"background-position":"-302px -676px"});
		$(".xsrwt1").css({"background-position":"-183px -676px"})
		$("#morexsrws1").hide();
		$("#morexsrws2").show();
		$("#xsrws1").hide();
		$("#xsrws2").show();
		})
	//$(".hmenu div ul li a").hover(function(){
//		$(this).siblings("dl").show().parent("li").siblings().children("dl").hide();
//		},function(){
//			$(".hmenu").find(".on").siblings("dl").show().parent("li").siblings().children("dl").hide();
//			})
	$(".hmenu div ul li").hover(function(){
		$(this).children("a").addClass("mactive");
		$(this).children("dl").show().end().siblings().children("dl").hide();
		},function(){
			$(this).children("a").removeClass("mactive");
			})
})
function AutoScroll(obj){
$(obj).find("dd:first").animate({
marginTop:"-30px"
},300,function(){
$(this).css({marginTop:"0px"}).find("p:first").appendTo(this);
});
}
$(document).ready(function(){
setInterval('AutoScroll(".gdnews")',3000)
});
//tab
$(function(){
	var tabTitle = ".gg0908 .inn .title h3";
	var tabContent = ".gg0908 .inn .info ul";
	var tabMore = ".gg0908 .inn .title .more";
	$(tabTitle + ":first a").addClass("on");
	$(tabContent).not(":first").hide();
	$(tabMore).not(":first").hide();
	$(tabTitle).hover(function(){
		$(this).children("a").addClass("on").end().siblings("h3").children("a").removeClass("on");
		var index = $(tabTitle).index( $(this) );
		$(tabContent).eq(index).siblings(tabContent).hide().end().show();
		$(tabMore).eq(index).siblings(tabMore).hide().end().show();
   });
});