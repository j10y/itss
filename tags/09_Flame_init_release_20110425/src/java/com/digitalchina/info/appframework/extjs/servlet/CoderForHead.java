package com.digitalchina.info.appframework.extjs.servlet;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;

public class CoderForHead {

	//for head
	public static String encode(List<UserTableSetting> userVisibleColumns) {
		String json = "";
		for(UserTableSetting uts : userVisibleColumns) {
			Column column = uts.getColumn();
			String columnCnName = column.getColumnCnName();//表头标题
			String propertyName = column.getPropertyName();
			json += "{header:'"+columnCnName+"',dataIndex:'"+propertyName+"'},";
		}
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return "["+json+"]";
	}
}
