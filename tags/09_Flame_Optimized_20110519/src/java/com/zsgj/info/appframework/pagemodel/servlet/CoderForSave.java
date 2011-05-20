package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.extjs.servlet.Component;
import com.zsgj.info.appframework.extjs.servlet.ComponentCoder;
import com.zsgj.info.appframework.extjs.servlet.Validator;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemFile;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.PropertiesUtil;

/**
 * 表单页面组件生成器，2期框架修改
 * @Class Name CoderForSave
 * @Author peixf
 * @Create In 2009-1-1
 */
public class CoderForSave {
	    private static SystemColumnService systemColumnService = (SystemColumnService) ContextHolder.getBean("systemColumnService");
	    private static Service baseBervice = (Service) ContextHolder.getBean("baseService");
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
			SystemMainTableColumn smtColumn=(SystemMainTableColumn)column;
			if(smtColumn.getIsExtColumn()==SystemMainTableColumn.isMain){
				if (column.getForeignTable() != null) {
					displayFiled = column.getForeignTableValueColumn().getPropertyName();
					valueFiled = column.getForeignTableKeyColumn().getPropertyName();
					relationshipClazz = column.getForeignTable().getClassName();
				}
			}else{
				if(smtColumn.getExtSelectType()!=null&&smtColumn.getExtSelectType()==0){
					if (column.getForeignTable() != null) {
						displayFiled = column.getForeignTableValueColumn().getPropertyName();
						valueFiled = column.getForeignTableKeyColumn().getPropertyName();
						relationshipClazz = column.getForeignTable().getClassName();
					}
				}else if(smtColumn.getExtSelectType()!=null&&smtColumn.getExtSelectType()==2){
						displayFiled = "extOptionValue";
						valueFiled = "id";
						relationshipClazz = "com.zsgj.info.appframework.metadata.entity.ExtOptionData&extColumnId="+smtColumn.getId();
				}
			}
			
			String componentStr = "";
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
			if (value!=null) {  //if (forEdit) {
				c.setValue(value);
			}

	        if (columnType.equalsIgnoreCase("hidden")) {// type = "hidden";
				componentStr = ComponentCoder.makeHidden(c);
			} else if (columnType.equalsIgnoreCase("text")) {
				componentStr = ComponentCoder.makeTextField(c);
			} else if (columnType.equalsIgnoreCase("textArea")) {
				componentStr = ComponentCoder.makeTextArea(c);
			} else if (columnType.equalsIgnoreCase("htmlEditor")) { //富文本
//				String renderStr = "function(value){"; //该加到head
//				renderStr += "value = Ext.util.Format.htmlDecode(value);";
//				renderStr += "return value;	";		            
//				renderStr += "}";
				componentStr = ComponentCoder.makeHtmlEditor(c);
			}
	        //add by lee for add fckediter in 20090928 begin
			else if (columnType.equalsIgnoreCase("fckEditor")) {
				componentStr = ComponentCoder.makeFCKEditor(c);
			}
	        //add by lee for add fckediter in 20090928 end
			else if (columnType.equalsIgnoreCase("radio")) {
				Integer bigFlag = column.getBigFlag();
				Integer columnSum = column.getColumnSum();
				if(bigFlag!=null && bigFlag.intValue()==1){
					c.setSingleLine(true);
				}
				if(columnSum!=null){
					c.setColumns(columnSum);
				}
				List sourceList = (List) dataMap.get(tablePropertyName+"s");
				String displaykey= column.getForeignTableKeyColumn().getPropertyName();
				String displayvalue = column.getForeignTableValueColumn().getPropertyName();
				for(Object ob:sourceList){
					BeanWrapper bw = new BeanWrapperImpl(ob);
					String id=bw.getPropertyValue(displaykey).toString();
					String value1=null;
					if(bw.getPropertyValue(displayvalue)!=null){
						value1=(String) bw.getPropertyValue(displayvalue).toString();
					}
					Component item = new Component();
					item.setId(id);
					item.setName(c.getName());
					item.setLabel(value1);
					c.getItems().add(item);
				}
				componentStr = ComponentCoder.makeRadioGroup(c);
			}
			else if (columnType.equalsIgnoreCase("yesNoRadio")) {
				componentStr = ComponentCoder.makeYseNoRadio(c);
			} 
			else if (columnType.equalsIgnoreCase("multiSelect")) {
				String displaykey= column.getForeignTableKeyColumn().getPropertyName();
				String displayvalue = column.getForeignTableValueColumn().getPropertyName();
				List list = (List) dataMap.get(tablePropertyName);
				List sourceList = (List) dataMap.get(tablePropertyName+"s");
				if(list!=null){
					if(list.size()>0){
						sourceList.removeAll(list);
						for(Object ob:list){
							BeanWrapper bw = new BeanWrapperImpl(ob);
							Component item = new Component();
							item.setId(bw.getPropertyValue(displaykey).toString());
							item.setValue(bw.getPropertyValue(displayvalue).toString());
							c.getToSelectList().add(item);
						}
					}
				}
				for(Object ob:sourceList){
					BeanWrapper bw = new BeanWrapperImpl(ob);
					Component item = new Component();
					item.setId(bw.getPropertyValue(displaykey).toString());
					item.setValue(bw.getPropertyValue(displayvalue).toString());
					c.getFromSelectList().add(item);
				}
				c.getFromSelectList();
				componentStr = ComponentCoder.makeMultiSelect(c);
			}else if (columnType.equalsIgnoreCase("checkboxGroup")||columnType.equalsIgnoreCase("checkbox")) {
				Integer bigFlag = column.getBigFlag();
				Integer columnSum = column.getColumnSum();
				if(bigFlag!=null && bigFlag.intValue()==1){
					c.setSingleLine(true);
				}
				if(columnSum!=null){
					c.setColumns(columnSum);
				}
				
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
					if(list!=null){
						if(list.contains(selectedObject)){
							item.setValue("on");
						}
					}
					//加入一个子组件
					c.getItems().add(item);
				}
				
