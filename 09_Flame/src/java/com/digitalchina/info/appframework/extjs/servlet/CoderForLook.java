package com.digitalchina.info.appframework.extjs.servlet;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.framework.context.ContextHolder;

public class CoderForLook {
	private static SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
	// for save
	@SuppressWarnings("unchecked")
	public static String encode(Map<String, Object> dataMap,
			List<UserTableSetting> columns, boolean forEdit) {
		String json = "";
		for (UserTableSetting uts : columns) {
			SystemMainTable smt = uts.getColumn().getSystemMainTable();
			String tableName = smt.getTableName();
			String className = smt.getClassName();
			String propertyName = uts.getPropertyName();
			String propertyNames = propertyName + "s";
			boolean isDisplay = uts.getIsDisplay().intValue() != 1;
					//|| uts.getIsHiddenItem().intValue() == 1;
			boolean isUpdateItem = uts.getIsUpdateItem() != null
					&& uts.getIsUpdateItem().intValue() != 0;
			Integer isMustInput = null;

			String cnName = uts.getColumnCnName();
			//String width = uts.getLengthForPage();
			Column column = uts.getColumn();
			String width = column.getLengthForPage();
			String height = column.getHeightForPage();
			Object value = dataMap.get(propertyName);

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
			// String columnType =
			// column.systemMainTableColumnType.columnTypeName
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
			c.setName(propertyName);//
			c.setDisplay(isDisplay);
			c.setMustInput(isMustInput.intValue() == 1);
			c.setLabel(cnName);
			c.setWidth(width);
			c.setHeight(height);
			c.setReadOnly(true);
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
			} else if (columnType.equalsIgnoreCase("file")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("dateText")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {// ����ģ�ʹ洢��map��
				Integer extSelectType = column.getExtSelectType();
				Map vMap = (Map) dataMap.get(propertyNames);
				String[][] values = new String[2][3];
				for (int i = 0; i < 2; i++) {
					// Map vMap = (Map)vList.get(i);
					values[i][0] = String.valueOf(i);
					values[i][1] = (String) vMap.get(String.valueOf(i));
					if (forEdit
							&& values[i][0].equals(dataMap.get(propertyName))) {
						values[i][2] = "selected";
					} else {
						values[i][2] = "";
					}
				}
				c.setValue(values);
				componentStr = ComponentCoder.makeHtml(c);// ͬ��ͨ��select
			} else if (columnType.equalsIgnoreCase("select")) {// ����ģ�ʹ洢��bean��
				
				 boolean isAbstract = column.getAbstractFlag()!=null && column.getAbstractFlag().intValue()==1;
			        if(isAbstract){
			           Column discColumn = column.getDiscColumn();
			 	       SystemMainTable foreignDiscTable = column.getForeignDiscTable();
			 	       String disccId =  tableName + "$"+ discColumn.getPropertyName();
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
				
				
//				if(forEdit){
//					componentStr = ComponentCoder.makeComboForEdit(c);
//				}else{
//					componentStr = ComponentCoder.makeComboForAdd(c);
//				}
				
				
				
				
			} else if(columnType.equalsIgnoreCase("extSelect")) {//����ģ�ʹ洢��bean��			
				//componentStr = ComponentCoder.makeExtSelect(c);
			}	
			// ����componentStr�ַ���
			if (componentStr.length() != 0) {// ������������
				json += componentStr + ",";
			}
			
		}
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		return json;
	}

}
