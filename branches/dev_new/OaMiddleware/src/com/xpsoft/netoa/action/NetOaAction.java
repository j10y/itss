package com.xpsoft.netoa.action;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.xpsoft.framework.util.FileDownloaderUtil;
import com.xpsoft.framework.util.HttpUtil;
import com.xpsoft.framework.util.PropertiesUtil;
import com.xpsoft.framework.web.adapter.struts2.BaseAction;
import com.xpsoft.netoa.util.UserCheckMap;

/**
 * pad端访问oa系统接口
 * @Class Name NetOa
 * @Author likang
 * @Create In Jul 25, 2011
 */
public class NetOaAction extends BaseAction{
	
	//中间件访问oa地址 webservice
	public static final String OAURL = PropertiesUtil.getProperties("system.oa.webserviceUrl");
	//中间件访问oa地址
	public static final String OAURLDOWN = PropertiesUtil.getProperties("system.oa.url");
	
	/**
	 * 检查是否需要更新软件
	 * @Methods Name checkUpdate
	 * @Create In 2011-10-12 By Jack
	 * @return String
	 * @throws Exception String
	 */
	public String checkUpdate() throws Exception{
		JSONObject jo = new JSONObject();
		try{
			String currentVersion = super.getRequest().getParameter("cv");
			
			Map<String, String> ck = FileDownloaderUtil.checkUpdate(super.getResponse(), new Integer(currentVersion),super.getSession().getServletContext().getRealPath("/"));
			if (ck != null){
				jo.put("success", (new Boolean(ck.get("success")).booleanValue()));
				jo.put("fn", ck.get("fn"));
				jo.put("url", ck.get("url"));
			}else{
				jo.put("success", false);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		printJson(jo);
		return null;
	}
	
	/**
	 * pad端下载文件
	 * @Methods Name downFile
	 * @Create In Jul 26, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String downFile() throws Exception  {
		try {
			String url = super.getRequest().getParameter("url");
			String name = super.getRequest().getParameter("name");
			if (url != null && name != null) {
				url = OAURLDOWN + "/attachFiles/" + url;
				byte [] bytes = FileDownloaderUtil.readFileByUrl(url);
				FileDownloaderUtil.downloadFile(super.getResponse(), name, bytes);
//				FileDownloaderUtil.downAndLoadFileTogether(url, super.getResponse(), name);
				bytes = null;
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 是否安全访问
	 * @Methods Name isSafeAccess
	 * @Create In Jul 28, 2011 By Administrator
	 * @return boolean
	 */
	public boolean isSafeAccess() {
		String userid = super.getRequest().getParameter("userid");
		String password = super.getRequest().getParameter("password");
//		password = StringUtil.encryptSha256(password);
		boolean isSafe = false;
		if (UserCheckMap.passCheck(userid, password)) {
			isSafe = true;
		}
		return isSafe;
	}
	
	/**
	 * 1.1 pad端登陆
	 * @Methods Name login
	 * @Create In Jul 27, 2011 By Administrator
	 * @return
	 * @throws Exception String
	 */
	public String login() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("msg", "");
		jo.put("userid", "");
		jo.put("password", "");
		//访问oa的url
		String accessUrl = OAURL + "/LoginServiceImpl";
		try {
			String loginName = super.getRequest().getParameter("loginName");
			String password = super.getRequest().getParameter("password");
			if (loginName != null && password != null) {
				//调用接口方法 
				String methodName = "Login";
				//public String Login(String userName, String passwd)
				Object [] paramArr = new Object[]{loginName,password};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				if (backJsonObejct.containsKey("success")) {
					jo = backJsonObejct;
					//装入缓存
					UserCheckMap.put(backJsonObejct.getString("userid"), backJsonObejct.getString("password"));
				}  else {
					jo.put("msg", backJsonObejct.getString("msg"));
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			//假数据用于无url连接时
//			jo.put("success", true);
//			jo.put("msg", "");
//			jo.put("userid", 99);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 1.2 查询每个功能的个数
	 * @Methods Name queryAllCounts
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {success:true, dytzCount :”12”,dycyCount :”23”, dbsxCount :”78”, yytzCount :”45”, yycyCount :”78”}
	 */
	public String queryAllCounts() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("dytzCount", "0");
		jo.put("dycyCount", "0");
		jo.put("dbsxCount", "0");
		jo.put("yytzCount", "0");
		jo.put("yycyCount", "0");
		jo.put("dyffCount", "0");
		//访问oa的url
		String accessUrl = OAURL + "/LoginServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			if (userid != null && UserCheckMap.get(userid) != null) {
				String methodName = "getInfoCount";
				//public String getInfoCount(String userId)
				Object [] paramArr = new Object[]{userid,UserCheckMap.get(userid)};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	
	/**
	 * 1.3.	通过用户姓名或者username查询人员列表
	 * @Methods Name queryUser
	 * @Create In Jul 29, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {
		data:[
			{id:”1”,name:”张喜”}，
				{id:”1”,name:”张喜”}，
				{id:”1”,name:”张喜”}
			]
		}
	 */
	public String queryUser() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/LoginServiceImpl";
		try {
			String name = super.getRequest().getParameter("name");
			String userid = super.getRequest().getParameter("userid");
			String departId = super.getRequest().getParameter("deptId");
			//public String findUserByUserName(String userName,String userId,String passwd,String departId)
			if (name != null && userid != null && UserCheckMap.get(userid) != null) {
				String methodName = "findUserByUserName";
				//通过webserice返回json对象
				Object [] paramArr = new Object[]{name, userid,UserCheckMap.get(userid),departId};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	/**
	 * 1.4.	查询归档类型
	 * @Methods Name queryGdlx
	 * @Create In Aug 5, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String queryGdlx() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			//public String findUserByUserName(String userName)
			if (userid != null && UserCheckMap.get(userid) != null) {
				String methodName = "getGdlx";
				//通过webserice返回json对象
				Object [] paramArr = new Object[]{};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 1.5选择分管领导
	 * @Methods Name queryFgld
	 * @Create In Aug 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String queryFgld() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			if (userid != null && UserCheckMap.get(userid) != null) {
				String methodName = "findFgld";
				//public String findFgld(String userId,String passwd)
				//通过webserice返回json对象
				Object [] paramArr = new Object[]{userid,UserCheckMap.get(userid)};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 1.6 查询分局人
	 * @Methods Name queryFjry
	 * @Create In Aug 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String queryFjry() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			if (userid != null && UserCheckMap.get(userid) != null) {
				String methodName = "findFfry";
				//public String findFfry(String userId,String passwd)
				//通过webserice返回json对象
				Object [] paramArr = new Object[]{userid,UserCheckMap.get(userid)};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 1.7 局长审批
	 * @Methods Name queryFjry
	 * @Create In Aug 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String queryJld() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			if (userid != null && UserCheckMap.get(userid) != null) {
				String methodName = "findJld";
				//public String findJld(String userId,String passwd)
				//通过webserice返回json对象
				Object [] paramArr = new Object[]{userid,UserCheckMap.get(userid)};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	public String testws() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = "a";
//		String url ="http://127.0.0.1:8080/HelloWebservice/services/HelloWebservice";
		String url = "http://127.0.0.1:8080/jtcf/webservices/MenuWebService";
		try {
			String name = super.getRequest().getParameter("name");
			if (name != null) {
				String methodName = "reloadMenu";
//				methodName ="example";
				//通过webserice返回json对象
				int a = 10000;
				List list = new ArrayList<String>();
				list.add("hah");
				list.add("中文");
				Object [] paramArr = new Object[]{name,"hah",list};
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(url.toString(), methodName, paramArr);
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	
	/**
	 * 2.	待阅通知-2.1.	查询待阅通知列表
	 * @Methods Name queryDytz
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String queryDytz() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("totalCount", "0");
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/NoticeServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String pageNum = super.getRequest().getParameter("pageNum");
			String pageSize = super.getRequest().getParameter("pageSize");
			String title = super.getRequest().getParameter("title");
			String department = super.getRequest().getParameter("department");
			String password = UserCheckMap.get(userid); 
			if (userid != null && pageNum != null && pageSize != null && password != null) {
				//调用接口方法 
				String methodName = "getDyNotice";
				//public String getDyNotice(String userId,String passwd,String pageNum,String pageSize,String title,String department)
				Object [] paramArr = new Object[]{userid,password,pageNum,pageSize,title,department};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	
	/**
	 * 2.	待阅通知-2.2.	点击列表查看详细内容
	 * @Methods Name queryDytzDetail
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 *  {success:true, clickrate:”893”, department:”办公室”, desc:”通知内容。。。”，
 		accessories：[{name:”文件通知.doc”,url:”/a/文件知.doc”,type:”0”},{name:””,url:””,type:”1”}]
 		}
	 */
	public String queryDytzDetail() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("clickrate", "0");
		jo.put("department", "");
		jo.put("desc", "");
		jo.put("accessories", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/NoticeServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String password = UserCheckMap.get(userid); 
			String id = super.getRequest().getParameter("id");
			if (userid != null && id != null && password != null) {
				//调用接口方法 
				String methodName = "getNoticeDetail";
				//public String getNoticeDetail(String noticeId,String userId,String passwd);
				Object [] paramArr = new Object[]{id,userid,password};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 2.	待阅通知-2.3.	回复通知
	 * @Methods Name queryDytzReply
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {success：true }   回复成功
		{success：false } 回复失败
		id=1&commentDesc=这个通知我已经读过了&userid=99
	 */
	public String queryDytzReply() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		//访问oa的url
		String accessUrl = OAURL + "/NoticeServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String id = super.getRequest().getParameter("id");
			String commentDesc = super.getRequest().getParameter("commentDesc");
			String password = UserCheckMap.get(userid); 
			if (userid != null && id != null && commentDesc != null && password != null) {
				//调用接口方法 
				String methodName = "saveNoticeComment";
				//public String saveNoticeComment(String userId,String passwd,String noticeId,String commentDesc);
				Object [] paramArr = new Object[]{userid,password,id,commentDesc};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	/**
	 * 3.	已阅通知-3.1.	查询已阅通知的类型
	 * @Methods Name queryDytzReply
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {返回
		{success:true,data:[
		{typeId:”1”,typeName:”城市创建通知”},
		{typeId:”21”,typeName:”全系统通知”},
		{typeId:”13”,typeName:”鼎城区通知”}
		]}
	 */
	public String queryYytzTypes() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/NoticeServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String password = UserCheckMap.get(userid); 
			if (userid != null && password != null) {
				//调用接口方法 
				String methodName = "getNoticeType";
				//public String getNoticeType(String userId,String passwd);
				Object [] paramArr = new Object[]{userid,password};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			}
		} catch (Exception e) {
			// TODO: handle exception
//			jo.put("success", true);
//			JSONArray jArray = new JSONArray();
//			jArray.add("{typeId:\"1\",typeName:\"假城市创建通知\"}");
//			jArray.add("{typeId:\"21\",typeName:\"假全系统通知\"}");
//			jArray.add("{typeId:\"13\",typeName:\"假鼎城区通知\"}");
//			jo.put("data", jArray);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	
	/**
	 * 3.	已阅通知-3.2.	查询已阅通知列表
	 * @Methods Name queryYytz
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String queryYytz() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("totalCount", "0");
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/NoticeServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String pageNum = super.getRequest().getParameter("pageNum");
			String pageSize = super.getRequest().getParameter("pageSize");
			String title = super.getRequest().getParameter("title");
			String department = super.getRequest().getParameter("department");
			String typeId = super.getRequest().getParameter("typeId");
			String password = UserCheckMap.get(userid); 
			if (userid != null && pageNum != null && pageSize != null && typeId != null && password != null) {
				//调用接口方法 
				String methodName = "getYyNotice";
				//public String getYyNotice(String userId,String passwd,String pageNum,String pageSize,String title,String department,String noticeTypeId)
				Object [] paramArr = new Object[]{userid,password,pageNum,pageSize,title,department,typeId};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
//			// TODO: handle exception
//			jo.put("success", true);
//			jo.put("totalCount", "13");
//			JSONArray jArray = new JSONArray();
//			jArray.add("{id:\"1\",title:\"假我们都是好通知1\", department:\"办公室\",author :\"张三丰1\",createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"2\",title:\"假我们都是好通知2\", department:\"办公室\",author :\"张三丰1\",  createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"11\",title:\"假我们都是好通知3\", department:\"办公室\",author :\"张三丰1\",  createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"12\",title:\"假我们都是好通知4\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"13\",title:\"假我们都是好通知5\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"14\",title:\"假我们都是好通知6\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"15\",title:\"假我们都是好通知7\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"16\",title:\"假我们都是好通知8\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"17\",title:\"假我们都是好通知9\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"18\",title:\"假我们都是好通知10\", department:\"办公室\",author :\"张三丰1\",  createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"19\",title:\"假我们都是好通知11\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"10\",title:\"假我们都是好通知22\", department:\"办公室\",  author :\"张三丰1\",createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{id:\"100\",title:\"假我们都是好通知223\", department:\"办公室\", author :\"张三丰1\", createDate :\"2011-7-16 12:34\"}");
//			jo.put("data", jArray);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	/**
	 * 3.	已阅通知-3.3.	点击列表查看详细内容
	 * @Methods Name queryYytzDetail
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 *  {success:true, clickrate:”893”, department:”办公室”, desc:”通知内容。。。”，commentDesc:”我已经看过这个了”,
 			accessories：[{name:”文件通知.doc”,url:”/a/文件知.doc”,type:”0”},{name:””,url:””,type:”0`”}]
 		}
	 */
	public String queryYytzDetail() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("clickrate", "0");
		jo.put("department", "");
		jo.put("desc", "");
		jo.put("commentDesc", "");
		jo.put("accessories", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/NoticeServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String id = super.getRequest().getParameter("id");
			String password = UserCheckMap.get(userid); 
			if (userid != null && id != null && password != null) {
				//调用接口方法 
				String methodName = "getNoticeDetail";
				//public String getNoticeDetail(String noticeId,String userId,String passwd);
				Object [] paramArr = new Object[]{id,userid,password};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
//			jo.put("success", true);
//			jo.put("clickrate", "100");
//			jo.put("department", "假加班开发部");
//			jo.put("desc", "假加班开发部，在扯淡。加班开发部，在扯淡。加班开发部，在扯淡。加班开发部，在扯淡。加班开发部，在扯淡。加班开发部，在扯淡。加班开发部，在扯淡。加班开发部，在扯淡。");
//			jo.put("commentDesc", "假我看过这个破玩意。");
//			JSONArray jArray = new JSONArray();
//			jArray.add("{name:\" 附件名称1.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jArray.add("{name:\" 附件名称2.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jo.put("accessories", jArray);

		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	
	/**
	 * 4.	待阅传阅-4.1.	查询待阅传阅列表
	 * @Methods Name queryDycy
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {success:true, data:[
		{id:”1”,title:”我们都是好通知”，gwzh :”00001”， department:”办公室”,  createDate :”2011-7-16 12:34”,taskid:10002},
		{id:”1”,title:”我们都是好通知”，gwzh :”00001”， department:”办公室”,  createDate :”2011-7-16 12:34”, taskid:10002}
		]}

	 */
	public String queryDycy() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String password = UserCheckMap.get(userid); 
			if (userid != null && password != null) {
				//调用接口方法 
				String methodName = "getDycyList";
				//public String getDycyList(String userId, String passwd)
				Object [] paramArr = new Object[]{userid,password};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
//			jo.put("success", true);
//			JSONArray jArray = new JSONArray();
//			jArray.add("{taskid:\"1\",nodeName:\"假发文核稿\", author :\"超级管理员\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"10\",nodeName:\"假领导审批\", author :\"网络\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"12\",nodeName:\"假领导审批\", author :\"啊鸡鸡\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"14\",nodeName:\"假请假审批\", author :\"中文啊\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"15\",nodeName:\"假请假审批\", author :\"阿鸡是个X\",createDate :\"2011-7-16 12:34\"}");
//			jo.put("data", jArray);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	/**
	 * 4.	待阅传阅-4.2.	点击列表查看详细内容
	 * @Methods Name queryDycyDetail
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 *  {
		success:true,
		title:”22222222222(待审批)”,
		创建日期:”2011-07-22”,
		activityName: “发文核稿”,(pad端不显示,可能用来审批传入参数)
		 data:[
		{label:”来文文字号”,value:”1111111”},
		{label:”发文人”,value:”超级管理员”},
		{label:”发文机关”,value:”信息部门”},
		{label:”来文类型”,value:“三星”},
		{label:”主题词”,value:”2222222”},
		{label:”公文来源”,value:”22”},
		{label:”紧急程度”,value:”特级”},
		{label:”秘密等级”,value:”机密”},
		{label:”签收部门”,value:”信息部门”},
		{label:”内容”,value:”哈哈哈哈”},
		{label:”审批流程名称”,value:”[发文核稿]”},
		{label:”核稿人”,value:”zp”},
		{label:”核稿意见”,value:“aaaaaaaaaaaaaaaaa”},
		{label:”审批流程名称”,value:”[科室审核]”},
		{label:”审批人”,value:”张领导”},
		{label:”审批意见”,value:“没有意见哈”}
		。。。。如果下面还有流程继续拼这种串
		],
		accessories：[{name:” 附件名称1.doc”,url:” /attachFiles/archive/201107/附件名称1.doc”,type:”0”} , {name:” 附件名称2.ppt”,url:” /attachFiles/archive/201107/附件名称2.ppt” ,type:”3”}]
		}
		userid=99 &id=1&taskeid=10002
	 */
	public String queryDycyDetail() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("title", "");
		jo.put("createDate", "");
		jo.put("activityName", "");
		jo.put("taskId","false");
		jo.put("signalName","false");
		jo.put("type","");
		jo.put("boxstatus",false);
		jo.put("userquery",false);
		jo.put("data", new JSONArray());
		jo.put("accessories", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String taskid = super.getRequest().getParameter("taskid");
			String activityName = super.getRequest().getParameter("activityName");
			String password = UserCheckMap.get(userid); 
			if (userid != null && taskid != null && password != null) {
				//调用接口方法 
				String methodName = "getDbsxDetail";
				//public String getDbsxDetail(String userId, String passwd,String activityName, String taskId)
				Object [] paramArr = new Object[]{userid,password,activityName,taskid};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
//			jo.put("success", true);
//			jo.put("title", "22222222222(待审批)");
//			jo.put("createDate", "2011-07-22 12:10:00");
//			jo.put("activityName", "发文核稿");
//			jo.put("type", "0");
//			jo.put("id", "88");
//			jo.put("taskId", "100");
//			jo.put("signalName", "审核");
//			jo.put("boxstatus", false);
//			jo.put("userquery", false);
//			JSONArray jArrayD = new JSONArray();
//			jArrayD.add("{label:\"假来文文字号\",value:\"1111111\"}");
//			jArrayD.add("{label:\"假发文人\",value:\"超级管理员\"}");
//			jArrayD.add("{label:\"假发文机关\",value:\"信息部门\"}");
//			jArrayD.add("{label:\"假来文类型\",value:\"三星\"}");
//			jArrayD.add("{label:\"假主题词\",value:\"2222222\"}");
//			jArrayD.add("{label:\"假公文来源\",value:\"22\"}");
//			jArrayD.add("{label:\"假紧急程度\",value:\"特级\"}");
//			jArrayD.add("{label:\"假秘密等级\",value:\"机密\"}");
//			jArrayD.add("{label:\"假签收部门\",value:\"信息部门\"}");
//			jArrayD.add("{label:\"假内容\",value:\"哈哈哈哈\"}");
//			jArrayD.add("{label:\"审批流程名称\",value:\"[发文核稿]\"}");
//			jArrayD.add("{label:\"核稿人\",value:\"zp\"}");
//			jArrayD.add("{label:\"核稿意见\",value:\"aaaaaaaaaaaaaaaaa\"}");
//			jArrayD.add("{label:\"审批流程名称\",value:\"[科室审核]\"}");
//			jArrayD.add("{label:\"审批人\",value:\"张领导\"}");
//			jArrayD.add("{label:\"审批意见\",value:\"没有意见哈\"}");
//			jo.put("data", jArrayD);
//			JSONArray jArray = new JSONArray();
//			jArray.add("{name:\" 附件名称1.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jArray.add("{name:\" 附件名称2.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jo.put("accessories", jArray);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	/**
	 * 4.	待阅传阅-4.3.	审批传阅
	 * @Methods Name queryDytzReply
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {success：true }   回复成功
		{success：false } 回复失败
		id=1&commentDesc=我没啥意见&userid=99&taskId=99999&activityName=发文核稿(pad端不显示，可能用来审批传入参数)
	 */
	public String submitSpcy() throws Exception  {
//		boolean isSafe = this.isSafeAccess();
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String id = super.getRequest().getParameter("id");
			String commentDesc = super.getRequest().getParameter("commentDesc");
			String taskId = super.getRequest().getParameter("taskid");
			String activityName = super.getRequest().getParameter("activityName");
			//（id用逗号隔开）
			String nextuser = super.getRequest().getParameter("nextuser");
			String password = UserCheckMap.get(userid); 
			//(1局长2分管局长3全有)
			String checkboxvalue = super.getRequest().getParameter("checkboxvalue");
			String signalName = super.getRequest().getParameter("signalName");
			String gdlx = super.getRequest().getParameter("gdlx");
			if (userid != null && id != null && commentDesc != null && taskId != null && password != null && signalName != null) {
				//调用接口方法 
				String methodName = "saveProcessAndToNext";
				//public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx)
				//public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx,String bh)
				Object [] paramArr = new Object[]{userid,password,id,taskId,activityName,signalName,commentDesc,nextuser,checkboxvalue,null,gdlx,null};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	
	/**
	 * 5.	已阅传阅-5.1.	查询已阅传阅列表
	 * @Methods Name queryDycy
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {success:true, data:[
		{id:”1”,title:”我们都是好通知”，gwzh :”00001”， department:”办公室”,  createDate :”2011-7-16 12:34”,taskid:10002},
		{id:”1”,title:”我们都是好通知”，gwzh :”00001”， department:”办公室”,  createDate :”2011-7-16 12:34”, taskid:10002}
		]}

	 */
	public String queryYycy() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("totalCount", "0");
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String title = super.getRequest().getParameter("title");
			String passType = super.getRequest().getParameter("passType");
			String pageNum = super.getRequest().getParameter("pageNum");
			String pageSize = super.getRequest().getParameter("pageSize");
			String password = UserCheckMap.get(userid); 
			if (userid != null && password != null && pageNum != null && pageSize != null) {
				//调用接口方法 
				String methodName = "getYycyList";
				//public String getYycyList(String userId, String passwd,String passType,String title,String pageNum,String pageSize) 
				Object [] paramArr = new Object[]{userid,password,passType,title,pageNum,pageSize};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
//			// TODO: handle exception
//			jo.put("success", true);
//			jo.put("totalCount", "13");
//			JSONArray jArray = new JSONArray();
//			jArray.add("{id:\"1\",title:\"假我们都是好通知1\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\",taskid:10002}");
//			jArray.add("{id:\"2\",title:\"假我们都是好通知2\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"11\",title:\"假我们都是好通知3\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"12\",title:\"假我们都是好通知4\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"13\",title:\"假我们都是好通知5\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"14\",title:\"假我们都是好通知6\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"15\",title:\"假我们都是好通知7\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"16\",title:\"假我们都是好通知8\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"17\",title:\"假我们都是好通知9\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"18\",title:\"假我们都是好通知10\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"19\",title:\"假我们都是好通知11\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"10\",title:\"假我们都是好通知22\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jArray.add("{id:\"100\",title:\"假我们都是好通知223\",gwzh :\"00001\", department:\"办公室\",  createDate :\"2011-7-16 12:34\", taskid:10002}");
//			jo.put("data", jArray);
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 5.  已阅传阅-5.2.	点击列表查看详细内容 
	 * @Methods Name queryYycyDetail
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String queryYycyDetail() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("title", "");
		jo.put("createDate", "");
		jo.put("activityName", "");
		jo.put("taskId","false");
		jo.put("signalName","false");
		jo.put("type","");
		jo.put("boxstatus",false);
		jo.put("userquery",false);
		jo.put("data", new JSONArray());
		jo.put("accessories", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String id = super.getRequest().getParameter("id");
			String password = UserCheckMap.get(userid); 
			if (userid != null && id != null && password != null) {
				//调用接口方法 
				String methodName = "getYycyDetail";
				//public String getYycyDetail(String userId, String passwd, String id) 
				Object [] paramArr = new Object[]{userid,password,id};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
//			jo.put("success", true);
//			jo.put("title", "22222222222(待审批)");
//			jo.put("createDate", "2011-07-22 12:10:00");
//			jo.put("activityName", "发文核稿");
//			jo.put("type", "0");
//			jo.put("id", "88");
//			jo.put("boxstatus", false);
//			jo.put("userquery", false);
//			jo.put("taskId","false");
//			jo.put("signalName","false");
//			JSONArray jArrayD = new JSONArray();
//			jArrayD.add("{label:\"假来文文字号\",value:\"1111111\"}");
//			jArrayD.add("{label:\"假发文人\",value:\"超级管理员\"}");
//			jArrayD.add("{label:\"假发文机关\",value:\"信息部门\"}");
//			jArrayD.add("{label:\"假来文类型\",value:\"三星\"}");
//			jArrayD.add("{label:\"假主题词\",value:\"2222222\"}");
//			jArrayD.add("{label:\"假公文来源\",value:\"22\"}");
//			jArrayD.add("{label:\"假紧急程度\",value:\"特级\"}");
//			jArrayD.add("{label:\"假秘密等级\",value:\"机密\"}");
//			jArrayD.add("{label:\"假签收部门\",value:\"信息部门\"}");
//			jArrayD.add("{label:\"假内容\",value:\"哈哈哈哈\"}");
//			jArrayD.add("{label:\"审批流程名称\",value:\"[发文核稿]\"}");
//			jArrayD.add("{label:\"核稿人\",value:\"zp\"}");
//			jArrayD.add("{label:\"核稿意见\",value:\"aaaaaaaaaaaaaaaaa\"}");
//			jArrayD.add("{label:\"审批流程名称\",value:\"[科室审核]\"}");
//			jArrayD.add("{label:\"审批人\",value:\"张领导\"}");
//			jArrayD.add("{label:\"审批意见\",value:\"没有意见哈\"}");
//			jo.put("data", jArrayD);
//			JSONArray jArray = new JSONArray();
//			jArray.add("{name:\" 附件名称1.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jArray.add("{name:\" 附件名称2.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jo.put("accessories", jArray);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 6.	代办公文-6.1.	代办公文列表（对应待办事项只包括发文流程和收文流程）
	 * @Methods Name queryDbgw
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {
		success:true, data:[
		{taskid:”1”,nodeName:”发文核稿”， author :”超级管理员”， createDate :”2011-7-16 12:34”},
		{taskid:”10”,nodeName:”领导审批”， author :”超级管理员”， createDate :”2011-7-16 12:34”},
		]
		}
	 */
	public String queryDbgw() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String password = UserCheckMap.get(userid); 
			if (userid != null && password != null) {
				//passType=0传阅中 passType=1传阅完成
				//调用接口方法 
				String methodName = "getDbsxList";
				//public String getDbsxList(String userId, String passwd) 
				Object [] paramArr = new Object[]{userid,password};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			// TODO: handle exception
//			jo.put("success", true);
//			JSONArray jArray = new JSONArray();
//			jArray.add("{taskid:\"1\",nodeName:\"假发文核稿\", author :\"超级管理员\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"10\",nodeName:\"假领导审批\", author :\"网络\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"12\",nodeName:\"假领导审批\", author :\"啊鸡鸡\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"14\",nodeName:\"假请假审批\", author :\"中文啊\", createDate :\"2011-7-16 12:34\"}");
//			jArray.add("{taskid:\"15\",nodeName:\"假请假审批\", author :\"阿鸡是个X\",createDate :\"2011-7-16 12:34\"}");
//			jo.put("data", jArray);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	
	/**
	 * 6.	代办公文-6.2.	点击列表查看详细内容
	 * @Methods Name queryDbgw
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	*  {
		success:true,
		title:”22222222222(待审批)”,
		创建日期:”2011-07-22”,
		activityName: “发文核稿”,(pad端不显示，可能用来审批传入参数)
		 data:[
		{label:”来文文字号”,value:”1111111”},
		{label:”发文人”,value:”超级管理员”},
		{label:”发文机关”,value:”信息部门”},
		{label:”来文类型”,value:“三星”},
		{label:”主题词”,value:”2222222”},
		{label:”公文来源”,value:”22”},
		{label:”紧急程度”,value:”特级”},
		{label:”秘密等级”,value:”机密”},
		{label:”签收部门”,value:”信息部门”},
		{label:”内容”,value:”哈哈哈哈”},
		{label:”审批流程名称”,value:”[发文核稿]”},
		{label:”核稿人”,value:”zp”},
		{label:”核稿意见”,value:“aaaaaaaaaaaaaaaaa”},
		{label:”审批流程名称”,value:”[科室审核]”},
		{label:”审批人”,value:”张领导”},
		{label:”审批意见”,value:“没有意见哈”}
		。。。。如果下面还有流程继续拼这种串
		]，
		accessories：[{name:” 附件名称1.doc”,url:” /attachFiles/archive/201107/附件名称1.doc”,type:”0”} , {name:” 附件名称2.ppt”,url:” /attachFiles/archive/201107/附件名称2.ppt” ,type:”3”}]
		}
		userid=99 &id=1&taskeid=10002
	 */
	public String queryDbgwDetail() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("title", "");
		jo.put("createDate", "");
		jo.put("activityName", "");
		jo.put("taskId","");
		jo.put("signalName","");
		jo.put("type","");
		jo.put("boxstatus",false);
		jo.put("userquery",false);
		jo.put("showgdlx",false);
		jo.put("showback",false);
		jo.put("showbh",false);
		jo.put("isdx",false);
		jo.put("data", new JSONArray());
		jo.put("accessories", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		StringBuffer url = new StringBuffer();
		url.append(OAURL);
		url.append(accessUrl);
		try {
			String userid = super.getRequest().getParameter("userid");
			String taskid = super.getRequest().getParameter("taskid");
			String password = UserCheckMap.get(userid); 
			String activityName = super.getRequest().getParameter("activityName");
			if (userid != null && taskid != null && password != null) {
				//调用接口方法 
				String methodName = "getDbsxDetail";
				//public String getDbsxDetail(String userId, String passwd,String activityName, String taskId)
				Object [] paramArr = new Object[]{userid,password,activityName,taskid};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
//			jo.put("success", true);
//			jo.put("title", "22222222222(待审批)");
//			jo.put("createDate", "2011-07-22 12:10:00");
//			jo.put("activityName", "发文核稿");
//			jo.put("type", "0");
//			jo.put("id", "88");
//			jo.put("taskId", "100");
//			jo.put("signalName", "审核");
//			jo.put("boxstatus", false);
//			jo.put("userquery", false);
//			JSONArray jArrayD = new JSONArray();
//			jArrayD.add("{label:\"假来文文字号\",value:\"1111111\"}");
//			jArrayD.add("{label:\"假发文人\",value:\"超级管理员\"}");
//			jArrayD.add("{label:\"假发文机关\",value:\"信息部门\"}");
//			jArrayD.add("{label:\"假来文类型\",value:\"三星\"}");
//			jArrayD.add("{label:\"假主题词\",value:\"2222222\"}");
//			jArrayD.add("{label:\"假公文来源\",value:\"22\"}");
//			jArrayD.add("{label:\"假紧急程度\",value:\"特级\"}");
//			jArrayD.add("{label:\"假秘密等级\",value:\"机密\"}");
//			jArrayD.add("{label:\"假签收部门\",value:\"信息部门\"}");
//			jArrayD.add("{label:\"假内容\",value:\"哈哈哈哈\"}");
//			jArrayD.add("{label:\"审批流程名称\",value:\"[发文核稿]\"}");
//			jArrayD.add("{label:\"核稿人\",value:\"zp\"}");
//			jArrayD.add("{label:\"核稿意见\",value:\"aaaaaaaaaaaaaaaaa\"}");
//			jArrayD.add("{label:\"审批流程名称\",value:\"[科室审核]\"}");
//			jArrayD.add("{label:\"审批人\",value:\"张领导\"}");
//			jArrayD.add("{label:\"审批意见\",value:\"没有意见哈\"}");
//			jo.put("data", jArrayD);
//			JSONArray jArray = new JSONArray();
//			jArray.add("{name:\" 附件名称1.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jArray.add("{name:\" 附件名称2.txt\",url:\"/attachFiles/hrm/201107/2ae833ae7d884a70a4bad80fa6b533c8.txt\",type:\"0\"}");
//			jo.put("accessories", jArray);
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	/**
	 * 6.	代办公文-6.3.	审批公文
	 * @Methods Name querySpgw
	 * @Create In Jul 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 * {success：true }   回复成功
		{success：false } 回复失败
		id=1&commentDesc=我没啥意见&userid=99&taskId=99999&activityName=发文核稿(pad端不显示，可能用来审批传入参数)
		&nextuser=（id用逗号隔开）
		&checkboxvalue=(1局长2分管局长3全有)
	 */
	public String submitSpgw() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String id = super.getRequest().getParameter("id");
			String commentDesc = super.getRequest().getParameter("commentDesc");
			String taskId = super.getRequest().getParameter("taskid");
			String activityName = super.getRequest().getParameter("activityName");
			//（id用逗号隔开）
			String nextuser = super.getRequest().getParameter("nextuser");
			String password = UserCheckMap.get(userid); 
			//(1局长2分管局长3全有)
			String checkboxvalue = super.getRequest().getParameter("checkboxvalue");
			String signalName = super.getRequest().getParameter("signalName");
			String gdlx = super.getRequest().getParameter("gdlx");
			//编号
			String bh = super.getRequest().getParameter("bh");
			//分管领导
			String fgld = super.getRequest().getParameter("fgld");
			//分局人
			String fjry = super.getRequest().getParameter("fjry");
			//审批0（nextuser）  直接归档1（gdlx）  分管领导审批2（nextuser）  
			String btType = super.getRequest().getParameter("btType");
			//增加审批驳回
			String ispass = super.getRequest().getParameter("ispass");
			if (userid != null && id != null && activityName != null && commentDesc != null && taskId != null && password != null && signalName != null) {
				//调用接口方法 
				String methodName = "saveProcessAndToNext";
				//public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx)
				//public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx,String bh)
				//public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx,String bh,String fgld);
				//public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx,String bh,String fgld,String fjry,String btType)
				Object [] paramArr = new Object[]{userid,password,id,taskId,activityName,signalName,commentDesc,nextuser,checkboxvalue,ispass,gdlx,bh,fgld,fjry,btType};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	/**
	 * 输出json
	 * @Methods Name printJson
	 * @Create In Jul 27, 2011 By likang
	 * @param jo void
	 */
	private void printJson(JSONObject jo) {
		PrintWriter pw = super.getPrintWriter();
		pw.append(jo.toString());
		pw.flush();
		pw.close();
//		System.out.println(super.getRequest().getHeader("user-agent"));
	}
	
	/**
	 * 7.1 待阅分发
	 * @Methods Name getDyffList
	 * @Create In Aug 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String getDyffList() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("totalCount", "0");
		jo.put("data", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String title = super.getRequest().getParameter("title");
			String pageNum = super.getRequest().getParameter("pageNum");
			String pageSize = super.getRequest().getParameter("pageSize");
			String password = UserCheckMap.get(userid); 
			if (userid != null && password != null && pageNum != null && pageSize != null) {
				//调用接口方法 
				String methodName = "getDyffList";
				//public String getDyffList(String userId, String passwd,String title, String pageNum, String pageSize);
				Object [] paramArr = new Object[]{userid,password,title,pageNum,pageSize};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 7.2 待阅分发详细
	 * @Methods Name getDyffDetail
	 * @Create In Aug 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String getDyffDetail() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		jo.put("title", "");
		jo.put("createDate", "");
		jo.put("activityName", "");
		jo.put("taskId","false");
		jo.put("signalName","false");
		jo.put("type","");
		jo.put("boxstatus",false);
		jo.put("userquery",false);
		jo.put("data", new JSONArray());
		jo.put("accessories", new JSONArray());
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String id = super.getRequest().getParameter("id");
			String distid = super.getRequest().getParameter("distid");
			String password = UserCheckMap.get(userid); 
			if (userid != null && id != null && password != null && distid != null) {
				//调用接口方法 
				String methodName = "getDyffDetail";
				//public String getDyffDetail(String userId, String passwd, String id,String distid)
				Object [] paramArr = new Object[]{userid,password,id,distid};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	/**
	 * 7.3 保存待阅分发详细
	 * @Methods Name saveDyffDetail
	 * @Create In Aug 27, 2011 By likang
	 * @return
	 * @throws Exception String
	 */
	public String saveDyffDetail() throws Exception  {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		//访问oa的url
		String accessUrl = OAURL + "/FlowServiceImpl";
		try {
			String userid = super.getRequest().getParameter("userid");
			String id = super.getRequest().getParameter("id");
			String password = UserCheckMap.get(userid); 
			if (userid != null && id != null && password != null) {
				//调用接口方法 
				String methodName = "saveDyffDetail";
				//public String saveDyffDetail(String userId, String passwd, String id );
				Object [] paramArr = new Object[]{userid,password,id};
				//通过webserice返回json对象
				JSONObject backJsonObejct = HttpUtil.getWebserviceJsonStrByUrl(accessUrl, methodName, paramArr);
				//如果有success字符返回则成功
				jo = backJsonObejct;
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		//输出json
		printJson(jo);
		return null;
	}
	
	
	public String downFile1() throws Exception  {
		try {
			String url = super.getRequest().getParameter("url");
			String name = super.getRequest().getParameter("name");
			if (url != null && name != null) {
//				url = OAURL + url;
				FileDownloaderUtil.downAndLoadFileTogether(url, super.getResponse(), name);
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	/**
	 * axis通过简单的方式访问xfire webservice
	 * @Methods Name main
	 * @Create In Mar 12, 2010 By Administrator
	 * @param args void
	 */
	public static void main(String[] args) {
		try {
			//xfire提供的ws
			String endpoint = "http://127.0.0.1:8080/HelloWebservice/services/HelloWebservice";
			endpoint = "http://127.0.0.1:8080/axis4webservice/services/SayHello";
	        Service service = new Service();
	        Call call = (Call) service.createCall();
	        call.setTargetEndpointAddress(new java.net.URL(endpoint));
	        //要调用的方法名
	        call.setOperationName("getName");
	        int a = 1000;
			List list = new ArrayList<String>();
//				list.add("hah");
//				list.add("中文");
			  //设置参数
//		           call.addParameter("message", XMLType.XSD_STRING, ParameterMode.IN);
//		        //设置参数
//		           call.setReturnType(XMLType.XSD_STRING);
	        //要传入ws的参数注意参数不能多不能少
			//"哈哈这个是用的"
	        Object ret = call.invoke(new Object[] {"中文",200299});
	        System.out.println(ret);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
