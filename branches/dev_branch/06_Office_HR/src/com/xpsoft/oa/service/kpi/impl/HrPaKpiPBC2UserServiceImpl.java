package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiPBC2UserDao;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;

public class HrPaKpiPBC2UserServiceImpl extends BaseServiceImpl<HrPaKpiPBC2User>
		implements HrPaKpiPBC2UserService {
	private HrPaKpiPBC2UserDao dao;
	
	public HrPaKpiPBC2UserServiceImpl(HrPaKpiPBC2UserDao dao) {
		super(dao);
		this.dao = dao;
	}
	
}
