package com.digitalchina.info.appframework.pagemodel.servlet;

import java.util.Random;

import com.digitalchina.info.appframework.pagemodel.servlet.Component;

public class ComponentCoder {
	public static String makeTextField(Component c) {
		String s = "new Ext.form.TextField({";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "xtype:\"textfield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "style:\"\",";//必加此项
		s += "width:\""+getWidth(c)+"\",";
		//s += "disabled:true,";
		s += "value:\""+blank(c.getValue())+"\",";
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "validator:"+c.getValidator()+","; //无引号
		s += "vtype:\"\"";		
		s += "})";
		return s;
	}
	public static String makeNumberField(Component c) {
		String s = "new Ext.form.NumberField({";
		s += "xtype:\"numberfield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\","; 
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "validator:"+c.getValidator()+","; //无引号
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	public static String makeHidden(Component c) {
		String s = "new Ext.form.Hidden({";
		s += "xtype:\"hidden\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "style:\"\",";//必加此项
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "value:\""+blank(c.getValue())+"\","; 
		//s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		//s += "validator:"+c.getValidator()+","; //无引号
		s += "fieldLabel:\""+c.getLabel()+"\"";
		//s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	public static String makeTextArea(Component c) {
		String s = "new Ext.form.TextArea({";
		s += "xtype:\"textarea\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\",";  
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "validator:"+c.getValidator()+","; //无引号
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		//System.out.println(s+"wenbenyu");
		return s;
	}
	//todo
	public static String makeRadio(Component c) {
		String s = "new Ext.form.Radio({";
		s += "xtype:\"radio\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\","; 
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	private static String getWidth(Component c) {
		String width="";
		if(c.getWidth()==null||"".equals(c.getWidth())||"null".equals(c.getWidth())){
			width="";
		}else{
			width=c.getWidth();
		}
		return width;
	}
	//todo
	public static String makeMultiSelect(Component c) {
		
		String s = "{";
		s += "xtype:\"combo\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "style:\"\",";//必加此项
		s += "id:\""+c.getName()+"\",";
		//s += "value:\""+blank(c.getValue())+"\",";  
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "}";
		return s;
	}
	//显示和数据支持存储
	public static String makeFile(Component c) {
		String s = "{";
//		s += "xtype: \"textfield\",";
		//s += "html:\"[ "+c.getLink()+" ]\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "},";
		s += "new Ext.form.Hidden({";
		s += "xtype:\"hidden\",";
		s += "id:\""+c.getName()+"\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\""; 
		s += "})";
		return s;
	}
	public static String makeDateText(Component c) {
		String s = "new Ext.form.DateField({";
		s += "xtype:\"datefield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\",";  
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "validator:"+c.getValidator()+","; //无引号
		s += "format:\"Y-m-d\",";
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	
	public static String makeGridDateText(Component c) {
		String s = "new Ext.form.DateField({";
		s += "xtype:\"datefield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\",";  
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "validator:"+c.getValidator()+","; //无引号
		s += "format:\"Y-m-d\",";
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	//作废
	public static String makeYesNoSelect(Component c) {
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "id: \""+c.getName()+"ComboId\","; //ComboId居然还不能去掉
		//s += "id:\""+c.getName()+"\",";
		s += "mode: \"local\",";
		s += "triggerAction:\"all\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "forceSelection: true,";
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: [\"id\", \"name\"],";
		s += "data: [[1,\"是\"],[0,\"否\"]],id:\"id\"";
 		s += "}),";
 		s += "emptyText:\"请选择...\",";
 		s += "valueField :\"id\",";
 		s += "displayField: \"name\",";
		s += "name:\""+c.getName()+"\",";
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "style:\"\",";//必加此项
		s += "width:\""+getWidth(c)+"\",";
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	public static String makeSelect(Component c) {
		String data = "";
		String value = "";
		String dispValue = "";
		String[][] values = (String[][])c.getValue();
		if(values==null) {
			data = "[[]]";
		}
		else {
			for(int i=0;i<values.length;i++) {
				data += "[\""+values[i][0]+"\",\""+values[i][1]+"\"],";
				if(values[i][2]!=null&&values[i][2].equalsIgnoreCase("selected")) {
					value = values[i][0];//被选中的项
					dispValue = values[i][1];
				}
			}
			if(data.endsWith(",")) {
				data = data.substring(0,data.length()-1);
			}
			data = "["+data+"]";
		}
		//Add by DJ
		//判断列表长度,如果超过10的话就用query,少于15则用all
		String triggerAction="query";
		if(values.length<16){
			triggerAction="all";
		}
		
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "id: Ext.id(),";
		s += "style:\"\",";//必加此项
		s += "mode: \"local\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "typeAhead: true,";
	//	s += "triggerAction:\"all\","; //add by peixf$$$$$$$$$$$$$$$
		s += "forceSelection: true,";
		s += "allowBlank: "+c.getAllowBlank()+",";
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: [\"id\", \"name\"],";
		s += "data: "+data;
 		s += "}),";
 		s += "emptyText:\"请选择...\",";
 		s += "valueField :\"id\",";
 		s += "value :\""+value+"\",";
 		s += "dispValue:\""+dispValue+"\",";
 		s += "displayField: \"name\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "triggerAction:\""+triggerAction+"\",";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "listeners : {";
		s += "\"expand\" : function(combo) {combo.reset();}";
		s += "}";
		s += "})";
		//System.out.println(s+"下拉列表");
		return s;
	}
	
	public static String makeGridSelect(Component c) {
		String data = "";
		String value = "";
		String dispValue = "";
		String[][] values = (String[][])c.getValue();
		if(values==null) {
			data = "[[]]";
		}
		else {
			for(int i=0;i<values.length;i++) {
				data += "[\""+values[i][0]+"\",\""+values[i][1]+"\"],";
				if(values[i][2]!=null&&values[i][2].equalsIgnoreCase("selected")) {
					value = values[i][0];//被选中的项
					dispValue = values[i][1];
				}
			}
			if(data.endsWith(",")) {
				data = data.substring(0,data.length()-1);
			}
			data = "["+data+"]";
		}
		//Add by DJ
		//判断列表长度,如果超过10的话就用query,少于15则用all
		String triggerAction="query";
		if(values.length<16){
			triggerAction="all";
		}
		
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "id: \""+c.getName()+"ComboId\",";
		s += "style:\"\",";//必加此项
		s += "mode: \"local\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank: "+c.getAllowBlank()+",";
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: [\"id\", \"name\"],";
		s += "data: "+data;
 		s += "}),";
 		s += "emptyText:\"请选择...\",";
 		s += "valueField :\"id\",";
 		s += "value :\""+value+"\",";
 		s += "dispValue:\""+dispValue+"\",";
 		s += "displayField: \"name\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+getWidth(c)+"\",";
		s += "triggerAction:\""+triggerAction+"\",";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "listeners : {";
		s += "\"expand\" : function(combo) {combo.reset();}";
		s += "}";
		s += "})";
		return s;
	}
	
	/**
	 * 组装修改时的Combo
	 * @Methods Name makeComboForEdit
	 * @Create In Aug 18, 2010 By duxh
	 * @param c
	 * @Return String
	 */
	public static String makeComboForEdit(Component c) {
		StringBuilder sb=new StringBuilder();
		sb.append("new Ext.form.ComboBox({");
		sb.append("hiddenName: \"");sb.append(c.getName());sb.append("\",");
		sb.append("id :\"");sb.append(c.getName());sb.append("Combo"+"\",");
		sb.append("width:\"");sb.append(c.getWidth());sb.append("\",");
		sb.append("fieldLabel:\"");sb.append(c.getLabel());sb.append("\",");
		sb.append("lazyRender: true,");
		sb.append("displayField: \"");sb.append(c.getDisplayFiled());sb.append("\",");
		sb.append("valueField :\"id\",");
		sb.append("forceSelection: true,");
		sb.append("emptyText:\"请选择...\",");
		sb.append("allowBlank:");sb.append(c.getAllowBlank());sb.append(",");
		sb.append("name:\"");sb.append(c.getName());sb.append("\",");
		sb.append("value:\"");sb.append(blank(c.getValue()));sb.append("\",");
		sb.append("triggerAction:\"all\",");
		sb.append("queryDelay : 700,");
		sb.append("store:new Ext.data.JsonStore({url:webContext+\"/extjs/comboDataAction?clazz=");sb.append(c.getRelationship());sb.append("\",");
		sb.append("fields:[\"id\",\"");sb.append(c.getDisplayFiled());sb.append("\"],");
		sb.append("totalProperty:\"rowCount\",root:\"data\"})");
		sb.append( ",pageSize:10");
		sb.append(",listeners:{"); 
		sb.append( "\"beforequery\" : function(queryEvent)");
		sb.append("{var store=queryEvent.combo.store;");
		sb.append("store.baseParams.");sb.append(c.getDisplayFiled());sb.append("=queryEvent.combo.getRawValue();");
		sb.append("store.baseParams.start=0;");
		sb.append("store.load();return false;}}");
		sb.append( ",initComponent : function() {");
		sb.append("if(this.getValue()!=''){");
		sb.append("var combo=this;");
		sb.append("this.store.load({params : {id : combo.getValue(),start : 0},");
		sb.append("callback : function(r, options, success) {");
		sb.append("if(r.length>0){combo.setRawValue(r[0].get(combo.displayField));}}});}}})");
		return sb.toString();
	}
	
	/**
	 * 组装新建时的Combo
	 * @Methods Name makeComboForAdd
	 * @Create In Aug 18, 2010 By duxh
	 * @param c
	 * @Return String
	 */
	public static String makeComboForAdd(Component c) {
		StringBuilder sb=new StringBuilder();
		sb.append("new Ext.form.ComboBox({");
		sb.append("hiddenName: \"");sb.append(c.getName());sb.append("\",");
		sb.append("id :\"");sb.append(c.getName());sb.append("Combo"+"\",");
		sb.append("width:\"");sb.append(c.getWidth());sb.append("\",");
		sb.append("fieldLabel:\"");sb.append(c.getLabel());sb.append("\",");
		sb.append("lazyRender: true,");
		sb.append("displayField: \"");sb.append(c.getDisplayFiled());sb.append("\",");
		sb.append("valueField :\"id\",");
		sb.append("forceSelection: true,");
		sb.append("emptyText:\"请选择...\",");
		sb.append("allowBlank:");sb.append(c.getAllowBlank());sb.append(",");
		sb.append("name:\"");sb.append(c.getName());sb.append("\",");
		sb.append("triggerAction:\"all\",");
		sb.append("queryDelay : 700,");
		sb.append("store:new Ext.data.JsonStore({url:webContext+\"/extjs/comboDataAction?clazz=");sb.append(c.getRelationship());sb.append("\",");
		sb.append("fields:[\"id\",\"");sb.append(c.getDisplayFiled());sb.append("\"],");
		sb.append("totalProperty:\"rowCount\",root:\"data\"})");
		sb.append( ",pageSize:10");
		sb.append(",listeners:{"); 
		sb.append( "\"beforequery\" : function(queryEvent)");
		sb.append("{var store=queryEvent.combo.store;");
		sb.append("store.baseParams.");sb.append(c.getDisplayFiled());sb.append("=queryEvent.combo.getRawValue();");
		sb.append("store.baseParams.start=0;");
		sb.append("store.load();return false;}}");
		sb.append( ",initComponent : function() {");
		sb.append("if(this.getValue()!=''){");
		sb.append("var combo=this;");
		sb.append("this.store.load({params : {id : combo.getValue(),start : 0},");
		sb.append("callback : function(r, options, success) {");
		sb.append("if(r.length>0){combo.setRawValue(r[0].get(combo.displayField));}}});}}})");
		return sb.toString();
	}
	
	public static String makeExtSelect(Component c) {
		String id  = c.getName()+"Combo" + new Random().nextInt(10000);
		String s = "{";
		s += "xtype:\"combo\",";
		s += "id:\""+id+"\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "hiddenName: \""+c.getName()+"\",";
		s += "defaultParam : \"\",";
		s += "id :\""+id+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "displayField: \""+c.getDisplayFiled()+"\",";
		s += "valueField :\"id\",";
		s += "emptyText:\"请选择...\",";
//		s += "typeAhead:true,";
		s += "name:\""+c.getName()+"\",";
		s += "triggerAction:\"all\",";
		s += "minChars :50,";
//		s += "queryDelay : 700,";
		s += "validator:function(){if(Ext.getCmp(\""+id+"\").store.getCount()==0&&Ext.getCmp(\""+id+"\").getRawValue()!=\"\"){return false;}return true;},";
		s += "store:new Ext.data.JsonStore({url:webContext+\"/extjs/comboDataAction?clazz="+c.getRelationship()+"\",";
			s += "fields:[\"id\",\""+c.getDisplayFiled()+"\"],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params[\""+c.getName()+"\"] == undefined){";
			s+="opt.params[\""+c.getDisplayFiled()+"\"] =Ext.getCmp(\""+id+"\").defaultParam;";
			s+="}}},";
			s += "totalProperty:\"rowCount\",root:\"data\",id:\"id\",";
			s += "listeners:{beforeload:function(store,opt){if(opt.params[\""+c.getDisplayFiled()+"\"]==undefined){opt.params[\""+c.getDisplayFiled()+"\"]=Ext.getCmp(\""+id+"\").defaultParam;}}}";
			s += "})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "\"beforequery\" : function(queryEvent)";
			s += "{var param = queryEvent.query;this.defaultParam = param;";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0},callback:function(r, options, success){Ext.getCmp(\""+id+"\").validate();}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:\""+blank(c.getValue())+"\",start:0},callback:function(r, options, success){Ext.getCmp(\""+id+"\").setValue(\""+blank(c.getValue())+"\");}});}";
		s += "}";
		return s;
	}

	public static String makeLazySelect(Component c) {
		String id  = c.getName()+"Combo";
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "id :\""+id+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
		s += "feildLabel:\""+c.getLabel()+"\",";
		s += "lazyRender: true,";
		s += "displayField: \""+c.getDisplayFiled()+"\",";
		s += "valueField :\"id\",";
		s += "emptyText:\"请选择...\",";
		s += "typeAhead:true,";
		s += "name:\""+c.getName()+"\",";
		s += "triggerAction:\"all\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "minChars :50,";
//		s += "queryDelay : 700,";
		s += "store:new Ext.data.JsonStore({url:webContext+\"/extjs/comboDataAction?clazz="+c.getRelationship()+"\",";
			s += "fields:[\"id\",\""+c.getDisplayFiled()+"\"],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params[\""+c.getName()+"\"] == undefined){";
			s+="opt.params[\""+c.getDisplayFiled()+"\"] =Ext.getCmp(\""+id+"\").defaultParam;";
			s+="}}},";
			s += "totalProperty:\"rowCount\",root:\"data\",id:\"id\"})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "\"beforequery\" : function(queryEvent)";
			s += "{var param = queryEvent.combo.getRawValue();this.defaultParam = param;";
			s += "if(queryEvent.query==\"\"){param=\"\";}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:\""+blank(c.getValue())+"\",start:0},callback:function(r, options, success){Ext.getCmp(\""+id+"\").setValue(\""+blank(c.getValue())+"\");}});}";
		s += "})";
		return s;
	}

	
	//下载控件
	public static String makeDownLoad(Component c) {
		String s = "";
//		return "<a href=\""+webContext+\"/upload/\"+value+"\" target=\"_blank\">下载</a>";
		return s;
	}
	public static String makeHtml(Component c) {
		String s = "{";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "html :\"<font color=blouse>"+blank(c.getValue())+"</font>\",";
		s += "cls : \"common-text\",";
		s += "width:135,";
		s += "style : \"width:150;text-align:right\","; 
		//s += "xtype:\"textfield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "style:\"\",";//必加此项
		s += "width:\""+c.getWidth()+"\",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		//s += "value:\""+blank(c.getValue())+"\","; 
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "validator:"+c.getValidator()+","; //无引号
		s += "vtype:\"\"";		
		s += "}";
		return s;
	}
	
	//////////////short style
	public static String makeTextField(String name,String label,String width) {
		return makeTextField(new Component(name,label,width));
	}
	public static String makeHidden(String name,String label,String width) {
		return makeHidden(new Component(name,label,width));
	}
	public static String makeTextArea(String name,String label,String width) {
		return makeTextArea(new Component(name,label,width));
	}
	public static String makeRadio(String name,String label,String width) {
		return makeRadio(new Component(name,label,width));
	}
	public static String makeMultiSelect(String name,String label,String width) {
		return makeMultiSelect(new Component(name,label,width));
	}
	public static String makeFile(String name,String label,String width) {
		return makeFile(new Component(name,label,width));
	}
	public static String makeDateText(String name,String label,String width) {
		return makeDateText(new Component(name,label,width));
	}
	public static String makeYesNoSelect(String name,String label,String width) {
		return makeYesNoSelect(new Component(name,label,width));
	}
	public static String makeSelect(String name,String label,String width) {
		return makeSelect(new Component(name,label,width));
	}
	

	private static Object blank(Object s) {
		//return s==null?"":s.toString();
		return (s==null?"":s);
	}
}
