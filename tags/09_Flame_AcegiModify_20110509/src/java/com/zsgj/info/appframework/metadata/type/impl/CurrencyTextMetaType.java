package com.zsgj.info.appframework.metadata.type.impl;

import java.text.DecimalFormat;

import com.zsgj.info.appframework.metadata.type.AbstractMetaType;
import com.zsgj.info.appframework.metadata.type.FormatableType;
import com.zsgj.info.framework.util.PropertiesUtil;

public class CurrencyTextMetaType extends AbstractMetaType implements
		FormatableType {

	private DecimalFormat nf = new DecimalFormat(PropertiesUtil.getProperties(
			"system.number.formatString", "###,###.##"));

	public String formatForAdd(String value) {
		if (value == null || value.equals(""))
			return value;
		return nf.format(value);
	}

	public String formatForEdit(String value) {
		if (value == null || value.equals(""))
			return value;
		return nf.format(value);
	}

	public String formatForList(String value) {
		if (value == null || value.equals(""))
			return value;
		return nf.format(value);
	}

	public String formatForView(String value) {
		if (value == null || value.equals(""))
			return value;
		return nf.format(value);
	}

}
