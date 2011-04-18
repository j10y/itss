package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.util.Random;

import com.digitalchina.info.appframework.pagemodel.autocreat.servlet.Component;

public class ButtonGather {
	public static String createSaveForModel(ButtonParameter b) {
		String result = "";
		
		result = "new Ext.Button({";
		result += "text : btnName,";
		result += "pressed : true, ";
		result += "iconCls : 'save', ";
		result += "scope : scope, ";
		result += "handler : function() { ";
//		result += "	if (link == "") ";
//		result += "	alert("未指定添加跳转链接页面！"); ";
		result += "var formParam = \"\"; ";
		result += "var gridParam = \"\"; ";
		result += "var param = '{';";
		result += "if (this.pa.length != \"0\") { ";
		result += "	for (var i = 0; i < this.pa.length; i++) { ";
		result += "		var fP = Ext.encode(this.pa[i].form.getValues(false)); ";
		result += "		formParam += '\"' + this.formname[i] + '\"' + ':[' + fP ";
		result += "				+ '],'; ";
		result += "} ";
		result += "param += formParam; ";
		result += "} ";
		result += "if (this.gd.length != \"0\") { ";
		result += "	for (var k = 0; k < this.gd.length; k++) { ";
		result += "		var gParam = \"\"; ";
		result += "		var gP = this.gd[k].getStore().getRange(0, ";
		result += "				this.gd[k].getStore().getCount()); ";
		result += "		for (i = 0; i < gP.length; i++) { ";
		result += "			gParam += Ext.encode(gP[i].data) + \",\"; ";
		result += "		} ";
		result += "		gParam = gParam.slice(0, gParam.length - 1); ";
		result += "		gridParam += '\"' + this.gridname[k] + '\"' + ':[' ";
		result += "				+ gParam + '],'; ";
		result += "	} ";
		result += "	param += gridParam; ";
		result += "} ";
		result += "param = param.slice(0, param.length - 1) + '}'; ";
		result += "Ext.Ajax.request({ ";
		result += "	url : webContext + '/extjs/pageData?method=save', ";
		result += "	params : { ";
		result += "		info : param, ";
		result += "		model : this.model ";
		result += "	}, ";
		result += "	success : function(response, options) { ";
		result += "		if (link == null || link == \"null\" || link == \"\") { ";
		result += "			window.location = window.location.href.toString(); ";
		result += "		} else { ";
		result += "			window.location = webContext + link; ";
		result += "		} ";
		result += "	}, ";
		result += "	failure : function(response, options) { ";
		result += "		Ext.MessageBox.alert(\"保存失败\"); ";
		result += "	} ";
		result += "}, this); ";
		result += "} ";
		result += "}); ";
		
		
		return result;
	}
	
