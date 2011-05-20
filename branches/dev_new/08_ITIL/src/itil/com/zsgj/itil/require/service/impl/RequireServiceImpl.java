package com.zsgj.itil.require.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.extlist.entity.SRExpendPlan;
import com.zsgj.itil.config.extlist.entity.SRIncomePlan;
import com.zsgj.itil.require.entity.BusinessAccount;
import com.zsgj.itil.require.entity.RealIncome;
import com.zsgj.itil.require.entity.RealPayment;
import com.zsgj.itil.require.entity.RequireAppSystem;
import com.zsgj.itil.require.entity.RequireApplyDefaultAudit;
import com.zsgj.itil.require.entity.SpecialRequirement;
import com.zsgj.itil.require.entity.UpDatePlan;
import com.zsgj.itil.require.entity.UpDatePlanEvent;
import com.zsgj.itil.require.service.RequireService;
import com.zsgj.itil.service.dao.RequireServiceDao;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.train.entity.Quest;
import com.zsgj.itil.train.entity.QuestOption;
import com.zsgj.itil.train.entity.QuestResult;
import com.zsgj.itil.train.entity.Survey;
import com.zsgj.itil.train.entity.SurveyType;

public class RequireServiceImpl extends BaseDao implements RequireService {
	private MetaDataManager metaDataManager;// = (MetaDataManager)
											// ContextHolder.getBean("metaDataManager");
	private RequireServiceDao rsd;

	public RequireServiceDao getRsd() {
		return rsd;
	}

	public void setRsd(RequireServiceDao rsd) {
		this.rsd = rsd;
	}

	public List<PageModelPanel> findPanelsByServiceItem(String pageModel,
			ServiceItem serviceItem) {

		PageModelService pageModelService = (PageModelService) ContextHolder
				.getBean("pageModelService");
		PageModel model = pageModelService.findPageModel(pageModel);

		ServiceItemUserTable siut = (ServiceItemUserTable) super.findUniqueBy(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
//		SystemMainTable smt = siut.getSystemMainTable();
		PagePanel panel = siut.getPagePanel();

		List<PageModelPanel> pageModelPanels = new ArrayList<PageModelPanel>();
		PageModelPanel pmp = new PageModelPanel();
		pmp.setPageModel(model);
		pmp.setPagePanel(panel);

		pageModelPanels.add(pmp);
		return pageModelPanels;
	}

	public String getRequirePanelJson(String pageModel, ServiceItem serviceItem) {
		List<PageModelPanel> pmps = this.findPanelsByServiceItem(pageModel,
				serviceItem);
		String json = "";
		for (PageModelPanel item : pmps) {
			String divFloat = item.getDivFloat();
			if (divFloat == null)
				divFloat = "";
			PagePanel panel = item.getPagePanel();
			json += "{";
			json += "\"panelname\":\"" + panel.getName() + "\",";
			json += "\"title\":\"" + panel.getTitle() + "\",";
			json += "\"panelTableName\":\""
					+ panel.getSystemMainTable().getTableCnName() + "\",";
			json += "\"xtype\":\"" + panel.getXtype().getName() + "\",";
			json += "\"divFloat\":\"" + divFloat + "\",";
			json += "\"order\":\"" + item.getOrder() + "\",";
			json += "\"clazz\":\"" + panel.getSystemMainTable().getClassName()
					+ "\"";
			json += "},";
		}
		json = json.substring(0, json.length() - 1);
		return json;
	}

	public String forQuerry(String className, int sidProcessType, int i) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		List<UserTableSetting> userVisibleColumns = metaDataManager
				.getUserColumnForList(clazz);
		Criteria c = super.createCriteria(clazz);
		c.add(Restrictions.eq("processType", sidProcessType));
		c.add(Restrictions.eq("status", i));
		List list = c.list();
		List<Map<String, Object>> listData = metaDataManager
				.getEntityMapDataForList(clazz, list);
		String json = this.encode(userVisibleColumns, listData);
		return json;
	}

