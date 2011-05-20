package com.zsgj.info.appframework.extjs.servlet;

import java.util.List;
import java.util.Map;

import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;

public class CoderForPort {
	//for query

	public static String forQuery(List<UserTableSetting> userVisibleColumns,List<Map<String, Object>> listData) {
		String json = "";
		for(Map<String,Object> item :listData) {
			String dataItem = "";
			for(UserTableSetting uts : userVisibleColumns) {
//				String columnCnName = uts.getColumn().getColumnCnName();//表头标题
				SystemMainTableColumn mainTableColumn = uts.getMainTableColumn();			
				if(mainTableColumn.getIsExtColumn()==SystemMainTableColumn.isMain) {//主表
					String propertyName = mainTableColumn.getPropertyName();//
					String columnTypeName = mainTableColumn.getSystemMainTableColumnType().getColumnTypeName();
					if(columnTypeName.equalsIgnoreCase("hidden")) {
						Object value = item.get(propertyName);
						dataItem += ""+propertyName+":'"+value+"',";
	//	value				${item.id}
					}
					else if(columnTypeName.equalsIgnoreCase("text")||
							columnTypeName.equalsIgnoreCase("dateText")||
							columnTypeName.equalsIgnoreCase("radio")) {
						Object value = item.get(propertyName);
						
						dataItem += ""+propertyName+":'"+value+"',";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("select")) {
						//?????
//						String foreignPropertyName = uts.getMainTableColumn().getForeignTableValueColumn().getPropertyName();
						Object value = item.get(propertyName);
						dataItem += ""+propertyName+":'"+value+"',";
	//	value				${item[column.mainTableColumn.propertyName]}
					}
					else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
						Object value = item.get(propertyName);
						dataItem += ""+propertyName+":'"+value+"',";
	//	value				${item[column.mainTableColumn.propertyName]}
					}	
					else {//其他
						Object value = item.get(propertyName);
						dataItem += ""+propertyName+":'"+value+"',";
					}
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
		json = "{success: true, rowCount:"+listData.size()+",data:["+json+"]}";
		//json = "{'data':["+json+"]}";
		return json;
	}
	
//	data: [
//  {name: 'Image one', url:'/GetImage.php?id=1', size:46.5, lastmod: new Date(2007, 10, 29)},
//  {name: 'Image Two', url:'/GetImage.php?id=2', size:43.2, lastmod: new Date(2007, 10, 30)}
//]	

	//for head
	public static String forHead(List<UserTableSetting> userVisibleColumns) {
		String json = "";
		for(UserTableSetting uts : userVisibleColumns) {
			String columnCnName = uts.getColumn().getColumnCnName();//表头标题
			SystemMainTableColumn mainTableColumn = uts.getMainTableColumn();
			if(mainTableColumn.getIsExtColumn()==SystemMainTableColumn.isMain) {//主表
				String propertyName = mainTableColumn.getPropertyName();//
//				String columnTypeName = mainTableColumn.getSystemMainTableColumnType().getColumnTypeName();
				json += "{header:'"+columnCnName+"',dataIndex:'"+propertyName+"'},";
			}
			
		}
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return "["+json+"]";
	}
}
