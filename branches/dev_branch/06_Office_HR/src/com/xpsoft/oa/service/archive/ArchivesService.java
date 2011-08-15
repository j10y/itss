package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.system.AppRole;
import java.util.List;
import java.util.Set;

public abstract interface ArchivesService extends BaseService<Archives> {
	public abstract List<Archives> findByUserOrRole(Long paramLong,
			Set<AppRole> paramSet, PagingBean paramPagingBean);
}
