package com.xpsoft.oa.service.bandpoor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.SaleStore;

public abstract interface ScoreManageService extends BaseService<InfoPoor>{
	public boolean validateUnique(Map params);
	
	public void saveInfoPoor(List<InfoPoor> list);
	
	public Map checkBrand(int row,Sheet sheet,HttpServletRequest request,String saleStoreName,SaleStore saleStoreId,String checkUser,String checkDate);
}
