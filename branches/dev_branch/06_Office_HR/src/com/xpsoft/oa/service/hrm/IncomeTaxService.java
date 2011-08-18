package com.xpsoft.oa.service.hrm;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.IncomeTax;

public abstract interface IncomeTaxService extends BaseService<IncomeTax>
{
  public abstract List<IncomeTax> findByHql(String paramString);

  public abstract List<IncomeTax> findIncomeTax();
}
