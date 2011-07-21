package com.xpsoft.oa.service.personal.impl;

import java.util.Iterator;

import javax.annotation.Resource;

import com.xpsoft.core.jbpm.pv.ParamField;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.action.flow.FlowRunInfo;
import com.xpsoft.oa.dao.flow.FormDataDao;
import com.xpsoft.oa.dao.personal.ErrandsRegisterDao;
import com.xpsoft.oa.model.flow.FormData;
import com.xpsoft.oa.model.personal.ErrandsRegister;
import com.xpsoft.oa.service.personal.ErrandsRegisterService;

public class ErrandsRegisterServiceImpl extends
		BaseServiceImpl<ErrandsRegister> implements ErrandsRegisterService {
	private ErrandsRegisterDao dao;

	public ErrandsRegisterServiceImpl(ErrandsRegisterDao dao) {
		super(dao);
		this.dao = dao;
	}
}
