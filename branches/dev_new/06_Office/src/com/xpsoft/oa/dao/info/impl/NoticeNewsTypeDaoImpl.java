package com.xpsoft.oa.dao.info.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.info.NoticeNewsTypeDao;
import com.xpsoft.oa.model.info.NoticeNewsType;
import java.util.ArrayList;
import java.util.List;

public class NoticeNewsTypeDaoImpl extends BaseDaoImpl<NoticeNewsType>
		implements NoticeNewsTypeDao {
	public NoticeNewsTypeDaoImpl() {
		/* 17 */super(NoticeNewsType.class);
	}

	public Short getTop() {
		/* 25 */String hql = "select max(sn) from NoticeNewsType";
		/* 26 */List list = findByHql("select max(sn) from NoticeNewsType");
		/* 27 */return (Short) list.get(0);
	}

	public List<NoticeNewsType> getAllBySn() {
		/* 34 */String hql = "from NoticeNewsType nt order by nt.sn asc";
		/* 35 */return findByHql("from NoticeNewsType nt order by nt.sn asc");
	}

	public List<NoticeNewsType> getAllBySn(PagingBean pb) {
		/* 42 */String hql = "from NoticeNewsType nt order by nt.sn asc";
		/* 43 */return findByHql("from NoticeNewsType nt order by nt.sn asc", null,
				pb);
	}

	public NoticeNewsType findBySn(Short sn) {
		/* 50 */String hql = "from NoticeNewsType nt where nt.sn=?";
		/* 51 */Object[] objs = { sn };
		/* 52 */List list = findByHql("from NoticeNewsType nt where nt.sn=?", objs);
		/* 53 */return (NoticeNewsType) list.get(0);
	}

	public List<NoticeNewsType> findBySearch(NoticeNewsType newsType, PagingBean pb) {
		/* 60 */StringBuffer hql = new StringBuffer(
				"from NoticeNewsType nt where 1=1 ");
		/* 61 */List params = new ArrayList();
		/* 62 */if (newsType != null) {
			/* 63 */if ((!"".equals(newsType.getTypeName()))
					&& (newsType.getTypeName() != null)) {
				/* 64 */hql.append("and nt.typeName like ?");
				/* 65 */params.add("%" + newsType.getTypeName() + "%");
			}
			/* 67 */if ((newsType.getSn() != null)
					&& (newsType.getSn().shortValue() > 0)) {
				/* 68 */hql.append("and nt.sn = ?");
				/* 69 */params.add(newsType.getSn());
			}
		}
		/* 72 */hql.append("order by nt.sn asc");
		/* 73 */return findByHql(hql.toString(), params.toArray(), pb);
	}
}
