package com.xpsoft.oa.service.task.impl;

import java.util.List;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.task.PlanAttendDao;
import com.xpsoft.oa.model.task.PlanAttend;
import com.xpsoft.oa.service.task.PlanAttendService;

public class PlanAttendServiceImpl extends BaseServiceImpl<PlanAttend>
		implements PlanAttendService {
	private PlanAttendDao dao;

	public PlanAttendServiceImpl(PlanAttendDao dao) {
		/* 17 */super(dao);
		/* 18 */this.dao = dao;
	}

	public boolean deletePlanAttend(Long planId, Short isDep, Short isPrimary) {
		/* 23 */List<PlanAttend> list = this.dao.FindPlanAttend(planId, isDep,
				isPrimary);
		/* 24 */for (PlanAttend pa : list) {
			/* 25 */this.dao.remove(pa.getAttendId());
		}
		/* 27 */return true;
	}
}