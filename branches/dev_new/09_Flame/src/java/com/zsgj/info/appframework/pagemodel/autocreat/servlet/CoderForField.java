package com.zsgj.info.appframework.pagemodel.autocreat.servlet;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;

/**
 * Ϊչ�������ֶ�ʹ��
 * @Class Name CoderForField
 * @Author sujs
 * @Create In May 12, 2009
 */
public class CoderForField {  
	/**
	 * ��ȡ��ʾgrid����ʾ�ֶ�
	 * @Methods Name encode
	 * @Create In May 12, 2009 By sujs
	 * @param pagePanelColumns
	 * @return String
	 */
	public static String encode(List<PagePanelColumn> pagePanelColumns) {
		String json = "";
		for (PagePanelColumn uts : pagePanelColumns) {
			Column column = uts.getColumn();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String propertyName = column.getPropertyName();
			String tablePropertyName = tableName + "$"+ propertyName;
			@SuppressWarnings("unused")
			String columnCnName = column.getColumnCnName();//��ͷ����
			@SuppressWarnings("unused")
			String columnType = column.getSystemMainTableColumnType().getColumnTypeName();
			json += "{name:'"+tablePropertyName+"'},";
		}
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return "["+json+"]";
	}
	
}
