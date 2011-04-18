package com.digitalchina.info.appframework.metadata.type.impl;

import java.util.HashMap;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.type.AbstractMetaType;

public class YesNoSelectMetaType extends AbstractMetaType {

	@Override
	public String getCnName() {
		return "�Ƿ����";
	}

	@Override
	public Map getDefaultSimpleKeyValue() {
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1, "��");
		map.put(0, "��");
		return map;
	}

	@Override
	public String getName() {
		return "yesOrNoSelect";
	}

}
