package com.zsgj.itil.event.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.actor.entity.SupportGroupRank;
import com.zsgj.itil.actor.entity.SupportGroupServiceItem;
import com.zsgj.itil.event.dao.SupportGroupDao;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventTypeServiceItem;
import com.zsgj.itil.service.entity.ServiceItem;

public class SupportGroupDaoImpl extends BaseDao<SupportGroup> implements
		SupportGroupDao {

	@SuppressWarnings("unchecked")
	public List<SupportGroup> selectAllGroup(ServiceItem serviceItem)
			throws DaoException {
		try {
			Criteria c = createCriteria(SupportGroupServiceItem.class)
						.createAlias("supportGroup", "s")
						.add(Restrictions.eq("serviceItem", serviceItem))
						.add(Restrictions.eq("s.deleteFlag", Integer.valueOf(0)))
			 			.setProjection(Property.forName("supportGroup"))
			 			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List l=c.list();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
		
	}

	public List<SupportGroupEngineer> selectAllEngineer(
			List<SupportGroup> supportGroups) throws DaoException {
		try {
			Criteria c= createCriteria(SupportGroupEngineer.class);
			c.add(Restrictions.in("supportGroup", supportGroups));
			return c.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	
	public Page selectAllEngineer(
			List<SupportGroup> supportGroups,String itcode,int start,int pageSize) throws DaoException {
		try {
			 Criteria count=createCriteria(SupportGroupEngineer.class);
			 if(!supportGroups.isEmpty()){
				 
				 count.add(Restrictions.in("supportGroup", supportGroups));
			 }
			 if(itcode!=null&&itcode.trim().length()!=0){
				 count.createAlias("userInfo", "user");
				 count.add(Restrictions.like("user.itcode", itcode, MatchMode.ANYWHERE));
			 }
			 long totalCount=((Integer)count.setProjection(Projections.rowCount()).uniqueResult()).longValue();
			 Criteria c=createCriteria(SupportGroupEngineer.class)
				.setFetchMode("userInfo", FetchMode.JOIN)
				.createAlias("userInfo", "user")
				.setFetchMode("user.department",FetchMode.JOIN)
				.setFirstResult(start)
				.setMaxResults(pageSize);
			 if(!supportGroups.isEmpty()){
				 c.add(Restrictions.in("supportGroup", supportGroups));
			 }
			 if(itcode!=null&&itcode.trim().length()!=0){
				 c.add(Restrictions.like("user.itcode", itcode, MatchMode.ANYWHERE));
			 }
			 List<SupportGroupEngineer> supportGroupEngineers=c.list();
			 return new Page(start,totalCount,pageSize,supportGroupEngineers);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	
	public List<SupportGroupEngineer> selectAllEngineer(
			SupportGroup supportGroup) throws DaoException {
		try {
			Criteria c= createCriteria(SupportGroupEngineer.class);
			c.add(Restrictions.eq("supportGroup", supportGroup));
			return c.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Boolean deleteSupportGroupByIds(String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				Criteria criteria = getCriteria(SupportGroup.class);
				Long id = Long.parseLong(ids[i]);
				criteria.add(Restrictions.eq("id",id));
				SupportGroup supportGroup = (SupportGroup) criteria.uniqueResult();
				supportGroup.setDeleteFlag(SupportGroup.DELETEFLAG_DELETE);
				supportGroup = (SupportGroup) super.save(supportGroup);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//	public List<ServiceEngineerIn> selectServiceEngineerIn(String serviceId) {
//		Criteria c = createCriteria(ServiceEngineerIn.class);
//		ServiceProviderIn serviceProviderIn = new ServiceProviderIn();
//		serviceProviderIn.setId(Long.parseLong(serviceId));
//		c.add(Restrictions.eq("serviceProviderIn", serviceProviderIn));
//		return c.list();
//	}

//	public List<ServiceEngineerOut> selectServiceEngineerOut(String serviceId) {
//		Criteria c = createCriteria(ServiceEngineerOut.class);
//		ServiceProviderOut serviceProviderOut = new ServiceProviderOut();
//		serviceProviderOut.setId(Long.parseLong(serviceId));
//		c.add(Restrictions.eq("serviceProviderOut", serviceProviderOut));
//		return c.list();
//		
//	}

	public List<ServiceItem> selectServiceItemData(String official) {
		Criteria c = createCriteria(ServiceItem.class);
		if(official!=null&&"".equals(official)==false){
			c.add(Restrictions.eq("official",Integer.valueOf(official)));//2010-05-28 add by huzh for支持组创建处
		}
		return c.list();
		
	}

	public List<SupportGroupEngineer> selectServiceLead(String id) {
		Criteria c = createCriteria(SupportGroupEngineer.class);
		UserInfo userInfo = new UserInfo();
		userInfo.setId(Long.parseLong(id));
		c.add(Restrictions.eq("userInfo", userInfo));
		return c.list();
	}

//	public List<ServiceProviderIn> selectServiceProviderIn() {
//		return createCriteria(ServiceProviderIn.class).list();
//	}
	
//	public List<ServiceProviderOut> selectServiceProviderOut() {
//		return createCriteria(ServiceProviderOut.class).list();
//	}

	public List<SupportGroupServiceItem> selectSupportGroupData(String supportId) {
		Criteria c = createCriteria(SupportGroupServiceItem.class);
		SupportGroup supportGroup = new SupportGroup();
		supportGroup.setId(Long.parseLong(supportId));
		c.add(Restrictions.eq("supportGroup", supportGroup));
		return c.list();
	}

	public List<SupportGroupServiceItem> selectSupportGroupServiceItem(ServiceItem keyword) {
		Criteria c=createCriteria(SupportGroupServiceItem.class);
		c.add(Restrictions.eq("serviceItem", keyword));
		return c.list();
	}

//	public Page selectServiceEngineer(Long serviceProviderType, Long serviceProvider,String itcode,
//			int start, int pageSize) {
//		try {
//			ServiceProviderType providerType=get(ServiceProviderType.class, serviceProviderType);
//			Criteria count=createCriteria(Class.forName("com.zsgj.itil.actor.entity.ServiceEngineer"+providerType.getDiscValue()))
//			               .setProjection(Projections.rowCount());
//			if(serviceProvider!=null){
//				count.add(Restrictions.eq("serviceProvider"+providerType.getDiscValue()+".id",serviceProvider));
//			}
//			if(itcode.length()!=0){
//				count.createAlias("userInfo", "user");
//				count.add(Restrictions.like("user.itcode", itcode, MatchMode.ANYWHERE));
//			}
//			Long totalSize=((Integer)count.uniqueResult()).longValue();
//			Criteria c=createCriteria(Class.forName("com.zsgj.itil.actor.entity.ServiceEngineer"+providerType.getDiscValue()))
//			.createAlias("userInfo", "user")
//			.setFetchMode("userInfo", FetchMode.JOIN)
//			.setFetchMode("user.department", FetchMode.JOIN)
//			.setProjection(Projections.property("userInfo"))
//			.setFirstResult(start)
//			.setMaxResults(pageSize);
//			if(serviceProvider!=null){
//				c.add(Restrictions.eq("serviceProvider"+providerType.getDiscValue()+".id",serviceProvider));
//			}
//			if(itcode.length()!=0){
//				c.add(Restrictions.like("user.itcode", itcode, MatchMode.ANYWHERE));
//			}
//			List list=c.list();
//			return new Page(start,totalSize,pageSize,list);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DaoException(e.getMessage());
//		}
//	}

//	public List<ServiceProviderType> selectServiceProviderType(String serviceProviderTypeId) {
//		List<ServiceProviderType> list = null;
//		if (serviceProviderTypeId == "" || serviceProviderTypeId == null) {
//			Criteria c = createCriteria(ServiceProviderType.class);
//			list = c.list();
//		} else {
//			Criteria c = createCriteria(ServiceProviderType.class);
//			c.add(Restrictions.eq("id", Long.parseLong(serviceProviderTypeId)));
//			list = c.list();
//		}
//		return list;
//	}
	
	@SuppressWarnings("unchecked")
	public UserInfo selectCurrentGroupLeader(Long userId, Long eventId) {
		try {
			//add by huzh in 20100305 start 需要添加事件的服务项这一过滤条件
			Criteria ce = super.createCriteria(Event.class);
			ce.add(Restrictions.eq("id",eventId));
			ce.setFetchMode("scidData", FetchMode.JOIN);
			Event ev = (Event) ce.uniqueResult();
//			System.out.println("event-------:"+ev.getId()+ev.getSummary());
			Criteria cs = createCriteria(SupportGroupServiceItem.class);
			cs.add(Restrictions.eq("serviceItem", ev.getScidData()));
			cs.setFetchMode("supportGroup", FetchMode.JOIN);
			cs.setProjection(Projections.property("supportGroup"));
			List<SupportGroup> supportGroups = cs.list();
//			System.out.println("supportGroups-----:"+"size: "+supportGroups.size());
//			for(SupportGroup ss: supportGroups){
//				System.out.println("所有选出的支持组：-----"+ss.getId()+ss.getGroupName()+ss.getGroupLeader().getRealName());
//			}
			//add by huzh in 20100305 end
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			UserInfo groupLeader = null;
			Criteria c = createCriteria(SupportGroupEngineer.class);
			c.add(Restrictions.eq("userInfo", userInfo));
			c.add(Restrictions.in("supportGroup", supportGroups));//add by huzh in 20100305
			c.setFetchMode("supportGroup", FetchMode.JOIN);
			c.setProjection(Projections.property("supportGroup"));
			List<SupportGroup> supportGroupList = c.list();
//			System.out.println("supportGroupList-----:"+"size: "+supportGroupList.size());
//			for(SupportGroup sss: supportGroupList){
//				System.out.println("所有选出的支持组：-----"+sss.getId()+sss.getGroupName()+sss.getGroupLeader().getRealName());
//			}
			if (supportGroupList.size() > 0) {
				SupportGroup sGroup = supportGroupList.get(0);
				groupLeader = sGroup.getGroupLeader();
			}
			return groupLeader;
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SupportGroup> selectSupportGroupList(ServiceItem serviceItem, UserInfo leader) {
		try {
			return createCriteria(SupportGroupServiceItem.class)
			.add(Restrictions.eq("serviceItem", serviceItem))
			.createAlias("supportGroup", "sg")
			.setFetchMode("supportGroup",FetchMode.JOIN)
		    .add(Restrictions.eq("sg.groupLeader", leader))
			.setProjection(Projections.property("supportGroup")).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> selectFirstRankGroup(Long[] serviceItemsIdArray,Long supportGroupId) throws DaoException {
		try {
			Criteria c=createCriteria(SupportGroupServiceItem.class)
					.createAlias("serviceItem", "si")
					.createAlias("supportGroup", "sg")
					.createAlias("sg.groupRank", "gr")
					.add(Restrictions.in("si.id", serviceItemsIdArray))
					.add(Restrictions.eq("gr.keyString", SupportGroupRank.FIRST_RANK))
					.setProjection(Projections.property("si.name"))
					.setProjection(Projections.distinct(Projections.property("si.name")));
					if(supportGroupId!=null){
						c.add(Restrictions.ne("sg.id", supportGroupId));
					}
					return c.list();
	} catch (Exception e) {
		e.printStackTrace();
		throw new DaoException(e.getMessage());
	}
	}

	public List<SupportGroupEngineer> selectEngineersBySupportGroupId(Long supportGroupId) throws DaoException {
		try {
			return createCriteria(SupportGroupEngineer.class)
			.setFetchMode("userInfo", FetchMode.JOIN)
			.createAlias("userInfo", "user")
			.setFetchMode("user.department",FetchMode.JOIN)
			.add(Restrictions.eq("supportGroup.id", supportGroupId)).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public void deleteAllEngineers(SupportGroup supportGroup) throws DaoException {
		try {
			String hql="delete from SupportGroupEngineer where supportGroup= ?";
			getHibernateTemplate().bulkUpdate(hql, supportGroup);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
		
	}

	public void deleteAllSupportGroupServiceItem(SupportGroup supportGroup) throws DaoException {
		try {
			String hql="delete from SupportGroupServiceItem where supportGroup =?";
			getHibernateTemplate().bulkUpdate(hql,supportGroup);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<SupportGroup> selectSupportGroupsByServiceItems(List<ServiceItem> siList) {
		try {
			Criteria c = createCriteria(SupportGroupServiceItem.class)
			.add(Restrictions.in("serviceItem", siList))
			.setFetchMode("supportGroup", FetchMode.JOIN)
			.setProjection(Projections.property("supportGroup"))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<SupportGroup> li=c.list();
			return li;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<UserInfo> selectCurrentGroupLeaders(Long userId) {
		try {
			DetachedCriteria etdCriteria = DetachedCriteria.forClass(SupportGroupEngineer.class)
							.add(Restrictions.eq("userInfo.id", userId))
							.setProjection(Property.forName("supportGroup.id"));
			Criteria  criteria=super.createCriteria(SupportGroup.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Subqueries.propertyIn("id", etdCriteria));
			criteria.add(dis);
			criteria.setProjection(Property.forName("groupLeader"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public Page selectAllEngineers(List<SupportGroup> supportGroups,
			String itcode, int start, int pageSize) {
		try {
			 Criteria c=createCriteria(SupportGroupEngineer.class)
				.setFetchMode("userInfo", FetchMode.JOIN)
				.createAlias("userInfo", "user")
				.setFetchMode("user.department",FetchMode.JOIN);
			 if(!supportGroups.isEmpty()){
				 c.add(Restrictions.in("supportGroup", supportGroups));
			 }
			 if(itcode!=null&&itcode.trim().length()!=0){
				 c.add(Restrictions.or(Restrictions.ilike("user.realName", itcode, MatchMode.ANYWHERE),
							Restrictions.ilike("user.userName", itcode, MatchMode.ANYWHERE)));
			 }
			 c.setProjection(Property.forName("userInfo"));
			c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 return super.pagedQuery(c, start, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<SupportGroup> selectSupportGroupsByLeader(UserInfo leader) {
		try {
			Criteria c = createCriteria(SupportGroup.class);
			c.add(Restrictions.eq("groupLeader", leader));
			c.add(Restrictions.eq("deleteFlag", Integer.valueOf(0)));//过滤已删除的支持组
			List<SupportGroup> li=c.list();
			return li;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<ServiceItem> selectServiceItemsBySupportGroupId(
			Long supportGroupId, Integer official) {
		try {
			Criteria c=createCriteria(SupportGroupServiceItem.class)
					.add(Restrictions.eq("supportGroup.id", supportGroupId))
					.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN)
					.add(Restrictions.eq("serviceItem.official", official))
			        .setProjection(Property.forName("serviceItem"));
			return c.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public SupportGroup selectSupportGroupByLeaderAndSupportId(Long supportId,
			Long leader) {
		try {
			Criteria c=createCriteria(SupportGroup.class)
					.add(Restrictions.eq("id", supportId))
					.add(Restrictions.eq("groupLeader.id", leader))
					.add(Restrictions.eq("deleteFlag", 0));
			return (SupportGroup) c.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<SupportGroup> selectSupportGroupByEventType(Long eventtype) {
		try {
			Criteria  c=super.createCriteria(EventTypeServiceItem.class)
						.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem",FetchMode.JOIN)
						.add(Restrictions.eq("eventType.id", eventtype))
						.add(Restrictions.eq("serviceItem.official", Integer.valueOf(1)))
						.setProjection(Property.forName("serviceItem"));
			List sIList=c.list();
			Criteria  criteria=super.createCriteria(SupportGroupServiceItem.class)
						.createAlias("this.supportGroup", "supportGroup").setFetchMode("supportGroup",FetchMode.JOIN)
						.add(Restrictions.eq("supportGroup.deleteFlag", Integer.valueOf(0)))
						.add(Restrictions.in("serviceItem", sIList))
						.setProjection(Property.forName("supportGroup"))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
	public List<SupportGroup> selectSupportGroupByEngineer(UserInfo engineer) {
		try {
			 Criteria firstC=createCriteria(SupportGroupEngineer.class)
							.createAlias("supportGroup", "s")
							.createAlias("s.groupRank", "rank")
							.add(Restrictions.eq("s.deleteFlag", Integer.valueOf(0)))
							.add(Restrictions.eq("rank.keyString", "first"))
							.add(Restrictions.eq("userInfo", engineer))
				 			.setProjection(Property.forName("supportGroup"))
				 			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 List<SupportGroup> firstList=firstC.list();
			 if(firstList!=null&&firstList.size()!=0){
				 return firstList;
			 }
			 Criteria secondC=createCriteria(SupportGroupEngineer.class)
							.createAlias("supportGroup", "s")
							.createAlias("s.groupRank", "rank")
							.add(Restrictions.eq("s.deleteFlag", Integer.valueOf(0)))
							.add(Restrictions.eq("rank.keyString", "second"))
							.add(Restrictions.eq("userInfo", engineer))
				 			.setProjection(Property.forName("supportGroup"))
				 			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 List<SupportGroup> secondList=secondC.list();
			 return secondList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<SupportGroup> selectSupportGroupByServiceItem(ServiceItem sitem) {
		try {
			 Criteria firstC=createCriteria(SupportGroupServiceItem.class)
							.createAlias("supportGroup", "s")
							.createAlias("s.groupRank", "rank")
							.add(Restrictions.eq("s.deleteFlag", Integer.valueOf(0)))
							.add(Restrictions.eq("rank.keyString", "first"))
							.add(Restrictions.eq("serviceItem", sitem))
				 			.setProjection(Property.forName("supportGroup"))
				 			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 List<SupportGroup> firstList=firstC.list();
			 if(firstList!=null&&firstList.size()!=0){
				 return firstList;
			 }
			 Criteria secondC=createCriteria(SupportGroupServiceItem.class)
							.createAlias("supportGroup", "s")
							.createAlias("s.groupRank", "rank")
							.add(Restrictions.eq("s.deleteFlag", Integer.valueOf(0)))
							.add(Restrictions.eq("rank.keyString", "second"))
							.add(Restrictions.eq("serviceItem", sitem))
				 			.setProjection(Property.forName("supportGroup"))
				 			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 List<SupportGroup> secondList=secondC.list();
			 return secondList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}

	public List<SupportGroup> selectSupportGroupByEngineer(UserInfo userInfo,ServiceItem scidData) {
		try {
			 Criteria groupC=createCriteria(SupportGroupEngineer.class)
							.createAlias("supportGroup", "s")
							.add(Restrictions.eq("s.deleteFlag", Integer.valueOf(0)))
							.add(Restrictions.eq("userInfo", userInfo))
				 			.setProjection(Property.forName("supportGroup"))
				 			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 List<SupportGroup> groupList=groupC.list();
			 Criteria returnC=createCriteria(SupportGroupServiceItem.class)
							.add(Restrictions.eq("serviceItem", scidData))
				 			.add(Restrictions.in("supportGroup", groupList))
				 			.setProjection(Property.forName("supportGroup"))
				 			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 List<SupportGroup> returnList=returnC.list();
			 if(returnList!=null&&returnList.size()!=0){
				 return returnList;
			 }else{
				 return groupList;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e.getMessage());
		}
	}
}
