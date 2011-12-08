package com.xpsoft.oa.service.bandpoor;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.ProClass;

public abstract interface ProClassService extends BaseService<ProClass>{
	public boolean validateUnique(Map params);}
