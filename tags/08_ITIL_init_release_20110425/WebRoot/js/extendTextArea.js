ExtendTextArea =  Ext.extend(Ext.form.TextArea, {
    initComponent : function(){
        this.value= replaceBR(this.value);
    }
});
function replaceEntry(str){
var reg=new RegExp("\r\n","g"); 
str = str.replace(reg,"<br>"); 
return str; 
}

function replaceBR(str){
var reg=new RegExp("<br>","g"); 
str = str.replace(reg,"\r\n"); 
return str; 
}