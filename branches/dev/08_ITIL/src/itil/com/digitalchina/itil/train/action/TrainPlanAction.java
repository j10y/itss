package com.digitalchina.itil.train.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.digitalchina.info.framework.util.BeanUtil;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import sun.print.resources.serviceui;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.digitalchina.info.appframework.extjs.servlet.CoderForList;
import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventProblem;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.train.entity.AnswerNo;
import com.digitalchina.itil.train.entity.Quest;
import com.digitalchina.itil.train.entity.QuestOption;
import com.digitalchina.itil.train.entity.QuestResult;
import com.digitalchina.itil.train.entity.QuestType;
import com.digitalchina.itil.train.entity.Survey;
import com.digitalchina.itil.train.entity.SurveyType;
import com.digitalchina.itil.train.entity.TrainCourse;
import com.digitalchina.itil.train.entity.TrainCourseSignup;
import com.digitalchina.itil.train.entity.TrainCourseSurvey;
import com.digitalchina.itil.train.service.TrainPlanService;

public class TrainPlanAction extends BaseAction{
	private TrainPlanService trainPlanService = (TrainPlanService) getBean("trainPlanService");
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");
	public String remove() throws Exception {
		String scIds = super.getRequest().getParameter("dataId");
		String clazz = super.getRequest().getParameter("clazz");
		trainPlanService.removeTrain(clazz,scIds);
		return null;
	}
	public String removeSurveyType() throws Exception{
		String scId = super.getRequest().getParameter("scId");
		SurveyType surveyType = (SurveyType) super.getService().find(SurveyType.class, scId);
		super.getService().remove(surveyType);
		return null;
	}
	public String saveCourseAndSurvey() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String cId = request.getParameter("cId");
		String dataId = request.getParameter("dataId");
		Map map = new HashMap();
		Enumeration en = ((ServletRequest) request).getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			if (key.equalsIgnoreCase("name")||key.equalsIgnoreCase("cId")||key.equalsIgnoreCase("dataId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		TrainCourse trainCourse = (TrainCourse) metaDataManager.saveEntityData(TrainCourse.class, map);
		Survey survey = (Survey) getService().find(Survey.class, cId);
		//String dataId = trainCourse.getId().toString();
		TrainCourseSurvey trainCourseSurvey = null;
		if(dataId!=""){
			trainCourseSurvey = trainPlanService.findCourseSurveyByCourse2(trainCourse);
		}else{
			trainCourseSurvey = new TrainCourseSurvey();
		}
		trainCourseSurvey.setTrainCourse(trainCourse);
		trainCourseSurvey.setSurvey(survey);
		super.getService().save(trainCourseSurvey);
		return null;
	}
	public String saveSurveyType() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		Map map = new HashMap();
		Enumeration en = ((ServletRequest) request).getParameterNames();//HttpUtil.requestParam2Map(request);
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		metaDataManager.saveEntityData(SurveyType.class, map);
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
	public String newSave() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deployFlag="";
		String surveyTypeId="";
		String info=request.getParameter("info");
		 HashMap myinfoMap =new HashMap();   
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String [] keyString=key.split("\\$");
				String value=jo.getString(key);
				if(keyString[1].equals("deployFlag")){
					deployFlag=value;
				}
				if(keyString[1].equals("surveyType")){
					surveyTypeId=value;
				}
				myinfoMap.put(keyString[1], value);	
			}
		String json="";
		if(deployFlag.equals("1")){
		SurveyType surveyType = (SurveyType) getService().find(SurveyType.class,surveyTypeId);
		List<Survey> surveyList = getService().find(Survey.class, "surveyType", surveyType);
		
		for(int i = 0;i<surveyList.size();i++){
			Survey  oSurvey = surveyList.get(i);
			String dFlag = oSurvey.getDeployFlag().toString();
			if(dFlag.equals("1")){
				json+="100";
				try {			
					super.getResponse().setCharacterEncoding("utf-8");
					PrintWriter pw = super.getResponse().getWriter();	
					pw.write(json);		
					} catch (IOException e) {
					e.printStackTrace();
					}
				return null;
			}
		}
		}
//		
//		
//		TrainCourse trainCourse = trainPlanService.findTrainCourseById(courseName);
//		List<TrainCourseSurvey> trainCourseSurveyList = trainPlanService.findCourseSurveyByCourse(trainCourse);
//		//Survey surveys = null;
//		//String failure = "{failure : }";
//		Iterator<TrainCourseSurvey> it = trainCourseSurveyList.iterator();
//		while(it.hasNext()){
//			TrainCourseSurvey trainCourseSurvey = it.next();
//			if(deployFlag.equals("1") &&trainCourseSurvey.getSurvey()!=null&& trainCourseSurvey.getSurvey().getDeployFlag()==1){
//				json+="100";
//				try {			
//					super.getResponse().setCharacterEncoding("utf-8");
//					PrintWriter pw = super.getResponse().getWriter();	
//					pw.write(json);		
//					} catch (IOException e) {
//					e.printStackTrace();
//					}
//				return null;
//			}
//		}
		Survey survey = (Survey) metaDataManager.saveEntityData(Survey.class, myinfoMap);
		String id = survey.getId().toString();
		json = "{success:true,id:"+id+"}";
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
	public String save() throws Exception {
		String json="";
		String dataId = super.getRequest().getParameter("dataId");
		String id = super.getRequest().getParameter("dataId");
		String surveyName = super.getRequest().getParameter("surveyName");
		String deployFlag = super.getRequest().getParameter("deployFlag");
		String courseName = super.getRequest().getParameter("name");
		String surveyTypes = super.getRequest().getParameter("name2");
		String beginDate = super.getRequest().getParameter("beginDate");
		String endDate = super.getRequest().getParameter("endDate");
		SimpleDateFormat   formatDate  =   new   SimpleDateFormat("yyyy-MM-dd");  
		Date   date   =   formatDate.parse(beginDate);  
		Date enddate = formatDate.parse(endDate);
		SurveyType surveyType = (SurveyType) super.getService().find(SurveyType.class, surveyTypes);
		TrainCourse trainCourse = trainPlanService.findTrainCourseById(courseName);
		List<TrainCourseSurvey> trainCourseSurveyList = trainPlanService.findCourseSurveyByCourse(trainCourse);
		//Survey surveys = null;
		//String failure = "{failure : }";
		Iterator<TrainCourseSurvey> it = trainCourseSurveyList.iterator();
		while(it.hasNext()){
			TrainCourseSurvey trainCourseSurvey = it.next();
			if(deployFlag.equals("1") &&trainCourseSurvey.getSurvey()!=null&& trainCourseSurvey.getSurvey().getDeployFlag()==1){
				json+="100";
				try {			
					super.getResponse().setCharacterEncoding("utf-8");
					PrintWriter pw = super.getResponse().getWriter();	
					pw.write(json);		
					} catch (IOException e) {
					e.printStackTrace();
					}
				return null;
			}
		}
		if(dataId==null||dataId==""){
			Survey survey = new Survey();	
			survey.setSurveyName(surveyName);
			survey.setDeployFlag(Integer.valueOf(deployFlag));
			survey.setSurveyType(surveyType);
			survey.setBeginDate(date);
			survey.setEndDate(enddate);
			super.getService().save(survey);
			TrainCourseSurvey trainCourseSurvey = new TrainCourseSurvey();
			trainCourseSurvey.setSurvey(survey);
			trainCourseSurvey.setTrainCourse(trainCourse);
			super.getService().save(trainCourseSurvey);
			dataId=String.valueOf(survey.getId());
			json += "{dataId:\""+dataId+"\"}";
		}else{
			Survey survey = trainPlanService.findSurveyById(id);
			survey.setSurveyName(surveyName);
			survey.setDeployFlag(Integer.valueOf(deployFlag));
			survey.setSurveyType(surveyType);
			survey.setBeginDate(date);
			survey.setEndDate(enddate);
			super.getService().save(survey);
			TrainCourseSurvey trainCourseSurvey = trainPlanService.findTrainCourseSurvey(survey);
			trainCourseSurvey.setSurvey(survey);
			trainCourseSurvey.setTrainCourse(trainCourse);
			super.getService().save(trainCourseSurvey);
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
	@SuppressWarnings("unchecked")
	public String findCourse() throws Exception {
		String json = "";
		String ids= super.getRequest().getParameter("id");
		//List list = super.getService().findAll(TrainCourse.class);	
		SurveyType surveyType = (SurveyType) super.getService().find(SurveyType.class, ids);
		SystemMainTable systemMainTable = surveyType.getSystemMainTable();
		Class cla = null;
		cla = Class.forName(systemMainTable.getClassName());
		List list = super.getService().findAll(cla);
		//List list = trainPlanService.findAllCourse();
		for(int i=0;i<list.size();i++){
			if(ids.equals("1")){
				TrainCourse trainCourse = (TrainCourse) list.get(i);
				Long id = trainCourse.getId();
				String name = trainCourse.getCourseName();
				json += "{\"code\":"+id+",\"name\":\""+name+"\"},";
			}
			if(ids.equals("2")){
				return null;
			}
			//TrainCourse trainCourse = (TrainCourse) list.get(i);
//			Long id = trainCourse.getId();
//			String name = trainCourse.getCourseName();

//			json += "{\"code\":"+id+",\"name\":\""+name+"\"},";
			}
			json = "{data:[" + json.substring(0, json.length()-1) + "]}";
			System.out.println(json);
			try {			
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter pw = super.getResponse().getWriter();	
				pw.write(json);		
				} catch (IOException e) {
				e.printStackTrace();
				}
		return null;
		
	}
	public String findSurvey() throws Exception {
		String json ="";
		String id = super.getRequest().getParameter("id");
		Survey survey = trainPlanService.findSurveyById(id);
		Long surveyId = survey.getId();
		String surveyName = survey.getSurveyName();
		SurveyType surveyType = survey.getSurveyType();
		int deployFlag = survey.getDeployFlag();
		TrainCourseSurvey trainCourseSurvey = trainPlanService.findTrainCourseSurvey(survey);
		TrainCourse trainCourse = trainCourseSurvey.getTrainCourse();
		Long trainCourseid = trainCourse.getId();
		Long surveyTypeid = surveyType.getId();
		json += "{surveyName:\""+surveyName+"\",deployFlag:"+deployFlag+",surveyTypeid:"+surveyTypeid+",trainCourse:"+trainCourseid+",id:"+surveyId+"}";
		//json = "{success:[" + json.substring(0, json.length()-1) + "]}";
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
		
	}
	@SuppressWarnings("unchecked")
	public String saveQuest() throws Exception {
		String json = "";
		String dataId = super.getRequest().getParameter("dataId");
		String id = super.getRequest().getParameter("id");
		String questName = super.getRequest().getParameter("questName");
		String questType = super.getRequest().getParameter("questType");
		String product = super.getRequest().getParameter("product");
		Survey survey = trainPlanService.findSurveyById(dataId);
		QuestType questType2 = (QuestType) super.getService().find(QuestType.class, questType);
		Quest quest = null;
		if(id==null||id==""){
			quest = new Quest();
			quest.setQuestName(questName);
			quest.setQuestType(questType2);
			quest.setSurvey(survey);
			super.getService().save(quest);
		}else{
			quest = (Quest) super.getService().find(Quest.class, id);
			quest.setQuestName(questName);
			quest.setQuestType(questType2);
			quest.setSurvey(survey);
			super.getService().save(quest);
		}
		//
		Long qid = quest.getId();
		Long questTypeid = quest.getQuestType().getId();
		if(questTypeid==1||questTypeid==2){
			product = HttpUtil.ConverUnicode(product);
			JSONArray ja = JSONArray.fromObject("[" + product + "]");
			QuestOption questOption =null;
			for (int i = 0; i < ja.size(); i++) {
				HashMap productMap = new HashMap();
				JSONObject opl = (JSONObject) ja.get(i);
				Iterator itProduct = opl.keys();
				while (itProduct.hasNext()) {
					String key = (String) itProduct.next();
					String value = opl.getString(key);
//					if(key.equals("code")&&value=="null"){
//						questOption = null;
//					}
					if(key.equals("id")&&value!="null"){
						questOption = (QuestOption) super.getService().find(QuestOption.class, value);
					}
	//				if(key.equals("content")&&value=="null"){
	//					value = "";
	//				}
	//				if(key.equals("content")&&value!="null"){
	//					value = questOption.getContent();
	//				}
	//				if(key.equals("answerNo")&&value=="null"){
	//					value = "";
	//				}
	//				if(key.equals("answerNo")&&value!="null"){
	//					value = questOption.getAnswerNo();
	//				}
					productMap.put(key, value);
				}
				productMap.put("quest",qid );
				questOption = (QuestOption) BeanUtil.getObject(productMap, QuestOption.class);
				super.getService().save(questOption);
			}
		}else if(questTypeid==3||questTypeid==4){
			Quest qq = (Quest) super.getService().find(Quest.class, String.valueOf(qid));
			List<QuestOption> list = super.getService().find(QuestOption.class, "quest", qq);
			if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				super.getService().remove(list.get(i));
			}
			}
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
	public String removeQuestOption() throws Exception {
		String scIds = super.getRequest().getParameter("dataId");
		String clazz = super.getRequest().getParameter("clazz");
		Class cls = null;
		cls = Class.forName(clazz);
		if(scIds!=""&&scIds!=null){
			super.getService().remove(cls, scIds);
		}
		return null;
	}
	public String removeQuest() throws Exception {
		String scIds = super.getRequest().getParameter("dataId");
		String clazz = super.getRequest().getParameter("clazz");
		Class cls = null;
		cls = Class.forName(clazz);
		Quest quest = (Quest) super.getService().find(Quest.class, scIds);
		List<QuestOption> lists = trainPlanService.findQuestOption(quest);
		Iterator<QuestOption> iter = lists.iterator();
		while(iter.hasNext()){
			QuestOption  questOption = iter.next();
			super.getService().remove(questOption);
		}
		super.getService().remove(cls, scIds);
		return null;
	}
	public String removeSurvey() throws Exception {
		String scIds = super.getRequest().getParameter("dataId");
		String clazz = super.getRequest().getParameter("clazz");
		Class cls = null;
		cls = Class.forName(clazz);
		Survey survey = (Survey) super.getService().find(Survey.class, scIds);
		TrainCourseSurvey trainCourseSurvey = trainPlanService.findTrainCourseSurvey(survey);
		List<Quest> lists = trainPlanService.findQuest(survey);
		Iterator<Quest> iter = lists.iterator();
		
		while(iter.hasNext()){
			Quest  quest = iter.next();
			List<QuestOption> list = trainPlanService.findQuestOption(quest);
			Iterator<QuestOption> ite = list.iterator();
			while(ite.hasNext()){
				QuestOption  questOption = ite.next();
				super.getService().remove(questOption);
			}
			super.getService().remove(quest);
		}
		super.getService().remove(cls, scIds);
		super.getService().remove(trainCourseSurvey);
		return null;
	}
	/**
	 * 显示用户反馈
	 * @Methods Name findQuest
	 * @Create In May 4, 2009 By sujs
	 * @return
	 * @throws Exception String
	 */
	public String findQuest() throws Exception {
		//这个id是报名id,所以我们必须通过这个id找到课程id,然后通过课程id再找到问卷的id
		String surveyId= super.getRequest().getParameter("dataId");
		//TrainCourseSignup trainCourseSignup=(TrainCourseSignup)service.find(TrainCourseSignup.class,trainCourseSignupId,true);
		
		Survey survey1 = (Survey)service.find(Survey.class, surveyId, true);
		List<TrainCourseSurvey> trainCourseSurvey=service.find(TrainCourseSurvey.class, "survey", survey1);
		
		//List<TrainCourseSurvey> trainCourseSurvey=service.find(TrainCourseSurvey.class, "trainCourse", trainCourseSignup.getTrainCourse());
		//这里是判断如果只有课程没有问卷的情况
		if(trainCourseSurvey.size()==0){
			return "nosurvey";
		}else if(trainCourseSurvey.get(0).getSurvey().equals(null)){
			return "nosurvey";
		}
		//判断用户是否填写过反馈问卷
		TrainCourse trainCourse=new TrainCourse();
		trainCourse.setId(trainCourseSurvey.get(0).getTrainCourse().getId());
		List<QuestResult> questResult=trainPlanService.findQuestByCourse(trainCourseSurvey.get(0).getTrainCourse());
		if(questResult.size()!=0){
			return "noresult";	
		}
		Survey survey = trainPlanService.findSurveyById(trainCourseSurvey.get(0).getSurvey().getId().toString());
		List<Quest> lists = trainPlanService.findQuest(survey);
		//super.getRequest().setAttribute("quests", lists);
		Iterator<Quest> iter = lists.iterator();
		Map map=new HashMap();
		while(iter.hasNext()){
			Quest  quest = iter.next();
			List<QuestOption> list = trainPlanService.findQuestOption(quest);
			map.put(quest, list);
		}
		//super.getRequest().setAttribute("dataId", dataId);
		//super.getResponse().sendRedirect("");
		super.getRequest().setAttribute("questOptions", map);
		super.getRequest().setAttribute("surveyId", surveyId);
		super.getRequest().setAttribute("survey", survey);
		super.getRequest().setAttribute("courseId", trainCourseSurvey.get(0).getTrainCourse().getId());
		return "ContactSucess";
		   
	}
	/**
	 * 通过当前登陆的用户查出他所能填写课程的用户反馈
	 * @Methods Name findCourseByuser
	 * @Create In May 4, 2009 By Administrator
	 * @return
	 * @throws IOException String
	 */
	public String findCourseByuser() throws IOException {
			String json="";
			int pageSize = 10;
			// 注意以后从设置中取出
			int start = HttpUtil.getInt(super.getRequest(), "start", 0);
			int pageNo = start / pageSize + 1;
			String orderBy = HttpUtil.getString(super.getRequest(), "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(super.getRequest(), "isAsc", true);
			
			Map requestParams = HttpUtil.requestParam2Map(super.getRequest());
			requestParams.put("signupUser", UserContext.getUserInfo());
//			requestParams.remove("id");
			
			Page page = metaDataManager.query(TrainCourseSignup.class, requestParams, pageNo, pageSize, orderBy, isAsc);
					
			Long total = page.getTotalCount();
			List queryList = page.list();
			List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(TrainCourseSignup.class, queryList);
			List<UserTableSetting> userVisibleColumns = metaDataManager.getUserColumnForList(TrainCourseSignup.class);
			json = CoderForList.encode(userVisibleColumns, listData, total);
		super.getResponse().setContentType("text/plain");
		super.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	public String findQuestContact() throws Exception {		
		String dataId = super.getRequest().getParameter("dataId");
		Survey survey = trainPlanService.findSurveyById(dataId);
		List<Quest> lists = trainPlanService.findQuest(survey);
		//super.getRequest().setAttribute("quests", lists);
		Iterator<Quest> iter = lists.iterator();
		Map map=new HashMap();
		while(iter.hasNext()){
			Quest  quest = iter.next();
			List<QuestOption> list = trainPlanService.findQuestOption(quest);
			map.put(quest, list);
		}
		//super.getRequest().setAttribute("dataId", dataId);
		//super.getResponse().sendRedirect("");
		super.getRequest().setAttribute("questOptions", map);
		super.getRequest().setAttribute("survey", survey);
		return "ContactSucess";
		
	}
	@SuppressWarnings("unchecked")
	public String findUserCourse() throws Exception {
		UserInfo userInfo = UserContext.getUserInfo();
		//UserContext.changeCurrentUserInfo(userInfo);
		List<TrainCourseSignup> list = super.getService().find(TrainCourseSignup.class, "signupUser", userInfo);
		if(list.size()==0){
			return null;
		}
		List<TrainCourse> course = new ArrayList();
		Iterator<TrainCourseSignup> iter = list.iterator();
		while(iter.hasNext()){
			TrainCourseSignup  trainCourseSignup = iter.next();
			TrainCourse trainCourse = trainCourseSignup.getTrainCourse();
			course.add(trainCourse);
		}
		super.getRequest().setAttribute("course", course);
		return "ok";
		
	}
	@SuppressWarnings("unchecked")
	public String findQuest2() throws Exception {

		
		UserInfo userInfo = UserContext.getUserInfo();
		String dataId = super.getRequest().getParameter("radio");
		if(dataId==""||dataId==null){
			this.findUserCourse();
			return "ok";
		}
		super.getSession().setAttribute("dataId",dataId);
		TrainCourse trainCourse = trainPlanService.findTrainCourseById(dataId);
		List<TrainCourseSurvey> trainCourseSurveyList = trainPlanService.findCourseSurveyByCourse(trainCourse);
		Survey survey = null;
		Iterator<TrainCourseSurvey> it = trainCourseSurveyList.iterator();
		while(it.hasNext()){
			TrainCourseSurvey trainCourseSurvey = it.next();
			//System.out.println(trainCourseSurvey.getSurvey().getDeployFlag());

			if(trainCourseSurvey.getSurvey()!=null&&trainCourseSurvey.getSurvey().getDeployFlag()==1){
				survey = trainCourseSurvey.getSurvey();
				break;
			}
		}
		
		
		//判断用户是否已经填过该课程的反馈信息 trainCourse survey userInfo
		
		List<QuestResult> resultList = super.getService().find(QuestResult.class, "userId", userInfo);
		Iterator<QuestResult> reit = resultList.iterator();
		while(reit.hasNext()){
			QuestResult questResult = reit.next();
			if(questResult.getTrainCourse().equals(trainCourse)){
				System.out.println("****************************************");
				return "noresult";
			}
			
			
		}
		int num = 0;
		int pageSize = 5;
		HttpServletRequest request = super.getRequest();
		super.getRequest().getParameter("pageNo");
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		num = (pageNo-1)*5+1;
		//************************************点下一步时把值取到
		Map listresult = new HashMap();
		String[] quest_ids = super.getRequest().getParameterValues("quest_id");
		if(quest_ids!=null){
			listresult.put("quest_ids", quest_ids);
			for(int i =0;i<quest_ids.length;i++){
				String radio = super.getRequest().getParameter(quest_ids[i]+"_radio");
				listresult.put("radio"+quest_ids[i], radio);
			}
		}
		
		String[] quest_ids2 = super.getRequest().getParameterValues("quest_id2");
		if(quest_ids2!=null){
			listresult.put("quest_ids2", quest_ids2);
			for(int i =0;i<quest_ids2.length;i++){
				String[] checkbox = super.getRequest().getParameterValues(quest_ids2[i]+"_checkbox");
				listresult.put("checkbox"+quest_ids2[i], checkbox);
			}
		}
		String[] quest_ids3 = super.getRequest().getParameterValues("quest_id3");
		if(quest_ids3!=null){
			listresult.put("quest_ids3", quest_ids3);
			for(int i =0;i<quest_ids3.length;i++){
				String text = super.getRequest().getParameter(quest_ids3[i]+"_text");
				listresult.put("text"+quest_ids3[i], text);
			}
		}
	
		String[] quest_ids4 = super.getRequest().getParameterValues("quest_id4");
		if(quest_ids4!=null){
			listresult.put("quest_ids4", quest_ids4);
			for(int i =0;i<quest_ids4.length;i++){
				String text2 = super.getRequest().getParameter(quest_ids4[i]+"_text");
				listresult.put("text2"+quest_ids4[i], text2);
			}
		}
		super.getSession().setAttribute("listresult"+pageNo, listresult);
		
		
		
		//********************************
		
	
		
		//List<Quest> lists = trainPlanService.findQuest(survey);//加个排序，分页
		Page page = (Page) trainPlanService.findQuest(survey,pageNo,pageSize);
		List lists = page.getData();
		//super.getRequest().setAttribute("quests", lists);
		Iterator<Quest> iter = lists.iterator();
		Map map=new LinkedHashMap();//linkedHashMap按set进去先后顺序排序的
		while(iter.hasNext()){
			Quest  quest = iter.next();
			List<QuestOption> list = trainPlanService.findQuestOption(quest);
			map.put(quest, list);
		}
		//super.getRequest().setAttribute("dataId", dataId);
		//super.getResponse().sendRedirect("");
		super.getRequest().setAttribute("questOptions", map);
		super.getRequest().setAttribute("page", page);
		super.getRequest().setAttribute("pageNo", pageNo);
		super.getRequest().setAttribute("num", num+"");
		//System.out.println("7894654");
		return "view";
    
		
	}
	 /**
	  * 保存用户填写的反馈问卷
	  * @Methods Name saveQuestResult
	  * @Create In May 5, 2009 By zhaogh after modify By sujs
	  * @return
	  * @throws Exception String
	  */
	public String saveQuestResult() throws Exception {
		//String radio = super.getRequest().getParameter("4_radio");
			@SuppressWarnings("unused")
			UserInfo userInfo = UserContext.getUserInfo();
//			Map map = new HashMap();
			String dataId = (String) super.getRequest().getParameter("courseId");
			TrainCourse trainCourse = trainPlanService.findTrainCourseById(dataId);
			List<TrainCourseSurvey> trainCourseSurveyList = trainPlanService.findCourseSurveyByCourse(trainCourse);
			Survey survey = null;
			Iterator<TrainCourseSurvey> it = trainCourseSurveyList.iterator();
			while(it.hasNext()){
				TrainCourseSurvey trainCourseSurvey = it.next();
				Survey s = trainCourseSurvey.getSurvey();
					if(s.getDeployFlag()==1){
						survey = s;
						break;
					}
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
					questResult.setTrainCourse(trainCourse);
					questResult.setSurvey(survey);
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
							questResult.setTrainCourse(trainCourse);
							questResult.setSurvey(survey);
							super.getService().save(questResult);
					}
				}
			}
			
			String[] quest_ids3 = super.getRequest().getParameterValues("quest_id3");
			if(quest_ids3!=null){
				for(int i=0;i<quest_ids3.length;i++){
					 //map.put(quest_ids[i],request.getParameter(quest_ids[i]+"_radio")) ;     //题目ID对应的用户选的答案
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
					questResult.setTrainCourse(trainCourse);
					questResult.setSurvey(survey);
					super.getService().save(questResult);
				}
			}
			String[] quest_ids4 = super.getRequest().getParameterValues("quest_id4");
			if(quest_ids4!=null){
				for(int i=0;i<quest_ids4.length;i++){
					 //map.put(quest_ids[i],request.getParameter(quest_ids[i]+"_radio")) ;     //题目ID对应的用户选的答案
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
					questResult.setTrainCourse(trainCourse);
					questResult.setSurvey(survey);
					super.getService().save(questResult);
				}
			}
			HttpServletRequest request = super.getRequest();
			super.getRequest().getParameter("pageNo");
			int pageNo = HttpUtil.getInt(request, "pageNo", 1);
			for(int p=2;p<=pageNo;p++){
				Map listresult = (Map) super.getSession().getAttribute("listresult"+p);
				String[] questids = (String[]) listresult.get("quest_ids");
				String[] questids2 = (String[]) listresult.get("quest_ids2");
				String[] questids3 = (String[]) listresult.get("quest_ids3");
				String[] questids4 = (String[]) listresult.get("quest_ids4");
				if(questids!=null){
					for(int i=0;i<questids.length;i++){
						String radio = (String) listresult.get("radio"+questids[i]);
						Quest quest = (Quest) super.getService().find(Quest.class, questids[i]);
						QuestOption questOption = (QuestOption) super.getService().find(QuestOption.class, radio);
						QuestResult questResult = new QuestResult();
						questResult.setQuest(quest);
						questResult.setQuestOption(questOption);
						questResult.setUserId(userInfo);
						questResult.setTrainCourse(trainCourse);
						questResult.setSurvey(survey);
						super.getService().save(questResult);
					}
				}
				if(questids2!=null){
					for(int i=0;i<questids2.length;i++){
						String[] checkbox = (String[]) listresult.get("checkbox"+questids2[i]);
						Quest quest = (Quest) super.getService().find(Quest.class, questids2[i]);
						for(int j=0;j<checkbox.length;j++){
							QuestOption questOption = (QuestOption) super.getService().find(QuestOption.class, checkbox[j]);
							QuestResult questResult = new QuestResult();
							questResult.setQuest(quest);
							questResult.setQuestOption(questOption);
							questResult.setUserId(userInfo);
							questResult.setTrainCourse(trainCourse);
							questResult.setSurvey(survey);
							super.getService().save(questResult);
						}
						
					}
				}
				if(questids3!=null){
					for(int i=0;i<questids3.length;i++){
						String text = (String) listresult.get("text"+questids3[i]);
						Quest quest = (Quest) super.getService().find(Quest.class, questids3[i]);
						QuestOption questOption = new QuestOption();
						questOption.setContent(text);
						questOption.setQuest(quest);
						super.getService().save(questOption);
						QuestResult questResult = new QuestResult();
						questResult.setQuest(quest);
						questResult.setQuestOption(questOption);
						questResult.setUserId(userInfo);
						questResult.setTrainCourse(trainCourse);
						questResult.setSurvey(survey);
						super.getService().save(questResult);
					}
				}
				if(questids4!=null){
					for(int i=0;i<questids4.length;i++){
						String text2 = (String) listresult.get("text2"+questids4[i]);
						Quest quest = (Quest) super.getService().find(Quest.class, questids4[i]);
						QuestOption questOption = new QuestOption();
						questOption.setContent(text2);
						questOption.setQuest(quest);
						super.getService().save(questOption);
						QuestResult questResult = new QuestResult();
						questResult.setQuest(quest);
						questResult.setQuestOption(questOption);
						questResult.setUserId(userInfo);
						questResult.setTrainCourse(trainCourse);
						questResult.setSurvey(survey);
						super.getService().save(questResult);
					}
				}
			}
			super.getResponse().setCharacterEncoding("utf-8");
		return "addCallBackSuccess";
		
	}
	public String findSurveyType() throws Exception {
		String json = "";
		List<SurveyType> list = super.getService().findAll(SurveyType.class);
		for(int i = 0 ; i < list.size();i++){
			SurveyType surveyType = list.get(i);
			Long code = surveyType.getId();
			String name2 = surveyType.getName();
			json += "{\"code\":"+code+",\"name2\":\""+name2+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println(json);
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			} 
		
		return null;
		
	}
	public String fingQuestOption() throws Exception{
		String json = "";
		String ids = super.getRequest().getParameter("id");
		if(ids==""||ids==null){
			return null;
		}
		Quest quest = (Quest) super.getService().find(Quest.class, ids);
		List list = super.getService().find(QuestOption.class, "quest", quest);
//		List list = super.getService().findAll(QuestOption.class);
		for(int i =0 ;i<list.size();i++){
			QuestOption questOption = (QuestOption) list.get(i);
			Long id = questOption.getId();
			String content = questOption.getContent();
			String answerNo = questOption.getAnswerNo();
			json += "{\"id\":"+id+",\"content\":\""+content+"\",\"answerNo\":\""+answerNo+"\"},";
		}
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println(json);
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			} 
		
		return null;
		
	}
	public String findQuestType() throws Exception{
		String json = "";
		String ids = super.getRequest().getParameter("id");
		if(ids==""||ids==null){
			return null;
		}
		Quest quest = (Quest) super.getService().find(Quest.class, ids);
		QuestType questType = quest.getQuestType();
		Long typeid = questType.getId();
		json += "{\"typeid\":"+typeid+"\"}";
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(String.valueOf(typeid));		
			} catch (IOException e) {
			e.printStackTrace();
			} 
		return null;
		
	}
	/**
	 * 确定分页的页码
	 * @Methods Name confirmPageNo
	 * @Create In Apr 8, 2009 By Administrator
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
	public String findSurveyByCourse() throws Exception{
		String json= "";
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String paramName = super.getRequest().getParameter("start");
		if(dataId==""||dataId==null){
			return null;
		}
		int pageSize = 10;
		int size = 0;
		int pageNo = this.confirmPageNo(paramName, 1);
		SurveyType surveyType = (SurveyType) super.getService().find(SurveyType.class, "1");
		//TrainCourse trainCourse =  (TrainCourse) super.getService().find(TrainCourse.class, dataId);
		Page page = trainPlanService.getSurveyAll(surveyType,pageNo, pageSize);
		size = page.getPageSize();
		List<Survey> list = page.list();
		Survey survey = null;
		for(int i=0;i<list.size();i++){
			survey= list.get(i);
			String id = survey.getId().toString();
			String name = survey.getSurveyName();
			json+="{code:'" + id + "',name:'" + name + "'},";
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
	 *遍历课程的问卷
	 * @Methods Name selectProblem
	 * @Create In Mar 24, 2009 By daijf
	 * @return
	 * @throws IOException String
	 */
	 public String selectCourseSurvey()throws Exception {    
		    HttpServletResponse  response = super.getResponse();
		    int pageSize = 10;
		    int pageNo = HttpUtil.getInt(super.getRequest(), "start", 1);
			String orderBy = HttpUtil.getString(super.getRequest(), "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(super.getRequest(), "isAsc", false);
		    String  courseId=super.getRequest().getParameter("courseId");
		    TrainCourse traincourse=new TrainCourse();
		    traincourse.setId(Long.parseLong(courseId));
			Map requestParams = HttpUtil.requestParam2Map(super.getRequest());
			requestParams.put("trainCourse", traincourse);
			Page page = metaDataManager.query(TrainCourseSurvey.class, requestParams, pageNo, pageSize, orderBy, isAsc);
			Long total = page.getTotalCount();
			List<TrainCourseSurvey> trainCourseSurveyList = page.list();
			String json="";	
		    for(TrainCourseSurvey trainCourseSurvey:trainCourseSurveyList){
		    	String id=trainCourseSurvey.getSurvey().getId().toString();
		    	String surveyName=trainCourseSurvey.getSurvey().getSurveyName();
		    	String surveyTypeName=trainCourseSurvey.getSurvey().getSurveyType().getName();
		    	String deployFlagg=trainCourseSurvey.getSurvey().getDeployFlag().toString();
		    	String deployFlag="";
		    	if(deployFlagg.equals("0")){
		    		deployFlag="否";
		    	}else{
		    		deployFlag="是";
		    	}
		    	Date   startDate=trainCourseSurvey.getSurvey().getBeginDate();
		    	String  sstartDate="";
		    	if(startDate !=null){
		    		sstartDate=startDate.toString();
		    	}
		    	Date   endDate=trainCourseSurvey.getSurvey().getEndDate();
		    	String  eendDate="";
		    	if(endDate !=null){
		    		eendDate=endDate.toString();
		    	}
				json += "{TrainSurvey$id:\""+id+"\",";
				json +="TrainSurvey$surveyName:\""+surveyName+"\",";
				json +="TrainSurvey$surveyType:\""+surveyTypeName+"\",";
				json +="TrainSurvey$deployFlag:\""+deployFlag+"\",";
				json +="TrainSurvey$beginDate:\""+sstartDate+"\",";
		        json +="TrainSurvey$endDate:\""+eendDate+"\"},";
			}
		   if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success : true,rowCount:" + total + ",data:["
			+ json + "]}";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		    return null;	
		
	 }
	 /**
	  * 得到deleteFlag为0 的培训课程
	  * @Methods Name findTrainCourse
	  * @Create In Jun 24, 2009 By guoxl
	  * @return
	  * @throws Exception String
	  */
	 public String findTrainCourse() throws Exception{
//			String json= "";
//			HttpServletRequest request = super.getRequest();
//			HttpServletResponse response = super.getResponse();
//			int pageSize = 10;
//			int size = 0;
//			List<TrainCourse> list = service.findAll(TrainCourse.class);
//			for(int i=0;i<list.size();i++){
//				TrainCourse tc = list.get(i);
//				if(tc.getDeleteFlag()==0){
//					String id = tc.getId().toString();
//					String name = tc.getCourseName();
//					json+="{trainCourse:'" + id + "',name:'" + name + "'},";
//				}
//			}
		 
		 	HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String 	json = "";	
			int start = this.getInt(request, "start", 0);
			int pageNo = start / pageSize + 1;
			int pageSize = 10;
			String CName = 	HttpUtil.ConverUnicode(request.getParameter("name"));
			// 把查询结果做分页
			Page page = trainPlanService.findCourseNameByParam(CName,pageNo,pageSize);
			Long total = page.getTotalCount();
			List<TrainCourse> CourseList =  page.list();

			if (CourseList.size() == 0) {

				json = "{success: true, rowCount:" + total + ",data:[" + json
						+ "]}";
				try {
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(json);
					response.getWriter().flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				for (TrainCourse course : CourseList) {

					String dataStr = "";
					json += "{trainCourse:" + course.getId() + ",name:'" + course.getCourseName()
							+ "'}";
					json += ",";
				}
		 
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true, rowCount:" + page.getTotalCount() + ",data:[" + json + "]}";
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
			}
			return null;
		}
	 /**
	  * 培训报名列表页面的报名的分页查询（支持模糊查询）
	  * @Methods Name findAllUser
	  * @Create In Jun 25, 2009 By guoxl
	  * @return
	  * @throws Exception String
	  */
	 public String findAllUser() throws Exception{
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String 	json = "";	
			int start = this.getInt(request, "start", 0);
			int pageNo = start / pageSize + 1;
			int pageSize = 10;
			String UName = 	HttpUtil.ConverUnicode(request.getParameter("name"));
			// 把查询结果做分页
			Page page = trainPlanService.findUserNameByParam(UName,pageNo,pageSize);
			Long total = page.getTotalCount();
			List<UserInfo> UserList =  page.list();

			if (UserList.size() == 0) {

				json = "{success: true, rowCount:" + total + ",data:[" + json
						+ "]}";
				try {
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(json);
					response.getWriter().flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				for (UserInfo user : UserList) {

					String dataStr = "";
					json += "{signupUser:" + user.getId() + ",name:'" + user.getRealName()
							+ "'}";
					json += ",";
				}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true, rowCount:" + page.getTotalCount() + ",data:[" + json + "]}";
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
		}
			return null;
	 }
	public int getInt(HttpServletRequest request, String param,
					int defaultValue) {
				String strValue = request.getParameter(param);
				if (strValue == null) {
					return defaultValue;
				} else {
					return Integer.parseInt(strValue);
				}
			}
}
