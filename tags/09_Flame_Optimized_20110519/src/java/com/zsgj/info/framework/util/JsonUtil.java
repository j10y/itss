package com.zsgj.info.framework.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zsgj.info.framework.dao.support.Page;

public class JsonUtil {
	public JsonUtil() {}
	
	/**
	 * 将结果列表转换成json格式的数据
	 * @Methods Name convertListT
	 * @Create In Jul 30, 2008 By itnova
	 * @param listData 结果列表数据
	 * @param page 分页对象
	 * @return String
	 */
	public static String convertListData2JsonString(List listData,Page page){
		String js = "";
		if(listData.size()==0){
			js = "{success: false, rowCount:0,data:["+js+"]}";
		}
		for(int i=0;i<listData.size();i++) {
			Map data =  (Map)listData.get(i);
			Set keySet = data.keySet();
			Iterator it = keySet.iterator();
			String jss = "";
			while(it.hasNext()) {
				String key = (String)it.next();
				jss += "'"+key+"':'"+(data.get(key) ==null ? "" : data.get(key))+"',";
				
			}
			jss += "'id':'"+(data.get("id")==null?"":data.get("id"))+"'";
			if(jss.length()>0&&jss.endsWith(",")) {
				jss = jss.substring(0,jss.length()-1);
			}
			js += "{"+jss+"},";
		}
		if(js.length()>0&&js.endsWith(",")) {
			js = "{success: true, rowCount:"+page.getTotalCount()+",data:["+js.substring(0,js.length()-1)+"]}";
		}
		return js;
	}
}
