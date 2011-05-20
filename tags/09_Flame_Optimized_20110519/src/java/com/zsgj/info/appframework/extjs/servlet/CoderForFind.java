package com.zsgj.info.appframework.extjs.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;

public class CoderForFind {
	//for query
	public static String encode(Map<String, Object> queryMap,List<UserTableQueryColumn> columns) {
		String json = "";
		for(UserTableQueryColumn utqc :columns) {			
			Column column = utqc.getColumn();
			String columnName = column.getColumnName();
			String propertyName = column.getPropertyName();
			String columnCnName = column.getColumnCnName();
			String width = utqc.getLengthForPage(); 
			//begin by peixf
			//after by sujs
			String displayField = "";
			String valueField = "";
			String foreignClazz = "";
			if (column.getForeignTable() != null) {
				displayField = column.getForeignTableValueColumn().getPropertyName();
				valueField = column.getForeignTableKeyColumn().getPropertyName();
				foreignClazz = column.getForeignTable().getClassName();
			}
			
			
			Integer isHiddenQueryItem = utqc.getIsHiddenItem();
			boolean isHiddenQuery = isHiddenQueryItem!=null&& isHiddenQueryItem.intValue()==1;
			//end
			String componentStr = "";
			SystemTableQueryColumn sysQueryColumn = utqc.getSystemTableQueryColumn();
	//		if(sysQueryColumn.getMainTableColumn()!=null) {//?原来不是这样参考jsp
				String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
				String matchModeStr = sysQueryColumn.getMatchModeStr();	
				if(columnTypeName.equalsIgnoreCase("hidden")||isHiddenQuery/*|| isHiddenQuery*/) {				//add by peixf	
					componentStr = ComponentCoder.makeHidden(propertyName,columnCnName,width); 
				}
				else if(columnTypeName.equalsIgnoreCase("text")||columnTypeName.equalsIgnoreCase("textArea")) {		//add by peixf			
					if(matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
						componentStr = ComponentCoder.makeTextField(propertyName+"Begin",columnCnName+" 从",width);
						componentStr += ",";
						componentStr += ComponentCoder.makeTextField(propertyName+"End",columnCnName+" 到",width); 
					}
					else {
						componentStr = ComponentCoder.makeTextField(propertyName,columnCnName,width); 
					}
				}
				else if(columnTypeName.equalsIgnoreCase("dateText")) {
					if(matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
						componentStr = ComponentCoder.makeDateText(propertyName+"Begin",columnCnName+" 从",width);
						componentStr += ",";
						componentStr += ComponentCoder.makeDateText(propertyName+"End",columnCnName+" 到",width); 
					}
					else {
						componentStr = ComponentCoder.makeDateText(propertyName,columnCnName,width); 
					}					
				}
				else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
					componentStr = ComponentCoder.makeYesNoSelect(propertyName,columnCnName,width);   
				}
				else if(columnTypeName.equalsIgnoreCase("sexSelect")) {
					componentStr = ComponentCoder.makeSexSelect(propertyName,columnCnName,width);   
				}
				else if (columnTypeName.equalsIgnoreCase("radio")) {
					Component c = new Component();
					c.setName(propertyName);
					c.setId(c.getName()); //使用表名$属性名称确保组件id唯一
					c.setLabel(columnCnName);
					c.setWidth(width);
					c.setDisplayFiled(displayField);
					c.setValueFiled(valueField);
					Integer bigFlag = column.getBigFlag();
					Integer columnSum = column.getColumnSum();
					if(bigFlag!=null && bigFlag.intValue()==1){
						c.setSingleLine(true);
					}
					if(columnSum!=null){
						c.setColumns(columnSum);
					}
					List sourceList = (List) queryMap.get(propertyName+"s");
					String displaykey= column.getForeignTableKeyColumn().getPropertyName();
					String displayvalue = column.getForeignTableValueColumn().getPropertyName();
					for(Object ob:sourceList){
						BeanWrapper bw = new BeanWrapperImpl(ob);
						String id=bw.getPropertyValue(displaykey).toString();
						String value1=null;
						if(bw.getPropertyValue(displayvalue)!=null){
							value1=(String) bw.getPropertyValue(displayvalue).toString();
						}
						Component item = new Component();
						item.setId(id);
						item.setName(c.getName());
						item.setLabel(value1);
						c.getItems().add(item);
					}
					componentStr = ComponentCoder.makeRadioGroup(c);
				}
				else if(columnTypeName.equalsIgnoreCase("select")) {
					String foreignPropNames = propertyName+"s";
					Integer extSelectType=column.getExtSelectType();
					if(extSelectType==null){
						List vList = (List)queryMap.get(foreignPropNames);
						Component c = new Component();
						c.setName(propertyName);
						c.setLabel(columnCnName);
						c.setWidth(width);
						c.setDisplayFiled(displayField);
						c.setValueFiled(valueField);
						c.setRelationship(foreignClazz);
						String[][] values = new String[vList.size()][3];
						for(int i=0;i<values.length;i++) {
							Object bean = (Object)vList.get(i);
							try {
								values[i][0] = BeanUtils.getProperty(bean, "id");
								values[i][1] = BeanUtils.getProperty(bean, column.getForeignTableValueColumn().getPropertyName());
								values[i][2] = "";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						c.setValue(values);
						componentStr = ComponentCoder.makeSelect(c);
					}else if(extSelectType==0){
						List vList = (List)queryMap.get(foreignPropNames);
						Component c = new Component();
						c.setName(propertyName);
						c.setLabel(columnCnName);
						c.setWidth(width);
						String[][] values = new String[vList.size()][3];
						for(int i=0;i<values.length;i++) {
							Object bean = (Object)vList.get(i);
							try {
								values[i][0] = BeanUtils.getProperty(bean, "id");
								values[i][1] = BeanUtils.getProperty(bean, column.getForeignTableValueColumn().getPropertyName());
								values[i][2] = "";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						c.setValue(values);
						componentStr = ComponentCoder.makeSelect(c);
					}else if(extSelectType==2){
						List vList = (List)queryMap.get(foreignPropNames);
						Component c = new Component();
						c.setName(propertyName);
						c.setLabel(columnCnName);
						c.setWidth(width);
						String[][] values = new String[vList.size()][3];
						for(int i=0;i<values.length;i++) {
							Object bean = (Object)vList.get(i);
							try {
								values[i][0] = BeanUtils.getProperty(bean, "id");
								values[i][1] = BeanUtils.getProperty(bean, "extOptionValue");
								values[i][2] = "";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						c.setValue(values);
						componentStr = ComponentCoder.makeSelect(c);
					}
//					Integer level = column.getForeignTable().getLevel();
//					if(level == null||level.intValue()==1) {
//						List vList = (List)queryMap.get(foreignPropNames);
//						Component c = new Component();
//						c.setName(propertyName);
//						c.setLabel(columnCnName);
//						c.setWidth(width);
//						String[][] values = new String[vList.size()][3];
//						for(int i=0;i<values.length;i++) {
//							Object bean = (Object)vList.get(i);
//							try {
//								values[i][0] = BeanUtils.getProperty(bean, "id");
//								values[i][1] = BeanUtils.getProperty(bean, column.getForeignTableValueColumn().getPropertyName());
//								values[i][2] = "";
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//						c.setValue(values);
//						componentStr = ComponentCoder.makeSelect(c);
//					}
//					else if(level.intValue()==2) {
//						String parentForeignTableNames = "parent"+column.getForeignTable().getTableName()+"s";
//					}										
				}				
	//		}
			json += componentStr+",";		
		}
		if(json.endsWith(",")) {
			json = "["+json.substring(0, json.length()-1)+"]";
		}
		return json;
	}
}
