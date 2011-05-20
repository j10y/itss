package com.zsgj.info.appframework.extjs.servlet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;

public class ExtHtmlBuilder {

	public static String genQueryHtml(Map<String, Object> queryMap, 
				List<UserTableQueryColumn> columns) {
		StringBuffer buff = new StringBuffer();
		buff.append("'<table cellSpacing=\"0\" cellPadding=\"0\" width=\"100%\">");
		buff.append("<tr style=\"height:28px;\" class=\"x-grid3-header\">");
		buff.append(" <td class=\"x-window-body\" style=\"font-size:9pt\">");
		
		for(UserTableQueryColumn utqc :columns) {			
			Column column = utqc.getColumn();
			String propertyName = column.getPropertyName();
//			String columnName = column.getColumnName();
			String columnCnName = column.getColumnCnName();
//			String width = utqc.getLengthForPage(); 
			//begin by peixf
			Integer isHiddenQueryItem = utqc.getIsHiddenItem();
			boolean isHiddenQuery = isHiddenQueryItem!=null&& isHiddenQueryItem.intValue()==1;
			
			SystemTableQueryColumn sysQueryColumn = utqc.getSystemTableQueryColumn();
			
			String columnTypeName = column.getSystemMainTableColumnType().getColumnTypeName();
			String matchModeStr = sysQueryColumn.getMatchModeStr();	
			
			if(columnTypeName.equalsIgnoreCase("text")||columnTypeName.equalsIgnoreCase("textArea")) {		
				if(!matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
					buff.append(columnCnName).append(":");
					buff.append(" <input type=\"text\"");
					buff.append(" id=\"").append(propertyName).append("\"");
					buff.append(" name=\"").append(propertyName).append("\"");
					buff.append(" class=\"x-form-text\"");
					buff.append(" value=\"\" />");
				}else {
					String propertyNameBegin = column.getPropertyName()+"Begin";
					String propertyNameEnd = column.getPropertyName()+"End";
					buff.append(columnCnName).append(":");
					buff.append(" <input type=\"text\"");
					buff.append(" id=\"").append(propertyNameBegin).append("\"");
					buff.append(" name=\"").append(propertyNameBegin).append("\"");
					buff.append(" class=\"x-form-text\"");
					buff.append(" value=\"\" />");

					buff.append(" <input type=\"text\"");
					buff.append(" id=\"").append(propertyNameEnd).append("\"");
					buff.append(" name=\"").append(propertyNameEnd).append("\"");
					buff.append(" class=\"x-form-text\"");
					buff.append(" value=\"\" />");
				}
			}else if(columnTypeName.equalsIgnoreCase("select")) {
				buff.append(columnCnName).append(":");
				buff.append(" <select");
				buff.append(" id=\"").append(propertyName).append("\"");
				buff.append(" name=\"").append(propertyName).append("\"");
				buff.append(" class=\"x-form-text\"");
				buff.append(" >");
				buff.append(" <option value=\"\"></option>");
				String foreignPropNames = propertyName+"s";
				Integer extSelectType=column.getExtSelectType();
				if(extSelectType==null|| extSelectType!=null&& extSelectType==0){
					List vList = (List)queryMap.get(foreignPropNames);
					Iterator vIter = vList.iterator();
					while(vIter.hasNext()){
						Object bean = (Object)vIter.next();
						try {
							String key = BeanUtils.getProperty(bean, "id");
							String text = BeanUtils.getProperty(bean, column.getForeignTableValueColumn().getPropertyName());
							buff.append(" <option value=\"").append(key).append("\">").append(text).append("</option>");
		
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}else if(extSelectType==2){
					List vList = (List)queryMap.get(foreignPropNames);
					Iterator vIter = vList.iterator();
					while(vIter.hasNext()){
						Object bean = (Object)vIter.next();
						try {
							String key = BeanUtils.getProperty(bean, "id");
							String text = BeanUtils.getProperty(bean, "extOptionValue");
							buff.append(" <option value=\"").append(key).append("\">").append(text).append("</option>");
		
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				buff.append(" </select>");
			     
			}else if(columnTypeName.equalsIgnoreCase("hidden")||isHiddenQuery/*|| isHiddenQuery*/) {
				buff.append(columnCnName).append(":");
				buff.append(" <input type=\"hidden\"");
				buff.append(" id=\"").append(propertyName).append("\"");
				buff.append(" name=\"").append(propertyName).append("\"");
				buff.append(" value=\"\" />");
			}else if(columnTypeName.equalsIgnoreCase("dateText")) {
				if(!matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")) {
					buff.append(columnCnName).append(":");
					buff.append(" <input type=\"text\" readonly");
					buff.append(" id=\"").append(propertyName).append("\"");
					buff.append(" name=\"").append(propertyName).append("\"");
					buff.append(" class=\"Wdate\"");
					buff.append(" onclick=\"WdatePicker({isShowWeek:true})\");");
					buff.append(" value=\"\" />");
				}else {
					
				}					
			}else if(columnTypeName.equalsIgnoreCase("yesNoSelect")) {
				
			}
		}
		buff.append("</td></tr></table>'");
		return buff.toString();
	}
	
	
/*	'<table cellSpacing=0 cellPadding=0 width="100%">'+
    '<tr style="height:28px;" class="x-grid3-header">'+
      '<td class="x-window-body" style="font-size:9pt">图书名称：</td>'+
      '<td class="x-window-body" style="font-size:9pt"><input type="text" id="name" name="bookName" class="x-form-text" value=""></td>'+
      '<td class="x-window-body" style="font-size:9pt">图书编号：</td>'+
      '<td class="x-window-body" style="font-size:9pt"><input type="text" name="bookCode" class="x-form-text" value=""></td>'+
      '<td class="x-window-body" style="font-size:9pt">作者：</td>'+
      '<td class="x-window-body" style="font-size:9pt"><input type="text" name="author" class="x-form-text" value=""></td>'+
      '<td class="x-window-body" style="font-size:9pt">出处：</td>'+
      '<td class="x-window-body" style="font-size:9pt"><input type="text" name="publisher" class="x-form-text" value=""></td>'+
      '<td class="x-window-body" style="font-size:9pt">日期：</td>'+
      '<td class="x-window-body" style="font-size:9pt">' +
        '<input id="date"  name="date" type="text" class="Wdate" value="" readonly onclick="WdatePicker({isShowWeek:true})">&nbsp;&nbsp;'+
        '<input type="reset" name="reset" class="x-btn-pressed" value="重置">'+
      '</td>'+
     '</tr>'+
	'</table>'*/
	
}
