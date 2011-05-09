package com.zsgj.info.appframework.extjs.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;

public class CoderForAdd {
	
	//for add tjp
	public static String encode(Map<String, Object> addMap,List<UserTableSetting> columns) {
		String json = "";		
		for(UserTableSetting uts:columns) {  
			String propertyName = uts.getPropertyName();
			String propertyNames = propertyName+"s";
			boolean isDisplay = uts.getIsDisplay().intValue()!=1||uts.getIsHiddenItem().intValue()==1;
			boolean isUpdateItem = uts.getIsUpdateItem()!=null&&uts.getIsUpdateItem().intValue()!=0;
			Integer isMustInput = null;
			
			String cnName = uts.getColumnCnName();
			String width = uts.getLengthForPage();
			
			Column column = uts.getColumn();
			
			String componentStr = "";
			String columnType = column.getSystemMainTableColumnType().getColumnTypeName();
			//String columnType = column.systemMainTableColumnType.columnTypeName
			propertyName = column.getPropertyName();
			String type = column.getValidateType()==null?"":column.getValidateType().getValidateTypeName();
			isMustInput = column.getIsMustInput();
			
			//必填项		
			String mustInput = "";
			if(isMustInput.intValue()==1) {//必填
				mustInput = "true";
			}
			else{//选填					
			}
			
			//校验器		
			String validator = "";
			validator = Validator.get(type);				
			isDisplay = isDisplay||columnType.equalsIgnoreCase("hidden");				

			//组件编码
			Component c = new Component();
			c.setName(propertyName);
			c.setDisplay(isDisplay);
			c.setMustInput(isMustInput.intValue()==1);
			c.setLabel(cnName);
			c.setWidth(width);
			c.setReadOnly(!isUpdateItem);
			c.setValue(null);				
			if(columnType.equalsIgnoreCase("hidden")) {//type = "hidden";
				//componentStr = ComponentCoder.makeHidden(c);//增加时无意义
			}
			else if(columnType.equalsIgnoreCase("text")) {
				componentStr = ComponentCoder.makeTextField(c);
			}
			else if(columnType.equalsIgnoreCase("textArea")) {
				componentStr = ComponentCoder.makeTextArea(c);				
			}
			else if(columnType.equalsIgnoreCase("radio")) {
				componentStr = ComponentCoder.makeRadio(c);										
			}
			else if(columnType.equalsIgnoreCase("multiSelect")) {
				componentStr = ComponentCoder.makeMultiSelect(c);		
			}
			else if(columnType.equalsIgnoreCase("file")) {
				componentStr = ComponentCoder.makeFile(c);
			}
			else if(columnType.equalsIgnoreCase("dateText")) {
				componentStr = ComponentCoder.makeDateText(c);
			}
			else if(columnType.equalsIgnoreCase("yesNoSelect")) {
				componentStr = ComponentCoder.makeYesNoSelect(c);
			}
			else if(columnType.equalsIgnoreCase("select")||columnType.equalsIgnoreCase("radio")) {
				Integer extSelectType = column.getExtSelectType();
				List vList = (List)addMap.get(propertyNames);
				String vId = (String)addMap.get(propertyName);
				String[][] values = new String[vList.size()][3];
									
				for(int i=0;i<vList.size();i++) {
					if(extSelectType == null) {//引用外部数据
						Object bean = (Object)vList.get(i);
						try {
							values[i][0] = (String)BeanUtils.getProperty(bean, "id");
							String beanPropertyName = uts.getForeignTableValueColumn().getPropertyName();
							values[i][1] = (String)BeanUtils.getProperty(bean, beanPropertyName);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}else if(extSelectType.intValue()==2) {//引用外部数据自定义
						Object bean = (Object)vList.get(i);
						try {
							values[i][0] = (String)BeanUtils.getProperty(bean, "id");
							values[i][1] = (String)BeanUtils.getProperty(bean, "extOptionValue");
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
					else {//不引用外部数据
						
						//vList.get(i).getClass();
						//Object vMap = vList.get(i);	
						BeanWrapper bw = new BeanWrapperImpl(vList.get(i));
						values[i][0] = bw.getPropertyValue("id").toString();
						String beanColumnName = uts.getForeignTableValueColumn().getColumnName();
						values[i][1] = bw.getPropertyValue(beanColumnName).toString();
						/*Map vMap = (Map)vList.get(i);						
						values[i][0] = (String)vMap.get("id");
						String beanColumnName = uts.getForeignTableValueColumn().getColumnName();
						values[i][1] = (String)vMap.get(beanColumnName);*/
					}
					if(values[i][0].equals(addMap.get(propertyName))) {
						values[i][2] = "selected";
					}
					else {
						values[i][2] = "";
					}		
					c.setValue(values);
					componentStr = ComponentCoder.makeSelect(c);						
				}
			}				
			//处理componentStr字符串
			if(componentStr.length()!=0) {//？？？？？？
				json += componentStr+",";	
			}
		}
		if(json.endsWith(",")) {
			json = "["+json.substring(0, json.length()-1)+"]";
		}
		//System.out.println("&&&&&&&:"+json);
		return json;
	}
}
