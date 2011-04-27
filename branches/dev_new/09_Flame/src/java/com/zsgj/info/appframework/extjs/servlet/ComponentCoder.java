package com.zsgj.info.appframework.extjs.servlet;

import java.util.List;


public class ComponentCoder {

	public static String makeCheckboxGroup(Component c) {
		String s = "new Ext.form.CheckboxGroup({";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "xtype:\"checkboxgroup\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		//s += "style:\"\",";//必加此项
		
		if(c.isSingleLine()){
			s += "width:\"9999\",";
		}
		Integer columnSum = c.getColumns();
		if(columnSum!=null){
			s += "columns: "+columnSum+",";
		}
		List<Component> items = c.getItems();
		s += "items: [";  
		for(Component item: items){
			s += "{boxLabel: \""+item.getLabel()+
				"\", name: \""+item.getName()+
				"\", id : \""+item.getName()+"\"";    
			if(item.getValue()!=null){
				Object value = item.getValue();
				if(value.toString().equalsIgnoreCase("on")){
					s += ", checked: true";
				}
			}
			s += "},";
		}
		if(s.endsWith(",")) {
			s = s.substring(0,s.length()-1);
		}
		s += "]})";  
		return s;
	}
	
	public static String makeRadioGroup(Component c) {
		String s = "new Ext.form.RadioGroup({";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "xtype:\"radiogroup\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		//s += "style:\"\",";//必加此项
		if(c.isSingleLine()){
			s += "width:\"9999\",";
		}
		Integer columnSum = c.getColumns();
		if(columnSum!=null){
			s += "columns: "+columnSum+",";
		}
		List<Component> items = c.getItems();
		s += "items: [";  
		for(Component item: items){
			s += "{boxLabel: \""+item.getLabel()+"\", name: \""+item.getName()+"\", inputValue:"+item.getId();    
			if(c.getValue()!=null){
				String value = (String) c.getValue().toString();
				if(value.equals(item.getId())){
					s += ", checked: true";
				}
			}
			s += "},";
		}
		if(s.endsWith(",")) {
			s = s.substring(0,s.length()-1);
		}
		s += "]})";  
		return s;
	}
	public static String makeYseNoRadio(Component c) {
		String s = "new Ext.form.RadioGroup({";
		s += "xtype:\"radiogroup\",";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s+="itemCls: \"x-check-group-alt\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		//s += "style:\"\",";//必加此项
		s += "columns: 2,";
		s += "vertical: true,";
		s += "items: [";  
		s += "{boxLabel: \"是\", name: \""+c.getName()+"\", inputValue: 1";    
		if(c.getValue()!=null){
			Object value = c.getValue();
			if(value.toString().equalsIgnoreCase("1")){
				s += ", checked: true";
			}
		}
		s += "},";
		s += "{boxLabel: \"否\", name: \""+c.getName()+"\", inputValue: 0";    
		if(c.getValue()!=null){
			Object value = c.getValue();
			if(value.toString().equalsIgnoreCase("0")){
				s += ", checked: true";
			}
		}
		s += "},";
		if(s.endsWith(",")) {
			s = s.substring(0,s.length()-1);
		}
		s += "]})";  
		return s;
	}
	public static String makeHtmlEditor(Component c) {
		String s = "new Ext.form.HtmlEditor({";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "xtype:\"htmleditor\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		//s += "style:\"\",";//必加此项//remove by lee for remove useless property in 20090927
		s += "width:\"9999\",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "value:\""+blank(c.getValue())+"\""; 
		//s += "allowBlank:"+c.getAllowBlank()+","; //无引号//remove by lee for remove useless property in 20090927
		//s += "validator:"+c.getValidator()+","; //无引号//remove by lee for remove useless property in 20090927
		//s += "vtype:\"\"";//remove by lee for remove useless property in 20090927		
		s += "})";
		
		return s;
	}
	
	/**
	 * 创建FCKediter编辑器组件
	 * @Methods Name makeFCKEditor
	 * @Create In Sep 28, 2009 By lee
	 * @param c
	 * @return String
	 */
	public static String makeFCKEditor(Component c) {
		String s = "new Ext.form.FCKeditor({";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "xtype:\"fckeditor\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\"9999\",";
		s += "value:\""+blank(c.getValue())+"\"";
		s += "})";
		return s;
	}
	
