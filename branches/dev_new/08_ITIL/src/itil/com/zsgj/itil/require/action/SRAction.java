package com.zsgj.itil.require.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemFile;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.impl.TaskServiceImpl;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemExtendInfo;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.config.extci.entity.DeliveryTeam;
import com.zsgj.itil.config.extci.entity.Server;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.config.extlist.entity.NewNoticeType;
import com.zsgj.itil.config.extlist.entity.SRAnalyse;
import com.zsgj.itil.config.extlist.entity.SREngineerAndShare;
import com.zsgj.itil.config.extlist.entity.SREngineerOperateInfo;
import com.zsgj.itil.config.extlist.entity.SRExpendPlan;
import com.zsgj.itil.config.extlist.entity.SRGroupFinanceInfo;
import com.zsgj.itil.config.extlist.entity.SRIncomePlan;
import com.zsgj.itil.config.extlist.entity.SRManager;
import com.zsgj.itil.config.extlist.entity.SRProjectNotice;
import com.zsgj.itil.config.extlist.entity.SRProjectPlan;
import com.zsgj.itil.config.extlist.entity.SRServiceItem;
import com.zsgj.itil.config.extlist.entity.SRServiceProvider;
import com.zsgj.itil.config.extlist.entity.SRTestReport;
import com.zsgj.itil.config.extlist.entity.SRTransmisEngineer;
import com.zsgj.itil.config.extlist.entity.SRprojectContracts;
import com.zsgj.itil.notice.entity.NewNotice;
import com.zsgj.itil.project.entity.ProjectPlanProgress;
import com.zsgj.itil.project.entity.ProjectWorkReport;
import com.zsgj.itil.project.service.SRProjectPlanService;
import com.zsgj.itil.project.service.SRServiceItemService;
import com.zsgj.itil.require.entity.BASISEngineerFeedback;
import com.zsgj.itil.require.entity.EBEngineerFeedback;
import com.zsgj.itil.require.entity.ERP_NormalNeed;
import com.zsgj.itil.require.entity.ErpEngineerFeedback;
import com.zsgj.itil.require.entity.RequireApplyDefaultAudit;
import com.zsgj.itil.require.entity.RequireFactoryInfo;
import com.zsgj.itil.require.entity.SRServiceProviderInfo;
import com.zsgj.itil.require.entity.SRTestReportFile;
import com.zsgj.itil.require.entity.SRUserFeedback;
import com.zsgj.itil.require.entity.ServerManage;
import com.zsgj.itil.require.entity.ServerManageInfoOne;
import com.zsgj.itil.require.entity.ServerManageInfoTwo;
import com.zsgj.itil.require.entity.SpecialRequirement;
import com.zsgj.itil.require.entity.SpecialRequirementInfo;
import com.zsgj.itil.require.service.RequireService;
import com.zsgj.itil.require.service.SRService;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.train.entity.Quest;
import com.zsgj.itil.train.entity.QuestOption;
import com.zsgj.itil.train.entity.QuestResult;
import com.zsgj.itil.train.entity.Survey;

public class SRAction extends BaseAction{
	
	ApplicationContext ctx = ContextHolder.getApplicationContext();
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private SystemMainTableService systemMainTableService = (SystemMainTableService) getBean("systemMainTableService");
	private RequireService  requireService=(RequireService)getBean("requireService");
	private SRService srs = (SRService) getBean("srService");
	private Service service = (Service) ContextHolder.getBean("baseService");
//	private SRServiceItemService srsis = (SRServiceItemService) getBean("srServiceItemService");
	private SRProjectPlanService projectPlanService = (SRProjectPlanService) getBean("SRprojectPlanService");
	/**
 	 * ������Ի�������ʵ��
 	 * @Methods Name saveSpecialRequire
 	 * @Create In Jun 25, 2009 By lee
 	 * @return String
 	 */
	public String saveSpecialRequire(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String param = request.getParameter("info");
		Map map = getMapFormPanelJson(param);
		SpecialRequirement sr = (SpecialRequirement) metaDataManager.saveEntityData(SpecialRequirement.class, map);
//		String name = sr.getName();

		//add by lee for ���ط����̸�����ID�����ڽڵ�������ָ��ʱ�� in 20091126 begin
		DeliveryTeam deliveryTeam = sr.getDeliveryTeam();
		String principalId = "0";	//�����̸�����Id
		if(deliveryTeam!=null){
			deliveryTeam = (DeliveryTeam) service.find(DeliveryTeam.class, deliveryTeam.getId().toString(),true);
			if(deliveryTeam!=null&&deliveryTeam.getPrincipal()!=null){
				principalId = deliveryTeam.getPrincipal().getId().toString();
			}
		}
		ServiceEngineer mainEngineer = sr.getMainEngineer();
		String mainEngineerId = "0";
		if(mainEngineer!=null){
			mainEngineer = (ServiceEngineer) service.find(ServiceEngineer.class, mainEngineer.getId().toString(),true);
			if(mainEngineer!=null&&mainEngineer.getUserInfo()!=null){
				mainEngineerId = mainEngineer.getUserInfo().getId().toString();
			}
		}
		UserInfo serviceManager = null;
		if(deliveryTeam!=null){
			serviceManager = deliveryTeam.getMajordomo();//�����ܼ�
		}
		String serviceManagerId = "";
		if(serviceManager != null){
			serviceManagerId = serviceManager.getId().toString();
		}
		
		String json= "{success:true,id:"+sr.getId()+",serviceProviderInOfficer:"+principalId+",mainEngineer:"+mainEngineerId+",serviceManager:'"+serviceManagerId+"'}";
		//add by lee for ���ط����̸�����ID�����ڽڵ�������ָ��ʱ�� in 20091126 end
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
	/**
	 * ����ERP����ʦ������Ϣ
	 * @Methods Name saveErpEngineerFeedback
	 * @Create In Aug 13, 2009 By lee
	 * @return String
	 */
	public String saveErpEngineerFeedback(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		ERP_NormalNeed sr=(ERP_NormalNeed)super.getService().find(ERP_NormalNeed.class, reqId);
		String param = request.getParameter("info");
		Map map = getMapFormPanelJson(param);
		map.put("erpNeed", sr);
		ErpEngineerFeedback sp =(ErpEngineerFeedback) metaDataManager.saveEntityData(ErpEngineerFeedback.class, map);
		String json= "{success:true,id:"+sp.getId()+"}";
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
	/**
	 * ����BASIS����ʦ������Ϣ
	 * @Methods Name saveBasisEngineerFeedback
	 * @Create In Aug 13, 2009 By lee
	 * @return String
	 */
	public String saveBasisEngineerFeedback(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		String param = request.getParameter("info");
		ERP_NormalNeed sr=(ERP_NormalNeed)super.getService().find(ERP_NormalNeed.class, reqId);
		Map map = getMapFormPanelJson(param);
//		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		
		map.put("erpNeed", sr);
		BASISEngineerFeedback sp =(BASISEngineerFeedback) metaDataManager.saveEntityData(BASISEngineerFeedback.class, map);
		String json= "{success:true,id:"+sp.getId()+"}";
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
	/**
	 * ����EB����ʦ������Ϣ
	 * @Methods Name saveEbEngineerFeedback
	 * @Create In Aug 13, 2009 By lee
	 * @return String
	 */
	public String saveEbEngineerFeedback(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		String param = request.getParameter("info");
		ERP_NormalNeed sr=(ERP_NormalNeed)super.getService().find(ERP_NormalNeed.class, reqId);
		Map map = this.getMapFormPanelJson(param);
		
		map.put("erpNeed", sr);
		EBEngineerFeedback sp =(EBEngineerFeedback) metaDataManager.saveEntityData(EBEngineerFeedback.class, map);
		String json= "{success:true,id:"+sp.getId()+"}";
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
	/**
	 * ���漼���ܼ�����ҳ��
	 * @Methods Name saveSRServiceProviderInfo
	 * @Create In Jun 25, 2009 By lee
	 * @return String
	 */
	public String saveSRServiceProviderInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")||
					key.equalsIgnoreCase("reqId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		map.put("specialRequire", sr);
		SRServiceProviderInfo sp =(SRServiceProviderInfo) metaDataManager.saveEntityData(SRServiceProviderInfo.class, map);
		//String mainEngineerId = sp.getMainEngineer().getUserInfo().getId().toString();
		DeliveryTeam spin = (DeliveryTeam) service.find(DeliveryTeam.class, sp.getDeliveryTeam().getId().toString());
		String officerId = spin.getPrincipal().getId().toString();//�����̸�����ID
		String engineerId = "0";
		if(sp.getMainEngineer()!=null){
			ServiceEngineer mainEngineer = (ServiceEngineer) service.find(ServiceEngineer.class, sp.getMainEngineer().getId().toString());
			UserInfo user = (UserInfo) service.find(UserInfo.class, mainEngineer.getUserInfo().getId().toString());
			engineerId = user.getId().toString();
		}
		String json= "{success:true,id:"+sp.getId()+",engineerId:"+engineerId+",officerId:"+officerId+"}";
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
	/**
	 * ����ͻ�IT������ѡ�񽻸�����ͷ�̯���
	 * @Methods Name saveSREngineerAndShareInfo
	 * @Create In Jun 25, 2009 By lee
	 * @return String
	 */
	public String saveSREngineerAndShareInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")||
					key.equalsIgnoreCase("reqId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		map.put("specialRequire", sr);
		SREngineerAndShare sp =(SREngineerAndShare) metaDataManager.saveEntityData(SREngineerAndShare.class, map);
		String json= "{success:true,id:"+sp.getId()+"}";
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
	/**
	 * ������ǰ֧��ʱ������������Ϣ
	 * @Methods Name saveSREngineerOperateInfo
	 * @Create In Jun 25, 2009 By lee
	 * @return String
	 */
	public String saveSREngineerOperateInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")||
					key.equalsIgnoreCase("reqId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		map.put("specialRequire", sr);
		SREngineerOperateInfo sp =(SREngineerOperateInfo) metaDataManager.saveEntityData(SREngineerOperateInfo.class, map);
		String json= "{success:true,id:"+sp.getId()+"}";
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
	/**
	 * ��ȡ�����Ӧ������ID,��ת����������SCIDҳ��
	 * @Methods Name getReqServiceItemId
	 * @Create In Mar 11, 2009 By lee
	 * @return String
	 */
	public String getReqServiceItemId(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");	
		String siId = "0";
		SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, reqId);
		SRServiceItem srsi = (SRServiceItem) getService().findUnique(SRServiceItem.class, "specialRequire", sr);
		if(srsi!=null){
		siId = srsi.getServiceItem().getId().toString();
		}
		String json= "{success:true,id:"+siId+"}";
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
	/**
	 * ��ȡ�����Ӧ������ID,��ת����������SCIDҳ��
	 * @Methods Name getReqServiceProviderId
	 * @Create In Apr 10, 2009 By lee
	 * @return String
	 */
	public String getReqServiceProviderId(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		String serviceItemId = "0";
		SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, reqId);
		SRServiceProvider srsp = (SRServiceProvider) getService().findUnique(SRServiceProvider.class, "specialRequire", sr);
		if(srsp!=null){
		serviceItemId = srsp.getId().toString();
		}
		String json= "{success:true,id:"+serviceItemId+"}";
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
	
	/**
	 * �����û����Ա���
	 * @Methods Name saveProjectPlan
	 * @Create In May 5, 2009 By gaowen
	 * @return String
	 */
	public String saveTestReport(){
		String product = super.getRequest().getParameter("product");
		String reqId = super.getRequest().getParameter("reqId");
		SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, reqId);
		product = HttpUtil.ConverUnicode(product);
		JSONArray ja = JSONArray.fromObject("[" + product + "]");
//		SRTestReport srTestReport = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap productMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);
//				if(key.equals("code")&&value=="null"){
//					questOption = null;
//				}
//				if(("flm_ProjectTestReport$id").equals(key)&&value!="null"){
//					srTestReport = (SRTestReport) super.getService().find(SRTestReport.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				productMap.put(key, value);
			}
			//productMap.put("requirementId", reqId);
			//productMap.put("requirementClass", reqCls);
			productMap.put("specialRequire",sr);
			metaDataManager.saveEntityData(SRTestReport.class, productMap);
//			projectTestReport = (ProjectTestReport) BeanUtil.getObject(productMap, ProjectTestReport.class);
//			super.getService().save(projectTestReport);
		}
		return null;
	}
	/**
	 * ������Ա��渽��
	 * @Methods Name saveTestReportFile
	 * @Create In Aug 6, 2009 By lee
	 * @return String
	 */
	public String saveTestReportFile(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("dataId");
		SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, reqId);
		SRTestReportFile file = (SRTestReportFile) getService().findUnique(SRTestReportFile.class, "specialRequire", sr);
		String fileStr = "";
		if(file==null){
			SRTestReportFile temp = new SRTestReportFile();
			temp.setSpecialRequire(sr);
			fileStr = new Date().getTime()+"";
			temp.setAttachment(fileStr);
			super.getService().save(temp);
		}else{
			fileStr = file.getAttachment(); 
		}
		String json= "{success:true,file:"+fileStr+"}";
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
	/**
	 * ɾ���û����Ա���  testByClient.js
	 * @Methods Name removeGridProjectPlan
	 * @Create In May 5, 2009 By gaowen
	 * @return String
	 */
	public String removeTestReport() {
		String removedIds = super.getRequest().getParameter("removedIds");
		String[] removedId = StringUtils.split(removedIds,",");
		for(int i=0;i<removedId.length;i++){
			if(!"null".equals(removedId[i])){
				super.getService().remove(SRTestReport.class,removedId[i]);
			}
		}
		return null;
	}
		/**
	 * ������Ի�����֧������ƻ�
	 * @Methods Name saveExpendPlan
	 * @Create In May 5, 2009 By lee
	 * @return String
	 */
	public String saveExpendPlan(){
		String info = super.getRequest().getParameter("info");
		String reqId = super.getRequest().getParameter("reqId");
		info = HttpUtil.ConverUnicode(info);
		JSONArray ja = JSONArray.fromObject("[" + info + "]");
//		SRExpendPlan srep = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap dataMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);
//				if(("SRExpendPlan$id").equals(key)&&value!="null"){
//					srep = (SRExpendPlan) super.getService().find(SRExpendPlan.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				dataMap.put(key, value);
			}
			dataMap.put("specialRequire", reqId);
			dataMap.put("isFinish", Integer.valueOf(0));//add by lee for buinessAccount in 20090909
			metaDataManager.saveEntityData(SRExpendPlan.class, dataMap);
		}
		return null;
	}
	/**
	 * ������Ի������տ�ƻ�
	 * @Methods Name saveIncomePlan
	 * @Create In May 5, 2009 By lee
	 * @return String
	 */
	public String saveIncomePlan(){
		String info = super.getRequest().getParameter("info");
		String reqId = super.getRequest().getParameter("reqId");
		info = HttpUtil.ConverUnicode(info);
		JSONArray ja = JSONArray.fromObject("[" + info + "]");
//		SRIncomePlan srip = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap dataMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);
//				if(("SRIcomePlan$id").equals(key)&&value!="null"){
//					srip = (SRIncomePlan) super.getService().find(SRIncomePlan.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				dataMap.put(key, value);
			}
			dataMap.put("specialRequire", reqId);
			dataMap.put("isFinish", Integer.valueOf(0));//add by lee for buinessAccount in 20090909
			metaDataManager.saveEntityData(SRIncomePlan.class, dataMap);
		}
		return null;
	}
	
