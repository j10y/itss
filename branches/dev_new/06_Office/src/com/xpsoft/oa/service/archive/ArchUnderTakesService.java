package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.archive.ArchUnderTakes;

public interface ArchUnderTakesService extends BaseService<ArchUnderTakes>{
	public String findArchUnderTakesByArchId(String id);
	public String saveArchUnderTakesByArchId(String id,String userids);
	public String saveArchUnderTakesByArchIdAndSign(String id,String upsignUserIds);
	public String findArchUnderTakesUpSignByArchId(String id);
}