				componentStr = ComponentCoder.makeCheckboxGroup(c);
				
			} else if (columnType.equalsIgnoreCase("file")) {
				componentStr = ComponentCoder.makeFile(c);
			} else if (columnType.equalsIgnoreCase("dateText")) {
				componentStr = ComponentCoder.makeDateText(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {
				//注意，调用的还是1期的coder，改成2期的页面不正常，待确认原因
				componentStr = ComponentCoder.makeYesNoSelect(c);// 同普通的select
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
				
				
			} else if (columnType.equalsIgnoreCase("sexSelect")) {
				//注意，调用的还是1期的coder，改成2期的页面不正常，待确认原因
				componentStr = ComponentCoder.makeSexSelect(c);// 同普通的select
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
		 	    		  if(relationship==null){
		 	    			Object selectedDiscValue = dataMap.get(disccId);
		 	    			relationship = systemColumnService.findClassNameByDisc(selectedDiscValue.toString(), fdiscTable);
		 	    		  }
		 	    	   }
		 	    	   c.setRelationship(relationship);
		 	    	   componentStr=ComponentCoder.makeAbstractComboForEdit(c); 
		 	       }else{
		 	    	   componentStr=ComponentCoder.makeAbstractComboForAdd(c);
		 	       } 
		 	       
		        }else{
		        	if(forEdit){
						componentStr = ComponentCoder.makeComboForEdit(c); //改成makeSelect居然页面不正常
					}else{
						if(c.getValue()==null){
							componentStr = ComponentCoder.makeComboForAdd(c);
						}else{
							componentStr = ComponentCoder.makeComboForEdit(c);
						}
						
					}
		        }
				
	 	        
				
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
		 	    	   componentStr=ComponentCoder.makeAbstractComboForEdit(c); 
		 	       }else{
		 	    	   componentStr=ComponentCoder.makeAbstractComboForAdd(c);
		 	       } 
		 	       
		        }else{
		        	if(forEdit){
						componentStr = ComponentCoder.makeComboForEdit(c); //改成makeSelect居然页面不正常
					}else{
						
						componentStr = ComponentCoder.makeComboForAdd(c);
					}
		        }
				
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
				componentStr = ComponentCoder.makeFile(c);
			}else if (columnType.equalsIgnoreCase("multiFile")) {
				//隐藏域面板的id
				String hiddenId = String.valueOf(System.currentTimeMillis());
				c.setHiddenString(hiddenId);
				SystemMainTable foreignTable = column.getForeignTable();
				String filesPathString="";
				if(forEdit){//value = "1237983021828";
					if(value!=null){
					//modify by lee for 修改处理JSON后附件异常 in 20100524 begin
						String webRoot = PropertiesUtil.getProperties("webContext", "");
						c.setNowtime(value.toString());
						c.setValue(value); //编辑时value既是nowtime值
						//-----------------sujs 增加为获取附件数据-------------------
					    List<SystemFile> systemfileList=baseBervice.find(SystemFile.class, "nowtime", value);
					    int k=1;
					    for(SystemFile filesTemp:systemfileList){
					    	filesPathString += 
					    		"<span id =su_"+filesTemp.getId()+"><img src="+webRoot+"/images/other/file.gif >" 
					    	    + "&nbsp;<a href="+webRoot
								+ "/fileDown.do?methodCall=downloadFile&id="
								+ filesTemp.getId()+"&columnId="+ String.valueOf(column.getId())+">"  + filesTemp.getFileName()
								+ "</a>&nbsp;&nbsp;"
					    	    +"<img style=\\\"display: \\\" src="+webRoot+"/images/other/suremove.gif onClick=getRemoveFile(\\\""
							    + filesTemp.getId()+"\\\",\\\""+hiddenId+"\\\",\\\"com.zsgj.info.appframework.metadata.entity.SystemFile\\\")" 
							    +" alt=\\\"删除附件\\\" style=\\\"cursor:hand;margin:-1 0\\\">";
						    	//modify by lee for 修改处理JSON后附件异常 in 20100524 end  
						    	  if(k%4==0){
						    		 filesPathString=filesPathString+"<br></span>";  
						          }else{
						        	 filesPathString=filesPathString+"</span>";
						          }
					    	    k++;
						}
					    c.setFileString(filesPathString);
						//-----------------sujs 增加为获取附件数据-------------------
						
					}else{
						String nowtime = String.valueOf(System.currentTimeMillis());
						c.setNowtime(nowtime);
						c.setFileString(filesPathString);
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
					c.setFileString(filesPathString);
				}
				
				componentStr = ComponentCoder.makeAnnex(c); //改成生成多文件文本，自动加链接
				
			}
			// 处理componentStr字符串
			if (componentStr.length() != 0) {// ？？？？？？
				json += componentStr + ",";
			}
			
		}
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		return json;
	}
}