package com.xpsoft.oa.model.hrm;

import java.math.BigDecimal;

import com.xpsoft.core.model.BaseModel;

public class JobSalaryRelation extends BaseModel {

	protected Long relationId;
	protected Job job;//岗位
	protected StandSalary standSalary;//薪资标准
	protected Integer empCount;//人数
	protected BigDecimal totalMoney;
}
