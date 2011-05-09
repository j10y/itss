package com.zsgj.info.appframework.metadata.type.impl;

import com.zsgj.info.appframework.metadata.type.AbstractMetaType;
import com.zsgj.info.appframework.metadata.type.FormatableType;

public class TextAreaMetaType extends AbstractMetaType implements FormatableType{

	public static String HTMLEncode(String txt) {
		if (txt!=null && txt.length()>0) {
			txt=txt.replaceAll("&","&amp;");
			txt=txt.replaceAll("<","&lt;");
			txt=txt.replaceAll(">","&gt;");
			txt=txt.replaceAll("\"","&quot;");
			txt=txt.replaceAll("'","&#146;");
		}
		return txt;
	}
	
	@Override
	public String getCnName() {
		return "ÎÄ±¾Óò";
	}

	@Override
	public String getName() {
		return "textArea";
	}

	public String formatForAdd(String value) {
		// TODO Auto-generated method stub
		return value;
	}

	public String formatForEdit(String value) {
		// TODO Auto-generated method stub
		return value;
	}

	public String formatForList(String value) {
		// TODO Auto-generated method stub
		return value;
	}

	public String formatForView(String value) {
		// TODO Auto-generated method stub
		return value;
	}

}
