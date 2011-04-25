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
 * ��ҳ�������������2�ڿ���޸�
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
		 * ����ܵײ㷵�ص�Ԫ���������Ϣ�ͱ�����ת���ɷ���
		 * JSON��ʽ���ַ�������ǰ�˽��б�������ɣ�ͬʱ��ʼ�������������
		 * @Methods Name encode
		 */
	    
		public static String encode(List<PagePanelColumn> pagePanelColumns,
			Map dataMap,boolean forEdit) {
		    String json = "";
		    int labellen = 135;
		    String itemlen = "200";
		    String  throulen = "530";
			String hiddenString ="";
			//��������λ
			int sign=1;
		    for (PagePanelColumn uts : pagePanelColumns) {
				Component c = new Component();
				String componentStr = "";
			//Ϊ��fieldset��ȡ���ݵ�++++++++++++++++++++++
		    Integer fieldSetFlag = uts.getFieldSetFlag();
		    if(fieldSetFlag!=null&& fieldSetFlag.intValue()==1){//��fieldset
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
		   //Ϊ��fieldset��ȡ���ݵ�+++++++++++++++++++++++
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
			String columnCnName = column.getColumnCnName();// ��ͷ����
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

			// ������
			String allowBlank = "true";

			if (isMustInput.intValue() == 1) {// ����
				allowBlank = "false";
			} else {// ѡ��
				allowBlank = "true";
			}

			// У����
			String validator = Validator.get(type);
			isDisplay = isDisplay || columnType.equalsIgnoreCase("hidden");
            
			// �������

			c.setName(tableName+"$"+propertyName);
			c.setId(c.getName()); //ʹ�ñ���$��������ȷ�����idΨһ
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
			//��ӵĹ�������+++++++++++++++++++++++++++++++++++++++++++++++
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
			} else if (columnType.equalsIgnoreCase("htmlEditor")) { //���ı�
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
			else if (columnType.equalsIgnoreCase("fckEditor")) { //���ı�
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
				while(iterSource.hasNext()){ //�������д�ѡ��ѡ��
					BaseObject selectedObject = (BaseObject) iterSource.next();
					BeanWrapper bw = new BeanWrapperImpl(selectedObject);
					String labelName = (String) bw.getPropertyValue(displayFiledStr);
					Long selectedObjectId = selectedObject.getId();
					Component item = new Component();
					BeanUtils.copyProperties(c, item);
					item.setId(c.getId()+"$"+selectedObjectId);
					item.setName(c.getName()+"$"+selectedObjectId);
					item.setLabel(labelName);
					//�жϵ�ǰ�����Ƿ���ѡ����󼯺��д���
					if(list.contains(selectedObject)){
						item.setValue("on");
					}
					//����һ�������
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
				//ע�⣬���õĻ���1�ڵ�coder���ĳ�2�ڵ�ҳ�治��������ȷ��ԭ��
				componentStr += ComponentGather.makeYesNoSelect(c);// ͬ��ͨ��select
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
				//ע�⣬���õĻ���1�ڵ�coder���ĳ�2�ڵ�ҳ�治��������ȷ��ԭ��
				componentStr += ComponentGather.makeSexSelect(c);// ͬ��ͨ��select
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
		 	       c.setDisccId(disccId);//�������ͻ����������ֶΣ�ConfigItem$customerType
		 	       c.setFdiscTable(fdiscTable); //�����ֶ����ñ�customerType��
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
						componentStr += ComponentGather.makeComboForExpand(c); //�ĳ�makeSelect��Ȼҳ�治����
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
		 	       c.setDisccId(disccId);//�������ͻ����������ֶΣ�ConfigItem$customerType
		 	       c.setFdiscTable(fdiscTable); //�����ֶ����ñ�customerType��
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
						componentStr += ComponentGather.makeComboForEdit(c); //�ĳ�makeSelect��Ȼҳ�治����
					}else{
						
						componentStr += ComponentGather.makeComboForAdd(c);
					}
		        }
		        sign++;
				
			}else if (columnType.equalsIgnoreCase("file")) {
				// ���´����ṩ�ϴ��ļ��ο�ʹ��
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
					link = "��";
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
						c.setValue(value); //�༭ʱvalue����nowtimeֵ
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
				
				componentStr += ComponentGather.makeAnnex(c); //�ĳ����ɶ��ļ��ı����Զ�������
				sign++;
			}
			// ����componentStr�ַ���
			if (componentStr.length() != 0) {
				json += componentStr + ",";
			}
			
		}
		//����������
	        json+=hiddenString;    
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		return json;
	}
}