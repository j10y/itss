package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.extjs.servlet.Component;
import com.zsgj.info.appframework.extjs.servlet.ComponentCoder;
import com.zsgj.info.appframework.extjs.servlet.Validator;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;

/**
 * 将pagePanelColumn数据封装成JSON字符串的编译器
 * @Methods Name encode
 * @Create In Dec 5, 2008 By lee
 */
public class CoderForAdd {
	public static String encode(Map<String, Object> addMap,List<PagePanelColumn> columns) {
		String json = "";		
		for(PagePanelColumn ppc:columns) {
			Column tableColumn = ppc.getColumn();
			//String mainTableName = tableColumn.getSystemMainTable().getName();
			String mainTableName = ppc.getSystemMainTable().getTableName();
			String tablePropertyName = mainTableName+"$"+tableColumn.getPropertyName();
			String tablePropertyNames = tablePropertyName+"s";
			boolean isDisplay = ppc.getIsDisplay().intValue()!=1||tableColumn.getIsHiddenItem().intValue()==1;
			boolean isUpdateItem = tableColumn.getIsUpdateItem()!=null&&tableColumn.getIsUpdateItem().intValue()!=0;
			Integer isMustInput = null;
			
			String cnName = tableColumn.getColumnCnName();
			String width = tableColumn.getLengthForPage();
			String componentStr = "";
			String columnType = tableColumn.getSystemMainTableColumnType().getColumnTypeName();
//			String type = tableColumn.getValidateType()==null?"":tableColumn.getValidateType().getValidateTypeName();
			isMustInput = ppc.getIsMustInput();
			
			//必填项		
//			String mustInput = "";
//			if(isMustInput.intValue()==1) {//必填
//				mustInput = "true";
//			}
//			else{//选填					
//			}
			
			//校验器		
//			String validator = "";
//			validator = Validator.get(type);				
			isDisplay = isDisplay||columnType.equalsIgnoreCase("hidden");				

			//组件编码
			Component c = new Component();
			c.setName(tablePropertyName);
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
				Integer bigFlag = tableColumn.getBigFlag();
				Integer columnSum = tableColumn.getColumnSum();
				if(bigFlag!=null && bigFlag.intValue()==1){
					c.setSingleLine(true);
				}
				if(columnSum!=null){
					c.setColumns(columnSum);
				}
				List sourceList = (List) addMap.get(tablePropertyName+"s");
				String displaykey= tableColumn.getForeignTableKeyColumn().getPropertyName();
				String displayvalue = tableColumn.getForeignTableValueColumn().getPropertyName();
				for(Object ob:sourceList){
					BeanWrapper bw = new BeanWrapperImpl(ob);
					String id=bw.getPropertyValue(displaykey).toString();
					String value1=(String) bw.getPropertyValue(displayvalue).toString();
					Component item = new Component();
					item.setId(id);
					item.setName(c.getName());
					item.setLabel(value1);
					c.getItems().add(item);
				}
				componentStr = ComponentCoder.makeRadioGroup(c);										
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
			else if(columnType.equalsIgnoreCase("select")) {
				Integer extSelectType = tableColumn.getExtSelectType();
				List vList = (List)addMap.get(tablePropertyNames);
//				String vId = (String)addMap.get(tablePropertyName);
				String[][] values = new String[vList.size()][3];
									
				for(int i=0;i<vList.size();i++) {
					if(extSelectType == null) {//引用外部数据
						Object bean = (Object)vList.get(i);
						try {
							values[i][0] = (String)BeanUtils.getProperty(bean, "id");
							String beanPropertyName = tableColumn.getForeignTableValueColumn().getPropertyName();
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
					else {
						BeanWrapper bw = new BeanWrapperImpl(vList.get(i));
						values[i][0] = bw.getPropertyValue("id").toString();
						String beanColumnName = tableColumn.getForeignTableValueColumn().getColumnName();
						values[i][1] = bw.getPropertyValue(beanColumnName).toString();
					}
					if(values[i][0].equals(addMap.get(tablePropertyName))) {
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
