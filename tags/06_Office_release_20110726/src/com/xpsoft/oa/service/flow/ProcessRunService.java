package com.xpsoft.oa.service.flow;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.action.flow.FlowRunInfo;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.model.flow.ProcessRun;
import java.util.List;

public abstract interface ProcessRunService extends BaseService<ProcessRun>
{
  public abstract ProcessRun initNewProcessRun(ProDefinition paramProDefinition);

  public abstract void saveProcessRun(ProcessRun paramProcessRun, ProcessForm paramProcessForm, FlowRunInfo paramFlowRunInfo);

  public abstract ProcessRun getByExeId(String paramString);

  public abstract ProcessRun getByTaskId(String paramString);

  public abstract ProcessRun getByPiId(String paramString);

  public abstract void saveAndNextStep(FlowRunInfo paramFlowRunInfo);

  public abstract void removeByDefId(Long paramLong);

  public abstract List<ProcessRun> getByUserIdSubject(Long paramLong, String paramString, PagingBean paramPagingBean);
}
