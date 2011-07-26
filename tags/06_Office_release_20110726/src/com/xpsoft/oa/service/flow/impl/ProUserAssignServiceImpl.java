package com.xpsoft.oa.service.flow.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.flow.ProUserAssignDao;
import com.xpsoft.oa.model.flow.ProUserAssign;
import com.xpsoft.oa.service.flow.ProUserAssignService;
import java.util.List;

public class ProUserAssignServiceImpl extends BaseServiceImpl<ProUserAssign>
		implements ProUserAssignService {
	private ProUserAssignDao dao;

	public ProUserAssignServiceImpl(ProUserAssignDao dao) {
		super(dao);
		this.dao = dao;
	}

	public List<ProUserAssign> getByDeployId(String deployId) {
		return this.dao.getByDeployId(deployId);
	}

	public ProUserAssign getByDeployIdActivityName(String deployId,
			String activityName) {
		return this.dao.getByDeployIdActivityName(deployId, activityName);
	}
}
