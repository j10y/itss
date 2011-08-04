package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.archive.ArchivesHandle;
import java.util.List;

public abstract interface ArchivesHandleService extends
		BaseService<ArchivesHandle> {
	public abstract ArchivesHandle findByUAIds(Long paramLong1, Long paramLong2);

	public abstract List<ArchivesHandle> findByAid(Long paramLong);

	public abstract int countHandler(Long paramLong);
}
