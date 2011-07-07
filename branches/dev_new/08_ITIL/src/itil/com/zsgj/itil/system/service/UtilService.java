package com.zsgj.itil.system.service;

import com.zsgj.info.framework.dao.support.Page;

public interface UtilService {

	Page searchComboMessage(Class clazz, String propertyName, String value, boolean isLike, 
			String orderBy, boolean isAsc, int pageNo, int pageSize);
}
