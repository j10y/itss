package com.digitalchina.info.appframework.metadata.type.impl;

import java.util.HashMap;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.type.AbstractMetaType;

public class GenderMetaType extends AbstractMetaType {

	@Override
	public String getCnName() {
		return "性别列表";
	}

	@Override
	public Map getDefaultSimpleKeyValue() {
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1, "男");
		map.put(0, "女");
		return map;
	}

	@Override
	public String getName() {
		return "genderSelect";
	}

}
