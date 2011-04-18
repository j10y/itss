package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.digitalchina.info.framework.workflow.ConfigUnitService;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.entity.ConfigUnit;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitMail;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitMailCC;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitMailNodeSender;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitMailNodeSenderTable;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitTimer;

public class ConfigUnitAction extends BaseAction {
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService");
	ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	//sService service = (Service) ContextHolder.getBean("baseService");
	/**
	 * 配置单元保存
	 * @Methods Name configUnitSave
	 * @Create In Feb 13, 2009 By guangsa
	 * @return String
	 */
	public String configUnitSave(){
		
		String configUnitId = super.getRequest().getParameter("configUnitId");
		String configUnitName = super.getRequest().getParameter("configUnitname");
		String configUnitDesc = super.getRequest().getParameter("description");
		String configUnitUrl = super.getRequest().getParameter("url");
		String configUnitExecute = super.getRequest().getParameter("executes");
		String configUnitHandler = super.getRequest().getParameter("handler");		
		if("".equals(configUnitId)||configUnitId==null){			
			ConfigUnit configUnit = new ConfigUnit();
			configUnit.setDescription(configUnitDesc);
			configUnit.setExecutes(configUnitExecute);
			configUnit.setHandler(configUnitHandler);
			configUnit.setName(configUnitName);
			configUnit.setUrl(configUnitUrl);
			super.getService().save(configUnit);
		}else{
			ConfigUnit configUnit = (ConfigUnit)super.getService().find(ConfigUnit.class, configUnitId);
			configUnit.setDescription(configUnitDesc);
			configUnit.setExecutes(configUnitExecute);
			configUnit.setHandler(configUnitHandler);
			configUnit.setName(configUnitName);
			configUnit.setUrl(configUnitUrl);
			super.getService().save(configUnit);
		}		
		try {			
			PrintWriter pw;
			pw = super.getResponse().getWriter();
			pw.write("{success:true}");
		} catch (IOException e) {
			System.out.println("保存实体的时候出现错误");
			e.printStackTrace();
		}		
		return null;
	}
	/**
	 * 删除某个配置单元
	 * @Methods Name deleteConfigUnit
	 * @Create In Feb 2, 2009 By Administrator
	 * @return String
	 */
	public String deleteConfigUnit(){
		String[] configId = super.getRequest().getParameterValues("removeIds");
		for(int i=0;i<configId.length;i++){
			super.getService().remove(ConfigUnit.class, configId[i]);			
		}
		try {
			super.getResponse().getWriter().write("{success:" + true+ "}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name findConfigRelFormValue
	 * @Create In Feb 13, 2009 By guangsa
	 * @return String
	 */
	public String findConfigUnitFormValue(){
		String configUnitId = super.getRequest().getParameter("configUnitId");
		ConfigUnit configUnit = (ConfigUnit)super.getService().find(ConfigUnit.class, configUnitId);
		
		List listProperty = new ArrayList();		
		HashMap<String , Object> unit = new HashMap<String , Object>();
		unit.put("configUnitname", configUnit.getName()!=null ? configUnit.getName():"");
		unit.put("description", configUnit.getDescription()!=null ? configUnit.getDescription():"");
		unit.put("url", configUnit.getUrl()!=null ? configUnit.getUrl():"");	
		unit.put("executes", configUnit.getExecutes()!=null ? configUnit.getExecutes():"");
		unit.put("unitHandlerId", configUnit.getHandler()!=null ? configUnit.getHandler():"");
		listProperty.add(unit);
			
		JSONArray jsonObject = JSONArray.fromObject(listProperty);
		System.out.println(jsonObject.toString());
		
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write("{success:" + true + ",list:"+ jsonObject.toString() + "}");
			super.getResponse().getWriter().flush();
						
		} catch (IOException e) {
			
			e.printStackTrace(); 
		}		 
		return null;
	}
	/**
	 * 
	 * @Methods Name getConfigUnit
	 * @Create In Mar 24, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String getConfigUnit() throws Exception{
		String json = "";
		List<ConfigUnit> configUnit = super.getService().findAll(ConfigUnit.class);
		
		for(int i=0; i< configUnit.size(); i++){
			ConfigUnit unit = (ConfigUnit)configUnit.get(i);			
			Long id = unit.getId();
			String descn = unit.getDescription();
			String  name= unit.getName();
			String url = unit.getUrl();
			
			json += "{\"id\":"+id+",\"configUnitLink\":\""+url+"\",\"configUnitDesc\":\""+descn+"\",\"configUnitName\":\""+name+"\"},";
		}
		if(json.length()!=0){
			json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		}			
		
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name recordConfigUnitId
	 * @Create In Feb 13, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String recordConfigUnitId() throws Exception{
		String id  = super.getRequest().getParameter("configUnitId");
		ConfigUnit configUnit = (ConfigUnit)super.getService().find(ConfigUnit.class, id);
		super.getRequest().setAttribute("configUnit", configUnit);
		return SUCCESS;
	}
	/**
	 * 根据流程名和节点名得到相应的timer数据
	 * @Methods Name showNodeTimer
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String showNodeTimer() throws Exception{
		
		String json = "";		
		String nodeName = "";
		String nodeDesc = "";
		
		String virtualDefinitionInfoId = super.getRequest().getParameter("virtualDefinitionInfoId");
		String nodeId = super.getRequest().getParameter("nodeId");
//		VirtualDefinitionInfo vitualDef = (VirtualDefinitionInfo) super.getService().findUnique(VirtualDefinitionInfo.class, "id", Long.valueOf(virtualDefinitionInfoId));
//		List<VirtualNodeInfo> virturalNodes = super.getService().find(VirtualNodeInfo.class,"virtualDefinitionInfo", vitualDef);
//		String vProcessName = vitualDef.getVirtualDefinitionName();	
//		String vProcessDesc = vitualDef.getVirtualDefinitionDesc();
//		for(VirtualNodeInfo node : virturalNodes){
//			if(node.getNodeId()==Long.valueOf(nodeId)||nodeId.equals(node.getNodeId()+"")){				
//				nodeName = node.getVirtualNodeName();				
//			}
//		}			
		ConfigUnitTimer timer = cs.showConfigUnitTimer(Long.valueOf(virtualDefinitionInfoId), Long.valueOf(nodeId));//+",\"inverse\":\""+timer.getInverseNodeName()
		if(timer!=null){
		json += "{\"id\":"+timer.getId()+",\"inverse\":\""+timer.getInverseNodeName()+"\",\"isListener\":"+timer.getFlag()+",\"frequently\":"+timer.getEffectTime()+",\"nodeName\":\""+timer.getNodeName()+"\",\"defName\":\""+timer.getProcessName()+"\"},";
		System.out.println(json);
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		}		
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;		
	}
	/**
	 * 保存或修改配置单元Timer信息
	 * @Methods Name saveNodeTimerMessage
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String saveNodeTimerMessage() throws Exception{
		String id = super.getRequest().getParameter("id");
		String virtualDefinitionInfoId = super.getRequest().getParameter("virtualDefinitionInfoId");
		String nodeId = super.getRequest().getParameter("nodeId");
		
		String defName = super.getRequest().getParameter("defName");
		String nodeName = super.getRequest().getParameter("nodeName");
		String frequently = super.getRequest().getParameter("frequently");
		String inverse = super.getRequest().getParameter("inverse");
		//String isListener = super.getRequest().getParameter("isListener");
		ConfigUnitTimer timer = null;
		if(id!=null&&!"".equals(id)&&!"undefined".equals(id)){
			timer = (ConfigUnitTimer)super.getService().find(ConfigUnitTimer.class, id);			
		}else{
			timer = new ConfigUnitTimer();				
		}
		timer.setProcessName(defName);
		timer.setNodeName(nodeName);
		//timer.setFlag(Integer.valueOf(isListener));
		timer.setEffectTime(Integer.valueOf(frequently));
		timer.setInverseNodeName(inverse);
		timer.setVirtualProcessId(Long.valueOf(virtualDefinitionInfoId));
		timer.setNodeId(Long.valueOf(nodeId));
		super.getService().save(timer);	
		try {			
			PrintWriter pw;
			pw = super.getResponse().getWriter();
			pw.write("{success:true}");
		} catch (IOException e) {
			System.out.println("保存实体的时候出现错误");
			e.printStackTrace();
		}			
		return null;
	}
	/**
	 *删除配置单元节点信息
	 * @Methods Name removeNodeTimerMessage
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String removeNodeTimerMessage() throws Exception{		
		String[] ids = super.getRequest().getParameterValues("ids");
		for(int i=0;i<ids.length;i++){
			ConfigUnitTimer timer = (ConfigUnitTimer)super.getService().find(ConfigUnitTimer.class, ids[i]);
			super.getService().remove(timer);
		}	
		try {
			super.getResponse().getWriter().write("{success:" + true+ "}");
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 抄送MailCc邮件信息的保存(信息包括邮件标题，内容，抄送人地址，抄送人姓名)
	 * @Methods Name saveNodeMailMessage
	 * @Create In Mar 30, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String saveNodeMailMessage() throws Exception{
		
		String perName = "";
		String perMail = "";
		/*****************************得到前台传输的必要参数***************************************/		
		String nodeId = super.getRequest().getParameter("nodeId");
		String virtualDefinitionInfoId = super.getRequest().getParameter("virtualDefinitionInfoId");
		String subject = super.getRequest().getParameter("subject");//邮件主题
		String content = super.getRequest().getParameter("content");//邮件内容
		
//		String product = super.getRequest().getParameter("product");
//		product = HttpUtil.ConverUnicode(product);
		subject = HttpUtil.ConverUnicode(subject);
		String context = HttpUtil.ConverUnicode(content);
		context = context.substring(context.lastIndexOf("nbsp;")+5, context.length()-4);
		//content = HttpUtil.ConverUnicode(content);
		
//		if (product != null && product.endsWith(",")) {
//			product = product.substring(0, product.length() - 1);
//		}	
		ConfigUnitMail configUnitMail = cs.findMailObjectById(virtualDefinitionInfoId, nodeId);
		if((subject==null||"".equals(subject))&&(context==null||"".equals(context))){
			if(configUnitMail!=null&&!"".equals(configUnitMail)){
				super.getService().remove(configUnitMail);
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter writer = super.getResponse().getWriter();
				writer.write("{success:true}");
				writer.flush();
				return null;		
			}else{
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter writer = super.getResponse().getWriter();
				writer.write("{success:true}");
				writer.flush();
				return null;		
			}
		}
		
		/*********************上面确定有没有相应的mail实体（根据虚拟流程标识和节点标识）***********************************************/
		
		//JSONArray ja = JSONArray.fromObject("[" + product + "]");
//		for (int i = 0; i < ja.size(); i++) {
//			HashMap productMap = new HashMap();
//			JSONObject obj = (JSONObject) ja.get(i);
//			Iterator itProduct = obj.keys();
//			while (itProduct.hasNext()) {//这是记录中相应的字段
//				String key = (String) itProduct.next();
//				String value = obj.getString(key);				
//				productMap.put(key, value);				
//			}
//			perName = (String)productMap.get("name");
//			perMail = (String)productMap.get("mail");
//			if(perName.equals("")||perName==null){
//				throw new Exception("抄送人姓名不存在");				
//			}
//			String[] spl = perName.split("\\(");
//			String name = spl[1].substring(0,spl[1].length()-1);
//			/**********上面是把一条记录所有属性及其值都放到一个集合当中了**************/					
//			UserInfo userInfo = (UserInfo)super.getService().find(UserInfo.class, "userName", name).get(0);
			
			if(configUnitMail!=null){//说明已经保存过了,这时只需更新数据库中相应内容即可	
					//if(productMap.containsKey("id")){
						/*保存邮件基本信息数据*/				
//						configUnitMail.setContent(content);
//						configUnitMail.setSubject(subject);
//						configUnitMail.setNodeId(Long.valueOf(nodeId));
//						configUnitMail.setVirtualProcessId(Long.valueOf(virtualDefinitionInfoId));
//						ConfigUnitMail UnitMail = null;
//						
//						try{
//							UnitMail = (ConfigUnitMail)super.getService().save(configUnitMail);
//						}catch(Exception e){
//							new Exception("保存邮件主题时发生异常(configUnitAction328)");	
//						}
						
						/*保存抄送人的基本信息*/
//						ConfigUnitMailCC mailCC=(ConfigUnitMailCC)super.getService().find(ConfigUnitMailCC.class, (String)productMap.get("id"));
//						mailCC.setUserInfo(userInfo);
//						mailCC.setConfigUnitMail(UnitMail);
//						mailCC.setMail(perMail);
//						
//						try{
//							super.getService().save(mailCC);
//						}catch(Exception e){
//							new Exception("保存抄送人时发生异常(configUnitAction386)");	
//						}						
					//}else{									
						configUnitMail.setContent(content);
						configUnitMail.setSubject(subject);
						configUnitMail.setNodeId(Long.valueOf(nodeId));
						configUnitMail.setVirtualProcessId(Long.valueOf(virtualDefinitionInfoId));
						ConfigUnitMail UnitMail = null;
						try{
							UnitMail = (ConfigUnitMail)super.getService().save(configUnitMail);
						}catch(Exception e){
							new Exception("保存邮件主题时发生异常");	
						}
//						/*保存抄送人的基本信息*/
//						ConfigUnitMailCC mailCC = new ConfigUnitMailCC();
//						mailCC.setUserInfo(userInfo);
//						mailCC.setConfigUnitMail(UnitMail);
//						mailCC.setMail(perMail);
//						
//						try{
//							super.getService().save(mailCC);
//						}catch(Exception e){
//							new Exception("保存抄送人时发生异常(configUnitAction361)");
//						}
//					}	
					
			}else{//保存一个新的实体,内容包括邮件主题，邮件内容等信息
				
					ConfigUnitMail conMail = new ConfigUnitMail();
					conMail.setContent(content);
					conMail.setSubject(subject);
					conMail.setNodeId(Long.valueOf(nodeId));
					conMail.setVirtualProcessId(Long.valueOf(virtualDefinitionInfoId));
					ConfigUnitMail UnitMail = null;
					
					try{
						UnitMail = (ConfigUnitMail)super.getService().save(conMail);
					}catch(Exception e){
						new Exception("保存邮件主题时发生异常");						
					}
					
					/*保存抄送人的基本信息*/
//					ConfigUnitMailCC mailCC = new ConfigUnitMailCC();
//					mailCC.setUserInfo(userInfo);
//					mailCC.setConfigUnitMail(UnitMail);
//					mailCC.setMail(perMail);
//					
//					try{
//						super.getService().save(mailCC);
//					}catch(Exception e){
//						new Exception("保存抄送人时发生异常(configUnitAction386)");						
//					}	
				}		
//		}
		/**************************************************************************************************************××*******/
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter writer = super.getResponse().getWriter();
		writer.write("{success:true}");
		writer.flush();
		return null;		
		
	}
	/**
	 * mailCc可查询下拉列表的查询方法
	 * @Methods Name queryUserInfoName
	 * @Create In Mar 31, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String queryUserInfoName() throws Exception{
		
		String 	json = "";	
		
		HttpServletRequest request = super.getRequest();		
		HttpServletResponse response = super.getResponse();
		String paramName = request.getParameter("start");
		int start = this.confirmPageNo(paramName, 1);;		
		int pageNo = start / pageSize + 1;
		int pageSize = 10;
		String userName = request.getParameter("userName");
		//把查询结果做分页
		Map requestParams = new HashMap();
		requestParams.put("userName", userName);
		Page page = cs.findUserInfoByParams(requestParams, pageNo, pageSize);
		Long total = page.getTotalCount();
		List<UserInfo> userInfo = page.list();
		
		if(userInfo.size()==0){
			
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			try{				
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				response.getWriter().flush();
			}catch(Exception e){
				e.printStackTrace();
			}					
			
		}else{
			
			for(UserInfo info: userInfo){
				
				String dataStr = "";
				dataStr+="{userName:'"+info.getRealName()+"("+info.getUserName()+")'},";
				json += dataStr;
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			
			try{				
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				response.getWriter().flush();
			}catch(Exception e){
				e.printStackTrace();
			}			
		}	
		
		return null;
	}
	
	/**
	 * mailNodeSender可查询下拉列表的查询方法
	 * @Methods Name queryUserInfoName
	 * @Create In Mar 31, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String queryMailNodeSenderUserInfoMessage() throws Exception{
		
		String 	json = "";	
		
		HttpServletRequest request = super.getRequest();		
		HttpServletResponse response = super.getResponse();
		String paramName = request.getParameter("start");
		int start = this.confirmPageNo(paramName, 1);;		
		int pageNo = start / pageSize + 1;
		int pageSize = 10;
		String userName = request.getParameter("userName");
		//把查询结果做分页
		Map requestParams = new HashMap();
		requestParams.put("userName", userName);
		Page page = cs.findMailNodeSenderUserInfoByParams(requestParams, pageNo, pageSize);
		Long total = page.getTotalCount();
		List<UserInfo> userInfo = page.list();
		
		if(userInfo.size()==0){
			
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			try{				
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				response.getWriter().flush();
			}catch(Exception e){
				e.printStackTrace();
			}					
			
		}else{
			
			for(UserInfo info: userInfo){
				
				String dataStr = "";
				dataStr+="{userName:'"+info.getRealName()+"("+info.getUserName()+")'},";
				json += dataStr;
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			
			try{				
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				response.getWriter().flush();
			}catch(Exception e){
				e.printStackTrace();
			}			
		}	
		
		return null;
	}
	/**
	 * 用于分页计算
	 * @Methods Name getInt
	 * @Create In Feb 24, 2009 By guangsa
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return int
	 */
	public int confirmPageNo(String paramName ,int size){
		
		if(paramName == null || paramName.equals("")){
			return size;
		}
		return Integer.parseInt(paramName);
	}
	
	/**
	 * 加载MailCc面板域的信息
	 * @Methods Name loadPanelFieldMessage
	 * @Create In Mar 31, 2009 By guangsa
	 * @return String
	 */
	public String loadPanelFieldMessage()throws Exception{
		
		String json = "";
		String virtualId = super.getRequest().getParameter("virId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitMail unitMail = cs.findMailObjectById(virtualId, nodeId);
		if(unitMail!=null||"".equals(unitMail)){
			json+="{mailSubject:'"+unitMail.getSubject()+"',mailContent:'"+unitMail.getContent()+"'}";
		}		
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write("{success:" + true + ",list:"+ json + "}");
			String str ="{success:" + true + ",list:"+ json + "}";
			super.getResponse().getWriter().flush();						
		} catch (IOException e) {			
			e.printStackTrace(); 
		}		 
		return null;
	}
	/**
	 * 加载MailNodeSender面板域的信息
	 * @Methods Name loadPanelFieldMessage
	 * @Create In Mar 31, 2009 By guangsa
	 * @return String
	 */
	public String loadMailNodeSenderFormMessage()throws Exception{
		
		String json = "";
		String virtualId = super.getRequest().getParameter("virId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitMailNodeSender mailSender = cs.findMailNodeById(virtualId, nodeId);
		if(mailSender!=null||"".equals(mailSender)){
			json+="{mailSubject:'"+mailSender.getSubject()+"',mailContent:'"+mailSender.getContent()+"'}";
		}		
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write("{success:" + true + ",list:"+ json + "}");
			String str ="{success:" + true + ",list:"+ json + "}";
			super.getResponse().getWriter().flush();						
		} catch (IOException e) {			
			e.printStackTrace(); 
		}		 
		return null;
	}
	/**
	 * mailCc当中grid的数据，即显示用户名和邮件地址
	 * @Methods Name showAllMessage
	 * @Create In Mar 31, 2009 By guangsa
	 * @return String
	 */
	public String showAllMessage()throws Exception{
		
		String json="[";
		String virtualId = super.getRequest().getParameter("virId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitMail unitMail = cs.findMailObjectById(virtualId, nodeId);
		if(unitMail!=null&&!"".equals(unitMail)){
			json += "{";
			json += "id:'"+unitMail.getId()+"',";
			json += "subject:'"+unitMail.getSubject()+"',";
			json += "content:'"+unitMail.getContent()+"'";
			json += "}]";
		}else{
			json +="]";
		}
		
//		List<ConfigUnitMailCC> mailCc = super.getService().find(ConfigUnitMailCC.class, "configUnitMail", unitMail);
//		
//		if(mailCc.isEmpty()){
//			json+="";
//		}else{
//			for(ConfigUnitMailCC mail : mailCc){				
//				json+="{";
//				json += "id:"+mail.getId()+",";
//				json += "name:'"+mail.getUserInfo().getRealName()+"("+mail.getUserInfo().getUserName()+")',";
//				json += "mail:'"+mail.getMail()+"'";
//				json+="},";				
//			}
//			if(json.length()>1)
//			json = json.substring(0, json.length()-1);
//		}
//		json += "]";
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write("{success:" + true + ",data:"+ json + "}");
			super.getResponse().getWriter().flush();						
		} catch (IOException e) {			
			e.printStackTrace(); 
		}		 
		return null;
	}
	/**
	 * mailNodeSender当中集团内部grid的数据，即显示用户名和邮件地址
	 * (此为集团内部用户)
	 * @return
	 * @throws Exception
	 */
	public String showMailNodeSenderMessage()throws Exception{
		
		String json="[";
		String virtualId = super.getRequest().getParameter("virId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitMailNodeSender mailSender = cs.findMailNodeById(virtualId, nodeId);
		if(mailSender!=null&&!"".equals(mailSender)){
			List<ConfigUnitMailNodeSenderTable> mailTable = super.getService().find(ConfigUnitMailNodeSenderTable.class, "mailNodeSender", mailSender);
			
			if(mailTable.isEmpty()){
				json+="";
			}else{
				for(ConfigUnitMailNodeSenderTable mail : mailTable){				
					json+="{";
					json += "id:"+mail.getId()+",";
					json += "name:'"+mail.getUserInfo().getRealName()+"("+mail.getUserInfo().getUserName()+")',";
					json += "mail:'"+mail.getMail()+"'";
					json+="},";				
				}
				if(json.length()>1){
					json = json.substring(0, json.length()-1);
				}
			}
			json += "]";
		}else{
			json += "]";
		}
		
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			System.out.println(json);
			super.getResponse().getWriter().write("{success:" + true + ",data:"+ json + "}");
			super.getResponse().getWriter().flush();						
		} catch (IOException e) {			
			e.printStackTrace(); 
		}		 
		return null;
	}
	/**
	 * mailNodeSender当中集团外部grid的数据，即显示用户名和邮件地址
	 * (此为集团外部用户)
	 * 外部客户的保存格式为”序号（id）:用户名,邮件地址；序号（id）：用户名，邮件地址“
	 * @return
	 * @throws Exception
	 */
	public String showNoCombineMailNodeSenderMessage()throws Exception{
		
		String json="[";
		String recipient = "";
		String virtualId = super.getRequest().getParameter("virId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitMailNodeSender mailSender = cs.findMailNodeById(virtualId, nodeId);
		
		if(mailSender!=null&&!"".equals(mailSender)){
			recipient = mailSender.getRecipient();
			if(recipient!=null&&!"".equals(recipient)){
				String[] mutiUser = recipient.split(";");
				//把当前用户的id和用户名，email地址都用json拼装起来
				for(int i=0 ;i<mutiUser.length;i++){
					String[] idAndUserMessage = mutiUser[i].split(":");
					String id = idAndUserMessage[0];//这是序号
					String[] userMessage = idAndUserMessage[1].split(",");//包含了用户名和密码
					String userName = userMessage[0];
					String email = userMessage[1];
					json+="{";
					json += "id:"+id+",";
					json += "name:'"+userName+"',";
					json += "mail:'"+email+"'";
					json+="},";		
				}
				if(json.length()>1){
					json = json.substring(0, json.length()-1);
				}
			}
			json += "]";
		}else{
			json += "]";
		}
		
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			System.out.println(json);
			super.getResponse().getWriter().write("{success:" + true + ",data:"+ json + "}");
			super.getResponse().getWriter().flush();						
		} catch (IOException e) {			
			e.printStackTrace(); 
		}		 
		return null;
	}
	/**
	 * 删除mailCc表格中的数据
	 * @Methods Name removeMailMessage
	 * @Create In Mar 31, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String removeMailMessage() throws Exception{	
		
		String[] ids = super.getRequest().getParameterValues("ids");
		ConfigUnitMailCC mail = null;
		if(ids.length!=0){
			try {	
				for(int i=0;i<ids.length;i++){
					mail = (ConfigUnitMailCC)super.getService().find(ConfigUnitMailCC.class, ids[i]);
					super.getService().remove(mail);
				}	
				super.getResponse().getWriter().write("{success:" + true+ "}");
			} catch (IOException e) {			
				new Exception("删除抄送邮件时候发生异常configUnitAction555");
			}
		}else{
			try {
				super.getResponse().getWriter().write("{success:" + false+ "}");
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
		
		return null;
	}
	/**
	 * 删除MailNodeSender表格中的数据,仅仅删除MailNodeSenderTable这个表
	 * (集团内部用户)
	 * @return
	 * @throws Exception
	 */
	public String removeMailNodeSenderMessage() throws Exception{
		
		String[] ids = super.getRequest().getParameterValues("ids");
		ConfigUnitMailNodeSenderTable mail = null;
		if(ids.length!=0){
			try {	
				for(int i=0;i<ids.length;i++){
					mail = (ConfigUnitMailNodeSenderTable)super.getService().find(ConfigUnitMailNodeSenderTable.class, ids[i]);
					if(mail!=null&&!"".equals(mail)){
						super.getService().remove(mail);
					}
				}	
				super.getResponse().getWriter().write("{success:" + true+ "}");
			} catch (IOException e) {			
				new Exception("删除邮件时候发生异常removeMailNodeSenderMessage");
			}
		}else{
			try {
				super.getResponse().getWriter().write("{success:" + false+ "}");
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 删除集团外部客户的基本信息
	 * (集团外部)
	 * 因为外部客户的保存格式为”序号（id）:用户名,邮件地址；序号（id）：用户名，邮件地址“，需要按照相应的id删除掉基本信息
	 * @return
	 * @throws Exception
	 */
	public String removeNoCombineMailNodeSenderMessage() throws Exception{
		
		String[] ids = super.getRequest().getParameterValues("ids");
		String virtualId = super.getRequest().getParameter("virId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitMailNodeSender mailSender = cs.findMailNodeById(virtualId, nodeId);
		List<String> remainUserMessage = new ArrayList<String>();
		
		if(mailSender!=null&&!"".equals(mailSender)){
			String recipient = mailSender.getRecipient();
			if(recipient!=null&&!"".equals(recipient)){
				String[] mutiUser = recipient.split(";");
				/**************************先把原有数据放入集合中*****************************/
				for(int k=0;k<mutiUser.length;k++){
					remainUserMessage.add(mutiUser[k]);
				}
				/**************************然后再去掉要去除的数据*****************************/
				for(int i=0 ;i<remainUserMessage.size();i++){
					String[] idAndUserMessage = remainUserMessage.get(i).split(":");
					String id = idAndUserMessage[0];//这是序号
					for(int j=0 ;j<ids.length ;j++){
						if(ids[j].equals(id)){
							remainUserMessage.remove(i);
							break;
						}
					}
				}
			}
		}
		/**************************要把list当中的数据重新组装成字符串**********************************/
		String recipients = "";
		for(String remain : remainUserMessage){
			recipients+=remain.toString();
			recipients+=";";
		}
		if(recipients.endsWith(";")){
			recipients = recipients.substring(0,recipients.length()-1);
		}
		/**************************最后重新保存ConfigUnitNodeMailSender*****************************/
		
		
		
//		String recipients = remainUserMessage.toString();
//		recipients = recipients.substring(1, recipients.length()-1);
		System.out.println(recipients);
		mailSender.setRecipient(recipients);
		super.getService().save(mailSender);
		
		return null;
	}
	/**
	 * 保存集团外部用户信息
	 * 保存格式为”序号（id）:用户名,邮件地址；序号（id）：用户名，邮件地址“，需要按照相应的id删除掉基本信息
	 * @return
	 * @throws Exception
	 */
	public void saveNoConbineMailNodeSenderMessage(String virtualId ,String nodeId, String nProduct)throws Exception{
		
		Map productMap = new HashMap();
		String records = "";
		List<String> UserMessage = new ArrayList<String>();
//		String nodeId = super.getRequest().getParameter("nodeId");
//		String virtualDefinitionInfoId = super.getRequest().getParameter("virtualDefinitionInfoId");
		
		/**************************按照”序号（id）:用户名,邮件地址；序号（id）：用户名，邮件地址“这种格式拼装数据********************************/
		JSONArray ja = JSONArray.fromObject("[" + nProduct + "]");
		for (int i = 0; i < ja.size(); i++) {
			
			JSONObject obj = (JSONObject) ja.get(i);//这相当于一条记录
			Iterator itProduct = obj.keys();//开始遍历这条记录的每一个字段
			records+=String.valueOf(i)+":";
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = obj.getString(key);	
				if(!"newRecord".equals(key)&&!"id".equals(key)){
					records+=value+",";		
				}
				
			}
			if(records.endsWith(",")){
				records = records.substring(0, records.length()-1);
			}
			records+=";";
		}
		System.out.println(records.length());
		if(records.endsWith(";")){
			records = records.substring(0, records.length()-1);
		}
		/**********************************保存相应的邮件单元数据数据*************************/
		ConfigUnitMailNodeSenderTable senderTable = null;
		ConfigUnitMailNodeSender mailNodeSender = cs.findMailNodeById(virtualId, nodeId);
		if(mailNodeSender==null||"".equals(mailNodeSender)){
			mailNodeSender = new ConfigUnitMailNodeSender();
			mailNodeSender.setRecipient(records);
			mailNodeSender.setVirtualProcessId(Long.valueOf(virtualId));
			mailNodeSender.setNodeId(Long.valueOf(nodeId));
			
		}else{
			mailNodeSender.setRecipient(records);
			mailNodeSender.setVirtualProcessId(Long.valueOf(virtualId));
			mailNodeSender.setNodeId(Long.valueOf(nodeId));
		}
		
		try{
			
			super.getService().save(mailNodeSender);
			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter writer = super.getResponse().getWriter();
			writer.write("{success:true}");
			writer.flush();
		}catch(Exception e){
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter writer = super.getResponse().getWriter();
			writer.write("{success:false}");
			writer.flush();
			e.printStackTrace();
			
		}
		
		
	}
	
	
	/**
	 * MailNodeSender邮件信息的保存(信息包括邮件标题，内容，抄送人地址，抄送人姓名)
	 * 保存集团内部用户信息
	 * @Methods Name saveNodeMailMessage
	 * @Create In Mar 30, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String saveMailNodeSenderMessage() throws Exception{
		
		String perName = "";
		String perMail = "";
		/*****************************得到前台传输的必要参数***************************************/		
		String nodeId = super.getRequest().getParameter("nodeId");
		String virtualDefinitionInfoId = super.getRequest().getParameter("virtualDefinitionInfoId");
		String subject = super.getRequest().getParameter("subject");
		String content = super.getRequest().getParameter("content");
		String product = super.getRequest().getParameter("product");
		String nProduct = super.getRequest().getParameter("nProduct");
		
		product = HttpUtil.ConverUnicode(product);
		subject = HttpUtil.ConverUnicode(subject);
		content = HttpUtil.ConverUnicode(content);
		nProduct = HttpUtil.ConverUnicode(nProduct);
		System.out.println("nProduct"+nProduct);
		System.out.println("product"+product);
		
		if (nProduct != null && nProduct.endsWith(",")) {
			nProduct = nProduct.substring(0, nProduct.length() - 1);
		}	
		
		if (product != null && product.endsWith(",")) {
			product = product.substring(0, product.length() - 1);
		}	
		this.saveNoConbineMailNodeSenderMessage(virtualDefinitionInfoId,nodeId,nProduct);
		ConfigUnitMailNodeSender mailNodeSender = cs.findMailNodeById(virtualDefinitionInfoId, nodeId);
		/*********************上面确定有没有相应的mail实体（根据虚拟流程标识和节点标识）***********************************************/
		
		JSONArray ja = JSONArray.fromObject("[" + product + "]");
		for (int i = 0; i < ja.size(); i++) {
			HashMap productMap = new HashMap();
			JSONObject obj = (JSONObject) ja.get(i);
			Iterator itProduct = obj.keys();
			while (itProduct.hasNext()) {//这是记录中相应的字段
				String key = (String) itProduct.next();
				String value = obj.getString(key);				
				productMap.put(key, value);				
			}
			perName = (String)productMap.get("name");
			perMail = (String)productMap.get("mail");
			if(perName.equals("")||perName==null){
				throw new Exception("收信人姓名不存在");				
			}
			String[] spl = perName.split("\\(");
			String name = spl[1].substring(0,spl[1].length()-1);
			/**********上面是把一条记录所有属性及其值都放到一个集合当中了**************/					
			UserInfo userInfo = (UserInfo)super.getService().find(UserInfo.class, "userName", name).get(0);
			
			if(mailNodeSender!=null){//说明已经保存过了		
					if(productMap.containsKey("id")){
						/*保存邮件基本信息数据*/				
						mailNodeSender.setContent(content);
						mailNodeSender.setSubject(subject);
						mailNodeSender.setNodeId(Long.valueOf(nodeId));
						mailNodeSender.setVirtualProcessId(Long.valueOf(virtualDefinitionInfoId));
						ConfigUnitMailNodeSender mailNode = null;
						
						try{
							mailNode = (ConfigUnitMailNodeSender)super.getService().save(mailNodeSender);
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:true}");
							writer.flush();
						}catch(Exception e){
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:false}");
							writer.flush();
							new Exception("保存邮件主题时发生异常(configUnitAction328)");	
						}
						
						/*保存收信人的基本信息*/
						ConfigUnitMailNodeSenderTable mailTable=(ConfigUnitMailNodeSenderTable)super.getService().find(ConfigUnitMailNodeSenderTable.class, (String)productMap.get("id"));
						mailTable.setUserInfo(userInfo);
						mailTable.setMailNodeSender(mailNode);
						mailTable.setMail(perMail);
						
						try{
							super.getService().save(mailTable);
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:true}");
							writer.flush();
						}catch(Exception e){
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:false}");
							writer.flush();
							new Exception("保存抄送人时发生异常(configUnitAction386)");	
						}						
					}else{									
						mailNodeSender.setContent(content);
						mailNodeSender.setSubject(subject);
						mailNodeSender.setNodeId(Long.valueOf(nodeId));
						mailNodeSender.setVirtualProcessId(Long.valueOf(virtualDefinitionInfoId));
						ConfigUnitMailNodeSender mailSender = null;
						try{
							mailSender = (ConfigUnitMailNodeSender)super.getService().save(mailNodeSender);
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:true}");
							writer.flush();
						}catch(Exception e){
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:false}");
							writer.flush();
							new Exception("保存邮件主题时发生异常(configUnitAction351)");	
						}
						/*保存抄送人的基本信息*/
						ConfigUnitMailNodeSenderTable mailTable = new ConfigUnitMailNodeSenderTable();
						mailTable.setUserInfo(userInfo);
						mailTable.setMailNodeSender(mailSender);
						mailTable.setMail(perMail);
						
						try{
							super.getService().save(mailTable);
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:true}");
							writer.flush();
						}catch(Exception e){
							super.getResponse().setCharacterEncoding("utf-8");
							PrintWriter writer = super.getResponse().getWriter();
							writer.write("{success:false}");
							writer.flush();
							new Exception("保存抄送人时发生异常(configUnitAction361)");
						}
					}	
					
			}else{//保存一个新的实体，里面没有任何数据	
				
					ConfigUnitMailNodeSender mailSender = new ConfigUnitMailNodeSender();
					mailSender.setContent(content);
					mailSender.setSubject(subject);
					mailSender.setNodeId(Long.valueOf(nodeId));
					mailSender.setVirtualProcessId(Long.valueOf(virtualDefinitionInfoId));
					ConfigUnitMailNodeSender UnitMail = null;
					
					try{
						UnitMail = (ConfigUnitMailNodeSender)super.getService().save(mailSender);
						super.getResponse().setCharacterEncoding("utf-8");
						PrintWriter writer = super.getResponse().getWriter();
						writer.write("{success:true}");
						writer.flush();
					}catch(Exception e){
						super.getResponse().setCharacterEncoding("utf-8");
						PrintWriter writer = super.getResponse().getWriter();
						writer.write("{success:false}");
						writer.flush();
						e.printStackTrace();
						new Exception("保存邮件主题时发生异常(configUnitAction376)");						
					}
					
					/*保存抄送人的基本信息*/
					ConfigUnitMailNodeSenderTable mailTable = new ConfigUnitMailNodeSenderTable();
					mailTable.setUserInfo(userInfo);
					mailTable.setMailNodeSender(UnitMail);
					mailTable.setMail(perMail);
					
					try{
						super.getService().save(mailTable);
						super.getResponse().setCharacterEncoding("utf-8");
						PrintWriter writer = super.getResponse().getWriter();
						writer.write("{success:true}");
						writer.flush();
					}catch(Exception e){
						super.getResponse().setCharacterEncoding("utf-8");
						PrintWriter writer = super.getResponse().getWriter();
						writer.write("{success:false}");
						writer.flush();
						new Exception("保存抄送人时发生异常(configUnitAction386)");						
					}	
				}		
		}
		/**************************************************************************************************************××*******/
		super.getResponse().setCharacterEncoding("utf-8");
		PrintWriter writer = super.getResponse().getWriter();
		writer.write("{success:true}");
		writer.flush();
		return null;		
		
	}
}
