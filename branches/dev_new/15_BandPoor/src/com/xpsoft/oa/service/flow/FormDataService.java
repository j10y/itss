package com.xpsoft.oa.service.flow;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.flow.FormData;
import java.util.Map;

public abstract interface FormDataService extends BaseService<FormData>
{
  public abstract Map<String, Object> getFromDataMap(Long paramLong, String paramString);
}
