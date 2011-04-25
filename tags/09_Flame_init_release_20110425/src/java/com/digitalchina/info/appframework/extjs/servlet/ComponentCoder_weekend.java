//package com.digitalchina.info.appframework.extjs.servlet;
//
//import java.util.List;
//
//
//public class ComponentCoder_weekend {
//
//	public static String makeCheckboxGroup(Component c) {
//		String s = "new Ext.form.CheckboxGroup({";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "xtype:'checkboxgroup',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		//s += "style:'',";//必加此项
//		
//		if(c.isSingleLine()){
//			s += "width:'9999',";
//		}
//		Integer columnSum = c.getColumns();
//		if(columnSum!=null){
//			s += "columns: "+columnSum+",";
//		}
//		List<Component> items = c.getItems();
//		s += "items: [";  
//		for(Component item: items){
//			s += "{boxLabel: '"+item.getLabel()+"', name: '"+item.getName()+"'";    
//			if(item.getValue()!=null){
//				Object value = item.getValue();
//				if(value.toString().equalsIgnoreCase("on")){
//					s += ", checked: true";
//				}
//			}
//			s += "},";
//		}
//		if(s.endsWith(",")) {
//			s = s.substring(0,s.length()-1);
//		}
//		s += "]})";  
//		return s;
//	}
//	 
//	public static String makeHtmlEditor(Component c) {
//		String s = "new Ext.form.HtmlEditor({";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "xtype:'htmleditor',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "style:'',";//必加此项
//		s += "width:'9999',";
//		//s += "readOnly:"+blank(c.isReadOnly())+",";
//		s += "value:'"+blank(c.getValue())+"',"; 
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "validator:"+c.getValidator()+","; //无引号
//		s += "vtype:''";		
//		s += "})";
//		
//		return s;
//	}
//	
//	public static String makeTextField(Component c) {
//		String s = "new Ext.form.TextField({";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "xtype:'textfield',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "style:'',";//必加此项
//		s += "width:'"+c.getWidth()+"',";
//		//s += "readOnly:"+blank(c.isReadOnly())+",";
//		s += "value:'"+blank(c.getValue())+"',"; 
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "validator:"+c.getValidator()+","; //无引号
//		s += "vtype:''";		
//		s += "})";
//		return s;
//	}
//	public static String makeNumberField(Component c) {
//		String s = "new Ext.form.NumberField({";
//		s += "xtype:'numberfield',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		s += "value:'"+blank(c.getValue())+"',"; 
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "validator:"+c.getValidator()+","; //无引号
//		s += "fieldLabel:'"+c.getLabel()+"'";
//		s += "})";
//		return s;
//	}
//	public static String makeHidden(Component c) {
//		String s = "new Ext.form.Hidden({";
//		s += "xtype:'hidden',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		s += "value:'"+blank(c.getValue())+"',"; 
//		//s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		//s += "validator:"+c.getValidator()+","; //无引号
//		s += "fieldLabel:'"+c.getLabel()+"'";
//		//s += "fieldLabel:'"+c.getLabel()+"'";
//		s += "})";
//		return s;
//	}
//	public static String makeTextArea(Component c) {
//		String s = "new Ext.form.TextArea({";
//		s += "xtype:'textarea',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		//s += "readOnly:"+blank(c.isReadOnly())+",";
//		s += "style:'',";//必加此项
//		s += "value:'"+blank(c.getValue())+"',";  
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "validator:"+c.getValidator()+","; //无引号
//		s += "fieldLabel:'"+c.getLabel()+"'";
//		s += "})";
//		return s;
//	}
//	//todo
//	public static String makeRadio(Component c) {
//		String s = "new Ext.form.Radio({";
//		s += "xtype:'radio',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "style:'',";//必加此项
//		s += "value:'"+blank(c.getValue())+"',"; 
//		s += "fieldLabel:'"+c.getLabel()+"'";
//		s += "})";
//		return s;
//	}
//	//todo
//	public static String makeMultiSelect(Component c) {
//		String s = "{";
//		s += "xtype:'combo',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "style:'',";//必加此项
//		//s += "value:'"+blank(c.getValue())+"',";  
//		s += "fieldLabel:'"+c.getLabel()+"'";
//		s += "}";
//		return s;
//	}
//	//todo
//	public static String makeFile(Component c) {
//		String s = "{";
//		s += "xtype:'textfield',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		s += "value:'"+blank(c.getValue())+"',";  
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "fieldLabel:'"+c.getLabel()+"'";
//		s += "}";
//		return s;
//	}
//	public static String makeDateText(Component c) {
//		String s = "new Ext.form.DateField({";
//		s += "xtype:'datefield',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		if(c.getValue()!=null) {//去掉后面毫秒
//			String value = c.getValue().toString();
//			if(value.length()>10){
//				c.setValue(value.substring(0,10));
//			}
////			int index = c.getValue().toString().indexOf(".");//去除毫秒
////			String value = c.getValue().toString();
////			c.setValue(value.substring(0,index));
//		}
//		s += "value:'"+blank(c.getValue())+"',";   //2009-04-23 09:20:01
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "validator:"+c.getValidator()+","; //无引号
//		s += "format:'Y-m-d',";
//		s += "fieldLabel:'"+c.getLabel()+"'";
//		s += "})";
//		return s;
//	}
//	//作废
//	public static String makeYesNoSelect(Component c) {
//		String id = c.getName();
//		String datas="[['1',"+"'"+"是"+"'"+"],['0',"+"'"+"否"+"'"+"]]";
//		String value = "";
//		if(c.getValue()!=null){
//			value = c.getValue().toString();
//		}
//		String s = "new Ext.form.ComboBox({";
//		s += "xtype:'combo',";
//		s += "id:'"+id+"Combo',"; //修改成s += "id:'"+c.getName()+"',";前端无法选择，待确原因
//		s += "style:'',";//必加此项
//		s += "mode: 'local',";
//		s += "hiddenName: '"+c.getName()+"',";
//		s += "triggerAction:'all',";
//		s += "typeAhead: true,";
//		s += "forceSelection: true,";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "store: new Ext.data.SimpleStore({";
//		s += "fields: ['id', 'name'],";
//		s += "data: "+datas;
// 		s += "}),";
// 		s += "emptyText:'请选择...',";
// 		s += "valueField :'id',";
// 		s += "value :'"+value+"',";
// 		s += "displayField: 'name',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "listeners : {";
//		s += "'expand' : function(combo) {combo.reset();}";
//		s += "}";
//		s += "})";
//		return s;
//	}
//	
//	public static String makeSexSelect(Component c) {
//		String datas="[['1',"+"'"+"男"+"'"+"],['0',"+"'"+"女"+"'"+"]]";
//		String value = "";
//		if(c.getValue()!=null){
//			value = c.getValue().toString();
//		}
//		String s = "new Ext.form.ComboBox({";
//		s += "xtype:'combo',";
//		s += "id: Ext.id(),"; //修改成s += "id:'"+c.getName()+"',";前端无法选择，待确原因
//		s += "style:'',";//必加此项
//		s += "mode: 'local',";
//		s += "hiddenName: '"+c.getName()+"',";
//		s += "triggerAction:'all',";
//		s += "typeAhead: true,";
//		s += "forceSelection: true,";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "store: new Ext.data.SimpleStore({";
//		s += "fields: ['id', 'name'],";
//		s += "data: "+datas;
// 		s += "}),";
// 		s += "emptyText:'请选择...',";
// 		s += "valueField :'id',";
// 		s += "value :'"+value+"',";
// 		s += "displayField: 'name',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "listeners : {";
//		s += "'expand' : function(combo) {combo.reset();}";
//		s += "}";
//		s += "})";
//		return s;
//	}
//	
//	
//	public static String makeSelect(Component c) {
//		String data = "";
//		String value = "";
//		String[][] values = (String[][])c.getValue();
//		if(values==null) {
//			data = "[[]]";
//		}
//		else {
//			for(int i=0;i<values.length;i++) {
//				data += "['"+values[i][0]+"','"+values[i][1]+"'],";
//				if(values[i][2]!=null&&values[i][2].equalsIgnoreCase("selected")) {
//					value = values[i][0];//被选中的项
//				}
//			}
//			if(data.endsWith(",")) {
//				data = data.substring(0,data.length()-1);
//			}
//			data = "["+data+"]";
//		}
//		String s = "new Ext.form.ComboBox({";
//		s += "xtype:'combo',";
//		s += "id: Ext.id(),";
//		s += "style:'',";//必加此项
//		s += "mode: 'local',";
//		s += "hiddenName: '"+c.getName()+"',";
//		s += "typeAhead: true,";
//		s += "forceSelection: true,";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "store: new Ext.data.SimpleStore({";
//		s += "fields: ['id', 'name'],";
//		s += "data: "+data;
// 		s += "}),";
// 		s += "emptyText:'请选择...',";
// 		s += "valueField :'id',";
// 		s += "value :'"+value+"',";
// 		s += "displayField: 'name',";
//		s += "name:'"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "listeners : {";
//		s += "'expand' : function(combo) {combo.reset();}";
//		s += "}";
//		s += "})";
//		return s;
//	}
//	
//	
//	/**
//	 * 组装一个修改页面的combo
//	 * TODO
//	 * Nov 6, 2008 By chuanyu ou
//	 * @param c
//	 * @return TODO
//	 */
//	public static String makeComboForEdit(Component c) {
//		String id  = c.getName()+"Combo";
//		String s = "new Ext.form.ComboBox({";
//		s += "xtype:'combo',";
//		s += "hiddenName: '"+c.getName()+"',";
//		s += "id :'"+id+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "lazyRender: true,";
//		s += "displayField: '"+c.getDisplayFiled()+"',";
//		s += "valueField :'id',";
//		s += "emptyText:'请选择...',";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "typeAhead:true,";
//		s += "name:'"+c.getName()+"',";
//		s += "triggerAction:'all',";
//		s += "minChars :50,";
//		s += "queryDelay : 700,";
//		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz="+c.getRelationship()+"',";
//			s += "fields:['id','"+c.getDisplayFiled()+"'],";
//			s += "totalProperty:'rowCount',root:'data',id:'id'})";
//		s += ",pageSize:10";
//		s += ",listeners:{"; 
//			s += "'beforequery' : function(queryEvent)";
//			s += "{var param = queryEvent.combo.getRawValue();";
//			s += "if(queryEvent.query==''){param='';}";
//			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
//			s += "}";
//			s += ",initComponent : function() {this.store.load({params:{id:'"+blank(c.getValue())+"',start:0},callback:function(r, options, success){Ext.getCmp('"+id+"').setValue('"+blank(c.getValue())+"');}});}";
//		s += "})";
//		return s;
//	}
//	
//	/**
//	 * 组装一个增加页面的combo
//	 * TODO
//	 * Nov 6, 2008 By chuanyu ou
//	 * @param c
//	 * @return TODO
//	 */
//	public static String makeComboForAdd(Component c) {
//		String id  = c.getName()+"Combo"; 
//		String s = "{";
//		s += "xtype:'combo',";
//		s += "id :'"+id+"',";
//		s += "hiddenName: '"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		s += "lazyRender: true,";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "displayField: '"+c.getDisplayFiled()+"',";
//		s += "valueField :'id',";
//		s += "emptyText:'请选择...',";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "typeAhead:true,";
//		s += "minChars :50,";
//		s += "triggerAction:'all',";
////		s += "queryDelay : 700,";
//		s += "name:'"+c.getName()+"',";
//		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz="+c.getRelationship()+"',";
//			s += "fields: ['id', '"+c.getDisplayFiled()+"'],";
//			s += "totalProperty:'rowCount',root:'data',id:'id'})";
//		s += ",pageSize:10";
//		s += ",listeners:{"; 
//			s += "'beforequery' : function(queryEvent)";
//			s += "{var param = queryEvent.combo.getRawValue();var val = queryEvent.combo.getValue();";
//			s += "if(queryEvent.query==''){param='';}";
//			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
//			s += "}";
//		s += "}";
//		return s;
//	}
//	/**
//	 * 用于修改抽象类的combox
//	 * @param c
//	 * @return
//	 */
//	public static String makeAbstractComboForEdit(Component c) {
//		//System.out.print(c.getValue());
//		String id  = c.getName()+"Combo";
//		String disccId = c.getDisccId()+"Combo";
//		String fdiscTable = c.getFdiscTable();
//		c.setDisplayFiled("name");
//		String s = "new Ext.form.ComboBox({";
//		s += "xtype:'combo',";
//		s += "hiddenName: '"+c.getName()+"',";
//		s += "id :'"+id+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "lazyRender: true,";
//		s += "displayField: '"+c.getDisplayFiled()+"',";
//		s += "valueField :'id',";
//		s += "emptyText:'请选择...',";
//		s += "minChars :50,";
//		s += "typeAhead:true,";
//		s += "name:'"+c.getName()+"',";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "triggerAction:'all',";
////		s += "queryDelay : 700,";
//		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?isAbstract=true&fdiscTable="+fdiscTable+"&clazz="+c.getRelationship()+"',";
//			s += "fields:['id','"+c.getDisplayFiled()+"'],";
//			s += "totalProperty:'rowCount',root:'data',id:'id'})";
//		s += ",pageSize:10";
//		s += ",listeners:{"; 
//			s += "'beforequery' : function(queryEvent){";
//			s+="var discValue=Ext.getCmp('"+disccId+"').getValue();";
//			s+="Ext.Ajax.request({url:webContext+'/extjs/comboDataAction?discValue='+discValue});";
//			s += "var param = queryEvent.combo.getRawValue();";
//			s += "if(queryEvent.query==''){param='';}";
//			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
//			s += "}";
//			s += ",initComponent : function() {this.store.load({params:{id:'"+blank(c.getValue())+"',start:0},callback:function(r, options, success){Ext.getCmp('"+id+"').setValue('"+blank(c.getValue())+"');}});}";
//		s += "})";
//		return s;
//	}
//	/**
//	 *用于抽象类型的新建combox
//	 * @param c
//	 * @return
//	 */
//	public static String makeAbstractComboForAdd(Component c) {
//		String id  = c.getName()+"Combo";
//		String disccId = c.getDisccId()+"Combo";
//		String fdiscTable = c.getFdiscTable();
//		c.setDisplayFiled("name");
//		String s = "{";
//		s += "xtype:'combo',";
//		s += "id :'"+id+"',";
//		s += "hiddenName: '"+c.getName()+"',";
//		s += "width:'"+c.getWidth()+"',";
//		s += "style:'',";//必加此项
//		s += "lazyRender: true,";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "displayField: '"+c.getDisplayFiled()+"',";
//		s += "valueField :'id',";
//		s += "emptyText:'请选择...',";
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "typeAhead:true,";
//		s += "minChars :50,";
//		s += "triggerAction:'all',";
////		s += "queryDelay : 700,";
//		s += "name:'"+c.getName()+"',";
//		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?isAbstract=true&fdiscTable="+fdiscTable+"&clazz="+c.getRelationship()+"',";
//			s += "fields: ['id', '"+c.getDisplayFiled()+"'],";
//			s += "totalProperty:'rowCount',root:'data',id:'id'})";
//		s += ",pageSize:10";
//		s += ",listeners:{"; 
//			s += "'beforequery': function(queryEvent){";
//			s+="var discValue=Ext.getCmp('"+disccId+"').getValue();";
//			s+="Ext.Ajax.request({url:webContext+'/extjs/comboDataAction?discValue='+discValue});";
//			s += "var param = queryEvent.combo.getRawValue();var val = queryEvent.combo.getValue();";
//			s += "if(queryEvent.query==''){param='';}";
//			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
//			s += "}";
//		s += "}";
//	
//		return s;
//	}
//	
//	public static String makeHtml(Component c) {
//		String s = "{";
//		s += "fieldLabel:'"+c.getLabel()+"',";
//		s += "html :\"<font color=blouse>"+blank(c.getValue())+"</font>\",";
//		s += "cls : 'common-text',";
//		s += "width:135,";
//		s += "style : 'width:150;text-align:right',"; 
//		//s += "xtype:'textfield',";
//		s += "id:'"+c.getName()+"',";
//		s += "name:'"+c.getName()+"',";
//		s += "style:'',";//必加此项
//		s += "width:'"+c.getWidth()+"',";
//		//s += "readOnly:"+blank(c.isReadOnly())+",";
//		//s += "value:'"+blank(c.getValue())+"',"; 
//		s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		s += "validator:"+c.getValidator()+","; //无引号
//		s += "vtype:''";		
//		s += "}";
//		return s;
//	}
//	
//	public static String makeAnnex(Component c) {
//		String s="{";
//		  s += "fieldLabel:'"+c.getLabel()+"',";
//		  s += "xtype:'button',";
//		  s+="text:'<font color=red>上传</font>/<font color=green>下载</font>',";
//		  s += "width:'50',";
//		  //s+="pressed:true,";
//		  s+="scope:this,";
//		  s+="handler:function(){";
//		  s+="var ud=new UpDownLoadFile();";
//		  s+="ud.getUpDownLoadFileSu('"+c.getNowtime()+"','"+c.getColumnId()+"','"+c.getRelationship()+"');";
//		  s+="}}";
//		  s+=",";
//		  s += "new Ext.form.Hidden({";
//		  s += "xtype:'hidden',";
//		  s += "id:'nowtime',";
//		  s += "name:'"+c.getName()+"',";
//		  s += "style:'',";//必加此项
//		  s += "value:'"+c.getValue()+"',"; 
//		  //s += "allowBlank:"+c.getAllowBlank()+","; //无引号
//		  //s += "validator:"+c.getValidator()+","; //无引号
//		  // s += "fieldLabel:'"+c.getLabel()+"'";
//		  s += "fieldLabel:'nowtime'";
//		  s += "})";
//		return s;
//	}
//	//////////////short style
//	public static String makeTextField(String name,String label,String width) {
//		return makeTextField(new Component(name,label,width));
//	}
//	public static String makeHidden(String name,String label,String width) {
//		return makeHidden(new Component(name,label,width));
//	}
//	public static String makeTextArea(String name,String label,String width) {
//		return makeTextArea(new Component(name,label,width));
//	}
//	public static String makeRadio(String name,String label,String width) {
//		return makeRadio(new Component(name,label,width));
//	}
//	public static String makeMultiSelect(String name,String label,String width) {
//		return makeMultiSelect(new Component(name,label,width));
//	}
//	public static String makeFile(String name,String label,String width) {
//		return makeFile(new Component(name,label,width));
//	}
//	public static String makeDateText(String name,String label,String width) {
//		return makeDateText(new Component(name,label,width));
//	}
//	public static String makeYesNoSelect(String name,String label,String width) {
//		return makeYesNoSelect(new Component(name,label,width));
//	}
//	public static String makeSelect(String name,String label,String width) {
//		return makeSelect(new Component(name,label,width));
//	}
//	
//
//	private static String blank(Object s) {
//		return s==null?"":s.toString();
//	}
//}