	/**
	 * 跳转到其他页面，比如新建
	 * @Methods Name jumpPage
	 * @Create In May 12, 2009 By sujs
	 * @param b
	 * @return String
	 */
	public static String jumpPage(ButtonParameter b) {
     String buttonString="new Ext.Button({";
        buttonString += "text:'"+b.getButtonName()+"',";
        buttonString += "pressed:true,";
        buttonString += "iconCls:'"+b.getImageUrl()+"',";
        buttonString += "scope:this,";
        buttonString += "handler:function(){";
        buttonString += "window.location = webContext+'"+b.getButtonLink()+"';";
        buttonString += "}})";
		return buttonString;
	}
	/**
	 * 新增window窗口主要用来引入另外一个jsp
	 * @Methods Name careatWindow
	 * @Create In May 12, 2009 By sujs
	 * @param b
	 * @return String
	 */
	public static String careatWindow(ButtonParameter b) {
	 String buttonString="new Ext.Button({";
	        buttonString += "text:'"+b.getButtonName()+"',";
	        buttonString += "pressed:true,";
	        buttonString += "iconCls:'"+b.getImageUrl()+"',";
	        buttonString += "scope:this,";
	        buttonString += "handler:function(){";
	        //这里构建一个windows窗口+++++++++++++++++++++++begin++++++++++++++++++
		        buttonString +="this.addWin = new Ext.Window({";
		        buttonString += "id:'contarinerWindow',";
		        buttonString += "title:'添加',";
		        buttonString += "scope:this,";
		        buttonString += "height:500,";
		        buttonString += "width:800,";
		        buttonString += "modal:true,";
		        buttonString += "resizable:false,";
		        buttonString += "draggable:true,";
		        buttonString += "autoLoad:{";
		        buttonString += "url:webContext+'/tabFrame.jsp?url='+webContext+";
		        buttonString += "'"+b.getButtonLink()+"****parentId='+this.dataId+";
		        buttonString += "'****parentTabId="+b.getContainer()+"',";
		        buttonString += "text:'页面加载中......',";
		        buttonString += "method:'post'";
		        buttonString += "scripts:true,";
		        buttonString += "scope:this},";
		        buttonString += "viewConfig:{autoFill:true,forceFit : true},";
		        buttonString += "layout:'fit',";
		        buttonString += "items : [{html :'正在加载页面数据......'}]";
		        buttonString += "});";
		        buttonString += "this.addWin.setPagePosition(400, 100);";
		        buttonString += "this.addWin.show();";
		        buttonString += "this.addWin.on('deactivate',function(){";
		        buttonString += "window.location = window.location.href.toString()";
		        buttonString += "+ '&tabId=' +"+b.getContainer()+";";
		        buttonString += "},this);";
	        //这里构建一个windows窗口+++++++++++++++++++++++end++++++++++++++++++++
	        buttonString += "}})";
			return buttonString;
		}
	/**
	 * 查询按钮
	 * @Methods Name query
	 * @Create In May 12, 2009 By sujs
	 * @param b
	 * @return String
	 */
	public static String query(ButtonParameter b) {
	    String buttonString="new Ext.Button({";
        buttonString += "text:'"+b.getButtonName()+"',";
        buttonString += "pressed:true,";
        buttonString += "iconCls:'search',";
        buttonString += "scope:this,";
        buttonString += "handler:function(){";
        buttonString += "var param = this.panel.form.getValues(false);";
        buttonString += "param.methodCall = 'query';";
        buttonString += "param.start = 1;";
        buttonString += "this.formValue = param;";
        buttonString += "this.pageBar.formValue = this.formValue;";
        buttonString += "this.store.removeAll();";
        buttonString += "this.store.load({params : param});";
        buttonString += "}})";
		return buttonString;
	}
	/**
	 * 重置
	 * @Methods Name reset
	 * @Create In May 12, 2009 By sujs
	 * @param b
	 * @return String
	 */
	public static String reset(ButtonParameter b) {
	    String buttonString="new Ext.Button({";
        buttonString += "text:'"+b.getButtonName()+"',";
        buttonString += "pressed:true,";
        buttonString += "iconCls:'refresh',";
        buttonString += "scope:this,";
        buttonString += "handler:function(){";
        buttonString += "this.panel.form.reset();";
        buttonString += "}})";
		return buttonString;
	}
	/**
	 * 重置面板数据
	 * @Methods Name reset
	 * @Create In May 12, 2009 By sujs
	 * @param b
	 * @return String
	 */
	public static String resetPanel(ButtonParameter b) {
	    String buttonString="new Ext.Button({";
        buttonString += "text:'"+b.getButtonName()+"',";
        buttonString += "pressed:true,";
        buttonString += "iconCls:'refresh',";
        buttonString += "scope:this,";
        buttonString += "handler:function(){";
        buttonString += "Ext.getCmp('"+b.getContainer()+"').form.reset();";
        buttonString += "}})";
		return buttonString;
	}
	/**
	 * 修改数据
	 * @Methods Name modify
	 * @Create In May 12, 2009 By sujs
	 * @param b
	 * @return String
	 */
	public static String modify(ButtonParameter b) {
	    String buttonString="new Ext.Button({";
        buttonString += "text:'"+b.getButtonName()+"',";
        buttonString += "pressed:true,";
        buttonString += "iconCls:'edit',";
        buttonString += "scope:this,";
        buttonString += "handler:function(){";
     //   buttonString += "if (link =='')alert('未指定添加跳转链接页面！');";
        buttonString += "var record = this.grid.getSelectionModel().getSelected();";
        buttonString += "var records = this.grid.getSelectionModel().getSelections();";
        buttonString += "if(!record){";
        buttonString += "Ext.Msg.alert('提示', '请先选择要修改的行!');";
        buttonString += "return;}";
        buttonString += "if(records.length > 1){";
        buttonString += "Ext.Msg.alert('提示', '修改时只能选择一行!');";
        buttonString += "return;}";
        buttonString += "var modelTableId ='"+b.getButtonMainTable()+"$id';";
        buttonString += "var dataId = record.get(modelTableId);";
        buttonString += "window.location = webContext + '"+b.getButtonLink()+"' + dataId;";
        buttonString += "}})";
		return buttonString;
	}
	/**
	 * 返回
	 * @Methods Name callBack
	 * @Create In May 12, 2009 By sujs
	 * @param b
	 * @return String
	 */
	public static String callBack(ButtonParameter b) {
	    String buttonString="new Ext.Button({";
        buttonString += "text:'"+b.getButtonName()+"',";
        buttonString += "pressed:true,";
        buttonString += "iconCls:'back',";
        buttonString += "scope:this,";
        buttonString += "handler:function(){";
        buttonString += "window.location.href = webContext + '"+b.getButtonLink()+"';";
        buttonString += "}})";
		return buttonString;
	}
}
