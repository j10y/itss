package com.zsgj.itil.workflow.action.project;

import java.util.HashMap;
import java.util.Map;

public class ActionHelper {
	public static String getUrl(String nodeName) {
		String url = "";
		Map urls = new HashMap();
		urls.put("demand", "/test/1.jsp");
		urls.put("config", "/test/2.jsp");
		urls.put("plan", "/test/3.jsp");
		urls.put("contract", "/test/4.jsp");
		urls.put("confirm", "/test/5.jsp");
		return url;
	}
}
