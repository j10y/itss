package com.zsgj.info.appframework.pagemodel.autocreat.servlet;

import java.util.List;
import java.util.Random;

import com.zsgj.info.appframework.pagemodel.autocreat.servlet.Component;

public class ComponentGather {
	public static String makeCheckboxGroup(Component c) {
		String s = "new Ext.form.CheckboxGroup({";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "xtype:'checkboxgroup',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "id:'"+c.getName()+"',";
		s += "name:'"+c.getName()+"',";
		//s += "style:'',";//�ؼӴ���
		
		if(c.isSingleLine()){
			s += "width:"+c.getWidth()+",";
		}
		Integer columnSum = c.getColumns();
		if(columnSum!=null){
			s += "columns: "+columnSum+",";
		}
		List<Component> items = c.getItems();
		s += "items: [";  
		for(Component item: items){
			s += "{boxLabel: '"+item.getLabel()+"', name: '"+item.getName()+"'";    
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
	 
	public static String makeHtmlEditor(Component c) {
		String s = "new Ext.form.HtmlEditor({";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "xtype:'htmleditor',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "id:'"+c.getName()+"',";
		s += "name:'"+c.getName()+"',";
		s += "style:'',";//�ؼӴ���
		s += "width:"+c.getWidth()+",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "value:'"+blank(c.getValue())+"',"; 
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "validator:"+c.getValidator()+","; //������
		s += "vtype:''";		
		s += "})";
		
		return s;
	}
	/**
	 * ����FCKediter�༭�����
	 * @Methods Name makeFCKEditor
	 * @Create In Sep 28, 2009 By lee
	 * @param c
	 * @return String
	 */
	public static String makeFCKEditor(Component c) {
		String s = "new Ext.form.FCKeditor({";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "xtype:'fckeditor',";
		s += "id:'"+c.getName()+"',";
		s += "name:'"+c.getName()+"',";
		s += "width:'9999',";
		//modify by lee for debug error when string contain ' or " in 20091020 begin
		//s += "value:'"+blank(c.getValue())+"'";
		s += "value:"+(c.getValue()==null?"''":blank(c.getValue())+"");
		//modify by lee for debug error when string contain ' or " in 20091020 end
		s += "})";
		return s;
	}
	public static String makeTextField(Component c) {
		String s = "new Ext.form.TextField({";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "xtype:'textfield',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "id:'"+c.getName()+"',";
		s += "name:'"+c.getName()+"',";
		s += "style:'',";//�ؼӴ���
		s += "width:"+c.getWidth()+",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "value:'"+blank(c.getValue())+"',"; 
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "validator:"+c.getValidator()+","; //������
		s += "vtype:''";		
		s += "})";
		return s;
	}
	public static String makeNumberField(Component c) {
		String s = "new Ext.form.NumberField({";
		s += "xtype:'numberfield',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "value:'"+blank(c.getValue())+"',"; 
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "validator:"+c.getValidator()+","; //������
		s += "fieldLabel:'"+c.getLabel()+"'";
		s += "})";
		return s;
	}
	public static String makeHidden(Component c) {
		String s = "new Ext.form.Hidden({";
		s += "xtype:'hidden',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "value:'"+blank(c.getValue())+"',"; 
		//s += "allowBlank:"+c.getAllowBlank()+","; //������
		//s += "validator:"+c.getValidator()+","; //������
		s += "fieldLabel:'"+c.getLabel()+"'";
		//s += "fieldLabel:'"+c.getLabel()+"'";
		s += "})";
		return s;
	}
	public static String makeTextArea(Component c) {
		String s = "new Ext.form.TextArea({";
		s += "xtype:'textarea',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "height:"+c.getHeight()+",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "style:'',";//�ؼӴ���
		s += "value:'"+blank(c.getValue())+"',";  
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "validator:"+c.getValidator()+","; //������
		s += "fieldLabel:'"+c.getLabel()+"'";
		s += "})";
		return s;
	}
	//todo
	public static String makeRadio(Component c) {
		String s = "new Ext.form.Radio({";
		s += "xtype:'radio',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "style:'',";//�ؼӴ���
		s += "value:'"+blank(c.getValue())+"',"; 
		s += "fieldLabel:'"+c.getLabel()+"'";
		s += "})";
		return s;
	}
	//todo
	public static String makeMultiSelect(Component c) {
		String s = "{";
		s += "xtype:'combo',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "style:'',";//�ؼӴ���
		//s += "value:'"+blank(c.getValue())+"',";  
		s += "fieldLabel:'"+c.getLabel()+"'";
		s += "}";
		return s;
	}
	//todo
	public static String makeFile(Component c) {
		String s = "{";
		s += "xtype:'textfield',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "value:'"+blank(c.getValue())+"',";  
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "fieldLabel:'"+c.getLabel()+"'";
		s += "}";
		return s;
	}
	public static String makeLazySelect(Component c) {
		String id  = c.getName()+"Combo";
		String s = "new Ext.form.ComboBox({";
		s += "xtype:'combo',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "id :'"+id+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "feildLabel:'"+c.getLabel()+"',";
		s += "lazyRender: true,";
		s += "displayField: '"+c.getDisplayFiled()+"',";
		s += "valueField :'id',";
		s += "emptyText:'��ѡ��...',";
		s += "typeAhead:true,";
		s += "name:'"+c.getName()+"',";
		s += "triggerAction:'all',";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "minChars :50,";
//		s += "queryDelay : 700,";
		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz="+c.getRelationship()+"',";
			s += "fields:['id','"+c.getDisplayFiled()+"'],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params['"+c.getName()+"'] == undefined){";
			s+="opt.params['"+c.getDisplayFiled()+"'] =Ext.getCmp('"+id+"').defaultParam;";
			s+="}}},";
			s += "totalProperty:'rowCount',root:'data',id:'id'})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "'beforequery' : function(queryEvent)";
			s += "{var param = queryEvent.combo.getRawValue();this.defaultParam = param;";
			s += "if(queryEvent.query==''){param='';}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:'"+blank(c.getValue())+"',start:0},callback:function(r, options, success){Ext.getCmp('"+id+"').setValue('"+blank(c.getValue())+"');}});}";
		s += "})";
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
				data += "['"+values[i][0]+"','"+values[i][1]+"'],";
				if(values[i][2]!=null&&values[i][2].equalsIgnoreCase("selected")) {
					value = values[i][0];//��ѡ�е���
					dispValue = values[i][1];
				}
			}
			if(data.endsWith(",")) {
				data = data.substring(0,data.length()-1);
			}
			data = "["+data+"]";
		}
		//Add by DJ
		//�ж��б���,�������10�Ļ�����query,����15����all
		String triggerAction="query";
		if(values.length<16){
			triggerAction="all";
		}
		
		String s = "new Ext.form.ComboBox({";
		s += "xtype:'combo',";
		s += "id: '"+c.getName()+"ComboId',";
		s += "style:'',";//�ؼӴ���
		s += "mode: 'local',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "readOnly:"+blank(c.isReadOnly())+",";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank: "+c.getAllowBlank()+",";
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: ['id', 'name'],";
		s += "data: "+data;
 		s += "}),";
 		s += "emptyText:'��ѡ��...',";
 		s += "valueField :'id',";
 		s += "value :'"+value+"',";
 		s += "dispValue:'"+dispValue+"',";
 		s += "displayField: 'name',";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "triggerAction:'"+triggerAction+"',";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "listeners : {";
		s += "'expand' : function(combo) {combo.reset();}";
		s += "}";
		s += "})";
		return s;
	}
	public static String makeDateText(Component c) {
		String s = "new Ext.form.DateField({";
		s += "xtype:'datefield',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		if(c.getValue()!=null) {//ȥ���������
			String value = c.getValue().toString();
			if(value.length()>10){
				c.setValue(value.substring(0,10));
			}
//			int index = c.getValue().toString().indexOf(".");//ȥ������
//			String value = c.getValue().toString();
//			c.setValue(value.substring(0,index));
		}
		s += "value:'"+blank(c.getValue())+"',";   //2009-04-23 09:20:01
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "validator:"+c.getValidator()+","; //������
		s += "format:'Y-m-d',";
		s += "fieldLabel:'"+c.getLabel()+"'";
		s += "})";
		return s;
	}
	//����
	public static String makeYesNoSelect(Component c) {
		String id = c.getName();
		String datas="[['1',"+"'"+"��"+"'"+"],['0',"+"'"+"��"+"'"+"]]";
		String value = "";
		if(c.getValue()!=null){
			value = c.getValue().toString();
		}
		String s = "new Ext.form.ComboBox({";
		s += "xtype:'combo',";
		s += "id:'"+id+"Combo',"; //�޸ĳ�s += "id:'"+c.getName()+"',";ǰ���޷�ѡ�񣬴�ȷԭ��
		s += "style:'',";//�ؼӴ���
		s += "mode: 'local',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "triggerAction:'all',";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: ['id', 'name'],";
		s += "data: "+datas;
 		s += "}),";
 		s += "emptyText:'��ѡ��...',";
 		s += "valueField :'id',";
 		s += "value :'"+value+"',";
 		s += "displayField: 'name',";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "listeners : {";
		s += "'expand' : function(combo) {combo.reset();}";
		s += "}";
		s += "})";
		return s;
	}
	
	public static String makeSexSelect(Component c) {
		String datas="[['1',"+"'"+"��"+"'"+"],['0',"+"'"+"Ů"+"'"+"]]";
		String value = "";
		if(c.getValue()!=null){
			value = c.getValue().toString();
		}
		String s = "new Ext.form.ComboBox({";
		s += "xtype:'combo',";
		s += "id: Ext.id(),"; //�޸ĳ�s += "id:'"+c.getName()+"',";ǰ���޷�ѡ�񣬴�ȷԭ��
		s += "style:'',";//�ؼӴ���
		s += "mode: 'local',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "triggerAction:'all',";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: ['id', 'name'],";
		s += "data: "+datas;
 		s += "}),";
 		s += "emptyText:'��ѡ��...',";
 		s += "valueField :'id',";
 		s += "value :'"+value+"',";
 		s += "displayField: 'name',";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "listeners : {";
		s += "'expand' : function(combo) {combo.reset();}";
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
				data += "['"+values[i][0]+"','"+values[i][1]+"'],";
				if(values[i][2]!=null&&values[i][2].equalsIgnoreCase("selected")) {
					value = values[i][0];//��ѡ�е���
				}
			}
			if(data.endsWith(",")) {
				data = data.substring(0,data.length()-1);
			}
			data = "["+data+"]";
		}
		String s = "new Ext.form.ComboBox({";
		s += "xtype:'combo',";
		s += "id: Ext.id(),";
		s += "style:'',";//�ؼӴ���
		s += "mode: 'local',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "typeAhead: true,";
		s += "forceSelection: true,";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "store: new Ext.data.SimpleStore({";
		s += "fields: ['id', 'name'],";
		s += "data: "+data;
 		s += "}),";
 		s += "emptyText:'��ѡ��...',";
 		s += "valueField :'id',";
 		s += "value :'"+value+"',";
 		s += "displayField: 'name',";
		s += "name:'"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "listeners : {";
		s += "'expand' : function(combo) {combo.reset();}";
		s += "}";
		s += "})";
		return s;
	}
	
	
	/**
	 * ��װһ���޸�ҳ���combo
	 * TODO
	 * Nov 6, 2008 By chuanyu ou
	 * @param c
	 * @return TODO
	 */
	public static String makeComboForEdit(Component c) {
		String id  = c.getName()+"Combo";
		String s = "new Ext.form.ComboBox({";
		s += "xtype:'combo',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "id :'"+id+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "lazyRender: true,";
		s += "displayField: '"+c.getDisplayFiled()+"',";
		s += "valueField :'id',";
		s += "emptyText:'��ѡ��...',";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "typeAhead:true,";
		s += "name:'"+c.getName()+"',";
		s += "triggerAction:'all',";
		s += "minChars :50,";
		s += "queryDelay : 700,";
		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz="+c.getRelationship()+"',";
			s += "fields:['id','"+c.getDisplayFiled()+"'],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params['"+c.getName()+"'] == undefined){";
			s+="opt.params['"+c.getDisplayFiled()+"'] =Ext.getCmp('"+id+"').defaultParam;";
			s+="}}},";
			s += "totalProperty:'rowCount',root:'data',id:'id'})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "'beforequery' : function(queryEvent)";
			s += "{var param = queryEvent.combo.getRawValue();this.defaultParam = param;";
			s += "if(queryEvent.query==''){param='';}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:'"+blank(c.getValue())+"',start:0},callback:function(r, options, success){Ext.getCmp('"+id+"').setValue('"+blank(c.getValue())+"');}});}";
		s += "})";
		return s;
	}
	/**
	 * ��ȡչ���������б����
	 * @Methods Name makeComboForExpand
	 * @Create In May 15, 2009 By sa
	 * @param c
	 * @return String
	 */
	public static String makeComboForExpand(Component c) {
		String id  = c.getName()+"Combo";
		String s = "new Ext.form.ComboBox({";
		s += "xtype:'combo',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "id :'"+id+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "lazyRender: true,";
		s += "displayField: '"+c.getDisplayFiled()+"',";
		s += "valueField :'id',";
		s += "emptyText:'��ѡ��...',";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "typeAhead:true,";
		s += "name:'"+c.getName()+"',";
		s += "triggerAction:'all',";
		s += "minChars :50,";
		s += "queryDelay : 700,";
		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz="+c.getRelationship()+"',";
			s += "fields:['id','"+c.getDisplayFiled()+"'],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params['"+c.getName()+"'] == undefined){";
			s+="opt.params['"+c.getDisplayFiled()+"'] =Ext.getCmp('"+id+"').defaultParam;";
			s+="}}},";
			s += "totalProperty:'rowCount',root:'data',id:'id'})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "'beforequery' : function(queryEvent)";
			s += "{var param = queryEvent.combo.getRawValue();this.defaultParam = param;";
			s += "if(queryEvent.query==''){param='';}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:Ext.getCmp('"+id+"').getValue(),start:0},callback:function(r, options, success){Ext.getCmp('"+id+"').setValue(Ext.getCmp('"+id+"').getValue());}});}";
		s += "})";
		return s;
	}
	
	/**
	 * ��װһ������ҳ���combo
	 * TODO
	 * Nov 6, 2008 By chuanyu ou
	 * @param c
	 * @return TODO
	 */
	public static String makeComboForAdd(Component c) {
		String id  = c.getName()+"Combo"; 
		String s = "{";
		s += "xtype:'combo',";
		s += "id :'"+id+"',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "lazyRender: true,";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "displayField: '"+c.getDisplayFiled()+"',";
		s += "valueField :'id',";
		s += "emptyText:'��ѡ��...',";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "typeAhead:true,";
		s += "minChars :50,";
		s += "triggerAction:'all',";
//		s += "queryDelay : 700,";
		s += "name:'"+c.getName()+"',";
		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?clazz="+c.getRelationship()+"',";
			s += "fields: ['id', '"+c.getDisplayFiled()+"'],";
			s += "totalProperty:'rowCount',root:'data',id:'id',";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params['"+c.getName()+"'] == undefined){";
			s+="opt.params['"+c.getDisplayFiled()+"'] =Ext.getCmp('"+id+"').defaultParam;";
			s+="}}}})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "'beforequery' : function(queryEvent)";
			s += "{var param = queryEvent.combo.getRawValue();this.defaultParam = param;var val = queryEvent.combo.getValue();";
			s += "if(queryEvent.query==''){param='';}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
		s += "}";
		return s;
	}
	/**
	 * �����޸ĳ������combox
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
		s += "xtype:'combo',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "id :'"+id+"',";
		s += "width:"+c.getWidth()+",";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "style:'',";//�ؼӴ���
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "lazyRender: true,";
		s += "displayField: '"+c.getDisplayFiled()+"',";
		s += "valueField :'id',";
		s += "emptyText:'��ѡ��...',";
		s += "minChars :50,";
		s += "typeAhead:true,";
		s += "name:'"+c.getName()+"',";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "triggerAction:'all',";
//		s += "queryDelay : 700,";
		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?isAbstract=true&fdiscTable="+fdiscTable+"&clazz="+c.getRelationship()+"',";
			s += "fields:['id','"+c.getDisplayFiled()+"'],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params['"+c.getName()+"'] == undefined){";
			s+="opt.params['"+c.getDisplayFiled()+"'] =Ext.getCmp('"+id+"').defaultParam;";
			s+="}}},";
			s += "totalProperty:'rowCount',root:'data',id:'id'})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "'beforequery' : function(queryEvent){";
			s+="var discValue=Ext.getCmp('"+disccId+"').getValue();";
			s+="Ext.Ajax.request({url:webContext+'/extjs/comboDataAction?discValue='+discValue});";
			s += "var param = queryEvent.combo.getRawValue();this.defaultParam = param;";
			s += "if(queryEvent.query==''){param='';}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
			s += ",initComponent : function() {this.store.load({params:{id:'"+blank(c.getValue())+"',start:0},callback:function(r, options, success){Ext.getCmp('"+id+"').setValue('"+blank(c.getValue())+"');}});}";
		s += "})";
		return s;
	}
	/**
	 *���ڳ������͵��½�combox
	 * @param c
	 * @return
	 */
	public static String makeAbstractComboForAdd(Component c) {
		String id  = c.getName()+"Combo";
		String disccId = c.getDisccId()+"Combo";
		String fdiscTable = c.getFdiscTable();
		c.setDisplayFiled("name");
		String s = "{";
		s += "xtype:'combo',";
		s += "id :'"+id+"',";
		s += "hiddenName: '"+c.getName()+"',";
		s += "width:"+c.getWidth()+",";
		s += "style:'',";//�ؼӴ���
		s += "lazyRender: true,";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "displayField: '"+c.getDisplayFiled()+"',";
		s += "valueField :'id',";
		s += "emptyText:'��ѡ��...',";
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "typeAhead:true,";
		s += "minChars :50,";
		s += "triggerAction:'all',";
//		s += "queryDelay : 700,";
		s += "name:'"+c.getName()+"',";
		s += "store:new Ext.data.JsonStore({url:webContext+'/extjs/comboDataAction?isAbstract=true&fdiscTable="+fdiscTable+"&clazz="+c.getRelationship()+"',";
			s += "fields: ['id', '"+c.getDisplayFiled()+"'],";
			s+="listeners:{beforeload : function(store, opt){";
			s+="if(opt.params['"+c.getName()+"'] == undefined){";
			s+="opt.params['"+c.getDisplayFiled()+"'] =Ext.getCmp('"+id+"').defaultParam;";
			s+="}}},";
			s += "totalProperty:'rowCount',root:'data',id:'id'})";
		s += ",pageSize:10";
		s += ",listeners:{"; 
			s += "'beforequery': function(queryEvent){";
			s+="var discValue=Ext.getCmp('"+disccId+"').getValue();";
			s+="Ext.Ajax.request({url:webContext+'/extjs/comboDataAction?discValue='+discValue});";
			s += "var param = queryEvent.combo.getRawValue(); this.defaultParam = param;var val = queryEvent.combo.getValue();";
			s += "if(queryEvent.query==''){param='';}";
			s += "this.store.load({params:{"+c.getDisplayFiled()+":param,start:0}});return true;}";
			s += "}";
		s += "}";
	
		return s;
	}
	
	public static String makeHtml(Component c) {
		String s = "{";
		s += "fieldLabel:'"+c.getLabel()+"',";
		s += "html :\"<font color=blouse>"+blank(c.getValue())+"</font>\",";
		s += "cls : 'common-text',";
		s += "width:135,";
		s += "style : 'width:150;text-align:right',"; 
		//s += "xtype:'textfield',";
		s += "id:'"+c.getName()+"',";
		s += "colspan:"+c.getColspan()+",";
		s += "rowspan:"+c.getRowspan()+",";
		s += "name:'"+c.getName()+"',";
		s += "style:'',";//�ؼӴ���
		s += "width:"+c.getWidth()+",";
		//s += "readOnly:"+blank(c.isReadOnly())+",";
		//s += "value:'"+blank(c.getValue())+"',"; 
		s += "allowBlank:"+c.getAllowBlank()+","; //������
		s += "validator:"+c.getValidator()+","; //������
		s += "vtype:''";		
		s += "}";
		return s;
	}
	
	public static String makeAnnex(Component c) {
		String s = "{";
		s += "fieldLabel:'" + c.getLabel() + "',";
		s += "xtype:'button',";
		s += "text:'<font color=red>�ϴ�</font>/<font color=green>����</font>',";
		s += "width:'50',";
		// s+="pressed:true,";
		s += "scope:this,";
		s += "handler:function(){";
//add by lee for attachment static page unused bug in 20090708 begin
		s += "var attachmentFlag = Ext.getCmp('" + c.getName()
				+ "').getValue();";
		s += "if(attachmentFlag==''){";
		s += "attachmentFlag = Date.parse(new Date());";
		s += "Ext.getCmp('" + c.getName() + "').setValue(attachmentFlag);";
		s += "var ud=new UpDownLoadFile();";
		s += "ud.getUpDownLoadFileSu(attachmentFlag,'" + c.getColumnId()+ "','" + c.getRelationship() + "');";
		s += "}else{";
		s += "var ud=new UpDownLoadFile();";
		s += "ud.getUpDownLoadFileSu(attachmentFlag,'" + c.getColumnId()
				+ "','" + c.getRelationship() + "');";
		s += "}}}";
		s += ",";
		s += "new Ext.form.Hidden({";
		s += "xtype:'hidden',";
		s += "id:'" + c.getName() + "',";
		s += "name:'" + c.getName() + "',";
		s += "style:'',";// �ؼӴ���
		s += "value:'" + c.getValue() + "',";
//add by lee for attachment static page unused bug in 20090708 end
//remove by lee for attachment static page unused bug in 20090708 begin		
		// s+="var ud=new UpDownLoadFile();";
		// s+="ud.getUpDownLoadFileSu('"+c.getNowtime()+"','"+c.getColumnId()+"','"+c.getRelationship()+"');";
		// s+="}}";
		// s+=",";
		// s += "new Ext.form.Hidden({";
		// s += "xtype:'hidden',";
		// s += "id:'nowtime',";
		// s += "name:'"+c.getName()+"',";
		// s += "style:'',";//�ؼӴ���
		// s += "value:'"+c.getValue()+"',";
		// //s += "allowBlank:"+c.getAllowBlank()+","; //������
		// //s += "validator:"+c.getValidator()+","; //������
		// // s += "fieldLabel:'"+c.getLabel()+"'";
//remove by lee for attachment static page unused bug in 20090708 end
		s += "fieldLabel:'nowtime'";
		s += "})";
		return s;
	}
	
	public static String makeFieldSet(Component c) {
	  String s = "{";
			s += "xtype:'fieldset',";
			s += "title:'"+c.getName()+"',";
			s += "colspan:"+c.getColspan()+",";
			s += "rowspan:"+c.getRowspan()+",";
			s += "layoutConfig:{columns : 4},";
			s += "layoutConfig:{bodyStyle : 'padding:16px'},";
			s += "width:"+c.getWidth()+",";
			s += "layout:'table',";//�ؼӴ���
			s += "items:"+c.getFieldsetItem();
			s += "})";
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
	

	private static String blank(Object s) {
		return s==null?"":s.toString();
	}
}
