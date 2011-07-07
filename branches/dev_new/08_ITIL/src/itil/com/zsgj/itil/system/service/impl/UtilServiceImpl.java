package com.zsgj.itil.system.service.impl;

import java.lang.reflect.Field;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.itil.system.dao.UtilDao;
import com.zsgj.itil.system.service.UtilService;

public class UtilServiceImpl extends BaseService implements UtilService {

	private UtilDao utilDao;
	
	
	
	public UtilDao getUtilDao() {
		return utilDao;
	}



	public void setUtilDao(UtilDao utilDao) {
		this.utilDao = utilDao;
	}



	public Page searchComboMessage(Class clazz, String propertyName, String propertyValue, boolean isLike, String orderBy, boolean isAsc, int pageNo, int pageSize){
		Object value = null;
		try {
			Field field = clazz.getDeclaredField(propertyName);
			String typeName = field.getType().getName();
			if(typeName.equals("java.lang.String")){
				value = propertyValue;
			}else if(typeName.equals("int") || typeName.equals("java.lang.Integer")) {
            	value = Integer.valueOf(propertyValue);
            }else if(typeName.equals("long") || typeName.equals("java.lang.Long")) {
            	value = Long.valueOf(propertyValue);
            }else if(typeName.equals("boolean") || typeName.equals("java.lang.Boolean")) {
            	value = Boolean.valueOf(propertyValue);
            }else if(typeName.equals("float") || typeName.equals("java.lang.Float")) {
            	value = Float.valueOf(propertyValue);
            }else if(typeName.equals("double") || typeName.equals("java.lang.Double")) {
            	value = Double.valueOf(propertyValue);
            }else if(typeName.equals("java.util.Date")){//增加一个万能时间转换器
            	
            }else{//Object类型根本就不可能
            	
            }
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utilDao.searchComboMessage(clazz, propertyName, value, isLike, orderBy, isAsc, pageNo, pageSize);
	}
}
