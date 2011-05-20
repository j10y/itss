package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.zsgj.info.appframework.extjs.servlet.Component;
import com.zsgj.info.appframework.extjs.servlet.ComponentCoder;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;

public class CoderForFind {
	public static String encode(Map<String, Object> queryMap,List<UserTableQueryColumn> columns) {
		String json = "";
		for(UserTableQueryColumn utqc :columns) {			
			Column column = utqc.getColumn();
//			String columnName = column.getColumnName();
			String tableName=column.getSystemMainTable().getTableName();
			String propertyName = column.getPropertyName();
			String tablePropertyName=tableName+"$"+propertyName;
			String columnCnName = column.getColumnCnName();
			String width = utqc.getLengthForPage(); 
			//begin by peixf
			Integer isHiddenQueryItem = utqc.getIsHiddenItem();
			boolean isHiddenQuery = isHiddenQueryItem!=null&& isHiddenQueryItem.intValue()==1;
			//end
			String componentStr = "";
			SystemTableQueryColumn sysQueryColumn = utqc.getSystemTableQueryColumn();
	//		if(sysQueryColumn.getMainTableColumn()!=null) {//?原来不是这样参考jsp
				String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
				String matchModeStr = sysQueryColumn.getMatchModeStr();	
				if(columnTypeName.equalsIgnoreCase("hidden")||isHiddenQuery/*|| isHiddenQuery*/) {				//add by peixf	
					componentStr = ComponentCoder.makeHidden(tablePropertyName,columnCnName,width); 
				}
				else if(columnTypeName.equalsIgnoreCase("text")||columnTypeName.equalsIgnoreCase("textArea")) {		//add by peixf			
					if(matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
						componentStr = ComponentCoder.makeTextField(tablePropertyName+"Begin",columnCnName+" 从",width);
						componentStr += ",";
						componentStr += ComponentCoder.makeTextField(tablePropertyName+"End",columnCnName+" 到",width); 
					}
					else {
						componentStr = ComponentCoder.makeTextField(tablePropertyName,columnCnName,width); 
					}
				}
				else if(columnTypeName.equalsIgnoreCase("dateText")) {
					if(matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
						componentStr = ComponentCoder.makeDateText(tablePropertyName+"Begin",columnCnName+" 从",width);
						componentStr += ",";
						componentStr += ComponentCoder.makeDateText(tablePropertyName+"End",columnCnName+" 到",width); 
					}
					else {
						componentStr = ComponentCoder.makeDateText(tablePropertyName,columnCnName,width); 
					}					
				}
				else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
					componentStr = ComponentCoder.makeYesNoSelect(tablePropertyName,columnCnName,width);   
				}
				else if(columnTypeName.equalsIgnoreCase("sexSelect")) {
					componentStr = ComponentCoder.makeYesNoSelect(tablePropertyName,columnCnName,width);   
				}
				else if(columnTypeName.equalsIgnoreCase("select")) {
					String foreignPropNames = propertyName+"s";
					Integer extSelectType=column.getExtSelectType();
					if(extSelectType==null){
						List vList = (List)queryMap.get(foreignPropNames);
						Component c = new Component();
						c.setName(tablePropertyName);
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
					}else if(extSelectType==0){
						List vList = (List)queryMap.get(foreignPropNames);
						Component c = new Component();
						c.setName(tablePropertyName);
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
						c.setName(tablePropertyName);
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
				}				
			json += componentStr+",";		
		}
		if(json.endsWith(",")) {
			json = "["+json.substring(0, json.length()-1)+"]";
		}
		return json;
	}
}
