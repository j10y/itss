package com.digitalchina.info.appframework.metadata.type.impl;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import com.digitalchina.info.appframework.metadata.type.AbstractMetaType;

public class TextMetaType extends AbstractMetaType{

	@Override
	public String getCnName() {
		return "нд╠╬©Р";
	}

	@Override
	public String getName() {
		return "text";
	}

	@Override
	public Type hibernateType() {
		return Hibernate.TEXT;
	}

	@Override
	public int sqlType() {
		return java.sql.Types.VARCHAR;
	}

}