	/**
	 * ͨ��SpecialRequire��systemMainTable�е�Id��ѯ�û�����ȵ�������
	 * @author gaowen
	 * @return
	 * @throws IOException
	 */
	public String findSpecialRequireSurvey() throws IOException {
		HttpServletResponse response = super.getResponse();
		//System.out.println(requireService);
		Survey survey = requireService.findSpecialRequireSurvey();
		String json = "";
		json="{success : true , surveyId : '" + survey.getId() + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * ��ѯ�û����������б�
	 * @author gaowen
	 * @return
	 * @throws IOException
	 */
	public String findQuestions() throws IOException {
		HttpServletRequest request = super.getRequest();
		Long surveyId = Long.parseLong(request.getParameter("surveyId"));
		Long dataId = Long.parseLong(request.getParameter("dataId"));
		Long taskId =null;
		Survey survey = (Survey) service.find(Survey.class, request.getParameter("surveyId"));
		if(request.getParameter("taskId") != null){
			taskId = Long.parseLong(request.getParameter("taskId"));
		}
		UserInfo userInfo = UserContext.getUserInfo();
		//��������Ѿ������ʾ�
		boolean isExist = requireService.findIsUserFeedbackOrNot(userInfo.getId(), dataId, surveyId);
		if(isExist){
			return "userFeedback_success";
		}
		List<Quest> questList = requireService.findQuest(surveyId);
		Iterator<Quest> iter = questList.iterator();
		Map map = new LinkedHashMap();//linkedHashMap��set��ȥ�Ⱥ�˳�������
		while(iter.hasNext()){
			Quest  quest = iter.next();
			List<QuestOption> list = requireService.findQuestOption(quest);
			map.put(quest, list);
		}
		super.getRequest().setAttribute("questOptions", map);
		super.getRequest().setAttribute("dataId", dataId);
		if(taskId != null){
			super.getRequest().setAttribute("taskId", taskId);
		}
		super.getRequest().setAttribute("survey", survey);
		super.getRequest().setAttribute("surveyId", surveyId);
		return "userFeedback_view";
	}
	
	/**
	 * ��ѯ�û���������б�
	 * @author gaowen
	 * @return
	 * @throws IOException
	 */
	public String findQuestionsResult() throws IOException {
		HttpServletRequest request = super.getRequest();
		Long surveyId = Long.parseLong(request.getParameter("surveyId"));
		Long dataId = Long.parseLong(request.getParameter("dataId"));
		Long taskId =null;
		Survey survey = (Survey) service.find(Survey.class, request.getParameter("surveyId"));
		if(request.getParameter("taskId") != null){
			taskId = Long.parseLong(request.getParameter("taskId"));
		}
//		UserInfo userInfo = UserContext.getUserInfo();
		List<Quest> questList = requireService.findQuest(surveyId);
		Iterator<Quest> iter = questList.iterator();
		Map map = new LinkedHashMap();//linkedHashMap��set��ȥ�Ⱥ�˳�������
		while(iter.hasNext()){
			Quest  quest = iter.next();
			List<QuestOption> list = requireService.findQuestOption(quest);
			map.put(quest, list);
		}
		List questResultList=super.getService().find(QuestResult.class,"objId", dataId);
		Map results=new LinkedHashMap();
		Iterator<QuestResult> iterResult=questResultList.iterator();
		while(iterResult.hasNext()){
			QuestResult questResult=iterResult.next();
			Quest q=questResult.getQuest();
			QuestOption qo=questResult.getQuestOption();
			results.put(q, qo);
			//System.out.println(questResult.getQuestOption().getContent());
		}
		
		super.getRequest().setAttribute("questOptions", map);
		super.getRequest().setAttribute("dataId", dataId);
		
		if(taskId != null){
			super.getRequest().setAttribute("taskId", taskId);
		}
		super.getRequest().setAttribute("survey", survey);
		super.getRequest().setAttribute("surveyId", surveyId);
		super.getRequest().setAttribute("results", results);
		return "userFeedback_result";
	}
	/**
	 * �����û����������
	 * @method saveQuestResult
	 * @return string 
	 * @author gaowen
	 * @throws IOException
	 */
	public String saveQuestResult() throws IOException {
		HttpServletRequest request = super.getRequest();
		Long dataId = Long.parseLong(request.getParameter("dataId"));
		String surveyId = request.getParameter("surveyId");
		UserInfo userInfo = UserContext.getUserInfo();
		Survey survey = (Survey) service.find(Survey.class, surveyId, true);
		boolean isExist = requireService.findIsUserFeedbackOrNot(userInfo.getId(), dataId, Long.valueOf(surveyId));
		if(isExist){
			return "userFeedback_success";
		}
		String[] quest_ids = super.getRequest().getParameterValues("quest_id");   
		if(quest_ids!=null){
			for(int i=0;i<quest_ids.length;i++){
				String radio = super.getRequest().getParameter(quest_ids[i]+"_radio");
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids[i]);
				QuestOption questOption = (QuestOption) super.getService().find(QuestOption.class, radio);
				QuestResult questResult = new QuestResult();
				questResult.setQuest(quest);
				questResult.setQuestOption(questOption);
				questResult.setUserId(userInfo);
				questResult.setTrainCourse(null);
				questResult.setSurvey(survey);
				questResult.setObjId(dataId);
				super.getService().save(questResult);
			}
		}
		String[] quest_ids2 = super.getRequest().getParameterValues("quest_id2");
		if(quest_ids2!=null){
			for(int i=0;i<quest_ids2.length;i++){
				String[] radio = super.getRequest().getParameterValues(quest_ids2[i]+"_checkbox");
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids2[i]);
					for(int j=0;j<radio.length;j++){
						QuestOption questOption = (QuestOption) super.getService().find(QuestOption.class, radio[j]);
						QuestResult questResult = new QuestResult();
						questResult.setQuest(quest);
						questResult.setQuestOption(questOption);
						questResult.setUserId(userInfo);
						questResult.setTrainCourse(null);
						questResult.setSurvey(survey);
						questResult.setObjId(dataId);
						super.getService().save(questResult);
				}
			}
		}
		
