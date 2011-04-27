package com.zsgj.info.appframework.pagemodel.autocreat.servlet;

import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.pagemodel.autocreat.servlet.Component;
import com.zsgj.info.appframework.pagemodel.autocreat.servlet.ComponentGather;

public class CoderForQueryForm {
	@SuppressWarnings("unchecked")
	public static String encode(Map<String, Object> queryMap,List<UserTableQueryColumn> columns) {
	       int labellen = 135;
	       String itemlen = "200";
	       int throulen = 530;
	       String json = "";
		   String hiddenString ="";
		for(UserTableQueryColumn utqc :columns) {		
			Column column = utqc.getColumn();
			@SuppressWarnings("unused")
			String columnName = column.getColumnName();
			String tableName=column.getSystemMainTable().getTableName();
			String propertyName = column.getPropertyName();
			String tablePropertyName=tableName+"$"+propertyName;
			String columnCnName = column.getColumnCnName();
			String width = utqc.getLengthForPage(); 
			Integer isHiddenQueryItem = utqc.getIsHiddenItem();
			boolean isHiddenQuery = isHiddenQueryItem!=null&& isHiddenQueryItem.intValue()==1;
			String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
			//添加的共有属性+++++++++++++++++++++++++++++++++++++++++++++++
			Component c= new Component();
			c.setWidth(itemlen);
			String componentStr="";
			if(!columnTypeName.equalsIgnoreCase("hidden")) {
			       componentStr = "{html : '"+columnCnName+ ":',"+
                                    "cls : 'common-text',"+
                                  "style : 'width:" + labellen + ";text-align:right'},";
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			SystemTableQueryColumn sysQueryColumn = utqc.getSystemTableQueryColumn();
				String matchModeStr = sysQueryColumn.getMatchModeStr();	
				if(columnTypeName.equalsIgnoreCase("hidden")||isHiddenQuery/*|| isHiddenQuery*/) {				//add by peixf	
					hiddenString += ComponentGather.makeHidden(propertyName,columnCnName,itemlen)+",";  //tablePropertyName备注
				}
				else if(columnTypeName.equalsIgnoreCase("text")||columnTypeName.equalsIgnoreCase("textArea")) {		//add by peixf			
					if(matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
						componentStr += ComponentGather.makeTextField(propertyName+"Begin",columnCnName+" 从",itemlen);
						componentStr += ",";
						componentStr += ComponentGather.makeTextField(propertyName+"End",columnCnName+" 到",itemlen); 
					}
					else {
						componentStr += ComponentGather.makeTextField(propertyName,columnCnName,itemlen); 
					}
				}
				else if(columnTypeName.equalsIgnoreCase("dateText")) {
					if(matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
						componentStr += ComponentGather.makeDateText(propertyName+"Begin",columnCnName+" 从",itemlen);
						componentStr += ",";
						componentStr += ComponentGather.makeDateText(propertyName+"End",columnCnName+" 到",itemlen); 
					}
					else {
						componentStr += ComponentGather.makeDateText(propertyName,columnCnName,itemlen); 
					}					
				}
				else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
					componentStr += ComponentGather.makeYesNoSelect(propertyName,columnCnName,itemlen);   
				}
				else if(columnTypeName.equalsIgnoreCase("sexSelect")) {
					componentStr += ComponentGather.makeYesNoSelect(propertyName,columnCnName,itemlen);   
				}
				else if(columnTypeName.equalsIgnoreCase("select")) {
					String foreignPropNames = propertyName+"s";
					Integer extSelectType=column.getExtSelectType();
					if(extSelectType==null){
						List vList = (List)queryMap.get(foreignPropNames);
						c.setName(propertyName);
						c.setLabel(columnCnName);
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
						componentStr += ComponentGather.makeSelect(c);
					}else if(extSelectType==0){
						List vList = (List)queryMap.get(foreignPropNames);
						c.setName(propertyName);
						c.setLabel(columnCnName);
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
						componentStr += ComponentGather.makeSelect(c); 
					}else if(extSelectType==2){
						List vList = (List)queryMap.get(foreignPropNames);
						c.setName(propertyName);
						c.setLabel(columnCnName);
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
						componentStr += ComponentGather.makeSelect(c);
					}								
				}				
			json += componentStr+",";		
		}
		//加上隐藏域
        json+=hiddenString;
		if(json.endsWith(",")) {
			json = "["+json.substring(0, json.length()-1)+"]";
		}
		return json;
	}
}
