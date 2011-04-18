package com.digitalchina.itil.event.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.itil.event.dao.CCTelInfoDao;
import com.digitalchina.itil.event.entity.CCCallInfo;

public class CCTelInfoDaoImpl extends BaseDao implements CCTelInfoDao {

	public List<CCCallInfo> selectUnEmailCCCallInfo() {
		Criteria c = createCriteria(CCCallInfo.class);
		c.createAlias("this.event", "event").setFetchMode("event",FetchMode.JOIN);
		c.add(Restrictions.eq("selfFlag",Integer.parseInt("1")));
		c.add(Restrictions.eq("mailFlag",Integer.parseInt("0")));
		c.add(Restrictions.isNotNull("event"));
		List list = c.list();
		return list;
	}
	public List<CCCallInfo> selectNoFeedBackofCCCall() {
		Criteria c = createCriteria(CCCallInfo.class);
		c.createAlias("this.event", "event").setFetchMode("event",FetchMode.JOIN);
		c.add(Restrictions.eq("selfFlag",CCCallInfo.SELFFLAG_YES));
		c.add(Restrictions.eq("mailFlag",CCCallInfo.MAILFLAG_NO));
		c.add(Restrictions.ne("satisSynFlag",CCCallInfo.TELFLAG_YES));
		c.add(Restrictions.isNotNull("event"));
		List list = c.list();
		return list;
	}

}
