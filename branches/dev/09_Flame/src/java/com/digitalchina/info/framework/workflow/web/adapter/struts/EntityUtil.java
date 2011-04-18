package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForward;

import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

public class EntityUtil extends BaseDispatchAction {

	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");

	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");

	/**
	 * 删除
	 */
	public ActionForward removeEntity(String className, String removedIds) {
		Class clazz = getClass(className);
		if (removedIds != null) {
			String[] idArg = removedIds.split(",");
			for (int i = 0; i < idArg.length; i++) {
				if (!idArg[i].equals("") && !idArg[i].equals("undefined")) {
					metaDataManager.removeEntityData(clazz, idArg[i]);
				}
			}
		}
		return null;
	}

	/**
	 * js删除
	 */
	public ActionForward removeExtjsEntity(HttpServletRequest request) {
		// new EntityUtil().removeEntity(OrderApplyAAP.class,"");
		String[] idArg = request.getParameterValues("idArg");
		if (idArg != null) {
			for (int i = 0; i < idArg.length; i++) {
				if (!idArg[i].equals("")) {
					metaDataManager.removeEntityData(clazz, idArg[i]);
				}
			}
		}
		return null;
	}

	/**
	 * 把form放入map
	 */
	public Map getDataMap(String formData) {
		if (formData != null && !formData.equals("")) {
			formData = HttpUtil.ConverUnicode(formData);
			JSONObject object = JSONObject.fromObject(formData);
			Iterator dataIterator = object.keys();
			Map dataMap = new HashMap();
			while (dataIterator.hasNext()) {
				String key = (String) dataIterator.next();
				String value = object.getString(key);
				dataMap.put(key, value);
			}
			return dataMap;
		}
		return null;
	}

	/**
	 * 把form放入set
	 */
	public List getDataArray(String formData) {
		if (formData != null && !formData.equals("")) {
			formData = HttpUtil.ConverUnicode(formData);
			String ids[] = formData.split(":\"on\",");
			List list = new ArrayList();
			for (String id : ids) {
				if (id.startsWith("{")) {
					id = id.substring(2, id.length() - 1);
				}
				if (id.endsWith("\":\"on\"}")) {
					id = id.substring(1, id.length() - 7);
				}
				list.add(id);
			}
			return list;

		}
		return null;
	}

	public String[] getArray(String data) {
		data = HttpUtil.ConverUnicode(data);
		String str = data.replace("\"", "");
		String item = str.substring(1,str.length()-1);
		String arr[] = item.split(",");
		return arr;
	}

	public Class getClass(String className){
		try {
			Class clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把以endWidth结尾的字符串放入map
	 */
	public Map getDataMap(String gridData, String endWidth) {
		if (gridData != null) {
			gridData = HttpUtil.ConverUnicode(gridData);
			Map dataMap = new HashMap();
			gridData = gridData.substring(0, gridData.length()
					- endWidth.length());
			JSONArray objectArg = JSONArray.fromObject("[" + gridData + "]");
			for (int i = 0; i < objectArg.size(); i++) {
				JSONObject object = (JSONObject) objectArg.get(i);
				Iterator objectIterator = object.keys();
				while (objectIterator.hasNext()) {
					String key = (String) objectIterator.next();
					String value = object.getString(key);
					dataMap.put(key, value);
				}
			}
			return dataMap;
		}
		return null;

	}

	/**
	 * 逻辑删除数据
	 */
	public void backupData(String className, String id, String classEventName) {

		try {
			Class clazz = Class.forName(className);
			Object obj = service.find(clazz, id);
			Class clazzEve = Class.forName(classEventName);
			Object objEve = clazzEve.newInstance();
			BeanUtils.copyProperties(objEve, obj);
			metaDataManager.removeEntityData(clazz, id);
			BeanUtils.setProperty(objEve, "id", null);
			service.save(objEve);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteDate(Object ob,Object obe){
		try {
			BeanUtils.copyProperties(obe, ob);
			BeanUtils.setProperty(obe, "id", null);
			service.save(obe);
			service.remove(ob);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
