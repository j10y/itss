package com.htsoft.oa.dao.admin.impl;

import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.dao.admin.BookBorRetDao;
import com.htsoft.oa.model.admin.BookBorRet;

public class BookBorRetDaoImpl extends BaseDaoImpl<BookBorRet> implements
		BookBorRetDao {
	public BookBorRetDaoImpl() {
		/* 17 */super(BookBorRet.class);
	}

	public BookBorRet getByBookSnId(Long bookSnId) {
		/* 23 */String hql = "from BookBorRet bookBorRet where bookBorRet.bookSn.bookSnId=?";
		/* 24 */Object[] params = { bookSnId };
		/* 25 */return (BookBorRet) findByHql(hql, params).get(0);
	}

	public List<BookBorRet> getBorrowInfo(PagingBean pb) {
		/* 32 */String hql = "select bookBorRet from BookBorRet bookBorRet,BookSn bookSn where bookBorRet.bookSn.bookSnId=bookSn.bookSnId and bookSn.status=1";
		/* 33 */return findByHql(hql, null, pb);
	}

	public List<BookBorRet> getReturnInfo(PagingBean pb) {
		/* 40 */String hql = "select bookBorRet from BookBorRet bookBorRet,BookSn bookSn where bookBorRet.bookSn.bookSnId=bookSn.bookSnId and bookSn.status=0";
		/* 41 */return findByHql(hql, null, pb);
	}

	public Long getBookBorRetId(Long snId) {
		/* 46 */String hql = "from BookBorRet vo where vo.bookSn.bookSnId=?";
		/* 47 */Object[] objs = { snId };
		/* 48 */List list = findByHql(hql, objs);
		/* 49 */if (list.size() == 1) {
			/* 50 */return ((BookBorRet) list.get(0)).getRecordId();
		}
		/* 52 */return null;
	}

}
