package com.digitalchina.info.appframework.pagemodel.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.appframework.extjs.servlet.Validator;
import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;

/**
 * 1�ڿ������2�ڻ�ȡpanel�ֶδ���
 * @author Administrator
 *
 */
public class CoderForHead {  
	
	/**
	 * GridPanel��ͷ��ʾ��Ҫ��json�ַ���
	 * @Methods Name encode
	 * @Create In 2009-1-2 By sa
	 * @param pagePanelColumns
	 * @return String
	 */
	public static String encode(List<PagePanelColumn> pagePanelColumns) {
		String json = "";
		for (PagePanelColumn uts : pagePanelColumns) { //UserTableSetting uts : userVisibleColumns
			Column column = uts.getColumn();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String propertyName = column.getPropertyName();
			String tablePropertyName = tableName + "$"+ propertyName;
			String columnCnName = column.getColumnCnName();//��ͷ����
			String columnType = column.getSystemMainTableColumnType()
			.getColumnTypeName();
			//"',isHidden:'"+isHidden+"},";
			//json += "{header:'"+columnCnName+"',dataIndex:'"+tablePropertyName+"'},";
			json += "{header:'"+columnCnName+"',dataIndex:'"+tablePropertyName+"',isHidden:'"+columnType.equalsIgnoreCase("hidden")+"'},";
		}
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return "["+json+"]";
	}
	
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
			Object value = dataMap.get(propertyName);
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
				componentStr = ComponentCoder.makeHidden(c);
			} else if (columnType.equalsIgnoreCase("text")) {
				componentStr = ComponentCoder.makeTextField(c);
			} else if (columnType.equalsIgnoreCase("textArea")) {
				componentStr = ComponentCoder.makeTextArea(c);
			} else if (columnType.equalsIgnoreCase("radio")) {
				componentStr = ComponentCoder.makeRadio(c);
			} else if (columnType.equalsIgnoreCase("multiSelect")) {
				componentStr = ComponentCoder.makeMultiSelect(c);
			} else if (columnType.equalsIgnoreCase("dateText")) {
				renderStr = "function(value)" + "{if(value instanceof Date)"
				+ " {return new Date(value).dateFormat('Y-m-d');" + "}"
				+ "return value;" + "}";
				componentStr = ComponentCoder.makeDateText(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {// ����ģ�ʹ洢��map��
				//2�ڿ��ΪGridPanelд���Ƿ��б������
				componentStr = ComponentCoder.makeYesNoSelect(c);//����bug
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
				componentStr = ComponentCoder.makeYesNoSelect(c);//����bug
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
					componentStr = ComponentCoder.makeLazySelect(c);
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
					Object vId = dataMap.get(propertyName);
					values[i][2] = "";
					c.setValue(values);
					componentStr = ComponentCoder.makeGridSelect(c);
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
				componentStr = ComponentCoder.makeTextField(c);
			}
			//�����Ⱦ���� Render
	
			json += "{header:'" + columnCnName + "',dataIndex:'" + tablePropertyName
					+ "',renderer:" + renderStr + ",editor:" + componentStr+",hidden:'"+columnType.equalsIgnoreCase("hidden")+"'"
					+ "},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		return "[" + json + "]";
	}
	
	
//	public static String encode(List<UserTableSetting> userVisibleColumns,
//			Map dataMap,boolean forEdit) {
//		String json = "";
//		for (UserTableSetting uts : userVisibleColumns) {
//			String propertyName = uts.getPropertyName();
//			SystemMainTable smt = uts.getColumn().getSystemMainTable();
//			String tableName = smt.getTableName();
//			String propertyNames = propertyName + "s";
//			boolean isDisplay = uts.getIsDisplay().intValue() != 1
//					|| uts.getIsHiddenItem().intValue() == 1;
//			boolean isUpdateItem = !(uts.getIsUpdateItem() != null && uts
//					.getIsUpdateItem().intValue() == 0);
//			Integer isMustInput = null;
//
//			String cnName = uts.getColumnCnName();
//			String width = uts.getLengthForPage();
//			Column column = uts.getColumn();
//			String columnCnName = column.getColumnCnName();// ��ͷ����
//			Object value = dataMap.get(propertyName);
//
//			String componentStr = "";
//			// ADD Render<<<<<<<<<<<<<<<<<<<<<<by DJ
//			String renderStr = "''";
//			
//			// >>>>>>>>>>>>>>>>>>>>>>>>>>>
//			String columnType = column.getSystemMainTableColumnType()
//					.getColumnTypeName();
//			// String columnType =
//			// column.systemMainTableColumnType.columnTypeName
//			propertyName = column.getPropertyName();
//			String type = column.getValidateType() == null ? "" : column
//					.getValidateType().getValidateTypeName();
//			isMustInput = column.getIsMustInput();
//
//			// У����
//			String validator = Validator.get(type);
//			isDisplay = isDisplay || columnType.equalsIgnoreCase("hidden");
//
//			// �������
//			Component c = new Component();
//			c.setName(propertyName);
//			c.setDisplay(isDisplay);
//			c.setAllowBlank(String.valueOf(isMustInput.intValue() != 1));
//			c.setLabel(cnName);
//			c.setWidth(width);
//			c.setReadOnly(!isUpdateItem);
//			c.setValidator(validator);
//			 if(forEdit) {
//			 c.setValue(value);
//			 }
//
//			if (columnType.equalsIgnoreCase("hidden")) {// type = "hidden";
//				componentStr = ComponentCoder.makeHidden(c);
//			} else if (columnType.equalsIgnoreCase("text")) {
//				componentStr = ComponentCoder.makeTextField(c);
//			} else if (columnType.equalsIgnoreCase("textArea")) {
//				componentStr = ComponentCoder.makeTextArea(c);
//			} else if (columnType.equalsIgnoreCase("radio")) {
//				componentStr = ComponentCoder.makeRadio(c);
//			} else if (columnType.equalsIgnoreCase("multiSelect")) {
//				componentStr = ComponentCoder.makeMultiSelect(c);
//			} else if (columnType.equalsIgnoreCase("dateText")) {
//				renderStr = "function(value)" + "{if(value instanceof Date)"
//				+ " {return new Date(value).dateFormat('Y-m-d');" + "}"
//				+ "return value;" + "}";
//				componentStr = ComponentCoder.makeDateText(c);
//			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {// ����ģ�ʹ洢��map��
//				Integer extSelectType = column.getExtSelectType();
//				Map vMap = (Map) dataMap.get(propertyNames);
//				String[][] values = new String[2][3];
//				for (int i = 0; i < 2; i++) {
//					// Map vMap = (Map)vList.get(i);
//					values[i][0] = String.valueOf(i);
//					values[i][1] = (String) vMap.get(String.valueOf(i));
//					// if(forEdit&&values[i][0].equals(dataMap.get(propertyName)))
//					// {
//					// values[i][2] = "selected";
//					// }
//					// else {
//					// values[i][2] = "";
//					// }
//					values[i][2] = "";
//				}
//				c.setValue(values);
//				componentStr = ComponentCoder.makeSelect(c);// ͬ��ͨ��select
//			} else if (columnType.equalsIgnoreCase("file")) {
//				componentStr = "''";// �༭��Ϊ��
//				// ���´����ṩ�ϴ��ļ��ο�ʹ��
//				//				
//				// /* Column����һ��4������
//				// * private String uploadUrl; //�ϴ�������ŵ�·��
//				// * private String fileNamePrefix; //�ϴ��ļ����Ƶ�ǰ׺
//				// * private SystemMainTableColumn fileNameColumn;
//				// * private SystemMainTableColumn systemFileNameColumn;
//				// */
//				// //��ʾ���ǹ����ļ�ʵ���fileName
//				// String uploadUrl = column.getUploadUrl();
//				// String fileNamePrefix = column.getFileNamePrefix();
//				// Object value = item.get(propertyName);
//				// //���ظ���ʵ���systemFileName,
//				// ���ϴ�������ŵ�·��+ʵ���ļ����ƣ���̨ȡ����systemFileNameColumn��ϵͳ�ļ����ֶ�
//				// Object systemFileName = item.get(propertyName+"Link");
//				// if(value==null) value="";
//				// dataItem += propertyName+":'<a
//				// href=\'"+fileNamePrefix+"\'>"+uploadUrl+"</a>',";
//				// // value ${item[column.mainTableColumn.propertyName]}
//			} else if (columnType.equalsIgnoreCase("select")) {// ����ģ�ʹ洢��bean��
//				Integer extSelectType = column.getExtSelectType();
//				List vList = (List) dataMap.get(propertyNames);
//				// String vId = (String)editMap.get(propertyName);
//				String[][] values = new String[vList == null ? 0 : vList.size()][3];
//
//				for (int i = 0; vList != null && i < vList.size(); i++) {
//					if (extSelectType == null) {// �����ⲿ����
//						Object bean = (Object) vList.get(i);
//						try {
//							values[i][0] = (String) BeanUtils.getProperty(bean,
//									"id");
//							String beanPropertyName = uts
//									.getForeignTableValueColumn()
//									.getPropertyName();
//							values[i][1] = (String) BeanUtils.getProperty(bean,
//									beanPropertyName);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					} else if (extSelectType.intValue() == 2) {// �����ⲿ�����Զ���
//						Object bean = (Object) vList.get(i);
//						try {
//							values[i][0] = (String) BeanUtils.getProperty(bean,
//									"id");
//							values[i][1] = (String) BeanUtils.getProperty(bean,
//									"extOptionValue");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					} else {// �������ⲿ����
//						BeanWrapper bw = new BeanWrapperImpl(vList.get(i));
//						values[i][0] = bw.getPropertyValue("id").toString();
//						String beanColumnName = uts
//								.getForeignTableValueColumn().getColumnName();
//						values[i][1] = bw.getPropertyValue(beanColumnName)
//								.toString();
//					}
//					Object vId = dataMap.get(propertyName);
//					values[i][2] = "";
//					c.setValue(values);
//					componentStr = ComponentCoder.makeGridSelect(c);
//					renderStr = "function(value){";
//					renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "ComboId');";
//					renderStr += "var comboStore = comboObject.store;";
//					renderStr += "if (comboStore.find('id', value) !=-1) {";
//					renderStr += "return comboStore.getAt(comboStore.find('id', value)).get('name');";
//					renderStr += "} ";
//					renderStr += "else{";
//					renderStr += " 	return  value;";
//					renderStr += " }";
//					renderStr += " }";
////					String s = "";
////					s += "renderer: 
//
//					// s += "var xStore = this.editor.store;";
//					// s += "if (xStore.find('id', value) == null) { ";
//					// s += "return '��ѡ��...';";
//					// s += "}";
//					// s += "else {";
//					// s += "return xStore.getAt(xStore.find('id',
//					// value)).get('name'); ";
//					// s += "}";
//					// s += "}";
////					componentStr += "," + s;
//				}
//			}
//
//			// json +=
//			// "{header:'"+columnCnName+"',dataIndex:'"+propertyName+"'},";
//			if (componentStr.trim().equals("")) {// Ĭ��Ϊ�ı���
//				componentStr = ComponentCoder.makeTextField(c);
//			}
//			// FIXME �����Ⱦ���� Render
//			//
//			String tablePropertyName="";
//			if (columnType.equalsIgnoreCase("hidden")) {
//			   tablePropertyName=propertyName;
//			}else{
//			   tablePropertyName = tableName + "$"+propertyName;	
//			}
//			json += "{header:'" + columnCnName + "',dataIndex:'" + tablePropertyName
//					+ "',renderer:" + renderStr + ",editor:" + componentStr+",hidden:'"+columnType.equalsIgnoreCase("hidden")+"'"
//					+ "},";
//		}
//		if (json.endsWith(",")) {
//			json = json.substring(0, json.length() - 1);
//		}
//		return "[" + json + "]";
//	}
}
