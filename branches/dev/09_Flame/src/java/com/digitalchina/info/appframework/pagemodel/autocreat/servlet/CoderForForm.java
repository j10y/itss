package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import com.digitalchina.info.appframework.extjs.servlet.Validator;
import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelFieldSet;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelFieldSetService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.util.PropertiesUtil;

/**
 * 表单页面组件生成器，2期框架修改
 * @Class Name CoderForSave
 * @Author peixf
 * @Create In 2009-1-1
 */
public class CoderForForm {
	    private static SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
		private static PagePanelFieldSetService ppfss = (PagePanelFieldSetService) ContextHolder.getBean("pagePanelFieldSetService");
		String FSP = System.getProperty("file.separator");
		String LSP = "\n";

	    /**
		 * 将框架底层返回的元数据组件信息和表单数据转换成符合
		 * JSON格式的字符串发给前端进行表单组件生成，同时初始化表单组件的数据
		 * @Methods Name encode
		 */
	    
		public static String encode(List<PagePanelColumn> pagePanelColumns,
			Map dataMap,boolean forEdit) {
		    String json = "";
		    int labellen = 135;
		    String itemlen = "200";
		    String  throulen = "530";
			String hiddenString ="";
			//用它来定位
			int sign=1;
		    for (PagePanelColumn uts : pagePanelColumns) {
				Component c = new Component();
				String componentStr = "";
			//为了fieldset获取数据的++++++++++++++++++++++
		    Integer fieldSetFlag = uts.getFieldSetFlag();
		    if(fieldSetFlag!=null&& fieldSetFlag.intValue()==1){//是fieldset
		    	List<PagePanelColumn> columns = ppfss.findFieldSetColumn(uts);
				Map<String, Object> mapcolumn=new HashMap<String, Object>();
		    	String fieldsetItem=CoderForForm.encode(columns,mapcolumn,false);
		        PagePanelFieldSet ppfs = ppfss.findPagePanelFieldSet(uts);
		        c.setLabel(ppfs.getName());
//		        c.setWidth(ppfs.getWidth());
//		        c.setHeight(ppfs.getHeigth());
		        c.setFieldsetItem(fieldsetItem);
		        componentStr += ComponentGather.makeFieldSet(c);   
		        if (componentStr.length() != 0) {
					json += componentStr + ",";
				}
		        continue;
		    }
		   //为了fieldset获取数据的+++++++++++++++++++++++
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
			
			String columnType = column.getSystemMainTableColumnType()
					.getColumnTypeName();
			// String columnType =
			// column.systemMainTableColumnType.columnTypeName
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

			c.setName(tableName+"$"+propertyName);
			c.setId(c.getName()); //使用表名$属性名称确保组件id唯一
			c.setDisplay(isDisplay);
			c.setMustInput(isMustInput.intValue() == 1);
			c.setLabel(cnName);
			c.setWidth(itemlen);
			c.setReadOnly(!isUpdateItem);
			c.setAllowBlank(allowBlank);
			c.setValidator(validator);
			c.setDisplayFiled(displayFiled);
			c.setValueFiled(valueFiled);
			c.setRelationship(relationshipClazz);
			//添加的共有属性+++++++++++++++++++++++++++++++++++++++++++++++
			if(!columnType.equalsIgnoreCase("hidden")) {
			       componentStr = "{html : '"+columnCnName+ ":',"+
                                    "cls : 'common-text',"+
                                  "style : 'width:" + labellen + ";text-align:right'},";
			}
			if (value!=null) {  //if (forEdit) {
				c.setValue(value);
			}

	        if (columnType.equalsIgnoreCase("hidden")) {// type = "hidden";
	        	hiddenString += ComponentGather.makeHidden(c)+",";
			} else if (columnType.equalsIgnoreCase("text")) {
				if(width!=null&&width.endsWith("9999")){
					if(sign%2==0){
						json+="{},{}";
					}
					   c.setWidth(throulen);
					   c.setColspan(3);
				}
				componentStr += ComponentGather.makeTextField(c);
				sign++;
			} else if (columnType.equalsIgnoreCase("textArea")) {
				if(width!=null&&width.endsWith("9999")){
					if(sign%2==0){
						json+="{},{},";
					}
				   c.setWidth(throulen);
				   c.setHeight("150");
				   c.setColspan(3);
				}
				componentStr += ComponentGather.makeTextArea(c);
				sign++;
			} else if (columnType.equalsIgnoreCase("htmlEditor")) { //富文本
				if(sign%2==0){
					json+="{},{},";
				}
			   c.setWidth(throulen);
			   c.setHeight("150");
			   c.setColspan(3);
			   componentStr += ComponentGather.makeHtmlEditor(c);
				sign++;
			}
	        //add by lee for add fckediter in 20090928 begin
			else if (columnType.equalsIgnoreCase("fckEditor")) { //富文本
				if(sign%2==0){
					json+="{},{},";
				}
			   c.setWidth(throulen);
			   c.setHeight("150");
			   c.setColspan(3);
			   componentStr += ComponentGather.makeFCKEditor(c);
				sign++;
			} 
	      //add by lee for add fckediter in 20090928 end
			else if (columnType.equalsIgnoreCase("radio")) {
				componentStr += ComponentGather.makeRadio(c);
				sign++;
			} else if (columnType.equalsIgnoreCase("multiSelect")) {
				componentStr += ComponentGather.makeMultiSelect(c);
				sign++;
			} else if (columnType.equalsIgnoreCase("checkboxGroup")) {
				Integer bigFlag = column.getBigFlag();
				Integer columnSum = column.getColumnSum();
				if(bigFlag!=null && bigFlag.intValue()==1){
					c.setSingleLine(true);
				}
				if(columnSum!=null){
					c.setColumns(columnSum);
				}
				if(sign%2==0){
					json+="{},{},";
				}
			    c.setWidth(throulen);
			    c.setHeight("150");
			    c.setColspan(3);
				List list = (List) dataMap.get(tablePropertyName);
				List sourceList = (List) dataMap.get(tablePropertyName+"s");
				Iterator iterSource = sourceList.iterator();
				String displayFiledStr = column.getForeignTableValueColumn().getPropertyName();
				while(iterSource.hasNext()){ //遍历所有待选复选框
					BaseObject selectedObject = (BaseObject) iterSource.next();
					BeanWrapper bw = new BeanWrapperImpl(selectedObject);
					String labelName = (String) bw.getPropertyValue(displayFiledStr);
					Long selectedObjectId = selectedObject.getId();
					Component item = new Component();
					BeanUtils.copyProperties(c, item);
					item.setId(c.getId()+"$"+selectedObjectId);
					item.setName(c.getName()+"$"+selectedObjectId);
					item.setLabel(labelName);
					//判断当前对象是否在选择对象集合中存在
					if(list.contains(selectedObject)){
						item.setValue("on");
					}
					//加入一个子组件
					c.getItems().add(item);
				}
				
				componentStr += ComponentGather.makeCheckboxGroup(c);
				sign++;
				
			} else if (columnType.equalsIgnoreCase("file")) {
				componentStr += ComponentGather.makeFile(c);
				sign++;
			} else if (columnType.equalsIgnoreCase("dateText")) {
				componentStr += ComponentGather.makeDateText(c);
				sign++;
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {
				//注意，调用的还是1期的coder，改成2期的页面不正常，待确认原因
				componentStr += ComponentGather.makeYesNoSelect(c);// 同普通的select
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
				sign++;
			} else if (columnType.equalsIgnoreCase("sexSelect")) {
				//注意，调用的还是1期的coder，改成2期的页面不正常，待确认原因
				componentStr += ComponentGather.makeSexSelect(c);// 同普通的select
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
				sign++;
				
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
		 	    		  if(relationship==null){
		 	    			Object selectedDiscValue = dataMap.get(disccId);
		 	    			relationship = systemColumnService.findClassNameByDisc(selectedDiscValue.toString(), fdiscTable);
		 	    		  }
		 	    	   }
		 	    	   c.setRelationship(relationship);
		 	    	   componentStr +=ComponentGather.makeComboForExpand(c); 
		 	       }else{
		 	    	   componentStr +=ComponentGather.makeComboForExpand(c);
		 	       } 
		 	       
		        }else{
		        	if(forEdit){
						componentStr += ComponentGather.makeComboForExpand(c); //改成makeSelect居然页面不正常
					}else{
						if(c.getValue()==null){
							componentStr += ComponentGather.makeComboForExpand(c);
						}else{
							componentStr += ComponentGather.makeComboForExpand(c);
						}
						
					}
		        }
				
		        sign++;
				
			}else if (columnType.equalsIgnoreCase("extSelect")) {
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
		 	    	   componentStr +=ComponentGather.makeAbstractComboForEdit(c); 
		 	       }else{
		 	    	   componentStr +=ComponentGather.makeAbstractComboForAdd(c);
		 	       } 
		 	       
		        }else{
		        	if(forEdit){
						componentStr += ComponentGather.makeComboForEdit(c); //改成makeSelect居然页面不正常
					}else{
						
						componentStr += ComponentGather.makeComboForAdd(c);
					}
		        }
		        sign++;
				
			}else if (columnType.equalsIgnoreCase("file")) {
				// 以下代码提供上传文件参考使用
				String uploadUrl = column.getUploadUrl();
				String fileNamePrefix = column.getFileNamePrefix();
				SystemMainTableColumn fileNameColumn = column
						.getFileNameColumn();
				SystemMainTableColumn systemFileNameColumn = column
						.getSystemFileNameColumn();
				// fileName
				String fileNamePropertyName = fileNameColumn.getPropertyName();
				// systemFileName
				String sysFileNamePropertyName = systemFileNameColumn
						.getPropertyName();
				String text = "" + dataMap.get(propertyName + "Text");
				String link = "" + dataMap.get(propertyName + "Link");
				link = link.replaceAll("\\\\", "/");
				// FIXME remove hard code for b2b
				String webRoot = PropertiesUtil.getProperties("webContext", "/b2b");
				webRoot = ContextFilter.getWebContext();
				link = webRoot + link;								
				if(text==null||text.equals("null")||text.trim().equals("")) {
					link = "无";
				}
				else {
					link = "<a href=\"" + link + "\" target=\"_blank\">" + text
					+ "</a>";
				}
				c.setLink(link);
				c.setValue(value);
				componentStr += ComponentGather.makeFile(c);
				sign++;
			}else if (columnType.equalsIgnoreCase("multiFile")) {
		         
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
				
				componentStr += ComponentGather.makeAnnex(c); //改成生成多文件文本，自动加链接
				sign++;
			}
			// 处理componentStr字符串
			if (componentStr.length() != 0) {
				json += componentStr + ",";
			}
			
		}
		//加上隐藏域
	        json+=hiddenString;    
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		return json;
	}
}