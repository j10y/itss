package com.zsgj.itil.system.dao;

import com.zsgj.info.framework.dao.support.Page;

public interface UtilDao {

	Page searchComboMessage(Class clazz, String propertyName, Object value, boolean isLike, String orderBy, boolean isAsc,
			int pageNo, int pageSize);

}