	public static String makeTextField(Component c) {
		String s = "new Ext.form.TextField({";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "xtype:\"textfield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "style:\"\",";//必加此项
		s += "width:\""+c.getWidth()+"\",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "value:\""+blank(c.getValue())+"\","; 
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
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\","; 
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
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
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
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
		s += "width:\""+c.getWidth()+"\",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\",";  
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "validator:"+c.getValidator()+","; //无引号
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	//todo
	public static String makeRadio(Component c) {
		String s = "new Ext.form.Radio({";
		s += "xtype:\"radio\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\","; 
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	//todo
	public static String makeMultiSelect(Component c) {
		String s = "{";
		s += "xtype:\"itemselector\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\"9999\",";
		s += "fieldLabel:\"全部"+c.getLabel()+"\",";
		s += "dataFields:[\"code\", \"desc\"],";
		s += "toData:[";
		List<Component> toDatas = c.getToSelectList();
		for(Component toData:toDatas){
			s += "[\""+toData.getId()+"\",\""+toData.getValue()+"\"],";
		}
		if(toDatas.size()>0){
			s=s.substring(0, s.length()-1);	
		}
		s += "],";
		s += "msWidth:250,";
		s += "msHeight:200,";
		s += "valueField:\"code\",";
		s += "displayField:\"desc\",";
		//s += "imagePath:\"images/\",";
		s += "toLegend:\"已选择的"+c.getLabel()+"\",";
		s += "fromLegend:\""+c.getLabel()+"\",";
		s += "fromData:[";
		List<Component> fromDatas = c.getFromSelectList();
		for(Component fromData:fromDatas){
			s += "[\""+fromData.getId()+"\",\""+fromData.getValue()+"\"],";
		}
		if(fromDatas.size()>0){
			s=s.substring(0, s.length()-1);
		}
		s += "]";
		s += "}";
		return s;
	}
	//todo
	public static String makeFile(Component c) {
		String s = "{";
		s += "xtype:\"textfield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
		s += "value:\""+blank(c.getValue())+"\",";  
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "}";
		return s;
	}
	public static String makeDateText(Component c) {
		String s = "new Ext.form.DateField({";
		s += "xtype:\"datefield\",";
		s += "id:\""+c.getName()+"\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
		if(c.getValue()!=null) {//去掉后面毫秒
			String value = c.getValue().toString();
			if(value.length()>10){
				c.setValue(value.substring(0,10));
			}
//			int index = c.getValue().toString().indexOf(".");//去除毫秒
//			String value = c.getValue().toString();
//			c.setValue(value.substring(0,index));
		}
		s += "value:\""+blank(c.getValue())+"\",";   //2009-04-23 09:20:01
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "validator:"+c.getValidator()+","; //无引号
		s += "format:\"Y-m-d\",";
		s += "fieldLabel:\""+c.getLabel()+"\"";
		s += "})";
		return s;
	}
	//作废
	public static String makeYesNoSelect(Component c) {
		String id = c.getName();
		String datas="[[\"1\","+"\""+"是"+"\""+"],[\"0\","+"\""+"否"+"\""+"]]";
		String value = "";
		if(c.getValue()!=null){
			value = c.getValue().toString();
		}
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "id:\""+id+"Combo\","; //修改成s += "id:\""+c.getName()+"\",";前端无法选择，待确原因
		s += "style:\"\",";//必加此项
		s += "mode: \"local\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "triggerAction:\"all\",";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: [\"id\", \"name\"],";
		s += "data: "+datas;
 		s += "}),";
 		s += "emptyText:\"请选择...\",";
 		s += "valueField :\"id\",";
 		s += "value :\""+value+"\",";
 		s += "displayField: \"name\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "listeners : {";
		s += "\"expand\" : function(combo) {combo.reset();}";
		s += "}";
		s += "})";
		return s;
	}
	
	public static String makeSexSelect(Component c) {
		String datas="[[\"1\","+"\""+"男"+"\""+"],[\"0\","+"\""+"女"+"\""+"]]";
		String value = "";
		if(c.getValue()!=null){
			value = c.getValue().toString();
		}
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "id: Ext.id(),"; //修改成s += "id:\""+c.getName()+"\",";前端无法选择，待确原因
		s += "style:\"\",";//必加此项
		s += "mode: \"local\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "triggerAction:\"all\",";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: [\"id\", \"name\"],";
		s += "data: "+datas;
 		s += "}),";
 		s += "emptyText:\"请选择...\",";
 		s += "valueField :\"id\",";
 		s += "value :\""+value+"\",";
 		s += "displayField: \"name\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "listeners : {";
		s += "\"expand\" : function(combo) {combo.reset();}";
		s += "}";
		s += "})";
		return s;
	}
	
	
	public static String makeSelect(Component c) {
		String data = "";
		String value = "";
		String[][] values = (String[][])c.getValue();
		if(values==null) {
			data = "[[]]";
		}
		else {
			for(int i=0;i<values.length;i++) {
				data += "[\""+values[i][0]+"\",\""+values[i][1]+"\"],";
				if(values[i][2]!=null&&values[i][2].equalsIgnoreCase("selected")) {
					value = values[i][0];//被选中的项
				}
			}
			if(data.endsWith(",")) {
				data = data.substring(0,data.length()-1);
			}
			data = "["+data+"]";
		}
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "id: Ext.id(),";
		s += "style:\"\",";//必加此项
		s += "mode: \"local\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: [\"id\", \"name\"],";
		s += "data: "+data;
 		s += "}),";
 		s += "emptyText:\"请选择...\",";
 		s += "valueField :\"id\",";
 		s += "value :\""+value+"\",";
 		s += "displayField: \"name\",";
		s += "name:\""+c.getName()+"\",";
		s += "width:\""+c.getWidth()+"\",";
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
	/**
	 * 用于修改抽象类的combox
	 * @param c
	 * @return
	 */
	public static String makeAbstractComboForEdit(Component c) {
		//System.out.print(c.getValue());
		String id  = c.getName()+"Combo";
		String disccId = c.getDisccId()+"Combo";
		String fdiscTable = c.getFdiscTable();
		c.setDisplayFiled("name");
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "id :\""+id+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "lazyRender: true,";
		s += "displayField: \""+c.getDisplayFiled()+"\",";
		s += "forceSelection: true,";//add by lee for 选择的值必须是下拉列表中的值 in 20091217
		s += "valueField :\"id\",";
		s += "emptyText:\"请选择...\",";
		s += "minChars :50,";
		s += "typeAhead:true,";
		s += "name:\""+c.getName()+"\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "triggerAction:\"all\",";
//		s += "queryDelay : 700,";
		s += "store:new Ext.data.JsonStore({url:webContext+\"/extjs/comboDataAction?isAbstract=true&fdiscTable="+fdiscTable+"&clazz="+c.getRelationship()+"\",";
			s += "fields:[\"id\",\""+c.getDisplayFiled()+"\"],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params[\""+c.getName()+"\"] == undefined){";
			s+="opt.params[\""+c.getDisplayFiled()+"\"] =Ext.getCmp(\""+id+"\").defaultParam;";
			s+="}}},";
			s += "totalProperty:\"rowCount\",root:\"data\",id:\"id\"})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "\"beforequery\" : function(queryEvent){";
			s+="var discValue=Ext.getCmp(\""+disccId+"\").getValue();";
			s+="Ext.Ajax.request({url:webContext+\"/extjs/comboDataAction?discValue=\"+discValue});";
			s += "var param = queryEvent.combo.getRawValue();this.defaultParam = param;";
			s += "if(queryEvent.query==\"\"){param=\"\";}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:\""+blank(c.getValue())+"\",start:0},callback:function(r, options, success){Ext.getCmp(\""+id+"\").setValue(\""+blank(c.getValue())+"\");}});}";
		s += "})";
		return s;
	}
	/**
	 *用于抽象类型的新建combox
	 * @param c
	 * @return
	 */
	public static String makeAbstractComboForAdd(Component c) {
//modify by lee for 修正下拉列表不点击不能显示必填的BUG in 20090901 start		
		String id  = c.getName()+"Combo";
		String disccId = c.getDisccId()+"Combo";
		String fdiscTable = c.getFdiscTable();
		c.setDisplayFiled("name");
		String s = "new Ext.form.ComboBox({";
		s += "xtype:\"combo\",";
		s += "hiddenName: \""+c.getName()+"\",";
		s += "id :\""+id+"\",";
		s += "width:\""+c.getWidth()+"\",";
		s += "style:\"\",";//必加此项
		s += "fieldLabel:\""+c.getLabel()+"\",";
		s += "lazyRender: true,";
		s += "displayField: \""+c.getDisplayFiled()+"\",";
		s += "forceSelection: true,";//add by lee for 选择的值必须是下拉列表中的值 in 20091217
		s += "valueField :\"id\",";
		s += "emptyText:\"请选择...\",";
		s += "minChars :50,";
		s += "typeAhead:true,";
		s += "name:\""+c.getName()+"\",";
		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
		s += "triggerAction:\"all\",";
//		s += "queryDelay : 700,";
		s += "store:new Ext.data.JsonStore({url:webContext+\"/extjs/comboDataAction?isAbstract=true&fdiscTable="+fdiscTable+"&clazz="+c.getRelationship()+"\",";
			s += "fields:[\"id\",\""+c.getDisplayFiled()+"\"],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params[\""+c.getName()+"\"] == undefined){";
			s+="opt.params[\""+c.getDisplayFiled()+"\"] =Ext.getCmp(\""+id+"\").defaultParam;";
			s+="}}},";
			s += "totalProperty:\"rowCount\",root:\"data\",id:\"id\"})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "\"beforequery\" : function(queryEvent){";
			s+="var discValue=Ext.getCmp(\""+disccId+"\").getValue();";
			s+="Ext.Ajax.request({url:webContext+\"/extjs/comboDataAction?discValue=\"+discValue});";
			s += "var param = queryEvent.combo.getRawValue();this.defaultParam = param;";
			s += "if(queryEvent.query==\"\"){param=\"\";}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:Ext.getCmp(\""+id+"\").getValue(),start:0},callback:function(r, options, success){Ext.getCmp(\""+id+"\").setValue(Ext.getCmp(\""+id+"\").getValue());}});}";
		s += "})";
//		
//		String id  = c.getName()+"Combo";
//		String disccId = c.getDisccId()+"Combo";
//		String fdiscTable = c.getFdiscTable();
//		c.setDisplayFiled("name");
//		String s = "{";
//		s += "xtype:\"combo\",";
//		s += "id :\""+id+"\",";
//		s += "hiddenName: \""+c.getName()+"\",";
//		s += "width:\""+c.getWidth()+"\",";
//		s += "style:\"\",";//必加此项
//		s += "lazyRender: true,";
//		s += "fieldLabel:\""+c.getLabel()+"\",";
//		s += "displayField: \""+c.getDisplayFiled()+"\",";
//		s += "valueField :\"id\",";
//		s += "emptyText:\"请选择...\",";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "typeAhead:true,";
//		s += "minChars :50,";
//		s += "triggerAction:\"all\",";
////		s += "queryDelay : 700,";
//		s += "name:\""+c.getName()+"\",";
//		s += "store:new Ext.data.JsonStore({url:webContext+\"/extjs/comboDataAction?isAbstract=true&fdiscTable="+fdiscTable+"&clazz="+c.getRelationship()+"\",";
//			s += "fields: [\"id\", \""+c.getDisplayFiled()+"\"],";
//			s+="listeners:{beforeload : function(store, opt){";
//			s+="if(opt.params[\""+c.getName()+"\"] == undefined){";
//			s+="opt.params[\""+c.getDisplayFiled()+"\"] =Ext.getCmp(\""+id+"\").defaultParam;";
//			s+="}}},";
//			s += "totalProperty:\"rowCount\",root:\"data\",id:\"id\"})";
//		s += ",pageSize:10";
//		s += ",listeners:{"; 
//			s += "\"beforequery\": function(queryEvent){";
//			s+="var discValue=Ext.getCmp(\""+disccId+"\").getValue();";
//			s+="Ext.Ajax.request({url:webContext+\"/extjs/comboDataAction?discValue=\"+discValue});";
//			s += "var param = queryEvent.combo.getRawValue(); this.defaultParam = param;var val = queryEvent.combo.getValue();";
//			s += "if(queryEvent.query==\"\"){param=\"\";}";
//			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
//			s += "}";
//		s += "}";
		//modify by lee for 修正下拉列表不点击不能显示必填的BUG in 20090901 end	
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
	
	//附件上传
	public static String makeAnnex(Component c) {
		String s = "{";
		s += "xtype:\"panel\",layout:\"table\",width:9999,layoutConfig:{columns:4},";
		s += "fieldLabel:\"" + c.getLabel() + "\",";
		s += "defaults:{baseCls:\"margin : 10 15 12 15\"},items:[";
		s += "{fieldLabel:\"" + c.getLabel() + "\",";
		s += "xtype:\"button\",";
		s += "disabled:false,";
		s += "text:\"<font color=red>上 传</font>\",";
		s += "width:50,";
		s += "scope:this,";
		s += "handler:function(){";
		s += "var attachmentFlag = Ext.getCmp(\"" + c.getName()
				+ "\").getValue();";
		s += "if(attachmentFlag==\"\"||attachmentFlag==\"null\"){";
		s += "attachmentFlag = Date.parse(new Date());";
		s += "Ext.getCmp(\"" + c.getName() + "\").setValue(attachmentFlag);";
		s += "var ud=new UpDownLoadFile();";
		//String hiddenId = String.valueOf(System.currentTimeMillis());
		s += "ud.getUpDownLoadFileSu(attachmentFlag,\"" + c.getColumnId()+ "\",\"" + c.getRelationship() + "\",\""+c.getHiddenString()+"\");";
		s += "}else{";
		s += "var ud=new UpDownLoadFile();";
		s += "ud.getUpDownLoadFileSu(attachmentFlag,\"" + c.getColumnId()
				+ "\",\"" + c.getRelationship() + "\",\""+c.getHiddenString()+"\");";
		s += "}}},";
		//s += "{border : true,html:\"8888\",cls : \"common-text\",style : \"text-align:right\"},";
		s += "{id:\""+c.getHiddenString()+"\",width:600,border : true,html:\""+c.getFileString()+"\",cls : \"common-text\",style : \"width:100;text-align:left\"}";
		s += "]}";
		//--------------下面是隐藏域隐藏真实值的-------------
		s += ",";
		s += "new Ext.form.Hidden({";
		s += "xtype:\"hidden\",";
		s += "id:\"" + c.getName() + "\",";
		s += "name:\"" + c.getName() + "\",";
		s += "style:\"\",";// 必加此项
		s += "value:\"" + c.getValue() + "\",";
		s += "fieldLabel:\"nowtime\"";
		s += "})";
		return s;
    //--------------------------sujs 修改之前的完成代码------------------
//		String s = "{";
//		s += "fieldLabel:\"" + c.getLabel() + "\",";
//		s += "xtype:\"button\",";
//		s += "text:\"<font color=red>上传</font>/<font color=green>下载</font>\",";
//		s += "width:\"50\",";
//		// s+="pressed:true,";
//		s += "scope:this,";
//		s += "handler:function(){";
////add by lee for attachment static page unused bug in 20090708 begin
//		s += "var attachmentFlag = Ext.getCmp(\"" + c.getName()
//				+ "\").getValue();";
//		s += "if(attachmentFlag==\"\"||attachmentFlag==\"null\"){";
//		s += "attachmentFlag = Date.parse(new Date());";
//		s += "Ext.getCmp(\"" + c.getName() + "\").setValue(attachmentFlag);";
//		s += "var ud=new UpDownLoadFile();";
//		s += "ud.getUpDownLoadFileSu(attachmentFlag,\"" + c.getColumnId()+ "\",\"" + c.getRelationship() + "\");";
//		s += "}else{";
//		s += "var ud=new UpDownLoadFile();";
//		s += "ud.getUpDownLoadFileSu(attachmentFlag,\"" + c.getColumnId()
//				+ "\",\"" + c.getRelationship() + "\");";
//		s += "}}}";
//		s += ",";
//		s += "new Ext.form.Hidden({";
//		s += "xtype:\"hidden\",";
//		s += "id:\"" + c.getName() + "\",";
//		s += "name:\"" + c.getName() + "\",";
//		s += "style:\"\",";// 必加此项
//		s += "value:\"" + c.getValue() + "\",";
////add by lee for attachment static page unused bug in 20090708 end
////remove by lee for attachment static page unused bug in 20090708 begin		
//		// s+="var ud=new UpDownLoadFile();";
//		// s+="ud.getUpDownLoadFileSu(\""+c.getNowtime()+"\",\""+c.getColumnId()+"\",\""+c.getRelationship()+"\");";
//		// s+="}}";
//		// s+=",";
//		// s += "new Ext.form.Hidden({";
//		// s += "xtype:\"hidden\",";
//		// s += "id:\"nowtime\",";
//		// s += "name:\""+c.getName()+"\",";
//		// s += "style:\"\",";//必加此项
//		// s += "value:\""+c.getValue()+"\",";
//		// //s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		// //s += "validator:"+c.getValidator()+","; //无引号
//		// // s += "fieldLabel:\""+c.getLabel()+"\"";
////remove by lee for attachment static page unused bug in 20090708 end
//		s += "fieldLabel:\"nowtime\"";
//		s += "})";
//		return s;
    //----------------------------------------------------------------
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
	
	public static String makeSexSelect(String name,String label,String width) {
		return makeSexSelect(new Component(name,label,width));
	}
	
	public static String makeSelect(String name,String label,String width) {
		return makeSelect(new Component(name,label,width));
	}
	

	private static Object blank(Object s) {
		//return s==null?"":s.toString();
		return (s==null?"":s);
	}
}
