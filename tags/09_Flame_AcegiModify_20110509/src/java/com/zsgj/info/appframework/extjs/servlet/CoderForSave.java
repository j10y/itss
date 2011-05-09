package com.zsgj.info.appframework.extjs.servlet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.pagemodel.servlet.ContextFilter;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.util.PropertiesUtil;

public class CoderForSave {
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

			// 必填项
			String allowBlank = "true";

			if (isMustInput.intValue() == 1) {// 必填
				allowBlank = "false";
			} else {// 选填
				allowBlank = "true";
			}

			// 校验器
			String validator = Validator.get(type);
			isDisplay = isDisplay || columnType.equalsIgnoreCase("hidden");

			// 组件编码
			Component c = new Component();
			c.setName(propertyName);//
			c.setDisplay(isDisplay);
			c.setMustInput(isMustInput.intValue() == 1);
			c.setLabel(cnName);
			c.setWidth(width);
			c.setHeight(height);
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
				componentStr = ComponentCoder.makeTextField(c);
			} else if (columnType.equalsIgnoreCase("textArea")) {
				componentStr = ComponentCoder.makeTextArea(c);
			} else if (columnType.equalsIgnoreCase("radio")) {
				componentStr = ComponentCoder.makeRadio(c);
			} else if (columnType.equalsIgnoreCase("multiSelect")) {
				componentStr = ComponentCoder.makeMultiSelect(c);
			} else if (columnType.equalsIgnoreCase("file")) {
				componentStr = ComponentCoder.makeFile(c);
			} else if (columnType.equalsIgnoreCase("dateText")) {
				componentStr = ComponentCoder.makeDateText(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {// 数据模型存储于map中
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
				componentStr = ComponentCoder.makeSelect(c);// 同普通的select
			} else if (columnType.equalsIgnoreCase("sexSelect")) {// 数据模型存储于map中
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
				componentStr = ComponentCoder.makeSelect(c);// 同普通的select
			} else if (columnType.equalsIgnoreCase("select")) {// 数据模型存储于bean中
				
				 boolean isAbstract = column.getAbstractFlag()!=null && column.getAbstractFlag().intValue()==1;
			        if(isAbstract){
			           Column discColumn = column.getDiscColumn();
			 	       SystemMainTable foreignDiscTable = column.getForeignDiscTable();
			 	       String disccId =  tableName + "$"+ discColumn.getPropertyName();
			 	       String fdiscTable = foreignDiscTable.getTableName();
			 	       c.setAbstract(isAbstract);
			 	       c.setDisccId(disccId);//如隶属客户引用区分字段：ConfigItem$customerType
			 	       c.setFdiscTable(fdiscTable); //区分字段引用表：customerType表
			 	       c.setDisplayFiled("name");
			 	       if(forEdit){
			 	    	   String relationship = null;
			 	    	   if(c.getValue()!=null){
			 	    		  String discValue = c.getValue().toString();
			 	    		  relationship = systemColumnService.findClassNameByDisc(discValue, fdiscTable);
			 	    	   }
			 	    	   c.setRelationship(relationship);
			 	    	   componentStr=ComponentCoder.makeAbstractComboForEdit(c); 
			 	       }else{
			 	    	   componentStr=ComponentCoder.makeAbstractComboForAdd(c);
			 	       } 
			 	       
			        }else{
			        	if(forEdit){
							componentStr = ComponentCoder.makeComboForEdit(c); //改成makeSelect居然页面不正常
						}else{
							
							componentStr = ComponentCoder.makeComboForAdd(c);
						}
			        }
//				if(forEdit){
//					componentStr = ComponentCoder.makeComboForEdit(c);
//				}else{
//					componentStr = ComponentCoder.makeComboForAdd(c);
//				}
				
			} else if (columnType.equalsIgnoreCase("file")) {
				// 以下代码提供上传文件参考使用
				String uploadUrl = column.getUploadUrl();
				String fileNamePrefix = column.getFileNamePrefix();
				SystemMainTableColumn fileNameColumn = column
						.getFileNameColumn();
				SystemMainTableColumn systemFileNameColumn = column
						.getSystemFileNameColumn();
				// fileName
				String fileNamePropertyName = fileNameColumn.getPropertyName();
				// systemFileName
				String sysFileNamePropertyName = systemFileNameColumn
						.getPropertyName();
				String text = "" + dataMap.get(propertyName + "Text");
				String link = "" + dataMap.get(propertyName + "Link");
				link = link.replaceAll("\\\\", "/");
			
				String webRoot = (String) dataMap.get("webRoot");
				
				link = webRoot + link;								
				if(text==null||text.equals("null")||text.trim().equals("")) {
					link = "无";
				}
				else {
					link = "<a href=\"" + link + "\" target=\"_blank\">" + text
					+ "</a>";
				}
				c.setLink(link);
				c.setValue(value);
				componentStr = ComponentCoder.makeFile(c);
				
			}else if (columnType.equalsIgnoreCase("multiFile")) {
				SystemMainTable foreignTable = column.getForeignTable();
				
				if(forEdit){//value = "1237983021828";
					if(value!=null){
						c.setNowtime(value.toString());
						c.setValue(value); //编辑时value既是nowtime值
					}else{
						String nowtime = String.valueOf(System.currentTimeMillis());
						c.setNowtime(nowtime);
					}
					c.setColumnId(String.valueOf(column.getId()));
					c.setRelationship(foreignTable.getClassName());
				}else{//"1237983021828";//
					String nowtime = String.valueOf(System.currentTimeMillis());
					c.setNowtime(nowtime);
					c.setColumnId(String.valueOf(column.getId()));
					c.setRelationship(foreignTable.getClassName());
				}
				
				componentStr = ComponentCoder.makeAnnex(c); //改成生成多文件文本，自动加链接
				
			}else if(columnType.equalsIgnoreCase("extSelect")) {//数据模型存储于bean中			
				//componentStr = ComponentCoder.makeExtSelect(c);
			}	
			// 处理componentStr字符串
			if (componentStr.length() != 0) {// ？？？？？？
				json += componentStr + ",";
			}
			
		}
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		return json;
	}

}
