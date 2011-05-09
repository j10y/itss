package com.zsgj.info.framework.security;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: cac Date: 2006-3-15
 */

public class Constants {

	public static final String STATUS_INVALID = "0";

	public static final String STATUS_VALID = "1";

	public static final Map statusEnum = new HashMap();

	public static final String STATUS_AUTH = "1";

	public static final String STATUS_UNAUTH = "0";

	public static final Map authEnum = new HashMap();


	public static final String RESOURCE_URL = "URL";

	public static final String RESOURCE_FUNCTION = "FUNCTION";

	public static final String RESOURCE_COMPONENT = "COMPONENT";

	public static final Map resTypeEnum = new HashMap();

	static {
		statusEnum.put(STATUS_INVALID, "无效");
		statusEnum.put(STATUS_VALID, "有效");

		authEnum.put(STATUS_AUTH, "已授�?");
		authEnum.put(STATUS_UNAUTH, "未授�?");

		resTypeEnum.put("URL", RESOURCE_URL);
		resTypeEnum.put("FUNCTION", RESOURCE_FUNCTION);
		resTypeEnum.put("COMPONENT", RESOURCE_COMPONENT);
	}

	private Constants() {
	}

	public static Map statusEnum() {
		return statusEnum;
	}

	public static Map resTypeEnum() {
		return resTypeEnum;
	}

	public static Map authEnum() {
		return authEnum;
	}
}
