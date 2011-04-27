package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.List;
import java.util.Map;

import com.zsgj.info.appframework.metadata.ColumnDataWrapper;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.util.BeanUtil;


public class TreeDateCode {
	//for query
	public static String encodeTreeData(List<PagePanelColumn> userVisibleColumns,
			List<Object> listData,Long total) {
		String json = "[";
		
		String temp="[";
		//temp+="{success: true, rowCount:10,data:[";
//		temp+="{'ConfigItem$id':'126','ConfigItem$configItemType':'外部客户','ConfigItem$name':'','ConfigItem$configItemStatus':'','ConfigItem$customer':'','ConfigItem$useDate':'','ConfigItem$financeInfo':'','ConfigItem$status':'','_id':1,'_parent':null,'_level':1,'_lft':1,'_rgt':6,'_is_leaf':false},";
//		temp+="{'ConfigItem$id':'125','ConfigItem$configItemType':'服务','ConfigItem$name':'dddd333','ConfigItem$configItemStatus':'','ConfigItem$customer':'','ConfigItem$useDate':'','ConfigItem$financeInfo':'','ConfigItem$status':'','_id':2,'_parent':1,'_level':2,'_lft':2,'_rgt':5,'_is_leaf':false},";
//		temp+="{'ConfigItem$id':'124','ConfigItem$configItemType':'服务','ConfigItem$name':'eee','ConfigItem$configItemStatus':'','ConfigItem$customer':'神州数码政务公司','ConfigItem$useDate':'','onfigItem$financeInfo':'','ConfigItem$status':'','_id':3,'_parent':2,'_level':3,'_lft':3,'_rgt':4,'_is_leaf':true}";
	//	List depts = this.configItemService.findAllDeptByParentDepartcode("50008953");
		temp+="{'company':'0. Johnson & Johnson','_id':1,'_parent':null,'_level':1,'_lft':1,'_rgt':8,'_is_leaf':false},";
		temp+="{'company':'1. American International Group, Inc.','_id':2,'_parent':1,'_level':2,'_lft':2,'_rgt':3,'_is_leaf':true},";
		temp+="{'company':'2. Alcoa Inc','_id':3,'_parent':1,'_level':2,'_lft':4,'_rgt':5,'_is_leaf':true}";
       temp+="]";
       
		int _parent=0;
		int _level=1;
		int _lft=1;
		int _rgt=20;
		int y=8;
		int x=1;
		boolean _is_leaf=false;
		
		for(Object object :listData) {
			String dataItem = "";
			Map item = BeanUtil.object2Map(object);
			
			for(PagePanelColumn uts : userVisibleColumns) {
				Column column = uts.getColumn();
				
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(column);
				columnDataWrapper.initData((BaseObject) object);
				
				
				SystemMainTable smt = column.getSystemMainTable();
				String tableName = smt.getTableName();
				String propertyName = column.getPropertyName();
				String tablePropertyName = tableName + "$"+ propertyName;
				String columnCnName = column.getColumnCnName();//表头标题
				String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();

				if(columnTypeName.equalsIgnoreCase("hidden")) {
					Object value = item.get(tablePropertyName);
					if(value==null) value="";
					dataItem += ""+tablePropertyName+":'"+value+"',";
				}
				else if(columnTypeName.equalsIgnoreCase("text")||
						columnTypeName.equalsIgnoreCase("dateText")||
						columnTypeName.equalsIgnoreCase("radio")) {
					Object value = item.get(tablePropertyName);
					if(value==null) value="";
					dataItem += ""+tablePropertyName+":'"+value+"',";
				}
				else if(columnTypeName.equalsIgnoreCase("select")) {
					Object value = item.get(tablePropertyName);
					if(value==null) value="";
					dataItem += ""+tablePropertyName+":'"+value+"',";
				}
				else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
					Object value = item.get(tablePropertyName);
					if(value==null) value="";
					dataItem += ""+tablePropertyName+":'"+value+"',";
				}	
				else {//其他
					Object value = item.get(tablePropertyName);
					if(value==null) value="";
					dataItem += ""+tablePropertyName+":'"+value+"',";
				}		
			}
			
			
		}
		return json;
	}
	
	
	public static String encode(List<PagePanelColumn> userVisibleColumns, 
				List<Map<String, Object>> listData,Long total) {
		String json = "";
		int _parent=0;
		int _level=1;
		int _lft=1;
		int _rgt=20;
		int y=8;
		int x=1;
		boolean _is_leaf=false;
		
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
					else {//其他
						Object value = item.get(tablePropertyName);
						if(value==null) value="";
						dataItem += ""+tablePropertyName+":'"+value+"',";
					}						
			}
			//增加参数
			///"_parent":null,"_level":1,"_lft":1,"_rgt":98,"_is_leaf":false
			dataItem += ""+"_id"+":'"+(x++)+"',";
			if(x==2){
				dataItem += ""+"_parent"+":'"+null+"',";
			}else{
				dataItem += ""+"_parent"+":'"+_parent+"',";
			}
			dataItem += ""+"_level"+":'"+_level+"',";
			dataItem += ""+"_lft"+":'"+_lft+"',";
			dataItem += ""+"_rgt"+":'"+_rgt+"',";
			dataItem += ""+"_is_leaf"+":'"+_is_leaf+"',";
			_parent+=1;
			_level+=1;
			_lft+=1;
			_rgt=_lft+2*(y--)+1;
			if(y==-1){
				_is_leaf=true;
			}else{
				_is_leaf=false;
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
