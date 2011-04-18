package com.digitalchina.info.appframework.extjs.servlet;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;

public class CoderForList {
	//for query

	public static String encode(List<UserTableSetting> userVisibleColumns,List<Map<String, Object>> listData,Long total) {
		String json = "";
		for(Map<String,Object> item :listData) {
			String dataItem = "";
			for(UserTableSetting uts : userVisibleColumns) {
				String columnCnName = uts.getColumn().getColumnCnName();//表头标题
				//SystemMainTableColumn mainTableColumn = uts.getMainTableColumn();	
				Column column=uts.getColumn();
				
					String propertyName = column.getPropertyName();//
					String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
					if(columnTypeName.equalsIgnoreCase("hidden")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
	//	value				${item.id}
					}
					else if(columnTypeName.equalsIgnoreCase("text")||
							columnTypeName.equalsIgnoreCase("dateText")||
							columnTypeName.equalsIgnoreCase("radio")) {

						//SystemMainTable fTable = column.getSystemMainTable();
					    //String fclasString = fTable.getClassName();
					    
					    Object value = null;
//						if(fclasString.equalsIgnoreCase("com.digitalchina.info.framework.security.entity.UserInfo")){
//					    	value = item.get("realNameAndDept");
//					    }else{
					    	 value = item.get(propertyName);
//					    }
						value = item.get(propertyName);
						
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("select")) {
						//?????
						//String foreignPropertyName = column.getForeignTableValueColumn().getPropertyName();
						Object value = item.get(propertyName);
						if(value==null) value="";
//						Integer isAbstract = column.getAbstractFlag();
//						if(isAbstract!=null&& isAbstract.intValue()==1){
//							propertyName = "name";
//						}
						dataItem += ""+propertyName+":\""+value+"\",";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
	//	value				${item[column.mainTableColumn.propertyName]}
					}	
					else {//其他
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
					}						
			}
			if(dataItem.endsWith(",")) {
				dataItem = dataItem.substring(0,dataItem.length()-1);
			}
			dataItem = "{"+dataItem+"},";	
			json += dataItem;
		}
		if(json.endsWith(",")) {
			json = json.substring(0,json.length()-1);
		}
		json = "{success: true, rowCount:"+total+",data:["+json+"]}";
		return json;
	}
	
	
	public static String encodeJson(Class clazz, List<Map<String, Object>> listData, Long total) {
		String json = "";
		for(Map<String,Object> item :listData) {
			String dataItem = "";
			 Method[] ms = clazz.getMethods();
		        for(int i=0; i<ms.length; i++) {
		            String name = ms[i].getName();
		            if(name.startsWith("set")) {
		                Class[] cc = ms[i].getParameterTypes();
		                if(cc.length==1) {
		                    String type = cc[0].getName(); // parameter type
		                   
		                    try {
		                        String propertyName = Character.toLowerCase(name.charAt(3)) + name.substring(4);
							 
								if(type.equalsIgnoreCase("java.lang.String")) {
									Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
									
								}
								else if(type.equals("int") || type.equals("java.lang.Integer")) {
									Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
				
								}
								else if(type.equals("long") || type.equals("java.lang.Long")) {
									Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
				
								}
	                            else if(type.equals("boolean") || type.equals("java.lang.Boolean")) {
									Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
				
	                            }
	                            else if(type.equals("float") || type.equals("java.lang.Float")) {
	                            	Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
	                            }
	                            else if(type.equals("double") || type.equals("java.lang.Double")) {
	                            	Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
	                            }
	                            else if(type.equals("java.util.Date")) {
	                            	Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
	                            }
								
								else {//其他
									Class fclazz = Class.forName(type);
	 	                        	Object object = clazz.newInstance();
									
									
									Object value = item.get(propertyName);
									if(value==null) value="";
									dataItem += ""+propertyName+":\""+value+"\",";
								}						
		                    }//end catch
		                    catch(Exception e) {
		                    }
		                }
		            }
		        }
		    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			if(dataItem.endsWith(",")) {
				dataItem = dataItem.substring(0,dataItem.length()-1);
			}
			dataItem = "{"+dataItem+"},";	
			json += dataItem;
		}
		if(json.endsWith(",")) {
			json = json.substring(0,json.length()-1);
		}
		json = "{success: true, rowCount:"+total+",data:["+json+"]}";
		return json;
	}
	
