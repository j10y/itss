package com.digitalchina.info.appframework.extjs.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;

public class CoderForEdit {

	//for edit
	public static String encode(Map<String, Object> editMap,List<UserTableSetting> columns) {
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
			Object value = editMap.get(propertyName);
			
			String componentStr = "";
			String columnType = column.getSystemMainTableColumnType().getColumnTypeName();
			//String columnType = column.systemMainTableColumnType.columnTypeName
			propertyName = column.getPropertyName();
			String type = column.getValidateType()==null?"":column.getValidateType().getValidateTypeName();
			isMustInput = column.getIsMustInput();
			
			//������		
			String mustInput = "";
			if(isMustInput.intValue()==1) {//����
				mustInput = "true";
			}
			else{//ѡ��					
			}
			
			//У����		
			String validator = "";
			validator = Validator.get(type);				
			isDisplay = isDisplay||columnType.equalsIgnoreCase("hidden");				

			//�������
			Component c = new Component();
			c.setName(propertyName);
			c.setDisplay(isDisplay);
			c.setMustInput(isMustInput.intValue()==1);
			c.setLabel(cnName);
			c.setWidth(width);
			c.setReadOnly(!isUpdateItem);
			c.setValue(value);				
			if(columnType.equalsIgnoreCase("hidden")) {//type = "hidden";
				componentStr = ComponentCoder.makeHidden(c);
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
			else if(columnType.equalsIgnoreCase("yesNoSelect")) {//����ģ�ʹ洢��map��
				Integer extSelectType = column.getExtSelectType();
				List vList = (List)editMap.get(propertyNames);
				String vId = (String)editMap.get(propertyName);
				String[][] values = new String[vList.size()][3];
				for(int i=0;i<vList.size();i++) {
					Map vMap = (Map)vList.get(i);						
					values[i][0] = (String)vMap.get("key");
					values[i][1] = (String)vMap.get("value");			
					if(values[i][0].equals(editMap.get(propertyName))) {
						values[i][2] = "selected";
					}
					else {
						values[i][2] = "";
					}	
				}
				c.setValue(values);
				componentStr = ComponentCoder.makeSelect(c);//ͬ��ͨ��select
			}
			else if(columnType.equalsIgnoreCase("select")||columnType.equalsIgnoreCase("radio")) {//����ģ�ʹ洢��bean��
				Integer extSelectType = column.getExtSelectType();
				List vList = (List)editMap.get(propertyNames);
				//String vId = (String)editMap.get(propertyName);
				String[][] values = new String[vList==null?0:vList.size()][3];				
				for(int i=0;vList!=null&&i<vList.size();i++) {
					if(extSelectType == null) {//�����ⲿ����
						Object bean = (Object)vList.get(i);
						try {
							values[i][0] = (String)BeanUtils.getProperty(bean, "id");
							String beanPropertyName = uts.getForeignTableValueColumn().getPropertyName();
							values[i][1] = (String)BeanUtils.getProperty(bean, beanPropertyName);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}else if(extSelectType.intValue()==2) {//�����ⲿ�����Զ���
						Object bean = (Object)vList.get(i);
						try {
							values[i][0] = (String)BeanUtils.getProperty(bean, "id");
							values[i][1] = (String)BeanUtils.getProperty(bean, "extOptionValue");
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
					else {//�������ⲿ����
						vList.get(i).getClass();
						Object vMap = vList.get(i);	
						BeanWrapper bw = new BeanWrapperImpl(vList.get(i));
						values[i][0] = bw.getPropertyValue("id").toString();
						String beanColumnName = uts.getForeignTableValueColumn().getColumnName();
						values[i][1] = bw.getPropertyValue(beanColumnName).toString();
//						values[i][0] = (String)vMap.get("id");
//						String beanColumnName = uts.getForeignTableValueColumn().getColumnName();
//						values[i][1] = (String)vMap.get(beanColumnName);
					}
					Object vId = editMap.get(propertyName);
					if(values[i][0].equals(vId==null?"":vId.toString())) {
						values[i][2] = "selected";
					}
					else {
						values[i][2] = "";
					}		
					c.setValue(values);
					componentStr = ComponentCoder.makeSelect(c);						
				}
			}				
			//����componentStr�ַ���
			if(componentStr.length()!=0) {//������������
				json += componentStr+",";	
			}
		}
		if(json.endsWith(",")) {
			json = "["+json.substring(0, json.length()-1)+"]";
		}
		return json;
	}
	
}
