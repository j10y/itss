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
 * 1期框架新增2期获取panel字段代码
 * @author Administrator
 *
 */
public class CoderForHeadEditGrid {  
	/**
	 * EditorGridPanel表头显示需要的json字符串，区别是需要初始化下拉列表、是否列表等
	 * 关联数据，此时不需要是否编辑标记，数据都是单独发请求给query获取的
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
			String columnCnName = column.getColumnCnName();// 表头标题
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

			// 校验器
			String validator = Validator.get(type);
			isDisplay = isDisplay || columnType.equalsIgnoreCase("hidden");

			// 组件编码
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
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {// 数据模型存储于map中
				//2期框架为GridPanel写的是否列表处理代码
				componentStr = ComponentGather.makeYesNoSelect(c);//隐藏bug
				renderStr = "function(value){";
				renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "ComboId');"; //ComboId居然还不能去掉
				renderStr += "var comboStore = comboObject.store;";
				renderStr += "if (comboStore.find('id', value) !=-1) {";
				renderStr += "  return comboStore.getAt(comboStore.find('id', value)).get('name');";
				renderStr += "} ";
				renderStr += "else{";
				renderStr += " 	return value;";
				renderStr += " }";
				renderStr += "}";
				
			} else if (columnType.equalsIgnoreCase("sexSelect")) {// 数据模型存储于map中
				//2期框架为GridPanel写的是否列表处理代码
				componentStr = ComponentGather.makeYesNoSelect(c);//隐藏bug
				renderStr = "function(value){";
				renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "ComboId');"; //ComboId居然还不能去掉
				renderStr += "var comboStore = comboObject.store;";
				renderStr += "if (comboStore.find('id', value) !=-1) {";
				renderStr += "  return comboStore.getAt(comboStore.find('id', value)).get('name');";
				renderStr += "} ";
				renderStr += "else{";
				renderStr += " 	return value;";
				renderStr += " }";
				renderStr += "}";
				
			}else if (columnType.equalsIgnoreCase("file")) {
				componentStr = "''";// 待完成
				
			} else if (columnType.equalsIgnoreCase("extSelect")) {//待调试
				//componentStr = ComponentCoder.makeExtSelect(c);
			//++++++++++++++++++++sujs++++++++++++++++++++++++++
			 // 数据模型存储于bean中
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
			} else if (columnType.equalsIgnoreCase("select")) {// 数据模型存储于bean中
				Integer extSelectType = column.getExtSelectType();
				List vList = (List) dataMap.get(propertyNames);
				// String vId = (String)editMap.get(propertyName);
				String[][] values = new String[vList == null ? 0 : vList.size()][3];

				for (int i = 0; vList != null && i < vList.size(); i++) {
					if (extSelectType == null) {// 引用外部数据
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
					} else if (extSelectType.intValue() == 2) {// 引用外部数据自定义
						Object bean = (Object) vList.get(i);
						try {
							values[i][0] = (String) BeanUtils.getProperty(bean,
									"id");
							values[i][1] = (String) BeanUtils.getProperty(bean,
									"extOptionValue");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {// 不引用外部数据
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

			if (componentStr.trim().equals("")) {// 默认为文本域
				componentStr = ComponentGather.makeTextField(c);
			}
			//添加渲染函数 Render
			json += "{header:'"+columnCnName+"',renderer:" + renderStr + ",editor:" + componentStr+",dataIndex:'"+tablePropertyName+"',align : 'left',sortable : true,"+"hidden:"+columnType.equalsIgnoreCase("hidden")+"},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		return "[sm," + json + "]";
	}
}
