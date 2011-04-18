package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.appframework.extjs.servlet.Validator;
import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;

/**
 * 二期为展开所有字段用
 * @Class Name CoderForHeadCommonGrid
 * @Author sujs
 * @Create In May 12, 2009
 */
public class CoderForHeadCommonGrid {  
	/**
	 * 获取普通grid的表头
	 * @Methods Name encode
	 * @Create In May 12, 2009 By sujs
	 * @param pagePanelColumns
	 * @return String
	 */
	public static String encode(List<PagePanelColumn> pagePanelColumns) {
		String json = "";
		for (PagePanelColumn uts : pagePanelColumns) { //UserTableSetting uts : userVisibleColumns
			Column column = uts.getColumn();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String propertyName = column.getPropertyName();
			String tablePropertyName = tableName + "$"+ propertyName;
			String columnCnName = column.getColumnCnName();//表头标题
			String columnType = column.getSystemMainTableColumnType().getColumnTypeName();
			json += "{header:'"+columnCnName+"',dataIndex:'"+tablePropertyName+"',align : 'left',sortable : true,"+"hidden:"+columnType.equalsIgnoreCase("hidden")+"},";
		}
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return "[sm,"+json+"]";
	}
}
