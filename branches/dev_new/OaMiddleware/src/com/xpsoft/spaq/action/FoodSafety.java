package com.xpsoft.spaq.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.rpc.ParameterMode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import oracle.net.ano.SupervisorService;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xpsoft.netoa.util.InitSys;
import com.xpsoft.netoa.util.UserCheckMap;
import com.xpsoft.padoa.test.entity.Children;
import com.xpsoft.padoa.test.entity.LoginSendLog;
import com.xpsoft.padoa.test.entity.User;
import com.xpsoft.padoa.test.service.UserService;
import com.xpsoft.framework.context.ContextHolder;
import com.xpsoft.framework.util.FileDownloaderUtil;
import com.xpsoft.framework.util.HttpUtil;
import com.xpsoft.framework.util.PropertiesUtil;
import com.xpsoft.framework.util.StaticHtml;
import com.xpsoft.framework.util.StringUtil;
import com.xpsoft.framework.web.adapter.struts2.BaseAction;

/**
 * pad端访问食品安全
 * @Class Name FoodSafety
 * @Author likang
 * @Create In Aug 2, 2011
 */
public class FoodSafety extends BaseAction{
	
	//中间件访问oa地址 webservice
	public static final String FoodSafetyUrl = PropertiesUtil.getProperties("system.oa.webserviceUrl");
	//中间件访问oa地址
	public static final String OAURLDOWN = PropertiesUtil.getProperties("system.oa.url");
	
	/**
	 * 转发pad端请求到食品安全系统
	 * @Methods Name forwardRequest
	 * @Create In Aug 2, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String forwardRequest() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		String jsonString = jo.toString();
		//访问食品安全的url
		String accessUrl = "";
		StringBuffer url = new StringBuffer();
		url.append(FoodSafetyUrl);
		url.append(accessUrl);
		try {
			//得到所有参数
			String paramString = super.getRequest().getParameter("");
			//解码
			if (accessUrl != null) {
				url.append("?");
				url.append(HttpUtil.encodeParamString(paramString));
				//读取json
				jsonString = HttpUtil.getJsonStringByUrl(url.toString());
			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//输出json
		PrintWriter pw = super.getPrintWriter();
		pw.append(jsonString);
		pw.flush();
		pw.close();
		return null;
	}
	
	public static void main(String args[]) {
		
		System.out.println(HttpUtil.encodeParamString("id=15&commentDesc=没问题，你可以修改家了&userid=1&taskId=54&activityName=部门负责人审批&nextuser=&checkboxvalue="));
		System.out.println(HttpUtil.encodeParamString("taskid=分发"));
	}
	
}
