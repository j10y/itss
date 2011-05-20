package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.List;
import java.util.Map;

import com.zsgj.info.appframework.extjs.servlet.Component;
import com.zsgj.info.appframework.extjs.servlet.ComponentCoder;
import com.zsgj.info.appframework.extjs.servlet.Validator;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.framework.context.ContextHolder;

/**
 * ��ҳ�������������2�ڿ��Ԥ��
 * @Class Name CoderForSave
 * @Author peixf
 * @Create In 2009-1-1
 */
public class CoderForLook {
	    private static SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
		/**
		 * ����ܵײ㷵�ص�Ԫ���������Ϣ�ͱ�����ת���ɷ���
		 * JSON��ʽ���ַ�������ǰ�˽��б�������ɣ�ͬʱ��ʼ�������������
		 * @Methods Name encode
		 */
		public static String encode(List<PagePanelColumn> pagePanelColumns,
			Map dataMap,boolean forEdit) {
		    String json = "";
		    for (PagePanelColumn uts : pagePanelColumns) {
			Column column = uts.getColumn();
			String propertyName = column.getPropertyName();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String tablePropertyName = tableName + "$"+ propertyName;
			String propertyNames = tablePropertyName + "s";
			boolean isDisplay = uts.getIsDisplay().intValue() != 1;
			
	        boolean isUpdateItem = !(uts.getColumn().getIsUpdateItem() != null && uts
			.getColumn().getIsUpdateItem().intValue() == 0);
	        Integer isMustInput = null;      
	        
			//Column column = uts.getColumn();
			String cnName = column.getColumnCnName();
			String width = column.getLengthForPage();
			String columnCnName = column.getColumnCnName();// ��ͷ����
			Object value = dataMap.get(tablePropertyName);

			String displayFiled = "";
			String valueFiled = "";
			String relationshipClazz = "";
			if (column.getForeignTable() != null) {
				displayFiled = column.getForeignTableValueColumn().getPropertyName();
				valueFiled = column.getForeignTableKeyColumn().getPropertyName();
				relationshipClazz = column.getForeignTable().getClassName();
			}
			String componentStr = "";
			String columnType = column.getSystemMainTableColumnType()
					.getColumnTypeName();

			propertyName = column.getPropertyName();
			String type = column.getValidateType() == null ? "" : column
					.getValidateType().getValidateTypeName();
			isMustInput = column.getIsMustInput();

			// ������
			String allowBlank = "true";

			if (isMustInput.intValue() == 1) {// ����
				allowBlank = "false";
			} else {// ѡ��
				allowBlank = "true";
			}

			// У����
			String validator = Validator.get(type);
			isDisplay = isDisplay || columnType.equalsIgnoreCase("hidden");

			// �������
			Component c = new Component();
			c.setName(tableName+"$"+propertyName);
			c.setId(c.getName()); //ʹ�ñ���$��������ȷ�����idΨһ
			c.setDisplay(isDisplay);
			c.setMustInput(isMustInput.intValue() == 1);
			c.setLabel(cnName);
			c.setWidth(width);
			c.setReadOnly(!isUpdateItem);
			c.setAllowBlank(allowBlank);
			c.setValidator(validator);
			c.setDisplayFiled(displayFiled);
			c.setValueFiled(valueFiled);
			c.setRelationship(relationshipClazz);
			if (forEdit) {
				c.setValue(value);
			}
	
	        if (columnType.equalsIgnoreCase("hidden")) {// type = "hidden";
				componentStr = ComponentCoder.makeHidden(c);
			} else if (columnType.equalsIgnoreCase("text")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("textArea")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("radio")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("multiSelect")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("file")) 
			{
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("multiFile")) {
		         
				SystemMainTable foreignTable = column.getForeignTable();
			
				if(forEdit){//value = "1237983021828";
					if(value!=null){
						c.setNowtime(value.toString());
						c.setValue(value); //�༭ʱvalue����nowtimeֵ
					}else{
						String nowtime = String.valueOf(System.currentTimeMillis());
						c.setNowtime(nowtime);
						//c.setValue(nowtime);//remove by lee for attachment static page unused bug in 20090708
					}
					c.setColumnId(String.valueOf(column.getId()));
					c.setRelationship(foreignTable.getClassName());
				}else{//"1237983021828";//
//remove by lee for attachment static page unused bug in 20090708 begin
//					String nowtime = String.valueOf(System.currentTimeMillis());
//					c.setNowtime(nowtime);
//					c.setValue(nowtime);
//remove by lee for attachment static page unused bug in 20090708 end
					c.setColumnId(String.valueOf(column.getId()));
					c.setRelationship(foreignTable.getClassName());
				}
				
				componentStr = ComponentCoder.makeAnnex(c); //�ĳ����ɶ��ļ��ı����Զ�������
				
			}
			else if (columnType.equalsIgnoreCase("dateText")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {
				//ע�⣬���õĻ���1�ڵ�coder���ĳ�2�ڵ�ҳ�治��������ȷ��ԭ��
				componentStr = ComponentCoder.makeHtml(c);// ͬ��ͨ��select
				String renderStr = "function(value){";
				renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "');";
				renderStr += "var comboStore = comboObject.store;";
				renderStr += "if (comboStore.find('id', value) !=-1) {";
				renderStr += "return comboStore.getAt(comboStore.find('id', value)).get('name');";
				renderStr += "} ";
				renderStr += "else{";
				renderStr += " 	return  value;";
				renderStr += " }";
				renderStr += " }";
				
				
			} else if (columnType.equalsIgnoreCase("select")) {
			    boolean isAbstract = column.getAbstractFlag()!=null && column.getAbstractFlag().intValue()==1;
		        if(isAbstract){
		           Column discColumn = column.getDiscColumn();
		 	       SystemMainTable foreignDiscTable = column.getForeignDiscTable();
		 	       String disccId = tableName + "$" + discColumn.getPropertyName();
		 	       String fdiscTable = foreignDiscTable.getTableName();
		 	       c.setAbstract(isAbstract);
		 	       c.setDisccId(disccId);//�������ͻ����������ֶΣ�ConfigItem$customerType
		 	       c.setFdiscTable(fdiscTable); //�����ֶ����ñ�customerType��
		 	       c.setDisplayFiled("name");
		 	       if(forEdit){
		 	    	   String relationship = null;
		 	    	   if(c.getValue()!=null){
		 	    		  String discValue = c.getValue().toString();
		 	    		  relationship = systemColumnService.findClassNameByDisc(discValue, fdiscTable);
		 	    	   }
		 	    	   c.setRelationship(relationship);
		 	    	  componentStr = ComponentCoder.makeHtml(c); 
		 	       }else{
		 	    	  componentStr = ComponentCoder.makeHtml(c);
		 	       } 
		 	       
		        }else{
		        	if(forEdit){
		        		componentStr = ComponentCoder.makeHtml(c); //�ĳ�makeSelect��Ȼҳ�治����
					}else{
						
						componentStr = ComponentCoder.makeHtml(c);
					}
		        }
				
	 	        
				
			}
			// ����componentStr�ַ���
			if (componentStr.length() != 0) {
				json += componentStr + ",";
			}
			
		}
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		return json;
	}
}