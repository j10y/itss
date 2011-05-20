package com.zsgj.info.appframework.pagemodel.autocreat.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.extjs.servlet.Validator;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;

/**
 * 1�ڿ������2�ڻ�ȡpanel�ֶδ���
 * @author Administrator
 *
 */
public class CoderForHeadEditGrid {  
	/**
	 * EditorGridPanel��ͷ��ʾ��Ҫ��json�ַ�������������Ҫ��ʼ�������б��Ƿ��б��
	 * �������ݣ���ʱ����Ҫ�Ƿ�༭��ǣ����ݶ��ǵ����������query��ȡ��
	 * @Methods Name encode
	 * @Create In 2009-1-2 By sa
	 * @param pagePanelColumns
	 * @param dataMap
	 * @param forEdit
	 * @return String
	 */
	public static String encode(List<PagePanelColumn> pagePanelColumns,
			Map dataMap) { //,boolean forEdit
		String json = "";
		for (PagePanelColumn uts : pagePanelColumns) { 
			Column column = uts.getColumn();
			String propertyName = column.getPropertyName();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String tablePropertyName = tableName + "$"+ propertyName;
			String propertyNames = tablePropertyName + "s";
			boolean isDisplay = uts.getIsDisplay().intValue() != 1
					|| uts.getColumn().getIsHiddenItem()!=null&& uts.getColumn().getIsHiddenItem().intValue() == 1;
			boolean isUpdateItem = !(uts.getColumn().getIsUpdateItem() != null && uts
					.getColumn().getIsUpdateItem().intValue() == 0);
			Integer isMustInput = null;

			String cnName = column.getColumnCnName();
			String width = column.getLengthForPage();
			String columnCnName = column.getColumnCnName();// ��ͷ����
//			Object value = dataMap.get(propertyName);
			String displayField = "";
			String valueField = "";
			String foreignClazz = "";
			if (column.getForeignTable() != null) {
				displayField = column.getForeignTableValueColumn().getPropertyName();
				valueField = column.getForeignTableKeyColumn().getPropertyName();
				foreignClazz = column.getForeignTable().getClassName();
			}
			
			String componentStr = "";
			String renderStr = "''";
			
			String columnType = column.getSystemMainTableColumnType()
					.getColumnTypeName();
		
			propertyName = column.getPropertyName();
			String type = column.getValidateType() == null ? "" : column
					.getValidateType().getValidateTypeName();
			isMustInput = column.getIsMustInput();

			// У����
			String validator = Validator.get(type);
			isDisplay = isDisplay || columnType.equalsIgnoreCase("hidden");

			// �������
			Component c = new Component();
			c.setName(tablePropertyName);
			c.setId(c.getName());
			c.setDisplay(isDisplay);
			c.setAllowBlank(String.valueOf(isMustInput.intValue() != 1));
			c.setLabel(cnName);
			c.setWidth(width);
			c.setReadOnly(!isUpdateItem);
			c.setValidator(validator);
			c.setDisplayFiled(displayField);
			c.setValueFiled(valueField);
			c.setRelationship(foreignClazz);
//			if (forEdit) {
//				c.setValue(value);
//			}

			if (columnType.equalsIgnoreCase("hidden")) {// type = "hidden";
				componentStr = ComponentGather.makeHidden(c);
			} else if (columnType.equalsIgnoreCase("text")) {
				componentStr = ComponentGather.makeTextField(c);
			} else if (columnType.equalsIgnoreCase("textArea")) {
				componentStr = ComponentGather.makeTextArea(c);
			} else if (columnType.equalsIgnoreCase("radio")) {
				componentStr = ComponentGather.makeRadio(c);
			} else if (columnType.equalsIgnoreCase("multiSelect")) {
				componentStr = ComponentGather.makeMultiSelect(c);
			} else if (columnType.equalsIgnoreCase("dateText")) {
				renderStr = "function(value)" + "{if(value instanceof Date)"
				+ " {return new Date(value).dateFormat('Y-m-d');" + "}"
				+ "return value;" + "}";
				componentStr = ComponentGather.makeDateText(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {// ����ģ�ʹ洢��map��
				//2�ڿ��ΪGridPanelд���Ƿ��б������
				componentStr = ComponentGather.makeYesNoSelect(c);//����bug
				renderStr = "function(value){";
				renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "ComboId');"; //ComboId��Ȼ������ȥ��
				renderStr += "var comboStore = comboObject.store;";
				renderStr += "if (comboStore.find('id', value) !=-1) {";
				renderStr += "  return comboStore.getAt(comboStore.find('id', value)).get('name');";
				renderStr += "} ";
				renderStr += "else{";
				renderStr += " 	return value;";
				renderStr += " }";
				renderStr += "}";
				
			} else if (columnType.equalsIgnoreCase("sexSelect")) {// ����ģ�ʹ洢��map��
				//2�ڿ��ΪGridPanelд���Ƿ��б������
				componentStr = ComponentGather.makeYesNoSelect(c);//����bug
				renderStr = "function(value){";
				renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "ComboId');"; //ComboId��Ȼ������ȥ��
				renderStr += "var comboStore = comboObject.store;";
				renderStr += "if (comboStore.find('id', value) !=-1) {";
				renderStr += "  return comboStore.getAt(comboStore.find('id', value)).get('name');";
				renderStr += "} ";
				renderStr += "else{";
				renderStr += " 	return value;";
				renderStr += " }";
				renderStr += "}";
				
			}else if (columnType.equalsIgnoreCase("file")) {
				componentStr = "''";// �����
				
			} else if (columnType.equalsIgnoreCase("extSelect")) {//������
				//componentStr = ComponentCoder.makeExtSelect(c);
			//++++++++++++++++++++sujs++++++++++++++++++++++++++
			 // ����ģ�ʹ洢��bean��
					componentStr = ComponentGather.makeLazySelect(c);
					renderStr = "function(value){";
					renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "Combo');";
					renderStr += "var comboStore = comboObject.store;";
					renderStr += "if (comboStore.find('id', value) !=-1) {";
					renderStr += "return comboStore.getAt(comboStore.find('id', value)).get('"+c.getDisplayFiled()+"');";
					renderStr += "} ";
					renderStr += "else{";
					renderStr += " 	return  value;";
					renderStr += " }";
					renderStr += " }";

			//++++++++++++++++++++sujs++++++++++++++++++++++++++	
			} else if (columnType.equalsIgnoreCase("select")) {// ����ģ�ʹ洢��bean��
				Integer extSelectType = column.getExtSelectType();
				List vList = (List) dataMap.get(propertyNames);
				// String vId = (String)editMap.get(propertyName);
				String[][] values = new String[vList == null ? 0 : vList.size()][3];

				for (int i = 0; vList != null && i < vList.size(); i++) {
					if (extSelectType == null) {// �����ⲿ����
						Object bean = (Object) vList.get(i);
						try {
							values[i][0] = (String) BeanUtils.getProperty(bean,
									"id");
							String beanPropertyName = uts.getColumn()
									.getForeignTableValueColumn()
									.getPropertyName();
							values[i][1] = (String) BeanUtils.getProperty(bean,
									beanPropertyName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (extSelectType.intValue() == 2) {// �����ⲿ�����Զ���
						Object bean = (Object) vList.get(i);
						try {
							values[i][0] = (String) BeanUtils.getProperty(bean,
									"id");
							values[i][1] = (String) BeanUtils.getProperty(bean,
									"extOptionValue");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {// �������ⲿ����
						BeanWrapper bw = new BeanWrapperImpl(vList.get(i));
						values[i][0] = bw.getPropertyValue("id").toString();
						String beanColumnName = uts.getColumn()
								.getForeignTableValueColumn().getColumnName();
						values[i][1] = bw.getPropertyValue(beanColumnName)
								.toString();
					}
//					Object vId = dataMap.get(propertyName);
					values[i][2] = "";
					c.setValue(values);
					componentStr = ComponentGather.makeGridSelect(c);
					renderStr = "function(value){";
					renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "ComboId');";
					renderStr += "var comboStore = comboObject.store;";
					renderStr += "if (comboStore.find('id', value) !=-1) {";
					renderStr += "return comboStore.getAt(comboStore.find('id', value)).get('name');";
					renderStr += "} ";
					renderStr += "else{";
					renderStr += " 	return  value;";
					renderStr += " }";
					renderStr += " }";

				}
			}

			if (componentStr.trim().equals("")) {// Ĭ��Ϊ�ı���
				componentStr = ComponentGather.makeTextField(c);
			}
			//�����Ⱦ���� Render
			json += "{header:'"+columnCnName+"',renderer:" + renderStr + ",editor:" + componentStr+",dataIndex:'"+tablePropertyName+"',align : 'left',sortable : true,"+"hidden:"+columnType.equalsIgnoreCase("hidden")+"},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		return "[sm," + json + "]";
	}
}
