package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.List;
import java.util.Map;

import com.zsgj.info.appframework.extjs.servlet.Component;
import com.zsgj.info.appframework.extjs.servlet.ComponentCoder;
import com.zsgj.info.appframework.extjs.servlet.Validator;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.framework.context.ContextHolder;

/**
 * 表单页面组件生成器，2期框架预览
 * @Class Name CoderForSave
 * @Author peixf
 * @Create In 2009-1-1
 */
public class CoderForLook {
	    private static SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
		/**
		 * 将框架底层返回的元数据组件信息和表单数据转换成符合
		 * JSON格式的字符串发给前端进行表单组件生成，同时初始化表单组件的数据
		 * @Methods Name encode
		 */
		public static String encode(List<PagePanelColumn> pagePanelColumns,
			Map dataMap,boolean forEdit) {
		    String json = "";
		    for (PagePanelColumn uts : pagePanelColumns) {
			Column column = uts.getColumn();
			String propertyName = column.getPropertyName();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String tablePropertyName = tableName + "$"+ propertyName;
			String propertyNames = tablePropertyName + "s";
			boolean isDisplay = uts.getIsDisplay().intValue() != 1;
			
	        boolean isUpdateItem = !(uts.getColumn().getIsUpdateItem() != null && uts
			.getColumn().getIsUpdateItem().intValue() == 0);
	        Integer isMustInput = null;      
	        
			//Column column = uts.getColumn();
			String cnName = column.getColumnCnName();
			String width = column.getLengthForPage();
			String columnCnName = column.getColumnCnName();// 表头标题
			Object value = dataMap.get(tablePropertyName);

			String displayFiled = "";
			String valueFiled = "";
			String relationshipClazz = "";
			if (column.getForeignTable() != null) {
				displayFiled = column.getForeignTableValueColumn().getPropertyName();
				valueFiled = column.getForeignTableKeyColumn().getPropertyName();
				relationshipClazz = column.getForeignTable().getClassName();
			}
			String componentStr = "";
			String columnType = column.getSystemMainTableColumnType()
					.getColumnTypeName();

			propertyName = column.getPropertyName();
			String type = column.getValidateType() == null ? "" : column
					.getValidateType().getValidateTypeName();
			isMustInput = column.getIsMustInput();

			// 必填项
			String allowBlank = "true";

			if (isMustInput.intValue() == 1) {// 必填
				allowBlank = "false";
			} else {// 选填
				allowBlank = "true";
			}

			// 校验器
			String validator = Validator.get(type);
			isDisplay = isDisplay || columnType.equalsIgnoreCase("hidden");

			// 组件编码
			Component c = new Component();
			c.setName(tableName+"$"+propertyName);
			c.setId(c.getName()); //使用表名$属性名称确保组件id唯一
			c.setDisplay(isDisplay);
			c.setMustInput(isMustInput.intValue() == 1);
			c.setLabel(cnName);
			c.setWidth(width);
			c.setReadOnly(!isUpdateItem);
			c.setAllowBlank(allowBlank);
			c.setValidator(validator);
			c.setDisplayFiled(displayFiled);
			c.setValueFiled(valueFiled);
			c.setRelationship(relationshipClazz);
			if (forEdit) {
				c.setValue(value);
			}
	
	        if (columnType.equalsIgnoreCase("hidden")) {// type = "hidden";
				componentStr = ComponentCoder.makeHidden(c);
			} else if (columnType.equalsIgnoreCase("text")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("textArea")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("radio")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("multiSelect")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("file")) 
			{
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("multiFile")) {
		         
				SystemMainTable foreignTable = column.getForeignTable();
			
				if(forEdit){//value = "1237983021828";
					if(value!=null){
						c.setNowtime(value.toString());
						c.setValue(value); //编辑时value既是nowtime值
					}else{
						String nowtime = String.valueOf(System.currentTimeMillis());
						c.setNowtime(nowtime);
						//c.setValue(nowtime);//remove by lee for attachment static page unused bug in 20090708
					}
					c.setColumnId(String.valueOf(column.getId()));
					c.setRelationship(foreignTable.getClassName());
				}else{//"1237983021828";//
//remove by lee for attachment static page unused bug in 20090708 begin
//					String nowtime = String.valueOf(System.currentTimeMillis());
//					c.setNowtime(nowtime);
//					c.setValue(nowtime);
//remove by lee for attachment static page unused bug in 20090708 end
					c.setColumnId(String.valueOf(column.getId()));
					c.setRelationship(foreignTable.getClassName());
				}
				
				componentStr = ComponentCoder.makeAnnex(c); //改成生成多文件文本，自动加链接
				
			}
			else if (columnType.equalsIgnoreCase("dateText")) {
				componentStr = ComponentCoder.makeHtml(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {
				//注意，调用的还是1期的coder，改成2期的页面不正常，待确认原因
				componentStr = ComponentCoder.makeHtml(c);// 同普通的select
				String renderStr = "function(value){";
				renderStr += "var comboObject = Ext.getCmp('" + c.getName()+ "');";
				renderStr += "var comboStore = comboObject.store;";
				renderStr += "if (comboStore.find('id', value) !=-1) {";
				renderStr += "return comboStore.getAt(comboStore.find('id', value)).get('name');";
				renderStr += "} ";
				renderStr += "else{";
				renderStr += " 	return  value;";
				renderStr += " }";
				renderStr += " }";
				
				
			} else if (columnType.equalsIgnoreCase("select")) {
			    boolean isAbstract = column.getAbstractFlag()!=null && column.getAbstractFlag().intValue()==1;
		        if(isAbstract){
		           Column discColumn = column.getDiscColumn();
		 	       SystemMainTable foreignDiscTable = column.getForeignDiscTable();
		 	       String disccId = tableName + "$" + discColumn.getPropertyName();
		 	       String fdiscTable = foreignDiscTable.getTableName();
		 	       c.setAbstract(isAbstract);
		 	       c.setDisccId(disccId);//如隶属客户引用区分字段：ConfigItem$customerType
		 	       c.setFdiscTable(fdiscTable); //区分字段引用表：customerType表
		 	       c.setDisplayFiled("name");
		 	       if(forEdit){
		 	    	   String relationship = null;
		 	    	   if(c.getValue()!=null){
		 	    		  String discValue = c.getValue().toString();
		 	    		  relationship = systemColumnService.findClassNameByDisc(discValue, fdiscTable);
		 	    	   }
		 	    	   c.setRelationship(relationship);
		 	    	  componentStr = ComponentCoder.makeHtml(c); 
		 	       }else{
		 	    	  componentStr = ComponentCoder.makeHtml(c);
		 	       } 
		 	       
		        }else{
		        	if(forEdit){
		        		componentStr = ComponentCoder.makeHtml(c); //改成makeSelect居然页面不正常
					}else{
						
						componentStr = ComponentCoder.makeHtml(c);
					}
		        }
				
	 	        
				
			}
			// 处理componentStr字符串
			if (componentStr.length() != 0) {
				json += componentStr + ",";
			}
			
		}
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		return json;
	}
}