$(function(){
 $(".albumshow dl dt>a:first").addClass("tabActive");
 $(".albumshow dl dd ul").not(":first").hide();
 $(".albumshow dl dt>a").unbind("click").bind("click", function(){
  $(this).siblings("a").removeClass("tabActive").end().addClass("tabActive");
  var index = $(".albumshow dl dt>a").index( $(this) );
  $(".albumshow dl dd ul").eq(index).siblings(".albumshow dl dd ul").hide().end().fadeIn("slow");
   });
});
$(document).ready(function(){
 $('.albumshow dl dt a:first').addClass('tabActive');
 $('.albumshow dl dd ul:first').css('display','block');
 autoroll();
 hookThumb();
});
var i=-1;
var offset = 3000;
var timer = null;
function autoroll(){
 n = $('.albumshow dl dt a').length-1;
 i++;
 if(i > n){
 i = 0;
 }
 slide(i);
    timer = window.setTimeout(autoroll, offset);
 }
function slide(i){
 $('.albumshow dl dt a').eq(i).addClass('tabActive').siblings().removeClass('tabActive');
 $('.albumshow dl dd ul').eq(i).fadeIn("slow").siblings('.albumshow dl dd ul').hide();
 }
function hookThumb(){    
 $('.albumshow dl dt a').hover(
  function () {
    if (timer) {
                clearTimeout(timer);
    i = $(this).prevAll().length;
             slide(i); 
            }
  },
  function () {
            timer = window.setTimeout(autoroll, offset);  
            this.blur();            
            return false;
  }
); 
}