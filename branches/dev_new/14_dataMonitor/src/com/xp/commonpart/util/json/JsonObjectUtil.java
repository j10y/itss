package com.xp.commonpart.util.json;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public final class JsonObjectUtil {
	@SuppressWarnings("unchecked")
	public static String list2Json(List source) {
		try {
			if (null == source || source.isEmpty()) {
				return null;
			}
			StringBuffer json = null;
			if (source.get(0) instanceof ListOrderedMap) {
				json = new StringBuffer(JSONArray.fromObject(source).toString());
			}
			if (source.get(0) instanceof Map) {
				json = new StringBuffer(JSONArray.fromObject(source).toString());
			} else {
				json = new StringBuffer(StringPool.CHARACTER_MIDDLE_LEFT);
				for (int i = 0; i < source.size(); i++) {
					Object item = source.get(i);
					if (item instanceof Object) {
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
			e.printStackTrace();
			return null;
		}
	}

}
