package com.zsgj.itil.finance.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.finance.dao.UserCostInputDao;
import com.zsgj.itil.finance.entity.FinanceConstant;
import com.zsgj.itil.finance.entity.FinanceCostCenter;
import com.zsgj.itil.finance.entity.FinanceCostSchedules;
import com.zsgj.itil.finance.entity.FinanceCostType;
import com.zsgj.itil.finance.service.UserCostInputService;

public class UserCostInputServiceImpl implements UserCostInputService{

	private UserCostInputDao userCostInputDao;
	public UserCostInputDao getUserCostInputDao() {
		return userCostInputDao;
	}
	public void setUserCostInputDao(UserCostInputDao userCostInputDao) {
		this.userCostInputDao = userCostInputDao;
	}
	/**
	 * 把人员成本页面参数放入到成本明细实体中
	 */
	public void saveUserCostMesg(FinanceCostSchedules financeCostSchedules,
			Map paramMap,String costCenter) {
		financeCostSchedules.setAmount(BigDecimal.valueOf(Double.valueOf((String)paramMap.get("costAmount"))));
		financeCostSchedules.setCostReduceType(FinanceConstant.COSTREDUCECONFIG);//默认配置项
		financeCostSchedules.setBorrowType(FinanceConstant.BORROWTYPEEXPENSE);//默认直接报销
		financeCostSchedules.setCostDataSource(FinanceConstant.COSTDATARESOURECESYSTEM);
		if(paramMap.get("costCenter")!=null&&!"".equals(paramMap.get("costCenter"))){
			FinanceCostCenter financeCostCenter = userCostInputDao.findFinanceCostCenterByCode((String)paramMap.get("costCenter"));
			financeCostSchedules.setCostCenter(financeCostCenter);
		}else{
//			String  costCenterCode= costCenter.split("/")[1];
//			FinanceCostCenter financeCostCenter = userCostInputDao.findFinanceCostCenterByCode(costCenterCode);
//			financeCostSchedules.setCostCenter(financeCostCenter);
		}
		String costData = (String)paramMap.get("costDate");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			financeCostSchedules.setCostDate(simpleDateFormat.parse(costData));
			String configItemCode = userCostInputDao.findInterPartmentCode();
			FinanceCostType financeCostType = userCostInputDao.findFinanceCostType();
			financeCostSchedules.setCostItem(configItemCode);
			financeCostSchedules.setFinanceCostType(financeCostType);
			userCostInputDao.saveUserCostMesg(financeCostSchedules);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 得到所有的成本中心类型
	 * @return
	 */
	public Page findFinanceCostCenterBySpecialParam(int pageNo, int pageSize,String propertyValue) {
		// TODO Auto-generated method stub
		Page page = userCostInputDao.findFinanceCostCenterBySpecialParam(pageNo, pageSize, propertyValue);
		return page;
	}
	

}
