package com.zsgj.info.appframework.metadata.type;

/**
 * 可格式化类型
 * 
 * @Class Name FormatableType
 * @Author peixf
 * @Create In 2008-6-30
 */
public interface FormatableType extends MetaType{
	
	String formatForEdit(String value);

	String formatForAdd(String value);

	String formatForView(String value);

	String formatForList(String value);
}