	private String encode(List<UserTableSetting> userVisibleColumns,
			List<Map<String, Object>> listData) {
		String json = "";
		for (Map<String, Object> item : listData) {
			String dataItem = "";
			dataItem += "process:'',";
			for (UserTableSetting uts : userVisibleColumns) {
//				String columnCnName = uts.getColumn().getColumnCnName();// 表头标题
				// SystemMainTableColumn mainTableColumn =
				// uts.getMainTableColumn();
				Column column = uts.getColumn();

				String propertyName = column.getPropertyName();//
				String columnTypeName = column.getSystemMainTableColumnType()
						.getColumnTypeName();
				if (columnTypeName.equalsIgnoreCase("hidden")) {
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item.id}
				} else if (columnTypeName.equalsIgnoreCase("text")
						|| columnTypeName.equalsIgnoreCase("dateText")
						|| columnTypeName.equalsIgnoreCase("radio")) {
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item[column.mainTableColumn.propertyName]}
				} else if (columnTypeName.equalsIgnoreCase("select")) {
					// ?????
					// String foreignPropertyName =
					// column.getForeignTableValueColumn().getPropertyName();
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					// Integer isAbstract = column.getAbstractFlag();
					// if(isAbstract!=null&& isAbstract.intValue()==1){
					// propertyName = "name";
					// }
					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item[column.mainTableColumn.propertyName]}
				} else if (columnTypeName.equalsIgnoreCase("yesNoSelect")) {
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					dataItem += "" + propertyName + ":'" + value + "',";
					// value ${item[column.mainTableColumn.propertyName]}
				} else {// 其他
					Object value = item.get(propertyName);
					if (value == null)
						value = "";
					dataItem += "" + propertyName + ":'" + value + "',";
				}
			}
			// if(dataItem.endsWith(",")) {
			// dataItem = dataItem.substring(0,dataItem.length()-1);
			// }
			dataItem += "uiProvider:'col',";
			dataItem += "leaf:true,";
			dataItem += "iconCls:'task'";
			dataItem = "{" + dataItem + "},";
			json += dataItem;
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		// json = "{success: true,data:["+json+"]}";
		return json;
	}

	public MetaDataManager getMetaDataManager() {
		return metaDataManager;
	}

	public void setMetaDataManager(MetaDataManager metaDataManager) {
		this.metaDataManager = metaDataManager;
	}

	public void saveEntityToEvent(String entityClass, BaseObject entityObject) {
		String eventClassName = entityClass + "Event";
		Class eventClass = this.toClass(eventClassName);
		BaseObject eventObject = (BaseObject) BeanUtils
				.instantiateClass(eventClass);
		BeanUtils.copyProperties(entityObject, eventObject);

		Long entityId = entityObject.getId();
		BeanWrapper bw = new BeanWrapperImpl(eventObject);
		bw.setPropertyValue("id", null);
		bw.setPropertyValue("rootId", entityId);
		super.save(eventObject);
	}

	public Object getOldApplyObject(String entityClass, BaseObject entityObject) {
		Class clazz = toClass(entityClass);
		Criteria criteria = super.createCriteria(clazz);
		criteria.setFetchMode("oldApply", FetchMode.JOIN);
		criteria.add(Restrictions.eq("id", entityObject.getId()));
		Object object = criteria.uniqueResult();
		BeanWrapper bw = new BeanWrapperImpl(object);
		Object oldApplyObject = bw.getPropertyValue("oldApply");
		return oldApplyObject;
	}

	private Class toClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println(className + "类不存在！");
			e.printStackTrace();
		}
		return clazz;
	}

	public Survey findSpecialRequireSurvey() {
		SurveyType surveyType = (SurveyType) super.createCriteria(
				SurveyType.class).setFetchMode("systemMainTable",
				FetchMode.JOIN).createAlias("this.systemMainTable",
				"systemMainTable").add(
				Restrictions.eq("systemMainTable.id", new Long("597")))
				.uniqueResult();
		Criteria criteria = super.createCriteria(Survey.class).setFetchMode(
				"surveyType", FetchMode.JOIN).add(
				Restrictions.eq("this.surveyType.id", surveyType.getId())).add(
				Restrictions.eq("deployFlag", new Integer("1")));
		Survey survey = (Survey) criteria.list().get(0);
		return survey;
	}

	public List<Quest> findQuest(Long surveyId) {
		List<Quest> questList = null;
		Criteria c = super.getCriteria(Quest.class);
		c.setFetchMode("survey", FetchMode.JOIN);
		c.createAlias("survey", "survey");
		c.add(Restrictions.eq("survey.id", surveyId));
		c.addOrder(Property.forName("questType").asc());
		questList = c.list();
		return questList;
	}

	public boolean findIsUserFeedbackOrNot(Long userInfoId, Long dataId,
			Long surveyId) {
		List<QuestResult> questResultList = super.createCriteria(
				QuestResult.class).setFetchMode("userId", FetchMode.JOIN)
				.setFetchMode("survey", FetchMode.JOIN).createAlias(
						"this.userId", "userInfo").add(
						Restrictions.eq("userInfo.id", userInfoId))
				.createAlias("this.survey", "survey").add(
						Restrictions.eq("survey.id", surveyId)).add(
						Restrictions.eq("objId", dataId)).list();
		if (questResultList != null && questResultList.size() > 0) {
			return true;
		}
		return false;
	}

	public List<QuestOption> findQuestOption(Quest quest) {
		// TODO Auto-generated method stub
		// this.findBy(QuestOption.class, "quest", quest);
		// Criteria c = super.getCriteria(QuestOption.class);
		// c.add(Restrictions.eq("quest", quest));
		return this.findBy(QuestOption.class, "quest", quest);
	}

	public Page findAuditsByPage(String departmentName, UserInfo audit,
			int pageNo, int pageSize) {
		Criteria c = super.getCriteria(RequireApplyDefaultAudit.class);
		//modify by lee for 优化列表数据获取方法 in 20100421 begin
		Disjunction useabledisjunction = Restrictions.disjunction();
		useabledisjunction.add(Restrictions.eq("deleteFlag", Integer.valueOf(0)));
		useabledisjunction.add(Restrictions.isNull("deleteFlag"));
		c.add(useabledisjunction);
		if (StringUtils.isNotBlank(departmentName)) {
			c.add(Restrictions.ilike("departmentName", departmentName,
					MatchMode.ANYWHERE));
		}
		if (audit != null) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("cadreBizAudit", audit));
			disjunction.add(Restrictions.eq("cadreFinanceAudit", audit));
			disjunction.add(Restrictions.eq("groupFinanceAudit", audit));
			disjunction.add(Restrictions.eq("cadreBusinessAudit", audit));
			disjunction.add(Restrictions.eq("clientItManager", audit));
			c.add(disjunction);			
		}		
		//modify by lee for 优化列表数据获取方法 in 20100421 end
		c.addOrder(Order.asc("sortNum"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	
	public Page findAppByPage(ConfigItem configItem,UserInfo appManager, int pageNo, int pageSize) {
		Criteria c =super.createCriteria(RequireAppSystem.class).setFetchMode("appConfigItem", FetchMode.JOIN);
		if(configItem!=null){
			c.add(Restrictions.eq("appConfigItem", configItem));
		}
		
		if(appManager != null){
			c.add(Restrictions.eq("appManager", appManager));
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
		
	}

	public Page findRequireByName(String name, int pageNo, int size) {
		Criteria c=createCriteria(SpecialRequirement.class);
		if(name.trim().length()!=0){
			c.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo,size);
//		Long totalSize=((Integer)count.setProjection(Projections.rowCount()).uniqueResult()).longValue();
//		Criteria c=createCriteria(SpecialRequirement.class)
//				   .addOrder(Order.asc("name"))
//				   .setFirstResult(start)
//				   .setMaxResults(size);
//		if(name.trim().length()!=0){
//			c.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
//		}
//		List<SpecialRequirement> specialRequirements=c.list();
//		return new Page(start,totalSize,size,specialRequirements);
		return page;
	}

	public String isRefuseFlag(String dataId, String processId,String nodeId) {
		List<ServiceItemApplyAuditHis> dataList = rsd.findServiceItemApplyAuditHis(dataId, processId);
		String strResultFlag = "";
		for(int i = 0;i<dataList.size();i++){
			String tempNodeId =  dataList.get(i).getNodeId();
			int tempId = tempNodeId.compareTo(nodeId);
			String tempFlag = dataList.get(i).getResultFlag();
			if(tempId>0 && tempFlag.equals("N")){
				strResultFlag = "true";
				return strResultFlag;
			}
		}
		return strResultFlag = "false";
	}

	public String removeRequireAudit(String id) {
		
		boolean flag = rsd.updateDeleteFlag(id);
		
		return flag+"";
	}
	
}
