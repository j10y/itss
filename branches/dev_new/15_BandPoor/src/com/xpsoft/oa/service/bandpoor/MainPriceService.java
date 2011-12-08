package com.xpsoft.oa.service.bandpoor;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.MainPrice;

public abstract interface MainPriceService extends BaseService<MainPrice>{
	public boolean validateUnique(Map params);}