	public static String encodeAbs(List<UserTableSetting> userVisibleColumns,List<Map<String, Object>> listData,Long total) {
		String json = "";
		for(Map<String,Object> item :listData) {
			String dataItem = "";
			for(UserTableSetting uts : userVisibleColumns) {
				String columnCnName = uts.getColumn().getColumnCnName();//表头标题
				//SystemMainTableColumn mainTableColumn = uts.getMainTableColumn();	
				Column column=uts.getColumn();
				
					String propertyName = column.getPropertyName();//
					String propertyNameKey = propertyName;
					Integer uniqueFlag = column.getUniqueFlag();
					if(uniqueFlag!=null&& uniqueFlag.intValue()==1){
						propertyNameKey = "name";
					}
					String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
					if(columnTypeName.equalsIgnoreCase("hidden")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyNameKey+":\""+value+"\",";
	//	value				${item.id}
					}
					else if(columnTypeName.equalsIgnoreCase("text")||
							columnTypeName.equalsIgnoreCase("dateText")||
							columnTypeName.equalsIgnoreCase("radio")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyNameKey+":\""+value+"\",";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("select")) {
						//?????
						//String foreignPropertyName = column.getForeignTableValueColumn().getPropertyName();
						Object value = item.get(propertyName);
						if(value==null) value="";
						
						dataItem += ""+propertyNameKey+":\""+value+"\",";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyNameKey+":\""+value+"\",";
	//	value				${item[column.mainTableColumn.propertyName]}
					}	
					else {//其他
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyNameKey+":\""+value+"\",";
					}						
			}
			if(dataItem.endsWith(",")) {
				dataItem = dataItem.substring(0,dataItem.length()-1);
			}
			dataItem = "{"+dataItem+"},";	
			json += dataItem;
		}
		if(json.endsWith(",")) {
			json = json.substring(0,json.length()-1);
		}
		json = "{success: true, rowCount:"+total+",data:["+json+"]}";
		return json;
	}
	/**
	 * 为分页下拉列表提供数据
	 * @Methods Name encodeForComboList
	 * @Create In Jul 16, 2009 By lee
	 * @param userVisibleColumns
	 * @param listData
	 * @param total
	 * @return String
	 */
	public static String encodeForComboList(List<SystemMainTableColumn> visibleColumns,List<Map<String, Object>> listData,Long total) {
		String json = "";
//		String nullItem = "";
//		for(UserTableSetting uts : userVisibleColumns) {
//			String columnCnName = uts.getColumn().getColumnCnName();//表头标题
//			Column column=uts.getColumn();
//			String propertyName = column.getPropertyName();//
//			if("id".equals(propertyName)){
//				nullItem += ""+propertyName+":\"\",";
//			}else{
//				nullItem += ""+propertyName+":\"无\",";
//			}
//		}
//		nullItem = nullItem.substring(0,nullItem.length()-1);
//		nullItem = "{"+nullItem+"},";	
//		json += nullItem;
		for(Map<String,Object> item :listData) {
			String dataItem = "";

			for(SystemMainTableColumn column : visibleColumns) {
				String columnCnName = column.getColumnCnName();//表头标题
				//SystemMainTableColumn mainTableColumn = uts.getMainTableColumn();	
				//Column column=uts.getColumn();
				
					String propertyName = column.getPropertyName();//
					String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
					if(columnTypeName.equalsIgnoreCase("hidden")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
					}
					else if(columnTypeName.equalsIgnoreCase("text")||
							columnTypeName.equalsIgnoreCase("dateText")||
							columnTypeName.equalsIgnoreCase("radio")) {
					    Object value = null;
						value = item.get(propertyName);
						
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
					}
					else if(columnTypeName.equalsIgnoreCase("select")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
					}
					else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
					}else {//其他
						Object value = item.get(propertyName);
						if(value==null) value="";
						dataItem += ""+propertyName+":\""+value+"\",";
					}						
			}
			if(dataItem.endsWith(",")) {
				dataItem = dataItem.substring(0,dataItem.length()-1);
			}
			dataItem = "{"+dataItem+"},";	
			json += dataItem;
		}

		if(json.endsWith(",")) {
			json = json.substring(0,json.length()-1);
		}
		json = "{success: true, rowCount:"+total+",data:["+json+"]}";
		return json;
	}
}
