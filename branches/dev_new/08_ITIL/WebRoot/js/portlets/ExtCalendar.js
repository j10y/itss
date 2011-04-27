
/**
 * 日历控件
 */
Ext.namespace("My");

My.ExtCalendar = Ext.extend(Ext.DatePicker, {
 todayText         : "今天",
 minText           : "最小日期之前",
 maxText           : "最大日期之后",
 disabledDaysText  : "",
 disabledDatesText : "",
 monthNames        : Date.monthNames,
 dayNames          : Date.dayNames,
 nextText          : '下月 (Control+Right)',
 prevText          : '上月 (Control+Left)',
 monthYearText     : '选择月 (Control+Up/Down)',
 todayTip          : "{0}",
 format            : "y年m月d日",
 okText            : "确定",
 cancelText        : "取消",
 constrainToViewport:false,

 
 initComponent : function(){
        My.ExtCalendar.superclass.initComponent.call(this);
        this.addEvents(
            /**//**
             * @event markupselected
             * Fires when the markup cell is selected
             * @param {ExtCalendar} this
             * @param {Cfg} the current markup setting which user seeted
             */
            'markupselected',
            /**//**
             * @event beforechange
             * Fires before the select date is changed 
             * @param {ExtCalendar} this
             * @param {date} the new date
             * @param {date} the old date
             */            
            'beforechange'
        );        
 },
 
 // private
 update:function(date){
  if(!date){
   date=new Date();
  }
  var oldDate=(this.activeDate || this.value);
  if(date.getTime()!=oldDate.getTime()){
   this.fireEvent("beforechange", this, date, oldDate);
  }
  My.ExtCalendar.superclass.update.call(this,date);
  if(this.markups){
   this.onMarkup();
   var cfg=this.getMarkupDateConfig(date);
   if(cfg){
    this.fireEvent("markupselected", this, cfg);
   }
  }  
 },
 
 markupTPL:new Ext.Template(
        '<span style="color:blue" title="{tip}">{day}</span>'
 ), 
 /**//**
  * @param {markups} the array of object{date,tip}
  * @param {handler} the function on the markup cell clicked, the callback param is (cell_date)
  */
 setMarkup:function(markups,handler){
  this.markups=markups;
  this.markupHandler=handler;
  this.onMarkup();
 },
 onMarkup:function(){
  var textNodes=this.textNodes;
  for(var i=0;i<this.markups.length;i++){
   var m=this.markups[i];
   var span=this.getDateCellSpan(m.date);
   if(span){
    var day=span.innerHTML;
    span.innerHTML='';
    this.markupTPL.append(Ext.get(span),{tip:m.tip,day:day});    
   }
  }
 },
 

 /**//**
  * @param date  String:'yyyy-mm-dd' or Date object
  */
 // private
 getDateCellSpan:function(date){
  var d;
  if(typeof date =='string'){
   d=this.getMyDateFormat(date).getTime();
  }else{
   d=date.getTime();
  }
  
  for(var i=0;i<this.textNodes.length;i++){
   var cd=this.getCellDateTime(this.textNodes[i]);
   if(cd==d){
    return this.textNodes[i];
   }
  }
  return null;
 },
 
 // private
 getCellDateTime:function(span){
  var dd=(this.activeDate || this.value).clearTime(true);
  var tdEL=Ext.get(span).parent("td");
  if(tdEL.hasClass('x-date-prevday')){
   dd=dd.add("mo", -1);   
  }else if(tdEL.hasClass('x-date-active')){
  }else if(tdEL.hasClass('x-date-nextday')){
   dd=dd.add("mo", 1);
  }
  dd.setDate(span.innerHTML);
  return dd.getTime();
 },
 
 /**//**
  * @param date  String:'yyyy-mm-dd'
  * @return Date
  */
 // private
 getMyDateFormat:function(date){
  return Date.parseDate(date, "yyyy-mm-dd")
 },
 
 /**//**
  * 返回date时间的用户markup设置信息
  */
 getMarkupDateConfig:function(date){
  var dd=(date||(this.activeDate || this.value)).getTime();
  for(var i=0;i<this.markups.length;i++){
   var m=this.markups[i];
   var mdt=this.getMyDateFormat(m.date).getTime();
   if(mdt==dd){
    return m;
   }
  }
  return null;
 }
});
