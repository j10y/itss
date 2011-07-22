package com.xpsoft.oa.service.flow;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.flow.ProUserAssign;
import java.util.List;

public abstract interface ProUserAssignService extends BaseService<ProUserAssign> {
	
	public abstract List<ProUserAssign> getByDeployId(String paramString);

	public abstract ProUserAssign getByDeployIdActivityName(String paramString1, String paramString2);
}
