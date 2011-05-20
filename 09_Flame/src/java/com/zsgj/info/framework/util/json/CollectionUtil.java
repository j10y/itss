package com.zsgj.info.framework.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.zsgj.info.framework.dao.BaseObject;

public class CollectionUtil {
	private static CollectionUtil collectionUtil = null;

	private CollectionUtil() {

	}

	/**
	 * Singleton
	 * 
	 * @return
	 */
	public static CollectionUtil getCollectionUtil() {
		if (collectionUtil == null) {
			collectionUtil = new CollectionUtil();
		}
		return collectionUtil;
	}

	@SuppressWarnings("unchecked")
	public static/* synchronized */List transList(List arg0) {
		if (arg0 == null) {
			return null;
		}
		List result = new ArrayList();
		for (int i = 0; i < arg0.size(); i++) {

			if (arg0.get(i) instanceof List) {
				List item = (List) arg0.get(i);
				HashMap map = new HashMap();
				for (int j = 0; i < item.size(); j++) {
					map.put("v" + j + "v", item.get(j));
				}
				result.add(map);
			} else if (arg0.get(i) instanceof Map) {
				result.add(arg0.get(i));
			} else if (arg0.get(i) instanceof BaseObject) {
				if (ClassUtils.hasMethod(arg0.get(i).getClass(),
						StringPool.REFLECTION_METHOD_MAP, null)) {
					result.add(ReflectionUtils.invokeMethod(ClassUtils
							.getMethodIfAvailable(arg0.get(i).getClass(),
									StringPool.REFLECTION_METHOD_MAP, null),
							arg0.get(i)));
				} else {
					result.add(arg0.get(i));
				}

			} else {
				Object[] item = (Object[]) arg0.get(i);
				if (item != null) {
					HashMap map = new HashMap();
					for (int j = 0; j < item.length; j++) {
						map.put("v" + j + "v", item[j]);
					}
					result.add(map);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map transRequestParametersMap(Map map) {
		if (map == null || "".equals(map)) {
			return null;
		}
		Map result = new HashMap();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String[] keyValues = (String[]) map.get(key);
			String keyValue = StringUtil.transFromArray(keyValues);
			result.put(key, keyValue);
		}
		return result;
	}

	/**
	 * ��һ��List�а���Map �ṹʱ������List������Map��ָ��Ԫ�أ�key��ʶ����ת��Ϊһ���ַ����ṹ���м��ԡ������ָ�
	 * 
	 * @param arg0
	 * @param key
	 * @return
	 */
	public String listElemets2String(List arg0, Object key) {
		StringBuffer sb = new StringBuffer();
		Iterator it = arg0.iterator();
		while (it.hasNext()) {
			Map item = (Map) it.next();
			if (item.containsKey(key)) {
				if (item.get(key) != null) {
					if (StringUtils.isNotEmpty(item.get(key).toString())) {
						sb.append(item.get(key).toString());
						sb.append(StringPool.CHARACTER_COMMA);
					}
				}
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(StringPool.CHARACTER_COMMA));
		return sb.toString();
	}

	/**
	 * ��һ�������ת��Ϊjson���ݽṹ
	 * 
	 * @param source
	 * @return
	 */
	public String list2Json(List source) {
		try {
			if (null == source || source.isEmpty()) {
				return null;
			}
			StringBuffer json = null;
			if (source.get(0) instanceof Map) {
				json = new StringBuffer(JSONArray.fromObject(source).toString());
			} else {
				json = new StringBuffer(StringPool.CHARACTER_MIDDLE_LEFT);
				for (int i = 0; i < source.size(); i++) {
					Object item = source.get(i);
					if (item instanceof BaseObject) {
						Class clazz = item.getClass();
						if (ClassUtils.hasMethod(clazz,
								StringPool.REFLECTION_METHOD_JSON, null)) {
							String temp=ReflectionUtils.invokeMethod(
									ClassUtils.getMethodIfAvailable(clazz,
											StringPool.REFLECTION_METHOD_JSON,
											null), item).toString();
							json.append(temp);
						} else {
							json.append(JSONArray.fromObject(item).toString());
						}
					} else if (item instanceof Map) {
						json.append(JSONArray.fromObject(item).toString());
					}
					json.append(StringPool.CHARACTER_COMMA);
				}
				json.deleteCharAt(json.lastIndexOf(StringPool.CHARACTER_COMMA));
				json.append(StringPool.CHARACTER_MIDDLE_RIGHT);
			}
			return json.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ȡ��һ��mapһ��keyֵ��value ���������key,��ô����null �������key,��value������,�򷵻�null
	 * �������key,ͬʱ����value �򷵻�object
	 */
	public Object getMapValueByKey(Map map, Object key) {
//		Object o = null;
		if (null == key) {
			return null;
		}
		if (!map.containsKey(key)) {
			return null;
		} else {
			if (null == map.get(key)) {
				return null;
			} else {
				return map.get(key);
			}
		}
	}

}
