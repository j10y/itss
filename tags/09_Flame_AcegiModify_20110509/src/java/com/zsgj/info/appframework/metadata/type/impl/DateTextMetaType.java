package com.zsgj.info.appframework.metadata.type.impl;

import com.zsgj.info.appframework.metadata.type.AbstractMetaType;

public class DateTextMetaType extends AbstractMetaType{

	@Override
	public String getCnName() {
		return "ÈÕÆÚ¿Ø¼þ";
	}

	@Override
	public String getName() {
		return "dateText";
	}

	@Override
	public boolean isMutable() {
		return false;
	}

}
