Ext.ux.Printer.BaseRenderer=Ext.extend(Object,{print:function(b){var a=b&&b.getXType?String.format("print_{0}_{1}",b.getXType(),b.id):"print";var c=window.open("",a);c.document.write(this.generateHTML(b));c.document.close();c.print();c.close();},generateHTML:function(a){return new Ext.XTemplate('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">',"<html>","<head>",'<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />','<link href="'+this.stylesheetPath+'" rel="stylesheet" type="text/css" media="screen,print" />',"<title>"+this.getTitle(a)+"</title>","</head>","<body>",this.generateBody(a),"</body>","</html>").apply(this.prepareData(a));},generateBody:Ext.emptyFn,prepareData:function(a){return a;},getTitle:function(a){return typeof a.getTitle=="function"?a.getTitle():(a.title||"Printing");},stylesheetPath:__ctxPath+"/css/print.css"});