package com.zsgj.info.appframework.pagemodel.autocreat.servlet;

import java.util.List;
import java.util.Map;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;


public class CoderForList {
	//for query

	public static String encode(List<PagePanelColumn> userVisibleColumns, /*List<UserTableSetting> userVisibleColumns**/
				List<Map<String, Object>> listData,Long total) {
		String json = "";
		for(Map<String,Object> item :listData) {
			String dataItem = "";
			for(PagePanelColumn uts : userVisibleColumns) {
				//为了获取表名前缀
				Column column = uts.getColumn();
				SystemMainTable smt = column.getSystemMainTable();
				String tableName = smt.getTableName();
				String propertyName = column.getPropertyName();
				String tablePropertyName = tableName + "$"+ propertyName;
				String columnCnName = column.getColumnCnName();//表头标题
				//String tablePropertyName = tableName + "." + propertyName;

					String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
					if(columnTypeName.equalsIgnoreCase("hidden")) {
						Object value = item.get(tablePropertyName);
						if(value==null) value="";
						dataItem += ""+tablePropertyName+":'"+value+"',";
	//	value				${item.id}
					}
					else if(columnTypeName.equalsIgnoreCase("text")||
							columnTypeName.equalsIgnoreCase("dateText")||
							columnTypeName.equalsIgnoreCase("radio")) {
						Object value = item.get(tablePropertyName);
						if(value==null) value="";
						dataItem += ""+tablePropertyName+":'"+value+"',";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("select")) {
						//?????
						//String foreignPropertyName = column.getForeignTableValueColumn().getPropertyName();
						Object value = item.get(tablePropertyName);
						if(value==null) value="";
						dataItem += ""+tablePropertyName+":'"+value+"',";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
						Object value = item.get(tablePropertyName);
						if(value==null) value="";
						dataItem += ""+tablePropertyName+":'"+value+"',";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("sexSelect")) {
						Object value = item.get(tablePropertyName);
						if(value==null) value="";
						dataItem += ""+tablePropertyName+":'"+value+"',";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else {//其他
						Object value = item.get(tablePropertyName);
						if(value==null) value="";
						dataItem += ""+tablePropertyName+":'"+value+"',";
					}						
			}
			if(dataItem.endsWith(",")) {
				dataItem = dataItem.substring(0,dataItem.length()-1);
			}
			dataItem = "{"+dataItem+"},";	
			json += dataItem;
		}
		if(json.endsWith(",")) {
			json = json.substring(0,json.length()-1);
		}
		json = "{success: true, rowCount:"+total+",data:["+json+"]}";
		return json;
	}
	
//	data: [
//  {name: 'Image one', url:'/GetImage.php?id=1', size:46.5, lastmod: new Date(2007, 10, 29)},
//  {name: 'Image Two', url:'/GetImage.php?id=2', size:43.2, lastmod: new Date(2007, 10, 30)}
//]	

}