		String[] quest_ids3 = super.getRequest().getParameterValues("quest_id3");
		if(quest_ids3!=null){
			for(int i=0;i<quest_ids3.length;i++){
				 //map.put(quest_ids[i],request.getParameter(quest_ids[i]+"_radio")) ;     //��ĿID��Ӧ���û�ѡ�Ĵ�
				String radio = super.getRequest().getParameter(quest_ids3[i]+"_text");
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids3[i]);
				QuestOption questOption = new QuestOption();
				questOption.setContent(radio);
				questOption.setQuest(quest);
				super.getService().save(questOption);
				QuestResult questResult = new QuestResult();
				questResult.setQuest(quest);
				questResult.setQuestOption(questOption);
				questResult.setUserId(userInfo);
				questResult.setTrainCourse(null);
				questResult.setSurvey(survey);
				questResult.setObjId(dataId);
				super.getService().save(questResult);
			}
		}
		String[] quest_ids4 = super.getRequest().getParameterValues("quest_id4");
		if(quest_ids4!=null){
			for(int i=0;i<quest_ids4.length;i++){
				 //map.put(quest_ids[i],request.getParameter(quest_ids[i]+"_radio")) ;     //��ĿID��Ӧ���û�ѡ�Ĵ�
				String radio = super.getRequest().getParameter(quest_ids4[i]+"_text");
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids4[i]);
				QuestOption questOption = new QuestOption();
				questOption.setContent(radio);
				questOption.setQuest(quest);
				super.getService().save(questOption);
				QuestResult questResult = new QuestResult();
				questResult.setQuest(quest);
				questResult.setQuestOption(questOption);
				questResult.setUserId(userInfo);
				questResult.setTrainCourse(null);
				questResult.setSurvey(survey);
				questResult.setObjId(dataId);
				super.getService().save(questResult);
			}
		}
		return "userFeedback_history";
	}
	/**
	 * ��ѯ�ύ���Ƿ���д����û����������ʾ�
	 * @method findIsUserFeedbackOrNot
	 * @author gaowen
	 * @return string 
	 * @throws IOException
	 */
	public String findIsUserFeedbackOrNot() throws IOException{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo userInfo = UserContext.getUserInfo();
		Long surveyId = Long.parseLong(request.getParameter("surveyId"));
		Long dataId = Long.parseLong(request.getParameter("dataId"));
		boolean isExist = requireService.findIsUserFeedbackOrNot(userInfo.getId(), dataId, surveyId);
		String json = "";
		json="{success : false}";
		if(isExist){
			json="{success : true}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		//System.out.println(json);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * ���������û�����
	 * @Methods Name saveUserFeedback
	 * @Create In Aug 6, 2009 By lee
	 * @return String
	 */
	public String saveUserFeedback(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")||
					key.equalsIgnoreCase("reqId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		map.put("specialRequire", sr);
		SRUserFeedback feedback=(SRUserFeedback) metaDataManager.saveEntityData(SRUserFeedback.class, map);
		String feedbackId = feedback.getId().toString();
		String json= "{success:true,id:"+feedbackId+"}";
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
	/**
	 * ���潻���Ŷ����ͺͽ����Ŷӹ�ϵ
	 * @Methods Name saveServiceProviderInfo
	 * @Create In May 12, 2009 By gaowen
	 * @return String
	 */
	public String saveServiceProviderInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		//String reqClass = request.getParameter("reqClass");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")||
					key.equalsIgnoreCase("reqId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		map.put("specialRequire", sr);
		SRServiceProvider srServiceProvider=(SRServiceProvider) metaDataManager.saveEntityData(SRServiceProvider.class, map);
		String praId = srServiceProvider.getId().toString();
		String userId = "";
		String userName = "";
		String serviceProviderId =srServiceProvider.getDeliveryTeam().getId().toString();
		DeliveryTeam serviceProviderIn = (DeliveryTeam) getService().find(DeliveryTeam.class, serviceProviderId);
	    userId = serviceProviderIn.getPrincipal().getId().toString();
	    userName = serviceProviderIn.getPrincipal().getUserName();
		String json= "{success:true,id:"+praId+","+"userId:"+userId+",userName:'"+userName+"'}";
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
	/**
	 * ������Ի���������Ӧ������
	 * @Methods Name saveServiceItemForRequire
	 * @Create In May 12, 2009 By gaowen
	 * @return String
	 */
	public String saveServiceItemForRequire(){
		HttpServletRequest request = super.getRequest();
		String requireId= request.getParameter("reqId");
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")||
					key.equalsIgnoreCase("reqId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		ServiceItem serviceItem =(ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, map);
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, requireId);

		SRServiceItem srServiceItem = null;
		List list = getService().find(SRServiceItem.class, "serviceItem", serviceItem);
		if(!(list.size()>0)){
			srServiceItem = new SRServiceItem();
			srServiceItem.setSpecialRequire(sr);
			srServiceItem.setServiceItem(serviceItem);
			srServiceItem=(SRServiceItem) getService().save(srServiceItem);
		}
    	return null;
	}
	/**
	 * ����ѡ��ķ������ϵ
	 * @Methods Name saveServiceItemReShip
	 * @Create In May 12, 2009 By gaowen
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveServiceItemReShip(){
		String reqId = super.getRequest().getParameter("reqId");
		String newdataId = super.getRequest().getParameter("newdataId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		ServiceItem serviceItem = (ServiceItem) super.getService().find(ServiceItem.class, newdataId);
		SRServiceItem srServiceItem = (SRServiceItem) getService().findUnique(SRServiceItem.class, "specialRequire", sr);
		if(srServiceItem==null){
			srServiceItem=new SRServiceItem();
			srServiceItem.setSpecialRequire(sr);
			srServiceItem.setServiceItem(serviceItem);
			srServiceItem=(SRServiceItem) getService().save(srServiceItem);
		}else{
			srServiceItem.setServiceItem(serviceItem);
			srServiceItem=(SRServiceItem) getService().save(srServiceItem);
		}
    	return null;
	}
	/**
	 * ��ȡ��Ŀ��Ӧ����ʦ
	 * @Methods Name getReqEngineerId
	 * @Create In May 13, 2009 By gaowen
	 * @return String
	 */
	public String getReqEngineerId(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		SRManager requirementEngineer = (SRManager)getService().findUnique(SRManager.class, "specialRequire", sr);
		String requirementEngineerId = "0";
		if(requirementEngineer!=null){
			requirementEngineerId = requirementEngineer.getId().toString();
		}
		String json= "{success:true,id:"+requirementEngineerId+"}";
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
	/**
	 * ������Ի���������Ӧ��Ŀ�����������ʦ
	 * @Methods Name saveReqEngineerForReq
	 * @Create In May 13, 2009 By gaowen
	 * @return String
	 */
	public String saveReqEngineerForReq(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
//		String id= request.getParameter("id");
		String requireId= request.getParameter("reqId");
		String mainManagerId = request.getParameter("mainManager");
		String assistantEngineerId = request.getParameter("assistantEngineer");
		String isNewFactory = request.getParameter("isNewFactory");
		
		UserInfo mainManager = (UserInfo) getService().find(UserInfo.class, mainManagerId);
		UserInfo assistantEngineer=null;
		if(assistantEngineerId!=""){
			assistantEngineer = (UserInfo) getService().find(UserInfo.class, assistantEngineerId);
		}
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, requireId);
		SRManager engineer = (SRManager)getService().findUnique(SRManager.class, "specialRequire", sr);
		if(engineer==null){
			engineer = new SRManager();
		}
		engineer.setSpecialRequire(sr);
		engineer.setMainManager(mainManager);
		engineer.setAssistanEngineer(assistantEngineer);
		if(StringUtils.isNotBlank(isNewFactory)){
			engineer.setIsNewFactory(Integer.valueOf(isNewFactory));
		}
		SRManager srManager = (SRManager) getService().save(engineer);
		String srManagerId = srManager.getId().toString();
		String json = "{success:true,srManagerId:"+srManagerId+"}";
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
	/**
	 * ��ȡָ���������¿��ù���ʦ
	 * @Methods Name findEngineerByServiceProvider
	 * @Create In Jun 11, 2009 By lee
	 * @return String
	 */
	public String findEngineerByServiceProvider(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		int pageSize = 10;
		String paramName = super.getRequest().getParameter("start");
		String reqId = request.getParameter("requirementId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		SRServiceProvider requirementServiceProvider= (SRServiceProvider)getService().findUnique(SRServiceProvider.class, "specialRequire", sr);
		String json = "";
		int size = 0;
		if(requirementServiceProvider!=null){
		int pageNo = this.confirmPageNo(paramName, 1);
		String id = requirementServiceProvider.getDeliveryTeam().getId().toString();
		Page serviceEngineerIn = srs.findServiceEngineerIn(id,pageNo, pageSize);
		size = serviceEngineerIn.getPageSize();
		List<ServiceEngineer> list = serviceEngineerIn.list();
		for (ServiceEngineer engineer : list) {
			json += "{id:'" + engineer.getUserInfo().getId()
					+ "',userName:'"+ engineer.getUserInfo().getRealName()+"/"+engineer.getUserInfo().getUserName()
					+ "'},";
			}
		}
		
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:" + size + ",data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		}
	/**
	 * ���ҹ���ʦ �����б�
	 * @Methods Name findUserInfoByServiceProviderInfo
	 * @Create In May 13, 2009 By gaowen
	 * @return String
	 */
	public String findUserInfoByServiceProviderInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		int pageSize = 10;
		String paramName = super.getRequest().getParameter("start");
		String reqId = request.getParameter("requirementId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		SRServiceProvider requirementServiceProvider= (SRServiceProvider)getService().findUnique(SRServiceProvider.class, "specialRequire", sr);
		String json = "";
		int size = 0;
		if(requirementServiceProvider!=null){
		int pageNo = this.confirmPageNo(paramName, 1);
		String id = requirementServiceProvider.getDeliveryTeam().getId().toString();
		Page serviceEngineerIn = srs.findServiceEngineerIn(id,pageNo, pageSize);
		size = serviceEngineerIn.getPageSize();
		List<ServiceEngineer> list = serviceEngineerIn.list();
		for (ServiceEngineer ServiceEngineer : list) {
			json += "{id:'" + ServiceEngineer.getUserInfo().getId()
						+ "',name:'"
						+ ServiceEngineer.getUserInfo().getRealName()
						+ "'},";
			}
		}
		
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:" + size + ",data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		}
	/**
	 * ���Ҹ�������ʦ
	 * @Methods Name findassistantEngineerIdByProviderInfo
	 * @Create In May 13, 2009 By gaowen
	 * @return String
	 */
	public String findassistantEngineerIdByProviderInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		int pageSize = 10;
		String paramName = super.getRequest().getParameter("start");
		String reqId = request.getParameter("requirementId");
		SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
		SRServiceProvider requirementServiceProvider= (SRServiceProvider)getService().findUnique(SRServiceProvider.class, "specialRequire", sr);
		String json = "";
		int size = 0;
		if(requirementServiceProvider!=null){
			
		int pageNo = this.confirmPageNo(paramName, 1);
		String id = requirementServiceProvider.getDeliveryTeam().getId().toString();
		Page serviceEngineerIn = srs.findServiceEngineerIn(id,pageNo, pageSize);
			size = serviceEngineerIn.getPageSize();
			List<ServiceEngineer> list = serviceEngineerIn.list();
			for (ServiceEngineer ServiceEngineer : list) {
				json += "{id:'" + ServiceEngineer.getUserInfo().getId()
						+ "',name2:'"
						+ ServiceEngineer.getUserInfo().getRealName()
						+ "'},";
			}	
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:" + size + ",data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
		
	
		/**
		 * ȷ����ҳ��ҳ��
		 * @Methods Name confirmPageNo
		 * @Create In May 13, 2009 By gaowen
		 * @param paramName
		 * @param size
		 * @return int
		 */
		public int confirmPageNo(String paramName ,int size){
			
			if(paramName == null || paramName.equals("")||paramName.equals("0")){
				return size;
			}
			return Integer.parseInt(paramName);
		}
		/**
		 * ������ʦ
		 * @Methods Name getUserId
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String getUserId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRManager engineer = (SRManager)getService().findUnique(SRManager.class, "specialRequire", sr);
			String userId = "0";
			if(engineer!=null){
				if(engineer.getMainManager()!=null){
					userId = engineer.getMainManager().getId().toString();
				}
			}
			String json= "{success:true,id:"+userId+"}";
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
		/**
		 * ��������ʦ
		 * @Methods Name getassistantEngineerUserId
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String getassistantEngineerUserId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRManager engineer = (SRManager)getService().findUnique(SRManager.class, "specialRequire", sr);
			String userId = "0";
			if(engineer!=null){
				if(engineer.getAssistanEngineer()!=null){
					userId = engineer.getAssistanEngineer().getId().toString();
				}
			}
			String json= "{success:true,id:"+userId+"}";
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
		
		/**
		 * ������Ի���������Ӧ��Ŀ�������
		 * @Methods Name saveProjectRequireAnalyseForReq
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String saveProjectRequireAnalyseForReq(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String requireId= request.getParameter("reqId");
			SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, requireId);
			Map map = new HashMap();
			Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
			while(en.hasMoreElements()) {
				String key = (String)en.nextElement();
				if (key.equalsIgnoreCase("pagePanel")||
						key.equalsIgnoreCase("reqId")) {
					continue;
				}
				String value = request.getParameter(key);
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				map.put(key, value);
			}
			map.put("specialRequire",sr);
			SRAnalyse pra =(SRAnalyse) metaDataManager.saveEntityData(SRAnalyse.class, map);
			String json= "{success:true,id:"+pra.getId().toString()+"}";
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
		
		/**
		 * ������Ի���������Ӧ��Ŀ�������
		 * @Methods Name saveProjectPlanForReq
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		@SuppressWarnings("unchecked")
		public String saveProjectPlanForReq(){
			HttpServletRequest request = super.getRequest();
			String requireId= request.getParameter("reqId");
			SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, requireId);
			Map map = new HashMap();
			Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
			while(en.hasMoreElements()) {
				String key = (String)en.nextElement();
				if (key.equalsIgnoreCase("pagePanel")||
						key.equalsIgnoreCase("reqId")) {
					continue;
				}
				String value = request.getParameter(key);
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				map.put(key, value);
			}
			map.put("status",1);
			map.put("specialRequire",sr);
			metaDataManager.saveEntityData(SRProjectPlan.class, map);
			return null;
		}
		/**
		 * ɾ�����Ի���������Ӧ��Ŀ�������
		 * @Methods Name removeProjectPlan
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String removeProjectPlan(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String id = request.getParameter("id");
			metaDataManager.removeEntityData(SRProjectPlan.class, id);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println("{success:true}");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * ��ȡ�����Ӧ��Ŀ�ƻ�Id,Ϊ����ʦҳ���ṩ����
		 * @Methods Name getProjectPlanId
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String getProjectPlanId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRProjectPlan projectPlan = (SRProjectPlan)getService().findUnique(SRProjectPlan.class, "specialRequire", sr);
			String projectPlanId = "0";
			if(projectPlan!=null){
				projectPlanId = projectPlan.getId().toString();
			}
			String json= "{success:true,id:"+projectPlanId+"}";
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
		/**
		 * ��ȡ��Ŀ��Ӧ����Ŀ�ƻ�Id
		 * @Methods Name getRootProjectPlanId
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String getRootProjectPlanId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
//			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRProjectPlan projectPlan = projectPlanService.findRootProjectPlanByReq(dataId);
			String projectPlanId = "0";
			if(projectPlan!=null){
				projectPlanId = projectPlan.getId().toString();
			}
			String json= "{success:true,id:"+projectPlanId+"}";
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
		/**
		 * ��ȡ�����Ӧ��Ŀ�������Id,Ϊ����ʦҳ���ṩ����
		 * @Methods Name getProjectRequireAnalyseId
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String getProjectRequireAnalyseId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRAnalyse pra = (SRAnalyse)getService().findUnique(SRAnalyse.class, "specialRequire", sr);
			String praId = "0";
			if(pra!=null){
				praId = pra.getId().toString();
			}
			String json= "{success:true,id:"+praId+"}";
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
		
		/**
		 * ������Ի���������Ӧ�����ͬ
		 * @Methods Name saveRequirementContractForReq
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String saveRequirementContractForReq(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String requireId= request.getParameter("reqId");
			String param = request.getParameter("info");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, requireId);
			Map map = getMapFormPanelJson(param);
			String contractId="0";
			map.put("specialRequire",sr);
			SRprojectContracts pc = (SRprojectContracts)metaDataManager.saveEntityData(SRprojectContracts.class, map);
			String contractCode = "";
			if(pc!=null){
				  contractId=pc.getId().toString();
				  contractCode = pc.getContractCode(); //�ڵڶ������ڡ��ͻ�It���������������������ˣ������Ͱ󶨵�����
			}
		    String json = "{success:true,contractId:"+contractId+",contractCode:'"+contractCode+"'}";
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
		
		/**
		 * ��ȡ�����Ӧ��Ŀ��ͬID
		 * @Methods Name getRequirementContractId
		 * @Create In May 13, 2009 By gaowen
		 * @return String
		 */
		public String getRequirementContractId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRprojectContracts contract = (SRprojectContracts)super.getService().findUnique(SRprojectContracts.class, "specialRequire", sr);
			String contractId = "0";
			if(contract!=null){
				contractId = contract.getId().toString();
			}
			String json= "{success:true,id:"+contractId+"}";
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

		/**
		 * ��ȡ�����������ϢID
		 * @Methods Name getReqGroupFinanceInfoId
		 * @Create In May 14, 2009 By gaowen
		 * @return String
	    */
	
		public String getReqGroupFinanceInfoId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
  		    SRGroupFinanceInfo requirementGroupFinanceInfo = (SRGroupFinanceInfo)super.getService().findUnique(SRGroupFinanceInfo.class, "specialRequire", sr);
            String serviceItemId = "0";
			String serviceItemName = "";
			if(requirementGroupFinanceInfo!=null){
				serviceItemId = requirementGroupFinanceInfo.getId().toString();
				serviceItemName = requirementGroupFinanceInfo.getName();
			}
			String json= "{success:true,id:"+serviceItemId+",name:'"+serviceItemName+"'}";
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
		
		/**
		 * ���������������Ϣ
		 * @Methods Name saveForRequirementGroupFinanceInfo
		 * @Create In May 14, 2009 By gaowen
		 * @return String
	    */
		public String saveForRequirementGroupFinanceInfo(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("dataId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			Map map = new HashMap();
			Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
			while(en.hasMoreElements()) {
				String key = (String)en.nextElement();
				if (key.equalsIgnoreCase("pagePanel")||
						key.equalsIgnoreCase("reqId")) {
					continue;
				}
				String value = request.getParameter(key);
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				map.put(key, value);
			}
			map.put("specialRequire",sr);
			SRGroupFinanceInfo requirementGroupFinanceInfo = (SRGroupFinanceInfo) metaDataManager.saveEntityData(SRGroupFinanceInfo.class, map);
			String id = requirementGroupFinanceInfo.getId().toString();
			String json= "{success:true,id:"+id+"}";
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
		
		/**
		 * ��ö�Ӧ���乤��ʦId ��ȡ��壿
		 * @Methods Name getProjectTransmissionEngineerId
		 * @Create In May 14, 2009 By gaowen
		 * @return String
		 */
		public String getProjectTransmissionEngineerId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRTransmisEngineer pte = (SRTransmisEngineer)super.getService().findUnique(SRTransmisEngineer.class, "specialRequire", sr);
			String pteId = "0";
			if(pte!=null){
				pteId = pte.getId().toString();
			}
			String json= "{success:true,id:"+pteId+"}";
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

/*	
    remove by duxh in 2010-02-22 -----begin-----ע�����÷����������ı������
    
 	public String getReqServiceItemShip(){
			String serviceId = super.getRequest().getParameter("serviceId");
			ServiceItem serviceItem =(ServiceItem) super.getService().find(ServiceItem.class, serviceId);
			List<CIRelationShip> ciRelationShips = super.getService().find(CIRelationShip.class, "serviceItem", serviceItem);//���ܷ����û��ʲô��ϵ����ʱ��Ҫ���½�һ��
			//���佨��һ���¶��Ĺ�ϵ����ȻҪ�ж���û�й�ϵ��
			String serviceItemName = "";
			if(serviceItem!=null){
				serviceItemName = serviceItem.getName();
			}
			CIRelationShip ciRelationShip = new CIRelationShip();
			String json ="";//"{success:true,id:"+ciRelationShipId+"}";
			Long  ciRelationShipId = 0L;
			if(ciRelationShips.size()>0){
				for(CIRelationShip ciReShip :ciRelationShips){
					ciRelationShipId = ciReShip.getId();
					if(ciRelationShipId!=null){		
						break;
					}
				}
				 json="{success:true,id:"+ciRelationShipId+",name:'"+serviceItemName+"'}";
				
			}else{
				CIRelationShipPic ciRelationShipPic = ciRelationShipPic = new CIRelationShipPic();
				ciRelationShipPic.setName(serviceItem.getName()+"��ϵͼ");
				ciRelationShipPic.setBeginDate(serviceItem.getBeginDate());
				ciRelationShipPic.setEndDate(serviceItem.getEndDate());
				ciRelationShipPic.setDeleteFlag(Constants.STATUS_DRAFT);
				service.save(ciRelationShipPic);
				
				ciRelationShip.setParentRelationShip(null);
				ciRelationShip.setServiceItem(serviceItem);
				ciRelationShip.setTypeFlag(CIRelationShip.TYPE_SCID);
				ciRelationShip.setOrder(Integer.valueOf(0));
				ciRelationShip.setDeleteFlag(Constants.STATUS_DRAFT);
				service.save(ciRelationShip);
				
				CIRelationShipPicNode cRelationShipPicNode = new CIRelationShipPicNode();
				cRelationShipPicNode.setCiRelationShip(ciRelationShip);
				cRelationShipPicNode.setCiRelationShipPic(ciRelationShipPic);
				cRelationShipPicNode.setDeleteFlag(Constants.STATUS_DRAFT);
				service.save(cRelationShipPicNode);
				
				json="{success:true,id:"+ciRelationShip.getId()+",name:'"+serviceItemName+"'}";
			}
			HttpServletResponse response = super.getResponse();
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
		
		remove by duxh in 2010-02-22 -----end-----*/
		
		/**
		 * ��ȡ��Ŀ���߷�������ID
		 * @Methods Name getProjectNoticeId
		 * @Create In May 16, 2009 By gaowen
		 * @return String
		 */
		public String getProjectNoticeId(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRProjectNotice projectNotice= (SRProjectNotice)super.getService().findUnique(SRProjectNotice.class, "specialRequire", sr);
			NewNotice notice = null;
			if(projectNotice!=null){
				notice = projectNotice.getNotice();
			}
			String noticeId = "0";
			if(notice!=null){
				noticeId = notice.getId().toString();
			}
			String json= "{success:true,id:"+noticeId+"}";
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

		/**
		 * ��ѯ���мƻ����� ��������Ӽ� ������Ϊ100%�ͰѸ�Ҳ��Ϊ100%
		 * @Methods Name findAllProjectPlan
		 * @Create In May 16, 2009 By gaowen
		 * @return String
		 */
		@SuppressWarnings("unchecked")
		public String findAllProjectPlan(){
			String dataId = super.getRequest().getParameter("dataId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			List<SRProjectPlan> list = super.getService().find(SRProjectPlan.class, "specialRequire", sr);
			boolean flag = true;
			ProjectPlanProgress p = null;
			for(int i=0;i<list.size();i++){
				SRProjectPlan projectPlan = list.get(i);
				if(projectPlan.getParentPlan()!=null){
					ProjectPlanProgress progress = projectPlan.getProgress();
					if(progress!=null){
						String name = progress.getName();
						if(!"100%".equals(name)){
							flag = false;
						}else{
							p = progress;
						}
					}
				}
			}
			if(flag==true){
				if(list.size()>1){
					for(int i=0;i<list.size();i++){
						SRProjectPlan projectPlan = list.get(i);
						if(projectPlan.getParentPlan()==null){
							projectPlan.setProgress(p);
							String name="";
							if(p!=null){
								name = p.getName();
							}
							super.getService().save(projectPlan);
							HttpServletResponse response = super.getResponse();
							response.setContentType("text/plain");
							response.setCharacterEncoding("UTF-8");
							PrintWriter out;
							try {
								out = response.getWriter();
								out.println("{success:true,name:'"+name+"'}");
								out.flush();
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			return null;
		}
		/**
		 * ��ѯ��Ŀ�ƻ����� ���Ϊ100%�Ͳ����ٱ���
		 * projectRenderList.js
		 * @Methods Name findProjectPlanForReq
		 * @Create In May 16, 2009 By gaowen
		 * @return String
		 */
		public String findProjectPlanForReq(){
			String dataId = super.getRequest().getParameter("dataId");
			SRProjectPlan projectPlan = (SRProjectPlan) super.getService().find(SRProjectPlan.class, dataId);
			ProjectPlanProgress progress = projectPlan.getProgress();
			String name ="";
			if(progress!=null){
				name = progress.getName();
			}
			HttpServletResponse response = super.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println("{success:true,name:'"+name+"'}");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * ���汨�����ѵ�ǰ������Ϊ��Ŀ����
		 * @Methods Name saveProjectWorkReport
		 * @Create In May 19, 2009 By gaowen
		 * @return String
		 */
		@SuppressWarnings("unchecked")
		public String saveProjectWorkReport(){
			HttpServletRequest request = super.getRequest();
			Map map = new HashMap();
			String dataId = request.getParameter("reqId");
			SRProjectPlan projectPlan = (SRProjectPlan) super.getService().find(SRProjectPlan.class, dataId);
			Enumeration en = request.getParameterNames();//HttpUtil.requestParam2Map(request);
			while(en.hasMoreElements()) {
				String key = (String)en.nextElement();
				if (key.equalsIgnoreCase("pagePanel")||
						key.equalsIgnoreCase("reqId")) {
					continue;
				}
				String value = request.getParameter(key);
				key = StringUtils.substringAfter(key, "$");
				if(key.equalsIgnoreCase("progress")&&!"".equals(value)){
					ProjectPlanProgress progress = (ProjectPlanProgress) super.getService().find(ProjectPlanProgress.class, value);
					String name = progress.getName();
					projectPlan.setProgress(progress);
					super.getService().save(projectPlan);
					HttpServletResponse response = super.getResponse();
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out;
					try {
						out = response.getWriter();
						out.println("{success:true,name:'"+name+"'}");
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				value = value.trim();
				map.put(key, value);
			}
			map.put("projectPlan", dataId);
			metaDataManager.saveEntityData(ProjectWorkReport.class, map);
			return null;
		}
		/**
		 * ɾ������
		 * @Methods Name removeProjectWorkReport
		 * @Create In Mar 19, 2009 By lee
		 * @return String
		 */
		public String removeProjectWorkReport(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String id = request.getParameter("id");
			metaDataManager.removeEntityData(ProjectWorkReport.class, id);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println("success:true");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * ���������Ӧ��Ŀ��������
		 * @Methods Name saveNoticeForReq
		 * @Create In Mar 26, 2009 By lee
		 * @return String
		 */
		public String saveNoticeForReq(){
			HttpServletRequest request = super.getRequest();
			String reqId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, reqId);
			String auditflag = super.getRequest().getParameter("auditflag");
			String dataId = super.getRequest().getParameter("dataId");
			String title = super.getRequest().getParameter("title");
			String remark = super.getRequest().getParameter("remark");
			String beginDate = super.getRequest().getParameter("beginDate");
			String endDate = super.getRequest().getParameter("endDate");
			String content = super.getRequest().getParameter("content");
			//add by lee for ��¼�������� in 20091102 begin 
			String noticeType = super.getRequest().getParameter("noticeType");
			NewNoticeType newNoticeType = null;
			if(!"".equals(noticeType)){
				newNoticeType= (NewNoticeType) super.getService().find(NewNoticeType.class, noticeType);
			}
			//add by lee for ��¼�������� in 20091102 end
			UserInfo userInfo = UserContext.getUserInfo();
			SimpleDateFormat   formatDate  =   new   SimpleDateFormat("yyyy-MM-dd");  
			Date date = null;
			Date enddate = null;
			try {
				date = formatDate.parse(beginDate);
				enddate = formatDate.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			Date createDate = new Date();
//			String newDataId = "";
			NewNotice notice = null;
			if(dataId=="0"||dataId==null||dataId==""||"0".equals(dataId)){
				NewNotice newNotice = new NewNotice();
				newNotice.setTitle(title);
				newNotice.setRemark(remark);
				newNotice.setBeginDate(date);
				newNotice.setCreateDate(createDate);
				newNotice.setCreateUser(userInfo);
				newNotice.setEndDate(enddate);
				newNotice.setContent(content);
				newNotice.setNewNoticeType(newNoticeType);//add by lee for ��¼�������� in 20091102
				newNotice.setAuditflag(Integer.valueOf(auditflag));
				notice=(NewNotice) super.getService().save(newNotice);
//				newDataId = notice.getId().toString();
			}else{
				NewNotice newNotice = (NewNotice) super.getService().find(NewNotice.class, dataId);
				newNotice.setTitle(title);
				newNotice.setRemark(remark);
				newNotice.setBeginDate(date);
				newNotice.setCreateDate(createDate);
				newNotice.setCreateUser(userInfo);
				newNotice.setEndDate(enddate);
				newNotice.setContent(content);
				newNotice.setNewNoticeType(newNoticeType);//add by lee for ��¼�������� in 20091102
				newNotice.setAuditflag(Integer.valueOf(auditflag));
				notice=(NewNotice) super.getService().save(newNotice);
//				newDataId = dataId;
			}
			if(StringUtils.isBlank(dataId)||"0".equals(dataId)){
				SRProjectNotice projectNotice = new SRProjectNotice();
				projectNotice.setNotice(notice);
				projectNotice.setSpecialRequire(sr);
				getService().save(projectNotice);
			}
			String json ="{success:true,newNoticeId:"+notice.getId()+",newNoticeName:'"+notice.getName()+"',newNoticeType:'"+notice.getNewNoticeType().getName()+"'}";
			HttpServletResponse response = super.getResponse();
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
		
		public String getTEngineerId(){

			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("reqId");
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, dataId);
			SRTransmisEngineer projectTransmissionEngineer= (SRTransmisEngineer)super.getService().findUnique(SRTransmisEngineer.class, "specialRequire", sr);
			String userId = "0";
			String yesOrNoId="";
			if(projectTransmissionEngineer!=null){
				if(projectTransmissionEngineer.getUserInfo()!=null){
					userId = projectTransmissionEngineer.getUserInfo().getId().toString();
				}
				yesOrNoId = projectTransmissionEngineer.getNeedOrNot().toString();
			}
			String json= "{success:true,userId:"+userId+","+"yesOrNoId:"+yesOrNoId+"}";
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
		/**
		 * ���洫�乤��ʦ
		 * @Methods Name saveTEengineerForReq
		 * @Create In Apr 13, 2009 By Allen
		 * @return String
		 */
		public String saveTEengineerForReq(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String tempId = request.getParameter("id");
			String requireId= request.getParameter("reqId");
			String need = request.getParameter("need");
			String userInfoId = request.getParameter("userId");
			
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, requireId);
			
			SRTransmisEngineer tempEngineer = null;
			if(StringUtils.isNotBlank(tempId)){
				tempEngineer=(SRTransmisEngineer) service.find(SRTransmisEngineer.class, tempId);
			}else{
				tempEngineer=new SRTransmisEngineer();
			}
			
			UserInfo engineer = null;
			if(StringUtils.isNotBlank(userInfoId)){
				engineer=(UserInfo) service.find(UserInfo.class, userInfoId);
			}
			tempEngineer.setSpecialRequire(sr);
			tempEngineer.setNeedOrNot(Integer.valueOf(need));
			tempEngineer.setUserInfo(engineer);
			tempEngineer=(SRTransmisEngineer) service.save(tempEngineer);
			String id = tempEngineer.getId().toString();
			String json= "{success:true,id:"+id+"}";
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
		/**
		 * ������Ի���������ķ���������Ŷ���Ϣ
		 * @Methods Name saveServiceItemAndProvider
		 * @Create In Jun 10, 2009 By lee
		 * @return String
		 */
		public String saveServiceItemAndProvider(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse(); 
			String requireId= request.getParameter("reqId");
			String serviceItemInfo = request.getParameter("serviceItemInfo");
			String serviceProvider = request.getParameter("serviceProvider");
			
			JSONObject serviceItemInfoJO = JSONObject.fromObject(serviceItemInfo);
			JSONObject serviceProviderJO = JSONObject.fromObject(serviceProvider);
			
			/////////////////////////////////////����������/////////////////////////////////////////
			Map serviceItemDataMap = new HashMap();
			Iterator serviceItemInfoIter = serviceItemInfoJO.keys();
			while(serviceItemInfoIter.hasNext()) {
			
			String columnName = (String) serviceItemInfoIter.next();
			String columnData = serviceItemInfoJO.getString(columnName);
			
			columnName = StringUtils.substringAfter(columnName, "$");
			serviceItemDataMap.put(columnName, columnData);
			
			}
			ServiceItem serviceItem =(ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, serviceItemDataMap);
			String serviceItemId = serviceItem.getId().toString();
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, requireId);
			
			SRServiceItem srServiceItem = null;
			List list = getService().find(SRServiceItem.class, "serviceItem", serviceItem);
			if(!(list.size()>0)){
				srServiceItem = new SRServiceItem();
				srServiceItem.setSpecialRequire(sr);
				srServiceItem.setServiceItem(serviceItem);
				srServiceItem=(SRServiceItem) getService().save(srServiceItem);
			}
			/////////////////////////////////////���潻���ŶӲ���///////////////////////////////////
			Map providerDataMap = new HashMap();
			Iterator serviceProviderIter = serviceProviderJO.keys();
			while(serviceProviderIter.hasNext()) {
			
			String columnName = (String) serviceProviderIter.next();
			String columnData = serviceItemInfoJO.getString(columnName);
			
			columnName = StringUtils.substringAfter(columnName, "$");
			providerDataMap.put(columnName, columnData);
			
			}
			providerDataMap.put("specialRequire", sr);
			SRServiceProvider srServiceProvider=(SRServiceProvider) metaDataManager.saveEntityData(SRServiceProvider.class, providerDataMap);
			String praId = srServiceProvider.getId().toString();
			String userId = "";
			String userName = "";
			String serviceProviderId =srServiceProvider.getDeliveryTeam().getId().toString();
			DeliveryTeam serviceProviderIn = (DeliveryTeam) getService().find(DeliveryTeam.class, serviceProviderId);
		    userId = serviceProviderIn.getPrincipal().getId().toString();
		    userName = serviceProviderIn.getPrincipal().getUserName();
		    /////////////////////////////��ǰ̨��������//////////////////////////////
			String json= "{success:true,serviceItemId:"+serviceItemId+",srServiceProviderId:"+praId+",officerId:"+userId+",officerName:'"+userName+"'}";
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
		
		
		
		
		
		
		/**
		 * ������Ի���������ķ�����
		 * @Methods Name saveServiceItem
		 * @Create In Jun 25, 2009 By gaowen
		 * @return String
		 */
		public String saveServiceItem(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse(); 
			String requireId= request.getParameter("reqId");
			String serviceItemInfo = request.getParameter("serviceItemInfo");
			
			
			JSONObject serviceItemInfoJO = JSONObject.fromObject(serviceItemInfo);
		
			
			/////////////////////////////////////����������/////////////////////////////////////////
			Map serviceItemDataMap = new HashMap();
			Iterator serviceItemInfoIter = serviceItemInfoJO.keys();
			while(serviceItemInfoIter.hasNext()) {
			
			String columnName = (String) serviceItemInfoIter.next();
			String columnData = serviceItemInfoJO.getString(columnName);
			
			columnName = StringUtils.substringAfter(columnName, "$");
			serviceItemDataMap.put(columnName, columnData);
			
			}
			ServiceItem serviceItem =(ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, serviceItemDataMap);
			String serviceItemId = serviceItem.getId().toString();
			SpecialRequirement sr=(SpecialRequirement)super.getService().find(SpecialRequirement.class, requireId);
			
			SRServiceItem srServiceItem = null;
			List list = getService().find(SRServiceItem.class, "serviceItem", serviceItem);
			if(!(list.size()>0)){
				srServiceItem = new SRServiceItem();
				srServiceItem.setSpecialRequire(sr);
				srServiceItem.setServiceItem(serviceItem);
				srServiceItem=(SRServiceItem) getService().save(srServiceItem);
			}
			
		    /////////////////////////////��ǰ̨��������//////////////////////////////
			String json= "{success:true,serviceItemId:"+serviceItemId+"}";
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
	/**
	 * ��ȡ��������פ�������Ĺ�����������Ϣ
	 * @Methods Name findServerManageCiData
	 * @Create In Jul 31, 2009 By lee
	 * @return String
	 */
	public String findServerManageCiData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String json = "{success:true,ciId:0,serverId:0}";
		ServerManage serverManage = null;
		if(StringUtils.isNotBlank(dataId)){
			serverManage = (ServerManage) service.find(ServerManage.class, dataId,true);
		}
		if(serverManage!=null){
			ConfigItem configItem = serverManage.getConfigItem();
			if(configItem!=null){
				ConfigItemExtendInfo ciextendInfo = (ConfigItemExtendInfo) service.findUnique(ConfigItemExtendInfo.class, "configItem", configItem);
				Long serverId = ciextendInfo.getExtendDataId();
				json = "{success:true,ciId:"+configItem.getId()+",serverId:"+serverId+"}";
			}
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
	/**
	 * ͨ��������������ID��ȡ��Ϣ
	 * @Methods Name findServerCiDataByCiId
	 * @Create In Aug 2, 2009 By lee
	 * @return String
	 */
	public String findServerCiDataByCiId(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String json = "{success:true,ciId:0,serverId:0}";
		ConfigItem configItem = (ConfigItem) service.find(ConfigItem.class, dataId, true);
		if(configItem!=null){
			ConfigItemExtendInfo ciextendInfo = (ConfigItemExtendInfo) service.findUnique(ConfigItemExtendInfo.class, "configItem", configItem);
			Long serverId = ciextendInfo.getExtendDataId();
			json = "{success:true,ciId:"+dataId+",serverId:"+serverId+"}";
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
	/**
	 * Ϊ��������פ�����������ɷ�������������
	 * @Methods Name initServerConfigItem
	 * @Create In Jul 31, 2009 By lee
	 * @return String
	 */
	public String initServerConfigItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		ServerManage serverManage = (ServerManage) service.find(ServerManage.class, dataId, true);
		ServerManageInfoOne one = (ServerManageInfoOne) service.findUnique(ServerManageInfoOne.class, "serverManage", serverManage);
//		ServerManageInfoTwo two = (ServerManageInfoTwo) service.findUnique(ServerManageInfoTwo.class, "serverManage", serverManage);
		ConfigItem configItem = serverManage.getConfigItem();		//��ȡ�������������
		SystemMainTable serverTable = systemMainTableService.findSystemMainTableByClazz(Server.class);
		String serverClassName = serverTable.getClassName();
		ConfigItemType cofigItemType = (ConfigItemType) service.findUnique(ConfigItemType.class, "className", serverClassName);//��ʼ������������
		
		if(configItem==null){
			configItem = new ConfigItem();	//�����µ�������
			Server server = new Server();				//�����µķ������������������������Ϣ��
			configItem.setConfigItemType(cofigItemType);//����������Ϊ���������
			configItem.setName(serverManage.getServerName());	//��ʼ������������
			server.setName(serverManage.getServerName());		//��ʼ������������
			server.setModel(serverManage.getServerModel());		//��ʼ���������ͺ�
			server.setCpuType(serverManage.getServerCpu());		//��ʼ��������CPU�ͺ�
			server.setRam(serverManage.getServerMomery());		//��ʼ���������ڴ�
			server.setInternalDriver(serverManage.getServerDisk());//��ʼ��������Ӳ��
			server.setIpAddress1(one.getIpAddress());			//��ʼ��������IP
			server.setCreateUser(UserContext.getUserInfo());	//��ʼ��������
			//server.setCreateDate(DateUtil.getCurrentDate());	//��ʼ������ʱ��
			Map data = metaDataManager.getEntityDataForEdit(server);
			Server newServer = (Server) metaDataManager.saveEntityData(Server.class, data);	//�������������ʵ��(���ñ���������ı�������ô˷���)
			Long newServerId = newServer.getId();
			String cisn = newServer.getCisn();		//��ȡ������������
			configItem.setCisn(cisn);				//���������Ÿ���������ʵ��
			ConfigItem newCi = (ConfigItem) service.save(configItem);	//����������
			//�������������������ϵ
			ConfigItemExtendInfo ciExtendInfo = new ConfigItemExtendInfo();	
			ciExtendInfo.setConfigItem(newCi);
			ciExtendInfo.setExtendDataId(newServerId);
			
			service.save(ciExtendInfo);	
			//�������������������ϵ
			//�����������פ�������������ϵ
			serverManage.setConfigItem(configItem);
			service.save(serverManage);
		}else{
			ConfigItemExtendInfo ciExtendInfo = (ConfigItemExtendInfo) service.findUnique(ConfigItemExtendInfo.class, "configItem", configItem);	//��ȡ�����������ʵ���ϵ
			Long serverId = ciExtendInfo.getExtendDataId();
			Server server = (Server) service.find(Server.class, serverId.toString(), true);	//��ȡ������ʵ��
//			String lalasfslal = Server.class.toString();
			configItem.setConfigItemType(cofigItemType);//����������Ϊ���������
			configItem.setName(serverManage.getServerName());	//��ʼ������������
			server.setName(serverManage.getServerName());		//��ʼ������������
			server.setModel(serverManage.getServerModel());		//��ʼ���������ͺ�
			server.setCpuType(serverManage.getServerCpu());		//��ʼ��������CPU�ͺ�
			server.setRam(serverManage.getServerMomery());		//��ʼ���������ڴ�
			server.setInternalDriver(serverManage.getServerDisk());//��ʼ��������Ӳ��
			server.setIpAddress1(one.getIpAddress());			//��ʼ��������IP
			server.setModifyUser(UserContext.getUserInfo());	//��ʼ���޸���
			//server.setModifyDate(DateUtil.getCurrentDate());	//��ʼ���޸�ʱ��
			Map data = metaDataManager.getEntityDataForEdit(server);
			Server newServer = (Server) metaDataManager.saveEntityData(Server.class, data);	//�������������ʵ��(���ñ���������ı�������ô˷���)
//			Long newServerId = newServer.getId();
			String cisn = newServer.getCisn();		//��ȡ������������
			configItem.setCisn(cisn);				//���������Ÿ���������ʵ��
			service.save(configItem);				//����������
		}
		String json = "{success:true}";
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
	/**
	 * ������������������ʵ��
	 * @Methods Name saveServerConfigItem
	 * @Create In Aug 1, 2009 By lee
	 * @return String
	 */
	public String saveServerConfigItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
//		String ciId = request.getParameter("dataId");
		String configItemInfo = request.getParameter("configItemInfo");
//		String serverInfo = request.getParameter("serverInfo");
		//����������
		JSONArray cijsonArray = JSONArray.fromObject(configItemInfo);   
		Object[] ciDataArrays = cijsonArray.toArray();
		for(int i=0; i<ciDataArrays.length; i++){
			JSONObject record = (JSONObject) ciDataArrays[i];
			Map<String,Object> ciDataMap = new HashMap<String,Object>();
			Iterator columnIter = record.keys();
			while(columnIter.hasNext()){
				String columnName = (String) columnIter.next();
				
				String columnValue = record.getString(columnName);
				columnName = StringUtils.substringAfter(columnName, "$");
				columnValue = columnValue.trim();
				ciDataMap.put(columnName, columnValue); //��ʱ���ֶ������Ǵ��˱���ǰ׺��
			}
			metaDataManager.saveEntityData(ConfigItem.class, ciDataMap);
		}
		//�������������ʵ��
		JSONArray serverJsonArray = JSONArray.fromObject(configItemInfo);   
		Object[] serverDataArrays = serverJsonArray.toArray();
		for(int i=0; i<ciDataArrays.length; i++){
			JSONObject record = (JSONObject) serverDataArrays[i];
			Map<String,Object> serverDataMap = new HashMap<String,Object>();
			Iterator columnIter = record.keys();
			while(columnIter.hasNext()){
				String columnName = (String) columnIter.next();
				
				String columnValue = record.getString(columnName);
				columnName = StringUtils.substringAfter(columnName, "$");
				columnValue = columnValue.trim();
				serverDataMap.put(columnName, columnValue); //��ʱ���ֶ������Ǵ��˱���ǰ׺��
			}
			metaDataManager.saveEntityData(Server.class, serverDataMap);
		}
		String json = "{success:true}";
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
	
	public String getTestReportFileList(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("dataId");
		String columnid =request.getParameter("columnid");
		SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, reqId);
		SRTestReportFile file = (SRTestReportFile) getService().findUnique(SRTestReportFile.class, "specialRequire", sr);
		String fileStr = "";
		if(file==null){
			SRTestReportFile temp = new SRTestReportFile();
			temp.setSpecialRequire(sr);
			fileStr = new Date().getTime()+"";
			temp.setAttachment(fileStr);
			super.getService().save(temp);
		}else{
			fileStr = file.getAttachment(); 
		}
		String json = "";
		 List<SystemFile> systemfileList=service.find(SystemFile.class, "nowtime",fileStr);
		 String filesPathString="";
		    int k=1;
		    for(SystemFile filesTemp:systemfileList){
		    	filesPathString += 
		    		"<span id =su_"+filesTemp.getId()+"><img src='+webContext+'/images/other/file.gif >" 
		    	    + "&nbsp;<a href='+webContext+'"
					+ "/fileDown.do?methodCall=downloadFile&id="
					+ filesTemp.getId()+"&columnId="+ columnid+">"  + filesTemp.getFileName()
					+ "</a>&nbsp;&nbsp;"
		    	    +"<img src='+webContext+'/images/other/suremove.gif onClick=getRemoveFile(\""
				    + filesTemp.getId()+"\",\"com.zsgj.info.appframework.metadata.entity.SystemFile\")" 
				    +" alt=\"ɾ������\" style=\"cursor:hand;margin:-1 0\">&nbsp;&nbsp;&nbsp;&nbsp;";
			    	  if(k%4==0){
			    		 filesPathString=filesPathString+"<br></span>";  
			          }else{
			        	 filesPathString=filesPathString+"</span>";
			          }
		    	    k++;
			}
		  json="{file:'"+filesPathString+"'}";
		  try {
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();
				pw.write(json);
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	/**
	 * ��ȡ������ǩ��
	 * @Methods Name getCadreBizAudit
	 * @Create In Nov 12, 2009 By lee
	 * @return String
	 */
	public String getCadreBizAudit(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String flatId = request.getParameter("flatId");
		RequireApplyDefaultAudit rada = (RequireApplyDefaultAudit) service.find(RequireApplyDefaultAudit.class, flatId);
		UserInfo auditUser = rada.getCadreBizAudit();
		String json="{success:true,auditUser:'"+auditUser.getRealName()+"/"+auditUser.getUserName()+"'}";
		  try {
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();
				pw.write(json);
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	/**
	 * ��ȡָ�����͵�Ӧ��ϵͳ����������
	 * @Methods Name getAppConfigItemComboData
	 * @Create In Nov 25, 2009 By lee
	 * @return String
	 */
	public String getAppConfigItemComboData(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		String appName = request.getParameter("name");
		String appTypeIdStr = request.getParameter("appTypeId");
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start / pageSize + 1;
		
		String json = "";
		if(StringUtils.isNotBlank(id)){
			ConfigItem ci = (ConfigItem) service.find(ConfigItem.class, id);
			json +="{id:" + ci.getId() + ",name:'" + ci.getName() + "'}";
			json = "{success: true, rowCount:1,data:[" + json + "]}";
		}else{
			Long appTypeId = Long.valueOf(appTypeIdStr);
			Page page = srs.findAppConfigItem(appName, appTypeId, pageNo, pageSize);
			
			
			List<ConfigItem> appCis = page.list();
			for(ConfigItem ci : appCis){
				json +="{id:" + ci.getId() + ",name:'" + ci.getName() + "'},";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true, rowCount:"+page.getTotalCount()+",data:[" + json + "]}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ���涨����������Ϣ
	 * @Methods Name saveSpecialRequirementInfo
	 * @Create In Nov 30, 2009 By lee
	 * @return String
	 */
	public String saveSpecialRequirementInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String requireId= request.getParameter("reqId");
		String param = request.getParameter("info");
		SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, requireId);
		Map map = getMapFormPanelJson(param);
		
		map.put("specialRequire",sr);
		SpecialRequirementInfo srInfo =(SpecialRequirementInfo) metaDataManager.saveEntityData(SpecialRequirementInfo.class, map);
		String json= "{success:true,id:"+srInfo.getId().toString()+"}";
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
	/**
	 * ���°󶨱��ڵ�������
	 * @Methods Name reAssignAudit
	 * @Create In 2009 12 30 By zhangzy
	 * @return String
	 */
	public String reAssignAudit(){
		HttpServletRequest request = super.getRequest();
//		HttpServletResponse response = super.getResponse();
		
		String taskId = request.getParameter("taskId");			
		UserInfo ui = UserContext.getUserInfo();
		String actorId = ui.getUserName();
		TaskService ts =new  TaskServiceImpl ();
		
		ts.reAssign(Long.parseLong(taskId), actorId);
		return null;		
	}

	/**
	 * ɾ��������ϸ��Ϣ
	 * @Methods Name removeRequireFactoryInfo
	 * @Create In 2010 03 25 By zhangzy
	 * @return String
	 */
	public String removeRequireFactoryInfo() {
		String removedIds = super.getRequest().getParameter("removedIds");
		String[] removedId = StringUtils.split(removedIds,",");
		for(int i=0;i<removedId.length;i++){
			if(!"null".equals(removedId[i])){
				super.getService().remove(RequireFactoryInfo.class,removedId[i]);
			}
		}
		return null;
	}
	/**
	 * ����ERP����ʦ������Ϣ�����빤����ϸ����
	 * @Methods Name saveErpEngineerFeedbackAndGridInfo
	 * @Create In 25 03, 2010 By zhangzy
	 * @return String
	 */
	public String saveErpEngineerFeedbackAndGridInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		String param = request.getParameter("info");
		String product = request.getParameter("product");
		ERP_NormalNeed sr=(ERP_NormalNeed)super.getService().find(ERP_NormalNeed.class, reqId);
		Map map = getMapFormPanelJson(param);
		map.put("erpNeed", sr);
		ErpEngineerFeedback sp =(ErpEngineerFeedback) metaDataManager.saveEntityData(ErpEngineerFeedback.class, map);

//		String tempProduct = "";
//		if (!"".equals(product) ) {
//			byte[] bt1;
//			try {
//				bt1 = product.getBytes("ISO8859_1");
//				tempProduct = new String(bt1,"GBK");//��GBK���н��룬����1���µ��ַ���  
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}//��ISO8859_1���б���			
//		}		
		JSONArray ja = JSONArray.fromObject("[" + product + "]");
//		RequireFactoryInfo rfi = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap productMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);

//				if(("flm_ProjectTestReport$id").equals(key)&&value!="null"){
//					rfi = (RequireFactoryInfo) super.getService().find(RequireFactoryInfo.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				productMap.put(key, value);				
			}

			productMap.put("requireData",sr);
			metaDataManager.saveEntityData(RequireFactoryInfo.class, productMap);
		}				
		
		String json= "{success:true,id:"+sp.getId()+"}";
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
	/**
	 * �������빤����ϸ����
	 * @Methods Name saveGridInfo
	 * @Create In 25 03, 2010 By zhangzy
	 * @return String
	 */
	public String saveGridInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("reqId");
		ERP_NormalNeed sr=(ERP_NormalNeed)super.getService().find(ERP_NormalNeed.class, reqId);

		String product = request.getParameter("product");
		JSONArray ja = JSONArray.fromObject("[" + product + "]");
//		RequireFactoryInfo rfi = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap productMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);

//				if(("flm_ProjectTestReport$id").equals(key)&&value!="null"){
//					rfi = (RequireFactoryInfo) super.getService().find(RequireFactoryInfo.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				productMap.put(key, value);				
			}

			productMap.put("requireData",sr);
			metaDataManager.saveEntityData(RequireFactoryInfo.class, productMap);
		}				
		
		String json= "{success:true,id:"+reqId+"}";
		response.setContentType("text/html");
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
		
	private Map getMapFormPanelJson(String param){
		JSONObject basicJson=JSONObject.fromObject(param);
		Set<String> basicSet=basicJson.keySet();
		Map map = new HashMap();
		for (String key:basicSet) {
			String keyString = StringUtils.substringAfter(key, "$");
			String value = basicJson.getString(key);
			map.put(keyString, value);
		}
		return map;
	}
}
