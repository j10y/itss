package com.zsgj.itil.config.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.ParameterService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.info.HistoryInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ModleToProcess;
import com.zsgj.itil.config.service.ConfigItemService;
import com.zsgj.itil.service.entity.ServiceItemProcess;

@SuppressWarnings("serial")
public class ConfigManagerAction extends BaseAction{
	private TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService)ContextHolder.getBean("parameterService");
	private Service service = (Service) ContextHolder.getBean("baseService");
//	private ContextService vm = (ContextService)ContextHolder.getBean("contextService");
//	private TaskService tm = (TaskService)ContextHolder.getBean("taskService");
	private ProcessService ps = (ProcessService)ContextHolder.getBean("processService");
	private ConfigItemService configItemService =(ConfigItemService)ContextHolder.getBean("configItemService");
	
	/**
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵����
	 * ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c,
	 * @Methods Name apply
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String apply() throws Exception{
		String json = "";
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
//		String departmentCode = super.getRequest().getParameter("deptcode");
//		String userAssign = super.getRequest().getParameter("userAssign");
//			//��Ҫ���������ĵ�ҵ�����
			Map<String,String> mapBizz = new HashMap<String,String>();
			if(buzzParameters!=null&&!buzzParameters.equals("")) {
				JSONObject jo = JSONObject.fromObject(buzzParameters);
				Iterator it = jo.keys();
				while(it.hasNext()) {
					String key = (String)it.next();
					String value = (String)jo.get(key);
					mapBizz.put(key, value);
				}
				//add by awen for add two param in bizMap on 2009-08-23 begin
				ConfigItem ci = (ConfigItem)service.find(ConfigItem.class, dataId, true);
				if(ci != null){
					mapBizz.put("applyName", ci.getName());
					mapBizz.put("applyNum", ci.getCisn());
				}
				//add by awen for add two param in bizMap on 2009-08-23 end
			}
			String creator = UserContext.getUserInfo().getUserName();
			Long instanceId = null;
			String meg = "";
			try{
				instanceId = ps.createProcess(definitionName,creator,mapBizz);
				json = "{success:true,id:'"+instanceId+"'}";	
			}catch(Exception e){
				meg = e.getMessage();
				json = "{success:true,Exception:'"+meg+"'}";
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
	 * ���ݲ�ͬ����������ʾ��ͬ�������б�
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String tasks() throws Exception{
		HttpServletRequest request = super.getRequest();
		//��Ҫ�Ĳ���
		String actor = request.getParameter("actorId");
		//String actor = UserContext.getUserInfo().getUserName();
		String json = "";
		
		int rowCount = 0;
	  	List<TaskInfo> list = ts.listTasks(actor);
		for(TaskInfo taskInfo:list) {
			String str = "";
			str += "pageUrl:'"+taskInfo.getBizParams().get("goStartState")+"',";
			str += "defname:'"+taskInfo.getDefinitionName()+"',";
			str += "defdesc:'"+taskInfo.getDefinitionDesc()+"',";
			str += "nodeName:'"+taskInfo.getNodeName()+"',";
			str += "taskId:'"+taskInfo.getId()+"',";
			str += "taskName:'"+taskInfo.getName()+"',";
			//��ʵ�����ƴ����û�ϵͳ��
//			String realName = getUserRealNameByName(taskInfo.getActorId());
//			str += "actorId:'"+realName+"',";
			str += "startDate:'"+toBlank(taskInfo.getStart())+"',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String applyTypeString = (String)bizParams.get("applyType");
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams==null||strBizParams.equals("null")?"''":strBizParams;
			if(strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if(strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0,strBizParams.length()-1);
			}
			
			str += strBizParams+",";
			str += "comments:'"+toBlank(taskInfo.getComments().getValue("comment"))+"'";
			str = "{"+str+"},";			
			if("cproject".equals(applyTypeString)){
				json += str;
				rowCount++;
			}
		}		
		json = deleteComma(json);
		json =  "{success: true, rowCount:'"+rowCount+"',data:["+json+"]}";
		
		HttpServletResponse res = super.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		return null;
	}
	/**
	 * ���ĳ���̵�������ʷ��Ϣ
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unused")
	private String next() throws Exception{
		//��Ҫ�Ĳ���
		String taskId = super.getRequest().getParameter("taskid");	 
		String procId = super.getRequest().getParameter("procid");		
		long instId = 0;
		if(procId!=null&&procId.trim().length()!=0) {
			instId = Long.parseLong(procId);
			List tasks = ps.getActiveTasks(instId);
			if(!tasks.isEmpty()) {
				taskId = ((TaskInfo)tasks.get(0)).getId()+"";
			}
		}
		else if(taskId!=null&&taskId.trim().length()!=0){
			instId = ts.getProcessInfo(Long.parseLong(taskId)).getId();
		}
		else {
			System.out.println("ListHistoryAction��������");
		}
		
//	 	List<HistoryInfo> list = ps.getHistory(instId);
	 	List<HistoryInfo> list = new ArrayList();
	 	List tasks = ps.getActiveTasks(instId);
	 	for(int i=0;i<tasks.size();i++) {
	 		TaskInfo ti = (TaskInfo)tasks.get(i);
	 		HistoryInfo hi = new HistoryInfo(ti);
	 		hi.setTaskName(ti.getName());
	 		list.add(hi);
	 	}
	 	String json = "";
	 	for(HistoryInfo historyInfo:list) {
	 		String str = "";
	 		//historyInfo.getComments()
	 		//��ʵ�����ƴ����û�ϵͳ��
//	 		String realName = getUserRealNameByName(historyInfo.getActorId());
//	 		str += "actorId:'"+realName+"',";
	 		str += "date:'"+historyInfo.getDate()+"',";	
	 		str += "definitionName:'"+historyInfo.getDefinitionName()+"',";
	 		str += "processId:'"+historyInfo.getProcessId()+"',";
	 		str += "nodeName:'"+historyInfo.getNodeName()+"',";
	 		str += "taskName:'"+historyInfo.getTaskName()+"',";
	 		str += "name:'"+historyInfo.getName()+"',";
	 		str += "taskId:'"+historyInfo.getTaskId()+"'";
	 		str = "{"+str+"},";
	 		json += str;
	 	}
	 	if(json.endsWith(",")) {
	 		json = json.substring(0,json.length()-1);
	 	}
	 	json = "["+json+"]";
	 	//json = "{data:["+json+"]}";
	 	//json = "{success:true,data:"+json+"}";
		return json;		
	}
	
	private String toBlank(Object o){
		return o==null?"":(String)o;		
	}
	
	private String deleteComma(String json){
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return json;
	}
	public String getModleProcess(){
		HashMap modleTypeMap=new HashMap();
		modleTypeMap.put("CI", "������");
		modleTypeMap.put("SCI", "������");
		modleTypeMap.put("SCIC", "����Ŀ¼");
		modleTypeMap.put("Event", "�¼�");
		modleTypeMap.put("Notice", "����(����)");
		modleTypeMap.put("Notice_Sample", "����(��ͨ)");
		modleTypeMap.put("Kno_Solution", "�������");
		modleTypeMap.put("Kno_Contract", "��ͬ");
		modleTypeMap.put("Kno_File", "�ļ�");
		modleTypeMap.put("Kno_Knowledge", "֪ʶ");
		modleTypeMap.put("Busi_InFeel", "��������տ�");
		modleTypeMap.put("Busi_OutFeel", "������㸶��");
		
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String json="";
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start / pageSize + 1;
		Page page=service.findByPagedQuery(ModleToProcess.class, "id", false, pageNo, pageSize);
		List<ModleToProcess> list=page.getData();
		for(ModleToProcess mp:list){
			String processStatusType1="";
			if(mp.getProcessStatusType().equals(0)){
				processStatusType1="��������";
			}else if(mp.getProcessStatusType().equals(1)){
				processStatusType1="�������";
			}else if(mp.getProcessStatusType().equals(2)){
				processStatusType1="ɾ������";
			}
			
			json+="{id:\""+mp.getId()+"\",modleType:\""+modleTypeMap.get(mp.getModleType())+"\",processStatusType:\""+processStatusType1+"\",definitionName:\""+mp.getProcessInfo().getVirtualDefinitionDesc()+"/"+mp.getProcessInfo().getVirtualDefinitionName()+"\"},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:"+page.getTotalCount()+",data:[" + json + "]}";
		try {
			HttpServletResponse res = super.getResponse();
			res.setCharacterEncoding("utf-8");
			PrintWriter pw = res.getWriter();
			pw.write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String saveModleToProcess(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String info = request.getParameter("info");
//		ServiceItem serviceItem = sis.findServiceItemById(servcieItemId);
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Iterator columnIter = panelJO.keys();
		while(columnIter.hasNext()){
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			columnName = StringUtils.substringAfter(columnName, "$");
			dataMap.put(columnName, columnValue);
		}
		ModleToProcess mp=new ModleToProcess();
		if(dataMap.get("id")!=null&&dataMap.get("id").toString().length()>0){
			mp.setId(Long.parseLong(dataMap.get("id").toString()));
		}
		String processInfoId = (String) dataMap.get("processInfo");
		//String definname=processInfoId.substring(processInfoId.lastIndexOf("/")+1,processInfoId.length());
		
		List list=service.find(VirtualDefinitionInfo.class, "id", Long.parseLong(processInfoId));
		if(list!=null&&list.size()>0){
			VirtualDefinitionInfo vd=(VirtualDefinitionInfo) list.get(0);
			mp.setProcessInfo(vd);
			mp.setDefinitionName(vd.getVirtualDefinitionName());
		}
		mp.setModleType(dataMap.get("modleType").toString());
		mp.setProcessStatusType(Integer.parseInt(dataMap.get("processStatusType").toString()));
		ModleToProcess mp1=configItemService.findProcessByParm(dataMap.get("modleType").toString(), dataMap.get("processStatusType").toString());
		String mesg="����ɹ�";
		if(dataMap.get("id").toString().length()==0&&mp1!=null){
			mesg="�Ѵ��ڣ���������";
		}else{
			service.save(mp);
		}
		//service.save(mp);
		String json= "{success:true,mesg:\""+mesg+"\"}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String removeModleToProcess(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String[] dataIds =request.getParameterValues("dataId");
		String dataId = request.getParameter("dataId");
		service.remove(ModleToProcess.class, dataId);
		String json= "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String findModleToProcess(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		ModleToProcess mp=(ModleToProcess) service.find(ModleToProcess.class, dataId);
		String temp="";
		temp+="ModleToProcess$id:\""+mp.getId()+"\",ModleToProcess$processInfo:\""+mp.getProcessInfo().getId()
		+"\",ModleToProcess$processStatusType:\""+mp.getProcessStatusType()+"\",ModleToProcess$modleType:\""+mp.getModleType()+"\"";
		String json = "{success:" + true + ",form:[{"+ temp + "}]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String findProcessByPram(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String modleType = request.getParameter("modleType");
		String processStatusType = request.getParameter("processStatusType");
		ModleToProcess mp=configItemService.findProcessByParm(modleType, processStatusType);
		String json ="";
		if(mp!=null){
			json = "{success:" + true + ",vpid:\""+mp.getDefinitionName()+"\"}";
		}else{
			json = "{success:true,Exception:'û���ҵ���Ӧ������'}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
