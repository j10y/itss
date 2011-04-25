package com.digitalchina.itil.event.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.itil.actor.entity.SupportGroup;
import com.digitalchina.itil.event.dao.SearchEventStateDao;
import com.digitalchina.itil.event.entity.Event;

public class SearchEventStateDaoImpl extends BaseDao implements
		SearchEventStateDao {
	
	/**
	 * 数据库事件状态查询的grid数据信息
	 * @param 
	 * @Methods Name selectSearchEventStateGridInfo
	 * @Create In 2010-6-24 By zhangzy
	 * @return Page
	 */
	public Page selectSearchEventStateGridInfo(String summary,
			String eventStatus, String supportGroup, String createUser,
			String dealuser ,int  pageNo, int pageSize) {
		//2010-07-20 modified by huzh for 没有服务项的事件无法显示的问题 begin
		try {		
			String querySql = "select e.*,s.* ";
			String queryCount="select count(*) ";
			String subSql= " FROM Event e "
						    + "LEFT OUTER JOIN EventStatus st ON e.eventStatus = st.id " 
						    + "LEFT OUTER JOIN sUserInfos cUser ON e.createUser = cUser.ID " 
						    + "LEFT OUTER JOIN sUserInfos dUser ON e.dealuser = dUser.ID " 
						    + "LEFT OUTER JOIN SupportGroup s " 
						    + "INNER JOIN SupportGroupEngineer se " 
					     	+ "ON s.id = se.supportGroup " 
					     	+ "ON e.dealuser = se.userInfo where 1=1";
					if(summary!=null&&"".equals(summary.trim())==false){
						subSql+=" and e.summary like :summaryName";
					}
					if(eventStatus!=null&&"".equals(eventStatus.trim())==false){
						subSql+=" and st.id= :eventStatusId";
					}
					if(supportGroup!=null&&"".equals(supportGroup.trim())==false){
						subSql+=" and s.groupName like :gName";
					}
					if(createUser!=null&&"".equals(createUser.trim())==false){
						subSql+=" and (cUser.userName like :cUserName or cUser.realName like :cUserName)";
					}
					if(dealuser!=null&&"".equals(dealuser.trim())==false){
						subSql+=" and (dUser.userName like :dUserName or dUser.realName like :dUserName)";
					}
					String orderSql =" order by e.id desc";
					List qList = createquery(summary, eventStatus, supportGroup,
							createUser, dealuser, pageNo, pageSize, querySql+subSql+orderSql);
					Integer count = (Integer) createCountquery(summary, eventStatus, supportGroup,
							createUser, dealuser, pageNo, pageSize, queryCount+subSql);
					return new Page(pageNo,count,pageSize,qList);
					//2010-07-20 modified by huzh for 没有服务项的事件无法显示的问题 end
		}catch(Exception e){
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}

	}
	//查询所有数据
	private List createquery(String summary, String eventStatus,
			String supportGroup, String createUser, String dealuser,
			int pageNo, int pageSize, String querySql) {
		Query q=this.getSession().createSQLQuery(querySql)
					.addEntity("e", Event.class)
					.addEntity("s", SupportGroup.class)
					.setFirstResult((pageNo-1)*pageSize)
					.setMaxResults(pageSize);
		if(summary!=null&&"".equals(summary.trim())==false){
			q.setParameter("summaryName", "%"+summary+"%");
		}
		if(eventStatus!=null&&"".equals(eventStatus.trim())==false){
			q.setParameter("eventStatusId", Long.valueOf(eventStatus));
		}
		if(supportGroup!=null&&"".equals(supportGroup.trim())==false){
			q.setParameter("gName", "%"+supportGroup+"%");
		}
		if(createUser!=null&&"".equals(createUser.trim())==false){
			q.setParameter("cUserName", "%"+createUser+"%");
		}
		if(dealuser!=null&&"".equals(dealuser.trim())==false){
			q.setParameter("dUserName", "%"+dealuser+"%");
		}
		return q.list();
	}
	//查询总数
	private Integer createCountquery(String summary, String eventStatus,
			String supportGroup, String createUser, String dealuser,
			int pageNo, int pageSize, String querySql) {
		Query q=this.getSession().createSQLQuery(querySql);
		if(summary!=null&&"".equals(summary.trim())==false){
			q.setParameter("summaryName", "%"+summary+"%");
		}
		if(eventStatus!=null&&"".equals(eventStatus.trim())==false){
			q.setParameter("eventStatusId", Long.valueOf(eventStatus));
		}
		if(supportGroup!=null&&"".equals(supportGroup.trim())==false){
			q.setParameter("gName", "%"+supportGroup+"%");
		}
		if(createUser!=null&&"".equals(createUser.trim())==false){
			q.setParameter("cUserName", "%"+createUser+"%");
		}
		if(dealuser!=null&&"".equals(dealuser.trim())==false){
			q.setParameter("dUserName", "%"+dealuser+"%");
		}
		return (Integer) q.uniqueResult();
	}
	
	
}
