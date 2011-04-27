package com.zsgj.itil.service.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.zsgj.info.appframework.extjs.servlet.Component;
import com.zsgj.info.appframework.extjs.servlet.ComponentCoder;
import com.zsgj.info.appframework.extjs.servlet.Validator;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.itil.service.entity.SCIColumn;
import com.zsgj.itil.service.entity.SCIDColumn;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.service.SCIDColumnService;

public class SCIDColumnServiceImpl extends BaseDao implements SCIDColumnService{

	public List<SCIDColumn> findSCIDColumnByServiceItem(ServiceItem serviceItem) {
		Criteria c = super.createCriteria(SCIDColumn.class);
		c.add(Restrictions.eq("serviceItem",serviceItem));
		c.addOrder(Order.asc("order"));
		List<SCIDColumn> list = c.list();
		return list;
	}
	public String encode(List<SCIDColumn> columns) {
		String json = "";
		for (SCIDColumn scidColumn : columns) {
			String propertyName = scidColumn.getColumnName();
			String propertyNames = propertyName + "s";
			boolean isDisplay = true;
			boolean isUpdateItem = scidColumn.getIsUpdateItem() != null
					&& scidColumn.getIsUpdateItem().intValue() != 0;
			Integer isMustInput = null;

			String cnName = scidColumn.getColumnCnName();
			String width = scidColumn.getLengthForPage();
			Object value = scidColumn.getValue();

			String displayFiled = "";
			String valueFiled = "";
			String relationshipClazz = "";
			if (scidColumn.getForeignTable() != null) {
				displayFiled = scidColumn.getForeignTableValueColumn().getPropertyName();
				valueFiled = scidColumn.getForeignTableKeyColumn().getPropertyName();
				relationshipClazz = scidColumn.getForeignTable().getClassName();
			}
			String componentStr = "";
			String columnType = scidColumn.getSystemMainTableColumnType()
					.getColumnTypeName();
			String type = scidColumn.getValidateType() == null ? "" : scidColumn
					.getValidateType().getValidateTypeName();
			isMustInput = scidColumn.getIsMustInput();

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
			Component c = new Component();
			c.setName(propertyName);//
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
			if (value!=null) {
				c.setValue(value);
				if(scidColumn.getForeignTable() != null){
					String v = scidColumn.getValue();
					c.setValue(Long.valueOf(v));
				}
			}

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
			} else if (columnType.equalsIgnoreCase("file")) {
				componentStr = ComponentCoder.makeFile(c);
			} else if (columnType.equalsIgnoreCase("dateText")) {
				componentStr = ComponentCoder.makeDateText(c);
			} else if (columnType.equalsIgnoreCase("yesNoSelect")) {// ����ģ�ʹ洢��map��
//				Integer extSelectType = scidColumn.getExtSelectType();
//				Map vMap = (Map) dataMap.get(propertyNames);
//				String[][] values = new String[2][3];
//				for (int i = 0; i < 2; i++) {
//					// Map vMap = (Map)vList.get(i);
//					values[i][0] = String.valueOf(i);
//					values[i][1] = (String) vMap.get(String.valueOf(i));
//					if (forEdit&& values[i][0].equals(dataMap.get(propertyName))) {
//						values[i][2] = "selected";
//					} else {
//						values[i][2] = "";
//					}
//				}
//				c.setValue(values);
//				componentStr = ComponentCoder.makeSelect(c);// ͬ��ͨ��select
			} else if (columnType.equalsIgnoreCase("select")) {// ����ģ�ʹ洢��bean��
				if(value!=null){
					componentStr = ComponentCoder.makeComboForEdit(c);
				}else{
					componentStr = ComponentCoder.makeComboForAdd(c);
				}
			} else if(columnType.equalsIgnoreCase("extSelect")) {//����ģ�ʹ洢��bean��			
				//componentStr = ComponentCoder.makeExtSelect(c);
			}	
			// ����componentStr�ַ���
			if (componentStr.length() != 0) {// ������������
				json += componentStr + ",";
			}
			
		}
		if (json.endsWith(",")) {
			json = "[" + json.substring(0, json.length() - 1) + "]";
		}
		if(json.equals("")){
			json = "[]";
		}
		return json;
	}
	public void save(List<SCIColumn> sciColumns, ServiceItem serviceItem) {
		for(SCIColumn column : sciColumns){
			SCIDColumn scidColumn = new SCIDColumn();
			try {
				BeanUtils.copyProperties(column,scidColumn);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			scidColumn.setId(null);
			scidColumn.setValue(null);
			scidColumn.setServiceItem(serviceItem);
			save(scidColumn);
		}
		
	}
	
	public List findSCIDColumnByServiceItemAndName(ServiceItem serviceItem,String name){
		Criteria c = super.createCriteria(SCIDColumn.class);
		c.add(Restrictions.eq("serviceItem",serviceItem));
		c.add(Restrictions.eq("columnName",name));
		c.addOrder(Order.asc("order"));
		List<SCIDColumn> list = c.list();
		return list;
	}
	
	public void saveSCIDColumn(List<SCIColumn> sciColumns, ServiceItem serviceItem) {
		for(SCIColumn column : sciColumns){
			List list=this.findSCIDColumnByServiceItemAndName(serviceItem, column.getColumnName());
			if(list.size()==0||list.isEmpty()){
				SCIDColumn scidColumn = new SCIDColumn();
				try {
					BeanUtils.copyProperties(column,scidColumn);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				scidColumn.setId(null);
				scidColumn.setValue(null);
				scidColumn.setOrder(null);
				scidColumn.setServiceItem(serviceItem);
				this.saveSCIDColumn(scidColumn);
			}
			
		}
		
	}
	public SCIDColumn save(SCIDColumn scidColumn) {
		SCIDColumn result = (SCIDColumn)super.save(scidColumn);
		return result;
	}
	
	public SCIDColumn saveSCIDColumn(SCIDColumn scidColumn){
		if(scidColumn.getOrder()==null){
			int maxOrder=this.findMaxNumberByOrder(scidColumn)+1;
			scidColumn.setOrder(maxOrder);
		}
		SCIDColumn result = (SCIDColumn)super.save(scidColumn);
		return result;
	}
	public void removeSCIDColumn(SCIDColumn column) {
		// TODO Auto-generated method stub
		this.remove(column);
	}
	
	public SCIDColumn findSCIDColumnById(String id){
		Criteria c = super.createCriteria(SCIDColumn.class);
		c.add(Restrictions.eq("id",Long.parseLong(id)));
		SCIDColumn sCIDColumn=(SCIDColumn) c.uniqueResult();
		return sCIDColumn;
	}
	public SCIDColumn saveColumnValue(ServiceItem serviceItem,String columnName, String value) {
		Criteria c = super.createCriteria(SCIDColumn.class);
		c.add(Restrictions.eq("serviceItem",serviceItem));
		c.add(Restrictions.eq("columnName", columnName));
		List<SCIDColumn> columns = c.list();
		SCIDColumn scidColumn = null;
		for(SCIDColumn column : columns){
			scidColumn = column;
		}
		scidColumn.setValue(value);
		return this.save(scidColumn);
	}
	public void removeSCIDColumnByIds(String[] ids){
		for(String id:ids){
			SCIDColumn sCIDColumn=this.findSCIDColumnById(id);
			this.remove(sCIDColumn);
		}
	}
	
	private int findMaxNumberByOrder(SCIDColumn column){
		Criteria c=this.createCriteria(SCIDColumn.class);
		c.add(Restrictions.eq("serviceItem",column.getServiceItem()));
		c.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder")));
		Object maxOrder = c.uniqueResult();
		int orderMax = 0; 
		if(maxOrder!=null){
			orderMax = ((Integer)maxOrder).intValue();
		}
		return orderMax;
	}
}
