package com.digitalchina.itil.event.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.CIBatchModify;
import com.digitalchina.itil.config.entity.CIBatchModifyShip;
import com.digitalchina.itil.event.dao.ProblemDao;
import com.digitalchina.itil.event.entity.Event;
import com.digitalchina.itil.event.entity.EventProblem;
import com.digitalchina.itil.event.entity.EventRelation;
import com.digitalchina.itil.event.entity.EventRelationType;
import com.digitalchina.itil.event.entity.EventStatus;
import com.digitalchina.itil.event.entity.Problem;
import com.digitalchina.itil.event.entity.ProblemRelation;
import com.digitalchina.itil.event.entity.ProblemStatus;

public class ProblemDaoImpl extends BaseDao<Problem> implements ProblemDao {

//	public Map selectAllNotEndProblemsOfAllEndEvents(Long userId, int start, int pageSize,
//			String problemName) throws DaoException {
//		try {
//			Map pageMap = new HashMap();
//			List<EventProblem> eventProblemList = new ArrayList<EventProblem>();
//			UserInfo submitUser = super.get(UserInfo.class, userId);
//			// 事件结束状态
//			EventStatus endEventStatus = (EventStatus) super.createCriteria(EventStatus.class).add(
//					Restrictions.eq("keyword", "finish")).uniqueResult();
//			// 问题结束状态
//			ProblemStatus endProblemStatus = (ProblemStatus) super.createCriteria(
//					ProblemStatus.class).add(Restrictions.eq("keyword", "finish")).uniqueResult();
//			// 问题处理中状态
//			ProblemStatus dealingProblemStatus = (ProblemStatus) super.createCriteria(
//					ProblemStatus.class).add(Restrictions.eq("keyword", "dealing")).uniqueResult();
//
//			Criteria criteria = super.createCriteria(EventProblem.class);
//			criteria.setFetchMode("event", FetchMode.JOIN);
//			criteria.createAlias("this.event", "event");
//			// criteria.add(Restrictions.eq("event.eventStatus",
//			// endEventStatus));
//			criteria.setFetchMode("problem", FetchMode.JOIN);
//			criteria.createAlias("this.problem", "problem");
//			criteria.add(Restrictions.eq("problem.status", dealingProblemStatus));
//			criteria.add(Restrictions.eq("problem.submitUser", submitUser)); // 过滤了本人的
//			if (problemName != null || "".equals(problemName)) {
//				criteria
//						.add(Restrictions.ilike("problem.summary", problemName, MatchMode.ANYWHERE));
//			}
//			int total = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult())
//					.intValue();
//
//			Criteria criteria1 = super.createCriteria(EventProblem.class);
//			criteria1.setFetchMode("event", FetchMode.JOIN);
//			criteria1.createAlias("this.event", "event");
//			// criteria1.add(Restrictions.eq("event.eventStatus",
//			// endEventStatus));
//			criteria1.setFetchMode("problem", FetchMode.JOIN);
//			criteria1.createAlias("this.problem", "problem");
//			criteria1.add(Restrictions.eq("problem.status", dealingProblemStatus));
//			criteria1.add(Restrictions.eq("problem.submitUser", submitUser)); // 过滤了本人的
//			if (problemName != null || "".equals(problemName)) {
//				criteria1.add(Restrictions
//						.ilike("problem.summary", problemName, MatchMode.ANYWHERE));
//			}
//			criteria1.addOrder(Order.asc("id"));
//			criteria1.setFirstResult(start);// start
//			criteria1.setMaxResults(pageSize);// pageSize
//			eventProblemList = criteria1.list();
//			pageMap.put("total", total);
//			pageMap.put("eventProblemList", eventProblemList);
//			return pageMap;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DaoException(e.getMessage());
//		}
//	}
	public Page selectAllNotEndProblemsOfAllEndEvents(Long userId, int start, int pageSize,
			String problemName) throws DaoException {
		try {
			UserInfo submitUser = super.get(UserInfo.class, userId);
			ProblemStatus dealingProblemStatus = (ProblemStatus) super.createCriteria(
					ProblemStatus.class).add(Restrictions.eq("keyword", "dealing")).uniqueResult();
			Criteria criteria1 = super.createCriteria(EventProblem.class);
			criteria1.setFetchMode("event", FetchMode.JOIN);
			criteria1.createAlias("this.event", "event");
			criteria1.setFetchMode("problem", FetchMode.JOIN);
			criteria1.createAlias("this.problem", "problem");
			criteria1.add(Restrictions.eq("problem.status", dealingProblemStatus));
			criteria1.add(Restrictions.eq("problem.submitUser", submitUser)); // 过滤了本人的
			if (problemName != null || "".equals(problemName)) {
				criteria1.add(Restrictions
						.ilike("problem.summary", problemName, MatchMode.ANYWHERE));
			}
			criteria1.addOrder(Order.asc("id"));
			Page page = this.pagedQuery(criteria1, start, pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectProblemsEvents(Event event, int start, int pageSize) throws DaoException {
		try {
			String hqlCount = "select count(distinct e.event) from EventProblem e ";
			List countlist = getHibernateTemplate().find(hqlCount, null);
			long totalCount = (Long) countlist.get(0);
			if (totalCount < 1)
				return new Page();
			// 实际查询返回分页对象
			int startIndex = Page.getStartOfPage(start, pageSize);

			String hql = "select distinct e.event from EventProblem e ";
			Query query = createQuery(hql, null);
			List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

			Page page = new Page(startIndex, totalCount, pageSize, list);

			return page;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<Problem> selectProblemByCurrProblem(Problem problem) throws DaoException {
		try {
			Criteria c = super.createCriteria(ProblemRelation.class);
			c.add(Restrictions.eq("problem", problem));
			c.createAlias("this.problemRelationType", "problemRelationType").setFetchMode(
					"problemRelationType", FetchMode.JOIN);
			c.add(Restrictions.ne("problemRelationType.typeFlag", "C"));// C代表当前问题是其它问题的子问题排除
			//add by huzh for bug in 20100412 begin,问题关闭时，该问题的关联问题不需要关闭
			c.add(Restrictions.ne("problemRelationType.typeFlag", "D"));// D代表当前问题是其它问题的关联问题排除			
			//add by huzh for bug in 20100412 end
			c.setFetchMode("parentProblem", FetchMode.JOIN);
			c.setProjection(Projections.property("parentProblem"));
			List<Problem> list = c.list();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectEventProblem(String dataId, String eventId, String name, int pageNo, int pageSize,String status)
			throws DaoException {
		Criteria cc = super.createCriteria(Problem.class);
		if(StringUtils.isNotBlank(dataId)){
			cc.add(Restrictions.eq("id", Long.valueOf(dataId)));
		}else{
			if(StringUtils.isNotBlank(name)){
				cc.add(Restrictions.ilike("summary", name, MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(status)){
				cc.createAlias("status", "s")
				.add(Restrictions.ne("s.keyword", "delete"));
			}
			if(StringUtils.isNotBlank(eventId)){
				Criteria c = super.createCriteria(EventProblem.class);
				c.add(Restrictions.eq("event.id", Long.valueOf(eventId)));
				List<EventProblem> list = c.list();
				if(!list.isEmpty()){
					List idlist = new ArrayList();
					for(EventProblem ep : list){
						idlist.add(ep.getProblem().getId());
					}
					cc.add(Restrictions.in("id", idlist));
				}else{
					cc.add(Restrictions.eq("id", Long.valueOf(0)));
				}
			}
		}
		Page page = this.pagedQuery(cc, pageNo, pageSize);
		return page;
	}

	public void deleteProblemDoubleRel(String relId) {
		try {
			Criteria c = super.createCriteria(ProblemRelation.class);
			c.setFetchMode("problem", FetchMode.JOIN);
			c.setFetchMode("parentProblem", FetchMode.JOIN);
			c.add(Restrictions.eq("id", Long.valueOf(relId)));
			ProblemRelation curTemp = (ProblemRelation) c.uniqueResult();
			Criteria c2 = super.createCriteria(ProblemRelation.class);
			c2.add(Restrictions.eq("problem.id", curTemp.getParentProblem().getId()));
			c2.add(Restrictions.eq("parentProblem.id", curTemp.getProblem().getId()));
			ProblemRelation curTemp2 = (ProblemRelation) c2.uniqueResult();
			this.remove(curTemp);
			this.remove(curTemp2);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectProblemRelByCurrEvent(Problem problem, int pageNo, int pageSize)
			throws DaoException {
		try {
			Criteria c = super.createCriteria(ProblemRelation.class);
			c.add(Restrictions.eq("problem", problem));
			Page page =super.pagedQuery(c, pageNo, pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public boolean selectIsExistProblem(Long currProblemId, Long parentProblemId) {
		try {
			Criteria c = super.createCriteria(ProblemRelation.class);
			c.add(Restrictions.eq("problem.id", currProblemId));
			c.add(Restrictions.eq("parentProblem.id", parentProblemId));
			List list = c.list();
			return !list.isEmpty();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public EventRelation selectEventRelationByEventRel(EventRelation eventRel) {
		try {
			EventRelationType EventRelTypeC =  super.findUniqueBy(EventRelationType.class, "typeFlag", EventRelationType.CHILD);//子事件
			EventRelationType EventRelTypeB = super.findUniqueBy(EventRelationType.class, "typeFlag", EventRelationType.PARENT);//父事件
			Criteria c = super.createCriteria(EventRelation.class);
			c.add(Restrictions.eq("event", eventRel.getParentEvent()));
			c.add(Restrictions.eq("parentEvent", eventRel.getEvent()));
			if((eventRel.getEventRelationType()).equals(EventRelTypeC)){
				c.add(Restrictions.eq("eventRelationType", EventRelTypeB));
			}else{
				c.add(Restrictions.eq("eventRelationType", EventRelTypeC));
			}
			EventRelation eventRelation = (EventRelation) c.uniqueResult();
			return eventRelation;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public void updateProblemsStatus(Long[] problemsId, String status_keyword) {
		try {
			
			StringBuilder hql=new StringBuilder();
			hql.append("update Problem p set p.status = (");
			hql.append(" select ps from ProblemStatus ps");
			hql.append(" where ps.keyword = :kw )");
			hql.append(" where p.id in ( :pid )");
			createQuery(hql.toString())
			.setString("kw", status_keyword)
			.setParameterList("pid", problemsId)
			.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<String> selectWhetherHasConfigItem(Long[] problemsId) {
		try {
			return createCriteria(CIBatchModifyShip.class)
			.createAlias("problem", "p")
			.add(Restrictions.in("p.id", problemsId))
			.setProjection(Projections.property("p.problemCisn"))
			.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	public List<CIBatchModifyShip> selectAllNoPassedCIBatchModifyShipByProblem(Long problemId){
		try {
			return createCriteria(CIBatchModifyShip.class)
			.add(Restrictions.isNotNull("problem"))
			.add(Restrictions.eq("problem.id", problemId))
			.createAlias("ciBatchModify", "cibm")
			.add(Restrictions.in("cibm.status", new Integer[]{CIBatchModify.STATUS_DRAFT,CIBatchModify.STATUS_PROCESSING}))
			.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
		
	}
}
